<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport"
	content="width=device-width, initial-scale=1, maximum-scale=1" />
<%@include file="/context/mytags.jsp"%>
<link rel="shortcut icon" href="resources/fc/images/icon/favicon.ico">
<link href="pages/newframework/static/css/app.css?v=<%=version%>"
	rel="stylesheet" type="text/css" />
<link href="pages/html/css/web.im.css?v=<%=version%>" rel="stylesheet"
	type="text/css" />
<link href="pages/html/css/index.css?v=<%=version%>" rel="stylesheet"
	type="text/css" />
</head>
<body ng-controller="AppCtrl"
	style="overflow-y: auto !important; overflow-x: auto !important;">
	<div>没有权限</div>
	<%@include file="/context/footer.jsp"%>
	<script type="text/javascript"
		src="pages/newframework/static/js/IndexBoot.js?v=<%=version%>"></script>
</body>
</html>