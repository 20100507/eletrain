<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!doctype html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>管理员公告列表</title>
    <meta name="description" content="管理员公告列表">
    <meta name="keywords" content="index">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="renderer" content="webkit">
    <meta http-equiv="Cache-Control" content="no-siteapp" />
    <script src="${pageContext.request.contextPath}/assets/js/jquery.min.js"></script>
    <script src="${pageContext.request.contextPath}/assets/js/amazeui.min.js"></script>
  <%--   <script src="${pageContext.request.contextPath}/assets/js/app.js"></script> --%>
    <link rel="icon" type="image/png" href="${pageContext.request.contextPath}/assets/i/favicon.png">
    <link rel="apple-touch-icon-precomposed" href="${pageContext.request.contextPath}/assets/i/app-icon72x72@2x.png">
    <meta name="apple-mobile-web-app-title" content="Amaze UI" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/amazeui.min.css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/admin.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/app.css">
    
    <script type="text/javascript">
	    function editNotice(id){
			location.href = "${pageContext.request.contextPath}/notice/toUpdateNotice?menuId=36&id="+id;
		}
	    
	    function noticeInfo(id){
			location.href = "${pageContext.request.contextPath}/notice/toNoticeInfo?menuId=36&id="+id;
		}
    
    	function deleteBox(id){
    		$("#confirmBox1").html("删除");
    		$("#confirmBox2").html("确定要删除吗？？？？");
 	    	$('#my-confirm').modal({
 	        	relatedTarget: this,
 	        	onConfirm: function(options){
 	        		location.href="${pageContext.request.contextPath}/notice/deleteNotice?menuId=36&id="+id;
 	        	},
 	       		// closeOnConfirm: false,
 	      		onCancel: function(){
 	       		}
 	  		});
    	}
    
	    $(function(){
	    	//window.open("getExcelList","_blank");
	    	  $('#deleteMultipleNoticesBuntton').
	    	    on('click', function(){
	    	      $("#confirmBox1").html("批量删除");
	    	      $("#confirmBox2").html("确定要删除所有得选项吗？？？？");
	    	      $('#my-confirm').modal({
	    	        relatedTarget: this,
	    	        onConfirm: function(options){
	    	        	var ids = $('input[name="ids"]:checked').val();
	    	        	if(typeof(ids) != 'undefined'){
	    	        		$("#deleteMultipleNoticesForm").submit();
	    	        	}else{
	    	        		$(".am-modal-hd").html("您没有选择删除项！！！");
	    	        		$("#my-alert").modal();
	    	        	}
	    	        	
	    	        },
	    	       // closeOnConfirm: false,
	    	       onCancel: function(){
	    	       }
	    	      });
	    	  });
	    	  
	      	$("#toAddNotice").click(function(){
	    		location.href="${pageContext.request.contextPath}/notice/toAddNotice?menuId=36"
	    	});
	      	
	    	$("#checkAllId").click(function(){
	    		$("tbody input").prop("checked",this.checked);
	    	});
	    	
	    	$("#conditionButton").click(function(){
	    		location.href="${pageContext.request.contextPath}/notice/getNoticeListByPageAndCondition?menuId=36&condition="+$("#condition").val();
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
                        <span class="am-icon-code">公告列表</span>
                    </div>
                </div>
                <div class="tpl-block">
					<div class="am-g">
						<div class="am-u-sm-12 am-u-md-6">
							<div class="am-btn-toolbar">
								<div class="am-btn-group am-btn-group-xs">
									<button type="button" id="toAddNotice"
										class="am-btn am-btn-default am-btn-success">
										<span class="am-icon-plus"> 新增</span>
									</button>
									<button type="button" id="deleteMultipleNoticesBuntton" 
										class="am-btn am-btn-default am-btn-danger">
										<span class="am-icon-trash-o"> 批量删除</span>
									</button>
								</div>
							</div>
						</div>
						<div class="am-u-sm-12 am-u-md-3">
							<div class="am-input-group am-input-group-sm">
								<input type="text" id="condition" name="condition" placeholder="请输入公告发布人" class="am-form-field"> 
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
                            <form id="deleteMultipleNoticesForm" class="am-form" action="${pageContext.request.contextPath}/notice/deleteMultipleNotices">
                                <table class="am-table am-table-striped am-table-hover table-main am-table-centered" style="table-layout: fixed;">
                                    <thead>
                                        <tr>
                                            <th class="table-check"><input type="checkbox" id="checkAllId" class="tpl-table-fz-check"></th>
                                            <th class="table-id">ID</th>
                                           	<th class="table-title am-hide-sm-only" style="max-width: 210px">标题</th>
                                          	<th class="table-author">发布人</th>
                                          	<th class="table-date am-hide-sm-only">创建日期</th>
                                          	<th class="table-id">是否发布</th>
                                           	<th class="table-set">操作</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                    	<c:forEach items="${pageBean.list}" var="notice">
	                                        <tr>
	                                        	<td><input type="checkbox" name="ids" value="${notice.noticeId}"></td>
	                                            <td>${notice.noticeId}</td>
	                                            <td class="table-title am-hide-sm-only" title="${notice.title}" style="text-overflow: ellipsis; white-space: nowrap; overflow: hidden;">
	                                            	<span style="width:15em;display:inline-block;white-space:nowrap;overflow:hidden;text-overflow:ellipsis;">
	                                            		${notice.title}
	                                            	</span>
	                                            </td>
	                                            <td>${notice.admin.username}</td>
	                                            <td class="am-hide-sm-only"><fmt:formatDate value="${notice.createtime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
	                                            <td>
	                                            	<c:if test="${notice.isReleased == 1}">
	                                            		是
	                                            	</c:if>
	                                            	<c:if test="${notice.isReleased == 0}">
	                                            		否
	                                            	</c:if>
	                                            </td>
	                                            <td>
	                                                <div class="am-btn-toolbar" style="display:inline-block;">
	                                                    <div class="am-btn-group am-btn-group-xs">
										                    <button type="button" onclick="noticeInfo('${notice.noticeId}')" class="am-btn am-btn-default am-btn-xs am-text-secondary am-hide-sm-only">
										                   		<span class="am-icon-archive"> 详情</span>
										                    </button>
	                                                        <button type="button" onclick="editNotice('${notice.noticeId}')" class="am-btn am-btn-default am-btn-xs am-text-secondary">
	                                                        	<span class="am-icon-pencil-square-o"> 编辑</span>
	                                                        </button>
										                    <button type="button" onclick="deleteBox('${notice.noticeId}')" class="am-btn am-btn-default am-btn-xs am-text-danger am-hide-sm-only">
										                   		<span class="am-icon-trash-o"> 删除</span>
										                    </button>
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
													href="${pageContext.request.contextPath}/notice/${path}&page=1">首页</a></li>
												<li><a
													href="${pageContext.request.contextPath}/notice/${path}&page=${pageBean.page -1}">«</a></li>
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
														href="${pageContext.request.contextPath}/notice/${path}&page=${i}">${i}</a></li>
												</c:if>
											</c:forEach>
											<c:if test="${pageBean.page ne pageBean.totalPage}">
												<li><a
													href="${ pageContext.request.contextPath}/notice/${path}&page=${pageBean.page +1}">»</a></li>
												<li><a
													href="${ pageContext.request.contextPath}/notice/${path}&page=${pageBean.totalPage}">尾页</a></li>
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