<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!doctype html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>公告详情</title>
    <meta name="description" content="公告详情">
    <meta name="keywords" content="index">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="renderer" content="webkit">
    <meta http-equiv="Cache-Control" content="no-siteapp" />
    <script src="${pageContext.request.contextPath}/assets/js/jquery.min.js"></script>
    <script src="${pageContext.request.contextPath}/assets/js/amazeui.min.js"></script>
    <%-- <script src="${pageContext.request.contextPath}/assets/js/app.js"></script> --%>
    <link rel="icon" type="image/png" href="${pageContext.request.contextPath}/assets/i/favicon.png">
    <link rel="apple-touch-icon-precomposed" href="${pageContext.request.contextPath}/assets/i/app-icon72x72@2x.png">
    <meta name="apple-mobile-web-app-title" content="Amaze UI" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/amazeui.min.css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/admin.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/app.css">
</head>
<body data-type="generalComponents">
		<jsp:include page="head.jsp"></jsp:include>
		<jsp:include page="side.jsp"></jsp:include>
        <div class="tpl-content-wrapper">
            <div class="tpl-portlet-components">
                <div class="portlet-title">
                    <div class="caption font-green bold">
                        <span class="am-icon-code">公告详情</span>
                    </div>
                </div>
                <div class="tpl-block">
					<div class="am-g">
                        <div class="am-u-sm-12 am-u-md-9">
                        	<div class="am-form-group">
	                        	<div class="am-u-md-9" style="float:right">
	                        		<span style="font-weight:bold">发布人 : </span>
	                        		<span class="am-kai">${notice.admin.username}</span>
	                        		<hr/>
	                        	</div>
	                        	
	                        	<div class="am-u-md-9" style="float:right">
	                        		<span style="font-weight:bold">发布时间 : </span>
	                        		<span class="am-kai"><fmt:formatDate value="${notice.createtime}" pattern="yyyy-MM-dd HH:mm:ss"/></span>
	                        		<hr/>
	                        	</div>
	                        	
	                        	<div class="am-u-md-9" style="float:right">
	                        		<span style="font-weight:bold">公告标题  : </span>
	                        		<span class="am-kai">${notice.title}</span>
	                        		<hr/>
	                        	</div>
	                        	
	                        	<div class="am-u-md-9">
		                        	<span style="font-weight:bold">公告内容 : </span>
		                        	<p style="text-indent:50px" class="am-kai">${notice.content}</p>
		                        	<hr/>
	                        	</div>
                        	</div>
                        </div>
                        <div class="am-u-sm-9">
                            <button type="button" onclick='return window.history.back();' class="am-btn am-btn-primary">
                            	返回
                            </button>
                        </div>
                    </div>
                </div>
                <div class="tpl-alert"></div>
            </div>
        </div>
</body>
<script type="text/javascript">
	$(function() {
		$("a[menuId='${menuId}']").trigger("click");
	})
</script>
</html>