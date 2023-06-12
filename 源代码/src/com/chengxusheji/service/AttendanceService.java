package com.chengxusheji.service;

import java.util.ArrayList;
import javax.annotation.Resource; 
import org.springframework.stereotype.Service;
import com.chengxusheji.po.Student;
import com.chengxusheji.po.Course;
import com.chengxusheji.po.AttendResult;
import com.chengxusheji.po.Attendance;

import com.chengxusheji.mapper.AttendanceMapper;
@Service
public class AttendanceService {

	@Resource AttendanceMapper attendanceMapper;
    /*每页显示记录数目*/
    private int rows = 10;;
    public int getRows() {
		return rows;
	}
	public void setRows(int rows) {
		this.rows = rows;
	}

    /*保存查询后总的页数*/
    private int totalPage;
    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }
    public int getTotalPage() {
        return totalPage;
    }

    /*保存查询到的总记录数*/
    private int recordNumber;
    public void setRecordNumber(int recordNumber) {
        this.recordNumber = recordNumber;
    }
    public int getRecordNumber() {
        return recordNumber;
    }

    /*添加考勤记录*/
    public void addAttendance(Attendance attendance) throws Exception {
    	attendanceMapper.addAttendance(attendance);
    }

    /*按照查询条件分页查询考勤记录*/
    public ArrayList<Attendance> queryAttendance(Student studentObj,Course courseObj,String weekDay,String sectionNo,AttendResult arObj,int currentPage) throws Exception { 
     	String where = "where 1=1";
    	if(null != studentObj &&  studentObj.getStudentNo() != null  && !studentObj.getStudentNo().equals(""))  where += " and t_attendance.studentObj='" + studentObj.getStudentNo() + "'";
    	if(null != courseObj &&  courseObj.getCourseNo() != null  && !courseObj.getCourseNo().equals(""))  where += " and t_attendance.courseObj='" + courseObj.getCourseNo() + "'";
    	if(!weekDay.equals("")) where = where + " and t_attendance.weekDay like '%" + weekDay + "%'";
    	if(!sectionNo.equals("")) where = where + " and t_attendance.sectionNo like '%" + sectionNo + "%'";
    	if(null != arObj && arObj.getArId()!= null && arObj.getArId()!= 0)  where += " and t_attendance.arObj=" + arObj.getArId();
    	int startIndex = (currentPage-1) * this.rows;
    	return attendanceMapper.queryAttendance(where, startIndex, this.rows);
    }
    
    /*按照查询条件分页查询考勤记录*/
    public ArrayList<Attendance> queryAttendance(String teacherNo,Student studentObj,Course courseObj,String weekDay,String sectionNo,AttendResult arObj,int currentPage) throws Exception { 
     	String where = "where 1=1";
     	where += " and t_course.teacherObj='" + teacherNo + "'";
    	if(null != studentObj &&  studentObj.getStudentNo() != null  && !studentObj.getStudentNo().equals(""))  where += " and t_attendance.studentObj='" + studentObj.getStudentNo() + "'";
    	if(null != courseObj &&  courseObj.getCourseNo() != null  && !courseObj.getCourseNo().equals(""))  where += " and t_attendance.courseObj='" + courseObj.getCourseNo() + "'";
    	if(!weekDay.equals("")) where = where + " and t_attendance.weekDay like '%" + weekDay + "%'";
    	if(!sectionNo.equals("")) where = where + " and t_attendance.sectionNo like '%" + sectionNo + "%'";
    	if(null != arObj && arObj.getArId()!= null && arObj.getArId()!= 0)  where += " and t_attendance.arObj=" + arObj.getArId();
    	int startIndex = (currentPage-1) * this.rows;
    	return attendanceMapper.queryAttendance(where, startIndex, this.rows);
    }
    

    /*按照查询条件查询所有记录*/
    public ArrayList<Attendance> queryAttendance(Student studentObj,Course courseObj,String weekDay,String sectionNo,AttendResult arObj) throws Exception  { 
     	String where = "where 1=1";
    	if(null != studentObj &&  studentObj.getStudentNo() != null && !studentObj.getStudentNo().equals(""))  where += " and t_attendance.studentObj='" + studentObj.getStudentNo() + "'";
    	if(null != courseObj &&  courseObj.getCourseNo() != null && !courseObj.getCourseNo().equals(""))  where += " and t_attendance.courseObj='" + courseObj.getCourseNo() + "'";
    	if(!weekDay.equals("")) where = where + " and t_attendance.weekDay like '%" + weekDay + "%'";
    	if(!sectionNo.equals("")) where = where + " and t_attendance.sectionNo like '%" + sectionNo + "%'";
    	if(null != arObj && arObj.getArId()!= null && arObj.getArId()!= 0)  where += " and t_attendance.arObj=" + arObj.getArId();
    	return attendanceMapper.queryAttendanceList(where);
    }

    /*查询所有考勤记录*/
    public ArrayList<Attendance> queryAllAttendance()  throws Exception {
        return attendanceMapper.queryAttendanceList("where 1=1");
    }

    /*当前查询条件下计算总的页数和记录数*/
    public void queryTotalPageAndRecordNumber(Student studentObj,Course courseObj,String weekDay,String sectionNo,AttendResult arObj) throws Exception {
     	String where = "where 1=1";
    	if(null != studentObj &&  studentObj.getStudentNo() != null && !studentObj.getStudentNo().equals(""))  where += " and t_attendance.studentObj='" + studentObj.getStudentNo() + "'";
    	if(null != courseObj &&  courseObj.getCourseNo() != null && !courseObj.getCourseNo().equals(""))  where += " and t_attendance.courseObj='" + courseObj.getCourseNo() + "'";
    	if(!weekDay.equals("")) where = where + " and t_attendance.weekDay like '%" + weekDay + "%'";
    	if(!sectionNo.equals("")) where = where + " and t_attendance.sectionNo like '%" + sectionNo + "%'";
    	if(null != arObj && arObj.getArId()!= null && arObj.getArId()!= 0)  where += " and t_attendance.arObj=" + arObj.getArId();
        recordNumber = attendanceMapper.queryAttendanceCount(where);
        int mod = recordNumber % this.rows;
        totalPage = recordNumber / this.rows;
        if(mod != 0) totalPage++;
    }
    
    
    /*老师查询时候当前查询条件下计算总的页数和记录数*/
    public void queryTotalPageAndRecordNumber(String teacherNo,Student studentObj,Course courseObj,String weekDay,String sectionNo,AttendResult arObj) throws Exception {
     	String where = "where 1=1";
     	where += " and t_course.teacherObj='" + teacherNo + "'";
    	if(null != studentObj &&  studentObj.getStudentNo() != null && !studentObj.getStudentNo().equals(""))  where += " and t_attendance.studentObj='" + studentObj.getStudentNo() + "'";
    	if(null != courseObj &&  courseObj.getCourseNo() != null && !courseObj.getCourseNo().equals(""))  where += " and t_attendance.courseObj='" + courseObj.getCourseNo() + "'";
    	if(!weekDay.equals("")) where = where + " and t_attendance.weekDay like '%" + weekDay + "%'";
    	if(!sectionNo.equals("")) where = where + " and t_attendance.sectionNo like '%" + sectionNo + "%'";
    	if(null != arObj && arObj.getArId()!= null && arObj.getArId()!= 0)  where += " and t_attendance.arObj=" + arObj.getArId();
        recordNumber = attendanceMapper.queryAttendanceCount(where);
        int mod = recordNumber % this.rows;
        totalPage = recordNumber / this.rows;
        if(mod != 0) totalPage++;
    }
    

    /*根据主键获取考勤记录*/
    public Attendance getAttendance(int attendanceId) throws Exception  {
        Attendance attendance = attendanceMapper.getAttendance(attendanceId);
        return attendance;
    }

    /*更新考勤记录*/
    public void updateAttendance(Attendance attendance) throws Exception {
        attendanceMapper.updateAttendance(attendance);
    }

    /*删除一条考勤记录*/
    public void deleteAttendance (int attendanceId) throws Exception {
        attendanceMapper.deleteAttendance(attendanceId);
    }

    /*删除多条考勤信息*/
    public int deleteAttendances (String attendanceIds) throws Exception {
    	String _attendanceIds[] = attendanceIds.split(",");
    	for(String _attendanceId: _attendanceIds) {
    		attendanceMapper.deleteAttendance(Integer.parseInt(_attendanceId));
    	}
    	return _attendanceIds.length;
    }
}
