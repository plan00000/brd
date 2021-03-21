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
<script type="text/javascript">
(function($){
    $.fn.UIdialog = $.fn.dialog; /* 把jquery-ui的dialog另存为UIdialog */
})(jQuery);
</script>

<script type="text/javascript" src="${ctx }/static/js/common.js"></script>
<script src="${ctx}/static/js/input-number-change.js"></script>

<link rel="stylesheet" type="text/css" href="${ctx}/static/js/jquery-easyui-1.4.3/themes/default/easyui.css" media="all" />
<link rel="stylesheet" type="text/css" href="${ctx}/static/js/jquery-easyui-1.4.3/themes/icon.css" media="all" />
<script src="${ctx}/static/js/jquery-easyui-1.4.3/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${ctx}/static/js/jquery-easyui-1.4.3/locale/easyui-lang-zh_CN.js"></script>

<title>订单详情</title>

<script type="text/javascript">
(function($){
    $.fn.Udialog = $.fn.dialog; /* 把jquery-ui的dialog另存为UIdialog */
})(jQuery);
(function($){
	$.fn.dialog = $.fn.UIdialog; /* 把jquery-ui的dialog另存为UIdialog */
})(jQuery);
</script>
<script type="text/javascript">
$(function(){
	activeNav2("3","3_2");
	$("#btn_contacted").click(function(){
		//$("#btn_nocontacted").removeClass('con_blue');
		//$("#btn_nocontacted").addClass('c_ju');
		$("#btn_contacted").removeClass('c_ju');
		$("#btn_contacted").addClass('con_blue');
		$("#ipt_status").val("UNTOLKWITH");
		if(!checkcontact()){
			return ;
		}
		$.post('${ctx}/admin/orderform/pc/changeStatus;JSESSIONID=<%=request.getSession().getId()%>',$("#status_form").serialize(),function(res){
			if(res.code == 1){
				/* $("#div_contacted").addClass('hidden');
				$("#div_tolkwith").removeClass('hidden'); */
				alert("操作成功");
				location.reload();
			}
			else{
				alert(res.mes);
			}
		})
	});
	
	/*  面谈处理*/
	$("#btn_tolkwith").click(function(){
		//$("#btn_notolkwith").removeClass('con_blue');
	//	$("#btn_notolkwith").addClass('c_ju');
		$("#btn_tolkwith").removeClass('c_ju');
		$("#btn_tolkwith").addClass('con_blue');
		$("#ipt_status").val("UNCHECKED");
		
		if(!checktolkwith()){
			return ;
		}
		$.post('${ctx}/admin/orderform/pc/changeStatus;JSESSIONID=<%=request.getSession().getId()%>',$("#status_form").serialize(),function(res){
			if(res.code == 1){
				/* $("#div_tolkwith").addClass('hidden');
				$("#div_check").removeClass('hidden'); */
				alert("操作成功");
				location.reload();
			}
			else{
				alert(res.mes);
			}
		})
	});
	/*  审核处理*/
	$("#btn_checkpass").click(function(){
	//	$("#btn_uncheck").removeClass('con_blue');
	//	$("#btn_uncheck").addClass('c_ju');
	//	$("#btn_checkpass").removeClass('c_ju');
	//	$("#btn_checkpass").addClass('con_blue');
		$("#ipt_status").val("UNLOAN");
		
		if(!checkchecked()){
			return ;
		}
		$.post('${ctx}/admin/orderform/pc/changeStatus;JSESSIONID=<%=request.getSession().getId()%>',$("#status_form").serialize(),function(res){
			if(res.code == 1){
				/* $("#div_check").addClass('hidden');
				$("#div_loan").removeClass('hidden'); */
				alert("操作成功");
				location.reload();
			}
			else{
				alert(res.mes);
			}
		})
	});
	/** 审核拒绝*/
	$("#btn_checkrufuse").click(function(){
	//	$("#btn_uncheck").removeClass('con_blue');
	//	$("#btn_uncheck").addClass('c_ju');
		$("#btn_checkpass").removeClass('con_blue');
		$("#btn_checkpass").addClass('c_ju');
		$("#btn_checkrufuse").removeClass('c_ju');
		$("#btn_checkrufuse").addClass('con_blue');
		$("#div_refuse").removeClass("hidden");
	});
	$("#btn_refuseOk").click(function(){
		if(!checkrefuseOk()){
			return ;
		}
		$("#ipt_status").val("CHECKFAIL");
		$("#ipt_refuse").val($("#ipt_refuse").val());
		$.post('${ctx}/admin/orderform/pc/changeStatus;JSESSIONID=<%=request.getSession().getId()%>',$("#status_form").serialize(),function(res){
			if(res.code == 1){
				/* $("#div_refuse").addClass('hidden'); */
				alert("操作成功");
				location.reload();
			}
			else{
				alert(res.mes);
			}
		})
	});
	/** 审核拒绝-返回*/
	$("#btn_refuseCancel").click(function(){
		$("#btn_checkrufuse").removeClass('con_blue');
		$("#btn_checkrufuse").addClass('c_ju');
		$("#btn_checkpass").addClass('con_blue');
		$("#btn_checkpass").removeClass('c_ju');
		$("#div_refuse").addClass("hidden");
	});
	/** 放款处理*/
	$("#btn_loan").click(function(){
		//$("#btn_noloan").removeClass('con_blue');
	//	$("#btn_noloan").addClass('c_ju');
	//	$("#btn_loan").removeClass('c_ju');
	//	$("#btn_loan").addClass('con_blue');
		$("#ipt_status").val("LOANED");
		
		if(!checkloaned()){
			return ;
		}
		$("#ipt_loanActualManey").val($("#ipt_loanActualManey1").val());
		$("#ipt_loanTime").val($("#ipt_loanTime1").val());
		$("#ipt_loanInsterestRate").val($("#ipt_loanInsterestRate1").val());
		
		$.post('${ctx}/admin/orderform/pc/changeStatus;JSESSIONID=<%=request.getSession().getId()%>',$("#status_form").serialize(),function(res){
			if(res.code == 1){
				$("#div_loan").addClass('hidden');
				$("#div_end").removeClass('hidden');
				alert("操作成功");
				location.reload();
			}
			else{
				alert(res.mes);
			}
		})
	});
	/** 无效订单*/
	$("#btn_invalid").click(function(){
	//	$("#btn_noloan").removeClass('con_blue');
	//	$("#btn_noloan").addClass('c_ju');
		$("#btn_loan").removeClass('con_blue');
		$("#btn_loan").addClass('c_ju');
		$("#btn_invalid").removeClass('c_ju');
		$("#btn_invalid").addClass('con_blue');
		$("#ipt_status").val("INVALID");
		if(!checkinvalid()){
			return ;
		}
		showCallBackDialog("是否确定把订单改为无效订单？",function(){
			$.post('${ctx}/admin/orderform/pc/changeStatus;JSESSIONID=<%=request.getSession().getId()%>',$("#status_form").serialize(),function(res){
				if(res.code == 1){
					alert("操作成功");
					location.reload();
				}
				else{
					alert(res.mes);
				}
			})
		});
	});
	
	/** 备注操作*/
	$("#btn_addRemark").click(function(){
		$("#div_remark").removeClass('hidden');
	})
	$("#btn_remarkCancel").click(function(){
		$("#div_remark").addClass('hidden');
	})
	$("#btn_remarkOk").click(function(){
		if(checkRemark()){
			
			$.post('${ctx}/admin/orderform/weixin/saveRemark;JSESSIONID=<%=request.getSession().getId()%>',$("#remark_form").serialize(),function(res){
				if(res.code == 1){
					alert("添加备注成功");
					location.reload();
				}
				else{
					alert(res.mes);
				}
			})
		}
	});
	<%-- /** 备注操作*/
	$("#btn_saveRemark").click(function(){
		if(!checkRemark()){
			return ;
		}
		$.post('${ctx}/admin/orderform/weixin/saveRemark;JSESSIONID=<%=request.getSession().getId()%>',$("#remark_form").serialize(),function(res){
			if(res.code == 1){
				alert("添加备注成功");
				location.reload();
			}
			else{
				alert(res.mes);
			}
		})
	}); --%>
	/** 审核更改产品*/
	$("#btn_changeProduct").click(function(){
		changeProduct();
	});
	/** 根据条件查询产品*/
	$("#btn_search_product").click(function(){
		
		$("#products_tab").datagrid({
			queryParams:{
				billType:$("#select_billType").val(),
				interestType:$("#select_interestType").val(),
				type:$("#select_type").val()
			},
			pageNumber:1			
		});
	
	});
	
	/** 合同编号*/
	$("#btn_comtractNum").click(function(){
		var comtractNum = $("#ipt_comtractNum").val().trim();
		if(comtractNum.length == 0){
			alert("请输入合同编号");
			return false;
		}
		var data = {};
		data.comtractNum = comtractNum;
		data.orderformId = ${orderform.id};
		$.post("${ctx}/admin/orderform/weixin/saveComtractNum;JSESSIONID=<%=request.getSession().getId()%>",data,function(res){
			if(res.code == 1){
				alert("合同编号保存成功");
				location.reload();
			}else{
				alert(res.mes);
			}
			
		})
		
	});
	/** 更改产品*/
	$("#btn_saveproductid").click(function(){
		var selectIds = $("#products_tab").datagrid("getChecked");
		if(selectIds.length == 0){
			alert("请选择要更改的产品");
		}else if(selectIds.length >1){
			alert("只能选择一个产品");
		}else{
			var data = {};
			data.productId = selectIds[0].productId;
			data.orderformId = ${orderform.id};
			$.post('${ctx}/admin/orderform/weixin/changeProduct;JSESSIONID=<%=request.getSession().getId()%>',data,function(res){
				if(res.code == 1){
					$("#div_changeproduct").addClass('hidden');
					alert("操作成功");
					location.reload();
				}else{
					$("#div_changeproduct").addClass('hidden');
					alert(res.mes);
				}
				
			});
		}
	})
	/** 更改产品返回*/
	$("#btn_backproductid").click(function(){
		$("#div_changeproduct").addClass('hidden');
	})
	/** 放款更改产品*/
	$("#btn_unloanChangeProduct").click(function(){
		changeProduct();
	})
});
/** 更改产品*/
function changeProduct(){
	$("#dialog_products").dialog({
		  closeText: "关闭",
		  position:{
			  my: "left top",
			  at: "left+300 top+100",
			  of: window
			},
		  width: 800,
		  heigth: 300,
		  resizable: false,
		  modal: true,
		  title:"更改产品",
	});
	$("#products_tab").datagrid({
		 url : "${ctx}/admin/orderform/pc/getProducts/${orderform.id}",//首次查询路径  
		 queryParams:{
			 productName:'${productName}',
			 billType:'${billType}',
			 typeName:'${typeName}',
			 loanRate:'${loanRate}'
		 },
      idField : 'productId',
	    minHeight:306,
	    maxHeight:306,
      fit:true,
	    fitColumns:true,
	    nowrap:true,
	    rownumbers:true,
	    cache:false,
     checkOnSelect:true,  
	    selectOnCheck: true,
	    singleSelect:true,
     columns : [ [{
     	field:'productId',
     	title:'',
     	align:'center',
     	width:'5%',
     	checkbox:true,
     	sortable:true
     },{  
          field : "productName",  
          title : "产品名称",
          align:'center',
          width:'35%',
          formatter:function(value,row,index){
			     	return value;
			  }}
      ,{  
          field : "billType",  
          title : "提单类型",
          align:'center',
          width:'20%',
          formatter:function(value,row,index){
			     	return value;
			  }
      },{  
          field : "typeName",  
          title : "产品类型", 
          align:'center',
          width:'20%',
      },{  
          field : "loanRate",  
          title : "贷款利率", 
          align:'center',
          width:'20%',
      }
      ] ],
      onLoadSuccess:function(rowIndex, rowData){
			/* for(var i=0;i<strs.length;i++){
				$(this).datagrid('selectRecord',strs[i]);
			}  */  
		} 
	});
}
/*  订单-联系处理*/
function checkcontact(){
	if(!($("#btn_contacted").hasClass('con_blue'))){
		alert("请选择处理状态");
		return false;
	}
	return true;
}
/*  订单-面谈处理*/
function checktolkwith(){
	if(!($("#btn_tolkwith").hasClass('con_blue'))){
		alert("请选择处理状态");
		return false;
	}
	return true;
}
/* 订单-审核处理*/
function checkchecked(){
	var status = ${orderform.status.ordinal()};
	if(!($("#btn_checkpass").hasClass('con_blue'))){
		alert("请选择处理状态");
		return false;
	}
	if(status == '4'){
		return false;
	}
	return true;
}
/** 订单-审核通过*/
function checkrefuseOk(){
	var refuse = $("#ipt_refuse1").val().trim();
	if(refuse.length == 0){
		alert("请输入拒绝原因");
		return false;
	}
	return true;
}
/** 订单-放款*/
function checkloaned(){
	
	var loanMinAmount = ${orderform.product.info.loanMinAmount};
	var loanMaxAmount = ${orderform.product.info.loanMaxAmount};
	var loanMinTime = ${orderform.product.info.loanMinTime};
	var loanMaxTime = ${orderform.product.info.loanMaxTime};
	loanMinAmount = loanMinAmount/10000;
	loanMaxAmount = loanMaxAmount/10000;
	var loanActualManey = $("#ipt_loanActualManey1").val().trim();
	var loanTime = $("#ipt_loanTime1").val().trim();
	var loanInsterestRate = $("#ipt_loanInsterestRate1").val().trim();
	if(!($("#btn_loan").hasClass('con_blue'))){
		alert("请选择处理状态");
		return false;
	}
	if(loanActualManey.length == 0){
		alert("请输入实际贷款金额");
		return false;
	}
	if(!CommValidate.isPositiveInteger(loanActualManey)){
		alert("实际贷款金额为正整数");
		return false;
	}
	if(loanTime.length == 0){
		alert("请输入贷款期限");
		return false;
	}
	if(!CommValidate.isPositiveInteger(loanTime)){
		alert("贷款期限为正整数");
		return false;
	}
	if(loanInsterestRate.length == 0){
		alert("请输入贷款利率");
		return false;
	}
	
	if(loanActualManey<loanMinAmount){
		alert("请输入"+loanMinAmount+"-"+ loanMaxAmount+"的金额");
		return false;
	}
	if(loanActualManey>loanMaxAmount){
		alert("请输入"+loanMinAmount+"-"+ loanMaxAmount+"的金额");
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
/** 无效订单*/
function checkinvalid(){
	if(!($("#btn_invalid").hasClass('con_blue'))){
		alert("请选择处理状态");
		return false;
	}
	return true;
}
/** 备注*/
function checkRemark(){
	var remark = $("#ipt_remark").val().trim();
	if(remark.length == 0){
		alert("请输入备注");
		return false;
	}
	return true;
}
/** */
function changeValue(object){
	
	var $this = $(object);
	$(".selectBox").iCheck('uncheck');
	$this.iCheck('check');
	var value = $this.data("value");
	$("#ipt_productid").val(value);
}

</script>
</head>
<body>
	<form id = "status_form">
		<input type ="hidden" id = "ipt_status" name = "status" />
		<input type ="hidden" id = "ipt_orderfomid" name = "orderformId" value = "${orderform.id }"/>
		<input type ="hidden" id = "ipt_refuse" name = "refuse" />
		<input type ="hidden" id = "ipt_loanActualManey" name = "loanActualManey" />
		<input type ="hidden" id = "ipt_loanTime" name = "loanTime" />
		<input type ="hidden" id = "ipt_loanInsterestRate" name = "loanInsterestRate" />
	</form>
	
	<div class="row border-bottom">
		<div class="basic">
			<p>订单管理</p>
		    <%-- <span><a href="${ctx }/admin/main;JSESSIONID=<%=request.getSession().getId()%>"  style="margin-left:0;">首页</a>><a href="#" >贷款订单</a>><a><strong>贷款订单详情</strong></a></span> --%>
	    		    <span><a href="${ctx }/admin/main;JSESSIONID=<%=request.getSession().getId()%>"  
		    style="margin-left:0;">首页</a>><a href="#" >订单管理</a>><a href="#" >官网订单</a>><a><strong>${orderform.productInfo.productName }</strong></a></span>
	    </div>
    </div>
	<div class="details animated fadeInRight">
          <div class="new_xinxi"><p><font>订单信息</font></p></div>
               <div class="details_box d_xqy">
                  <ul>
                      <li>
                          <p>下单时间：</p>
                          <span>${util:formatNormalDate(orderform.createTime) }</span>
                      </li>
                      <%-- <li>
                          <p>身份证号：</p>
                          <span>${orderform.idcard }</span>
                      </li> --%>
                      <li>
                          <p>所在地：</p>
                          <span>${orderform.province }&nbsp;&nbsp;${orderform.city }</span>
                      </li>
                      <li style="width: auto;">
                          <p>申请贷款：</p>
                          <span>${orderform.oldProduct.info.productName }</span>
                      </li>
                      <li>
                          <p>贷款金额：</p>
                          <span>${util:showTenThousandPrice(orderform.money) }万</span>
                      </li>
                      <li>
                          <p>贷款期限：</p>
                          <c:choose>
                          	<c:when test="${orderform.product.interestType.ordinal() eq '1' }">
                          		<span>${orderform.oldLoanTime }天</span>
                          	</c:when>
                          	<c:otherwise>
                          		<span>${orderform.oldLoanTime }个月</span>
                          	</c:otherwise>
                          </c:choose>
                          
                      </li>
                      <li style="width: auto;">
                          <p>订单备注：</p>
                          <span style="width:320px;">${orderform.remark }</span>
                      </li>
                 </ul>
                 </div>
                 <div class="details_box d_xqy">
                  <ul>
                      <li>
                          <p>订单编号：</p>
                          <span>${orderform.orderNo }</span>
                      </li>
                      <li>
                          <p>手机号码：</p>
                          <span>${orderform.telephone }</span>
                      </li>
                       <li style="margin-top:30px;"></li>
                    <li>
                        <p>提单类型：</p>
                        <span>${orderform.oldProduct.type.billType.getDes() }</span>
                    </li>
                    <li>
                        <p>贷款利率：</p>
                        <c:if test = "${ orderform.oldProduct.interestType.ordinal() eq '0'}">
                        	<span>${util:showRateWithoutUnit(orderform.oldProduct.info.loanRate) }%</span>
                        </c:if>
                        <c:if test = "${ orderform.oldProduct.interestType.ordinal() eq '1'}">
                        	<span>${util:showThousandRateWithoutUnit(orderform.oldProduct.info.loanRate) }‰</span>
                        </c:if>
                    </li>
                    <li style="margin-top:80px;"></li>
               </ul>
               </div>
               <div class="new_xinxi"><p><font>操作</font></p></div>
               <!-- 订单-联系-->
               <div class="contact <c:if test = "${orderform.status != 'UNCONTACTED' }">hidden</c:if>" id ="div_contacted">
                   <dl>
                       <dt>订单状态：</dt>
                       <dd><span  id = "btn_nocontacted">待联系</span><span style="margin-left:30px;" class="con_blue"  id="btn_contacted">已联系</span></dd>
                   </dl>
                   
               </div>
               <!-- 订单-面谈-->
               <div class="contact <c:if test = "${orderform.status != 'UNTOLKWITH' }">hidden</c:if>" id = "div_tolkwith">
                   <dl>
                       <dt>订单状态：</dt>
                       <dd><span  id="btn_notolkwith">待面谈</span><span style="margin-left:30px;" class="con_blue" id= "btn_tolkwith">已面谈</span></dd>
                   </dl>
               </div>
               <!-- 订单-审核-->
               <div class="contact <c:if test = "${orderform.status != 'UNCHECKED' }">hidden</c:if>" id= "div_check">
                   <dl>
                       <dt>申请贷款：</dt>
                       <dd><span>${orderform.product.info.productName }</span><samp class="c_bjs" style="margin-left:30px;" id ="btn_changeProduct">更改产品</samp></dd>
                   </dl>
                   <dl>
                       <dt>订单状态：</dt>
                       <dd>
                           <span  id = "btn_uncheck">待审核</span>
                           <span class="con_blue" id = "btn_checkpass" style="margin-left:30px;">通过</span>
                           <span class ="c_ju" id = "btn_checkrufuse" style="margin-left:30px;" >拒绝</span>	
                       </dd>
                   </dl>
                   
                </div>
                <!-- 订单-放款-->
                <div class="contact <c:if test = "${orderform.status !='UNLOAN' }">hidden</c:if>" id="div_loan">
                   <dl>
                       <dt>申请贷款：</dt>
                       <dd><span>${orderform.product.info.productName }</span><samp class="c_bjs" style="margin-left:30px;" id ="btn_unloanChangeProduct">更改产品</samp></dd>
                   </dl>
                   <dl>
                       <dt>订单状态：</dt>
                       <dd>
                           <span  id = "btn_noloan">待放款</span>
                           <span class="con_blue" id = "btn_loan" style="margin-left:30px;">已放款</span>
                           <span class ="c_ju" id = "btn_invalid" style="margin-left:30px;">无效订单</span>	
                       </dd>
                   </dl>
                   <dl>
                       <dt>实际贷款金额：</dt>
                       <dd>
                           <input type="text" class="c_wan" id = "ipt_loanActualManey1" value = "${util:showTenThousandPrice(orderform.money) }">
                           <span>万元</span>
                           <span class="c_in">贷款期限：</span>
                           <input type="text" class="c_wan" id= "ipt_loanTime1" value = "${orderform.loanTime}">
                           <c:choose>
                          	<c:when test="${orderform.product.interestType.ordinal() eq '1' }">
                          		<span>天</span>
                          	</c:when>
                          	<c:otherwise>
                          		<span>个月</span>
                          	</c:otherwise>
                          </c:choose>
                           
                           <span class="c_in">贷款利率：</span>
                           <c:choose>
                          	<c:when test="${orderform.product.interestType.ordinal() eq '1' }">
                          		<input type="text" class="c_wan" id = "ipt_loanInsterestRate1" value = "${util:showThousandRateWithoutUnit(orderform.productInfo.loanRate) }">
                          	</c:when>
                          	<c:otherwise>
                          		<input type="text" class="c_wan" id = "ipt_loanInsterestRate1" value = "${util:showRateWithoutUnit(orderform.productInfo.loanRate) }">
                          	</c:otherwise>
                          </c:choose>
                          <%--  <input type="text" class="c_wan" id = "ipt_loanInsterestRate1" value = "${orderform.productInfo.loanRate }"> --%>
                           <c:choose>
                          	<c:when test="${orderform.product.interestType.ordinal() eq '1' }">
                          		<span>‰/日</span>
                          	</c:when>
                          	<c:otherwise>
                          		<span>%/月</span>
                          	</c:otherwise>
                          </c:choose>
                       </dd>
                   </dl>
                   
                   <!-- <dl>
                       <dt>合同编号：</dt>
                       <dd><input type="text" class="c_het" id = "ipt_comtractNum1"></dd>
                   </dl> -->
                   <dl>
                        <dt>合同编号：</dt>
                        <dd>
                            <input type="text" class="c_het" id = "ipt_comtractNum" value = "${orderform.comtractNum }">
                            <span class="c_lanse" id = "btn_comtractNum">保存</span>
                        </dd>
                   </dl>
                   
                </div>
                <!-- 订单-end-->
                <div class="contact <c:if test = "${orderform.status !='LOANED' }">hidden</c:if>" id= "div_end">
                   <dl>
                       <dt>申请贷款：</dt>
                       <dd><span>${orderform.productInfo.productName }</span></dd>
                   </dl>
                   <dl>
                       <dt>订单状态：</dt>
                       <dd>
                           <span>${orderform.status.getDes() }</span>
                       </dd>
                   </dl>
                   <dl>
                       <dt>实际贷款金额：</dt>
                       <dd>
                           <span>${util:showTenThousandPrice(orderform.actualMoney) }万</span>
                           <span class="c_in2">贷款期限：</span>
                           <c:choose>
                          	<c:when test="${orderform.product.interestType.ordinal() eq '1' }">
                          		<span>${orderform.loanTime }天</span>
                          	</c:when>
                          	<c:otherwise>
                          		<span>${orderform.loanTime }个月</span>
                          	</c:otherwise>
                          </c:choose>
                           
                           <span class="c_in2">贷款利率：</span>
                           <c:choose>
                          	<c:when test="${orderform.product.interestType.ordinal() eq '1' }">
                          		<span>${util:showThousandRateWithoutUnit(orderform.actualLoanInsterestRate) }‰/日</span>
                          	</c:when>
                          	<c:otherwise>
                          		<span>${util:showRateWithoutUnit(orderform.actualLoanInsterestRate) }%/月</span>
                          	</c:otherwise>
                          </c:choose>
                       </dd>
                   </dl>
                   <%-- <dl>
                       <dt>合同编号：</dt>
                       <dd><span>${orderform.comtractNum }</span></dd>
                   </dl> --%>
                   <dl style=" border-bottom:dashed #dcdcdc 1px; height:50px;">
                       <dt>合同编号：</dt>
                       <dd><span>${orderform.comtractNum }</span></dd>
                   </dl>
                </div>
                <%-- <div class="contact_text">
                     <dl>
                        <dt>订单备注：</dt>
                        <dd>
                        <form id = "remark_form">
                        <c:forEach items = "${ orderformRemarks}" var = "orderformRemark">
                        	<samp>${orderformRemark.remark }</samp>
                        </c:forEach>
                           <textarea name="remark" class="c_com" id="ipt_remark" 
                        	onKeyDown="gbcount(this.form.remark,this.form.used,30);" 
				 			onKeyUp="gbcount(this.form.remark,this.form.used,30);" onblur = "gbcount(this.form.remark,this.form.used,30);"></textarea>
				 			<font> <input class="inputtext1 inputtextn" style="float: right;text-align: right;" value ="0/30" name="used"   readonly="readonly"></font>
                        	<!-- <textarea id="ipt_remark" name = "remark" class="c_com" ></textarea> -->
							<input type ="hidden" id = "ipt_remarkOrder" name = "orderformId" value = "${orderform.id }"/>
                        </form>
                        </dd>
                        </dl>
                   <p id = "btn_saveRemark">保存</p>
                    
                </div> --%>
                <div class="contact_text">
                    <dl>
                        <dt>订单备注：</dt>
                        <dd id = "btn_addRemark">添加</dd>
                     </dl>
                     <div class="c_table">
                     	<table width="100%" cellpadding="0" cellspacing="0">
                         	<tbody>
                         		 <c:forEach items = "${ orderformRemarks}" var = "orderformRemark">
                         		 	<tr>
                                  	<td width="300px">${orderformRemark.remark }</td>
                                      <td width="130px">${orderformRemark.user.username }</td>
                                      <td width="155px">${util:formatNormalDate(orderformRemark.createTime) }</td>
                                 	</tr>
                         		 </c:forEach>
                             </tbody>
                         </table>
                     </div>
                 </div>
                <!-- 订单-拒绝弹出框-->
                <div class="refuse_box hidden" id = "div_refuse">
                	<div class="refuse">
                	<form>
                  	 <p>请输入审核拒绝原因:</p>
                     <textarea id = "ipt_refuse1"  class="r_text"
                     id="ipt_refuse1" name="reason"
                        	onKeyDown="gbcount(this.form.reason,this.form.used3,100);" 
				 			onKeyUp="gbcount(this.form.reason,this.form.used3,100);"></textarea>
                    <%--  <span>预计佣金总和：${orderform.brokerageRateNum }元</span> --%>
                     <font> <input class="inputtext1 inputtextn" style="float: right;text-align: right;" value ="0/100" name="used3"  id="treason" readonly="readonly"></font>
                         <samp>
                             <a id = "btn_refuseOk">确定</a>
                             <a id = "btn_refuseCancel" class="r_anniu">返回</a>
                         </samp>
                     </form>
                    </div>
                </div>
                <!-- 订单-备注 -->
                <div class="refuse_box hidden" id = "div_remark">
                	<form id ="remark_form">
                	<div class="refuse">
                  	 <p>请输入备注:</p>
                          <textarea  class="r_text" id="ipt_remark" name="remark"
                        	onKeyDown="gbcount(this.form.remark,this.form.used,30);" 
				 			onKeyUp="gbcount(this.form.remark,this.form.used,30);" onblur = "gbcount(this.form.remark,this.form.used,30);"></textarea>
                        	<font> <input class="inputtext1 inputtextn" style="float: right;text-align: right;" name="used"  id="tremark" readonly="readonly"> </font>
                        	
							<input type ="hidden" id = "ipt_remarkOrder" name ="orderformId" value = "${orderform.id }"/>
                         <samp>
                             <a id = "btn_remarkOk">确定</a>
                             <a id = "btn_remarkCancel" class="r_anniu">返回</a>
                         </samp>
                    </div>
                    </form>
                </div>
                <!-- 订单-更改产品-->
                <div id="dialog_products" style="display:none;">
               		<div class="change_top">
					<form method="get" class="form-horizontal" id="search_form_product">
						<div class="form-group" style="width: 100%;">
							<dl>
                       		 <dt style = "margin-left:10px;">提单类型：</dt>
                             <dd>
                             <select class="c_dou" id = "select_billType" name = "billType">
                                 <option value = "SELFHELPLOAN">自助贷</option>
                             </select>
                             </dd>
                        </dl>
                        <dl>
                       		 <dt>产品类型：</dt>
                             <dd>
                             <select class="c_dou" id = "select_type" name = "type">
                             	<option value ="">不限</option>
                                <c:forEach items = "${productTypes}" var = "productType">
                                 	<option value = "${productType.productName}">${productType.productName}</option>
                                </c:forEach>
                             </select>
                             </dd>
                        </dl>
                        <dl>
                       		 <dt style = "margin-left:10px;">利息模式：</dt>
                             <dd>
                             <select class="c_dou" id = "select_interestType" name = "interestType">
                             	 <option value = "">不限</option>
                                 <option value = "INTERESTMODELMONTH">利息模式 按月</option>
                                 <option value = "INTERESTMODELDAY">利息模式 按日</option>
                             </select>
                             </dd>
                        </dl>
                        <span id ="btn_search_product">查询</span>
						</div>
					</form>
					</div>
					<table id="products_tab" title="" class="easyui-datagrid" ></table>
					<br>
					<div align="left" align="left" style="float: left; margin-left: 20px">
						<!-- <div class="form-group">
							<div class="change_firt">
                         		<p id = "btn_saveproductid">保存</p>
                         		<span id = "btn_backproductid">取消</span>
                    		</div>
						</div> -->
						<div data-toggle="buttons" class="btn-group">
							<button id="btn_saveproductid" class="btn btn-primary" type="button">确定</button>
						</div>
					</div>
				</div>
			<!-- 更改产品 -->
            <div class="new_xinxi">
                <p><font>操作记录</font></p>
            </div>
            <div class="record_mex tact">
                  	<table width="100%" cellpadding="1" cellspacing="1" align="center">
                      <tbody>
                          <tr>
                          	<td width="160"><strong>操作时间</strong></td>
                            <td width="130"><strong>员工名</strong></td>
                            <td width="130"><strong>角色</strong></td>
                            <td><strong>操作内容</strong></td>
                          </tr>
                          <c:forEach items = "${orderOperLogs.content }" var = "orderOperLog">
                          	<tr>
                          	<td>${util:formatNormalDate(orderOperLog.createTime) }</td>
                            <td>${orderOperLog.opertor.username }</td>
                            <td>${orderOperLog.opertor.role.rolename }</td>
                            <td><samp>${orderOperLog.operContent }</samp></td>
                          </tr>
                          </c:forEach>
                          
                      </tbody>
                  </table>
                  <div class=" m_right">
					<tags:pagination paginationSize="10" page="${orderOperLogs}" hrefPrefix="${ctx }/admin/orderform/pc/detail/${orderform.id }" hrefSubfix=""></tags:pagination> 
                 </div>
          </div>
    </div> 
</body>
</html>