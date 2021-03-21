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
<title>贷款申请页面</title>
<script type="text/javascript" src="${ctx }/static/js/common.js"></script>
<script type="text/javascript" src="${ctx }/static/js/brokerageAlgorithm.js"></script>
<script type="text/javascript">
document.title="申请贷款";
var loanMinAmount = ${product.info.loanMinAmount} ;
var loanMaxAmount = ${product.info.loanMaxAmount} ;
loanMinAmount = loanMinAmount /10000;
loanMaxAmount = loanMaxAmount /10000;
var loanMinTime = ${product.info.loanMinTime};
var loanMaxTime = ${product.info.loanMaxTime};
var interestType = ${product.interestType.ordinal()};
var billType = ${product.type.billType.ordinal()};
$(function(){
	$("#btn_submit").click(function(){
		
		if(!check()){
			return ;
		}
		showLoading();
		$.post('${ctx}/weixin/orderform/addOrderform;JSESSIONID=<%=request.getSession().getId()%>',$("#form_submit").serialize(),function(res){
			if(res.code == 1){
				var orderformId = res.mes;
				location.href = "${ctx}/weixin/orderform/success/"+ orderformId +";JSESSIONID=<%=request.getSession().getId()%>";
			}else{
				layer.open({content: res.mes, time: 2});
			}
		})
		hideLoading();
	})
	/** 计算佣金*/
	$(".in_brokerage").change(function(){
		changeBrokerage();
	});
	$(".in_brokerage").click(function(){
		changeBrokerage();
	});
})
function check(){
	var interestType = ${product.interestType.ordinal()};
	if(!checkName()){
		return false;
	}
	if(!checkCity()){
		return false;
	}
	if(!checkIdcart()){
		return false;
	}
	if(!checkMoney()){
		return false;
	}
	if(!checkLoanTime()){
		return false;
	}
	if(!checkTelephone()){
		return false;
	}
	if(billType == '1'){
		if(!checkSpreadRate()){
			return false;
		}
	}
	
	return true;	
}
function checkName(){
	var name = $("#ipt_name").val().trim();
	if(name.length == 0){
		layer.open({content: '请输入姓名', time: 2});
		return false;
	}
	if(name.length >10){
		layer.open({content: '姓名不能超过10个字符', time: 2});
		return false;
	}
	return true;
}
function checkCity(){
	var city = $("#ipt_city").val().trim();
	if(city.length == 0){
		layer.open({content:'请选择城市',time:2});
		return false;
	}
	return true;
}
function checkIdcart(){
	var idcard = $("#ipt_idcart").val().trim();
	if(idcard.length == 0){
		layer.open({content:'请输入身份证后六位',time:2});
		return false;
	}
	if(idcard.length != 6){
		layer.open({content:'请输入正确身份证后6位',time:2});
		return false;
	}
	if(isNaN(idcard)){
		layer.open({content:'身份证后6位只能是数字',time:2});
		return false;
	}
	return true;
}
function checkMoney(){
	var money = $("#ipt_money").val().trim();
	if(money.length == 0){
		layer.open({content:'请输入贷款金额',time:2});
		return false;
	}
	if(isNaN(money)){
		layer.open({content:'贷款金额只能是数字',time:2});
		return false;
	}
	if(!CommValidate.isPositiveInteger(money)){
		layer.open({content:'贷款金额为正整数',time:2});
		return false;
	}
	if(money<loanMinAmount){
		layer.open({content:'请输入'+loanMinAmount+'-'+ loanMaxAmount+'的金额',time:2});
		return false;
	}
	if(money>loanMaxAmount){
		layer.open({content:'请输入'+loanMinAmount+'-'+ loanMaxAmount+'的金额',time:2});
		return false;
	}
	return true;
}
function checkLoanTime(){
	var loanTime = $("#ipt_loanTime").val().trim();
	
	if(loanTime.length == 0){
		layer.open({content:'请输入贷款期限',time:2});
		return false;
	}
	if(isNaN(loanTime)){
		layer.open({content:'贷款期限只能是数字',time:2});
		return false;
	}
	if(!CommValidate.isPositiveInteger(loanTime)){
		layer.open({content:'贷款期限为正整数',time:2});
		return false;
	}
	/* //特殊模式只能6、12、24、36
	if(interestType == '3' && billType =='2'){
		if(loanTime != '6' || loanTime != '12'||loanTime != '24'||loanTime != '36'){
			layer.open({content:'该产品贷款期限只能是6个月、12个月、24个月、36个月',time:2});
			return false;
		}
	} */
	if(loanTime < loanMinTime){
		layer.open({content:'请输入'+loanMinTime +"-"+ loanMaxTime+'的贷款期限',time:2});
		return false;
	}
	if(loanTime > loanMaxTime){
		layer.open({content:'请输入'+loanMinTime +"-"+ loanMaxTime+'的贷款期限',time:2});
		return false;
	}
	return true;
}
function checkTelephone(){
	var telephone = $("#ipt_telephone").val().trim();
	if(telephone.length == 0){
		layer.open({content:'请输入贷款手机号',time:2});
		return false;
	}
	if(telephone.length != 11){
		layer.open({content:'手机号码必须是11位',time:2});
		return false;
	}
	if(!CommValidate.checkPhone(telephone)){
		layer.open({content:'手机号码格式错误',time:2});
		return false;
	}
	return true;
}
function checkSpreadRate(){
	var interestType = ${product.interestType.ordinal()};
	var spreadRate = $("#ipt_spreadRate").val().trim();
	
	var spreadMinRate = $("#ipt_spreadMin").val();
	var spreadMaxRate = $("#ipt_spreadMax").val();

	if(spreadRate.length == 0){
		layer.open({content:'请输入加价息差',time:2});
		return false;
	}
	if (isNaN(spreadRate)) {
    	layer.open({content:'加价息差需为数字',time:2});
		return false;
    }
	// 加价息差值判断
	if(interestType !='1'){
		var spreadRate1 = $("#ipt_spreadRate").val().trim()/100;
		if(parseFloat(spreadRate1)<parseFloat(spreadMinRate)){
			layer.open({content: '加价息差不能小于后台设置的最小值', time: 2});
			return false;
		}
		if(parseFloat(spreadRate1)>parseFloat(spreadMaxRate)){
			layer.open({content: '加价息差不能大于后台设置的最大值', time: 2});
			return false;
		}
	}
	if(interestType =='1'){
		var spreadRate1 = $("#ipt_spreadRate").val().trim()/1000;
		if(parseFloat(spreadRate1)<parseFloat(spreadMinRate)){
			layer.open({content: '加价息差不能小于后台设置的最小值', time: 2});
			return false;
		}
		if(parseFloat(spreadRate1)>parseFloat(spreadMaxRate)){
			layer.open({content: '加价息差不能大于后台设置的最大值', time: 2});
			return false;
		}
	}
	if(interestType != '1'){
	 //检查小数点后是否对于两位
	    if (spreadRate.split(".").length > 1 && spreadRate.split(".")[1].length > 3) {
	        layer.open({content: '加价息差小数点不超过三位', time: 2});
	        return false;
	    }else
	    if (spreadRate.split(".").length > 1 && spreadRate.split(".")[0].length > 2) {
	        layer.open({content: '加价息差不能超过100%', time: 2});
	        return false;
	    }else
	    if (spreadRate.split(".").length < 2 && spreadRate.length > 2) {
	        layer.open({content: '加价息差不能超过100%', time: 2});
	        return false;
	    }
	}
	if(interestType == '1'){
		//检查小数点后是否对于两位
	    if (spreadRate.split(".").length > 1 && spreadRate.split(".")[1].length > 2) {
	        layer.open({content: '加价息差小数点不超过二位', time: 2});
	        return false;
	    }else
	    if (spreadRate.split(".").length > 1 && spreadRate.split(".")[0].length > 2) {
	        layer.open({content: '加价息差不能超过1000‰', time: 2});
	        return false;
	    }else
	    if (spreadRate.split(".").length < 2 && spreadRate.length > 2) {
	        layer.open({content: '加价息差不能超过1000‰', time: 2});
	        return false;
	    }
	}
	return true;	
}

function changeBrokerage(){
	var money = $("#ipt_money").val().trim();
	var loanTime = $("#ipt_loanTime").val().trim();
	var brokerageRateNum ;
	if(money.length !=0 && loanTime !=0){
		/** 赚差价-按月*/
		if(interestType == '0' && billType == '1'){
			var spreadRate = $("#ipt_spread").val();
			var spreadMinRate = $("#ipt_spreadMin").val();
			var spreadMaxRate = $("#ipt_spreadMax").val();
			var iptSpreadRate = $("#ipt_spreadRate").val().trim()/100;
			if (isNaN(iptSpreadRate)) {
				layer.open({content: '加价息差需为数字', time: 2});
				return false;
		    }
			/* if(parseFloat(iptSpreadRate)<parseFloat(spreadMinRate)){
				layer.open({content: '加价息差不能小于后台设置的最小值', time: 2});
				return false;
			} */
			/* if(parseFloat(iptSpreadRate)>parseFloat(spreadMaxRate)){
				layer.open({content: '加价息差不能大于后台设置的最大值', time: 2});
				return false;
			} */
			
			brokerageRateNum = getDifferenceMonthBrokerage(money,iptSpreadRate,loanTime*30);
			$("#dd_brokerageRateNum").html(brokerageRateNum +"元");
			$("#ipt_brokerageRateNum").val(brokerageRateNum);
		}
		/** 赚差价-按日*/
		if(interestType == '1' && billType == '1'){
			var spreadRate = $("#ipt_spread").val();
			var spreadMaxRate = $("#ipt_spreadMax").val();
			var iptSpreadRate = $("#ipt_spreadRate").val().trim()/1000;
			if (isNaN(iptSpreadRate)) {
				layer.open({content: '加价息差值需为数字', time: 2});
				return false;
		    }
			/* if(parseFloat(iptSpreadRate)<parseFloat(spreadMinRate)){
				layer.open({content: '加价息差不能小于后台设置的最小值', time: 2});
				return false;
			} */
			/* if(parseFloat(iptSpreadRate)>parseFloat(spreadMaxRate)){
				layer.open({content: '加价息差不能大于后台设置的最大值', time: 2});
				return false;
			} */
			if(loanTime > 15){
				loanTime = 15;
			}
			brokerageRateNum = getDifferenceDayBrokerage(money,iptSpreadRate,loanTime);
			$("#dd_brokerageRateNum").html(brokerageRateNum +"元");
			$("#ipt_brokerageRateNum").val(brokerageRateNum);
		}
		
		/** 赚差价-收益金*/
		if(interestType == '2' && billType == '1'){
			var spreadRate = $("#ipt_spread").val();
			var spreadMaxRate = $("#ipt_spreadMax").val();
			var spreadMaxRate = $("#ipt_spreadMax").val();
			var iptSpreadRate = $("#ipt_spreadRate").val().trim()/100;
			if (isNaN(iptSpreadRate)) {
				layer.open({content: '约定费用差价需为数字', time: 2});
				return false;
		    }
			/* if(parseFloat(iptSpreadRate)<parseFloat(spreadMinRate)){
				layer.open({content: '约定费用差价不能小于后台设置的最小值', time: 2});
				return false;
			} */
			/* if(parseFloat(iptSpreadRate)>parseFloat(spreadMaxRate)){
				layer.open({content: '约定费用差价不能大于后台设置的最大值', time: 2});
				return false;
			} */
			brokerageRateNum = getDifferenceProfitBrokerage(money,iptSpreadRate,loanTime);
			$("#dd_brokerageRateNum").html(brokerageRateNum +"元");
			$("#ipt_brokerageRateNum").val(brokerageRateNum);
		}
		
		/** 提成-按月*/
		if(interestType == '0' && (billType == '2'|| billType == '0')){
			var expense = $("#ipt_expense").val();
			var percentageRate;
			if(billType =='2'){
				percentageRate = $("#ipt_percentageRate").val();
			}
			if(billType =='0'){
				percentageRate = 1;
			}
			
			brokerageRateNum = getPercentMonthBrokerage(money,expense,loanTime*30,percentageRate);
			$("#dd_brokerageRateNum").html(brokerageRateNum +"元");
			$("#ipt_brokerageRateNum").val(brokerageRateNum);
		}
		
		/** 提成-按日*/
		if(interestType == '1' && (billType == '2'|| billType == '0')){
			var expense = $("#ipt_expense").val();
			var percentageRate;
			if(billType =='2'){
				percentageRate = $("#ipt_percentageRate").val();
			}
			if(billType =='0'){
				percentageRate = 1;
			}
			brokerageRateNum = getPercentByDayBrokerage(money,expense,loanTime,percentageRate);
			$("#dd_brokerageRateNum").html(brokerageRateNum +"元");
			$("#ipt_brokerageRateNum").val(brokerageRateNum);
		}

		/** 提成-收益金*/
		if(interestType == '2' && (billType == '2'|| billType == '0')){
			var expense = $("#ipt_expense").val();
			var percentageRate;
			if(billType =='2'){
				percentageRate = $("#ipt_percentageRate").val();
			}
			if(billType =='0'){
				percentageRate = 1;
			}
			brokerageRateNum = getPercentProfitBrokerage(money,expense,percentageRate);
			$("#dd_brokerageRateNum").html(brokerageRateNum +"元");
			$("#ipt_brokerageRateNum").val(brokerageRateNum);
		}
		
		/** 提成-特殊模式*/
		if(interestType == '3' && billType == '2'){
			var pa = $("#ipt_algoParamA").val();
			var pb = $("#ipt_algoParamB").val();
			var pc = $("#ipt_algoParamC").val();
			var pd = $("#ipt_algoParamD").val();
			var pe = $("#ipt_algoParamE").val();
			var pf = $("#ipt_algoParamF").val();
			var pg = $("#ipt_algoParamG").val();
			var ph = $("#ipt_algoParamH").val();
			
			brokerageRateNum =getPercentSpecialBrokerage(money,loanTime*30,pa,pb,pc,pd,pe,pf,pg,ph);
			$("#dd_brokerageRateNum").html(brokerageRateNum +"元");
			$("#ipt_brokerageRateNum").val(brokerageRateNum);
			
		}

	}
}
</script>
</head>
<body>
	<input type="hidden" id="ipt_spread" value="${product.info.spread}">
	<input type="hidden" id="ipt_spreadMin" value="${product.info.spreadMin}">
	<input type="hidden" id="ipt_spreadMax" value="${product.info.spreadMax}">
	<input type="hidden" id="ipt_expense" value="${product.info.expense}">
	<input type="hidden" id="ipt_percentageRate" value="${product.info.percentageRate}">
	<input type="hidden" id="ipt_algoParamA" value="${product.info.algoParamA}">
	<input type="hidden" id="ipt_algoParamB" value="${product.info.algoParamB}">
	<input type="hidden" id="ipt_algoParamC" value="${product.info.algoParamC}">
	<input type="hidden" id="ipt_algoParamD" value="${product.info.algoParamD}">
	<input type="hidden" id="ipt_algoParamE" value="${product.info.algoParamE}">
	<input type="hidden" id="ipt_algoParamF" value="${product.info.algoParamF}">
	<input type="hidden" id="ipt_algoParamG" value="${product.info.algoParamG}">
	<input type="hidden" id="ipt_algoParamH" value="${product.info.algoParamH}">
	<div class="apply">
	 <form id="form_submit">
	  <input type = "hidden" name ="productId" value = "${product.id }"/>
	  <input type = "hidden" id = "ipt_brokerageRateNum" name = "brokerageRateNum"/>
        <dl>
            <dt>姓名</dt>
            <dd><input type="text"  placeholder="请填写真实姓名" name = "name" id = "ipt_name" class="a_xm"></dd>
        </dl>
         <dl>
            <dt>所在地</dt>
            <dd>
                <samp>福建省</samp>
                <input type = "hidden" name = "province" value = "福建省"/>
                <select class="a_xm1" name="city" id = "ipt_city">
                	<option value = "厦门市">厦门市</option>
                	<option value = "福州市">福州市</option>
                    <option value = "泉州市">泉州市</option>
                    <option value = "莆田市">莆田市</option>
                    <option value = "漳州市">漳州市</option>
                    <option value = "龙岩市">龙岩市</option>
                    <option value = "三明市">三明市</option>
                    <option value = "南平市">南平市</option>
                    <option value = "宁德市">宁德市</option>
                </select>
             </dd>
        </dl>
        <input type = "hidden" name="idcard" id = "ipt_idcart" value = "000000">
         <!-- <dl>
            <dt>身份证号</dt>
            <dd><input type="text"  placeholder="身份证号后6位" name = "idcard" id = "ipt_idcart" class="a_xm"></dd>
        </dl> -->
         <dl>
            <dt>贷款金额</dt>
            <dd><input type="text"  placeholder="${util:showTenThousandPrice(product.info.loanMinAmount)}-${util:showTenThousandPrice(product.info.loanMaxAmount)}" name = "money" id = "ipt_money" class="a_xm2 in_brokerage"><span>万元</span></dd>
        </dl>
        <c:if test = "${product.interestType.ordinal() eq '1' }">
	        <dl>
	            <dt>贷款期限</dt>
	            <dd>
	            <c:choose>
		            	<c:when test="${product.info.loanMinTime == product.info.loanMaxTime }">
		            		<input type="text"  value="${product.info.loanMinTime }" name = "loanTime" id = "ipt_loanTime" class="a_xm2 in_brokerage" readonly="true"><span>天</span>
		            	</c:when>
		            	<c:otherwise>
		            		<input type="text"  placeholder="${product.info.loanMinTime }-${product.info.loanMaxTime }" name = "loanTime" id = "ipt_loanTime" class="a_xm2 in_brokerage"><span>天</span>
		            	</c:otherwise>
		            </c:choose>
	            <%-- <input type="text"  placeholder="${product.info.loanMinTime }-${product.info.loanMaxTime }" name = "loanTime" id = "ipt_loanTime" class="a_xm2 in_brokerage"><span>天</span> --%>
	            </dd>
	        </dl>
        </c:if>
        <c:if test = "${product.interestType.ordinal() != '1' }">
        	<c:if test = "${product.interestType.ordinal() != '3' }">
		        <dl>
		            <dt>贷款期限</dt>
		            <dd>
		            <c:choose>
		            	<c:when test="${product.info.loanMinTime == product.info.loanMaxTime }">
		            		<input type="text"  value="${product.info.loanMinTime }" name = "loanTime" id = "ipt_loanTime" class="a_xm2 in_brokerage" readonly="true"><span>个月</span>
		            	</c:when>
		            	<c:otherwise>
		            		<input type="text"  placeholder="${product.info.loanMinTime }-${product.info.loanMaxTime }" name = "loanTime" id = "ipt_loanTime" class="a_xm2 in_brokerage"><span>个月</span>
		            	</c:otherwise>
		            </c:choose>
		            	
		            	
		            </dd>
		        </dl>
	        </c:if>
	        <c:if test = "${product.interestType.ordinal() == '3' }">
		        <dl>
		            <dt>贷款期限</dt>
		            <dd>
		            	<span>月</span>
		                <select class="a_xm6 in_brokerage" name = "loanTime" id = "ipt_loanTime">
		                	<option value = "6">6</option>
		                    <option value = "12">12</option>
		                    <option value = "24">24</option>
		                    <option value = "36">36</option>
		                </select>
		            </dd>
	        	</dl>
	        </c:if>
        </c:if>
        <dl>
            <dt>手机号</dt>
            <dd><input type="text"  placeholder="贷款人手机号" name = "telephone" id = "ipt_telephone" class="a_xm"></dd>
        </dl>
         <c:if test = "${product.interestType.ordinal() eq '0' && product.type.billType =='EARNDIFFERENCE' }">
        <dl>
            <dt>月息</dt>
            <dd>
            	<span>%/月</span>
                <!-- <input type="text" id = "ipt_spreadRate" name = "spreadRate" class="a_xm4 in_brokerage"> -->
                <select class="a_xm4 in_brokerage" id = "ipt_spreadRate" name = "spreadRate" >
                	<c:forEach items = "${spreads }" var = "spread">
                		<option value = "${spread }">${spread }</option>
                	</c:forEach>
                </select>
                <span>%+</span><span>${util:showRateWithoutUnit(product.info.spread) }</span>
            </dd>
        </dl>
        </c:if>
        <c:if test = "${product.interestType.ordinal() eq '1' && product.type.billType =='EARNDIFFERENCE' }">
        <dl>
            <dt>日息</dt>
            <dd>
            	<span>‰/天</span>
                <!-- <input type="text" id = "ipt_spreadRate" name = "spreadRate" class="a_xm4 in_brokerage"> -->
                <select class="a_xm4 in_brokerage" id = "ipt_spreadRate" name = "spreadRate">
                	<c:forEach items = "${spreads }" var = "spread">
                	<option value = "${spread }">${spread }</option>
                	</c:forEach>
                	
                </select>
                <span>‰+</span><span>${util:showThousandRateWithoutUnit(product.info.spread) }</span>
            </dd>
        </dl>
        </c:if>
        <c:if test = "${product.interestType.ordinal() eq '2' && product.type.billType =='EARNDIFFERENCE' }">
        <dl>
            <dt>收益金</dt>
            <dd>
            	<span>%/月</span>
                <!-- <input type="text" id = "ipt_spreadRate" name = "spreadRate" class="a_xm4 in_brokerage"> -->
                <select class="a_xm4 in_brokerage" id = "ipt_spreadRate" name = "spreadRate">
                	<c:forEach items = "${spreads }" var = "spread">
                		<option value = "${spread }">${spread }</option>
                	</c:forEach>
                </select>
                <span>%+</span><span>${util:showRateWithoutUnit(product.info.spread) }</span>
            </dd>
        </dl>
        
        </c:if >
        <!-- 赚提成 -->
        <c:if test = "${product.interestType.ordinal() eq '0' && product.type.billType.ordinal() eq '2' }">
        	<dl>
            <dt>利息</dt>
            <dd>
            	<span>%/月</span>
                <span>${util:showRateWithoutUnit(product.info.expense) }</span>
            </dd>
        </dl>
        </c:if >
         <c:if test = "${product.interestType.ordinal() eq '1' && product.type.billType.ordinal() eq '2' }">
        	<dl>
            <dt>日息</dt>
            <dd>
            	<span>‰/天</span>
                <span>${util:showThousandRateWithoutUnit(product.info.expense) }</span>
            </dd>
        </dl>
        </c:if>
        <c:if test = "${product.interestType.ordinal() eq '2' && product.type.billType.ordinal() eq '2' }">
        	<dl>
            <dt>手续费</dt>
            <dd>
            	<span>%/月</span>
                <span>${util:showRateWithoutUnit(product.info.expense) }</span>
            </dd>
        </dl>
        </c:if>
        <c:if test = "${product.type.billType.ordinal() != '0' }">
	        <dl>
	            <dt>预计佣金</dt>
	            <dd>
	            	<span class="kuang">${product.info.brokerageSendDesc }</span>
	            	<span id="dd_brokerageRateNum">0元</span>
	            </dd>
	        </dl>
        </c:if>
         <dl>
            <dt>备注</dt>
            <dd><input type="text" maxlength="30"  placeholder="订单备注信息" name = "remark" id = "ipt_remark" class="a_xm"></dd>
        </dl>
        </form> 
        <p>由于借款人不确定性，本平台可能在线下进行产品匹配，以平台最终确认为准。</p>
        <a id = "btn_submit"><samp>提交申请</samp></a>
       
    </div> 
    
</body>
</html>