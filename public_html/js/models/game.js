define([
'backbone'
], function (Backbone) {
    var GameModel = Backbone.Model.extend({
        connection:null,
   		initialize: function () {

   			this.connection = undefined;
   			this.json = undefined;
   			this.status = 0;
            this.players = 2;
   			_.bindAll(this, 'onConnect', 'onMessage');

   		},
   		connect: function () {
            this.trigger('load:start', 'Подключение...');
            if (this.connection !== undefined) {
               this.connection.close();
            }
            this.connection = new WebSocket('ws://' + location.host + '/gameplay');
   			this.connection.onopen = this.onConnect;
   			this.connection.onmessage = this.onMessage;
   		},
   		close: function () {
   		console.log("closed");
   			if (this.connection !== undefined) {
   				this.connection.close();
   				this.trigger("model:closed");
   			}
   		},
   		onConnect: function () {
   			//this.connection.send(JSON.stringify(sendObj));
   			console.log("open")
            this.trigger('load:done');
            this.trigger('load:start', 'Ожидание игроков...');
   		},
   		onMessage: function (msg) {
   			var data = JSON.parse(msg.data);

   			if (data.status=="game") {
   			    this.json=data;
   			    console.log(data.myState.x)
   			    console.log(data.enemyState.y)
   			    this.trigger('model:change',data);
   			} else if (data.status=="start") {
   			    this.json=data;
   			    console.log(data);
   			    this.trigger('model:start');
   			} else if(data.status=="finish") {
   			    this.json=data;
   			    //console.log(data);
   			    this.trigger("model:close");
   			}
   		}

    	});

return new GameModel();
});