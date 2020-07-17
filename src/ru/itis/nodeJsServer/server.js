const express = require('express');
const app = express();
app.use(express.static('public'));
app.use(express.static('img'));
app.use(express.static('style'));
app.listen(80);
console.log("Server started at 80");