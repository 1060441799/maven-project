'use strict';
app.controller('SignupFormController', ['$scope', '$http', function ($scope, $http) {
    $scope.user = {};
    $scope.userNameError = null;
    $scope.emailError = null;
    $scope.password1Error = null;
    $scope.password2Error = null;
    $scope.randCodeError = null;
    
    var index = layer.load(1, {
        shade: false
  	});
    $scope.$watch('title', function(){
    	$('.container').fadeIn(500);
    	layer.close(index);
    });
    $scope.title = "注册  " + WX.constants.APPNAME;
    WX.ckeckUserName({
    	$http : $http,
    	$scope : $scope,
    	fieldError : 'userNameError',
    	type : 'signup'
    });
    WX.ckeckCode({
    	$http : $http,
    	$scope : $scope,
    	fieldError : 'randCodeError'
    });
    WX.checkField({
    	rootElement : $('#password1'),
    	context : this,
    	errorMessage : "不能为空",
    	$scope : $scope,
    	fieldError : 'password1Error'
    });
    WX.checkField({
    	rootElement : $('#password2'),
    	context : this,
    	errorMessage : "不能为空",
    	$scope : $scope,
    	fieldError : 'password2Error'
    });
    WX.checkField({
    	rootElement : $('#email'),
    	context : this,
    	errorMessage : "不能为空",
    	$scope : $scope,
    	fieldError : 'emailError'
    });
    $scope.signup = function () {
    	if(!$scope.user.userName ||
    			!$scope.user.email ||
    			!$scope.user.password1 ||
    			!$scope.user.password2 ||
    			!$scope.user.randCode){
    		return;
    	}
    	if($scope.user.password1 != $scope.user.password2){
    		$scope.otherError = "密码不一致!";
        	$('.container').find('.bg-danger').fadeIn();
        	setTimeout(function(){
        		$('.container').find('.text').fadeOut();
        	},3000);
        	return;
        }
    	if(!$scope.agree){
    		$scope.otherError = "必须同意!";
        	$('.container').find('.bg-danger').fadeIn();
        	setTimeout(function(){
        		$('.container').find('.text').fadeOut();
        	},3000);
        	return;
        }
    	$scope.$invalid = true;
        var formData = {
            "userName": $scope.user.userName,
            "email": $scope.user.email,
            "password": $scope.user.password1,
            "randCode": $scope.user.randCode
        };
        $.ajax({
            async: false,
            cache: false,
            type: 'POST',
            url: WX.constants.path + 'loginController?saveUser',
            data: formData,
            error: function () {
            	$scope.$invalid = false;
            	$scope.otherError = d.msg;
            	$('.container').find('text').removeClass('bg-success').addClass("bg-danger").fadeIn();
            	setTimeout(function(){
            		$('.container').find('.bg-danger').fadeOut();
            	},3000);
            },
            success: function (data) {
                try {
                	console.info(data);
                    var d = data;
                    if (d.success) {
                    	$scope.otherError = d.msg;
                    	$('.container').find('.text').removeClass('bg-danger').addClass("bg-success").fadeIn();
                    	setTimeout(function(){
                    		$('.container').find('.text').fadeOut();
                    	},3000);
                    } else {
                        if (d.msgType == 1) {
                        	$scope.userNameError = d.msg;
                        	$('#userAccount').find('.passport-note').fadeIn();
                        } else if (d.msgType == 3) {
                            $scope.randCodeError = d.msg;
                            $('#sigin_icode').find('.passport-note').fadeIn();
                        } else {
                        	$scope.otherError = d.msg;
                        	$('.container').find('.bg-danger').fadeIn();
                        	setTimeout(function(){
                        		$('.container').find('.bg-danger').fadeOut();
                        	},3000);
                        }
                    }
                } catch (e) {
                	$scope.otherError = d.msg;
                	$('.container').find('.bg-danger').fadeIn();
                	setTimeout(function(){
                		$('.container').find('.bg-danger').fadeOut();
                	},3000);
                }
                $scope.$invalid = false;
            }
        });
    };
}]);
