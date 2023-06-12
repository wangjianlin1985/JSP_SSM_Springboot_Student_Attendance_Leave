package com.chengxusheji.po;

import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotEmpty;
import org.json.JSONException;
import org.json.JSONObject;

public class ClassInfo {
    /*班级编号*/
    @NotEmpty(message="班级编号不能为空")
    private String classNo;
    public String getClassNo(){
        return classNo;
    }
    public void setClassNo(String classNo){
        this.classNo = classNo;
    }

    /*班级名称*/
    @NotEmpty(message="班级名称不能为空")
    private String className;
    public String getClassName() {
        return className;
    }
    public void setClassName(String className) {
        this.className = className;
    }

    /*班主任*/
    @NotEmpty(message="班主任不能为空")
    private String mainTeacher;
    public String getMainTeacher() {
        return mainTeacher;
    }
    public void setMainTeacher(String mainTeacher) {
        this.mainTeacher = mainTeacher;
    }

    /*成立日期*/
    @NotEmpty(message="成立日期不能为空")
    private String bornDate;
    public String getBornDate() {
        return bornDate;
    }
    public void setBornDate(String bornDate) {
        this.bornDate = bornDate;
    }

    public JSONObject getJsonObject() throws JSONException {
    	JSONObject jsonClassInfo=new JSONObject(); 
		jsonClassInfo.accumulate("classNo", this.getClassNo());
		jsonClassInfo.accumulate("className", this.getClassName());
		jsonClassInfo.accumulate("mainTeacher", this.getMainTeacher());
		jsonClassInfo.accumulate("bornDate", this.getBornDate().length()>19?this.getBornDate().substring(0,19):this.getBornDate());
		return jsonClassInfo;
    }}