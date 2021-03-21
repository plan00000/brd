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
<title>提现明细</title>
<style>
	.noOrder{
		margin-left:40%
	}
</style>
<script>
	document.title="提现明细"
	$(function(){
		loadData();
		
		$("#All").attr("class","d_tal");
		$("#NOLENDING").attr("class","");
		$("#ALEARDYLOAN").attr("class","");
		$("#FAILCHECK").attr("class","");
		
		
		$("#All").click(function(){
			$("#All").attr("class","d_tal");
			$("#NOLENDING").attr("class","");
			$("#ALEARDYLOAN").attr("class","");
			$("#FAILCHECK").attr("class","");
			$("#status").val("All");
			loadData();
		})
		
		
		$("#NOLENDING").click(function(){
			$("#All").attr("class","");
			$("#NOLENDING").attr("class","d_tal");
			$("#ALEARDYLOAN").attr("class","");
			$("#FAILCHECK").attr("class","");
			$("#status").val("NOLENDING");
			loadData();
		});
		
		$("#ALEARDYLOAN").click(function(){
			$("#All").attr("class","");
			$("#NOLENDING").attr("class","");
			$("#ALEARDYLOAN").attr("class","d_tal");
			$("#FAILCHECK").attr("class","");
			$("#status").val("ALEARDYLOAN");
			loadData();
		});
		
		$("#FAILCHECK").click(function(){
			$("#All").attr("class","");
			$("#NOLENDING").attr("class","");
			$("#ALEARDYLOAN").attr("class","");
			$("#FAILCHECK").attr("class","d_tal");
			$("#ALEARDYLOAN").val("FAILCHECK");
			$("#status").val("FAILCHECK");
			loadData();
		});
		
		
		
		function loadData() {
			var status =$("#status").val();
			if(status==null ||status=="undefined" || status.length==0){
				status="All";
			}		
			var pageNumber= $("#pageNumber").val();
			$.post("${ctx}/weixin/withdraw/detail",{status:status}, function (data){
				if(!data.exists){
					$("#content").html("<span class='noOrder'>没有提现记录</span>");
				}else{
					$("#status").val(data.status);
					if(data.status=="All"){
						$("#All").attr("class","d_tal");
						$("#NOLENDING").attr("class","");
						$("#ALEARDYLOAN").attr("class","");
						$("#FAIL").attr("class","");
					}else if(data.status=="NOLENDING") {
						$("#All").attr("class","");
						$("#NOLENDING").attr("class","d_tal");
						$("#ALEARDYLOAN").attr("class","");
						$("#FAIL").attr("class","");
					} else if(data.status=="ALEARDYLOAN") {
						$("#All").attr("class","");
						$("#NOLENDING").attr("class","");
						$("#ALEARDYLOAN").attr("class","d_tal");
						$("#FAIL").attr("class","");
					} else if(data.status=="FAIL"){
						$("#All").attr("class","");
						$("#NOLENDING").attr("class","");
						$("#ALEARDYLOAN").attr("class","");
						$("#FAIL").attr("class","d_tal");
					}
				 	var list = data.dto;
				 	var contentHtml ="";
				 	for(var i=0;i<list.length;i++){
						if(list[i].statusStr=='已提现'){
									contentHtml += "<dl>"+
					                "<dt>"+
				                    "<p>"+list[i].statusStr+"</p>"+
				                    "<span>申请时间："+list[i].createTime+"</span>"+
				                "</dt>"+
					                "<dd>"+
					               	 	"<p>-"+list[i].money+"元</p>"+
					                    "<span>发放时间："+list[i].sendTime+"</span>"+
					                "</dd>"+
					           "</dl>"
						}else{
					 		if(list[i].remark==null){
								contentHtml += "<dl>"+
							                "<dt>"+
						                    "<p>"+list[i].statusStr+"</p>"+
						                    "<span>申请时间："+list[i].createTime+"</span>"+
						                "</dt>"+
						                "<dd>"+
						               	 	"<p>-"+list[i].money+"元</p>"+
						                    "<span></span>"+
						                "</dd>"+
						           "</dl>"
							}else{
								contentHtml += "<dl>"+
								                "<dt>"+
							                    "<p>"+list[i].statusStr+"</p>"+
							                    "<span>申请时间："+list[i].createTime+"</span>"+
							                "</dt>"+
							                "<dd>"+
							               	 	"<p>-"+list[i].money+"元</p>"+
							                    "<span>失败原因："+list[i].remark+"</span>"+
							                "</dd>"+
							           "</dl>"
							}
						}
					}  
					$("#content").html(contentHtml); 
				}
			});
		}
	});
</script>
</head>
<body>
			<input type="hidden" id="status" value="${status}"  />
        	<input type="hidden" id="pageNumber" value="1" />
	 <div class="details">
            <ul>
                <a href="#"><li id="All" >全部</li></a>
                <a href="#"><li id="NOLENDING" >提现中</li></a>
                <a href="#"><li id="ALEARDYLOAN"  >已提现</li></a>
                <a href="#"><li id="FAILCHECK">失败</li></a>
                
            </ul>
           	 	<div id="content" ></div>
	</div>
</body>
</html>