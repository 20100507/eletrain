<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>添加或删除学生</title>
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
<%-- <script src="${pageContext.request.contextPath}/assets/js/app.js"></script> --%>
<script type="text/javascript">
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
	// 复选框回显
	$(function() {
		var boxObj = $("input:checkbox[name='ids']"); //获取所有的复选框值  
		var expresslist = '${list}'; //用el表达式获取在控制层存放的复选框的值为字符串类型  
		var express = expresslist.split(',');
		$.each(express, function(index, expressId) {
			boxObj.each(function() {
				if ($(this).val() == expressId) {
					$(this).attr("checked", true);
				}
			});
		});
	});
	// 查看个人信息
	function info(id) {
		window.location.href = "/student/stuInfo?menuId=17&id=" + id;
	}
	// 编辑
	function edit(id) {
		window.location.href = "/student/editPage?id=" + id;
	}
	// 删除一个学生
	function delBox(id) {
		$('#my-confirm2').modal({
			relatedTarget : this,
			onConfirm : function(options) {
				$.ajax({
					type : "post",//数据发送的方式（post 或者 get）
					traditional : true,
					url : "/student/delStu",//要发送的后台地址
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
	// 初始化学生密码
	function init(id) {
		$('#my-confirm3').modal({
			relatedTarget : this,
			onConfirm : function(options) {
				$.ajax({
					type : "post",//数据发送的方式（post 或者 get）
					url : "/student/initPassword",//要发送的后台地址
					data : {
						"ids" : id
					},
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
	// 搜索学生
	$(function() {
		$('#searchBut')
				.click(
						function() {
							var loginname = $("#search").val();
							window.location.href = "/classes/searchByLoginname?loginname="
									+ encodeURI(encodeURI(loginname));
						});
	});
	// 批量
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
						url : "/student/delStu",//要发送的后台地址
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
	$(function() {
		  $('#doc-modal-list').find('.am-icon-close').add('#delStudentButten').
		    on('click', function() {
		    	var ids = [];
				$('input[name="ids"]:checked').each(function() {  
					ids.push($(this).val());  
				});
				if (ids == "") {
					alert("对不起，您还没有选择要添加的选项！");
					return;
				} 
		      $('#delstudent').modal({
		        relatedTarget: this,
		        onConfirm: function(options) {
					$('#insertOrDelete').submit()
		        },
		        onCancel: function() {
		          return;
		        }
		      });
		    });
		});
</script>
<style>
    table{
        table-layout: fixed;
    }
   table td:hover{
       overflow: visible;
       white-space: normal;
   }
    table td {
        word-wrap: break-word;
        overflow: hidden;
        text-overflow: ellipsis;
        white-space: nowrap;
    }
</style>
</head>
<body data-type="generalComponents">
	<jsp:include page="head.jsp"></jsp:include>
	<jsp:include page="side.jsp"></jsp:include>
	<div>
		<div class="tpl-content-wrapper">
			<div class="tpl-portlet-components">
				<div class="portlet-title">
					<div class="caption font-green bold">
						<span class="am-icon-code"></span> 学生列表
					</div>
				</div>
				<div class="tpl-block">
					<div class="am-g">
					 <div class="am-u-sm-12 am-u-md-6">
					
						<div class="am-btn-group am-btn-group-xs">
									<c:if test="${adType==0 }">
										<button type="button"
											class="am-btn am-btn-default am-btn-primary"
											onclick="sub()">
											<span class="am-icon-plus"></span> 添加
										</button>
									</c:if>
									<c:if test="${adType==1 }">
										<button type="button"
											id="delStudentButten"
											class="am-btn am-btn-default am-btn-danger"
											>
											<span class="am-icon-trash-o"></span> 删除
										</button>
									</c:if>
									<script type="text/javascript">
										function sub(){
											var ids = [];
											$('input[name="ids"]:checked').each(function() {  
												ids.push($(this).val());  
											});
											if (ids == "") {
												alert("对不起，您还没有选择要添加的选项！");
												return;
											} 
											$('#insertOrDelete').submit()
										}
									</script>
						</div>			
						<div class="am-modal am-modal-confirm" tabindex="-1" id="delstudent">
						  <div class="am-modal-dialog">
						    <div class="am-modal-hd">移除学生</div>
						    <div class="am-modal-bd">
						      你，确定要从班级移除这些学生吗？
						    </div>
						    <div class="am-modal-footer">
						      <span class="am-modal-btn" data-am-modal-cancel>取消</span>
						      <span class="am-modal-btn" data-am-modal-confirm>确定</span>
						    </div>
						  </div>
						</div>
						<div class="am-btn-group am-btn-group-xs">
									<button type="button"
										class="am-btn am-btn-default am-btn-secondary"
										onclick="location='${pageContext.request.contextPath }/classes/list?type=${type}&menuId=${menuId}'">
										<span class="am-icon-save"></span> 返回
									</button>
						</div>			
					</div>	
						<div class="am-u-sm-12 am-u-md-6">
							<div class="am-btn-toolbar">
								<div class="am-btn-group am-btn-group-xs">
								</div>
							</div>
						</div>
						<div class="am-u-sm-12 am-u-md-3">
							<div class="am-form-group"></div>
						</div>
						<form action="${pageContext.request.contextPath }/classes/searchByLoginname">
						<input type="hidden" name="menuId" value="17"/>
						<div class="am-u-sm-12 am-u-md-3">
							<div class="am-input-group am-input-group-sm">
								<%-- <div class="am-input-group am-input-group-sm">
									<input required id="search" name="loginname" type="text" class="am-form-field"
										placeholder="身份证号" value="${loginname }"> <span
										class="am-input-group-btn">
										<button
											class="am-btn  am-btn-default am-btn-success tpl-am-btn-success am-icon-search"
											type="submit"></button>
									</span>
								</div> --%>
							</div>
						</div>
						</form>	
					</div>
					<div class="am-g">
						<div class="am-u-sm-12">
							<form id="insertOrDelete" method="post" class="am-form" action="${pageContext.request.contextPath }/classes/insertOrDelete?menuId=17">
								<input type="hidden" name="classesId" value="${classesId }">
								<input type="hidden" name="type" value="${type }">
								<input type="hidden" name="adType" value="${adType }"/>
								<table
								class="am-table am-table-striped am-table-hover table-main">
								<thead>
									<tr>
										<th style="width:30px"><input type="checkbox" id="selectAll"></th>
										<th style="width:100px" class="table-id">学号</th>
										<th class="table-title">姓名</th>
										<th class="table-title">性别</th>
										<th class="table-type">单位</th>
										<th class="table-author am-hide-sm-only">电话</th>
										<th class="table-date am-hide-sm-only">学历</th>
										<th class="table-date am-hide-sm-only">状态</th>
										<th class="table-set">操作</th>
									</tr>
								</thead>
								<c:forEach items="${pageBean.list }" var="stu">
									<tbody>
										<tr>
											<td><input id="ck" type="checkbox" name="ids"
												value=${stu.studentId }></td>
											<td>${stu.loginname }</td>
											<td>${stu.username }</td>
											<td>${stu.email }</td>
											<td>${stu.address }</td>
											<td class="am-hide-sm-only">${stu.telephone }</td>
											<td class="am-hide-sm-only">${stu.education }</td>
											<c:if test="${stu.isfinish==0}">
												<td class="am-hide-sm-only">未毕业</td>
											</c:if>
											<c:if test="${stu.isfinish==1}">
												<td class="am-hide-sm-only">毕业</td>
											</c:if>
											<td>
												<div class="am-btn-toolbar">
													<div class="am-btn-group am-btn-group-xs">
														<%-- <button id="ed" type="button"
															onclick="edit('${stu.studentId}')"
															class="am-btn am-btn-default am-btn-xs am-text-secondary">
															<span class="am-icon-pencil-square-o"></span> 编辑
														</button>
														<button id="del" type="button"
															onclick="delBox('${stu.studentId}')"
															class="am-btn am-btn-default am-btn-xs am-text-danger ">
															<span class="am-icon-trash-o"></span> 毕业
														</button> --%>
														<button id="infoBut" type="button"
															onclick="info('${stu.studentId}')"
															class="am-btn am-btn-default am-btn-xs am-hide-sm-only">
															<span class="am-icon-copy"></span>学生详情
														</button>
														<div class="am-modal am-modal-confirm" tabindex="-1"
															id="my-confirm2">
															<div class="am-modal-dialog">
																<div class="am-modal-hd">毕业</div>
																<div class="am-modal-bd">您确定要毕业这个学生么，一旦操作不可恢复</div>
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
													href="${pageContext.request.contextPath }/classes/insertOrDeleteUI?page=1&classesId=${classesId}&adType=${adType}&type=${type}&menuId=${menuId}">首页</a></li>
												<li><a
													href="${pageContext.request.contextPath }/classes/insertOrDeleteUI?page=${pageBean.page -1}&classesId=${classesId}&adType=${adType}&type=${type}&menuId=${menuId}">«</a></li>
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
														href="${ pageContext.request.contextPath }/classes/insertOrDeleteUI?page=${i}&classesId=${classesId}&adType=${adType}&type=${type}&menuId=${menuId}">${i }</a></li>
												</c:if>
											</c:forEach>
											<c:if test="${pageBean.page < pageBean.totalPage  }">
												<li><a
													href="${ pageContext.request.contextPath }/classes/insertOrDeleteUI?page=${pageBean.page +1}&classesId=${classesId}&adType=${adType}&type=${type}&menuId=${menuId}">»</a></li>
												<li><a
													href="${ pageContext.request.contextPath }/classes/insertOrDeleteUI?page=${pageBean.totalPage}&classesId=${classesId}&adType=${adType}&type=${type}&menuId=${menuId}">尾页</a></li>
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
	</div>
</body>
<script type="text/javascript">
	$(function() {
		$("a[menuId='${menuId}']").trigger("click");
	})
</script>
</html>