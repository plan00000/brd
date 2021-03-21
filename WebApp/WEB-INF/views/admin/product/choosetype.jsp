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
<title>选择产品类型</title>
<script type="text/javascript">
$(function(){
	activeNav2("4","4_2");
	$("#next").click(function(){
		var productType = $("#ipt_productType option:selected"); 
		if(productType.length ==0){
			alert("产品类型不能为空");
			return ;
		}
		$("#submit_form").submit();
	})
	$("#back").click(function(){
		location.href = "${ctx}/admin/product/productlist;JSESSIONID=<%=request.getSession().getId()%>";
	})
})
function changeType(obj){
	$obj = $(obj);
	if($obj.val() =='1'){
		var a = "<select class='c_scel' name = 'productTypeId' id = 'ipt_productType'><c:forEach items = '${differenceProductTypes }' var = 'differenceProductType'>"
				+"<option value = '${differenceProductType.id }'>${differenceProductType.productName }</option>"
				+"</c:forEach></select>";
		$("#dd_type").html(a);
		
		/*  利息模式*/
		var b = "<select class='c_scel' name = 'interestType'><option value = '0'>利息模式 按月</option>"
				+"<option value = '1'>利息模式 按日</option><option value = '2'>手续费、收益金模式</option></select>";
		$("#dd_interestType").html(b);
	}
	if($obj.val() =='0'){
		var a = "<select class='c_scel' name = 'productTypeId' id = 'ipt_productType'><c:forEach items = '${selfProductTypes }' var = 'selfProductType'>"
		+"<option value = '${selfProductType.id }'>${selfProductType.productName }</option>"
		+"</c:forEach></select>";
		$("#dd_type").html(a);
		
		/*  利息模式*/
		var b = "<select class='c_scel' name = 'interestType'><option value = '0'>利息模式 按月</option>"
			+"<option value = '1'>利息模式 按日</option><option value = '2'>手续费、收益金模式</option></select>";
		$("#dd_interestType").html(b);
	}
	if($obj.val() =='2'){
		var a = "<select class='c_scel' name = 'productTypeId' id = 'ipt_productType'><c:forEach items = '${commissionProductTypes }' var = 'commissionProductType'>"
		+"<option value = '${commissionProductType.id }'>${commissionProductType.productName }</option>"
		+"</c:forEach></select>";
		$("#dd_type").html(a);
		
		/*  利息模式*/
		var b = "<select class='c_scel' name = 'interestType'><option value = '0'>利息模式 按月</option>"
			+"<option value = '1'>利息模式 按日</option><option value = '2'>手续费、收益金模式</option><option value = '3'>特殊模式</option></select>";
		$("#dd_interestType").html(b);
	}
}
</script>
</head>
<body>
	<div class="row border-bottom">
		<div class="basic">
	       <p>产品管理</p>
	       <span><a href="${ctx }/admin/main;JSESSIONID=<%=request.getSession().getId()%>"  style="margin-left:0;">首页</a>><a href="#">产品管理</a>><a><strong>添加产品</strong></a></span>
	    </div>
    </div>
	<div class="new_emp animated fadeInRight">
          <div class="new_xinxi"><p><font style="width:100px;">选择产品类型</font></p></div>
             <div class="choose">
             <form id ="submit_form" action="${ctx }/admin/product/toAddProduct" method = "post">
                 <dl>
                     <dt>提单类型：</dt>
                     <dd>
                     <select class="c_scel" onchange="changeType(this);">
                         <option value ="1">赚差价</option>
                         <option value ="2">赚提成</option>
                         <option value ="0">自助贷</option>
                     </select>
                    </dd>
                 </dl>
                 <dl>
                     <dt>产品类型：</dt>
                     <dd id = "dd_type">
                     <select class="c_scel" name = "productTypeId" id = "ipt_productType">
                     	<c:forEach items = "${differenceProductTypes }" var = "differenceProductType" >
                     		<option value = "${differenceProductType.id }">${differenceProductType.productName }</option>
                     	</c:forEach>
                     </select>
                    </dd>
                 </dl>
                 <dl>
                     <dt>抵押方式：</dt>
                     <dd>
                     <select class="c_scel" name = "mortgageType">
                         <option value ="0">抵押贷</option>
                         <option value ="2">信用贷</option>
                         <option value ="1">无抵押</option>
                     </select>
                    </dd>
                 </dl>
                 <dl>
                     <dt>利息模式：</dt>
                     <dd id = "dd_interestType">
                     <select class="c_scel" name = "interestType">
                         <option value = "0">利息模式 按月</option>
                         <option value = "1">利息模式 按日</option>
                         <option value = "2">手续费、收益金模式</option>
                     </select>
                    </dd>
                 </dl>
                 </form>
                 <p id = "next">下一步</p>
                 <span id = "back">返回</span>
             </div>
     </div> 
</body>
</html>