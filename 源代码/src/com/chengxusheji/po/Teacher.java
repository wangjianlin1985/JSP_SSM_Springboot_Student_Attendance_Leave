package com.chengxusheji.po;

import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotEmpty;
import org.json.JSONException;
import org.json.JSONObject;

public class Teacher {
    /*教师编号*/
    @NotEmpty(message="教师编号不能为空")
    private String teacherNo;
    public String getTeacherNo(){
        return teacherNo;
    }
    public void setTeacherNo(String teacherNo){
        this.teacherNo = teacherNo;
    }

    /*登录密码*/
    @NotEmpty(message="登录密码不能为空")
    private String password;
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    /*姓名*/
    @NotEmpty(message="姓名不能为空")
    private String name;
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    /*性别*/
    @NotEmpty(message="性别不能为空")
    private String sex;
    public String getSex() {
        return sex;
    }
    public void setSex(String sex) {
        this.sex = sex;
    }

    /*老师照片*/
    private String teacherPhoto;
    public String getTeacherPhoto() {
        return teacherPhoto;
    }
    public void setTeacherPhoto(String teacherPhoto) {
        this.teacherPhoto = teacherPhoto;
    }

    /*联系电话*/
    @NotEmpty(message="联系电话不能为空")
    private String telephone;
    public String getTelephone() {
        return telephone;
    }
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    /*邮箱地址*/
    private String email;
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    /*老师介绍*/
    @NotEmpty(message="老师介绍不能为空")
    private String teacherDesc;
    public String getTeacherDesc() {
        return teacherDesc;
    }
    public void setTeacherDesc(String teacherDesc) {
        this.teacherDesc = teacherDesc;
    }

    public JSONObject getJsonObject() throws JSONException {
    	JSONObject jsonTeacher=new JSONObject(); 
		jsonTeacher.accumulate("teacherNo", this.getTeacherNo());
		jsonTeacher.accumulate("password", this.getPassword());
		jsonTeacher.accumulate("name", this.getName());
		jsonTeacher.accumulate("sex", this.getSex());
		jsonTeacher.accumulate("teacherPhoto", this.getTeacherPhoto());
		jsonTeacher.accumulate("telephone", this.getTelephone());
		jsonTeacher.accumulate("email", this.getEmail());
		jsonTeacher.accumulate("teacherDesc", this.getTeacherDesc());
		return jsonTeacher;
    }}