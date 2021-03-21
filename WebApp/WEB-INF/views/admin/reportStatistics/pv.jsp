<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="util" uri="functions"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>PV统计</title>
<link href="${ctx }/static/brd/css/plugins/datapicker/datepicker3.css" rel="stylesheet">
<script src="${ctx }/static/brd/js/plugins/datapicker/bootstrap-datepicker.js" type="text/javascript"></script>
<script src="${ctx}/static/js/highcharts.js"></script>
<script type="text/javascript">
	$(function(){
		activeNav2("9","9_8");
		//日期控件初始化
		$.fn.datepicker.dates['zh-cn'] = {
			    days: ["星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"],
			    daysShort: ["日", "一", "二", "三", "四", "五", "六"],
			    daysMin: ["日", "一", "二", "三", "四", "五", "六"],
			    months: ["一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "十一月", "十二月"],
			    monthsShort: ["一", "二", "三", "四", "五", "六", "七", "八", "九", "十", "十一", "十二"],
			    today: "今天",
			    clear: "清除",
			    format: "yyyy-mm-dd",
			    titleFormat: "yyyy MM", /* Leverages same syntax as 'format' */
			    weekStart: 1
		};
		$(".d_shur").datepicker({
		    language: 'zh-cn'
		});
		
		//每日访问量
		$('#containerPV').highcharts({
		      chart: {
		          type: 'line'
		      },
				credits:{
					enabled:false
				},
		      title: {
		          text: ''
		      },
		      subtitle: {
		          text: ''
		      },
		      xAxis: {
		          tickmarkPlacement :"on",
		          categories: <c:out value="${xdata}" escapeXml="false"/>
		      },
		      yAxis: {
		    	  min: 0,
		    	  allowDecimals:false,
		          title: {
		              text: ''
		          }
		      },
		      tooltip: {
		          formatter: function() {
		              return '<b>'+ this.x +'</b><br/>'+this.series.name+":"+ this.y ;
		          }
		      },
		      plotOptions: {
		          line: {
		              dataLabels: {
		                  enabled: false
		              },
		              enableMouseTracking: true
		          }
		      },
		      
		      legend: {
		            useHTML: true
		       },
		      series: [{
		    	  cursor: 'pointer',
		          name: '<c:out value="每日访问量" escapeXml="false"/>',
		          data: <c:out value="${ydata}" escapeXml="false"/>,
                  point: {
                      events: {
                          click: function (e) {
							var dateList ='${dateList}';
							var datex = dateList[this.x];
							datex = datex.toString() 
							var a = datex.slice(0,4);
							var b = datex.slice(4,6);
							var c = datex.slice(6,8);
							datex = a+"-"+b+"-"+c;
                          }
                      }
                  }, 
                  
		      }]
		  });
		

	});	
	
	//提交
	function submit(object){
		var $this = $(object);
		var data = $this.data("value");
		$("#nearRange").val(data);
		$("#startDate").val("");
		$("#endDate").val("");
		$("#myform").submit();
	}
	
	
	function submitDate() {
		$("#myform").submit();		
	}
	
</script>
</head>
<body>
	
	<div class="row border-bottom">
	<div class="basic">
        <p>报表统计</p>
        <span><a href="<c:url value='/admin/main'/>" style="margin-left:0;">首页</a>><a href="#" >报表统计</a>><a><strong>访问量统计</strong></a></span>
    </div>
    </div>
	
	<div class="username animated fadeInRight">
       		 <p>昨日微信站访问量：
       		 <c:choose>
       		 	<c:when test="${not empty comon }" >
       		 		${comon.websitePv}
       		 	</c:when>
       		 	<c:otherwise>
       		 		0
       		 	</c:otherwise>
       		 </c:choose>
       		  次</p>
    </div>
    
	<div class="details animated fadeInRight">
      <div class="daily">
			<div class="statistics1" style="padding-left: 0px; padding-top: 0px;" >
		                <form name="myform" id="myform" action="${ctx }/admin/reportStatistics/pv" >
		                <div class="daily_d_title">
		                          <h1><b>每日微信站访问量</b></h1>
		                         <p class = "neardayrelogin hand  <c:if test = "${nearRange =='7' }">  d_bj</c:if>"  onclick="submit(this)" data-value = "7" >最近7天</p>
		         				<p class = "neardayrelogin hand  <c:if test = "${nearRange =='30' }"> d_bj</c:if>" data-value = "30" onclick="submit(this)" >最近30天</p>
		                        <input type="hidden" id="nearRange" name="nearRange" >
		                        <p><strong>起止日期：</strong></p>
								<input type="text" readonly id="startDate" class="d_shur"   name="startDate" data-date-format="yyyy-mm-dd" value="${startDate }" data-provide="datepicker">
		                       <p>~</p>
		                      <input type="text" readonly id="endDate"  class="d_shur" name="endDate" data-date-format="yyyy-mm-dd" value="${endDate }" data-provide="datepicker">
		     
		                     <p class="d_bj1" onclick="submitDate()" >确定</p>
		                     <p>${difference }天访问${total}次</p>
		                 </div>
		                 </form>
		            		<div class="ibox float-e-margins" style="padding-top:60px;margin-bottom: 0px;" >
								<div class="flot-chart">
							<div class="flot-chart-content" id="containerPV"></div>
						</div>
					</div>
		    </div>
		</div>
	</div>
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
</body>
</html>
