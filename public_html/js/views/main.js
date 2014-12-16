define([
    'backbone',
    'tmpl/main',
    'models/session'
], function(
    Backbone,
    tmpl,
    sessionModel
){

    var View = Backbone.View.extend({
        el: $('.main'),
        template: tmpl,
        session: sessionModel,
        events: {
            "click .logoffButton": "logoff"
        },
        initialize: function () {
            this.listenTo(this.session, 'main:anonymous', this.userNotIdentified);
            this.listenTo(this.session, 'main:known', this.userIdentified);
            this.listenTo(this.session, 'successLogout', this.logoutSuccess);
            this.listenTo(this.session, 'errorLogout', this.logoutError);
            this.listenTo(this.session, 'successAuth', this.userIdentified);
            this.listenTo(this.session, 'change', this.render);
            this.render();
            this.$el.hide();
        },

        render: function () {
            this.$el.html(this.template(this.session.toJSON()));
        },
        hideLogin: function() {
            $(".loginButton").hide();
        },
        show: function () {
            this.session.postLogin('main');
            this.trigger("reshow",this);
        },

        logoff: function (event) {

            event.preventDefault();
            this.session.postLogout();
        },
        
        userIdentified: function(data) {
            this.$el.find(".loginButton, .registerButton").hide();
            this.$el.find(".to_profile, .exit, .gameButton, .scoreboard").show();
            this.$el.find(".form__header__myName").html(this.session.login);
            this.trigger('reshow', this);

        },
        userNotIdentified: function() {
            this.$el.find(".to_profile, .exit , .logoffButton").hide();
            this.$el.find(".gameButton, .scoreboardButton").hide();
            this.$el.find(".registerButton, .loginButton").show();
            this.$el.find(".form__header__myName").html("anon");
            this.trigger('reshow', this);
        },

        logoutSuccess: function (data) {
            this.trigger('successLogoff');
            this.show();
        },

        logoutError: function (message) {
            var elem = this.$(".alert-error").slideDown().delay(3000).slideUp();
            elem.html("<p>" + message + "</p>");
        }
    });

    return new View();
});