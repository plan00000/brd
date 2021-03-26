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
<title>司机管理</title>
<script type="text/javascript">
$(function(){
	activeNav2("3","3_2");
	$("#sortcreateTime").click(function(){
		var id=$(this).data('id');
		if(id == 1){
			$("#ipt_sortName").val("orderTime");
			$("#ipt_sortType").val("asc");
			requestLoad();
		}else{
			$("#ipt_sortName").val("orderTime");
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
	<form id = "request_form" action ="${ctx}/admin/driver/list">
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
	   		<p>司机管理</p>
	        <span><a href="${ctx }/admin/main;JSESSIONID=<%=request.getSession().getId()%>"  style="margin-left:0;">首页</a>><a href="#" >司机管理</a>><a><strong>司机列表</strong></a></span>
	    </div>
    </div>
    <div class="employee animated fadeInRight ">
       <dl>
       	 <dt>司机状态：</dt>
         <dd>
         	<p class=" orderstatus <c:if test = "${status =='' }"> e_class</c:if>" data-type = "ordersStatus" data-value = "" onclick = "changeValue(this)">全部状态</p>
            <p class=" orderstatus <c:if test = "${status =='0' }"> e_class</c:if>" data-type = "ordersStatus" data-value = "0" onclick = "changeValue(this)">下班</p>
            <p class=" orderstatus <c:if test = "${status =='1' }"> e_class</c:if>" data-type = "ordersStatus" data-value = "1" onclick = "changeValue(this)">上班</p>
            </dd>
       </dl>
       <dl>
       	 <dt>查询：</dt>
         <dd>
         	<select id = "selectorder" class="e_inpt" style="margin:0 10px 0 0;">
            	<option value ="userName" >司机姓名</option>
                <option value ="carNO">车牌号</option>
                <option value ="idCard">身份证号</option>
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
			 <a href="${ctx}/admin/driver/toAddDriver" ><samp>新增司机</samp></a>
            <%--<samp id = "btn_excel">导出EXCEL</samp>--%><span class="e_loy">共<b>${totalcount }</b>条订单记录</span>
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
                    <td><strong>姓名</strong></td>
					 <td><strong>手机话号</strong></td>
                    <td><strong>身份证号</strong></td>
					 <td><strong>性别</strong></td>
                    <td><strong>驾驶证号</strong></td>
					 <td><strong>车牌号</strong></td>
					 <td><strong>车辆型号</strong></td>
					 <td><strong>颜色</strong></td>
					 <td><strong>状态</strong></td>
                     <td><strong>操作</strong></td>
                 </tr>
                 <c:forEach items = "${driverforms.content }" var ="driverforms">
                 	<tr>
                 		<td></td>
	                    <td>${driverforms.userName }</td>
						<td>${driverforms.mobileno }</td>
						<td>${driverforms.idCard }</td>
						<td class="l_ls">${driverforms.sex.getDes() }</td>
						<td>${driverforms.driverNo }</td>
						<td>${driverforms.carNo }</td>
						<td>${driverforms.carMark }</td>
						<td>${driverforms.carColor }</td>
	                    <td class="l_ls">${driverforms.driverStatus.getDes() }</td>
	                    <td><a href="${ctx }/admin/driver/toDriverEvalute/${driverforms.id}"><span><img src="${ctx }/static/brd/img/bjt1.png"></span></a></td>
                 	</tr>
                 </c:forEach>
                 
                 </tbody>
                 </table>
       			<div class=" m_right">
                     <tags:pagination paginationSize="10" page="${driverforms}" hrefPrefix="${ctx }/admin/driver/list" hrefSubfix="${queryStr}"></tags:pagination>
                 </div>
        </div>
        </div>
        </div>
        </div>
        </div>
    </div> 

</body>
</html>