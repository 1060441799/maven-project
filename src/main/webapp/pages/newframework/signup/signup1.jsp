<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html ng-app="app">
<head>
<meta charset="utf-8" />
<meta name="viewport"
	content="width=device-width, initial-scale=1, maximum-scale=1" />
<%@include file="/context/mytags.jsp"%>
<link rel="shortcut icon" href="resources/fc/images/icon/favicon.ico">
<link href="pages/newframework/static/css/app.css?v=<%=version%>" rel="stylesheet" type="text/css" />
<link href="pages/newframework/static/css/signin.css?v=<%=version%>" rel="stylesheet" type="text/css" />
</head>
 <body data-type="signup" style="background-color: #fff;">
	<div class="app app-header-fixed" style="background-color: #fff;">
    <div class="container w-xxl w-auto-xs" ng-controller="SignupFormController">
      <div class="navbar-brand block m-t signin-title">
      	<input type="text"  disabled ng-model="title"/>
      </div>
      <div class="text-tips">
      	<p class="text bg-danger">{{otherError}}</p>
      </div>
      <section id="signun-form" check="loginController.do?checkuser">
	      <div class="m-b-lg">
	          <div class="list-group list-group-sm" style="margin-bottom: 26px;">
	            <div id="userAccount" class="list-group-item">
	              <input placeholder="用户名" ng-model="user.userName" class="form-control no-border" autofocus>
	              <div class="passport-note passport-error-text passport-note-hide"><span>{{userNameError}}</span></div>
	            </div>
	            <div id="email" class="list-group-item">
	              <input type="email" placeholder="邮箱" ng-model="user.email" class="form-control no-border">
	              <div class="passport-note passport-error-text passport-note-hide"><span>{{emailError}}</span></div>
	            </div>
	            <div id="password1" class="list-group-item">
	               <input type="password" placeholder="密码" ng-model="user.password1" class="form-control no-border" >
	               <div class="passport-note passport-error-text passport-note-hide"><span>{{password1Error}}</span></div>
	            </div>
	            <div id="password2" class="list-group-item">
	               <input type="password" placeholder="确认密码" ng-model="user.password2" class="form-control no-border" >
	               <div class="passport-note passport-error-text passport-note-hide"><span>{{password2Error}}</span></div>
	            </div>
	            <div id="sigin_icode" class="list-group-item">
					<input type="text" placeholder="验证码" ng-model="user.randCode" class="form-control no-border" ng-model="user.randCode" title="验证码">
					<div class="passport-note passport-error-text passport-note-hide"><span>{{randCodeError}}</span></div>
					<div class="imgcode">
						<img src="<%=basePath%>randCodeImage" alt="验证码" title="点击图片或者点击验证码图片右边的刷新图标来刷新验证码">
						<i class="passport-icon icon-refresh"></i>
					</div>
				</div>
	          </div>
	          <div class="checkbox m-b-md m-t-none">
	            <label class="i-checks">
	              <input type="checkbox" ng-model="agree" required><i></i> 同意我们的《Wechat后台管理系统使用条款》
	            </label>
	          </div>
	          <button id="page_sigin_login_btn" ng-click="signup()" ng-disabled='$invalid' class="btn btn-lg btn-primary btn-block">确认注册</button>
	          <div class="line line-dashed"></div>
	          <p class="text-center"><small>已有帐号?</small></p>
	          <a href="<%=basePath%>login/main" class="btn btn-lg btn-default btn-block">登录系统</a>
	      </div>
	  </section>
      <div class="text-center">
      	<p><small class="text-muted">Wechat framework<br>&copy; 2016</small></p>
      </div>
    </div>
</div>
   <%@include file="/context/footer.jsp"%>
   <script type="text/javascript" src="pages/newframework/static/js/signup.js?v=<%=version%>"></script>
</body>
</html>