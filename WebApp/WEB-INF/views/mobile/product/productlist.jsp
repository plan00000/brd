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
<script type="text/javascript">
document.title="贷款";
var userFlag = "${userFlag}";
var Data = {};
$(function(){
	//赚差价
	$("#earncommission").click(function(){
		if(userFlag == 'all'){
			$("#selfhelploan").removeClass('l_bj');
			$("#earndifference").removeClass('l_bj');
			$("#earncommission").addClass('l_bj');
			
			$("#ipt_billtype").val('earncommission');
			Data.billType = 'earncommission';
			requestLoad();
		}
	});
	//自助贷
	$("#selfhelploan").click(function(){
		$("#earndifference").removeClass('l_bj');
		$("#earncommission").removeClass('l_bj');
		$("#selfhelploan").addClass('l_bj');
		
		$("#ipt_billtype").val('selfhelploan');
		Data.billType = 'selfhelploan';
		requestLoad();
	});
	//赚差价
	$("#earndifference").click(function(){
		if(userFlag == 'all'){
			$("#selfhelploan").removeClass('l_bj');
			$("#earncommission").removeClass('l_bj');
			$("#earndifference").addClass('l_bj');
			
			$("#ipt_billtype").val('earndifference');
			Data.billType = 'earndifference';
			requestLoad();
		}
	});
	//赚提成1
	$("#earncommission1").click(function(){
		if(userFlag == 'manager'){
			$("#earndifference1").removeClass('l_bj');
			$("#earncommission1").addClass('l_bj');
			
			$("#ipt_billtype").val('earncommission');
			Data.billType = 'earncommission';
			requestLoad();
		}
	});
	//赚差价1
	$("#earndifference1").click(function(){
		if(userFlag == 'manager'){
			$("#earncommission1").removeClass('l_bj');
			$("#earndifference1").addClass('l_bj');
			
			$("#ipt_billtype").val('earndifference');
			Data.billType = 'earndifference';
			requestLoad();
		}
	});
	//抵押方式
	$("#mortgagetype").click(function(){
		$("#mortgagetype_select").removeClass('hidden');
		$("#type_select").addClass('hidden');
		$("#sortby_select").addClass('hidden');
	});
	//产品类型
	$("#producttype").click(function(){
		$("#type_select").removeClass('hidden');
		$("#mortgagetype_select").addClass('hidden');
		$("#sortby_select").addClass('hidden');
	});
	//排序
	$("#sorttype").click(function(){
		$("#sortby_select").removeClass('hidden');
		$("#mortgagetype_select").addClass('hidden');
		$("#type_select").addClass('hidden');
	});
});

function changeValue(object){
	var $this = $(object);
	var type = $this.data("type");
	var value = $this.data("value");
	var name = $this.data("name");
	if(type=='mortgageType'){
		
		$(".mortgagetype").removeClass('m_bj');
		$this.addClass('m_bj');
		setTimeout(function(){
			$("#mortgagetype_select").addClass('hidden');
		},500); 
		$("#mortgagetype").text(name);
		
		$("#ipt_mortgagetype").val(value);
		Data.mortgageType = value;
		requestLoad();
		
	}
	if(type == 'productType'){
		$(".producttype").removeClass('m_bj');
		$this.addClass('m_bj');
		setTimeout(function(){
			$("#type_select").addClass('hidden');
		},500);
		$("#producttype").text(name);
		
		$("#ipt_type").val(value);
		Data.type= value;
		requestLoad();
	}
	
	if(type =='sortType'){
		$(".sorttype").removeClass('m_bj');
		$this.addClass('m_bj');
		setTimeout(function(){
			$("#sortby_select").addClass('hidden');
		},500);
		$("#sorttype").text(name);
		
		$("#ipt_sortby").val(value);
		Data.sortBy = value;
		requestLoad();
		
	}
	
}
/** 条件查询*/
function requestLoad(){
	$("#request_form").submit();
}
var isLoading = false;
function loadProductData(){
	var nowpage = $("#nowpage").val();
	var nextpage = parseInt(nowpage) + 1;
	Data.page = nextpage;
	var totalpage = $("#totlepage").val();
	if(nowpage < totalpage){
		if(isLoading){
			return false;
		}
		isLoading = true;
		Data.billType = $("#ipt_billtype").val();
		Data.mortgageType =  $("#ipt_mortgagetype").val();
		Data.type =$("#ipt_type").val();
		Data.sortBy = $("#ipt_sortby").val();
		$.ajax({
			url : "${ctx}/weixin/product/ajaxProductList",
			async:false,
			type : 'post',
			dataType: 'html',
			data: Data,
			success : function(result){
				$("#productul").append(result);
				$("#nowpage").val(nextpage);
				$("#ipt_page").val(nextpage);
				if(nextpage == totalpage){
					$(".nomoreproducts").css("display", "block");
				}
			},
			complete : function() {
				setTimeout(function() {
					isLoading = false;
				}, 2000);
			}
		});
	}else if(totalpage>1){
		$(".nomoreproducts").css("display", "block");
	}
}
$(window).scroll(
	function() {
		setTimeout(function() {
			totalheight = parseFloat($(window).height())
					+ parseFloat($(window).scrollTop());
			if ($(document).height() <= totalheight) { //当文档的高度小于或者等于总的高度的时候，开始动态加载数据
				loadProductData();
			}
		}, 400);
	});
</script>
</head>
<body>
	<input type="hidden" id="nowpage" value="1" />
	<input type="hidden" id="totlepage" value="${totlepage}" />
	<form id = "request_form" action="${ctx }/weixin/product/list">
		<input type ="hidden" id ="ipt_billtype" value ="${billType }" name = "billType"/>
		<input type ="hidden" id ="ipt_mortgagetype" value ="${mortgageType }" name = "mortgageType"/>
		<input type ="hidden" id ="ipt_type" value ="${type }" name = "type"/>
		<input type ="hidden" id ="ipt_sortby" value ="${sortBy }" name = "sortBy"/>
		<input type = "hidden" id ="ipt_page" name = "page"/>
		<input type = "hidden" id ="isIndex" name ="isIndex" value="${isIndex }"/>
	</form>
	<div class="m_big">
	<c:if test = "${userFlag == 'all'}">
	<div class="loan">
        <ul>
        	<li id ="earncommission"<c:if test="${billType == 'earncommission' }">class="l_bj"</c:if>>赚提成</li>
        	<li id ="selfhelploan"<c:if test="${billType == 'selfhelploan'  }">class="l_bj"</c:if>>自助贷</li>
            <li id ="earndifference"<c:if test="${billType =='earndifference' }">class="l_bj"</c:if> >赚差价</li>
        </ul>
       		<div class="l_what" onclick="location.href='${ctx}/weixin/information/toArticleDetails/9'" ></div>
     </div>
     	
     </c:if>
     <c:if test = "${userFlag == 'user'}">
     	<div class="loan_text">
     		<p>自助贷
     		</p>
     		
     	</div>     	
     </c:if>
     <c:if test = "${userFlag == 'manager' }">
      <div class="loan_t">
       <ul>
       		<li id ="earncommission1"<c:if test="${billType == 'earncommission' }">class="l_bj"</c:if>>赚提成</li>
            <li id ="earndifference1"<c:if test="${billType =='earndifference' }">class="l_bj"</c:if> >赚差价</li>   
       </ul>
       		<div class="l_what" onclick="location.href='${ctx}/weixin/information/toArticleDetails/9'" ></div>
       </div>
      
     </c:if>
 		
     
     
     	<div class="mort">
         <ul>
             <li id = "mortgagetype">
             	<c:choose>
             		<c:when test="${mortgageType == 'NULLLOAN'}">无抵押贷</c:when>
             		<c:when test="${mortgageType == 'CREDITLOAN' }">信用贷</c:when>
             		<c:when test="${mortgageType == 'MORTGAGELOAN' }">抵押贷</c:when>
             		<c:otherwise>不限</c:otherwise>
             	</c:choose>
             </li>
             <li id = "producttype">
             	<c:choose>
	             	<c:when test="${type == '' }">不限</c:when>
	             	<c:otherwise>${type }</c:otherwise>
             	</c:choose>
            </li>
             <li id = "sorttype">
             	<c:if test="${sortBy == ''}">默认</c:if>
             	<c:if test="${sortBy == 'loanMinAmount'}">金额优先</c:if>
             </li>
         </ul>
         </div>
         </div>
         	<div id ="mortgagetype_select" class="m_drop hidden">
                 <ul>
                 	<a href="#"><li class = "mortgagetype <c:if test = "${mortgageType =='' }">m_bj</c:if>" data-type = "mortgageType" data-value = "" data-name = "不限" onclick = "changeValue(this)">不限</li></a>
                 	<a href="#"><li class=" mortgatetype <c:if test = "${mortgageType == 'NULLLOAN' }">m_bj</c:if>"  data-type = "mortgageType" data-value= "NULLLOAN" data-name = "无抵押贷" onclick = "changeValue(this)">无抵押贷</li></a>
                    <a href="#"><li class = "mortgagetype <c:if test = "${mortgageType =='CREDITLOAN' }">m_bj</c:if>" data-type = "mortgageType" data-value = "CREDITLOAN" data-name = "信用贷" onclick = "changeValue(this)">信用贷</li></a>
                    <a href="#"><li class = "mortgagetype <c:if test = "${mortgageType =='MORTGAGELOAN'}">m_bj</c:if>" data-type = "mortgageType" data-value = "MORTGAGELOAN" data-name = "抵押贷" onclick = "changeValue(this)">抵押贷</li></a>
                    
              	</ul>
            </div> 
            <div id ="type_select" class="m_drop hidden">
                 <ul>
                 	<a href="#"><li class = "producttype <c:if test = "${type == ''}">m_bj</c:if>" 
	                 					data-type = "productType" data-value ="" data-name = "不限" onclick = "changeValue(this)">不限</li></a>
                 	<c:forEach items= "${ productTypes}" var = "productType">
	                 	<a href="#"><li class = "producttype <c:if test = "${productType.productName eq type }">m_bj</c:if>" 
	                 					data-type = "productType" data-value ="${productType.productName }" data-name = "${productType.productName }" onclick = "changeValue(this)">${productType.productName }</li></a>
                    </c:forEach>
              	</ul>
            </div>
            <div id ="sortby_select" class="m_drop hidden">
                 <ul>
                 <a href="#"><li class = "sorttype <c:if test="${sortBy != 'loanMinAmount'}">m_bj</c:if>" data-type = "sortType" data-value = "" data-name = "默认" onclick = "changeValue(this)">默认</li></a>
                 	<a href="#"><li class = "sorttype <c:if test="${sortBy == 'loanMinAmount'}">m_bj</c:if>"
                 	 data-type = "sortType" data-value = "loanMinAmount" data-name = "金额优先" onclick = "changeValue(this)">金额优先</li></a>
              	</ul>
            </div>
         <div class="m_text">
         	<ul id = "productul">
         	<c:forEach items = "${products }" var = "product">
         		<c:if test="${product.type.billType.ordinal() != '0'}">
         		 <a href="${ctx }/weixin/product/productDetail/${product.id }">
	                 <dl>
	                     <dt>
		                     <c:if test = "${product.type.id =='1'||product.type.id =='2'|| product.type.id == '3' }">
		                     	<font><img src="${ctx }/static/brd-mobile/images/start.png"></font>
		                     </c:if>
	                     	${product.info.productName }
	                     </dt>
	                     <dd>
	                     	<c:if test = "${product.interestType.ordinal() != '1' }">
		                         <p>
		                             <font>${product.info.loanMinRate}%<c:if test = "${ not empty product.info.loanMaxRate }">~${product.info.loanMaxRate }%</c:if></font>
		                             <span>每月贷款利率</span>
		                         </p>
	                         </c:if>
	                         <c:if test = "${product.interestType.ordinal() == '1' }">
		                         <p>
		                             <font>${product.info.loanMinRate}‰<c:if test = "${ not empty product.info.loanMaxRate }">~${product.info.loanMaxRate }‰</c:if></font>
		                             <span>每日贷款利率</span>
		                         </p>
	                         </c:if>
	                         
	                          <%-- <p>
	                          	<c:if test = "${product.type.billType.ordinal() == '1' && product.interestType.ordinal() != '1'}">
	                          		 <samp><font>${util:showRateWithoutUnit(product.info.spreadMin) }-${util:showRateWithoutUnit(product.info.spreadMax) }%</font></samp>
	                          	</c:if>
	                          	<c:if test = "${product.type.billType.ordinal() == '1' && product.interestType.ordinal() == '1'}">
	                          		 <samp><font>${util:showThousandRateWithoutUnit(product.info.spreadMin) }-${util:showThousandRateWithoutUnit(product.info.spreadMax) }‰</font></samp>
	                          	</c:if>
	                          	<c:if test = "${product.type.billType.ordinal() == '2' && product.interestType.ordinal() != '3'}">
	                          		 <samp><font>${util:showRateWithoutUnit(product.info.percentageRate) }%</font></samp>
	                          	</c:if>
	                          	<c:if test = "${product.type.billType.ordinal() == '2' && product.interestType.ordinal() == '3'}">
	                          		 <samp><font>${util:showRateWithoutUnit(product.info.algoParamB) }%</font></samp>
	                          	</c:if>
	                            <c:if test = "${product.type.billType.ordinal() =='1'&& product.interestType.ordinal() != '2' }">
	                            	<samp>加价息差</samp>
	                            </c:if>
	                            <c:if test = "${product.type.billType.ordinal() =='1'&& product.interestType.ordinal() == '2' }">
	                            	<samp>约定费用价差</samp>
	                            </c:if>
	                            <c:if test = "${product.type.billType.ordinal() =='2'&& product.interestType.ordinal() != '3' }">
	                            	<samp>提成比例</samp>
	                            </c:if>
	                            <c:if test = "${product.type.billType.ordinal() =='2'&& product.interestType.ordinal() == '3' }">
	                            	<samp>佣金比例</samp>
	                            </c:if>
	                             
                         	 </p> --%>
                         	 <p>
                         	 	<samp><font>${product.info.fontBrokerageNum }</font></samp>
                         	 	<samp>${product.info.fontBrokerageDesc }</samp>
                         	 </p>
                         	 
                         	 <c:if test = "${product.interestType.ordinal() != '1' }">
	                         	 <p>
	                             	<samp>${util:showTenThousandPrice(product.info.loanMinAmount) }-${util:showTenThousandPrice(product.info.loanMaxAmount)}万元</samp>
	                             	<samp>${product.info.loanMinTime }<c:if test ="${product.info.loanMinTime != product.info.loanMaxTime }">-${product.info.loanMaxTime }</c:if>个月</samp>
	                         	</p>
	                         </c:if>
	                         <c:if test = "${product.interestType.ordinal() == '1' }">
	                         	 <p>
	                             	<samp>${util:showTenThousandPrice(product.info.loanMinAmount) }-${util:showTenThousandPrice(product.info.loanMaxAmount)}万元</samp>
	                             	<samp>${product.info.loanMinTime }<c:if test ="${product.info.loanMinTime != product.info.loanMaxTime }">-${product.info.loanMaxTime }</c:if>天</samp>
	                         	</p>
	                         </c:if>
	                     </dd>
	                 </dl>
                   </a>
                  </c:if>
                  
                  <!-- 自助贷-按月/收益金 -->
                  <c:if test = "${product.interestType.ordinal() != '1' && product.type.billType.ordinal() eq '0'}">
            		<a href="${ctx }/weixin/product/productDetail/${product.id }">
	                 <dl class="m_ziz">
	                     <dt>
	                     	<c:if test = "${product.type.id =='1'||product.type.id =='2'|| product.type.id == '3' }">
		                     	<font><img src="${ctx }/static/brd-mobile/images/start.png"></font>
		                     </c:if>
	                     	${product.info.productName }
	                     </dt>
	                     <dd>
	                         <p>
		                         <font>${product.info.loanMinRate}%<c:if test = "${ not empty product.info.loanMaxRate }">~${product.info.loanMaxRate }%</c:if></font>
		                         <span>每月贷款利率</span>
		                     </p>
	                         <p>
                             	<samp>${util:showTenThousandPrice(product.info.loanMinAmount) }-${util:showTenThousandPrice(product.info.loanMaxAmount)}万元</samp>
                             	<samp>${product.info.loanMinTime }<c:if test ="${product.info.loanMinTime != product.info.loanMaxTime }">-${product.info.loanMaxTime }</c:if>个月</samp>
                         	 </p>
	                     </dd>
	                   </dl>
                   </a>
                  </c:if>
                  <!-- 自助贷-按日 -->
                  <c:if test = "${product.interestType.ordinal() eq '1' && product.type.billType.ordinal() eq '0'}">
                  	<a href="${ctx }/weixin/product/productDetail/${product.id }">
	                 <dl class="m_ziz">
	                     <dt>
	                     	<c:if test = "${product.type.id =='1'||product.type.id =='2'|| product.type.id == '3' }">
		                     	<font><img src="${ctx }/static/brd-mobile/images/start.png"></font>
		                     </c:if>
	                     	${product.info.productName }
	                     </dt>
	                     <dd>
	                         <p>
		                         <font>${product.info.loanMinRate}‰<c:if test = "${ not empty product.info.loanMaxRate }">~${product.info.loanMaxRate }‰</c:if></font>
		                         <span>每日贷款利率</span>
		                     </p>
	                          <p>
                             	<samp>${util:showTenThousandPrice(product.info.loanMinAmount) }-${util:showTenThousandPrice(product.info.loanMaxAmount)}万元</samp>
                             	<samp>${product.info.loanMinTime }<c:if test ="${product.info.loanMinTime != product.info.loanMaxTime }">-${product.info.loanMaxTime }</c:if>天</samp>
                         	 </p>
	                     </dd>
	                   </dl>
                   </a>
                  </c:if>
                  
         	</c:forEach>
            </ul>
            <div class='clear'></div>
			<div style='height: 50px; text-align: center; display: none'
				class='nomoreproducts'>
				<br>无更多产品信息了
			</div>
			<div class="m_back"></div>
         </div>
       <jsp:include page="../common/footermenu.jsp?mod=dk"/>  
</body>
</html>