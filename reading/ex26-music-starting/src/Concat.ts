import assert from 'assert';
import { Music } from './Music';
import { SequencePlayer } from './SequencePlayer';

/**
 * Concat represents two pieces of music played one after the other.
 */
export class Concat implements Music {

    /**
     * Make a Music sequence that plays first followed by second.
     * 
     * @param first music to play first
     * @param second music to play second
     */
    public constructor(
        public readonly first: Music,
        public readonly second: Music
    ) {
        this.checkRep();
    }

    private checkRep(): void {
    }

    /**
     * @returns duration of this concatenation
     */
    public get duration(): number {
        return this.first.duration + this.second.duration;
    }

    /**
     * Play this concatenation.
     */
    public play(player: SequencePlayer, atBeat: number): void {
        this.first.play(player, atBeat);
        this.second.play(player, atBeat + this.first.duration);
    }

    /**
     * @inheritdoc
     */
    public equalValue(that: Music): boolean {
        return (that instanceof Concat)
                && this.first.equalValue(that.first)
                && this.second.equalValue(that.second);
    }

    public toString(): string {
        return this.first + " " + this.second;
    }
}
