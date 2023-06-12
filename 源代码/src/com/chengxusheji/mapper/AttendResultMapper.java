package com.chengxusheji.mapper;

import java.util.ArrayList;
import org.apache.ibatis.annotations.Param;
import com.chengxusheji.po.AttendResult;

public interface AttendResultMapper {
	/*添加考勤结果信息*/
	public void addAttendResult(AttendResult attendResult) throws Exception;

	/*按照查询条件分页查询考勤结果记录*/
	public ArrayList<AttendResult> queryAttendResult(@Param("where") String where,@Param("startIndex") int startIndex,@Param("pageSize") int pageSize) throws Exception;

	/*按照查询条件查询所有考勤结果记录*/
	public ArrayList<AttendResult> queryAttendResultList(@Param("where") String where) throws Exception;

	/*按照查询条件的考勤结果记录数*/
	public int queryAttendResultCount(@Param("where") String where) throws Exception; 

	/*根据主键查询某条考勤结果记录*/
	public AttendResult getAttendResult(int arId) throws Exception;

	/*更新考勤结果记录*/
	public void updateAttendResult(AttendResult attendResult) throws Exception;

	/*删除考勤结果记录*/
	public void deleteAttendResult(int arId) throws Exception;

}
