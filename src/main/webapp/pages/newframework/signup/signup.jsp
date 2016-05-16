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
    <link href="bootstrap/css/bootstrap-chosen/bootstrap-chosen.css" rel="stylesheet" type="text/css"/>
    <link href="bootstrap/js/bootstrap-tagsinput/dist/bootstrap-tagsinput.css" rel="stylesheet" type="text/css"/>
    <link href="bootstrap/js/ngImgCrop/compile/minified/ng-img-crop.css" rel="stylesheet" type="text/css"/>
    <link href="pages/newframework/static/css/signin.css?v=<%=version%>" rel="stylesheet" type="text/css"/>
    <style>
        .passport-note {
            top: 18px;
            display: none;
            font-size: 13px;
        }

        .form-group {
            position: relative;
        }

        .text-tips .bg-danger, .text-tips .bg-success {
            display: none;
        }
    </style>
</head>
<body style="
    background: #fff !important;
">
<div id="root" class="root" style="background: #fff !important;">
    <div class="bg-black lter b-b wrapper-sm">
        <h2 class="m-n font-thin h4">
            <a href="<%=basePath%>login/main" class="btn btn-sm btn-icon btn-rounded btn-info m-l"><i
                    class="fa fa-reply"></i></a>
            core framework
        </h2>
    </div>
    <div class="wrapper-md ng-scope" ng-controller="FormDemoCtrl">
        <div class="row">
            <div class="col-sm-1"></div>
            <div class="col-sm-3">
                <div class="panel panel-default">
                    <div class="panel-heading font-bold">填写你的基本信息</div>
                    <div class="panel-body">
                        <form role="form" class="ng-pristine ng-valid">
                            <div id="userAccount" class="form-group">
                                <label>用户名</label>
                                <input type="text" class="form-control" placeholder="用户名"
                                       ng-model="user.userName">
                                <div class="passport-note passport-error-text passport-note-hide">
                                    <span>{{error.userNameError}}</span></div>
                            </div>
                            <div class="line line-dashed b-b line-lg pull-in"></div>
                            <div id="userPassword" class="form-group">
                                <label>密码</label>
                                <input type="password" class="form-control" placeholder="密码"
                                       ng-model="user.password">
                                <div class="passport-note passport-error-text passport-note-hide">
                                    <span>{{error.passwordError}}</span></div>
                            </div>
                            <div class="line line-dashed b-b line-lg pull-in"></div>
                            <div class="form-group">
                                <label>确认密码</label>
                                <input type="password" class="form-control" placeholder="确认密码"
                                       ng-model="user.password1">
                                <div class="passport-note passport-error-text passport-note-hide">
                                    <span>{{error.password1Error}}</span></div>
                            </div>
                        </form>
                    </div>
                </div>
                <div class="panel panel-default">
                    <div class="panel-heading font-bold">可选的信息</div>
                    <div class="panel-body" style="height: 428px;">
                        <form role="form" class="ng-pristine ng-valid">
                            <div id="mobilePhone" class="form-group">
                                <label>手机</label>
                                <input type="text" class="form-control" placeholder="手机"
                                       ng-model="user.mobilePhone">
                                <div class="passport-note passport-error-text passport-note-hide">
                                    <span>{{error.mobilePhoneError}}</span></div>
                            </div>
                            <div class="line line-dashed b-b line-lg pull-in"></div>
                            <div id="officePhone" class="form-group">
                                <label>办公电话</label>
                                <input type="text" class="form-control" placeholder="办公电话"
                                       ng-model="user.officePhone">
                                <div class="passport-note passport-error-text passport-note-hide">
                                    <span>{{error.officePhoneError}}</span></div>
                            </div>
                            <div class="line line-dashed b-b line-lg pull-in"></div>
                            <div class="form-group">
                                <label>真实姓名</label>
                                <input type="text" class="form-control" placeholder="真实姓名"
                                       ng-model="user.realName">
                            </div>
                            <div class="line line-dashed b-b line-lg pull-in"></div>
                            <div class="form-group">
                                <input ui-jq="tagsinput" ui-options="" ng-model="user.signature" placeholder="个性标签"
                                       class="form-control"/>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
            <div class="col-sm-7">
                <div class="panel panel-default">
                    <div class="panel-heading font-bold">
                        核实你的邮箱地址
                    </div>
                    <div class="panel-body">
                        <form class="form-inline ng-pristine ng-valid" role="form">
                            <div class="form-group">
                                <label class="sr-only">邮箱地址</label>
                                <input type="email" class="form-control" placeholder="邮箱地址"
                                       ng-model="user.userEmail">
                            </div>
                            <div class="form-group">
                                <label class="sr-only">验证码</label>
                                <input type="text" class="form-control" placeholder="输入收到的验证码">
                            </div>
                            <button class="btn btn-success" ng-click="open('lg')">发送验证码</button>
                            <div class="form-group">
                                <div class="text-tips">
                                    <p class="text bg-danger">{{error.checkCodeError}}</p>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
                <div class="panel panel-default">
                    <div class="panel-heading font-bold">选一个你的所属部门</div>
                    <div class="panel-body">
                        <form class="bs-example form-inline ng-pristine ng-valid">
                            <div id="department" class="form-group">
                                <select ui-jq="chosen" class="w-md" ng-model="user.departmentId">
                                    <option value="" selected>请选择一个部门</option>
                                    <c:forEach items="${departments}" var="department" varStatus="status">
                                        <option value="${department.id}">${department.departname}</option>
                                    </c:forEach>
                                </select>
                            </div>
                            <div class="form-group">
                                <div class="text-tips">
                                    <p class="text bg-danger">{{error.departmentError}}</p>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
                <div class="panel panel-default">
                    <div class="panel-heading font-bold">你来自哪里</div>
                    <div class="panel-body">
                        <form class="bs-example form-inline ng-pristine ng-valid">
                            <div class="form-group">
                                <select ui-jq="chosen" id="province" class="w-md" ng-model="user.province">
                                    <option value="">---所在省份---</option>
                                    <option ng-repeat="province in provinces" value="{{province.id}}">
                                        {{province.name}}
                                    </option>
                                </select>
                            </div>
                            <div class="form-group">
                                <select ui-jq="chosen" id="city" class="w-md" ng-model="user.city">
                                    <option value="">---所在城市---</option>
                                </select>
                            </div>
                        </form>
                    </div>
                </div>
                <div class="panel panel-default">
                    <div class="panel-heading font-bold">设置你的头像信息</div>
                    <div class="panel-body">
                        <div class="bs-example form-horizontal ng-pristine ng-valid">
                            <div class="form-group">
                                <div class="col-sm-12">
                                    <input id="fileInput" ui-jq="filestyle" type="file" data-icon="false"
                                           data-classButton="btn btn-default" <%--uploader="uploader" nv-file-select=""--%>
                                           data-classInput="form-control inline v-middle input-s">
                                </div>
                            </div>
                        </div>
                        <div class="line line-dashed b-b line-lg pull-in"></div>
                        <div class="form-group">
                            <div class="col-sm-8"
                                 style="height:326px;background: rgba(238, 238, 238, 0.42);border: 1px solid #edf1f2;">
                                <img-crop
                                        image="myImage"
                                        result-image="user.signatureFile"
                                        result-image-size="160"
                                        change-on-fly=true

                                        area-type="{{cropType}}"
                                ></img-crop>
                            </div>
                            <div class="col-sm-2 avatar"
                                 style="margin-left:10px;width:160px;height:160px;">
                                <img ng-src="{{user.signatureFile}}"/>
                            </div>
                        </div>
                        <%--<div class="form-group">--%>
                        <%--<div class="col-sm-12">--%>
                        <%--<div class="btn-group m-t">--%>
                        <%--<label class="btn btn-default" ng-model="cropType"--%>
                        <%--btn-radio="'circle'">Circle</label>--%>
                        <%--<label class="btn btn-default" ng-model="cropType"--%>
                        <%--btn-radio="'square'">Square</label>--%>
                        <%--</div>--%>
                        <%--</div>--%>
                        <%--</div>--%>
                    </div>
                </div>
            </div>
            <div class="col-sm-1"></div>
        </div>
        <div class="row">
            <div class="col-sm-9"></div>
            <div class="col-sm-1">
                <div class="btn-group btn-group-justified">
                    <a href="javascript:void(0)" class="btn btn-danger" ng-click="reset()">重置</a>
                </div>
            </div>
            <div class="col-sm-1">
                <div class="btn-group btn-group-justified">
                    <a href="javascript:void(0)" class="btn btn-info" ng-click="submit()">提交</a>
                </div>
            </div>
            <div class="col-sm-1"></div>
        </div>
    </div>
</div>
<%@include file="/context/footer.jsp" %>
<script type="text/javascript" src="pages/newframework/static/js/SignupBoot.js?v=<%=version%>"></script>
</body>
</html>