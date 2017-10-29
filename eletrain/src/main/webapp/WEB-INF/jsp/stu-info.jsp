<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>学员个人信息</title>
<meta name="description" content="学员个人信息">
<meta name="keywords" content="index">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="renderer" content="webkit">
<meta http-equiv="Cache-Control" content="no-siteapp" />
<link rel="icon" type="image/png"
	href="${pageContext.request.contextPath}/assets/i/favicon.png">
<link rel="apple-touch-icon-precomposed"
	href="${pageContext.request.contextPath}/assets/i/app-icon72x72@2x.png">
<meta name="apple-mobile-web-app-title" content="Amaze UI" />
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/assets/css/amazeui.min.css" />
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/assets/css/admin.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/assets/css/app.css">
	 <script src="${pageContext.request.contextPath}/assets/js/jquery.min.js"></script>
    <%-- <script src="${pageContext.request.contextPath}/assets/js/amazeui.min.js"></script> --%>
<%--     <script src="${pageContext.request.contextPath}/assets/js/app.js"></script> --%>
</head>
<body data-type="generalComponents">
	<jsp:include page="head.jsp"></jsp:include>
	<jsp:include page="side.jsp"></jsp:include>
		<div class="tpl-content-wrapper">
			<div class="tpl-portlet-components">
				<div class="portlet-title">
					<div class="caption font-green bold">
						<span class="am-icon-code"></span> 学员个人信息
					</div>
				</div>
				<div class="tpl-block ">
					<div class="am-g tpl-amazeui-form">
						<div class="am-u-sm-12 am-u-md-9">
							<form class="am-form am-form-horizontal"
								action="${pageContext.request.contextPath }/student/addStu"
								method="post" onsubmit="return checkForm();">
								<div class="am-form-group">
									<label for="user-name" class="am-u-sm-3 am-form-label">姓名
										</label>
									<div class="am-u-sm-9">${stuInfo.username }</div>
								</div>
								<div class="am-form-group">
									<label for="user-email" class="am-u-sm-3 am-form-label">性别
										</label>
									<div class="am-u-sm-9">${stuInfo.email }</div>
								</div>

								<div class="am-form-group">
									<label for="user-phone" class="am-u-sm-3 am-form-label">电话
										</label>
									<div class="am-u-sm-9">${stuInfo.telephone }</div>
								</div>

								<div class="am-form-group">
									<label for="user-name" class="am-u-sm-3 am-form-label">学历</label>
									<div class="am-u-sm-9">${stuInfo.education }</div>
								</div>
								<div class="am-form-group">
									<label for="user-name" class="am-u-sm-3 am-form-label">身份证号</label>
									<div class="am-u-sm-9">${stuInfo.idcard }</div>
								</div>

								<div class="am-form-group">
									<label for="user-name" class="am-u-sm-3 am-form-label">单位</label>
									<div class="am-u-sm-9">${stuInfo.address }</div>
								</div>
								<div class="am-form-group">
									<label for="user-name" class="am-u-sm-3 am-form-label">创建时间</label>
									<div class="am-u-sm-9">
										<fmt:formatDate value="${stuInfo.createtime }" type="both" />

									</div>
								</div>
								<div class="am-form-group">
									<label for="user-name" class="am-u-sm-3 am-form-label">更新时间</label>
									<div class="am-u-sm-9">
										<fmt:formatDate value="${stuInfo.updatetime }" type="both" />
									</div>
								</div>
								<c:forEach items="${classScoreInfo }" var="classScore">
								<div class="am-form-group">
									<label for="user-name" class="am-u-sm-3 am-form-label">培训班</label>
									<div class="am-u-sm-9">培训班名称：${classScore.className }<c:if test="${classScore.sign ==0}">（未结束）</c:if><c:if test="${classScore.sign ==1}">（已结束）</c:if><br>培训班编号：${classScore.classNo }
									</div>
								</div>
								<div class="am-form-group">
									<label for="user-name" class="am-u-sm-3 am-form-label">成绩</label>
									<div class="am-u-sm-9">理论成绩：${classScore.theoryscore }分</div>
									<div class="am-u-sm-9">实践成绩：${classScore.practicescore }分</div>
									<div class="am-u-sm-9">总成绩：${classScore.total }分&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;证书编号：${classScore.certificateNo }</div>
								</div>
								</c:forEach>
								<div class="am-form-group">
									<label for="user-name" class="am-u-sm-3 am-form-label">是否毕业</label>
									<c:if test="${stuInfo.isfinish==0}">
									<div class="am-u-sm-9">未毕业</div>
									</c:if>
									<c:if test="${stuInfo.isfinish==1}">
									<div class="am-u-sm-9">已毕业(如果显示多个培训班，请查看最新此学生信息)</div>
									</c:if>
								</div>
								<div id="image">
									<img height="108px" width="91px" src="/${stuInfo.picpath } "/>
								</div>
								<div class="am-form-group">
									<div class="am-u-sm-9 am-u-sm-push-3">
										<button type="button" class="am-btn am-btn-primary"
											onclick="javascript:history.back(-1);">返回</button>
									</div>
								</div>
							</form>
						</div>
					</div>
				</div>
			</div>
		</div>
</body>
<script type="text/javascript">
$(function(){
$("a[menuId='${menuId}']").trigger("click");
})
</script>
</html>