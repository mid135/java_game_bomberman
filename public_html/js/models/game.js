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
   			this.connect();
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
   			if (this.connection !== undefined) {
   				this.connection.close();
   				this.trigger("model:closed");
   			}
   		},
   		onConnect: function () {
   			//this.connection.send(JSON.stringify(sendObj));
            this.trigger('load:done');
            this.trigger('load:start', 'Ожидание игроков...');
   		},
   		onMessage: function (msg) {
   			var data = JSON.parse(msg.data);
   			if (data.status=="game") {
   			    this.json=message;
   			    this.trigger('model:change');
   			}
   		}

    	});

return new GameModel();
});