/*
 Navicat Premium Data Transfer

 Source Server         : root
 Source Server Type    : MySQL
 Source Server Version : 50726
 Source Host           : localhost:3306
 Source Schema         : newjobhunt

 Target Server Type    : MySQL
 Target Server Version : 50726
 File Encoding         : 65001

 Date: 10/03/2023 12:27:37
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for jh_job
-- ----------------------------
DROP TABLE IF EXISTS `jh_job`;
CREATE TABLE `jh_job`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `cname` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `uid` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `detail` varchar(2550) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `view` int(11) NULL DEFAULT NULL,
  `thumb` int(11) NULL DEFAULT NULL,
  `groupnum` int(11) NULL DEFAULT NULL,
  `guarantee` bit(1) NULL DEFAULT NULL,
  `price` int(11) NULL DEFAULT NULL,
  `contactor1` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `contactor2` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `date` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `showing` bit(1) NULL DEFAULT b'0',
  `topping` bit(1) NULL DEFAULT b'0',
  `hot` bit(1) NULL DEFAULT b'0',
  `keeping` bit(1) NULL DEFAULT b'1',
  `picture` varchar(2550) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of jh_job
-- ----------------------------
INSERT INTO `jh_job` VALUES (1, '微信单', '10000', '雪铁龙落地13W赶紧抢', '快去湖北抢，政企补贴，跨快开', 1, 1, 0, b'0', 0, '123456', '', '2023-03-10 10:35:51', b'1', b'0', b'0', b'1', 'http://localhost:8080/uploadimages/2023/03/10/2b32e39a-5a7f-4100-9579-11db0afbda2e.png@@');
INSERT INTO `jh_job` VALUES (2, '需求发布', '10000', '招聘运营人员', '1.年龄27周岁（含）以下，博士生31周岁（含）以下；\n2.掌握扎实的计算机基础知识，热爱计算机科学与技术，熟练掌握至少一种主流编程语言；\n3.有优秀的逻辑分析能力，能够对业务逻辑进行合理的抽象和拆分；\n4.对数据开发、分析、挖掘，人工智能、机器学习、自然语言处理、数据仓库等领域有浓厚的兴趣和一定研究；\n5.具备高度责任心和团队合作意识，执行力强，具备较强的学习及协调沟通能力，善于提出创新性思路和方法。', 2, 1, 3, b'1', 6000, '11111', '2222', '2023-03-10 10:38:09', b'1', b'1', b'0', b'1', 'http://localhost:8080/uploadimages/2023/03/10/beb9076c-3d40-4734-bcf8-415005eb146e.png@@');
INSERT INTO `jh_job` VALUES (3, '骗子曝光', '10000', '曝光一个骗子', '张三骗了李四钱，阿真爱上了阿强', 0, 0, 0, b'0', 0, '55555', '', '2023-03-10 10:41:43', b'1', b'0', b'0', b'1', '');

-- ----------------------------
-- Table structure for jh_notice
-- ----------------------------
DROP TABLE IF EXISTS `jh_notice`;
CREATE TABLE `jh_notice`  (
  `content` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `color` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `id` int(11) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = MyISAM CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of jh_notice
-- ----------------------------
INSERT INTO `jh_notice` VALUES ('测试公告，多写bug', '#cf0bec', 0);

-- ----------------------------
-- Table structure for jh_ppt
-- ----------------------------
DROP TABLE IF EXISTS `jh_ppt`;
CREATE TABLE `jh_ppt`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `description` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `image` varchar(510) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `router` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `showing` bit(1) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 5 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of jh_ppt
-- ----------------------------
INSERT INTO `jh_ppt` VALUES (1, '测试', 'https://img.static.996so.icu//2022/11/15/bd363311-7f12-4d50-8334-612f62edc620.png', '1', b'1');

-- ----------------------------
-- Table structure for jh_user
-- ----------------------------
DROP TABLE IF EXISTS `jh_user`;
CREATE TABLE `jh_user`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `phone` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `balance` int(11) NULL DEFAULT NULL,
  `area` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `avatar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `lastsign` bigint(20) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10002 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of jh_user
-- ----------------------------
INSERT INTO `jh_user` VALUES (10000, '文丑', '7ABCB67DE22396A510652E5444C34374', '1551015899@qq.com', '13421535537', 48, NULL, 'http://localhost:8080/uploadimages/2023/03/10/00ef3ec0-7975-4bd0-9358-60da65b8085e.jpeg', 1678377600000);
INSERT INTO `jh_user` VALUES (10001, 'asd', '7ABCB67DE22396A510652E5444C34374', '5435154@qq.com', '17180125899', 50, NULL, NULL, 5847660182);

SET FOREIGN_KEY_CHECKS = 1;
