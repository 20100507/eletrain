<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!doctype html>
<html>

<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>添加学员</title>
<meta name="description" content="添加学员">
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
<script type="text/javascript">
window.onload=function(){ 

	connect(); 
	} 
	function clearForm() {
		document.getElementById("partyName").value = "";
		document.getElementById("certNumber").value = "";
		document.getElementById("result").value = "";
		document.getElementById("headBase64Data").value = "";
		document.getElementById("dispHead").src = "";

		var CertCtl = document.getElementById("CertCtl");
		CertCtl.ClearData(3); // 1为控件缓存的读卡数据，2为缓存的指纹数据，3为所有缓存数据
	}
	function connect() {
		clearForm();

		var CertCtl = document.getElementById("CertCtl");
		try {
			var result = CertCtl.OpenComm();
			if (result == "")
				document.getElementById("result").value = "连接成功";
			else
				//document.getElementById("result").value = result;
			//document.getElementById("lj").onclick();
			connect();
		} catch (e) {
		}
	}
	function disconnect() {
		clearForm();

		var CertCtl = document.getElementById("CertCtl");
		try {
			var result = CertCtl.CloseComm();
			if (result == "")
				document.getElementById("result").value = "断开成功";
			else
				document.getElementById("result").value = result;
		} catch (e) {
		}
	}

	function readCert() {
		clearForm();

		var CertCtl = document.getElementById("CertCtl");

		try {
			var startDt = new Date();
			var result = CertCtl.ReadCard();
			var endDt = new Date();

			if (result == "")
				document.getElementById("result").value = "读卡成功";
			else
				document.getElementById("result").value = result;

			if (result == "") {
				document.getElementById("partyName").value = CertCtl.Name;
				if (CertCtl.Sex == "男") {
					$("input[name=email][value=" + CertCtl.Sex + "]").attr(
							"checked", true);
				} else if (CertCtl.Sex == "女") {
					$("input[name=email][value=" + CertCtl.Sex + "]").attr(
							"checked", true);
				}
				document.getElementById("certNumber").value = CertCtl.CardNo;
				document.getElementById("headBase64Data").value = CertCtl.CardPictureData;
				document.getElementById("dispHead").src = 'data:image/jpeg;base64,' + CertCtl.CardPictureData;
			}
		} catch (e) {
		}
	}
</script>
</head>
<body data-type="generalComponents">
	<jsp:include page="head.jsp"></jsp:include>
	<jsp:include page="side.jsp"></jsp:include>
	<div class="tpl-content-wrapper">
		<div class="tpl-portlet-components">
			<div class="portlet-title">
				<div class="caption font-green bold">
					<span class="am-icon-code"></span> 添加学员
				</div>
			</div>
			<div class="am-btn-group am-btn-group-xs">
				<button type="button" onclick="readCert()"
					class="am-btn am-btn-default am-btn-secondary">
					<span class="am-icon-save"></span> 读卡
				</button>
			</div>
			<OBJECT classid="clsid:5819234B-5977-4C82-9C59-A9D3C7D46083"
				id="CertCtl" name="CertCtl" width="0" height="0"> </OBJECT>
			<div class="tpl-block ">
				<div class="am-g tpl-amazeui-form">
					<div class="am-u-sm-12 am-u-md-9">

						<form class="am-form am-form-horizontal"
							action="${pageContext.request.contextPath }/student/addStu?menuId=0"
							method="post" id="addStuForm">
							<input id="headBase64Data" type="hidden" name="headBase64Data">
							<div class="am-form-group">
								<label for="user-name" class="am-u-sm-3 am-form-label">操作结果</label>
								<div class="am-u-sm-9">
									<input type="text" id="result" style="width: 100px;"
										readonly="readonly">
								</div>
							</div>
							<div class="am-form-group">
								<label for="user-name" class="am-u-sm-3 am-form-label">姓名
								</label>
								<div class="am-u-sm-9">
									<input pattern="^\S*" type="text" id="partyName"
										name="username" placeholder="姓名" required>
								</div>
							</div>
							<div class="am-form-group">
								<label for="user-name" class="am-u-sm-3 am-form-label">性别</label>
								<label class="am-radio-inline"> <input id="gender1"
									type="radio" name="email" value="男" data-am-ucheck required>
									男
								</label> <label class="am-radio-inline"> <input id="gender2"
									type="radio" name="email" value="女" data-am-ucheck> 女
								</label>
							</div>
							<div class="am-form-group">
								<label for="user-phone" class="am-u-sm-3 am-form-label">电话
								</label>
								<div class="am-u-sm-9">
									<input type="tel" name="telephone" id="phone"
										placeholder="输入你的电话号码 " required> 
										
								</div>
							</div>
							<div class="am-form-group">
								<label for="user-name" class="am-u-sm-3 am-form-label">学历</label>
								<div class="am-u-sm-9">
										<select id="education" name="education" data-am-selected>
										<option value="">请选择</option>
										<option value="硕士及以上学历">硕士及以上学历</option>
										<option value="本科">本科</option>
										<option value="大专">大专</option>
										<option value="高中">高中</option>
										<option value="初中">初中</option>
									</select>
								</div>
							</div>
							<div class="am-form-group">
								<label for="user-name" class="am-u-sm-3 am-form-label">身份证号</label>
								<div class="am-u-sm-9">
									<input type="text" id="certNumber" name="idcard"
										placeholder="18位身份证号" pattern="^([0-9]){7,18}(x|X)?$" required>
								</div>
							</div>
							<div class="am-form-group">
								<label for="user-name" class="am-u-sm-3 am-form-label">单位</label>
								<div class="am-u-sm-9">
									<input pattern="^\S*" type="text" id="address" name="address"
										placeholder="单位" required>
								</div>
							</div>
							<div id="image">
										<img id="dispHead" style="width: 91px; height: 108px" name="dispHead" src=""/>
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
									<button onclick="checkForm();" type="button"
										class="am-btn am-btn-primary">添加</button>
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
		check();
	});
	function check() {
		$('#loginnamecheck').blur(function() {
			var a = $(this).val();
			$.ajax({
				type : "post",//数据发送的方式（post 或者 get）
				traditional : true,
				url : "/student/checkNo",//要发送的后台地址
				data : {
					"loginname" : a
				},//要发送的数据（参数）格式为{'val1':"1","val2":"2"}
				success : function(data) {//ajax请求成功后触发的方法
					if (data == "exist") {
						$('#sp').html("学号已经存在");
						$('#loginnamecheck').prop("value", "");
					} else {
						$('#sp').html("");
					}
				}
			});
		});
	}
	function checkForm() {
		var $modal = $('#your-modal');
		 var name = $('#partyName').val().replace(/(^\s*)|(\s*$)/g, "");
		var phone = $('#phone').val().replace(/(^\s*)|(\s*$)/g, "");
		var idcard = $('#certNumber').val().replace(/(^\s*)|(\s*$)/g, "");
		if (name == "") {
			$modal.modal('open');
			$('#ts').html("姓名不能为空");
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
		$('#addStuForm').submit(); 

	}
	$(function() {
		$("a[menuId='${menuId}']").trigger("click");
	})
</script>
</html>