define([
    'backbone',
    'views/main',
    'views/game',
    'views/login',
    'views/scoreboard',
    'views/register',
    'views/profile',
    'views/viewman',
    'views/canvas'
], function(
    Backbone,
    main,
    game,
    login,
    scoreboard,
    register,
    profile,
    viewman,
    canvas
){
    viewman.subscribe([main, game, login, scoreboard, register, profile, canvas])

    var Router = Backbone.Router.extend({
        initialize: function () {
            this.listenTo(login, 'success', this.toIndex);
            this.listenTo(main, 'success', this.toIndex);
            this.listenTo(profile, 'anonymous', this.toLogin);
            this.listenTo(register, 'success', this.toLogin);
        },

        routes: {
            '': 'index',
            'logoff': 'logoffAction',
            'scoreboard': 'scoreboardAction',
            'game': 'gameAction',
            'login': 'loginAction',
            'register': 'registerAction',
            'profile': 'profileAction',
            'canvas': 'canvasAction',
            '*default': 'defaultActions'
        },

        index: function () {
            main.show();
        },
        defaultActions: function () {
            alert('404');
        },
        scoreboardAction: function () {

            scoreboard.show();
        },
        gameAction: function () {
            game.show();
        },
        loginAction: function () {
            login.show();
        },
        logoffAction: function() {
            this.navigate('', {trigger: true});
            //this.trigger('logoff');
            main.show();
        },
        registerAction: function () {
            register.show();
        },
        profileAction: function () {
            profile.show();
        },
        canvasAction: function () {
            canvas.show();
        },
        toIndex: function () {
            this.navigate('', {trigger: true});
        },
        toLogin: function () {
            this.navigate('login', {trigger: true});
        }
    });

    return new Router();
});