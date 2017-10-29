<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!doctype html>
<html>

<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>增加或编辑课表</title>
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

	<script
		src="${pageContext.request.contextPath}/assets/js/jquery.min.js"></script>
	<script
		src="${pageContext.request.contextPath}/assets/js/amazeui.min.js"></script>
	<%-- <script src="${pageContext.request.contextPath}/assets/js/app.js"></script> --%>	
<script type="text/javascript">
var subStatus='1';
/* function subForm(){
	if(subStatus=='0'){
		alert("表单填写不正确！");
	}
	if(subStatus=='1'){
		$('#syllabusform').submit();
	}
} */

function checkDatePlace(){
	var currentValue = $('#week').val();
	if($('#classId').val()!=''&&$('#week').val()!=''){
		$.ajax({
	        cache: true,
	        type: "POST",
	        url:"${pageContext.request.contextPath }/syllabus/checkDatePlace",
	        data:$('#syllabusform').serialize(),
	        async: false,
	        error: function(request) {
	        },
	        success: function(data) {
	        	jsonDate = $.parseJSON(data);
	            if(jsonDate.returnValue!=""&&currentValue!='${syllabus.week }'){
	            	subStatus='0';
	            	$('#returnValue').html('<span style="color:#FF0000">'+jsonDate.returnValue+'</span>');
	            	$('#amValue').nextAll().remove();
	            	$('#pmValue').nextAll().remove();
	            	$('#niValue').nextAll().remove();
	            }else{
	            	subStatus='1';
	            	$('#returnValue').html('');
	            	$('#amValue').nextAll().remove();
	            	$('#pmValue').nextAll().remove();
	            	$('#niValue').nextAll().remove();
	            }
	            if(jsonDate.amValue!=''){
	            	$('#amfirst').append(jsonDate.amValue);
	            }
	            if(jsonDate.pmValue!=''){
	            	$('#pmfirst').append(jsonDate.pmValue);
	            }
	            if(jsonDate.niValue!=''){
	            	$('#night').append(jsonDate.niValue);
	            }
	           
	        }
	    });
	}
}
/* 确认提交 */
$(function() {
	  $('#doc-modal-list').find('.am-icon-close').add('#doc-confirm-toggle').
	    on('click', function() {
	    	if($('#classId').val()==''||$('#week').val()==''){
	    		alert("您没有选择班级或日期！");
	    		return;
	    	}
	    	if(subStatus=='0'){
	    		alert("表单填写不正确！");
	    		return;
	    	}
	    	if($('#amfirst').val()==''||$('#pmfirst').val()==''||$('#night').val()==''){
		    	$('#messageShow').html('<span style="color:#FF0000">您存在没有选择的课程，确认提交吗？</span>');
	    	}
	    	else{
	    		$('#messageShow').html('您已经选择了课表，确认要提交吗？');
	    	}
	      $('#my-confirm').modal({
	        relatedTarget: this,
	        onConfirm: function(options) {
	        	if(subStatus=='1'){
		    		$('#syllabusform').submit();
		    	}
	        },
	        onCancel: function() {
	          
	        }
	      });
	    });
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
					<span class="am-icon-code"></span> 表单
				</div>
			</div>
			<div class="tpl-block ">
				<div class="am-g tpl-amazeui-form">
					<div class="am-u-sm-12 am-u-md-9">
						<form id="syllabusform" data-am-validator method="post" class="am-form am-form-horizontal"
							action="${pageContext.request.contextPath }/syllabus/${ syllabus== null ? 'add' : 'update' }Syllabus?menuId=5">
							<input type="hidden" name="syllabusId"
								value="${syllabus.syllabusId }">
							<input type="hidden" name="oldWeek"
								value="${syllabus.week }">
							
							<div class="am-form-group">
								<label for="user-name4" class="am-u-sm-3 am-form-label">班级</label>
								<div class="am-u-sm-9">
									<select onchange="checkDatePlace()" name="classId" id="classId" required>
										<option value="">===================请选择班级==================</option>
										<c:forEach items="${classesList }" var="classes">
											<option
												<c:if test="${classes.classesId==syllabus.classId }">selected</c:if>
												value="${classes.classesId }">${classes.cname }</option>
										</c:forEach>
									</select>
								</div>
							</div>

							<div class="am-form-group">
								<label for="user-phone" class="am-u-sm-3 am-form-label">日期</label>
								<div class="am-u-sm-9">
									<input onchange="checkDatePlace()" required readonly name="week"  id="week" value="${syllabus.week }" type="text"
										id="week" placeholder="===========================请选择日期========================="
										data-am-datepicker>
									<span id="returnValue"></span>
								</div>
							</div>
							
							<div class="am-form-group">
								<label for="user-name1" class="am-u-sm-3 am-form-label">上午课</label>
								<div class="am-u-sm-9">
									<select name="amfirst" id="amfirst">
										<option id="amValue" value="">==========请选择课程(若选择此项，默认为无课)==========</option>										
										<c:forEach items="${amCDVList }" var="cdv">
											<option
												<c:if test="${cdv.amCourse.courseId==syllabus.amfirst }">selected</c:if>
												value="${cdv.amCourse.courseId }">${cdv.amCourse.cname }(${cdv.amCourse.place }--${cdv.amTeacher.username })</option>
										</c:forEach>
									</select>
								</div>
							</div>
							<div class="am-form-group">
								<label for="user-name2" class="am-u-sm-3 am-form-label">下午课</label>
								<div class="am-u-sm-9">
									<select name="pmfirst" id="pmfirst">
										<option id="pmValue" value="">==========请选择课程(若选择此项，默认为无课)==========</option>
										<c:forEach items="${pmCDVList }" var="cdv">
											<option
												<c:if test="${cdv.pmCourse.courseId==syllabus.pmfirst }">selected</c:if>
												value="${cdv.pmCourse.courseId }">${cdv.pmCourse.cname }(${cdv.pmCourse.place }--${cdv.pmTeacher.username })</option>
										</c:forEach>
									</select>
								</div>
							</div>

							<div class="am-form-group">
								<label for="user-name3" class="am-u-sm-3 am-form-label">晚上课</label>
								<div class="am-u-sm-9">
									<select name="night" id="night">
										<option id="niValue" value="">==========请选择课程(若选择此项，默认为无课)==========</option>
										<c:forEach items="${niCDVList }" var="cdv">
											<option
												<c:if test="${cdv.niCourse.courseId==syllabus.night }">selected</c:if>
												value="${cdv.niCourse.courseId }">${cdv.niCourse.cname }(${cdv.niCourse.place }--${cdv.niTeacher.username })</option>
										</c:forEach>
									</select>
								</div>
							</div>

							<div class="am-form-group">
								<div class="am-u-sm-9 am-u-sm-push-3">
									<button id="doc-confirm-toggle" type="button" class="am-btn am-btn-primary">保存修改</button>
									<button type="button" class="am-btn am-btn-primary"
										onclick="javascript:history.back(-1);">返回</button>
								</div>
							</div>
						</form>
							<div class="am-modal am-modal-confirm" tabindex="-1"
								id="my-confirm">
								<div class="am-modal-dialog">
									<div class="am-modal-hd">课表确认</div>
									<div id="messageShow" class="am-modal-bd"></div>
									<div class="am-modal-footer">
										<span class="am-modal-btn" data-am-modal-cancel>取消</span> <span
											class="am-modal-btn" data-am-modal-confirm>确定</span>
									</div>
								</div>
							</div>
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