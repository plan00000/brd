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
<script src="//cdn.bootcss.com/html2canvas/0.5.0-beta4/html2canvas.js"></script>
<script src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
<script src="${ctx }/static/js/jweixin-1.0.0.js" ></script>
<title>我的收徒二维码</title>
<script>
	document.title="我的收徒二维码";
	$(function(){
		 var str = $('#toImage'); 
		/*  html2canvas(str, {  
			 onrendered: function (canvas) {  
		        var image = canvas.toDataURL("image/png");  
		        var pHtml = "<img src="+image+" />";  
		        $('#toImage').html(pHtml);  
		    },width:540,height:300
		  });  */
		  ////分享  朋友 朋友圈 QQ
		  var title = '送你88元帮人贷注册礼包，你离提现只差一步。';
		  var desc = '帮人贷一个兼具贷款赚佣的互联网O2O平台，让你体验坐享其"佣"的快感！';
		  var shareImg = '${shareImg}';
		 /// var shareImg = "http://zyhtest.cnolnic.com/static/brd/img/shareIcon.jpg";
		  var appid  = '${appid}';
		  var noncestr = '${res.noncestr}';
		  var timestamp = '${res.timestamp}';
		  var signature = '${res.signature}';
		  var link = '${sharelink}';
		  wx.config({
			    debug: false, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
			    appId: appid, // 必填，公众号的唯一标识
			    timestamp:timestamp, // 必填，生成签名的时间戳
			    nonceStr:noncestr, // 必填，生成签名的随机串
			    signature:signature,// 必填，签名，见附录1
			    jsApiList: [
						'checkJsApi',
						'onMenuShareTimeline',
						'onMenuShareAppMessage',
						'hideMenuItems',
						'onMenuShareQQ',
						'onMenuShareQZone',
						'hideOptionMenu',
						'showOptionMenu',
						'chooseImage',
						'uploadImage',
						'previewImage'
			                ] // 必填，需要使用的JS接口列表，所有JS接口列表见附录2
			});
		  wx.ready(function () {
				wx.hideOptionMenu();
				wx.showOptionMenu();
			    wx.hideMenuItems({
			        menuList: [
			            'menuItem:openWithQQBrowser',
			            'menuItem:openWithSafari',
			            'menuItem:copyUrl',
			            'menuItem:readMode',
			        ] 
			    });
					    
				//朋友圈分享
			    wx.onMenuShareTimeline({
			    	title: title,
			        imgUrl: shareImg,
			        link: link,
			        desc: desc
			    });
				//分享给朋友
			    wx.onMenuShareAppMessage({
				    title: title,
			        desc: desc,
			        link: link,
			        imgUrl: shareImg
				   });
				
			    var QQData = {
			    		title: title,
		 		        desc: desc,
				        link: link,
				        imgUrl: shareImg,
			    }
			    //分享到QQ
				wx.onMenuShareQQ(QQData);
			    //分享到QQ空间
			    wx.onMenuShareQZone(QQData); 
			 });
		  
		  wx.error(function(res){
				 /*  alert(res);  */
				    // config信息验证失败会执行error函数，如签名过期导致验证失败，具体错误信息可以打开config的debug模式查看，也可以在返回的res参数中查看，对于SPA可以在这里更新签名。

		 });
		  
		  
	});
</script>
</head>
<body>
	 <div class="c_bady"> 
        <div class="code">
            <p><font>如何邀请好友</font></p>
            <ul>
                <li>点击微信浏览器右上方</li>
                <li>选择发送给好友</li>
            </ul>
         </div>
         <div class="c_box" >
             <div class="c_index">
                 <div class="i_text">
                 <c:choose>
                 <c:when test="${not empty headImg }" >
                 	<img id="userimage_img" src="${ctx}/files/displayProThumbTemp?filePath=${headImg}&thumbWidth=50&thumbHeight=50"/>
                 </c:when>
                 <c:otherwise>
                 	<img id="userimage_img" src="${ctx}/static/brd/img/u1428.png" />
                 </c:otherwise>
                 </c:choose>
                 </div>
                     <span>Hi~我在帮人贷注册已获得<font>${money }</font>元红包！<br />${shareNotify}</span>
                     <div class="c_index_img">
                     	 <div id="toImage" style="width:270px;height:150px" >
	                     	 <p><img src="${ctx}/files/displayPro?filePath=${qrCode}&thumbWidth=160&thumbHeight=160"/></p>
	                         <p><img src="${ctx}/static/brd-mobile/images/zhiwen.png"></p>
                         </div>
                         <span>长按指纹识别二维码<br/>我的邀请码:${recommended}</span>
                     </div>
                     </div>
         </div>
	 </div> 
</body>
</html>