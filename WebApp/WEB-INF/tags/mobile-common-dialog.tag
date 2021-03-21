<%@tag pageEncoding="UTF-8"%>

	<script type="text/javascript">
	//显示公共对话框
	function showMessage(mes){
		layer.open({content: mes, time: 4});
	}

	//显示公共对话框，但是带回调函数
	function showDialog(message,bt1,bt2,f,f2,title){
		title = title || '提示';
		$("#dialogTitle").text(title);
		$("#dialogMessage").text(message);
		$("#dialogButton1").val(bt1);
		$("#dialogButton2").val(bt2);
		
		$("#dialogButton1").unbind("click");
		$("#dialogButton1").bind("click",function(){
			hideDialog();
			if(f&&typeof f =='function'){
				f();
			}
		})
	
		$("#dialogButton2").unbind("click");
		$("#dialogButton2").bind("click",function(){
			hideDialog();
			if(f2&&typeof f2 =='function'){
				f2();
			}
		})
		var top = (document.documentElement.clientHeight - $("#commonDialog").height()) / 2 + $(document).scrollTop() - 50;
		var left = ($(document).width() - $("#commonDialog").width()) / 2 + $(document).scrollLeft();
		$("#commonDialog").css({
			top:top,
		    left:left
		})
		$("#commonDialog").show();
		$("#coverMask").show();
	}
	
	function hideDialog(){
		$("#commonDialog").hide();
		$("#coverMask").hide();
	}
	
	//显示公共对话框，但是带回调函数
	function showConfirm(mes,f){
		showDialog(mes,"确定","取消",f)
	}
	
	//覆盖掉alert方法
	var alert = function(mes){
		showMessage(mes);
	};
	</script>
	
	<div class="tstc" id="commonDialog" style="display:none;z-index: 9999;position: absolute;">
	    <p><i id="dialogTitle"></i><a href="javascript:void(0)" onclick="hideDialog();"></a></p>
	    <span id="dialogMessage"></span>
	    <input id="dialogButton2" type="submit" value="" class="ui-btn-lg4 ui-btn-primary" />
	    <input id="dialogButton1" type="submit" value=""  class="ui-btn-lg4 ui-btn-determine" />
	</div>
	
    <div style="display:none;width:100%; height:100%; position:fixed; top:0; left:0; z-index:1001;background:#cccccc; filter:alpha(opacity=50); -moz-opacity:0.5; -khtml-opacity: 0.5; opacity:0.5;" id="coverMask"></div>