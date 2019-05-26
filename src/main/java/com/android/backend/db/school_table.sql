DROP TABLE IF EXISTS `user_student`;
CREATE TABLE `user_student` (
                                     id INTEGER PRIMARY KEY AUTO_INCREMENT,
                                     user_name NVARCHAR(100) UNIQUE NOT NULL,
                                     user_password  NVARCHAR(100) NOT NULL,
                                    PRIMARY KEY (`id`)
    )COMMENT='登陆表';


DROP TABLE IF EXISTS `student_info`;
CREATE TABLE `student_info` (
                                id INTEGER PRIMARY KEY AUTO_INCREMENT,
                                nick_name NVARCHAR(100) NOT NULL DEFAULT '',
                                user_id int(11) NOT NULL DEFAULT '0',
                                phone NVARCHAR(100) NOT NULL DEFAULT ' ' COMMENT '联系电话',
                                major NVARCHAR(100) NOT NULL DEFAULT ' ' COMMENT'专业',
                                year NVARCHAR(100) NOT NULL DEFAULT ' ' COMMENT'入学年份',
                                institute NVARCHAR(100) NOT NULL DEFAULT ' ' COMMENT'学院',
                                province NVARCHAR(100) NOT NULL DEFAULT ' ' COMMENT'行政区域表的省',
                                city NVARCHAR(100) NOT NULL DEFAULT ' 'COMMENT'行政区域表的市',
                                area NVARCHAR(100) NOT NULL DEFAULT ' 'COMMENT'行政区域表的区县',
                                gender tinyint(3) NOT NULL DEFAULT '0'COMMENT '性别：0 未知， 1男， 1 女',
                                birthday date DEFAULT NULL COMMENT'生日',
                                last_login_time datetime DEFAULT NULL COMMENT'最近一次登录时间',
                                add_time datetime DEFAULT NULL COMMENT'创建时间',
                                update_time datetime DEFAULT NULL COMMENT '更新时间',
                                headshot varchar(255) NOT NULL DEFAULT ''COMMENT '用户头像图片',
                                PRIMARY KEY (`id`)
)COMMENT='学生信息表';

DROP TABLE IF EXISTS `role_permission`;
CREATE TABLE `role_permission` (
                                id INTEGER PRIMARY KEY AUTO_INCREMENT,
                                rid int(11) NOT NULL DEFAULT '0' COMMENT '1为学生，2为教师',
                                role NVARCHAR(100) UNIQUE NOT NULL COMMENT '角色名称',
                                PRIMARY KEY (`id`)
)COMMENT='角色表';


DROP TABLE IF EXISTS `teacher_info`;
CREATE TABLE `teacher_info` (
                                id INTEGER PRIMARY KEY AUTO_INCREMENT,
                                user_id int(11) NOT NULL DEFAULT '0',
                                nick_name NVARCHAR(100) NOT NULL DEFAULT '',
                                province NVARCHAR(100) NOT NULL DEFAULT ' ' COMMENT'行政区域表的省',
                                city NVARCHAR(100) NOT NULL DEFAULT ' 'COMMENT'行政区域表的市',
                                area NVARCHAR(100) NOT NULL DEFAULT ' 'COMMENT'行政区域表的区县',
                                gender tinyint(3) NOT NULL DEFAULT '0'COMMENT '性别：0 未知， 1男， 1 女',
                                birthday date DEFAULT NULL COMMENT'生日',
                                last_login_time datetime DEFAULT NULL COMMENT'最近一次登录时间',
                                add_time datetime DEFAULT NULL COMMENT'创建时间',
                                update_time datetime DEFAULT NULL COMMENT '更新时间',
                                headshot varchar(255) NOT NULL DEFAULT ''COMMENT '用户头像图片',
                                PRIMARY KEY (`id`)
)COMMENT='教师信息表';


DROP TABLE IF EXISTS `table_storage`;
CREATE TABLE `table_storage` (
                                id INTEGER PRIMARY KEY AUTO_INCREMENT,
                                key NVARCHAR(100) NOT NULL DEFAULT ' 'COMMENT '文件的唯一索引',
                                name NVARCHAR(100) NOT NULL DEFAULT ' 'COMMENT '文件名',
                                type NVARCHAR(100) NOT NULL DEFAULT ' 'COMMENT '文件类型',
                                size NVARCHAR(100) NOT NULL DEFAULT '' COMMENT '文件大小',
                                url NVARCHAR(100) NOT NULL DEFAULT '' COMMENT '访问连接',
                                PRIMARY KEY (`id`)
)COMMENT='文件存储';

DROP TABLE IF EXISTS `course_info`;
CREATE TABLE `course_info` (
                                 id INTEGER PRIMARY KEY AUTO_INCREMENT,
                                 course_name NVARCHAR(100) NOT NULL DEFAULT ' ' COMMENT '课程名字',
                                 teacher NVARCHAR(100) NOT NULL DEFAULT ' ' COMMENT '教师名字',
                                 PRIMARY KEY (`id`)
)COMMENT='课堂信息';

DROP TABLE IF EXISTS `user_course`;
CREATE TABLE `user_course` (
                               id INTEGER PRIMARY KEY AUTO_INCREMENT,
                               cid integer(10) NOT NULL DEFAULT '0' COMMENT '课程标记id',
                               uid NVARCHAR(100) NOT NULL DEFAULT ' ' COMMENT '课程名字',
                               cid NVARCHAR(100) NOT NULL DEFAULT ' ' COMMENT '教师名字',
                               PRIMARY KEY (`id`)
)COMMENT='课堂';
/*'****************安卓端的global ，整体设计在数据库表中。dtd也不需要传值*/
DROP TABLE IF EXISTS `global_info`;
CREATE TABLE `global_info` (
                                   id INTEGER PRIMARY KEY AUTO_INCREMENT,
                                   version integer(100) NOT NULL DEFAULT ' ' COMMENT '版本号',
                                   versionStr NVARCHAR(100) NOT NULL DEFAULT ' ' COMMENT '版本描述',
                                   termBegin NVARCHAR(100) NOT NULL DEFAULT ' ' COMMENT '起始端',
                                   yearFrom integer(100) NOT NULL DEFAULT ' ' COMMENT '开始',
                                   yearTo integer(100) NOT NULL DEFAULT ' ' COMMENT '至',
                                   term integer(100) NOT NULL DEFAULT ' ' COMMENT '端',
                                   isFirstUse integer(100) NOT NULL DEFAULT ' ' COMMENT '判断需要初始化',
                                   activeUserUid integer(100) NOT NULL DEFAULT ' ' COMMENT '判断用户UID',
                                   PRIMARY KEY (`id`)
)COMMENT='课程整体信息';


DROP TABLE IF EXISTS `course_baseinfo`;
CREATE TABLE `course_baseinfo` (
                               id INTEGER PRIMARY KEY AUTO_INCREMENT,
                               weekfrom integer(100) NOT NULL DEFAULT ' ' COMMENT '起始周',
                               weekto integer(100) NOT NULL DEFAULT ' ' COMMENT '结束周',
                               weektype integer(100) NOT NULL DEFAULT ' ' COMMENT '周类型，1普通，2单周，3双周',
                               day NVARCHAR(100) NOT NULL DEFAULT ' ' COMMENT '星期几上课',
                               lessonfrom integer(100) NOT NULL DEFAULT ' ' COMMENT '开始节次',
                               lessonto integer(100) NOT NULL DEFAULT ' ' COMMENT '结束节次',
                               place NVARCHAR(100) NOT NULL DEFAULT ' ' COMMENT '地点',
                               PRIMARY KEY (`id`)
)COMMENT='课程整体信息';

DROP TABLE IF EXISTS `course_detail`;
CREATE TABLE `course_detail` (
                               id INTEGER PRIMARY KEY AUTO_INCREMENT,
                               student_name NVARCHAR(100) NOT NULL DEFAULT ' ' COMMENT '学生名字',
                               course_id NVARCHAR(100) NOT NULL DEFAULT ' ' COMMENT '课程所属id',
                               regular_grade NVARCHAR(100) NOT NULL DEFAULT ' ' COMMENT '平时成绩',
                               evaluation_score int(100) NOT NULL DEFAULT ' ' COMMENT '评价分数',
                               evaluation_info NVARCHAR(100) NOT NULL DEFAULT ' ' COMMENT '评价内容',
                               PRIMARY KEY (`id`)
)COMMENT='课程学生信息表';

DROP TABLE IF EXISTS `class_room_on_duty`;
CREATE TABLE `class_room_on_duty` (
                                 id INTEGER PRIMARY KEY AUTO_INCREMENT,
                                 student_name NVARCHAR(100) NOT NULL DEFAULT ' ' COMMENT '学生名字',
                                 course_id NVARCHAR(100) NOT NULL DEFAULT ' ' COMMENT '课程所属id',
                                 homework_grade NVARCHAR(100) NOT NULL DEFAULT ' ' COMMENT '作业成绩',
                                 participation int(11) NOT NULL DEFAULT ' ' COMMENT '出勤, 0未到，1有到',
                                 day int(100) NOT NULL DEFAULT ' ' COMMENT '第{}节课',
                                 PRIMARY KEY (`id`)
)COMMENT='课堂出勤表';


DROP TABLE IF EXISTS `class_room_home_work`;
CREATE TABLE `class_room_on_duty` (
                                      id INTEGER PRIMARY KEY AUTO_INCREMENT,
                                      student_name NVARCHAR(100) NOT NULL DEFAULT ' ' COMMENT '学生名字',
                                      course_id NVARCHAR(100) NOT NULL DEFAULT ' ' COMMENT '课程所属id',
                                      homework_grade NVARCHAR(100) NOT NULL DEFAULT ' ' COMMENT '作业成绩',
                                      homework_info NVARCHAR(100) NOT NULL DEFAULT ' ' COMMENT '作业信息',
                                      PRIMARY KEY (`id`)
)COMMENT='作业表';
