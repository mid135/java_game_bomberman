
$(document).ready(function(){
    console.log("!");
    game_js.init(document.getElementById('myCanvas'));

});


//var nn = 0;
var game_js = {

	start_game:false,
    canvas:null,
    context:null,
	count_players:2,
	url:'ws://localhost:8080/gameplay',
	connection:null,
	canvas_params:{
		scene_color:"white",
		width:'500px',
		height: '200px'
	},
    init:function(canvas){
        //îáúÿâëÿåì êàíâàñ
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
		console.log(game_js.rect_params);
		game_js.connection = new WebSocket(game_js.url);
		game_js.connection.onopen = function(){
			console.log("It's WORK! YEAAAAAAAAA!");
		};
		game_js.connection.onmessage = game_js.render;
		
		//game_js.render({status:"start"});
		//game_js.render({status:"game",mystate:{x:50,y:50,name:"rect"},enemystate:{x:100,y:20,name:"rect"},ballstate:{x:10,y:29,name:"ball"}});
		
		
		$(document).bind("keypress",function(event){
		    //ïðîâåðèòü êîä êíîïêè
		   var key = event.keyCode || event.which;
			//nn++;
            console.log(key);
		   if(key ==97||key==100){
			//var pos = key==100?5:-5;
		     self.send(key==100?5:-5);
			 //game_js.render({status:"game",mystate:{x:game_js.rect_params[0].cur_x+pos,y:50,name:"rect"},enemystate:{x:game_js.rect_params[1].cur_x+pos,y:20,name:"rect"},ballstate:{x:10+nn,y:29,name:"ball"}});
		   }

		});
		
    },
	//ïàðàìåòðû îáúåêòîâ
    ball_params:{
        radius:10
    },
    rect_params:[],	
	//êîîðäèíàòû ìûøêè
	getMousePos:function(evt) {
		var rect = game_js.canvas.getBoundingClientRect();
		return {
		  x: evt.clientX - rect.left,
		  y: evt.clientY - rect.top
		};
	},
	//îòðèñîâêà îáúåêòîâ
	draw_shape:function(shape,a){
		
		//â shape íóæíî ïåðåäàâàòü {name:"ïðÿìîóãîëüíèê/êðóã",...(ïàðàìåòðû)}
		context = game_js.context;
		
		if (shape.x === null||shape.y === null||!shape.name){
					console.log("no info!");
					console.log(shape);
					console.log("---");
					return;
		} 
		
		//console.log(shape);
		
		var self = game_js,
		draw = {
		
			//ðàñøèðÿåìàÿ ñòðóêòóðà :) íàñêîëüêî ýòî âîçìîæíî
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
	draw_scene:function(json){		
		if (!game_js.start_game){		
			console.log("игра не началась");
			return;
		}
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
	finish_scene:function(json){
		game_js.start_game = false;
		return true;
	},	
	//ïðèíÿòèå json è åãî îáðàáîòêà
    render:function(json){
        console.log(json.data);
        var json = JSON.parse(json.data);
        //console.log(json);
		if (typeof json == 'undefined' || !json.status){
			console.log("server error");
			console.log(json);			
			return;
		}		
		var self = game_js,		
		acts = {
			'game':self.draw_scene,
			'finish':self.finish_scene,
			'start':function(){
				self.start_game = true;
				return true;
			}
		}	
		if (!acts[json.status](json)){
			console.log("default");
		};
    },
    send:function(delta){
        var resp = {
            delta:delta
        }
		game_js.connection.send(JSON.stringify(resp));
		
    },
	clean_scene:function(){
		game_js.context.clearRect(0, 0, game_js.canvas.width, game_js.canvas.height);
	}

}