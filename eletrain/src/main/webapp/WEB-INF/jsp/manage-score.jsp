<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!doctype html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <script src="${pageContext.request.contextPath}/assets/js/jquery.min.js"></script>
	<script src="${pageContext.request.contextPath}/assets/js/amazeui.min.js"></script>
<%-- 	<script src="${pageContext.request.contextPath}/assets/js/app.js"></script> --%>
	<link rel="icon" type="image/png" href="${pageContext.request.contextPath}/assets/i/favicon.png">
	<link rel="apple-touch-icon-precomposed" href="${pageContext.request.contextPath}/assets/i/app-icon72x72@2x.png">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/amazeui.min.css" />
	<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/admin.css">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/app.css">
    
    <!-- 老师，班级下拉框 -->
    <script type="text/javascript">
    	$(function(){
        		/* var teacherContent="<option value='-100'>---请选择教师---</option>";
    			$.ajax({
    				"async":true,
    				"url":"${pageContext.request.contextPath}/score/findTeacherList",
    				"type":"POST",
    				"dataType":"json",
    				"success":function(data){
    							for(var i=0;i<data.length;i++){
    								teacherContent+="<option value='"+data[i].teacherId+"'>"+data[i].username+"</option>";
    							}
    							$("#teacher-select").html(teacherContent);
    						  }
    			}); */
			
				var classesContent="<option value='-100'>---请选择班级---</option>";
				var exportByClasses = "";
				$.ajax({
					"async":true,
					"url":"${pageContext.request.contextPath}/score/findClassesList",
					"type":"POST",
					"dataType":"json",
					"success":function(data){
								for(var i=0;i<data.length;i++){
									classesContent+="<option value='"+data[i].classesId+"'>"+data[i].cname+"</option>";
									exportByClasses += "<li><a href='javascript:void(0)' onclick='exportDataByClassId(\""+data[i].classesId+"\",\""+data[i].cname+"\")'>"+data[i].cname+"</a></li>";
								}
								$("#classes-select").html(classesContent);
								$("#exportDataByClassIdUl").html(exportByClasses);
							  }
				});
    	});
    </script>
    
    <script type="text/javascript">
	    $(function(){
	      	$("#toAddScore").click(function(){
	    		location.href="${pageContext.request.contextPath}/score/toAddScore?menuId=23"
	    	});
	      	
	    	$("#conditionButton").click(function(){
	    		location.href="${pageContext.request.contextPath}/score/getScoreListByPageAndCondition?menuId=23&condition1="
	    														+$("#condition").val()+"&condition2="
	    														+$("#classes-select").val();
	    	});
	    	
	    	$("#downloadTemplate").click(function(){
	    		$('#downloadBox').modal({
	 	        	relatedTarget: this,
	 	        	onConfirm: function(options){
	 	        		location.href="${pageContext.request.contextPath}/score/downloadTemplate?format=.xls";
	 	        		$('#downloadBox').modal('close');
	 	        	},
	 	       		// closeOnConfirm: false,
	 	      		onCancel: function(){
	 	      			location.href="${pageContext.request.contextPath}/score/downloadTemplate?format=.xlsx";
	 	      			$('#downloadBox').modal('close');
	 	       		}
	 	  		});
	    	});
    	
	    	$("#exportData").click(function(){
 		    	$("#scoreTitle").html("成绩单");
		    	$("#scoreNameXls").html("成绩单.xls");
		    	$("#scoreNameXlsx").html("成绩单.xlsx");
		    	
	    		$('#downloadBox').modal({
	 	        	relatedTarget: this,
 	 	        	onConfirm: function(options){
 	 	        		location.href = "${pageContext.request.contextPath}/score/exportData?format=xls";
 	 	        		$('#downloadBox').modal('close');
	 	        	},
	 	       		closeOnConfirm: true,
	 	      		onCancel: function(){
  	 	      			location.href = "${pageContext.request.contextPath}/score/exportData?format=xlsx";
  	 	      			$('#downloadBox').modal('close');
 	 	       		}
	 	  		});
	    	});
	    });
	    
	    function exportDataByClassId(classesId,cname){
	    	var $modal = $('#your-modal1');
	    	$("#scoreTitle").html(cname+"班成绩单");
	    	$("#scoreNameXls").html(cname+"班成绩单.xls");
	    	$("#scoreNameXlsx").html(cname+"班成绩单.xlsx");
	    	$("#axls").attr("href","${pageContext.request.contextPath}/score/exportData?format=xls&classesId="+classesId);
	    	$("#axlsx").attr("href","${pageContext.request.contextPath}/score/exportData?format=xlsx&classesId="+classesId);
	    	$modal.modal('open');
		}
	    
	    function editScore(id){
			location.href = "${pageContext.request.contextPath}/score/toUpdateScore?menuId=23&id="+id;
		}
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
				成绩单
				<a href="javascript: void(0)" class="am-close am-close-spin" data-am-modal-close>&times;</a>
			</div>
			<div class="am-modal-bd">请选择文件类型（xls或xlsx）？</div>
			<ul class="am-list">
 				<li class="am-modal-bd" data-am-modal-confirm><a>成绩单.xls</a></li> 
				<li class="am-modal-bd" data-am-modal-cancel><a>成绩单.xlsx</a></li> 
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
                        <span class="am-icon-code">学生成绩列表(管理员)</span>
                    </div>
                </div>
                <div class="tpl-block">
					<div class="am-g">
						<div class="am-u-sm-12 am-u-md-6" style="width:300px">
							<div class="am-btn-toolbar">
								<div class="am-btn-group am-btn-group-xs">
									<button type="button" id="toAddScore"
										class="am-btn am-btn-default am-btn-success">
										<span class="am-icon-plus"> 新增</span>
									</button>
									<button type="button" id="exportData"
										class="am-btn am-btn-default am-btn-warning">
										<span class="am-icon-archive"> 导出</span>
									</button>
									<div class="am-dropdown" data-am-dropdown style="margin-top: -3px">
  										<button class="am-btn am-btn-primary am-dropdown-toggle" data-am-dropdown-toggle style="font-size:0.7em">
  											按班级导出 
  											<span class="am-icon-caret-down"></span>
  										</button>
									    <ul id="exportDataByClassIdUl" class="am-dropdown-content" style="max-height:200px; overflow:auto;">
										</ul>
									</div>
									<div class="am-modal am-modal-no-btn" tabindex="-1" id="your-modal1">
										<div class="am-modal-dialog">
											<div class="am-modal-hd">
												<span id="scoreTitle"></span>
												<a href="javascript: void(0)" class="am-close am-close-spin" data-am-modal-close>&times;</a>
											</div>
											<div class="am-modal-bd">
												<div class="am-modal-actions-group">
													<ul class="am-list">
														<li class="am-modal-actions-header">
															<a id="axls" href=""><span id="scoreNameXls"></span></span></a>
														</li>
														<li class="am-modal-actions-header">
															<a id="axlsx" href=""><span id="scoreNameXlsx"></span></a>
														</li>
													</ul>
												</div>
											</div>
										</div>
									</div>
								</div>
							</div>
						</div>
						<!-- <div class="am-u-sm-12 am-u-md-3" style="width:200px">
							<div class="am-form-group">
								<select id="teacher-select" name="teacherId">
								</select>
							</div>
						</div> -->
						<div class="am-u-sm-12 am-u-md-3" style="width:200px">
							<div class="am-form-group">
								<select id="classes-select" name="classesId">
								</select>
							</div>
						</div>
						<div class="am-u-sm-12 am-u-md-3">
							<div class="am-input-group am-input-group-sm">
								<input type="text" id="condition" name="condition" placeholder="请输入学员身份证号码" class="am-form-field"> 
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
                            <form id="deleteMultipleAdminsForm" class="am-form" action="${pageContext.request.contextPath}/admin/deleteMultipleAdmins">
                                <table class="am-table am-table-striped am-table-hover table-main am-table-centered" style="table-layout: fixed;">
                                    <thead>
                                        <tr>
                                            <th class="table-title">班级</th>
                                            <th class="table-author">学生姓名</th>
                                            <th class="table-title">理论成绩</th>
                                            <th class="table-title">实践成绩</th>
                                            <th class="table-set">总成绩</th>
                                            <th class="table-set">备注</th>
                                            <th class="table-set">操作</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                    	<c:forEach items="${pageBean.list}" var="score">
	                                        <tr>
	                                            <td>${score.clazz.cname}</td>
	                                            <td class="am-hide-sm-only">${score.student.username}</td>
	                                            <td>${score.theoryscore}</td>
	                                            <td class="am-hide-sm-only">${score.practicescore}</td>
	                                            <td class="am-hide-sm-only">${score.total}</td>
	                                            <td class="am-hide-sm-only" title="${score.common}" style="text-overflow: ellipsis; white-space: nowrap; overflow: hidden;">${score.common}</td>
	                                            <td>
	                                                <div class="am-btn-toolbar" style="display:inline-block;">
	                                                    <div class="am-btn-group am-btn-group-xs">
                                                        <button type="button" onclick="editScore('${score.scoreId}')" class="am-btn am-btn-default am-btn-xs am-text-secondary">
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
												href="${pageContext.request.contextPath }/score/${path}&page=1&menuId=0">首页</a></li>
											<li><a
												href="${pageContext.request.contextPath }/score/${path}&page=${pageBean.page -1}&menuId=0">«</a></li>
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
													href="${ pageContext.request.contextPath }/score/${path}&page=${i}&menuId=0">${i }</a></li>
											</c:if>
										</c:forEach>
										<c:if test="${pageBean.page < pageBean.totalPage  }">
											<li><a
												href="${ pageContext.request.contextPath }/score/${path}&page=${pageBean.page +1}&menuId=0">»</a></li>
											<li><a
												href="${ pageContext.request.contextPath }/score/${path}&page=${pageBean.totalPage}&menuId=0">尾页</a></li>
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