define([
  'jquery',
  'backbone',
  'tmpl/app',
  'views/header',
  'models/user'
], function ($, Backbone, tmpl, headerView, userModel) {

  var AppView = Backbone.View.extend({
      tagName: 'div',
      className: 'app',
      model: userModel,
      initialize: function() {
        this.$container = $('body');
        this.render();
      },
      template: tmpl,
      render: function () {
        this.$el.html(this.template());
        this.$container.html(this.$el);
        this.$el.find('#header').html(headerView.render().$el);
      },
      subscribe: function (view) {
        this.listenTo(view, 'show', this.add);
      },
      unsubscribe: function (view) {
        this.stopListening(view);
      },
      add: function (view) {
        this.$el.find('#page').html(view.render().$el);
      }
  });

  return new AppView();

});
