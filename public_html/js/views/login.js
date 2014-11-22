define([
    'jquery',
    'backbone',
    'tmpl/login',
    'tmpl/register',
    'models/user',

    ],
    function(
        $,
        Backbone,
        tmplLogin,
        tmplRegister,
        userModel
    )
{
    var View = Backbone.View.extend({
        user: userModel,
        template: tmplLogin,
        templateLogin: tmplLogin,
        templateRegister: tmplRegister,

        events: {
            "click .login": "login"
        },

        initialize: function () {
            //this.listenTo(this.user,"login_ok", this.login);
            this.render();
        },

        render: function () {
            this.$el.html(this.template);
            return this;
        },

        show: function () {
            this.trigger('show',this);
            this.$el.show();
        },

        hide: function () {
            this.$el.hide();
        },

        login: function () {
            //alert("sdg");
            var args = {login:$("#login").val(),password:$("#password").val()};
            userModel.login(args);

        }

    });

    return new View();
});