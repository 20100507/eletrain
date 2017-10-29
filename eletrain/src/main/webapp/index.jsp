<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="description" content="">
<meta name="keywords" content="">
<meta name="viewport"
	content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
<!-- Set render engine for 360 browser -->
<meta name="renderer" content="webkit">
<!-- No Baidu Siteapp-->
<meta http-equiv="Cache-Control" content="no-siteapp" />
<!-- Add to homescreen for Chrome on Android -->
<meta name="mobile-web-app-capable" content="yes">
<!-- Add to homescreen for Safari on iOS -->
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="apple-mobile-web-app-status-bar-style" content="black">
<!-- Tile icon for Win8 (144x144 + tile color) -->
<meta name="msapplication-TileColor" content="#0e90d2">
<link rel="stylesheet" href="assets/css/amazeui.min.css">
<link rel="stylesheet" href="assets/css/app1.css">
</head>
<body>
	<header>
	</header>
	<div class="log">
		
		<div class="am-g">
			<div
				class="am-u-lg-3 am-u-md-6 am-u-sm-8 am-u-sm-centered log-content">
				<h1 class="log-title am-animation-slide-top">系统登录</h1>
				<br>
				<form class="am-form" id="log-form"
					action="${pageContext.request.contextPath }/function/index"
					method="post">
					<div class="am-input-group am-radius am-animation-slide-left">
						<input type="text" name="username" id="doc-vld-email-2-1"
							class="am-radius" data-validation-message="请输入用户名"
							placeholder="用户名" required value="${errorName}"/>
							 <span
							class="am-input-group-label log-icon am-radius"><i
							class="am-icon-user am-icon-sm am-icon-fw"></i></span>
					</div>
					<br>
					<div
						class="am-input-group am-animation-slide-left log-animation-delay">
						<input type="password" name="password"
							class="am-form-field am-radius log-input" placeholder="密码"
							data-validation-message="请输入密码" required> 
							<span
							class="am-input-group-label log-icon am-radius"><i
							class="am-icon-lock am-icon-sm am-icon-fw"></i></span>
					</div>
					<br>
					<button type="submit"
						class="am-btn am-btn-primary am-btn-block am-btn-lg am-radius am-animation-slide-bottom log-animation-delay"
						id="denglu">登 录</button>
				</form>
			</div>
		</div>
		<footer class="log-footer"> </footer>
	</div>
	<!--[if (gte IE 9)|!(IE)]><!-->
	<script src="assets/js/jquery.min.js"></script>
	<!--<![endif]-->
	<!--[if lte IE 8 ]>
<script src="http://libs.baidu.com/jquery/1.11.3/jquery.min.js"></script>
<script src="http://cdn.staticfile.org/modernizr/2.8.3/modernizr.js"></script>
<script src="assets/js/amazeui.ie8polyfill.min.js"></script>
<![endif]-->
	<script src="assets/js/app1.js"></script>
	<script src="assets/js/amazeui.min.js"></script>
	<script src="assets/js/amazeui.dialog.min.js"></script>
	<buttong class="am-btn am-btn-primary js-alert" style="display:none;">点击显示 Alert</buttong>
</body>
<script>
$('.js-alert').on('click', function() {
    AMUI.dialog.alert({
      title: '温馨提示',
      content: '用户名或者密码错误！',
      onConfirm: function() {
        console.log('close');
      }
    });
  });

$(function(){
	if("${error}" != ""){
	$('.js-alert').trigger("click");
	}
})
</script>
</html>