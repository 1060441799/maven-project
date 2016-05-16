define([], function() {
	var cli = function() {
		var index = 0;
		var ismove = false;
		var isdock = false;
		var isable = false;

		WX.moveEnd = function() {
			var $current = layer.getComponentByIndex(index);
			var off = $current.offset();
			var top = off.top;
			ismove = true;
			if (top < 10) {
				isable = true;
			} else {
				isable = false;
			}
		}
		
//		var obj = {
//			type: 1,
//			area : [ '350px', '630px' ],
//			fix : false,
//			content : $('#chat').html(),
//			shade : false,
//			id : 'myTest',
//			title : 'Web IM',
//			windowId : 'myWindow',
//			moveEnd : moveEnd,
//			success : function(){
//				$('#chat').find('a').attr('href','javascript:void(0)')
//			}
//		};
//
//		index = layer.open(obj);

		try {
			var $current = layer.getComponentByIndex(index);
			var off = $current.offset();
			var top = off.top;

			$current.mousedown(function() {
				ismove = false;
			});

			$current.mouseleave(function() {
				if (ismove && isable) {
					$current.animate({
						top : '-626px'
					}, 210, function() {
						isdock = true;
						ismove = false;
					});
				}
			});

			$current.mouseenter(function() {
				if (isdock && isable) {
					$current.animate({
						top : '3px'
					}, 210, function() {
						isdock = false;
						ismove = true;
					});
				}
			});
		} catch (e) {

		}
	};
	return cli;
});
