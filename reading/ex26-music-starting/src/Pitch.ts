import assert from 'assert';

/**
 * Pitch is an immutable type representing the frequency of a musical note.
 * Standard music notation represents pitches by letters: A, B, C, ..., G.
 * Pitches can be sharp or flat, or whole octaves up or down from these
 * primitive generators.
 * 
 * <p> For example:
 * <br> new Pitch('C') makes middle C
 * <br> new Pitch('C').transpose(1) makes C-sharp
 * <br> new Pitch('E').transpose(-1) makes E-flat
 * <br> new Pitch('C').transpose(OCTAVE) makes high C
 * <br> new Pitch('C').transpose(-OCTAVE) makes low C
 */
 export class Pitch {

    private constructor(
        private readonly value: number
    ) {
        this.checkRep();
    }

    /*
     * Rep invariant: value is an integer.
     *
     * Abstraction function AF(value):
     *   AF(0),...,AF(11) map to middle C, C-sharp, D, ..., A, A-sharp, B.
     *   AF(i+12n) maps to n octaves above middle AF(i)
     *   AF(i-12n) maps to n octaves below middle AF(i)
     *
     * Safety from rep exposure:
     *   all fields are private and immutable.
     */

    private checkRep(): void {
        assert(this.value === Math.floor(this.value));
    }

    private static readonly SCALE: {[letter:string]: number} = {
            'A': 9,
            'B': 11,
            'C': 0,
            'D': 2,
            'E': 4,
            'F': 5,
            'G': 7,
    };

    private static readonly VALUE_TO_STRING: Array<string> = [
            "C", "^C", "D", "^D", "E", "F", "^F", "G", "^G", "A", "^A", "B"
    ];
    
    /**
     * Middle C.
     */
    public static readonly MIDDLE_C: Pitch = Pitch.make('C');

    /**
     * Number of pitches in an octave.
     */
    public static readonly OCTAVE: number = 12;

    /**
     * Make a Pitch named `letter` in the middle octave of the piano keyboard.
     * For example, new Pitch('C') constructs middle C.
     * 
     * @param letter must be in {'A',...,'G'}
     * @returns pitch of the given note in the middle octave
     */
    public static make(letter: string): Pitch {
        const value = Pitch.SCALE[letter];
        if (value === undefined) { throw new Error(letter + " must be in the range A-G"); }
        return new Pitch(value);
    }

    /**
     * @param semitonesUp must be integer
     * @returns pitch made by transposing this pitch by semitonesUp semitones;
     *          for example, middle C transposed by 12 semitones is high C, and
     *          E transposed by -1 semitones is E flat
     */
    public transpose(semitonesUp: number): Pitch {
        if (Math.floor(semitonesUp) !== semitonesUp) {
            throw new Error(semitonesUp + ' must be integer');
        }
        return new Pitch(this.value + semitonesUp);
    }

    /**
     * @param that Pitch to compare `this` with
     * @returns number of semitones between this and that; i.e., n such that
     *          that.transpose(n).equalValue(this)
     */
    public difference(that: Pitch): number {
        return this.value - that.value;
    }

    /**
     * @param that Pitch to compare `this` with
     * @returns true iff this and that represent the same pitch, must be an
     *          equivalence relation (reflexive, symmetric, and transitive)
     */
    public equalValue(that: Pitch): boolean {
        return this.value === that.value;
    }

    /**
     * @returns this pitch in abc music notation
     * @see "http://abcnotation.com/examples/"
     */
    public toString(): string {
        let suffix = "";
        let v = this.value;

        while (v < 0) {
            suffix += ",";
            v += Pitch.OCTAVE;
        }

        while (v >= Pitch.OCTAVE) {
            suffix += "'";
            v -= Pitch.OCTAVE;
        }

        return Pitch.VALUE_TO_STRING[v] + suffix;
    }
}
