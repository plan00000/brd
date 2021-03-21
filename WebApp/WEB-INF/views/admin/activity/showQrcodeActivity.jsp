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
    <title>二维码详情</title>
    <script src="${ctx}/static/js/input-number-change.js"></script>
    <link href="${ctx }/static/brd/css/plugins/datapicker/datepicker3.css" rel="stylesheet">
    <script src="${ctx }/static/brd/js/plugins/datapicker/bootstrap-datepicker.js" type="text/javascript"></script>
    <script src="${ctx }/static/js/moment.js" type="text/javascript"></script>
    <script type="text/javascript">
      $(function () {
        //日期控件初始化
        $.fn.datepicker.dates['zh-cn'] = {
          days: ["星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"],
          daysShort: ["日", "一", "二", "三", "四", "五", "六"],
          daysMin: ["日", "一", "二", "三", "四", "五", "六"],
          months: ["一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "十一月", "十二月"],
          monthsShort: ["一", "二", "三", "四", "五", "六", "七", "八", "九", "十", "十一", "十二"],
          today: "今天",
          clear: "清除",
          format: "yyyy-mm-dd",
          titleFormat: "yyyy MM", /* Leverages same syntax as 'format' */
          weekStart: 1
        };
        $(".datepicker").datepicker({
          language: 'zh-cn'
        });

        $('#timeForm').submit(function(evt) {
          evt.preventDefault();
          console.log('form', this);

          var startTime = $('#sStartTimeStr').val(),
            endTime = $('#sEndTimeStr').val(),
            dataDisplay = $('#timeData');

          if (!startTime) {
            return alert('开始时间不得为空');
          }
          if (!endTime) {
            return alert('结束时间不得为空');
          }

          dataDisplay.html('数据加载中....');

          $.get('${ctx }/admin/qrCodeActivity/queryScanRecord', {
            id: '${qrcodeActivity.id}',
            startTime: startTime,
            endTime: endTime
          }, function(res) {
            dataDisplay.html('<span  class="the-field">该时间段扫码次数：' + res.scanNum + '</span><br /><span  class="the-field">该时间段扫码关注人数: ' + res.concerNum + '</span>')
          });
        });


        searchUser({});
      });

      var searchField = {
        id: '${qrcodeActivity.id}',
        range: 'all',
        page: 1,
        asc: false
      };
      function searchUser(fields) {
        searchField = $.extend(searchField, fields);
        $('.user-content').hide();
        $('#userLoading').show();

        if (searchField.asc) {
          $('#sortorderSuccessSum').removeClass('l_sanj2').addClass('l_sanj1');
        }
        else {
          $('#sortorderSuccessSum').removeClass('l_sanj1').addClass('l_sanj2');
        }

        delete searchField.result;

        $.get('${ctx }/admin/qrCodeActivity/listWechatUser', searchField, function(res) {
          searchField.result = res;
          $('.btn-grp a').removeClass('active');
          $('#__' + searchField.range).addClass('active');
          $('#userCount').html($('.btn-grp a.active').html() + '人数: ' + res.totalElements);

          $('#userLoading').hide();
          $('#pageInfo').html('当前第' + searchField.page + '页，共' + res.totalPages + '页，共 ' + res.totalElements +' 条');

          var first = Math.max(1, searchField.page - 3);
          var last = Math.min(searchField.page + 3, res.totalPages);
          var btns = $('#pageBtns').html('');
          for (var i = first; i <= last; i++) {
            var ac = i == searchField.page ? 'active' : '';
            btns.append('<a href="javascript:void(0);" onclick="searchUser({page: ' + i +'})" class="btn btn-white ' + ac + '">' + i + '</a>');
          }

          if (res.totalElements == 0) {
            $('#userNoData').show();
          }
          else {
            console.log(res);
            var tableHtml = '';
            for (var i = 0; i < res.content.length; i++) {
              var r = res.content[i];
              tableHtml += '<tr><td><img src="' + r.headimgurl + '" style="width: 24px; height: 24px;"/></td><td>' + r.nickname + '</td><td>' + r.province + ' - ' + r.city +
                '</td><td>' + moment(r.subscribeTime).format('YYYY-MM-DD HH:mm:ss') + '</td></tr>';
            }
            $('#userTable').show().html(tableHtml);
          }


        });
      }
    </script>
    <style>
        .the-field {
            display: inline-block;
            margin: 10px;
            font-size: 14px;
        }
        .user-content {
            display: none;
        }
        .m_shuru {
            width: 115px;
            height: 38px;
            _height: 35px;
            overflow: hidden;
            font: 14px/38px "microsoft yahei";
            color: #999;
            text-align: center;
            background: #eeeeee;
            border-radius: 5px;
            border: solid #dcdcdc 1px;
        }
        .tbtn {
            border: 0px; margin-left:10px; background-color: #1ab394 ;font: 14px/30px 'microsoft yahei'; color: #fff ;cursor: pointer; padding: 0 5px; display: inline-block;
        }
        .btn-grp {
            border: 1px solid rgba(25, 170, 141, 1);
            border-left: 0px;
            display: inline-block;
            overflow: auto;
        }
        .btn-grp a {
            border-width: 0px;
            display: inline-block;
            border-left: 1px solid rgba(25, 170, 141, 1);
            font: 14px/30px 'microsoft yahei';
            color: #1ab394;
            background-color: #fff;
            cursor: pointer;
            padding: 0 5px;
            margin: 0px;
            float: left;
        }
        .btn-grp a.active {
            color: #fff;
            background-color: #1ab394;
        }
    </style>
</head>
<body>
<div class="row border-bottom">
    <div class="basic">
        <p>活动管理</p>
        <span><a href="<c:url value='/admin/main'/> style=" margin-left:0;">首页</a>>
			 <a href="#">活动管理</a>>
			 <a href="#">二维码活动</a>>
			 <a><strong>${state eq '0' ? '新增二维码' : '编辑二维码'}</strong></a>
		 </span>
    </div>
</div>
<div class="new_emp animated fadeInRight">
    <span class="the-field">二维码名称：${qrcodeActivity.name}</span> <br/>
    <span class="the-field">累计扫码总数: ${qrcodeActivity.scanNum}</span>
    <span class="the-field" style="margin-left: 50px;">累计扫码粉丝总数: ${qrcodeActivity.concernNum}</span>

    <div>
        <span style="float: left;"><img style="width: 120px; height:120px;"
                                        src="${ctx}/files/displayPro?filePath=${qrcodeActivity.qrcode}&thumbWidth=40&thumbHeight=40"/></span>
        <span style="height: 120px; display: inline-block; padding-top: 80px;">
              <a class="tbtn" href="${ctx }/admin/qrCodeActivity/downLoadCode/${qrcodeActivity.id}">下载二维码</a>
        </span>
    </div>
</div>

<div class="new_emp animated fadeInRight">
    <form id="timeForm">
        <span class="the-field">二维码数据：
            <input type="text" class="m_shuru datepicker" id="sStartTimeStr" value="" placeholder="开始时间"> -
            <input type="text" class="m_shuru datepicker" id="sEndTimeStr" value="" placeholder="结束时间">
        </span>
        <button class="tbtn">查询</button>
        <br/>
        <div id="timeData">

        </div>
    </form>
</div>

<div class="new_emp animated fadeInRight level">
    <div class="btn-grp">
        <a href="javascript:;" id="__all" onclick="searchUser({range:'all'})" class="active">扫码过本二维码关注的粉丝</a>
        <a href="javascript:;" id="__sub" onclick="searchUser({range:'sub'})">正在关注</a>
        <a href="javascript:;" id="__unsub" onclick="searchUser({range:'unsub'})">取消关注</a>
    </div>
    <div>
    <span class="the-field" id="userCount"></span>
        <table style="width: 100%;" class="table table-striped" >
            <tbody>
              <tr>
                  <td>头像</td>
                  <td>微信名</td>
                  <td>地区</td>
                  <td>
                      <strong>关注时间</strong>
                      <div class="level_triangle">
                      <span id="sortorderSuccessSum" data-id="1" onclick="searchUser({ asc: !searchField.asc })" style="margin: 0px; background-position: 0px 6px;"></span>
                  </div>
                  </td>
              </tr>
            </tbody>
            <tbody id="userTable" class="user-content"></tbody>
            <tbody id="userLoading" class="user-content">
              <tr>
                  <td colspan="4">正在加载中...</td>
              </tr>
            </tbody>
            <tbody id="userNoData" class="user-content">
                <tr>
                    <td colspan="4">没有数据</td>
                </tr>
            </tbody>

        </table>
        <div class="text-right">
            <div class="btn-group">
                <div class="dataTables_paginate paging_simple_numbers" id="DataTables_Table_0_paginate">
                    <!-- <div style="float:right;"> -->
                    <div id="pageT" class="text-right">
                        <span id="pageInfo">当前第1页，共1页，共 1 条</span>
                        <div class="btn-group">
                            <span><a href="javascript:void(0);" onclick="searchUser({ page: Math.max(1, searchField.page - 1) })" class="btn btn-white">上一页</a></span>
                            <span id="pageBtns"></span>
                            <span><a href="javascript:void(0);" onclick="searchUser({ page: Math.min(searchField.result.totalPages, searchField.page + 1) })" class="btn btn-white">下一页</a></span>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>