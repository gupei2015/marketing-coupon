/*
Source Server         : 来康郡养老平台数据库
Source Server Version : 50715
Source Database       : lk-p-marketing

Target Server Type    : MYSQL
Target Server Version : 50715
File Encoding         : 65001

Date: 2018-09-11 16:01:06
*/

SET FOREIGN_KEY_CHECKS=0;
-- ----------------------------
-- Table structure for `t_coupon`
-- ----------------------------
DROP TABLE IF EXISTS `t_coupon`;
CREATE TABLE `t_coupon` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '自动增加ID',
  `account_rule_id` bigint(20) DEFAULT NULL COMMENT '结算规则ID',
  `company_id` int(11) DEFAULT NULL COMMENT '公司,租户ID',
  `coupon_type` tinyint(4) DEFAULT NULL COMMENT '所属类型(0:积分;1:满减(包括现金抵扣和折扣,参考满减规则),2:赠送赠品)',
  `is_accumulated` bit(1) DEFAULT b'0' COMMENT '使用该优惠券产生的扣减是否计入满减条件的支付总量',
  `coupon_name` varchar(32) DEFAULT NULL COMMENT '优惠券名称',
  `coupon_desc` varchar(200) DEFAULT NULL COMMENT '优惠券活动描述',
  `icon` varchar(64) DEFAULT NULL COMMENT '优惠券图标的URL地址',
  `shop_id` varchar(64) DEFAULT NULL COMMENT '适用商户供应商ID',
  `sku_category` int(11) DEFAULT NULL COMMENT '对象范围,适用商品或服务分类ID',
  `user_category` int(11) DEFAULT NULL COMMENT '顾客资格,适用用户分类',
  `sku_id` int(11) DEFAULT NULL COMMENT '商品或服务SKU id',
  `accumulate_type` tinyint(4) DEFAULT '0' COMMENT '优惠券使用规则之间关系,0:排他;1:并存(累计);2:择优',
  `weight` tinyint(4) DEFAULT '0' COMMENT '权重值,数值小表示优先',
  `is_supportReturn` bit(1) DEFAULT b'1' COMMENT '退款时是否支持退回',
  `status` tinyint(4) DEFAULT '0' COMMENT '状态(0-已启用,1-未启用)',
  `is_delete` bit(1) DEFAULT b'0' COMMENT '是否生效',
  `version`  bigint(20) NULL DEFAULT NULL COMMENT '乐观锁标识' ,
  `creator_id` bigint(20) DEFAULT NULL COMMENT '创建人',
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `modifier_id` bigint(20) DEFAULT NULL COMMENT '修改人',
  `modified_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_coupon_name` (`coupon_name`),
  KEY `fk_account_rule` (`account_rule_id`),
  CONSTRAINT `fk_account_rule` FOREIGN KEY (`account_rule_id`) REFERENCES `t_coupon_account_rule` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='优惠券基础配置表';


-- ----------------------------
-- Table structure for `t_coupon_account_rule`
-- ----------------------------
DROP TABLE IF EXISTS `t_coupon_account_rule`;
CREATE TABLE `t_coupon_account_rule` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '自动增加ID',
  `rule_name` varchar(50) DEFAULT NULL COMMENT ' 结算规则名称',
  `rule_desc` varchar(2000) DEFAULT NULL COMMENT ' 规则描述',
  `threshold_type` tinyint(4) DEFAULT NULL COMMENT '满减条件类型 0:无条件;1:满金额;2:满数量;3:优惠价',
  `reward_threshold` decimal(11,2) DEFAULT '0.00' COMMENT '满减条件阈值, 0表示无条件阈值',
  `reward_type` tinyint(4) DEFAULT '0' COMMENT '扣减类型 0:积分;1:抵扣金额;2:折扣;3:其他赠品',
  `reward_amount` decimal(11,2) DEFAULT '0.00' COMMENT '扣减数,包括扣减积分数,金额,折扣率',
  `reward_product` varchar(64) DEFAULT '0' COMMENT '赠送商品或服务ID',
  `reward_activity_id` bigint(11) DEFAULT '0' COMMENT '赠送优惠券活动ID',
  `reward_desc` varchar(400) DEFAULT NULL COMMENT '赠品描述',
  `promotion_price` decimal(11,2) DEFAULT '0.00' COMMENT '优惠价',
  `exchange_ratio` decimal(11,2) DEFAULT '1.00' COMMENT '金额兑换积分比例,积分除以该比例等于金额',
  `is_delete` bit(1) DEFAULT b'0' COMMENT '是否生效',
  `version`  bigint(20) NULL DEFAULT NULL COMMENT '乐观锁标识',
  `creator_id` bigint(20) DEFAULT NULL COMMENT '创建人',
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `modifier_id` bigint(20) DEFAULT NULL COMMENT '修改人',
  `modified_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='优惠券结算规则配置表';

-- ----------------------------
-- Table structure for `t_coupon_consume`
-- ----------------------------
DROP TABLE IF EXISTS `t_coupon_consume`;
CREATE TABLE `t_coupon_consume` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '自动增加ID',
  `user_id` varchar(64) DEFAULT NULL COMMENT '用户买家ID',
  `user_code` varchar(200) DEFAULT NULL COMMENT '用户买家编号',
  `shop_id` varchar(64) DEFAULT NULL COMMENT '卖家商户供应商ID',
  `coupon_id` bigint(20) DEFAULT NULL COMMENT '基础优惠券ID,使用积分时设置',
  `order_id` varchar(64) DEFAULT NULL COMMENT '订单ID',
  `order_no` varchar(200) DEFAULT NULL COMMENT '订单编号',
  `order_item_id` varchar(64) DEFAULT NULL COMMENT '订单明细项ID',
  `original_amount` decimal(11,2) DEFAULT '0.00' COMMENT '原订单或商品项金额',
  `coupon_amount` decimal(11,2) DEFAULT '0.00' COMMENT '券额,包含积分数,金额,折扣率',
  `consume_quantity` bigint(20) DEFAULT 1 COMMENT '用券数量',
  `final_pay_amount` decimal(11,2) DEFAULT '0.00' COMMENT '优惠后订单或商品项实际支付金额',
  `status` int(2) DEFAULT '0' COMMENT '消费状态: 默认为0已支付使用劵完毕，退回优惠券为1',
  `version`  bigint(20) NULL DEFAULT NULL COMMENT '乐观锁标识' ,
  `is_delete` bit(1) DEFAULT b'0' COMMENT '是否生效',
  `creator_id` bigint(20) DEFAULT NULL COMMENT '创建人',
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `modifier_id` bigint(20) DEFAULT NULL COMMENT '修改人',
  `modified_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`),
  KEY `fk_coupon1` (`coupon_id`),
  CONSTRAINT `fk_coupon1` FOREIGN KEY (`coupon_id`) REFERENCES `t_coupon` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='优惠券消费记录主表';

-- ----------------------------
-- Table structure for `t_coupon_consume_detail`
-- ----------------------------
DROP TABLE IF EXISTS `t_coupon_consume_detail`;
CREATE TABLE `t_coupon_consume_detail` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '自动增加ID',
  `coupon_consume_id` bigint(20) DEFAULT NULL COMMENT '消费优惠券ID',
  `coupon_receive_id` bigint(20) DEFAULT NULL COMMENT '发放优惠券ID',
  `coupon_receive_detail_ids` text DEFAULT NULL COMMENT '发放优惠券明细ID集合',
  `activity_id` bigint(20) DEFAULT NULL COMMENT '促销活动ID',
  `consume_quantity` bigint(20) DEFAULT 1 COMMENT '本次用券数量',
  `version`  bigint(20) NULL DEFAULT NULL COMMENT '乐观锁标识',
  `is_delete` bit(1) DEFAULT b'0' COMMENT '是否生效',
  `creator_id` bigint(20) DEFAULT NULL COMMENT '创建人',
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `modifier_id` bigint(20) DEFAULT NULL COMMENT '修改人',
  `modified_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`),
  KEY `fk_coupon_receive` (`coupon_receive_id`),
  CONSTRAINT `fk_coupon_receive` FOREIGN KEY (`coupon_receive_id`) REFERENCES `t_coupon_receive` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='优惠券消费记录明细表';

-- ----------------------------
-- Table structure for `t_coupon_receive`
-- ----------------------------
DROP TABLE IF EXISTS `t_coupon_receive`;
CREATE TABLE `t_coupon_receive` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '自动增加ID',
  `user_id` varchar(64) DEFAULT NULL COMMENT '用户买家ID',
  `user_code` varchar(200) DEFAULT NULL COMMENT '用户买家编号',
  `coupon_id` bigint(20) DEFAULT NULL COMMENT '优惠券编号ID',
  `activity_id` bigint(20) DEFAULT NULL COMMENT '促销活动ID',
  `coupon_amount` decimal(11,2) DEFAULT '0.00' COMMENT '券额,包含积分数,金额,折扣率',
  `receive_quantity` bigint(20) DEFAULT '0' COMMENT '领券数量',
  `remain_quantity` bigint(20) DEFAULT '0' COMMENT '使用后剩余数量',
  `start_date` date DEFAULT NULL COMMENT '优惠券生效日期',
  `end_date` date DEFAULT NULL COMMENT '优惠券截至日期',
  `remark` varchar(200) DEFAULT NULL COMMENT '优惠券领取场景描述',
  `order_id` varchar(64) DEFAULT NULL COMMENT '领券关联订单ID',
  `order_no` varchar(200) DEFAULT NULL COMMENT '订单编号',
  `order_item_id` varchar(64) DEFAULT NULL COMMENT '订单明细项ID',
  `status` int(11) DEFAULT '0' COMMENT '状态，0为已领取未使用，1为已使用，2为部分使用, 3为已过期',
  `version`  bigint(20) NULL DEFAULT NULL COMMENT '乐观锁标识' ,
  `is_delete` bit(1) DEFAULT b'0' COMMENT '是否生效',
  `creator_id` bigint(20) DEFAULT NULL COMMENT '创建人',
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '领取时间',
  `modifier_id` bigint(20) DEFAULT NULL COMMENT '修改人',
  `modified_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`),
  KEY `fk_coupon2` (`coupon_id`),
  CONSTRAINT `fk_coupon2` FOREIGN KEY (`coupon_id`) REFERENCES `t_coupon` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='优惠券领取记录表';

-- ----------------------------
-- Table structure for `t_coupon_receive_detail`
-- ----------------------------
DROP TABLE IF EXISTS `t_coupon_receive_detail`;
CREATE TABLE `t_coupon_receive_detail` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '自动增加ID',
  `coupon_receive_id` bigint(20) NOT NULL COMMENT '优惠券领取ID',
  `coupon_no` varchar(200) DEFAULT NULL COMMENT '优惠券编码,发券时按规则生成',
  `coupon_amount` decimal(11,2) DEFAULT '0.00' COMMENT '券额,包含积分数,金额,折扣率',
  `coupon_qr_code_url` varchar(200) DEFAULT NULL COMMENT '优惠券二维码地址URL',
  `status` int(11) DEFAULT '0' COMMENT '状态，0为已领取未使用，1为已使用，2为使用中，3为已过期',
  `version`  bigint(20) NULL DEFAULT NULL COMMENT '乐观锁标识' ,
  `is_delete` bit(1) DEFAULT b'0' COMMENT '是否生效',
  `creator_id` bigint(20) DEFAULT NULL COMMENT '创建人',
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '领取时间',
  `modifier_id` bigint(20) DEFAULT NULL COMMENT '修改人',
  `modified_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`),
  KEY `fk_coupon_receive2` (`coupon_receive_id`),
  CONSTRAINT `fk_coupon_receive2` FOREIGN KEY (`coupon_receive_id`) REFERENCES `t_coupon_receive` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='优惠券领取明细记录表';

-- ----------------------------
-- Table structure for `t_coupon_summary`
-- ----------------------------
DROP TABLE IF EXISTS `t_coupon_summary`;
CREATE TABLE `t_coupon_summary` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '自动增加ID',
  `user_id` varchar(64) DEFAULT NULL COMMENT '用户买家ID',
  `user_code` varchar(200) DEFAULT NULL COMMENT '用户买家编号',
  `coupon_id` bigint(20) DEFAULT NULL COMMENT '优惠券编号ID',
  `coupon_amount` decimal(11,2) DEFAULT '0.00' COMMENT '券额,金额,折扣率',
  `received_quantity` bigint(20) DEFAULT '0' COMMENT '历史领取总数',
  `consumed_quantity` bigint(20) DEFAULT '0' COMMENT '历史使用总数',
  `reward_amount` decimal(11,2) DEFAULT '0.00' COMMENT '历史回馈扣减总数',
  `coupon_quantity` bigint(20) DEFAULT '0' COMMENT '可用优惠券张数或积分余额',
  `is_delete` bit(1) DEFAULT b'0' COMMENT '是否生效',
  `version`  bigint(20) NULL DEFAULT NULL COMMENT '乐观锁标识' ,
  `creator_id` bigint(20) DEFAULT NULL COMMENT '创建人',
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `modifier_id` bigint(20) DEFAULT NULL COMMENT '修改人',
  `modified_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`),
  KEY `fk_coupon3` (`coupon_id`),
  CONSTRAINT `fk_coupon3` FOREIGN KEY (`coupon_id`) REFERENCES `t_coupon` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='优惠券积分汇总表';


-- ----------------------------
-- Table structure for `t_promotion_activity`
-- ----------------------------
DROP TABLE IF EXISTS `t_promotion_activity`;
CREATE TABLE `t_promotion_activity` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '自动增加ID',
  `coupon_id` bigint(20) DEFAULT NULL COMMENT '基础优惠券ID',
  `activity_name` varchar(50) DEFAULT NULL COMMENT '促销活动名称',
  `activity_desc` varchar(200) DEFAULT NULL COMMENT '促销活动描述',
  `activity_start` date DEFAULT NULL COMMENT '优惠券活动开始日期',
  `activity_end` date DEFAULT NULL COMMENT '优惠券活动结束日期',
  `effect_date` date DEFAULT NULL COMMENT '优惠券生效日期',
  `effect_days` int(11) DEFAULT NULL COMMENT '优惠券有效天数,用于发券开始计算有效截至期',
  `start_time` time DEFAULT NULL COMMENT '优惠券使用开始时间',
  `end_time` time DEFAULT NULL COMMENT '优惠券使用结束时间',
  `is_quantityLimit` bit(1) DEFAULT b'1' COMMENT '数量限制，无限制则不需要发券，自动用券',
  `issue_quantity` bigint(20) DEFAULT '0' COMMENT '总发放数量',
  `received_quantity` bigint(20) DEFAULT '0' COMMENT '已发放数量',
  `limit_quantity` bigint(20) DEFAULT NULL COMMENT '每人最多领取数量',
  `auto_issue_quantity` int(11) DEFAULT '0' COMMENT '自动发放数量',
  `auto_issue_time` datetime DEFAULT NULL COMMENT '自动发放时间',
  `status` tinyint(4) DEFAULT NULL COMMENT '状态，0表示未开始，1表示进行中，2表示结束',
  `is_delete` bit(1) DEFAULT b'0' COMMENT '是否生效',
  `version`  bigint(20) NULL DEFAULT NULL COMMENT '乐观锁标识' ,
  `creator_id` bigint(20) DEFAULT NULL COMMENT '创建人',
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `modifier_id` bigint(20) DEFAULT NULL COMMENT '修改人',
  `modified_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`),
  KEY `fk_coupon4` (`coupon_id`),
  CONSTRAINT `fk _coupon3` FOREIGN KEY (`coupon_id`) REFERENCES `t_coupon` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='促销活动基础表';


-- ----------------------------
-- Table structure for `t_promotion_rule`
-- ----------------------------
DROP TABLE IF EXISTS `t_promotion_rule`;
CREATE TABLE `t_promotion_rule` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '自动增加ID',
  `activity_id` bigint(20) DEFAULT NULL COMMENT '促销活动ID',
  `rule_name` varchar(40) DEFAULT NULL COMMENT '规则名称',
  `rule_type` tinyint(4) DEFAULT '0' COMMENT '规则类型 0:发放规则;1:使用规则',
  `user_condition` varchar(1000) DEFAULT NULL COMMENT '用户适用条件 json字符串',
  `sku_condition` varchar(1000) DEFAULT NULL COMMENT '商品或服务适用条件 json字符串',
  `order_condition` varchar(1000) DEFAULT NULL COMMENT '订单适用条件 json字符串',
  `time_condition` varchar(1000) DEFAULT NULL COMMENT '适用时间范围条件 条件表达式',
  `extras_condition` varchar(1000) DEFAULT NULL COMMENT '其他使用范围条件 json字符串',
  `is_delete` bit(1) DEFAULT b'0' COMMENT '是否生效',
  `version`  bigint(20) NULL DEFAULT NULL COMMENT '乐观锁标识' ,
  `creator_id` bigint(20) DEFAULT NULL COMMENT '创建人',
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `modifier_id` bigint(20) DEFAULT NULL COMMENT '修改人',
  `modified_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`),
  KEY `fk_activity` (`activity_id`),
  CONSTRAINT `fk_activity` FOREIGN KEY (`activity_id`) REFERENCES `t_promotion_activity` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='促销活动规则配置表';