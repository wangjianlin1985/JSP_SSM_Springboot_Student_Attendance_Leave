package com.chengxusheji.po;

import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotEmpty;
import org.json.JSONException;
import org.json.JSONObject;

public class Course {
    /*课程编号*/
    @NotEmpty(message="课程编号不能为空")
    private String courseNo;
    public String getCourseNo(){
        return courseNo;
    }
    public void setCourseNo(String courseNo){
        this.courseNo = courseNo;
    }

    /*课程名称*/
    @NotEmpty(message="课程名称不能为空")
    private String courseName;
    public String getCourseName() {
        return courseName;
    }
    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    /*开设班级*/
    private ClassInfo classObj;
    public ClassInfo getClassObj() {
        return classObj;
    }
    public void setClassObj(ClassInfo classObj) {
        this.classObj = classObj;
    }

    /*上课老师*/
    private Teacher teacherObj;
    public Teacher getTeacherObj() {
        return teacherObj;
    }
    public void setTeacherObj(Teacher teacherObj) {
        this.teacherObj = teacherObj;
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

    /*上课教室*/
    @NotEmpty(message="上课教室不能为空")
    private String coursePlace;
    public String getCoursePlace() {
        return coursePlace;
    }
    public void setCoursePlace(String coursePlace) {
        this.coursePlace = coursePlace;
    }

    /*备注信息*/
    private String courseMemo;
    public String getCourseMemo() {
        return courseMemo;
    }
    public void setCourseMemo(String courseMemo) {
        this.courseMemo = courseMemo;
    }

    public JSONObject getJsonObject() throws JSONException {
    	JSONObject jsonCourse=new JSONObject(); 
		jsonCourse.accumulate("courseNo", this.getCourseNo());
		jsonCourse.accumulate("courseName", this.getCourseName());
		jsonCourse.accumulate("classObj", this.getClassObj().getClassName());
		jsonCourse.accumulate("classObjPri", this.getClassObj().getClassNo());
		jsonCourse.accumulate("teacherObj", this.getTeacherObj().getName());
		jsonCourse.accumulate("teacherObjPri", this.getTeacherObj().getTeacherNo());
		jsonCourse.accumulate("weekDay", this.getWeekDay());
		jsonCourse.accumulate("sectionNo", this.getSectionNo());
		jsonCourse.accumulate("coursePlace", this.getCoursePlace());
		jsonCourse.accumulate("courseMemo", this.getCourseMemo());
		return jsonCourse;
    }}