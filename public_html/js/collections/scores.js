define([
    'backbone',
    'models/score',
    'api/sync'
], function(
    Backbone,
    ScoreModel,
    ApiSync
){

    var ScoreCollection = Backbone.Collection.extend({
        model: ScoreModel,
        url: "/scores",
        sync: ApiSync,

        parse: function (response) {
            if (response.status == 1) {
                this.reset(response.bestScores);
            }
        },

        comparator: function (model) {
            return -model.get('score');
        }
    });

    return new ScoreCollection();
});