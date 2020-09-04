var MIN_SCALE = 1; // 1=scaling when first loaded
var MAX_SCALE = 64;
var tagWidth = null;
var tagHeight = null;
var viewportWidth = null;
var viewportHeight = null;
var scale = null;
var lastScale = null;
var container = null;
var tag = null;
var x = 0;
var lastX = 0;
var y = 0;
var lastY = 0;
var pinchCenter = null;

var disableTagEventHandlers = function () {
  var events = ['onclick', 'onmousedown', 'onmousemove', 'onmouseout', 'onmouseover',
                'onmouseup', 'ondblclick', 'onfocus', 'onblur'];

  events.forEach(function (event) {
    tag[event] = function () {
      return false;
    };
  });
};

// Traverse the DOM to calculate the absolute position of an element
var absolutePosition = function (el) {
  var x = 0,
    y = 0;

  while (el !== null) {
    x += el.offsetLeft;
    y += el.offsetTop;
    el = el.offsetParent;
  }

  return { x: x, y: y };
};

var restrictScale = function (scale) {
  if (scale < MIN_SCALE) {
    scale = MIN_SCALE;
  } else if (scale > MAX_SCALE) {
    scale = MAX_SCALE;
  }
  return scale;
};

var restrictRawPos = function (pos, viewportDim, tagDim) {
  if (pos < viewportDim/scale - tagDim) { // too far left/up?
    pos = viewportDim/scale - tagDim;
  } else if (pos > 0) { // too far right/down?
    pos = 0;
  }
  return pos;
};

var updateLastPos = function (deltaX, deltaY) {
  lastX = x;
  lastY = y;
};

var translate = function (deltaX, deltaY) {
  
  var newX = restrictRawPos(lastX + deltaX/scale,
                            Math.min(viewportWidth, curWidth), tagWidth);
  x = newX;
  // tag.style.marginLeft = Math.ceil(newX*scale) + 'px';
  tag.style.marginLeft = Math.ceil(newX*scale) + 'px';
  var newY = restrictRawPos(lastY + deltaY/scale,
                            Math.min(viewportHeight, curHeight), tagHeight);
  y = newY;
  //tag.style.marginTop = Math.ceil(newY*scale) + 'px';
  tag.style.marginTop = Math.ceil(newY*scale) + 'px';
  
};

var zoom = function (scaleBy) {
  scale = restrictScale(lastScale*scaleBy);

  curWidth = tagWidth*scale;
  curHeight = tagHeight*scale;
  //tag.offsetWidth = Math.ceil(curWidth)+'px';
  //tag.offsetHeight = Math.ceil(curHeight)+'px';
  tag.style.width = Math.ceil(curWidth) + 'px';
  tag.style.height = Math.ceil(curHeight) + 'px';

  translate(0, 0);
  //console.log(tag.style.width);
};

var rawCenter = function (e) {
  var pos = absolutePosition(container);

  // We need to account for the scroll position
  var scrollLeft = window.pageXOffset ? window.pageXOffset : document.body.scrollLeft;
  var scrollTop = window.pageYOffset ? window.pageYOffset : document.body.scrollTop;

  var zoomX = -x + (e.center.x - pos.x + scrollLeft)/scale;
  var zoomY = -y + (e.center.y - pos.y + scrollTop)/scale;

  return { x: zoomX, y: zoomY };
};

var updateLastScale = function () {
  lastScale = scale;
};

var zoomAround = function (scaleBy, rawZoomX, rawZoomY, doNotUpdateLast) {
  // Zoom
  zoom(scaleBy);

  // New raw center of viewport
  var rawCenterX = -x + Math.min(viewportWidth, curWidth)/2/scale;
  var rawCenterY = -y + Math.min(viewportHeight, curHeight)/2/scale;
  //console.log("viewportW : " +viewportWidth);
  // Delta
  var deltaX = (rawCenterX - rawZoomX)*scale;
  var deltaY = (rawCenterY - rawZoomY)*scale;

  // Translate back to zoom center
  translate(deltaX, deltaY);

  if (!doNotUpdateLast) {
    updateLastScale();
    updateLastPos();
  }
};

var zoomCenter = function (scaleBy) {
  // Center of viewport
  var zoomX = -x + Math.min(viewportWidth, curWidth)/2/scale;
  var zoomY = -y + Math.min(viewportHeight, curHeight)/2/scale;

  zoomAround(scaleBy, zoomX, zoomY);
};

var zoomIn = function () {
  zoomCenter(2);
};

var zoomOut = function () {
  zoomCenter(1/2);
};


  tag = document.getElementById('viewer');
  container = tag.parentElement;
  disableTagEventHandlers();

  tagWidth = tag.offsetWidth;
  tagHeight = tag.offsetHeight;
  //tagWidth = tag.width;
  //tagHeight = tag.height;
  //tagWidth = tag.style.width;
  //tagHeight = tag.style.height;
  
  viewportWidth = tag.offsetWidth;
  scale = viewportWidth/tagWidth;
  lastScale = scale;
  viewportHeight = tag.parentElement.offsetHeight;
  curWidth = tagWidth*scale;
  curHeight = tagHeight*scale;

  var hammer = new Hammer(container, {
    domEvents: true
  });

  hammer.get('pinch').set({
    enable: true
  });
  
  
  hammer.on('pan', function (e) {
    translate(e.deltaX, e.deltaY);
  });

  hammer.on('panend', function (e) {
    updateLastPos();
  });
   
  hammer.on('pinch', function (e) {

    if (pinchCenter === null) {
      pinchCenter = rawCenter(e);
      var offsetX = pinchCenter.x*scale - (-x*scale + Math.min(viewportWidth, curWidth)/2);
      var offsetY = pinchCenter.y*scale - (-y*scale + Math.min(viewportHeight, curHeight)/2);
      pinchCenterOffset = { x: offsetX, y: offsetY };
    }
    var newScale = restrictScale(scale*e.scale);
    var zoomX = pinchCenter.x*newScale - pinchCenterOffset.x;
    var zoomY = pinchCenter.y*newScale - pinchCenterOffset.y;
    var zoomCenter = { x: zoomX/newScale, y: zoomY/newScale };

    zoomAround(e.scale, zoomCenter.x, zoomCenter.y, true);
  });
  
  hammer.on('pinchend', function (e) {
    updateLastScale();
    updateLastPos();
    pinchCenter = null;
  });
	
  var doubleTapped = false; //to recognize singletap and doubletap
  
  hammer.on('doubletap', function (e) {
    //param - position of mouse cursor
    doubleTapped = true;
    var c = rawCenter(e);
    PDFViewerApplication.zoomIn();
    //zoomAround(2, c.x, c.y);
  }).on('tap',function(e){
  	setTimeout(function(){
  		if(!doubleTapped){
  			PDFViewerApplication.zoomOut();
  			//zoomOut();
  		}
  		setTimeout(function(){
  			doubleTapped = false;
  		},200);
  	},200);
  });     