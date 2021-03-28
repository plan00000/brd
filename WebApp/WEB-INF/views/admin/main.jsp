<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="util" uri="functions"%>
<%@ taglib prefix="shiro" uri="http://www.springside.org.cn/tags/shiro"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<html>
<head>
<title>城际车后台管理</title>
<script type="text/javascript">
$(function() {
	$("#countSelfhelploanNum").click(function(){
		location.href = "${ctx }/admin/orderform/weixin/list?status=UNCHECKED&billType=SELFHELPLOAN"
	})
	$("#countEarncommissionNum").click(function(){
		location.href = "${ctx }/admin/orderform/weixin/list?status=UNCHECKED&billType=EARNCOMMISSION"
	})
	$("#countEarndifferenceNum").click(function(){
		location.href = "${ctx }/admin/orderform/weixin/list?status=UNCHECKED&billType=EARNDIFFERENCE"
	})
/* 	$("#uncheck").click(function(){
		location.href = "${ctx }/admin/orderform/weixin/list?status=UNCHECKED"
	}) */
	
	
	
	$("#countPcUncheckNum").click(function(){
		location.href = "${ctx }/admin/orderform/pc/list?status=UNCHECKED"
	})
	$("#countPcUnloanNum").click(function(){
		location.href = "${ctx }/admin/orderform/pc/list?status=UNLOAN"
	})
/* 	$("#pcOrder").click(function(){
		location.href = "${ctx }/admin/orderform/pc/list?isMain=1"
	}) */
	
	
	$("#countFlowWithdrawUncheckNum").click(function(){
		location.href = "${ctx }/admin/orderform/flowwithdraw/list?status=NOCHECK"
	})
	$("#countFlowWithdrawUnloadNum").click(function(){
		location.href = "${ctx }/admin/orderform/flowwithdraw/list?status=NOLENDING"
	})
/* 	$("#flowOrder").click(function(){
		location.href = "${ctx }/admin/orderform/flowwithdraw/list?isMain=1"
	}) */
	
	var rolename = "${rolename}";
	$("#countBrokerageUncheckNum").click(function(){
		if(rolename=="Admin"){
			location.href = "${ctx }/admin/orderform/brokerage/list?status=CEOCHECK"
		}if(rolename=="风控经理"){
			location.href = "${ctx }/admin/orderform/brokerage/list?status=RISKCHECK"
		}if(rolename=="财务"){
			location.href = "${ctx }/admin/orderform/brokerage/list?status=UNENTERING"
		}
	})
	
	$("#countBrokerageFinanceNum").click(function(){
		location.href = "${ctx }/admin/orderform/brokerage/list?status=FINANCESEND"
	})
	$("#countBrokerageCeoPass").click(function(){
		location.href = "${ctx}/admin/orderform/brokerage/list?status=CEOPASS"
	})
/* 	$("#brokerage").click(function(){
		location.href = "${ctx }/admin/orderform/brokerage/list?isMain=1"
	}) */
	
	$("#pendingAudit").click(function(){
		location.href = "${ctx }/admin/user/list?contacted=true"
	})
/* 	$("#pendingAudit1").click(function(){
		location.href = "${ctx }/admin/user/list?contacted=true"
	}) */
	
})
</script>
</head>

<body>
		<div class="username animated fadeInRight">
       		 <p>${timename}好：${name }</p>
        </div>
              <div class="username-text animated fadeInRight">
              	<shiro:hasPermission name="ORDER_MANAGER">
                    <div class="username-01">
                    	<p>订单</p>
                        <samp>共有 ${orderAll}条</samp>
                        <div class="username-02">
                            <span>订单：</span>
                            <ul>
                                <li id="orderQx">取消订单<font>${ orderQx}</font></li>
                                <li id="orderYwc">已完成订单<font>${ orderYwc}</font></li>
                                <li id="orderCs">超时订单<font>${ orderCs}</font></li>
                            </ul>
                        </div>
                    </div>
                    </shiro:hasPermission>
                    <shiro:hasPermission name="ORDER_MANAGER_PC">
                    <div class="username-01">
                    	<p>司机</p>
                        <samp>共有 ${driverAll}条</samp>
                        <div class="username-02">
                            <span>实时状态：</span>
                            <ul>
                                <li id="driverSb">上班<font>${ driverSb}</font></li>
                                <li id="driverXb">下班<font>${ driverXb}</font></li>
                            </ul>
                        </div>
                    </div>
                    </shiro:hasPermission>
                    <shiro:hasPermission name="ORDER_MANAGER_WITHDRAW">
                    <div class="username-01">
                    	<p>乘客</p>
                        <samp>共 ${passengerAll}个会员</samp>
                        <div class="username-02">
                            <span>待操作：</span>
                            <ul>
                                <li id="passengerJy">禁用<font>${passengerJy }</font></li>
                                <li id="passengerZc">正常<font>${ passengerZc}</font></li>
                                <li id="passengerZx">注销<font>${ passengerZx}</font></li>
                            </ul>
                        </div>
                    </div>
                    </shiro:hasPermission>
                    <shiro:hasPermission name="ORDER_MANAGER_BROKERAGE">
                    <div class="username-01">
                    	<p>线路</p>
                        <samp>共 ${lines}条</samp>
                        <div class="username-02">
                            <span>最热门线路：</span>
                            <ul>
                                <li id="oneLine">最热线路<font>${lines }</font></li>
                            </ul>
                        </div>
                    </div>
                  </shiro:hasPermission>
        </div>
<div style="height:50px;"></div>
</body>
</html>