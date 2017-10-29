<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!doctype html>
<html>

<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>编辑教材</title>
<meta name="description" content="编辑教材">
<meta name="keywords" content="index">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="renderer" content="webkit">
<meta http-equiv="Cache-Control" content="no-siteapp" />
<link rel="icon" type="image/png"
	href="${pageContext.request.contextPath}/assets/i/favicon.png">
<link rel="apple-touch-icon-precomposed"
	href="${pageContext.request.contextPath}/assets/i/app-icon72x72@2x.png">
<meta name="apple-mobile-web-app-title" content="Amaze UI" />
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/assets/css/amazeui.min.css" />
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/assets/css/admin.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/assets/css/app.css">
	 <script src="${pageContext.request.contextPath}/assets/js/jquery.min.js"></script>
    <script src="${pageContext.request.contextPath}/assets/js/amazeui.min.js"></script>
</head>
<body data-type="generalComponents">
<jsp:include page="head.jsp"></jsp:include>
	<jsp:include page="side.jsp"></jsp:include>
		<div class="tpl-content-wrapper">
			<div class="tpl-portlet-components">
				<div class="portlet-title">
					<div class="caption font-green bold">
						<span class="am-icon-code"></span> 编辑教材
					</div>
				</div>
				<div class="tpl-block ">
					<div class="am-g tpl-amazeui-form">
						<div class="am-u-sm-12 am-u-md-9">
							<form class="am-form am-form-horizontal"
								action="${pageContext.request.contextPath }/books/editBook"
								method="post">
								<input type="hidden" name="bookId"
									value="${bookInfo.bookId}">
								<div class="am-form-group">
									<label for="user-name" class="am-u-sm-3 am-form-label">教材名称</label>
									<div class="am-u-sm-9">
										<input pattern="^\S*" type="text" id="user-name" name="bname"
										value="${bookInfo.bname}"	placeholder="教材名称" required>
									</div>
								</div>
								<div class="am-form-group">
									<label for="user-name" class="am-u-sm-3 am-form-label">作者
										</label>
									<div class="am-u-sm-9">
										<input pattern="^\S*" type="text" id="user-name" name="author"
											value="${bookInfo.author}" placeholder="作者" required>
									</div>
								</div>
								
								<div class="am-form-group">
									<label for="user-name" class="am-u-sm-3 am-form-label">出版社</label>
									<div class="am-u-sm-9">
										<input pattern="^\S*" type="text" id="user-name" name="press"
											value="${bookInfo.press}" placeholder="出版社" required>
									</div>
								</div>
								<div class="am-form-group">
									<label for="user-name" class="am-u-sm-3 am-form-label">数量</label>
									<div class="am-u-sm-9">
										<input maxlength="4" pattern="^[0-9]*$" type="text" id="user-name" name="count"
											value="${bookInfo.count}" placeholder="数量" required>
									</div>
								</div>
								<div class="am-form-group">
									<div class="am-u-sm-9 am-u-sm-push-3">
										<button type="submit" class="am-btn am-btn-primary">修改</button>
										<button type="button" class="am-btn am-btn-primary"
											onclick="javascript:history.back(-1);">返回</button>
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
$(function(){
$("a[menuId='${menuId}']").trigger("click");
})
</script>
</html>