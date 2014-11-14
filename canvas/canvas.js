var activFocus = true;

window.requestAnimFrame = (function(callback) {
        return window.requestAnimationFrame || window.webkitRequestAnimationFrame || window.mozRequestAnimationFrame || window.oRequestAnimationFrame || window.msRequestAnimationFrame ||
        function(callback) {
          window.setTimeout(callback, 1000 / 60);
        };
      })();
// выводит координаты мышки
function writeMessage(canvas, figure) {
        var context = canvas.getContext('2d');
        var message = 'cord x ' + figure.mousePosX + ' cord y ' + figure.mousePosY;
        context.font = '18pt Calibri';
        context.fillStyle = 'black';
        context.fillText(message, 10, 25);
}
// возвращает координаты мышки
function getMousePos(canvas, evt) {
  var rect = canvas.getBoundingClientRect();
    return {
      x: evt.clientX - rect.left,
      y: evt.clientY - rect.top
    };
}


// рассчитывает анимацию     
function animate(figure, canvas, context, startTime) {
  if(activFocus) {
    // update
    var time = (new Date()).getTime() - startTime;

    var linearSpeed = 150;
    // pixels / second
    var newX = linearSpeed * time / 1000;

    if(newX < canvas.width - figure.width - figure.borderWidth / 2) {
      figure.x = newX;
      figure.y =200 - figure.amplituda*Math.sin((figure.x/(1200/figure.frequency))*Math.PI);
    }else {
      figure.indikatFinish = true;
      figure.xForAnimation = newX;
      figure.y = 200 - figure.amplituda*Math.sin((newX/(1200/figure.frequency))*Math.PI);
    }

    // clear
          
    context.clearRect(0, 0, canvas.width, canvas.height);
    writeMessage(canvas, figure);
    figure.draw(context);
  }
  
    // request new frame
    requestAnimFrame(function() {
      animate(figure, canvas, context, startTime);
    });


}
var canvas = document.getElementById('myCanvas');
var context = canvas.getContext('2d');

// объект содержайший все фигуры и методы для работы с ними
var figure = {
  x: 0,  // координаты по x
  y: 200,  // координаты по y
  amplituda: 50,  // амплитуда колебаний
  frequency: 4,  // частота
  xForAnimation: 0, // переменная используется для анимации после достижения объекта конца canvas
  indikatFinish: false, // индикатор true при достижении объектом конца canvas
  width: 100, // ширина прямоугольника
  height: 50,  // высота прямоугольника
  borderWidth: 5,  // толщина рамки прямоугольника
  imageObj1: new Image(),  // изображение шарика
  imageObj2: new Image(),  // изображение кнопок
  radius: 70,  // радиус окружности 
  pathX: 0,  // переменная для построения синусоиды
  pathY: 200,  // переменная для построения синусоиды
  mousePosX: 0,  // координата мышки по x
  mousePosY: 0,   // координата мышки по y
  nameFigure: 'circle',  // имя фигуры которая показывается
  init: function (){  // функция для инициализации объетов изображения
    this.imageObj1.src = 'sharik.png';
    this.imageObj2.src = 'button.jpg';
  },
  eventChang: function() {  // вызывается при событии click и изменяет параметры анимации
    if (this.nameFigure === 'square') { // изменение фигуры при клике на фигуру
      if ((this.mousePosX - this.x) <=100 && (this.mousePosX - this.x) >= 0 
      && (this.mousePosY - this.y) >= 0 && (this.mousePosY - this.y)<= 50) {
          this.nameFigure = 'circle';
      }
    }else {
      if (this.nameFigure === 'circle') {// изменение фигуры при клике на фигуру
        if (Math.sqrt(Math.pow(this.x-this.mousePosX, 2) + Math.pow(this.y-this.mousePosY, 2))<=70) {
          this.nameFigure = 'image';
        }
      }else {
        if (this.nameFigure === 'image') {// изменение фигуры при клике на фигуру
          if ((this.mousePosX - this.x) <=100 && (this.mousePosX - this.x) >= 0 
              && (this.mousePosY - this.y) >= 0 && (this.mousePosY - this.y)<= 100) {
            this.nameFigure = 'square';
          }
        } 
      }
    }
    //up
    // изменение амплитуды при клике на up
    if (Math.sqrt(Math.pow(600-this.mousePosX, 2) + Math.pow(537-this.mousePosY, 2))<=30){
      this.amplituda += 50;
    }
    //down
    // изменение амплитуды при клике на down
    if (Math.sqrt(Math.pow(600-this.mousePosX, 2) + Math.pow(607-this.mousePosY, 2))<=30){
      this.amplituda -= 50;
    }
    //left
    // изменение частоты при клике на left
    if (Math.sqrt(Math.pow(535-this.mousePosX, 2) + Math.pow(607-this.mousePosY, 2))<=30){
      this.frequency -= ((this.frequency > 1) ? 1 : 0);
    }
    //right
    // изменение частоты при клике на right
    if (Math.sqrt(Math.pow(668-this.mousePosX, 2) + Math.pow(607-this.mousePosY, 2))<=30){
      this.frequency += 1;
    }
  },
  // функция для рисования текущего кадра анимации
  draw: function (context) {
    if(this.nameFigure === 'square'){ // если нужно рисовать квадрат
      context.beginPath();
      context.rect(this.x, this.y, this.width, this.height);
      context.fillStyle = '#8ED6FF';
      context.fill();
      context.lineWidth = this.borderWidth;
      context.strokeStyle = 'black';
      context.stroke(); 
    }else {
      if(this.nameFigure === 'circle'){ // если нужно рисовать окружность
        context.beginPath();
        context.arc(this.x, this.y, this.radius, 0, 2 * Math.PI, false);
        context.fillStyle = 'green';
        context.fill();
        context.lineWidth = 5;
        context.strokeStyle = '#003300';
        context.stroke();
      }else {
        if(this.nameFigure === 'image'){  // если нужно рисовать изображение
          context.drawImage(this.imageObj1, this.x, this.y, 100, 100);
        }
      }
    }
    context.drawImage(this.imageObj2, 500, 500, 200, 150);  // рисуем кнопки
    // начинаем рисовать траекторию
    context.beginPath();
    context.strokeStyle = '#1A1FB5';
    context.lineWidth = 10;
    if (this.indikatFinish) { // если фигура достигла конца canvas
      for (var i = 5.0; this.x - this.pathX > 0; ) {
        this.pathY = 200 - this.amplituda*Math.sin(((this.xForAnimation - this.pathX)/(1200/this.frequency))*Math.PI);
        context.moveTo(this.x - this.pathX, this.pathY);
        this.pathX += i;
        this.pathY =200 - this.amplituda*Math.sin(((this.xForAnimation - this.pathX)/(1200/this.frequency))*Math.PI);
        context.lineTo(this.x - this.pathX, this.pathY);
      }
      this.pathX = 0;
    } else { // если фигура еще не достигла конца canvas
      for (var i = 0.0; i + this.pathX <= this.x; i=5) {
        this.pathY =200 - this.amplituda*Math.sin((this.pathX/(1200/this.frequency))*Math.PI);
        context.moveTo(this.pathX, this.pathY);
        this.pathX += i;
        this.pathY =200 - this.amplituda*Math.sin((this.pathX/(1200/this.frequency))*Math.PI);
        context.lineTo(this.pathX, this.pathY);
      }
      this.pathX = 0;
    }
    context.stroke(); // заканчиваем рисовать траекторию
           
  },   
};

// подписываемся на событие клик
canvas.addEventListener('click', function(evt) {
        var mousePos = getMousePos(canvas, evt);
        figure.mousePosX = mousePos.x;
        figure.mousePosY = mousePos.y;
        figure.eventChang();
}, false);

canvas.addEventListener('mousemove', function(evt) {
        var mousePos = getMousePos(canvas, evt);
        figure.mousePosX = mousePos.x;
        figure.mousePosY = mousePos.y;
}, false);

figure.init();
figure.draw(context);

// вкладка активна
window.onfocus = function(){ activFocus = true; }
// вкладка свернута
window.onblur = function(){ activFocus = false; }

      // wait one second before starting animation
setTimeout(function() {
    var startTime = (new Date()).getTime();
    animate(figure, canvas, context, startTime);
}, 1000);