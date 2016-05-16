<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8"/>
    <meta name="viewport"
          content="width=device-width, initial-scale=1, maximum-scale=1"/>
    <%@include file="/context/mytags.jsp" %>
    <link rel="shortcut icon" href="resources/fc/images/icon/favicon.ico">
    <link href="pages/newframework/static/css/app.css?v=<%=version%>" rel="stylesheet" type="text/css"/>
    <link href="pages/newframework/static/css/signin.css?v=<%=version%>" rel="stylesheet" type="text/css"/>
</head>
<body data-type="signin" style="
    background: #fff !important;
">
<div id="root" class="root">
    <div class="app app-header-fixed" style="background-color: #fff;">
        <div class="container w-xxl w-auto-xs" ng-controller="SigninFormController">
            <div class="navbar-brand block m-t signin-title">
                <input type="text" disabled ng-model="title"/>
            </div>
            <div class="text-tips">
                <p class="text bg-danger">{{otherError}}</p>
            </div>
            <form id="signin-form" name="form">
                <div class="m-b-lg">
                    <div class="list-group list-group-sm" style="margin-bottom: 26px;">
                        <div id="userAccount" class="list-group-item">
                            <input type="text" name="name" placeholder="用户名" ng-model="name" class="form-control no-border" title="你的系统帐号"
                                   validate val="$value" validate-config="{datatype:'s6-18',errormsg:'昵称至少6个字符,最多18个字符！',nullmsg:'请填写信息！'}">
                            <div class="passport-note passport-error-text " ng-show='form.name.$error.blacklist'><span>dasdasdas</span></div>
                        </div>
                        <div id="userPassword" class="list-group-item">
                            <input type="password" placeholder="输入6~32位密码" ng-model="user.password" class="form-control no-border" title="你的登录密码">
                            <div class="passport-note passport-error-text passport-note-hide"><span>{{passwordError}}</span></div>
                        </div>
                        <div id="sigin_icode" class="list-group-item">
                            <input type="text" placeholder="验证码" class="form-control no-border" ng-model="user.randCode" title="验证码">
                            <div class="passport-note passport-error-text passport-note-hide"><span>{{randCodeError}}</span></div>
                            <div class="imgcode">
                                <img src="<%=basePath%>randCodeImage" alt="验证码" title="点击图片或者点击验证码图片右边的刷新图标来刷新验证码">
                                <i class="passport-icon icon-refresh"></i>
                            </div>
                        </div>
                    </div>
                    <div class="checkbox m-b-md m-t-none">
                        <label class="i-checks" title="为了你的帐号安全，请勿在公共电脑勾选此项！"> <input
                                type="checkbox" ng-model="user.setCookie"><i></i> 7天内免登录
                        </label>
                    </div>
                    <button id="page_sigin_login_btn" ng-click="login()" ng-disabled='$invalid'
                            class="btn btn-lg btn-primary btn-block">登录系统
                    </button>
                    <div class="text-center m-t m-b"><a href="page_forgotpwd.html">忘记密码?</a></div>
                    <div class="line line-dashed"></div>
                    <p class="text-center">
                        <small>没有帐号?</small>
                    </p>
                    <a href="<%=basePath%>login/signup" class="btn btn-lg btn-default btn-block">创建一个</a>
                </div>
            </form>
            <div class="text-center">
                <p>
                    <small class="text-muted">Wechat framework<br>&copy; 2016</small>
                </p>
            </div>
        </div>
    </div>
</div>
<%@include file="/context/footer.jsp" %>
<script type="text/javascript" src="pages/newframework/static/js/SigninBoot.js?v=<%=version%>"></script>
</body>
</html>