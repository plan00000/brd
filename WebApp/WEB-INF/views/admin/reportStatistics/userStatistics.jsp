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
<title>会员统计</title>
<link href="${ctx }/static/brd/css/plugins/datapicker/datepicker3.css" rel="stylesheet">
<script src="${ctx }/static/brd/js/plugins/datapicker/bootstrap-datepicker.js" type="text/javascript"></script>
<script src="${ctx}/static/js/highcharts.js"></script>
<script type="text/javascript">
	$(function() {
		activeNav2("9","9_1");
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
	
		//每日注册会员数趋势图
		$('#containerRegister').highcharts({
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
		          name: '<c:out value="${fieldNameRegister}" escapeXml="false"/>',
		          data: <c:out value="${ydataRegister}" escapeXml="false"/>,
                  point: {
                      events: {
                          click: function (e) {
							var dateList1 =${dateList1};
							var datex = dateList1[this.x];
							datex = datex.toString() 
							var a = datex.slice(0,4);
							var b = datex.slice(4,6);
							var c = datex.slice(6,8);
							datex = a+"-"+b+"-"+c;
							location.href = "${ctx }/admin/user/list?startTimestr="+datex+"&endTimestr="+datex;
                          }
                      }
                  },
                  
		      }]
		  });
		//每日登录人数趋势图
		$('#containerLogin').highcharts({
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
		          name: '<c:out value="${fieldNameLogin}" escapeXml="false"/>',
		          data: <c:out value="${ydataLogin}" escapeXml="false"/>,
                  point: {
                      events: {
                          click: function (e) {
							var dateList2 =${dateList2};
							//alert(dateList2[this.x]);
							var datex = dateList2[this.x];
							datex = datex.toString() 
							var a = datex.slice(0,4);
							var b = datex.slice(4,6);
							var c = datex.slice(6,8);
							datex = a+"-"+b+"-"+c;
							location.href = "${ctx }/admin/user/list?lastLoginDate="+datex;
                        //	  location.href = "${ctx}/admin/activity/starOrderSetting"; 
			
                          }
                      }
                  }
		      }]
		  });
		//会员总数增长量
		$('#containerAll').highcharts({
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
		          categories: <c:out value="${xdata3}" escapeXml="false"/>
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
		          name: '<c:out value="${fieldNameAll}" escapeXml="false"/>',
		          data: <c:out value="${ydataAll}" escapeXml="false"/>,
/*                   point: {
                      events: {
                          click: function (e) {
							var dateList3 =${dateList3};
							var datex = dateList3[this.x];
							datex = datex.toString() 
							var a = datex.slice(0,4);
							var b = datex.slice(4,6);
							var c = datex.slice(6,8);
							datex = a+"-"+b+"-"+c;
							location.href = "${ctx }/admin/user/list?startTimestr=0000-00-00&endTimestr="+datex;
			
                          }
                      }
                  } */
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
	            name: "占比 ",
	            data: [
		                ['商家：'+'${rep1.sellercount}'+'人',  sellercount],
		                ['普通会员：'+'${rep1.usercount}'+'人',   usercount],
		                {
		                    name: '融资经理：'+'${rep1.managercount}'+'人',
		                    y: managercount,
		                    sliced: true,
		                    selected: true
		                },
		                ['业务员：'+'${rep1.othercount}'+'人',    othercount]
	            ]
	        }]
	    });
		
		
		
		
		
		$("#confirm").click(function(){
			var startregister = $("#startregister").val();
			var endregister = $("#endregister").val();
			if(startregister.length<1||endregister.length<1){
				alert("开始时间或结束时间不为空");
				return ;
			}
			if(startregister>endregister){
				pdate = endregister;
				endregister = startregister;
				startregister = pdate;
			} 
			$("#start_register").val(startregister);
			$("#end_register").val(endregister);
			$(".neardayregister").removeClass('d_bj');
			requestLoad();
		})
		$("#confirm1").click(function(){
			var startlogin = $("#startlogin").val();
			var endlogin = $("#endlogin").val();
			if(startlogin.length<1||endlogin.length<1){
				alert("开始时间或结束时间不为空");
				return ;
			}
			if(startlogin>endlogin){
				pdate = endlogin;
				endlogin = startlogin;
				startlogin = pdate;
			} 
			$("#start_login").val(startlogin);
			$("#end_login").val(endlogin);
			$(".neardaylogin").removeClass('d_bj');
			requestLoad();
		})
		$("#confirm3").click(function(){
			var startall = $("#startall").val();
			var endall = $("#endall").val();
			if(startall.length<1||endall.length<1){
				alert("开始时间或结束时间不为空");
				return ;
			}
			if(startall>endall){
				pdate = endall;
				endall = startall;
				startall = pdate;
			} 
			$("#start_all").val(startall);
			$("#end_all").val(endall);
			$(".neardayall").removeClass('d_bj');
			requestLoad();
		})
		
		
		
		
		
	});
	
		function changeValue(object){
			var $this = $(object);
			var type = $this.data("type");
			var value = $this.data("value");
			if(type== 'neardayRegister'){
				$(".neardayregister").removeClass('d_bj');
				$this.addClass('d_bj');
				$("#date_register").val(value);
				$("#start_register").val('');
				$("#end_register").val('');
				requestLoad();
			}
			if(type== 'neardayLogin'){
				$(".neardayrelogin").removeClass('d_bj');
				$this.addClass('d_bj');
				$("#date_login").val(value);
 				$("#start_login").val('');
				$("#end_login").val('');
				requestLoad();
			}
			if(type== 'neardayAll'){
				$(".neardayall").removeClass('d_bj');
				$this.addClass('d_bj');
				$("#date_all").val(value);
 				$("#start_all").val('');
				$("#end_all").val('');
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
	//转到会员列表-昨天普通用户
	function yesUserCount() {
		var timestr = GetDateStr(-1);
		location.href = "${ctx }/admin/user/list?userType=USER&startTimestr="+timestr+"&endTimestr="+timestr;
	}
	//转到会员列表-昨天融资经理
	function yesManagerCount() {
		var timestr = GetDateStr(-1);
		location.href = "${ctx }/admin/user/list?userType=MANAGER&startTimestr="+timestr+"&endTimestr="+timestr;
	}
	//转到会员列表-昨天商家
	function yesSellerCount() {
		var timestr = GetDateStr(-1);
		location.href = "${ctx }/admin/user/list?userType=SELLER&startTimestr="+timestr+"&endTimestr="+timestr;
	}
</script>
</head>
<body>
	<form id = "request_form" action ="${ctx}/admin/reportStatistics/userStatistics/userStatistics">
		<input type ="hidden" id = "date_register" value = "${neardayRegister }" name = "neardayRegister">
		<input type ="hidden" id = "start_register" value = "${startRegister }" name = "startRegister">
		<input type ="hidden" id = "end_register" value = "${endRegister }" name = "endRegister">
		<input type ="hidden" id = "date_login" value = "${neardayLogin }" name = "neardayLogin">
		<input type ="hidden" id = "start_login" value = "${startLogin }" name = "startLogin">
		<input type ="hidden" id = "end_login" value = "${endLogin }" name = "endLogin">
		<input type ="hidden" id = "date_all" value = "${neardayAll }" name = "neardayAll">
		<input type ="hidden" id = "start_all" value = "${startAll }" name = "startAll">
		<input type ="hidden" id = "end_all" value = "${endAll }" name = "endAll">
	</form>
	<div class="row border-bottom">
	<div class="basic">
        <p>报表统计</p>
        <span><a href="<c:url value='/admin/main'/>" style="margin-left:0;">首页</a>><a href="#" >报表统计</a>><a><strong>会员统计</strong></a></span>
    </div>
    </div>
              <div class="details animated fadeInRight">
        	<div class="statistics">
                <dl>
                    <dt>
                        <p>当前普通会员总数</p>
                        <p>${rep.totalUserCount }</p>
                    </dt>
                    <dd>
                        <p>昨日新增普通会员</p>
                        <span><a href="javascript:void(0)" onclick="yesUserCount()">${rep.yesUserCount }</a></span>
                    </dd>
                </dl>
                <dl>
                    <dt>
                        <p>当前融资经理总数</p>
                        <p>${rep.totalManagerCount }</p>
                    </dt>
                    <dd>
                        <p>昨日新增融资经理</p>
                        <span><a href="javascript:void(0)" onclick="yesManagerCount()">${rep.yesManagerCount }</a></span>
                    </dd>
                </dl>
                <dl>
                    <dt>
                        <p>当前普通商家总数</p>
                        <p>${rep.totalSellerCount }</p>
                    </dt>
                    <dd>
                        <p>昨日新增商家</p>
                        <span><a href="javascript:void(0)" onclick="yesSellerCount()">${rep.yesSellerCount }</a></span>
                    </dd>
                </dl>
             </div>    
                <!--details-->
				</div>
                <div class="details animated fadeInRight">
                	<div class="daily">
                		<div class="statistics1" style="padding-left: 0px; padding-top: 0px;" >
                		     <div class="daily_d_title">
                                <h1><b>每日新注册会员</b></h1>
                                <p class = "neardayregister hand <c:if test = "${neardayRegister =='7' }">  d_bj</c:if>" data-type = "neardayRegister" data-value = "7" onclick = "changeValue(this)">最近7天</p>
         					    <p class = "neardayregister hand <c:if test = "${neardayRegister =='30' }"> d_bj</c:if>" data-type = "neardayRegister" data-value = "30" onclick = "changeValue(this)">最近30天</p>
                                <p><strong>起止日期：</strong></p>
								<input type="text" readonly id="startregister" class="d_shur" name="search_starttime" data-date-format="yyyy-mm-dd" value="${startRegister }" data-provide="datepicker">
                                <p>~</p>
                                <input type="text" readonly id="endregister"  class="d_shur" name="search_endtime" data-date-format="yyyy-mm-dd" value="${endRegister }" data-provide="datepicker">
                       			<p class="d_bj1" id="confirm">确定</p>
                       			<p >${difference1}天共${total1 }人</p>
                         </div>
               			<div class="ibox float-e-margins" style="padding-top:60px;margin-bottom: 0px;" >
							<div class="flot-chart">
								<div class="flot-chart-content" id="containerRegister"></div>
							</div>
						</div>
                		</div>
                		<div class="statistics1" style="padding-left: 0px; padding-top: 0px;" >
                		     <div class="daily_d_title">
                                <h1><b>每日登录会员数</b></h1>
                                <p class = "neardayrelogin hand  <c:if test = "${neardayLogin =='7' }">  d_bj</c:if>" data-type = "neardayLogin" data-value = "7" onclick = "changeValue(this)">最近7天</p>
         					    <p class = "neardayrelogin hand  <c:if test = "${neardayLogin =='30' }"> d_bj</c:if>" data-type = "neardayLogin" data-value = "30" onclick = "changeValue(this)">最近30天</p>
                                <p><strong>起止日期：</strong></p>
								<input type="text" readonly id="startlogin" class="d_shur" name="search_starttime" data-date-format="yyyy-mm-dd" value="${startLogin }" data-provide="datepicker">
                                <p>~</p>
                                <input type="text" readonly id="endlogin"  class="d_shur" name="search_endtime" data-date-format="yyyy-mm-dd" value="${endLogin }" data-provide="datepicker">
                       			<p class="d_bj1" id="confirm1">确定</p>
                       			<p >${difference2}天共${total2 }次</p>
                         </div>
               			<div class="ibox float-e-margins" style="padding-top:60px;margin-bottom: 0px;" >
							<div class="flot-chart">
								<div class="flot-chart-content" id="containerLogin"></div>
							</div>
						</div>
                		</div>
                		<div class="statistics1" style="padding-left: 0px; padding-top: 0px;" >
                		     <div class="daily_d_title">
                                <h1><b>会员总数增长量</b></h1>
                                <p class = "neardayall hand  <c:if test = "${neardayAll =='7' }">  d_bj</c:if>" data-type = "neardayAll" data-value = "7" onclick = "changeValue(this)">最近7天</p>
         					    <p class = "neardayall hand  <c:if test = "${neardayAll =='30' }"> d_bj</c:if>" data-type = "neardayAll" data-value = "30" onclick = "changeValue(this)">最近30天</p>
                                <p><strong>起止日期：</strong></p>
								<input type="text" readonly id="startall" class="d_shur" name="search_starttime" data-date-format="yyyy-mm-dd" value="${startAll }" data-provide="datepicker">
                                <p>~</p>
                                <input type="text" readonly id="endall"  class="d_shur" name="search_endtime" data-date-format="yyyy-mm-dd" value="${endAll }" data-provide="datepicker">
                       			<p class="d_bj1" id="confirm3">确定</p>
                       			<p >${difference3}天共${total3 }个</p>
                         </div>
               			<div class="ibox float-e-margins" style="padding-top:60px;margin-bottom: 0px;" >
							<div class="flot-chart">
								<div class="flot-chart-content" id="containerAll"></div>
							</div>
						</div>
                		</div>

                        <div class="daily_d_day d_line">
                        	<div class="daily_d_title"><h1><b>会员等级分布</b></h1></div> 
                            <div id="containerCake" style="min-width:250px;height:250px;padding-left: 100px;"></div>
                    	</div>

                      </div>
                <!--details-->
                </div>
                    <div style="height:50px;"></div>
          <!--page-wrapper-->
</body>
</html>
