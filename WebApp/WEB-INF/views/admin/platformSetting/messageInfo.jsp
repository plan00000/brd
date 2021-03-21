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
<title>短信通知</title>
<script>
	window.UEDITOR_HOME_URL = "${ctx}/"
</script>
<script type="text/javascript">
(function($){
    $.fn.UIdialog = $.fn.dialog; /* 把jquery-ui的dialog另存为UIdialog */
})(jQuery);
</script>
<script type="text/javascript" charset="utf-8" src="${ctx}/static/js/ueditor/ueditor.config.js"></script>
<script type="text/javascript" charset="utf-8" src="${ctx}/static/js/ueditor/ueditor.all.min.js"></script>
<script type="text/javascript" charset="utf-8" src="${ctx}/static/js/ueditor/lang/zh-cn/zh-cn.js"></script>
<link rel="stylesheet" type="text/css" href="${ctx}/static/js/jquery-easyui-1.4.3/themes/default/easyui.css" media="all" />
<link rel="stylesheet" type="text/css" href="${ctx}/static/js/jquery-easyui-1.4.3/themes/icon.css" media="all" />
<script src="${ctx}/static/js/jquery-easyui-1.4.3/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${ctx}/static/js/jquery-easyui-1.4.3/locale/easyui-lang-zh_CN.js"></script>
<script src="${ctx}/static/js/input-number-change.js"></script>
<script type="text/javascript">
(function($){
    $.fn.Udialog = $.fn.dialog; /* 把jquery-ui的dialog另存为UIdialog */
})(jQuery);
(function($){
	$.fn.dialog = $.fn.UIdialog; /* 把jquery-ui的dialog另存为UIdialog */
})(jQuery);
</script>
<script type="text/javascript">
$(function() {
	activeNav2("8","8_2");
	this.REGX_HTML_ENCODE = /"|&|'|<|>|[\x00-\x20]|[\x7F-\xFF]|[\u0100-\u2700]/g; 
	 this.encodeHtml = function(s){
          return (typeof s != "string") ? s :
              s.replace(this.REGX_HTML_ENCODE,
                        function($0){
                            var c = $0.charCodeAt(0), r = ["&#"];
                            c = (c == 0x20) ? 0xA0 : c;
                            r.push(c); r.push(";");
                            return r.join("");
                        });
      };
		$("#sure_btn").click(function(){
			var checkeds = $("#users_tab").datagrid("getChecked");
			var ids = new Array();
			var idStr = "";
			var realnames = new Array();
			var mobilenos = new Array();
			var realnameStr = "";
			for (var i=0;i<checkeds.length;i++ ){
				ids.push(checkeds[i].id);
				idStr += checkeds[i].id + ";";
				mobilenos.push(checkeds[i].mobileno);
				realnames.push(checkeds[i].realname);
				realnameStr += checkeds[i].realname + " ";
			}
			$("#mobilenum").val(mobilenos);
			$("#sendUserString").val(idStr);
			$('#dialog_users').dialog('close');
			$('#choose1').text(realnames[0]);
			$('#choose2').text(realnames[1]);
			$('#choose3').text(realnames[2]);
			if(realnames.length>3){
				$('#other').text("……");
				$("#other").css({'margin-left':'15px','margin-right':'15px'});
				$("#choose1").css({'margin-left':'15px','margin-right':'15px'});
				$("#choose2").css({'margin-left':'15px','margin-right':'15px'});
				$("#choose3").css({'margin-left':'15px','margin-right':'15px'});
			}
			if(realnames.length==3){
				$('#other').text("");
				$("#choose1").css({'margin-left':'15px','margin-right':'15px'});
				$("#choose2").css({'margin-left':'15px','margin-right':'15px'});
				$("#choose3").css({'margin-left':'15px','margin-right':'15px'});
				$("#other").css({'margin-left':'0px','margin-right':'0px'});
			}
			if(realnames.length==1){
				$('#choose2').text("");
				$('#choose3').text("");
				$('#other').text("");
				$("#choose1").css({'margin-left':'15px','margin-right':'15px'});
				$("#choose2").css({'margin-left':'0px','margin-right':'0px'});
				$("#choose3").css({'margin-left':'0px','margin-right':'0px'});
				$("#other").css({'margin-left':'0px','margin-right':'0px'});
			}
			if(realnames.length==2){
				$('#choose3').text("");
				$('#other').text("");
				$("#choose1").css({'margin-left':'15px','margin-right':'15px'});
				$("#choose2").css({'margin-left':'15px','margin-right':'15px'});
				
				$("#choose3").css({'margin-left':'0px','margin-right':'0px'});
				$("#other").css({'margin-left':'0px','margin-right':'0px'});
			}
		});
		
		$("#clear_btn").click(function(){
			$("#_username").val("");
			$("#_realname").val("");
			$("#_mobileno").val("");
			$("#users_tab").datagrid({
				pageNumber:1			
			});
		});
		$("#sear_btn").click(function(){
			$("#users_tab").datagrid({
				queryParams:{
					username:$("#_username").val(),
					realname:$("#_realname").val(),
				    mobileno:$("#_mobileno").val()
				},
				pageNumber:1			
			});
		});
		/*选择相关用户*/
		$("#choose").click(function() {
			//初始化选中的用户
			var sendUserStringarray = $("#sendUserString").val().split(";");
			$("#dialog_users").dialog({
				  closeText: "关闭",
				  position:{
					  my: "left top",
					  at: "left+300 top+100",
					  of: window
					},
				  width: 800,
				  heigth: 300,
				  resizable: false,
				  modal: true,
				  title:"选择发送对象",
			});
			$("#users_tab").datagrid('uncheckAll');
			$("#users_tab").datagrid('unselectAll');
			var idss = $("#sendUserString").val();
			var strs= new Array(); //定义一数组 
			strs = idss.split(";"); 
			$("#users_tab").datagrid({
				 url : "${ctx}/admin/platformSetting/getUsers",//首次查询路径  
				 queryParams:{
					 username:'${username}',
					  realname:'${realname}',
					  mobileno:'${mobileno}'
				 },
		        idField : 'id',
			    minHeight:306,
			    maxHeight:306,
		        fit:true,
			    fitColumns:true,
		 	    nowrap:true,
			    rownumbers:true,
			    cache:false,
	            checkOnSelect:true,  
			    selectOnCheck: true,
			    singleSelect:false,
	            columns : [ [{
	            	field:'id',
	            	title:'',
	            	align:'center',
	            	width:'10%',
	            	checkbox:true,
	            	sortable:true
	            },{  
		            field : "username",  
		            title : "登录帐号",
		            align:'center',
		            width:'30%',
		            formatter:function(value,row,index){
					     	return value;
					  }}
		        ,{  
		            field : "realname",  
		            title : "姓名",
		            align:'center',
		            width:'30%',
		            formatter:function(value,row,index){
					     	return value;
					  }
		        },{  
		            field : "mobileno",  
		            title : "手机号码", 
		            align:'center',
		            width:'30%',
		        } 
		        ] ],
		        onLoadSuccess:function(rowIndex, rowData){
					for(var i=0;i<strs.length;i++){
						$(this).datagrid('selectRecord',strs[i]);
					}  
				}
			});
		});		
		$("#send_btn").click(function() {
			if(check()){
				var mobilenostr = $("#mobilenum").val();
				var content = $(".s_input").val();
				$.post(
						"${ctx}/admin/platformSetting/sendMessage",
						{mobilenostr:mobilenostr,content:content},
						function(data){
							if(data.code == 1){
								showCallBackDialog(data.mes,function(){
								})} else {
										alert(data.mes);
								}
								});
							}
						});
		
		$(".s_bj").click(function() {
			window.history.go(-1);
/* 			$('#choose1').text("");
			$('#choose2').text("");
			$('#choose3').text("");
			$('#other').text("");
			$("#choose1").css({'margin-left':'0px','margin-right':'0px'});
			$("#choose2").css({'margin-left':'0px','margin-right':'0px'});
			$("#choose3").css({'margin-left':'0px','margin-right':'0px'});
			$("#other").css({'margin-left':'0px','margin-right':'0px'});
			$("#sendUserString").val("");
			$("#mobilenum").val(""); */
		})
      
})

	function check() {
		if (!checkObject()) {
			return false;
		}
		if (!checkContent()) {
			return false;
		}
		return true;
	}
function checkObject() {
	if ($("#sendUserString").val().trim().length == 0) {
		alert("请选择发送对象");
		return false;
	}
	return true;
}
function checkContent() {
	if ($(".s_input").val().trim().length == 0) {
		alert("内容不能为空");
		return false;
	}
	if ($(".s_input").val().trim().length > 70) {
		alert("内容太长，请确保在70个字符之内");
		return false;
	}
	return true;
}
</script>
</head>
<body>
<div class="row border-bottom">
	<div class="basic">
        <p>平台设置</p>
        <span><a href="<c:url value='/admin/main'/>" style="margin-left:0;">首页</a>><a href="#" >平台设置</a>><a><strong>短信通知</strong></a></span>
    </div>	
    </div>	
		<div class="details animated fadeInRight o_height">
			<form id="oper_form" class="form-horizontal">
            	 <div class="new_xinxi"><p><font>发送短信</font></p></div>
                     <div class="send">
                     <input type="hidden" name="choose" id="sendUserString">
                     <input type="hidden" name="choose" id="mobilenum">
                         <dl>
                             <dt>发送对象：</dt>
                             <dd>
                              	<span id="choose1" style="margin-left:0px;margin-right:0px;"></span>
                             	<span id="choose2" style="margin-left:0px;margin-right:0px;"></span>
                             	<span id="choose3" style="margin-left:0px;margin-right:0px;"></span>
                             	<span id="other"   style="margin-left:0px;margin-right:0px;"></span> 
                           		 <samp id = "choose">选择发送对象</samp></dd>
                         </dl>
                         <dl>
                             <dt>发送内容：</dt>
                             <dd>
                                 <textarea class="s_input" name="Memo"
                        	onKeyDown="gbcount(this.form.Memo,this.form.used1,70);" 
				 			onKeyUp="gbcount(this.form.Memo,this.form.used1,70);" 
                                 
                                 ></textarea>
                                 <font style="padding-top: 3px;">
                                  <input class="inputtext1 inputtextn" style="float:right;text-align:right;height: 26px;" name="used1" id="tshareNotify" value="0/70" readonly="readonly"> </font>
                             </dd>
                         </dl>
                         <p id= "send_btn">发送</p>
                         <p class="s_bj">返回</p>
                     </div>
                 </form>    
                     
       <div id="dialog_users" style="display:none;">
		<form method="get" class="form-horizontal" id="search_form_user">
			<div class="form-group" style="width: 100%;">
				<label class="col-sm-1 control-label">登录帐号</label>
				<div class="col-sm-2">
					<input type="text" name="LIKE_username" id="_username"
						class="form-control m-b">
				</div>
				<label class="col-sm-1 control-label">姓名</label>
				<div class="col-sm-2">
					<input type="text" name="LIKE_realname" id="_realname"
						class="form-control m-b">
				</div>
				<label class="col-sm-1 control-label">手机号</label>
				<div class="col-sm-2">
					<input type="text" name="LIKE_mobileno" id="_mobileno"
						class="form-control m-b">
				</div>
				<div class="col-sm-8" align="right"
					style="float: right; margin-right: 20px;">
					<button id="sear_btn" class="btn btn-primary" type="button">查询</button>
					<button id="clear_btn" class="btn btn-white" type="button">清除</button>
				</div>
			</div>
		</form>
		<table id="users_tab" title="" class="easyui-datagrid" ></table>
		<br>
		<div align="left" align="left" style="float: left; margin-left: 20px">
			<div class="form-group">
				<div data-toggle="buttons" class="btn-group">
					<button id="sure_btn" class="btn btn-primary" type="button">确定</button>
				</div>
			</div>
		</div>
	</div>
</div>
</body>
</html>
