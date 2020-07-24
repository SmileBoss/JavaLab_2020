bodyParser = require('body-parser');


module.exports = function (app) {
    app.get('/index', (request, response) => {
        var result = [{
            "id": 1,
            "name": "Nikita Shirmanov",
            "position": "Back-end developer",
            "text": "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.",
            "img": "http://localhost/me.jpg",
            "email": "billie_jean@gmail.com",
            "tel": "+00013370000"
        },
        ];
        response.setHeader("Content-Type", "application/json");
        response.send(JSON.stringify(result));
    });

    const pg = require('pg')
    const conString = 'pg://postgres:**********@localhost:5432/homework_1';


    app.get('/users', function (req, res, next) {
        pg.connect(conString, function (err, client, done) {
            if (err) {

                return next(err);
            }
            client.query('SELECT * FROM student;', [], function (err, result) {
                done()
                if (err) {

                    return next(err);
                }
                res.json(result.rows);
            });
        });
    });


    app.post('/users', function (req, res, next) {
        const user = req.body
        pg.connect(conString, function (err, client, done) {
            if (err) {

                return next(err)
            }
            client.query('INSERT INTO student (first_name, last_name, age, group_number) VALUES ($1, $2, $3, $4);', [user.postName, user.postLastname, user.postAge, user.postGroupNumber], function (err, result) {
                done()
                if (err) {

                    return next(err)
                }
                res.send(200);

            })
        })
    })

};
