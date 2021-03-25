<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="util" uri="functions"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<%@ taglib prefix="shiro" uri="http://www.springside.org.cn/tags/shiro"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<!--  The Load Image plugin is included for the preview images and image resizing functionality  -->
<script
	src="${ctx}/static/js/jquery-fileupload-9.8.1/js/vendor/load-image.all.min.js"></script>
<!-- The Canvas to Blob plugin is included for image resizing functionality -->
<script
	src="${ctx}/static/js/jquery-fileupload-9.8.1/js/vendor/canvas-to-blob.min.js"></script>
<!-- The Iframe Transport is required for browsers without support for XHR file uploads -->
<script
	src="${ctx}/static/js/jquery-fileupload-9.8.1/js/jquery.iframe-transport.js"></script>
<!-- The basic File Upload plugin -->
<script
	src="${ctx}/static/js/jquery-fileupload-9.8.1/js/jquery.fileupload.js"></script>
<!-- The File Upload processing plugin -->
<script
	src="${ctx}/static/js/jquery-fileupload-9.8.1/js/jquery.fileupload-process.js"></script>
<!-- The File Upload image preview & resize plugin -->
<script
	src="${ctx}/static/js/jquery-fileupload-9.8.1/js/jquery.fileupload-image.js"></script>
<script
	src="${ctx}/static/js/jquery-fileupload-9.8.1/js/jquery.fileupload-validate.js"></script>
<script 
	src="${ctx }/static/js/md5-min.js" type="text/javascript" ></script>
<script type="text/javascript">
(function($){
    $.fn.UIdialog = $.fn.dialog; /* 把jquery-ui的dialog另存为UIdialog */
})(jQuery);
</script>
<link rel="stylesheet" type="text/css"
	href="${ctx}/static/js/jquery-easyui-1.4.3/themes/default/easyui.css"
	media="all" />
<link rel="stylesheet" type="text/css"
	href="${ctx}/static/js/jquery-easyui-1.4.3/themes/icon.css" media="all" />
<script src="${ctx}/static/js/jquery-easyui-1.4.3/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${ctx}/static/js/jquery-easyui-1.4.3/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript">
(function($){
    $.fn.Udialog = $.fn.dialog; /* 把jquery-ui的dialog另存为UIdialog */
})(jQuery);
(function($){
	$.fn.dialog = $.fn.UIdialog; /* 把jquery-ui的dialog另存为UIdialog */
})(jQuery);
</script>
<title>会员详情</title>
<script type="text/javascript">
	$(function() {
		activeNav("2");
	    $('#userimage_input').fileupload({
	    	acceptFileTypes : /(\.|\/)(jpg|jpe?g|png|bmp)$/i,
	    	url : '${ctx}/files/uploadimage;JSESSIONID=<%=request.getSession().getId()%>',
	    	dataType : 'json',
	    	formData : {
				type : 0
			},
	        previewMaxWidth: 160,
	        previewMaxHeight: 80,
			messages: {
		        acceptFileTypes: '文件格式错误',
	     	},
		   	done:function(e,data){
		    	if(data.result.files[0].error==null){
					$.each(data.result.files, function(index, file) {
						hideLoading();
						$("#userimage_img").attr('src', '${ctx}'+file.url); 
						$('#userimage_url').attr("value", '${ctx}'+file.url);
						//上传成功后进行提交到后台
						$.post('${ctx}/admin/user/modifyUserImage;JSESSIONID=<%=request.getSession().getId()%>',$('#submit_form').serialize(),function(res){
							if(res.code==1){
								showCallBackDialog("头像修改成功",function() {
									location.href = "${ctx}/admin/user/toEditUser/${users.id}";
								})
							}else{
								alert(res.mes);
							}
						})
					});
				}else{
					hideLoading();
					alert(data.files[0].error);					
				}
		    }
	    });	    
	  //上传文件按钮点击事件
		$('.touxiang').click(function(){
			$('#userimage_input').click();
		});
		//失败处理
		$('#userimage_input').bind('fileuploadprocessfail', function (e, data) {
			hideLoading();
			alert(data.files[0].error);	
		});
		
		//进度设置
		$('#userimage_input').bind('fileuploadprogress', function (e, data) {
			 showLoading();
		}); 
		
		$(".changeState").click(function() {
			var $this = $(this);
			var state = $(this).attr("state");
			var operation ="open";
			var userId = $(this).attr("changeId");
			var poststate = "ON";
			if(state==operation){
				operation="close";
				poststate ="OFF"
			}
			$.post("${ctx}/admin/employee/changeState",{id:userId,state:poststate},function(data){
				if(data==0){
					if (state == 'open') {
						$this.html("禁用");
						$this.removeClass("btn-primary");
						$this.addClass("btn-danger");
						$this.attr("state", "close");
					} else {
						$this.html("启用");
						$this.removeClass("btn-danger");
						$this.addClass("btn-primary");
						$this.attr("state", "open");
					}
				}
			}) 
		});
		
		var type = '${type}';
		var e_was1 = $("#e_was1");
		var e_was2 = $("#e_was2");
		var e_was3 = $("#e_was3");
		var e_was4 = $("#e_was4");
		if(type=='brokerage'){
			e_was1.attr("class","r_bj");
		}else if (type=='withdraw'){
			e_was2.attr("class","r_bj");
		}else if (type=='apprentice'){
			e_was3.attr("class","r_bj");
		}else if(type=='loginlog'){
			e_was4.attr("class","r_bj");
		}
		
		
		$("#e_was1").click(function(){
			location.href="${ctx}/admin/user/toEditUser/${userId}?type=brokerage"
		});
		$("#e_was2").click(function(){
			location.href="${ctx}/admin/user/toEditUser/${userId}?type=withdraw"
		});
		$("#e_was3").click(function(){
			location.href="${ctx}/admin/user/toEditUser/${userId}?type=apprentice"
		});
		$("#e_was4").click(function(){
			location.href="${ctx}/admin/user/toEditUser/${userId}?type=loginlog"
		});
		
		
		
		$("#onState").click(function() {
			var userId = '${users.id}';
			var poststate = "ON";
			$.post("${ctx}/admin/user/changeState",{id:userId,state:poststate},function(data){
				if(data==0){
					$("#onState").attr("class","dongjie");
					$("#onState1").attr("class","");					
				}
			
			}) 
		});
		
		$("#onState1").click(function() {
			var userId = '${users.id}';
			var poststate = "OFF";
			
			$.post("${ctx}/admin/user/changeState",{id:userId,state:poststate},function(data){
				if(data==0){
					$("#onState").attr("class","");
					$("#onState1").attr("class","dongjie");					
				}
			
			}) 
		});
		
		$("#offState").click(function() {
			var userId = '${users.id}';
			var poststate = "OFF";
			
			$.post("${ctx}/admin/user/changeState",{id:userId,state:poststate},function(data){
				if(data==0){
					$("#offState").attr("class","dongjie");
					$("#offState1").attr("class","");	
				}
			
			})  
		});
		
		$("#offState1").click(function() {
			var userId = '${users.id}';
			var poststate = "ON";
			
			$.post("${ctx}/admin/user/changeState",{id:userId,state:poststate},function(data){
				if(data==0){
					$("#offState").attr("class","");
					$("#offState1").attr("class","dongjie");	
				}
			
			})  
		});
		
		$("#modifyMobile").click(function(){
			var phone = $("#mobileno").val();
			$("#modifymobile").css("display","none");
			$.post("${ctx}/admin/user/modifyPhone",{phone:phone,userId:'${users.id}'},function(data){
				if(data.code==0){
					showCallBackDialog("修改成功",function(){
						location.href="${ctx}/admin/user/toEditUser/${users.id}";
					})
				}else{
					showCallBackDialog(data.mes,function(){
						$("#modifymobile").css("display","block");
					})
				}
				
				
			});
			
			
		})
		
		$("#modifyAddress").click(function(){
			var address = $("#address").val();
			$("#modifyaddress").css("display","none");
			$.post("${ctx}/admin/user/modifyAddress",{address:address,userId:'${users.id}'},function(data){
				if(data.code==0){
					showCallBackDialog("修改成功",function(){
						location.href="${ctx}/admin/user/toEditUser/${users.id}";
					})
				}else{
					showCallBackDialog(data.mes,function(){
						$("#modifyaddress").css("display","block");
					})
				}
			}); 
		});
		
		$("#modifyPassword1").click(function(){
			var newPassword = $("#password").val();
			$("#modifyPassword").css("display","none");
			if(newPassword.length == 0) {
				showCallBackDialog("密码不能为空",function(){
					$("#modifyPassword").css("display","block");
				})
			}
			newPassword =hex_md5(newPassword);
			$.post("${ctx}/admin/user/modifyPassword",{id:'${users.id}', password:newPassword},function(data){
				if(data.code ==0) {
					showCallBackDialog("修改成功",function(){
						location.href="${ctx}/admin/user/toEditUser/${users.id}";
					})
				} else {
					showCallBackDialog(data.mes,function(){
						$("#modifyPassword").css("display","block");
					})
				}
				
			});
			
		});
		
		$("#addRemark").click(function(){
		    var remark=$("#remark").val();
		    if(remark.trim().length>30){
		    	$("#addRemarks").css("display","none");
		    	showCallBackDialog("备注不能超过30个字符",function(){
		    		$("#addRemarks").css("display","block");
				})
		    	return ;
		    }
		    if(remark.trim().length==0){
		    	$("#addRemarks").css("display","none");
		    	showCallBackDialog("请输入备注",function(){
		    		$("#addRemarks").css("display","block");
				})
		    	return ;
		    }
		    $.post("${ctx}/admin/user/addRemarks",{remark:remark,userId:'${users.id}'},function(data){
				$("#addRemarks").css("display","none");
		    	if(data.code==0){
					showCallBackDialog("添加成功",function(){
						location.href="${ctx}/admin/user/toEditUser/${users.id}";
					})
				}else{
					showCallBackDialog(data.mes,function(){
						$("#addRemarks").css("display","block");
					})
				}
		    });
		    
		})
		//分配业务员
		$("#bindSalesman").click(function(){
			var salesmanMobile = $("#ipt_salesmanMobile").val();
			 if(salesmanMobile.trim().length==0){
		    	$("#bindsalesman").css("display","none");
		    	showCallBackDialog("请输入业务员号码",function(){
		    		$("#bindsalesman").css("display","block");
				})
		    	return ;
		    } 
			$.post("${ctx}/admin/user/bindSalesman",{salesmanMobile:salesmanMobile,userId:'${users.id}'},function(data){
				$("#bindsalesman").css("display","none");
				if(data.code==1){
					showCallBackDialog("分配成功",function(){
						location.href="${ctx}/admin/user/toEditUser/${users.id}";
					})
				}else{
					showCallBackDialog(data.mes,function(){
						$("#bindsalesman").css("display","block");
					})
				}
				
				
			});
			
		});
		
		$("#changeUserType").click(function(){
			var id ='${users.id}';
			var userType = $("input[name='userType']:checked").val();
			var companyname = $("#companyname").val();
			var showAlert =$("#showAlert");
			var address = $("#address").val();
			var idcard = $("#inputidcard").val();
			if(userType=='SELLER'){
				if(companyname.trim().length==0){
					showAlert.html("<span style='color:red'>请输入公司名称!</span>");
					return ;
				}
				if(companyname.trim().length>15){
					showAlert.html("<span style='color:red'>公司名称不能超过15个字符</span>");
					return ;
				}
				if(address.trim().length==0){
					showAlert.html("<span style='color:red'>请输入公司地址!</span>");
					return ;
				}
				
			} else{
				if(idcard.trim().length==0 || idcard.trim().length!=6){
					showAlert.html("<span style='color:red'>请输入身份证后6位</span>");
					return ;
					
				}	
			}		
			$("#modifyUserType").css("display","none")
			$.post("${ctx}/admin/user/changeUserType",{userId:id,userType:userType,companyname:companyname,address:address,idcard:idcard},function(data){
				if(data.code==0){
					showCallBackDialog(data.mes,function(){
						location.href="${ctx}/admin/user/toEditUser/${users.id}";
					});
				}else {
					showCallBackDialog(data.mes,function(){
						$("#modifyUserType").css("display","block")
					})
				}
			})
		});
		
		$("#apprentice").click(function(){
			$("#dialog_tt").dialog({
				  closeText: "关闭",
				  width: 1200,
				  heigth: 500,
				  resizable: false,
				  title:"徒弟",
				  close:function(event,ui){
					  location.href="${ctx}/admin/user/toEditUser/${users.id}";
				  },
			});
			pagegetSons('getSons'); 
			
		 });
		
		  $("#grandSon").click(function(){
			  $("#dialog_tt").dialog({
				  closeText: "关闭",
				  width: 1200,
				  heigth: 500,
				  resizable: false,
				  title:"徒孙",
				  close:function(event,ui){
					  location.href="${ctx}/admin/user/toEditUser/${users.id}";
				  },
			});
			pagegetSons('getGrandSons');
		});
		
		$("#ggrandSon").click(function(){
			$("#dialog_tt").dialog({
				  closeText: "关闭",
				  width: 1200,
				  heigth: 500,
				  resizable: false,
				  modal: true,
				  title:"徒孙孙",
				  close:function(event,ui){
					  location.href="${ctx}/admin/user/toEditUser/${users.id}";
				  },
			});
			pagegetSons('getGgrandSons');

		});   
		
		
		$("#companyname").mousedown(function(){
			$("#showAlert").html("");			
		})
		
		$("#address").mousedown(function(){
			$("#showAlert").html("");			
		})
		
		$("#showIdcard").mousedown(function(){
			$("#showAlert").html("");			
		})
		
		//下载二维码
		$("#btn_download").click(function(){
			var id= $(this).attr("downloadId");
			$.post("${ctx}/admin/user/check/"+ id,{},function(res){
				if(res=="0"){
					showCallBackDialog("该商家已不存在！",function(){
						location.href = "${ctx}/admin/user/list";
					});
				}else{
					location.href="${ctx }/admin/user/downLoadCode/"+id;
				}
			})
		});
		
		///jquery结束
	});
	
	 function pagegetSons(type){
		 $("#tt").treegrid({  
		        url : "${ctx}/admin/user/getSons",//首次查询路径  
		        animate : true,
		        queryParams:{"id":'${users.id}',"flag":true,"isFirst":true,"i":0,"getType":type},//首次查询参数         
		        idField : 'id',
		        treeField : 'realname',
	            columns : [ [{  
		            field : "realname",  
		            title : "姓名",  
		        },{  
		            field : "phone",  
		            title : "手机号码",  
		        },{  
		            field : "idcard",  
		            title : "身份证",  
		        },{  
		            field : "recommend",  
		            title : "推荐码",  
		        },{  
		            field : "named",  
		            title : "称呼",  
		        },{  
		            field : "sonSum",  
		            title : "徒弟数量",  
		        } ,{  
		            field : "grandSonsSum",  
		            title : "徒孙数量",  
		        } ,{  
		            field : "ggrandSonsSum",  
		            title : "徒孙孙数量",  
		        },{  
		            field : "orderSum",  
		            title : "我的订单总数",  
		        },{  
		            field : "orderMoney",  
		            title : "我的订单总额",  
		        } ,{  
		            field : "sonOrderSum",  
		            title : "徒弟订单总数",  
		        } ,{  
		            field : "sonOrderMoney",  
		            title : "徒弟订单总额",  
		        } ,{  
		            field : "grandSonOrderSum",  
		            title : "徒孙订单总数",  
		        } ,{  
		            field : "grandSonOrderMoney",  
		            title : "徒孙订单总额",  
		        }  ,{  
		            field : "ggrandSonOrderSum",  
		            title : "徒孙孙订单总数",  
		        } ,{  
		            field : "ggrandSonOrderMoney",  
		            title : "徒孙孙订单总额",  
		        }     
		        ] ]  
		    });
	} 
	
	 
	function showCompany(){
		$("#showCompany").css("display","block");
		$("#showIdcard").css("display","none");
		$("#showAlert").html("");
	} 
	function hiddenCompany(){
		$("#showIdcard").css("display","block")
		$("#showCompany").css("display","none");
		$("#showAlert").html("");
	}
	
	
	function checktext(text){
	    allValid = true;
	    for (i = 0;  i < text.length;  i++)
	    {
	      if (text.charAt(i) != " ")
	      {
	        allValid = false;
	        break;
	      }
	    }
		return allValid;
	}

	function gbcount(message,used,max){
	  if (message.value.length > max) {
	  	message.value = message.value.substring(0,max);
	 	 used.value = max+"/"+max;
		// alert("输入框最大值为"+max);
		  }
	 	 else {
	  	used.value = message.value.length+"/"+max;
	 	}
	}

	function gbcount1(message,used,max,mes){
		  if (message.value.length > max) {
		  	message.value = message.value.substring(0,max);
		 	 used.value = max+"/"+max;
			 alert(mes);
			  }
		 	 else {
		  	used.value = message.value.length+"/"+max;
		 	}
		}
	
</script>
</head>
<body>
<div class="row  border-bottom">
	<div class="basic">
        <p>会员管理</p>
        <span><a href="<c:url value='/admin/main'/>"  style="margin-left:0;">首页</a>><a href="#" >会员管理</a>><a><strong>${users.username }</strong></a></span>
    </div>
</div>	
	<div class="details animated fadeInRight">
            <div class="new_xinxi"><p><font>会员信息</font></p></div>
                <div class="details_text">
                    <dl>
                    	<dt>
					        <c:choose>
								<c:when test="${empty users.getHeadimgurl()}">
									<p><img src="${ctx }/static/brd/img/bj.png"></p>      			
								</c:when>
								<c:otherwise>
									<img id="userimage_img" src="${ctx}/files/displayProThumbTemp?filePath=${users.getHeadimgurl()}&thumbWidth=94&thumbHeight=94"/>        					          						     		
								</c:otherwise>
							</c:choose>
                    	</dt>
                    	<form id="submit_form">
                    		<input type="hidden" name="id" value="${users.id }" />
							<input type="file" id="userimage_input"  style="display: none;" accept="image/*"/>
							<input type="hidden" name="headimgurl" id="userimage_url" />
						</form>
                    	<dd class="touxiang" >选择图片</dd>
                    </dl>
                    <div class="details_box" style="margin-left:7%;">
                    <ul>
                        <li>
                            <p>会员账号：</p>
                            <span>${users.mobileno}</span>
                        </li>
                        <li>
                            <p>会员名：</p>
                            <span>${users.username }</span>
                        </li>
                        <li>
                            <p>姓名：</p>
                            <span>${users.realname }</span>
                        </li>
                        <li>
                        	<p>当前状态：</p>
                            <c:choose>
									<c:when test="${users.state.ordinal() eq 0 }">
										<a href="#"><span id="onState" >启用</span> </a>
                            			<a href="#"><span id="onState1" class="dongjie">禁用</span></a>
									</c:when>			
									<c:otherwise>
										<a href="#"><span id="offState1" class="dongjie">启用</span></a>
                            			<a href="#"><span id="offState" >禁用</span></a>
									</c:otherwise> 
							</c:choose> 
                        </li>
                        <li>
                            <p>手机号码：</p>
                            <span id="mobilephone" >${users.mobileno }</span>
                            <input type="hidden" value='${users.mobileno}' id="phone" />
                          	<samp colspan="3" data-toggle="modal" data-target="#modifymobile" >修改</samp>
	                            <div class="modal fade"  id="modifymobile" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	                            	 <div class="modal-dialog" style="width:430px; height:500px" >
										<div class="modal-content" style="width:430px;margin:auto">
									      <div class="modal-header">
									        <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
									        <h4 dclass="modal-title" class="i_ios" >修改手机号码</h4>
									      </div>
									      <div class="modal-body">
												<input type="text" name="mobileno" id="mobileno" value="${users.mobileno }" >
									      </div>
									      <div class="modal-footer ">
									        <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
									        <button type="button" id="modifyMobile" class="btn btn-primary">提交</button>
									      </div>
									    </div>
	                            	</div> 
	                       </div>	
                        </li>
                        <li>
                            <p>注册时间：</p>
                            <span>${util:formatNormalDate(users.createdate) }</span>
                        </li>
                         <c:if test="${users.userType eq 'SELLER' }" >
	                        <li>
	                            <p>详细地址：</p>
	                            <span class="d_add" >${users.userInfoSeller.address}</span>
	                            <samp class="d_a01" data-toggle="modal" data-target="#modifyaddress"  >修改</samp>
	                            <div class="modal fade"  id="modifyaddress" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	                            	 <div class="modal-dialog" style="width:430px; height:500px" >
										<div class="modal-content" style="width:430px;margin:auto">
									      <div class="modal-header">
									        <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
									        <h4 class="modal-title" class="i_ios" >修改商家地址</h4>
									      </div>
									      <div class="modal-body">

									      		<textarea  style="width:265px;height:100px" name="address" id="address" >${users.userInfoSeller.address}</textarea>
									      </div>
									      <div class="modal-footer ">
									        <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
									        <button type="button" id="modifyAddress" class="btn btn-primary">提交</button>
									      </div>
									    </div>
	                            	</div> 
	                       </div>	
	                        </li>
                        </c:if>
                        <c:if test="${users.userType ne 'USER' }" >
                        	 <li>
	                            <p>收徒二维码：</p>
	                            <span >
	                            	<img src="${ctx}/files/displayPro?filePath=${users.userInfoBoth.qrCode}&thumbWidth=160&thumbHeight=160"/>
	                            </span>
	                            <samp colspan="3" data-toggle="modal" id = "btn_download" downloadId="${users.id}" class="d_a02" >下载二维码</samp>
	                        </li>
                        </c:if>
                   </ul>
                   </div>
                   <div class="details_box">
                    <ul>
                        <li>
                            <p>会员身份：</p>
                            <span>${users.userType.getStr()}</span>
                            <shiro:hasPermission name="USER_UPGRADE">
                            	<samp data-toggle="modal" data-target="#modifyUserType" >更改身份</samp>
                            </shiro:hasPermission>
                            <div class="modal fade"  id="modifyUserType" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	                            	 <div class="modal-dialog" style="width:430px; height:500px" >
										<div class="modal-content" style="width:430px;margin:auto">
									      <div class="modal-header">
									        <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
									        <h4 class="modal-title" class="i_ios" style="color:red" >更改身份后不可撤销更改,请谨慎操作。</h4>
									      </div>
									      <div class="modal-body">
									      	<c:if test="${users.userType eq 'USER' }" >
									      		<p>请选择身份:</p>							      		
									      		<span ><input type="radio" name="userType"  value="SELLER" onclick="showCompany()"  class="i_ios">商家 </span>
									      		<span><input type="radio" name="userType" checked="checked" onclick="hiddenCompany()"  value="MANAGER" class="i_ios">融资经理</span>									      	
									      		<p style="display:none" id="showCompany">
					                       			<span>公司名称:<input type="text" id="companyname" name="companyname"> </span> <br />
					                       			<br />
					                       			<span>公司地址:<input type="text" id="address" name="address"> </span>
					                       		</p>
					                       		<p id="showIdcard">
					                       			<span>身份证号:<input type="text" id="inputidcard" name="inputidcard"> </span> <br />
					                       		</p>	
									      	</c:if>
									      	<c:if test="${users.userType eq 'MANAGER' }" >
									      		<p>请选择身份:</p>
									      		<span ><input type="radio" value="SELLER" checked="checked"  name="userType" class="i_ios">商家 </span>
									      		<p id="showCompany">
					                       		<span>公司名称:<input type="text" id="companyname" name="companyname"> </span> <br />
					                       		<span>公司地址:<input type="text" id="address" name="address" > </span>
					                       	</p>
									      	</c:if>
									      	<c:if test="${users.userType eq 'SALESMAN' }" >
					                        	<p>已经是业务员，无法修改身份</p>
					                        </c:if>
					                       	<c:if test="${users.userType eq 'SELLER' }" >
					                       		<p>已经是商家，无法修改身份</p>
					                       	</c:if>	
					                       	 <div id="showAlert" >
									      		
									      	</div>				                       	
									      </div>
									     
									      <div class="modal-footer ">
									        <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
									        	<c:if test="${users.userType ne 'SELLER' && users.userType ne 'SALESMAN' }" >
									        		<button type="button" id="changeUserType" class="btn btn-primary">提交</button>
									        	</c:if>
									      </div>
									    </div>
	                            	</div> 
	                       </div>	
                        </li>
                        
                        <li>
                            <c:if test="${users.userType eq 'SELLER' }" >
                            	 <p>公司名称：</p>
                            <span>${users.userInfoSeller.company }</span>
                            </c:if>
                             <c:if test="${users.userType ne 'SELLER' }" >
                              <p>身份证号：</p>
                            <span>${users.userInfoBoth.expands }</span>
                            </c:if>
                        </li>
                        <li>
                            <p>推荐码：</p>
                            <span>
                            <c:if test="${users.userType ne 'USER' }" >
                            ${users.userInfoBoth.recommendCode }
                            </c:if>
                            </span>
                        </li>
                        <li>
                            <p>微信号：</p>
                            <span>${users.weixinUser.nickname}</span>
                        </li>
                        <li>
                            <p>绑定银行卡：</p>
                            <span class="d_ls">${userBank.size() }张</span>
                            <!-- <samp>查看</samp> -->
                            <samp data-toggle="modal" data-target="#muserBankinfo" >查看</samp>
                            <div class="modal fade" id="muserBankinfo" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	                            	<div class="modal-dialog"  >
										<div class="modal-content" style="width:500px;margin:auto">
									      <div class="modal-header">
									        <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
									        <h4 class="modal-title" class="i_ios" >绑定银行卡</h4>
									      </div>
									      <div class="modal-body">
									      	<table width="100%" cellpadding="0" cellspacing="0">
									      		<tbody>
									      			<tr>
									      				<td><strong>持卡人</strong></td>
									      				<td><strong>开户银行</strong></td>
									      				<td><strong>开卡地区</strong></td>
									      				<td><strong>卡号</strong></td>
									      			</tr>
									      			<c:forEach var="list" items="${bankinfoList }">
									      				<tr bgcolor="#f2f2f2" >
									      					<td>${list.accountname }</td>
									      					<td>${list.bankname}</td>
									      					<td>${list.province}--${list.city }</td>
									      					<td>${list.bankaccount }</td>
									      				</tr>
									      			</c:forEach>
									      		</tbody>
									      	</table>
									      </div>
									      <div class="modal-footer">
									        <button type="button" class="btn btn-primary" data-dismiss="modal">关闭</button>
									      </div>
									    </div>
	                            	</div> 
	                            	
	                            </div>
                        </li>
                        <li>
                            <p>登录次数：</p>
                            <span >${users.loginTimes }</span>
                        </li>
                        <li>
                        	<p>修改登录密码:</p>
                        	<span>
                        		<input type="hidden" value='${users.mobileno}' id="phone" />
	                          	<samp colspan="3" data-toggle="modal" data-target="#modifyPassword" >修改</samp>
		                            <div class="modal fade"  id="modifyPassword" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		                            	 <div class="modal-dialog" style="width:430px; height:500px" >
											<div class="modal-content" style="width:430px;margin:auto">
										      <div class="modal-header">
										        <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
										        <h4 dclass="modal-title" class="i_ios" >修改登录密码</h4>
										      </div>
										      <div class="modal-body">
													<input type="password" name="password" id="password" placeholder="请输入新的登录密码" >
										      </div>
										      <div class="modal-footer ">
										        <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
										        <button type="button" id="modifyPassword1" class="btn btn-primary">提交</button>
										      </div>
										    </div>
		                            	</div> 
		                       		</div>	
							</span>
                        </li>
                   </ul>
                   </div>
                </div>
                <div class="new_xinxi"><p><font>其他信息</font></p></div>
                <div class="details_text" style="height:265px">
                    <dl></dl>
                    <div class="details_box" style="margin-left:7%;">
                    <ul>
                        <li>
                            <p>推荐人：</p>
                            <span >
                            	 <c:choose>
                            		<c:when test="${not empty users.userInfoBoth.parent.username }" >
                            			${users.userInfoBoth.parent.username }
                            		</c:when>
                            		<c:otherwise>
                            			无
                            		</c:otherwise>
                            	</c:choose> 
                            </span>
                        </li>
                        <li>
                            <p>所属业务员：</p>
                            <span>
                            <c:choose>
                            	<c:when test="${not empty users.userInfoBoth.salesman.username }">
                            		${users.userInfoBoth.salesman.username }
                            	</c:when>
                            	<c:otherwise>
                            		<c:if test = "${users.userType.ordinal() == '2' }">
                            			<samp data-toggle="modal" data-target="#bindsalesman" >分配</samp>
                            			<div class="modal fade"  id="bindsalesman" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
			                            	 <div class="modal-dialog" style="width:430px; height:500px" >
												<div class="modal-content" style="width:430px;margin:auto">
											      <div class="modal-header">
											        <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
											        <h4 class="modal-title" class="i_ios" style="color:red" >分配后不可撤销更改，请谨慎操作。</h4>
											      </div>
											      <div class="modal-body">
														<span>业务员手机号:<input type="text" name="salesmanMobile" id="ipt_salesmanMobile" ></span>
											      </div>
											      <div class="modal-footer ">
											        <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
											        <button type="button" id="bindSalesman" class="btn btn-primary">提交</button>
											      </div>
											    </div>
			                            	</div> 
	                       				</div>
                            		</c:if>
                            		<c:if test = "${users.userType.ordinal() != '2' }">
                            		无
                            		</c:if>
                            	</c:otherwise>
                            </c:choose>
                            </span>
                        </li>
                        <li>
                            <p>徒弟数：</p>
                            <a href="javascript:void(0)"><span class="d_ls" id="apprentice"  >${users.userInfoBoth.sonSum }</span></a>
                        </li>
                        <li>
                            <p>徒孙孙：</p>
                            <a href="javascript:void(0)"><span class="d_ls" id="ggrandSon" >${users.userInfoBoth.ggsonsSum }</span></a>
                        </li>
                        <li>
                            <p>提交订单：</p>
                            <span>${users.userInfoBoth.orderSum }</span>
                        </li>
                        <li>
                        	<p>订单总额：</p>
                        	<span>${util:showTenThousandPrice(users.userInfoBoth.orderMoney) }万元</span>
                        </li>
                        <li>
                            <p>佣金总额：</p>
                            <span>${total }元</span>
                        </li>
                   </ul>
                   </div>
                   
                   <div class="details_box">
                    <ul>
                        <li>
                            <p>所属商家：</p>
                            <span >
                            <c:choose>
                            	<c:when test="${not empty users.userInfoBoth.seller.username }">
                            		${users.userInfoBoth.seller.username }
                            	</c:when>
                            	<c:otherwise>
                            		无
                            	</c:otherwise>
                            </c:choose>
                            </span>
                        </li>
                        <li style="margin-top:28px;"></li>
                        <li>
                            <p>徒孙数：</p>
                           <a href="javascript:void(0)"> <span id="grandSon" class="d_ls">${users.userInfoBoth.grandsonSum }</span> </a>
                        </li>
                        <li style="margin-top:30px;"></li>
                        <li>
                            <p>成交订单：</p>
                            <span>${users.userInfoBoth.orderSuccessSum }</span>
                        </li>
                        <li style="margin-top:30px;"></li>
                        <li>
                            <p>提现：</p>
                            <span>已提现${users.userInfoBoth.brokerageHaveWithdraw }元，余额${users.userInfoBoth.brokerageCanWithdraw }元</span>
                        </li>
                   </ul>
                   </div>
                </div>
                <div class="remarks">
                	<table width="100%" cellpadding="0" cellspacing="0">
                    	<tbody>
                        	<tr>
                            	<td class="font">备注信息：</td>
                            	<td ><span colspan="3" data-toggle="modal" data-target="#addRemarks">添加</span></td>
	                            <div class="modal fade"  id="addRemarks" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	                            	 <div class="modal-dialog" style="width:430px; height:500px" >
										<div class="modal-content" style="width:430px;margin:auto">
									      <div class="modal-header">
									        <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
									        <h4 class="modal-title" class="i_ios" >添加备注</h4>
									      </div>
									      <form>
									      <div class="modal-body">
									     <!--  	<textarea name="remark" id="remark" cols=40 rows=4  ></textarea>  -->
			                           <textarea name="remark"  id="remark" cols=50 rows=4
			                        	onKeyDown="gbcount(this.form.remark,this.form.used,30);" 
							 			onKeyUp="gbcount(this.form.remark,this.form.used,30);" ></textarea>
							 			<font> <input class="inputtext1 inputtextn" style="float: right;text-align: right;" value ="0/30" name="used"   readonly="readonly"></font>
									      </div>
									      </form>
									      <div class="modal-footer ">
									        <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
									        <button type="button" id="addRemark" class="btn btn-primary">提交</button>
									      </div>
									    </div>
	                            	</div> 
	                       </div>		
                            </tr>
                            <c:forEach var ="list" items="${remarkList}" >
                            	<tr>
	                                <td width="80px" ></td>
	                                <td width="350px" align="left">${list.remark }</td>
	                                <td width="140px" align="center">操作人：${list.opertor.realname }</td>
	                                <td width="140px" align="center">${util:formatNormalDate(list.createTime)}</td>
                            	</tr>
                            </c:forEach>
                          </tbody>
                    </table>
                </div>
                <div class="record">
                  <div class="new_xinxi"><p><font>详细记录</font></p></div>
                      <div class="record_table">
                      	<div class="record_age">
                            <ul>
                                <li id="e_was1" >获取佣金记录</li>
                                <li id="e_was2" >提现记录</li>
                                <li id="e_was3" >收徒记录</li>
                                <li id="e_was4" >登录日志</li>
                            </ul>
                        </div>
                        <c:choose>
                        <c:when test="${type eq 'brokerage' }" >
	                        <div class="record_mex" >
	                        	<table width="100%" cellpadding="1" cellspacing="1" align="center" >
	                        		<tbody>
	                        			<tr>
	                        				<td>订单号</td>
	                        				<td>确认人</td>
	                        				<td>与会员关系</td>
	                        				<td>抽佣比例</td>
	                        				<td>获得佣金</td>
	                        				<td>佣金发放时间</td>
	                        			</tr>
	                        			<c:forEach var="list" items="${page.getContent() }" >
	                        				<tr>
	                        					<td>${list.order.orderNo }</td>
	                        					<td>${list.confirmName }</td>
	                        					<td>${list.getRelate().getDes() }</td>
	                        					<td>${util:showRate(list.haveBrokerageRate)}</td>
	                        					<td>￥${util:showMoneyWithoutUnit(list.haveBrokerage)}</td>
	                        					<td>${util:formatNormalDate(list.sendBrokerage)}</td>
	                        				</tr>
	                        			</c:forEach>	
	                        		</tbody>
	                        	</table>
	                        		<div class="btn-group"style="float:right" >
												<div class="dataTables_paginate paging_simple_numbers"
													id="DataTables_Table_0_paginate">
													<tags:pagination page="${page}" paginationSize="10"
														hrefSubfix="type=brokerage"
														hrefPrefix="${ctx}/admin/user/toEditUser/${users.id }" />
												</div>
								    </div>
	                        </div>
                        </c:when>
                        <c:when test="${type eq 'withdraw' }" >
                        	 <div class="record_mex" >
	                        	<table width="100%" cellpadding="1" cellspacing="1" align="center" >
	                        		<tbody>
	                        			<tr>
	                        				<td>申请时间</td>
	                        				<td>完成时间</td>
	                        				<td>提现金额</td>
	                        				<td>到账方式</td>
	                        			</tr>
	                        			<c:forEach var="list" items="${page.getContent() }" >
	                        				<tr>
	                        					<td>${util:formatNormalDate(list.createTime) }</td>
	                        					<td>
	                        						<c:choose>
	                        							<c:when test="${list.status eq 'ALEARDYLOAN' }">
	                        								${util:formatNormalDate(list.sendDate)}
	                        							</c:when>
	                        							<c:otherwise>
	                        								${util:formatNormalDate(list.verifyTime) }
	                        							</c:otherwise>
	                        						</c:choose>	
	                        					</td>
	                        					<td>￥${list.money }</td>
	                        					<td>银行卡(${list.bankaccount })</td>
	                        				</tr>
	                        			</c:forEach>	
	                        		</tbody>
	                        	</table>
	                        		<div class="btn-group"style="float:right" >
												<div class="dataTables_paginate paging_simple_numbers"
													id="DataTables_Table_0_paginate">
													<tags:pagination page="${page}" paginationSize="10"
														hrefSubfix="type=withdraw"
														hrefPrefix="${ctx}/admin/user/toEditUser/${users.id }" />
												</div>
								    </div>
	                        </div>
                        </c:when>
                          <c:when test="${type eq 'apprentice' }" >
                        	 <div class="record_mex" >
	                        	<table width="100%" cellpadding="1" cellspacing="1" align="center" >
	                        		<tbody>
	                        			<tr>
	                        				<td>注册时间</td>
	                        				<td>会员帐号</td>
	                        				<td>姓名</td>
	                        				<td>订单</td>
	                        				<td>徒弟</td>
	                        			</tr>
	                        			<c:forEach items="${page.getContent()}" var="list">
	                        			<tr>
	                        				<td>${util:formatNormalDate(list.registerTime) }</td>
											<td>${list.account }</td>
	                        				<td>${list.name } </td>
	                        				<td>${list.orderSum }</td>
	                        				<td>${list.sonsSum }</td>
	                        				
	                        			</tr>	
	                        			</c:forEach>                        				
	                        		</tbody>
	                        	</table>
	                        		 <div class="btn-group"style="float:right" >
												<div class="dataTables_paginate paging_simple_numbers"
													id="DataTables_Table_0_paginate">
													<tags:pagination page="${page}" paginationSize="10"
														hrefSubfix="type=apprentice"
														hrefPrefix="${ctx}/admin/user/toEditUser/${users.id }" />
												</div>
								    </div> 
	                        </div>
                        </c:when>
                        	    
                        	    
                        <c:when test="${type eq 'loginlog' }" >
                        	 <div class="record_mex" >
	                        	<table width="100%" cellpadding="1" cellspacing="1" align="center" >
	                        		<tbody>
	                        			<tr>
	                        				<td>登录时间</td>
	                        				<td>登录IP</td>
	                        				<td>登录地址</td>
	                        				<td>累计登录次数</td>
	                        			</tr>
	                        			<c:forEach var="list" items="${page.getContent() }" >
	                        				<tr>
	                        					<td>${util:formatNormalDate(list.loginDate)}</td>
	                        					<td>${list.userLoginIp }</td>
	                        					<td>${list.loginAddress}</td>
	                        					<td>${list.loginTimes }</td>
	                        				</tr>
	                        			</c:forEach>	
	                        		</tbody>
	                        	</table>
	                        		<div class="btn-group"style="float:right" >
												<div class="dataTables_paginate paging_simple_numbers"
													id="DataTables_Table_0_paginate">
													<tags:pagination page="${page}" paginationSize="10"
														hrefSubfix="type=loginlog"
														hrefPrefix="${ctx}/admin/user/toEditUser/${users.id }" />
												</div>
								    </div>
	                        </div>
                        </c:when>         
                        </c:choose>
                  
                     </div>
                     </div>
        </div>
        <!-- 徒弟等 -->
        <div id="dialog_tt" style="display:none;text-align:center;">
			<table id="tt" title="" class="easyui-treegrid " style="width:550;height:300px">
		    </table>
		</div>
        
         		
</body>
</html>