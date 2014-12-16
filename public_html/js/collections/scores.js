define([
  'jquery',
  'backbone',
  'models/score',
  'sync_scores'
], function($, Backbone, ScoreModel, scoreSync){

  var ScoreCollection = Backbone.Collection.extend({
    sync: scoreSync,
    initialize: function () {
        console.log(this);
        this.fetch();
    },
    model: ScoreModel
  });

  return new ScoreCollection();
});
