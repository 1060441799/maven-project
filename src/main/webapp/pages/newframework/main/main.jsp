<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html ng-controller="MainController">
<head>
    <meta charset="utf-8"/>
    <meta name="viewport"
          content="width=device-width, initial-scale=1, maximum-scale=1"/>
    <%@include file="/context/mytags.jsp" %>
    <link rel="shortcut icon" href="resources/fc/images/icon/favicon.ico">
    <link href="pages/newframework/static/css/app.css?v=<%=version%>"
          rel="stylesheet" type="text/css"/>
    <style type="text/css">
        .table > tbody > tr > td, .table > tfoot > tr > td,
        .table > thead > tr > th {
            padding: 5px 8px;
        }

        .wrapper-md {
            padding: 10px;
        }
    </style>
</head>
<body>
<div class="root">
    <div class="app app-header-fixed app-aside-folded">
        <!-- header -->
        <header id="header" class="app-header navbar">
            <!-- navbar header -->
            <div class="navbar-header bg-info">
                <button class="pull-right visible-xs dk" ui-toggle="show"
                        target=".navbar-collapse">
                    <i class="glyphicon glyphicon-cog"></i>
                </button>
                <button class="pull-right visible-xs" ui-toggle="off-screen"
                        target=".app-aside">
                    <i class="glyphicon glyphicon-align-justify"></i>
                </button>
                <!-- brand -->
                <a href="javascript:void(0)" ui-sref="app" class="navbar-brand text-lt"> <i
                        class="fa fa-weixin"></i> <span class="hidden-folded m-l-xs">Wechat</span>
                </a>
                <!-- / brand -->
            </div>
            <!-- / navbar header -->
            <!-- navbar collapse -->
            <div
                    class="collapse pos-rlt navbar-collapse box-shadow bg-white-only">
                <!-- buttons -->
                <div class="nav navbar-nav hidden-xs">
                    <a href="javascript:void(0)" class="btn no-shadow navbar-btn"
                       ui-toggle="app-aside-folded" target=".app"> <i
                            class="fa fa-dedent fa-fw text"></i> <i
                            class="fa fa-indent fa-fw text-active"></i>
                    </a> <a href="javascript:void(0)" class="btn no-shadow navbar-btn"
                            ui-toggle="show" target="#aside-user"> <i
                        class="icon-user fa-fw"></i>
                </a>
                </div>
                <!-- / buttons -->
                <!-- link and dropdown -->
                <ul class="nav navbar-nav hidden-sm">
                    <li class="dropdown pos-stc"><a href="javascript:void(0)"
                                                    data-toggle="dropdown" class="dropdown-toggle"> <span>快速</span>
                        <span class="caret"></span>
                    </a>
                        <div class="dropdown-menu wrapper w-full bg-white">
                            <div class="row">
                                <div class="col-sm-4">
                                    <div class="m-l-xs m-t-xs m-b-xs font-bold">
                                        Pages <span class="badge badge-sm bg-success">10</span>
                                    </div>
                                    <div class="row">
                                        <div class="col-xs-6">
                                            <ul class="list-unstyled l-h-2x">
                                                <li><a href><i
                                                        class="fa fa-fw fa-angle-right text-muted m-r-xs"></i>Profile</a>
                                                </li>
                                            </ul>
                                        </div>
                                        <div class="col-xs-6">
                                            <ul class="list-unstyled l-h-2x">
                                                <li><a href><i
                                                        class="fa fa-fw fa-angle-right text-muted m-r-xs"></i>Price</a>
                                                </li>
                                            </ul>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-sm-4 b-l b-light">
                                    <div class="m-l-xs m-t-xs m-b-xs font-bold">
                                        UI Kits <span class="label label-sm bg-primary">12</span>
                                    </div>
                                    <div class="row">
                                        <div class="col-xs-6">
                                            <ul class="list-unstyled l-h-2x">
                                                <li><a href><i
                                                        class="fa fa-fw fa-angle-right text-muted m-r-xs"></i>Buttons</a>
                                                </li>
                                            </ul>
                                        </div>
                                        <div class="col-xs-6">
                                            <ul class="list-unstyled l-h-2x">
                                                <li><a href><i
                                                        class="fa fa-fw fa-angle-right text-muted m-r-xs"></i>Bootstap</a>
                                                </li>
                                            </ul>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </li>
                    <li class="dropdown"><a href="javascript:void(0)"
                                            data-toggle="dropdown" class="dropdown-toggle"> <i
                            class="fa fa-fw fa-plus visible-xs-inline-block"></i> <span
                            translate="header.navbar.new.NEW">其他</span> <span class="caret"></span>
                    </a>
                        <ul class="dropdown-menu" role="menu">
                            <li><a href="javascript:void(0)">Projects</a></li>
                        </ul>
                    </li>
                </ul>
                <!-- / link and dropdown -->
                <!-- search form -->
                <form class="navbar-form navbar-form-sm navbar-left shift"
                      ui-shift="prependTo" data-target=".navbar-collapse" role="search">
                    <div class="form-group">
                        <div class="input-group">
                            <input type="text" ng-model="selected"
                                   typeahead="state for state in states | filter:$viewValue | limitTo:8"
                                   class="form-control input-sm bg-light no-border rounded padder"
                                   placeholder="搜索..."> <span class="input-group-btn">
								<button type="submit" class="btn btn-sm bg-light rounded">
                                    <i class="fa fa-search"></i>
                                </button>
							</span>
                        </div>
                    </div>
                </form>
                <!-- / search form -->
                <!-- nabar right -->
                <ul class="nav navbar-nav navbar-right">
                    <li class="dropdown"><a href="javascript:void(0)"
                                            data-toggle="dropdown" class="dropdown-toggle"> <i
                            class="icon-bell fa-fw"></i> <span class="visible-xs-inline">Notifications</span>
                        <span class="badge badge-sm up bg-danger pull-right-xs">2</span>
                    </a> <!-- dropdown -->
                        <div class="dropdown-menu w-xl animated fadeInUp">
                            <div class="panel bg-white">
                                <div class="panel-heading b-light bg-light">
                                    <strong>You have <span>2</span> notifications
                                    </strong>
                                </div>
                                <div class="list-group">
                                    <a href="javascript:void(0)" class="media list-group-item">
										<span class="pull-left thumb-sm"> <img
                                                src="<%=basePath %>${signatureFile}" alt="..." class="img-circle">
									</span> <span class="media-body block m-b-none"> Use awesome
											animate.css<br> <small class="text-muted">10
                                            minutes ago
                                        </small>
									</span>
                                    </a> <a href="javascript:void(0)" class="media list-group-item">
										<span class="media-body block m-b-none"> 1.0 initial
											released<br> <small class="text-muted">1 hour
                                                ago
                                            </small>
									</span>
                                </a>
                                </div>
                                <div class="panel-footer text-sm">
                                    <a href="javascript:void(0)" class="pull-right"><i
                                            class="fa fa-cog"></i></a> <a href="javascript:void(0)"
                                                                          data-toggle="class:show animated fadeInRight">See
                                    all the
                                    notifications</a>
                                </div>
                            </div>
                        </div> <!-- / dropdown --></li>
                    <li class="dropdown"><a href="javascript:void(0)"
                                            data-toggle="dropdown" class="dropdown-toggle clear"
                                            data-toggle="dropdown"> <span
                            class="thumb-sm avatar pull-right m-t-n-sm m-b-n-sm m-l-sm">
								<img src="${signatureFile}" alt="..."> <i
                            class="on md b-white bottom"></i>
						</span> <span class="hidden-sm hidden-md">${userName }</span> <b
                            class="caret"></b>
                    </a> <!-- dropdown -->
                        <ul class="dropdown-menu animated fadeInRight w">
                            <li class="wrapper b-b m-b-sm bg-light m-t-n-xs">
                                <div>
                                    <p>职务：${roleName }</p>
                                </div>
                                <div class="progress progress-xs m-b-none dker">
                                    <div class="progress-bar progress-bar-info"
                                         data-toggle="tooltip" data-original-title="50%"
                                         style="width: 50%"></div>
                                </div>
                            </li>
                            <li><a href> <span class="badge bg-danger pull-right">30%</span>
                                <span>个人信息</span>
                            </a></li>
                            <li><a ui-sref="app.page.profile">修改密码</a></li>
                            <li class="divider"></li>
                            <li><a href="javascript:void" ng-click="logout()">退出系统</a></li>
                        </ul> <!-- / dropdown --></li>
                </ul>
                <!-- / navbar right -->
            </div>
            <!-- / navbar collapse -->
        </header>
        <!-- / header -->
        <!-- aside -->
        <aside id="aside" class="app-aside hidden-xs b-r bg-white">
            <div class="aside-wrap">
                <div class="navi-wrap">
                    <!-- user -->
                    <div class="clearfix hidden-xs text-center hide" id="aside-user">
                        <div class="dropdown wrapper">
                            <a href="app.page.profile"> <span
                                    class="thumb-lg w-auto-folded avatar m-t-sm"> <img
                                    src="${signatureFile}" class="img-full" alt="...">
							</span>
                            </a> <a href="javascript:void(0)" data-toggle="dropdown"
                                    class="dropdown-toggle hidden-folded"> <span class="clear">
									<span class="block m-t-sm"> <strong
                                            class="font-bold text-lt">${userName }</strong> <b
                                            class="caret"></b>
								</span> <span class="text-muted text-xs block">${roleName }</span>
							</span>
                        </a>
                            <!-- dropdown -->
                            <ul class="dropdown-menu animated fadeInRight w hidden-folded">
                                <li class="wrapper b-b m-b-sm bg-info m-t-n-xs"><span
                                        class="arrow top hidden-folded arrow-info"></span>
                                    <div>
                                        <p>${roleName }</p>
                                    </div>
                                    <div class="progress progress-xs m-b-none dker">
                                        <div class="progress-bar bg-white" data-toggle="tooltip"
                                             data-original-title="50%" style="width: 50%"></div>
                                    </div>
                                </li>
                                <li><a href="javascript:void" ng-click="logout()">退出系统</a>
                                </li>
                            </ul>
                            <!-- / dropdown -->
                        </div>
                        <div class="line dk hidden-folded"></div>
                    </div>
                    <!-- / user -->
                    <!-- nav -->
                    <nav ui-nav class="navi clearfix">
                        <ul class="nav">
                            <li class="hidden-folded padder m-t m-b-sm text-muted text-xs">
                                <span>功能导航</span>
                            </li>
                            <li ng-repeat="item in items" repeat-done="doSomething()"><a
                                    href="javascript:void(0)" class="auto"> <span
                                    class="pull-right text-muted"> <i
                                    class="fa fa-fw fa-angle-right text"></i> <i
                                    class="fa fa-fw fa-angle-down text-active"></i>
								</span> <i class="{{item.iconClas}}"></i> <span class="font-bold"
                                                                                ng-bind="item.functionName"></span>
                            </a>
                                <ul class="nav nav-sub dk">
                                    <li class="nav-sub-header"><a href="javascript:void(0)">
                                        <span ng-bind="item.functionName"></span>
                                    </a></li>
                                    <li ng-repeat="i in item.functions track by $index"><a
                                            href="javascript:void(0)" ui-sref="{{i.functionAPPUrl}}"> <span
                                            ng-bind="i.functionName"></span>
                                    </a></li>
                                </ul>
                            </li>
                            <li class="line dk"></li>
                            <li class="hidden-folded padder m-t m-b-sm text-muted text-xs">
                                <span>个人信息</span>
                            </li>
                            <li><a href="javascript:void(0)"> <i
                                    class="icon-user icon text-success-lter"></i> <b
                                    class="badge bg-success pull-right">30%</b> <span>Profile</span>
                            </a></li>
                            <li><a href="javascript:void(0)"> <i
                                    class="icon-question icon"></i> <span>Documents</span>
                            </a></li>
                        </ul>
                    </nav>
                    <!-- nav -->
                    <!-- aside footer -->
                    <div class="wrapper m-t">
                        <div class="text-center-folded">
                            <span class="pull-right pull-none-folded">60%</span> <span
                                class="hidden-folded">Milestone</span>
                        </div>
                        <div class="progress progress-xxs m-t-sm dk">
                            <div class="progress-bar progress-bar-info" style="width: 60%;">
                            </div>
                        </div>
                        <div class="text-center-folded">
                            <span class="pull-right pull-none-folded">35%</span> <span
                                class="hidden-folded">Release</span>
                        </div>
                        <div class="progress progress-xxs m-t-sm dk">
                            <div class="progress-bar progress-bar-primary"
                                 style="width: 35%;"></div>
                        </div>
                    </div>
                    <!-- / aside footer -->
                </div>
            </div>
        </aside>
        <!-- / aside -->
        <!-- content -->
        <div id="content" ui-view></div>
        <!-- / content -->
        <!-- footer -->
        <footer id="footer" class="app-footer" role="footer">
            <div class="wrapper b-t bg-light">
				<span class="pull-right">2.0.1 <a href
                                                  class="m-l-sm text-muted"><i
                        class="fa fa-long-arrow-up"></i></a></span>
                &copy; 2016 Copyright.
            </div>
        </footer>
        <!-- / footer -->
    </div>
</div>
<%@include file="/context/footer.jsp" %>
<script type="text/javascript" src="pages/newframework/static/js/MainBoot.js?v=<%=version%>"></script>
</body>
</html>