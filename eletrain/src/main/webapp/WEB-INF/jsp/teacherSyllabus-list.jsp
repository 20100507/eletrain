<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!doctype html>
<html>

<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>课表列表</title>
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
	<div>
        <div class="tpl-content-wrapper">
            <div class="tpl-portlet-components">
                <div class="portlet-title">
                    <div class="caption font-green bold">
                        <span class="am-icon-code"></span> 课表列表
                    </div>
                </div>
                	<div class="am-g">
                        <div class="am-u-sm-12 am-u-md-6">
                            <div class="am-btn-toolbar">
                                <div class="am-btn-group am-btn-group-xs">
									<button style="visibility:hidden" type="button"
										class="am-btn am-btn-primary js-modal-open">
									</button>
                                </div>
                            </div>
                        </div>
					<form method="post"
						action="${pageContext.request.contextPath }/syllabus/getSyllabusByTeacher?menuId=39">   
						<div class="am-u-sm-12 am-u-md-3">
							<div class="am-form-group">
								<select onchange="search()" id="sear" required name="searchWay" style="border-radius:5px;">
									<option value="">===请选择查询方式===</option>
									<option value="3">查询所有</option>
									<option value="0">班级名称</option>
									<!-- <option value="1">教师姓名</option> -->
									<option value="2">日期（2017-09-10）</option>
								</select>
								<script type="text/javascript">
									function search(){
										if($('#sear').val()==3){
											window.location='${pageContext.request.contextPath }/syllabus/getSyllabusByTeacher?menuId=39';
										}
									}
								</script>
							</div>
						</div>
						<div class="am-u-sm-12 am-u-md-3">
							<div class="am-input-group am-input-group-sm">
								<input onblur="this.value=this.value.replace(/\s+/g,'')" required name="index" type="text" class="am-form-field">
								<span class="am-input-group-btn">
									<button
										class="am-btn  am-btn-default am-btn-success tpl-am-btn-success am-icon-search"
										type="submit"></button>
								</span>
							</div>
						</div>
					</form>
					</div>
                    <div class="am-g">
                        <div class="am-u-sm-12">
                            <form action="${pageContext.request.contextPath }/teacher/delTeacherByIds" id="teacher_list_delete_batch" method="post" class="am-form">
                                <table class="am-table am-table-striped am-table-hover table-main">
                                    <thead>
                                        <tr>
                                            <th style="width:100px" class="table-title">日期</th>
                                            <th class="table-type">班级</th>
                                            <th class="table-author am-hide-sm-only">08.30-11.30</th>
                                            <th class="table-date am-hide-sm-only">13.30-16.30</th>
                                            <th class="table-set">17.30-20.30</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                    	<c:forEach items="${pageBean.list }" var="cdv">
                                        <tr>
                                            <td>${cdv.date }</td>
                                            <td>${cdv.classes.cname }</td>
                                            <td class="am-hide-sm-only"><c:if test="${cdv.amCourse!=null }">${cdv.amCourse.cname }(${cdv.amTeacher.username })</c:if><c:if test="${cdv.amCourse==null }">*无课*</c:if></td>
                                            <td class="am-hide-sm-only"><c:if test="${cdv.pmCourse!=null }">${cdv.pmCourse.cname }(${cdv.pmTeacher.username })</c:if><c:if test="${cdv.pmCourse==null }">*无课*</c:if></td>
                                            <td class="am-hide-sm-only"><c:if test="${cdv.niCourse!=null }">${cdv.niCourse.cname }(${cdv.niTeacher.username })</c:if><c:if test="${cdv.niCourse==null }">*无课*</c:if></td>
                                            
                                             <td>
                                                 <div class="am-btn-toolbar">
                                                    <div class="am-btn-group am-btn-group-xs">
														
														<div class="am-modal am-modal-confirm" tabindex="-1"
															id="my-confirm2">
															<div class="am-modal-dialog">
																<div class="am-modal-hd">删除</div>
																<div class="am-modal-bd">您确定要删除这个课表的信息吗？</div>
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
                                            <li><a href="${pageContext.request.contextPath }/syllabus/${path }&page=1">首页</a></li>
                                            <li><a href="${pageContext.request.contextPath }/syllabus/${path }&page=${pageBean.page -1}">«</a></li>
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
												<li><a href="${ pageContext.request.contextPath }/syllabus/${path }&page=${i}">${i }</a></li>
											</c:if>
										</c:forEach>
										<c:if test="${pageBean.page ne pageBean.totalPage  }">
										<li><a href="${ pageContext.request.contextPath }/syllabus/${path }&page=${pageBean.page +1}">»</a></li>
										<li><a href="${ pageContext.request.contextPath }/syllabus/${path }&page=${pageBean.totalPage}">尾页</a></li>
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