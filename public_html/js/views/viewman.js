/**
 * Created by max on 16.10.14.
 */

define([
    'backbone'
], function (Backbone) {

    var Manager = Backbone.View.extend({

        subscribe: function (views) {
            for (var I in views) {
                this.listenTo(views[I], 'rerender', this.rerender);
                this.listenTo(views[I], 'reshow', this.reshow);
            }
        },

        unsubscribe: function (view) {
            this.stopListening(view);
        },

        rerender: function (view) {
            view.render();
            this.reshow(view);
        },

        reshow: function (view) {
            if (this.currentView) {
                this.currentView.hide();
            }
            this.currentView = view;
            view.$el.show();
        }

    });

    return new Manager();
});