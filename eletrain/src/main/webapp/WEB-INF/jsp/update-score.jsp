<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!doctype html>
<html>

<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
	<script src="${pageContext.request.contextPath}/assets/js/jquery.min.js"></script>
	<script src="${pageContext.request.contextPath}/assets/js/amazeui.min.js"></script>
	<%-- <script src="${pageContext.request.contextPath}/assets/js/app.js"></script> --%>
	<link rel="icon" type="image/png" href="${pageContext.request.contextPath}/assets/i/favicon.png">
	<link rel="apple-touch-icon-precomposed" href="${pageContext.request.contextPath}/assets/i/app-icon72x72@2x.png">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/amazeui.min.css" />
	<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/admin.css">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/app.css">
    
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
        		$("#total").val(total);
            	$('#totalScore').html($("#total").val());
        	});
    	});
    </script>
    
    <script type="text/javascript">
    $(function(){
    	$("#updateScore").click(function(){
        	$("#confirmBox1").html("修改成绩");
    		$("#confirmBox2").html("确定要修改此学员的成绩吗？？？？");
        	$('#my-confirm').modal({
            	relatedTarget: this,
            	onConfirm: function(options){
            		$("#updateScoreForm").submit();
            	},
           		// closeOnConfirm: false,
          		onCancel: function(){
           		}
      		});
        });
    	
    	$('#updateAdminForm').validator({
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
                        <span class="am-icon-code">修改学员成绩</span>
                    </div>
                </div>
                <div class="tpl-block ">
                    <div class="am-g tpl-amazeui-form">
                        <div class="am-u-sm-12 am-u-md-9">
                            <form class="am-form am-form-horizontal" data-am-validator id="updateScoreForm" action="${pageContext.request.contextPath}/score/updateScore" method="post">
                            	<input type="hidden" name="scoreId" value="${score.scoreId}"/>
                            	<input type="hidden" name="menuId" value="${menuId}"/>
                            	<div class="am-form-group">
                                   <label for="classes-select" class="am-u-sm-3 am-form-label">班级</label> 
									<div class="am-u-sm-9">
										<span class="title">${score.clazz.cname}</span>
                                    </div>
                                </div>
                                <div class="am-form-group">
                                    <label for="student-select" class="am-u-sm-3 am-form-label">学生</label> 
									<div class="am-u-sm-9">
										<span class="title">${score.student.username}</span>
                                    </div>
                                </div>
                                <div class="am-form-group">
                                    <label for="user-name" class="am-u-sm-3 am-form-label">理论分数</label>
                                    <div class="am-u-sm-9 am-form-inline">
                                        <input type="number" id="theoryscore" name="theoryscore" value="${score.theoryscore}" class="am-form-field am-radius" onkeyup="this.value=this.value.replace(/\s+/g,'')"
                                        data-validation-message="分数必须在0到100之间" min="0" max="100" placeholder="输入理论分数"/>
                                    </div>
                                </div>  
                                <div class="am-form-group">  
                                    <label for="user-name" class="am-u-sm-3 am-form-label">实践分数</label>
                                    <div class="am-u-sm-9">
                                        <input type="number" id="practicescore" name="practicescore" value="${score.practicescore}" onkeyup="this.value=this.value.replace(/\s+/g,'')"
                                        data-validation-message="分数必须在0到100之间" min="0" max="100" placeholder="输入实践分数"/>
                                    </div>
                                </div> 
                                <div class="am-form-group">  
                                    <label for="user-name" class="am-u-sm-3 am-form-label">总分</label>
                                    <div class="am-u-sm-9">
                                    	<input type="hidden" id="total" name="total" value="${score.total}"/>
                                        <span id="totalScore">${score.total}</span>
                                    </div>
                                </div>
                                <div class="am-form-group">
                                	<label for="user-name" class="am-u-sm-3 am-form-label">备注</label>
                                    <div class="am-u-sm-9">
                                    	<textarea name="common" rows="5" placeholder="备注" maxlength="255">${score.common}</textarea>
                                    </div>
                                </div>
                                <div class="am-form-group">
                                    <div class="am-u-sm-9 am-u-sm-push-3">
                                        <button type="button" id="updateScore" class="am-btn am-btn-primary">修改</button>
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