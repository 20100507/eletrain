<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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
    
    <script type="text/javascript">
    $(function(){
    	$("#updateAdmin").click(function(){
        	$("#confirmBox1").html("修改管理员");
    		$("#confirmBox2").html("确定要修改此管理员吗？？？？");
        	$('#my-confirm').modal({
            	relatedTarget: this,
            	onConfirm: function(options){
            		$("#updateAdminForm").submit();
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
                        <span class="am-icon-code">修改管理员信息</span>
                    </div>
                </div>
                <div class="tpl-block ">
                    <div class="am-g tpl-amazeui-form">
                        <div class="am-u-sm-12 am-u-md-9">
                            <form class="am-form am-form-horizontal" id="updateAdminForm" action="${pageContext.request.contextPath}/admin/updateAdmin" method="post">
                            	<input type="hidden" name="adminId" value="${admin.adminId}"/>
                            	<input type="hidden" name="menuId" value="${menuId}"/>
                           		<div class="am-form-group">
                                    <label for="user-name" class="am-u-sm-3 am-form-label">登录账号 / loginname</label>
                                    <div class="am-u-sm-9">
                                    	<c:if test="${admin.identify != 1}">
                                    		<input type="hidden" id="loginname" name="loginname" value="${admin.loginname}"/>
			                                <input type="text" value="${admin.loginname}" disabled/>
			                            </c:if>
			                            <c:if test="${admin.identify == 1}">
			                            	<input type="text" id="loginname" name="loginname" value="${admin.loginname}"
			                                	required maxlength="10" minlength="10" onkeyup="this.value=this.value.replace(/\s+/g,'')"
			                                	data-validation-message="登录账号不能为空且长度为10位" placeholder="输入你的登录名，用于登录账号"/>	
			                            </c:if>
                                    </div>
                               	</div>	
                                <div class="am-form-group">
                                    <label for="user-name" class="am-u-sm-3 am-form-label">姓名 / Name</label>
                                    <div class="am-u-sm-9">
                                        <input type="text" id="username" name="username" value="${admin.username}" required onkeydown="return banInputSapce(event);"
                                        data-validation-message="姓名不能为空" placeholder="输入你的名字，让我们记住你"/>
                                    </div>
                                </div>
                                <div class="am-form-group">
                                    <div class="am-u-sm-9 am-u-sm-push-3">
                                        <button type="button" id="updateAdmin" class="am-btn am-btn-primary">保存修改</button>
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