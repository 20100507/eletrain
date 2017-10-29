<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<!doctype html>
<html>

<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>增加或删修改班级</title>
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
    	var status='0';
    	var cstatus='0';
    	var statusRepeat="0";
    	var cnameStatusRepeat="0";
		window.onload = function() {
			$(".selectTeacherIds").bind("click", function() {
				$(this).children(":first").attr("checked", true);
			});
		};
    	function cnameRemove(){
    		cnameStatusRepeat="0";
    		$('#cnameTitle').html("");
    	};
    	function cnameContentChecked(){
    		var currentValue = $('#cname').val().replace(/\s+/g,'');
    		var cname=currentValue;
			$.ajax({
				cache: false,
				type : "post",
				traditional : true,
				url : "/classes/classesNoContentChecked?type=1",
				data : {
					"classesNo" : cname
				},
				success : function(data) {
					if(data=='allow'){
						cnameStatusRepeat="0";
						return;
					}
					else if(data=='repeat'&&currentValue!='${classes.cname }'){
						cnameStatusRepeat="1";
						$('#cnameTitle').html('<span style="color:#FF0000">您输入的班级名称已经存在，请重新输入！</span>');
					}
				}

			});
    	};
    	function remove(){
    		statusRepeat="0";
    		$('#classesNo').html("");
    	};
    	function classesNoContentChecked(){
    		var currentValue = $('#classesNo').val().replace(/\s+/g,'');
    		var classesNo=currentValue;
			$.ajax({
				cache: false,
				type : "post",
				traditional : true,
				url : "/classes/classesNoContentChecked?type=0",
				data : {
					"classesNo" : classesNo
				},
				success : function(data) {
					if(data=='allow'){
						statusRepeat="0";
						return;
					}
					else if(data=='repeat'&&currentValue!='${classes.classesNo }'){
						statusRepeat="1";
						$('#classesNoTitle').html('<span style="color:#FF0000">您输入的班级编号已经存在，请重新输入！</span>');
					}
				}

			});
    	};
		/* 验证时间 */
		    function checkTime(){
	   		 var createtime = $('#createtime').val();
	   		 var endtime = $('#endTime').val();
	   		 var starttime = $('#startTime').val();
	   		 if(starttime!=null&&createtime!=null){
		   		 var Create = new Date(createtime).getTime();
		   		 var Start = new Date(starttime).getTime();
		   		 if(Create>Start){
		   			cstatus='1';
		   			$('#createTimeTile').html('<span style="color:#FF0000">报到时间不能大于授课时间！</span>');
		   		 }
		   		 else{
		   			cstatus='0';
		   			$('#createTimeTile').html("");
		   		 }
	   		 }
	   		 if(endtime!=null&&starttime!=null){
		   		 var Start = new Date(starttime).getTime();
		   		 var End = new Date(endtime).getTime();
		   		 if(Start>End){
		   			status='1';
		   			$('#startTimeTile').html('<span style="color:#FF0000">开始时间不能大于终止时间！</span>');
		   			$('#endTimeTile').html('<span style="color:#FF0000">终止时间不能小于开始时间！</span>');
		   		 }
		   		 else{
		   			status='0';
		   			$('#startTimeTile').html("");
		   			$('#endTimeTile').html("");
		   		 }
	   		 }
			}
    	/* 表单空格验证 */
   		$(document).ready(function() {
   		    $("#checkForm").bind("click",
               function() {
                     if(status=='0'&&statusRepeat=='0'&&cstatus=='0'&&cnameStatusRepeat=='0'){
                    	  $('#addOrDelClassesForm').submit();
                     }else{
                    	 alert('表单填写不正确！');
                     }
            });
   			/*  验证班级编号 */
   			$("#classesNo").blur(function() {
   			 this.value=this.value.replace(/\s+/g,'');
   		        var value = $(this).val();
   		        value = $.trim(value);
   		        if (value == '') {
   		        	$('#classesNoTitle').html('<span style="color:#FF0000">班级编号不能为空！</span>');
   		        	status='1';
   		            return false;
   		        };
   		    })
   		    $("#classesNo").focus(function() {$('#classesNoTitle').html("");status='0';})
   		 		
   			/*  验证班级名称*/
   			$("#cname").blur(function() {
   			 this.value=this.value.replace(/\s+/g,'');
   		        var value = $(this).val();
   		        value = $.trim(value);
   		        if (value == '') {
   		        	$('#cnameTitle').html('<span style="color:#FF0000">班级名称不能为空！</span>');
   		        	status='1';
   		            return false;
   		        };
   		    })
   		    $("#cname").focus(function() {$('#cnameTitle').html("");status='0';})
   		    
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
                        <span class="am-icon-code"></span> 新增/编辑班级
                    </div>
                </div>
                <div class="tpl-block ">
                    <div class="am-g tpl-amazeui-form">
                        <div class="am-u-sm-12 am-u-md-9">
                            <form id="addOrDelClassesForm" data-am-validator method="post" class="am-form am-form-horizontal" action="${pageContext.request.contextPath }/classes/${ classes== null ? 'add' : 'update' }Classes?menuId=17">
                            	<input type="hidden" name="classesId" value="${classes.classesId }">
                            	<input type="hidden" name="type" value="${type}">
                            	
                                <div class="am-form-group">
                                    <label for="user-name" class="am-u-sm-3 am-form-label">班级编号</label>
                                    <div class="am-u-sm-9">
                                        <input required onfocus="remove()" onblur="classesNoContentChecked()" name="classesNo" value="${classes.classesNo }" type="text" id="classesNo" placeholder="请输入班级的编号">
                                        <span id="classesNoTitle"></span>
                                    </div>
                                </div>
                                <div class="am-form-group">
                                    <label for="user-name" class="am-u-sm-3 am-form-label">班级名称</label>
                                    <div class="am-u-sm-9">
                                        <input required onfocus="cnameRemove()" onblur="cnameContentChecked()" name="cname" value="${classes.cname }" type="text" id="cname" placeholder="请输入班级的名称">
                                        <span id="cnameTitle"></span>
                                    </div>
                                </div>
                                <div class="am-form-group">
                                    <label for="user-name" class="am-u-sm-3 am-form-label">班主任</label>
									<div class="am-u-sm-9">
										<select id="classesTeacher" required name="teacherIds">
											<option disabled selected value="">===请选择班主任===</option>
											<c:forEach items="${classesTeacherList }" var="classesTeacher">
												<option value="${classesTeacher.teacherId }"
													<c:if test="${fn:contains(ctnameList,classesTeacher.username) }">selected</c:if>>${classesTeacher.username }(${classesTeacher.loginname })</option>
											</c:forEach>
										</select>
									</div>
								</div>
                                <div class="am-form-group">
                                    <label for="user-name" class="am-u-sm-3 am-form-label">报到时间</label>
                                    <div class="am-u-sm-9">
                                        <input required readonly onchange="checkTime()" name="createtime" value="${classes.createtime }" type="text" id="createtime" placeholder="请输入班级报到时间(如：1970-01-01)" data-am-datepicker>
                                        <span id="createTimeTile"></span>
                                    </div>
                                </div>
                                <div class="am-form-group">
                                    <label for="user-name" class="am-u-sm-3 am-form-label">授课起始时间</label>
                                    <div class="am-u-sm-9">
                                        <input required readonly onchange="checkTime()" name="startTime" value="${startTime }" type="text" id="startTime" placeholder="请输入授课起始时间(如：1970-01-01)" data-am-datepicker>
                                        <span id="startTimeTile"></span>
                                    </div>
                                </div>
                                <div class="am-form-group">
                                    <label for="user-name" class="am-u-sm-3 am-form-label">授课终止时间</label>
                                    <div class="am-u-sm-9">
                                        <input required readonly onchange="checkTime()" name="endTime" value="${endTime }" type="text" id="endTime" placeholder="请输入授课终止时间(如：1970-01-01)" data-am-datepicker>
                                        <span id="endTimeTile"></span>
                                    </div>
                                </div>
                                <div class="am-form-group">
                                     <div class="am-u-sm-9 am-u-sm-push-3">
										<button type="button" id="checkForm" class="am-btn am-btn-primary">保存修改</button>
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