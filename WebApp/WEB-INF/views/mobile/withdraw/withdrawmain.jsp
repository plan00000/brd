<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="util" uri="functions"%>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html >
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript" src="${ctx }/static/js/common.js"></script>
<script type="text/javascript" src="${ctx }/static/js/md5-min.js"></script>
<link href="${ctx}/static/brd/css/plugins/sweetalert/sweetalert.css" rel="stylesheet">
<script src="${ctx}/static/js/sweetalert/sweetalert.min.js"  ></script>
<title>取现</title>
<script>
var getcode;
	document.title="提现"
	$(function(){
			
			var listSize = '${listSize}';
			if(listSize==0) {
				swal({
					title:"您尚未添加银行卡,是否去添加？",
					type:"warning",
					showCancelButton:true,
					confirmButtonColor:"#DD6B55",
					confirmButtonText: "是",
					cancelButtonText: "取消",
					closeOnConfirm:false					
				},
				 function(){
					location.href = "${ctx}/weixin/bankinfo/toAddBankinfo/-1;JSESSIONID=<%=request.getSession().getId()%>";
				}
				)	
			}
			
		
			
			$("#all").click(function(){
				var money = '${money}';
				if(money=='0.00' || money=='0'){
					swal("没有可提现的金额");
				}else{
					$("#money").val(money);
				}
			});
		
		
			 $("#sendCode").click(function(){
					var count = 60;
					var timedown;
					 var $this = $(this);
					var bankinfoId =$("#bankId").val();
					if(bankinfoId==null|| bankinfoId=='undefind' || bankinfoId.trim().length==0 ){
						swal("请先添加银行卡");
						return ;
					}					
					$.get("${ctx}/weixin/withdraw/getPhoneAuthcode",{auth:1},function(data){
						if(data.code==0){
							$this.data("enable", false);
							getcode = $this;
							showtime=$("#sendCode");
							time = 60;
							getcode.css("background-color","#f4f4f4");
							window.setTimeout(countdown,1000);
						}else{
							swal(data.mes);
						}
					
					});

					
					
					
				});
			
			 $("#closeChoose").click(function(){
						 
				 $(".immediately").css("display","none")
			 });
			
			 
			$("#withdraw").click(function(){
				var code = $("#code").val().trim();
				var password=hex_md5($("#withdrawPassword").val()).trim();
				var money = $("#money").val().trim();
				var realmoney = '${money}';
				
				if(code.length==0){
					swal("请填写验证码");
					return ;
				}
				if($("#withdrawPassword").val().length==0){
					swal("请输入提现密码");
					return;
				}
				if(code.length!=6){
					swal("请输入正确的验证码");
					return ;
				}
				if(money==0|| money==0.00){
					swal("请输入提现金额");
					return ;
				}
				
				var rep =/^\d{1,10}(\.\d{1,2})?$/gi;
				if(!rep.test(money)){
					swal("请输入正确金额,只能到小数点后两位");
					return ;
				}
				if(money<1){
					swal("最小提现金额为1元")
					return ;
				}
				var bankinfoId =$("#bankId").val();
				if(bankinfoId==null|| bankinfoId=='undefind' || bankinfoId.trim().length==0 ){
					swal("请先添加银行卡");
					return ;
				}		
				
				var dto={
					password:password,
					code:code,
					bankinfoId:bankinfoId,
					money:money
				}	 		
				
				$.post("${ctx}/weixin/withdraw",dto,function(data){
					if(data.code==0){
						swal({
							title:"提现成功,请等待到账",
							timer:2000,
							showConfirmButton:false
						});
						setTimeout(function(){
							location.href = "${ctx}/weixin/withdraw/detail;JSESSIONID=<%=request.getSession().getId()%>";
						},2000);
						
					}else{
						swal(data.mes);
					}
				});
				
				
				
				
			});	 	
	});
	function countdown() {
		time--;
		if (time <= 0) {
			getcode.data("enable", true);
			showtime.text("获取验证码");
			getcode.css("background", "#fb8044");
			getcode.css("color", "#fff");
		} else {
			showtime.text(time + "秒后可重发");
			getcode.css("color", "#a5411f");
			window.setTimeout(countdown, 1000);
		}

	}
	function showChoose(){
		$(".immediately").css("display","block")
	}
	function choose(id,bankname,bankaccount) {
		$(".immediately").css("display","none")
		var immed = $(".immed_01");	
		var imgHtml = "";
		if(bankname=='中国建设银行'){
			imgHtml = "<p><img src='${ctx }/static/brd-mobile/images/jh.png'>"
		}else if (bankname=='中国工商银行'){
			imgHtml = "<p><img src='${ctx }/static/brd-mobile/images/gsyh.png'>"
		}else if (bankname=='中国农业银行'){
			imgHtml = "<p><img src='${ctx }/static/brd-mobile/images/ry.png'> "
		}else if (bankname=='中国交通银行'){
			imgHtml = "<p><img src='${ctx }/static/brd-mobile/images/jtyh.png'> "
		}else if (bankname=='中国兴业银行'){
			imgHtml = "<p><img src='${ctx }/static/brd-mobile/images/xy.png'> "
		}else if (bankname=='中信银行'){
			imgHtml = "<p><img src='${ctx }/static/brd-mobile/images/zx.png'> "
		}else if (bankname=='中国银行'){
			imgHtml = "<p><img src='${ctx }/static/brd-mobile/images/zg.png'> "
		}else if (bankname=='中国招商银行'){
			imgHtml = "<p><img src='${ctx }/static/brd-mobile/images/zs.png'> "
		}else if (bankname=='中国邮政储蓄银行'){
			imgHtml = "<p><img src='${ctx }/static/brd-mobile/images/yz.png'> "
		}else if (bankname=='中国民生银行'){ 
			imgHtml = "<p><img src='${ctx }/static/brd-mobile/images/ms.png'> "
		}else if (bankname=='中国光大银行'){
			imgHtml = "<p><img src='${ctx }/static/brd-mobile/images/gd.png'> "
		}else if (bankname=='中国华夏银行'){
			imgHtml = "<p><img src='${ctx }/static/brd-mobile/images/hx.png'> "
		}else if (bankname=='中国广发银行'){
			imgHtml = "<p><img src='${ctx }/static/brd-mobile/images/gf.png'> "
		}
         var html = imgHtml + bankname+"</p><span>尾号:"+bankaccount+"<img onclick='showChoose()' src='${ctx }/static/brd-mobile/images/jtt.png'></span>";
         immed.html(html);
		$("#bankId").val(id);
		
	}
</script>
</head>
<body>	
		<div class="content">
			<input type="hidden" id="bankId" value="${list[0].id }"    >
	        <div class="immed">
	            <div onclick="showChoose()" class="immed_01">
	                 <p>
	                 <c:choose>
	           				<c:when test="${list[0].bankname eq '中国建设银行' }" >
	           				 <img src="${ctx }/static/brd-mobile/images/jh.png"> 
	           				</c:when>
	           				<c:when test="${list[0].bankname eq '中国工商银行' }" >
	           				 <img src="${ctx }/static/brd-mobile/images/gsyh.png"> 
	           				</c:when>
	           				<c:when test="${list[0].bankname eq '中国农业银行' }" >
	           				 <img src="${ctx }/static/brd-mobile/images/ry.png"> 
	           				</c:when>
	           				<c:when test="${list[0].bankname eq '中国交通银行' }" >
	           				 <img src="${ctx }/static/brd-mobile/images/jtyh.png"> 
	           				</c:when>
	           				<c:when test="${list[0].bankname eq '中国兴业银行' }" >
	           				 <img src="${ctx }/static/brd-mobile/images/xy.png"> 
	           				</c:when>
	           				<c:when test="${list[0].bankname eq '中信银行' }" >
	           				 <img src="${ctx }/static/brd-mobile/images/zx.png"> 
	           				</c:when>
	           				<c:when test="${list[0].bankname eq '中国银行' }" >
	           				 <img src="${ctx }/static/brd-mobile/images/zg.png"> 
	           				</c:when>
	           				<c:when test="${list[0].bankname eq '中国招商银行' }" >
	           				 <img src="${ctx }/static/brd-mobile/images/zs.png"> 
	           				</c:when>
	           				<c:when test="${list[0].bankname eq '中国邮政储蓄银行' }" >
	           				 <img src="${ctx }/static/brd-mobile/images/yz.png"> 
	           				</c:when>
	           				<c:when test="${list[0].bankname eq '中国民生银行' }" >
	           				 <img src="${ctx }/static/brd-mobile/images/ms.png"> 
	           				</c:when>
	           				<c:when test="${list[0].bankname eq '中国光大银行' }" >
	           				 <img src="${ctx }/static/brd-mobile/images/gd.png"> 
	           				</c:when>
	           				<c:when test="${list[0].bankname eq '中国华夏银行' }" >
	           				 <img src="${ctx }/static/brd-mobile/images/hx.png"> 
	           				</c:when>
	           				<c:when test="${list[0].bankname eq '中国广发银行' }" >
	           				 <img src="${ctx }/static/brd-mobile/images/gf.png"> 
	           				</c:when>
           				</c:choose>
	                ${list[0].bankname}</p>
	                 <span >尾号:${list[0].bankaccount}<img onclick="showChoose()" src="${ctx }/static/brd-mobile/images/jtt.png"></span>
	            	<!-- <p><img src="images/jh1.png">招商银行</p>
                	<span>尾号5293<img src="images/jtt.png"></span> -->
	            	
	            </div>
	        </div>
	        <div class="immed_02">
            <dl>
                <dt>账户余额：</dt>
                <dd>${money}元<font id="all" >全部提现</font></dd>
            </dl>
         </div>
         <div class="immed_03">
        	 <dl>
                 <dt>提现</dt>
                 <dd>
                     <input type="text" class="i_in" id="money" placeholder="最低为1" >
                     <span>元</span>
                 </dd>
             </dl>
             <dl>
                 <dt>验证码</dt>
                 <dd>
                     <input type="text" id="code" maxlength="6" class="i_in1" placeholder="6位数字" >
                	 <button type="button" value="获取验证码" class="i_in2" id="sendCode" style="-webkit-appearance:none;" >获取验证码</button>
                 </dd>
             </dl>
             <dl>
                 <dt>提现密码</dt>
                 <dd>
                     <input type="password" class="i_in3" id="withdrawPassword" placeholder="取现密码" >
                 </dd>
             </dl>
             <p id="withdraw">立即提现</p>
             <samp>每日16:00前申请提现，当日到账，16:00后申请提现次日到账</samp>
           </div> 
	        <div class="immediately"  style="display:none">
	        	<div class="imme">
	            	 <p id="closeChoose" >关闭</p>
	                <c:forEach  var ="list" items="${list }" >
	                	<dl onclick="choose('${list.id}','${list.bankname }','${list.bankaccount}')">
	                		<dt>${list.bankname }</dt>
	                		<dd>尾号:${list.bankaccount}</dd>
	                	</dl>
	                </c:forEach>
	            </div>
         </div> 
		</div>
</body>
</html>