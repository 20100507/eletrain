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
<title>流程执行列表</title>
<meta name="description" content="流程执行列表">
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
	
	// 提交流程
	function edit(id,taskId) {
		var menuId = 28;
		if("${user.identify}" == "3"){
			menuId = 28;
		}else if("${user.identify}" == "4"){
			menuId = 30;
		}else if("${user.identify}" == "5"){
			menuId = 32;
		}
		window.location.href = "/process/submitFlowPage?id=" + id +"&"+"taskId="+taskId+"&menuId="+menuId;
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
		$('#searchBut').click(
				function() {
					var menuId = 28;
					var pname = $("#search").val();
					if("${user.identify}" == "3"){
						menuId = 28;
					}else if("${user.identify}" == "4"){
						menuId = 30;
					}else if("${user.identify}" == "5"){
						menuId = 32;
					}
					window.location.href = "/process/getProcessByName?planName="
							+ encodeURI(encodeURI(pname))+"&menuId="+menuId;
				});
	   });
	
	// 查看流程详情
	function info(id) {
		window.location.href = "/process/processInfo?appId=" + id;
	}
</script>
<body data-type="generalComponents">
	<jsp:include page="head.jsp"></jsp:include>
	<jsp:include page="side.jsp"></jsp:include>
	<div class="tpl-content-wrapper">
		<div class="tpl-portlet-components">
			<div class="portlet-title">
				<div class="caption font-green bold">
					<span class="am-icon-code"></span>待我审批列表
				</div>
			</div>
			<div class="tpl-block">
				<div class="am-g">
					<div class="am-u-sm-12 am-u-md-6">
						<div class="am-btn-toolbar">
							<div class="am-btn-group am-btn-group-xs">
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
							</div>
						</div>
					</div>
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
									<th class="table-title">方案申请人</th>
									<th class="table-author am-hide-sm-only">申请时间</th>
									<th class="table-date am-hide-sm-only">审批状态</th>
									<th class="table-set">操作</th>
								</tr>
							</thead>
							<c:forEach varStatus="count" items="${pageBean.list }" var="taskVo">
							   		<tbody>
									<tr>
										<td><input id="ck" type="checkbox" name="ids"
											value=${taskVo.applicationVo.applicationId }></td>
										<td>${fn:substring(taskVo.applicationVo.title,0,6) }</td>
										<td>${taskVo.applicationVo.username }</td>
										<td class="am-hide-sm-only"><fmt:formatDate
												value="${taskVo.applicationVo.applyDate }" pattern="yyyy-MM-dd" /></td>
										<c:if test="${taskVo.applicationVo.status == 0}">
											<td class="am-hide-sm-only">未提交</td>
										</c:if>
										<c:if test="${taskVo.applicationVo.status == 1}">
											<td class="am-hide-sm-only">审批中</td>
										</c:if>
										<c:if test="${taskVo.applicationVo.status == 2}">
											<td class="am-hide-sm-only">未通过</td>
										</c:if>
										<c:if test="${taskVo.applicationVo.status == 3}">
											<td class="am-hide-sm-only">通过</td>
										</c:if>
										<td>
											<div class="am-btn-toolbar">
												<div class="am-btn-group am-btn-group-xs">

													<button id="ed" type="button"
														onclick="edit('${taskVo.applicationVo.applicationId }','${taskVo.task.id }')"
														class="am-btn am-btn-default am-btn-xs am-text-secondary">
														<span class="am-icon-pencil-square-o"></span>审批
													</button>
													<button id="infoBut" type="button"
														onclick="info('${taskVo.applicationVo.applicationId }')"
														class="am-btn am-btn-default am-btn-xs am-hide-sm-only">
														<span class="am-icon-copy"></span>流程详情
													</button>
													<button id="infoBut" type="button"
														onclick="showWindow('${taskVo.applicationVo.pdId }','${taskVo.applicationVo.applicationId }')"
														<c:if test="${taskVo.applicationVo.status != 1}">
													    disabled="disabled"</c:if>
														class="am-btn am-btn-default am-btn-xs am-hide-sm-only">
														<span class="am-icon-copy"></span>流程进展图
													</button>
													<button onclick="b('${taskVo.applicationVo.planId }')" type="button"
														class="am-btn am-btn-default js-modal-open">
														<span class="am-icon-archive"></span> 下载方案详情文档
													</button>
													<div class="am-modal am-modal-no-btn" tabindex="-1"
														id="modaldownload${taskVo.applicationVo.planId }">
														<div style="width: 800px;" class="am-modal-dialog">
															<div class="am-modal-hd">
																方案文档下载 <a href="javascript: void(0)"
																	class="am-close am-close-spin" data-am-modal-close>&times;</a>
															</div>
															<div class="am-modal-bd">
																<div class="am-modal-actions-group">
																	<ul id="downul${taskVo.applicationVo.planId }" class="am-list">
																	</ul>
																</div>
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
						<c:if test="${user.identify}=='3' ">
							<c:set property="menuId2" value="28"></c:set>
						</c:if>
						<c:if test="${user.identify}=='4' ">
							<c:set property="menuId2" value="30"></c:set>
						</c:if>
						<c:if test="${user.identify}=='5' ">
							<c:set property="menuId2" value="32"></c:set>
						</c:if>

						<div class="am-cf">
							<div class="am-fr">
								<ul class="am-pagination tpl-pagination">
									<c:if test="${pageBean.page ne 1}">
										<li><a
											href="${pageContext.request.contextPath }/process/${url }&page=1&menuId=${menuId2}">首页</a></li>
										<li><a
											href="${pageContext.request.contextPath }/process/${url }&page=${pageBean.page -1}&menuId=${menuId2}">«</a></li>
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
												href="${ pageContext.request.contextPath }/process/${url }&page=${i}&menuId=${menuId2}">${i }</a></li>
										</c:if>
									</c:forEach>
									<c:if test="${pageBean.page < pageBean.totalPage  }">
										<li><a
											href="${ pageContext.request.contextPath }/process/${url }&page=${pageBean.page +1}&menuId=${menuId2}">»</a></li>
										<li><a
											href="${ pageContext.request.contextPath }/process/${url }&page=${pageBean.totalPage}&menuId=${menuId2}">尾页</a></li>
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
	//浏览器兼容弹框
	function showWindow(pdId,applicationId){
            var url = "${pageContext.request.contextPath}/process/showImage?pdId="+pdId+"&applicationId="+applicationId+"&r="+Math.random();
            var modalReturnValue = myShowModalDialog(url,window, 976, 333);
        }
            //弹出框google Chrome执行的是open
        function myShowModalDialog(url,args,width, height) {
            var tempReturnValue;
            if (navigator.userAgent.indexOf("Chrome") > 0) {
                var paramsChrome = 'height=' + height + ', width=' + width + ', top=' + (((window.screen.height - height) / 2) - 50) +
                    ',left=' + ((window.screen.width - width) / 2) + ',toolbar=no, menubar=no, scrollbars=no, resizable=no, location=no, status=no';
                window.open(url, "newwindow", paramsChrome);
            }
            else {
                var params = 'dialogWidth:' + width + 'px;dialogHeight:' + height + 'px;status:no;dialogLeft:'
                            + ((window.screen.width - width) / 2) + 'px;dialogTop:' + (((window.screen.height - height) / 2) - 50) + 'px;';
                tempReturnValue = window.showModalDialog(url, args, params);
            }
            return tempReturnValue;
        }
</script>
</html>