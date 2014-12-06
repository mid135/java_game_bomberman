define([
    'backbone'
], function(
    Backbone
){
    var SessionModel = Backbone.Model.extend({

        sendPost: function (url, data, eventSuccess, eventFail) {
            var self = this;
            $.ajax({
                url: url,
                method: "POST",
                data: data,
                dataType: "json"
            }).done(function(data) {
                if (data.status == 1) {
                    self.trigger(eventSuccess, data);
                } else {
                    self.trigger(eventFail, data.message);
                }
            }).fail(function(data) {
                self.trigger(eventFail, "Connection error, please try again later");
            });
        },

        postAuth: function(url, data) {
            this.sendPost(url, data, 'successAuth', 'errorAuth');
        },

        postReg: function(url, data) {
            this.sendPost(url, data, 'successReg', 'errorReg');
        },

        postChangePassword: function(url, data) {
            this.sendPost(url, data, 'successChangePassword', 'errorChangePassword');
        },

        postLogin: function(prefix) {
            this.sendPost("/login", {}, prefix + ":known", prefix + ":anonymous");
        },

        postLogout: function() {
            this.sendPost("/logout", {}, 'successLogout', 'errorLogout');
        },

        isLoggedIn: function() {
            return true;
        }

    });

    return new SessionModel();
});