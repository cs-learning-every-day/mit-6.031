import assert from 'assert';
import { Instrument } from './Instrument';
import { Pitch } from './Pitch';

/**
 * Schedules and plays a sequence of notes at given times.
 */
export interface SequencePlayer {

    /**
     * Schedule a note to be played starting at startBeat for the duration numBeats.
     * 
     * @param instr instrument for the note
     * @param pitch pitch value of the note
     * @param startBeat the starting beat (while playing, must be now or in the future)
     * @param numBeats the number of beats the note is played
     */
    addNote(instr: Instrument, pitch: Pitch, startBeat: number, numBeats: number): void;

    /**
     * Play the scheduled music.
     * 
     * @returns (a promise that) resolves after the music has played
     */
    start(): Promise<void>;

}
