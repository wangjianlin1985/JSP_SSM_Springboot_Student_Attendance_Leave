# JSP_SSM_Springboot_Student_Attendance_Leave
JSP基于SSM学生考勤请假管理系统可升级SpringBoot毕业源码案例设计
## 程序开发环境：myEclipse/Eclipse/Idea都可以 + mysql数据库
## 前台技术框架： Bootstrap  后台架构框架: SSM
### 学生：
个人中心，查看自己的个人信息修改密码，注册登录，查看我的课表，在线请教，请假时间，请假原因，需要请假多久，查看我的请假状态，（老师审核，或者未审核）查看我在校期间的出勤信息。（例如星期几，下午第三节课计算机专业课，整个学期请假、旷课、迟到、早退了多少次，以及具体的时间、任课老师姓名详细信息。）
### 老师：
查看我的教师课表，查看我的学生信息，登记我学生的考勤状况（例如某某学生在我的第几节课上旷课，迟到了），查看我学生请假信息
审核我学生的请假，可以审核通过跟不通过，查看提交上来的请假
### 管理员
管理管理员，管理教师，管理学生，管理学生课表，管理教师课表，管理考勤信息，管理请假信息，个人中心
学生跟老师要能管理员添加账号，又能自己注册
## 实体ER属性：
班级: 班级编号,班级名称,班主任,成立日期

学生: 学号,登录密码,所在班级,姓名,性别,出生日期,学生照片,联系电话,邮箱,家庭地址,注册时间

老师: 教师编号,登录密码,姓名,性别,老师照片,联系电话,邮箱地址,老师介绍

请假: 请假id,请假原因,请假内容,请假多久,请假学生,请假时间,审核状态,审核回复,审核的老师,审核时间

课程: 课程编号,课程名称,开设班级,上课老师,周日期,第几节,上课教室,备注信息

考勤: 考勤id,考勤学生,考勤课程,周日期,第几节,考勤结果,备注信息

考勤结果: 考勤结果id,考勤结果名称
