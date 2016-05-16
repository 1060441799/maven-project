define([ 'app' ], function(app) {
	app.directive('draggable', ['$document',function($document) {
		return function(scope, element, attr) {
			var startX = 0, startY = 0, x = 0, y = 0;
			var element = $(".mywindow");
			var height = element.height();

			var winWidth = element.outerWidth();
			var winHeight = element.outerHeight();

			var pageHeight = document.body.clientHeight;
			var pageWidth = document.body.clientWidth;

			var top = (pageHeight - winHeight)/2;
			var left = (pageWidth - winWidth)/2;

			element.css({'top':top,'left':left});

			var index = 0;
			var ismove = false;
			var isdock = false;
			var isable = false;
			var moveEnd = function() {
				var $current = element;
				var off = $current.offset();
				var top = off.top;
				ismove = true;
				if (top < 10) {
					isable = true;
				} else {
					isable = false;
				}
			}
			try {
				var $current = element;
				var off = $current.offset();
				var top = off.top;
				$current.find('.mywindow-font').mousedown(function() {
					ismove = false;
				});
				$current.mouseleave(function() {
					if (ismove && isable) {
						$current.animate({
							top : '-' + (height - 3) + 'px'
						}, 210, function() {
							isdock = true;
							ismove = false;
						});
					}
				});
				$current.mouseenter(function() {
					if (isdock && isable) {
						$current.animate({
							top : '1px'
						}, 210, function() {
							isdock = false;
							ismove = true;
						});
					}
				});
			} catch (e) {
			}

			var win = $(window);
			var conf = {
				layero : element,
			    setY: 0,
			    moveLayer: function(){
			      var layero = conf.layero, mgleft = parseInt(layero.css('margin-left'));
			      var lefts = parseInt(conf.move.css('left'));
			      mgleft === 0 || (lefts = lefts - mgleft);
			      if(layero.css('position') !== 'fixed'){
			        lefts = lefts - layero.parent().offset().left;
			        conf.setY = 0;
			      }
			      layero.css({left: lefts, top: parseInt(conf.move.css('top')) - conf.setY});
			    }
			};
			element.find('.mywindow-font').on('mousedown', function(M) {
				M.preventDefault();
				conf.ismove = true;
			      var xx = conf.layero.offset().left,
			      yy = conf.layero.offset().top,
			      ww = conf.layero.outerWidth(),
			      hh = conf.layero.outerHeight();
			      $('body').append('<div id="layui-layer-moves" class="layui-layer-moves" style="left:'+ xx +'px; top:'+ yy +'px; width:'+ ww +'px; height:'+ hh +'px; z-index:2147483584"></div>');
			      conf.move = $('#layui-layer-moves');
			      conf.moveX = M.pageX - conf.move.position().left;
			      conf.moveY = M.pageY - conf.move.position().top;
			      conf.layero.css('position') !== 'fixed' || (conf.setY = win.scrollTop());
			});
			$(document).mousemove(function(M) {
				if(conf.ismove){
			      var offsetX = M.pageX - conf.moveX, offsetY = M.pageY - conf.moveY;
			      M.preventDefault();
			      if(true){
			        conf.setY = win.scrollTop();
			        var setRig = win.width() - conf.move.outerWidth(), setTop = conf.setY;
			        offsetX < 0 && (offsetX = 0);
			        offsetX > setRig && (offsetX = setRig);
			        offsetY < setTop && (offsetY = setTop);
			        offsetY > win.height() - conf.move.outerHeight() + conf.setY && (offsetY = win.height() - conf.move.outerHeight() + conf.setY);
			      }
			      conf.move.css({left: offsetX, top: offsetY});
			      offsetX = offsetY = setRig = setTop = null;
			    }
			}).mouseup(function (M) {
				try{
			      if(conf.ismove){
			        conf.moveLayer();
			        conf.move.remove();
			        moveEnd && moveEnd();
			      }
			      conf.ismove = false;
			    }catch(e){
			      conf.ismove = false;
			    }
			});
		};
	}]);
});