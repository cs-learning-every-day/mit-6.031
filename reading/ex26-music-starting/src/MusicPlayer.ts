import { SequencePlayer } from './SequencePlayer';
import { MidiSequencePlayer } from './MidiSequencePlayer';
import { Music } from './Music';

/**
 * Play music with the MIDI synthesizer.
 * 
 * @param music music to play
 * @returns (a promise that) resolves after the music has played
 * @throws Error if MIDI device unavailable or MIDI play fails
 */
export function playOnMidiPlayer(music: Music): Promise<void> {
    const player: SequencePlayer = new MidiSequencePlayer();
    
    // load the player with a sequence created from music (add a small delay at the beginning)
    const warmup = 0.125;
    music.play(player, warmup);
    
    // start playing
    return player.start();
}
