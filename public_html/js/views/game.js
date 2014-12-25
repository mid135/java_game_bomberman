define([
    'backbone',
    'tmpl/game',
    'models/session',
    'models/game'
], function(
    Backbone,
    tmpl,
    session,
    gameModel
){

var View = Backbone.View.extend({
        el: $('.game'),
        template: tmpl,
        user: session,
        gm:gameModel,

        initialize: function() {
            //debugger;
            this.listenTo(this.gm, 'model:change', this.draw);
            this.listenTo(this.gm, 'model:close', this.show);
            this.listenTo(this.gm, 'load:done', this.initGame);
            this.render();
            this.$el.hide();

        },

        start_game:false,
        canvas:null,
        context:null,
        count_players:2,
        ball_params:{
                radius:10
        },
        rect_params:[],
        canvas_params:{
        	scene_color:"white",
        	width:'500px',
        	height: '200px'
        },


        render: function () {
            this.$el.html(this.template());

        },
        show: function () {
            this.$el.find('.gameplay').hide();
            this.$el.find('.over').hide();
            this.trigger('reshow', this);
            this.initGame(document.getElementById('myCanvas'));
        },
        hide: function () {
           this.$el.hide();
        },

        gameplay: function() {
            this.$el.find('.gameplay').show();
        },

        initGame: function(canvas) {
            this.canvas = canvas;
            this.context = canvas;
            var self = this;
        	$(canvas)
        	.css({
        	    width:this.canvas_params.width,
        	    height:this.canvas_params.height
        	});
       		for(var i=0;i<this.count_players;i++){
       			self.rect_params.push({
       				name:i,
       				cur_x:null,
       				cur_y:null,
       				width:10,
       				height:10
       			});
       		}
        },
        draw: function() {
            if (!this.start_game){
                console.log("игра не началась");
                return;
            }
            var json = game;
            this.clean_scene();
            var myState = json.myState,
            enemyState = json.enemyState,
            ballState = json.ballState;

            myState.name = 'rect';
            enemyState.name = 'rect';
            ballState.name = 'ball';

            this.draw_shape(myState,0);
            this.draw_shape(enemyState,1);
            this.draw_shape(ballState);

                    //ïèøåì òåêóùóþ ïîçèöèþ
            this.rect_params[0].cur_x = myState.x;
            this.rect_params[0].cur_y = myState.y;
            this.rect_params[1].cur_x = enemyState.x;
            this.rect_params[1].cur_y = enemyState.y;
            return true;
        },
        draw_shape:function(shape,a){
            context = this.context;
            if (shape.x === null||shape.y === null||!shape.name){
                        console.log("no info!");
                        console.log(shape);
                        console.log("---");
                        return;            }
            var self = this,
            draw = {
                rect:function(){
                    var w = shape.width||self.rect_params[a].width,
                    h = shape.height||self.rect_params[a].height;
                    context.rect(shape.x, shape.y, w, h);
                    return true;
                },
                ball:function(){
                     var r = shape.radius||self.ball_params.radius;
                     context.arc(shape.x, shape.y, r, 0, 2*Math.PI, false);
                     return true;
                }
            }
            context.beginPath();
            if (!draw[shape.name]()){
                console.log("default");
            };
            context.fillStyle = 'yellow';
            context.fill();
            context.lineWidth = 1;
            context.strokeStyle = 'black';
            context.stroke();

	    }

    });


    return new View();
});