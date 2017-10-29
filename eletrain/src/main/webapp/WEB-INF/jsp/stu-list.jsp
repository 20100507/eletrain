<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>学员列表</title>
<meta name="description" content="学员列表">
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
<%-- <script
	src="${pageContext.request.contextPath}/assets/js/amazeui.min.js"></script>
 --%><%-- <script src="${pageContext.request.contextPath}/assets/js/app.js"></script> --%>
<script type="text/javascript">
	function noFinfishStu(classesId,cname) {
		var a = "<li class='am-modal-actions-header'><a href='${pageContext.request.contextPath }/student/exportFile?type=2&filename="+encodeURI(encodeURI(cname)) +"&isFinish=0&classesId="
				+ classesId + "'>"+cname+".xls</a></li>";
		a += "<li class='am-modal-actions-header'><a href='${pageContext.request.contextPath }/student/exportFile?type=1&filename="+encodeURI(encodeURI(cname)) +"&isFinish=0&classesId="
				+ classesId + "'>"+cname+".xlsx</a></li>"
		$('#exportList').html(a);
		var $modal = $('#your-modal2');
		$modal.modal('open');
		var $popup = $('#my-popup1');
		$popup.modal('close');
	}
	function finfishStu(classesId,cname) {
		var a = "<li class='am-modal-actions-header'><a href='${pageContext.request.contextPath }/student/exportFile?type=2&filename="+encodeURI(encodeURI(cname)) +"&isFinish=1&classesId="
				+ classesId + "'>"+cname+".xls</a></li>";
		a += "<li class='am-modal-actions-header'><a href='${pageContext.request.contextPath }/student/exportFile?type=1&filename="+encodeURI(encodeURI(cname)) +"&isFinish=1&classesId="
				+ classesId + "'>"+cname+".xlsx</a></li>"
		$('#exportList').html(a);
		var $modal = $('#your-modal2');
		$modal.modal('open');
		var $popup = $('#my-popup');
		$popup.modal('close');
	}
	// 下载模板弹框
	$(function() {
		var $modal = $('#your-modal');
		$modal.siblings('#open').on('click', function(e) {
			var $target = $(e.target);
			if (($target).hasClass('js-modal-open')) {
				$modal.modal();
			} else if (($target).hasClass('js-modal-close')) {
				$modal.modal('close');
			} else {
				$modal.modal('toggle');
			}
		});
	});
	$(function() {
		var content1 = "";
		$
				.ajax({
					"async" : true,
					"url" : "${pageContext.request.contextPath}/student/allNoFinishClasses",
					"type" : "POST",
					"dataType" : "json",
					"success" : function(data1) {
						for (var i = 0; i < data1.length; i++) {
							content1 += "<li class='am-modal-actions-header'><a href='javascript:void(0);' onclick=\"" + "noFinfishStu("
									+ data1[i].classesId +"," +"\'"+ data1[i].cname +"\'"
									+ ")\">"
									+ data1[i].cname
									+ "("
									+ data1[i].classesNo
									+ ")" + "</a></li>";
						}
						$("#nofinfishUl").html(content1);
					}
				});
		var content2 = "";
		$
				.ajax({
					"async" : true,
					"url" : "${pageContext.request.contextPath}/student/allFinishClasses",
					"type" : "POST",
					"dataType" : "json",
					"success" : function(data2) {
						for (var i = 0; i < data2.length; i++) {
							content2 += "<li class='am-modal-actions-header'><a href='javascript:void(0);' onclick=\"" + "finfishStu("
									+ data2[i].classesId +"," +"\'"+ data2[i].cname +"\'"
									+ ")\">"
									+ data2[i].cname
									+ "("
									+ data2[i].classesNo
									+ ")" + "</a></li>";
						}
						$("#finfishUl").html(content2);
					}
				});
	});
	// 查看个人信息
	function info(id) {
		window.location.href = "/student/stuInfo?id=" + id + "&menuId=0";
	}
	// 编辑
	function edit(id) {
		window.location.href = "/student/editPage?id=" + id + "&menuId=0";
	}
	// 删除一个学生
	function delBox(id) {
		$('#my-confirm2').modal({
			relatedTarget : this,
			onConfirm : function(options) {
				$.ajax({
					type : "post",//数据发送的方式（post 或者 get）
					traditional : true,
					url : "/student/delStu?menuId=0",//要发送的后台地址
					data : {
						"ids" : id
					},//要发送的数据（参数）格式为{'val1':"1","val2":"2"}
					success : function(data) {//ajax请求成功后触发的方法
						window.location.reload();
					}

				});
			},
			onCancel : function() {
				return;
			}
		});
	}
	// 全选
	$(function() {
		$("#selectAll").click(function() {
			var flag = $("#selectAll").is(':checked');
			if (flag == true) {
				$('input[name="ids"]').prop("checked", true);
			} else {
				$('input[name="ids"]').prop("checked", false);
			}
		});
	});
	// 搜索
	$(function() {
		$('#searchBut')
				.click(
						function() {
							var loginname = $("#search").val();
							window.location.href = "/student/searchByLoginname?loginname="
									+ encodeURI(encodeURI(loginname))
									+ "&menuId=0";
						});
	});
	// 批量删除
	$(function() {
		$('#doc-confirm-toggle').on('click', function() {
			$('#my-confirm').modal({
				relatedTarget : this,
				onConfirm : function(options) {
					var chk_value = [];
					$('input[name="ids"]:checked').each(function() {//遍历每一个名字为interest的复选框，其中选中的执行函数  
						chk_value.push($(this).val());//将选中的值添加到数组chk_value中  
					});
					if (chk_value == "") {
						alert("您还没有选择");
						return;
					}
					$.ajax({
						type : "post",//数据发送的方式（post 或者 get）
						traditional : true,
						url : "/student/delStu?menuId=0",//要发送的后台地址
						data : {
							"ids" : chk_value
						},//要发送的数据（参数）格式为{'val1':"1","val2":"2"}
						success : function(data) {//ajax请求成功后触发的方法
							window.location.reload();
						}

					});
				},
				onCancel : function() {
					return;
				}
			});
		});
	});
	function a() {
		window.location.href = "/student/allFinishStu?menuId=0";
	}
	function b() {
		window.location.href = "/student/allNotFinishStu?menuId=0";
	}
	function c() {
		$('#f').click();
	}
</script>
<body data-type="generalComponents">
	<jsp:include page="head.jsp"></jsp:include>
	<jsp:include page="side.jsp"></jsp:include>

	<div class="tpl-content-wrapper">
		<div class="tpl-portlet-components">
			<div class="portlet-title">
				<div class="caption font-green bold">
					<span class="am-icon-code"></span>学员列表
				</div>
			</div>
			<div class="tpl-block">
				<div class="am-g">
					<div class="am-u-sm-12 am-u-md-6">
						<div class="am-btn-toolbar" style="width: 700px;">
							<div class="am-btn-group am-btn-group-xs">
								<button type="button"
									class="am-btn am-btn-default am-btn-success"
									onclick="location='${pageContext.request.contextPath}/student/addPage?menuId=0'">
									<span class="am-icon-plus"></span> 新增
								</button>
								<form style="display: none;" method="post"
									action="/student/upload" enctype="multipart/form-data">
									<input type="file" id="f" onchange="this.form.submit()"
										name="file"
										style="position: absolute; filter: alpha(opacity = 0); opacity: 0; width: 30px;"
										size="1" />
								</form>
								<button type="button"
									class="am-btn am-btn-default am-btn-secondary" onclick="c();">
									<span class="am-icon-save"></span> 导入
								</button>
								<button type="button"
									class="am-btn am-btn-default am-btn-warning"
									data-am-modal="{target: '#your-modal1', closeViaDimmer: 0, width: 400, height: 225}">
									<span class="am-icon-archive"></span> 导出
								</button>
								<div class="am-modal am-modal-no-btn" tabindex="-1"
									id="your-modal1">
									<div class="am-modal-dialog">
										<div class="am-modal-hd">
											学生信息表(两种Excel格式) <a href="javascript: void(0)"
												class="am-close am-close-spin" data-am-modal-close>&times;</a>
										</div>
										<div class="am-modal-bd">
											<div class="am-modal-actions-group">
												<ul class="am-list">
													<li class="am-modal-actions-header">
														<button type="button" class="am-btn am-btn-danger"
															data-am-modal="{target: '#my-popup',dimmer:false}">已毕业学生</button>
													</li>
													<li class="am-modal-actions-header"><button
															type="button" class="am-btn am-btn-danger"
															data-am-modal="{target: '#my-popup1',dimmer:false}">未毕业学生</button></li>
													</li>
												</ul>
											</div>
										</div>
									</div>
								</div>
								<div class="am-popup" id="my-popup"
									style="top: 65%; height: 500px">
									<div class="am-popup-inner">
										<div class="am-popup-hd">
											<h4 class="am-popup-title">班级列表（已毕业班级）</h4>
											<span data-am-modal-close class="am-close">&times;</span>
										</div>
										<ul class="am-list" id="finfishUl">
											<li class="am-modal-actions-header"></li>
										</ul>
									</div>
								</div>
								<div class="am-popup" id="my-popup1"
									style="top: 65%; height: 500px">
									<div class="am-popup-inner">
										<div class="am-popup-hd">
											<h4 class="am-popup-title">班级列表（未毕业班级）</h4>
											<span data-am-modal-close class="am-close">&times;</span>
										</div>
										<ul class="am-list" id="nofinfishUl">
											</li>
										</ul>
									</div>
								</div>
								<div class="am-modal am-modal-no-btn" tabindex="-1"
									id="your-modal2">
									<div class="am-modal-dialog">
										<div class="am-modal-hd">
											学生信息表(两种Excel格式) <a href="javascript: void(0)"
												class="am-close am-close-spin" data-am-modal-close>&times;</a>
										</div>
										<div class="am-modal-bd">
											<div class="am-modal-actions-group">
												<ul class="am-list" id="exportList">
													<li class="am-modal-actions-header"><a
														href="javascript:void(0);" onclick="finfishStu">已毕业学生</a>
													</li>
													<li class="am-modal-actions-header"><a
														href="javascript:void(0);" onclick="noFinishStu"">未毕业学生</a>
													</li>
												</ul>
											</div>
										</div>
									</div>
								</div>
								<button id="open" type="button"
									class="am-btn am-btn-primary js-modal-open">
									<span class="am-icon-archive"></span> 下载模板
								</button>
								<div class="am-modal am-modal-no-btn" tabindex="-1"
									id="your-modal">
									<div class="am-modal-dialog">
										<div class="am-modal-hd">
											学生信息录入表(模板,两种Excel格式) <a href="javascript: void(0)"
												class="am-close am-close-spin" data-am-modal-close>&times;</a>
										</div>
										<div class="am-modal-bd">
											<div class="am-modal-actions-group">
												<ul class="am-list">
													<li class="am-modal-actions-header"><a
														href="${pageContext.request.contextPath }/student/download?type=1">学员信息录入表.xlsx</a>
													</li>
													<li class="am-modal-actions-header"><a
														href="${pageContext.request.contextPath }/student/download?type=2">学员信息录入表.xls</a>
													</li>
												</ul>
											</div>
										</div>
									</div>
								</div>
								<button type="button"
									class="am-btn am-btn-default am-btn-secondary" onclick="a()">
									<span class="am-icon-save"></span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;已毕业学生&nbsp;&nbsp;&nbsp;
								</button>
								<button type="button"
									class="am-btn am-btn-warning am-btn-secondary" onclick="b();">
									<span class="am-icon-save"></span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;未毕业学生&nbsp;&nbsp;&nbsp;
								</button>
							</div>
						</div>
					</div>
					<div class="am-modal am-modal-confirm" tabindex="-1"
						id="my-confirm">
						<div class="am-modal-dialog">
							<div class="am-modal-hd">毕业</div>
							<div class="am-modal-bd">您确定要毕业这些学生么，一旦操作不可恢复</div>
							<div class="am-modal-footer">
								<span class="am-modal-btn" data-am-modal-confirm>确定</span> <span
									class="am-modal-btn" data-am-modal-cancel>取消</span>
							</div>
						</div>
					</div>
					<div class="am-u-sm-12 am-u-md-3">
						<div class="am-form-group"></div>
					</div>
					<div class="am-u-sm-12 am-u-md-3">
						<div class="am-input-group am-input-group-sm">
							<input id="search" type="text" class="am-form-field"
								placeholder="身份证号" value="${loginname }"> <span
								class="am-input-group-btn">
								<button id="searchBut"
									class="am-btn  am-btn-default am-btn-success tpl-am-btn-success am-icon-search"
									type="button"></button>
							</span>
						</div>
					</div>
				</div>
				<div class="am-g">
					<div class="am-u-sm-12">
						<form class="am-form">
							<table
								class="am-table am-table-striped am-table-hover table-main">
								<thead>
									<tr>
										<th><input type="checkbox" id="selectAll"></th>
										<th class="table-title">姓名</th>
										<th class="table-title">性别</th>
										<th class="table-author am-hide-sm-only">电话</th>
										<th class="table-date am-hide-sm-only">单位</th>
										<th class="table-date am-hide-sm-only">状态</th>
										<th class="table-set">操作</th>
									</tr>
								</thead>
								<c:forEach items="${pageBean.list }" var="stu">
									<tbody>
										<tr>
											<td><input id="ck" type="checkbox" name="ids"
												value=${stu.studentId }></td>
											<td>${stu.username }</td>
											<td>${stu.email }</td>
											<td class="am-hide-sm-only">${stu.telephone }</td>
											<td class="am-hide-sm-only">${stu.address }</td>
											<c:if test="${stu.isfinish==0}">
												<td class="am-hide-sm-only">未毕业</td>
											</c:if>
											<c:if test="${stu.isfinish==1}">
												<td class="am-hide-sm-only">毕业</td>
											</c:if>
											<td>
												<div class="am-btn-toolbar">
													<div class="am-btn-group am-btn-group-xs">
														<button id="ed" type="button"
															onclick="edit('${stu.studentId}')"
															class="am-btn am-btn-default am-btn-xs am-text-secondary">
															<span class="am-icon-pencil-square-o"></span> 编辑
														</button>
														<button id="infoBut" type="button"
															onclick="info('${stu.studentId}')"
															class="am-btn am-btn-default am-btn-xs am-hide-sm-only">
															<span class="am-icon-copy"></span>学生详情
														</button>
													</div>
												</div>
											</td>
										</tr>
									</tbody>
								</c:forEach>
							</table>
							<div class="am-cf">
								<div class="am-fr">
									<ul class="am-pagination tpl-pagination">
										<c:if test="${pageBean.page ne 1}">
											<li><a
												href="${pageContext.request.contextPath }/student/${url }&page=1&menuId=0">首页</a></li>
											<li><a
												href="${pageContext.request.contextPath }/student/${url }&page=${pageBean.page -1}&menuId=0">«</a></li>
										</c:if>
										<c:choose>
											<c:when test="${pageBean.totalPage <= 10 }">
												<c:set var="begin" value="1" />
												<c:set var="end" value="${pageBean.totalPage }" />
											</c:when>
											<c:otherwise>
												<%-- 当总页数>10时，通过公式计算出begin和end --%>
												<c:set var="begin" value="${pageBean.page -5 }" />
												<c:set var="end" value="${pageBean.page +4 }" />
												<%-- 头溢出 --%>
												<c:if test="${begin < 1 }">
													<c:set var="begin" value="1" />
													<c:set var="end" value="10" />
												</c:if>
												<%-- 尾溢出 --%>
												<c:if test="${end > pageBean.totalPage }">
													<c:set var="begin" value="${pageBean.totalPage - 9 }" />
													<c:set var="end" value="${pageBean.totalPage }" />
												</c:if>
											</c:otherwise>
										</c:choose>
										<c:forEach var="i" begin="${begin }" end="${end }">
											<c:if test="${pageBean.page ==  i }">
												<li>${i }</li>
											</c:if>
											<c:if test="${pageBean.page !=  i }">
												<li><a
													href="${ pageContext.request.contextPath }/student/${url }&page=${i}&menuId=0">${i }</a></li>
											</c:if>
										</c:forEach>
										<c:if test="${pageBean.page < pageBean.totalPage  }">
											<li><a
												href="${ pageContext.request.contextPath }/student/${url }&page=${pageBean.page +1}&menuId=0">»</a></li>
											<li><a
												href="${ pageContext.request.contextPath }/student/${url }&page=${pageBean.totalPage}&menuId=0">尾页</a></li>
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