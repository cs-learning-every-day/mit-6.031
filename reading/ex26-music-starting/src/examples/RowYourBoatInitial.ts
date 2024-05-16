import { Music } from '../Music';
import { notes } from '../MusicLanguage';
import { Instrument } from '../Instrument';
import { playOnMidiPlayer } from '../MusicPlayer';

/**
 * Play a song.
 */
export async function main(): Promise<void> {

    const rowYourBoat: Music =
        notes("C C C3/4 D/4 E |"       // Row, row, row your boat,
            + "E3/4 D/4 E3/4 F/4 G2 |" // Gently down the stream.
            + "C'/3 C'/3 C'/3 G/3 G/3 G/3 E/3 E/3 E/3 C/3 C/3 C/3 |"
                                       // Merrily, merrily, merrily, merrily,
            + "G3/4 F/4 E3/4 D/4 C2",  // Life is but a dream.
            Instrument.PIANO);


    console.log(rowYourBoat.toString());

    // play!
    console.log('playing now...');
    await playOnMidiPlayer(rowYourBoat);
    console.log('playing done');
    
}

if (require.main === module) {
    main();
}
