<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="util" uri="functions"%>
<%@ taglib prefix="shiro" uri="http://www.springside.org.cn/tags/shiro"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html >
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>官网订单</title>
<script type="text/javascript">
$(function(){
	activeNav2("3","3_2");
	$("#sortcreateTime").click(function(){
		var id=$(this).data('id');
		if(id == 1){
			$("#ipt_sortName").val("createTime");
			$("#ipt_sortType").val("asc");
			requestLoad();
		}else{
			$("#ipt_sortName").val("createTime");
			$("#ipt_sortType").val("desc");
			requestLoad();
		}
	});
	$("#sortmoney").click(function(){
		var id=$(this).data('id');
		if(id == 1){
			$("#ipt_sortName").val("money");
			$("#ipt_sortType").val("asc");
			requestLoad();
		}else{
			$("#ipt_sortName").val("money");
			$("#ipt_sortType").val("desc");
			requestLoad();
		}
	});
	$("#sortactualMoney").click(function(){
		var id=$(this).data('id');
		if(id == 1){
			$("#ipt_sortName").val("actualMoney");
			$("#ipt_sortType").val("asc");
			requestLoad();
		}else{
			$("#ipt_sortName").val("actualMoney");
			$("#ipt_sortType").val("desc");
			requestLoad();
		}
	});
	$("#btn_search").click(function(){
		var searchName =$("#selectorder").val();
		var searchValue =$("#ipt_searchValue1").val();
		$("#ipt_searchName").val(searchName);
		$("#ipt_searchValue").val(searchValue);
		requestLoad();
	})
	$("#btn_excel").click(function(){
		window.open("${ctx}/admin/orderform/pc/export;JSESSIONID=<%=request.getSession().getId()%>?"
				+ "userType=${userType}&status=${status}&billType=${billType}&type=${type}"
				+ "&sortName=${sortName}&sortType=${sortType}&searchName=${searchName}&searchValue=${searchValue}");
	})
});
function changeValue(object){
	var $this = $(object);
	var type = $this.data("type");
	var value = $this.data("value");
	if(type== 'ordersStatus'){
		$(".orderstatus").removeClass('e_class');
		$this.addClass('e_class');
		$("#ipt_status").val(value);
		requestLoad();
	}
}
function requestLoad(){
	$("#request_form").submit();
}
</script>
</head>
<body>
	<form id = "request_form" action ="${ctx}/admin/orderform/pc/list">
		<input type ="hidden" id ="ipt_status" value ="${status}" name = "status"/>
		<input type ="hidden" id ="ipt_sortName" value ="${sortName }" name = "sortName"/>
		<input type ="hidden" id = "ipt_sortType" value = "${sortType}" name = "sortType"/>
		<input type ="hidden" id = "ipt_searchName" value = "${searchName }" name = "searchName"/>
		<input type ="hidden" id = "ipt_searchValue" value = "${searchValue }" name = "searchValue"/>
		<input type = "hidden" id ="ipt_page" name = "page"/>
		<%-- <input type = "hidden" id ="ipt_isMain" name = "${isMain }"/> --%>
	</form>
	<div class="row border-bottom">
		<div class="basic">
	   		<p>订单管理</p>
	        <span><a href="${ctx }/admin/main;JSESSIONID=<%=request.getSession().getId()%>"  style="margin-left:0;">首页</a>><a href="#" >订单管理</a>><a><strong>官网订单</strong></a></span>
	    </div>
    </div>
    <div class="employee animated fadeInRight ">
       <dl>
       	 <dt>订单状态：</dt>
         <dd>
         	<p class=" orderstatus <c:if test = "${status =='' }"> e_class</c:if>" data-type = "ordersStatus" data-value = "" onclick = "changeValue(this)">全部状态</p>
            <p class=" orderstatus <c:if test = "${status =='UNCONTACTED' }"> e_class</c:if>" data-type = "ordersStatus" data-value = "UNCONTACTED" onclick = "changeValue(this)">待联系</p>
            <p class=" orderstatus <c:if test = "${status =='UNTOLKWITH' }"> e_class</c:if>" data-type = "ordersStatus" data-value = "UNTOLKWITH" onclick = "changeValue(this)">待面谈</p>
            <p class=" orderstatus <c:if test = "${status =='UNCHECKED' }"> e_class</c:if>" data-type = "ordersStatus" data-value = "UNCHECKED" onclick = "changeValue(this)">待审核</p>
            <p class=" orderstatus <c:if test = "${status =='UNLOAN' }"> e_class</c:if>" data-type = "ordersStatus" data-value = "UNLOAN" onclick = "changeValue(this)">审核通过</p>
            <p class=" orderstatus <c:if test = "${status =='CHECKFAIL' }"> e_class</c:if>" data-type = "ordersStatus" data-value = "CHECKFAIL" onclick = "changeValue(this)">审核失败</p>
            <p class=" orderstatus <c:if test = "${status =='LOANED' }"> e_class</c:if>" data-type = "ordersStatus" data-value = "LOANED" onclick = "changeValue(this)">已放款</p>
            <p class=" orderstatus <c:if test = "${status =='INVALID' }"> e_class</c:if>" data-type = "ordersStatus" data-value = "INVALID" onclick = "changeValue(this)">无效订单</p>
         </dd>
       </dl>
       <dl>
       	 <dt>订单查询：</dt>
         <dd>
         	<select id = "selectorder" class="e_inpt" style="margin:0 10px 0 0;">
            	<option value ="productName" >订单名称</option>
                <option value ="orderNo">订单号码</option>
                <option value ="comtractNum">合同编号</option>
            </select>
            <script type="text/javascript">
				$("#selectorder option").each(function(){
					var a=$(this).val();
					if($(this).val() == "${searchName}"){
						$(this).attr("selected","selected");
					}
				});
			</script>
         	<input type="text" placeholder="输入要搜索的关键词" id ="ipt_searchValue1"class="e_sou" value = "${searchValue }">
            <samp id="btn_search">搜索</samp>
         </dd>
       </dl>
       <shiro:hasPermission name="USER_EXPORT">
       <dl>
       	 <dt>操作：</dt>
         <dd>
            <samp id = "btn_excel">导出EXCEL</samp><span class="e_loy">共<b>${totalcount }</b>条订单记录</span>
         </dd>
       </dl>
       </shiro:hasPermission>
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
                   <!--  <input type="checkbox" class="l_in"> -->
                    </td>
                    <td><strong>贷款名称</strong></td>
                    <td><strong>订单状态</strong></td>
                    <td><strong>手机号</strong></td>
                    <td>
                    <div class="level_triangle">
                    	<p><strong>下单时间</strong></p>
                    	<c:choose>
                    		<c:when test="${sortName eq 'createTime' && sortType eq 'desc' }">
                    			<span class="l_sanj2" id ="sortcreateTime" data-id = 1></span>
                    		</c:when>
                    		<c:when test="${sortName eq 'createTime' && sortType eq 'asc' }">
                    			<span class="l_sanj1" id ="sortcreateTime" data-id = 2></span>
                    		</c:when>
                    		<c:otherwise>
                    			<span class="l_sanj3" id ="sortcreateTime" data-id = 1></span>
                    		</c:otherwise>
                    	</c:choose>
                        
                    </div></td>
                    <td>
                    <div class="level_triangle">
                    	<p><strong>申请金额</strong></p>
                       <c:choose>
                    		<c:when test="${sortName eq 'money' && sortType eq 'desc' }">
                    			<span class="l_sanj2" id ="sortmoney" data-id = 1></span>
                    		</c:when>
                    		<c:when test="${sortName eq 'money' && sortType eq 'asc' }">
                    			<span class="l_sanj1" id ="sortmoney" data-id = 2></span>
                    		</c:when>
                    		<c:otherwise>
                    			<span class="l_sanj3" id ="sortmoney" data-id = 1></span>
                    		</c:otherwise>
                    	</c:choose>
                    </div></td>
                    <td>
                    <div class="level_triangle">
                    	<p><strong>实际金额</strong></p>
                        <c:choose>
                    		<c:when test="${sortName eq 'actualMoney' && sortType eq 'desc' }">
                    			<span class="l_sanj2" id ="sortactualMoney" data-id = 1></span>
                    		</c:when>
                    		<c:when test="${sortName eq 'actualMoney' && sortType eq 'asc' }">
                    			<span class="l_sanj1" id ="sortactualMoney" data-id = 2></span>
                    		</c:when>
                    		<c:otherwise>
                    			<span class="l_sanj3" id ="sortactualMoney" data-id = 1></span>
                    		</c:otherwise>
                    	</c:choose>
                    </div></td>
                    <td><strong>操作</strong></td>
                 </tr>
                 <c:forEach items = "${orderforms.content }" var ="orderform">
                 	<tr>
                 		<td></td>
	                    <td>${orderform.productInfo.productName }</td>
	                    <td class="l_ls">${orderform.status.getDes() }</td>
	                    <td>${orderform.telephone }</td>
	                    <td>${util:formatNormalDate(orderform.createTime) }</td>
	                    <td>${util:showTenThousandPrice(orderform.money) }万元</td>
	                    <td>${util:showTenThousandPrice(orderform.actualMoney) }万元</td>
	                    <td><a href="${ctx }/admin/orderform/pc/detail/${orderform.id}"><span><img src="${ctx }/static/brd/img/bjt1.png"></span></a></td>
                 	</tr>
                 </c:forEach>
                 
                 </tbody>
                 </table>
       			<div class=" m_right">
                     <tags:pagination paginationSize="10" page="${orderforms}" hrefPrefix="${ctx }/admin/orderform/pc/list" hrefSubfix="${queryStr}"></tags:pagination>
                 </div>
        </div>
        </div>
        </div>
        </div>
        </div>
    </div> 

</body>
</html>