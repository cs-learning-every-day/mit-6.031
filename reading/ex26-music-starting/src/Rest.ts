import assert from 'assert';
import { Music } from './Music';
import { SequencePlayer } from './SequencePlayer';

/**
 * Rest represents a pause in a piece of music.
 */
export class Rest implements Music {

    /**
     * Make a Rest that lasts for duration beats.
     * 
     * @param duration duration in beats, must be >= 0
     */
    public constructor(
        public readonly duration: number
    ) {
        this.checkRep();
    }

    private checkRep(): void {
        assert(this.duration >= 0);
    }

    /**
     * Play this rest.
     */
    public play(player: SequencePlayer, atBeat: number):void {
        return;
    }

    /**
     * @inheritdoc
     */
    public equalValue(that: Music): boolean {
        return (that instanceof Rest) 
                && this.duration === that.duration;
    }

    public toString(): string {
        return "." + this.duration;
    }
}
