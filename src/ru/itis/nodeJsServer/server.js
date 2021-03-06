const express = require('express');
const app = express();
const bodyParser = require('body-parser');
const urlencodedParser = bodyParser.urlencoded({ extended: true });
app.use(urlencodedParser)
require('./app/routes')(app);
app.use(express.static('public'));
app.use(express.static('img'));
app.use(express.static('style'));
app.listen(80);
console.log("Server started at 80");