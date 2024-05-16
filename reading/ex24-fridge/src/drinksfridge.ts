import assert from 'assert';
import { MessagePort } from 'worker_threads';

/** Record type for DrinksFridge's reply messages. */
export type FridgeResult = {
    drinksTakenOrAdded: number,
    drinksLeftInFridge: number 
};

/**
 * A mutable type representing a refrigerator containing drinks.
 */
export class DrinksFridge {

    private drinksInFridge: number = 0;

    // Abstraction function:
    //   AF(drinksInFridge, port) = a refrigerator containing `drinksInFridge` drinks
    //                              that takes requests from and sends replies to `port`
    // Rep invariant:
    //   drinksInFridge >= 0

    /**
     * Make a DrinksFridge that will listen for requests and generate replies.
     * 
     * @param port port to receive requests from and send replies to
     */
    public constructor(
        private readonly port: MessagePort
    ) {
        this.checkRep();
    }

    private checkRep(): void {
        assert(this.drinksInFridge >= 0);
    }

    /** Start handling drink requests. */
    public start(): void {
        this.port.addListener('message', (n: number) => {
            const reply: FridgeResult = this.handleDrinkRequest(n);
            this.port.postMessage(reply);
        });
    }

    private handleDrinkRequest(n: number): FridgeResult {
        const change = Math.min(n, this.drinksInFridge);
        this.drinksInFridge -= change;
        this.checkRep();
        return { drinksTakenOrAdded: change, drinksLeftInFridge: this.drinksInFridge };
    }
}
