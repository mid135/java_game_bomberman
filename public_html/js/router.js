define([
    'backbone',
    'views/main',
    'views/game',
    'views/login',
    'views/scoreboard'
    ],
    function(
        Backbone,
        main,
        game,
        login,
        scoreboard
    )
{
    var Router = Backbone.Router.extend({
        routes: {
            'scoreboard': 'scoreboardAction',
            'game': 'gameAction',
            'login': 'loginAction',
            '*default': 'defaultActions'
        },
        defaultActions: function () {
            $('#page').html(main.$el);
        },
        scoreboardAction: function () {
            $('#page').html(scoreboard.$el);
        },
        gameAction: function () {
            $('#page').html(game.$el);
        },
        loginAction: function () {
            $('#page').html(login.$el);
        }
    });

    return new Router();
});