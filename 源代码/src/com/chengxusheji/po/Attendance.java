package com.chengxusheji.po;

import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotEmpty;
import org.json.JSONException;
import org.json.JSONObject;

public class Attendance {
    /*考勤id*/
    private Integer attendanceId;
    public Integer getAttendanceId(){
        return attendanceId;
    }
    public void setAttendanceId(Integer attendanceId){
        this.attendanceId = attendanceId;
    }

    /*考勤学生*/
    private Student studentObj;
    public Student getStudentObj() {
        return studentObj;
    }
    public void setStudentObj(Student studentObj) {
        this.studentObj = studentObj;
    }

    /*考勤课程*/
    private Course courseObj;
    public Course getCourseObj() {
        return courseObj;
    }
    public void setCourseObj(Course courseObj) {
        this.courseObj = courseObj;
    }

    /*周日期*/
    @NotEmpty(message="周日期不能为空")
    private String weekDay;
    public String getWeekDay() {
        return weekDay;
    }
    public void setWeekDay(String weekDay) {
        this.weekDay = weekDay;
    }

    /*第几节*/
    @NotEmpty(message="第几节不能为空")
    private String sectionNo;
    public String getSectionNo() {
        return sectionNo;
    }
    public void setSectionNo(String sectionNo) {
        this.sectionNo = sectionNo;
    }

    /*考勤结果*/
    private AttendResult arObj;
    public AttendResult getArObj() {
        return arObj;
    }
    public void setArObj(AttendResult arObj) {
        this.arObj = arObj;
    }

    /*备注信息*/
    private String attendMemo;
    public String getAttendMemo() {
        return attendMemo;
    }
    public void setAttendMemo(String attendMemo) {
        this.attendMemo = attendMemo;
    }

    public JSONObject getJsonObject() throws JSONException {
    	JSONObject jsonAttendance=new JSONObject(); 
		jsonAttendance.accumulate("attendanceId", this.getAttendanceId());
		jsonAttendance.accumulate("studentObj", this.getStudentObj().getName());
		jsonAttendance.accumulate("studentObjPri", this.getStudentObj().getStudentNo());
		jsonAttendance.accumulate("courseObj", this.getCourseObj().getCourseName());
		jsonAttendance.accumulate("courseObjPri", this.getCourseObj().getCourseNo());
		jsonAttendance.accumulate("weekDay", this.getWeekDay());
		jsonAttendance.accumulate("sectionNo", this.getSectionNo());
		jsonAttendance.accumulate("arObj", this.getArObj().getArName());
		jsonAttendance.accumulate("arObjPri", this.getArObj().getArId());
		jsonAttendance.accumulate("attendMemo", this.getAttendMemo());
		return jsonAttendance;
    }}