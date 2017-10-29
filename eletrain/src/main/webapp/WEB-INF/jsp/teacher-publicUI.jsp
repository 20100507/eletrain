<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!doctype html>
<html>

<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>增加或编辑教师</title>
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
    	var statusRepeat="0";
    	function remove(){
    		statusRepeat="0";
    		$('#idcardTitle').html("");
    	};
    	function LoginNameContentChecked(){
    		var currentValue = $('#idcard').val().replace(/\s+/g,'');
    		var loginname=currentValue;
			$.ajax({
				cache: false,
				type : "post",
				traditional : true,
				url : "/teacher/LoginNameContentChecked",
				data : {
					"loginname" : loginname
				},
				success : function(data) {
					if(data=='allow'){
						statusRepeat="0";
						return;
					}
					else if(data=='repeat'&&currentValue!='${teacher.idcard }'){
						statusRepeat="1";
						$('#idcardTitle').html('<span style="color:#FF0000">您输入的身份证号已经存在，请重新输入！</span>');
					}
					else if(data=='noAllow'&&currentValue!='${teacher.idcard }'){
						statusRepeat="1";
						$('#idcardTitle').html('<span style="color:#FF0000">您输入的身份证号已经存在，请重新输入！</span>');
					}
				}

			});
    	}; 
    	/* 表单校验*/
   		$(document).ready(function() {
   		    $("#checkForm").bind("click",
               function() {
                     if(status=='0'&&statusRepeat=='0'){
                    	   $('#addOrDelForm').submit();
                     }else{
                    	 alert('表单填写不正确！');
                     }
            });
   		    
   			/*验证用户名 
   			$("#username").blur(function() {
   				this.value=this.value.replace(/\s+/g,'');
   		        var value = $(this).val();
   		        value = $.trim(value);
   		        if (value == '') {
   		        	$('#usernameTitle').html('<span style="color:#FF0000">姓名不能为空！</span>');
   		        	status='1';
   		            return false;
   		        };
   		    })
   		    $("#username").focus(function() {$('#usernameTitle').html("");status='0';})
   		   	验证工号 
   			$("#loginname").blur(function() {
   				this.value=this.value.replace(/\s+/g,'');
   		        var value = $(this).val();
   		        value = $.trim(value);
   		        if (value == '') {
   		        	$('#loginnameTitle').html('<span style="color:#FF0000">工号不能为空！</span>');
   		        	status='1';
   		            return false;
   		        };
   		    })
   		    $("#loginname").focus(function() {$('#loginnameTitle').html("");status='0';})
   		   	验证职称 
   			$("#position").blur(function() {
   				this.value=this.value.replace(/\s+/g,'');
   		        var value = $(this).val();
   		        value = $.trim(value);
   		        if (value == '') {
   		        	$('#positionTitle').html('<span style="color:#FF0000">职称不能为空！</span>');
   		        	status='1';
   		            return false;
   		        };
   		    })
   		    $("#position").focus(function() {$('#positionTitle').html("");status='0';})
   		   	验证电话 
   			$("#telephone").blur(function() {
   				this.value=this.value.replace(/\s+/g,'');
   		        var value = $(this).val();
   		        value = $.trim(value);
   		        if (value == '') {
   		        	$('#telephoneTitle').html('<span style="color:#FF0000">电话不能为空！</span>');
   		        	status='1';
   		            return false;
   		        };
   		    })
   		    $("#telephone").focus(function() {$('#telephoneTitle').html("");status='0';})
   		   	验证地址 
   			$("#address").blur(function() {
   				this.value=this.value.replace(/\s+/g,'');
   		        var value = $(this).val();
   		        value = $.trim(value);
   		        if (value == '') {
   		        	$('#addressTitle').html('<span style="color:#FF0000">地址不能为空！</span>');
   		        	status='1';
   		            return false;
   		        };
   		    })
   		    $("#address").focus(function() {$('#addressTitle').html("");status='0';})
   		    
   		   	验证学历 
   			$("#education").blur(function() {
   				this.value=this.value.replace(/\s+/g,'');
   		        var value = $(this).val();
   		        value = $.trim(value);
   		        if (value == '') {
   		        	$('#educationTitle').html('<span style="color:#FF0000">学历不能为空！</span>');
   		        	status='1';
   		            return false;
   		        };
   		    })
   		    $("#education").focus(function() {$('#educationTitle').html("");status='0';})
   		    
   		   	验证身份证 
   			$("#idcard").blur(function() {
   				this.value=this.value.replace(/\s+/g,'');
   		        var value = $(this).val();
   		        value = $.trim(value);
   		        if (value == '') {
   		        	$('#idcardTitle').html('<span style="color:#FF0000">身份证号不能为空！</span>');
   		        	status='1';
   		            return false;
   		        };
   		    })
   		    $("#idcard").focus(function() {$('#idcardTitle').html("");status='0';})
   		 */	
   		});
   	
    </script>
</head>
<body data-type="generalComponents">

<jsp:include page="head.jsp"></jsp:include>
	<jsp:include page="side.jsp"></jsp:include>
    <div>
        <div class="tpl-content-wrapper">
            <div class="tpl-portlet-components">
                <div class="portlet-title">
                    <div class="caption font-green bold">
                        <span class="am-icon-code"></span> 增加/修改教师：
                    </div>
                </div>
                <div class="tpl-block ">
                    <div class="am-g tpl-amazeui-form">
                        <div class="am-u-sm-12 am-u-md-9">
                            <form id="addOrDelForm" data-am-validator method="post" class="am-form am-form-horizontal" action="${pageContext.request.contextPath }/teacher/${ teacher== null ? 'add' : 'update' }Teacher?menuId=0">
                            	<input type="hidden" name="teacherId" value="${teacher.teacherId }">
                            	<input type="hidden" name="mould" value="${mould }">
                            	<input type="hidden" name="type" value="${type }">
                                <div class="am-form-group">
                                    <label for="user-name" class="am-u-sm-3 am-form-label">姓名</label>
                                    <div class="am-u-sm-9">
                                        <input name="username" value="${teacher.username }" type="text" id="username" placeholder="输入您的名字">
                                        <span id="usernameTitle"></span>
                                    </div>
                                </div>
                                <%-- <div class="am-form-group">
                                    <label for="user-name" class="am-u-sm-3 am-form-label">工号</label>
                                    <div class="am-u-sm-9">
                                        <input onfocus="remove()" onblur="LoginNameContentChecked(this.value)" name="loginname" value="${teacher.loginname }" type="text" id="loginname" placeholder="输入您的账号">
                                        <span id="loginnameTitle"></span>
                                    </div>
                                </div> --%>
                                
                                <div class="am-form-group">
                                    <label for="user-name" class="am-u-sm-3 am-form-label">职称</label>
                                    <div class="am-u-sm-9">
                                        <input name="position" value="${teacher.position }" type="text" id="position" placeholder="输入您的职称">
                                        <span id="positionTitle"></span>
                                    </div>
                                </div>
                                
                                <div class="am-form-group">
                                    <label for="user-name" class="am-u-sm-3 am-form-label">教师类别</label>
                                    <div class="am-u-sm-9">
                                    	<select required id="identify" name="identify">
                                    		<option disabled selected value="">===请选择教师类别===</option>
                                    		<option value="2" <c:if test="${teacher.identify==2 }">selected</c:if>>授课教师</option>
                                    		<option value="6" <c:if test="${teacher.identify==6 }">selected</c:if>>班主任</option>
                                    	</select>
                                    </div>
                                </div>

                                <div class="am-form-group">
                                    <label for="user-email" class="am-u-sm-3 am-form-label">性别</label>
                                    <div class="am-u-sm-9">
                                        <select id="email" name="email">
	                                    	<option value="2" selected>===请选择性别===</option>
                                    		<option value="1" <c:if test="${teacher.email==1 }">selected</c:if>>男</option>
                                    		<option value="0" <c:if test="${teacher.email==0 }">selected</c:if>>女</option>
                                    	</select>
                                    </div>
                                </div>

                                <div class="am-form-group">
                                    <label for="user-phone" class="am-u-sm-3 am-form-label">电话</label>
                                    <div class="am-u-sm-9">
                                        <input name="telephone" value="${teacher.telephone }" type="tel" id="telephone" placeholder="输入您的电话号码 ">
                                        <span id="telephoneTitle"></span>
                                    </div>
                                </div>
                                
                                <div class="am-form-group">
                                    <label for="user-address" class="am-u-sm-3 am-form-label">地址</label>
                                    <div class="am-u-sm-9">
                                        <input name="address" value="${teacher.address }" type="text" id="address" placeholder="输入您的地址 ">
                                        <span id="addressTitle"></span>
                                    </div>
                                </div>

                                <div class="am-form-group">
                                    <label for="user-edu" class="am-u-sm-3 am-form-label">学历</label>
                                    <div class="am-u-sm-9">
                                        <input name="education" value="${teacher.education }" type="text" id="education" placeholder="输入您的学历">
                                        <span id="educationTitle"></span>
                                    </div>
                                </div>
                                
                                <div class="am-form-group">
                                    <label for="user-edu" class="am-u-sm-3 am-form-label">身份证号</label>
                                    <div class="am-u-sm-9">
                                        <input onfocus="remove()" onblur="LoginNameContentChecked(this.value)" required name="idcard" minlength="18" maxlength="18" value="${teacher.idcard }" type="text" id="idcard" placeholder="输入18位身份证号">
                                        <span id="idcardTitle"></span>
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

    </div>

</body>
<script type="text/javascript">
	$(function() {
		$("a[menuId='${menuId}']").trigger("click");
	})
</script>
</html>