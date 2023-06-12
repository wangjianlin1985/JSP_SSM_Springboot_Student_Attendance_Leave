package com.chengxusheji.po;

import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotEmpty;
import org.json.JSONException;
import org.json.JSONObject;

public class AttendResult {
    /*考勤结果id*/
    private Integer arId;
    public Integer getArId(){
        return arId;
    }
    public void setArId(Integer arId){
        this.arId = arId;
    }

    /*考勤结果名称*/
    @NotEmpty(message="考勤结果名称不能为空")
    private String arName;
    public String getArName() {
        return arName;
    }
    public void setArName(String arName) {
        this.arName = arName;
    }

    public JSONObject getJsonObject() throws JSONException {
    	JSONObject jsonAttendResult=new JSONObject(); 
		jsonAttendResult.accumulate("arId", this.getArId());
		jsonAttendResult.accumulate("arName", this.getArName());
		return jsonAttendResult;
    }}