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
<title>佣金统计</title>
<script type="text/javascript">
$(function() {
	activeNav2("9","9_6");
})
	function toEmployee(userId) {
		location.href = "${ctx }/admin/employee/toEditEmployee/"+userId;
	}
	function toUser(userId) {
		location.href = "${ctx }/admin/user/toEditUser/"+userId;
	}
	function toOrder(orderId) {
		location.href = "${ctx }/admin/orderform/brokerage/detail/"+orderId;
	}
</script>
</head>
<body>
<div class="row border-bottom">
	<div class="basic">
        <p>报表统计</p>
        <span><a href="<c:url value='/admin/main'/>" style="margin-left:0;">首页</a>><a href="#" >报表统计</a>><a><strong>佣金统计</strong></a></span>
    </div>
    </div>
     <div class="details animated fadeInRight">
        	<div class="products">
            	<div class="d_ation">
                    <table width="100%" cellpadding="0" cellspacing="0">
                        <tbody>
                            <tr>
                            	<td width="30%">订单提成佣金：￥${map.orderBrokerage }</td>
                                <td rowspan="2" class="d_ion">已发放佣金总额： ￥${map.totalBrokerage }</td>
                                <td width="30%">已提现：￥${map.haveBrokerage }</td>
                            </tr>
                            <tr>
                            	<td>活动奖励佣金：￥${map.activityBrokerage }</td>
                                <td>未提现：￥${map.brokerageCanWithdraw }</td>
                            </tr>
                        </tbody>
                    </table>
                </div>
                <div class="distributed d_class">
                <table width="100%" cellpadding="0" cellspacing="0">
                    <tbody>
                    	<tr>
                            <td colspan="4" class="d_but01">佣金总额TOP10业务员</td>
                        </tr>
                        <tr>
                            <td>序号</td>
                            <td>用户名</td>
                            <td>部门</td>
                            <td>累计佣金</td>
                        </tr>
                      <c:forEach items="${page1.getContent()}" var="item1" varStatus="status">
                        <tr>
                            <td>${status.index + 1}</td>
                            <td class="s_color">
                            	<a href="javascript:void(0)" onclick="toEmployee(${item1.userId})">${item1.username }</a>
                            </td>
                            <td>${item1.departname }</td>
                            <td>￥${item1.addBrokerage }</td>
                        </tr>
                      </c:forEach>         
                    </tbody>
                  </table>
                </div>
                <div class="distributed d_class">
                <table width="100%" cellpadding="0" cellspacing="0">
                    <tbody>
                    	<tr>
                            <td colspan="4" class="d_but01">佣金总额TOP10会员</td>
                        </tr>
                        <tr>
                            <td>序号</td>
                            <td>会员帐号</td>
                            <td>会员身份</td>
                            <td>累计佣金</td>
                        </tr>
                   <c:forEach items="${page2.getContent()}" var="item2" varStatus="status">
                        <tr>
                            <td>${status.index + 1}</td>
                            <td class="s_color">
                         	   <a href="javascript:void(0)" onclick="toUser(${item2.userId})">${item2.username }</a>
                            </td>
                            <td>${item2.userType }</td>
                            <td>￥${item2.addBrokerage }</td>
                        </tr>
                   </c:forEach>             
                    </tbody>
                  </table>
                </div>
                <div class="distributed d_class d_right">
                <table width="100%" cellpadding="0" cellspacing="0">
                    <tbody>
                    	<tr>
                            <td colspan="4" class="d_but01">单笔佣金金额TOP5</td>
                        </tr>
                        <tr>
                            <td>序号</td>
                            <td>订单号</td>
                            <td>下单人</td>
                            <td>获得佣金</td>
                        </tr>
      				<c:forEach items="${page3.getContent()}" var="item3" varStatus="status">
                        <tr>
                            <td>${status.index + 1}</td>
                            <td class="s_color">
                             <a href="javascript:void(0)" onclick="toOrder(${item3.orderId})">${item3.orderNo }</a>
                            </td>
                            <td class="s_color">
                            	 <a href="javascript:void(0)" onclick="toUser(${item3.userId})">${item3.username }</a>
                            </td>
                            <td>￥${item3.addBrokerage }</td>
                        </tr>
                   </c:forEach>  
                    </tbody>
                  </table>
                </div>
            </div>
          </div>
</body>
</html>
