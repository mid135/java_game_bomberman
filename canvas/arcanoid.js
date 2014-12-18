

    console.log("!");
    $.post('/auth',{login:'mid',password:'123'},function(){
        game_js.init(document.getElementById('myCanvas'));
    });



var game_js = {

	start_game:false,
    canvas:null,
    context:null,
	count_players:2,
	url:'ws://127.0.0.1:8080/gameplay',
	connection:null,
	canvas_params:{
		scene_color:"white",
		width:'500px',
		height: '800px'
	},
    init:function(canvas){
        //объявляем канвас
        this.canvas = canvas;
        this.context = canvas.getContext('2d');     
	    $(canvas)
	    .css({
	        width:this.canvas_params.width,
	        height:this.canvas_params.height
	    });

		for(var i=0;i<this.count_players;i++){
			
			this.rect_params.push({
				name:i,
				cur_x:null,
				cur_y:null,
				width:"10px",
				height:"10px"
			});
		}
		
		this.connection = new WebSocket(this.url);
		this.connection.onopen = function(){
			console.log("It's WORK! YEAAAAAAAAA!");
		};
		this.connection.onmessage = this.render;
		
		
    },
	//параметры объектов
    ball_params:{
        radius:"10px"
    },
    rect_params:[],	
	//координаты мышки
	getMousePos:function(evt) {
		var rect = this.canvas.getBoundingClientRect();
		return {
		  x: evt.clientX - rect.left,
		  y: evt.clientY - rect.top
		};
	},
	//отрисовка объектов
	draw_shape:function(shape){
		
		//в shape нужно передавать {name:"прямоугольник/круг",...(параметры)}
		context = this.context;
		
		if (!shape.x||!shape.y||!shape.name){
					console.log("no info!");
					console.log(shape);
					console.log("---");
					return;
		} 
		var self = this,
		draw = {
		
			//расширяемая структура :) насколько это возможно
			rect:function(){
				var w = shape.width||self.rect_params.width,
				h = shape.height||self.rect_params.height;
				context.rect(shape.x, shape.y, w, h);
				  
			},
			ball:function(){
				 var r = shape.radius||this.ball_params.radius;
				 context.arc(shape.x, shape.y, r, -1, 2*Math.PI, false);
			}
		}
		context.beginPath();
		draw[obj.name];
		context.fillStyle = 'yellow';
		context.fill();
		context.lineWidth = 7;
		context.strokeStyle = 'black';
		context.stroke();
	
	},
	draw_scene:function(json){
		if (!this.start_game)
			return;
		
		var myState = json.mystate,
		enemyState = json.enemystate,
		ballState = json.ballstate;
		
		myState.name = 'rect';
		enemyState.name = 'rect';
		ballState.name = 'ball';
		
		this.draw_shape(mystate);
		this.draw_shape(enemyState);
		this.draw_shape(ballState);
		
	},
	finish_scene:function(json){
		this.start_game = false;
	},	
	//принятие json и его обработка
    render:function(json){
		if (typeof json == 'undefined' || !json.status){
			console.log("Какая-то ошибка на серваке");
			console.log(json);			
			return;
		}		
		var self = this,		
		acts = {
			'game':self.draw_scene,
			'finish':self.finish_scene,
			'start':function(){
				self.start_game = true;
			}
		}	
		acts[json.status](json);
    },
	clean_scene:function(){
	
	}

}