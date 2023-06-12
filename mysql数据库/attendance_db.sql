/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50051
Source Host           : localhost:3306
Source Database       : attendance_db

Target Server Type    : MYSQL
Target Server Version : 50051
File Encoding         : 65001

Date: 2017-12-26 03:18:11
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `admin`
-- ----------------------------
DROP TABLE IF EXISTS `admin`;
CREATE TABLE `admin` (
  `username` varchar(20) NOT NULL default '',
  `password` varchar(32) default NULL,
  PRIMARY KEY  (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of admin
-- ----------------------------
INSERT INTO `admin` VALUES ('a', 'a');

-- ----------------------------
-- Table structure for `t_attendance`
-- ----------------------------
DROP TABLE IF EXISTS `t_attendance`;
CREATE TABLE `t_attendance` (
  `attendanceId` int(11) NOT NULL auto_increment COMMENT '考勤id',
  `studentObj` varchar(30) NOT NULL COMMENT '考勤学生',
  `courseObj` varchar(20) NOT NULL COMMENT '考勤课程',
  `weekDay` varchar(20) NOT NULL COMMENT '周日期',
  `sectionNo` varchar(20) NOT NULL COMMENT '第几节',
  `arObj` int(11) NOT NULL COMMENT '考勤结果',
  `attendMemo` varchar(500) default NULL COMMENT '备注信息',
  PRIMARY KEY  (`attendanceId`),
  KEY `studentObj` (`studentObj`),
  KEY `courseObj` (`courseObj`),
  KEY `arObj` (`arObj`),
  CONSTRAINT `t_attendance_ibfk_1` FOREIGN KEY (`studentObj`) REFERENCES `t_student` (`studentNo`),
  CONSTRAINT `t_attendance_ibfk_2` FOREIGN KEY (`courseObj`) REFERENCES `t_course` (`courseNo`),
  CONSTRAINT `t_attendance_ibfk_3` FOREIGN KEY (`arObj`) REFERENCES `t_attendresult` (`arId`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_attendance
-- ----------------------------
INSERT INTO `t_attendance` VALUES ('1', 'STU001', 'KC001', '星期二', '上午第一节', '1', '测试');
INSERT INTO `t_attendance` VALUES ('2', 'STU001', 'KC001', '星期二', '上午第三节', '1', 'test');
INSERT INTO `t_attendance` VALUES ('3', 'STU001', 'KC001', '星期二', '上午第四节', '2', 'test');
INSERT INTO `t_attendance` VALUES ('4', 'STU002', 'KC002', '星期五', '下午第一节', '1', '好学生');

-- ----------------------------
-- Table structure for `t_attendresult`
-- ----------------------------
DROP TABLE IF EXISTS `t_attendresult`;
CREATE TABLE `t_attendresult` (
  `arId` int(11) NOT NULL auto_increment COMMENT '考勤结果id',
  `arName` varchar(20) NOT NULL COMMENT '考勤结果名称',
  PRIMARY KEY  (`arId`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_attendresult
-- ----------------------------
INSERT INTO `t_attendresult` VALUES ('1', '已到');
INSERT INTO `t_attendresult` VALUES ('2', '请假');
INSERT INTO `t_attendresult` VALUES ('3', '旷课');
INSERT INTO `t_attendresult` VALUES ('4', '迟到');
INSERT INTO `t_attendresult` VALUES ('5', '早退');

-- ----------------------------
-- Table structure for `t_classinfo`
-- ----------------------------
DROP TABLE IF EXISTS `t_classinfo`;
CREATE TABLE `t_classinfo` (
  `classNo` varchar(20) NOT NULL COMMENT 'classNo',
  `className` varchar(20) NOT NULL COMMENT '班级名称',
  `mainTeacher` varchar(20) NOT NULL COMMENT '班主任',
  `bornDate` varchar(20) default NULL COMMENT '成立日期',
  PRIMARY KEY  (`classNo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_classinfo
-- ----------------------------
INSERT INTO `t_classinfo` VALUES ('BJ001', '计算机1班', '李明杰', '2017-12-04');
INSERT INTO `t_classinfo` VALUES ('BJ002', '计算机2班', '王大力', '2017-12-12');

-- ----------------------------
-- Table structure for `t_course`
-- ----------------------------
DROP TABLE IF EXISTS `t_course`;
CREATE TABLE `t_course` (
  `courseNo` varchar(20) NOT NULL COMMENT 'courseNo',
  `courseName` varchar(20) NOT NULL COMMENT '课程名称',
  `classObj` varchar(20) NOT NULL COMMENT '开设班级',
  `teacherObj` varchar(20) NOT NULL COMMENT '上课老师',
  `weekDay` varchar(20) NOT NULL COMMENT '周日期',
  `sectionNo` varchar(40) NOT NULL COMMENT '第几节',
  `coursePlace` varchar(20) NOT NULL COMMENT '上课教室',
  `courseMemo` varchar(500) default NULL COMMENT '备注信息',
  PRIMARY KEY  (`courseNo`),
  KEY `classObj` (`classObj`),
  KEY `teacherObj` (`teacherObj`),
  CONSTRAINT `t_course_ibfk_1` FOREIGN KEY (`classObj`) REFERENCES `t_classinfo` (`classNo`),
  CONSTRAINT `t_course_ibfk_2` FOREIGN KEY (`teacherObj`) REFERENCES `t_teacher` (`teacherNo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_course
-- ----------------------------
INSERT INTO `t_course` VALUES ('KC001', 'PHP网站开发速成', 'BJ001', 'TH001', '星期二,星期四', '周二上午第3,4;周四下午3,4节', '6A-202', '测试');
INSERT INTO `t_course` VALUES ('KC002', 'HTML5响应式网站开发', 'BJ002', 'TH002', '每周三和周五', '周三上午，周五下午1,2节', '7B-101', '美丽老师上课了');

-- ----------------------------
-- Table structure for `t_leaveinfo`
-- ----------------------------
DROP TABLE IF EXISTS `t_leaveinfo`;
CREATE TABLE `t_leaveinfo` (
  `leaveId` int(11) NOT NULL auto_increment COMMENT '请假id',
  `reason` varchar(80) NOT NULL COMMENT '请假原因',
  `content` varchar(800) NOT NULL COMMENT '请假内容',
  `duration` varchar(20) NOT NULL COMMENT '请假多久',
  `studentObj` varchar(30) NOT NULL COMMENT '请假学生',
  `leaveAddTime` varchar(20) default NULL COMMENT '请假时间',
  `shzt` varchar(20) NOT NULL COMMENT '审核状态',
  `shhf` varchar(500) default NULL COMMENT '审核回复',
  `teacherNo` varchar(20) default NULL COMMENT '审核的老师',
  `shsj` varchar(20) default NULL COMMENT '审核时间',
  PRIMARY KEY  (`leaveId`),
  KEY `studentObj` (`studentObj`),
  CONSTRAINT `t_leaveinfo_ibfk_1` FOREIGN KEY (`studentObj`) REFERENCES `t_student` (`studentNo`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_leaveinfo
-- ----------------------------
INSERT INTO `t_leaveinfo` VALUES ('1', '奶奶生病了', '奶奶得了脑溢血进医院了', '3天', 'STU001', '2017-12-24 02:28:19', '审核通过', '准假！', 'TH001', '2017-12-26 02:18:27');
INSERT INTO `t_leaveinfo` VALUES ('2', '111', '2222', '33', 'STU001', '2017-12-26 01:48:21', '审核拒绝', '原因不详', 'TH001', '2017-12-26 03:02:12');
INSERT INTO `t_leaveinfo` VALUES ('3', '肚子疼不行了', '中午吃了不卫生的东西，肚子太痛了，受不了哦！', '1天', 'STU002', '2017-12-26 03:15:13', '审核通过', '注意多休息！', 'TH002', '2017-12-26 03:16:17');

-- ----------------------------
-- Table structure for `t_student`
-- ----------------------------
DROP TABLE IF EXISTS `t_student`;
CREATE TABLE `t_student` (
  `studentNo` varchar(30) NOT NULL COMMENT 'studentNo',
  `password` varchar(30) NOT NULL COMMENT '登录密码',
  `classObj` varchar(20) NOT NULL COMMENT '所在班级',
  `name` varchar(20) NOT NULL COMMENT '姓名',
  `gender` varchar(4) NOT NULL COMMENT '性别',
  `birthDate` varchar(20) default NULL COMMENT '出生日期',
  `studentPhoto` varchar(60) NOT NULL COMMENT '学生照片',
  `telephone` varchar(20) NOT NULL COMMENT '联系电话',
  `email` varchar(50) NOT NULL COMMENT '邮箱',
  `address` varchar(80) default NULL COMMENT '家庭地址',
  `regTime` varchar(20) default NULL COMMENT '注册时间',
  PRIMARY KEY  (`studentNo`),
  KEY `classObj` (`classObj`),
  CONSTRAINT `t_student_ibfk_1` FOREIGN KEY (`classObj`) REFERENCES `t_classinfo` (`classNo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_student
-- ----------------------------
INSERT INTO `t_student` VALUES ('STU001', '123', 'BJ001', '双鱼林', '男', '2017-11-29', 'upload/0d8d12d2-f3a3-47c2-9009-8a03c9d42213.jpg', '13958342342', 'syl@163.com', '四川南充滨江路13号', '2017-12-24 02:26:59');
INSERT INTO `t_student` VALUES ('STU002', '123', 'BJ002', '张丽英', '女', '2017-12-01', 'upload/92caccb8-3a70-49bb-ac93-6351fe65913f.jpg', '13539842343', 'liying@163.com', '福建南平', '2017-12-26 03:09:07');

-- ----------------------------
-- Table structure for `t_teacher`
-- ----------------------------
DROP TABLE IF EXISTS `t_teacher`;
CREATE TABLE `t_teacher` (
  `teacherNo` varchar(20) NOT NULL COMMENT 'teacherNo',
  `password` varchar(20) NOT NULL COMMENT '登录密码',
  `name` varchar(20) NOT NULL COMMENT '姓名',
  `sex` varchar(4) NOT NULL COMMENT '性别',
  `teacherPhoto` varchar(60) NOT NULL COMMENT '老师照片',
  `telephone` varchar(20) NOT NULL COMMENT '联系电话',
  `email` varchar(20) default NULL COMMENT '邮箱地址',
  `teacherDesc` varchar(5000) NOT NULL COMMENT '老师介绍',
  PRIMARY KEY  (`teacherNo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_teacher
-- ----------------------------
INSERT INTO `t_teacher` VALUES ('TH001', '123', '王家树', '男', 'upload/NoImage.jpg', '13598342342', 'wangjiashu@126.com', '<p>此老师计算机超神了！</p>');
INSERT INTO `t_teacher` VALUES ('TH002', '123', '王乐乐', '女', 'upload/852d4cb0-bb70-4dae-8bcd-7019bcbd6a8c.jpg', '1309894934', 'wanglele@163.com', '<p>我美丽我自信的老师！</p>');
