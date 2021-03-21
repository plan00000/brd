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
<title>我的贷款</title>
<style >
	.noOrder{
		margin-left:40%
	}
</style>
<script>
	document.title="我的贷款";
	$(function(){
		loadData();
		
		$("#All").attr("class","dian");
		$("#UNCHECKED").attr("class","");
		$("#UNLOAN").attr("class","");
		$("#LOANED").attr("class","");
		$("#INVALID").attr("class","");
		$("#status").val("All");
		
		$("#All").click(function(){
			$("#All").attr("class","dian");
			$("#UNCHECKED").attr("class","");
			$("#UNLOAN").attr("class","");
			$("#LOANED").attr("class","");
			$("#INVALID").attr("class","");
			$("#status").val("All");
			loadData();
		});
		

		$("#UNCHECKED").click(function(){
			$("#All").attr("class"," ");
			$("#UNCHECKED").attr("class","dian");
			$("#UNLOAN").attr("class","");
			$("#LOANED").attr("class","");
			$("#INVALID").attr("class","");
			$("#status").val("UNCHECKED");
			loadData();
		});
		
		$("#UNLOAN").click(function(){
			$("#All").attr("class"," ");
			$("#UNCHECKED").attr("class","");
			$("#UNLOAN").attr("class","dian");
			$("#LOANED").attr("class","");
			$("#INVALID").attr("class","");
			$("#status").val("UNLOAN");
			loadData();
		});
		
		$("#LOANED").click(function(){
			$("#All").attr("class"," ");
			$("#UNCHECKED").attr("class","");
			$("#UNLOAN").attr("class","");
			$("#LOANED").attr("class","dian");
			$("#INVALID").attr("class","");
			$("#status").val("LOANED");
			loadData();
		});
		$("#INVALID").click(function(){
			$("#All").attr("class"," ");
			$("#UNCHECKED").attr("class","");
			$("#UNLOAN").attr("class","");
			$("#LOANED").attr("class","");
			$("#INVALID").attr("class","dian");
			$("#status").val("INVALID");
			loadData();
		});
		
		function loadData() {
			
			var status =$("#status").val();
			if(status==null ||status=="undefined" || status.length==0){
				status="All";
			}		
			$.post("${ctx}/weixin/loan/myloanData",{status:status}, function (data){
				if(!data.exists){
					$("#htmlcontent").html("<span class='noOrder'>没有任何订单</span>");
				}else{
					if(data.status=="All"){
						$("#All").attr("class","dian");
						$("#UNCHECKED").attr("class","");
						$("#UNLOAN").attr("class","");
						$("#LOANED").attr("class","");
						$("#INVALID").attr("class","");
					}else if(data.status=="UNCHECKED") {
						$("#All").attr("class"," ");
						$("#UNCHECKED").attr("class","dian");
						$("#UNLOAN").attr("class","");
						$("#LOANED").attr("class","");
						$("#INVALID").attr("class","");
					} else if(data.status=="UNLOAN") {
						$("#All").attr("class"," ");
						$("#UNCHECKED").attr("class","");
						$("#UNLOAN").attr("class","dian");
						$("#LOANED").attr("class","");
						$("#INVALID").attr("class","");
					} else if(data.status=="LOANED"){
						$("#All").attr("class"," ");
						$("#UNCHECKED").attr("class","");
						$("#UNLOAN").attr("class","");
						$("#LOANED").attr("class","dian");
						$("#INVALID").attr("class","");
					} else if(data.status=="INVALID"){
						$("#All").attr("class"," ");
						$("#UNCHECKED").attr("class","");
						$("#UNLOAN").attr("class","");
						$("#LOANED").attr("class","");
						$("#INVALID").attr("class","dian");
					}
				 	var list = data.dto;
				 	var contentHtml ="";
				 	for(var i=0;i<list.length;i++){
				 		if(list[i].productType=='自助贷'){
					 			contentHtml += 
							 			"<div class='check'>"+
					                "<dl>"+
					                    "<dt>"+
					                        "<p>"+list[i].productName+"("+list[i].productType+")"+"</p>"+
					                        "<a href='${ctx}/weixin/loan/"+list[i].id+"/detail;JSESSIONID=<%=request.getSession().getId()%> '><span>"+list[i].statusStr+"</span></a>"+
					                    "</dt>"+
					                    "<dd>"+
					                        "<span>贷款金额：<font>"+list[i].money+"</font>万</span>"+
					                        "<span>贷款人："+list[i].name+"</span>"+
					                        "<span><font></font></span>"+
					                    "</dd>"+
					                "</dl>"+
					                "<a href='${ctx}/weixin/loan/"+list[i].id+"/progress;JSESSIONID=<%=request.getSession().getId()%> '> <div class='view'>查看进度</div></a>"+ 
					            "</div>" ;
				 		}else{
					 		contentHtml += 
						 			"<div class='check'>"+
				                "<dl>"+
				                    "<dt>"+
				                        "<p>"+list[i].productName+"("+list[i].productType+")"+"</p>"+
				                        "<a href='${ctx}/weixin/loan/"+list[i].id+"/detail;JSESSIONID=<%=request.getSession().getId()%> '><span>"+list[i].statusStr+"</span></a>"+
				                    "</dt>"+
				                    "<dd>"+
				                        "<span>贷款金额：<font>"+list[i].money+"</font>万</span>"+
				                        "<span>贷款人："+list[i].name+"</span>"+
				                        "<span>佣金：<font>"+list[i].brokerage+"</font>元</span>"+
				                    "</dd>"+
				                "</dl>"+
				                "<a href='${ctx}/weixin/loan/"+list[i].id+"/progress;JSESSIONID=<%=request.getSession().getId()%> '> <div class='view'>查看进度</div></a>"+ 
				            "</div>" ;
				 		}
					}  
					$("#htmlcontent").html(contentHtml); 
				}
			});
		}
		
	//jquery结束	
	});
	
</script>
</head>
<body>
       <input type="hidden" id="status" value="${status}"  />
	  <div class="content">
	  <div class="jindu">
            <ul>
                <a  href="#" ><li id="All" >全部</li></a>
                <a  href="#"><li id="UNCHECKED">待审核</li></a>
                <a  href="#"><li id="UNLOAN" >待放款</li></a>
                <a  href="#"><li id="LOANED" >已放款</li></a>
                <a  href="#"><li id="INVALID">失败</li></a>
            </ul>

            <div id="htmlcontent">
            
            </div>
        	</div>
       </div> 			
</body>
</html>