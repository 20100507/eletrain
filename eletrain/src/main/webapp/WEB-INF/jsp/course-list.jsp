<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!doctype html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>Amaze UI Admin index Examples</title>
    <meta name="description" content="这是一个 index 页面">
    <meta name="keywords" content="index">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="renderer" content="webkit">
    <meta http-equiv="Cache-Control" content="no-siteapp" />
	<script src="${pageContext.request.contextPath}/assets/js/jquery.min.js"></script>
    <script src="${pageContext.request.contextPath}/assets/js/amazeui.min.js"></script>
    <link rel="icon" type="image/png" href="${pageContext.request.contextPath}/assets/i/favicon.png">
    <link rel="apple-touch-icon-precomposed" href="${pageContext.request.contextPath}/assets/i/app-icon72x72@2x.png">
    <meta name="apple-mobile-web-app-title" content="Amaze UI" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/amazeui.min.css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/admin.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/app.css">
    
    <script type="text/javascript">
	    function editCourse(id){
			location.href = "${pageContext.request.contextPath}/course/toUpdateCourse?menuId=5&id="+id;
		}
    
    	function deleteBox(id){
    		$("#confirmBox1").html("删除");
    		$("#confirmBox2").html("确定要删除吗？？？？");
 	    	$('#my-confirm').modal({
 	        	relatedTarget: this,
 	        	onConfirm: function(options){
 	        		location.href="${pageContext.request.contextPath}/course/deleteCourse?menuId=5&id="+id;
 	        	},
 	       		// closeOnConfirm: false,
 	      		onCancel: function(){
 	       		}
 	  		});
    	}
    	
    	function importData(){
    		$('#fileForm').click();
    	}
    
	    $(function(){
	    	  $('#deleteMultipleCoursesBuntton').
	    	    on('click', function(){
	    	      $("#confirmBox1").html("批量删除");
	    	      $("#confirmBox2").html("确定要删除所有得选项吗？？？？");
	    	      $('#my-confirm').modal({
	    	        relatedTarget: this,
	    	        onConfirm: function(options){
	    	        	var ids = $('input[name="ids"]:checked').val();
	    	        	if(typeof(ids) != 'undefined'){
	    	        		$("#deleteMultipleCoursesForm").submit();
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
	    	  
	      	$("#toAddCourse").click(function(){
	    		location.href="${pageContext.request.contextPath}/course/toAddCourse?menuId=5"
	    	});
	      	
	    	$("#checkAllId").click(function(){
	    		$("tbody input").prop("checked",this.checked);
	    	});
	    	
	    	$("#conditionButton").click(function(){
	    		location.href = "${pageContext.request.contextPath}/course/getCourseListByPageAndCondition?menuId=5&condition="+$("#condition").val();
	    	});
	    	
	    	$("#downloadTemplate").click(function(){
	    		$('#downloadBox').modal({
	 	        	relatedTarget: this,
	 	        	onConfirm: function(options){
	 	        		location.href="${pageContext.request.contextPath}/course/downloadTemplate?format=.xls";
	 	        		$('#downloadBox').modal('close');
	 	        	},
	 	      		onCancel: function(){
	 	      			location.href="${pageContext.request.contextPath}/course/downloadTemplate?format=.xlsx";
	 	      			$('#downloadBox').modal('close');
	 	       		}
	 	  		});
	    	});
	    	
	    	$("#exportData").click(function(){
	    		$('#exportDataBox').modal({
	 	        	relatedTarget: this,
	 	        	onConfirm: function(options){
 	 	        		location.href = "${pageContext.request.contextPath}/course/exportData?format=xls&page="+${pageBean.page};
 	 	        		$('#exportDataBox').modal('close');
	 	        	},
	 	       		closeOnConfirm: true,
	 	      		onCancel: function(){
 	 	      			location.href = "${pageContext.request.contextPath}/course/exportData?format=xlsx&page="+${pageBean.page}
 	 	      			$('#exportDataBox').modal('close');
	 	       		}
	 	  		});
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
	
	<div class="am-modal am-modal-confirm" tabindex="-1" id="downloadBox" >
		<div class="am-modal-dialog">
			<div class="am-modal-hd">
				课程信息表 
				<a href="javascript: void(0)" class="am-close am-close-spin" data-am-modal-close>&times;</a>
			</div>
			<div class="am-modal-bd">请选择文件类型？？？？</div>
			<ul class="am-list">
 				<li class="am-modal-bd" data-am-modal-confirm><a>课程信息表.xls</a></li> 
				<li class="am-modal-bd" data-am-modal-cancel><a>课程信息表.xlsx</a></li> 
			</ul>
		</div>
	</div>
	<div class="am-modal am-modal-confirm" tabindex="-1" id="exportDataBox" >
		<div class="am-modal-dialog">
			<div class="am-modal-hd">
				课程信息表 
				<a href="javascript: void(0)" class="am-close am-close-spin" data-am-modal-close>&times;</a>
			</div>
			<div class="am-modal-bd">请选择下载文件类型？？？？</div>
			<ul class="am-list">
 				<li class="am-modal-bd" data-am-modal-confirm><a>课程信息表.xls</a></li> 
				<li class="am-modal-bd" data-am-modal-cancel><a>课程信息表.xlsx</a></li> 
			</ul>
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
                        <span class="am-icon-code">课程列表</span>
                    </div>
                </div>
                <div class="tpl-block">
					<div class="am-g">
						<div class="am-u-sm-12 am-u-md-6">
							<div class="am-btn-toolbar">
								<div class="am-btn-group am-btn-group-xs">
									<button type="button" id="toAddCourse"
										class="am-btn am-btn-default am-btn-success">
										<span class="am-icon-plus"> 新增</span>
									</button>
									<form style="display: none;" method="post"
										action="${pageContext.request.contextPath}/course/importData" enctype="multipart/form-data">
										<input type="hidden" name="menuId" value="${menuId}"/>
										<input type="file" id="fileForm" onchange="this.form.submit()"
											name="file"
											style="position: absolute; filter: alpha(opacity = 0); opacity: 0; width: 30px;"
											size="1" />
									</form>
<!-- 									<button type="button" -->
<!-- 										class="am-btn am-btn-default am-btn-secondary" -->
<!-- 										onclick="importData();"> -->
<!-- 										<span class="am-icon-save"></span> 导入 -->
<!-- 									</button> -->
									<button type="button" id="exportData"
										class="am-btn am-btn-default am-btn-warning">
										<span class="am-icon-archive"> 导出</span>
									</button>
<!-- 									<button type="button" id="downloadTemplate" -->
<!-- 										class="am-btn am-btn-default am-btn-secondary"> -->
<!-- 										<span class="am-icon-save"> 下载模板</span> -->
<!-- 									</button> -->
								</div>
							</div>
						</div>
						<div class="am-u-sm-12 am-u-md-3">
							<div class="am-input-group am-input-group-sm">
								<input type="text" id="condition" name="condition" placeholder="请输入课程名称" class="am-form-field"> 
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
                            <form id="deleteMultipleCoursesForm" class="am-form" action="${pageContext.request.contextPath}/course/deleteMultipleCourses">
                                <table class="am-table am-table-striped am-table-hover table-main am-table-centered" style="table-layout: fixed;">
                                    <thead>
                                        <tr>
                                            <th class="table-check"><input type="checkbox" id="checkAllId" class="tpl-table-fz-check"></th>
                                            <th class="table-id">ID</th>
                                            <th class="table-title am-hide-sm-only">课程名</th>
                                            <th class="table-title am-hide-sm-only">任课教师</th>
                                            <th class="table-title">学分</th>
                                            <th class="table-title">容量</th>
                                            <th class="table-title">地点</th>
                                            <th class="table-title am-hide-sm-only" style="width:300px">详细信息</th>
                                            <th class="table-set">操作</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                    	<c:forEach items="${pageBean.list}" var="course">
	                                        <tr>
	                                            <td><input type="checkbox" name="ids" value="${course.courseId}"></td>
	                                            <td>${course.courseId}</td>
	                                            <td class="am-hide-sm-only" title="${course.cname}" style="text-overflow: ellipsis; white-space: nowrap; overflow: hidden;">${course.cname}</td>
	                                            <td>${course.teacher.username}</td>
	                                            <td>${course.period}</td>
	                                            <td>${course.capacity}</td>
	                                            <td title="${course.place}" style="text-overflow: ellipsis; white-space: nowrap; overflow: hidden;">${course.place}</td>
	                                            <td class="am-hide-sm-only" title="${course.info}" style="text-overflow: ellipsis; white-space: nowrap; overflow: hidden;">
	                                            	${course.info}
	                                            </td>
	                                            <td>
	                                                <div class="am-btn-toolbar" style="display: inline-block;">
	                                                    <div class="am-btn-group am-btn-group-xs">
	                                                        <button type="button" onclick="editCourse('${course.courseId}')" class="am-btn am-btn-default am-btn-xs am-text-secondary">
	                                                        	<span class="am-icon-pencil-square-o"> 编辑</span>
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
													href="${pageContext.request.contextPath}/course/${path}&page=1">首页</a></li>
												<li><a
													href="${pageContext.request.contextPath}/course/${path}&page=${pageBean.page -1}">«</a></li>
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
														href="${pageContext.request.contextPath}/course/${path}&page=${i}">${i}</a></li>
												</c:if>
											</c:forEach>
											<c:if test="${pageBean.page ne pageBean.totalPage}">
												<li><a
													href="${ pageContext.request.contextPath}/course/${path}&page=${pageBean.page +1}">»</a></li>
												<li><a
													href="${ pageContext.request.contextPath}/course/${path}&page=${pageBean.totalPage}">尾页</a></li>
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