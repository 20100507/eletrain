<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<body>
	<div class="tpl-page-container tpl-page-header-fixed">
		<div class="tpl-left-nav tpl-left-nav-hover">
			<div class="tpl-left-nav-title">国家电网培训</div>
			<div class="tpl-left-nav-list">
				<ul class="tpl-left-nav-menu">
					<li class="tpl-left-nav-item"><a href="${pageContext.request.contextPath }/function/index"
						class="nav-link active"> <i class="am-icon-home"></i> <span>首页</span>
					</a></li>
					<c:forEach items="${functionParent}" var="par">
						<li class="tpl-left-nav-item"><a href="javascript:void(0); "
							class="nav-link tpl-left-nav-link-list" menuId="${par.id}"> <i
								class="am-icon-table"></i> <span>${par.description }</span> <i
								class="am-icon-angle-right tpl-left-nav-more-ico am-fr am-margin-right"></i>
						</a></li>
					</c:forEach>
				</ul>
			</div>
		</div>
	</div>
</body>
<script src="${pageContext.request.contextPath}/assets/js/jquery.min.js"></script>
<script src="${pageContext.request.contextPath}/assets/js/amazeui.min.js"></script>
<script src="${pageContext.request.contextPath}/assets/js/app.js"></script>
</html>