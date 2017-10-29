<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>Amaze UI Admin index Examples</title>
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
</head>
<script src="${pageContext.request.contextPath}/assets/js/jquery.min.js"></script>
<script
	src="${pageContext.request.contextPath}/assets/js/amazeui.min.js"></script>
<%-- <script src="${pageContext.request.contextPath}/assets/js/app.js"></script> --%>
<script type="text/javascript">
	function editAdmin(id){
		location.href = "${pageContext.request.contextPath}/admin/toUpdateAdmin?menuId=0&id="+id;
	}
	function initializePassword(id) {
		$("#confirmBox1").html("初始化密码");
		$("#confirmBox2").html("确定要初始化密码吗？？？？");
		$('#my-confirm')
				.modal(
						{
							relatedTarget : this,
							onConfirm : function(options) {
								$
										.ajax({
											"type" : "POST",
											"async" : true,
											"url" : "${pageContext.request.contextPath}/admin/initializePassword",
											"data" : id,
											"contentType" : "application/json;charset=UTF-8",
											"dataType" : "json",
											"success" : function(data) {
												if (data == true) {
													$(".am-modal-hd").html(
															"密码初始化成功！！！");
													$('#my-alert').modal();
												} else {
													$(".am-modal-hd").html(
															"密码初始化失败，请重试！！！");
													$('#my-alert').modal();
												}
											}
										});
							},
							// closeOnConfirm: false,
							onCancel : function() {
							}
						});
	}
	function deleteBox(id) {
		$("#confirmBox1").html("删除");
		$("#confirmBox2").html("确定要删除吗？？？？");
		$('#my-confirm')
				.modal(
						{
							relatedTarget : this,
							onConfirm : function(options) {
								location.href = "${pageContext.request.contextPath}/admin/deleteAdmin?menuId=0&id="
										+ id;
							},
							// closeOnConfirm: false,
							onCancel : function() {
							}
						});
	}

	$(function() {
		$("#toAddAdmin")
				.click(
						function() {
							location.href = "${pageContext.request.contextPath}/admin/toAddAdmin?menuId=0"
						});

		$("#checkAllId").click(function() {
			$("tbody input").prop("checked", this.checked);
		});

		$("#conditionButton").click(function() {
							location.href = "${pageContext.request.contextPath}/admin/getAdminListByPageAndCondition?menuId=0&condition="
									+ $("#condition").val();
						});
	});
</script>
</head>
<body data-type="generalComponents">
	<jsp:include page="head.jsp"></jsp:include>
	<jsp:include page="side.jsp"></jsp:include>
	<div class="am-modal am-modal-confirm" tabindex="-1" id="my-confirm">
		<div class="am-modal-dialog">
			<div id="confirmBox1" class="am-modal-hd"></div>
			<div id="confirmBox2" class="am-modal-bd">确定要删除？？？？</div>
			<div class="am-modal-footer">
				<span class="am-modal-btn" data-am-modal-confirm>确定</span> 
					<span class="am-modal-btn" data-am-modal-cancel>取消</span>
			</div>
		</div>
	</div>
	<div class="am-modal am-modal-alert" tabindex="-1" id="my-alert">
		<div class="am-modal-dialog">
			<div id="alertBox" class="am-modal-hd"></div>
			<div class="am-modal-bd"></div>
			<div class="am-modal-footer">
				<span class="am-modal-btn">确定</span>
			</div>
		</div>
	</div>
	<div class="tpl-content-wrapper">
		<div class="tpl-portlet-components">
			<div class="portlet-title">
				<div class="caption font-green bold">
					<span class="am-icon-code">管理员列表</span>
				</div>
			</div>
			<div class="tpl-block">
				<div class="am-g">
					<div class="am-u-sm-12 am-u-md-6">
						<div class="am-btn-toolbar">
							<div class="am-btn-group am-btn-group-xs">
								<button type="button" id="toAddAdmin"
									class="am-btn am-btn-default am-btn-success">
									<span class="am-icon-plus"> 新增</span>
								</button>
							</div>
						</div>
					</div>
					<div class="am-u-sm-12 am-u-md-3">
						<div class="am-input-group am-input-group-sm">
							<input type="text" id="condition" name="condition" placeholder="请输入管理员账号" class="am-form-field"> 
								<span class="am-input-group-btn">
								<button type="button" id="conditionButton"
									class="am-btn  am-btn-default am-btn-success tpl-am-btn-success am-icon-search">
								</button>
							</span>
						</div>
					</div>
				</div>
				<div class="am-g">
					<div class="am-u-sm-12">
						<form id="deleteMultipleAdminsForm" class="am-form"
							action="${pageContext.request.contextPath}/admin/deleteMultipleAdmins">
							<table
								class="am-table am-table-striped am-table-hover table-main am-table-centered">
								<thead>
									<tr>
										<th class="table-check"></th>
										<th class="table-id">ID</th>
										<th class="table-author am-hide-sm-only">账号</th>
										<th class="table-title">姓名</th>
										<th class="table-type">身份</th>
										<th class="table-set">操作</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach items="${pageBean.list}" var="admin">
										<tr>
											<td></td>
											<td>${admin.adminId}</td>
											<td class="am-hide-sm-only">${admin.loginname}</td>
											<td>${admin.username}</td>
											<td>${admin.email}</td>
											<td>
												<div class="am-btn-toolbar" style="display: inline-block;">
													<div class="am-btn-group am-btn-group-xs">
														<c:if test="${admin.identify == 1 && admin.loginname != '000000'
																|| admin.identify == 3 || admin.identify == 4 || admin.identify == 5}">
																<button type="button" onclick="editAdmin('${admin.adminId}')"
																	class="am-btn am-btn-default am-btn-xs am-text-secondary">
																	<span class="am-icon-pencil-square-o"> 编辑</span>
																</button>
																<button type="button"
																	onclick="initializePassword('${admin.adminId}')"
																	class="am-btn am-btn-default am-btn-xs am-hide-sm-only">
																	<span class="am-icon-copy"> 初始化密码</span>
																</button>
														</c:if>
													</div>
												</div>
											</td>
										</tr>
									</c:forEach>
								</tbody>
							</table>
							<div class="am-cf">
								<div class="am-fr">
									<ul class="am-pagination tpl-pagination">
										<c:if test="${pageBean.page ne 1}">
											<li><a
												href="${pageContext.request.contextPath }/admin/${path}&page=1">首页</a></li>
											<li><a
												href="${pageContext.request.contextPath }/admin/${path}&page=${pageBean.page -1}">«</a></li>
										</c:if>
										<c:choose>
											<c:when test="${pageBean.totalPage <= 10 }">
												<c:set var="begin" value="1" />
												<c:set var="end" value="${pageBean.totalPage}" />
											</c:when>
											<c:otherwise>
												<%-- 当总页数>10时，通过公式计算出begin和end --%>
												<c:set var="begin" value="${pageBean.page -5}" />
												<c:set var="end" value="${pageBean.page +4}" />
												<%-- 头溢出 --%>
												<c:if test="${begin < 1 }">
													<c:set var="begin" value="1" />
													<c:set var="end" value="10" />
												</c:if>
												<%-- 尾溢出 --%>
												<c:if test="${end > pageBean.totalPage }">
													<c:set var="begin" value="${pageBean.totalPage - 9}" />
													<c:set var="end" value="${pageBean.totalPage}" />
												</c:if>
											</c:otherwise>
										</c:choose>
										<c:forEach var="i" begin="${begin}" end="${end}">
											<c:if test="${pageBean.page ==  i}">
												<li>${i }</li>
											</c:if>
											<c:if test="${pageBean.page !=  i}">
												<li><a
													href="${pageContext.request.contextPath}/admin/${path}&page=${i}">${i}</a></li>
											</c:if>
										</c:forEach>
										<c:if test="${pageBean.page ne pageBean.totalPage}">
											<li><a
												href="${ pageContext.request.contextPath}/admin/${path}&page=${pageBean.page +1}">»</a></li>
											<li><a
												href="${ pageContext.request.contextPath}/admin/${path}&page=${pageBean.totalPage}">尾页</a></li>
										</c:if>
									</ul>
								</div>
							</div>
							<hr>
						</form>
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