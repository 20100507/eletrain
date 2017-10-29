<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!doctype html>
<html>

<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>增加或编辑课表</title>
<meta name="description" content="这是一个 index 页面">
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

	<script
		src="${pageContext.request.contextPath}/assets/js/jquery.min.js"></script>
	<script
		src="${pageContext.request.contextPath}/assets/js/amazeui.min.js"></script>
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
					<span class="am-icon-code"></span> 表单
				</div>
			</div>
			<div class="tpl-block ">
				<div class="am-g tpl-amazeui-form">
					<div class="am-u-sm-12 am-u-md-9">
						<form id="doc-vld-msg-1" method="post" class="am-form am-form-horizontal"
							action="${pageContext.request.contextPath }/syllabus/${ syllabus== null ? 'add' : 'update' }SyllabusByClassesTeacher?menuId=52">
							<input type="hidden" name="syllabusId"
								value="${syllabus.syllabusId }">
							<div class="am-form-group">
								<label for="user-name1" class="am-u-sm-3 am-form-label">上午课</label>
								<div class="am-u-sm-9">
									<select name="amfirst" required>
										<option value="">===================请选择课程==================</option>										
										<c:forEach items="${courseList }" var="course">
											<option
												<c:if test="${course.courseId==syllabus.amfirst }">selected</c:if>
												value="${course.courseId }">${course.cname }</option>
										</c:forEach>
									</select>
								</div>
							</div>
							<div class="am-form-group">
								<label for="user-name2" class="am-u-sm-3 am-form-label">下午课</label>
								<div class="am-u-sm-9">
									<select name="pmfirst" required>
										<option value="">===================请选择课程==================</option>
										<c:forEach items="${courseList }" var="course">
											<option
												<c:if test="${course.courseId==syllabus.pmfirst }">selected</c:if>
												value="${course.courseId }">${course.cname }</option>
										</c:forEach>
									</select>
								</div>
							</div>

							<div class="am-form-group">
								<label for="user-name3" class="am-u-sm-3 am-form-label">晚上课</label>
								<div class="am-u-sm-9">
									<select name="night" required>
										<option value="">===================请选择课程==================</option>
										<c:forEach items="${courseList }" var="course">
											<option
												<c:if test="${course.courseId==syllabus.night }">selected</c:if>
												value="${course.courseId }">${course.cname }</option>
										</c:forEach>
									</select>
								</div>
							</div>

							<div class="am-form-group">
								<label for="user-name4" class="am-u-sm-3 am-form-label">班级</label>
								<div class="am-u-sm-9">
									<select name="classId" required>
										<option value="">===================请选择班级==================</option>
										<c:forEach items="${classesList }" var="classes">
											<option
												<c:if test="${classes.classesId==syllabus.classId }">selected</c:if>
												value="${classes.classesId }">${classes.cname }</option>
										</c:forEach>
									</select>
								</div>
							</div>

							<div class="am-form-group">
								<label for="user-phone" class="am-u-sm-3 am-form-label">日期</label>
								<div class="am-u-sm-9">
									<input required name="week" value="${syllabus.week }" type="text"
										id="user-phone" placeholder="===========================请选择日期========================="
										data-am-datepicker>
								</div>
							</div>
							<div class="am-form-group">
								<div class="am-u-sm-9 am-u-sm-push-3">
									<button type="submit" class="am-btn am-btn-primary">保存修改</button>
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

	</div>

</body>
<script type="text/javascript">
	$(function() {
		$("a[menuId='${menuId}']").trigger("click");
	})
</script>
</html>