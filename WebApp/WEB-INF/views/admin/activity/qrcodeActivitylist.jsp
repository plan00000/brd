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
<title>二维码活动</title>
<script src="${ctx}/static/js/input-number-change.js"></script>
<script type="text/javascript">
$(function(){
	activeNav2("5","5_4");
	$("#btn_add").click(function(){
		location.href = "${ctx}/admin/qrCodeActivity/toAdd;JSESSIONID=<%=request.getSession().getId()%>";
	});
	
	$("#btn_url").click(function(){
      location.href = "${ctx}/admin/qrCodeActivity/getUrl;JSESSIONID=<%=request.getSession().getId()%>";
	})

  $("#btn_search").click(function() {
    location.href = "${ctx}/admin/qrCodeActivity/toSearchUser;JSESSIONID=<%=request.getSession().getId()%>";
  });
})
</script>
</head>
<body>
	<div class="row border-bottom">
		<div class="basic">
	     <p>活动管理</p>
	     <span><a href="<c:url value='/admin/main'/> style="margin-left:0;">首页</a>><a href="#">活动管理</a>><a><strong>二维码活动</strong></a></span>
	    </div>
    </div>
       <div class="employee animated fadeInRight e_chan">
          <dl>
          	 <dt>操作：</dt>
            <dd><samp id= "btn_add">新增二维码</samp><samp id= "btn_search">查询微信号</samp></dd>
          </dl>
           </div>
           <div class="username-text animated fadeInRight">
           <div class="level l_repost">
           <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
                <tbody>
               	 <tr>
                       <td width="230"><strong>名称</strong></td>
                       <td width="230"><strong>二维码</strong></td>
                       <td width="230"><strong>添加时间</strong></td>
                       <td width="230"><strong>扫码数</strong></td>
					   <td width="230"><strong>关注数</strong></td>
                       <td width="230"><strong>操作</strong></td>
                       <td></td>
                    </tr>
                    <c:forEach items = "${qrcodeActivitys.content}" var = "qrcodeActivity">
	                    <tr>
	                    	<td>${ qrcodeActivity.name}</td>
	                        <td><img style = "width: 50px; height:50px" src="${ctx}/files/displayPro?filePath=${qrcodeActivity.qrcode}&thumbWidth=40&thumbHeight=40"/></td>
	                        <td>${util:formatNormalDate(qrcodeActivity.createTime) }</td>
							<td>${qrcodeActivity.scanNum }</td>
							<td>${qrcodeActivity.concernNum }</td>
	                        <td>
								<a href = "${ctx }/admin/qrCodeActivity/downLoadCode/${qrcodeActivity.id}"><samp style = "background-color: #1ab394 ;font: 14px/30px 'microsoft yahei'; color: #fff ;cursor: pointer; padding: 0 5px; display: inline-block;">下载二维码</samp></a>
	                            <a href="${ctx }/admin/qrCodeActivity/toEdit/${qrcodeActivity.id}"><samp style = "margin-left:10px; background-color: #1ab394 ;font: 14px/30px 'microsoft yahei'; color: #fff ;cursor: pointer; padding: 0 5px; display: inline-block;" >编辑</samp></a>
								<a href="${ctx}/admin/qrCodeActivity/toDetail/${qrcodeActivity.id}"><samp style="margin-left:10px; background-color: #1ab394 ;font: 14px/30px 'microsoft yahei'; color: #fff ;cursor: pointer; padding: 0 5px; display: inline-block;">详细</samp> </a>
	                        </td>
	                        <td></td>
	                    </tr> 
                    </c:forEach> 
                     
                </tbody>
           </table>
           <div class="text-right">
				<div class="btn-group">
					<div class="dataTables_paginate paging_simple_numbers"
						id="DataTables_Table_0_paginate">
						<tags:pagination page="${qrcodeActivitys}" paginationSize="10"
							hrefSubfix=""
							hrefPrefix="${ctx}/admin/qrCodeActivity/list" />
					</div> 
				</div>
		  </div>
      <!--username-text-->
       </div> 
    </div>
</body>
</html>