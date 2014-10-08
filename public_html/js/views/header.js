define([
  'jquery',
  'backbone',
  'tmpl/header',
  'models/user'
], function ($, Backbone, tmpl, userModel) {

  var HeaderView = Backbone.View.extend({
    model: userModel,
    className: 'header',
    template: function () {
      return tmpl(this.model.toJSON());
    },
    initialize: function () {
      this.$container = $('#header');
      this.listenTo(this.model, 'change', this.render);
    },
    events: {
      'click .js-logout': 'logout'
    },
    render: function () {
      this.$el.html(this.template());
      return this;
    },
    logout: function () {
      userModel.logout();
    }
  });

  return new HeaderView();

});
