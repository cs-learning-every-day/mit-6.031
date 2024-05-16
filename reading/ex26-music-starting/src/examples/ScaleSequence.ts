import { Instrument } from '../Instrument';
import { Pitch } from '../Pitch';
import { SequencePlayer } from '../SequencePlayer';
import { MidiSequencePlayer } from '../MidiSequencePlayer';

/**
 * Play an octave up and back down starting from middle C.
 */
export async function main(): Promise<void> {
    const piano = Instrument.PIANO;

    // create a new player
    const beatsPerMinute = 120; // a beat is a quarter note, so this is 120 quarter notes per minute
    const ticksPerBeat = 2;
    const player: SequencePlayer = new MidiSequencePlayer(beatsPerMinute, ticksPerBeat);

    // addNote(instr, pitch, startBeat, numBeats) schedules a note with pitch value 'pitch'
    // played by 'instr' starting at 'startBeat' to be played for 'numBeats' beats.

    const numBeats = 1;
    let startBeat = 0;
    player.addNote(piano, Pitch.make('C'), startBeat++, numBeats);
    player.addNote(piano, Pitch.make('D'), startBeat++, numBeats);
    player.addNote(piano, Pitch.make('E'), startBeat++, numBeats);
    player.addNote(piano, Pitch.make('F'), startBeat++, numBeats);
    player.addNote(piano, Pitch.make('G'), startBeat++, numBeats);
    player.addNote(piano, Pitch.make('A'), startBeat++, numBeats);
    player.addNote(piano, Pitch.make('B'), startBeat++, numBeats);
    player.addNote(piano, Pitch.make('C').transpose(Pitch.OCTAVE), startBeat++, numBeats);
    player.addNote(piano, Pitch.make('B'), startBeat++, numBeats);
    player.addNote(piano, Pitch.make('A'), startBeat++, numBeats);
    player.addNote(piano, Pitch.make('G'), startBeat++, numBeats);
    player.addNote(piano, Pitch.make('F'), startBeat++, numBeats);
    player.addNote(piano, Pitch.make('E'), startBeat++, numBeats);
    player.addNote(piano, Pitch.make('D'), startBeat++, numBeats);
    player.addNote(piano, Pitch.make('C'), startBeat++, numBeats);

    // play!
    console.log('playing now...');
    await player.start();
    console.log('playing done');
}

if (require.main === module) {
    main();
}
