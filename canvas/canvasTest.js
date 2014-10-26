window.requestAnimFrame = (function(callback) {
        return window.requestAnimationFrame || window.webkitRequestAnimationFrame || window.mozRequestAnimationFrame || window.oRequestAnimationFrame || window.msRequestAnimationFrame ||
        function(callback) {
          window.setTimeout(callback, 1000 / 60);
        };
      })();

function writeMessage(canvas, myRectangle) {
        var context = canvas.getContext('2d');
        var message = 'cord x ' + myRectangle.mousePosX + ' cord y ' + myRectangle.mousePosY;
        context.font = '18pt Calibri';
        context.fillStyle = 'black';
        context.fillText(message, 10, 25);
}
function getMousePos(canvas, evt) {
  var rect = canvas.getBoundingClientRect();
    return {
      x: evt.clientX - rect.left,
      y: evt.clientY - rect.top
    };
}


      
function animate(myRectangle, canvas, context, startTime) {
  // update
  var time = (new Date()).getTime() - startTime;

  var linearSpeed = 150;
  // pixels / second
  var newX = linearSpeed * time / 1000;

  if(newX < canvas.width - myRectangle.width - myRectangle.borderWidth / 2) {
    myRectangle.x = newX;
    myRectangle.y =200 - myRectangle.amplituda*Math.sin((myRectangle.x/(1200/myRectangle.frequency))*Math.PI);
  }else {
    myRectangle.indikatFinish = true;
    myRectangle.xForAnimation = newX;
    myRectangle.y = 200 - myRectangle.amplituda*Math.sin((newX/(1200/myRectangle.frequency))*Math.PI);
  }

  // clear
        
  context.clearRect(0, 0, canvas.width, canvas.height);
  writeMessage(canvas, myRectangle);
  myRectangle.drawRectangle(context);

  // request new frame
  requestAnimFrame(function() {
    animate(myRectangle, canvas, context, startTime);
  });

}
var canvas = document.getElementById('myCanvas');
var context = canvas.getContext('2d');


var myRectangle = {
  x: 0,
  y: 200,
  amplituda: 50,
  frequency: 2,  // частота
  xForAnimation: 0,
  indikatFinish: false,
  width: 100,
  height: 50,
  borderWidth: 5,
  imageObj1: new Image(),
  imageObj2: new Image(),
  radius: 70,
  pathX: 0,
  pathY: 200,
  mousePosX: 0,
  mousePosY: 0,
  nameFigure: 'square',
  init: function (){
    this.imageObj1.src = 'sharik.png';
    this.imageObj2.src = 'button.jpg';
  },
  eventChang: function() {
    if (this.nameFigure === 'square') {
      if ((this.mousePosX - this.x) <=100 && (this.mousePosX - this.x) >= 0 
      && (this.mousePosY - this.y) >= 0 && (this.mousePosY - this.y)<= 50) {
          this.nameFigure = 'circle';
      }
    }else {
      if (this.nameFigure === 'circle') {
        if (Math.sqrt(Math.pow(this.x-this.mousePosX, 2) + Math.pow(this.y-this.mousePosY, 2))<=70) {
          this.nameFigure = 'image';
        }
      }else {
        if (this.nameFigure === 'image') {
          if ((this.mousePosX - this.x) <=100 && (this.mousePosX - this.x) >= 0 
              && (this.mousePosY - this.y) >= 0 && (this.mousePosY - this.y)<= 100) {
            this.nameFigure = 'square';
          }
        } 
      }
    }
    //up
    if (Math.sqrt(Math.pow(600-this.mousePosX, 2) + Math.pow(537-this.mousePosY, 2))<=30){
      this.amplituda += 50;
    }
    //down
    if (Math.sqrt(Math.pow(600-this.mousePosX, 2) + Math.pow(607-this.mousePosY, 2))<=30){
      this.amplituda -= 50;
    }
    //left
    if (Math.sqrt(Math.pow(535-this.mousePosX, 2) + Math.pow(607-this.mousePosY, 2))<=30){
      this.frequency -= ((this.frequency > 1) ? 1 : 0);
    }
    //right
    if (Math.sqrt(Math.pow(668-this.mousePosX, 2) + Math.pow(607-this.mousePosY, 2))<=30){
      this.frequency += 1;
    }
  },
  drawRectangle: function (context) {
    if(this.nameFigure === 'square'){
      context.beginPath();
      context.rect(this.x, this.y, this.width, this.height);
      context.fillStyle = '#8ED6FF';
      context.fill();
      context.lineWidth = this.borderWidth;
      context.strokeStyle = 'black';
      context.stroke(); 
    }else {
      if(this.nameFigure === 'circle'){
        context.beginPath();
        context.arc(this.x, this.y, this.radius, 0, 2 * Math.PI, false);
        context.fillStyle = 'green';
        context.fill();
        context.lineWidth = 5;
        context.strokeStyle = '#003300';
        context.stroke();
      }else {
        if(this.nameFigure === 'image'){ 
          context.drawImage(this.imageObj1, this.x, this.y, 100, 100);
        }
      }
    }
    context.drawImage(this.imageObj2, 500, 500, 200, 150);
    context.beginPath();
    context.strokeStyle = 'red';
    context.lineWidth = 10;
    if (this.indikatFinish) {
      for (var i = 5.0; this.x - this.pathX > 0; ) {
        this.pathY = 200 - this.amplituda*Math.sin(((this.xForAnimation - this.pathX)/(1200/this.frequency))*Math.PI);
        context.moveTo(this.x - this.pathX, this.pathY);
        this.pathX += i;
        this.pathY =200 - this.amplituda*Math.sin(((this.xForAnimation - this.pathX)/(1200/this.frequency))*Math.PI);
        context.lineTo(this.x - this.pathX, this.pathY);
      }
      this.pathX = 0;
    } else {
      for (var i = 0.0; i + this.pathX <= this.x; i=5) {
        this.pathY =200 - this.amplituda*Math.sin((this.pathX/(1200/this.frequency))*Math.PI);
        context.moveTo(this.pathX, this.pathY);
        this.pathX += i;
        this.pathY =200 - this.amplituda*Math.sin((this.pathX/(1200/this.frequency))*Math.PI);
        context.lineTo(this.pathX, this.pathY);
      }
      this.pathX = 0;
    }
    context.stroke();
           
  },   
};

canvas.addEventListener('click', function(evt) {
        var mousePos = getMousePos(canvas, evt);
        myRectangle.mousePosX = mousePos.x;
        myRectangle.mousePosY = mousePos.y;
        myRectangle.eventChang();
}, false);


myRectangle.init();
myRectangle.drawRectangle(context);

      // wait one second before starting animation
setTimeout(function() {
    var startTime = (new Date()).getTime();
    animate(myRectangle, canvas, context, startTime);
}, 1000);