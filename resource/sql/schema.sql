-- ==============================================================
-- brd 数据库
-- ==============================================================
drop database if exists `brd`;
create database `brd`;
use `brd`;
-- =================================
-- 部门表
-- =================================
DROP TABLE IF EXISTS `department`;
CREATE TABLE `department` (
  -- ID
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  -- 部门名称
  `name` varchar(40) NOT NULL DEFAULT '',
  -- 父级部门id
  `parentid` bigint(20) DEFAULT '0',
  -- 部门人数
  `department_num` int(6) DEFAULT '0',
  -- 部门状态0-已删除1-正常
  `state` smallint(6) NOT NULL DEFAULT '1',
    -- 累计发放佣金
  `add_brokerage` decimal(17,2) NOT NULL DEFAULT '0',
  `level` smallint(3) NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`),
  KEY `i_department_parentid` (`parentid`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- =================================
-- 角色表
-- =================================
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  -- 角色名称
  `rolename` varchar(20)  NOT NULL,
  -- 权限
  `permissions` varchar(1024)  NOT NULL,
  -- 状态 0禁用1启用
  `state` smallint(6) DEFAULT '0', 
  -- 部门人数
  `number` int  DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
-- =================================
-- 短信验证码表
-- =================================
DROP TABLE IF EXISTS `sms_authcode`;
CREATE TABLE `sms_authcode` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  -- 电话
  `teleno` varchar(20) NOT NULL DEFAULT '',
  -- 验证码
  `authcode` varchar(10) NOT NULL DEFAULT '',
  -- 发送时间
  `send_date` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
  -- 来源
  `source` smallint(6) DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `sms_authcode_teleno` (`teleno`,`source`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- =================================
-- 用户表
-- =================================
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  -- 用户信息表-公共部分
  `user_info_employee_id` bigint(20) DEFAULT NULL,
  -- 用户信息表-用户和业务员共有的
  `user_info_both_id` bigint(20) DEFAULT NULL,
  -- 用户信息表- 商家
  `user_info_seller_id` bigint(20) DEFAULT NULL,
  -- 微信用户信息表
  `weixin_user_id` bigint(20) DEFAULT NULL,
  -- 权限表id
  `role_id` bigint(20) DEFAULT NULL,
  -- 用户名
  `username` varchar(40)  DEFAULT '',
  -- 真实姓名
  `realname` varchar(20) NOT NULL DEFAULT '',
  -- 头像
  `headimgurl` varchar(200) NOT NULL DEFAULT '',
  -- 密码
  `password` varchar(50) NOT NULL DEFAULT '',
  -- 密码的盐
  `salt` varchar(64) NOT NULL DEFAULT '',
  -- 手机号码
  `mobileno` varchar(20) NOT NULL DEFAULT '',
  -- 状态 0禁用1启用2删除
  `state` smallint(6) DEFAULT '0',
  -- 用户类型 0-用户1-融资经理2-商家3-业务员4-风控经理5-ceo6-员工
  `user_type` smallint(6) DEFAULT '0',
  -- 创建时间
  `createdate` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
  -- 登录次数
  `login_times` int NOT NULL DEFAULT '0',
  -- 备注
  `remark` varchar(255) DEFAULT NULL,
   -- 本次登录时间
  `logindate` datetime  DEFAULT NULL,
  -- 最后一次登录时间
  `lastlogindate` datetime  DEFAULT NULL,
  PRIMARY KEY (`id`),
  key `i_user_info_employee_id` (user_info_employee_id) USING BTREE,
  key `i_user_info_both_id` (user_info_both_id) USING BTREE,
  key `i_weixin_user_id`(weixin_user_id) USING BTREE,
  KEY `i_user_mobileno` (`mobileno`) USING BTREE,
  KEY `i_user_username` (`username`) USING BTREE,
  KEY `idx_roleId` (`role_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
-- =================================
-- 用户信息表--后台员工
-- =================================
DROP TABLE IF EXISTS `user_info_employee`;
CREATE TABLE `user_info_employee` (
  -- ID
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  -- 用户编号
  `userno` varchar(30) NOT NULL DEFAULT '',
  -- 部门的id
  `depart_id` bigint(20) DEFAULT NULL,
  -- 本次登录IP
  login_ip varchar(40) not null default '',
  -- 上一次登录IP
  lastlogin_ip varchar(40) not null default '',
  -- 本次登录地点
  login_city varchar(50) not null default '',
  -- 上一次登录地点
  lastlogin_city varchar(50) not null default '',
  
  KEY `i_user_info_employee_depart_id` (`depart_id`) USING BTREE,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- 用户表信息表 --会员、融资经理、商家、业务员共有的
-- ----------------------------
DROP TABLE IF EXISTS `user_info_both`;
CREATE TABLE `user_info_both` (
  -- 用户表信息表id
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  -- 师公的id
  `grand_parentid` bigint(20) DEFAULT NULL,
  -- 师父的id
  `parentid` bigint(20) DEFAULT NULL,
  -- 商家
  `sellerid` bigint(20) DEFAULT NULL,
  -- 业务员
  `salesmanid` bigint(20) DEFAULT NULL,
  -- 原业务员
  `past_salesmanid` bigint(20) DEFAULT NULL,
  -- 提现密码
  `withdraw_password` varchar(50) NOT NULL DEFAULT '',
  -- 提现密码的盐
  `withdraw_salt` varchar(64) NOT NULL DEFAULT '',
  -- 活动徒弟数量
  `acitivity_son_sum` int NOT NULL DEFAULT '0',
  -- 徒弟数量
  `son_sum` int NOT NULL DEFAULT '0',
  -- 徒孙数量
  `grandson_sum` int NOT NULL DEFAULT '0',
  -- 徒孙孙数量
  `ggsons_sum` int NOT NULL DEFAULT '0',
  -- 订单提成佣金
  `order_brokerage` decimal(17,2) NOT NULL DEFAULT '0',
  -- 活动奖励佣金
  `activity_brokerage` decimal(17,2) DEFAULT '0',
  -- 收徒奖励佣金
  `apprentice_aware_brokerage` decimal(17,2) DEFAULT '0',
  -- 佣金（已提取）
  `brokerage_have_withdraw` decimal(17,2) NOT NULL DEFAULT '0',
  -- 佣金（可提现）
  `brokerage_can_withdraw` decimal(17,2) DEFAULT '0',
  -- 佣金（提现中）
  `brokerage_withdrawing` decimal(17,2) NOT NULL DEFAULT '0',
  -- 订单总数
  `order_sum` int NOT NULL DEFAULT '0',
  -- 徒弟订单总数
  `sons_order_sum` int NOT NULL DEFAULT '0',
  -- 徒孙订单总数
  `gsons_order_sum` int NOT NULL DEFAULT '0',
  -- 徒孙孙订单总数
  `ggsons_order_sum` int NOT NULL DEFAULT '0',
  -- 订单成功总数
  `order_success_sum` int NOT NULL DEFAULT '0',
  -- 徒弟订单总额
  `sons_order_money` decimal(20,0) NOT NULL DEFAULT '0',
  -- 徒孙订单总额
  `gsons_order_money` decimal(20,0) NOT NULL DEFAULT '0',
  -- 徒孙孙订单总额
  `ggsons_order_money` decimal(20,0) NOT NULL DEFAULT '0',
  -- 我的订单总额
  `order_money` decimal(20,0)  DEFAULT '0',
  
  -- 当前登录设备的类型, 0-安卓,1-ios
  `login_device_apptype` smallint(6) DEFAULT NULL,
  -- 当前登录设备的推送码
  `login_device_seq` varchar(100) DEFAULT NULL,
  -- 当前活跃状态，是否登录，登录状态用于判断是否可以登录，0-未登录，1-登录
  `app_login_status` smallint(6) NOT NULL DEFAULT '0',
  
  -- 星级订单奖励发放标志
  `star_order_award_flag` tinyint NOT NULL DEFAULT '0',
  -- 收徒奖励发放标志, 0-未发放，1-已发放
  `apprentice_award_flag` tinyint NOT NULL DEFAULT '0',
  -- 二维码
  `qr_code` varchar(350) DEFAULT NULL,
  -- 邀请码
  `recommend_code` varchar(350) DEFAULT NULL,
  -- 保存额外的内容 比如商家保存 公司 其他保存身份证 或者其他内容
  `expands` varchar(255) DEFAULT NULL,
  KEY `i_user_info_both_parentid` (`parentid`) USING BTREE,
  KEY `i_user_info_both_grand_parentid` (`grand_parentid`) USING BTREE,
  KEY `i_user_info_both_sellerid` (`sellerid`) USING BTREE,
  KEY `i_user_info_both_salesmanid` (`salesmanid`) USING BTREE,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- 微信用户表
-- ----------------------------
DROP TABLE IF EXISTS `weixin_user`;
CREATE TABLE `weixin_user` (
  -- 微信用户表id
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  -- 是否订阅 0-未订阅,1-已订阅
  `subscribe` smallint(6) DEFAULT '0',
  -- openid
  `openid` varchar(40) DEFAULT NULL,
  -- 昵称
  `nickname` varchar(40) DEFAULT NULL,
  -- 性别
  `sex` smallint(6) DEFAULT '0',
  -- 城市
  `city` varchar(40) DEFAULT NULL,
  -- 国家
  `country` varchar(40) DEFAULT NULL,
  -- 省份
  `province` varchar(40) DEFAULT NULL,
  -- 语言
  `language` varchar(20) DEFAULT NULL,
  -- 头像地址
  `headimgurl` varchar(200) DEFAULT NULL,
  -- 订阅时间
  `subscribe_time` datetime DEFAULT now(),
  -- unionid
  `unionid` varchar(40) DEFAULT NULL,
  -- 备注
  `remark` varchar(40) DEFAULT NULL,
  -- groupid
  `groupid` bigint(20) DEFAULT NULL,
  -- 场景id
  sceneids VARCHAR(255) DEFAULT '[]',
  PRIMARY KEY (`id`),
  KEY `idx_openid` (`openid`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- =================================
-- 活动星级订单
-- =================================
DROP TABLE IF EXISTS `activity_star_order`;
CREATE TABLE `activity_star_order` (
  -- ID
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  -- 关联用户id
  `user_id` bigint(20) NOT NULL,
  -- 订单数
  `order_sum` int NOT NULL DEFAULT '0',
  -- 会员参加活动时间
  `start_time`  datetime DEFAULT NULL,
  -- 会员活动结束时间
  `end_time` datetime DEFAULT NULL,
  -- 状态  0-进行中 1-已完成
  `status` smallint(6) DEFAULT '0',
 PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- =================================
-- 活动表
-- =================================
DROP TABLE IF EXISTS `activity`;
CREATE TABLE `activity` (
  -- ID
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  -- banner图片地址
  `picurl` varchar(300) NOT NULL DEFAULT '',
  -- 活动规则
  `activity_rule` text NOT NULL,
  -- 最大收徒数或订单数
  `max_num` int NOT NULL,
  -- 星级订单不计时间订单数
  `star_ordernum` int DEFAULT NULL,
  -- 收徒奖励金额
  `bonus_amount` decimal(10,2) DEFAULT NULL,
  -- 活动设置(0-关闭活动1-注册就送红包2-填写推荐码送红包)
  `activity_set` varchar(100) DEFAULT NULL,
  -- 活动类型0-星级订单 1-收徒奖励2-推荐注册活动
  `activity_type` smallint(6) NOT NULL,
  -- 活动文案
  `activity_copy` varchar(100) DEFAULT '',
  -- 活动对象
  `activity_object` varchar(100) DEFAULT '',
 PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- =================================
-- 资讯表
-- =================================
DROP TABLE IF EXISTS `information`;
CREATE TABLE `information` (
  -- ID
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  -- 资讯类型
  `type` smallint(6) NOT NULL,
  -- 标题
  `title` varchar(255) NOT NULL DEFAULT '',
  -- 内容
  `content` longtext NOT NULL,
  -- 是否启用（0-不启用 1-启用）
  `status` smallint(6) DEFAULT '0',
  -- 排序
  `sortid` int(11) DEFAULT '1',
  -- 添加时间
  `add_date` datetime NOT NULL,
  -- 修改时间
  `modify_date` datetime DEFAULT NULL,
  -- 摘要
  `summary` varchar(255) DEFAULT '',
 PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- =================================
-- 报表统计 每日统计 包括会员统计 
-- =================================
 DROP TABLE IF EXISTS `user_common_statistics`;
 CREATE TABLE `user_common_statistics`(
 	-- 主键 自增长
 	`id` bigint(20) NOT NULL AUTO_INCREMENT,
 	-- 每日注册会员数
 	`register_num` int(11) NOT NULL,
 	-- 每日登录会员数
 	`login_num` int(11) NOT NULL,
 	-- 昨日新增会员
 	`yesterday_user`int(11) NOT NULL,
 	-- 昨日新增融资经理
 	`yesterday_manager` int(11) NOT NULL,
 	-- 昨日新增商家
 	`yesterday_seller` int(11) NOT NULL,
 	-- 所有会员数
 	`alluser_num` int(11) NOT NULL,
 	-- 统计的时间时间
 	`statistics_date` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
 	PRIMARY KEY(`id`)
 ) ENGINE=InnoDB DEFAULT CHARSET=utf8;
 -- =================================
-- 报表统计 每日统计 订单统计 
-- =================================
 DROP TABLE IF EXISTS `order_common_statistics`;
 CREATE TABLE `order_common_statistics`(
 	-- 主键 自增长
 	`id` bigint(20) NOT NULL AUTO_INCREMENT,
 	-- 每日提交贷款订单数 
 	`order_num` int(11) NOT NULL,
 	-- 每日成交贷款订单数
 	`order_success_num` int(11) NOT NULL,
 	-- 每日成交贷款订单金额
 	`order_success_decimal` decimal(15,0) NOT NULL,
 	-- 统计时间
 	`statistics_date` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
 PRIMARY KEY(`id`)
 ) ENGINE=InnoDB DEFAULT CHARSET=utf8;
 -- =================================
-- 报表统计 部门统计
-- =================================
 DROP TABLE IF EXISTS `user_department_statistics`;
 CREATE TABLE `user_department_statistics`(
 	-- 主键 自增长
 	`id` bigint(20) NOT NULL AUTO_INCREMENT,
 	-- 部门
 	`department_id` bigint(20) NOT NULL,
 	-- 员工个数
 	`user_num` int(11) DEFAULT '0',
 	-- 商家数
 	`salesman_num` int(11) DEFAULT '0',
 	-- 融资经理数
 	`manager_num` int(11) DEFAULT '0',
 	-- 总订单数
 	`orders_num` int(11) DEFAULT '0',
 	-- 总订单额
 	`orders_amount` decimal(10,2) DEFAULT NULL,
 	-- 昨日订单数
 	`yesterday_ordernum` int(11) DEFAULT '0',
 	-- 昨日订单额
 	`yesterday_orderamount` decimal(10,2) DEFAULT NULL,
 	-- 累计发放金额
 	`payment_amount` decimal(10,2) DEFAULT NULL,
 	PRIMARY KEY(`id`)
 ) ENGINE=InnoDB DEFAULT CHARSET=utf8;
 
-- =================================
-- 微信通知表
-- =================================
DROP TABLE IF EXISTS `weixin_notify`;
CREATE TABLE `weixin_notify`(
  -- 主键 自增长
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  -- 消息通知类型
  `notify_type` smallint(6) NOT NULL,
  -- 对应的用户id
  `user_id` bigint(20) NOT NULL,
  -- 对应的订单id
  `order_id` bigint(20) NOT NULL,
  -- 或者对应的 提现订单id
  `withdraw_id` bigint(11) NOT NULL,
  -- 创建时间
  `create_time` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
  -- remark 备注
  `remark` varchar(100) DEFAULT NULL,
  -- 公众号下模版
  `template` varchar(255) NOT NULL,
  --
  `first` varchar(255) NOT NULL,
  -- 
  `keynote1` varchar(255) NOT NULL,
  --
  `keynote2` varchar(255) NOT NULL,
  `keynote3` varchar(255) NOT NULL, 
  PRIMARY KEY(`id`),
  KEY `i_weixin_notify_user_id` (`user_id`) USING BTREE,
  KEY `i_weixin_notify_order_id` (`order_id`) USING BTREE
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- =================================
-- 系统信息表
-- =================================
DROP TABLE IF EXISTS `sys_info`;
CREATE TABLE `sys_info`(
	-- id 主键自增长
	`id` bigint(20) NOT NULL AUTO_INCREMENT,
	-- ICP 证书号
	`icp_number` varchar(200) DEFAULT '',
	-- 客服QQ号码
	`qq` varchar(20) DEFAULT '',
	-- 二维码图片存放路径
	`qr_code_url` varchar(200) DEFAULT '',
	-- 收徒二维码存放路径
	`apprentice_qr_code_url` varchar(200) DEFAULT '',
	-- 服务热线
	`hotline` varchar(100) DEFAULT '',
	-- 合作渠道
	`cooperate_phone` varchar(100) DEFAULT '',
	-- 分享词语（微信站收徒）提示语
	`share_notify` varchar(255) DEFAULT '',
	-- 滚动文字设置
	`scroll_ball` varchar(512) DEFAULT '',
	-- 订单短信通知电话
	`notify_phone` varchar(255) DEFAULT '',
	-- 微信appid
	`appid` varchar(100) DEFAULT '',
	-- 微信公众号秘钥
	`appsecret` varchar(100) DEFAULT '',
	-- SEO设置 标题
	`seo_title` varchar(255) DEFAULT '',
	-- SEO设置 关键词
	`seo_keyword` varchar(255) DEFAULT '',
	-- SEO设置 描述
	`seo_describe` varchar(255) DEFAULT '',
	-- 网站总访问量
	`website_total_pv` bigint(20) DEFAULT '0',	
	PRIMARY KEY (`id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;
 
-- =================================
-- 广告表 (pc广告 、 微信站轮播图      )
-- =================================
DROP TABLE IF EXISTS `advertisement`;
CREATE TABLE `advertisement`(
	-- id 主键 自增长
	`id` bigint(20) NOT NULL AUTO_INCREMENT,
	-- source 来源 pc站 微信站
	`source` smallint(6) NOT NULL,
	-- 位置
	`position` smallint(6) NOT NULL,
	-- 图片地址
	`picurl` varchar(255)  DEFAULT '',
	-- 是否使用外链
	`isouturl` smallint default 0,
	-- 添加时间
	`create_time` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
	-- 外链地址
	`address` varchar(350) DEFAULT '',
	PRIMARY KEY(`id`),
	KEY `i_advertisement_source` (`source`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- =================================
-- 产品类型
-- =================================
DROP TABLE IF EXISTS `product_type`;
CREATE TABLE `product_type` (
  -- ID 主键 自增长
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  -- 提单类型0-自助贷1-赚差价2-赚提成
  `bill_type` smallint(6) NOT NULL,
  -- 产品类型
  `product_name` varchar(10) NOT NULL,
  -- 创建时间
  `create_time` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
  -- 状态 0-已删除 1-正常
  `state` smallint(6) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- =================================
-- 产品表
-- =================================
DROP TABLE IF EXISTS `product`;
CREATE TABLE `product` (
  -- ID主键 自增长
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  -- 产品类型
  `product_type_id` bigint(20) NOT NULL,
  -- 产品详情
  `product_info_id` bigint(20) NOT NULL,
  -- 抵押方式0-抵押贷1-信用贷
  `mortgage_type` smallint(6) NOT NULL,
  -- 利息模式0-利息模式按月1-利息模式按日2-手续费、收益金模式3-特殊模式
  `interest_type` smallint(6) NOT NULL,
  -- 添加时间
  `create_time` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
  -- 是否推荐到首页0-不推荐1-推荐
  `is_display` smallint(6) NOT NULL,
  -- 是否已删除0-未删除1-已删除
  `status` smallint(6) NOT NULL DEFAULT '0',
  -- 申请次数
  `apply_times` int(11) NOT NULL DEFAULT '0',
  -- 排序
  `sortid` int(6) DEFAULT '1',
  -- 首页排序
  `index_sortid` int(6) DEFAULT '1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- =================================
-- 产品详细信息表
-- =================================
DROP TABLE IF EXISTS `product_info`;
CREATE TABLE `product_info` (
  -- ID主键 自增长
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  -- 产品名称
  `product_name` varchar(22) NOT NULL,
  -- 贷款利率
  `loan_rate` decimal(5,5) NOT NULL DEFAULT '0',
  -- 贷款页面利率-小
  `loan_min_rate` varchar(40)  DEFAULT NULL,
  -- 贷款页面利率-大
  `loan_max_rate` varchar(40)  DEFAULT NULL,
  -- 贷款金额
  `loan_min_amount` decimal(15,0) NOT NULL,
  `loan_max_amount` decimal(15,0) NOT NULL,
  -- 贷款期限
  `loan_min_time` int(11) NOT NULL,
  `loan_max_time` int(11) NOT NULL,
  -- 还款方式
  `repayment` varchar(40) NOT NULL,
  -- 产品信息
  `product_desc` text NOT NULL,
  -- 申请条件
  `requirment` text NOT NULL,
  -- 材料准备
  `materials` text NOT NULL,
  -- 佣金信息
  -- 日息低价、月息低价
  `spread` decimal(5,5)  DEFAULT '0',
  -- 加价息差
  `spread_min` decimal(5,5) DEFAULT '0',
  `spread_max` decimal(5,5) DEFAULT '0',
  -- 手续费用
  `expense` decimal(5,5) DEFAULT '0',
  -- 提成比例
  `percentage_rate` decimal(5,5) DEFAULT '0',
  -- 会员佣金比例
  `user_rate` decimal(5,5) DEFAULT '0',
  -- 师父佣金比例
  `father_rate` decimal(5,5) DEFAULT '0',
  -- 商家佣金比例
  `seller_rate` decimal(5,5) DEFAULT '0',
  -- 业务员佣金比例
  `salesman_rate` decimal(5,5) DEFAULT '0',
  -- 佣金比例说明
  -- 会员佣金说明
  `user_rate_desc` varchar(150) DEFAULT '',
  -- 师父佣金说明
  `father_rate_desc` varchar(150) DEFAULT '',
  -- 算法参数a
  `algo_param_a` decimal(5,5) DEFAULT '0',
  -- 算法参数b
  `algo_param_b` decimal(5,5) DEFAULT '0',
  -- 算法参数c
  `algo_param_c` decimal(5,5) DEFAULT '0',
  -- 算法参数d
  `algo_param_d` decimal(5,5) DEFAULT '0',
  -- 算法参数e
  `algo_param_e` decimal(5,5) DEFAULT '0',
  -- 算法参数f
  `algo_param_f` decimal(5,5) DEFAULT '0',
  -- 算法参数g
  `algo_param_g` decimal(5,5) DEFAULT '0',
  -- 算法参数h
  `algo_param_h` decimal(5,5) DEFAULT '0',
  -- 预计佣金发放形式
  `brokerage_send_desc` varchar(40)  DEFAULT NULL,
  -- 前端佣金比例文字
  `font_brokerage_desc` varchar(40) DEFAULT NULL,
  -- 前端佣金数值
  `font_brokerage_num` varchar(40) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
-- =================================
-- 提现订单表
-- =================================
DROP TABLE IF EXISTS `flow_withdraw`;
CREATE TABLE `flow_withdraw`(
	-- id主键自增长
	`id` bigint(20) NOT NULL AUTO_INCREMENT,
	-- 提现流水编号
	`flowno` varchar(30) NOT NULL DEFAULT '',
	-- 关联用户
	`user_id` bigint(20) NOT NULL,
	-- 提现的金额
	`money` decimal(15,2) NOT NULL,
	-- 提现的时间
	`create_time` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
	-- 提现状态0-待审核 1-未放款 2-体现失败 3-已提现
	`status` smallint(6) NOT NULL DEFAULT '0',
	-- 审核时间
	`verify_time` datetime,
	-- 审核人
	`verify_users` bigint(20),
	-- 拒绝理由
	`reject_reason` varchar(200) DEFAULT '',
	-- 开户的银行
	`bankname` varchar(40) NOT NULL DEFAULT '',
	-- 开户名
	`accountname` varchar(40) NOT NULL DEFAULT '',
	-- 银行的帐号
	`account` varchar(40) NOT NULL DEFAULT '',
	-- 开户的地区-省
	`province` varchar(40) NOT NULL DEFAULT '',
	-- 开户的城市
	`city` varchar(40) NOT NULL DEFAULT '',
	-- 开户 县/区
	`area` varchar(40)  DEFAULT '',
	-- 手机号码
	`telephone` varchar(24) NOT NULL DEFAULT '',
	-- 发放时间
	`send_date`  datetime,
	PRIMARY KEY (`id`),
	KEY `i_flow_withdraw_user_id` (`user_id`) USING BTREE,
	KEY `i_flow_withdraw_status` (`status`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- =================================
-- 订单 表
-- =================================
DROP TABLE IF EXISTS `orderform`;
CREATE TABLE `orderform` (
	-- id 
	`id` bigint(20) NOT NULL AUTO_INCREMENT,
	-- user
	`user_id` bigint(20) DEFAULT NULL,
	-- source 0-pc 1-微信站的订单 
	`source` int(6) DEFAULT '0',
	-- 姓名 
	`name` varchar(20) NOT NULL DEFAULT '',
	-- 身份证后6位
	`idcard` varchar(12) NOT NULL DEFAULT '',
	-- 手机号码
	`telephone` varchar(24) NOT NULL DEFAULT '',
	-- 所在地  省份
	`province` varchar(24) NOT NULL DEFAULT '',
	-- 所在地 城市
	`city` varchar(24) NOT NULL DEFAULT '',
	-- 当前产品的id
	`product_id` bigint(11) NOT NULL DEFAULT '0',
	-- 对应的产品信息
	`product_info_id` bigint(11) NOT NULL DEFAULT '0',
	-- money 贷款的金额 
	`money` decimal(15,0) NOT NULL DEFAULT '0',
	-- 贷款实际金额
	`actual_money` decimal(15,0) NOT NULL DEFAULT '0',
	-- 贷款的期限
	`loan_time` int(6) NOT NULL DEFAULT '0',
	-- 贷款利率
	`loan_insterest_rate` decimal(5,5)  DEFAULT '0',
	-- 实际预计佣金
	`brokerage_rate_num` decimal(17,2) DEFAULT '0',
	-- 提交的订单的备注
	`remark` varchar(255) DEFAULT '',
	-- 订单状态 0-待联系，1-已联系 2-已面谈  3-待审核  4-待放款 5-已放款 6-无效订单 
	`status` int(6) NOT NULL DEFAULT '0',
	-- 一下字段存在于后台
	-- 订单编号/流水
	`order_no` varchar(20) NOT NULL DEFAULT '',
	-- 合同编号
	`comtract_num` varchar(200) DEFAULT '',
	-- 订单无效原因
	`invalid_reason` varchar(255) DEFAULT '',
	-- 佣金发放类型 0-一次性发放 1-分次发放
	`payment_brokerage_type` int(6),
	-- 创建时间
	`create_time` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
	-- 加价息差值
	`spread_rate` decimal(5,5)  DEFAULT '0',
	-- 提成比例、佣金
	`percentage_rate` decimal(5,5)  DEFAULT '0',
	-- 预计佣金总和
	`brokerage_rate_total` decimal(17,2) DEFAULT '0',
	
	-- 实际贷款利率
	`actual_loan_insterest_rate` decimal(5,5)  DEFAULT '0',
	-- 下单人预计佣金
	`self_brokerage_num` decimal(17,2) DEFAULT '0',
	-- 旧的产品
	`old_product_id` bigint(11)  DEFAULT NULL,
	-- 旧的师父
	`old_parent_id` bigint(11)  DEFAULT NULL,
	-- 旧的商家
	`old_bussiness_id` bigint(11)  DEFAULT NULL,
	-- 旧的业务员
	`old_salesman_id` bigint(11)  DEFAULT NULL,
	
	-- 旧的贷款期限
	`old_loan_time` int(6) NOT NULL DEFAULT '0',
	
	PRIMARY KEY(`id`),
	KEY `idx_orderform_user_id` (`user_id`) USING BTREE,
	KEY `i_orderform_product_id` (`product_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- =================================
-- 佣金明细表 发放记录
-- =================================
DROP TABLE IF EXISTS `brokerage_distribution`;
CREATE TABLE `brokerage_distribution` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  -- 订单id
  `orderform_id` bigint(20) ,
  -- 佣金
  `brokerage` decimal(17,2) NOT NULL,
  -- 添加时间
  `add_date` datetime NOT NULL,
  -- 操作者
  `operater` bigint(20) NOT NULL,
  -- 佣金所属类型  （0本人，1师父，2师公）
  `brokerage_type` smallint(6) DEFAULT '0',
  -- 佣金所属userid
  `userid` bigint(20) NOT NULL,
  -- 备注
  `remark` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_orderformId` (`orderform_id`) USING BTREE,
  KEY `idx_operater` (`operater`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- =================================
--  佣金表
-- =================================
DROP TABLE IF EXISTS `brokerage_apply`;
CREATE TABLE `brokerage_apply`(
	`id` bigint(20) NOT NULL AUTO_INCREMENT,
	-- 对应的订单id
	`orderform_id` bigint(20) NOT NULL DEFAULT '0',
	-- 订单编号
	`brokerage_no` varchar(20) NOT NULL DEFAULT '',
	-- 用户id
	`user_id` bigint(20) NOT NULL DEFAULT '0',
	-- 产品id
  	`product_id` bigint(20) NOT NULL DEFAULT '0',
  	`product_info_id` bigint(20) NOT NULL DEFAULT '0',
	-- 订单创建时间
	`create_time` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
	-- 提单人佣金
	`self_brokerage` decimal(17,2) NOT NULL DEFAULT '0',
	-- 师父佣金
	`father_brokerage` decimal(17,2) NOT NULL DEFAULT '0',
	-- 业务员佣金
	`salesman_brokerage` decimal(17,2) DEFAULT '0' ,
	-- 商家佣金
	`business_brokerage` decimal(17,2) DEFAULT '0',
	-- 贷款金额
	`money` decimal(15,0) NOT NULL,
	-- 贷款利率 
	`interest` decimal(5,5)  DEFAULT '0',
	-- 状态
	`status` int(6) NOT NULL,
	-- 拒绝原因
	`reject_reason` varchar(255) DEFAULT NULL,
	-- 剩余发放佣金
  	-- `left_brokerage` decimal(17,2) default NULL,
  	-- 单次发放佣金
  	-- `single_brokerage` decimal(17,2) default NULL,
  	-- 佣金发放方式 0- 一次发放 1- 多次发放
  	`send_status` smallint(6) NOT NULL DEFAULT '0',
  	-- 发放次数
  	`send_times` smallint(6) NOT NULL DEFAULT '0',
  	-- 已发放次数
  	`has_send_times` smallint(6) NOT NULL DEFAULT '0',
  	-- 提单人剩余佣金
  	`self_residual_brokerage` decimal(17,2) DEFAULT '0',
  	-- 师父剩余佣金
	`father_residual_brokerage` decimal(17,2) DEFAULT '0',
	-- 业务员剩余佣金
	`salesman_residual_brokerage` decimal(17,2) DEFAULT '0',
	-- 商家剩余佣金
	`business_residual_brokerage` decimal(17,2) DEFAULT '0', 
  	-- 提单发放金额
  	`self_submit_brokerage` decimal(17,2) DEFAULT '0',
  	-- 师父发放金额
  	`father_submit_brokerage` decimal(17,2) DEFAULT '0',
  	-- 商家发放金额
  	`business_submit_brokerage` decimal(17,2) DEFAULT '0',
  	-- 业务员发放金额
  	`salesman_submit_brokerage` decimal(17,2) DEFAULT '0',
  	-- 佣金审核通过时间
  	`checkpass_time` datetime  DEFAULT NULL,
  	PRIMARY KEY (`id`),
  	KEY `i_brokerage_apply_orderform_id` (`orderform_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- =================================
--  历史记录-佣金修改
-- =================================
DROP TABLE IF EXISTS `brokerage_apply_modify_history`;
CREATE TABLE `brokerage_apply_modify_history` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  -- 操作者
  `oper_id` bigint(20) NOT NULL,
  -- 佣金订单id
  `brokerage_apply_id` bigint(20) NOT NULL,
  -- 操作内容
  `content` varchar(255) NOT NULL,
  -- 创建时间
  `add_date` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- =================================
-- 历史记录-佣金审核
-- =================================
-- DROP TABLE IF EXISTS `brokerage_apply_apply_history`;
-- CREATE TABLE `brokerage_apply_apply_history` (
-- `id` bigint(20) NOT NULL AUTO_INCREMENT,
-- `oper_id` bigint(20) NOT NULL,
-- `brokerage_apply_id` bigint(20) NOT NULL,
-- `apply_type` smallint(6) NOT NULL,
-- `content` varchar(255) NOT NULL,
-- `add_date` datetime NOT NULL,
-- PRIMARY KEY (`id`)
-- ) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- =================================
--  订单  产品改变记录
-- =================================
DROP TABLE IF EXISTS `orderform_product_record`;
CREATE TABLE `orderform_product_record` (
	-- id
	`id` bigint(20) NOT NULL AUTO_INCREMENT,
	-- 对应的订单
	`orderform_id` bigint(20) DEFAULT '0',
	-- 创建时间
	 `create_time` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
	-- 对应的 订单操作记录表的id
	`order_oper_log_id` bigint(20)  DEFAULT '0',
	-- 修改前的产品id
	`before_product` bigint(11) DEFAULT '0',
	-- 修改之后的产品的id
	`after_product` bigint(11) DEFAULT '0',
	-- 修改之前的信息
	-- 贷款金额
	`money` decimal(15,0) DEFAULT '0',
	-- 贷款的期限	
	`month` int(6) DEFAULT '0',
	-- 贷款比例
	`loan_interest_rate` decimal(5,5)  DEFAULT '0',
	-- 日息加价值
	`spread_rate` decimal(5,5)  DEFAULT '0',
	PRIMARY KEY (`id`),
	KEY `i_orderform_product_record_orderform` (`orderform_id`) USING BTREE
) ENGINE =InnoDB DEFAULT CHARSET =utf8;
-- =================================
-- 订单改变状态备注表
-- =================================
DROP TABLE IF EXISTS `orderform_remark`;
CREATE TABLE `orderform_remark` (
	-- id
	`id` bigint(20) NOT NULL AUTO_INCREMENT,
	-- 操作人
	`user_id` bigint(20) DEFAULT '0',
	-- 对应订单的id
	`orderform_id` bigint(20) DEFAULT '0',
	-- 备注
	`remark` varchar(255) NOT NULL DEFAULT '',
	-- 添加备注的时间
	`create_time` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
	PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- =================================
-- 订单 操作日志表
-- =================================
DROP TABLE IF EXISTS `order_oper_log`;
CREATE TABLE `order_oper_log` (
   -- 主键 自增长
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
   -- 操作人（user） id
   `opertor_id` bigint(20) NOT NULL,
   -- 操作内容
   `oper_content` varchar(40) NOT NULL,
   -- 创建时间
   `create_time` datetime DEFAULT NULL,
   -- 对应的订单
   `orderform_id` bigint(11) DEFAULT '0',
   -- 或者  对应的提现订单
   `withdraw_id` bigint(11) DEFAULT '0',
   -- orderform_product_change_record 如果操作类型是改变产品的话 该字段有值
   `product_change_record_id` bigint(20) DEFAULT '0', 
   -- 针对订单的操作类型，如果不是特定的其他为0
   `operat_type` smallint(6) DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `i_oper_log_opertor_id` (`opertor_id`) USING BTREE,
  KEY `i_oper_log_order_id` (`orderform_id`) USING BTREE,
  KEY `i_oper_log_withdraw_id` (`withdraw_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- =================================
-- 用户银行账户信息表
-- =================================
DROP TABLE IF EXISTS `user_bankinfo`;
CREATE TABLE `user_bankinfo` (
  -- ID
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  -- 关联用户ID
  `user_id` bigint(20) default NULL,
  -- 开户银行
  `bankname` varchar(60) NOT NULL DEFAULT '',
  -- 开户名
  `accountname` varchar(40) NOT NULL DEFAULT '',  
  -- 银行账号
  `bankaccount` varchar(40) NOT NULL DEFAULT '',
  -- 开户地区-省
  `province` varchar(40) NOT NULL DEFAULT '',
  -- 开户地区-市
  `city` varchar(40) NOT NULL DEFAULT '',
  -- 开户地区-县/区
  `area` varchar(40) DEFAULT '',
  -- 添加时间
  `add_date` datetime NOT NULL DEFAULT now(),
  PRIMARY KEY (`id`),
  KEY `i_user_bankinfo_user_id` (`user_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- =================================
-- 获取佣金记录表
-- =================================
DROP TABLE IF EXISTS `record_brokerage`;
CREATE TABLE `record_brokerage` (
  -- ID
  `id` bigint(20) NOT NULL AUTO_INCREMENT, 
  -- 用户id
  `user_id` bigint(20) default NULL,
  -- 下单人
  `gain_user_id` bigint(20) default NULL, 
  -- 订单id
  `order_id` bigint(20) default NULL,
  -- 确认人
  `confirm_name` varchar(20) NOT NULL DEFAULT '',
  -- 与会员的关系0-本人1-徒弟2-徒孙3-徒孙孙
  `relate` smallint(6) NOT NULL DEFAULT '0',
  -- 抽佣比例
  `have_brokerage_rate` decimal(5,5) NOT NULL DEFAULT '0',
  -- 获得佣金
  `have_brokerage` decimal(17,2) NOT NULL DEFAULT '0',
  -- 佣金发放时间
  `send_brokerage` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
  -- 下单人userInfoBoth
  `gain_infoboth_id` bigint(20) default NULL, 
  
  PRIMARY KEY (`id`),
  KEY `i_record_brokerage_user_id` (`user_id`) USING BTREE,
  KEY `i_record_brokerage_order_id` (`order_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- =================================
-- 登录日志表
-- =================================
DROP TABLE IF EXISTS `loginlog`;
CREATE TABLE `loginlog` (
  -- ID
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  -- 用户id
  `user_id` bigint(20) DEFAULT NULL,
  -- 登录时间
  `login_date` datetime NOT NULL,
  -- 登录ip
  `user_login_ip` varchar(40) DEFAULT '',
  -- 登录地址
  `login_address` varchar(40) DEFAULT '',
  -- 登陆累计次数
  `login_times` int(10) DEFAULT '1',
  PRIMARY KEY (`id`),
  KEY `i_loginlog_user_id` (`user_id`) USING BTREE,
  KEY `i_loginlog_login_date` (`login_date`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- =================================
-- 操作日志表
-- =================================
DROP TABLE IF EXISTS `user_oper_log`;
CREATE TABLE `user_oper_log` (
  -- ID
  `id` bigint(20) NOT NULL AUTO_INCREMENT, 
  -- 员工账号
  `user_id` bigint(20) DEFAULT NULL,
  -- 操作时间
   `oper_time` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
  -- 操作内容
  `oper_content` varchar(300) NOT NULL,
   PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- =================================
-- 会员-备注表
-- =================================
DROP TABLE IF EXISTS `user_remark`;
CREATE TABLE `user_remark` (
	-- ID
	`id` bigint(20) NOT NULL AUTO_INCREMENT, 
	-- 所属会员ID
	`user_id` bigint(20) NOT NULL,
	-- 添加备注操作人
	`opertor` bigint(20) NOT NULL ,
	-- 备注
	`remark` varchar(255) DEFAULT NULL,
	-- 添加时间
	`create_time` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
	 PRIMARY KEY (`id`),
	 KEY `i_user_remark_user_id` (`user_id`) USING BTREE
)ENGINE=InnoDB DEFAULT CHARSET=utf8;	

-- =================================
-- 用户每日数据统计
-- =================================
DROP TABLE IF EXISTS `user_daily_statistics` ;
CREATE TABLE `user_daily_statistics`(
	-- ID
	`id` bigint(20) NOT NULL AUTO_INCREMENT,
	-- 用户
	`user_id` bigint(20) NOT NULL DEFAULT '0',
	-- 徒弟数量
	`son_sum` int(11) NOT NULL DEFAULT '0',
	-- 徒孙数量
	`grand_son_sum` int(11) NOT NULL DEFAULT '0',
	-- 徒孙孙数量
	`ggrand_son_sum` int(11) NOT NULL DEFAULT '0',
	-- 订单数量
	`order_sum` int(11) NOT NULL DEFAULT '0',
	-- 成功订单数
	`order_success_sum` int(11) NOT NULL DEFAULT '0',
	-- 统计时间	
	`statistics_date` datetime NOT NULL,
	-- 登录次数
	`logintimes` smallint(6) NOT NULL DEFAULT '0',
	PRIMARY KEY (`id`),
   	KEY `i_user_daily_statistics_user_id` (`user_id`) USING BTREE
)ENGINE=InnoDB DEFAULT CHARSET=utf8;
-- =================================
-- 订单-体现-佣金订单号值
-- =================================
DROP TABLE IF EXISTS `order_serial` ;
CREATE TABLE `order_serial`(
	-- ID
	`id` bigint(20) NOT NULL AUTO_INCREMENT,
	-- 编号
	`serial` int (10) NOT NULL DEFAULT '0',
	-- 类型
	`name` varchar(10) NOT NULL DEFAULT '',
	PRIMARY KEY (`id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;
-- =================================
--  佣金多次发放记录
-- =================================
DROP TABLE IF EXISTS `brokerage_payment_records`;
CREATE TABLE `brokerage_payment_records` (
  -- ID
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  -- 所属佣金订单
  `brokerage_apply_id` bigint(20) NOT NULL DEFAULT '0',
  -- 所属订单orderform
  `orderform_id` bigint(20) NOT NULL DEFAULT '0',
  -- 本次是第几次
  `number` smallint(6) NOT NULL DEFAULT '0',
  -- 创建时间
  `create_time` datetime NOT NULL,
  -- 备注
  `remark` varchar(255) DEFAULT NULL,
  -- 提单人发放金额
  `self_payment_brokerage` decimal(17,2) DEFAULT '0',
  -- 师父发放金额
  `father_payment_brokerage` decimal(17,2) DEFAULT '0',
  -- 商家发放金额
  `business_payment_brokerage` decimal(17,2) DEFAULT '0',
  -- 业务员发放金额
  `salesman_payment_brokerage` decimal(17,2) DEFAULT '0',
  PRIMARY KEY(`id`),
  KEY `i_brokerage_payment_records_orderform_id` (`orderform_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- =================================
--  商家信息表
-- =================================
DROP TABLE IF EXISTS `user_info_seller`;
CREATE TABLE `user_info_seller` (
	-- ID
  	`id` bigint(20) NOT NULL AUTO_INCREMENT,	
  	-- 坐标 经度
  	`longitude` varchar(25) DEFAULT '0',
  	-- 坐标纬度
  	`latitude` varchar(25) DEFAULT '0',
  	-- 详细地址
  	`address` varchar(255) DEFAULT '0', 
  	-- 保存的百度地图的id
  	`baidu_map_id` smallint(6) DEFAULT '0',
  	-- 公司名
	`company` varchar(255) NOT NULL DEFAULT '',
  	PRIMARY KEY(`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- =================================
--  微信推送表
-- =================================
DROP TABLE IF EXISTS `weixin_post`;
CREATE TABLE `weixin_post` (
  -- ID
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
   -- 通知类型 0-贷款提交成功 1-贷款审核成功 2-贷款审核失败 3-贷款放款成功 4-佣金生成 5-佣金放款 6-提现到账
  `notice_type` smallint(6) NOT NULL DEFAULT '0',
  -- 通知状态 0-开	1-关
  `state` smallint(6) NOT NULL DEFAULT '0',
  -- 模板id
  `template_id` varchar(100) DEFAULT NULL,
    -- 首段
  `first` varchar(255) DEFAULT NULL,
    -- 备注
  `remark` varchar(255) DEFAULT NULL,
  PRIMARY KEY(`id`),
  KEY `i_weixin_post_template_id` (`template_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
-- =================================
--  友情链接表
-- =================================
DROP TABLE IF EXISTS `friendship_link`;
CREATE TABLE `friendship_link` (
	-- id
	`id` bigint(20) NOT NULL AUTO_INCREMENT,
	-- 链接地址
	`link_url` varchar(500) NOT NULL DEFAULT '',
	-- 链接名称
	`title` varchar(255) NOT NULL DEFAULT '',
	-- 创建时间
  	`create_time` datetime NOT NULL DEFAULT now(),
	-- 修改时间
	`modify_time` datetime DEFAULT NULL,
	-- 状态  0 正常 1 删除
	`status` smallint(6) NOT NULL DEFAULT '0',
	PRIMARY KEY(`id`)  	
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
-- =================================
--  二维码活动
-- =================================
DROP TABLE IF EXISTS `qrcode_activity`;
CREATE TABLE `qrcode_activity` (
	-- id
	`id` bigint(20) NOT NULL AUTO_INCREMENT,
	-- 名称
	`name` varchar(50) NOT NULL DEFAULT '',
	-- 二维码
	`qrcode` varchar(350) DEFAULT NULL,
	-- 添加时间
	`create_time` datetime NOT NULL DEFAULT now(),
	-- 扫描量
	`scan_num` int(11) NOT NULL DEFAULT '0',
	-- 关注量
	`concern_num` int(11)  DEFAULT NULL,
	-- 留存量
	`stay_num`  int(11)  DEFAULT NULL,
	-- 关注率
	concern_rate  decimal(5,2) DEFAULT NULL,
	-- 留存率
	`stay_rate` decimal(5,2) DEFAULT NULL,
	PRIMARY KEY(`id`)  	
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 访问量统计表
DROP TABLE IF EXISTS `pv_common_statistics`;
CREATE TABLE `pv_common_statistics` (
	-- id
	`id` bigint(20) NOT NULL AUTO_INCREMENT,
	-- 每日统计访问量
	`website_pv` bigint(20) DEFAULT '0',
	-- 统计时总计的访问量
	`website_total_pv` bigint(20) DEFAULT '0',
	-- 统计的时间时间
 	`statistics_date` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
	PRIMARY KEY(`id`)  	
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


