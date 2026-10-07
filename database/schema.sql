-- Schema only. No account, employee, customer or transaction data.
SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

CREATE TABLE `department`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(20) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '部门名称',
  `info` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '描述',
  `state` char(2) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '状态',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = COMPACT;

CREATE TABLE `detail_sale_records`  (
  `sell_cn` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '销售订单号',
  `goods_id` bigint NOT NULL COMMENT '商品编号',
  `goods_num` bigint NOT NULL COMMENT '商品数量',
  `goods_price` double(10, 2) NOT NULL COMMENT '销售单价',
  `goods_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '商品名'
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = COMPACT;

CREATE TABLE `employee`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `phone` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '用户名',
  `email` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '邮箱',
  `address` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '住址',
  `sex` char(1) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '性别',
  `password` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '密码',
  `nick_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '昵称',
  `head_img` longtext CHARACTER SET utf8 COLLATE utf8_bin NULL COMMENT '头像',
  `state` varchar(1) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '状态 0：在职 1：离职',
  `isAdmin` bit(1) NULL DEFAULT NULL COMMENT '是否是超管 1:是 0不是',
  `info` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '描述',
  `createby` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `age` int NULL DEFAULT NULL COMMENT '年龄',
  `deptId` bigint NULL DEFAULT NULL COMMENT '部门主键',
  `id_card` varchar(18) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '身份证号',
  `leave_time` datetime NULL DEFAULT NULL COMMENT '离职时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 17 CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = COMPACT;

CREATE TABLE `exchange_point_products_records`  (
  `cn` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '订单号',
  `goods_id` bigint NULL DEFAULT NULL COMMENT '商品编号',
  `member_id` bigint NULL DEFAULT NULL COMMENT '会员编号',
  `integral` bigint NULL DEFAULT NULL COMMENT '积分',
  `update_time` datetime NULL DEFAULT NULL COMMENT '最近操作时间',
  `updateby` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '操作者',
  `update_id` bigint NULL DEFAULT NULL COMMENT '操作者编号',
  `state` char(2) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '状态',
  PRIMARY KEY (`cn`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = COMPACT;

CREATE TABLE `goods`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '商品名',
  `createby` varchar(20) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `category_id` bigint NULL DEFAULT NULL COMMENT '商品分类id',
  `sell_price` double(10, 2) NULL DEFAULT NULL COMMENT '销售价格',
  `purchash_price` double(10, 2) NULL DEFAULT NULL COMMENT '进货价格',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更改时间',
  `updateby` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '更改者',
  `category_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_czech_ci NULL DEFAULT NULL COMMENT '分类名',
  `cover_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '商品封面',
  `state` char(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '状态',
  `residue_num` bigint NULL DEFAULT NULL COMMENT '剩余数量',
  `info` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '备注',
  `sales_volume` bigint NULL DEFAULT NULL COMMENT '销量',
  `inventory` bigint NULL DEFAULT NULL COMMENT '需库存量',
  `shelves` bigint NULL DEFAULT NULL COMMENT '货架上需摆放的数量',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 12 CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = COMPACT;

CREATE TABLE `goods_category`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '分类名',
  `info` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '备注',
  `state` char(2) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '状态',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 14 CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = COMPACT;

CREATE TABLE `point_products`  (
  `goods_id` bigint NULL DEFAULT NULL,
  `goods_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `integral` bigint NULL DEFAULT NULL,
  `updateby` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `update_time` datetime NULL DEFAULT NULL,
  `update_id` bigint NULL DEFAULT NULL,
  `cover_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `state` char(2) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = COMPACT;

CREATE TABLE `store`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '仓库名称',
  `address` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '仓库地址',
  `state` char(2) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '状态',
  `info` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '描述',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = COMPACT;

CREATE TABLE `supplier`  (
  `cn` bigint NOT NULL AUTO_INCREMENT COMMENT '编号',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '名称',
  `address` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '地址',
  `tel` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '联系电话',
  `info` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '备注',
  `state` char(2) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  PRIMARY KEY (`cn`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = COMPACT;

CREATE TABLE `t_detail_store_goods`  (
  `cn` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `goods_id` bigint NOT NULL,
  `goods_num` int NULL DEFAULT NULL,
  `goods_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `goods_price` double NULL DEFAULT NULL COMMENT '0：入库 1：出库',
  `type` char(2) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `createid` bigint NOT NULL,
  `create_time` datetime NOT NULL,
  `state` char(2) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '0：正常 1：过期 2：下架',
  `info` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `expiry_time` datetime NULL DEFAULT NULL COMMENT '过期时间',
  `createby` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `birth_time` datetime NULL DEFAULT NULL COMMENT '生产时间',
  `state1` char(2) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '0:正常 1：删除 2：待处理',
  `store_id` bigint NULL DEFAULT NULL,
  `supplier_id` bigint NULL DEFAULT NULL COMMENT '供货商编号',
  `supplier_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '供货商名称',
  `untreated_num` bigint NULL DEFAULT NULL COMMENT '待处理数量',
  PRIMARY KEY (`cn`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = COMPACT;

CREATE TABLE `t_emp_role`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `eid` bigint NOT NULL COMMENT '用户id',
  `rid` bigint NOT NULL COMMENT '角色id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 23 CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = COMPACT;

CREATE TABLE `t_goods_store`  (
  `goods_id` bigint NOT NULL COMMENT '商品编号',
  `store_id` bigint NOT NULL COMMENT '仓库编号',
  `in_num` bigint NOT NULL COMMENT '入库数数量',
  `residue_num` bigint NOT NULL COMMENT '剩余数量',
  `store_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '仓库名'
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = COMPACT;

CREATE TABLE `t_member`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '姓名',
  `phone` varchar(11) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '手机号',
  `password` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '密码',
  `email` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '邮箱',
  `state` char(2) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '状态',
  `info` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '描述',
  `integral` bigint NULL DEFAULT NULL COMMENT '会员积分',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = COMPACT;

CREATE TABLE `t_menu`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `label` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '名称',
  `purl` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '地址',
  `type` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '类型 0:目录 1 菜单 2 按钮',
  `parent_id` bigint NULL DEFAULT NULL COMMENT '父id',
  `parent_label` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '父名称',
  `info` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '描述',
  `state` varchar(2) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '状态',
  `flag` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '权限的唯一标识',
  `icon` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '图标',
  `component` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '组件路径',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 63 CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = COMPACT;

CREATE TABLE `t_role`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '角色名',
  `info` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '描述',
  `state` char(2) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '状态 0： 正常 -1：停用',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = COMPACT;

CREATE TABLE `t_role_menu`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `rid` bigint NOT NULL COMMENT '角色id',
  `mid` bigint NOT NULL COMMENT '菜单id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 134 CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = COMPACT;

CREATE TABLE `t_sale_records`  (
  `cn` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `eid` bigint NOT NULL,
  `sellway` char(2) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `sell_time` datetime NOT NULL,
  `state` char(2) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '0:正常 1：删除',
  `info` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '备注',
  `sellby` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `sell_total` bigint NOT NULL COMMENT '销售总数量',
  `sell_totalmoney` double NOT NULL COMMENT '销售总金额',
  `type` char(1) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '0:非会员消费 1：会员消费',
  `member_phone` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '顾客会员号码',
  PRIMARY KEY (`cn`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = COMPACT;

SET FOREIGN_KEY_CHECKS = 1;
