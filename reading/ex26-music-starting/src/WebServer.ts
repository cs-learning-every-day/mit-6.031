/**
 * Web interface to the music system.
 */

import fs from 'fs';
import path from 'path';
import { Readable } from 'stream';
import Browserify from 'browserify';
import express from 'express';

const app = express();

const project = 'ex26-music-starting';
const head = `
  <head><title>${project}</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5/dist/css/bootstrap.min.css" rel="stylesheet">
  </head>
`;

app.get('/bundle/:example.js', function(req, res, next) {
  res.contentType('application/javascript');
  new Browserify()
      // JZZ loaded from external script
      .require(Readable.from('module.exports = window.JZZ'), { expose: 'jzz' })
      // JZZ-midi-SMF loaded from external script that also installs itself
      .require(Readable.from('module.exports = function factory() {}'), { expose: 'jzz-midi-smf' })
      // shim Node.js higher-precision time with regular time
      .require(Readable.from(
          'module.exports = { performance: { now: () => new Date().getTime() } }'), { expose: 'perf_hooks' })
      .add(`dist/examples/${req.params.example}.js`, { expose: 'example' })
      .exclude('jzz')
      .exclude('jzz-midi-smf')
      .bundle().pipe(res, { end: true });
});

app.get('/:example', function(req, res, next) {
  const example = req.params.example;
  res.contentType('text/html');
  res.end(`
    <html>${head}<body class="container m-5"><h1>${example}</h1>
    <button id="main">run main</button></body>
    <script src="https://cdn.jsdelivr.net/npm/jzz"></script>
    <script src="https://cdn.jsdelivr.net/npm/jzz-midi-smf"></script>
    <script src="https://cdn.jsdelivr.net/npm/jzz-synth-tiny"></script>
    <script src="/bundle/${example}.js"></script>
    <script>document.getElementById('main').addEventListener('click', async () => {
      await JZZ.synth.Tiny.register('Synth'); // use Web Audio synthesizer
      require('example').main();
    });
    </script>
    </html>
  `);
});

app.get('/', async function(req, res, next) {
  res.contentType('text/html');
  res.write(`<html>${head}<body class="container m-5"><h1>${project} examples</h1>`);
  try {
    const ext = '.js';
    const files = await fs.promises.readdir(path.join(__dirname, 'examples'));
    for (const file of files.filter(file => file.endsWith(ext))) {
      const name = path.basename(file, ext);
      res.write(`<p><a href="/${name}">${name}</a></p>`);
    }
  } catch (err) {
    res.write(`<p>${err}</p>`);
  }
  res.end('</ul></body></html>');
});

const port = 8080;
app.listen(port, function() {
  console.log(`${project} web server listening on http://localhost:${port}`);
});
