define([
    'backbone',
    'tmpl/game'
], function(
    Backbone,
    tmpl
){

var View = Backbone.View.extend({
        el: $('.game'),
        template: tmpl,

        events: {
            "click #gmscr": "gameClick"
        },

        initialize: function() {
            this.render();
            this.$el.hide();
        },
        render: function () {
            this.$el.html(this.template());
        },
        show: function () {
            this.trigger('reshow', this);
        },
        gameClick: function(event) {
            alert("Great shot");
        }

    });


    return new View();
});