define([
    'backbone',
    'user_sync'
], function(
    Backbone,api
){
    var SessionModel = Backbone.Model.extend({
        initialize: function() {
            this.fetch();
        },
        sync:api,
        login: "",
        defaults: function() {
            this.set({
                login: "lel",
                email: "",
                gameCount: 0,
                gameLose: 0,
                gameWin: 0,
                'isLoggedIn': false
            });
        },
        getLogin: function() {
            return this.login;
        },
        events: {
            'click .logoffButton': 'postLogout'
        },
        sendPost: function (url, data, eventSuccess, eventFail) {
            var self = this;
            $.ajax({
                url: url,
                method: "POST",
                data: data,
                dataType: "json"
            }).done(function(data) {

                if (+data.status === 1) {
                    self.setUser(data);
                    self.trigger(eventSuccess, data);
                }
                else {
                    self.trigger(eventFail, data.message);
                }
            }).fail(function(data) {
                self.isLoggedIn="false";
                self.trigger(eventFail, "Connection error, please try again later");
            });
        },

        userAuthorised: function() {
        debugger;
            this.trigger('successAuth');
        },
        postAuth: function(url, data) {
            this.sendPost(url, data, 'successAuth', 'errorAuth');
        },

        postReg: function(url, data) {
            this.sendPost("/register", data, 'successReg', 'errorReg');
        },

        postChangePassword: function(url, data) {
            this.sendPost(url, data, 'successChangePassword', 'errorChangePassword');
        },

        postLogin: function(prefix) {
            this.sendPost("/auth", {}, prefix + ":known", prefix + ":anonymous");

        },

        postLogout: function() {
            this.sendPost("/logoff", {}, 'successLogout', 'errorLogout');
        },

        isLoggedIn: function() {
            return isLoggedIn;
        },
        setUser: function (data) {
            this.set('login',data.user);
            this.set('isLoggedIn', true);
        }

    });

    return new SessionModel();
});