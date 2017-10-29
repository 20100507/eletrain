<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!doctype html>
<html>

<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>班级列表</title>
    <meta name="description" content="这是一个 index 页面">
    <meta name="keywords" content="index">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="renderer" content="webkit">
    <meta http-equiv="Cache-Control" content="no-siteapp" />
    <link rel="icon" type="image/png" href="${pageContext.request.contextPath}/assets/i/favicon.png">
    <link rel="apple-touch-icon-precomposed" href="${pageContext.request.contextPath}/assets/i/app-icon72x72@2x.png">
    <meta name="apple-mobile-web-app-title" content="Amaze UI" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/amazeui.min.css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/admin.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/app.css">
    
    
    <script src="${pageContext.request.contextPath}/assets/js/jquery.min.js"></script>
    <script src="${pageContext.request.contextPath}/assets/js/amazeui.min.js"></script>
 <%--    <script src="${pageContext.request.contextPath}/assets/js/app.js"></script> --%>
    
    
    <script type="text/javascript">
		/* 全选 */
 		window.onload = function(){
			$("#list_All_Select").click(function(){
			    $("tbody input").prop("checked",this.checked);
			});
		}
</script>
<script type="text/javascript">
		function edit(){
			$('#index').attr('value',$('#shuju').val());			
			$('#searchAddition').submit();
		}
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
	/* 导入 */	
	$(function() {
		var $modal = $('#exportClasses-modal');
		$modal.siblings('#exportClasses').on('click', function(e) {
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
	/* 批量删除班级 */
	$(function() {
		$('#doc-confirm-toggle').on('click', function() {
			$('#my-confirm1').modal({
				relatedTarget : this,
				onConfirm : function(options) {
					var classesIds = [];
					$('input[name="classesIds"]:checked').each(function() {  
						classesIds.push($(this).val());  
					});
					if (classesIds == "") {
						alert("对不起，您还没有选择要删除的选项！");
						return;
					}
					$.ajax({
						type : "post",
						traditional : true,
						url : "/classes/delClassesByIds?menuId=17",
						data : {
							"classesIds" : classesIds
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
	/* 通过id删除班级 */
	function delClasses(id) {
		$('#my-confirm2').modal({
			relatedTarget : this,
			onConfirm : function(options) {
				$.ajax({
					type : "post",
					traditional : true,
					url : "/classes/deleteClasses?menuId=17",
					data : {
						"classesId" : id
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
	/* 增加或删除学生 */
	function insertOrDelete(a) {
		$('#my-confirm3').modal({
			relatedTarget : this,
			onConfirm : function(options) {
				$.ajax({
					type : "post",
					url : "/classes/insertOrDeleteUI",
					data : {
						"classesId" : a
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
	/* 通过id恢复班级 */
	function updClasses(id) {
		$('#my-confirm3').modal({
			relatedTarget : this,
			onConfirm : function(options) {
				$.ajax({
					type : "post",
					traditional : true,
					url : "/classes/updClasses?menuId=17",
					data : {
						"classesId" : id
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
	<div>
        <div class="tpl-content-wrapper">
            <div class="tpl-portlet-components">
                <div class="portlet-title">
                    <div class="caption font-green bold">
                        <span class="am-icon-code"></span> 班级列表
                    </div>
                </div>
                <div class="tpl-block">
                    <div class="am-g">
                        <div class="am-u-sm-12 am-u-md-6">
                            <div class="am-btn-toolbar">
                                <div class="am-btn-group am-btn-group-xs">
									<button type="button"
										class="am-btn am-btn-default am-btn-success"
										onclick="location='${pageContext.request.contextPath}/classes/addClassesUI?menuId=17'">
										<span class="am-icon-plus"></span> 新增
									</button>
									<button type="button" class="am-btn am-btn-primary"
										data-am-modal="{target: '#doc-modal-1', closeViaDimmer: 0, width: 540, height: 360}">
										<span class="am-icon-archive"></span>导出
									</button>
									<div class="am-modal am-modal-no-btn" tabindex="-1"
										id="doc-modal-1">
										<div class="am-modal-dialog">
											<div class="am-modal-hd">
												班级信息表(两种Excel格式) <a href="javascript: void(0)"
													class="am-close am-close-spin" data-am-modal-close>&times;</a>
											</div>
											<div class="am-modal-bd">
												<div class="am-modal-actions-group">
													<ul class="am-list">
														<li class="am-modal-actions-header"><a
															href="${pageContext.request.contextPath }/classes/exportClasses?mould=1&type=z">正常班级信息表.xlsx</a>
														</li>
														<li class="am-modal-actions-header"><a
															href="${pageContext.request.contextPath }/classes/exportClasses?mould=2&type=z">正常班级信息表.xls</a>
														</li>
														<li class="am-modal-actions-header"><a
															href="${pageContext.request.contextPath }/classes/exportClasses?mould=1&type=l">已结课班级信息表.xlsx</a>
														</li>
														<li class="am-modal-actions-header"><a
															href="${pageContext.request.contextPath }/classes/exportClasses?mould=2&type=l">已结课班级信息表.xls</a>
														</li>
													</ul>
												</div>
											</div>
										</div>
									</div>
									<button type="button" id="doc-confirm-toggle"
										class="am-btn am-btn-danger">
										<span class="am-icon-trash-o"></span> 批量结课
									</button>
                                </div>
                            </div>
                        </div>
                       
                        <div class="am-modal am-modal-confirm" tabindex="-1"
							id="my-confirm1">
							<div class="am-modal-dialog">
								<div class="am-modal-hd">批量删除</div>
								<div class="am-modal-bd">正在操作的是删除这些班级的信息，确定要继续吗？</div>
								<div class="am-modal-footer">
									<span class="am-modal-btn" data-am-modal-cancel>取消</span> <span
										class="am-modal-btn" data-am-modal-confirm>确定</span>
								</div>
							</div>
						</div>
						<form method="post" id="searchAddition"
							action="${pageContext.request.contextPath }/classes/list?menuId=17">
							<input id="index" name="index" type="hidden">
							<div class="am-u-sm-12 am-u-md-3">
								<div class="am-form-group">
									<select onchange="typeSelect()"
										name="type" required style="border-radius:5px;" >
										<option disabled selected>==请选择班级状态==</option>
										<option value="0">正常</option>
										<option value="1">已结课</option>
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
								action="${pageContext.request.contextPath }/classes/searchClasses?menuId=17">
								<div class="am-u-sm-12 am-u-md-3">
									<div class="am-form-group">
										<select required name="searchWay" style="width:202px;height:32px;border-left:none;border-top:none;">
											<option value="">>>&nbsp;&nbsp;&nbsp;&nbsp;点击箭头</option>
											<option value="0">编号</option>
											<option value="1">名称</option>
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
                            <form action="${pageContext.request.contextPath }/teacher/delTeacherByIds" id="teacher_list_delete_batch" method="post" class="am-form">
                                <table class="am-table am-table-striped am-table-hover table-main">
                                    <thead>
                                        <tr>
                                            <th class="table-check"><input id="list_All_Select" type="checkbox" class="tpl-table-fz-check"></th>
                                            <th style="width:100px" class="table-title">班级编号</th>
                                            <th style="width:100px" class="table-title">班级名称</th>
                                            <th style="width:100px" class="table-type">班级状态</th>
                                            <th style="width:100px" class="table-author am-hide-sm-only">报道时间</th>
                                            <th style="width:200px" class="table-date am-hide-sm-only">授课时间段</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                    	<c:forEach items="${pageBean.list }" var="classes">
                                        <tr>
                                            <td><input type="checkbox" name="classesIds" value="${classes.classesId }"></td>
                                            <td>${classes.classesNo }</td>
                                            <td>${classes.cname }</td>
                                            <td>
                                            <c:if test="${classes.capacity==0 }">正常</c:if>
                                            <c:if test="${classes.capacity==1 }">已结课</c:if>
                                            </td>
                                            <td class="am-hide-sm-only">${classes.createtime }</td>
                                            <td class="am-hide-sm-only">${classes.starttime }</td>
                                             <td>
                                                 <div class="am-btn-toolbar">
                                                    <div class="am-btn-group am-btn-group-xs">
														<button <c:if test="${classes.capacity==1 }">disabled</c:if> id="ed" type="button"
															class="am-btn am-btn-default am-btn-xs am-text-secondary"
															onclick="location='${pageContext.request.contextPath }/classes/updateClassesUI?classesId=${classes.classesId }&menuId=17&type=0'">
															<span class="am-icon-pencil-square-o">
															</span> 编辑
														</button>
														 <c:if test="${classes.capacity==0 }">
															<button id="del" type="button"
																onclick="delClasses('${classes.classesId }')"
																class="am-btn am-btn-default am-btn-xs am-text-danger ">
																<span class="am-icon-trash-o"></span> 结课
															</button>
														 </c:if>
														 <c:if test="${classes.capacity==1 }">
															 <button id="del" type="button"
																onclick="updClasses('${classes.classesId }')"
																class="am-btn am-btn-default am-btn-xs am-text-danger ">
																<span class="am-icon-trash-o"></span> 恢复
															</button>
														</c:if>
														<button <c:if test="${classes.capacity==1 }">disabled</c:if> id="infoBut" type="button"
															class="am-btn am-btn-default am-btn-xs am-hide-sm-only"
															data-am-modal="{target: '#${classes.classesId }', closeViaDimmer: 0, width: 540, height: 225}">
															<span class="am-icon-copy"> </span>添加或删除学生
														</button>
														<div class="am-modal am-modal-no-btn" tabindex="-1"
															id="${classes.classesId }">
															<div class="am-modal-dialog">
																<div class="am-modal-hd">
																	添加或删除学生 <a href="javascript: void(0)"
																		class="am-close am-close-spin" data-am-modal-close>&times;</a>
																</div>
																<div class="am-modal-bd">
																	<div class="am-modal-actions-group">
																		<ul class="am-list">
																			<li class="am-modal-actions-header"><a
																				href="${pageContext.request.contextPath }/classes/insertOrDeleteUI?classesId=${classes.classesId }&menuId=17&adType=0&type=0">添加学生</a>
																			</li>
																			<li class="am-modal-actions-header"><a
																				href="${pageContext.request.contextPath }/classes/insertOrDeleteUI?classesId=${classes.classesId }&menuId=17&adType=1&type=0">删除学生</a>
																			</li>
																		</ul>
																	</div>
																</div>
															</div>
														</div>
													
														<button id="infoBut" type="button"
															onclick="location='${pageContext.request.contextPath }/classes/lookInfo?classesId=${classes.classesId }&type=0&menuId=17'"
															class="am-btn am-btn-default am-btn-xs am-hide-sm-only">
															<span class="am-icon-copy">
															</span>查看详情
														</button>
														<div class="am-modal am-modal-confirm" tabindex="-1"
															id="my-confirm2">
															<div class="am-modal-dialog">
																<div class="am-modal-hd">删除</div>
																<div class="am-modal-bd">您确定要删除这个班级的信息吗？</div>
																<div class="am-modal-footer">
																	<span class="am-modal-btn" data-am-modal-cancel>取消</span>
																	<span class="am-modal-btn" data-am-modal-confirm>确定</span>
																</div>
															</div>
														</div>
														<div class="am-modal am-modal-confirm" tabindex="-1"
															id="my-confirm3">
															<div class="am-modal-dialog">
																<div class="am-modal-hd">恢复</div>
																<div class="am-modal-bd">您确定要恢复这个班级的信息吗？</div>
																<div class="am-modal-footer">
																	<span class="am-modal-btn" data-am-modal-cancel>取消</span>
																	<span class="am-modal-btn" data-am-modal-confirm>确定</span>
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
                                            <li><a href="${pageContext.request.contextPath }/classes/${path }&page=1">首页</a></li>
                                            <li><a href="${pageContext.request.contextPath }/classes/${path }&page=${pageBean.page -1}">«</a></li>
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
												<li><a href="${ pageContext.request.contextPath }/classes/${path }&page=${i}">${i }</a></li>
											</c:if>
										</c:forEach>
										<c:if test="${pageBean.page ne pageBean.totalPage  }">
										<li><a href="${ pageContext.request.contextPath }/classes/${path }&page=${pageBean.page +1}">»</a></li>
										<li><a href="${ pageContext.request.contextPath }/classes/${path }&page=${pageBean.totalPage}">尾页</a></li>
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