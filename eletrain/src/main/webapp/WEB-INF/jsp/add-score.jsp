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
   <%--  <script src="${pageContext.request.contextPath}/assets/js/app.js"></script> --%>
    <link rel="icon" type="image/png" href="${pageContext.request.contextPath}/assets/i/favicon.png">
    <link rel="apple-touch-icon-precomposed" href="${pageContext.request.contextPath}/assets/i/app-icon72x72@2x.png">
    <meta name="apple-mobile-web-app-title" content="Amaze UI" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/amazeui.min.css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/admin.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/app.css">
    
	<!-- 班级，课程，学生下拉框三级联动 -->
    <script type="text/javascript">
    	$(function(){
    		//获取该老师所教的所有班级
	    	var classesContent="<option value='0'>---请选择班级---</option>";
			$.ajax({
				"async":true,
				"url":"${pageContext.request.contextPath}/score/findClassesListByTeacherId",
				"data":'-1',
				"type":"POST",
				"dataType":"json",
				"success":function(data){
							for(var i=0;i<data.length;i++){
								classesContent+="<option value='"+data[i].classesId+"'>"+data[i].cname+"</option>";
							}
							$("#classes-select").html(classesContent);
						  }
			});
			
			$("#classes-select").change(function(){
          		$('#theoryscore').attr("disabled","disabled");
          		$('#practicescore').attr("disabled","disabled");
          		$('#certificateno').attr("disabled","disabled");
          		$("#theoryscore").val("");
          		$("#practicescore").val("");
          		$("#total").val("");
          		$("#certificateno").val("");
             	
				//获取该老师所教的所有班级中的某个学生
		    	var studentContent="<option value='0'>---请选择学生---</option>";
		    	var classesId = $("#classes-select").val();
				$.ajax({
					"async":true,
					"type":"POST",
					"url":"${pageContext.request.contextPath}/score/findStudentListByClassesId",
					"data":classesId,
					"contentType":"application/json;charset=UTF-8",
					"dataType":"json",
					"success":function(data){
								for(var i=0;i<data.length;i++){
									studentContent+="<option value='"+data[i].studentId+"'>"+data[i].username+"</option>";
								}
								$("#student-select").html(studentContent);
							  }
				});
			});
			
			$("#student-select").change(function(){
				if($("#student-select").val() != 0){
	             	$('#theoryscore').removeAttr("disabled"); 
	             	$('#practicescore').removeAttr("disabled"); 
				}else{
             		$('#theoryscore').attr("disabled","disabled");
             		$('#practicescore').attr("disabled","disabled");
             		$('#certificateno').attr("disabled","disabled");
             		$("#theoryscore").val("");
             		$("#practicescore").val("");
             		$("#total").val("");
             		$("#certificateno").val("");
             	}
			});
    	});
    </script>
    
    <!-- 根据理论分数与实际分数计算总分 -->
    <script type="text/javascript">
    	$(function(){
    		var theoryscore = 0;
        	var practicescore = 0;
        	var total = 0;
        	$("#theoryscore").change(function(){
        		theoryscore = $("#theoryscore").val();
        		practicescore = $("#practicescore").val();
        		if(theoryscore != 0 && practicescore ==0){
        			total = theoryscore*1;
        		}else if(theoryscore == 0 && practicescore !=0){
        			total = practicescore*1;
        		}else if(theoryscore != 0 && practicescore !=0){
        			total = theoryscore*0.4 + practicescore*0.6;
        			total = Number(total).toFixed(2); 
        		}else{
        			total = "";
        		}
        		if(total >= 60){
             		$('#certificateno').removeAttr("disabled"); 
             	}else{
             		$('#certificateno').attr("disabled","disabled");
             	}
        		$("#total").val(total);
            	$('#totalScore').html($("#total").val());
            	
        	});
        	
        	$("#practicescore").change(function(){
        		theoryscore = $("#theoryscore").val();
        		practicescore = $("#practicescore").val();
        		if(theoryscore != 0 && practicescore ==0){
        			total = theoryscore*1;
        		}else if(theoryscore == 0 && practicescore !=0){
        			total = practicescore*1;
        		}else if(theoryscore != 0 && practicescore !=0){
        			total = theoryscore*0.4 + practicescore*0.6;
        			total = Number(total).toFixed(2); 
        		}else{
        			total = "";
        		}
        		if(total >= 60){
             		$('#certificateno').removeAttr("disabled"); 
             	}else{
             		$('#certificateno').attr("disabled","disabled");
             	}
        		$("#total").val(total);
            	$('#totalScore').html($("#total").val());
        	});
    	});
    </script>
    
    <script type="text/javascript">
	    $(function(){
	    	$("#addAdmin").click(function(){
	        	$("#confirmBox1").html("新建成绩");
	    		$("#confirmBox2").html("确定要新建此成绩吗？？？？");
	        	$('#my-confirm').modal({
	            	relatedTarget: this,
	            	onConfirm: function(options){
	            		$("#addScoreForm").submit();
	            	},
	           		// closeOnConfirm: false,
	          		onCancel: function(){
	           		}
	      		});
	        });
	    	
	    	$('#addAdminForm').validator({
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

        <div class="tpl-content-wrapper" style="width:85%">
            <div class="tpl-portlet-components">
                <div class="portlet-title">
                    <div class="caption font-green bold">
                        <span class="am-icon-code">新建学员成绩</span>
                    </div>
                </div>
                <div class="tpl-block ">
                    <div class="am-g tpl-amazeui-form">
                        <div class="am-u-sm-12 am-u-md-9">
                            <form class="am-form am-form-horizontal" data-am-validator id="addScoreForm" action="${pageContext.request.contextPath}/score/addScore" method="post">
                            	<input type="hidden" name="menuId" value="${menuId}"/>
                            	<div class="am-form-group">
                                   <label for="classes-select" class="am-u-sm-3 am-form-label">班级</label> 
									<div class="am-u-sm-9">
										<select id="classes-select" name="classesId"
											data-validation-message="班级不能为空" required>
										</select> 
										<span class="am-form-caret"></span>
                                    </div>
                                </div>
                                <div class="am-form-group">
                                    <label for="student-select" class="am-u-sm-3 am-form-label">学生</label> 
									<div class="am-u-sm-9">
										<select id="student-select" name="studentId"
											 data-validation-message="学生不能为空"	required>
										</select> 
										<span class="am-form-caret"></span>
                                    </div>
                                </div>
                                <div class="am-form-group">
                                    <label for="user-name" class="am-u-sm-3 am-form-label">理论分数</label>
                                    <div class="am-u-sm-9 am-form-inline">
                                        <input type="number" id="theoryscore" name="theoryscore" class="am-form-field am-radius"
                                        data-validation-message="分数必须在0到100之间" min="0" max="100" onkeyup="this.value=this.value.replace(/\s+/g,'')"
                                        disabled="disabled" placeholder="输入理论分数"/>
                                    </div>
                                </div>  
                                <div class="am-form-group">  
                                    <label for="user-name" class="am-u-sm-3 am-form-label">实践分数</label>
                                    <div class="am-u-sm-9">
                                        <input type="number" id="practicescore" name="practicescore" onkeyup="this.value=this.value.replace(/\s+/g,'')"
                                        data-validation-message="分数必须在0到100之间" min="0" max="100" 
                                        disabled="disabled" placeholder="输入实践分数"/>
                                    </div>
                                </div>   
                                <div class="am-form-group">
                                    <label for="user-name" class="am-u-sm-3 am-form-label">总分</label>
                                    <div class="am-u-sm-9">
                                    	<input type="hidden" id="total" name="total" value=""/>
                                        <span id="totalScore"></span>
                                    </div>
                                </div>
                                <div class="am-form-group">
                                	<label for="certificateNo" class="am-u-sm-3 am-form-label">证书编号</label>
                                	<div class="am-u-sm-9">
	                                	<input type="text" id="certificateno" name="certificateno" disabled="disabled" onkeyup="this.value=this.value.replace(/\s+/g,'')"/>
                                	</div>
                                </div>
                                <div class="am-form-group">
                                	<label for="user-name" class="am-u-sm-3 am-form-label">备注</label>
                                    <div class="am-u-sm-9">
                                    	<textarea name="common" rows="5" placeholder="备注" maxlength="255"></textarea>
                                    </div>
                                </div>
                                <div class="am-form-group">
                                    <div class="am-u-sm-9 am-u-sm-push-3">
                                        <button type="button" id="addAdmin" class="am-btn am-btn-primary">新建</button>
                                        <button type="button" onclick='return window.history.back();' class="am-btn am-btn-primary">
                                        	返回
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