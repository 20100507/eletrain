<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>方案详情</title>
<meta name="description" content="方案详情">
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
    <script src="${pageContext.request.contextPath}/assets/js/amazeui.min.js"></script>
</head>
<body data-type="generalComponents">
<jsp:include page="head.jsp"></jsp:include>
	<jsp:include page="side.jsp"></jsp:include>
		<div class="tpl-content-wrapper">
			<div class="tpl-portlet-components">
				<div class="portlet-title">
					<div class="caption font-green bold">
						<span class="am-icon-code"></span> 方案详情
					</div>
				</div>
				<div class="tpl-block ">
					<div class="am-g tpl-amazeui-form">
						<div class="am-u-sm-12 am-u-md-9">
							<form class="am-form am-form-horizontal"
								action="${pageContext.request.contextPath }/student/addStu"
								method="post" onsubmit="return checkForm();">
								<div class="am-form-group">
									<label for="user-name" class="am-u-sm-3 am-form-label">方案名称</label>
									<div class="am-u-sm-9">${planInfo.pname }</div>
								</div>
								<div class="am-form-group">
									<label for="user-name" class="am-u-sm-3 am-form-label">方案负责人
										</label>
									<div class="am-u-sm-9">${planInfo.adminName }</div>
								</div>
								<div class="am-form-group">
									<label for="user-name" class="am-u-sm-3 am-form-label">创建时间</label>
									<div class="am-u-sm-9"><fmt:formatDate value="${planInfo.createtime }" type="both" /></div>
								</div>
								<div class="am-form-group">
									<label for="user-name" class="am-u-sm-3 am-form-label">更新时间</label>
									<div class="am-u-sm-9"><fmt:formatDate value="${planInfo.createtime }" type="both" /></div>
								</div>
								<div class="am-form-group">
									<label for="user-name" class="am-u-sm-3 am-form-label">是否通过</label>
									<c:if test="${planInfo.sign == 0}">
									<div class="am-u-sm-9">未审批</div>
									</c:if>
									<c:if test="${planInfo.sign == 1}">
									<div class="am-u-sm-9">审批中</div>
									</c:if>
									<c:if test="${planInfo.sign == 2}">
									<div class="am-u-sm-9">未通过</div>
									</c:if>
									<c:if test="${planInfo.sign == 3}">
									<div class="am-u-sm-9">通过</div>
									</c:if>
								</div>
								<div class="am-form-group">
									<label for="user-name" class="am-u-sm-3 am-form-label">培训目标</label>
									<div class="am-u-sm-9">${planInfo.planAim }</div>
								</div>
								<div class="am-form-group">
									<label for="user-name" class="am-u-sm-3 am-form-label">能力要求</label>
									<div class="am-u-sm-9">${planInfo.ability }</div>
								</div>
								<div class="am-form-group">
									<label for="user-name" class="am-u-sm-3 am-form-label">培训规模</label>
									<div class="am-u-sm-9">${planInfo.scale }</div>
								</div>
								<div class="am-form-group">
									<label for="user-name" class="am-u-sm-3 am-form-label">适用班级 </label>
									<div class="am-u-sm-9">
									<c:forEach varStatus="stat" items="${planInfo.classesNames }" var="cname">
									${cname }<c:if test="${!stat.last}">，</c:if>
									</c:forEach>
									</div>
								</div>
								<div class="am-form-group">
									<label for="user-name" class="am-u-sm-3 am-form-label">配备讲师 </label>
									<div class="am-u-sm-9">
									<c:forEach varStatus="stat" items="${planInfo.teacherNames }" var="tname">
									${tname }<c:if test="${!stat.last}">，</c:if>
									</c:forEach>
									</div>
								</div>
								<div class="am-form-group">
									<label for="user-name" class="am-u-sm-3 am-form-label">培训方式</label>
									<div class="am-u-sm-9">${planInfo.planPattern }</div>
								</div>
								<div class="am-form-group">
									<label for="user-name" class="am-u-sm-3 am-form-label">考核方式</label>
									<div class="am-u-sm-9">${planInfo.examPattern }</div>
								</div>
								<div class="am-form-group">
									<label for="user-name" class="am-u-sm-3 am-form-label">设备 </label>
									<div class="am-u-sm-9">
									<c:forEach varStatus="stat" items="${planInfo.devNames }" var="dnames">
									${dnames }<c:if test="${!stat.last}">，</c:if>
									</c:forEach>
									</div>
								</div>
								<div class="am-form-group">
									<label for="user-name" class="am-u-sm-3 am-form-label">教材 </label>
									<div class="am-u-sm-9">
									<c:forEach varStatus="stat" items="${planInfo.bookNames }" var="bnames">
									${bnames }<c:if test="${!stat.last}">，</c:if>
									</c:forEach>
									</div>
								</div>
								<div class="am-form-group">
									<label for="user-name" class="am-u-sm-3 am-form-label">培训内容</label>
									<div class="am-u-sm-9">${planInfo.planContent }</div>
								</div>
								<div class="am-form-group">
									<label for="user-name" class="am-u-sm-3 am-form-label">培训要求</label>
									<div class="am-u-sm-9">${planInfo.planRequirement }</div>
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