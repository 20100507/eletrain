<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!doctype html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>公告信息列表</title>
    <meta name="description" content="公告信息列表">
    <meta name="keywords" content="index">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="renderer" content="webkit">
    <meta http-equiv="Cache-Control" content="no-siteapp" />
    <script src="${pageContext.request.contextPath}/assets/js/jquery.min.js"></script>
    <script src="${pageContext.request.contextPath}/assets/js/amazeui.min.js"></script>
<%--     <script src="${pageContext.request.contextPath}/assets/js/app.js"></script> --%>
    <link rel="icon" type="image/png" href="${pageContext.request.contextPath}/assets/i/favicon.png">
    <link rel="apple-touch-icon-precomposed" href="${pageContext.request.contextPath}/assets/i/app-icon72x72@2x.png">
    <meta name="apple-mobile-web-app-title" content="Amaze UI" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/amazeui.min.css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/admin.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/app.css">
    
    <script type="text/javascript">
	    function noticeInfo(id){
			location.href = "${pageContext.request.contextPath}/notice/toNoticeInfo?menuId=11&id="+id;
		}
    
    	$(function(){
    		$("#conditionButton").click(function(){
        		location.href="${pageContext.request.contextPath}/notice/getNoticeListByPageAndCondition?condition="+$("#condition").val();
        	});
    	});
    </script>
</head>

<body data-type="generalComponents">
	<jsp:include page="head.jsp"></jsp:include>
	<jsp:include page="side.jsp"></jsp:include>
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
							</div>
						</div>
						<div class="am-u-sm-12 am-u-md-3">
							<div class="am-input-group am-input-group-sm am-table-bordered">
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
                               <table class="am-table am-table-striped am-table-hover table-main am-table-centered" style="table-layout: fixed;">
                                   <thead>
                                       <tr>
                                           <th class="table-id">ID</th>
                                           <th class="table-title am-hide-sm-only">标题</th>
                                           <th class="table-author">发布人</th>
                                           <th class="table-date am-hide-sm-only">创建日期</th>
                                           <th class="table-set">操作</th>
                                       </tr>
                                   </thead>
                                   <tbody>
                                   	<c:forEach items="${pageBean.list}" var="notice">
                                        <tr style="height:45px">
                                            <td>${notice.noticeId}</td>
                                            <td class="am-hide-sm-only" title="${notice.title}" style="text-overflow: ellipsis; white-space: nowrap; overflow: hidden;">
                                            	<span display:inline-block;">
                                            		${notice.title}
                                            	</span>
                                            </td>
                                            <td>${notice.admin.username}</td>
                                            <td class="am-hide-sm-only"><fmt:formatDate value="${notice.createtime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                                            <td>
                                                <div class="am-btn-toolbar" style="display:inline-block;">
                                                    <div class="am-btn-group am-btn-group-xs">
									                    <button type="button" onclick="noticeInfo('${notice.noticeId}')" class="am-btn am-btn-default am-btn-xs am-text-secondary am-hide-sm-only">
									                   		<span class="am-icon-archive"> 详情</span>
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