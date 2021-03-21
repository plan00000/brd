<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="util" uri="functions" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html >
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>查询微信用户与二维码</title>
    <script src="${ctx}/static/js/input-number-change.js"></script>
    <link href="${ctx }/static/brd/css/plugins/datapicker/datepicker3.css" rel="stylesheet">
    <script src="${ctx }/static/brd/js/plugins/datapicker/bootstrap-datepicker.js" type="text/javascript"></script>
    <script src="${ctx }/static/js/moment.js" type="text/javascript"></script>
    <script type="text/javascript">
        // 显示二维码
        function showQrcodes(div, user) {
          div.html('加载中...');

          var sceneIds = JSON.parse(user.sceneids);

          for (var i = sceneIds.length; i--;) {
            sceneIds[i] = parseInt(sceneIds[i]);
          }

          $.get('${ctx}/admin/qrCodeActivity/getQrcodeByIds', { ids: JSON.stringify(sceneIds) }, function(qrcodes) {
            console.log(qrcodes);
            div.html('');
            var cur = qrcodes.pop();
            div.append('当前关注: <a href="${ctx}/admin/qrCodeActivity/toDetail/' + cur.id + '">' + cur.name + '</a><br />');
            if (qrcodes.length) {
              div.append('曾经关注: ');
              qrcodes.forEach(function(qr) {
                div.append('<a href="${ctx}/admin/qrCodeActivity/toDetail/' + qr.id + '">' + qr.name + '</a> ');
              });
            }
          });

          return div;
        }

        $(function() {

          $('#userForm').submit(function(evt) {
            evt.preventDefault();

            var infoDiv = $('#infoDiv');
            var table = $('#userList');
            var name = $('#userName').val();
            if (name) {
              table.hide();
              infoDiv.show().html('搜索中....');
              $.get('${ctx}/admin/qrCodeActivity/searchWechatUser?name=' + name, function (users) {
                if (users.numberOfElements > 0) {
                  infoDiv.hide();
                  table.html('');
                  users.content.forEach(function(user) {
                    if (!user.sceneids) {
                      return;
                    }
                    var tr = $('<tr />');
                    tr.append('<td><img src="' + user.headimgurl + '" style="width: 24px; height: 24px;" /></td>')
                    tr.append('<td>' + user.nickname + '</td>');
                    tr.append('<td>' + user.province + ' - ' + user.city + '</td>');
                    var btn = $('<a href="javascript:;">查看关注二维码</a>');
                    tr.append($('<td>').append(btn));
                    table.append(tr);

                    var content = $('<div style="text-align: left; padding: 10px;"/>');
                    btn.popover({
                      trigger: 'click',
                      container: 'body',
                      content: content,
                      placement: 'bottom',
                      html: true
                    }).on('show.bs.popover', function() {
                      showQrcodes(content, user);
                    });
                  });
                  table.show();
                }
                else {
                  infoDiv.html('没有搜索到相应的结果');
                }
              });
            }
          });
        });
    </script>
    <style>
        .tbtn {
            padding: 0 18px;
            margin-top: 2px;
            line-height: 28px;
            background: #19aa8d;
            color: #fff;
            margin-left: 15px;
            cursor: pointer;
            border-width: 0px;
        }

        .text_input {
            width: 120px;
            height: 29px;
            overflow: hidden;
            border: solid #dcdcdc 1px;
            margin-top: 2px;
            font: 14px/30px "microsoft yahei";
            color: #333;
        }

    </style>
</head>
<body>
<div class="row border-bottom">
    <div class="basic">
        <p>活动管理</p>
        <span><a href="<c:url value='/admin/main'/>" style="margin-left:0;">首页</a>>
			 <a href="#">活动管理</a>>
			 <a href="#">二维码活动</a>>
			 <a><strong>查询微信用户</strong></a>
		 </span>
    </div>
</div>

<div class="new_emp animated fadeInRight">
    <form id="userForm">
        <span class="the-field">输入微信昵称：
            <input type="text" id="userName" class="text_input" />
        </span>
        <button class="tbtn">查询</button>
    </form>

    <table id="userList" class="table" style="display: none; width: 100%; margin-top: 20px;">

    </table>

    <div id="infoDiv" style="text-align: center; padding: 10px; display: none; margin-top: 20px;"></div>
</div>

</body>
</html>