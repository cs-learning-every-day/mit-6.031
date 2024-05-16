import assert from 'assert';
import { Worker, isMainThread, parentPort } from 'worker_threads';

import { DrinksFridge, FridgeResult } from './drinksfridge';

//
// Create and use a drinks fridge.
//

/** Runs on the main thread. */
function main() {
    const worker = new Worker('./dist/loadfridge.js');
    worker.addListener('message', (result: FridgeResult) => {
      console.log('result from worker', result);
    });
    worker.postMessage(-42);
    worker.postMessage(2);
    
    // abruptly end the program, including the worker, after a short time
    setTimeout(() => process.exit(0), 1000);
}

/** Runs in a worker. */
function worker() {
    assert(parentPort);
    new DrinksFridge(parentPort).start();
}

if (isMainThread) {
    main();
} else {
    worker();
}
