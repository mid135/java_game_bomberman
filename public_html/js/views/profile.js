define([
  'backbone',
  'tmpl/profile',
  'models/user'
], function (Backbone, tmpl, userModel) {
  var ProfileView = Backbone.View.extend({

    initialize: function() {
      this.$page = $('#page');
    },
    template: tmpl,
    render: function() {
      var profile = userModel.toJSON();
      console.log(profile);
      this.$el.html(this.template(profile));
      this.$page.html(this.$el);
    }
  });

  return new ProfileView();
});
