<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="util" uri="functions"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html >
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>${title }</title>
<script src="${ctx}/static/js/input-number-change.js"></script>
<script type="text/javascript">
$(function(){
	activeNav2("3","3_3");
	//录入佣金
	$("#btn_Ok").click(function(){
		if(check()){
		$.post('${ctx}/admin/orderform/brokerage/changeStatus;JSESSIONID=<%=request.getSession().getId()%>',$("#brokerage_form").serialize(),function(res){
			if(res.code ==1){
				alert("操作成功");
				location.reload();
			}else{
				alert(res.mes);
			}
		});
	}
});
	//放款
	$("#btn_hadSend").click(function(){
		//判断是不是财务人员
		$.post('${ctx}/admin/orderform/brokerage/isFinancesend;JSESSIONID=<%=request.getSession().getId()%>',function(res){
			if(res.code == 1){
				var sendStatus = ${brokerageApply.sendStatus.ordinal()};
				if(sendStatus == '0'){
					$(".f_box").removeClass('hidden');
				}else{
					$(".i_ued").removeClass('hidden');
				}
			}else{
				alert(res.mes);
			}
		})
		
	});
	//发放佣金
	$("#btn_nextSend").click(function(){
		var sendMethod = $('.f_box input[name = "send"]:checked').val();
		if(sendMethod == '0'){
			$(".f_box").addClass('hidden');
			//金额
			var signalsendmony = ${brokerageApply.selfBrokerage } +${brokerageApply.fatherBrokerage} + ${brokerageApply.businessBrokerage} +${brokerageApply.salesmanBrokerage };
			signalsendmony = Number((signalsendmony).toFixed(2));
			$("#singalsendmony").html("￥"+signalsendmony);
			$('.amount').removeClass('hidden');
			$("#ipt_sendMethod").val(0);
			
		}else{
			$(".f_box").addClass('hidden');
			$(".i_ued").removeClass('hidden');
			$("#ipt_sendMethod").val(1);
		}
	});
	//发放佣金取消
	$("#btn_cancelSend").click(function(){
		$(".f_box").addClass('hidden');
	});
	//财务发放一次发放
	$("#next_singalsend").click(function(){
		var data = {};
		data.brokerageId = ${brokerageApply.id};
		data.sendStatus = 0;
		$.post('${ctx}/admin/orderform/brokerage/financesend;JSESSIONID=<%=request.getSession().getId()%>',data,function(res){
			if(res.code ==1){
				location.reload();
			}else{
				alert(res.mes);
			}
		});
	});
	//财务发放取消
	$("#cancel_singalsend").click(function(){
		$(".amount").addClass('hidden');
		$(".f_box").removeClass('hidden');
	//	location.reload();
	});
	//财务发放多次发放确定
	$("#btn_manyTimes_ok").click(function(){
		var hasSendTimes = ${brokerageApply.hasSendTimes};
		if(checkManyTimes()){
			$.post('${ctx}/admin/orderform/brokerage/financesend;JSESSIONID=<%=request.getSession().getId()%>',$("#submitSend_form").serialize(),function(res){
				if(res.code == 1){
					alert("操作成功");
					location.reload();
				}else{
					alert(res.mes);
				}
			})
		}
	
	})
	//财务发放多次发放取消
	$("#btn_manyTimes_cancel").click(function(){
		var sendStatus = ${brokerageApply.sendStatus.ordinal()};
		if(sendStatus == '1' ){
			$(".i_ued").addClass('hidden');
		}else{
			$(".i_ued").addClass('hidden');
			$(".f_box").removeClass('hidden');
		}
	})
	//取消
	$("#btn_Cancel").click(function(){
		location.href = "${ctx}/admin/orderform/brokerage/list;JSESSIONID=<%=request.getSession().getId()%>";
	})
	//ceo确定成功
	$("#btn_ceopass_Ok").click(function(){
		$.post('${ctx}/admin/orderform/brokerage/ceoConfirm;JSESSIONID=<%=request.getSession().getId()%>',$("#brokerage_form").serialize(),function(res){
			if(res.code ==1){
				alert("操作成功");
				location.reload();
			}else{
				alert(res.mes);
			}
		});
	})
	//ceo确定取消
	$("#btn_ceopass_Cancel").click(function(){
		location.href = "${ctx}/admin/orderform/brokerage/list;JSESSIONID=<%=request.getSession().getId()%>";
	})
})
function checkManyTimes(){
	var hasSendTimes = ${brokerageApply.hasSendTimes};
	var sendStatus = $("#ipt_sendMethod").val().trim();
	if(hasSendTimes == 0 && sendStatus == 1){
		if(!checkSendTimes()){
			return false;
		}
	}
	var billType = ${brokerageApply.orderform.product.type.billType.ordinal()};
	if(billType != '0'){
		if(!checkSelfResidualBrokerage()){
			return false;
		}
	}
	
	if(!checkFatherResidualBrokerage()){
		return false;
	}
	if(!checkBusinessResidualBrokerage()){
		return false;
	}
	if(!checkSalesResidualBrokerage()){
		return false;
	}
	return true;
}
function checkSendTimes(){
	var iptsendTimes = $("#ipt_sendTimes").val().trim();
	if(iptsendTimes.length ==0){
		showMsg("预计发放次数不能为空");
		return false;
	}
	if(iptsendTimes<2){
		showMsg("发放次数不能少于2次");
		return false;
	}
	return true;
}

function check() {
	var billType = ${brokerageApply.orderform.product.type.billType.ordinal()};
	if(billType != '0'){
		if(!checkSelfBrokerage()){
			return false;
		}
	}
	
	if(!checkFatherBrokerage()){
		return false;
	}
	if(!checkBusinessBrokerage()){
		return false;
	}
	if(!checkSalesBrokerage()){
		return false;
	}
	return true;
}



function checkSelfBrokerage(){
	var selfSubmitBrokerage = $("#iselfBrokerage").val().trim();
	var selfBrokerage = ${brokerageApply.selfBrokerage};
	var selfResidualBrokerage = $("#ipt_selfResidualBrokerage").val().trim();
	var status = ${brokerageApply.status.ordinal()};
	var sendStatus = ${brokerageApply.sendStatus.ordinal()};
	var hasSendTimes = ${brokerageApply.hasSendTimes}
	if(selfSubmitBrokerage.length == 0){
		alert("提单人发放佣金金额不能为空");
		return false;
	}
	if(parseFloat(selfSubmitBrokerage)<0){
		alert("提单人佣金不能为负数");
		return false;
	}
	if(status=='4'){
		if(parseFloat(selfSubmitBrokerage) > parseFloat(selfBrokerage)){
			alert("提单人佣金不能大于最大可发放金额");
			return false;
		}
	}
	if(sendStatus == '1' && hasSendTimes > '1'){
		if(parseFloat(selfSubmitBrokerage) > parseFloat(selfResidualBrokerage)){
			alert("提单人佣金不能大于最大可发放剩余佣金");
			return false;
		}
	}
	if (isNaN(selfSubmitBrokerage)) {
		alert("提单人佣金需为数字");
		return false;
    }
	if (selfSubmitBrokerage.split(".").length > 1 && selfSubmitBrokerage.split(".")[1].length > 2) {
		alert("提单人佣金小数点不超过二位");
        return false;
	}
	return true;
}



function checkSelfResidualBrokerage(){
	var selfSubmitBrokerage = $("#ipt_selfSubmitBrokerage").val().trim();
	var selfBrokerage = ${brokerageApply.selfBrokerage};
	var selfResidualBrokerage = $("#ipt_selfResidualBrokerage").val().trim();
	var sendStatus = ${brokerageApply.sendStatus.ordinal()};
	var hasSendTimes = ${brokerageApply.hasSendTimes}
	if(selfSubmitBrokerage.length == 0){
		showMsg("提单人发放佣金金额不能为空");
		return false;
	}
	if(parseFloat(selfSubmitBrokerage)<0){
		showMsg("提单人佣金不能为负数");
		return false;
	}
	if(parseFloat(selfSubmitBrokerage) > parseFloat(selfBrokerage)){
		showMsg("提单人佣金不能大于最大可发放金额");
		return false;
	} 
	if(sendStatus == '1' && hasSendTimes > '1'){
		if(parseFloat(selfSubmitBrokerage) > parseFloat(selfResidualBrokerage)){
			showMsg("提单人佣金不能大于最大可发放剩余佣金");
			return false;
		}
	}
	if (isNaN(selfSubmitBrokerage)) {
		showMsg("提单人佣金需为数字");
		return false;
    }
	if (selfSubmitBrokerage.split(".").length > 1 && selfSubmitBrokerage.split(".")[1].length > 2) {
		showMsg("提单人佣金小数点不超过二位");
        return false;
	}
	return true;
}
function checkFatherResidualBrokerage(){
	var fatherSubmitBrokerage = $("#ipt_fatherSubmitBrokerage").val().trim();
	var fatherBrokerage = ${brokerageApply.fatherBrokerage};
	var fatherResidualBrokerage = $("#ipt_fatherResidualBrokerage").val().trim();
	var sendStatus = ${brokerageApply.sendStatus.ordinal()};
	var hasSendTimes = ${brokerageApply.hasSendTimes}
	if(fatherSubmitBrokerage.length == 0){
		showMsg("师父发放佣金金额不能为空");
		return false;
	}
	if(parseFloat(fatherSubmitBrokerage)<0){
		showMsg("师父佣金不能为负数");
		return false;
	}
	if(parseFloat(fatherSubmitBrokerage) > parseFloat(fatherBrokerage)){
		showMsg("师父佣金不能大于最大可发放金额");
		return false;
	}
	if(sendStatus == '1' && hasSendTimes > '1'){
		if(parseFloat(fatherSubmitBrokerage) > parseFloat(fatherResidualBrokerage)){
			showMsg("师父佣金不能大于最大可发放剩余佣金");
			return false;
		}
	}
	if (isNaN(fatherSubmitBrokerage)) {
		showMsg("师父佣金需为数字");
		return false;
    }
	if (fatherSubmitBrokerage.split(".").length > 1 && fatherSubmitBrokerage.split(".")[1].length > 2) {
		showMsg("师父佣金小数点不超过二位");
        return false;
	}
	return true;
}
function checkFatherBrokerage(){
	var fatherSubmitBrokerage = $("#ifatherBrokerage").val().trim();
	var status = ${brokerageApply.status.ordinal()};
	var fatherBrokerage = ${brokerageApply.fatherBrokerage};
	var fatherResidualBrokerage = $("#ipt_fatherResidualBrokerage").val().trim();
	var sendStatus = ${brokerageApply.sendStatus.ordinal()};
	var hasSendTimes = ${brokerageApply.hasSendTimes}
	if(fatherSubmitBrokerage.length == 0){
		alert("师父发放佣金金额不能为空");
		return false;
	}
	if(parseFloat(fatherSubmitBrokerage)<0){
		alert("师父佣金不能为负数");
		return false;
	}
	if(status=='4'){
	  	if(parseFloat(fatherSubmitBrokerage) > parseFloat(fatherBrokerage)){
			alert("师父佣金不能大于最大可发放金额");
			return false;
		} 
	}
	if(sendStatus == '1' && hasSendTimes > '1'){
		if(parseFloat(fatherSubmitBrokerage) > parseFloat(fatherResidualBrokerage)){
			alert("师父佣金不能大于最大可发放剩余佣金");
			return false;
		}
	}
	if (isNaN(fatherSubmitBrokerage)) {
		alert("师父佣金需为数字");
		return false;
    }
	if (fatherSubmitBrokerage.split(".").length > 1 && fatherSubmitBrokerage.split(".")[1].length > 2) {
        alert("师父佣金小数点不超过二位");
        return false;
	}
	return true;
}
function checkBusinessResidualBrokerage(){
	var businessSubmitBrokerage = $("#ipt_businessSubmitBrokerage").val().trim();
	var businessBrokerage = ${brokerageApply.businessBrokerage};
	var businessResidualBrokerage = $("#ipt_businessResidualBrokerage").val().trim();
	var sendStatus = ${brokerageApply.sendStatus.ordinal()};
	var hasSendTimes = ${brokerageApply.hasSendTimes}
	if(businessSubmitBrokerage.length == 0){
		showMsg("商家发放佣金金额不能为空");
		return false;
	}
	if(parseFloat(businessSubmitBrokerage)<0){
		showMsg("商家佣金不能为负数");
		return false;
	}
	if(parseFloat(businessSubmitBrokerage) > parseFloat(businessBrokerage)){
		showMsg("商家佣金不能大于最大可发放金额");
		return false;
	}
	if(sendStatus == '1' && hasSendTimes > '1'){
		if(parseFloat(businessSubmitBrokerage) > parseFloat(businessResidualBrokerage)){
			showMsg("商家佣金不能大于最大可发放剩余佣金");
			return false;
		}
	}
	if (isNaN(businessSubmitBrokerage)) {
		showMsg("商家佣金需为数字");
		return false;
    }
	if (businessSubmitBrokerage.split(".").length > 1 && businessSubmitBrokerage.split(".")[1].length > 2) {
		showMsg("商家佣金小数点不超过二位");
        return false;
	}
	return true;
}
function checkBusinessBrokerage(){
	var businessSubmitBrokerage = $("#ibusinessBrokerage").val().trim();
	var businessBrokerage = ${brokerageApply.businessBrokerage};
	var status = ${brokerageApply.status.ordinal()};
	var businessResidualBrokerage = $("#ipt_businessResidualBrokerage").val().trim();
	var sendStatus = ${brokerageApply.sendStatus.ordinal()};
	var hasSendTimes = ${brokerageApply.hasSendTimes}
	if(businessSubmitBrokerage.length == 0){
		alert("商家发放佣金金额不能为空");
		return false;
	}
	if(parseFloat(businessSubmitBrokerage)<0){
		alert("商家佣金不能为负数");
		return false;
	}
	if(status=='4'){
		if(parseFloat(businessSubmitBrokerage) > parseFloat(businessBrokerage)){
			alert("商家佣金不能大于最大可发放金额");
			return false;
		} 
	}
	if(sendStatus == '1' && hasSendTimes > '1'){
		if(parseFloat(businessSubmitBrokerage) > parseFloat(businessResidualBrokerage)){
			alert("商家佣金不能大于最大可发放剩余佣金");
			return false;
		}
	}
	if (isNaN(businessSubmitBrokerage)) {
    	alert("商家佣金需为数字");
		return false;
    }
	if (businessSubmitBrokerage.split(".").length > 1 && businessSubmitBrokerage.split(".")[1].length > 2) {
        alert("商家佣金小数点不超过二位");
        return false;
	}
	return true;
}
function checkSalesResidualBrokerage(){
	var salesmanSubmitBrokerage = $("#ipt_salesSubmitBrokerage").val().trim();
	var salesmanBrokerage = ${brokerageApply.salesmanBrokerage};
	var salesmanResidualBrokerage = $("#ipt_salesmanResidualBrokerage").val().trim();
	var sendStatus = ${brokerageApply.sendStatus.ordinal()};
	var hasSendTimes = ${brokerageApply.hasSendTimes}
	if(salesmanSubmitBrokerage.length == 0){
		showMsg("业务员发放佣金金额不能为空");
		return false;
	}
	if(parseFloat(salesmanSubmitBrokerage)<0){
		showMsg("业务员佣金不能为负数");
		return false;
	}
	if(parseFloat(salesmanSubmitBrokerage) > parseFloat(salesmanBrokerage)){
		showMsg("业务员佣金不能大于最大可发放金额");
		return false;
	}
	if(sendStatus == '1' && hasSendTimes > '1'){
		if(parseFloat(salesmanSubmitBrokerage) > parseFloat(salesmanResidualBrokerage)){
			showMsg("业务员佣金不能大于最大可发放剩余佣金");
			return false;
		}
	}
	if (isNaN(salesmanSubmitBrokerage)) {
		showMsg("业务员佣金需为数字");
		return false;
    }
	if (salesmanSubmitBrokerage.split(".").length > 1 && salesmanSubmitBrokerage.split(".")[1].length > 2) {
		showMsg("业务员佣金小数点不超过二位");
        return false;
	}
	return true;
}
function checkSalesBrokerage(){
	var salesmanSubmitBrokerage = $("#isalesmanBrokerage").val().trim();
	var salesmanBrokerage = ${brokerageApply.salesmanBrokerage};
	var status = ${brokerageApply.status.ordinal()};
	var salesmanResidualBrokerage = $("#ipt_salesmanResidualBrokerage").val().trim();
	var sendStatus = ${brokerageApply.sendStatus.ordinal()};
	var hasSendTimes = ${brokerageApply.hasSendTimes}
	if(salesmanSubmitBrokerage.length == 0){
		alert("业务员发放佣金金额不能为空");
		return false;
	}
	if(parseFloat(salesmanSubmitBrokerage)<0){
		alert("业务员佣金不能为负数");
		return false;
	}
	if(status=='4'){
		if(parseFloat(salesmanSubmitBrokerage) > parseFloat(salesmanBrokerage)){
			alert("业务员佣金不能大于最大可发放金额");
			return false;
		} 
	}
	if(sendStatus == '1' && hasSendTimes > '1'){
		if(parseFloat(salesmanSubmitBrokerage) > parseFloat(salesmanResidualBrokerage)){
			alert("业务员佣金不能大于最大可发放剩余佣金");
			return false;
		}
	}
	if (isNaN(salesmanSubmitBrokerage)) {
    	alert("业务员佣金需为数字");
		return false;
    }
	if (salesmanSubmitBrokerage.split(".").length > 1 && salesmanSubmitBrokerage.split(".")[1].length > 2) {
        alert("业务员佣金小数点不超过二位");
        return false;
	}
	return true;
}
function showMsg(msg){
	$("#msgSpan").html(msg);
	$("#msgSpan").attr("style","display:inline-block;color:#B94A48");
}
</script>
</head>
<body>
	<input type = "hidden" id = "ipt_selfResidualBrokerage" value = "${brokerageApply.selfResidualBrokerage}">
	<input type = "hidden" id = "ipt_fatherResidualBrokerage" value = "${brokerageApply.fatherResidualBrokerage}">
	<input type = "hidden" id = "ipt_businessResidualBrokerage" value = "${brokerageApply.businessResidualBrokerage}">
	<input type = "hidden" id = "ipt_salesmanResidualBrokerage" value = "${brokerageApply.salesmanResidualBrokerage }">
	<input type = "hidden" id = "ipt_sendMethod" value = "${brokerageApply.sendStatus.ordinal() }">
	<div class="row border-bottom">
		<div class="basic">
	      	<p>订单管理</p>
	        <span><a href="${ctx }/admin/main;JSESSIONID=<%=request.getSession().getId()%>" style="margin-left:0;">首页</a>><a href="#" >订单管理</a>><a href="#" >佣金订单</a>><a><strong>佣金详情</strong></a></span>
	    </div>
	</div>
	<div class="details animated fadeInRight">
     <div class="new_xinxi"><p><font>订单信息</font></p></div>
         <div class="details_box d_xqy">
             <ul>
            		 <li>
                     <p>会员名：</p>
                     <span>${brokerageApply.user.username }</span>
                 </li>
                 <li>
                     <p>下单时间：</p>
                     <span>${util:formatNormalDate(brokerageApply.orderform.createTime) }</span>
                 </li>
                 <%-- <li>
                     <p>身份证号：</p>
                     <span>${brokerageApply.orderform.idcard }</span>
                 </li> --%>
                 <li>
                     <p>所在地：</p>
                     <span>${brokerageApply.orderform.province }&nbsp;&nbsp;${brokerageApply.orderform.city }</span>
                 </li>
                 <li >
                     <p>申请贷款：</p>
                     <span>${brokerageApply.productInfo.productName }</span>
                 </li>
                 <li>
                     <p>已贷款金额：</p>
                     <span>${util:showTenThousandPrice(brokerageApply.orderform.actualMoney) }万</span>
                 </li>
                 <li>
                     <p>贷款期限：</p>
                     <c:choose>
                     	<c:when test="${brokerageApply.orderform.product.interestType.ordinal() == '1' }">
                     		<span>${brokerageApply.orderform.loanTime }天</span>
                     	</c:when>
                     	<c:otherwise>
                     		<span>${brokerageApply.orderform.loanTime }个月</span>
                     	</c:otherwise>
                     </c:choose>
                     
                 </li>
                 <li >
                     <p>订单备注：</p>
                     <span><c:if test = "${ empty brokerageApply.orderform.remark }">无</c:if>${brokerageApply.orderform.remark }</span>
                 </li>
                 <c:if test= "${ brokerageApply.orderform.product.type.billType.ordinal() !='0'}">
	                 <li>
	                     <p>提单人：</p>
	                     <span class="d_ls"><a href = "${ctx}/admin/user/toEditUser/${brokerageApply.user.id}">${brokerageApply.user.username }</a></span>
	                 </li>
                 </c:if>
                 <li>
                     <p>商家：</p>
                     <span class="d_ls"><a href = "${ctx}/admin/user/toEditUser/${brokerageApply.orderform.oldBussiness.id}">${brokerageApply.orderform.oldBussiness.username }</a></span>
                 </li>
            </ul>
            </div>
            <div class="details_box d_xqy">
             <ul>
            		<li>
                     <p>会员身份：</p>
                     <span>${brokerageApply.user.userType.getStr() }</span>
                 </li>
                 <li>
                     <p>订单编号：</p>
                     <span class="d_ls"><a href = "${ctx }/admin/orderform/weixin/detail/${brokerageApply.orderform.id}">${brokerageApply.orderform.orderNo }</a></span>
                 </li>
                 <li>
                     <p>手机号码：</p>
                     <span>${brokerageApply.orderform.telephone }</span>
                 </li>
                 <li>
                     <p></p>
                     <span></span>
                 </li>
                 <li>
                     <p>贷款类型：</p>
                     <span>${brokerageApply.orderform.product.type.billType.getDes() }</span>
                 </li>
                 <c:if test = "${brokerageApply.orderform.product.type.billType.ordinal() =='1' && brokerageApply.orderform.product.interestType.ordinal() !='2' }">
	                 <li>
	                     <p>加价息差：</p>
	                     <c:if test = "${brokerageApply.orderform.product.interestType.ordinal() =='0' }">
	                      	<span>${util:showRateWithoutUnit(brokerageApply.orderform.spreadRate) }%</span>
	                 	 </c:if><c:if test = "${brokerageApply.orderform.product.interestType.ordinal() =='1' }">
	                      	<span>${util:showThousandRateWithoutUnit(brokerageApply.orderform.spreadRate) }‰</span>
	                 	 </c:if>
	                 </li>
                 </c:if>
                 <c:if test = "${brokerageApply.orderform.product.type.billType.ordinal() =='1' && brokerage.orderform.product.interestType.ordinal() =='2' }">
	                 <li>
	                     <p>约定费用差价：</p>
	                     <span>${util:showRateWithoutUnit(brokerageApply.orderform.spreadRate) }%</span>
	                 </li>
                 </c:if>
                 <c:if test = "${brokerageApply.orderform.product.type.billType.ordinal() =='2' && brokerageApply.orderform.product.interestType.ordinal() !='3' }">
	                 <li>
	                     <p>提成比例：</p>
	                     <span>${util:showRateWithoutUnit(brokerageApply.orderform.percentageRate) }%</span>
	                 </li>
                 </c:if>
                 <%-- <c:if test = "${brokerageApply.orderform.product.type.billType.ordinal() =='2'&& brokerageApply.orderform.product.interestType.ordinal() =='1'}">
	                 <li>
	                     <p>提成比例：</p>
	                     <span>${util:showThousandRateWithoutUnit(brokerageApply.orderform.percentageRate) }‰</span>
	                 </li>
                 </c:if> --%>
                 <c:if test = "${brokerageApply.orderform.product.type.billType.ordinal() =='2' && brokerageApply.orderform.product.interestType.ordinal() =='3' }">
	                 <li>
	                     <p>佣金：</p>
	                     <span>${util:showRateWithoutUnit(brokerageApply.orderform.percentageRate) }%</span>
	                 </li>
                 </c:if>
                 <li>
                     <p>贷款利率：</p>
                     <c:if test="${brokerageApply.orderform.product.interestType.ordinal() !='1' }">
                     	<span>${util:showRateWithoutUnit(brokerageApply.interest) }%</span>
                 	 </c:if>
                 	 <c:if test="${brokerageApply.orderform.product.interestType.ordinal() =='1' }">
                     	<span>${util:showThousandRateWithoutUnit(brokerageApply.interest) }‰</span>
                 	 </c:if>
                 </li>
                 <li>
                     <p>师父：</p>
                     <span class="d_ls"><a href = "${ctx}/admin/user/toEditUser/${brokerageApply.orderform.oldParent.id}">${brokerageApply.orderform.oldParent.username }</a></span>
                 </li>
                 <li>
                     <p>业务员：</p>
                     <span class="d_ls"><a href = "${ctx }/admin/user/toEditUser//${brokerageApply.orderform.oldSalesman.id}">${brokerageApply.orderform.oldSalesman.realname }</a></span>
                 </li>
                </ul>
            </div>
            <div class="new_xinxi"><p><font>佣金确认</font></p></div>
              <div class="commission">
                <form id = "brokerage_form">
                <input type = "hidden" name = "brokerageId" value = "${brokerageApply.id }"/>
                	<c:if test = "${brokerageApply.status =='UNENTERING' || brokerageApply.status == 'RISKCHECK' || brokerageApply.status == 'CEOCHECK' }">
                	<c:if test = "${brokerageApply.orderform.product.type.billType.ordinal() !='0' }">
	                    <dl>
	                        <dt>提单人佣金：</dt>
	                        <dd><input type="text" class="c_input" id = "iselfBrokerage" name = "selfBrokerage" value = "${brokerageApply.selfBrokerage }"  />元</dd>
	                    </dl>
	                </c:if>
                    <dl>
                        <dt>师父佣金：</dt>
                        <dd><input type="text" class="c_input" id = "ifatherBrokerage"name = "fatherBrokerage" value = "${brokerageApply.fatherBrokerage }"  >元</dd>
                    </dl>
                    <dl>
                        <dt>商家佣金：</dt>
                        <dd><input type="text" class="c_input" id = "ibusinessBrokerage" name = "businessBrokerage" value = "${brokerageApply.businessBrokerage }" >元</dd>
                    </dl>
                    <dl>
                        <dt>业务员佣金：</dt>
                        <dd><input type="text" class="c_input" id = "isalesmanBrokerage" name = "salesmanBrokerage" value = "${brokerageApply.salesmanBrokerage }" >元</dd>
                    </dl>
                    <c:if test = "${brokerageApply.orderform.product.type.billType.ordinal() =='0' }">
	                    	<dl>
		                        <dt></dt>
		                        <dd><samp></samp></dd>
	                    	</dl>
	                </c:if>
                    </c:if>
                    <c:if test = "${brokerageApply.status.ordinal() == '3'|| brokerageApply.status.ordinal() == '4' || brokerageApply.status.ordinal() == '5'|| brokerageApply.status.ordinal() == '6'|| brokerageApply.status.ordinal() == '7'}">
	                    <c:if test = "${brokerageApply.orderform.product.type.billType.ordinal() !='0' }">
		                    <dl>
		                        <dt>提单人佣金：</dt>
		                        <dd><samp>${brokerageApply.selfBrokerage }</samp>元</dd>
		                    </dl>
	                    </c:if>
	                    <dl>
	                        <dt>师父佣金：</dt>
	                        <dd><samp>${brokerageApply.fatherBrokerage }</samp>元</dd>
	                    </dl>
	                    <dl>
	                        <dt>商家佣金：</dt>
	                        <dd><samp>${brokerageApply.businessBrokerage }</samp>元</dd>
	                    </dl>
	                    <dl>
	                        <dt>业务员佣金：</dt>
	                        <dd><samp>${brokerageApply.salesmanBrokerage }</samp>元</dd>
	                    </dl>
	                    <c:if test = "${brokerageApply.orderform.product.type.billType.ordinal() =='0' }">
	                    	<dl>
		                        <dt></dt>
		                        <dd><samp></samp></dd>
	                    	</dl>
	                    </c:if>
                    </c:if>
                    
                    <c:if test = "${brokerageApply.status == 'RISKCHECK' || brokerageApply.status == 'CEOCHECK' || brokerageApply.status == 'CEOPASS'}">
	                    <dl>
	                        <dt>审核意见：</dt>
	                        <dd>
		                        <span><input type="radio" class="c_ridus" name = "check" value ="PASS" checked="checked">通过</span>
		                        <span><input type="radio" class="c_ridus" name = "check" value ="REFUSE">拒绝</span>
	                        </dd>
	                    </dl>
	                </c:if>
	                <c:if test = "${brokerageApply.status == 'UNENTERING' || brokerageApply.status == 'RISKCHECK' ||brokerageApply.status == 'CEOCHECK'}">
	                    <p>
	                        <a href="#" id = "btn_Ok">保存</a>
	                        <a href="#" class="c_an" id = "btn_Cancel">取消</a>
	                    </p>
	                </c:if>
	                <c:if test = "${ brokerageApply.status == 'CEOPASS'}">
	                	 <p>
	                        <a href="#" id = "btn_ceopass_Ok">保存</a>
	                        <a href="#" class="c_an" id = "btn_ceopass_Cancel">取消</a>
	                    </p>
	                </c:if>
	                <!-- 立即放款 -->
	                <c:if test = "${brokerageApply.status == 'FINANCESEND'|| brokerageApply.status == 'MANYTIMES' }">
	                	<p>
	                        <a href="#" id = "btn_hadSend">立即放款</a>
	                    </p>
	                </c:if>
                 </form>
             </div> 
             
         <div class="new_xinxi">
               	 <p><font>操作记录</font></p>
                </div>
                <div class="record_mex tact">
               	<table width="100%" cellpadding="0" cellspacing="0" align="center">
                   <tbody>
                       <tr>
                       	<td  width="160"><strong>操作时间</strong></td>
                         <td  width="130"><strong>员工名</strong></td>
                         <td  width="130"><strong>角色</strong></td>
                         <td><strong>操作内容</strong></td>
                       </tr>
                       <c:forEach items = "${brokerageHistorys.content }" var = "brokerageHistory">
                       	<tr>
	                       	<td>${util:formatNormalDate(brokerageHistory.addDate)}</td>
	                        <td>${brokerageHistory.oper.username }</td>
	                        <td>${brokerageHistory.oper.role.rolename }</td>
                         <td><samp>${brokerageHistory.content }</samp></td>
                       </tr>
                       </c:forEach>
                   </tbody>
               </table>
               <div class=" m_right">
				<tags:pagination paginationSize="10" page="${brokerageHistorys}" hrefPrefix="${ctx }/admin/orderform/brokerage/detail/${brokerageApply.id}" hrefSubfix=""></tags:pagination> 
               </div>
              </div> 
        </div> 
        <div class="f_box hidden">
           <div class="finance">
                <p>此次佣金发放方式为：</p>
                <span><input type="radio" class="f_rad" name = "send" value = "0" checked="checked">一次性发放</span>
                <span><input type="radio" class="f_rad" name = "send" value = "1">分次发放</span>
                <samp id = "btn_nextSend">下一步</samp>
                <samp id = "btn_cancelSend" class="f_quxiao">取消</samp>
            </div>
        </div>
        <div  class="i_ued hidden">
           <div class="issued">
           <form id = "submitSend_form">
           <input type = "hidden" id = "ipt_sendMethod" name = "sendStatus" value = "1">
           <input type = "hidden" name = "brokerageId" value = "${brokerageApply.id }"/>
           	<p>预计分：<c:choose><c:when test = "${brokerageApply.hasSendTimes eq '1' }"><input type="text" class="i_con" id = "ipt_sendTimes" name = "sendTimes" ></c:when><c:otherwise><font>${brokerageApply.sendTimes }</font></c:otherwise></c:choose>次发放佣金；本次为第<font><c:choose><c:when test="${brokerageApply.hasSendTimes eq '0' }">1</c:when> <c:otherwise>${brokerageApply.hasSendTimes}</c:otherwise></c:choose></font>次发佣。</p>
               <dl  style="width:100%">
                   <dt>本次发佣金额：</dt>
                   <dd></dd>
               </dl>
               <c:if test = "${brokerageApply.orderform.product.type.billType.ordinal() !='0' }">
	               <dl>
	                   <dt>提单人佣金：</dt>
	                   <dd><input type="text" class="i_text" id = "ipt_selfSubmitBrokerage" name = "selfSubmitBrokerage">元</dd>
	               </dl>
               </c:if>
               <dl>
                   <dt>师父佣金：</dt>
                   <dd><input type="text" class="i_text" id = "ipt_fatherSubmitBrokerage" name = "fatherSubmitBrokerage">元</dd>
               </dl>
               <dl>
                   <dt>商家佣金：</dt>
                   <dd><input type="text" class="i_text" id = "ipt_businessSubmitBrokerage" name = "businessSubmitBrokerage">元</dd>
               </dl>
               <dl>
                   <dt>业务员佣金：</dt>
                   <dd><input type="text" class="i_text" id = "ipt_salesSubmitBrokerage" name = "salesSubmitBrokerage">元</dd>
               </dl>
               <dl class="i_line">
                   <dt>备注：</dt>
                   <dd>
                       <input type="text" class="i_txt" id = "ipt _remark" name = "remark" onKeyDown="gbcount2(this.form.remark,this.form.used1,33);"  onKeyUp="gbcount2(this.form.remark,this.form.used1,33);" onblur = "gbcount2(this.form.remark,this.form.used1,33);">
                       <input class="inputtext2 inputtextn" name="used1"  id="tremark" value="0/33" readonly="readonly"> 
                       <font>此条备注信息前端手机用户会看见</font>
                       <font id="msgSpan" style="display: none;color:#B94A48"></font>
                   </dd>
                   
               </dl>
               <dl class="i_line">
                   <dt></dt>
                   <dd>
                       <span id = "btn_manyTimes_ok">确认</span>
                       <span id = "btn_manyTimes_cancel">上一步</span>
                       <%-- <c:if test = "">
                       		<span id = "btn_manyTimes_cancel">上一步</span>
                       </c:if> --%>
                   </dd>
               </dl>
               </form>
             </div>
      </div>
      <div class="amount hidden">
   		<div class="amount_use">
        	<p>发放金额：<font id = "singalsendmony"></font></p>
            <span id = "cancel_singalsend">上一步</span>
            <span id = "next_singalsend">确认</span>
        </div>
   	</div>
</body>
</html>