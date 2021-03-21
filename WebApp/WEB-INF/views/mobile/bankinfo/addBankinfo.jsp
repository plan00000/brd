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
<script src="${ctx}/static/js/PCASClass.js"  ></script>
<link href="${ctx}/static/brd/css/plugins/sweetalert/sweetalert.css" rel="stylesheet">
<script src="${ctx}/static/js/sweetalert/sweetalert.min.js"  ></script>
<title>添加银行卡</title>
<script>	
	document.title="添加银行卡"
	$(function(){
		
		$("#addBankinfo").click(function(){
			var bankname = $('#ipt_select option:selected').val();
			var province = $("#province").val();
			var city = $("#city").val();
			var name= $("#name").val();
			var bankaccount = $("#bankaccount").val();
			if(bankaccount.length==0){
				swal("银行卡帐号不能为空");
				return ;
			}
			console.log(province+"---"+province.trim.length);
			var re = /^[0-9]+$/gi
			if(!re.test(bankaccount)){
				swal("银行卡号只能为数字");
				return;
			}
			if(city.length==0){
				swal("请选择开卡城市")
				return ;
			}

			
			var dto={
					name:name,
					bankname:bankname,
					bankaccount:bankaccount,
					province:province,
					city:city
			}
			
			var order = '${order}';
			$.post("${ctx}/weixin/bankinfo/addBankinfo",dto,function(data){
				
				if(data.code==0){
					swal("添加成功");
					if(order==1){
						setTimeout(function(){
							location.href = "${ctx}/weixin/bankinfo/myBankinfo;JSESSIONID=<%=request.getSession().getId()%>";
						},2000);
					}else{
						setTimeout(function(){
							location.href = "${ctx}/weixin/withdraw/main;JSESSIONID=<%=request.getSession().getId()%>";
						},2000);
					}
				}else{
					swal(data.mes);
				}
				
				
			})			
			
			
		});

	});
	
	
</script>
</head>
<body>
<div class="b_att">
	<div class="bank_add">
            <p>为了你的账户资金安全，请绑定持卡人本人的银行卡。</p>
            <span>
            <samp>持卡人</samp>
            <font>
            	 <input type="text" class="b_k" value='${user.realname}' id="name"  />
            </font>
            </span>
            <dl>
                <dt>开户银行</dt>
               <!--  <a href="#"><dd>请选择银行</dd></a> -->
               <dd>
               <!-- <input id="bankName" type="text" placeholder="请输入银行 " /> -->         
                <select id="ipt_select" name="bankName" class="b_k">
	                  <option value="中国建设银行">中国建设银行</option>
	                  <option value="中国工商银行">中国工商银行</option>
	                  <option value="中国农业银行">中国农业银行</option>
	                  <option value="中国交通银行">中国交通银行</option>
	                  <option value="中国兴业银行">中国兴业银行</option>
	                  <option value="中信银行">中信银行</option>
	                  <option value="中国银行">中国银行</option>
	                  <option value="中国招商银行">中国招商银行</option>
	                  <option value="中国邮政储蓄银行">中国邮政储蓄银行</option>
	                  <option value="中国民生银行">中国民生银行</option>
	                  <option value="中国光大银行">中国光大银行</option>
	                  <option value="中国华夏银行">中国华夏银行</option>
	                  <option value="中国广发银行">中国广发银行</option>
                </select>                              
            </dd>
            </dl>
            <dl>
                <dt>开卡地区</dt>
                <dd>              
	               <select id="province" name="province" class="b_os" > </select>                 
	               <select id="city" name="city" class="b_os" > </select>                  
	                <script type="text/javascript">
						 mypacs =  new PCAS("province=福建,请选择省份", "city=${city},请选择城市");
					</script>
				</dd>
            </dl>
            <dl>
                <dt>卡号</dt>
                <dd><input type="text" class="b_k" id="bankaccount" placeholder="请输入您的银行卡号"></dd>
            </dl>
            <a href="#" id="addBankinfo"  ><h3>添加</h3></a>
            </div>  
</div>              
</body>
</html>