define([
    'backbone',
    'tmpl/canvas'
], function(
    Backbone,
    tmpl
){

    function Point (x, y) {
        this.x = x;
        this.y = y;
    }

    function Button(start, length, text, height, btnName) {
        this.start = start;
        this.length = length;
        this.text = text;
        this.height = height;
        this.btnName = btnName;

        this.drawButton = function(context) {
            context.beginPath();
            context.rect(this.start, 0, this.length, this.height);
            context.fillStyle = 'yellow';
            context.fill();
            context.font = '12pt Calibri';
            context.lineWidth = 1;
            context.strokeStyle = 'blue';
            context.strokeText(this.text, this.start + 5, this.height / 2);
            context.lineWidth = 2;
            context.strokeStyle = 'black';
            context.stroke();
        };

        this.isInBounds = function(x, y) {
            if ((x >  this.start) && (x < this.start + this.length) && (y > 0) && (y < this.height)) {
                return true;
            }
            return false;
        };
    }

    function Tail() {
        this.tail = [];

        this.drawTail = function (context) {
            context.beginPath();
            context.lineWidth = 4;
            context.strokeStyle = 'black';

            if (this.tail.length == 0) return;

            context.moveTo(this.tail[0].x, this.tail[0].y);
            for (var I in this.tail) {
                context.lineTo(this.tail[I].x, this.tail[I].y);
            }
            context.stroke();
        };

        this.append = function (x, y) {
            this.tail.push(new Point(x, y));
            if (this.tail.length == 100) {
                this.tail.shift();
            }
        };

        this.clearAll = function () {
            this.tail = [];
        }
    }

    function Circle(x, y, radius, borderWidth) {
        this.x = x;
        this.y = y;
        this.radius = radius;
        this.borderWidth = borderWidth;

        this.drawCircle = function(context) {
            context.beginPath();
            context.arc(this.x, this.y, this.radius, 0, 2 * Math.PI, false);
            context.fillStyle = '#00FFFF';
            context.fill();
            context.lineWidth = this.borderWidth;
            context.strokeStyle = '#003300';
            context.stroke();
        }
    }

    function sinePoint(base, X, amp, freq) {
        return base + (amp * Math.sin(freq * X * Math.PI));
    }

    function getMousePos(canvas, evt) {
        var rect = canvas.getBoundingClientRect();
        return new Point(evt.clientX - rect.left, evt.clientY - rect.top);
    }

    var View = Backbone.View.extend({
        el: $('.canvas'),
        template: tmpl,
        pause: false,
        timerID: 0,

        initialize: function () {
            this.render();
            this.$el.hide();
        },

        render: function () {
            this.$el.html(this.template());
        },

        show: function () {
            this.trigger('reshow', this);
            this.launch();
        },

        launch: function () {
            var self = this;

            var amp = 100,
                freq = 40,
                base = 200;

            var canvas = document.getElementById('myCanvas'),
                context = canvas.getContext('2d'),
                btnCanvas = document.getElementById('btnCanvas'),
                btnContext = btnCanvas.getContext('2d');

            var circle = new Circle(0, 75, 20, 2);
            var tail = new Tail();

            var btnArray = [
                new Button(0, 150, "Frequency Up", 50, "freqUp"),
                new Button(160, 150, "Frequency Down", 50, "freqDown"),
                new Button(320, 150, "Amplitude Up", 50, "ampUp"),
                new Button(480, 150, "Amplitude Down", 50, "ampDown"),
                new Button(640, 150, "Pause", 50, "pause")

            ];

            btnArray.forEach(function (entry) {
                entry.drawButton(btnContext);
            });

            btnCanvas.addEventListener('click', function (evt) {
                var mousePos = getMousePos(btnCanvas, evt);
                btnArray.forEach(function (entry) {
                    if (entry.isInBounds(mousePos.x, mousePos.y)) {
                        buttonPress(entry.btnName);
                    }
                });
            }, false);

            function buttonPress(btnName) {
                if (btnName == "freqUp") {
                    freq += 10;
                } else if (btnName == "freqDown") {
                    freq -= 10;
                } else if (btnName == "ampUp") {
                    amp += 10;
                } else if (btnName == "ampDown"){
                    amp -= 10;
                }
                else {
                    self.pause = !self.pause;
                }
            }

            var linearSpeed = 0.5,
                time = 0,
                interval = 1;

            var animate = function(circle, canvas, context) {
                if (!self.pause) {
                    var newX = 4*linearSpeed * time;
                    time += interval;
                    console.log(time);

                    var D = circle.radius * 2;
                    var R = circle.radius;
                    context.clearRect(circle.x - R - 3, circle.y - R - 3, D + 6, D + 6);

                    tail.drawTail(context);

                    if (newX < canvas.width - D) {
                        circle.x = newX;
                        circle.y = sinePoint(base, newX/1000, amp, freq);
                        tail.append(circle.x, circle.y);
                    } else {
                        time = 0;
                        tail.clearAll();
                        context.clearRect(0, 0, canvas.width, canvas.height);
                    }

                    circle.drawCircle(context);
                }
            };

            this.timerID = setInterval(function () {
                animate(circle, canvas, context);
            }, interval);
        },

        unlaunch: function () {
            clearInterval(this.timerID);
        }
    });

    return new View();
});
