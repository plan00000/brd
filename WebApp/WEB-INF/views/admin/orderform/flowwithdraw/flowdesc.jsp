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
<script src="${ctx}/static/js/input-number-change.js"></script>
<title>佣金体现详情</title>
<script type="text/javascript">
$(function(){
	activeNav2("3","3_4");
	/** 审核通过*/
	$("#btn_checkPass").click(function(){
		
		$("#btn_checkPass").removeClass('c_ant');
		$("#btn_checkPass").addClass('c_ant1');
		var data = {};
		data.check = "pass";
		data.flowWithdrawId = ${flowWithdraw.id};
		$.post('${ctx}/admin/orderform/flowwithdraw/changeStatus;JSESSIONID=<%=request.getSession().getId()%>',data,function(res){
			if(res.code==1){
				alert("操作成功");
				location.reload();
			}else{
				alert(res.mes);
			}
		})
	});
	$("#btn_checkRefuse").click(function(){
		$("#btn_checkRefuse").removeClass('c_ant');
		$("#btn_checkRefuse").addClass('c_ant1');
		$("#div_refuse").removeClass('hidden');
		
	})
	$("#btn_refuseOk").click(function(){
		if(!check()){
			return ;
		}
		var data = {};
		data.refuse = $("#ipt_refuse").val().trim();
		data.check = "refuse";
		data.flowWithdrawId = ${flowWithdraw.id};
		$.post('${ctx}/admin/orderform/flowwithdraw/changeStatus;JSESSIONID=<%=request.getSession().getId()%>',data,function(res){
			if(res.code==1){
				alert("操作成功");
				location.reload();
			}else{
				alert(res.mes);
			}
		});
	});
	$("#btn_refuseCancel").click(function(){
		$("#div_refuse").addClass('hidden');
		$("#btn_checkRefuse").removeClass('c_ant1');
		$("#btn_checkRefuse").addClass('c_ant');
	})
	
	$("#btn_hadSend").click(function(){
		$("#btn_hadSend").addClass('grass');
		$("#btn_noSend").removeClass('grass');
		$("#div_withdraw").removeClass('hidden');
	});
	$("#btn_sendbankOk").click(function(){
		$("#div_withdraw").addClass('hidden');
		var data = {};
		data.flowWithdrawId = ${flowWithdraw.id};
		$.post('${ctx}/admin/orderform/flowwithdraw/changeStatus;JSESSIONID=<%=request.getSession().getId()%>',data,function(res){
			if(res.code==1){
				alert("操作成功");
				location.reload();
			}else{
				alert(res.mes);
			}
		});
	})
	$("#btn_nosendbank").click(function(){
		$("#div_withdraw").addClass('hidden');
		location.reload();
	})
	/** 关闭按钮 */
	$("#btn_withdrawclose").click(function(){
		$(".refuse_box").addClass('hidden');
	})
})
function check(){
	var refuse = $("#ipt_refuse").val().trim();
	if(refuse.length == 0){
		alert("拒绝原因不能为空");
		return false;
	}
/* 	if(refuse.length >100){
		alert("拒绝原因不能超过100字");
		return false;
	} */
	return true;
}
</script>
</head>
<body>
	<div class="row border-bottom">
	 	<div class="basic">
	      	<p>订单管理</p>
	        <span><a href="${ctx }/admin/main;JSESSIONID=<%=request.getSession().getId()%>"  style="margin-left:0;">首页</a>><a href="#" >订单管理</a>><a href="#" >提现订单</a>><a><strong>提现详情</strong></a></span>
	    </div>
    </div>
	<div class="details animated fadeInRight">
 	<div class="new_xinxi"><p><font>提现信息</font></p></div>
         <div class="details_box d_xqy">
         <ul>
        		 <li>
                 <p>会员名：</p>
                 <span class="d_ls"><a href = "${ctx}/admin/user/toEditUser/${flowWithdraw.user.id}">${flowWithdraw.user.username }</a></span>
             </li>
             <li>
                 <p>申请时间：</p>
                 <span>${util:formatNormalDate(flowWithdraw.createTime)}</span>
             </li>
             <li>
                 <p>手机号码：</p>
                 <span>${ flowWithdraw.telephone}</span>
             </li>
             <li>
                 <p>申请金额：</p>
                 <span>${flowWithdraw.money }元</span>
             </li>
             <li>
                 <p>开户银行：</p>
                 <span>${ flowWithdraw.bankname}</span>
             </li>
             <li>
                 <p>开卡地区：</p>
                 <span>${flowWithdraw.province }&nbsp;&nbsp;${flowWithdraw.city }</span>
             </li>
        </ul>
        </div>
        <div class="details_box d_xqy">
         <ul>
        		<li>
                 <p>会员身份：</p>
                 <span>${flowWithdraw.user.userType.getStr() }</span>
             </li>
             <li>
                 <p>提现编号：</p>
                 <span>${flowWithdraw.flowno }</span>
             </li>
             <li style="margin-top:30px;">
                 <p>提现账户：</p>
                 <span>${flowWithdraw.bankaccount }</span>
             </li>
             <li>
                 <p>开户名：</p>
                 <span>${flowWithdraw.accountname }</span>
             </li>
            </ul>
        </div>
        <div class="new_xinxi"><p><font>操作</font></p></div>
            <div class="commission <c:if test = "${ flowWithdraw.status !='NOCHECK'}">hidden</c:if>" >
                <dl>
                    <dt>提现审核：</dt>
                    <dd>
                        <font class="c_ant1" style=" margin-right:30px;" id = "btn_checkPass">通过</font>
                        <font class="c_ant" id = "btn_checkRefuse">拒绝</font>
                    </dd>
                </dl>
            </div>
            <%-- <div class="commission <c:if test = "${flowWithdraw.status != 'FAILCHECK'}">hidden</c:if>" >
                <dl>
                    <dt>提现审核：</dt>
                    <dd>
                        <font class="c_ant" style=" margin-right:30px;">通过</font>
                        <font class="c_ant1">拒绝</font>
                    </dd>
                </dl>
            </div>  --%>
            <div class="t_om <c:if test = "${flowWithdraw.status != 'FAILCHECK' }">hidden</c:if>" >
                     <dl>
                          <dt>提现审核：</dt>
                          <dd>已拒绝</dd>
                     </dl>
                     <dl>
                          <dt>拒绝原因：</dt>
                          <dd>${flowWithdraw.rejectReason }</dd>
                     </dl>
                </div>
            <div class="through <c:if test = "${ flowWithdraw.status.ordinal() !='1'}">hidden</c:if>">
                <dl>
                     <dt>提现审核：</dt>
                     <dd>已通过</dd>
                </dl>
                <dl>
                	<dt>提现状态：</dt>
                	<dd>
                     <font class="<c:if test = "${flowWithdraw.status.ordinal() != '1'}">grass</c:if>" id = "btn_noSend" style="cursor:default;">待放款</font>
                     <font class = "<c:if test = "${flowWithdraw.status.ordinal() != '3'}">grass</c:if>" style="margin-left:30px;" id = "btn_hadSend">已放款</font>
                 </dd>
                </dl>
            </div>
            <div class="through <c:if test = "${flowWithdraw.status.ordinal() !='3'}">hidden</c:if>">
                <dl>
                     <dt>提现审核：</dt>
                     <dd>已通过</dd>
                </dl>
                <dl>
                     <dt>提现状态：</dt>
                     <dd>已提现</dd>
                </dl>
            </div>
            <div class="refuse_box hidden" id = "div_refuse">
                <div class="refuse">
                	<form>
                  <p>请输入审核拒绝原因:</p>
                  <textarea name="refuse" id = "ipt_refuse" class="r_text" 
                  onKeyDown="gbcount(this.form.refuse,this.form.used1,100);"  onKeyUp="gbcount(this.form.refuse,this.form.used1,100);" onblur = "gbcount(this.form.refuse,this.form.used1,100);"></textarea>
                  <input class="inputtext2 inputtextn" name="used1"  id="trefuse" value="0/100" readonly="readonly">
                      <samp>
                          <a href="#" id = "btn_refuseOk">确定</a>
                          <a href="#" class="r_anniu" id = "btn_refuseCancel">取消</a>
                      </samp>
                      </form>
                </div>
            </div> 
            
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
            <c:forEach items = "${ orderOperLogs.content}" var = "orderOperLog">
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
		<tags:pagination paginationSize="10" page="${orderOperLogs}" hrefPrefix="${ctx }/admin/orderform/flowwithdraw/detail/${flowWithdraw.id }" hrefSubfix=""></tags:pagination> 
    	</div>
   </div>
   <div class="refuse_box hidden" id = "div_withdraw">
   		<div class="withdraw" >
                <p><font id = "btn_withdrawclose"></font></p>
                <div class="withdraw_text">
                    <span>是否已提现到用户银行卡？</span>
                    <samp id = "btn_sendbankOk">是的，已发</samp>
                    <samp class="w_right" id = "btn_nosendbank">还未发放</samp>
            	</div>
         </div>
   </div>
 </div> 
</body>
</html>