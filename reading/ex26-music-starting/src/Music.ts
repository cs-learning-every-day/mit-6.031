import { SequencePlayer } from './SequencePlayer';

/**
 * Music represents a piece of music played by multiple instruments.
 */
export interface Music {

    /**
     * Total duration of this piece in beats.
     */
    duration: number;

    /**
     * Play this piece.
     * 
     * @param player player to play on
     * @param atBeat when to play
     */
    play(player: SequencePlayer, atBeat:number): void;

    /**
     * @param that Music to compare `this` with
     * @returns true iff this and that represent the same Music expression.
     *    Must be an equivalence relation (reflexive, symmetric, and transitive).
     */
    equalValue(that: Music): boolean;
}
