<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="fm"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!doctype html>
<html>

<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>教师详情</title>
    <meta name="description" content="这是一个 index 页面">
    <meta name="keywords" content="index">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="renderer" content="webkit">
    <meta http-equiv="Cache-Control" content="no-siteapp" />
    <link rel="icon" type="image/png" href="${pageContext.request.contextPath}/assets/i/favicon.png">
    <link rel="apple-touch-icon-precomposed" href="${pageContext.request.contextPath}/assets/i/app-icon72x72@2x.png">
    <meta name="apple-mobile-web-app-title" content="Amaze UI" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/amazeui.min.css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/admin.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/app.css">
    <script src="${pageContext.request.contextPath}/assets/js/jquery.min.js"></script>
    <script src="${pageContext.request.contextPath}/assets/js/amazeui.min.js"></script>
    <%-- <script src="${pageContext.request.contextPath}/assets/js/app.js"></script> --%>
</head>
<body data-type="generalComponents">
<jsp:include page="head.jsp"></jsp:include>
	<jsp:include page="side.jsp"></jsp:include>
	<div>
        <div class="tpl-content-wrapper">
            <div class="tpl-portlet-components">
                <div class="portlet-title">
                    <div class="caption font-green bold">
                         <span class="am-icon-code"></span> 教师详细信息：
                    </div>
                </div>
                <div class="tpl-block ">
                    <div class="am-g tpl-amazeui-form">
                        <div class="am-u-sm-12 am-u-md-9">
                            <form method="post" class="am-form am-form-horizontal" action="${pageContext.request.contextPath }/teacher/list">
                            	<input type="hidden" name="teacherId" value="${teacher.teacherId }">
                                <div class="am-form-group">
                                    <label for="user-name" class="am-u-sm-3 am-form-label">姓名</label>
                                    <div class="am-u-sm-9">
                                    	<input name="username" value="${teacher.username }" type="text" id="user-name" readonly placeholder="没有设置名字">
                                    </div>
                                </div>
                                <div class="am-form-group">
                                    <label for="user-name" class="am-u-sm-3 am-form-label">工号</label>
                                    <div class="am-u-sm-9">
                                    	<input name="username" value="${teacher.loginname }" type="text" id="user-name" readonly placeholder="没有设置工号">
                                    </div>
                                </div>
                                <div class="am-form-group">
                                    <label for="user-name" class="am-u-sm-3 am-form-label">工作状态</label>
                                    <div class="am-u-sm-9">
                                    	<input name="username" value='<c:if test="${teacher.isfinish==0 }">在职</c:if><c:if test="${teacher.isfinish==1 }">离职</c:if>' type="text" id="user-name" readonly placeholder="没有设置工号">
                                    </div>
                                </div>
                                <div class="am-form-group">
                                    <label for="user-name" class="am-u-sm-3 am-form-label">职称</label>
                                    <div class="am-u-sm-9">
                                    	<input name="username" value="${teacher.position }" type="text" id="user-name" readonly placeholder="没有设置职称">
                                    </div>
                                </div>

                                <div class="am-form-group">
                                    <label for="user-name" class="am-u-sm-3 am-form-label">教师类别</label>
                                    <div class="am-u-sm-9">
                                    	<input name="username" value='<c:if test="${teacher.identify==2 }">授课教师</c:if><c:if test="${teacher.identify==6 }">班主任</c:if>' type="text" id="user-name" readonly placeholder="没有设置教师类别">
                                    </div>
                                </div>

                                <div class="am-form-group">
                                    <label for="user-email" class="am-u-sm-3 am-form-label">性别</label>
                                    <div class="am-u-sm-9">
                                    	<input name="username" value="<c:if test="${teacher.email==0 }">女</c:if><c:if test="${teacher.email==1 }">男</c:if>" type="text" id="user-name" readonly placeholder="没有设置性别">
                                    </div>
                                </div>
                                <div class="am-form-group">
                                    <label for="user-phone" class="am-u-sm-3 am-form-label">电话</label>
                                    <div class="am-u-sm-9">
                                    	<input name="username" value="${teacher.telephone }" type="text" id="user-name" readonly placeholder="没有设置电话">
                                    </div>
                                </div>
                                
                                <div class="am-form-group">
                                    <label for="user-address" class="am-u-sm-3 am-form-label">地址</label>
                                    <div class="am-u-sm-9">
                                    	<input name="username" value="${teacher.address }" type="text" id="user-name" readonly placeholder="没有设置地址">
                                    </div>
                                </div>

                                <div class="am-form-group">
                                    <label for="user-edu" class="am-u-sm-3 am-form-label">学历</label>
                                    <div class="am-u-sm-9">
                                    	<input name="username" value="${teacher.education }" type="text" id="user-name" readonly placeholder="没有设置学历">
                                    </div>
                                </div>
                                
                                <div class="am-form-group">
                                    <label for="user-edu" class="am-u-sm-3 am-form-label">身份证号</label>
                                    <div class="am-u-sm-9">
                                    	<input name="username" value="${teacher.idcard }" type="text" id="user-name" readonly placeholder="没有设置身份证号">
                                    </div>
                                </div>
                                
                                <div class="am-form-group">
                                    <label for="user-edu" class="am-u-sm-3 am-form-label">创建时间</label>
                                    <div class="am-u-sm-9">
                                    	<input name="username" value="<fmt:formatDate value="${teacher.createtime }"  type="both" pattern="yyyy-MM-dd HH:mm:ss"/>" type="text" id="user-name" readonly placeholder="没有设置创建时间">
                                    </div>
                                </div>
                                
                                <div class="am-form-group">
                                    <label for="user-edu" class="am-u-sm-3 am-form-label">更新时间</label>
                                    <div class="am-u-sm-9">
                                    	<input name="username" value="<fmt:formatDate value="${teacher.updatetime }"  type="both" pattern="yyyy-MM-dd HH:mm:ss"/>" type="text" id="user-name" readonly placeholder="没有更新过教师">
                                    </div>
                                </div>
                                <div class="am-form-group">
                                    <div class="am-u-sm-9 am-u-sm-push-3">
                                    	<input style="background:#0e90d2;cursor:hand" class="am-btn am-btn-primary" value="返回" type="text" id="user-name" onclick="javascript:history.back(-1);">
                                    </div>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>

            </div>

        </div>

    </div>
</body>
<script type="text/javascript">
	$(function() {
		$("a[menuId='${menuId}']").trigger("click");
	})
</script>
</html>