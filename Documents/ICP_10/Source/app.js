var createError = require('http-errors');
var express = require('express');
var path = require('path');
var cookieParser = require('cookie-parser');
var logger = require('morgan');

//var indexRouter = require('./routes/index');
//var usersRouter = require('./routes/users');
var app = express();
app.set( 'port', ( process.env.PORT || 3000 ));
const port = 3000;
app.listen(app.get( 'port' ), function(){
    console.log('Connection Established ! Listening at port : '+port);
});
// view engine setup
app.set('views', path.join(__dirname, 'views'));
app.set('view engine', 'ejs');

app.get('/', function (req, res) {
    res.sendFile(path.join(__dirname,'public', 'home.html'));
});
//const CoinRouter = require('./routes/CoinRouter');

//app.use('/coins', CoinRouter);
//app.use(cors);
app.use(logger('dev'));
app.use(express.json());
app.use(express.urlencoded({ extended: false }));
app.use(cookieParser());
app.use(express.static(path.join(__dirname, 'dist')));

//Rest API's
require('./controllers/UMS/index')(app);
require('./controllers/external/index')(app);
var cors = require('cors');

// catch 404 and forward to error handler
app.use(function(req, res, next) {
    res.header("Access-Control-Allow-Origin", "http://localhost:63342");
    res.header("Access-Control-Allow-Methods", "POST");
    res.header("Access-Control-Allow-Headers", "Content-Type");
    next();
});

// error handler
app.use(function(err, req, res, next) {
    // Website you wish to allow to connect
  // set locals, only providing error in development
  res.locals.message = err.message;
  res.locals.error = req.app.get('env') === 'development' ? err : {};

  // render the error page
  res.status(err.status || 500);
  res.render('error');
});

module.exports = app;
