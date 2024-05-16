/**
 * Standard MIDI Files: read / write / play.
 */
declare module 'jzz-midi-smf' {
    
    type JZZ = typeof import('jzz');
    type MIDI = ReturnType<JZZ['MIDI']>;
    type Port = ReturnType<ReturnType<JZZ>['openMidiIn']>;
    
    /** Standard MIDI File. */
    interface SMF extends Array<MTrk> {
        /** Create a new MIDI file of requested type with PPQN timing. */
        new (type?: 0|1|2, ppqn?: number): SMF;
        MTrk: MTrk;
        /** Create the player. */
        player(): Player;
    }
    
    /** MIDI Track. */
    interface MTrk extends Array<MIDI & { tt: number }> {
        /** Create a new MIDI track. */
        new (): MTrk;
        /** Add a MIDI event to this track at the given time in MIDI ticks. */
        add(time: number, midi: MIDI): void;
    }
    
    /** MIDI Player. */
    interface Player extends Port {
        /** Start playback. */
        play(): void;
        /** @returns current MIDI file position in MIDI ticks. */
        position(): number;
        /** User-assigned callback, called when the end of the MIDI file is reached. */
        onEnd?: () => void;
    }
    
    /** JZZ with SMF. */
    type JZZ_SMF = { MIDI: MIDI & { SMF: SMF } };
    /** Modify JZZ to add the SMF constructor to JZZ.MIDI. */
    export default function factory(JZZ: JZZ): asserts JZZ is JZZ & JZZ_SMF;
}
