define([
    'backbone',
    'tmpl/game',
    'models/session',
    'models/game'
], function(
    Backbone,
    tmpl,
    session,
    game
){

var View = Backbone.View.extend({
        el: $('.game'),
        template: tmpl,
        user: session,
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

        initialize: function() {
            this.render();
            this.$el.hide();
            //this.listenTo(game, 'model:change', this.draw);
            //this.listenTo(game, 'model:close', this.show);

        },
        render: function () {
            this.$el.html(this.template());

        },
        show: function () {
            this.$el.find('.gameplay').hide();
            this.$el.find('.over').hide();
            this.trigger('reshow', this);
        },
        hide: function () {
           this.$el.hide();
        },

        initGame: function() {
            game_js.canvas = canvas;
            game_js.context = canvas.getContext('2d');
            var self = game_js;
        	$(canvas)
        	.css({
        	    width:game_js.canvas_params.width,
        	    height:game_js.canvas_params.height
        	});
       		for(var i=0;i<game_js.count_players;i++){
       			game_js.rect_params.push({
       				name:i,
       				cur_x:null,
       				cur_y:null,
       				width:10,
       				height:10
       			});
       		}
        },
        draw: function() {
            if (!game_js.start_game){
                console.log("игра не началась");
                return;
            }
            var json = game;
            game_js.clean_scene();
            var myState = json.myState,
            enemyState = json.enemyState,
            ballState = json.ballState;

            myState.name = 'rect';
            enemyState.name = 'rect';
            ballState.name = 'ball';

            game_js.draw_shape(myState,0);
            game_js.draw_shape(enemyState,1);
            game_js.draw_shape(ballState);

                    //ïèøåì òåêóùóþ ïîçèöèþ
            game_js.rect_params[0].cur_x = myState.x;
            game_js.rect_params[0].cur_y = myState.y;
            game_js.rect_params[1].cur_x = enemyState.x;
            game_js.rect_params[1].cur_y = enemyState.y;
            return true;
        },
        draw_shape:function(shape,a){
            context = game_js.context;
            if (shape.x === null||shape.y === null||!shape.name){
                        console.log("no info!");
                        console.log(shape);
                        console.log("---");
                        return;            }
            var self = game_js,
            draw = {
                rect:function(){
                    var w = shape.width||self.rect_params[a].width,
                    h = shape.height||self.rect_params[a].height;
                    context.rect(shape.x, shape.y, w, h);
                    return true;
                },
                ball:function(){
                     var r = shape.radius||game_js.ball_params.radius;
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

	},

    });


    return new View();
});