<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
	<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!doctype html>
<html>

<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>编辑学员</title>
<meta name="description" content="编辑学员">
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
</head>
<script src="${pageContext.request.contextPath}/assets/js/jquery.min.js"></script>
<script type="text/javascript">
function newPic() {
        var windowURL = window.URL || window.webkitURL;
        var loadImg = windowURL.createObjectURL(document.getElementById('f').files[0]);
        document.getElementById('img').setAttribute('src',loadImg);
}  
</script>
<body data-type="generalComponents">
<jsp:include page="head.jsp"></jsp:include>
	<jsp:include page="side.jsp"></jsp:include>
		<div class="tpl-content-wrapper">
			<div class="tpl-portlet-components">
				<div class="portlet-title">
					<div class="caption font-green bold">
						<span class="am-icon-code"></span> 编辑学员
					</div>
				</div>
				<div class="tpl-block ">
					<div class="am-g tpl-amazeui-form">
						<div class="am-u-sm-12 am-u-md-9">
							<form id="editStuForm" class="am-form am-form-horizontal"
								action="${pageContext.request.contextPath }/student/editStu"
								method="post" enctype="multipart/form-data">
								<input type="hidden" name="studentId"
									value="${stuInfo.studentId }">
								<div class="am-form-group">
									<label for="user-name" class="am-u-sm-3 am-form-label">姓名
										</label>
									<div class="am-u-sm-9">
										<input pattern="^\S*" type="text" id="partyName" name="username"
											value="${stuInfo.username }" placeholder="姓名 " required>
									</div>
								</div>
								<div class="am-form-group">
									<label for="user-name" class="am-u-sm-3 am-form-label">性别</label> 
									<label class="am-radio-inline">
										<c:if test="${stuInfo.email=='男'}">
									    <input type="radio" name="email" value="男" checked data-am-ucheck required> 男
									    </c:if>
									     <c:if test="${stuInfo.email=='女'}">
									    <input type="radio" name="email" value="男" data-am-ucheck required> 男
									    </c:if>
									  </label>
									  <label class="am-radio-inline">
									  <c:if test="${stuInfo.email=='女'}">
									    <input type="radio" name="email" value="女" checked data-am-ucheck> 女
									     </c:if>
									     <c:if test="${stuInfo.email=='男'}">
									     <input type="radio" name="email" value="女" data-am-ucheck> 女
									     </c:if>
									  </label>
								</div>
								<div class="am-form-group">
									<label for="user-phone" class="am-u-sm-3 am-form-label">电话
										</label>
									<div class="am-u-sm-9">
										<input type="tel" name="telephone"
											value="${stuInfo.telephone }" id="phone"
											placeholder="输入你的电话号码" pattern="^1[34578]\d{9}$"
											required>
									</div>
								</div>
								<div class="am-form-group">
									<label for="user-name" class="am-u-sm-3 am-form-label">学历</label>
									<div class="am-u-sm-9">
										<%-- <input pattern="^\S*" type="text" id="user-name"
											value="${stuInfo.education }" name="education"
											placeholder="学历" required> --%>
											<select id="education" name="education" data-am-selected>
											<option name="xl" value="a">未填</option>
										<option name="xl" value="硕士及以上学历">硕士及以上学历</option>
										<option name="xl" value="本科">本科</option>
										<option name="xl" value="大专">大专</option>
										<option name="xl" value="高中">高中</option>
										<option name="xl" value="初中">初中</option>
									</select>
									</div>
								</div>
								<div class="am-form-group">
									<label for="user-name" class="am-u-sm-3 am-form-label">身份证号</label>
									<div class="am-u-sm-9">
										<input type="text" id="certNumber" name="idcard"
											value="${stuInfo.idcard }" placeholder="18位身份证号"
											pattern="^([0-9]){7,18}(x|X)?$" required>
									</div>
								</div>
								<div class="am-form-group">
									<label for="user-name" class="am-u-sm-3 am-form-label">单位</label>
									<div class="am-u-sm-9">
										<input pattern="^\S*" type="text" id="address" name="address"
											value="${stuInfo.address }" placeholder="地址" required>
									</div>
								</div>
								<div class="am-form-group">
								<label for="user-name" class="am-u-sm-3 am-form-label">头像</label>
									<div class="am-u-sm-9">
										<img height="108px" width="91px" id="img" src="/${stuInfo.picpath }"></img>
									<input type="file" id="f" name="file" onchange="newPic()"/>
									</div>
								</div>
								<div class="am-modal am-modal-no-btn" tabindex="-1" id="your-modal">
							  <div class="am-modal-dialog">
							    <div class="am-modal-hd">提示
							      <a href="javascript: void(0)" class="am-close am-close-spin" data-am-modal-close>&times;</a>
							    </div>
							    <div id="ts" class="am-modal-bd">
							    </div>
							  </div>
							</div>
								<div class="am-form-group">
									<div class="am-u-sm-9 am-u-sm-push-3">
										<button type="button" onclick="checkForm();" class="am-btn am-btn-primary">修改</button>
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
$(document).ready(function() {
	a();
});
function a(){
	var checkBoxAll =$("option[name='xl']");
	var checkArray="${stuInfo.education }";
	var a = 0;
	  $.each(checkBoxAll,function(j,checkbox){
		  a ++;
		 var checkValue=$(checkbox).val();
		 if(checkArray==checkValue){
			 $('[data-am-selected]').eq(0).selected('select', a-2);
		 }
		 if (checkArray=="未填") {
			 $('[data-am-selected]').eq(0).selected('select',0);
		}
	 });
}
function checkForm() {
	var $modal = $('#your-modal');
	 var name = $('#partyName').val().replace(/(^\s*)|(\s*$)/g, "");
	var phone = $('#phone').val().replace(/(^\s*)|(\s*$)/g, "");
	var idcard = $('#certNumber').val().replace(/(^\s*)|(\s*$)/g, "");
	if (name == "" || name.indexOf(" ") >=0) {
		$modal.modal('open');
		$('#ts').html("姓名不能为空，不能带空格");
		return;
	}
	if (!(/^1[34578]\d{9}$/.test(phone))) {
		$modal.modal('open');
		$('#ts').html("电话号码有误");
		return;
	}
	if (!(/^([0-9]){7,18}(x|X)?$/.test(idcard))) {
		$modal.modal('open');
		$('#ts').html("身份证输入错误");
		return;
	}
	$('#editStuForm').submit(); 

}
$(function(){
$("a[menuId='${menuId}']").trigger("click");
})
</script>
</html>