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
<title>产品管理</title>
<script type="text/javascript">
$(function(){
	activeNav2("4","4_2");
	/** 批量删除*/
	$("#btn_deletes").click(function(){
		if (!hasChecked()) {
			alert("请选中要删除的产品类型");
			return ;
		}else{
			var ids={};
			values ="";
			$('input[name="ids"]:checked').each(function(){
				values += $(this).val()+",";
			});
			values = values.substring(0,values.length-1);
			ids.ids =values;
			showCallBackDialog("是否确定批量删除？",function(){
				$.post('${ctx}/admin/product/deletes;JSESSIONID=<%=request.getSession().getId()%>',ids,function(res){
					if(res.code==1){
						alert("删除成功");
						location.reload();
					}else{
						alert(res.mes);
						location.reload();
					}
				})
			});
		}
	});
	/** 修改推荐*/
	$(".display").click(function(){
		$this = $(this);
		var state = $this.attr("state");
		var productId = $this.attr("changeId");
		var data = {};
		data.productId = productId;
		$.post('${ctx}/admin/product/changeIsDisplay;JSESSIONID=<%=request.getSession().getId() %>',data,function(res){
			if(res.code ==1){
				if(state == 'YES'){
					$this.html("推荐");
					$this.attr("state","NO");
				}else{
					$this.html("取消推荐");
					$this.attr("state","YES");
				}
			}
		})
	});
	/* $(".display").mouseover(function(){
		$this = $(this);
		$this.style.cursor = "help";
	}) */
	/** 添加产品*/
	$("#btn_addProdut").click(function(){
		location.href="${ctx}/admin/product/toChooseType";
	})
	
	/** 全选按钮*/
	$("#allcheck").click(function(){
		var ischecked = $(this).is(":checked")?"check":"uncheck"; 
		 $("input[type='checkbox']").iCheck(ischecked);
	})
	
	//选择
	$(".childcheckbox").click(function(){
		var childNum = $(".childcheckbox").length ;
		var a = $('input[name="ids"]:checked').length;
		if($('input[name="ids"]:checked').length < childNum){
			$("#allcheck").iCheck("uncheck");
		}
		if($('input[name="ids"]:checked').length == childNum){
			$("#allcheck").iCheck("check");
		}
	});
	
	/** 排序*/
	$("#sortloanMinAmount").click(function(){
		var id=$(this).data('id');
		if(id == 1){
			$("#ipt_sortName").val("loanMinAmount");
			$("#ipt_sortType").val("asc");
			$("#request_form").submit();
		}else{
			$("#ipt_sortName").val("loanMinAmount");
			$("#ipt_sortType").val("desc");
			$("#request_form").submit();
		}
	});
	
	$("#sortloanRate").click(function(){
		var id=$(this).data('id');
		if(id == 1){
			$("#ipt_sortName").val("loanRate");
			$("#ipt_sortType").val("asc");
			$("#request_form").submit();
		}else{
			$("#ipt_sortName").val("loanRate");
			$("#ipt_sortType").val("desc");
			$("#request_form").submit();
		}
	})
	
	$("#sortapplyTimes").click(function(){
		var id=$(this).data('id');
		if(id == 1){
			$("#ipt_sortName").val("applyTimes");
			$("#ipt_sortType").val("asc");
			$("#request_form").submit();
		}else{
			$("#ipt_sortName").val("applyTimes");
			$("#ipt_sortType").val("desc");
			$("#request_form").submit();
		}
	})
	
});
/** 提单类型改变*/
function changeValue(object){
	var $this = $(object);
	var value = $this.data("value");
	$(".billtype").removeClass('e_class');
	$this.addClass('e_class');
	$("#ipt_billType").val(value);
	$("#request_form").submit();
}
/** 删除*/
function deleteproduct(object){
	var $this = $(object);
	var id = $this.data('value');
	var data = {};
	data.id=id;
	showCallBackDialog("是否确定删除？",function(){
		$.post('${ctx }/admin/product/delete;JSESSIONID=<%=request.getSession().getId()%>',data,function(res){
			if(res.code == 1){
				alert("删除成功");
				location.reload();
			}else{
				alert(res.mes);
			}
		})
	});
}
/* 判断是否有被选中的项 */
function hasChecked() {
	if ($('input[name="ids"]:checked').length <= 0) {
		return false;
	}
	return true;
}
</script>
</head>
<body>
	<form id = "request_form" action = "${ctx }/admin/product/productlist">
		<input type = "hidden" id = "ipt_billType" name = "billType"/>
		<input type ="hidden" id ="ipt_sortName" value ="${sortName }" name = "sortName"/>
		<input type ="hidden" id = "ipt_sortType" value = "${sortType}" name = "sortType"/>
	</form>
	<div class="row border-bottom">
		<div class="basic">
	        <p>产品管理</p>
	        <span><a href="${ctx }/admin/main;JSESSIONID=<%=request.getSession().getId()%>" style="margin-left:0;">首页</a>><a href="#">产品管理</a>><a><strong>产品管理</strong></a></span>
	    </div>
    </div>
    <div class="employee animated fadeInRight e_chan">
    	<dl>
       	 <dt>提单类型：</dt>
         	<dd>
	         	<p class=" billtype <c:if test = "${billType == ''}">e_class</c:if>" data-value ="" onclick = "changeValue(this)">全部类型</p>
	            <p class= "billtype <c:if test = "${billType == 'EARNDIFFERENCE'}">e_class</c:if>"  data-value ="EARNDIFFERENCE" onclick = "changeValue(this)">赚差价</p>
	            <p class= "billtype <c:if test = "${billType == 'EARNCOMMISSION'}">e_class</c:if>"  data-value ="EARNCOMMISSION" onclick = "changeValue(this)">赚提成</p>
	            <p class= "billtype <c:if test = "${billType == 'SELFHELPLOAN'}">e_class</c:if>" data-value ="SELFHELPLOAN" onclick = "changeValue(this)">自助贷</p>
         </dd>
       </dl>
       <dl>
       	 <dt>操作：</dt>
         <dd>
            <samp id = "btn_addProdut">添加产品</samp>
            <samp class="e_suo" id = "btn_deletes">批量删除</samp>
          </dd>
       </dl>
      	 <!-- <div class="employee_la e_pos">
                <samp>批量操作</samp>
                <samp>批量操作</samp>
                <samp>批量操作</samp>
            </div> -->
       </div> 
        <div class="username-text animated fadeInRight">
                    	<div class="row">
			<div class="col-lg-12">
				<div class="ibox float-e-margins">

						<div class="table-responsive">
        <div class="level">
        <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0" class ="table table-striped">
             <tbody>
            	 <tr>
                 	<td class="l_width">
                    	<input type="checkbox" id="allcheck" class="l_in">
                    </td>
                    <td width="130"><strong>产品名称</strong></td>
                    <td><strong>提单类型</strong></td>
                    <td><strong>产品类型</strong></td>
                    <td><div class="level_triangle">
                    	<p><strong>贷款金额</strong></p>
                        <c:choose>
                    		<c:when test="${sortName eq 'loanMinAmount' && sortType eq 'desc' }">
                    			<span class="l_sanj2" id ="sortloanMinAmount" data-id = 1></span>
                    		</c:when>
                    		<c:when test="${sortName eq 'loanMinAmount' && sortType eq 'asc' }">
                    			<span class="l_sanj1" id ="sortloanMinAmount" data-id = 2></span>
                    		</c:when>
                    		<c:otherwise>
                    			<span class="l_sanj3" id ="sortloanMinAmount" data-id = 1></span>
                    		</c:otherwise>
                    	</c:choose>
                    </div></td>
                    <td>
                    <div class="level_triangle">
                    	<p><strong>贷款利率</strong></p>
                        <c:choose>
                    		<c:when test="${sortName eq 'loanRate' && sortType eq 'desc' }">
                    			<span class="l_sanj2" id ="sortloanRate" data-id = 1></span>
                    		</c:when>
                    		<c:when test="${sortName eq 'loanRate' && sortType eq 'asc' }">
                    			<span class="l_sanj1" id ="sortloanRate" data-id = 2></span>
                    		</c:when>
                    		<c:otherwise>
                    			<span class="l_sanj3" id ="sortloanRate" data-id = 1></span>
                    		</c:otherwise>
                    	</c:choose>
                    </div></td>
                    <td>
                    <div class="level_triangle">
                    	<p><strong>申请次数</strong></p>
                        <c:choose>
                    		<c:when test="${sortName eq 'applyTimes' && sortType eq 'desc' }">
                    			<span class="l_sanj2" id ="sortapplyTimes" data-id = 1></span>
                    		</c:when>
                    		<c:when test="${sortName eq 'applyTimes' && sortType eq 'asc' }">
                    			<span class="l_sanj1" id ="sortapplyTimes" data-id = 2></span>
                    		</c:when>
                    		<c:otherwise>
                    			<span class="l_sanj3" id ="sortapplyTimes" data-id = 1></span>
                    		</c:otherwise>
                    	</c:choose>
                    </div>
                    </td>
                    <td><strong>推荐首页</strong></td>
                    <td><strong>操作</strong></td>
                 </tr>
                 <c:forEach items = "${ products.content}" var = "product">
	                 <tr>
	                 	<td><input type="checkbox" class="l_in childcheckbox" id = "ids" name = "ids" value = "${product.id }"></td>
	                    <td>${product.info.productName }</td>
	                    <td>${product.type.billType.getDes() }</td>
	                    <td>${product.type.productName }</td>
	                    <td>${util:showTenThousandPrice(product.info.loanMinAmount) } - ${util:showTenThousandPrice(product.info.loanMaxAmount) }万</td>
	                    <c:choose>
	                    	<c:when test="${product.interestType.ordinal() =='1' }">
	                    		<td>${util:showThousandRateWithoutUnit(product.info.loanRate) }‰</td>
	                    	</c:when>
	                    	<c:otherwise>
	                    		<td>${util:showRateWithoutUnit(product.info.loanRate) }%</td>
	                    	</c:otherwise>
	                    </c:choose>
	                    
	                    <td>${product.applyTimes }</td>
	                    <c:choose>
	                    	<c:when test="${product.isDisplay eq 'YES' }">
	                    		<td class="m_yew display" state = "YES" style="cursor:pointer;" changeId= "${product.id }">取消推荐</td>
	                    	</c:when>
	                    	<c:otherwise>
	                    		<td class="m_yew display" state = "NO" style="cursor:pointer;" changeId= "${product.id }">推荐</td> 
	                    	</c:otherwise>
	                    </c:choose>
	                    <td>
	                   		 <div class="level_img">
	                        <a href="${ctx }/admin/product/toEditProduct/${product.id}" ><span><img src="${ctx }/static/brd/img/bjt1.png"></span></a>
	                        <a href="#" class ="btn_delete" data-value = "${product.id }" onclick = "deleteproduct(this)"><span><img src="${ctx }/static/brd/img/bjt3.png"></span></a>
	                        </div>
	                    </td>
	                  </tr>
                  </c:forEach>
                  
                 </tbody>
                 </table>
       			<div class=" m_right">
            		<tags:pagination paginationSize="10" page="${products}" hrefPrefix="${ctx }/admin/product/productlist" hrefSubfix="${queryStr}"></tags:pagination>
        		</div>
        </div>
        </div>
        </div>
        </div>
        </div>
    </div> 
</body>
</html>