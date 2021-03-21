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
<title>提现订单</title>
<script type="text/javascript">
$(function(){
	activeNav2("3","3_4");
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
	$("#btn_search").click(function(){
		var searchName =$("#selectorder").val();
		var searchValue =$("#ipt_searchValue1").val();
		$("#ipt_searchName").val(searchName);
		$("#ipt_searchValue").val(searchValue);
		requestLoad();
	})
	$("#btn_excel").click(function(){
		window.open("${ctx}/admin/orderform/flowwithdraw/export;JSESSIONID=<%=request.getSession().getId()%>?"
				+"userType=${userType}&status=${status}&sortName=${sortName}&sortType=${sortType}&searchName=${searchName}&searchValue=${searchValue}");
	})
});
function changeValue(object){
	var $this = $(object);
	var type = $this.data("type");
	var value = $this.data("value");
	if(type== 'userType'){
		$(".usertype").removeClass('e_class');
		$this.addClass('e_class');
		$("#ipt_usertype").val(value);
		requestLoad();
	}
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
	<form id = "request_form" action ="${ctx}/admin/orderform/flowwithdraw/list">
		<input type ="hidden" id = "ipt_usertype" value = "${userType }" name = "userType">
		<input type ="hidden" id ="ipt_status" value ="${status}" name = "status"/>
		<input type ="hidden" id ="ipt_sortName" value ="${sortName }" name = "sortName"/>
		<input type ="hidden" id = "ipt_sortType" value = "${sortType}" name = "sortType"/>
		<input type ="hidden" id = "ipt_searchName" value = "${searchName }" name = "searchName"/>
		<input type ="hidden" id = "ipt_searchValue" value = "${searchValue }" name = "searchValue"/>
		<input type = "hidden" id ="ipt_page" name = "page"/>
		<input type = "hidden" id ="ipt_isMain" name = "${isMain }"/>
	</form>
	<div class="row border-bottom">
		<div class="basic">
	    	<p>订单管理</p>
	        <span><a href="${ctx }/admin/main;JSESSIONID=<%=request.getSession().getId()%>" style="margin-left:0;">首页</a>><a href="#">订单管理</a>><a><strong>提现订单</strong></a></span>
	    </div>
    </div>
    <div class="employee animated fadeInRight">
    	<dl>
	       	 <dt>会员身份：</dt>
	         <dd>
	         	<p class = "usertype <c:if test = "${userType =='' }"> e_class</c:if>" data-type = "userType" data-value = "" onclick = "changeValue(this)">全部身份</p>
	            <p class = "usertype <c:if test = "${userType =='USER' }"> e_class</c:if>" data-type = "userType" data-value = "USER" onclick = "changeValue(this)">普通会员</p>
	            <p class = "usertype <c:if test = "${userType =='MANAGER' }"> e_class</c:if>" data-type = "userType" data-value = "MANAGER" onclick = "changeValue(this)">融资经理</p>
	            <p class = "usertype <c:if test = "${userType =='SELLER' }"> e_class</c:if>" data-type = "userType" data-value = "SELLER" onclick = "changeValue(this)">商家</p>
	            <p class = "usertype <c:if test = "${userType =='SALESMAN' }"> e_class</c:if>" data-type = "userType" data-value = "SALESMAN" onclick = "changeValue(this)">业务员</p>
	         </dd>
       	</dl>
       <dl>
       	 <dt>订单状态：</dt>
         <dd>
         	<p class=" orderstatus <c:if test = "${status =='' }"> e_class</c:if>" data-type = "ordersStatus" data-value = "" onclick = "changeValue(this)">全部状态</p>
            <p class=" orderstatus <c:if test = "${status =='NOCHECK' }"> e_class</c:if>" data-type = "ordersStatus" data-value = "NOCHECK" onclick = "changeValue(this)">待审核</p>
            <p class=" orderstatus <c:if test = "${status =='NOLENDING' }"> e_class</c:if>" data-type = "ordersStatus" data-value = "NOLENDING" onclick = "changeValue(this)">待放款</p>
            <p class=" orderstatus <c:if test = "${status =='ALEARDYLOAN' }"> e_class</c:if>" data-type = "ordersStatus" data-value = "ALEARDYLOAN" onclick = "changeValue(this)">已提现</p>
            <p class=" orderstatus <c:if test = "${status =='FAILCHECK' }"> e_class</c:if>" data-type = "ordersStatus" data-value = "FAILCHECK" onclick = "changeValue(this)">提现失败</p>
         </dd>
       </dl>
       <dl>
       	 <dt>订单查询：</dt>
         <dd>
         	<select id = "selectorder" class="e_inpt" style="margin:0 10px 0 0;">
            	<option value ="mobileno" >会员账号</option>
                <option value ="flowno">订单号码</option>
            </select>
            <script type="text/javascript">
				$("#selectorder option").each(function(){
					var a=$(this).val();
					if($(this).val() == "${searchName}"){
						$(this).attr("selected","selected");
					}
				});
			</script>
         	<input type="text" placeholder="输入要搜索的关键词" id ="ipt_searchValue1"class="e_sou" value = "${searchValue}">
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
                    </td>
                    <td><strong>会员名</strong></td>
                    <td><strong>会员账号</strong></td>
                    <td>
                    <div class="level_triangle">
                    	<p><strong>申请时间</strong></p>
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
                    <td><strong>订单状态</strong></td>
                    <td><strong>操作</strong></td>
                 </tr>
                 <c:forEach items = "${flowWithdraws.content }" var ="flowWithdraw">
                 	<tr>
                 		<td></td>
	                    <td>${flowWithdraw.user.username }</td>
	                    <td>${flowWithdraw.user.mobileno }</td>
	                    <td class="l_ls">${util:formatNormalDate(flowWithdraw.createTime)}</td>
	                    <td>${flowWithdraw.money }元</td>
	                    <td>${flowWithdraw.status.getDes() }</td>
	                    <td><a href="${ctx }/admin/orderform/flowwithdraw/detail/${flowWithdraw.id}"><span><img src="${ctx }/static/brd/img/bjt1.png"></span></a></td>
                 	</tr>
                 </c:forEach>
                 </tbody>
                 </table>
       			 <div class=" m_right">
                     <tags:pagination paginationSize="10" page="${flowWithdraws}" hrefPrefix="${ctx }/admin/orderform/flowwithdraw/list" hrefSubfix="${queryStr}"></tags:pagination>
                 </div>
        </div>
        </div>
        </div>
        </div>
        </div>
    </div> 

</body>
</html>