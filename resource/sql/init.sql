-- 初始化数据
INSERT INTO `user` VALUES ('1', '1', null, null,null, '1', 'Admin', '超级管理员', '', 'e8fc2db7fcf3fc2679166706dbc91ef434372503', '9661e58983530c80', '18759207555', '1', '5', '2016-09-23 10:54:40', '0', null,'0000-00-00 00:00:00','0000-00-00 00:00:00');
INSERT INTO `user_info_employee` VALUES ('1', '', null, '127.0.0.1', '127.0.0.1', '未知地区', '未知地区');
INSERT INTO `role` VALUES ('1', 'Admin', 'LOGIN_MANAGER_FRONT;LOGIN_MANAGER_BACK;USER_MANAGER;ORGANIZATION_MANAGER_DEPART;ORGANIZATION_MANAGER_USER;ORGANIZATION_MANAGER_ROLE;ORDER_MANAGER;ORDER_MANAGER_PC;ORDER_MANAGER_BROKERAGE;ORDER_MANAGER_WITHDRAW;PRODUCT_MANAGER;PRODUCT_MANAGER_TYPE;ACTIVITY_MANAGER_STAR;ACTIVITY_MANAGER_APPRENTICE;ACTIVITY_MANAGER_REGISTER;TEXT_MANAGER_ACTIVITY;TEXT_MANAGER_HELP;TEXT_MANAGER_APPRENTICE;TEXT_MANAGER_ABOUTUS;AD_MANAGER_PC;AD_MANAGER_WEIXIN;SET_MANAGER_BASE;SET_MANAGER_INFO;SET_MANAGER_AGGREMENT;STATISTICS_MANAGER_USER;STATISTICS_MANAGER_ORDER;STATISTICS_MANAGER_PRODUCT;STATISTICS_MANAGER_DEPART;STATISTICS_MANAGER_BROKERAGE;SYS_LOG;USER_EXPORT;STATISTICS_REPORT_USER;SET_WECHAT_NOTIFY;STATISTICS_SELLER;SET_FRIENDSHIPLINK;SET_SEO_SETTING;TEXT_MANGER_PC_ACTIVITY','1','1');
-- 初始化 角色表  业务员  财务 风控经理
INSERT INTO `role` VALUES ('2','业务员','LOGIN_MANAGER_FRONT;','1','0');
INSERT INTO `role` VALUES ('3','风控经理','LOGIN_MANAGER_BACK;','1','0');
INSERT INTO `role` VALUES ('4','财务','LOGIN_MANAGER_BACK;','1','0');
-- 初始化 广告表 除了滚播图以外其他的位置
INSERT INTO `advertisement` VALUES ('1','1','1','','1',now(),'');
INSERT INTO `advertisement` VALUES ('2','0','2','','1',now(),'');
INSERT INTO `advertisement` VALUES ('3','0','3','','1',now(),'');
INSERT INTO `advertisement` VALUES ('4','0','4','','1',now(),'');
INSERT INTO `advertisement` VALUES ('5','0','5','','1',now(),'');

-- 初始化 产品类型
INSERT INTO `product_type` VALUES('1','0','星级贷','2016-10-00 01:03:04','1');
INSERT INTO `product_type` VALUES('2','1','星级贷','2016-10-00 01:03:04','1');
INSERT INTO `product_type` VALUES('3','2','星级贷','2016-10-00 01:03:04','1');

-- 初始化 微信推送
INSERT INTO `weixin_post` VALUES ('1', '0', '0', '', '【帮人贷】尊敬的用户，您的贷款请求已被系统接收，我们会尽快完成审核并通知您审核结果。', '感谢您的支持和信任！');
INSERT INTO `weixin_post` VALUES ('2', '1', '0', '', '【帮人贷】尊敬的用户，您的贷款审核已成功通过。我们会尽快安排放款并通知您放款结果。', '感谢您的支持和信任！');
INSERT INTO `weixin_post` VALUES ('3', '2', '0', '', '【帮人贷】尊敬的用户，很抱歉您的贷款申请审核失败。', '如有疑问，请登录【帮人贷】查看详情。感谢您的支持和信任！');
INSERT INTO `weixin_post` VALUES ('4', '3', '0', '', '【帮人贷】尊敬的用户，您申请的贷款已成功放款。', '请尽快登录【帮人贷】查看详情。感谢您的支持和信任！');
INSERT INTO `weixin_post` VALUES ('5', '4', '0', '', '【帮人贷】尊敬的用户，恭喜您获得了一笔未结算佣金。', '请登录【帮人贷】查看详情。感谢您的支持和信任！');
INSERT INTO `weixin_post` VALUES ('6', '5', '0', '', '【帮人贷】尊敬的用户，您的佣金已成功放款。', '请登录【帮人贷】查看详情。感谢您的支持和信任！');
INSERT INTO `weixin_post` VALUES ('7', '6', '0', '', '【帮人贷】尊敬的用户，您的佣金提现已成功到账。', '请登录【帮人贷】查看详情。感谢您的支持和信任！');

INSERT INTO `sys_info` VALUES ('1', '', '', '/files/display?filePath=adminBanner/2016-12-13/9/4/admin_54A6EA2D76C44E227EC9BFBA9CC2FF9E.png', '/admin/platformSetting/display?filePath=logo/2016-12-13/6/6/logo_6D8F95284C7528733CDE9D1BC7A616BA.png', '', '', '一起来帮人贷定个小目标，比如先赚一个亿~', '', null, 'wx5ae4346e8ee49d2f', 'da3d9bfe1e500f2bd6b40e2a83f09291', null, null, null);
