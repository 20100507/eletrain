<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<!doctype html>
<html>

<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>增加或编辑班级</title>
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
	    
		window.onload = function() {
			$(".selectTeacherIds").bind("click", function() {
				$(this).children(":first").attr("checked", true);
			});
		};
	</script>
    
</head>

<body data-type="generalComponents">
<jsp:include page="head.jsp"></jsp:include>
	<jsp:include page="side.jsp"></jsp:include>
        <div class="tpl-content-wrapper">
            <div class="tpl-portlet-components">
                <div class="portlet-title">
                    <div class="caption font-green bold">
                        <span class="am-icon-code"></span> 新增/编辑班级
                    </div>
                </div>
                <div class="tpl-block ">
                    <div class="am-g tpl-amazeui-form">
                        <div class="am-u-sm-12 am-u-md-9">
                            <form method="post" class="am-form am-form-horizontal" action="${pageContext.request.contextPath }/classes/${ classes== null ? 'add' : 'update' }ClassesByClassesTeacher?menuId=19">
                            	<input type="hidden" name="classesId" value="${classes.classesId }">
                            	<input type="hidden" name="type" value="${type}">
                            	
                                <div class="am-form-group">
                                    <label for="user-name" class="am-u-sm-3 am-form-label">班级编号</label>
                                    <div class="am-u-sm-9">
                                        <input required name="classesNo" value="${classes.classesNo }" type="text" id="user-name" placeholder="请输入班级的编号">
                                    </div>
                                </div>
                                <div class="am-form-group">
                                    <label for="user-name" class="am-u-sm-3 am-form-label">班级名称</label>
                                    <div class="am-u-sm-9">
                                        <input required name="cname" value="${classes.cname }" type="text" id="user-name" placeholder="输入班级的名字">
                                    </div>
                                </div>
                                <div class="am-form-group">
                                    <label for="user-name1" class="am-u-sm-3 am-form-label">班主任</label>
									<div class="am-u-sm-9">
										<select required name="teacherIds">
											<!-- <option disabled selected value="">===请选择班主任===</option> -->
											<c:forEach items="${classesTeacherList }"
											var="classesTeacher">
											<option value="${classesTeacher.teacherId }">${classesTeacher.username }(${classesTeacher.loginname })</option>
										</c:forEach>
									</select>
									</div>
								</div>
                                <div class="am-form-group">
                                    <label for="user-name" class="am-u-sm-3 am-form-label">报到时间</label>
                                    <div class="am-u-sm-9">
                                        <input required name="createtime" value="${classes.createtime }" type="text" id="user-name" placeholder="请输入班级报到时间(如：1970-01-01)" data-am-datepicker>
                                    </div>
                                </div>
                                <div class="am-form-group">
                                    <label for="user-name" class="am-u-sm-3 am-form-label">授课起始时间</label>
                                    <div class="am-u-sm-9">
                                        <input required name="startTime" value="${startTime }" type="text" id="user-name" placeholder="请输入授课起始时间(如：1970-01-01)" data-am-datepicker>
                                    </div>
                                </div>
                                <div class="am-form-group">
                                    <label for="user-name" class="am-u-sm-3 am-form-label">授课终止时间</label>
                                    <div class="am-u-sm-9">
                                        <input required name="endTime" value="${endTime }" type="text" id="user-name" placeholder="请输入授课终止时间(如：1970-01-01)" data-am-datepicker>
                                    </div>
                                </div>
                                <%-- <div class="am-form-group">
                                    <label for="user-name2" class="am-u-sm-3 am-form-label">授课教师</label>
									<div class="am-u-sm-9">
										<div class="am-form-group">
											<select required name="teacherIds" multiple="multiple">
												<option disabled value="">--------------------------按住Ctrl键可以多选--------------------------</option>
												<c:forEach items="${teacherList }" var="teacher">
													<option value="${teacher.teacherId }" <c:if test="${fn:contains(tnameList,teacher.username) }">selected</c:if>>${teacher.username }(${teacher.loginname })</option>
												</c:forEach>
											</select>
										</div>
									</div>
								</div> --%>
                                <div class="am-form-group">
                                     <div class="am-u-sm-9 am-u-sm-push-3">
										<button type="submit" class="am-btn am-btn-primary">保存修改</button>
                                       	<button type="button" class="am-btn am-btn-primary" onclick="javascript:history.back(-1);">返回</button>
                                    </div>
                                </div>
                            </form>
                        </div>
                    </div>
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