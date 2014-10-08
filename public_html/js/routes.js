define([
  'jquery',
  'backbone',
  'views/home',
  'views/game',
  'views/login',
  'views/signup',
  'views/profile',
  'views/scoreboard',
  'views/app',
  'models/user'
], function($, Backbone, homeView, gameView, loginView, signupView, profileView, scoreboardView, manager, userModel) {

  var Router = Backbone.Router.extend({
    initialize: function () {
        this.listenTo(userModel, 'logined', this.toGame);
        this.listenTo(userModel, 'registred', this.toLogin);
        this.listenTo(userModel, 'notlogined', this.toLogin);
        this.listenTo(userModel, 'logout', this.toMain);
    },
    routes: {
      '': 'index',
      'game': 'game',
      'login': 'login',
      'signup': 'signup',
      'profile': 'profile',
      'scoreboard': 'scoreboard',
      '*other': 'default'
    },
    index: function() {
      manager.subscribe(homeView);
      homeView.show();
    },
    game: function() {
      manager.subscribe(gameView);
      gameView.show();
    },
    login: function() {
      manager.subscribe(loginView);
      loginView.show();
    },
    signup: function() {
      manager.subscribe(signupView);
      signupView.show();
    },
    profile: function() {
      manager.subscribe(profileView);
      profileView.show();
    },
    scoreboard: function() {
      manager.subscribe(scoreboardView);
      scoreboardView.show();
    },
    default: function () {
      alert('404');
    },
    toGame: function () {
      this.navigate('game', {trigger: true});
    },
    toLogin: function () {
      this.navigate('login', {trigger: true});
    },
    toMain: function () {
      this.navigate('', {trigger: true});
    }
  });

  return new Router();
});
