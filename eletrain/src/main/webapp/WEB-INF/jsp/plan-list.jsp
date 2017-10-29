<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %> 
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>培训方案列表</title>
<meta name="description" content="培训方案列表">
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
	function upload(id){
		$("#formup"+id).submit();
	}
	//下载模板弹框
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
	//上传弹框
	function a(id) {
		var $modal = $('#modal'+id);
		$modal.modal('open');
		
	};
	//下载弹框
	function b(id) {
		var $modal = $('#modaldownload'+id);
		$('#downul'+id).empty();
		$.ajax({
			type : "post",//数据发送的方式（post 或者 get）
			traditional : true,
			url : "/plan/exportFile",//要发送的后台地址
			data : {
				"planId" : id
			},//要发送的数据（参数）格式为{'val1':"1","val2":"2"}
			success : function(data) {//ajax请求成功后触发的方法
				for (var i = 0; i < data.length; i++) {
				$('#downul'+id).append("<li class='am-modal-actions-header'><a href='${pageContext.request.contextPath }/plan/downloadAllFile?path="+encodeURI(encodeURI(data[i])) +"'>" + data[i].substring(data[i].lastIndexOf("\\")+1) + "</a></li>");
				}
			
			}

		});
		$modal.modal('open');
		
	};
	// 查看个人信息
	function info(id) {
		window.location.href = "/plan/planInfo?id=" + id + "&menuId=7";
	}
	// 编辑
	function edit(id) {
		window.location.href = "/plan/editPlanPage?id=" + id + "&menuId=7";
	}
	// 删除一个学生
	function delBox(id) {
		$('#my-confirm2').modal({
			relatedTarget : this,
			onConfirm : function(options) {
				$.ajax({
					type : "post",//数据发送的方式（post 或者 get）
					traditional : true,
					url : "/plan/delPlan?menuId=7",//要发送的后台地址
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
	//提交申请
	function submitApp(id){
		$('#submitApp').modal({
			relatedTarget : this,
			onConfirm : function(options) {
				$.ajax({
					type : "post",//数据发送的方式（post 或者 get）
					traditional : true,
					url : "/plan/submitApp",//要发送的后台地址
					data : {
						"id" : id
					},//要发送的数据（参数）格式为{'val1':"1","val2":"2"}
					success : function(data) {//ajax请求成功后触发的方法
						if(data == "success"){
							window.location.reload();
						}
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
			var chk_value = [];
			$('input[name="ids"]').each(function() {//遍历每一个名字为interest的复选框，其中选中的执行函数  
				chk_value.push($(this).val());//将选中的值添加到数组chk_value中  
			});
			var flag = $("#selectAll").is(':checked');
			if (flag == true){
			for (var i = 0; i < chk_value.length; i++) {
					if($('input[value="' + chk_value[i] +'"]').prop("disabled")){
					$('input[value="' + chk_value[i] +'"]').prop("checked", false);
					}else{
					$('input[value="' + chk_value[i] +'"]').prop("checked", true);
					}
				}
			}else{
				$('input[name="ids"]').prop("checked", false);
			}
		});
	});
	// 搜索
	$(function() {
		$('#searchBut').click(
				function() {
					var pname = $("#search").val();
					window.location.href = "/plan/searchByPlanName?pname="
							+ encodeURI(encodeURI(pname)) + "&menuId=7";
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
						url : "/plan/delPlan",//要发送的后台地址
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
	
</script>
<body data-type="generalComponents">
	<jsp:include page="head.jsp"></jsp:include>
	<jsp:include page="side.jsp"></jsp:include>
	<div class="tpl-content-wrapper">
		<div class="tpl-portlet-components">
			<div class="portlet-title">
				<div class="caption font-green bold">
					<span class="am-icon-code"></span> 培训方案列表
				</div>
			</div>
			<div class="tpl-block">
				<div class="am-g">
					<div class="am-u-sm-12 am-u-md-6">
						<div class="am-btn-toolbar">
							<div class="am-btn-group am-btn-group-xs">
								<button type="button"
									class="am-btn am-btn-default am-btn-success"
									onclick="location='${pageContext.request.contextPath}/plan/addPlanPage?menuId=7'">
									<span class="am-icon-plus"></span> 新增
								</button>
								<button id="open" type="button"
									class="am-btn am-btn-primary js-modal-open">
									<span class="am-icon-archive"></span> 下载方案相关文档
								</button>
								<div class="am-modal am-modal-no-btn" tabindex="-1"
									id="your-modal">
									<div class="am-modal-dialog">
										<div class="am-modal-hd">
											方案相关文档(空白) <a href="javascript: void(0)"
												class="am-close am-close-spin" data-am-modal-close>&times;</a>
										</div>
										<div class="am-modal-bd">
											<div class="am-modal-actions-group">
												<ul class="am-list">
													<li class="am-modal-actions-header"><a
														href="${pageContext.request.contextPath }/plan/download?type=1">培训项目需求调查方案.doc</a>
													</li>
													<li class="am-modal-actions-header"><a
														href="${pageContext.request.contextPath }/plan/download?type=2">培训需求说明书.doc</a>
													</li>
													<li class="am-modal-actions-header"><a
														href="${pageContext.request.contextPath }/plan/download?type=3">培训需求调研报告.doc</a>
													</li>
													<li class="am-modal-actions-header"><a
														href="${pageContext.request.contextPath }/plan/download?type=4">培训课程大纲.doc</a>
													</li>
												</ul>
											</div>
										</div>
									</div>
								</div>
								<button type="button" id="doc-confirm-toggle" 
									class="am-btn am-btn-danger">
									<span class="am-icon-trash-o"></span> 批量删除
								</button>
							</div>
						</div>
					</div>
					<!-- 确定删除对话框 -->
					<div class="am-modal am-modal-confirm" tabindex="-1"
						id="my-confirm">
						<div class="am-modal-dialog">
							<div class="am-modal-hd">删除</div>
							<div class="am-modal-bd">您确定要删除这些方案信息么，一旦操作不可恢复</div>
							<div class="am-modal-footer">
								<span class="am-modal-btn" data-am-modal-confirm>确定</span> <span
									class="am-modal-btn" data-am-modal-cancel>取消</span>
							</div>
						</div>
					</div>
					<!-- 确定提交对话框 -->
					<div class="am-modal am-modal-confirm" tabindex="-1"
						id="submitApp">
						<div class="am-modal-dialog">
							<div class="am-modal-hd">提交申请</div>
							<div class="am-modal-bd">你确定要提交方案吗?</div>
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
								placeholder="请输入方案名称" value="${pname }"> <span
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
						<table class="am-table am-table-striped am-table-hover table-main">
							<thead>
								<tr>
									<th><input type="checkbox" id="selectAll"></th>
									<th class="table-title">方案名称</th>
									<th class="table-title">方案负责人</th>
									<th class="table-author am-hide-sm-only">方案创建时间</th>
									<th class="table-date am-hide-sm-only">审批状态</th>
									<th class="table-set">操作</th>
								</tr>
							</thead>
							<c:forEach varStatus="count" items="${pageBean.list }" var="plan">
								<tbody>
									<tr>
										<td><input id="ck" type="checkbox" name="ids"
										<c:if test="${plan.sign == 1}">disabled="disabled"</c:if>
											value=${plan.planId }></td>
										<td>${fn:substring(plan.pname,0,6) }</td>
										<td>${plan.adminName }</td>
										<td class="am-hide-sm-only"><fmt:formatDate
												value="${plan.createtime }" pattern="yyyy-MM-dd" /></td>
										<c:if test="${plan.sign == 0}">
											<td class="am-hide-sm-only">未提交</td>
										</c:if>
										<c:if test="${plan.sign == 1}">
											<td class="am-hide-sm-only">审批中</td>
										</c:if>
										<c:if test="${plan.sign == 2}">
											<td class="am-hide-sm-only">未通过</td>
										</c:if>
										<c:if test="${plan.sign == 3}">
											<td class="am-hide-sm-only">通过</td>
										</c:if>
										<td>
											<div class="am-btn-toolbar">
												<div class="am-btn-group am-btn-group-xs">
													<button id="ed" type="button"
														onclick="edit('${plan.planId }')"
														<c:if test="${plan.sign == 1}">disabled="disabled"</c:if>
														<c:if test="${plan.sign == 3}">disabled="disabled"</c:if>
														class="am-btn am-btn-default am-btn-xs am-text-secondary">
														<span class="am-icon-pencil-square-o"></span> 编辑
													</button>
													<button id="del" type="button"
														onclick="delBox('${plan.planId }')"
														<c:if test="${plan.sign ==1 }">disabled="disabled"</c:if>
														class="am-btn am-btn-default am-btn-xs am-text-danger ">
														<span class="am-icon-trash-o"></span> 删除
													</button>
													<button id="infoBut" type="button"
														onclick="info('${plan.planId }')"
														class="am-btn am-btn-default am-btn-xs am-hide-sm-only">
														<span class="am-icon-copy"></span>方案详情
													</button>
													<button onclick="a('${plan.planId }')" type="button"
													<c:if test="${plan.sign == 1}">disabled="disabled"</c:if>
													<c:if test="${plan.sign == 3}">disabled="disabled"</c:if>
														class="am-btn am-btn-default js-modal-open">
														<span class="am-icon-archive"></span> 上传方案相关文档
													</button>
													<div class="am-modal am-modal-no-btn" tabindex="-1"
														id="modal${plan.planId }">
														<div style="width: 800px;" class="am-modal-dialog">
															<div class="am-modal-hd">
																方案相关文档上传 <a href="javascript: void(0)"
																	class="am-close am-close-spin" data-am-modal-close>&times;</a>
															</div>
															<div class="am-modal-bd">
																<div class="am-modal-actions-group">
																	<ul class="am-list">
																		<form id="formup${plan.planId }"
																			class="am-form am-form-horizontal"
																			action="${pageContext.request.contextPath }/plan/upload?menuId=7"
																			method="post" enctype="multipart/form-data">
																			<input type="hidden" name="planName"
																				value="${plan.pname }">
																			<div class="am-form-group">
																				<label for="user-name"
																					class="am-u-sm-3 am-form-label">培训项目需求调查方案</label>
																				<input type="file" name="files">
																			</div>
																			<div class="am-form-group">
																				<label for="user-name"
																					class="am-u-sm-3 am-form-label">培训需求说明书</label> <input
																					type="file" name="files">
																			</div>
																			<div class="am-form-group">
																				<label for="user-name"
																					class="am-u-sm-3 am-form-label">培训需求调研报告</label> <input
																					type="file" name="files">
																			</div>
																			<div class="am-form-group">
																				<label for="user-name"
																					class="am-u-sm-3 am-form-label">培训课程大纲</label> <input
																					type="file" name="files">
				     													</div>
																			<input onclick="upload(${plan.planId });"
																				type="button" value="提交">
																		</form>
																	</ul>
																</div>
															</div>
														</div>
													</div>
													<button onclick="b('${plan.planId }')" type="button"
														class="am-btn am-btn-default js-modal-open">
														<span class="am-icon-archive"></span> 下载方案相关文档
													</button>
													<div class="am-modal am-modal-no-btn" tabindex="-1"
														id="modaldownload${plan.planId }">
														<div style="width: 800px;" class="am-modal-dialog">
															<div class="am-modal-hd">
																方案相关文档下载 <a href="javascript: void(0)"
																	class="am-close am-close-spin" data-am-modal-close>&times;</a>
															</div>
															<div class="am-modal-bd">
																<div class="am-modal-actions-group">
																	<ul id="downul${plan.planId }" class="am-list">
																	</ul>
																</div>
															</div>
														</div>
													</div>
													<button onclick="submitApp('${plan.planId }')" type="button"
														<c:if test="${plan.sign == 1}">disabled="disabled"</c:if>
														<c:if test="${plan.sign == 3}">disabled="disabled"</c:if>
														class="am-btn am-btn-default js-modal-open">
														<span class="am-icon-archive"></span> 提交申请
													</button>
													<div class="am-modal am-modal-confirm" tabindex="-1"
														id="my-confirm2">
														<div class="am-modal-dialog">
															<div class="am-modal-hd">删除</div>
															<div class="am-modal-bd">您确定要删除这个方案信息么，一旦操作不可恢复</div>
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
											href="${pageContext.request.contextPath }/plan/${url }&page=1&menuId=7">首页</a></li>
										<li><a
											href="${pageContext.request.contextPath }/plan/${url }&page=${pageBean.page -1}&menuId=7">«</a></li>
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
												href="${ pageContext.request.contextPath }/plan/${url }&page=${i}&menuId=7">${i }</a></li>
										</c:if>
									</c:forEach>
									<c:if test="${pageBean.page < pageBean.totalPage  }">
										<li><a
											href="${ pageContext.request.contextPath }/plan/${url }&page=${pageBean.page +1}&menuId=7">»</a></li>
										<li><a
											href="${ pageContext.request.contextPath }/plan/${url }&page=${pageBean.totalPage}&menuId=7">尾页</a></li>
									</c:if>
								</ul>
							</div>
						</div>
						<hr>

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