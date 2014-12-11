define([
    'backbone',
    'tmpl/game',
    'models/session'
], function(
    Backbone,
    tmpl,
    session
){

var View = Backbone.View.extend({
        el: $('.game'),
        template: tmpl,
        user: session,
        events:{
            "click .game": "doTurn"
        },
        initialize: function() {
            this.render();
            this.$el.hide();
            $('.myName').html(this.user.login);
            $('.wait').show();
            $('.game').hide();
            $('.over').hide();
            $('.myScore').html(0);
            $('.enemyScore').html(0);

        },
        render: function () {
            this.$el.html(this.template());
        },
        show: function () {
            this.trigger('reshow', this);
            this.initWS();
        },

        initWS: function() {
        var that=this;
        $('.enemyName').html(this.user.enemyName);
        this.ws = new WebSocket("ws://localhost:8080/gameplay");
                    this.ws.onopen = function (event) {
                        console.log(event);
                    }
                    this.ws.onmessage = function (event) {
                                    var data = JSON.parse(event.data);
                                    if(data.status == "start"){
                                        $('.wait').hide();
                                        $('.game').show();
                                        $('.over').hide();
                                        $('.enemyName').html(data.enemyName);
                                        console.log("start");
                                       // document.getElementById("enemyName").innerHTML = data.enemyName;
                                    }

                                    if(data.status == "finish"){
                                       $('.wait').hide();
                                       $('.game').hide();
                                       $('.over').show();
                                       if(data.win)
                                            document.getElementById("win").innerHTML = "winner!";
                                       else
                                            document.getElementById("win").innerHTML = "loser!";
                                    }

                                    if(data.status == "increment" && data.name == that.user.login){
                                        $(".myScore").html(data.score);
                                    }

                                    if(data.status == "increment" && data.name == $('.enemyName').html()){
                                        $('.enemyScore').html(data.score);
                                    }
                    }
                    this.ws.onclose = function (event) {
                        that.ws = undefined;
                    }
                    function sendMessage() {
                        var message = "";
                        ws.send(message);
                    }
        },
        doTurn: function() {
            var message="";
            this.ws.send(message);
        }

    });


    return new View();
});