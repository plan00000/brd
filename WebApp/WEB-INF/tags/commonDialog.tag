<%-- controller 那边必须设置  跳页参数 page --%>
<%@tag pageEncoding="UTF-8"%>

	<script type="text/javascript">
	
	//显示公共对话框
	function showDialog(mes,title){
		$("#common_dialog div").html(mes);
		title=title||'提示';
		$("#common_dialog").dialog({
			  closeText: "关闭",
			  resizable: false,
			  modal: true,
			  title:title,
			  buttons:[{
				  text:"确定",
				  click: function() {
				        $( this ).dialog( "close" );
				    }
			  }
			  ]
		});
	}
	//显示公共对话框，但是带回调函数
	function showCallBackDialog(mes,f,title){
		title = title || '提示';
		$("#common_dialog2 div").html(mes);
		$("#common_dialog2").dialog({
			  closeText: "关闭",
			  resizable: false,
			  modal: true,
			  title:title,
			  buttons:[{
				  text:"确定",
				  click: function() {
				      $(this).dialog("close"); 
					  f();
				       
				    }
			  }
			  ]
		});
	}
	//覆盖掉alert方法
	var alert = function(mes){
		showDialog(mes);
	};
	</script>
	<!-- 公共对话框 -->
<div id="common_dialog" style="display:none;text-align: center">
		<div></div>
	</div>
	<div id="common_dialog2" style="display:none;text-align: center">
		<div></div>
	</div>