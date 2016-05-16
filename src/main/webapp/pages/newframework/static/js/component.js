var Root = function(){
	var $root = $("<div class='row' style='padding:20px;margin:0'></div>");
	$root.appendTo(document.body);
	return $root;
}

var $root = new Root();

var Form = function(base,options){
	var $root = $("<div>").addClass(base.clas);
	$root.appendTo(base.render);
	
	var $panel = $("<div class='panel panel-default'></div>");
	var $head = $('<div class="panel-heading font-bold">Basic Infomation</div>');
	var $body = $('<div class="panel-body"></div>');
	var $form = $('<form>');
	base.formClass && $form.addClass(base.formClass);
	$body.append($form);
	$panel.append($head,$body);
	$root.append($panel);
	
	_.map(options,function(option){
		handler(option);
	});
	function handler(option){
		var option = option || {};
		var $group = $('<div class="form-group">');
		var $label = $(option.label);
		$group.append($label);
		var $element = $(option.element);
		$element.appendTo($group);
		$group.appendTo($form);
	}
	return $root;
}

var form1 = new Form({clas:"col-sm-6",render : $root},[{
	label : "<label>User Name</label>",
	element : '<input type="email" class="form-control" placeholder="Enter email">'
},{
	label : "<label>User Password</label>",
	element : '<input type="password" class="form-control" placeholder="Password">'
}]);

var form2 = new Form({clas:"col-sm-6",render : $root,formClass:"form-horizontal"},[{
	label : '<label class="col-lg-2 control-label">Email</label>',
	element : '<div class="col-lg-10"><input type="email" class="form-control" placeholder="Email"></div>'
},{
	label : '<label class="col-lg-2 control-label">Password</label>',
	element : '<div class="col-lg-10"><input type="password" class="form-control" placeholder="Password"></div>'
}]);

