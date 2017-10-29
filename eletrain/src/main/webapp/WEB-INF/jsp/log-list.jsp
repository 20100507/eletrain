<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>日志记录</title>
<meta name="description" content="日志记录">
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
	// 删除一条日志
	function delBox(id) {
		$('#my-confirm2').modal({
			relatedTarget : this,
			onConfirm : function(options) {
				$.ajax({
					type : "post",//数据发送的方式（post 或者 get）
					traditional : true,
					url : "/log/delLog?menuId=34",//要发送的后台地址
					data : {
						"logId" : id
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
							var dname = $("#search").val();
							window.location.href = "/log/searchByLogUsername?logName="
									+ encodeURI(encodeURI(dname)) + "&menuId=34";
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
						url : "/log/delLog?menuId=34",//要发送的后台地址
						data : {
							"logId" : chk_value
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
</script>
<body data-type="generalComponents">
<jsp:include page="head.jsp"></jsp:include>
	<jsp:include page="side.jsp"></jsp:include>
		<div class="tpl-content-wrapper">
			<div class="tpl-portlet-components">
				<div class="portlet-title">
					<div class="caption font-green bold">
						<span class="am-icon-code"></span> 日志列表
					</div>
				</div>
				<div class="tpl-block">
					<div class="am-g">
						<div class="am-u-sm-12 am-u-md-6">
							<div class="am-btn-toolbar">
								<div class="am-btn-group am-btn-group-xs">
									<button type="button" id="doc-confirm-toggle"
										class="am-btn am-btn-danger">
										<span class="am-icon-trash-o"></span> 批量删除
									</button>
								</div>
							</div>
						</div>
						<div class="am-modal am-modal-confirm" tabindex="-1"
							id="my-confirm">
							<div class="am-modal-dialog">
								<div class="am-modal-hd">删除</div>
								<div class="am-modal-bd">您确定要删除这些登录记录吗?</div>
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
									placeholder="请输入登录人" value=""> <span
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
											<th class="table-title">登录人</th>
											<th class="table-title">登录时间</th>
											<th class="table-type">登录IP</th>
										</tr>
									</thead>
									<c:forEach items="${pageBean.list }" var="log">
										<tbody>
											<tr>
												<td><input id="ck" type="checkbox" name="ids"
													value=${log.logId }></td>
												<td>${log.username }</td>
												<td><fmt:formatDate value="${log.logintime }"
												pattern="yyyy-MM-dd HH:ss:mm"/>
												</td>
												<td>${log.ip }</td>
												<td>
													<div class="am-btn-toolbar">
														<div class="am-btn-group am-btn-group-xs">
															<button id="del" type="button"
																onclick="delBox('${log.logId}')"
																class="am-btn am-btn-default am-btn-xs am-text-danger ">
																<span class="am-icon-trash-o"></span> 删除
															</button>
															<div class="am-modal am-modal-confirm" tabindex="-1"
																id="my-confirm2">
																<div class="am-modal-dialog">
																	<div class="am-modal-hd">删除</div>
																	<div class="am-modal-bd">您确定要删除这条登录记录吗?</div>
																	<div class="am-modal-footer">
																		<span class="am-modal-btn" data-am-modal-confirm>确定</span>
																		<span class="am-modal-btn" data-am-modal-cancel>取消</span>
																	</div>
																</div>
															</div>
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
													href="${pageContext.request.contextPath }/log/${url }&page=1&menuId=7">首页</a></li>
												<li><a
													href="${pageContext.request.contextPath }/log/${url }&page=${pageBean.page -1}&menuId=7">«</a></li>
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
														href="${ pageContext.request.contextPath }/log/${url }&page=${i}&menuId=34">${i }</a></li>
												</c:if>
											</c:forEach>
											<c:if test="${pageBean.page < pageBean.totalPage  }">
												<li><a
													href="${ pageContext.request.contextPath }/log/${url }&page=${pageBean.page +1}&menuId=34">»</a></li>
												<li><a
													href="${ pageContext.request.contextPath }/log/${url }&page=${pageBean.totalPage}&menuId=34">尾页</a></li>
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
$(function(){
$("a[menuId='${menuId}']").trigger("click");
})
</script>
</html>