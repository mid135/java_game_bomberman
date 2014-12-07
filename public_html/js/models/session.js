define([
    'backbone'
], function(
    Backbone
){
    var SessionModel = Backbone.Model.extend({
        isLoggedIn: "true",
        sendPost: function (url, data, eventSuccess, eventFail) {
            var self = this;
            $.ajax({
                url: url,
                method: "POST",
                data: data,
                dataType: "json"
            }).done(function(data) {
                if (data.status == 1) {
                this.isLoggedIn="true";
                    self.trigger(eventSuccess, data);
                } else {
                    this.isLoggedIn="false";
                    self.trigger(eventFail, data.message);
                }
            }).fail(function(data) {
                this.isLoggedIn="false";
                self.trigger(eventFail, "Connection error, please try again later");
            });
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
            this.sendPost("/auth", {}, 'successLogout', 'errorLogout');
        },

        isLoggedIn: function() {
            return isLoggedIn;
        }

    });

    return new SessionModel();
});