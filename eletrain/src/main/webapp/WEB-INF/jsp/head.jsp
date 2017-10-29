<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
<head>
<title></title>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>首部页面</title>
<meta name="description" content="首部页面">
<meta name="keywords" content="index">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="renderer" content="webkit">
<meta http-equiv="Cache-Control" content="no-siteapp" />
<link rel="icon" type="image/png" href="${pageContext.request.contextPath}/assets/i/favicon.ico">
<link rel="apple-touch-icon-precomposed"
	href="assets/i/app-icon72x72@2x.png">
<meta name="apple-mobile-web-app-title" content="首部页面" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/amazeui.min.css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/admin.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/app.css">
<script
	src="${pageContext.request.contextPath}/assets/js/amazeui.dialog.min.js"></script>
</head>
<body>
	<!-- 密码修改确认框 -->
	<div class="am-modal am-modal-prompt" tabindex="-1" id="my-prompt">
		<div class="am-modal-dialog">
			<div class="am-modal-hd">修改       密码</div>
			新密码:<input type="password" class="am-modal-prompt-input">
			重复密码:<input type="password" class="am-modal-prompt-input">
			<div class="am-modal-footer">
				<span class="am-modal-btn" data-am-modal-cancel>取消修改</span> <span
					class="am-modal-btn" data-am-modal-confirm>确认修改</span>
			</div>
		</div>
	</div>
	<header class="am-topbar am-topbar-inverse admin-header">
		<div class="am-topbar-brand">
			<a href="${pageContext.request.contextPath }/function/index" class="tpl-logo"> <img
				src="${pageContext.request.contextPath }/assets/img/logo.png" alt="">
			</a>
		</div>
		<div
			class="am-icon-list tpl-header-nav-hover-ico am-fl am-margin-right"
			id="hidden" style="cursor:pointer;"></div>

		<button
			class="am-topbar-btn am-topbar-toggle am-btn am-btn-sm am-btn-success am-show-sm-only"
			data-am-collapse="{target: '#topbar-collapse'}" >
			<span class="am-sr-only">导航切换</span> <span class="am-icon-

bars"></span>
		</button>

		<div class="am-collapse am-topbar-collapse" id="topbar-collapse">

			<ul
				class="am-nav am-nav-pills am-topbar-nav am-topbar-right admin-header-list tpl-header-list">
				<li class="am-dropdown" data-am-dropdown data-am-dropdown-toggle>
					<a class="am-dropdown-toggle tpl-header-list-link"
					href="javascript:;"> <span class="am-icon-comment-o"></span>公告
						<span class="am-badge tpl-badge-danger am-
round">${count1 }</span>
				</a>
					<ul class="am-dropdown-content tpl-dropdown-content">
						<li class="tpl-dropdown-content-external">
							<h3>
								你有 <span class="tpl-color-danger">${count1 }</span> 条新消息
							</h3> 
							<c:if test="${user.identify == 1}">
							<a href="${pageContext.request.contextPath }/notice/getNoticeListByPage?&menuId=6">全部</a>
							</c:if>
							<c:if test="${user.identify != 1}">
							<a href="${pageContext.request.contextPath }/notice/getNoticeListByPage?&menuId=11">全部</a>
							</c:if>
						</li>
						<li>
						<c:if test="${notices[0] != null }">
						<a href="${pageContext.request.contextPath }/notice/toNoticeInfo?id=${notices[0].noticeId }" class="tpl-dropdown-content-message"> <span
								class="tpl-dropdown-content-photo"> <span
								class="am-icon-

btn am-icon-bell-o tpl-dropdown-ico-btn-size tpl-badge-warning"></span>
							</span> <span class="tpl-dropdown-content-subject"> <span
									class="tpl-dropdown-content-from"> ${notices[0].admin.username } </span> <span
									class="tpl-dropdown-content-time"><fmt:formatDate
												value="${notices[0].createtime }" pattern="yyyy-MM-dd" /> </span>
							</span> <span class="tpl-dropdown-content-font"> ${notices[0].title } </span>
						</a> 
						</c:if>
						<c:if test="${notices[1] != null }">
						<a href="${pageContext.request.contextPath }/notice/toNoticeInfo?id=${notices[1].noticeId }" class="tpl-dropdown-content-message"> <span
								class="tpl-dropdown-content-photo"> <span
								class="am-icon-

btn am-icon-bell-o tpl-dropdown-ico-btn-size tpl-badge-warning"></span>
							</span> <span class="tpl-dropdown-content-subject"> <span
									class="tpl-dropdown-content-from">${notices[1].admin.username } </span> <span
									class="tpl-dropdown-content-time"><fmt:formatDate
												value="${notices[1].createtime }" pattern="yyyy-MM-dd" /></span>
							</span> <span class="tpl-dropdown-content-font"> ${notices[1].title } </span>
						</a>
						</c:if>
						</li>
					</ul>
				</li>
				<li class="am-dropdown" data-am-dropdown data-am-dropdown-toggle>
					<a class="am-dropdown-toggle tpl-header-list-link"
					href="javascript:;"> <span class="am-icon-bell-o"></span> 关于 <span
						class="am-badge tpl-badge-success am-round">1</span>
				</a>
					<ul class="am-dropdown-content tpl-dropdown-content">
						<li class="tpl-dropdown-content-external">
							<h3>
								关于<span class="tpl-color-success"></span>
							</h3> 
						</li>
						<li class="tpl-dropdown-list-bdbc"><a href="http://www.qqhru.edu.cn/"
							class="tpl-dropdown-list-fl"><span
								class="am-icon-
btn am-icon-bell-o tpl-dropdown-ico-btn-size tpl-badge-warning"></span>
								由齐大软件14级BQ,QHL,ZJF,BPF开发</a></li>
					</ul>
				</li>
				
				<li class="am-hide-sm-only"><a href="javascript:;"
					id="admin-fullscreen" class="tpl-header-list-link"><span
						class="am-icon-arrows-alt"></span> <span class="admin-fullText">开启全屏</span></a></li>

				<li class="am-dropdown" data-am-dropdown data-am-dropdown-toggle>
					<a class="am-dropdown-toggle tpl-header-list-link"
					href="javascript:;"> <span class="tpl-header-list-user-nick">欢迎~~
					<c:choose>
						<c:when test="${user.identify == 1}">
							管理员:
						</c:when>
						<c:when test="${user.identify == 2}">
							外聘教师:
						</c:when>
						<c:when test="${user.identify == 3}">
							部门负责人:
						</c:when>
						<c:when test="${user.identify == 4}">
							培训管理部:
						</c:when>
						<c:when test="${user.identify == 5}">
							主管主任:
						</c:when>
						<c:when test="${user.identify == 6}">
							班主任:
						</c:when>
					</c:choose>					
					${user.username}</span>
				</a>
					<ul class="am-dropdown-content">
						<li><a href="javascript:void(0);" id="doc-prompt-toggle"><span
								class="am-icon-cog"></span> 修改密码</a></li>
						<li><a
							href="${pageContext.request.contextPath }/function/logout"><span
								class="am-icon-power-off"></span> 退出</a></li>
					</ul>
				</li>
				<li><a
					href="${pageContext.request.contextPath }/function/logout"
					class="tpl-header-list-link"><span
						class="am-icon-sign-out tpl-header-list-ico-out-
size"></span></a></li>
			</ul>
		</div>
	</header>
	<button class="am-btn am-btn-primary js-alert" style="display:none;"></button>
</body>
<script type="text/javascript">
	$(function() {
		$('#doc-prompt-toggle').on('click', function() {
			$('#my-prompt').modal({
				relatedTarget : this,
				onConfirm : function(e) {
					if (e.data[0].trim == "") {
						return;
					}
					if (e.data[0] != e.data[1]) {
						alert("两次输入不一致！！")
						return;
					}
					$.ajax({
						type : "post",//数据发送的方式（post 或者 get）
						traditional : true,
						url : "/function/modifyPwd",//要发送的后台地址
						data : {
							"newPwd" : e.data[0]
						},
						success : function(data) {
							if (data == "success") {
								alert("修改成功！请重新登录！")
								window.location.href="${pageContext.request.contextPath}/function/logout";
							}else{
								alert("你就根本没有输入密码或你的密码中包含空格！！！")
							}
						}
					});
				},
				onCancel : function(e) {
				}
			});
		});
	})
</script>
</html>