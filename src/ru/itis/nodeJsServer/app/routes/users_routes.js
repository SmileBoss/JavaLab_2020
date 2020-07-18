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
        response.send(JSON.stringify(result));
    });
};
