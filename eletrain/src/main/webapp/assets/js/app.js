$(function() {

	var $fullText = $('.admin-fullText');
	$('#admin-fullscreen').on('click', function() {
		$.AMUI.fullscreen.toggle();
	});

	$(document).on($.AMUI.fullscreen.raw.fullscreenchange, function() {
		$fullText.text($.AMUI.fullscreen.isFullscreen ? '退出全屏' : '开启全屏');
	});

//	var dataType = $('.content').attr('data-type');
//	for (key in pageData) {
//		if (key == dataType) {
//			pageData[key]();
//		}
//	}

	$('.tpl-switch').find('.tpl-switch-btn-view').on('click', function() {
		$(this).prev('.tpl-switch-btn').prop("checked", function() {
			if ($(this).is(':checked')) {
				return false
			} else {
				return true
			}
		})
		// console.log('123123123')

	})
})
// ==========================
// 侧边导航下拉列表
// ==========================
$('.tpl-left-nav-link-list')
		.on(
				'click',
				function() {
					var flag = true;
					$(this).siblings('.tpl-left-nav-sub-menu').slideToggle(80)
							.end().find('.tpl-left-nav-more-ico').toggleClass(
									'tpl-left-nav-more-ico-rotate');
					var nextThis = $(this);
					if ($(this).next().length > 0) {
						$(this).next().remove();
						flag = false;
					}
					var menuId = $(this).attr("menuId");
					$
							.ajax({
								type : "post",
								url : "/function/next",
								data : {
									"id" : $(this).attr("menuId")
								},
								success : function(data) {
									if (flag) {
										var ul = $("<ul class='tpl-left-nav-sub-menu' style='display: block;'><ul>");
										nextThis.after(ul);
										var li = $("<li></li>")
										$
												.each(
														data,
														function(i, val) {
															var a = "";
															if(data[i].page.indexOf("?") >= 0){
																a = $("<a href="
																		+ data[i].page
																		+ "&menuId="
																		+ menuId
																		+ " class='three first'></a>");
															}else{
																a = $("<a href="
																		+ data[i].page
																		+ "?menuId="
																		+ menuId
																		+ " class='three first'></a>");
															}
															var ifirst = $("<i class='am-icon-angle-right'></i>");
															var span = $("<span>"
																	+ data[i].description
																	+ "</span>");
															var isecond = $("<i class='am-icon-star tpl-left-nav-content-ico am-fr an-margin-right'></i>");
															ifirst.appendTo(a);
															span.appendTo(a);
															isecond.appendTo(a);
															a.appendTo(li);
														})
										li.appendTo(ul);
									}
								}
							})
				})
// ==========================
// 头部导航隐藏菜单
// ==========================

var flag = true;
$('.tpl-header-nav-hover-ico').on('click', function() {
	$('.tpl-left-nav').toggle();
	if (flag == true) {
		$('.tpl-content-wrapper').attr("style", "display:fixed");
		$('.tpl-content-wrapper').attr("style", "left:-100px");
		flag = false;
	} else {
		$('.tpl-content-wrapper').attr("style", "display:relative");
		$('.tpl-content-wrapper').attr("style", "left:0px");
		flag = true;
	}
})