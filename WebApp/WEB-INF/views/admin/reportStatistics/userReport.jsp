<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="util" uri="functions"%>
<%@ taglib prefix="shiro" uri="http://www.springside.org.cn/tags/shiro"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="${ctx }/static/brd/css/plugins/datapicker/datepicker3.css" rel="stylesheet">
<script src="${ctx }/static/brd/js/plugins/datapicker/bootstrap-datepicker.js" type="text/javascript"></script>
<title>会员报表</title>
<script type="text/javascript">
	$(function() {
		activeNav2("9","9_2");
	
		var step='${step}';
		if (step>0){
			document.title="第"+step+"步";
		}
		
		
		
		var orRangetime = '${timeRange}';
		if(orRangetime=='custom'){
			$("#showCustom").css("display","block");			
		}		
		
		
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
		$(".datepicker").datepicker({
		    language: 'zh-cn'
		});
		
		
		//徒弟
		$("#sortsonSum").click(function(){
			var id=$(this).data('id');
			if(id == 1){
				$("#formsortName").val("sum(son_sum)");
				$("#formsortType").val("asc");
				submit();
			}else{
				$("#formsortName").val("sum(son_sum)");
				$("#formsortType").val("desc");
				submit();
			}
		});
		//徒孙数
		$("#sortgrandsonSum").click(function(){
			var id = $(this).data('id');
			if(id == 1){
				$("#formsortName").val("sum(grand_son_sum)");
				$("#formsortType").val("asc");
				submit();
			}else{
				$("#formsortName").val("sum(grand_son_sum)");
				$("#formsortType").val("desc");
				submit();
			}
		});
		
		//徒孙孙
		$("#sortggsonsSum").click(function(){
			var id = $(this).data('id');
			if(id == 1){
				$("#formsortName").val("sum(ggrand_son_sum)");
				$("#formsortType").val("asc");
				submit();
			}else{
				$("#formsortName").val("sum(ggrand_son_sum)");
				$("#formsortType").val("desc");
				submit();
			}
		});
		
		//登录次数
		$("#sortloginTimes").click(function(){
			var id = $(this).data('id');
			if(id == 1){
				$("#formsortName").val("sum(logintimes)");
				$("#formsortType").val("asc");
				submit();
			}else{
				$("#formsortName").val("sum(logintimes)");
				$("#formsortType").val("desc");
				submit();
			}
		});
		
		
		
		//订单
		$("#sortorderSum").click(function(){
			var id = $(this).data('id');
			if(id == 1){
				$("#formsortName").val("sum(order_sum)");
				$("#formsortType").val("asc");
				submit();
			}else{
				$("#formsortName").val("sum(order_sum)");
				$("#formsortType").val("desc");
				submit();
			}
		})
		
		//成功订单
		$("#sortorderSuccessSum").click(function(){
			var id = $(this).data('id');
			if(id == 1){
				$("#formsortName").val("sum(order_success_sum)");
				$("#formsortType").val("asc");
				submit();
			}else{
				$("#formsortName").val("sum(order_success_sum)");
				$("#formsortType").val("desc");
				submit();
			}
		});
		
		 $("#query").click(function(){
		
			var loginTimes =$("#loginTimes").val();
			var grandsonSum =$("#grandsonSum").val();
			var orderSum=$("#orderSum").val();
			var orderSuccessSum=$("#orderSuccessSum").val(); 
			var keywordType=$("#formkeywordType").val($("#keywordType").val());
			var keyword = $("#formkeyword").val($("#keyword").val());
			var timeRange=$("#formtimeRange").val($("#timeRange").val());
			var startTimestr = $("#startTimestr").val();
			var endTimestr=$("#endTimestr").val();
			var sStartTimestr = $("#sStartTimeStr").val();
			var sEndTimestr=$("#sEndTimeStr").val();
			var step  ='${step+1}';
			$("#step").val(step);
			/* if(startTimestr!=""&& endTimestr==""){
				alert("请填写结束时间")
				return ;
			}
			if(startTimestr==""&& endTimestr!=""){
				alert("请填写开始时间")
				return ;
			} */
			$("#formstartTimestr").val($("#startTimestr").val());
			$("#formendTimestr").val($("#endTimestr").val());		
			$("#formloginTimes").val($("#loginTimes").val());
			$("#formsonSum").val($("#sonSum").val());
			$("#formgrandsonSum").val($("#grandsonSum").val());
			$("#formorderSum").val($("#orderSum").val());
			$("#formorderSuccessSum").val($("#orderSuccessSum").val());
			$("#formsStartTimestr").val(sStartTimestr);
			$("#formsEndTimestr").val(sEndTimestr);
			$("#fortimeRange").val($("#timeRange").val());
			$("#formggrandsonSum").val($("#ggrandsonSum").val());
			var userType =$("#userType").val();
			$("#formuserType").val($("#userType").val());
			
			var state = $("#state").val();
			$("#formstate").val($("#state").val());
			
			
			$("#myform").attr("target","_blank");
			$("#myform").submit();
		}); 
		
		 $("#clean").click(function(){
			$("#state").val("");
			$("#userType").val("");
			$("#loginTimes").val("");
			$("#sonSum").val("");
			$("#grandsonSum").val("");
			$("#orderSum").val("");
			$("#orderSuccessSum").val("");
			$("#sStartTimeStr").val("");
			$("#sEndTimeStr").val("");
			$("#timeRange").val("");
			$("#startTimestr").val("");
			$("#endTimestr").val("");
			$("#keywordType").val("0");
			$("#keyword").val("");
			$("#ggrandsonSum").val("");
		 })		
	});
	
	function submit(){
		$("#myform").submit();
	}
	
	function showCustomContent(){
		var value = $("#timeRange").val();
		if(value=="custom"){
			document.getElementById("showCustom").style.display="block";
		}else{
			document.getElementById("showCustom").style.display="none";
		}
	}
	
	
</script>
</head>
<body>
<div class="row  border-bottom">
		<div class="basic">
        <p>报表统计</p>
        <span><a href="<c:url value='/admin/main'/>"  style="margin-left:0;">首页</a>><a>报表统计</a>><a><strong>会员报表</strong></a></span>
    </div>
		<div class="col-lg-2"></div>
	</div>
		<div class="member animated fadeInRight">
              <form id = "myform" action ="${ctx}/admin/reportStatistics/userReport/userReport" >
				<input type ="hidden" id="formsortName" name = "sortName"/>
				<input type ="hidden" id="formsortType" name = "sortType"/>
				<input type ="hidden" id="formstate" name = "state" />
				<input type ="hidden" id="formuserType" name="userType" />
				<input type ="hidden" id="formloginTimes" name="loginTimes" />
				<input type ="hidden" id="formsonSum" name="sonSum" />
				<input type ="hidden" id="formgrandsonSum" name="grandsonSum" />
				<input type ="hidden" id="formorderSum" name="orderSum" />
				<input type ="hidden" id="formorderSuccessSum" name="orderSuccessSum" />
				<input type ="hidden" id="formkeywordType" name="keywordType" />
				<input type ="hidden" id="formkeyword" name="keyword" />
				<input type ="hidden" id="formtimeRange" name="timeRange" />
				<input type ="hidden" id="formstartTimestr" name="startTimestr" />
				<input type ="hidden" id="formendTimestr" name="endTimestr" />
				<input type ="hidden" id="formsStartTimestr" name="sStartTimestr" />
				<input type ="hidden" id="formsEndTimestr" name="sEndTimestr" />
				<input type ="hidden" id="formggrandsonSum" name="ggrandsonSum" />
				<input type ="hidden" name="step" id="step"  />
			</form> 
            <div class="member_text">
                <dl class="m_to">
                    <dt>报表时间：</dt>
                    <dd>
                        <input type="text" class="m_shuru datepicker" id="sStartTimeStr"  value="${sStartTimestr}"  placeholder="开始时间">
                        <font> - </font>
                        <input type="text" class="m_shuru datepicker" id="sEndTimeStr" value="${sEndTimestr}" placeholder="结束时间">
                    </dd>
                </dl>
                <dl>
                    <dt>会员状态：</dt>
                    <dd>
                        <select class="m_ter" name="state" id="state">
	                        <option value="-1" >全部状态</option>
	                        <option value="1" <c:if test="${state eq '1' }">selected</c:if> >正常</option>
	                       <option <c:if test="${state eq '0' }">selected</c:if> value='0'>冻结</option> 
                        </select>
                    </dd>
                </dl>
                <dl>
                    <dt>会员身份：</dt>
                    <dd>
                        <select class="m_ter" name="userType" id="userType" >
	                        <option value="-1" >全部状态</option>
	                        <option <c:if test="${userType eq '0' }">selected</c:if> value='0'>普通用户</option> 
	                       	<option <c:if test="${userType eq '1' }">selected</c:if> value='1'>融资经理</option> 
	                       	<option <c:if test="${userType eq '2' }">selected</c:if> value='2'>商家</option>
                       		<option <c:if test="${userType eq '4' }">selected</c:if> value='4'>业务员</option>
                        </select>
                    </dd>
                </dl>
                <dl>
                    <dt>登录次数：</dt>
                    <c:choose>
                    	<c:when test="${loginTimes ne '-1' }">
                    		<dd><input type="text" id="loginTimes" value="${loginTimes }" class="m_ter">≥次</dd>
                    	</c:when>
                    	<c:otherwise>
                    		<dd><input type="text" id="loginTimes" class="m_ter">≥次</dd>
                    	</c:otherwise>
                    </c:choose>
                    
                </dl>
                <dl>
                    <dt>徒弟数量：</dt>
	                   <c:choose>
	                    	<c:when test="${sonSum ne '-1' }">
	                    		 <dd><input type="text" id="sonSum" value="${sonSum }" class="m_ter">≥人</dd>
	                    	</c:when>
	                    	<c:otherwise>
	                    		<dd><input type="text" id="sonSum" class="m_ter">≥人</dd>
	                    	</c:otherwise>
	                    </c:choose>  
                </dl>
                <dl>
                    <dt>徒孙数量：</dt>
                    <c:choose>
                    	<c:when test="${grandsonSum ne '-1' }">
                    		<dd><input type="text" id="grandsonSum" value="${grandsonSum }" class="m_ter">≥人</dd>
                    	</c:when>
                    	<c:otherwise>
                    		<dd><input type="text" id="grandsonSum" class="m_ter">≥人</dd>
                    	</c:otherwise>
                    </c:choose>
                </dl>
                <dl>
                	<dt>徒孙孙数量:</dt>
                	 <c:choose>
                    	<c:when test="${ggrandsonSum ne '-1' }">
                    		<dd><input type="text" id="ggrandsonSum" value="${ggrandsonSum }" class="m_ter">≥人</dd>
                    	</c:when>
                    	<c:otherwise>
                    		<dd><input type="text" id="ggrandsonSum" class="m_ter">≥人</dd>
                    	</c:otherwise>
                    </c:choose>
                </dl>
                <dl>
                    <dt>提交订单：</dt>
                    <c:choose>
                    	<c:when test="${orderSum ne '-1' }">
                    		 <dd><input type="text" id="orderSum" value="${orderSum }" class="m_ter">≥单</dd>
                    	</c:when>
                    	<c:otherwise>
                    		 <dd><input type="text" id="orderSum" class="m_ter">≥单</dd>
                    	</c:otherwise>
                    </c:choose>
                </dl>
                <dl>
                    <dt>订单成交：</dt>
                    <c:choose>
                    	<c:when test="${orderSuccessSum ne '-1'}">
                    		<dd><input type="text" id="orderSuccessSum" value="${orderSuccessSum}" class="m_ter">≥单</dd>
                    	</c:when>
                    	<c:otherwise>
                    		<dd><input type="text" id="orderSuccessSum" class="m_ter">≥单</dd>
                    	</c:otherwise>
                    </c:choose>
                    
                </dl>
                <dl class="m_time">
                    <dt>注册时间：</dt>
                    <dd>
                        <select class="m_ter" name="timeRange" id="timeRange" onchange="showCustomContent()" >
                        <option <c:if test="${timeRange eq 'all' }">selected</c:if> value='all'>全部时间</option>
                        <option <c:if test="${timeRange eq 'today'}" >selected</c:if> value="today"  >当日注册</option>
                        <option <c:if test="${timeRange eq 'three'}" >selected</c:if>  value="three" >三天内</option>
                        <option <c:if test="${timeRange eq 'week'}">selected</c:if> value="week">七天内</option>
                        <option <c:if test="${timeRange eq 'month'}">selected</c:if> value="month" >一个月内</option>
                        <option <c:if test="${timeRange eq 'custom' }" >selected</c:if> value="custom" >自定义时间</option>
                        </select>
                        <div id="showCustom" style="display:none">
                         <span>自定义<input type="text" class="m_dy datepicker" id="startTimestr" value="${startTimestr }">
                         —
                         <input type="text" class="m_dy datepicker" id="endTimestr" value="${endTimestr }"></span>
						</div>
                    </dd>
                </dl>
                <dl class="m_time">
                    <dt>会员查询：</dt>
                    <dd>
                        <select class="m_ter" name="keywordType" id="keywordType" >
	                       <option <c:if test="${keywordType eq '0' }">selected</c:if> value='0'>会员名</option>
	                       <option <c:if test="${keywordType eq '1' }">selected</c:if> value='1'>手机号码</option>
                        </select> 
                        <span class="m_ct"><input type="text" id="keyword" class="m_cx" value="${keyword }" placeholder="输入要搜索的关键字"></span>
                    </dd>
                </dl>
                <div class="member_bottom">
                    <p>操作：</p>
                    <a href="#" id="query" >查询</a>
                    <a href="javascript:void(0)" id="clean" class="m_back" >清除</a> 
                </div> 
               </div>
        </div>
        	
        <div class="username-text animated fadeInRight">
            <div class="level m_bottom" style="padding-top:0px">
            <table width="100%" border="0" align="center" class="table table-striped" cellpadding="0" cellspacing="0">
                 <tbody>
                	 <tr>
                        <td><strong>会员名</strong></td>
                        <td><strong>身份</strong></td>
                        <td><strong>师父</strong></td>
                        <td><strong>所属商家</strong></td>
                        <td><strong>所属业务员</strong></td>
                        <td>
                        <div class="level_triangle">
                        	<p><strong>徒弟数</strong></p>
                            <c:choose>
                            	<c:when test="${sortName eq 'sum(son_sum)' && sortType eq 'desc' }" >
                            		<span class="l_sanj2" style="margin-top:6px" id="sortsonSum" data-id =1 ></span>
                            	</c:when>
                            	<c:when test="${sortName eq 'sum(son_sum)' && sortType eq 'asc' }" >
                            		<span class="l_sanj1" style="margin-top:6px" id="sortsonSum" data-id =2 ></span>
                            	</c:when>
                            	<c:otherwise>
                            		<span class="l_sanj3" id="sortsonSum" data-id=1 ></span>
                            	</c:otherwise>
                            </c:choose>
                        </div>
                        </td>
                        <td>
                        <div class="level_triangle">
                        	<p><strong>徒孙数</strong></p>
                            <c:choose>
                            	<c:when test="${sortName eq 'sum(grand_son_sum)' && sortType eq 'desc' }" >
                            		<span class="l_sanj2" style="margin-top:6px" id="sortgrandsonSum" data-id =1 ></span>
                            	</c:when>
                            	<c:when test="${sortName eq 'sum(grand_son_sum)' && sortType eq 'asc' }" >
                            		<span class="l_sanj1" style="margin-top:6px" id="sortgrandsonSum" data-id =2 ></span>
                            	</c:when>
                            	<c:otherwise>
                            		<span class="l_sanj3" id="sortgrandsonSum" data-id=1 ></span>
                            	</c:otherwise>
                            </c:choose>
                        </div>
                        </td>
                        <td>
                        <div class="level_triangle">
                        	<p><strong>徒孙孙数</strong></p>
                            <c:choose>
                            	<c:when test="${sortName eq 'sum(ggrand_son_sum)' && sortType eq 'desc' }" >
                            		<span class="l_sanj2" style="margin-top:6px" id="sortggsonsSum" data-id =1 ></span>
                            	</c:when>
                            	<c:when test="${sortName eq 'sum(ggrand_son_sum)' && sortType eq 'asc' }" >
                            		<span class="l_sanj1" style="margin-top:6px" id="sortggsonsSum" data-id =2 ></span>
                            	</c:when>
                            	<c:otherwise>
                            		<span class="l_sanj3" id="sortggsonsSum" data-id=1 ></span>
                            	</c:otherwise>
                            </c:choose>
                        </div>
                        </td>
                        <td>
                        <div class="level_triangle">
                        	<p><strong>登录次数</strong></p>
                            <c:choose>
                            	<c:when test="${sortName eq 'sum(logintimes)' && sortType eq 'desc' }" >
                            		<span class="l_sanj2" style="margin-top:6px" id="sortloginTimes" data-id =1 ></span>
                            	</c:when>
                            	<c:when test="${sortName eq 'sum(logintimes)' && sortType eq 'asc' }" >
                            		<span class="l_sanj1" style="margin-top:6px" id="sortloginTimes" data-id =2 ></span>
                            	</c:when>
                            	<c:otherwise>
                            		<span class="l_sanj3" id="sortloginTimes" data-id=1 ></span>
                            	</c:otherwise>
                            </c:choose>
                        </div>
                        </td>
                        <td>
                        <div class="level_triangle">
                        	<p><strong>订单数</strong></p>
                            <c:choose>
                            	<c:when test="${sortName eq 'sum(order_sum)' && sortType eq 'desc' }" >
                            		<span class="l_sanj2" style="margin-top:6px" id="sortorderSum" data-id =1 ></span>
                            	</c:when>
                            	<c:when test="${sortName eq 'sum(order_sum)' && sortType eq 'asc' }" >
                            		<span class="l_sanj1" style="margin-top:6px" id="sortorderSum" data-id =2 ></span>
                            	</c:when>
                            	<c:otherwise>
                            		<span class="l_sanj3" id="sortorderSum" data-id=1 ></span>
                            	</c:otherwise>
                            </c:choose>
                        </div>
                        </td>
                        <td>
                        <div class="level_triangle">
                        	<p><strong>成功订单</strong></p>
                            <c:choose>
                            	<c:when test="${sortName eq 'sum(order_success_sum)' && sortType eq 'desc' }" >
                            		<span class="l_sanj2" style="margin-top:6px" id="sortorderSuccessSum" data-id =1 ></span>
                            	</c:when>
                            	<c:when test="${sortName eq 'sum(order_success_sum)' && sortType eq 'asc' }" >
                            		<span class="l_sanj1" style="margin-top:6px" id="sortorderSuccessSum" data-id =2 ></span>
                            	</c:when>
                            	<c:otherwise>
                            		<span class="l_sanj3" id="sortorderSuccessSum" data-id=1 ></span>
                            	</c:otherwise>
                            </c:choose>
                        </div></td>
                     </tr>
                     <c:forEach var="list" items="${page.getContent() }" >
                     	<tr bgcolor="f3f3f4" >
                     		<td><a href="${ctx}/admin/user/toEditUser/${list.user.id}">${list.user.username }</a></td>
                     		<td>${list.user.userType.getStr()}</td>
                     		<c:choose>
	                     		<c:when test="${empty list.parent }" >
	                     			<td>无</td>
	                     		</c:when>
	                     		<c:otherwise>
	                     			<td><a target="_blank" href="${ctx}/admin/user/list?userId=${list.user.userInfoBoth.parent.id}&getType=getSons" >${list.parent.username }</a></td>
	                     		</c:otherwise>
                     		</c:choose>
                     		<c:choose>
	                     		<c:when test="${empty list.seller }" >
	                     			<td>无</td>
	                     		</c:when>
	                     		<c:otherwise>
	                     			<td><a target="_blank" href="${ctx}/admin/user/list?userId=${list.user.userInfoBoth.seller.id}&getType=getSeller" >${list.seller.username }</a></td>
	                     		</c:otherwise>
                     		</c:choose>
                     		<c:choose>
	                     		<c:when test="${empty list.salesman }" >
	                     			<td>无</td>
	                     		</c:when>
	                     		<c:otherwise>
	                     			<td><a target="_blank" href="${ctx}/admin/user/list?userId=${list.user.userInfoBoth.salesman.id}&getType=getSales" >${list.salesman.username }</a></td>
	                     		</c:otherwise>
                     		</c:choose>
                     		<td  ><a target="_blank" href="${ctx }/admin/user/list?userId=${list.user.id}&getType=getSons&registerStarttime=${sStartTimestr}&registerEndtime=${sEndTimestr}" >${list.sonsSum}</a></td>
                    		<td  ><a target="_blank" href="${ctx }/admin/user/list?userId=${list.user.id}&getType=getGrandSons&registerStarttime=${sStartTimestr}&registerEndtime=${sEndTimestr}" >${list.grandSonsSum}</a></td>
                    		<td  ><a target="_blank" href="${ctx }/admin/user/list?userId=${list.user.id}&getType=getGGrandSons&registerStarttime=${sStartTimestr}&registerEndtime=${sEndTimestr}" >${list.ggrandSonsSum}</a></td>
                    		<td  ><a target="_blank" style= "cursor:default" >${list.loginTimes }</a>
                    		<td  ><a target="_blank" href="${ctx }/admin/orderform/weixin/list?userId=${list.user.id}&createStarttime=${sStartTimestr}&createEndtime=${sEndTimestr}" >${list.orderSum}</a></td>
                    		<td  ><a target="_blank" href="${ctx }/admin/orderform/weixin/list?userId=${list.user.id}&getType=successOrder&createStarttime=${sStartTimestr}&createEndtime=${sEndTimestr}" >${list.orderSuccessSum}</a></td>
                     	</tr>
                     </c:forEach>                  
                      </tbody>
                     </table>
						 <div class="text-right">

							<div class="btn-group">
								<div class="dataTables_paginate paging_simple_numbers"
									id="DataTables_Table_0_paginate">
									<tags:pagination page="${page}" paginationSize="10"
										hrefSubfix="sortName=${sortName }&sortType=${sortType }&state=${state }&userType=${userType}&loginTimes=${loginTimes }&sonSum=${sonSum }&grandsonSum=${grandsonSum}&orderSum=${orderSum }&orderSuccessSum=${orderSuccessSum }&keywordType=${keywordType }&keyword=${keyword}&timeRange=${timeRange }&sStartTimestr=${sStartTimestr }&sEndTimestr=${sEndTimestr}&startTimestr=${startTimestr }&endTimestr=${endTimestr}&ggrandsonSum=${ggrandsonSum }"
										hrefPrefix="${ctx}/admin/reportStatistics/userReport/userReport" />
								</div>
								</div>
							</div>
            </div>
		</div>
</body>
</html>