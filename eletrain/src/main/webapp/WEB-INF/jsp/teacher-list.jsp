<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!doctype html>
<html>

<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>教师列表</title>
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
	/* 多条件查询 */
	function search() {
		$('#index').attr('value', $('#shuju').val());
		$('#searchAddition').submit();
	}
	/* 全选 */
	window.onload = function() {
		$("#list_All_Select").click(function() {
			$("tbody input").prop("checked", this.checked);
		});
	}
</script>
<script type="text/javascript">
	/* 下载模板 */
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
	/* 导出 */
	$(function() {
		var $modal = $('#export-modal');
		$modal.siblings('#export').on('click', function(e) {
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
	/* 批量删除 */
	$(function() {
		$('#doc-confirm-toggle').on('click', function() {
			$('#my-confirm1').modal({
				relatedTarget : this,
				onConfirm : function(options) {
					var teacherIds = [];
					$('input[name="teacherIds"]:checked').each(function() {
						teacherIds.push($(this).val());
					});
					if (teacherIds == "") {
						alert("对不起，您还没有选择要删除的选项！");
						return;
					}
					$.ajax({
						type : "post",
						traditional : true,
						url : "/teacher/delTeacherByIds?menuId=0",
						data : {
							"teacherIds" : teacherIds
						},
						success : function(data) {
							window.location.reload();
						}

					});
				},
				// closeOnConfirm: false,
				onCancel : function() {
					return;
				}
			});
		});
	});
	/* 通过id使教师就职 */
	function updTeacher(id) {
		$('#my-confirm4').modal({
			relatedTarget : this,
			onConfirm : function(options) {
				$.ajax({
					type : "post",
					traditional : true,
					url : "/teacher/updTeacherByIds",
					data : {
						"teacherIds" : id
					},
					success : function(data) {
						window.location.reload();
					}

				});
			},
			onCancel : function() {
				return;
			}
		});
	}
	/* 通过id删除教师 */
	function delTeacher(id) {
		$('#my-confirm2').modal({
			relatedTarget : this,
			onConfirm : function(options) {
				$.ajax({
					type : "post",
					traditional : true,
					url : "/teacher/delTeacherByIds?menuId=0",
					data : {
						"teacherIds" : id
					},
					success : function(data) {
						window.location.reload();
					}

				});
			},
			onCancel : function() {
				return;
			}
		});
	}
	/* 初始化密码 */
	function init(a) {
		$('#my-confirm3').modal({
			relatedTarget : this,
			onConfirm : function(options) {
				$.ajax({
					type : "post",
					url : "/teacher/init?menuId=0",
					data : {
						"teacherId" : a
					},
					success : function(data) {
						window.location.reload();
					}

				});
			},
			onCancel : function() {
				return;
			}
		});
	}

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
        <div class="tpl-content-wrapper">
            <div class="tpl-portlet-components">
                <div class="portlet-title">
                    <div class="caption font-green bold">
                        <span class="am-icon-code"></span> 在职教师列表：
                    </div>
                </div>
                <div class="tpl-block">
					<div class="am-g">
						<div class="am-u-sm-12 am-u-md-6">
							<div class="am-btn-toolbar">
								<div class="am-btn-group am-btn-group-xs">
									<button type="button"
										class="am-btn am-btn-default am-btn-success"
										onclick="location='${pageContext.request.contextPath }/teacher/addTeacherUI?menuId=0'">
										<span class="am-icon-plus"></span> 新增
									</button>
									<form id="fileUpload" style="display: none;" method="post"
										action="${pageContext.request.contextPath }/teacher/upload" enctype="multipart/form-data">
										<input type="file" id="f" onchange="this.form.submit()"
											name="file"
											style="position: absolute; filter: alpha(opacity = 0); opacity: 0; width: 30px;"
											size="1" />
									</form>
									<button type="button"
										class="am-btn am-btn-default am-btn-secondary"
										onclick="$('#f').click();">
										<span class="am-icon-save"></span> 导入
									</button>
									<button id="export" type="button"
										class="am-btn am-btn-primary js-modal-open">
										<span class="am-icon-archive"></span> 导出
									</button>
									<button id="open" type="button"
										class="am-btn am-btn-primary js-modal-open am-btn-secondary">
										<span class="am-icon-save"></span> 下载模板
									</button>
									<!-- 下载模板弹框 -->
									<div class="am-modal am-modal-no-btn" tabindex="-1"
										id="your-modal">
										<div class="am-modal-dialog">
											<div class="am-modal-hd">
												教师信息录入表(两种Excel格式) <a href="javascript: void(0)"
													class="am-close am-close-spin" data-am-modal-close>&times;</a>
											</div>
											<div class="am-modal-bd">
												<div class="am-modal-actions-group">
													<ul class="am-list">
														<li class="am-modal-actions-header"><a
															href="${pageContext.request.contextPath }/teacher/downLoad?mould=1">教师信息录入表.xlsx</a>
														</li>
														<li class="am-modal-actions-header"><a
															href="${pageContext.request.contextPath }/teacher/downLoad?mould=2">教师信息录入表.xls</a>
														</li>
													</ul>
												</div>
											</div>
										</div>
									</div>
									<!-- 导出弹框 -->
									<div class="am-modal am-modal-no-btn" tabindex="-1"
										id="export-modal">
										<div class="am-modal-dialog">
											<div class="am-modal-hd">
												教师信息表(两种Excel格式) <a href="javascript: void(0)"
													class="am-close am-close-spin" data-am-modal-close>&times;</a>
											</div>
											<div class="am-modal-bd">
												<div class="am-modal-actions-group">
													<ul class="am-list">
														<li class="am-modal-actions-header"><a
															href="${pageContext.request.contextPath }/teacher/export?mould=1&type=z">在职教师信息表.xlsx</a>
														</li>
														<li class="am-modal-actions-header"><a
															href="${pageContext.request.contextPath }/teacher/export?mould=2&type=z">在职教师信息表.xls</a>
														</li>
														<li class="am-modal-actions-header"><a
															href="${pageContext.request.contextPath }/teacher/export?mould=1&type=l">离职教师信息表.xlsx</a>
														</li>
														<li class="am-modal-actions-header"><a
															href="${pageContext.request.contextPath }/teacher/export?mould=2&type=l">离职教师信息表.xls</a>
														</li>
													</ul>
													</ul>
												</div>
											</div>
										</div>
									</div>
									<button type="button" id="doc-confirm-toggle"
										class="am-btn am-btn-danger">
										<span class="am-icon-trash-o"></span> 批量离职
									</button>
								</div>
							</div>
						</div>

						<div class="am-modal am-modal-confirm" tabindex="-1"
							id="my-confirm1">
							<div class="am-modal-dialog">
								<div class="am-modal-hd">批量删除</div>
								<div class="am-modal-bd">正在操作的是删除这些教师的信息，确定要继续吗？</div>
								<div class="am-modal-footer">
									<span class="am-modal-btn" data-am-modal-cancel>取消</span> <span
										class="am-modal-btn" data-am-modal-confirm>确定</span>
								</div>
							</div>
						</div>
						<form method="post" id="searchAddition"
							action="${pageContext.request.contextPath }/teacher/list?menuId=0">
							<input id="index" name="index" type="hidden">
							<div class="am-u-sm-12 am-u-md-3">
								<div class="am-form-group">
									<select required style="border-radius:5px;" onchange="typeSelect()"
										name="type">
										<option disabled selected>==请选择教师状态==</option>
										<option value="0">在职</option>
										<option value="1">离职</option>
									</select>
									<script type="text/javascript">
										function typeSelect(){
											$('#searchAddition').submit();
											/* $("#searchAddition").find("option[value=index]").attr("selected",true); */ 
										};
									</script>
								</div>
							</div>
						</form>
						<div class="am-u-sm-12 am-u-md-3">
							<form method="post"
								action="${pageContext.request.contextPath }/teacher/searchTeacher">
								<input type="hidden" name="menuId" value="0"/>
								<div class="am-u-sm-12 am-u-md-3">
									<div class="am-form-group">
										<select required name="searchWay" style="width:202px;height:32px;border-left:none;border-top:none;">
											<option value="">>>&nbsp;&nbsp;&nbsp;&nbsp;单击箭头</option>
											<option value="0">工号</option>
											<option value="1">姓名</option>
											<option value="2">证件</option>
										</select>
									</div>
								</div>
								<div class="am-input-group am-input-group-sm">
									<input onblur="this.value=this.value.replace(/\s+/g,'')" required name="index" type="text" class="am-form-field">
									<span class="am-input-group-btn">
										<button
											class="am-btn  am-btn-default am-btn-success tpl-am-btn-success am-icon-search"
											type="submit"></button>
									</span>
								</div>
							</form>
						</div>
					</div>
					<div class="am-g">
						<div class="am-u-sm-12">
							<form
								action="${pageContext.request.contextPath }/teacher/delTeacherByIds"
								id="teacher_list_delete_batch" method="post" class="am-form">
								<table
									class="am-table am-table-striped am-table-hover table-main">
									<thead>
										<tr>
											<th class="table-check"><input id="list_All_Select"
												type="checkbox" class="tpl-table-fz-check"></th>
											<!-- <th class="table-id">工号</th> -->
											<th style="width:60px" class="table-title">姓名</th>
											<th style="width:50px" class="table-type">性别</th>
											<th style="width:90px" class="table-type">教师类别</th>
											<th style="width:60px" class="table-type">职称</th>
											<th style="width:50px" class="table-type">状态</th>
											<th style="width:100px" class="table-author am-hide-sm-only">电话</th>
											<!-- <th class="table-date am-hide-sm-only">地址</th> -->
											<th style="width:60px" class="table-date am-hide-sm-only">学历</th>
											<th style="width:160px" class="table-set">身份证号</th>
										</tr>
									</thead>
									<tbody>
										<c:forEach items="${pageBean.list }" var="teacher">
											<tr>
												<td><input type="checkbox" name="teacherIds"
													value="${teacher.teacherId }"></td>
												<%--  <td>${teacher.teacherId }</td> --%>
												<td>${teacher.username }</td>
												<td><c:if test="${teacher.email==0 }">女</c:if><c:if test="${teacher.email==1 }">男</c:if></td>
												<td><c:if test="${teacher.identify==6 }">班主任</c:if><c:if test="${teacher.identify==2 }">授课教师</c:if></td>
												<td>${teacher.position }</td>
												<td><c:if test="${teacher.isfinish==0 }">在职</c:if><c:if test="${teacher.isfinish==1 }">离职</c:if></td>
												<td class="am-hide-sm-only">${teacher.telephone }</td>
												<%--  <td class="am-hide-sm-only">${teacher.address }</td> --%>
												<td class="am-hide-sm-only">${teacher.education }</td>
												<td class="am-hide-sm-only">${teacher.idcard }</td>

												<td>
													<div class="am-btn-toolbar">
														<div class="am-btn-group am-btn-group-xs">
															<button <c:if test="${teacher.isfinish==1 }">disabled</c:if> id="ed" type="button"
																class="am-btn am-btn-default am-btn-xs am-text-secondary"
																onclick="location='${pageContext.request.contextPath }/teacher/updateTeacherUI?teacherId=${teacher.teacherId }&menuId=0&type=0'">
																<span class="am-icon-pencil-square-o">
																</span> 编辑
															</button>
															<c:if test="${teacher.isfinish==0 }">
															<button id="del" type="button"
																onclick="delTeacher('${teacher.teacherId }')"
																class="am-btn am-btn-default am-btn-xs am-text-danger ">
																<span class="am-icon-trash-o"></span> 离职
															</button>
															</c:if>
															<c:if test="${teacher.isfinish==1 }">
															<button id="upd" type="button"
																onclick="updTeacher('${teacher.teacherId }')"
																class="am-btn am-btn-default am-btn-xs am-text-danger ">
																<span class="am-icon-trash-o"></span> 恢复
															</button>
															</c:if>
															<button <c:if test="${teacher.isfinish==1 }">disabled</c:if> id="initPwd" type="button"
																onclick="init('${teacher.teacherId }')"
																class="am-btn am-btn-default am-btn-xs am-hide-sm-only">
																<span class="am-icon-copy"></span>初始化密码
															</button>
															<button id="infoBut" type="button"
																onclick="location='${pageContext.request.contextPath }/teacher/lookInfo?index=${teacher.teacherId }&menuId=0'"
																class="am-btn am-btn-default am-btn-xs am-hide-sm-only">
																<span class="am-icon-copy">
																</span>查看详情
															</button>
															<div class="am-modal am-modal-confirm" tabindex="-1"
																id="my-confirm2">
																<div class="am-modal-dialog">
																	<div class="am-modal-hd">删除</div>
																	<div class="am-modal-bd">您确定要删除这个教师的信息吗？</div>
																	<div class="am-modal-footer">
																		<span class="am-modal-btn" data-am-modal-cancel>取消</span>
																		<span class="am-modal-btn" data-am-modal-confirm>确定</span>
																	</div>
																</div>
															</div>
															<div class="am-modal am-modal-confirm" tabindex="-1"
																id="my-confirm4">
																<div class="am-modal-dialog">
																	<div class="am-modal-hd">恢复</div>
																	<div class="am-modal-bd">您确定要恢复这个教师的信息吗？</div>
																	<div class="am-modal-footer">
																		<span class="am-modal-btn" data-am-modal-cancel>取消</span>
																		<span class="am-modal-btn" data-am-modal-confirm>确定</span>
																	</div>
																</div>
															</div>
															<div class="am-modal am-modal-confirm" tabindex="-1"
																id="my-confirm3">
																<div class="am-modal-dialog">
																	<div class="am-modal-hd">修改</div>
																	<div class="am-modal-bd">您确定要初始化这个教师的密码？</div>
																	<div class="am-modal-footer">
																		<span class="am-modal-btn" data-am-modal-cancel>取消</span>
																		<span class="am-modal-btn" data-am-modal-confirm>确定</span>
																	</div>
																</div>
															</div>
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
													href="${pageContext.request.contextPath }/teacher/${path }&page=1">首页</a></li>
												<li><a
													href="${pageContext.request.contextPath }/teacher/${path }&page=${pageBean.page -1}">«</a></li>
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
														href="${ pageContext.request.contextPath }/teacher/${path }&page=${i}">${i }</a></li>
												</c:if>
											</c:forEach>
											<c:if test="${pageBean.page ne pageBean.totalPage  }">
												<li><a
													href="${ pageContext.request.contextPath }/teacher/${path }&page=${pageBean.page +1}">»</a></li>
												<li><a
													href="${ pageContext.request.contextPath }/teacher/${path }&page=${pageBean.totalPage}">尾页</a></li>
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