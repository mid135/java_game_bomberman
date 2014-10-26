window.requestAnimFrame = (function(callback) {
        return window.requestAnimationFrame || window.webkitRequestAnimationFrame || window.mozRequestAnimationFrame || window.oRequestAnimationFrame || window.msRequestAnimationFrame ||
        function(callback) {
          window.setTimeout(callback, 1000 / 60);
        };
      })();

function drawRectangle(myRectangle, context) {
  context.beginPath();
  context.rect(myRectangle.x, myRectangle.y, myRectangle.width, myRectangle.height);
  context.fillStyle = '#8ED6FF';
  context.fill();
  context.lineWidth = myRectangle.borderWidth;
  context.strokeStyle = 'black';
  context.stroke(); 

  context.beginPath();
  context.strokeStyle = 'red';
  context.lineWidth = 10;
  for (var i = 0.0; i + myRectangle.pathX <= myRectangle.x; i=1) {
    myRectangle.pathY =200 - 100*Math.sin((myRectangle.pathX/(1200/4))*Math.PI);
    context.moveTo(myRectangle.pathX, myRectangle.pathY);
    myRectangle.pathX += i;
    myRectangle.pathY =200 - 100*Math.sin((myRectangle.pathX/(1200/4))*Math.PI);
    context.lineTo(myRectangle.pathX, myRectangle.pathY);
  };
  context.stroke();
  myRectangle.pathX = 0;
  myRectangle.pathY = 200;

  context.font = 'italic 40pt Calibri';
  context.fillText('Hello World!', 150, 100);
       
}      
      
function animate(myRectangle, canvas, context, startTime) {
  // update
  var time = (new Date()).getTime() - startTime;

  var linearSpeed = 100;
  // pixels / second
  var newX = linearSpeed * time / 1000;

  if(newX < canvas.width - myRectangle.width - myRectangle.borderWidth / 2) {
    myRectangle.x = newX;
    myRectangle.y =200 - 100*Math.sin((myRectangle.x/(1200/4))*Math.PI);
  }

  // clear
        
  context.clearRect(0, 0, canvas.width, canvas.height);

  drawRectangle(myRectangle, context);

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
  width: 100,
  height: 50,
  borderWidth: 5,
  pathX: 0,
  pathY: 200
};

drawRectangle(myRectangle, context);

      // wait one second before starting animation
setTimeout(function() {
    var startTime = (new Date()).getTime();
    animate(myRectangle, canvas, context, startTime);
}, 1000);