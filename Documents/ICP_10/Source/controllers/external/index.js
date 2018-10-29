var request = require('request');
module.exports = function(app) {
    var query='https://language.googleapis.com/v1beta2/documents:analyzeSyntax?key=AIzaSyBfeurGiyPEG3Zz1a6jaz5zNMpWCpNDUSE';
    var url = 'https://api.wunderground.com/api/36b799dc821d5836/hourly/q/MO/kansas%20city.json';
    app.post('/Hiresh/nlp', function(req, res) {
        res.contentType("application/json");
       request.post({
               url: query,
               method: 'POST',
               json: {

                   "document" :  {
                       "type":"PLAIN_TEXT",
                       "content" : req.query.Text
                   }
                   ,
                   "encodingType" : "None"
               },
           headers: {
               'Content-Type' : 'application/json',
               'Access-Control-Allow-Origin':'http://localhost:63342',
               'Access-Control-Allow-Methods':'POST',
               'Access-Control-Allow-Headers':'Content-Type',
           }
           }
       ).pipe(res);
    });
}