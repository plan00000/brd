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
<title>我的徒弟</title>
<script type="text/javascript">
document.title="我的徒弟";
var Data = {};
var isLoading = false;
function loadApprenticeData(){
	var nowpage = $("#nowpage").val();
	var nextpage = parseInt(nowpage) + 1;
	Data.page = nextpage;
	var totalpage = $("#totlepage").val();
	if(nowpage < totalpage){
		if(isLoading){
			return false;
		}
		isLoading = true;
		$.ajax({
			url : "${ctx}/weixin/user/apprentice/ajaxApprenticeList",
			async:false,
			type : 'post',
			dataType: 'html',
			data: Data,
			success : function(result){
				$("#apprenticeul").append(result);
				$("#nowpage").val(nextpage);
				if(nextpage == totalpage){
					$(".nomoreapprentices").css("display", "block");
				}
			},
			complete : function() {
				setTimeout(function() {
					isLoading = false;
				}, 2000);
			}
		});
	}else if(totalpage>1){
		$(".nomoreapprentices").css("display", "block");
	}
}
$(window).scroll(
		function() {
			setTimeout(function() {
				totalheight = parseFloat($(window).height())
						+ parseFloat($(window).scrollTop());
				if ($(document).height() <= totalheight) { //当文档的高度小于或者等于总的高度的时候，开始动态加载数据
					loadApprenticeData();
				}
			}, 400);
		});
</script>
</head>
<body style="background:#f2f2f2;">
	<input type="hidden" id="nowpage" value="1" />
	<input type="hidden" id="totlepage" value="${apprenticestotal}" />
     <div class="apprent_top">
     	<c:if test = "${ user.userType.ordinal() =='2' || user.userType.ordinal() == '4'}">
          <span>当前徒弟数</span>
          <p>${user.userInfoBoth.sonSum }</p>
        </c:if>
          <dl>
              <dt><span>已赚佣金(元)</span></dt>
              <dd>${user.userInfoBoth.orderBrokerage }</dd>
          </dl>
         
          <c:choose>
          	<c:when test="${user.userType.ordinal() =='2' || user.userType.ordinal() == '4'}">
          		<dl>
	              <dt><span>当前徒孙数</span></dt>
	              <dd>${apprenticeGGsons}</dd>
	          	</dl>
          	</c:when>
          	<c:otherwise>
          		<dl>
	              <dt><span>当前徒弟数</span></dt>
	              <dd>${user.userInfoBoth.sonSum }</dd>
	          	</dl>
          	</c:otherwise>
          </c:choose>
          
      </div>
      <div class="apprent_text" >
      	<ul id = "apprenticeul">
      	<c:forEach items = "${apprenticeUsers.content }" var = "apprenticeUser">
      		 <dl>
              <dt>
              	<p>${apprenticeUser.username }</p>
                <p>${util:showMobileno(apprenticeUser.mobileno) }</p>
                <c:if test = "${apprenticeUser.userType.ordinal() == '0'}">
                	<span>普</span>
                </c:if>
                <c:if test = "${apprenticeUser.userType.ordinal() == '1'}">
                	<span>融</span>
                </c:if>
                <c:if test = "${apprenticeUser.userType.ordinal() == '2'}">
                	<span>商</span>
                </c:if>
                  
              </dt>
              <dd>
              	
                  <p>
                  	<c:if test="${user.userType.ordinal() != '1'}">他收徒<font>${apprenticeUser.userInfoBoth.sonSum }</font>个，</c:if>
                  	为您赚佣金<font><c:if test = "${empty apprenticeUser.brokerage  }">0</c:if><c:if test = "${not empty apprenticeUser.brokerage }">${apprenticeUser.brokerage }</c:if></font>元</p>
                  <span>注册时间：${util:formatNormalDate(apprenticeUser.createdate) }</span>
              </dd>
           </dl>
      	</c:forEach>
      	</ul>
      	<div class='clear'></div>
		<div style='height: 50px; text-align: center; display: none'
			class='nomoreapprentices'>
			<br>无更多产品信息了
		</div>
		<div class="m_back"></div>
      </div>
</body>
</html>