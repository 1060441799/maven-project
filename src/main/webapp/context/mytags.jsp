<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt" %>
<%@page import="com.newframe.core.pojo.pojoimpl.impl.*" %>
<%@page import="java.util.*" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
    String version = System.currentTimeMillis() + "";
%>
<c:set var="webRoot" value="<%=basePath%>"/>
<base href="<%=basePath%>"/>
<title data-version="${APPVERSION}">${APPNAME}</title>
<link href="bootstrap/css/bootstrap.css?v=<%=version%>" rel="stylesheet" type="text/css"/>
<link href="bootstrap/css/animate.css/animate.css?v=<%=version%>" rel="stylesheet" type="text/css"/>
<link href="bootstrap/css/font-awesome/css/font-awesome.css?v=<%=version%>" rel="stylesheet" type="text/css"/>
<link href="bootstrap/css/simple-line-icons/css/simple-line-icons.css?v=<%=version%>" rel="stylesheet" type="text/css"/>
<link href="bootstrap/css/font.css?v=<%=version%>" rel="stylesheet" type="text/css"/>
<link href="bootstrap/js/angularjs-toaster/toaster.css?v=<%=version%>" rel="stylesheet" type="text/css"/>
<style>
    .root {
        display: none;
    }
</style>