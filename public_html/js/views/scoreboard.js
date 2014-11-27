define([
    'backbone',
    'tmpl/scoreboard',
    'collections/scores'
], function(
    Backbone,
    tmpl,
    scoreCollection
){

var View = Backbone.View.extend({
        el: $('.scoreboard'),
        template: tmpl,
        events: {

        },

        initialize: function () {
            this.collection.on("reset", this.render, this);
            this.render();
            this.$el.hide();
        },
        render: function () {
            var scores = this.collection.toJSON();
            this.$el.html(this.template(scores));
        },
        show: function () {
            this.collection.fetch();
            this.trigger('rerender', this);
        }
    });


    return new View({collection: scoreCollection});
});