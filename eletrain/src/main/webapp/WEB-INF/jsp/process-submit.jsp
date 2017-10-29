<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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
	<script
		src="${pageContext.request.contextPath}/assets/js/jquery.min.js"></script>
	<script
		src="${pageContext.request.contextPath}/assets/js/amazeui.min.js"></script>
<script type="text/javascript">

//下载模板弹框
$(function() {
	var $modal = $('#your-modal');
	$modal.siblings('#open').on('click', function(e) {
		var $target = $(e.target);
		if (($target).hasClass('js-modal-open')) {
			$modal.modal();
		} else if (($target).hasClass('js-modal-close')) {
			$modal.modal('close');
		} else {
			$modal.modal('toggle');
		}
	});
});

//下载弹框
function b(id) {
	var $modal = $('#modaldownload'+id);
	$('#downul'+id).empty();
	$.ajax({
		type : "post",//数据发送的方式（post 或者 get）
		traditional : true,
		url : "/plan/exportFile",//要发送的后台地址
		data : {
			"planId" : id
		},//要发送的数据（参数）格式为{'val1':"1","val2":"2"}
		success : function(data) {//ajax请求成功后触发的方法
			for (var i = 0; i < data.length; i++) {
			$('#downul'+id).append("<li class='am-modal-actions-header'><a href='${pageContext.request.contextPath }/plan/downloadAllFile?path="+encodeURI(encodeURI(data[i])) +"'>" + data[i].substring(data[i].lastIndexOf("\\")+1) + "</a></li>");
			}
		}

	});
	$modal.modal('open');
	
};
</script>
<body data-type="generalComponents">
	<jsp:include page="head.jsp"></jsp:include>
	<jsp:include page="side.jsp"></jsp:include>
	<div class="tpl-content-wrapper">
		<div class="tpl-portlet-components">
			<div class="portlet-title">
				<div class="caption font-green bold">
					<span class="am-icon-code"></span> 提交审批
				</div>
			</div>
			<div class="tpl-block ">
				<div class="am-g tpl-amazeui-form">
					<div class="am-u-sm-12 am-u-md-9">
						<form class="am-form am-form-horizontal"
							action="${pageContext.request.contextPath }/process/processFlow?menuId=30"
							method="post" onsubmit="return checkForm();">
							<div class="am-form-group">
								<label for="user-name" class="am-u-sm-3 am-form-label">申请方案名称:</label>
								<div class="am-u-sm-9">${appInfo.title }</div>
							</div>
							<div class="am-form-group">
								<label for="user-name" class="am-u-sm-3 am-form-label">申请时间:</label>
								<div class="am-u-sm-9">
									<fmt:formatDate value="${appInfo.applyDate }" type="both" />
								</div>
							</div>
							<div class="am-form-group">
								<label for="user-name" class="am-u-sm-3 am-form-label">文档下载[方案详情]:</label>
								<button onclick="b('${appInfo.planId }')" type="button"
								   class="am-btn am-btn-default js-modal-open">
								 <span class="am-icon-archive"></span> 下载方案文档
							     </button>
							</div>
							<!-- 文档相关下载 -->
							<div class="am-modal am-modal-no-btn" tabindex="-1"id="modaldownload${appInfo.planId }">
								<div style="width: 800px;" class="am-modal-dialog">
								<div class="am-modal-hd">
								方案相关文档下载 <a href="javascript: void(0)"
								class="am-close am-close-spin" data-am-modal-close>&times;</a>
								</div>
								<div class="am-modal-bd">
										<div class="am-modal-actions-group">
											<ul id="downul${appInfo.planId }" class="am-list"></ul>
											</div>
										</div>
								</div>
							</div>
							<input type="hidden" name="applicationId"value="${appInfo.applicationId }">
						    <input type="hidden" name="adminId" value="${user.adminId }">
						    <input type="hidden" name="approval" value="true" id="approval">
						    <input type="hidden" name="taskId" value="${taskId}">
							<div class="am-form-group">
								<label for="doc-ta-1">审批意见</label>
								<textarea  onkeyup="ta(this)" name="comment" class="" rows="5" id="user-name"></textarea>
							</div>
							<div class="am-form-group">
								<div class="am-u-sm-9 am-u-sm-push-3">
									<button type="submit" class="am-btn am-btn-success" onclick="$('#approval').val('true')">通过</button>
									&nbsp;&nbsp;&nbsp;&nbsp;
									<button type="submit" class="am-btn am-btn-danger" onclick="$('#approval').val('false')">拒绝</button>
									&nbsp;&nbsp;&nbsp;&nbsp;
									<button type="button" class="am-btn am-btn-warning"
										onclick="javascript:history.back(-1);">返回</button>
								</div>
							</div>
						</form>
					</div>
				</div>
			</div>
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

	<%-- <script src="${pageContext.request.contextPath}/assets/js/app.js"></script> --%>
</body>
<script type="text/javascript">
	$(function() {
		$("a[menuId='${menuId}']").trigger("click");
	})
	
	function ta(obj){
	var val=$(obj).val().length;
	if(val>30){
		var $modal = $('#your-modal');
		$modal.modal('open');
		$('#ts').html("最多输入30个字符");
		$(obj).val($(obj).val().substring(0,30))
		}
}
</script>
</html>