import assert from 'assert';
import { Deferred } from './Deferred';

class User {
    constructor(public readonly name: string) {}
}

class Book {
    constructor(public readonly title: string) {}
}

// Library represents a mutable collection of books that can be borrowed by users.
// At any time, there is a set of books physically present at the library,
// and for each user, a set of books borrowed by that user.
// These sets are all disjoint because a book can't be in two places at once.
interface Library {

    /**
     * Check out every book in `books` so that `user` is now borrowing it.
     * If any of them is not in the library, waits until it is checked in,
     * fulfilling the promise only when all the books have been successfully borrowed by `user`.
     */
     checkout(books: Array<Book>, user: User): Promise<void>;

    /**
     * Check in every book in `books` so that it is physically in the library.
     * If the book was previously borrowed by a user, it is now back in the library.
     */
     checkin(books: Array<Book>): void;
 
}


class MITLibrary implements Library {

    private inLibrary: Set<Book> = new Set();
    private borrowed: Map<User, Set<Book>> = new Map();
    private holds: Map<Book, Array<Deferred<void>>> = new Map();

    // AF(inLibrary, borrowed, holds) = the library such that 
    //    the books physically present is the set `inLibrary`,
    //    and the books borrowed by user u are borrowed.get(u)

    // RI(inLibrary, borrowed, holds) = the sets of books of `inLibrary` and `borrowed` are all disjoint

    // Safety from rep exposure: all rep fields are private, and the mutable types in the rep (Set and Map)
    //     are not taken or returned by any of the methods (which only use Array, Book, and User)

    private checkRep(): void {
        // assert that every book in the rep appears exactly once
        const allBooks: Set<Book> = new Set(this.inLibrary);
        this.borrowed.forEach((borrowedBooks:Set<Book>) => {
            borrowedBooks.forEach((book: Book)=> {
                assert( ! allBooks.has(book) );
                allBooks.add(book);
            });
        });
    }

    public constructor() {
        this.checkRep();
    }

    /** @inheritdoc */
    public async checkout(books: Array<Book>, user: User): Promise<void> {
        const isInLibrary = (book: Book) => this.inLibrary.has(book);
        const notInLibrary = (book: Book) => ! isInLibrary(book);
        const waitForBook = (book: Book) => { // requires notInLibrary(book)
            const hold = new Deferred<void>();
            this.holdsForBook(book).push(hold);
            return hold.promise;
        };

        // wait for all the books to be returned
        while ( ! books.every(isInLibrary) ) {
            await Promise.all(books.filter(notInLibrary).map(waitForBook));
        }

        // borrow the books
        assert(books.every(isInLibrary));
        for (const book of books) {
            assert(isInLibrary(book));
            this.inLibrary.delete(book);
            this.borrowedByUser(user).add(book);
        }

        this.checkRep();
    }

    /** @inheritdoc */
    public checkin(books: Array<Book>): void {
        for (const book of books) {
            // book is no longer borrowed, so remove it from all borrowed sets
            for (const borrowedBooks of this.borrowed.values()) {
                borrowedBooks.delete(book); // at most one of these sets has the book
            }

            // book is back in the library
            this.inLibrary.add(book);

            // notify the first person on the hold list, if any
            const holds = this.holds.get(book);
            if (holds !== undefined) {
                const deferred = holds.shift();
                if (deferred !== undefined) {
                    deferred.resolve();
                }
            }
        }

        this.checkRep();
    }

    /**
     * Helper method that automatically creates a borrow set 
     * for users we haven't seen before.
     * @param user
     * @returns mutable set of books borrowed by user
     */
     private borrowedByUser(user: User): Set<Book> {
        let books = this.borrowed.get(user);
        if (books === undefined) {
            books = new Set();
            this.borrowed.set(user, books);
        }
        return books;
    }

    /**
     * Helper method that automatically creates a hold list for a book
     * if it doesn't already exist.
     * @param book
     * @returns mutable hold list for this book
     */
     private holdsForBook(book: Book): Array<Deferred<void>> {
        let holds = this.holds.get(book);
        if (holds === undefined) {
            holds = [];
            this.holds.set(book, holds);
        }
        return holds;
    }
}

const fellowship = new Book('Fellowship of the Ring');
const twoTowers = new Book('Two Towers');
const returnOfKing = new Book('Return of the King');

const frodo = new User('Frodo Baggins');
const bilbo = new User('Bilbo Baggins');
const gandalf = new User('Gandalf the Grey');

async function main() {

    const library = new MITLibrary();

    // put some books in the library
    library.checkin([fellowship, twoTowers, returnOfKing]);

    // Frodo takes all the books
    library.checkout([fellowship, twoTowers, returnOfKing], frodo);

    // Frodo returns one
    library.checkin([fellowship]);

    // Bilbo takes that one
    library.checkout([fellowship], bilbo);
}

main();
