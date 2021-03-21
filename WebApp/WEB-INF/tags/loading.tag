<%-- controller 那边必须设置  跳页参数 page --%>
<%@tag pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="ctx" value="${pageContext.request.contextPath}" />

<script src="${ctx }/static/js/fakeLoader/js/fakeLoader.js" type="text/javascript"></script>
<link rel="stylesheet" href="${ctx }/static/js/fakeLoader/css/fakeLoader.css">

	<script type="text/javascript">
	
	function showLoading(){
		/**显示加载界面*/
		var $fakeLoader = $("#fakeLoader");
		if ($fakeLoader.data("loaded") !== true){
			$fakeLoader.fakeLoader({
				bgColor:"#000",
				imagePath:"${ctx}/static/images/aloading.gif"
			});
			$fakeLoader.data("loaded",true);
		}else{
			$fakeLoader.show();
		}
	}
	function hideLoading(){
		 $("#fakeLoader").fadeOut();
	}
	</script>
<div id="fakeLoader"></div>