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
<title>订单统计</title>
<link href="${ctx }/static/brd/css/plugins/datapicker/datepicker3.css" rel="stylesheet">
<script src="${ctx }/static/brd/js/plugins/datapicker/bootstrap-datepicker.js" type="text/javascript"></script>
<script src="${ctx}/static/js/highcharts.js"></script>
<script type="text/javascript">
$(function() {
	activeNav2("9","9_3");
	var usercount = "${rep1.usercountPercent}";
	var managercount = "${rep1.managercountPercent}";
	var sellercount = "${rep1.sellercountPercent}";
	var othercount = "${rep1.othercountPercent}";
	usercount = parseFloat(usercount);
	managercount =parseFloat(managercount);
	sellercount = parseFloat(sellercount);
	othercount =parseFloat(othercount);
	
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

	//每日提交贷款订单数趋势图
	$('#containerLoad').highcharts({
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
	          categories: <c:out value="${xdata1}" escapeXml="false"/>
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
	          name: '<c:out value="${fieldNameLoad}" escapeXml="false"/>',
	          data: <c:out value="${ydataLoad}" escapeXml="false"/>,
              point: {
                  events: {
                      click: function (e) {
						var dateList1 =${dateList1};
					//	alert(dateList1[this.x]);
						var datex = dateList1[this.x];
						datex = datex.toString() 
						var a = datex.slice(0,4);
						var b = datex.slice(4,6);
						var c = datex.slice(6,8);
						datex = a+"-"+b+"-"+c;
						location.href = "${ctx }/admin/orderform/weixin/list?createTime="+datex;
                      }
                  }
              },
              
	      }]
	  });
	//每日成交订单数趋势图
	$('#containerCredit').highcharts({
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
	          categories: <c:out value="${xdata2}" escapeXml="false"/>
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
	          name: '<c:out value="${fieldNameCredit}" escapeXml="false"/>',
	          data: <c:out value="${ydataCredit}" escapeXml="false"/>,
              point: {
                  events: {
                      click: function (e) {
						var dateList2 =${dateList2};
						var datex = dateList2[this.x];
						datex = datex.toString() 
						var a = datex.slice(0,4);
						var b = datex.slice(4,6);
						var c = datex.slice(6,8);
						datex = a+"-"+b+"-"+c;
						location.href = "${ctx }/admin/orderform/weixin/list?status=LOANED&createTime="+datex;
                      }
                  }
              },
              
	      }]
	  });
	//每日成交金额数趋势图
	$('#containerSum').highcharts({
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
	          categories: <c:out value="${xdata3}" escapeXml="false"/>,
		      
	      },
	      yAxis: {
	    	  min: 0,
	    	  allowDecimals:false,
	          title: {
	              text: ''
	          },
	          labels: {
	              formatter: function () {
	                  return (this.value/10000) + '万';
	              }
	          }, 
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
	          name: '<c:out value="${fieldNameSum}" escapeXml="false"/>',
	          data: <c:out value="${ydataSum}" escapeXml="false"/>,
/*               point: {
                  events: {
                      click: function (e) {
                      }
                  }
              }, */
              
	      }]
	  });
	
	
	 $('#containerCake').highcharts({
	        chart: {
	            plotBackgroundColor: null,
	            plotBorderWidth: null,
	            plotShadow: false
	        },
	        title: {
	            text: null
	        },
	        tooltip: {
	            pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>'
	        },
	        plotOptions: {
	            pie: {
	                allowPointSelect: true,
	                cursor: 'pointer',
	                dataLabels: {
	                    enabled: false
	                },
	                showInLegend: true
	            }
	        },
	        series: [{
	            type: 'pie',
	            name: "占比：",
	            data: [
	                ['商家：'+'${rep1.sellercount}'+'单',  sellercount],
	                ['普通会员：'+'${rep1.usercount}'+'单',   usercount],
	                {
	                    name: '融资经理：'+'${rep1.managercount}'+'单',
	                    y: managercount,
	                    sliced: true,
	                    selected: true
	                },
	                ['业务员：'+'${rep1.othercount}'+'单',    othercount]
	            ]
	        }]
	    });
	 
		$("#confirm").click(function(){
			var startload = $("#startload").val();
			var endload = $("#endload").val();
			if(startload.length<1||endload.length<1){
				alert("开始时间或结束时间不为空");
				return ;
			}
			if(startload>endload){
				pdate = endload;
				endload = startload;
				startload = pdate;
			} 
			$("#start_load").val(startload);
			$("#end_load").val(endload);
			$(".neardayload").removeClass('d_bj');
			requestLoad();
		})
		$("#confirm1").click(function(){
			var startcredit = $("#startcredit").val();
			var endcredit = $("#endcredit").val();
			if(startcredit.length<1||endcredit.length<1){
				alert("开始时间或结束时间不为空");
				return ;
			}
			if(startcredit>endcredit){
				pdate = endcredit;
				endcredit = startcredit;
				startcredit = pdate;
			} 
			$("#start_credit").val(startcredit);
			$("#end_credit").val(endcredit);
			$(".neardaycredit").removeClass('d_bj');
			requestLoad();
		})
		$("#confirm2").click(function(){
			var startsum = $("#startsum").val();
			var endsum = $("#endsum").val();
			if(startsum.length<1||endsum.length<1){
				alert("开始时间或结束时间不为空");
				return ;
			}
			if(startsum>endsum){
				pdate = endsum;
				endsum = startsum;
				startsum = pdate;
			} 
			$("#start_sum").val(startsum);
			$("#end_sum").val(endsum);
			$(".neardaysum").removeClass('d_bj');
			requestLoad();
		})
	 
});

			function changeValue(object){
				var $this = $(object);
				var type = $this.data("type");
				var value = $this.data("value");
				if(type== 'neardayLoad'){
					$(".neardayLoad").removeClass('d_bj');
					$this.addClass('d_bj');
					$("#date_load").val(value);
					$("#start_load").val('');
					$("#end_load").val('');
					requestLoad();
				}
				if(type== 'neardayCredit'){
					$(".neardayCredit").removeClass('d_bj');
					$this.addClass('d_bj');
					$("#date_credit").val(value);
					$("#start_credit").val('');
					$("#end_credit").val('');
					requestLoad();
				}
				if(type== 'neardaySum'){
					$(".neardaySum").removeClass('d_bj');
					$this.addClass('d_bj');
					$("#date_sum").val(value);
					$("#start_sum").val('');
					$("#end_sum").val('');
					requestLoad();
				}
			}
		
		function requestLoad(){
			$("#request_form").submit();
		}

		function GetDateStr(AddDayCount) { 
			var dd = new Date(); 
			dd.setDate(dd.getDate()+AddDayCount);//获取AddDayCount天后的日期 
			var y = dd.getFullYear(); 
			var m = dd.getMonth()+1;//获取当前月份的日期 
			var d = dd.getDate(); 
			return y+"-"+m+"-"+d; 
			} 
		function yesLoanCount() {
			var timestr = GetDateStr(-1);
			location.href = "${ctx }/admin/orderform/weixin/list?createTime="+timestr;
		}
		function yesCreditCount() {
			var timestr = GetDateStr(-1);
			location.href = "${ctx }/admin/orderform/weixin/list?status=LOANED&createTime="+timestr;
			//&startTimestr="+timestr+"&endTimestr="+timestr;
		}

</script>
</head>
<body>
	<form id = "request_form" action ="${ctx}/admin/reportStatistics/orderStatistics/orderStatistics">
		<input type ="hidden" id = "date_load" value = "${neardayLoad }" name = "neardayLoad">
		<input type ="hidden" id = "start_load" value = "${startLoad }" name = "startLoad">
		<input type ="hidden" id = "end_load" value = "${endLoad }" name = "endLoad">
		<input type ="hidden" id = "date_credit" value = "${neardayCredit }" name = "neardayCredit">
		<input type ="hidden" id = "start_credit" value = "${startCredit }" name = "startCredit">
		<input type ="hidden" id = "end_credit" value = "${endCredit }" name = "endCredit">
		<input type ="hidden" id = "date_sum" value = "${neardaySum }" name = "neardaySum">
		<input type ="hidden" id = "start_sum" value = "${startSum }" name = "startSum">
		<input type ="hidden" id = "end_sum" value = "${endSum }" name = "endSum">
	</form> 
<div class="row border-bottom">
	<div class="basic">
        <p>报表统计</p>
        <span><a href="<c:url value='/admin/main'/>" style="margin-left:0;">首页</a>><a href="#" >报表统计</a>><a><strong>订单统计</strong></a></span>
    </div>
    </div>
         <div class="details animated fadeInRight">
        	<div class="statistics">
              <dl class="s_dingdan">
                    <dt>
                        <p>当前提交贷款总数</p>
                        <p>${rep.totalLoanCount }</p>
                    </dt>
                    <dd>
                        <p>昨日新增贷款数</p>
                        <span><a href="javascript:void(0)" onclick="yesLoanCount()">${rep.yesLoanCount }</a></span>
                    </dd>
                </dl>
                <dl class="s_dingdan">
                    <dt>
                        <p>当前放款贷款总数</p>
                        <p>${rep.totalCreditCount }</p>
                    </dt>
                    <dd>
                        <p>昨日新增放款贷款数</p>
                        <span><a href="javascript:void(0)" onclick="yesCreditCount()">${rep.yesCreditCount }</a></span>
                    </dd>
                </dl>
             </div>    
                <!--details-->
				</div>
                <div class="details animated fadeInRight">
               	<div class="daily">
                		<div class="statistics1" style="padding-left: 0px; padding-top: 0px;" >
                		     <div class="daily_d_title">
                                <h1><b>每日提交贷款订单数</b></h1>
                                <p class = "neardayload hand <c:if test = "${neardayLoad =='7' }">  d_bj</c:if>" data-type = "neardayLoad" data-value = "7" onclick = "changeValue(this)">最近7天</p>
         					    <p class = "neardayload hand <c:if test = "${neardayLoad =='30' }"> d_bj</c:if>" data-type = "neardayLoad" data-value = "30" onclick = "changeValue(this)">最近30天</p>
                                <p><strong>起止日期：</strong></p>
								<input type="text" readonly id="startload" class="d_shur" name="search_starttime" data-date-format="yyyy-mm-dd" value="${startLoad }" data-provide="datepicker">
                                <p>~</p>
                                <input type="text" readonly id="endload"  class="d_shur" name="search_endtime" data-date-format="yyyy-mm-dd" value="${endLoad }" data-provide="datepicker">
                       			<p class="d_bj1" id="confirm">确定</p>
                         </div>
               			<div class="ibox float-e-margins" style="padding-top:60px;margin-bottom: 0px;" >
							<div class="flot-chart">
								<div class="flot-chart-content" id="containerLoad"></div>
							</div>
						</div>
                		</div>
                 	<div class="statistics1" style="padding-left: 0px; padding-top: 0px;" >
                		     <div class="daily_d_title">
                                <h1><b>每日成交贷款订单数</b></h1>
                                <p class = "neardayrecredit hand <c:if test = "${neardayCredit =='7' }">  d_bj</c:if>" data-type = "neardayCredit" data-value = "7" onclick = "changeValue(this)">最近7天</p>
         					    <p class = "neardayrecredit hand <c:if test = "${neardayCredit =='30' }"> d_bj</c:if>" data-type = "neardayCredit" data-value = "30" onclick = "changeValue(this)">最近30天</p>
                                <p><strong>起止日期：</strong></p>
								<input type="text" readonly id="startcredit" class="d_shur" name="search_starttime" data-date-format="yyyy-mm-dd" value="${startCredit }" data-provide="datepicker">
                                <p>~</p>
                                <input type="text" readonly id="endcredit" class="d_shur" name="search_starttime" data-date-format="yyyy-mm-dd" value="${endCredit }" data-provide="datepicker">
                       			<p class="d_bj1" id="confirm1">确定</p>
                         </div>
               			<div class="ibox float-e-margins" style="padding-top:60px;margin-bottom: 0px;" >
							<div class="flot-chart">
								<div class="flot-chart-content" id="containerCredit"></div>
							</div>
						</div>
                		</div>
                		<div class="statistics1" style="padding-left: 0px; padding-top: 0px;" >
                		     <div class="daily_d_title">
                                <h1><b>每日成交贷款订单金额</b></h1>
                                <p class = "neardaysum hand <c:if test = "${neardaySum =='7' }">  d_bj</c:if>" data-type = "neardaySum" data-value = "7" onclick = "changeValue(this)">最近7天</p>
         					    <p class = "neardaysum hand <c:if test = "${neardaySum =='30' }"> d_bj</c:if>" data-type = "neardaySum" data-value = "30" onclick = "changeValue(this)">最近30天</p>
                                <p><strong>起止日期：</strong></p>
								<input type="text" readonly id="startsum" class="d_shur" name="search_starttime" data-date-format="yyyy-mm-dd" value="${startSum }" data-provide="datepicker">
                                <p>~</p>
                                <input type="text" readonly id="endsum"  class="d_shur" name="search_endtime" data-date-format="yyyy-mm-dd" value="${endSum }" data-provide="datepicker">
                       			<p class="d_bj1" id="confirm2">确定</p>
                         </div>
               			<div class="ibox float-e-margins" style="padding-top:60px;margin-bottom: 0px;" >
							<div class="flot-chart">
								<div class="flot-chart-content" id="containerSum"></div>
							</div>
						</div>
                		</div>

                        <div class="daily_d_day d_line">
                        	<div class="daily_d_title"><h1><b>累计成功订单数分布</b></h1></div> 
                            <div id="containerCake" style="min-width:250px;height:250px;padding-left: 100px;"></div>
                    	</div>

                      </div> 
                </div>
                <div style="height:40px;"></div>
</body>
</html>
