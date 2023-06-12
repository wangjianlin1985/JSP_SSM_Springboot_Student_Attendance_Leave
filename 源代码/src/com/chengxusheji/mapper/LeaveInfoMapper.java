package com.chengxusheji.mapper;

import java.util.ArrayList;
import org.apache.ibatis.annotations.Param;
import com.chengxusheji.po.LeaveInfo;

public interface LeaveInfoMapper {
	/*添加请假信息*/
	public void addLeaveInfo(LeaveInfo leaveInfo) throws Exception;

	/*按照查询条件分页查询请假记录*/
	public ArrayList<LeaveInfo> queryLeaveInfo(@Param("where") String where,@Param("startIndex") int startIndex,@Param("pageSize") int pageSize) throws Exception;

	/*按照查询条件查询所有请假记录*/
	public ArrayList<LeaveInfo> queryLeaveInfoList(@Param("where") String where) throws Exception;

	/*按照查询条件的请假记录数*/
	public int queryLeaveInfoCount(@Param("where") String where) throws Exception; 

	/*根据主键查询某条请假记录*/
	public LeaveInfo getLeaveInfo(int leaveId) throws Exception;

	/*更新请假记录*/
	public void updateLeaveInfo(LeaveInfo leaveInfo) throws Exception;

	/*删除请假记录*/
	public void deleteLeaveInfo(int leaveId) throws Exception;

}
