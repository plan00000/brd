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
<title>贷款</title>
<script type="text/javascript" src="${ctx }/static/js/common.js"></script>
<script src="${ctx}/static/js/input-number-change.js"></script>
<script type="text/javascript">
var time;
var getcode;
var showtime;
var loanMinAmount = ${product.info.loanMinAmount};
var loanMaxAmount = ${product.info.loanMaxAmount};
loanMinAmount = loanMinAmount/10000;
loanMaxAmount = loanMaxAmount/10000;
var loanMinTime = ${product.info.loanMinTime};
var loanMaxTime = ${product.info.loanMaxTime};
$(function(){
	var ipt_name = $("#ipt_name").val();
	var a = ipt_name.length;
	$("#tname").val(a+"/5");
	
	var ipt_remark = $("#ipt_remark").val();
	var b = ipt_remark.length;
	$("#tremark").val(b+"/140");
	
	//点击获取手机验证码
	$("#ipt_get_phonecode").click(function(){
		var $this = $(this);
		if ($this.data("enable")==false){
			return ;
		}else{
			$this.data("enable", false);
		}
		if (!checkMobileno()){
			$this.data("enable", true);
			return ;
		}
		var phone = $("#ipt_telephone").val();
		var data = {};
		data.phone= phone;
		showLoading();
			$.post("${ctx}/pc/product/getPhoneAuthcode/addOrderform;JSESSIONID=<%=request.getSession().getId()%>",data,function(res){
				if (res.code == 1){
					//获取手机验证码成功
					$this.data("enable", false);
					getcode = $this;
					showtime=$("#showtime");
					time = 60;
					getcode.css("background-color","#f4f4f4");
					window.setTimeout(countdown,1000);
				}else{
					alert(res.mes);
				}
			});
			$this.data("enable", true);
			hideLoading();
	});
	
	$("#btn_submit").click(function(){
		if(!check()){
			return ;
		} 
		showLoading();
		$.post('${ctx}/pc/product/addOrderform;JSESSIONID=<%=request.getSession().getId()%>',$("#submit_form").serialize(),function(res){
			if(res.code == 1){
				$(".orders").removeClass('hidden');
			}else{
				alert(res.mes);
			}
		})
		hideLoading();
	})
	
	//弹出框
	$(".orders_mon").click(function(){
		$(".orders").addClass('hidden');
		location.href ="${ctx}/pc/product/list";
	})
	
	$("#close").click(function(){
		$("#info1").addClass('hidden');
	})
});
function countdown() {
	time--;
	if (time <= 0) {
		getcode.data("enable", true);
		showtime.text("获取短信验证码");
		getcode.css("background", "#fb8044");
		getcode.css("color", "#fff");
	} else {
		showtime.text(time + "秒后可重发");
		window.setTimeout(countdown, 1000);
	}

}
function check(){
	if(!checkName()){
		return false;
	}
	if(!checkIdcard()){
		return false;
	} 
	if(!checkMobileno()){
		return false;
	}
	if(!checkPhoneAuthcode()){
		return false;
	}
	if(!checkCity()){
		return false;
	}
	if(!checkMoney()){
		return false;
	}
	if(!checkLoanTime()){
		return false;
	}
	
	return true;
}
function checkName(){
	var name = $("#ipt_name").val().trim();
	if(name.length == 0){
		alert("请输入姓名");
		return false;
	}
	if(name.length >10){
		alert("姓名不能超过10个字符");
		return false;
	}
	return true;
}
function checkIdcard(){
	var idcard = $("#ipt_idcard").val().trim();
	if(idcard.length == 0){
		alert("请输入身份证后六位");
		return false;
	}
	if(idcard.length != 6){
		alert("请输入正确身份证后6位");
		return false;
	}
	return true;
} 
function checkMoney(){
	var money = $("#ipt_money").val().trim();
	if(money.length == 0){
		alert("请输入贷款金额");
		return false;
	}
	if(isNaN(money)){
		alert("贷款金额只能是数字");
		return false;
	}
	if(money<loanMinAmount){
		alert("请输入"+loanMinAmount+"-"+ loanMaxAmount+"的金额");
		return false;
	}
	if(money>loanMaxAmount){
		alert("请输入"+loanMinAmount+"-"+ loanMaxAmount+"的金额");
		return false;
	}
	if(!CommValidate.isPositiveInteger(money)){
		alert("贷款金额为正整数");
		return false;
	}
	return true;
}
function checkPhoneAuthcode(){
	var phoneAuthcode = $("#ipt_phoneAuthcode").val().trim();
	if(phoneAuthcode.length == 0){
		alert("请输入短信验证码");
		return false;
	}
	return true;
}
function checkCity(){
	var city = $("#ipt_city").val().trim();
	if(city.length == 0){
		alert("请选择城市");
		return false;
	}
	return true;
}
function checkLoanTime(){
	var loanTime = $("#ipt_loanTime").val().trim();
	if(loanTime.length == 0){
		alert("请输入贷款期限");
		return false;
	}
	if(isNaN(loanTime)){
		alert("贷款期限只能是数字");
		return false;
	}
	if(!CommValidate.isPositiveInteger(loanTime)){
		alert("贷款期限为正整数");
		return false;
	}
	if(loanTime < loanMinTime){
		alert("请输入"+loanMinTime +"-"+ loanMaxTime+"的贷款期限");
		return false;
	}
	if(loanTime > loanMaxTime){
		alert("请输入"+loanMinTime +"-"+ loanMaxTime+"的贷款期限");
		return false;
	}
	return true;
}
function checkMobileno(){
	var $mobileno = $("#ipt_telephone");
	var mobileno = $mobileno.val();
	if (mobileno.trim().length == 0){
		alert("请输入手机号码");
		return false;
	}
	if (mobileno.length != 11){
		alert("手机号码必须为11位");
		return false;
	}
	if (!CommValidate.checkPhone(mobileno)){
		alert("手机号码格式错误");
		return false
	}
	return true;
}
</script>
</head>
<body>
	<div class="war">
            <div class="title">${product.info.productName }</div>
            <div class="product">
                <div class="product_form">
                	<samp><font>产品信息</font></samp>
                    <span>
		          ${util:htmlUnescape(product.info.productDesc)}
		          </span>
                </div>
                <div class="product_form">
                	<samp><font>申请条件</font></samp>
                    <!-- <span>1.18—65周岁大陆公民<br/>2.个人或者配偶名下拥有有产权物业<br/>3.抵押物限制：住宅、店面、写字楼，房龄30年以内<br/>4.已婚人士必须夫妻二人共同借款<br/>5.抵押率不超过评估价50%</span> -->
                    <span>${util:htmlUnescape(product.info.requirment)}</span>
                </div>
                <div class="product_form bor">
                	<samp><font>材料准备</font></samp>
                    <!-- <span>借款人夫妻双方身份证、户口本、结婚证、信用报告、抵押物产权证复印件、其他资产证明(如有）</span> -->
                    <span>${product.info.materials }</span>
                </div>
             <!--product-->
            </div>
            <div class="cation" style="height: 800px;">
                <div class="cation_c">
                <form id = "submit_form">
                	<input type = "hidden" name = "productId" value = "${product.id }"/>
                    <dl>
                        <dt>姓名：</dt>
                        <dd><input type="text" id = "ipt_name" name="name" class="b_on" 
                            onKeyDown="gbcount(this.form.name,this.form.used1,5);" 
				 			onKeyUp="gbcount(this.form.name,this.form.used1,5);" >
                       <span><input class="inputtext inputtextn" name="used1" id="tname"  readonly="readonly"> </span> 
                        </dd>
                    </dl>
                    <input type = "hidden" name="idcard" id = "ipt_idcard" value = "000000">
                    <!-- <dl>
                        <dt>身份证号：</dt>
                        <dd><input type="text" placeholder="身份证后六位" name="idcard" id = "ipt_idcard" class="box"></dd>
                    </dl> -->
                    <dl>
                        <dt>手机号：</dt>
                        <dd><input type="text"  name = "telephone" id = "ipt_telephone" class="box"></dd>
                    </dl>
                    <dl>
                        <dt>验证码：</dt>
                        <dd>
	                        <input type="text"  name = "phoneAuthcode" id = "ipt_phoneAuthcode" class="box7">
	                        <button type="button" class="box8" id = "ipt_get_phonecode"><div id = "showtime" style = "color:#a5411f">获取验证码</div></button>
                        </dd> 
                    </dl>
                    <dl>
                        <dt>所在地：</dt>
                        <dd >
                        	<input type = "hidden" name = "province" value = "福建省"/>
                            <font>福建省</font>
                            <select name="city" class="box1" id = "ipt_city">
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
                    <dl>
                        <dt>贷款金额：</dt>
                           <dd>
                        	 <input type="text"  name = "money" placeholder="${util:showTenThousandPrice(product.info.loanMinAmount)}-${util:showTenThousandPrice(product.info.loanMaxAmount)}"id = "ipt_money" class="box4" >
                             <span>万元</span>
                        	</dd>
                    </dl>
                        <dl>
                            <dt>贷款期限：</dt>
                             <dd>
                             	<c:choose>
                             		<c:when test="${product.info.loanMinTime == product.info.loanMaxTime }">
                             			<input type="text"  name = "loanTime" id = "ipt_loanTime" value = "${product.info.loanMinTime }" class="box4" readonly="true">
                             		</c:when>
                             		<c:otherwise>
                             			<input type="text"  placeholder="${product.info.loanMinTime }-${product.info.loanMaxTime }" name = "loanTime" id = "ipt_loanTime" class="box4">
                             		</c:otherwise>
                             	</c:choose>
                             	
                             	<c:if test = "${product.interestType.ordinal() eq '0' }">
                             		<span>月</span>
                             	</c:if>
                             	<c:if test = "${product.interestType.ordinal() eq '1' }">
                             		<span>日</span>
                             	</c:if>
                             </dd>
                        </dl>
                        <dl class="box6">
                            <dt>备注：</dt>
                            <dd class="box6">
                          <textarea  class="box5" id="ipt_remark" name="remark"
                        	onKeyDown="gbcount(this.form.remark,this.form.used2,140);" 
				 			onKeyUp="gbcount(this.form.remark,this.form.used2,140);" ></textarea>
							<input class="inputtext inputtextn c_shu" name="used2" id="tremark" value="0/140" readonly="readonly">
                            </dd>
                        </dl>
                        <p>由于借款人不确定性，本平台可能在线下进行产品匹配，以平台最终确认为准。</p>
                        <samp><button type="button" id = "btn_submit">提交申请</button></samp>
                   </form>
                 </div>
             <!--cation-->
            </div>
            <jsp:include page="../footer.jsp"/>
         <!--war-->
        </div>
        <div id="info1" class="orders hidden">
            <div class="orders_butt" >
           		 <p>提示</p><font id="close"></font>
                 <dl style="width: 270px;" >
                     <dt><img src="${ctx }/static/brd-front/images/success.png"></dt>
                     <dd style="width: 190px;" >
                         <span style="width: 190px;" >订单已提交！</span>
                         <span style="width: 190px;" >稍后工作人员将和您确认。</span>
                     </dd>
                 </dl>
                 <div class="orders_mon"><samp>确定</samp></div>
            </div>
        
        </div>  
</body>
</html>