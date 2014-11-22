define([
    'backbone',
    'views/viewContainer',
    'views/main',
    'views/game',
    'views/login',
    'views/scoreboard',
    'models/user'
    ],
    function(
        Backbone,
        container,
        mainView,
        gameView,
        loginView,
        scoreboardView,
        loginModel
    )
{
    container.register({
                'main': mainView,
                'login':loginView,
                'scoreboard': scoreboardView,
                'game': gameView
            });
    container.render();
    var Router = Backbone.Router.extend({


        initialize:function() {

        },
        routes: {
            'scoreboard': 'scoreboard',
            'game': 'game',
            'login': 'login',
            '*other': 'default'
        },
        default: function () {
            container.getView('main').show();
        },
        scoreboard: function () {
            //$('#page').html(scoreboard.$el);
        },
        game: function () {
           container.getView('game').show();
        },
        login: function () {
            container.getView('login').show();

        }
    });

    return new Router();
});