
/**
 * @param milliseconds duration to wait
 * @returns a promise that fulfills no less than `milliseconds` after timeout() was called
 */
export async function timeout(milliseconds:number):Promise<void> {
    return new Promise((resolve) => setTimeout(resolve, milliseconds));
}
