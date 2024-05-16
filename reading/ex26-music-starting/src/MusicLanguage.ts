import assert from 'assert';

import { Pitch } from './Pitch';
import { Instrument } from './Instrument';
import { Note } from './Note';
import { Rest } from './Rest';
import { Concat } from './Concat';
import { Music } from './Music';

/**
 * MusicLanguage defines functions for constructing and manipulating Music expressions.
 */

////////////////////////////////////////////////////
// Factory functions
////////////////////////////////////////////////////

/**
 * Make Music from a string using a variant of abc notation
 *    (see http://abcnotation.com/examples/).
 * 
 * <p> The notation consists of whitespace-delimited symbols representing
 * either notes or rests. The vertical bar | may be used as a delimiter
 * for measures; notes() treats it as a space.
 * Grammar:
 * <pre>
 *     notes ::= symbol*
 *     symbol ::= . duration          // for a rest
 *              | pitch duration      // for a note
 *     pitch ::= accidental letter octave*
 *     accidental ::= empty string    // for natural,
 *                  | _               // for flat,
 *                  | ^               // for sharp
 *     letter ::= [A-G]
 *     octave ::= '                   // to raise one octave
 *              | ,                   // to lower one octave
 *     duration ::= empty string      // for 1-beat duration
 *                | /n                // for 1/n-beat duration
 *                | n                 // for n-beat duration
 *                | n/m               // for n/m-beat duration
 * </pre>
 * <p> Examples (assuming 4/4 common time, i.e. 4 beats per measure):
 *     C     quarter note, middle C
 *     A'2   half note, high A
 *     _D/2  eighth note, middle D flat
 * 
 * @param notes string of notes and rests in simplified abc notation given above
 * @param instr instrument to play the notes with
 * @returns the music in notes played by instr
 */
export function notes(notes: string, instr: Instrument): Music {
    let music: Music = rest(0);
    for (const sym of notes.split(/[\s|]+/)) {
        if (sym !== '') {
            music = concat(music, parseSymbol(sym, instr));
        }
    }
    return music;
}

/* Parse a symbol into a Note or a Rest. */
function parseSymbol(symbol: string, instr: Instrument): Music {
    const m = symbol.match(/^(?<pitch>[^\/0-9]+)(?<numerator>[0-9]+)?(?<denominator>\/[0-9]+)?$/);
    
    if ( ! (m && m.groups)) { throw new Error("couldn't understand " + symbol); }
    const { pitch, numerator, denominator } = m.groups;
    assert(pitch);

    let duration = 1.0;
    if (numerator) { duration *= parseInt(numerator); }
    if (denominator) { duration /= parseInt(denominator.substring(1)); }

    if (pitch === ".") {
        return rest(duration);
    } else {
        return note(duration, parsePitch(pitch), instr);
    }
}

/* Parse a symbol into a Pitch. */
function parsePitch(symbol: string): Pitch {
    if (symbol.endsWith("'")) {
        return parsePitch(symbol.substring(0, symbol.length-1)).transpose(Pitch.OCTAVE);
    } else if (symbol.endsWith(",")) {
        return parsePitch(symbol.substring(0, symbol.length-1)).transpose(-Pitch.OCTAVE);
    } else if (symbol.startsWith("^")) {
        return parsePitch(symbol.substring(1)).transpose(1);
    } else if (symbol.startsWith("_")) {
        return parsePitch(symbol.substring(1)).transpose(-1);
    } else if (symbol.length != 1) {
        throw new Error("can't understand " + symbol);
    } else {
        return Pitch.make(symbol.charAt(0));
    }
}

/**
 * @param duration duration in beats, must be >= 0
 * @param pitch pitch to play
 * @param instrument instrument to use
 * @returns pitch played by instrument for duration beats
 */
export function note(duration: number, pitch: Pitch, instrument: Instrument ): Music {
    return new Note(duration, pitch, instrument);
}

/**
 * @param duration duration in beats, must be >= 0
 * @returns rest that lasts for duration beats
 */
export function rest(duration: number): Music  {
    return new Rest(duration);
}

////////////////////////////////////////////////////
// Producers
////////////////////////////////////////////////////

/**
 * @param m1 first piece of music
 * @param m2 second piece of music
 * @returns m1 followed by m2
 */
export function concat(m1: Music, m2: Music): Music {
    return new Concat(m1, m2);
}
