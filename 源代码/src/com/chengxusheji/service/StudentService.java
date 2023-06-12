package com.chengxusheji.service;

import java.util.ArrayList;
import javax.annotation.Resource; 
import org.springframework.stereotype.Service;

import com.chengxusheji.po.Admin;
import com.chengxusheji.po.ClassInfo;
import com.chengxusheji.po.Student;

import com.chengxusheji.mapper.StudentMapper;
@Service
public class StudentService {

	@Resource StudentMapper studentMapper;
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

    /*添加学生记录*/
    public void addStudent(Student student) throws Exception {
    	studentMapper.addStudent(student);
    }

    /*按照查询条件分页查询学生记录*/
    public ArrayList<Student> queryStudent(String studentNo,ClassInfo classObj,String name,String birthDate,String telephone,int currentPage) throws Exception { 
     	String where = "where 1=1";
    	if(!studentNo.equals("")) where = where + " and t_student.studentNo like '%" + studentNo + "%'";
    	if(null != classObj &&  classObj.getClassNo() != null  && !classObj.getClassNo().equals(""))  where += " and t_student.classObj='" + classObj.getClassNo() + "'";
    	if(!name.equals("")) where = where + " and t_student.name like '%" + name + "%'";
    	if(!birthDate.equals("")) where = where + " and t_student.birthDate like '%" + birthDate + "%'";
    	if(!telephone.equals("")) where = where + " and t_student.telephone like '%" + telephone + "%'";
    	int startIndex = (currentPage-1) * this.rows;
    	return studentMapper.queryStudent(where, startIndex, this.rows);
    }

    /*按照查询条件查询所有记录*/
    public ArrayList<Student> queryStudent(String studentNo,ClassInfo classObj,String name,String birthDate,String telephone) throws Exception  { 
     	String where = "where 1=1";
    	if(!studentNo.equals("")) where = where + " and t_student.studentNo like '%" + studentNo + "%'";
    	if(null != classObj &&  classObj.getClassNo() != null && !classObj.getClassNo().equals(""))  where += " and t_student.classObj='" + classObj.getClassNo() + "'";
    	if(!name.equals("")) where = where + " and t_student.name like '%" + name + "%'";
    	if(!birthDate.equals("")) where = where + " and t_student.birthDate like '%" + birthDate + "%'";
    	if(!telephone.equals("")) where = where + " and t_student.telephone like '%" + telephone + "%'";
    	return studentMapper.queryStudentList(where);
    }

    /*查询所有学生记录*/
    public ArrayList<Student> queryAllStudent()  throws Exception {
        return studentMapper.queryStudentList("where 1=1");
    }

    /*当前查询条件下计算总的页数和记录数*/
    public void queryTotalPageAndRecordNumber(String studentNo,ClassInfo classObj,String name,String birthDate,String telephone) throws Exception {
     	String where = "where 1=1";
    	if(!studentNo.equals("")) where = where + " and t_student.studentNo like '%" + studentNo + "%'";
    	if(null != classObj &&  classObj.getClassNo() != null && !classObj.getClassNo().equals(""))  where += " and t_student.classObj='" + classObj.getClassNo() + "'";
    	if(!name.equals("")) where = where + " and t_student.name like '%" + name + "%'";
    	if(!birthDate.equals("")) where = where + " and t_student.birthDate like '%" + birthDate + "%'";
    	if(!telephone.equals("")) where = where + " and t_student.telephone like '%" + telephone + "%'";
        recordNumber = studentMapper.queryStudentCount(where);
        int mod = recordNumber % this.rows;
        totalPage = recordNumber / this.rows;
        if(mod != 0) totalPage++;
    }

    /*根据主键获取学生记录*/
    public Student getStudent(String studentNo) throws Exception  {
        Student student = studentMapper.getStudent(studentNo);
        return student;
    }

    /*更新学生记录*/
    public void updateStudent(Student student) throws Exception {
        studentMapper.updateStudent(student);
    }

    /*删除一条学生记录*/
    public void deleteStudent (String studentNo) throws Exception {
        studentMapper.deleteStudent(studentNo);
    }

    /*删除多条学生信息*/
    public int deleteStudents (String studentNos) throws Exception {
    	String _studentNos[] = studentNos.split(",");
    	for(String _studentNo: _studentNos) {
    		studentMapper.deleteStudent(_studentNo);
    	}
    	return _studentNos.length;
    }
	 
	
	/*保存业务逻辑错误信息字段*/
	private String errMessage;
	public String getErrMessage() { return this.errMessage; }
	
	/*验证用户登录*/
	public boolean checkLogin(String userName, String password) throws Exception { 
		Student db_student = (Student) studentMapper.getStudent(userName);
		if(db_student == null) { 
			this.errMessage = " 账号不存在 ";
			System.out.print(this.errMessage);
			return false;
		} else if( !db_student.getPassword().equals(password)) {
			this.errMessage = " 密码不正确! ";
			System.out.print(this.errMessage);
			return false;
		}
		
		return true;
	}
	
}
