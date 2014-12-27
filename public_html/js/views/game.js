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
            //debugger;
            this.canvas = document.getElementById('myCanvas');
            this.context = this.canvas.getContext('2d');
            this.listenTo(this.gm,'model:start',this.startGame)
            this.listenTo(this.gm, 'model:change', this.draw);
            this.listenTo(this.gm, 'model:close', this.gameOver);
            //this.listenTo(this.gm, 'load:done', this.initGame);
            this.render();
            this.$el.hide();

        },




        render: function () {
            this.$el.html(this.template());

        },
        show: function () {
            this.$el.show()

            this.$el.find('.gameplay').hide();
            this.$el.find('.over').hide();
            this.trigger("reshow",this);
            this.initGame();
            var self=this;
            $(document).bind("keypress",function(event){
            		    //ïðîâåðèòü êîä êíîïêè
            		   var key = event.keyCode || event.which;
            			//nn++;
                        //console.log(key);
            		   if(key ==97||key==100){
            			//var pos = key==100?5:-5;
            		     self.send(key==100?5:-5);
            			 //game_js.render({status:"game",mystate:{x:game_js.rect_params[0].cur_x+pos,y:50,name:"rect"},enemystate:{x:game_js.rect_params[1].cur_x+pos,y:20,name:"rect"},ballstate:{x:10+nn,y:29,name:"ball"}});
            		   }

            		});
        },
        hide: function () {
           this.$el.hide();
        },

        startGame: function() {
            this.start_game=true;
            console.log("game_start");
            this.$el.find('.wait').hide();
            this.$el.find('.gameplay').show();
        },
        gameOver: function() {
            this.$el.find('.gameplay').hide()
            this.$el.find('.over').show();
            this.gm.close();
        },
        initGame: function() {

            var self = this;
            this.gm.connect();
        	$(this.canvas)
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
            var json = this.gm.json;
            //console.log(json);
            this.clean_scene();
            var myState = json.myState,
            enemyState = json.enemyState,
            ballState = json.ballState;

            myState.name = 'rect';
            enemyState.name = 'rect';
            ballState.name = 'ball';
            //debugger;
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
                    //console.log('rect');
                    return true;
                },
                ball:function(){
                     var r = shape.radius||self.ball_params.radius;
                     context.arc(shape.x, shape.y, r, 0, 2*Math.PI, false);
                     //console.log('rad');
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
	    clean_scene:function(){
	            //console.log(this.canvas);
	           // debugger;
	       		this.context.clearRect(0, 0, this.canvas.width, this.canvas.height);
        },
        finish_scene:function(json){
            json=this.gm.json;
         	game_js.start_game = false;
         	game_js.clean_scene();
         	context.textAlign = "center";
            context.fillText(json.myName, 100, 75);
            context.fillText(json.myScore, 100, 90);
            context.fillText(json.oponentName, 150, 75);
            context.fillText(json.oponentScore, 150, 90);
        	return true;
        },
        send:function(delta){
            var resp = {
                delta:delta
            }
            this.gm.connection.send(JSON.stringify(resp));

        }

    });


    return new View();
});