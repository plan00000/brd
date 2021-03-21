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
<title>收徒奖励</title>
<script type="text/javascript">
$(function() {
	activeNav2("5","5_2");
    var item = $("tr"); 
    for(var i=0;i<item.length;i++){ 
        if(i%2==1){ 
            item[i].style.backgroundColor="#f3f3f4"; 
        } 
    } 
	$("#issueBonus").click(function(){
		$("#bonusFrame").css("display","block");
	});
	$("#canfirm").click(function(){
		 $("#bonusFrame").hide();
	});
	$(".w_right").click(function(){
		 $("#bonusFrame").hide();
	});
	$("#starOrderSetting").click(function(){
		location.href = "${ctx}/admin/activity/starOrderSetting?activityType=2"; 
	}); 
	
	$("#allstate").click(function(){
		$("#compelete").removeClass("bgc");
		$("#onduty").removeClass("bgc");
		location.href ="${ctx}/admin/activity/apprenticeReward";
	});
	$("#compelete").click(function(){
		location.href ="${ctx}/admin/activity/apprenticeReward?isCompelete=1";
		$("#onduty").removeClass("bgc");
		$("#compelete").addClass("bgc");
	});
	$("#onduty").click(function(){
		$("#compelete").removeClass("bgc");
		$("#onduty").addClass("bgc");
		location.href ="${ctx}/admin/activity/apprenticeReward?isCompelete=0";
	});
	
	//徒弟
	$("#sortsonSum").click(function(){
		var id=$(this).data('id');
		if(id == 1){
			$("#ipt_sortName").val("acitivitySonSum");
			$("#ipt_sortType").val("asc");
			requestLoad();
		}else{
			$("#ipt_sortName").val("acitivitySonSum");
			$("#ipt_sortType").val("desc");
			requestLoad();
		}
	});
	
})
	function changeValue(object){
		var $this = $(object);
		var type = $this.data("type");
		var value = $this.data("value");
		if(type== 'ordersStatus'){
			$(".orderstatus").removeClass('e_class');
			$this.addClass('e_class');
			$("#ipt_status").val(value);
			requestLoad();
		}
	}
	function requestLoad(){
		$("#request_form").submit();
	}
	
</script>
</head>
<body>
	<form id = "request_form" action ="${ctx}/admin/activity/apprenticeReward">
		<input type ="hidden" id ="ipt_status" value ="${status}" name = "status"/>
		<input type ="hidden" id ="ipt_sortName" value ="${sortName }" name = "sortName"/>
		<input type ="hidden" id = "ipt_sortType" value = "${sortType}" name = "sortType"/>
		<input type = "hidden" id ="ipt_page" name = "page"/>
	</form>
	<div class="row border-bottom">
	<div class="basic">
        <p>活动管理</p>
        <span><a href="<c:url value='/admin/main'/>" style="margin-left:0;">首页</a>><a href="#" >活动管理</a>><a><strong>收徒奖励</strong></a></span>
    </div>
	</div>
       <div class="employee animated fadeInRight e_chan">
           <dl>
           	 <dt>完成状态：</dt>
  			  <dd>
         	<p class=" orderstatus <c:if test = "${status =='' }"> e_class</c:if>" data-type = "ordersStatus" data-value = "" onclick = "changeValue(this)">全部状态</p>
            <p class=" orderstatus <c:if test = "${status =='COMPLETE' }"> e_class</c:if>" data-type = "ordersStatus" data-value = "COMPLETE" onclick = "changeValue(this)">已完成</p>
            <p class=" orderstatus <c:if test = "${status =='UNCOMPLETE' }"> e_class</c:if>" data-type = "ordersStatus" data-value = "UNCOMPLETE" onclick = "changeValue(this)">进行中</p>
     
             </dd>
           </dl>
           <dl>
           	 <dt>操作：</dt>
             <dd><samp id ="starOrderSetting">活动设置</samp></dd>
           </dl>
            </div>
            <div class="username-text animated fadeInRight">
             <div class="row">
			<div class="col-lg-12">
				<div class="ibox float-e-margins">
				<div class="table-responsive">
            <div class="level l_repost">
            
            
             <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
                 <tbody>
                	 <tr>
                        <td width="230"><strong>会员名</strong></td>
                        <td width="230"><strong>手机号码</strong></td>
                        <td width="230">                       
                         <div class="level_triangle">
                        	<p><strong>收徒数</strong></p>
                            <c:choose>
                            	<c:when test="${sortName eq 'acitivitySonSum' && sortType eq 'desc' }" >
                            		<span class="l_sanj2" id="sortsonSum" data-id =1 ></span>
                            	</c:when>
                            	<c:when test="${sortName eq 'acitivitySonSum' && sortType eq 'asc' }" >
                            		<span class="l_sanj1" id="sortsonSum" data-id =2 ></span>
                            	</c:when>
                            	<c:otherwise>
                            		<span class="l_sanj3" id="sortsonSum" data-id=1 ></span>
                            	</c:otherwise>
                            </c:choose>
                        </div>
                      </td>
                      	<td width="230" >
                      		<strong>获得奖励</strong>
                      	</td>
                        <td width="230"><strong>状态</strong></td>
                     </tr>
                      <c:forEach items="${page.getContent()}" var="item">
                   	  <tr>
                        <td>${item.username }</td>
                        <td>${item.mobileno }</td>
                        <td class="m_ls">${item.userInfoBoth.acitivitySonSum}</td>
                        <c:if test="${item.userInfoBoth.acitivitySonSum > maxNum-1}">
                        	<td>${item.userInfoBoth.apprenticeAwareBrokerage }</td>
                        	<td>已完成</td>
                        </c:if>
                        <c:if test="${item.userInfoBoth.acitivitySonSum < maxNum}">
                        	<td>0</td>
                        	<td>进行中</td>
                        </c:if>
                      </tr>
                       </c:forEach>
                     </tbody>
                     </table>
						<div class="text-right">

							<div class="btn-group">
								<div class="dataTables_paginate paging_simple_numbers"
									id="DataTables_Table_0_paginate">
									<tags:pagination page="${page}" paginationSize="10"
										hrefSubfix=""
										hrefPrefix="${ctx}/admin/activity/apprenticeReward" />
								</div>
							</div>
						</div>
                     <div id="bonusFrame" class="withdraw w_top" style="display: none;">
                           <p><font></font></p>
                  </div>
        </div> 
        </div> 
        </div> 
        </div> 
        </div> 
		</div>
</body>
</html>