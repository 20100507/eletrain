/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50536
Source Host           : localhost:3306
Source Database       : train

Target Server Type    : MYSQL
Target Server Version : 50536
File Encoding         : 65001

Date: 2017-09-19 00:20:00
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for admin
-- ----------------------------
DROP TABLE IF EXISTS `admin`;
CREATE TABLE `admin` (
  `admin_id` int(11) NOT NULL AUTO_INCREMENT,
  `loginname` varchar(40) DEFAULT NULL,
  `username` varchar(40) DEFAULT NULL,
  `password` varchar(40) DEFAULT NULL,
  `email` varchar(40) DEFAULT NULL,
  `identify` int(11) DEFAULT NULL,
  PRIMARY KEY (`admin_id`)
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of admin
-- ----------------------------
INSERT INTO `admin` VALUES ('1', '000000', '超管', '123456', '超级管理员', '1');
INSERT INTO `admin` VALUES ('3', '2014023009', '寇太明', '123456', '部门负责人', '3');
INSERT INTO `admin` VALUES ('4', '2014023010', '郝昌文', '123456', '培训管理部', '4');
INSERT INTO `admin` VALUES ('5', '2014023011', '李洪波', '123456', '主管主任', '5');
INSERT INTO `admin` VALUES ('6', '2014023000', '白鹏飞', '123456', '管理员', '1');

-- ----------------------------
-- Table structure for application
-- ----------------------------
DROP TABLE IF EXISTS `application`;
CREATE TABLE `application` (
  `application_id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(255) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  `apply_date` datetime DEFAULT NULL,
  `plan_id` int(11) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `pd_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`application_id`)
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of application
-- ----------------------------

-- ----------------------------
-- Table structure for approveinfo
-- ----------------------------
DROP TABLE IF EXISTS `approveinfo`;
CREATE TABLE `approveinfo` (
  `approveInfo_id` int(11) NOT NULL AUTO_INCREMENT,
  `admin_id` int(11) DEFAULT NULL,
  `approveDate` datetime DEFAULT NULL,
  `approval` tinyint(1) DEFAULT NULL,
  `comment` varchar(255) DEFAULT NULL,
  `application_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`approveInfo_id`)
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of approveinfo
-- ----------------------------

-- ----------------------------
-- Table structure for auth_function
-- ----------------------------
DROP TABLE IF EXISTS `auth_function`;
CREATE TABLE `auth_function` (
  `id` varchar(32) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `code` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `page` varchar(255) DEFAULT NULL,
  `generatemenu` varchar(255) DEFAULT NULL,
  `zindex` int(11) DEFAULT NULL,
  `pid` varchar(32) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of auth_function
-- ----------------------------
INSERT INTO `auth_function` VALUES ('0', '人员信息管理', '1', '人员信息管理', '', '人员信息管理', '1', '-1');
INSERT INTO `auth_function` VALUES ('2', '学员管理', '1', '学员管理', '/student/allStu', '学员管理', '2', '0');
INSERT INTO `auth_function` VALUES ('3', '培训师管理', '1', '培训师管理', '/teacher/list', '培训师管理', '3', '0');
INSERT INTO `auth_function` VALUES ('4', '管理员管理', '1', '管理员管理', '/admin/getAdminListByPage', '管理员管理', '4', '0');
INSERT INTO `auth_function` VALUES ('5', '课程管理', '1', '课程管理', '', '课程管理', '2', '-2');
INSERT INTO `auth_function` VALUES ('7', '资源管理', '1', '资源管理', '', '资源管理', '4', '-4');
INSERT INTO `auth_function` VALUES ('8', '培训方案管理', '1', '培训方案管理', '/plan/getAllPlan', '培训方案管理', '2', '7');
INSERT INTO `auth_function` VALUES ('9', '设备管理', '1', '设备管理', '/devices/getAllDevices', '设备管理', '3', '7');
INSERT INTO `auth_function` VALUES ('10', '教材管理', '1', '教材管理', '/books/getAllBooks', '教材管理', '4', '7');
INSERT INTO `auth_function` VALUES ('11', '查看公告', '6', '查看公告', '', '查看公告', '3', '-5');
INSERT INTO `auth_function` VALUES ('12', '查看公告', '6', '查看公告', '/notice/getNoticeListByPage', '查看公告', '1', '11');
INSERT INTO `auth_function` VALUES ('13', '公告信息管理', '1', '公告信息管理', '/notice/getNoticeListByPage', '公告信息管理', '1', '36');
INSERT INTO `auth_function` VALUES ('14', '课程信息管理', '1', '课程信息管理', '/course/getCourseListByPage', '课程信息管理', '1', '5');
INSERT INTO `auth_function` VALUES ('15', '审批流转', '1', '审批流转', '', '审批流转', '3', '-6');
INSERT INTO `auth_function` VALUES ('16', '课表管理', '1', '课表管理', '/syllabus/list', '课表管理', '2', '5');
INSERT INTO `auth_function` VALUES ('17', '班级管理', '1', '班级管理', '', '班级管理', '5', '-7');
INSERT INTO `auth_function` VALUES ('18', '班级管理', '1', '班级管理', '/classes/list', '班级管理', '1', '17');
INSERT INTO `auth_function` VALUES ('19', '班级管理', '6', '班级管理', '', '班级管理', '1', '-8');
INSERT INTO `auth_function` VALUES ('20', '班级列表', '6', '班级管理', '/classes/getClassesByClassesTeacherId', '班级管理', '1', '19');
INSERT INTO `auth_function` VALUES ('23', '成绩管理', '1', '成绩管理', '', '成绩管理', '6', '-10');
INSERT INTO `auth_function` VALUES ('24', '成绩信息管理', '1', '成绩信息管理', '/score/getScoreListByPage', '成绩信息管理', '1', '23');
INSERT INTO `auth_function` VALUES ('25', '成绩管理', '6', '成绩管理', '', '成绩管理', '4', '-11');
INSERT INTO `auth_function` VALUES ('26', '成绩管理', '6', '成绩管理', '/score/getScoreListByPage', '成绩管理', '1', '25');
INSERT INTO `auth_function` VALUES ('27', '审批列表', '1', '审批列表', '/process/list', '审批列表', '1', '15');
INSERT INTO `auth_function` VALUES ('28', '待我审批', '3', '待我审批', '', '待我审批', '1', '-12');
INSERT INTO `auth_function` VALUES ('29', '审批列表', '3', '审批列表', '/process/flowProcessList', '审批列表', '1', '28');
INSERT INTO `auth_function` VALUES ('30', '待我审批', '4', '待我审批', '', '待我审批', '1', '-13');
INSERT INTO `auth_function` VALUES ('31', '审批列表', '4', '审批列表', '/process/flowProcessList', '审批列表', '1', '30');
INSERT INTO `auth_function` VALUES ('32', '待我审批', '5', '待我审批', '', '待我审批', '1', '-14');
INSERT INTO `auth_function` VALUES ('33', '审批列表', '5', '审批列表', '/process/flowProcessList', '审批列表', '1', '32');
INSERT INTO `auth_function` VALUES ('34', '登录日志', '1', '登录日志', ' ', '登录日志', '7', '-15');
INSERT INTO `auth_function` VALUES ('35', '日志列表', '1', '日志列表', '/log/list', '日志列表', '1', '34');
INSERT INTO `auth_function` VALUES ('36', '公告管理', '1', '公告管理', '', '公告管理', '8', '-16');
INSERT INTO `auth_function` VALUES ('37', '班级列表', '2', '班级列表', '', '班级列表', '1', '-17');
INSERT INTO `auth_function` VALUES ('38', '班级列表', '2', '班级列表', '/classes/getClassesByTeacherId\r\n', '班级列表', '1', '37');
INSERT INTO `auth_function` VALUES ('39', '课表列表', '2', '课表列表', '', '课表列表', '2', '-18');
INSERT INTO `auth_function` VALUES ('40', '课表列表', '2', '课表列表', '/syllabus/getSyllabusByTeacher', '课表列表', '1', '39');
INSERT INTO `auth_function` VALUES ('41', '公告', '2', '公告列表', '', '公告列表', '3', '40');
INSERT INTO `auth_function` VALUES ('42', '公告列表', '2', '公告列表', '/notice/getNoticeListByPage', '公告列表', '1', '41');
INSERT INTO `auth_function` VALUES ('43', '公告', '3', '公告', '', '公告', '1', '-19');
INSERT INTO `auth_function` VALUES ('44', '公告列表', '3', '公告列表', '/notice/getNoticeListByPage', '公告列表', '1', '43');
INSERT INTO `auth_function` VALUES ('45', '公告', '4', '公告', '', '公告', '1', '-20');
INSERT INTO `auth_function` VALUES ('46', '公告列表', '4', '公告列表', '/notice/getNoticeListByPage', '公告列表', '1', '45');
INSERT INTO `auth_function` VALUES ('47', '公告', '5', '公告', '', '公告', '1', '-21');
INSERT INTO `auth_function` VALUES ('48', '公告列表', '5', '公告列表', '/notice/getNoticeListByPage', '公告列表', '1', '47');
INSERT INTO `auth_function` VALUES ('49', '成绩', '2', '成绩', '', '成绩', '4', '-22');
INSERT INTO `auth_function` VALUES ('50', '成绩列表', '2', '成绩列表', '/score/getScoreListByPage', '成绩列表', '1', '49');
INSERT INTO `auth_function` VALUES ('51', '班级课表', '6', '班级课表', '/syllabus/getClassesSyllabusByTeacher', '班级课表', '1', '52');
INSERT INTO `auth_function` VALUES ('52', '课表管理', '6', '课表管理', '', '课表管理', '6', '-23');
INSERT INTO `auth_function` VALUES ('53', '个人课表', '6', '个人课表', '/syllabus/getSyllabusByTeacher\r\n', '个人课表', '2', '52');

-- ----------------------------
-- Table structure for book
-- ----------------------------
DROP TABLE IF EXISTS `book`;
CREATE TABLE `book` (
  `book_id` int(11) NOT NULL AUTO_INCREMENT,
  `bname` varchar(255) DEFAULT NULL,
  `author` varchar(255) DEFAULT NULL,
  `press` varchar(255) DEFAULT NULL,
  `count` int(11) DEFAULT NULL,
  PRIMARY KEY (`book_id`)
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of book
-- ----------------------------

-- ----------------------------
-- Table structure for classes
-- ----------------------------
DROP TABLE IF EXISTS `classes`;
CREATE TABLE `classes` (
  `classes_id` int(11) NOT NULL AUTO_INCREMENT,
  `cname` varchar(255) DEFAULT NULL,
  `capacity` int(11) DEFAULT NULL,
  `createtime` varchar(255) DEFAULT NULL,
  `starttime` varchar(255) DEFAULT NULL,
  `classes_no` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`classes_id`)
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of classes
-- ----------------------------

-- ----------------------------
-- Table structure for classes_plan
-- ----------------------------
DROP TABLE IF EXISTS `classes_plan`;
CREATE TABLE `classes_plan` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `classes_id` int(11) DEFAULT NULL,
  `plan_id` int(11) DEFAULT NULL,
  `cname` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of classes_plan
-- ----------------------------

-- ----------------------------
-- Table structure for classes_teacher
-- ----------------------------
DROP TABLE IF EXISTS `classes_teacher`;
CREATE TABLE `classes_teacher` (
  `classes_id` int(11) NOT NULL,
  `teacher_id` int(11) NOT NULL,
  PRIMARY KEY (`classes_id`,`teacher_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of classes_teacher
-- ----------------------------

-- ----------------------------
-- Table structure for course
-- ----------------------------
DROP TABLE IF EXISTS `course`;
CREATE TABLE `course` (
  `course_id` int(11) NOT NULL AUTO_INCREMENT,
  `cname` varchar(255) DEFAULT NULL,
  `capacity` int(11) DEFAULT NULL,
  `place` varchar(255) DEFAULT NULL,
  `info` varchar(255) DEFAULT NULL,
  `teacher_id` int(11) DEFAULT NULL,
  `period` int(11) DEFAULT NULL,
  PRIMARY KEY (`course_id`)
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of course
-- ----------------------------

-- ----------------------------
-- Table structure for device
-- ----------------------------
DROP TABLE IF EXISTS `device`;
CREATE TABLE `device` (
  `device_id` int(11) NOT NULL AUTO_INCREMENT,
  `dname` varchar(255) DEFAULT NULL,
  `dcount` int(11) DEFAULT NULL COMMENT '?豸????',
  `info` varchar(255) DEFAULT NULL COMMENT '??ע',
  `tool` varchar(255) DEFAULT NULL,
  `tcount` int(11) DEFAULT NULL COMMENT '?????豸????',
  PRIMARY KEY (`device_id`)
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of device
-- ----------------------------

-- ----------------------------
-- Table structure for log
-- ----------------------------
DROP TABLE IF EXISTS `log`;
CREATE TABLE `log` (
  `log_id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(255) DEFAULT NULL,
  `ip` varchar(255) DEFAULT NULL,
  `logintime` datetime DEFAULT NULL,
  PRIMARY KEY (`log_id`)
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of log
-- ----------------------------
INSERT INTO `log` VALUES ('139', '超管', '172.21.105.182', '2017-09-15 23:12:18');
INSERT INTO `log` VALUES ('150', '寇太明', '172.21.105.182', '2017-09-16 13:30:22');
INSERT INTO `log` VALUES ('151', '超管', '172.21.105.182', '2017-09-16 13:31:32');
INSERT INTO `log` VALUES ('152', '郝昌文', '172.21.105.182', '2017-09-16 13:32:38');
INSERT INTO `log` VALUES ('153', '超管', '172.21.105.182', '2017-09-16 13:32:52');
INSERT INTO `log` VALUES ('154', '超管', '172.21.105.182', '2017-09-16 14:18:33');
INSERT INTO `log` VALUES ('155', '超管', '172.21.105.182', '2017-09-16 14:19:27');
INSERT INTO `log` VALUES ('156', '超管', '172.21.105.182', '2017-09-16 14:22:34');
INSERT INTO `log` VALUES ('157', '超管', '172.21.105.182', '2017-09-16 14:23:14');
INSERT INTO `log` VALUES ('158', '超管', '172.21.105.182', '2017-09-16 15:05:52');
INSERT INTO `log` VALUES ('159', '寇太明', '172.21.105.182', '2017-09-16 15:10:43');
INSERT INTO `log` VALUES ('160', '超管', '172.21.105.182', '2017-09-16 15:17:20');
INSERT INTO `log` VALUES ('161', '超管', '172.21.105.182', '2017-09-16 15:35:00');
INSERT INTO `log` VALUES ('162', '边琪', '172.21.105.182', '2017-09-16 15:36:10');
INSERT INTO `log` VALUES ('163', '寇太明', '172.21.105.182', '2017-09-16 15:39:34');
INSERT INTO `log` VALUES ('164', '超管', '172.21.105.182', '2017-09-16 15:40:53');
INSERT INTO `log` VALUES ('165', '超管', '172.21.105.182', '2017-09-16 15:42:09');
INSERT INTO `log` VALUES ('166', '超管', '172.21.105.182', '2017-09-16 15:48:56');
INSERT INTO `log` VALUES ('167', '超管', '172.21.105.182', '2017-09-16 16:19:52');
INSERT INTO `log` VALUES ('168', '寇太明', '172.21.105.182', '2017-09-16 16:45:39');
INSERT INTO `log` VALUES ('169', '超管', '172.21.105.182', '2017-09-16 16:45:49');
INSERT INTO `log` VALUES ('170', '边琪', '172.21.105.182', '2017-09-16 16:57:08');
INSERT INTO `log` VALUES ('171', '寇太明', '172.21.105.182', '2017-09-16 16:58:10');
INSERT INTO `log` VALUES ('172', '超管', '172.21.105.182', '2017-09-16 17:07:45');
INSERT INTO `log` VALUES ('173', '边琪', '172.21.105.182', '2017-09-16 17:08:03');
INSERT INTO `log` VALUES ('174', '超管', '172.21.105.182', '2017-09-16 17:08:25');
INSERT INTO `log` VALUES ('175', '超管', '172.21.105.182', '2017-09-16 17:26:22');
INSERT INTO `log` VALUES ('176', '超管', '172.21.105.182', '2017-09-16 18:26:48');
INSERT INTO `log` VALUES ('177', '寇太明', '172.21.105.182', '2017-09-16 18:28:10');
INSERT INTO `log` VALUES ('178', '郝昌文', '172.21.105.182', '2017-09-16 18:28:37');
INSERT INTO `log` VALUES ('179', '李洪波', '172.21.105.182', '2017-09-16 18:29:03');
INSERT INTO `log` VALUES ('180', '超管', '172.21.105.182', '2017-09-16 18:29:19');
INSERT INTO `log` VALUES ('181', '超管', '172.21.105.182', '2017-09-16 18:33:04');
INSERT INTO `log` VALUES ('182', '超管', '172.21.105.182', '2017-09-16 18:35:31');
INSERT INTO `log` VALUES ('183', '超管', '172.21.105.182', '2017-09-16 18:42:36');
INSERT INTO `log` VALUES ('184', '超管', '172.21.105.182', '2017-09-16 18:47:22');
INSERT INTO `log` VALUES ('185', '超管', '172.21.105.182', '2017-09-16 20:37:58');
INSERT INTO `log` VALUES ('186', '超管', '172.21.105.182', '2017-09-16 20:39:06');
INSERT INTO `log` VALUES ('187', '超管', '172.21.105.182', '2017-09-16 20:39:33');
INSERT INTO `log` VALUES ('188', '寇太明', '172.21.105.182', '2017-09-16 20:39:55');
INSERT INTO `log` VALUES ('189', '超管', '172.21.105.182', '2017-09-16 20:41:40');
INSERT INTO `log` VALUES ('190', '寇太明', '172.21.105.182', '2017-09-16 20:42:28');
INSERT INTO `log` VALUES ('191', '郝昌文', '172.21.105.182', '2017-09-16 20:43:01');
INSERT INTO `log` VALUES ('192', '超管', '172.21.105.182', '2017-09-16 20:58:48');
INSERT INTO `log` VALUES ('193', '超管', '172.21.105.182', '2017-09-16 21:02:21');
INSERT INTO `log` VALUES ('194', '超管', '172.21.105.182', '2017-09-16 21:03:52');
INSERT INTO `log` VALUES ('195', '超管', '172.21.105.182', '2017-09-16 21:05:26');
INSERT INTO `log` VALUES ('196', '超管', '172.21.105.182', '2017-09-16 21:44:28');
INSERT INTO `log` VALUES ('197', '超管', '172.21.105.182', '2017-09-16 21:45:31');
INSERT INTO `log` VALUES ('198', '超管', '172.21.105.182', '2017-09-16 21:45:57');
INSERT INTO `log` VALUES ('199', '超管', '172.21.105.182', '2017-09-16 21:47:05');
INSERT INTO `log` VALUES ('200', '超管', '172.21.105.182', '2017-09-16 22:53:55');
INSERT INTO `log` VALUES ('201', '超管', '172.21.105.182', '2017-09-17 00:23:28');
INSERT INTO `log` VALUES ('202', '超管', '172.21.105.182', '2017-09-17 00:25:23');
INSERT INTO `log` VALUES ('203', '寇太明', '172.21.105.182', '2017-09-17 00:29:21');
INSERT INTO `log` VALUES ('204', '郝昌文', '172.21.105.182', '2017-09-17 00:29:49');
INSERT INTO `log` VALUES ('205', '郝昌文', '172.21.105.182', '2017-09-17 00:30:26');
INSERT INTO `log` VALUES ('206', '超管', '172.21.105.182', '2017-09-17 00:30:43');
INSERT INTO `log` VALUES ('207', '郝昌文', '172.21.105.182', '2017-09-17 00:31:20');
INSERT INTO `log` VALUES ('208', '超管', '172.21.105.182', '2017-09-17 00:31:32');
INSERT INTO `log` VALUES ('209', '超管', '172.21.105.182', '2017-09-17 10:28:15');
INSERT INTO `log` VALUES ('210', '寇太明', '172.21.105.182', '2017-09-17 10:50:07');
INSERT INTO `log` VALUES ('211', '郝昌文', '172.21.105.182', '2017-09-17 10:50:41');
INSERT INTO `log` VALUES ('212', '李洪波', '172.21.105.182', '2017-09-17 10:50:51');
INSERT INTO `log` VALUES ('213', '超管', '172.21.105.182', '2017-09-17 11:03:13');
INSERT INTO `log` VALUES ('214', '超管', '172.21.105.182', '2017-09-17 12:58:58');
INSERT INTO `log` VALUES ('215', '超管', '172.21.105.182', '2017-09-17 12:59:26');
INSERT INTO `log` VALUES ('216', '超管', '172.21.105.182', '2017-09-17 20:11:42');
INSERT INTO `log` VALUES ('217', '李洪波', '172.21.105.182', '2017-09-17 20:20:36');
INSERT INTO `log` VALUES ('218', '超管', '172.21.105.182', '2017-09-17 20:20:43');
INSERT INTO `log` VALUES ('219', '超管', '172.21.105.182', '2017-09-17 20:37:49');
INSERT INTO `log` VALUES ('220', '超管', '172.21.105.182', '2017-09-17 20:41:38');
INSERT INTO `log` VALUES ('221', '超管', '172.21.105.182', '2017-09-17 20:47:09');
INSERT INTO `log` VALUES ('222', '超管', '172.21.105.182', '2017-09-18 11:15:19');
INSERT INTO `log` VALUES ('223', '超管', '172.21.105.182', '2017-09-18 17:18:28');
INSERT INTO `log` VALUES ('224', '超管', '172.21.105.182', '2017-09-18 17:21:00');
INSERT INTO `log` VALUES ('225', '边琪', '172.21.105.182', '2017-09-18 17:21:24');
INSERT INTO `log` VALUES ('226', '边琪', '172.21.105.182', '2017-09-18 17:30:53');
INSERT INTO `log` VALUES ('227', '边琪', '172.21.105.182', '2017-09-18 17:37:02');
INSERT INTO `log` VALUES ('228', '超管', '172.21.105.182', '2017-09-18 23:01:12');
INSERT INTO `log` VALUES ('229', '边琪', '172.21.105.182', '2017-09-18 23:18:29');
INSERT INTO `log` VALUES ('230', '超管', '172.21.105.182', '2017-09-18 23:21:39');
INSERT INTO `log` VALUES ('231', '超管', '172.21.105.182', '2017-09-18 23:22:12');
INSERT INTO `log` VALUES ('232', '邓人才', '172.21.105.182', '2017-09-18 23:23:05');
INSERT INTO `log` VALUES ('233', '邓人才', '172.21.105.182', '2017-09-18 23:26:23');
INSERT INTO `log` VALUES ('234', '邓人才', '172.21.105.182', '2017-09-18 23:31:56');
INSERT INTO `log` VALUES ('235', '邓人才', '172.21.105.182', '2017-09-18 23:32:54');
INSERT INTO `log` VALUES ('236', '超管', '172.21.105.182', '2017-09-18 23:38:37');
INSERT INTO `log` VALUES ('237', '邓人才', '172.21.105.182', '2017-09-18 23:41:53');
INSERT INTO `log` VALUES ('238', '邓人才', '172.21.105.182', '2017-09-18 23:44:10');
INSERT INTO `log` VALUES ('239', '邓人才', '172.21.105.182', '2017-09-18 23:44:56');
INSERT INTO `log` VALUES ('240', '邓人才', '172.21.105.182', '2017-09-18 23:46:12');
INSERT INTO `log` VALUES ('241', '邓人才', '172.21.105.182', '2017-09-18 23:49:25');
INSERT INTO `log` VALUES ('242', '超管', '172.21.105.182', '2017-09-18 23:50:08');
INSERT INTO `log` VALUES ('243', '超管', '172.21.105.182', '2017-09-18 23:56:37');
INSERT INTO `log` VALUES ('244', '超管', '172.21.105.182', '2017-09-19 00:00:20');
INSERT INTO `log` VALUES ('245', '超管', '172.21.105.182', '2017-09-19 00:05:30');
INSERT INTO `log` VALUES ('246', '超管', '172.21.105.182', '2017-09-19 00:07:13');
INSERT INTO `log` VALUES ('247', '超管', '172.21.105.182', '2017-09-19 00:07:58');
INSERT INTO `log` VALUES ('248', '超管', '172.21.105.182', '2017-09-19 00:09:45');
INSERT INTO `log` VALUES ('249', '寇太明', '172.21.105.182', '2017-09-19 00:12:31');
INSERT INTO `log` VALUES ('250', '超管', '172.21.105.182', '2017-09-19 00:12:46');
INSERT INTO `log` VALUES ('251', '寇太明', '172.21.105.182', '2017-09-19 00:13:34');
INSERT INTO `log` VALUES ('252', '郝昌文', '172.21.105.182', '2017-09-19 00:13:38');
INSERT INTO `log` VALUES ('253', '超管', '172.21.105.182', '2017-09-19 00:15:06');

-- ----------------------------
-- Table structure for logtime
-- ----------------------------
DROP TABLE IF EXISTS `logtime`;
CREATE TABLE `logtime` (
  `logtime_id` int(11) NOT NULL,
  `logintime` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`logtime_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of logtime
-- ----------------------------
INSERT INTO `logtime` VALUES ('0', '0');
INSERT INTO `logtime` VALUES ('1', '1');
INSERT INTO `logtime` VALUES ('2', '2');
INSERT INTO `logtime` VALUES ('3', '3');
INSERT INTO `logtime` VALUES ('4', '4');
INSERT INTO `logtime` VALUES ('5', '5');
INSERT INTO `logtime` VALUES ('6', '6');
INSERT INTO `logtime` VALUES ('7', '7');
INSERT INTO `logtime` VALUES ('8', '8');
INSERT INTO `logtime` VALUES ('9', '9');
INSERT INTO `logtime` VALUES ('10', '10');
INSERT INTO `logtime` VALUES ('11', '11');
INSERT INTO `logtime` VALUES ('12', '12');
INSERT INTO `logtime` VALUES ('13', '13');
INSERT INTO `logtime` VALUES ('14', '14');
INSERT INTO `logtime` VALUES ('15', '15	');
INSERT INTO `logtime` VALUES ('16', '16');
INSERT INTO `logtime` VALUES ('17', '17');
INSERT INTO `logtime` VALUES ('18', '18');
INSERT INTO `logtime` VALUES ('19', '19');
INSERT INTO `logtime` VALUES ('20', '20');
INSERT INTO `logtime` VALUES ('21', '21');
INSERT INTO `logtime` VALUES ('22', '22');
INSERT INTO `logtime` VALUES ('23', '23');

-- ----------------------------
-- Table structure for notice
-- ----------------------------
DROP TABLE IF EXISTS `notice`;
CREATE TABLE `notice` (
  `notice_id` int(11) NOT NULL AUTO_INCREMENT,
  `admin_id` int(11) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `content` varchar(10000) DEFAULT NULL,
  `createtime` datetime DEFAULT NULL,
  `is_released` int(11) DEFAULT NULL,
  PRIMARY KEY (`notice_id`)
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of notice
-- ----------------------------

-- ----------------------------
-- Table structure for plan
-- ----------------------------
DROP TABLE IF EXISTS `plan`;
CREATE TABLE `plan` (
  `plan_id` int(11) NOT NULL AUTO_INCREMENT,
  `admin_id` int(11) DEFAULT NULL,
  `createtime` datetime DEFAULT NULL,
  `sign` int(11) DEFAULT NULL,
  `exam_name` varchar(255) DEFAULT NULL,
  `pname` varchar(255) DEFAULT NULL,
  `updatetime` datetime DEFAULT NULL,
  `plan_aim` varchar(255) DEFAULT NULL,
  `ability` varchar(255) DEFAULT NULL,
  `scale` varchar(255) DEFAULT NULL,
  `plan_pattern` varchar(255) DEFAULT NULL,
  `exam_pattern` varchar(255) DEFAULT NULL,
  `plan_content` varchar(255) DEFAULT NULL,
  `plan_requirement` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`plan_id`)
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of plan
-- ----------------------------

-- ----------------------------
-- Table structure for plan_books
-- ----------------------------
DROP TABLE IF EXISTS `plan_books`;
CREATE TABLE `plan_books` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `book_id` int(11) DEFAULT NULL,
  `plan_id` int(11) DEFAULT NULL,
  `bname` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of plan_books
-- ----------------------------

-- ----------------------------
-- Table structure for plan_devices
-- ----------------------------
DROP TABLE IF EXISTS `plan_devices`;
CREATE TABLE `plan_devices` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `device_id` int(11) DEFAULT NULL,
  `plan_id` int(11) DEFAULT NULL,
  `dname` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of plan_devices
-- ----------------------------

-- ----------------------------
-- Table structure for plan_teacher
-- ----------------------------
DROP TABLE IF EXISTS `plan_teacher`;
CREATE TABLE `plan_teacher` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `teacher_id` int(11) DEFAULT NULL,
  `plan_id` int(11) DEFAULT NULL,
  `tname` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of plan_teacher
-- ----------------------------

-- ----------------------------
-- Table structure for role_function
-- ----------------------------
DROP TABLE IF EXISTS `role_function`;
CREATE TABLE `role_function` (
  `role_id` varchar(32) NOT NULL,
  `function_id` varchar(32) NOT NULL,
  PRIMARY KEY (`role_id`,`function_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of role_function
-- ----------------------------
INSERT INTO `role_function` VALUES ('0', '1');
INSERT INTO `role_function` VALUES ('2', '2');
INSERT INTO `role_function` VALUES ('3', '3');
INSERT INTO `role_function` VALUES ('4', '4');
INSERT INTO `role_function` VALUES ('5', '5');
INSERT INTO `role_function` VALUES ('6', '6');

-- ----------------------------
-- Table structure for score
-- ----------------------------
DROP TABLE IF EXISTS `score`;
CREATE TABLE `score` (
  `score_id` int(11) NOT NULL AUTO_INCREMENT,
  `student_id` int(11) DEFAULT NULL,
  `theoryscore` float(10,3) DEFAULT NULL,
  `practicescore` float(10,3) DEFAULT NULL,
  `total` float(10,3) DEFAULT NULL,
  `common` varchar(255) DEFAULT NULL,
  `course_id` int(11) DEFAULT NULL,
  `classes_id` int(11) DEFAULT NULL,
  `teacher_id` int(11) DEFAULT NULL,
  `certificateNo` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`score_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of score
-- ----------------------------

-- ----------------------------
-- Table structure for student
-- ----------------------------
DROP TABLE IF EXISTS `student`;
CREATE TABLE `student` (
  `student_id` int(11) NOT NULL AUTO_INCREMENT,
  `loginname` varchar(255) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `telephone` varchar(255) DEFAULT NULL,
  `education` varchar(255) DEFAULT NULL,
  `idcard` varchar(255) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `createtime` datetime DEFAULT NULL,
  `updatetime` datetime DEFAULT NULL,
  `identify` int(11) DEFAULT NULL,
  `classes_id` int(11) DEFAULT NULL,
  `picpath` varchar(255) DEFAULT NULL,
  `isFinish` int(11) DEFAULT NULL,
  PRIMARY KEY (`student_id`)
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of student
-- ----------------------------

-- ----------------------------
-- Table structure for syllabus
-- ----------------------------
DROP TABLE IF EXISTS `syllabus`;
CREATE TABLE `syllabus` (
  `syllabus_id` int(11) NOT NULL AUTO_INCREMENT,
  `week` varchar(255) DEFAULT NULL,
  `amfirst` varchar(255) DEFAULT NULL,
  `pmfirst` varchar(255) DEFAULT NULL,
  `class_id` int(11) DEFAULT NULL,
  `night` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`syllabus_id`)
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of syllabus
-- ----------------------------

-- ----------------------------
-- Table structure for teacher
-- ----------------------------
DROP TABLE IF EXISTS `teacher`;
CREATE TABLE `teacher` (
  `teacher_id` int(11) NOT NULL AUTO_INCREMENT,
  `loginname` varchar(255) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `telephone` varchar(255) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `education` varchar(255) DEFAULT NULL,
  `idcard` varchar(255) DEFAULT NULL,
  `identify` int(11) DEFAULT NULL,
  `createtime` datetime DEFAULT NULL,
  `updatetime` datetime DEFAULT NULL,
  `position` varchar(255) DEFAULT NULL,
  `isFinish` int(11) DEFAULT NULL,
  PRIMARY KEY (`teacher_id`)
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of teacher
-- ----------------------------

-- ----------------------------
-- Table structure for user_role
-- ----------------------------
DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role` (
  `user_id` varchar(32) NOT NULL,
  `role_id` varchar(32) NOT NULL,
  PRIMARY KEY (`user_id`,`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user_role
-- ----------------------------
INSERT INTO `user_role` VALUES ('1', '0');
INSERT INTO `user_role` VALUES ('2', '2');
INSERT INTO `user_role` VALUES ('3', '3');
INSERT INTO `user_role` VALUES ('4', '4');
INSERT INTO `user_role` VALUES ('5', '5');
INSERT INTO `user_role` VALUES ('6', '6');
