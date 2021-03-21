<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="util" uri="functions"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>部门统计</title>
<script type="text/javascript">
$(function() {
	activeNav2("9","9_5");
	$("#comfirm").click(function(){
		$("#request_form").submit();
	})
})
	function toEmployee(first,second,third) {
		location.href = "${ctx }/admin/employee/list?firstDepartmentId="+first+"&secondDepartmentId="+second+"&thirdDepartmentId="+third;
	}
	function toUser(departid,userType) {
		if(userType==2){
			location.href = "${ctx }/admin/user/list?userType=MANAGER&departid="+departid;
		}else{
			location.href = "${ctx }/admin/user/list?userType=SELLER&departid="+departid;
		}
	}
</script>
</head>
<body>
<div class="row border-bottom">
	<div class="basic">
        <p>报表统计</p>
        <span><a href="<c:url value='/admin/main'/>" style="margin-left:0;">首页</a>><a href="#" >报表统计</a>><a><strong>部门统计</strong></a></span>
    </div>
    </div>
     <div class="details animated fadeInRight">
        	<div class="products">
                <div class="products_title">
               		<p>当前订单总数：${map.allOrderCount }单</p>
                    <p>当前订单总额：${util:showTenThousandPrice(map.allOrderMoney)}万</p>
                    <p>昨日订单总数：${map.todayOrderCount }单</p>
                    <p>昨日订单总额：${util:showTenThousandPrice(map.todayOrderMoney)}万</p>
                </div>
                <div class="distributed">
                <table width="100%" cellpadding="0" cellspacing="0">
                    <tbody>
                    
                        <tr>
                            <td colspan="7">
                            <form id = "request_form"  action="${ctx}/admin/reportStatistics/departmentStatistics/departmentStatistics" method="get">
                                <div class="sandy">
                                    <p>部门</p>
                                  <select class="d_but03" name="groupid" id="groupid" style="width: 150px;">
										<option value="0">全部</option>
										<c:forEach items="${departs}" var="dp">
											<option value="${dp.id}">${dp.name}</option>
										</c:forEach>
									</select>
									<script type="text/javascript">
										$("#groupid option").each(function(){
											if($(this).val() == '${gid}'){
												$(this).attr("selected", "selected");
											}
										});
									</script>
                                    <span id="comfirm">搜索</span>
                                </div>
                                </form>
                            </td>
                        </tr>
                        <tr>
                            <td>部门</td>
                            <td>员工个数</td>
                            <td>商家数</td>
                            <td>融资经理</td>
                            <td>总订单数/订单额</td>
                            <td>昨日订单数/订单额</td>
                            <td>累计发放佣金</td>
                        </tr>
                       <c:forEach items="${dto}" var="d">
                        <tr>
                            <td>${d.departname}</td>
                            <td class="s_color">
                            	<a href="javascript:void(0)" onclick="toEmployee(${d.firstDepartmentId},${d.secondDepartmentId},${d.thirdDepartmentId})">${d.departUserCount }</a>
                            </td>
                            <td class="s_color">
                          	  <a href="javascript:void(0)" onclick="toUser(${d.departid},1)">${d.sellerCount }</a></td><!-- 跳到会员列表 -->
                            <td class="s_color">
                          	  <a href="javascript:void(0)" onclick="toUser(${d.departid},2)">${d.managerCount }</a></td><!-- 跳到会员列表 -->
                            <td>${d.totalTime}单/${util:showTenThousandPrice(d.totalMoney)}万</td>
                            <td>${d.yestodayTime}单/${util:showTenThousandPrice(d.yestodayMoney)}万</td>
                            <td>￥${d.addBrokerage}</td>
                        </tr> 
                        </c:forEach>              
                    </tbody>
                  </table>
                </div>
            </div>
          </div>
</body>
</html>
