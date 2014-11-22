define([
    'jquery',
    'backbone',
    'tmpl/container',
    'models/user'
    ], function ($, Backbone, tmpl, userModel) {
        var AppView = Backbone.View.extend({
        model: userModel,
        initialize: function() {
            this.$el = $('body');
            this.views = {};
            this.constructors = {};
            this.inLoad = false;
        },
        template: tmpl,

        render: function () {
            this.$el.html(this.template());
        },

        register: function (views) {
            _.forEach(views, function (view, name) {
                 this.constructors[name] = view;
            }, this);
        },
        hideOther: function (view) {
            _.forEach(this.views, function (v) {
                 if (view !== v) {
                     v.hide();
                 }
            });
        },
        getView: function (name) {
            this.trigger('stopall');

            var view = this.views[name];
            this.hideOther(view);
            if (view === undefined) {

                view = this.constructors[name];
                this.listenTo(view, 'show', this.hideOther);
                view.render();
                this.$el.find('.app').append(view.$el);
                this.views[name] = view;
            }
            return view;
        }
    });
    return new AppView();
    });