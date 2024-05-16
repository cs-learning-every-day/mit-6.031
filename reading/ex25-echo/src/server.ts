import express, { Request, Response } from 'express';
import { timeout } from './timeout';
import HttpStatus from 'http-status-codes';
import asyncHandler from 'express-async-handler';

const app = express();

const PORT = 8000;
app.listen(PORT);
console.log('now listening at http://localhost:' + PORT);

// GET /echo?greeting=<string>
//
// response is a greeting including <string>
app.get('/echo', (request: Request, response: Response) => {
    const greeting = request.query['greeting'];
    response
        .status(HttpStatus.OK)
        .type('text')
        .send(greeting + ' to you too!');
});

// GET /wait
//
// waits for 5 seconds before finishing response
app.get('/wait', async (request: Request, response: Response) => {
    await timeout(5000);
    response
        .status(HttpStatus.OK)
        .type('text')
        .send('done');
});

// GET /bad
//
// always produces an error output
app.get('/bad', (request: Request, response: Response) => {
    throw new Error('oof');
});

// GET /wait-bad
//
// waits 1 second, then produces an error output
app.get('/wait-bad', asyncHandler(async (request: Request, response: Response) => {
    await timeout(1000);
    throw new Error('oof');
}));

