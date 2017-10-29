<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!doctype html>
<html>

<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>index</title>
<meta name="description" content="首页">
<meta name="keywords" content="index">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="renderer" content="webkit">
<meta http-equiv="Cache-Control" content="no-siteapp" />
<link rel="icon" type="image/png" href="${pageContext.request.contextPath}/assets/i/favicon.ico">
<link rel="apple-touch-icon-precomposed"
	href="assets/i/app-icon72x72@2x.png">
<meta name="apple-mobile-web-app-title" content="Amaze UI" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/amazeui.min.css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/admin.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/app.css">
<script src="${pageContext.request.contextPath}/assets/js/echarts.min.js"></script>
<script src="${pageContext.request.contextPath}/assets/js/jquery.min.js"></script>
<script
	src="${pageContext.request.contextPath}/assets/js/amazeui.min.js"></script>
<%-- <script src="${pageContext.request.contextPath}/assets/js/app.js"></script> --%>
</head>

<body data-type="index">
	<jsp:include page="head.jsp"></jsp:include>	
	<jsp:include page="side.jsp"></jsp:include>
	<div class="tpl-content-wrapper">
		<div class="row">
			<div class="am-u-lg-3 am-u-md-6 am-u-sm-12">
				<div class="dashboard-stat blue" style="cursor:pointer;" onclick="window.location.href='${pageContext.request.contextPath}/function/logout'">
					<div class="visual" >
						<i class="am-icon-comments-o"></i>
					</div>
					<div class="details">
						<div class="number">886</div>
						<div class="desc">退出系统
						</div>
					</div>
					<a class="more" href="${pageContext.request.contextPath }/function/logout"> 退出系统 <i
						class="m-icon-swapright m-icon-white"></i>
					</a>
				</div>
			</div>
			<div class="am-u-lg-3 am-u-md-6 am-u-sm-12">
				<div class="dashboard-stat red" style="cursor:pointer;" onclick="window.location.href='${pageContext.request.contextPath}/notice/getNoticeListByPage'">
					<div class="visual">
						<i class="am-icon-bar-chart-o"></i>
					</div>
					<div class="details">
						<div class="number">${count1 }</div>
						<div class="desc">查看公告</div>
					</div>
					<a class="more" href="${pageContext.request.contextPath }/notice/getNoticeListByPage"> 查看更多 <i
						class="m-icon-swapright m-icon-white"></i>
					</a>
				</div>
			</div>
			<div class="am-u-lg-3 am-u-md-6 am-u-sm-12">
				<div class="dashboard-stat green" style="cursor:pointer;">
					<div class="visual">
						<i class="am-icon-apple"></i>
					</div>
					<div class="details">
						<div class="number">40%</div>
						<div class="desc">系统负载</div>
					</div>
					<a class="more" href="#">查看更多 <i
						class="m-icon-swapright m-icon-white"></i>
					</a>
				</div>
			</div>
			<div class="am-u-lg-3 am-u-md-6 am-u-sm-12">
				<div class="dashboard-stat purple" style="cursor:pointer;">
					<div class="visual">
						<i class="am-icon-android"></i>
					</div>
					<div class="details">
						<div class="number">22</div>
						<div class="desc">在线人数</div>
					</div>
					<a class="more" href="#"> 查看更多 <i
						class="m-icon-swapright m-icon-white"></i>
					</a>
				</div>
			</div>
		</div>

		<div class="row">
			<div class="am-u-md-6 am-u-sm-12 row-mb">
				<div class="tpl-portlet">
					<div class="tpl-portlet-title">
						<div class="tpl-caption font-green ">
							<i class="am-icon-cloud-download"></i> <span>服务器负载仪表盘</span>
						</div>
						<div class="actions">
							<ul class="actions-btn">
								<li class="red-on">今天</li>
								<li class="green">昨天</li>
								<li class="blue">前天</li>
							</ul>
						</div>
					</div>
				 <div class="tpl-echarts" id="main1"></div>
				</div>
			</div>
			<div class="am-u-md-6 am-u-sm-12 row-mb">
				<div class="tpl-portlet">
					<div class="tpl-portlet-title">
						<div class="tpl-caption font-red ">
							<i class="am-icon-bar-chart"></i> <span>网站访问频率[24H]</span>
						</div>
						<div class="actions">
							<ul class="actions-btn">
								<li class="purple-on">今天</li>
								<li class="green">昨天</li>
								<li class="dark">前天</li>
							</ul>
						</div>
					</div>
					<div class="tpl-scrollable" id="main">
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
<script type="text/javascript">
$(document).ready(function(){
    check();
});
function check(){
	 	$.ajax({
			type : "post",//数据发送的方式（post 或者 get）
			traditional : true,
			url : "/logStatistics/getLogStatistics",//要发送的后台地址
			success : function(data) {//ajax请求成功后触发的方法
                  var myChart = echarts.init(document.getElementById('main'));
                   option = {
                		    title: {
                		    	
                		    },
                		    tooltip: {
                		        trigger: 'axis',
                		        axisPointer: {
                		            type: 'cross'
                		        }
                		    },
                		    toolbox: {
                		        show: true,
                		        feature: {
                		            saveAsImage: {}
                		        }
                		    },
                		    xAxis:  {
                		        type: 'category',
                		        boundaryGap: false,
                		        data: ['00:00', '01:15', '02:30', '03:45', '05:00',
                		        	'06:15', '07:30', '08:45', '10:00', '11:15', 
                		        	'12:30', '13:45', '15:00', '16:15', '17:30', 
                		        	'18:45', '20:00', '21:15', '22:30', '23:45']
                		    },
                		    yAxis: {
                		        type: 'value',
                		        axisLabel: {
                		            formatter: '{value} 次'
                		        },
                		        axisPointer: {
                		            snap: true
                		        }
                		    },
                		    visualMap: {
                		        show: false,
                		        dimension: 0,
                		        pieces: [{
                		            lte: 6,
                		            color: 'green'
                		        }, {
                		            gt: 6,
                		            lte: 8,
                		            color: 'blue'
                		        }, {
                		            gt: 8,
                		            lte: 14,
                		            color: 'red'
                		        }, {
                		            gt: 14,
                		            lte: 17,
                		            color: 'yellow'
                		        }, {
                		            gt: 17,
                		            color: 'green'
                		        }]
                		    },
                		    series: [
                		        {
                		            name:'次数',
                		            type:'line',
                		            smooth: true,
                		            data: data,
                		        }
                		    ]
                		};
                	  myChart.setOption(option); 
			}
		})
}


$(function(){
	

//服务器负载的JS代码
var myChart1 = echarts.init(document.getElementById('main1'));
option1 = {
	    tooltip : {
	        formatter: "{a} <br/>{c} {b}"
	    },
	    toolbox: {
	        show: true,
	        feature: {
	            restore: {show: true},
	            saveAsImage: {show: true}
	        }
	    },
	    series : [
	        {
	            name: 'CPU负载',
	            type: 'gauge',
	            z: 3,
	            min: 0,
	            max: 220,
	            splitNumber: 11,
	            radius: '50%',
	            axisLine: {            // 坐标轴线
	                lineStyle: {       // 属性lineStyle控制线条样式
	                    width: 10
	                }
	            },
	            axisTick: {            // 坐标轴小标记
	                length: 15,        // 属性length控制线长
	                lineStyle: {       // 属性lineStyle控制线条样式
	                    color: 'auto'
	                }
	            },
	            splitLine: {           // 分隔线
	                length: 20,         // 属性length控制线长
	                lineStyle: {       // 属性lineStyle（详见lineStyle）控制线条样式
	                    color: 'auto'
	                }
	            },
	            axisLabel: {
	                backgroundColor: 'auto',
	                borderRadius: 2,
	                color: '#eee',
	                padding: 3,
	                textShadowBlur: 2,
	                textShadowOffsetX: 1,
	                textShadowOffsetY: 1,
	                textShadowColor: '#222'
	            },
	            title : {
	                // 其余属性默认使用全局文本样式，详见TEXTSTYLE
	                fontWeight: 'bolder',
	                fontSize: 20,
	                fontStyle: 'italic'
	            },
	            detail : {
	                // 其余属性默认使用全局文本样式，详见TEXTSTYLE
	                formatter: function (value) {
	                    value = (value + '').split('.');
	                    value.length < 2 && (value.push('00'));
	                    return ('00' + value[0]).slice(-2)
	                        + '.' + (value[1] + '00').slice(0, 2);
	                },
	                fontWeight: 'bolder',
	                borderRadius: 3,
	                backgroundColor: '#444',
	                borderColor: '#aaa',
	                shadowBlur: 5,
	                shadowColor: '#333',
	                shadowOffsetX: 0,
	                shadowOffsetY: 3,
	                borderWidth: 2,
	                textBorderColor: '#000',
	                textBorderWidth: 2,
	                textShadowBlur: 2,
	                textShadowColor: '#fff',
	                textShadowOffsetX: 0,
	                textShadowOffsetY: 0,
	                fontFamily: 'Arial',
	                width: 100,
	                color: '#eee',
	                rich: {}
	            },
	            data:[{value: 40, name: 'kb/s'}]
	        },
	        {
	            name: '网络',
	            type: 'gauge',
	            center: ['20%', '55%'],    // 默认全局居中
	            radius: '35%',
	            min:0,
	            max:7,
	            endAngle:45,
	            splitNumber:7,
	            axisLine: {            // 坐标轴线
	                lineStyle: {       // 属性lineStyle控制线条样式
	                    width: 8
	                }
	            },
	            axisTick: {            // 坐标轴小标记
	                length:12,        // 属性length控制线长
	                lineStyle: {       // 属性lineStyle控制线条样式
	                    color: 'auto'
	                }
	            },
	            splitLine: {           // 分隔线
	                length:20,         // 属性length控制线长
	                lineStyle: {       // 属性lineStyle（详见lineStyle）控制线条样式
	                    color: 'auto'
	                }
	            },
	            pointer: {
	                width:5
	            },
	            title: {
	                offsetCenter: [0, '-30%'],       // x, y，单位px
	            },
	            detail: {
	                // 其余属性默认使用全局文本样式，详见TEXTSTYLE
	                fontWeight: 'bolder'
	            },
	            data:[{value: 1.5, name: 'byte/s'}]
	        },
	        {
	            name: '服务器内存负载',
	            type: 'gauge',
	            center: ['77%', '50%'],    // 默认全局居中
	            radius: '25%',
	            min: 0,
	            max: 2,
	            startAngle: 135,
	            endAngle: 45,
	            splitNumber: 2,
	            axisLine: {            // 坐标轴线
	                lineStyle: {       // 属性lineStyle控制线条样式
	                    width: 8
	                }
	            },
	            axisTick: {            // 坐标轴小标记
	                splitNumber: 5,
	                length: 10,        // 属性length控制线长
	                lineStyle: {        // 属性lineStyle控制线条样式
	                    color: 'auto'
	                }
	            },
	            axisLabel: {
	                formatter:function(v){
	                    switch (v + '') {
	                        case '0' : return 'E';
	                        case '1' : return 'Gas';
	                        case '2' : return 'F';
	                    }
	                }
	            },
	            splitLine: {           // 分隔线
	                length: 15,         // 属性length控制线长
	                lineStyle: {       // 属性lineStyle（详见lineStyle）控制线条样式
	                    color: 'auto'
	                }
	            },
	            pointer: {
	                width:2
	            },
	            title : {
	                show: false
	            },
	            detail : {
	                show: false
	            },
	            data:[{value: 0.5, name: 'gas'}]
	        },
	        {
	            name: '磁盘',
	            type: 'gauge',
	            center : ['77%', '50%'],    // 默认全局居中
	            radius : '25%',
	            min: 0,
	            max: 2,
	            startAngle: 315,
	            endAngle: 225,
	            splitNumber: 2,
	            axisLine: {            // 坐标轴线
	                lineStyle: {       // 属性lineStyle控制线条样式
	                    width: 8
	                }
	            },
	            axisTick: {            // 坐标轴小标记
	                show: false
	            },
	            axisLabel: {
	                formatter:function(v){
	                    switch (v + '') {
	                        case '0' : return 'H';
	                        case '1' : return '内存';
	                        case '2' : return 'C';
	                    }
	                }
	            },
	            splitLine: {           // 分隔线
	                length: 15,         // 属性length控制线长
	                lineStyle: {       // 属性lineStyle（详见lineStyle）控制线条样式
	                    color: 'auto'
	                }
	            },
	            pointer: {
	                width:2
	            },
	            title: {
	                show: false
	            },
	            detail: {
	                show: false
	            },
	            data:[{value: 0.5, name: 'gas'}]
	        }
	    ]
	};
	setInterval(function (){
		option1.series[0].data[0].value = (Math.random()*100).toFixed(2) - 0;
		option1.series[1].data[0].value = (Math.random()*7).toFixed(2) - 0;
		option1.series[2].data[0].value = (Math.random()*2).toFixed(2) - 0;
		option1.series[3].data[0].value = (Math.random()*2).toFixed(2) - 0;
	    myChart1.setOption(option1,true);
	},2000);
})
</script>

</html>





