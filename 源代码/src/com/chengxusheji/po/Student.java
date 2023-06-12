package com.chengxusheji.po;

import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotEmpty;
import org.json.JSONException;
import org.json.JSONObject;

public class Student {
    /*学号*/
    @NotEmpty(message="学号不能为空")
    private String studentNo;
    public String getStudentNo(){
        return studentNo;
    }
    public void setStudentNo(String studentNo){
        this.studentNo = studentNo;
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

    /*所在班级*/
    private ClassInfo classObj;
    public ClassInfo getClassObj() {
        return classObj;
    }
    public void setClassObj(ClassInfo classObj) {
        this.classObj = classObj;
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
    private String gender;
    public String getGender() {
        return gender;
    }
    public void setGender(String gender) {
        this.gender = gender;
    }

    /*出生日期*/
    @NotEmpty(message="出生日期不能为空")
    private String birthDate;
    public String getBirthDate() {
        return birthDate;
    }
    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    /*学生照片*/
    private String studentPhoto;
    public String getStudentPhoto() {
        return studentPhoto;
    }
    public void setStudentPhoto(String studentPhoto) {
        this.studentPhoto = studentPhoto;
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

    /*邮箱*/
    @NotEmpty(message="邮箱不能为空")
    private String email;
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    /*家庭地址*/
    private String address;
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }

    /*注册时间*/
    private String regTime;
    public String getRegTime() {
        return regTime;
    }
    public void setRegTime(String regTime) {
        this.regTime = regTime;
    }

    public JSONObject getJsonObject() throws JSONException {
    	JSONObject jsonStudent=new JSONObject(); 
		jsonStudent.accumulate("studentNo", this.getStudentNo());
		jsonStudent.accumulate("password", this.getPassword());
		jsonStudent.accumulate("classObj", this.getClassObj().getClassName());
		jsonStudent.accumulate("classObjPri", this.getClassObj().getClassNo());
		jsonStudent.accumulate("name", this.getName());
		jsonStudent.accumulate("gender", this.getGender());
		jsonStudent.accumulate("birthDate", this.getBirthDate().length()>19?this.getBirthDate().substring(0,19):this.getBirthDate());
		jsonStudent.accumulate("studentPhoto", this.getStudentPhoto());
		jsonStudent.accumulate("telephone", this.getTelephone());
		jsonStudent.accumulate("email", this.getEmail());
		jsonStudent.accumulate("address", this.getAddress());
		jsonStudent.accumulate("regTime", this.getRegTime().length()>19?this.getRegTime().substring(0,19):this.getRegTime());
		return jsonStudent;
    }}