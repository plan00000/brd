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
<script type="text/javascript" src="${ctx }/static/js/common.js"></script>
<script type="text/javascript" src="${ctx }/static/js/md5-min.js"></script>
<script src="${ctx}/static/js/input-number-change.js"></script>
<title>新增线路</title>
<script type="text/javascript">
	$(function() {
		activeNav("2");
		
		$(":radio").click(function(){
			var value = $(this).val();
			if(value==0){
				$("#user").css("display","block")
				$("#manger").css("display","none");
				$("#seller").css("display","none")
			}else if(value==1){
				$("#user").css("display","none")
				$("#manger").css("display","block");
				$("#seller").css("display","none")
			}else if(value==2){
				$("#user").css("display","none")
				$("#manger").css("display","none");
				$("#seller").css("display","block")
			}
		})
		
		
		
		$("#submitform").click(function(){
			submitAdd();
			
		});
		
		function submitAdd(){

			var startAddress =$("#startAddress").val();
            var endAddress =$("#endAddress").val();
			if(startAddress.length==0){
				alert("请输入起点");
				return ;
			}
            if(endAddress.length==0){
                alert("请输入终点");
                return ;
            }

			$("#submitform").unbind("click");
			$.post("${ctx}/admin/line/addLine",{startAddress:startAddress,endAddress:endAddress},function(data){
					if(data.code==1){
						showCallBackDialog("添加成功",function(){
							location.href = "${ctx}/admin/line/list";
						})
					}else{
						alert(data.mes);
						$("#submitform").bind("click",function(){
							submitAdd();
						});
					}
			}); 
		}
	});

	
</script>
</head>
<body>
<div class="row  border-bottom">
	<div class="basic">
        <p>新增线路</p>
        <span><a href="<c:url value='/admin/main'/>"  style="margin-left:0;">首页</a>><a href="#" >线路管理</a>><a><strong>新增线路</strong></a></span>
    </div>
    </div>
	<div class="new_emp animated fadeInRight">
            <div class="new_xinxi"><p><font>线路信息</font></p></div>
            <form id="myform" action="${ctx }/admin/line/addLine" >
	            <div class="new_text">
	                <div class="new_batt" >
						<dl>
							<dt>起点：</dt>
							<dd><input type="text" name="startAddress" id="startAddress" maxlength="31"  class="n_la" ></dd>
						</dl>
						<dl>
							<dt>终点：</dt>
							<dd><input type="text" name="endAddress" id="endAddress" maxlength="31"  class="n_la" ></dd>
						</dl>
	            </div>
	            </div>
            </form>
            <div class="submit" align="center">
                     <div class="submit_trr">
                        <a href="javascript:void(0)" id="submitform" ><p>提交</p></a>
                        <a href="${ctx}/admin/line/list"><p>返回</p></a>
                     </div>
                 </div>
        </div> 

</body>
</html>