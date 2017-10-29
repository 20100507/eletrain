<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>流程详情</title>
<meta name="description" content="流程详情">
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
</script>
<body data-type="generalComponents">
	<jsp:include page="head.jsp"></jsp:include>
	<jsp:include page="side.jsp"></jsp:include>
	<div class="tpl-content-wrapper">
		<div class="tpl-portlet-components">
			<div class="portlet-title">
				<div class="caption font-green bold">
					<span class="am-icon-code"></span>审批列表
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
					<div class="am-u-sm-12 am-u-md-3">
						<div class="am-form-group"></div>
					</div>
				</div>
				<div class="am-g">
					<div class="am-u-sm-12">
						<table class="am-table am-table-striped am-table-hover table-main">
							<thead>
								<tr>
									<th><input type="checkbox" id="selectAll"></th>
									<th class="table-title">审批人</th>
									<th class="table-author am-hide-sm-only">审批时间</th>
									<th class="table-date am-hide-sm-only">是否通过</th>
									<th class="table-date am-hide-sm-only">审批意见</th>
								</tr>
							</thead>
							<c:forEach varStatus="count" items="${pageBean.list }" var="process">
								<tbody>
									<tr>
										<td><input id="ck" type="checkbox" name="ids"
											value=${process.approveinfoId }></td>
										<td>${process.username }</td>
										<td>
										<fmt:formatDate value="${process.approvedate }" pattern="yyyy-MM-dd HH:mm:ss"/>
										</td>
										<td>
											<c:choose>
                                                  <c:when test="${process.approval == true }">
                                                   		通过
                                                   </c:when>
                                                       <c:otherwise>
                                                       	拒绝
                                                      </c:otherwise>
                                              </c:choose>
										</td>
										<td>${process.comment}</td>
										<td>
											<div class="am-btn-toolbar">
												<div class="am-btn-group am-btn-group-xs">
													<button id="infoBut" type="button"
													<c:if test="${taskVo.applicationVo.status != 1}">
													    disabled="disabled"</c:if>
														onclick="showWindow('${taskVo.applicationVo.pdId }','${taskVo.applicationVo.applicationId }')"
														class="am-btn am-btn-default am-btn-xs am-hide-sm-only">
														<span class="am-icon-copy"></span>流程进展图
													</button>
												</div>
											</div>
										</td>
									</tr>
								</tbody>
							</c:forEach>
						</table>
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<button type="button" class="am-btn am-btn-warning"
										       onclick="javascript:history.back(-1);">返回</button>
						<div class="am-cf">
							<div class="am-fr">
								<ul class="am-pagination tpl-pagination">
									<c:if test="${pageBean.page ne 1}">
										<li><a
											href="${pageContext.request.contextPath }/plan/${url }&page=1&menuId=15">首页</a></li>
										<li><a
											href="${pageContext.request.contextPath }/plan/${url }&page=${pageBean.page -1}&menuId=15">«</a></li>
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
												href="${ pageContext.request.contextPath }/plan/${url }&page=${i}&menuId=15">${i }</a></li>
										</c:if>
									</c:forEach>
									<c:if test="${pageBean.page < pageBean.totalPage  }">
										<li><a
											href="${ pageContext.request.contextPath }/plan/${url }&page=${pageBean.page +1}&menuId=15">»</a></li>
										<li><a
											href="${ pageContext.request.contextPath }/plan/${url }&page=${pageBean.totalPage}&menuId=15">尾页</a></li>
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
            var modalReturnValue = myShowModalDialog(url,window, 1000, 1945);
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