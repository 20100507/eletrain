<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="fm"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<!doctype html>
<html>

<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>班级详情</title>
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
<script src="${pageContext.request.contextPath}/assets/js/jquery.min.js"></script>
<script
	src="${pageContext.request.contextPath}/assets/js/amazeui.min.js"></script>
<%--  <script src="${pageContext.request.contextPath}/assets/js/app.js"></script> --%>

</head>

<body data-type="generalComponents">
	<jsp:include page="head.jsp"></jsp:include>
	<jsp:include page="side.jsp"></jsp:include>
	<div>
		<div class="tpl-content-wrapper">
			<div class="tpl-portlet-components">
				<div class="portlet-title">
					<div class="caption font-green bold">
						<button type="button" class="am-btn am-btn-default am-btn-primary"
							onclick="javascript:history.back(-1);">
							<span class="am-icon-plus"></span> 返回
						</button>
					</div>
					<div class="tpl-portlet-input tpl-fz-ml">
						<button type="button" class="am-btn am-btn-success"
							data-am-modal="{target: '#doc-modal-1', closeViaDimmer: 0, width: 540, height: 225}">
							<span class="am-icon-plus"></span>导出教师
						</button>
						<div class="am-modal am-modal-no-btn" tabindex="-1"
							id="doc-modal-1">
							<div class="am-modal-dialog">
								<div class="am-modal-hd">
									教师信息表(两种Excel格式) <a href="javascript: void(0)"
										class="am-close am-close-spin" data-am-modal-close>&times;</a>
								</div>
								<div class="am-modal-bd">
									<div class="am-modal-actions-group">
										<ul class="am-list">
											<li class="am-modal-actions-header"><a
												href="${pageContext.request.contextPath }/classes/exportTeacher?mould=1&classesId=${classes.classesId}">教师信息表.xlsx</a>
											</li>
											<li class="am-modal-actions-header"><a
												href="${pageContext.request.contextPath }/classes/exportTeacher?mould=2&classesId=${classes.classesId}">教师信息表.xls</a>
											</li>
										</ul>
									</div>
								</div>
							</div>
						</div>
						<button type="button" class="am-btn am-btn-success"
							data-am-modal="{target: '#doc-modal-2', closeViaDimmer: 0, width: 540, height: 225}">
							<span class="am-icon-plus"></span>导出学生
						</button>
						<div class="am-modal am-modal-no-btn" tabindex="-1"
							id="doc-modal-2">
							<div class="am-modal-dialog">
								<div class="am-modal-hd">
									学生信息表(两种Excel格式) <a href="javascript: void(0)"
										class="am-close am-close-spin" data-am-modal-close>&times;</a>
								</div>
								<div class="am-modal-bd">
									<div class="am-modal-actions-group">
										<ul class="am-list">
											<li class="am-modal-actions-header"><a
												href="${pageContext.request.contextPath }/classes/exportStudent?mould=1&classesId=${classes.classesId}">学生信息表.xlsx</a>
											</li>
											<li class="am-modal-actions-header"><a
												href="${pageContext.request.contextPath }/classes/exportStudent?mould=2&classesId=${classes.classesId}">学生信息表.xls</a>
											</li>
										</ul>
									</div>
								</div>
							</div>
						</div>
						<button type="button" class="am-btn am-btn-default am-btn-success"
							onclick="location='${pageContext.request.contextPath}/classes/exportClassesSyllabus?classesId=${classes.classesId}'">
							<span class="am-icon-plus"></span> 导出课表
						</button>
						<%-- <button type="button" class="am-btn am-btn-default am-btn-success"
							onclick="location='${pageContext.request.contextPath}/classes/exportCheAttance?classesId=${classes.classesId}'">
							<span class="am-icon-plus"></span> 导出考勤表
						</button> --%>
						<button type="button" class="am-btn am-btn-success"
							data-am-modal="{target: '#doc-modal-4', closeViaDimmer: 0, width: 540, height: 225}">
							<span class="am-icon-plus"></span>导出考勤表
						</button>
						<div class="am-modal am-modal-no-btn" tabindex="-1"
							id="doc-modal-4">
							<div class="am-modal-dialog">
								<div class="am-modal-hd">
									考勤表(两种Excel格式) <a href="javascript: void(0)"
										class="am-close am-close-spin" data-am-modal-close>&times;</a>
								</div>
								<div class="am-modal-bd">
									<div class="am-modal-actions-group">
										<ul class="am-list">
											<li class="am-modal-actions-header"><a
												href="${pageContext.request.contextPath }/classes/exportCheAttance?mould=1&classesId=${classes.classesId}">考勤表.xlsx</a>
											</li>
											<li class="am-modal-actions-header"><a
												href="${pageContext.request.contextPath }/classes/exportCheAttance?mould=2&classesId=${classes.classesId}">考勤表.xls</a>
											</li>
										</ul>
									</div>
								</div>
							</div>
						</div>
					</div>


				</div>
				<div class="tpl-block ">

					<div class="am-g tpl-amazeui-form">


						<!-- <div class="am-u-sm-12 am-u-md-9"> -->
						<div class="am-panel-group" id="accordion">

							<div class="am-panel am-panel-default">
								<div class="am-panel-hd">
									<h4 class="am-panel-title"
										data-am-collapse="{parent: '#accordion', target: '#do-not-say-1'}">
										班级信息</h4>
								</div>
								<div id="do-not-say-1"
									class="am-panel-collapse am-collapse am-in">
									<div class="am-panel-bd">
										<div>
											<span>班级名称:</span><span>&nbsp;&nbsp;&nbsp;&nbsp;</span><span>${classes.cname }</span>
										</div>
										<div>
											<span>班级状态:</span><span>&nbsp;&nbsp;&nbsp;&nbsp;</span><span><c:if
													test="${classes.capacity==0 }">正常</c:if>
												<c:if test="${classes.capacity==1 }">已结课</c:if></span>
										</div>
										<div>
											<span>班主任:</span><span>&nbsp;&nbsp;&nbsp;&nbsp;</span><span>${classesTeacher }</span>
										</div>
										<div>
											<span>班级实际人数:</span><span>&nbsp;&nbsp;&nbsp;&nbsp;</span><span>${count }</span>
										</div>
										<div>
											<span>班级报到时间:</span><span>&nbsp;&nbsp;&nbsp;&nbsp;</span><span>${classes.createtime }</span>
										</div>
										<div>
											<span>班级授课时间:</span><span>&nbsp;&nbsp;&nbsp;&nbsp;</span><span>${classes.starttime }</span>
										</div>
									</div>
								</div>
							</div>
							<div class="am-panel am-panel-default">
								<div class="am-panel-hd">
									<h4 class="am-panel-title"
										data-am-collapse="{parent: '#accordion', target: '#do-not-say-5'}">
										课表信息</h4>
								</div>
								<div id="do-not-say-5"
									class="am-panel-collapse am-collapse am-collapse">
									<div class="am-panel-bd">
										<table border="1">
											<thead>${classes.cname }课程表:
											</thead>
											<tbody>
												<tr height="50px">
													<th
														style="width: 100px; text-align: center; vertical-align: middle">日期</th>
													<th
														style="width: 100px; text-align: center; vertical-align: middle">授课时间</th>
													<th
														style="width: 300px; text-align: center; vertical-align: middle">授课内容</th>
													<th
														style="width: 100px; text-align: center; vertical-align: middle">学时</th>
													<th
														style="width: 100px; text-align: center; vertical-align: middle">地点</th>
													<th
														style="width: 100px; text-align: center; vertical-align: middle">授课教师</th>
													<th
														style="width: 100px; text-align: center; vertical-align: middle">备注</th>
												</tr>

												<c:forEach items="${cdvList }" var="cdv">
													<tr>
														<td rowspan="4">${cdv.date }</td>
														<td colspan="6"></td>
													</tr>
													<tr>
														<td
															style="width: 100px; text-align: center; vertical-align: middle">08.30-11.30</td>
														<td
															style="width: 350px; text-align: center; vertical-align: middle"><c:if test="${cdv.amCourse!=null }">${cdv.amCourse.cname }</c:if><c:if test="${cdv.amCourse==null }">*无课*</c:if></td>
														<td
															style="width: 100px; text-align: center; vertical-align: middle"><c:if test="${cdv.amCourse!=null }">3</c:if><c:if test="${cdv.amCourse==null }">*</c:if></td>
														<td
															style="width: 100px; text-align: center; vertical-align: middle"><c:if test="${cdv.amCourse!=null }">${cdv.amCourse.place }</c:if><c:if test="${cdv.amCourse==null }">*</c:if></td>
														<td
															style="width: 100px; text-align: center; vertical-align: middle"><c:if test="${cdv.amCourse!=null }">${cdv.amTeacher.username }</c:if><c:if test="${cdv.amCourse==null }">*</c:if></td>
														<td
															style="width: 100px; text-align: center; vertical-align: middle"><c:if test="${cdv.amCourse!=null }">${cdv.amCourse.info }</c:if><c:if test="${cdv.amCourse==null }">*</c:if></td>
													</tr>
													<tr>
														<td
															style="width: 100px; text-align: center; vertical-align: middle">13.30-16.30</td>
														<td
															style="width: 350px; text-align: center; vertical-align: middle"><c:if test="${cdv.pmCourse!=null }">${cdv.pmCourse.cname }</c:if><c:if test="${cdv.pmCourse==null }">*无课*</c:if></td>
														<td
															style="width: 100px; text-align: center; vertical-align: middle"><c:if test="${cdv.pmCourse!=null }">3</c:if><c:if test="${cdv.pmCourse==null }">*</c:if></td>
														<td
															style="width: 100px; text-align: center; vertical-align: middle"><c:if test="${cdv.pmCourse!=null }">${cdv.pmCourse.place }</c:if><c:if test="${cdv.pmCourse==null }">*</c:if></td>
														<td
															style="width: 100px; text-align: center; vertical-align: middle"><c:if test="${cdv.pmCourse!=null }">${cdv.pmTeacher.username }</c:if><c:if test="${cdv.pmCourse==null }">*</c:if></td>
														<td
															style="width: 100px; text-align: center; vertical-align: middle"><c:if test="${cdv.pmCourse!=null }">${cdv.pmCourse.info }</c:if><c:if test="${cdv.pmCourse==null }">*</c:if></td>
													</tr>
													<tr>
														<td
															style="width: 100px; text-align: center; vertical-align: middle">17.30-20.30</td>
														<td
															style="width: 350px; text-align: center; vertical-align: middle"><c:if test="${cdv.niCourse!=null }">${cdv.niCourse.cname }</c:if><c:if test="${cdv.niCourse==null }">*无课*</c:if></td>
														<td
															style="width: 100px; text-align: center; vertical-align: middle"><c:if test="${cdv.niCourse!=null }">3</c:if><c:if test="${cdv.niCourse==null }">*</c:if></td>
														<td
															style="width: 100px; text-align: center; vertical-align: middle"><c:if test="${cdv.niCourse!=null }">${cdv.niCourse.place }</c:if><c:if test="${cdv.niCourse==null }">*</c:if></td>
														<td
															style="width: 100px; text-align: center; vertical-align: middle"><c:if test="${cdv.niCourse!=null }">${cdv.niTeacher.username }</c:if><c:if test="${cdv.niCourse==null }">*</c:if></td>
														<td
															style="width: 100px; text-align: center; vertical-align: middle"><c:if test="${cdv.niCourse!=null }">${cdv.niCourse.info }</c:if><c:if test="${cdv.niCourse==null }">*</c:if></td>
													</tr>
													</td>
													</tr>
												</c:forEach>
											</tbody>
										</table>
									</div>
								</div>
							</div>
							<div class="am-panel am-panel-default">
								<div class="am-panel-hd">
									<h4 class="am-panel-title"
										data-am-collapse="{parent: '#accordion', target: '#do-not-say-2'}">
										教师信息</h4>
								</div>
								<div id="do-not-say-2" class="am-panel-collapse am-collapse">
									<div class="am-panel-bd">
										<table border="1">
											<thead>
												<tr>
													<th
														style="width: 100px; text-align: center; vertical-align: middle">姓名</th>
													<th
														style="width: 50px; text-align: center; vertical-align: middle">性别</th>
													<th
														style="width: 50px; text-align: center; vertical-align: middle">职称</th>
													<th
														style="width: 50px; text-align: center; vertical-align: middle">教师类别</th>
													<th
														style="width: 100px; text-align: center; vertical-align: middle">电话</th>
													<th
														style="width: 350px; text-align: center; vertical-align: middle">工作单位</th>
													<th
														style="width: 100px; text-align: center; vertical-align: middle">学历</th>
													<th
														style="width: 200px; text-align: center; vertical-align: middle">身份证号</th>
												</tr>
											</thead>
											<tbody>
												<c:forEach items="${teacherList }" var="teacher">
													<tr>
														<td
															style="width: 100px; text-align: center; vertical-align: middle">${teacher.username }(${teacher.loginname })</td>
														<td
															style="width: 50px; text-align: center; vertical-align: middle">
															<c:if
																test="${teacher.email==0 }">女</c:if>
															<c:if test="${teacher.email==1 }">男</c:if></td>
														<td
															style="width: 50px; text-align: center; vertical-align: middle">${teacher.position }</td>
														<td
															style="width: 50px; text-align: center; vertical-align: middle"><c:if
																test="${teacher.identify==2 }">授课教师</c:if>
															<c:if test="${teacher.identify==6 }">班主任</c:if></td>
														<td
															style="width: 100px; text-align: center; vertical-align: middle">${teacher.telephone }</td>
														<td
															style="width: 350px; text-align: center; vertical-align: middle">${teacher.address }</td>
														<td
															style="width: 100px; text-align: center; vertical-align: middle">${teacher.education }</td>
														<td
															style="width: 200px; text-align: center; vertical-align: middle">${teacher.idcard }</td>
													</tr>
												</c:forEach>
											</tbody>
										</table>
									</div>
								</div>
							</div>
							<div class="am-panel am-panel-default">
								<div class="am-panel-hd">
									<h4 class="am-panel-title"
										data-am-collapse="{parent: '#accordion', target: '#do-not-say-4'}">
										学生信息</h4>
								</div>
								<div id="do-not-say-4" class="am-panel-collapse am-collapse">
									<div class="am-panel-bd">
										<table border="1">
											<thead>
												<tr>
													<th
														style="width: 100px; text-align: center; vertical-align: middle">姓名</th>
													<th
														style="width: 50px; text-align: center; vertical-align: middle">性别</th>
													<th
														style="width: 100px; text-align: center; vertical-align: middle">电话</th>
													<th
														style="width: 400px; text-align: center; vertical-align: middle">工作单位</th>
													<th
														style="width: 100px; text-align: center; vertical-align: middle">学历</th>
													<th
														style="width: 100px; text-align: center; vertical-align: middle">身份证号</th>
												</tr>
											</thead>
											<c:forEach items="${studentList }" var="stu">
												<tbody>
													<tr>
														<td
															style="width: 100px; text-align: center; vertical-align: middle">${stu.username }(${stu.loginname })</td>
														<td
															style="width: 50px; text-align: center; vertical-align: middle">${stu.email }</td>
														<td
															style="width: 100px; text-align: center; vertical-align: middle">${stu.telephone }</td>
														<td
															style="width: 400px; text-align: center; vertical-align: middle">${stu.address }</td>
														<td
															style="width: 100px; text-align: center; vertical-align: middle">${stu.education }</td>
														<td
															style="width: 100px; text-align: center; vertical-align: middle">${stu.idcard }</td>
													</tr>
												</tbody>
											</c:forEach>
										</table>
									</div>
								</div>
							</div>
							<!-- <div class="am-panel am-panel-default">
								    <div class="am-panel-hd">
								      <h4 class="am-panel-title" data-am-collapse="{parent: '#accordion', target: '#do-not-say-3'}">
								      培训信息
								      </h4>
								    </div>
								    <div id="do-not-say-3" class="am-panel-collapse am-collapse">
								      <div class="am-panel-bd">
								        尚未开发完成
								      </div>
								    </div>
								  </div> -->
						</div>

						<!--  </div> -->
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