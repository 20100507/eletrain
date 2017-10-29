<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
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
  <%--   <script src="${pageContext.request.contextPath}/assets/js/app.js"></script> --%>
    <link rel="icon" type="image/png" href="${pageContext.request.contextPath}/assets/i/favicon.png">
    <link rel="apple-touch-icon-precomposed" href="${pageContext.request.contextPath}/assets/i/app-icon72x72@2x.png">
    <meta name="apple-mobile-web-app-title" content="Amaze UI" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/amazeui.min.css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/admin.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/app.css">
    
    <script type="text/javascript">
	    $(function(){
	    	var content="";
			$.ajax({
				"async":true,
				"url":"${pageContext.request.contextPath}/course/findTeacherList",
				"type":"POST",
				"dataType":"json",
				"success":function(data){
							for(var i=0;i<data.length;i++){
								if(data[i].teacherId == ${course.teacherId}){
									content+="<option value='"+data[i].teacherId+"' selected='selected'>"+data[i].username+"</option>";
									
								}else{
									content+="<option value='"+data[i].teacherId+"'>"+data[i].username+"</option>";
								}
								
							}
							$("#doc-select-1").html(content);
						  }
			});
	    	
	    	$("#updateCourse").click(function(){
	        	$("#confirmBox1").html("修改课程");
	    		$("#confirmBox2").html("确认修改课程？");
	        	$('#my-confirm').modal({
	            	relatedTarget: this,
	            	onConfirm: function(options){
	            		$("#updateCourseForm").submit();
	            	},
	           		// closeOnConfirm: false,
	          		onCancel: function(){
	           		}
	      		});
	        });
	    	
	    	$('#updateCourseForm').validator({
	    	    onValid: function(validity) {
	    	      $(validity.field).closest('.am-form-group').find('.am-alert').hide();
	    	    },
	
	    	    onInValid: function(validity) {
	    	      var $field = $(validity.field);
	    	      var $group = $field.closest('.am-u-sm-9');
	    	      var $alert = $group.find('.am-alert');
	    	      // 使用自定义的提示信息 或 插件内置的提示信息
	    	      var msg = $field.data('validationMessage') || this.getValidationMessage(validity);
	
	    	      if (!$alert.length) {
	    	        $alert = $('<div class="am-alert am-alert-danger"></div>').hide().
	    	          appendTo($group);
	    	      }
	
	    	      $alert.html(msg).show();
	    	    }
	    	  });
	    });
	    
	    /** 
	     * 禁止空格输入 
	     * @param e 
	     * @returns {Boolean} 
	     */ 
	     function banInputSapce(e) 
	     { 
	 	    var keynum; 
	 	    if(window.event) // IE 
	 	    { 
	 	    	keynum = e.keyCode 
	 	    } 
	 	    else if(e.which) // Netscape/Firefox/Opera 
	 	    { 
	 	    	keynum = e.which 
	 	    } 
	 	    if(keynum == 32){ 
	 	    	return false; 
	 	    } 
	 	    return true; 
	     } 
    </script>
</head>

<body data-type="generalComponents">
	<jsp:include page="head.jsp"></jsp:include>
	<jsp:include page="side.jsp"></jsp:include>
	<div class="am-modal am-modal-confirm" tabindex="-1" id="my-confirm">
		<div class="am-modal-dialog">
			<div id="confirmBox1" class="am-modal-hd"></div>
			<div id="confirmBox2" class="am-modal-bd"></div>
			<div class="am-modal-footer">
				<span class="am-modal-btn" data-am-modal-confirm>确定</span>
				<span class="am-modal-btn" data-am-modal-cancel>取消</span> 
			</div>
		</div>
	</div>
        <div class="tpl-content-wrapper">
            <div class="tpl-portlet-components">
                <div class="portlet-title">
                    <div class="caption font-green bold">
                        <span class="am-icon-code">修改课程信息</span>
                    </div>
                </div>
                <div class="tpl-block ">
                    <div class="am-g tpl-amazeui-form">
                        <div class="am-u-sm-12 am-u-md-9">
                            <form class="am-form am-form-horizontal" id="updateCourseForm" action="${pageContext.request.contextPath}/course/updateCourse" method="post">
                            	<input type="hidden" name="courseId" value="${course.courseId}"/>
                            	<input type="hidden" name="menuId" value="${menuId}"/>
                                <div class="am-form-group">
                                    <label for="user-name" class="am-u-sm-3 am-form-label">课程名</label>
                                    <div class="am-u-sm-9">
                                        <input type="text" id="cname" value="${course.cname}" required disabled="disabled"
                                        data-validation-message="课程名不能为空" placeholder="输入课程名"/>
                                    </div>
                                    <input type="hidden" name="cname" value="${course.cname}"/>
                                </div>
								<div class="am-form-group">
									<label for="doc-select-1" class="am-u-sm-3 am-form-label">任课教师</label> 
									<div class="am-u-sm-9">
										<select id="doc-select-1" disabled="disabled"></select> 
										<span class="am-form-caret"></span>
                                    </div>
                                    <input type="hidden" name="teacherId" value="${course.teacherId}"/>
								</div>
								<div class="am-form-group">
                                    <label for="period" class="am-u-sm-3 am-form-label">学分(请输入整数)</label>
                                    <div class="am-u-sm-9">
                                        <input type="number" id="period" name="period" value="${course.period}" required onkeyup="this.value=this.value.replace(/\s+/g,'')"
                                        data-validation-message="学分不能为空,且必须为正整数" placeholder="输入学分"/>
                                    </div>
                                </div>
								<div class="am-form-group">
                                    <label for="user-name" class="am-u-sm-3 am-form-label">课程容量(请输入整数)</label>
                                    <div class="am-u-sm-9">
                                        <input type="number" id="capacity" name="capacity" value="${course.capacity}" required onkeyup="this.value=this.value.replace(/\s+/g,'')"
                                        data-validation-message="课程容量不能为空,且必须为正整数" placeholder="输入课程容量"/>
                                    </div>
                                </div>
                                <div class="am-form-group">
                                    <label for="user-name" class="am-u-sm-3 am-form-label">上课地点</label>
                                    <div class="am-u-sm-9">
                                        <input type="text" id="place" name="place" value="${course.place}" required onkeydown="return banInputSapce(event);"
                                        data-validation-message="上课地点不能为空" placeholder="输入上课地点"/>
                                    </div>
                                </div>
                                <div class="am-form-group">
                                    <label for="user-name" class="am-u-sm-3 am-form-label">课程详情</label>
                                    <div class="am-u-sm-9">
                                    	<textarea id="info" name="info" placeholder="输入课程详情" class="" rows="5" maxlength="255">${course.info}</textarea>
                                    </div>
                                </div>

                                <div class="am-form-group">
                                    <div class="am-u-sm-9 am-u-sm-push-3">
                                        <button type="button" id="updateCourse" class="am-btn am-btn-primary">保存修改</button>
                                        <button type="button" onclick='return window.history.back();' class="am-btn am-btn-primary">
                                        	&nbsp;&nbsp;返&nbsp;&nbsp;回&nbsp;&nbsp;
                                        </button>
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