define([
    'backbone',
    'tmpl/game'
    ],
    function(
        Backbone,
        tmpl
    )
{
    var View = Backbone.View.extend({

        template: tmpl,
        ws: {},
        started:  false,
        finished: false,
        myName: "lol",
        enemyName: "",
        initialize: function () {
            this.render();
        },
        render: function () {
            this.$el.html(this.template);

        },
        show: function () {
            ws = new WebSocket("ws://localhost:8080/gameplay");
            ws.onopen = function (event) {
            }
            ws.onmessage = function (event) {
            var data = JSON.parse(event.data);
            if(data.status == "start"){
            document.getElementById("wait").style.display = "none";
            document.getElementById("gameplay").style.display = "block";
            document.getElementById("enemyName").innerHTML = data.enemyName;
            }
            if(data.status == "finish"){
            document.getElementById("gameOver").style.display = "block";
            document.getElementById("gameplay").style.display = "none";
            if(data.win)
            document.getElementById("win").innerHTML = "winner!";
            else
            document.getElementById("win").innerHTML = "loser!";
            }
            if(data.status == "increment" && data.name == "${myName}"){
            document.getElementById("myScore").innerHTML = data.score;
            }
            if(data.status == "increment" && data.name == document.getElementById("enemyName").innerHTML){
            document.getElementById("enemyScore").innerHTML = data.score;
            }
            }
        },
        hide: function () {
            // TODO
        },
        sendMessgae: function() {

            var message = "";
            ws.send(message);

        }

    });

    return new View();
});