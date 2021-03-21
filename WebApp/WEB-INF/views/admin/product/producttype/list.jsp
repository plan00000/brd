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
<title>产品类型</title>
<script src="${ctx}/static/js/input-number-change.js"></script>
<script type="text/javascript">

var tdproduct;
$(function(){
	activeNav2("4","4_1");
	
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
				$.post('${ctx}/admin/product/productType/deletes;JSESSIONID=<%=request.getSession().getId()%>',ids,function(res){
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
	
	/** 添加产品类别*/
	$("#btn_addProdutType").click(function(){
		$(".refuse_box").removeClass('hidden');
	})
	$("#btn_add").click(function(){
		if(!check()){
			return ;
		}
		$.post('${ctx}/admin/product/productType/checkName;JSESSIONID=<%=request.getSession().getId()%>',$("#addType_form").serialize(),function(res){
			if(res.code == 1){
				$.post('${ctx}/admin/product/productType/add;JSESSIONID=<%=request.getSession().getId()%>',$("#addType_form").serialize(),function(res){
					if(res.code==1){
						alert("添加成功");
						$(".refuse_box").addClass('hidden');
						location.reload();
					}else{
						alert(res.mes);
					}
				});
			}else{
				alert(res.mes);
			}
		});
		<%-- $.post('${ctx}/admin/product/productType/add;JSESSIONID=<%=request.getSession().getId()%>',$("#addType_form").serialize(),function(res){
			if(res.code==1){
				alert("添加成功");
				$(".refuse_box").addClass('hidden');
				location.reload();
			}else{
				alert(res.mes);
			}
		}) --%>
		
	});
	$("#btn_cancel").click(function(){
		$(".refuse_box").addClass('hidden');
	});
	/** 全选按钮*/
	$("#allcheck").click(function(){
		var ischecked = $(this).is(":checked")?"check":"uncheck"; 
		 $("input[type='checkbox']").iCheck(ischecked);
	});
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
	/* $(".table").mousedown(function(){
		$(".edittypename").find("#edit_productName").blur(editLostBlur);
	});  */
	
})
function editProductType(object){
	var $this = $(object);
	var id = $this.data('value');
	var name = $this.data('name');
	var a = name.length+"/6";
	/* var a = $(".edittypename").find("input").length;
	
		editLostBlur;
		 */
	editLostBlur;
	tdproduct = "#td_productname"+id;
	$(tdproduct).html("<input style='display：inline-block;' onKeyDown='gbcount(this.form.productName,this.form.used,6);' "+
	" onKeyUp='gbcount(this.form.productName,this.form.used,6);' onblur= 'editLostBlur()'"+
	"  type = 'text' class='l_shuru'  id = 'edit_productName' data-id ='"+id+"' value = '"+name+"' name = 'productName'>"+
	"<input type='text' class='l_shuru01'style='padding-left:10px;' value='"+a+"' name='used' id='tshareNotify' readonly='readonly'> ");
	$(tdproduct).find("#edit_productName").focus();
	
}
function editLostBlur(){
	if($("#edit_productName").length>0 ){
		var editproductname = $("#edit_productName").val().trim();
		
		if(editproductname.length ==0){
			return ;
		}else{
			var data = {};
			data.productTypeId = $("#edit_productName").data('id');
			data.productName = editproductname;
			$.post("${ctx}/admin/product/productType/edit;JSESSIONID=<%=request.getSession().getId()%>",data,function(res){
				if(res.code ==1){
					location.reload();
				}else{
					alert(res.mes);
				}
			})
		}
	}
}
/** 提单类型改变*/
function changeValue(object){
	var $this = $(object);
	var value = $this.data("value");
	$(".billtype").removeClass('e_class');
	$this.addClass('e_class');
	$("#ipt_billType").val(value);
	$("#request_form").submit();
}

/**删除订单 */
function deleteProductType(object){
	var $this = $(object);
	var id = $this.data('value');
	var data = {};
	data.id=id;
	showCallBackDialog("是否确定删除？",function(){
		$.post('${ctx }/admin/product/productType/delete;JSESSIONID=<%=request.getSession().getId()%>',data,function(res){
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
/** 判断产品*/
function check(){
	var productName = $("#ipt_productName").val().trim();
	if(productName.length == 0){
		alert("名称不能为空");
		return false;
	}
	if(productName.length >6){
		alert("名称长度不能超过6个字");
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
	       <span><a href="${ctx }/admin/main;JSESSIONID=<%=request.getSession().getId()%>" style="margin-left:0;">首页</a>><a href="#" >产品管理</a>><a><strong>产品类型</strong></a></span>
	    </div>
    </div>
    <form action="${ctx }/admin/product/productType/list" id = "request_form">
    	<input type = "hidden" name = "billType" id = "ipt_billType"/>
    </form>
     
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
            <samp id = "btn_addProdutType">添加类型</samp>
            <samp class="e_suo" id = "btn_deletes">批量删除</samp>
          </dd>
       </dl>
       </div>
        <div class="username-text animated fadeInRight">
          <div class="row">
			<div class="col-lg-12">
				<div class="ibox float-e-margins">
				<div class="table-responsive">
        <div class="level">
        <form id = "submit_form" method ="post">
        <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0" class ="table table-striped">
             <tbody>
            	 <tr>
                 	<td class="l_width"><input type="checkbox" id="allcheck" class="l_in"></td>
                    <td><strong>提单类型</strong></td>
                    <td><strong>产品类型</strong></td>
                    <td><strong>操作</strong></td>
                 </tr>
                 <c:forEach items = "${productTypes.content }" var = "productType">
                 	<tr>
	                    <c:choose>
	                    <c:when test ="${productType.id eq '1'|| productType.id eq '2'||productType.id eq '3' }">
	                    	<td></td>
	                    </c:when>
	                    <c:otherwise>
	                    	<td><input type="checkbox" class="l_in childcheckbox" id = "ids" name = "ids" value = "${productType.id }"></td>
	                    </c:otherwise>
	                    </c:choose>
	                    <td>${productType.billType.getDes() }</td>
	                    <td id = "td_productname${productType.id }" class = "edittypename">${productType.productName }</td>  
	                    <c:choose>
	                    <c:when test ="${productType.id eq '1'|| productType.id eq '2'||productType.id eq '3' }">
	                    	<td>
	                   		 	<div class="level_img">
	                    			<a href="#" onclick = "editProductType(this)" data-value = "${productType.id }" data-name = "${productType.productName }" ><span><img src="${ctx}/static/brd/img/bjt1.png"></span></a>
<%-- 	                    			<a href="#" class = "btn_editProductType" ><span><img src="${ctx}/static/brd/img/bjt1.png"></span></a> --%>
	                    		</div>
	                    	</td>
	                    </c:when>
	                    <c:otherwise>
	                    	<td>
	                   		 <div class="level_img">
	                        <a href="#" onclick = "editProductType(this)" data-value = "${productType.id }" data-name = "${productType.productName }"><span><img src="${ctx}/static/brd/img/bjt1.png"></span></a>
	                        <a href="#" onclick = "deleteProductType(this)" data-value = "${productType.id }" ><span><img src="${ctx }/static/brd/img/bjt3.png"></span></a>
	                        </div>
	                    </td>
	                    </c:otherwise>
	                    </c:choose>
                  </tr>
                 </c:forEach>
                 </tbody>
           </table>
        </form>
		<div class=" m_right">
            <tags:pagination paginationSize="10" page="${productTypes}" hrefPrefix="${ctx }/admin/product/productType/list" hrefSubfix="${queryStr}"></tags:pagination>
        </div>
        </div>
        </div>
        </div>
        </div>
        </div>
     </div> 
     <div class="refuse_box hidden">
        	<form id = "addType_form">
             <div class="product_type">
             	<dl>
                	<dt>提单类型</dt>
                    <dd>
                        <select class="p_sel" name = "billType">
                        	<option value = "1">赚差价</option>
                            <option value = "2">赚提成</option>
                            <option value = "0">自助贷</option>
                        </select>
                    </dd>
                 </dl>
                 <dl>
                	<dt>产品类别</dt>
                    <dd id="ptext"><input class="p_sel" name = "productName" id = "ipt_productName" maxlength="6"
                   	 onKeyDown="gbcount(this.form.productName,this.form.used,6);"  onKeyUp="gbcount(this.form.productName,this.form.used,6);"/> 
                   	<input style="width: 40px;border:none;background:#fff;" name="used" value="0/6" readonly="readonly" /> 
                    </dd>
                   
                 </dl>
                 <span id = "btn_add">添加</span><samp id = "btn_cancel">取消</samp>
             </div>
             </form>
        </div>
</body>
</html>