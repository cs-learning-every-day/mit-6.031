import { Music } from '../Music';
import { notes } from '../MusicLanguage';
import { Instrument } from '../Instrument';
import { playOnMidiPlayer } from '../MusicPlayer';

/**
 * Play an octave up and back down starting from middle C.
 */
export async function main(): Promise<void> {

    // parse simplified abc into a Music
    const scale: Music = notes("C D E F G A B C' B A G F E D C", Instrument.PIANO);

    console.log(scale.toString());

    // play!
    console.log('playing now...');
    await playOnMidiPlayer(scale);
    console.log('playing done');
    
}

if (require.main === module) {
    main();
}
