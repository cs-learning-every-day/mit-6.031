import assert from 'assert';
import { performance } from 'perf_hooks';

import { Instrument } from './Instrument';
import { Pitch } from './Pitch';
import { SequencePlayer } from './SequencePlayer';

import JZZ from 'jzz';
import JZZ_MIDI_SMF from 'jzz-midi-smf';
import type { MTrk } from 'jzz-midi-smf';

JZZ_MIDI_SMF(JZZ);

const { MIDI } = JZZ;

/**
 * Default tempo.
 */
export const DEFAULT_BEATS_PER_MINUTE = 120;

/**
 * Default MIDI ticks per beat.
 */
export const DEFAULT_TICKS_PER_BEAT = 64;

// MIDI note number representing middle C
const MIDI_NOTE_MIDDLE_C = 60;

// MIDI marker meta message type
const MIDI_META_MARKER = 6;

/**
 * @returns the MIDI note number for a pitch, defined as the number of
 *          semitones above C 5 octaves below middle C; for example,
 *          middle C is note 60
 */
function getMidiNote(pitch: Pitch): number {
    return MIDI_NOTE_MIDDLE_C + pitch.difference(Pitch.MIDDLE_C);
}

/**
 * Schedules and plays a sequence of notes using the MIDI synthesizer.
 */
export class MidiSequencePlayer implements SequencePlayer {

    // queued MIDI events before playback begins
    private readonly queued: MTrk;
    
    // MIDI output device to handle callbacks
    private readonly midiOut;
    
    // time at which playback began, iff started
    private playStartTime: number|undefined;
    
    // counter of future MIDI events
    private unplayedEvents: number = 0;
    
    // instruments that have been used (selected into MIDI channels) so far
    // maps an instrument to a MIDI channel number
    private readonly channelForInstrument: Map<Instrument, number> = new Map();
    
    // event callback functions
    private readonly callbacks: Map<number, ()=>void> = new Map();
    private readonly doneCallback = -1;

    /**
     * Make a new MIDI sequence player.
     * 
     * @param beatsPerMinute number of beats per minute
     * @param ticksPerBeat number of MIDI ticks per beat
     */
    public constructor(
        private readonly beatsPerMinute: number = DEFAULT_BEATS_PER_MINUTE,
        private readonly ticksPerBeat: number = DEFAULT_TICKS_PER_BEAT
    ) {
        this.queued = new MIDI.SMF.MTrk();
        const thisMSP = this;
        this.midiOut = JZZ.Widget({
            _receive(this: ReturnType<typeof JZZ.Widget>, msg: typeof MIDI & { ff?: number }) {
                if (msg.isSMF() && msg.ff === MIDI_META_MARKER) {
                    // meta event indicates `addEvent(..)` callback
                    const id = parseInt(msg.getText());
                    const callback = thisMSP.callbacks.get(id);
                    if (callback) {
                        thisMSP.callbacks.delete(id);
                        callback();
                    }
                }
                // send this message to the MIDI output device
                this.emit(msg);
                if (--thisMSP.unplayedEvents <= 0) {
                    // no future events, `play()` is done
                    thisMSP.callbacks.get(thisMSP.doneCallback)?.();
                }
            }
        });
        this.checkRep();
    }
    
    private checkRep() {
        assert(this.beatsPerMinute >= 0);
        assert(this.ticksPerBeat >= 0);
    }

    /** @returns milliseconds since playback began (requires currently playing) */
    private msSincePlayStart(beat: number): number {
        assert(this.playStartTime !== undefined);
        return this.ms(beat) - (performance.now() - this.playStartTime);
    }

    /** @returns beats in milliseconds */
    private ms(beats: number): number {
        return beats / this.beatsPerMinute * 60 * 1000;
    }

    /**
     * @inheritdoc
     */
    public addNote(instr: Instrument, pitch: Pitch, startBeat: number, numBeats: number): void {
        const channel = this.getChannel(instr);
        const note = getMidiNote(pitch);

        if (this.playStartTime !== undefined) {
            const startTime = this.msSincePlayStart(startBeat);
            this.midiOut.wait(startTime).noteOn(channel, note);
            // TODO: (removing 1 ms avoids some timing glitches) find a better way to stream events
            this.midiOut.wait(startTime + this.ms(numBeats) - 1).noteOff(channel, note);
        } else {
            this.queued.add(startBeat * this.ticksPerBeat, MIDI.noteOn(channel, note));
            this.queued.add((startBeat + numBeats) * this.ticksPerBeat, MIDI.noteOff(channel, note));
        }
        this.unplayedEvents += 2;
    }

    public addEvent(callback: (beat:number)=>void, atBeat: number): void {
        let id = 0;
        while (this.callbacks.has(id)) { id++; }
        this.callbacks.set(id, () => callback(atBeat));
        if (this.playStartTime !== undefined) {
            this.midiOut.wait(this.msSincePlayStart(atBeat)).smfMarker(id.toString());
        } else {
            this.queued.add(atBeat * this.ticksPerBeat, MIDI.smfMarker(id.toString()));
        }
        this.unplayedEvents++;
    }

    /**
     * @inheritdoc
     */
    public async start(): Promise<void> {
        await this.midiOut.connect(await JZZ().openMidiOut().or('cannot open MIDI output port'));
        for (const midi of this.queued) {
            this.midiOut.wait(midi.tt / this.ticksPerBeat / this.beatsPerMinute * 60 * 1000).send(midi);
        }
        this.playStartTime = performance.now();
        return new Promise((resolve) => {
            this.callbacks.set(this.doneCallback, resolve);
        });
    }

    /**
     * Get a MIDI channel for the given instrument, allocating one if necessary.
     * 
     * @param instr instrument
     * @returns channel for the instrument
     */
    private getChannel(instr: Instrument): number {
        // check whether this instrument already has a channel
        let channel = this.channelForInstrument.get(instr);
        if (channel === undefined) {
            channel = this.channelForInstrument.size;
            // TODO: check whether nextChannel exceeds # of possible channels for this midi device
            if (this.playStartTime !== undefined) {
                this.midiOut.send(MIDI.program(channel, instr));
            } else {
                this.queued.add(0, MIDI.program(channel, instr));
            }
            this.unplayedEvents++;
            this.channelForInstrument.set(instr, channel);
        }
        return channel;
    }
}
