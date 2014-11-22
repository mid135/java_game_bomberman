define([
	'backbone'
	],
	function(
		Backbone
	)
{
    var userModel = Backbone.Model.extend({
        defaults: {
            name: '',
            password: '',
            email: ''
        },
    login: function(agrs) {
        var that=this;
        $.post("/authform",args,function(data) {
            //console.log("1");
            console.log(data);
            if (data.status == "success") {
                that.set({
                    'login': data.login,
                    'email': data.email,
                })
                that.trigger("login_ok");
            } else {
                that.trigger("login_bad");
            }
        });
    },
    isLoggedIn: function(){
            return true;
    }
    });
    return userModel;
});