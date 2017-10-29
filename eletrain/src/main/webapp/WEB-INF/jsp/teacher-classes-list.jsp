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
    <%-- <script src="${pageContext.request.contextPath}/assets/js/app.js"></script> --%>
    
    
    <script type="text/javascript">
		/* 全选 */
 		window.onload = function(){
			$("#list_All_Select").click(function(){
			    $("tbody input").prop("checked",this.checked);
			});
		}
</script>
<script type="text/javascript">
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
                                </div>
                            </div>
                        </div>
					</div>
                    <div class="am-g">
                        <div class="am-u-sm-12">
                            <form action="${pageContext.request.contextPath }/teacher/delTeacherByIds" id="teacher_list_delete_batch" method="post" class="am-form">
                                <table class="am-table am-table-striped am-table-hover table-main">
                                    <thead>
                                        <tr>
                                            <th style="width:15%" class="table-title">班级编号</th>
                                            <th style="width:20%" class="table-title">班级名称</th>
                                            <th style="width:100px" style="width:10%" class="table-type">班级状态</th>
                                            <th style="width:100px" style="width:15%" class="table-author am-hide-sm-only">报道时间</th>
                                            <th style="width:200px" style="width:20%" class="table-date am-hide-sm-only">授课时间段</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                    	<c:forEach items="${pageBean.list }" var="classes">
                                        <tr>
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
														<button id="infoBut" type="button"
															onclick="location='${pageContext.request.contextPath }/classes/lookInfo?classesId=${classes.classesId }&type=2&menuId=37'"
															class="am-btn am-btn-default am-btn-xs am-hide-sm-only">
															<span class="am-icon-copy">
															</span>查看详情
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
$(function(){
$("a[menuId='${menuId}']").trigger("click");
})
</script>
</html>