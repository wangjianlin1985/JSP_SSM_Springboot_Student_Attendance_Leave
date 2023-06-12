package com.chengxusheji.service;

import java.util.ArrayList;
import javax.annotation.Resource; 
import org.springframework.stereotype.Service;
import com.chengxusheji.po.AttendResult;

import com.chengxusheji.mapper.AttendResultMapper;
@Service
public class AttendResultService {

	@Resource AttendResultMapper attendResultMapper;
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

    /*添加考勤结果记录*/
    public void addAttendResult(AttendResult attendResult) throws Exception {
    	attendResultMapper.addAttendResult(attendResult);
    }

    /*按照查询条件分页查询考勤结果记录*/
    public ArrayList<AttendResult> queryAttendResult(int currentPage) throws Exception { 
     	String where = "where 1=1";
    	int startIndex = (currentPage-1) * this.rows;
    	return attendResultMapper.queryAttendResult(where, startIndex, this.rows);
    }

    /*按照查询条件查询所有记录*/
    public ArrayList<AttendResult> queryAttendResult() throws Exception  { 
     	String where = "where 1=1";
    	return attendResultMapper.queryAttendResultList(where);
    }

    /*查询所有考勤结果记录*/
    public ArrayList<AttendResult> queryAllAttendResult()  throws Exception {
        return attendResultMapper.queryAttendResultList("where 1=1");
    }

    /*当前查询条件下计算总的页数和记录数*/
    public void queryTotalPageAndRecordNumber() throws Exception {
     	String where = "where 1=1";
        recordNumber = attendResultMapper.queryAttendResultCount(where);
        int mod = recordNumber % this.rows;
        totalPage = recordNumber / this.rows;
        if(mod != 0) totalPage++;
    }

    /*根据主键获取考勤结果记录*/
    public AttendResult getAttendResult(int arId) throws Exception  {
        AttendResult attendResult = attendResultMapper.getAttendResult(arId);
        return attendResult;
    }

    /*更新考勤结果记录*/
    public void updateAttendResult(AttendResult attendResult) throws Exception {
        attendResultMapper.updateAttendResult(attendResult);
    }

    /*删除一条考勤结果记录*/
    public void deleteAttendResult (int arId) throws Exception {
        attendResultMapper.deleteAttendResult(arId);
    }

    /*删除多条考勤结果信息*/
    public int deleteAttendResults (String arIds) throws Exception {
    	String _arIds[] = arIds.split(",");
    	for(String _arId: _arIds) {
    		attendResultMapper.deleteAttendResult(Integer.parseInt(_arId));
    	}
    	return _arIds.length;
    }
}
