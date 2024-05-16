import assert from 'assert';
import { Music } from './Music';
import { SequencePlayer } from './SequencePlayer';
import { Pitch } from './Pitch';
import { Instrument } from './Instrument';

/**
 * Note represents a note played by an instrument.
 */
export class Note implements Music {

    /**
     * Make a Note played by instrument for duration beats.
     * 
     * @param duration duration in beats, must be >= 0
     * @param pitch pitch to play
     * @param instrument instrument to use
     */
    public constructor(
        public readonly duration: number,
        public readonly pitch: Pitch,
        public readonly instrument: Instrument
    ) {
        this.checkRep();
    }

    private checkRep(): void {
        assert(this.duration >= 0);
    }

    /**
     * Play this note.
     */
    public play(player: SequencePlayer, atBeat: number): void {
        player.addNote(this.instrument, this.pitch, atBeat, this.duration);
    }

    /**
     * @inheritdoc
     */
    public equalValue(that: Music): boolean {
        return (that instanceof Note) 
                && this.duration === that.duration
                && this.instrument === that.instrument
                && this.pitch.equalValue(that.pitch);
    }

    public toString(): string {
        return this.pitch.toString() + this.duration;
    }
}
