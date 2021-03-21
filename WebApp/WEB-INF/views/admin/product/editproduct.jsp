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
<script type="text/javascript" charset="utf-8" src="${ctx}/static/js/common.js"> </script>
<script src="${ctx}/static/js/input-number-change.js"></script>
<script>window.UEDITOR_HOME_URL = "${ctx}/"</script>
<script type="text/javascript" charset="utf-8" src="${ctx}/static/js/ueditor/ueditor.config.js"></script>
<script type="text/javascript" charset="utf-8" src="${ctx}/static/js/ueditor/ueditor.min.js"> </script>
<script type="text/javascript" charset="utf-8" src="${ctx}/static/js/ueditor/lang/zh-cn/zh-cn.js"> </script>
<title>${title }</title>
<script type="text/javascript">
var detailEditor1;
var detailEditor2;
$(function(){
	detailEditor1 =  UE.getEditor(
			'ipt_productDesc',
			{ toolbars: [["bold","italic","underline","justifyleft"]],
			wordCount:true,
			maximumWords:350,
			elementPathEnabled:false,
	        autoHeightEnabled: false,
	        enableAutoSave: false
	});
	detailEditor1.addListener("ready", function () {
		detailEditor1.setContent($("#div_productDesc").html());
	});
	detailEditor2 =  UE.getEditor(
			'ipt_requirment',
			{ toolbars: [["bold","italic","underline","justifyleft"]],
			wordCount:true,
			maximumWords:350,
			elementPathEnabled:false,
	        autoHeightEnabled: false,
	        enableAutoSave: false
	});
	detailEditor2.addListener("ready", function () {
		detailEditor2.setContent($("#div_requirment").html());
	});
	
	
	//初始化字符串长度
	var billType =${product.type.billType.ordinal()};
	//初始化前端佣金文字说明
	if(billType != 0){
		var ipt_fontBrokerageDesc = $("#ipt_fontBrokerageDesc").val();
		var h = ipt_fontBrokerageDesc.length;
		$("#tfontBrokerageDesc").val(h+"/8");
		
		var ipt_fontBrokerageNum = $("#ipt_fontBrokerageNum").val();
		var i = ipt_fontBrokerageNum.length;
		$("#tfontBrokerageNum").val(i+"/5");
	}
	if(billType == 1){
		var ipt_userRateDesc = $("#ipt_userRateDesc").val();
		var a = ipt_userRateDesc.length;
		$("#tuserRateDesc").val(a+"/25")
	}
	if(billType == 1){
		var ipt_fatherRateDesc = $("#ipt_fatherRateDesc").val();
		var b = ipt_fatherRateDesc.length;
		$("#tfatherRateDesc").val(b+"/25")
	}
	var ipt_productName = $("#ipt_productName").val();
	var c = ipt_productName.length;
	$("#tproductName").val(c+"/22")
	var ipt_repayment = $("#ipt_repayment").val();
	var d = ipt_repayment.length;
	$("#trepayment").val(d+"/9");
	if(billType != 0){
		var ipt_brokerageSendDesc = $("#ipt_brokerageSendDesc").val();
		var e = ipt_brokerageSendDesc.length;
		$("#tbrokerageSendDesc").val(e+"/9");
	}
	var ipt_materials = $("#ipt_materials").val();
	var g = ipt_materials.length;
	$("#tmaterials").val(g+"/100")
	
	activeNav2("4","4_2");
	/** 是否添加首页*/
	 $('#is_display').click(function(){
		if($("input[name='isDisplay']:checked").val()==1){
			 $("#div_isdisplay").removeClass('hidden');
		}else{
			$("#div_isdisplay").addClass('hidden');
		}
	 });
	 $('#no_display').click(function(){
		if($("input[name='isDisplay']:checked").val()==1){
			 $("#div_isdisplay").removeClass('hidden');
		}else{
			$("#div_isdisplay").addClass('hidden');
		}
	});
	 /** */
	$("#btn_save").click(function(){
		if(check()){
			$.post("${ctx}/admin/product/editProduct;JSESSIONID=<%=request.getSession().getId()%>",$("#submit_form").serialize(),
				function(data){
					if(data.code == 1){
						location.href = "${ctx}/admin/product/productlist;JSESSIONID=<%=request.getSession().getId()%>";
					}else{
						alert(data.mes);
					}
			});
		}
	});
	/* 自动生成 */
	$("#btn_autoRequirement").click(function(){
		var requirment = "1.18—65周岁大陆公民 <br>2.个人或者配偶名下拥有有产权物业 <br> 3.抵押物限制：住宅、店面、写字楼，房龄30年以内"
			+"<br> 4.已婚人士必须夫妻二人共同借款 "
			+"<br> 5.抵押率不超过评估价50%";
		detailEditor2.setContent(requirment);
	})
	$("#btn_autoMaterials").click(function(){
		var materials = "借款人夫妻双方身份证、户口本、结婚证、信用报告、抵押物产权证复印件、其他资产证明（如有）";
		$("#ipt_materials").val(materials);
		$("#tmaterials").val("44/100");
	})
	
	/**上一步 */
	$("#btn_back").click(function(){
		location.href = "${ctx}/admin/product/productlist;JSESSIONID=<%=request.getSession().getId()%>";
	})
	
})
function check(){
	/** 提单类型*/
	var billType = ${product.type.billType.ordinal() };
	var interestType = ${product.interestType.ordinal()};
	
	/** 赚差价*/
	if(billType == '1'){
		if(!checkSpread()){
			return false;
		}
	}
	/* 赚提成 */
	if(billType =='2' && interestType !='3'){
		if(!checkExpense()){
			return false;
		}
		if(!checkPercentageRate()){
			return false;
		}
	}
	/** 特殊模式*/
	if(interestType == '3'){
		if(!checkAlgoParams()){
			return false;
		}
	}
	/*自助贷 */
	if(billType == '0'){
		if(!checkExpense()){
			return false;
		}
	}
	/** 前端佣金显示*/
	if(billType !='0'){
		if(!checkFontBrokerage()){
			return false;
		}
	}
	/** 公共必填项*/
	
	if(!checkFatherRate()){
		return false;
	}
	if(!checkSellerRate()){
		return false;
	}
	if(!checkSalesmanRate()){
		return false;
	}
	
	if(billType !='0'){
		if(!checkRateDesc()){
			return false;
		}
	}
	if(!checkProductName()){
		return false;
	}
	if(!checkLoanRate()){
		return false;
	}
	/** 页面利率 */
	if(!checkLoanMinRate()){
		return false;
	}
	
	if(!checkLoanAmount()){
		return false;
	}
	
	if(!checkLoanTime()){
		return false;
	}
	
	if(!checkRepayment()){
		return false;
	}
	if(!checkRequirment()){
		return false;
	}
	if(!checkProductDesc()){
		return false;
	}
	if(!checkMaterials()){
		return false;
	}
	if(!checkSortid()){
		return false;
	}
	
	
	/** 是否添加首页排序*/
	if($("input[name='isDisplay']:checked").val()==1){
		if(!checkIndexSortid()){
			return false;
		}
	}
	return true;
}
function checkFontBrokerage(){
	var fontBrokerageDesc = $("#ipt_fontBrokerageDesc").val().trim();
	var fontBrokerageNum = $("#ipt_fontBrokerageNum").val().trim();
	if(fontBrokerageDesc.length == 0){
		alert("前端佣金比例文字不能为空");
		return false;
	}
	if(fontBrokerageNum.length == 0){
		alert("前端佣金数值不能为空");
		return false;
	}
	return true;
}
function checkFatherRate(){
	var fatherRate = $("#ipt_fatherRate").val().trim();
	if(fatherRate.length == 0){
		alert("师父佣金比例不能为空");
		return false;
	}
	if (isNaN(fatherRate)) {
    	alert("师父佣金比例需为数字");
		return false;
    }
	 //检查小数点后是否对于两位
    if (fatherRate.split(".").length > 1 && fatherRate.split(".")[1].length > 3) {
        alert("师父佣金比例小数点不超过三位");
        return false;
    }else
    if (fatherRate.split(".").length > 1 && fatherRate.split(".")[0].length > 2) {
        alert("师父佣金比例不能超过100%");
        return false;
    }else
    if (fatherRate.split(".").length < 2 && fatherRate.length > 2) {
        /* alert("师父佣金比例整数位数超过两位"); */
        alert("师父佣金比例不能超过100%");
        return false;
    }
	return true;
}
function checkSellerRate(){
	var sellerRate = $("#ipt_sellerRate").val().trim();
	if(sellerRate.length == 0){
		alert("商家佣金比例不能为空");
		return false;
	}
	if (isNaN(sellerRate)) {
    	alert("商家佣金比例需为数字");
		return false;
    }
	 //检查小数点后是否对于两位
    if (sellerRate.split(".").length > 1 && sellerRate.split(".")[1].length > 3) {
        alert("商家佣金比例小数点不超过三位");
        return false;
    }else
    if (sellerRate.split(".").length > 1 && sellerRate.split(".")[0].length > 2) {
        alert("商家佣金比例不能超过100%");
        return false;
    }else
    if (sellerRate.split(".").length < 2 && sellerRate.length > 2) {
        alert("商家佣金比例不能超过100%");
        return false;
    }
	return true;
}
function checkSalesmanRate(){
	var salesmanRate = $("#ipt_salesmanRate").val().trim();
	if(salesmanRate.length == 0){
		alert("业务员佣金比例不能为空");
		return false;
	}
	if (isNaN(salesmanRate)) {
    	alert("业务员佣金比例需为数字");
		return false;
    }
	 //检查小数点后是否对于两位
    if (salesmanRate.split(".").length > 1 && salesmanRate.split(".")[1].length > 3) {
        alert("业务员佣金比例小数点不超过三位");
        return false;
    }else
    if (salesmanRate.split(".").length > 1 && salesmanRate.split(".")[0].length > 2) {
        alert("业务员佣金比例不能超过100%");
        return false;
    }else
    if (salesmanRate.split(".").length < 2 && salesmanRate.length > 2) {
        alert("业务员佣金比例不能超过100%");
        return false;
    }
	return true;
}
function checkRateDesc(){
	var billType =${product.type.billType.ordinal()};
	
	var userRateDesc = $("#ipt_userRateDesc").val();
	
	if(userRateDesc.length == 0){
		alert("会员佣金说明不能为空");
		return false;
	}
	if(billType ==1){
		var fatherRateDesc = $("#ipt_fatherRateDesc").val().trim();
		if(fatherRateDesc.length == 0){
			alert("师父佣金说明不能为空");
			return false;
		}
	}
	return true;
}
function checkProductName(){
	var productName = $("#ipt_productName").val().trim();
	if(productName.length == 0){
		alert("产品名称不能为空");
		return false;
	}
	return true;
}
function checkLoanRate(){
	var interestType = ${product.interestType.ordinal()};
	var loanRate = $("#ipt_loanRate").val().trim();
	if(loanRate.length == 0){
		alert("贷款利率不能为空");
		return false;
	}
	if(interestType != '1'){
	 	//检查小数点后是否对于两位
	    if (loanRate.split(".").length > 1 && loanRate.split(".")[1].length > 3) {
	        alert("贷款利率小数点不超过三位");
	        return false;
	    }else
	    if (loanRate.split(".").length > 1 && loanRate.split(".")[0].length > 2) {
	        alert("贷款利率不能超过100%");
	        return false;
	    }else
	    if (loanRate.split(".").length < 2 && loanRate.length > 2) {
	        alert("贷款利率不能超过100%");
	        return false;
	    }
	}
	if(interestType =='1'){
		//检查小数点后是否对于两位
	    if (loanRate.split(".").length > 1 && loanRate.split(".")[1].length > 2) {
	        alert("贷款利率小数点不超过二位");
	        return false;
	    }else
	    if (loanRate.split(".").length > 1 && loanRate.split(".")[0].length > 3) {
	        alert("贷款利率不能超过1000‰");
	        return false;
	    }else
	    if (loanRate.split(".").length < 2 && loanRate.length > 3) {
	        alert("贷款利率不能超过1000‰");
	        return false;
	    }
	}
	return true;
}
function checkLoanAmount(){
	var loanMinAmount = $("#ipt_loanMinAmount").val().trim();
	var loanMaxAmount = $("#ipt_loanMaxAmount").val().trim();
	if(loanMinAmount.length == 0){
		alert("贷款最小金额不能为空");
		return false;
	}
	if(loanMaxAmount.length == 0){
		alert("贷款最大金额不能为空");
		return false;
	}
	if(parseFloat(loanMinAmount) >parseFloat(loanMaxAmount)){
		alert("贷款最小金额不能大于贷款最大金额");
		return false;
	}
	if (isNaN(loanMinAmount)) {
    	alert("贷款最小金额需为数字");
		return false;
    }
	if (isNaN(loanMaxAmount)) {
    	alert("贷款最大金额需为数字");
		return false;
    }
	if(!CommValidate.isPositiveInteger(loanMinAmount)){
		alert("贷款最小金额为正整数");
		return false;
	}
	if(!CommValidate.isPositiveInteger(loanMaxAmount)){
		alert("贷款最大金额为正整数");
		return false;
	}
	if(loanMinAmount.length >9){
		alert("贷款最小金额最大额度不能超过一亿");
		return false;
	}
	if(loanMaxAmount.length >9){
		alert("贷款最大金额最大额度不能超过一亿");
		return false;
	}
	return true;
}
function checkLoanTime(){
	var loanMinTime = $("#ipt_loanMinTime").val().trim();
	var loanMaxTime = $("#ipt_loanMaxTime").val().trim();
	if(loanMinTime.length == 0){
		alert("贷款最小期限不能为空");
		return false;
	}
	if(loanMaxTime.length == 0){
		alert("贷款最大期限不能为空");
		return false;
	}
	if (isNaN(loanMinTime)) {
    	alert("贷款最小期限需为数字");
		return false;
    }
	if (isNaN(loanMaxTime)) {
    	alert("贷款最大期限需为数字");
		return false;
    }
	if(!CommValidate.isPositiveInteger(loanMinTime)){
		alert("贷款最小期限为正整数");
		return false;
	}
	if(!CommValidate.isPositiveInteger(loanMaxTime)){
		alert("贷款最大期限为正整数");
		return false;
	}
	if(parseFloat(loanMinTime) >parseFloat(loanMaxTime)){
		alert("贷款最小期限不能大于贷款最大期限");
		return false;
	}
	return true;
}
function checkRepayment(){
	var repayment = $("#ipt_repayment").val().trim();
	if(repayment.length == 0){
		alert("贷款返还方式不能为空");
		return false;
	}
	if(repayment.length > 9){
		alert("贷款返还方式字数不能超过9个字");
		return false;
	}
	return true;
}
function checkRequirment(){
	var requirment = detailEditor2.getContent();
	if(requirment.length == 0){
		alert("贷款申请条件不能为空");
		return false;
	}
	return true;
}
function checkProductDesc(){
	var productDesc = detailEditor1.getContent();
	if(productDesc.length == 0){
		alert("产品信息不能为空");
		return false;
	}
	return true;
}
function checkMaterials(){
	var materials = $("#ipt_materials").val().trim();
	if(materials.length == 0){
		alert("贷款准备材料不能为空");
		return false;
	}
	if(materials.length > 350){
		alert("贷款准备材料字数不能超过350个字");
		return false;
	}
	return true;
}
function checkSortid(){
	var sortid = $("#ipt_sortid").val().trim();
	if(sortid.length == 0){
		alert("产品排序不能为空");
		return false;
	}
	if(isNaN(sortid)){
		alert("产品排序为数字");
		return false;
	}
	return true;
}
function checkIndexSortid(){
	var indexSortid = $("#ipt_indexSortid").val().trim();
	if(indexSortid.length == 0){
		alert("首页排序不能为空");
		return false;
	}
	if(isNaN(indexSortid)){
		alert("首页排序为数字");
		return false;
	}
	return true;
}
function checkSpread(){
	var billType = ${product.type.billType.ordinal() };
	var interestType = ${product.interestType.ordinal()};
	
	var spread = $("#ipt_spread").val().trim();
	var spreadMin = $("#ipt_spreadMin").val().trim();
	var spreadMax = $("#ipt_spreadMax").val().trim();
	if(spread.length == 0){
		if(interestType ==0){
			alert("月息不能为空");
		}
		if(interestType ==1){
			alert("日息不能为空");
		}
		if(interestType == 2){
			alert("手续费用不能为空");
		}
		return false;
	}
	if (isNaN(spread)) {
		if(interestType ==0){
			alert("月息低价需为数字");
		}
		if(interestType ==1){
			alert("日息低价需为数字");
		}
		if(interestType == 2){
			alert("收益金低价需为数字");
		}
		return false;
    }
	if(interestType != 1){
		 //检查小数点后是否对于两位
	    if (spread.split(".").length > 1 && spread.split(".")[1].length > 3) {
	        if(interestType ==0){
				alert("月息低价小数点不超过三位");
			}
			if(interestType == 2){
				alert("收益金低价小数点不超过三位");
			}
	        return false;
	    }else
	    if (spread.split(".").length > 1 && spread.split(".")[0].length > 2) {
	        if(interestType ==0){
				alert("月息低价整数位数超过两位");
			}
			if(interestType == 2){
				alert("收益金低价整数位数超过两位");
			}
	        return false;
	    }else
	    if (spread.split(".").length < 2 && spread.length > 2) {
	        if(interestType ==0){
				alert("月息低价不能超过100%");
			}
			if(interestType == 2){
				alert("收益金低价不能超过100%");
			}
       	return false;
	    }
   	}
	if(interestType == 1){
		if (spread.split(".").length > 1 && spread.split(".")[1].length > 2) {
			alert("日息低价小数点不超过二位");
	        return false;
	    }else
	    if (spread.split(".").length > 1 && spread.split(".")[0].length > 2) {
			alert("日息低价不能超过1000‰");
	        return false;
	    }else
	    if (spread.split(".").length < 2 && spread.length > 2) {
			alert("日息低价不能超过1000‰");
        	return false;
	    }
	}
	 
    if (isNaN(spreadMin)) {
    	alert("加价息差最小值需为数字");
		return false;
    }
    if(interestType !=1){
		 //检查小数点后是否对于两位
	    if (spreadMin.split(".").length > 1 && spreadMin.split(".")[1].length > 1) {
	        alert("加价息差最小值小数点不超过一位");
	        return false;
	    }else
	    if (spreadMin.split(".").length > 1 && spreadMin.split(".")[0].length > 2) {
	        alert("加价息差最小值不能超过100%");
	        return false;
	    }else
	    if (spreadMin.split(".").length < 2 && spreadMin.length > 2) {
	        alert("加价息差最小值不能超过100%");
	        return false;
	    }
   }
   if(interestType ==1){
   	if (spreadMin.split(".").length > 1 && spreadMin.split(".")[1].length > 1) {
	        alert("加价息差最小值小数点不超过一位");
	        return false;
	    }else
	    if (spreadMin.split(".").length > 1 && spreadMin.split(".")[0].length > 3) {
	        alert("加价息差最小值不能超过1000‰");
	        return false;
	    }else
	    if (spreadMin.split(".").length < 2 && spreadMin.length > 3) {
	        alert("加价息差最小值不能超过1000‰");
	        return false;
	    }
   }
	 
    if (isNaN(spreadMax)) {
    	alert("加价息差最大值需为数字");
		return false;
    }
    if(interestType !=1){
		 //检查小数点后是否对于两位
	    if (spreadMax.split(".").length > 1 && spreadMax.split(".")[1].length > 1) {
	        alert("加价息差最大值小数点不超过一位");
	        return false;
	    }else
	    if (spreadMax.split(".").length > 1 && spreadMax.split(".")[0].length > 2) {
	        alert("加价息差最大值不能超过100%");
	        return false;
	    }else
	    if (spreadMax.split(".").length < 2 && spreadMax.length > 2) {
	        alert("加价息差最大值不能超过100%");
	        return false;
	    }
   }
   if(interestType ==1){
   	 //检查小数点后是否对于两位
	    if (spreadMax.split(".").length > 1 && spreadMax.split(".")[1].length > 1) {
	        alert("加价息差最大值小数点不超过一位");
	        return false;
	    }else
	    if (spreadMax.split(".").length > 1 && spreadMax.split(".")[0].length > 3) {
	        alert("加价息差最大值不能超过1000‰");
	        return false;
	    }else
	    if (spreadMax.split(".").length < 2 && spreadMax.length > 3) {
	        alert("加价息差最大值不能超过1000‰");
	        return false;
	    }
   }
	if(parseFloat(spreadMin) > parseFloat(spreadMax)){
		alert("加价息差最小值不能大于加价息差最大值");
		return false;
	}
	
	return true;
}

function checkExpense(){
	var billType = ${product.type.billType.ordinal() };
	var interestType = ${product.interestType.ordinal()};
	var expense = $("#ipt_expense").val().trim();
	if(expense.length == 0){
		if(interestType ==0){
			alert("月息不能为空");
		}
		if(interestType ==1){
			alert("日息不能为空");
		}
		if(interestType == 2){
			alert("手续费用不能为空");
		}
		return false;
	}
	if (isNaN(expense)) {
		if(interestType ==0){
			alert("月息需为数字");
		}
		if(interestType ==1){
			alert("日息需为数字");
		}
		if(interestType == 2){
			alert("手续费需为数字");
		}
		return false;
    }
	 //检查小数点后是否对于两位
    if (expense.split(".").length > 1 && expense.split(".")[1].length > 3) {
    	if(interestType ==0){
			alert("月息小数点不超过三位");
		}
		if(interestType ==1){
			alert("日息小数点不超过三位");
		}
		if(interestType == 2){
			alert("手续费小数点不超过三位");
		}
        return false;
    }else
    if (expense.split(".").length > 1 && expense.split(".")[0].length > 2) {
    	if(interestType ==0){
			alert("月息整数位数超过两位");
		}
		if(interestType ==1){
			alert("日息整数位数超过两位");
		}
		if(interestType == 2){
			alert("手续费整数位数超过两位");
		}
        return false;
    }else
    if (expense.split(".").length < 2 && expense.length > 2) {
    	if(interestType ==0){
			alert("月息整数位数超过两位");
		}
		if(interestType ==1){
			alert("日息整数位数超过两位");
		}
		if(interestType == 2){
			alert("手续费整数位数超过两位");
		}
        return false;
    }
	return true;
}
function checkPercentageRate(){
	var percentageRate = $("#ipt_percentageRate").val().trim();
	if(percentageRate.length == 0){
		alert("提成比例不能为空");
		return false;
	}
	if (isNaN(percentageRate)) {
    	alert("提成比例需为数字");
		return false;
    }
	 //检查小数点后是否对于两位
    if (percentageRate.split(".").length > 1 && percentageRate.split(".")[1].length > 3) {
        alert("提成比例小数点不超过三位");
        return false;
    }else
    if (percentageRate.split(".").length > 1 && percentageRate.split(".")[0].length > 2) {
        alert("提成比例整数位数超过两位");
        return false;
    }else
    if (percentageRate.split(".").length < 2 && percentageRate.length > 2) {
        alert("提成比例整数位数超过两位");
        return false;
    }
	 return true;
}
function checkLoanMinRate(){
	var loanMinRate = $("#ipt_loanMinRate").val().trim();
	/* var loanMaxRate = $("#ipt_loanMaxRate").val().trim(); */
	if(loanMinRate.length ==0){
		alert("页面利率最小值不能为空");
		return false;
	}
	/* if(loanMaxRate.length ==0){
		alert("页面利率最大值不能为空");
		return false;
	} */
	
	 return true;
}
function checkAlgoParams(){
	var res = true;
	$(".ipt_algoParam").each(function(){
		if (res){
			res = checkAlgoParam($(this));
		}
	});
	return res;
}
function checkAlgoParam(object){
	var d = object.val();
	var str = object.data("data-str");
	//检查是否是非数字值
    if (isNaN(d)) {
    	alert(str + "需为数字");
		return false;
    }
    //检查小数点后是否对于两位
    if (d.split(".").length > 1 && d.split(".")[1].length > 3) {
        alert(str + "小数点不超过三位");
        return false;
    }else
    if (d.split(".").length > 1 && d.split(".")[0].length > 2) {
        alert(str + "整数位数超过两位");
        return false;
    }else
    if (d.split(".").length < 2 && d.length > 2) {
        alert(str + "整数位数超过两位");
        return false;
    }
    return true;
}
</script>
</head>
<body>
	<div class="row border-bottom">
	 	<div class="basic">
	       <p>产品管理</p>
	       <span><a href="${ctx }/admin/main;JSESSIONID=<%=request.getSession().getId()%>" style="margin-left:0;">首页</a>><a href="#" >产品管理</a>><a><strong>编辑产品</strong></a></span>
	
	    </div>
    </div>
	<div class="new_emp animated fadeInRight">
		<form id = "submit_form">
		<input type = "hidden" name = "productId" value = "${product.id }"/>
		<input type = "hidden" name = "productTypeId" value = "${product.type.id }"/>
		<input type = "hidden" name = "interestType" value = "${product.interestType.ordinal() }"/>
		
		<%-- <c:if test = "${product.type.billType != 'SELFHELPLOAN' }"> --%>
        <div class="new_xinxi"><p><font>佣金计算参数 </font></p></div>
          <div class="compute">
          <!--  赚差价-按月-->
          	<c:if test = "${product.interestType.ordinal() eq '0' && product.type.billType =='EARNDIFFERENCE'}">
              <dl>
                  <dt>月息底价：</dt>
                  <dd>
                  <input type="text" class="c_scet" id = "ipt_spread" name = "spread" value="${util:showRateWithoutUnit(product.info.spread)}"><font>%/月</font>
                 </dd>
              </dl>
              <dl>
                  <dt>加价息差：</dt>
                  <dd>
                      <input type="text" class="c_scet c_cpt" id ="ipt_spreadMin" name = "spreadMin" value="${util:showRateWithoutUnit(product.info.spreadMin)}">
                      <font style="width:30px;">—</font>
                      <input type="text" class="c_scet c_cpt" id = "ipt_spreadMax" name = "spreadMax" value="${util:showRateWithoutUnit(product.info.spreadMax)}">
                      <font>%/月</font>
                 </dd>
              </dl>
            </c:if>
            <!-- 赚差价-按日 -->
            <c:if test = "${product.interestType.ordinal() eq '1' && product.type.billType =='EARNDIFFERENCE'}">
              <dl>
                  <dt>日息底价：</dt>
                  <dd>
                  <input type="text" class="c_scet" id = "ipt_spread" name = "spread" value = "${util:showThousandRateWithoutUnit(product.info.spread)}"><font>‰/日</font>
                 </dd>
              </dl>
              <dl>
                  <dt>加价息差：</dt>
                  <dd>
                      <input type="text" class="c_scet c_cpt" id ="ipt_spreadMin" name = "spreadMin" value = "${util:showThousandRateWithoutUnit(product.info.spreadMin)}">
                      <font style="width:30px;">—</font>
                      <input type="text" class="c_scet c_cpt" id = "ipt_spreadMax" name = "spreadMax" value = "${util:showThousandRateWithoutUnit(product.info.spreadMax)}">
                      <font>‰/日</font>
                 </dd>
              </dl>
            </c:if>
            <!-- 赚差价-收益金 -->
            <c:if test = "${product.interestType.ordinal() eq '2' && product.type.billType =='EARNDIFFERENCE'}">
              <dl>
                  <dt>收益金底价：</dt>
                  <dd>
                  <input type="text" class="c_scet" id = "ipt_spread" name = "spread" value="${util:showRateWithoutUnit(product.info.spread)}"><font>%</font>
                 </dd>
              </dl>
              <dl>
                  <dt>加价息差：</dt>
                  <dd>
                      <input type="text" class="c_scet c_cpt" id ="ipt_spreadMin" name = "spreadMin" value="${util:showRateWithoutUnit(product.info.spreadMin)}">
                      <font style="width:30px;">—</font>
                      <input type="text" class="c_scet c_cpt" id = "ipt_spreadMax" name = "spreadMax" value="${util:showRateWithoutUnit(product.info.spreadMax)}">
                      <font>%</font>
                 </dd>
              </dl>
            </c:if>
            <!-- 赚提成-按月 -->
            <c:if test = "${product.interestType.ordinal() eq '0' && (product.type.billType =='EARNCOMMISSION'|| product.type.billType == 'SELFHELPLOAN')}">
              <dl>
                  <dt>月息：</dt>
                  <dd>
                  <input type="text" class="c_scet" id = "ipt_expense" name = "expense" value="${util:showRateWithoutUnit(product.info.expense)}"><font>%/月</font>
                 </dd>
              </dl>
              <c:if test = "${product.type.billType != 'SELFHELPLOAN' }">
	              <dl>
	                  <dt>提成比例：</dt>
	                  <dd>
	                  <input type="text" class="c_scet" id = "ipt_percentageRate" name = "percentageRate" value="${util:showRateWithoutUnit(product.info.percentageRate)}"><font>%</font>
	                 </dd>
	              </dl>
              </c:if>
            </c:if>
            <!-- 赚提成-按日 -->
            <c:if test = "${product.interestType.ordinal() eq '1' && (product.type.billType =='EARNCOMMISSION' ||product.type.billType == 'SELFHELPLOAN')}">
              <dl>
                  <dt>日息：</dt>
                  <dd>
                  <input type="text" class="c_scet" id = "ipt_expense" name = "expense" value = "${util:showThousandRateWithoutUnit(product.info.expense)}"><font>‰/日</font>
                 </dd>
              </dl>
              <c:if test="${product.type.billType != 'SELFHELPLOAN' }">
	              <dl>
	                  <dt>提成比例：</dt>
	                  <dd>
	                  <input type="text" class="c_scet" id = "ipt_percentageRate" name = "percentageRate" value = "${util:showRateWithoutUnit(product.info.percentageRate)}"><font>%</font>
	                 </dd>
	              </dl>
              </c:if>
            </c:if>
            <!-- 赚提成-收益金 -->
            <c:if test = "${product.interestType.ordinal() eq '2' && (product.type.billType =='EARNCOMMISSION' || product.type.billType == 'SELFHELPLOAN')}">
              <dl>
                  <dt>手续费用：</dt>
                  <dd>
                  <input type="text" class="c_scet" id = "ipt_expense" name = "expense" value="${util:showRateWithoutUnit(product.info.expense)}"><font>%</font>
                 </dd>
              </dl>
              <c:if test="${product.type.billType != 'SELFHELPLOAN' }">
	              <dl>
	                  <dt>提成比例：</dt>
	                  <dd>
	                  <input type="text" class="c_scet" id = "ipt_percentageRate" name = "percentageRate" value="${util:showRateWithoutUnit(product.info.percentageRate)}"><font>%</font>
	                 </dd>
	              </dl>
              </c:if>
            </c:if>
            <c:if test = "${product.type.billType.ordinal() != '0' && product.interestType.ordinal() !='3'}">
	           <dl>
		            <dt>前端佣金比例文字：</dt>
		            <dd>
		            <input type="text" class="c_scet" id ="ipt_fontBrokerageDesc" name = "fontBrokerageDesc" value = "${product.info.fontBrokerageDesc }"
		             onKeyDown="gbcount(this.form.fontBrokerageDesc,this.form.used9,8);"  onKeyUp="gbcount(this.form.fontBrokerageDesc,this.form.used9,8);" onblur = "gbcount(this.form.fontBrokerageDesc,this.form.used9,8);">
		             <input class="inputtext2 inputtextn" name="used9"  id="tfontBrokerageDesc" value="0/8" readonly="readonly"> 
		            <samp style = "margin-left:10px">前端佣金数值：</samp>
		            <input type="text" class="c_scet" id = "ipt_fontBrokerageNum" name = "fontBrokerageNum" value = "${product.info.fontBrokerageNum }"
		            onKeyDown="gbcount(this.form.fontBrokerageNum,this.form.used10,5);"  onKeyUp="gbcount(this.form.fontBrokerageNum,this.form.used10,5);" onblur = "gbcount(this.form.fontBrokerageNum,this.form.used10,5);">
		             <input class="inputtext2 inputtextn" name="used10"  id="tfontBrokerageNum" value="0/5" readonly="readonly"> 
		           </dd>
		        </dl>
	        </c:if>
          </div>
        	<!-- 赚提成-特殊模式-->
        <c:if test = "${product.interestType.ordinal() eq '3'}">
         <div class="special">
            <table width="100%" cellpadding="0" cellspacing="0">
            	<tbody>
                    <tr bgcolor="#19aa8d">
                        <td width="165" style="color:#fff;">期限</td>
                        <td width="220" style="color:#fff;">收益金（%）</td>
                        <td width="220" style="color:#fff;">佣金（%）</td>
                    </tr>
                    <tr>
                        <td>6个月</td>
                        <td><input type="text" class="s_cal ipt_algoParam" data-data-str="6个月收益金" id ="ipt_algoParamA" name = "algoParamA" value="${util:showRateWithoutUnit(product.info.algoParamA)}"></td>
                        <td><input type="text" class="s_cal ipt_algoParam" data-data-str="6个月佣金" id ="ipt_algoParamB" name = "algoParamB" value="${util:showRateWithoutUnit(product.info.algoParamB)}"></td>
                    </tr>
                    <tr>
                        <td>12个月</td>
                        <td><input type="text" class="s_cal ipt_algoParam" data-data-str="12个月收益金" id ="ipt_algoParamA" name = "algoParamC" value="${util:showRateWithoutUnit(product.info.algoParamC)}"></td>
                        <td><input type="text" class="s_cal ipt_algoParam" data-data-str="12个月佣金" id ="ipt_algoParamB" name = "algoParamD" value="${util:showRateWithoutUnit(product.info.algoParamD)}"></td>
                    </tr>
                    <tr>
                        <td>24个月</td>
                        <td><input type="text" class="s_cal ipt_algoParam" data-data-str="24个月收益金" id ="ipt_algoParamE" name = "algoParamE" value="${util:showRateWithoutUnit(product.info.algoParamE)}"></td>
                        <td><input type="text" class="s_cal ipt_algoParam" data-data-str="24个月佣金" id ="ipt_algoParamF" name = "algoParamF" value="${util:showRateWithoutUnit(product.info.algoParamF)}"></td>
                    </tr>
                    <tr>
                        <td>36个月</td>
                        <td><input type="text" class="s_cal ipt_algoParam" data-data-str="36个月收益金" id ="ipt_algoParamG" name = "algoParamG" value="${util:showRateWithoutUnit(product.info.algoParamG)}"></td>
                        <td><input type="text" class="s_cal ipt_algoParam" data-data-str="36个月佣金" id ="ipt_algoParamH" name = "algoParamH" value="${util:showRateWithoutUnit(product.info.algoParamH)}"></td>
                    </tr>
                </tbody>
            </table>
            <c:if test = "${product.type.billType.ordinal() != '0' }">
	           <dl>
		            <dt>前端佣金比例文字：</dt>
		            <dd>
		            <input type="text" class="s_dt" id ="ipt_fontBrokerageDesc" name = "fontBrokerageDesc" value = "${product.info.fontBrokerageDesc }"
		             onKeyDown="gbcount(this.form.fontBrokerageDesc,this.form.used9,8);"  onKeyUp="gbcount(this.form.fontBrokerageDesc,this.form.used9,8);" onblur = "gbcount(this.form.fontBrokerageDesc,this.form.used9,8);">
		             <input class="inputtext2 inputtextn" name="used9"  id="tfontBrokerageDesc" value="0/8" readonly="readonly"> 
		            <samp style = "margin-left:10px">前端佣金数值：</samp>
		            <input type="text" class="s_dt" id = "ipt_fontBrokerageNum" name = "fontBrokerageNum" value = "${product.info.fontBrokerageNum }"
		            onKeyDown="gbcount(this.form.fontBrokerageNum,this.form.used10,5);"  onKeyUp="gbcount(this.form.fontBrokerageNum,this.form.used10,5);" onblur = "gbcount(this.form.fontBrokerageNum,this.form.used10,5);">
		             <input class="inputtext2 inputtextn" name="used10"  id="tfontBrokerageNum" value="0/5" readonly="readonly"> 
		           </dd>
		        </dl>
	        </c:if>
            </div>
           </c:if>
           
          <div class="new_xinxi n_top"><p><font>佣金设置</font></p></div>
              <div class="compute">
              	<c:if test = "${productType.billType.ordinal() == '2' }">
                  <dl>
                      <dt>会员佣金比例：</dt>
                      <dd>100%</dd>
                  </dl>
                </c:if>
                  <dl>
                      <dt>师父佣金比例：</dt>
                      <dd>
                      <input type="text" class="c_scet" id = "ipt_fatherRate" name = "fatherRate" value="${util:showRateWithoutUnit(product.info.fatherRate)}"><font>%</font>
                     </dd>
              	</dl>
                  <dl>
                      <dt>商家佣金比例：</dt>
                      <dd>
                      <input type="text" class="c_scet" id = "ipt_sellerRate" name = "sellerRate" value="${util:showRateWithoutUnit(product.info.sellerRate)}"><font>%</font>
                     </dd>
              	</dl>
                  <dl>
                      <dt>业务员佣金比例：</dt>
                      <dd>
                      <input type="text" class="c_scet" id = "ipt_salesmanRate" name = "salesmanRate" value="${util:showRateWithoutUnit(product.info.salesmanRate)}"><font>%</font>
                     </dd>
              	</dl>
              	<c:if test = "${product.type.billType.ordinal() == '1'|| product.type.billType.ordinal() == '2' }">
                  <dl>
                  	  <c:if test = "${product.type.billType.ordinal() == '1'}">
                      	<dt>佣金比例：</dt>
                      </c:if>
                      <c:if test = "${product.type.billType.ordinal() == '2'}">
                      	<dt>会员收佣说明：</dt>
                      </c:if>
                      <dd>
                      <h6>
                      <input type="text" class="c_text c_one" id = "ipt_userRateDesc" name = "userRateDesc" value="${product.info.userRateDesc}"
                      onKeyDown="gbcount(this.form.userRateDesc,this.form.used6,25);"  onKeyUp="gbcount(this.form.userRateDesc,this.form.used6,25);"onblur = "gbcount(this.form.userRateDesc,this.form.used6,25);">
                      <input class="inputtext2 inputtextn" name="used6"  id="tuserRateDesc" value="0/25" readonly="readonly"> 
                      </h6>
                      <c:if test = "${product.type.billType.ordinal() == '1'}">
                      <h6>
                      <input type="text" class="c_text c_one" id = "ipt_fatherRateDesc" name = "fatherRateDesc" value="${product.info.fatherRateDesc}"
                      onKeyDown="gbcount(this.form.fatherRateDesc,this.form.used7,25);"  onKeyUp="gbcount(this.form.fatherRateDesc,this.form.used7,25);"onblur= "gbcount(this.form.fatherRateDesc,this.form.used7,25);">
                      <input class="inputtext2 inputtextn" name="used7"  id="tfatherRateDesc" value="0/25" readonly="readonly"> 
                      </h6>
                      </c:if>
                     </dd>
              	</dl>
              	</c:if>
              	<%-- <c:if test = "${product.type.billType.ordinal() == '2' }">
	                <dl>
                      <dt>会员收佣说明：</dt>
                      <dd>
                      <h6>
	                      <input type="text" class="c_text c_one" id = "ipt_userratedesc" name = "userRateDesc" value="${product.info.userRateDesc}"
	                      onKeyDown="gbcount(this.form.userRateDesc,this.form.used6,22);"  onKeyUp="gbcount(this.form.userRateDesc,this.form.used6,22);">
	                      <input class="inputtext2 inputtextn" name="used6"  id="tuserRateDesc" value="0/22" readonly="readonly"> 
                      </h6>
                     </dd>
	              	</dl>
	             </c:if> --%>
              </div>
             <%--  </c:if> --%>
              <div class="new_xinxi n_top"><p><font>产品信息</font></p></div>
              <div class="compute">
              <div id="div_productDesc" style="display: none;">${util:htmlUnescape(product.info.productDesc)}</div>
              <div id="div_requirment" style="display: none;">${util:htmlUnescape(product.info.requirment)}</div>
                  <dl>
                      <dt>产品名称：</dt>
                      <dd><input type="text" class="c_text c_one" id = "ipt_productName" name = "productName" value = "${product.info.productName }"
                      onKeyDown="gbcount(this.form.productName,this.form.used1,22);"  onKeyUp="gbcount(this.form.productName,this.form.used1,22);" onblur="gbcount(this.form.productName,this.form.used1,22);">
                      <input class="inputtext2 inputtextn" name="used1"  id="tproductName" value="0/22" readonly="readonly"> 
                      </dd>
                  </dl>
                  <dl>
                      <dt>贷款利率：</dt>
                      <c:if test = "${product.interestType.ordinal() != '1'}">
                      <dd>
                          <input type="text" class="c_scet c_cpt" id = "ipt_loanRate" name = "loanRate" value = "${util:showRateWithoutUnit(product.info.loanRate) }">
                          <font>%/月</font>
                          <samp>贷款页面利率：</samp>
                          <input type="text" class="c_scet c_cpt" id = "ipt_loanMinRate" name = "loanMinRate" value = "${product.info.loanMinRate}">
                          <font style="width:30px;">—</font>
                          <input type="text" class="c_scet c_cpt" id = "ipt_loanMaxRate" name = "loanMaxRate" value = "${product.info.loanMaxRate }">
                          <font>%/月</font>
                     </dd>
                     </c:if>
                     <c:if test = "${product.interestType.ordinal() eq '1'}">
                      <dd>
                          <input type="text" class="c_scet c_cpt" id = "ipt_loanRate" name = "loanRate" value = "${util:showThousandRateWithoutUnit(product.info.loanRate) }">
                          <font>‰/日</font>
                          <samp>贷款页面利率：</samp>
                          <input type="text" class="c_scet c_cpt" id = "ipt_loanMinRate" name = "loanMinRate" value = "${product.info.loanMinRate }">
                          <font style="width:30px;">—</font>
                          <input type="text" class="c_scet c_cpt" id = "ipt_loanMaxRate" name = "loanMaxRate" value = "${product.info.loanMaxRate }">
                          <font>‰/日</font>
                     </dd>
                     </c:if>
                     <%-- <!-- 收益金 -->
                     <c:if test = "${product.interestType.ordinal() eq '2' || product.interestType.ordinal() eq '3'}">
                         <dd>
                              <input type="text" class="c_scet" id = "ipt_loanRate" name = "loanRate" value = "${product.info.loanRate }">
                              <font>%/月</font>   
                         </dd>
                     </c:if> --%>
              	</dl>
              	<!-- 贷款金额： -->
                  <dl>
                      <dt>贷款金额：</dt>
                      <dd>
                      	<input type="text" class="c_scet c_cpt" id = "ipt_loanMinAmount" name = "loanMinAmount" value="${util:showTenThousandPrice(product.info.loanMinAmount)}">
                          <font style="width:30px;">—</font>
                          <input type="text" class="c_scet c_cpt" id = "ipt_loanMaxAmount" name = "loanMaxAmount" value="${util:showTenThousandPrice(product.info.loanMaxAmount)}">
                          <font>万元</font>
                     </dd>
              	</dl>
              	<!-- 贷款期限 -->
              	<c:if test ="${product.interestType.ordinal() == '3' }">
              		<dl>
                        <dt>贷款期限：</dt>
                        <dd>
                        	<input type ="hidden" id = "ipt_loanMinTime" value = "6" name = "loanMinTime" value = "${product.info.loanMinTime }"/>
                        	<input type = "hidden" id = "ipt_loanMaxTime" value = "36" name = "loanMaxTime" value = "${product.info.loanMaxTime }"/>
                         	<font>6</font>
                            <font style="width:30px;">—</font>
                            <font>36</font>
                            <font>个月</font>
                        </dd>
                    	</dl>
                 </c:if>
              	<c:if test = "${product.interestType.ordinal() == '0' || product.interestType.ordinal() == '2'}">
                  <dl>
                      <dt>贷款期限：</dt>
                      <dd>
                      	<input type="text" class="c_scet c_cpt" id = "ipt_loanMinTime" name = "loanMinTime" value = "${product.info.loanMinTime }">
                          <font style="width:30px;">—</font>
                          <input type="text" class="c_scet c_cpt" id = "ipt_loanMaxTime" name = "loanMaxTime" value = "${product.info.loanMaxTime }">
                          <font>个月</font>
                     </dd>
              	</dl>
              	</c:if>
              	<c:if test = "${product.interestType.ordinal() == '1' }">
                  <dl>
                      <dt>贷款期限：</dt>
                      <dd>
                      	<input type="text" class="c_scet c_cpt" id = "ipt_loanMinTime" name = "loanMinTime" value = "${product.info.loanMinTime }">
                          <font style="width:30px;">—</font>
                          <input type="text" class="c_scet c_cpt" id = "ipt_loanMaxTime" name = "loanMaxTime" value = "${product.info.loanMaxTime }">
                          <font>日</font>
                     </dd>
              	</dl>
              	</c:if>
                  <dl>
                      <dt>还款方式：</dt>
                      <dd><input type="text" class="c_scet" id="ipt_repayment" name = "repayment" value = "${product.info.repayment }"
                      onKeyDown="gbcount(this.form.repayment,this.form.used2,9);"  onKeyUp="gbcount(this.form.repayment,this.form.used2,9);" onblur = "gbcount(this.form.repayment,this.form.used2,9);">
                       <input class="inputtext2 inputtextn" name="used2"  id="trepayment" value="0/9" readonly="readonly"> 
                       <c:if test="${product.type.billType.ordinal() != '0' }"> 
	                       <samp style = "margin-left:10px">预计佣金发放方式：</samp>
	                      <input type="text" class="c_scet" name = "brokerageSendDesc"  id = "ipt_brokerageSendDesc" value = "${ product.info.brokerageSendDesc}" 
	                      onKeyDown="gbcount(this.form.brokerageSendDesc,this.form.used8,9);"  onKeyUp="gbcount(this.form.brokerageSendDesc,this.form.used8,9);" onblur = "gbcount(this.form.brokerageSendDesc,this.form.used8,9);">
	                      <input class="inputtext2 inputtextn" name="used8"  id="tbrokerageSendDesc" value="0/9" readonly="readonly"> 
                      </c:if>
              	</dl>
              	
                  <dl>
                      <dt>产品信息：</dt>
                      <dd>
<%--                       <textarea  class="c_text" id = "ipt_productDesc" name = "productDesc" 
                      onKeyDown="gbcount(this.form.productDesc,this.form.used3,350);"  onKeyUp="gbcount(this.form.productDesc,this.form.used3,350);">${product.info.productDesc }</textarea>
                      <input class="inputtext2 inputtextn" name="used3"  id="tproductDesc" value="0/350" readonly="readonly">  --%>
                      	 <script id="ipt_productDesc" name="productDesc" type="text/plain"  style="width:300px;height:115px;"></script>
                      </dd>
              	</dl>
                  <dl>
                      <dt>
                          <span>申请条件：</span>
                          <samp id = "btn_autoRequirement">自动生成</samp>
                      </dt>
                      <dd>
                      	<script id="ipt_requirment" name="requirment" type="text/plain"  style="width:300px;height:115px;"></script>
                      </dd>
              	</dl>
                  <dl>
                      <dt>
                          <span>材料准备：</span>
                          <samp id ="btn_autoMaterials">自动生成</samp>
                      </dt>
                      <dd>
                      <textarea  class="c_text m_height" id = "ipt_materials" name = "materials" 
                      onKeyDown="gbcount(this.form.materials,this.form.used5,100);"  onKeyUp="gbcount(this.form.materials,this.form.used5,100);" onblur= "gbcount(this.form.materials,this.form.used5,100);">${product.info.materials }</textarea>
                      <input class="inputtext2 inputtextn" name="used5"  id="tmaterials" value="0/100" readonly="readonly"> 
                      </dd>
              	</dl>
                  <dl>
                      <dt>推荐首页：</dt>
                      <dd>
                          <span><input type="radio" name = "isDisplay"  id = "is_display"  value = "1" class="c_rid" <c:if test = "${product.isDisplay.ordinal() eq '1' }"> checked :checked</c:if>>是</span>
                          <span><input type="radio" name = "isDisplay" id = "no_display"  value = "0" class="c_rid" <c:if test = "${product.isDisplay.ordinal() eq '0' }"> checked :checked</c:if>>否</span>
                      </dd>
              	</dl>
                <dl>
                      <dt>产品排序：</dt>
                      <dd><input type="text" class="c_scet" id = "ipt_sortid" name = "sortid" value = "${product.sortid }">&nbsp;&nbsp;排序值越大，显示越靠后</</dd>
              	</dl>
              	
              	<dl id = "div_isdisplay" <c:if test = "${product.isDisplay.ordinal() eq '0' }"> class = "hidden"</c:if>>
                      <dt>首页排序：</dt>
                      <dd><input type="text" class="c_scet" id = "ipt_indexSortid" name = "indexSortid" value = "${product.indexSortid }" >&nbsp;&nbsp;排序值越大，显示越靠后</</dd>
              	</dl>
                  <p>
                      <a href="javascript:void(0)" id = "btn_back" class="cut">上一步</a>
                      <a href="javascript:void(0)" id = "btn_save">保存</a>
                  </p>
               </div>
            </form>
      </div> 
</body>
</html>