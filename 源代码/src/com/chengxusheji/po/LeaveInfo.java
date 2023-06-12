package com.chengxusheji.po;

import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotEmpty;
import org.json.JSONException;
import org.json.JSONObject;

public class LeaveInfo {
    /*请假id*/
    private Integer leaveId;
    public Integer getLeaveId(){
        return leaveId;
    }
    public void setLeaveId(Integer leaveId){
        this.leaveId = leaveId;
    }

    /*请假原因*/
    @NotEmpty(message="请假原因不能为空")
    private String reason;
    public String getReason() {
        return reason;
    }
    public void setReason(String reason) {
        this.reason = reason;
    }

    /*请假内容*/
    @NotEmpty(message="请假内容不能为空")
    private String content;
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }

    /*请假多久*/
    @NotEmpty(message="请假多久不能为空")
    private String duration;
    public String getDuration() {
        return duration;
    }
    public void setDuration(String duration) {
        this.duration = duration;
    }

    /*请假学生*/
    private Student studentObj;
    public Student getStudentObj() {
        return studentObj;
    }
    public void setStudentObj(Student studentObj) {
        this.studentObj = studentObj;
    }

    /*请假时间*/
    private String leaveAddTime;
    public String getLeaveAddTime() {
        return leaveAddTime;
    }
    public void setLeaveAddTime(String leaveAddTime) {
        this.leaveAddTime = leaveAddTime;
    }

    /*审核状态*/
    @NotEmpty(message="审核状态不能为空")
    private String shzt;
    public String getShzt() {
        return shzt;
    }
    public void setShzt(String shzt) {
        this.shzt = shzt;
    }

    /*审核回复*/
    private String shhf;
    public String getShhf() {
        return shhf;
    }
    public void setShhf(String shhf) {
        this.shhf = shhf;
    }

    /*审核的老师*/
    private String teacherNo;
    public String getTeacherNo() {
        return teacherNo;
    }
    public void setTeacherNo(String teacherNo) {
        this.teacherNo = teacherNo;
    }

    /*审核时间*/
    private String shsj;
    public String getShsj() {
        return shsj;
    }
    public void setShsj(String shsj) {
        this.shsj = shsj;
    }

    public JSONObject getJsonObject() throws JSONException {
    	JSONObject jsonLeaveInfo=new JSONObject(); 
		jsonLeaveInfo.accumulate("leaveId", this.getLeaveId());
		jsonLeaveInfo.accumulate("reason", this.getReason());
		jsonLeaveInfo.accumulate("content", this.getContent());
		jsonLeaveInfo.accumulate("duration", this.getDuration());
		jsonLeaveInfo.accumulate("studentObj", this.getStudentObj().getName());
		jsonLeaveInfo.accumulate("studentObjPri", this.getStudentObj().getStudentNo());
		jsonLeaveInfo.accumulate("leaveAddTime", this.getLeaveAddTime().length()>19?this.getLeaveAddTime().substring(0,19):this.getLeaveAddTime());
		jsonLeaveInfo.accumulate("shzt", this.getShzt());
		jsonLeaveInfo.accumulate("shhf", this.getShhf());
		jsonLeaveInfo.accumulate("teacherNo", this.getTeacherNo());
		jsonLeaveInfo.accumulate("shsj", this.getShsj().length()>19?this.getShsj().substring(0,19):this.getShsj());
		return jsonLeaveInfo;
    }}