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

-- 增加昨日网站流量统计
alter table `sys_info` add column `website_total_pv` bigint(20) DEFAULT '0';
-- 订单表添加旧的贷款期限
alter table `orderform` add column `old_loan_time` int(6) NOT NULL DEFAULT '0';

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

-- 添加前端佣金说明和数值
alter table `product_info` add column `font_brokerage_desc` varchar(40) DEFAULT NULL;
alter table `product_info` add column `font_brokerage_num` varchar(40) DEFAULT NULL;
-- 2016-12-16 lzh
alter table `brokerage_apply` add column `checkpass_time` datetime  DEFAULT NULL;

-- 2016-12-14 xpk
alter table `user_info_both` add column `apprentice_aware_brokerage` decimal(17,2) DEFAULT '0';
-- 2016-12-14 lzh
alter table `record_brokerage` add column `gain_infoboth_id` bigint(20) default NULL;
-- 2016-12-14 lzh 
alter table `orderform` add column `old_parent_id` bigint(11)  DEFAULT NULL;
alter table `orderform` add column `old_bussiness_id` bigint(11)  DEFAULT NULL;
alter table `orderform` add column `old_salesman_id` bigint(11)  DEFAULT NULL;

-- 2016 12.8 xpk 修改友情链接长度
alter table  `friendship_link` modify column  `link_url` varchar(500) NOT NULL DEFAULT '';

-- 2016 12.7 xpk 修改每日统计字段名
alter table `user_daily_statistics` add column `logintimes` smallint(6) NOT NULL DEFAULT '0';
alter table `user_daily_statistics` drop column `login_times`;

alter table `weixin_user` modify column  `sex` smallint(6) DEFAULT '0';
alter table `weixin_user` modify column  `openid` varchar(40) DEFAULT NULL;
alter table `weixin_user` modify column  `nickname` varchar(40) DEFAULT NULL;
alter table `weixin_user` modify column  `subscribe_time` datetime DEFAULT now();
alter table `weixin_user` modify column  `subscribe` smallint(6) DEFAULT '0';

alter table `orderform` add column `old_product_id` bigint(11) DEFAULT NULL;
alter table `product_info` add column `brokerage_send_desc` varchar(40)  DEFAULT NULL;

-- 2016 12.6 增加资讯摘要 xpk
alter table `information` add column `summary` varchar(255) DEFAULT '';

-- 2016 12.5 商家信息增加公司名称 xpk
alter table `user_info_seller` add column `company` varchar(255) NOT NULL DEFAULT '';

-- 2016 12.2 增加 seo设置   友情链接表 xpk
alter table `sys_info` add column `seo_title` varchar(255) DEFAULT '';
alter table `sys_info` add column `seo_keyword` varchar(255) DEFAULT '';
alter table `sys_info` add column `seo_describe` varchar(255) DEFAULT '';
alter table `sys_info` add column `seo_describe` varchar(255) DEFAULT '';
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


-- 订单表增加下单人预计佣金
alter table `orderform` add column `self_brokerage_num` decimal(17,2) DEFAULT '0';

-- 会员每日统计增加登录次数 xpk
alter table `user_daily_statistics` add column `login_times` smallint(6) NOT NULL DEFAULT '0';

-- 修改资讯的字段的长度 增加商家信息表 xpk
alter table `information` modify column `content` longtext;
alter table `user` add column `user_info_seller_id` bigint(20) DEFAULT NULL;

-- 系统信息表滚动文字设置长度改为512 2016-11-24 csy
alter table sys_info modify `notify_phone` varchar(255) DEFAULT '';
-- 订单表增加实际贷款利率 lzh
alter table `orderform` add column `actual_loan_insterest_rate` decimal(5,5)  DEFAULT '0';

-- 修改操作日志 内容长度   xpk 2016/11/23
alter table `user_oper_log` modify column `oper_content` varchar(300) NOT NULL;
-- 订单表添加预计佣金总和
alter table `orderform` add column `brokerage_rate_total` decimal(17,2) DEFAULT '0';

-- 2016/11/23 csy 微信推送表 修改字段类型
alter table `weixin_post` modify column `template_id` varchar(100) DEFAULT NULL;

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
  `template_id` varchar(100) NOT NULL,
    -- 首段
  `first` varchar(255) DEFAULT NULL,
    -- 备注
  `remark` varchar(255) DEFAULT NULL,
  PRIMARY KEY(`id`),
  KEY `i_weixin_post_template_id` (`template_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;




-- 系统信息表增加微信公众号和秘钥
alter table `sys_info` add column `appid` varchar(100) DEFAULT '';
alter table `sys_info` add column `appsecret` varchar(100) DEFAULT '';


-- 2016/11/21 csy 微信用户表 修改字段类型
alter table `weixin_user` modify column `nickname` varchar(40) DEFAULT NULL;

-- 2016/11/21 用户银行卡添加 添加时间  
alter table `user_bankinfo` add column `add_date` datetime NOT NULL DEFAULT now();
-- 商家信息表
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
  	PRIMARY KEY(`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 2016/11/18 xpk 增加佣金多次发放记录表，删除 brokerage_distribution。
drop table brokerage_distribution ;
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


-- 2016/11/17 订单操作增加操作类型 针对订单
alter table `order_oper_log` add column `operat_type` smallint(6) DEFAULT '0';

-- 2016/11/17修改订单贷款利率字段
alter table `orderform` modify column `loan_insterest_rate` decimal(5,5)  DEFAULT '0';
alter table `brokerage_apply` modify column `interest` decimal(5,5)  DEFAULT '0';
alter table `orderform_product_record` modify column `loan_interest_rate` decimal(5,5)  DEFAULT '0';

-- 2016/11/17 修改提现订单的拒绝理由长度
alter table `flow_withdraw` modify column `reject_reason` varchar(200) DEFAULT '';
-- xpk 修改提现字段的位数
alter table `flow_withdraw` modify column `money` decimal(15,2) NOT NULL;

-- 2016/11/16 修改产品的贷款利率
alter table `product_info` modify column `loan_rate` decimal(5,5) NOT NULL DEFAULT '0';
-- 2016/11/16 修改username非必填
alter table `user` modify column `username` varchar(40)  DEFAULT '';

-- 修改用户共同表 可提现佣金和活动佣金可以为空
alter table `user_info_both` modify column `brokerage_can_withdraw` decimal(17,2) DEFAULT '0';
alter table `user_info_both` modify column `activity_brokerage` decimal(17,2) DEFAULT '0';

-- 修改获取佣金记录表
alter table `record_brokerage` modify column `have_brokerage` decimal(17,2) NOT NULL DEFAULT '0';
-- 修改系统表 增加收徒二维码存放路径
alter table `sys_info` add column `apprentice_qr_code_url` varchar(200) DEFAULT '' after `qr_code_url`;

-- 修改information 标题字段的大小 2016/11/14 xpk
alter table `information` modify column `title` varchar(255) NOT NULL DEFAULT '';

-- 佣金订单表
alter table `brokerage_apply` drop column `left_brokerage`;
alter table `brokerage_apply` drop column `single_brokerage`;
alter table `brokerage_apply`add column `send_status` smallint(6) NOT NULL DEFAULT '0';
alter table `brokerage_apply`add column `send_times` smallint(6) NOT NULL DEFAULT '0';
alter table `brokerage_apply`add column `has_send_times` smallint(6) NOT NULL DEFAULT '0';
alter table `brokerage_apply`add column `self_residual_brokerage` decimal(17,2) DEFAULT '0';
alter table `brokerage_apply`add column `father_residual_brokerage` decimal(17,2) DEFAULT '0';
alter table `brokerage_apply`add column `salesman_residual_brokerage` decimal(17,2) DEFAULT '0';
alter table `brokerage_apply`add column `business_residual_brokerage` decimal(17,2) DEFAULT '0';
alter table `brokerage_apply`add column `self_submit_brokerage` decimal(17,2) DEFAULT '0';
alter table `brokerage_apply`add column `father_submit_brokerage` decimal(17,2) DEFAULT '0';
alter table `brokerage_apply`add column `business_submit_brokerage` decimal(17,2) DEFAULT '0';
alter table `brokerage_apply`add column `salesman_submit_brokerage` decimal(17,2) DEFAULT '0';


-- 订单表增加提成比例
alter table `orderform` add column `percentage_rate` decimal(5,5)  DEFAULT '0';

-- 修改提现记录表的地区 
alter table `flow_withdraw` modify column `area` varchar(40)  DEFAULT '';

-- 活动规则 类型改为text 2016/11/7 csy
alter table activity modify `activity_rule` text NOT NULL;

-- 修改佣金订单字段 lzh 2016/11/4
alter table `brokerage_apply` add column `product_id` bigint(20) NOT NULL DEFAULT '0' after `user_id`;
alter table `brokerage_apply` add column `product_info_id` bigint(20) NOT NULL DEFAULT '0' after `product_id`;
alter table `brokerage_apply` modify column `interest` varchar(40) NOT NULL;
-- 修改订单字段lzh 2016/11/3
alter table `orderform` modify column `loan_insterest_rate` varchar(40) DEFAULT '';
alter table `orderform_product_record` modify column `loan_interest_rate` varchar(40)  DEFAULT '0';

-- 2016 11.3 增加我的订单总额
alter table `user_info_both` modify column `order_money` decimal(20,0) DEFAULT '0';
alter table `user_info_both` add column `order_money` decimal(20,0) DEFAULT '0';
-- 修改用户共同表 增加活动收徒数
alter table `user_info_both` add column `acitivity_son_sum` int NOT NULL DEFAULT '0' after `withdraw_salt`;

-- 修改产品详情 lzh 2016/10/31
alter table `product_info` drop column `rate_desc`;
alter table `product_info` add column `user_rate_desc` varchar(150) DEFAULT '' after `salesman_rate`;
alter table `product_info` add column `father_rate_desc` varchar(150) DEFAULT ''after `user_rate_desc`;

-- 修改用户共同表 删除身份证 改为 extends表示 额外添加的信息  xpk 2016/10/28
alter table `user_info_both` drop column `idcard`;
alter table `user_info_both` add column `expands` varchar(255) DEFAULT NULL;
-- 修改产品详情字段 lzh 2016/10/27
alter table `product_info` modify column `spread` decimal(5,5)  DEFAULT '0';
alter table `product_info` modify column `spread_min` decimal(5,5)  DEFAULT '0';
alter table `product_info` modify column `spread_max` decimal(5,5)  DEFAULT '0';

alter table `product_info` modify column `expense` decimal(5,5)  DEFAULT '0';
alter table `product_info` modify column `percentage_rate` decimal(5,5)  DEFAULT '0';

alter table `product_info` modify column `user_rate` decimal(5,5)  DEFAULT '0';
alter table `product_info` modify column `father_rate` decimal(5,5)  DEFAULT '0';
alter table `product_info` modify column `seller_rate` decimal(5,5)  DEFAULT '0';
alter table `product_info` modify column `salesman_rate` decimal(5,5)  DEFAULT '0';

alter table `product_info` modify column `algo_param_a` decimal(5,5)  DEFAULT '0';
alter table `product_info` modify column `algo_param_b` decimal(5,5)  DEFAULT '0';
alter table `product_info` modify column `algo_param_c` decimal(5,5)  DEFAULT '0';
alter table `product_info` modify column `algo_param_d` decimal(5,5)  DEFAULT '0';
alter table `product_info` modify column `algo_param_e` decimal(5,5)  DEFAULT '0';
alter table `product_info` modify column `algo_param_f` decimal(5,5)  DEFAULT '0';
alter table `product_info` modify column `algo_param_g` decimal(5,5)  DEFAULT '0';
alter table `product_info` modify column `algo_param_h` decimal(5,5)  DEFAULT '0';


-- =================================
-- 订单-体现-佣金订单号值 lzh 2016/10/27
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

-- 系统信息表滚动文字设置长度改为512 2016-10-27 csy
alter table sys_info modify `scroll_ball` varchar(512) DEFAULT '';

-- xpk 2016.10.27
alter table `advertisement` modify column `picurl` varchar(255) DEFAULT '';

-- 产品详情修改贷款利率为string 型 2016/10/26 lzh
alter table `product_info` modify column `loan_rate` varchar(40) NOT NULL DEFAULT '' ;
alter table `product_info` modify column `loan_min_rate` varchar(40)  DEFAULT NULL;
alter table `product_info` modify column `loan_max_rate` varchar(40)  DEFAULT NULL;

-- 活动文案、对象 改为可以空值。
alter table activity modify `activity_object` varchar(100) DEFAULT '';
alter table activity modify `activity_copy` varchar(100) DEFAULT '';

-- 产品详情修改贷款利率 lzh 
alter table `product_info` modify column `loan_rate` decimal(7,5) NOT NULL DEFAULT '0';
alter table `product_info` modify column `loan_min_rate` decimal(7,5) NOT NULL DEFAULT '0';
alter table `product_info` modify column `loan_max_rate` decimal(7,5) NOT NULL DEFAULT '0';
-- 产品增加日息低价 lzh 2016/10/21
alter table `product_info` add column `spread` decimal(5,5) NOT NULL DEFAULT '0';

-- 提现增加发放时间   增加会员备注表   新增每日会员统计表   2016 10.20 xpk

alter table `flow_withdraw` add column `send_date` datetime;
DROP TABLE IF EXISTS `user_remark`;
CREATE TABLE `user_remark` (
	-- ID
	`id` bigint(20) NOT NULL AUTO_INCREMENT, 
	-- 所属会员ID
	`user_id` bigint(20) NOT NULL,
	-- 添加备注操作人
	`opertor` bigint(20) NOT NULL,
	-- 备注
	`remark` varchar(255) DEFAULT NULL,
	-- 添加时间
	`create_time` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
	 PRIMARY KEY (`id`),
	 KEY `i_user_remark_user_id` (`user_id`) USING BTREE
)ENGINE=InnoDB DEFAULT CHARSET=utf8;	

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
	PRIMARY KEY (`id`),
   	KEY `i_user_daily_statistics_user_id` (`user_id`) USING BTREE
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 增加订单提成佣金和活动奖励佣金 csy 2016 10 20
alter table `user_info_both` add column `order_brokerage` decimal(17,2) NOT NULL DEFAULT '0' after `ggsons_sum`;
alter table `user_info_both` add column `activity_brokerage` decimal(17,2) NOT NULL DEFAULT '0' after `order_brokerage`;

-- 修改产品表 2016、10、20 lzh
alter table `product` add column `index_sortid` int(6) DEFAULT '1';

-- 修改产品详情表 2016/10/20 lzh
alter table `product_info` drop column `grand_father_rate`;
alter table `product_info` drop column `ggrand_father_rate`;
alter table `product_info` add column `seller_rate` decimal(5,5) NOT NULL DEFAULT '0' after `father_rate`;
alter table `product_info` add column `salesman_rate` decimal(5,5) NOT NULL DEFAULT '0' after `seller_rate`;
-- 修改佣金订单表字段
alter table `brokerage_apply` drop column `createTime`;
alter table `brokerage_apply` add column `create_time` datetime NOT NULL DEFAULT '0000-00-00 00:00:00' after `user_id`;

-- 2016 10.19 csy 部门添加累计发放佣金
alter table `department` add column `add_brokerage` decimal(17,2) NOT NULL DEFAULT '0';

-- 广告表增加外链地址  
alter table `advertisement` add column `address` varchar(350) DEFAULT '' ;
-- 订单表删除贷款佣金比例字段,添加加息息差值  lzh 2016/10/18
alter table `orderform` drop column `brokerage_rate`;
alter table `orderform` add column `spread_rate` decimal(5,5) DEFAULT '0';
alter table `product_info` add column `percentage_rate` decimal(5,5) NOT NULL DEFAULT '0';
alter table `orderform_product_record`drop column `brokerage_rate`;
alter table `orderform_product_record` add column `spread_rate` decimal(5,5) DEFAULT '0';

-- 修改数据库中的金额字段 lzh 2016/1018
-- 佣金表
alter table `brokerage_apply` modify column `self_brokerage` decimal(17,2) NOT NULL DEFAULT '0';
alter table `brokerage_apply` modify column `father_brokerage` decimal(17,2) NOT NULL DEFAULT '0';
alter table `brokerage_apply` modify column `salesman_brokerage` decimal(17,2) NOT NULL DEFAULT '0';
alter table `brokerage_apply` modify column `business_brokerage` decimal(17,2) NOT NULL DEFAULT '0';
alter table `brokerage_apply` modify column `money` decimal(15,0) NOT NULL;
alter table `brokerage_apply` modify column `interest` decimal(5,5) NOT NULL;

-- 订单表
alter table `orderform` modify column `money` decimal(15,0) NOT NULL DEFAULT '0';
alter table `orderform` modify column `actual_money` decimal(15,0) NOT NULL DEFAULT '0';
alter table `orderform` modify column `brokerage_rate_num` decimal(17,2) DEFAULT '0';
-- 提现表
alter table `flow_withdraw` modify column `money` decimal(15,0) NOT NULL;
-- 订单改变记录
alter table `orderform_product_record` modify column `money` decimal(15,0) DEFAULT '0';

-- 修改佣金记录表 lzh 2016/10/17 
alter table `brokerage_apply_modify_history` drop column `modify_type`;

-- userInfoBoth 增加userinfoboth身份证后6位 用于后台增加会员保存  xpk  删除日志操作表中操作类型 
alter table `user_info_both` add column `idcard` varchar(20) ;
alter table `user_oper_log` drop column oper_type;
alter table `user_oper_log` drop column role_id;

-- 订单操作日志 删除操作类型字段 lzh 2016/10/14
alter table `orderform_remark`drop column `oper_type`;
alter table `orderform_remark` add column `user_id` bigint(20) DEFAULT '0';


-- 会员统计表增加所有会员数 csy 2016/10/14
alter table `user_common_statistics` add column  `alluser_num` int(11) NOT NULL after `yesterday_seller`;

-- 贷款订单表增加实际金额 lzh 2016/10/13
alter table `orderform` add column `actual_money` decimal(10,1) NOT NULL DEFAULT '0' after `money`;
alter table `orderform` add column `brokerage_rate_num` decimal(17,5) DEFAULT '0'after `loan_insterest_rate`;

-- 2016/10/13 csy 将用户信息表--后台员工登录时间 和最后一次登录时间 换到用户表中
alter table `user` add column `logindate` datetime  DEFAULT  NULL;
alter table `user` add column `lastlogindate` datetime  DEFAULT NULL;
alter table `user_info_employee` drop column `logindate`;
alter table `user_info_employee` drop column `lastlogindate` ;

-- 2016/10/13 lzh 体现订单状态修改
alter table `flow_withdraw` drop column `verify_status`;

-- 2016/10/13 lzh 佣金订单增加订单号

alter table `brokerage_apply` add column `brokerage_no` varchar(20) NOT NULL DEFAULT '' after `orderform_id`;
alter table `brokerage_apply` add column `createTime` datetime NOT NULL DEFAULT '0000-00-00 00:00:00' after `brokerage_no`;
alter table `brokerage_apply` add column `user_id` bigint(20) NOT NULL DEFAULT '0' after `createTime`;
-- 2016/10/12 lzh 佣金订单表关联产品
alter table `brokerage_apply` add column `product_id` bigint(11) NOT NULL DEFAULT '0' after `orderform_id`;
alter table `brokerage_apply` add column `product_info_id` bigint(11) NOT NULL DEFAULT '0' after `product_id` ;

-- 2016.10.11 角色添加人数
alter table `role` add column `number` int DEFAULT '0';

-- 2016 10.11 活动添加文案、对象
alter table `activity` add column `activity_copy` varchar(100)  DEFAULT NULL ; 
alter table `activity` add column `activity_object` varchar(100)  DEFAULT NULL ;

-- 2016 10.9 部门添加部门等级
alter table `department` add column `level` smallint(3) NOT NULL DEFAULT '1';

-- 2016.10.8 添加二维码 邀请码
alter table user_info_both add column `qr_code` varchar(350)  DEFAULT NULL ; 
alter table user_info_both add column `recommend_code` varchar(350) DEFAULT NULL;
alter table `user_bankinfo` modify column `area` varchar(200) DEFAULT '';

-- 2016/9/29
alter table product_type add column `state` smallint(6) NOT NULL;

-- 2017/1/20
ALTER TABLE brd.weixin_user ADD sceneids VARCHAR(255) DEFAULT '[]';
-- 2017/1/20  微信扫码记录
CREATE TABLE brd.weixin_scan_record
(
    date DATE,
    scene_id INT NOT NULL,
    id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    scan_num INT DEFAULT 0,
    concern_num INT DEFAULT 0
);

