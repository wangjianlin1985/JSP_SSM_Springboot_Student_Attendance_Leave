﻿package com.chengxusheji.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.chengxusheji.utils.ExportExcelUtil;
import com.chengxusheji.utils.UserException;
import com.chengxusheji.service.AttendanceService;
import com.chengxusheji.po.Attendance;
import com.chengxusheji.service.AttendResultService;
import com.chengxusheji.po.AttendResult;
import com.chengxusheji.service.CourseService;
import com.chengxusheji.po.Course;
import com.chengxusheji.service.StudentService;
import com.chengxusheji.po.Student;

//Attendance管理控制层
@Controller
@RequestMapping("/Attendance")
public class AttendanceController extends BaseController {

    /*业务层对象*/
    @Resource AttendanceService attendanceService;

    @Resource AttendResultService attendResultService;
    @Resource CourseService courseService;
    @Resource StudentService studentService;
	@InitBinder("studentObj")
	public void initBinderstudentObj(WebDataBinder binder) {
		binder.setFieldDefaultPrefix("studentObj.");
	}
	@InitBinder("courseObj")
	public void initBindercourseObj(WebDataBinder binder) {
		binder.setFieldDefaultPrefix("courseObj.");
	}
	@InitBinder("arObj")
	public void initBinderarObj(WebDataBinder binder) {
		binder.setFieldDefaultPrefix("arObj.");
	}
	@InitBinder("attendance")
	public void initBinderAttendance(WebDataBinder binder) {
		binder.setFieldDefaultPrefix("attendance.");
	}
	/*跳转到添加Attendance视图*/
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(Model model,HttpServletRequest request) throws Exception {
		model.addAttribute(new Attendance());
		/*查询所有的AttendResult信息*/
		List<AttendResult> attendResultList = attendResultService.queryAllAttendResult();
		request.setAttribute("attendResultList", attendResultList);
		/*查询所有的Course信息*/
		List<Course> courseList = courseService.queryAllCourse();
		request.setAttribute("courseList", courseList);
		/*查询所有的Student信息*/
		List<Student> studentList = studentService.queryAllStudent();
		request.setAttribute("studentList", studentList);
		return "Attendance_add";
	}

	/*客户端ajax方式提交添加考勤信息*/
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public void add(@Validated Attendance attendance, BindingResult br,
			Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
		String message = "";
		boolean success = false;
		if (br.hasErrors()) {
			message = "输入信息不符合要求！";
			writeJsonResponse(response, success, message);
			return ;
		}
        attendanceService.addAttendance(attendance);
        message = "考勤添加成功!";
        success = true;
        writeJsonResponse(response, success, message);
	}
	
	
	/*客户端ajax方式提交添加考勤信息*/
	@RequestMapping(value = "/teacherAdd", method = RequestMethod.POST)
	public void teacherAdd(@Validated Attendance attendance, BindingResult br,
			Model model, HttpServletRequest request,HttpServletResponse response,HttpSession session) throws Exception {
		String message = "";
		boolean success = false;
		if (br.hasErrors()) {
			message = "输入信息不符合要求！";
			writeJsonResponse(response, success, message);
			return ;
		}
		
		String teacherNo = session.getAttribute("teacherNo").toString();
		String courseNo = attendance.getCourseObj().getCourseNo();
		Course course = courseService.getCourse(courseNo);
		String courseTeacherNo = course.getTeacherObj().getTeacherNo();
		if(!teacherNo.equals(courseTeacherNo)) {
			message = "你并没有上这门课！";
			writeJsonResponse(response, success, message);
			return ;
		}
		
		String studentNo = attendance.getStudentObj().getStudentNo();
		String studentClassNo = studentService.getStudent(studentNo).getClassObj().getClassNo();
		if(!studentClassNo.equals(course.getClassObj().getClassNo())) {
			message = "该学生没有上这门课！";
			writeJsonResponse(response, success, message);
			return ;
		}
		
		if(attendanceService.queryAttendance(attendance.getStudentObj(), attendance.getCourseObj(), attendance.getWeekDay(), attendance.getSectionNo(), null).size() > 0) {
			message = "已经登记过该学生该课程本堂课考勤！";
			writeJsonResponse(response, success, message);
			return ;
		}
		
		attendanceService.addAttendance(attendance);
        message = "考勤添加成功!";
        success = true;
        writeJsonResponse(response, success, message);
	}
	
	
	
	/*ajax方式按照查询条件分页查询考勤信息*/
	@RequestMapping(value = { "/list" }, method = {RequestMethod.GET,RequestMethod.POST})
	public void list(@ModelAttribute("studentObj") Student studentObj,@ModelAttribute("courseObj") Course courseObj,String weekDay,String sectionNo,@ModelAttribute("arObj") AttendResult arObj,Integer page,Integer rows, Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
		if (page==null || page == 0) page = 1;
		if (weekDay == null) weekDay = "";
		if (sectionNo == null) sectionNo = "";
		if(rows != 0)attendanceService.setRows(rows);
		List<Attendance> attendanceList = attendanceService.queryAttendance(studentObj, courseObj, weekDay, sectionNo, arObj, page);
	    /*计算总的页数和总的记录数*/
	    attendanceService.queryTotalPageAndRecordNumber(studentObj, courseObj, weekDay, sectionNo, arObj);
	    /*获取到总的页码数目*/
	    int totalPage = attendanceService.getTotalPage();
	    /*当前查询条件下总记录数*/
	    int recordNumber = attendanceService.getRecordNumber();
        response.setContentType("text/json;charset=UTF-8");
		PrintWriter out = response.getWriter();
		//将要被返回到客户端的对象
		JSONObject jsonObj=new JSONObject();
		jsonObj.accumulate("total", recordNumber);
		JSONArray jsonArray = new JSONArray();
		for(Attendance attendance:attendanceList) {
			JSONObject jsonAttendance = attendance.getJsonObject();
			jsonArray.put(jsonAttendance);
		}
		jsonObj.accumulate("rows", jsonArray);
		out.println(jsonObj.toString());
		out.flush();
		out.close();
	}
	
	
	/*老师ajax方式按照查询条件分页查询考勤信息*/
	@RequestMapping(value = { "/teacherList" }, method = {RequestMethod.GET,RequestMethod.POST})
	public void teacherList(@ModelAttribute("studentObj") Student studentObj,@ModelAttribute("courseObj") Course courseObj,String weekDay,String sectionNo,@ModelAttribute("arObj") AttendResult arObj,Integer page,Integer rows, Model model, HttpServletRequest request,HttpServletResponse response,HttpSession session) throws Exception {
		if (page==null || page == 0) page = 1;
		if (weekDay == null) weekDay = "";
		if (sectionNo == null) sectionNo = "";
		if(rows != 0)attendanceService.setRows(rows);
		String teacherNo = session.getAttribute("teacherNo").toString();
		List<Attendance> attendanceList = attendanceService.queryAttendance(teacherNo,studentObj, courseObj, weekDay, sectionNo, arObj, page);
	    /*计算总的页数和总的记录数*/
	    attendanceService.queryTotalPageAndRecordNumber(teacherNo,studentObj, courseObj, weekDay, sectionNo, arObj);
	    /*获取到总的页码数目*/
	    int totalPage = attendanceService.getTotalPage();
	    /*当前查询条件下总记录数*/
	    int recordNumber = attendanceService.getRecordNumber();
        response.setContentType("text/json;charset=UTF-8");
		PrintWriter out = response.getWriter();
		//将要被返回到客户端的对象
		JSONObject jsonObj=new JSONObject();
		jsonObj.accumulate("total", recordNumber);
		JSONArray jsonArray = new JSONArray();
		for(Attendance attendance:attendanceList) {
			JSONObject jsonAttendance = attendance.getJsonObject();
			jsonArray.put(jsonAttendance);
		}
		jsonObj.accumulate("rows", jsonArray);
		out.println(jsonObj.toString());
		out.flush();
		out.close();
	}
	
	

	/*ajax方式按照查询条件分页查询考勤信息*/
	@RequestMapping(value = { "/listAll" }, method = {RequestMethod.GET,RequestMethod.POST})
	public void listAll(HttpServletResponse response) throws Exception {
		List<Attendance> attendanceList = attendanceService.queryAllAttendance();
        response.setContentType("text/json;charset=UTF-8"); 
		PrintWriter out = response.getWriter();
		JSONArray jsonArray = new JSONArray();
		for(Attendance attendance:attendanceList) {
			JSONObject jsonAttendance = new JSONObject();
			jsonAttendance.accumulate("attendanceId", attendance.getAttendanceId());
			jsonArray.put(jsonAttendance);
		}
		out.println(jsonArray.toString());
		out.flush();
		out.close();
	}

	/*前台按照查询条件分页查询考勤信息*/
	@RequestMapping(value = { "/frontlist" }, method = {RequestMethod.GET,RequestMethod.POST})
	public String frontlist(@ModelAttribute("studentObj") Student studentObj,@ModelAttribute("courseObj") Course courseObj,String weekDay,String sectionNo,@ModelAttribute("arObj") AttendResult arObj,Integer currentPage, Model model, HttpServletRequest request) throws Exception  {
		if (currentPage==null || currentPage == 0) currentPage = 1;
		if (weekDay == null) weekDay = "";
		if (sectionNo == null) sectionNo = "";
		List<Attendance> attendanceList = attendanceService.queryAttendance(studentObj, courseObj, weekDay, sectionNo, arObj, currentPage);
	    /*计算总的页数和总的记录数*/
	    attendanceService.queryTotalPageAndRecordNumber(studentObj, courseObj, weekDay, sectionNo, arObj);
	    /*获取到总的页码数目*/
	    int totalPage = attendanceService.getTotalPage();
	    /*当前查询条件下总记录数*/
	    int recordNumber = attendanceService.getRecordNumber();
	    request.setAttribute("attendanceList",  attendanceList);
	    request.setAttribute("totalPage", totalPage);
	    request.setAttribute("recordNumber", recordNumber);
	    request.setAttribute("currentPage", currentPage);
	    request.setAttribute("studentObj", studentObj);
	    request.setAttribute("courseObj", courseObj);
	    request.setAttribute("weekDay", weekDay);
	    request.setAttribute("sectionNo", sectionNo);
	    request.setAttribute("arObj", arObj);
	    List<AttendResult> attendResultList = attendResultService.queryAllAttendResult();
	    request.setAttribute("attendResultList", attendResultList);
	    List<Course> courseList = courseService.queryAllCourse();
	    request.setAttribute("courseList", courseList);
	    List<Student> studentList = studentService.queryAllStudent();
	    request.setAttribute("studentList", studentList);
		return "Attendance/attendance_frontquery_result"; 
	}
	
	
	/*学生前台按照查询条件分页查询考勤信息*/
	@RequestMapping(value = { "/stuFrontlist" }, method = {RequestMethod.GET,RequestMethod.POST})
	public String stuFrontlist(@ModelAttribute("studentObj") Student studentObj,@ModelAttribute("courseObj") Course courseObj,String weekDay,String sectionNo,@ModelAttribute("arObj") AttendResult arObj,Integer currentPage, Model model, HttpServletRequest request,HttpSession session) throws Exception  {
		if (currentPage==null || currentPage == 0) currentPage = 1;
		if (weekDay == null) weekDay = "";
		if (sectionNo == null) sectionNo = "";
		studentObj = new Student();
	    studentObj.setStudentNo(session.getAttribute("user_name").toString());
	    
		List<Attendance> attendanceList = attendanceService.queryAttendance(studentObj, courseObj, weekDay, sectionNo, arObj, currentPage);
	    
		/*计算总的页数和总的记录数*/
	    attendanceService.queryTotalPageAndRecordNumber(studentObj, courseObj, weekDay, sectionNo, arObj);
	    /*获取到总的页码数目*/
	    int totalPage = attendanceService.getTotalPage();
	    /*当前查询条件下总记录数*/
	    int recordNumber = attendanceService.getRecordNumber();
	    request.setAttribute("attendanceList",  attendanceList);
	    request.setAttribute("totalPage", totalPage);
	    request.setAttribute("recordNumber", recordNumber);
	    request.setAttribute("currentPage", currentPage);
	    request.setAttribute("studentObj", studentObj);
	    request.setAttribute("courseObj", courseObj);
	    request.setAttribute("weekDay", weekDay);
	    request.setAttribute("sectionNo", sectionNo);
	    request.setAttribute("arObj", arObj);
	    List<AttendResult> attendResultList = attendResultService.queryAllAttendResult();
	    request.setAttribute("attendResultList", attendResultList);
	    List<Course> courseList = courseService.queryAllCourse();
	    request.setAttribute("courseList", courseList);
	    List<Student> studentList = studentService.queryAllStudent();
	    request.setAttribute("studentList", studentList);
		return "Attendance/attendance_stuFrontquery_result"; 
	}
	

     /*前台查询Attendance信息*/
	@RequestMapping(value="/{attendanceId}/frontshow",method=RequestMethod.GET)
	public String frontshow(@PathVariable Integer attendanceId,Model model,HttpServletRequest request) throws Exception {
		/*根据主键attendanceId获取Attendance对象*/
        Attendance attendance = attendanceService.getAttendance(attendanceId);

        List<AttendResult> attendResultList = attendResultService.queryAllAttendResult();
        request.setAttribute("attendResultList", attendResultList);
        List<Course> courseList = courseService.queryAllCourse();
        request.setAttribute("courseList", courseList);
        List<Student> studentList = studentService.queryAllStudent();
        request.setAttribute("studentList", studentList);
        request.setAttribute("attendance",  attendance);
        return "Attendance/attendance_frontshow";
	}

	/*ajax方式显示考勤修改jsp视图页*/
	@RequestMapping(value="/{attendanceId}/update",method=RequestMethod.GET)
	public void update(@PathVariable Integer attendanceId,Model model,HttpServletRequest request,HttpServletResponse response) throws Exception {
        /*根据主键attendanceId获取Attendance对象*/
        Attendance attendance = attendanceService.getAttendance(attendanceId);

        response.setContentType("text/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
		//将要被返回到客户端的对象 
		JSONObject jsonAttendance = attendance.getJsonObject();
		out.println(jsonAttendance.toString());
		out.flush();
		out.close();
	}

	/*ajax方式更新考勤信息*/
	@RequestMapping(value = "/{attendanceId}/update", method = RequestMethod.POST)
	public void update(@Validated Attendance attendance, BindingResult br,
			Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
		String message = "";
    	boolean success = false;
		if (br.hasErrors()) { 
			message = "输入的信息有错误！";
			writeJsonResponse(response, success, message);
			return;
		}
		try {
			attendanceService.updateAttendance(attendance);
			message = "考勤更新成功!";
			success = true;
			writeJsonResponse(response, success, message);
		} catch (Exception e) {
			e.printStackTrace();
			message = "考勤更新失败!";
			writeJsonResponse(response, success, message); 
		}
	}
    /*删除考勤信息*/
	@RequestMapping(value="/{attendanceId}/delete",method=RequestMethod.GET)
	public String delete(@PathVariable Integer attendanceId,HttpServletRequest request) throws UnsupportedEncodingException {
		  try {
			  attendanceService.deleteAttendance(attendanceId);
	            request.setAttribute("message", "考勤删除成功!");
	            return "message";
	        } catch (Exception e) { 
	            e.printStackTrace();
	            request.setAttribute("error", "考勤删除失败!");
				return "error";

	        }

	}

	/*ajax方式删除多条考勤记录*/
	@RequestMapping(value="/deletes",method=RequestMethod.POST)
	public void delete(String attendanceIds,HttpServletRequest request,HttpServletResponse response) throws IOException, JSONException {
		String message = "";
    	boolean success = false;
        try { 
        	int count = attendanceService.deleteAttendances(attendanceIds);
        	success = true;
        	message = count + "条记录删除成功";
        	writeJsonResponse(response, success, message);
        } catch (Exception e) { 
            //e.printStackTrace();
            message = "有记录存在外键约束,删除失败";
            writeJsonResponse(response, success, message);
        }
	}

	/*按照查询条件导出考勤信息到Excel*/
	@RequestMapping(value = { "/OutToExcel" }, method = {RequestMethod.GET,RequestMethod.POST})
	public void OutToExcel(@ModelAttribute("studentObj") Student studentObj,@ModelAttribute("courseObj") Course courseObj,String weekDay,String sectionNo,@ModelAttribute("arObj") AttendResult arObj, Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
        if(weekDay == null) weekDay = "";
        if(sectionNo == null) sectionNo = "";
        List<Attendance> attendanceList = attendanceService.queryAttendance(studentObj,courseObj,weekDay,sectionNo,arObj);
        ExportExcelUtil ex = new ExportExcelUtil();
        String _title = "Attendance信息记录"; 
        String[] headers = { "考勤id","考勤学生","考勤课程","周日期","第几节","考勤结果"};
        List<String[]> dataset = new ArrayList<String[]>(); 
        for(int i=0;i<attendanceList.size();i++) {
        	Attendance attendance = attendanceList.get(i); 
        	dataset.add(new String[]{attendance.getAttendanceId() + "",attendance.getStudentObj().getName(),attendance.getCourseObj().getCourseName(),attendance.getWeekDay(),attendance.getSectionNo(),attendance.getArObj().getArName()});
        }
        /*
        OutputStream out = null;
		try {
			out = new FileOutputStream("C://output.xls");
			ex.exportExcel(title,headers, dataset, out);
		    out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		*/
		OutputStream out = null;//创建一个输出流对象 
		try { 
			out = response.getOutputStream();//
			response.setHeader("Content-disposition","attachment; filename="+"Attendance.xls");//filename是下载的xls的名，建议最好用英文 
			response.setContentType("application/msexcel;charset=UTF-8");//设置类型 
			response.setHeader("Pragma","No-cache");//设置头 
			response.setHeader("Cache-Control","no-cache");//设置头 
			response.setDateHeader("Expires", 0);//设置日期头  
			String rootPath = request.getSession().getServletContext().getRealPath("/");
			ex.exportExcel(rootPath,_title,headers, dataset, out);
			out.flush();
		} catch (IOException e) { 
			e.printStackTrace(); 
		}finally{
			try{
				if(out!=null){ 
					out.close(); 
				}
			}catch(IOException e){ 
				e.printStackTrace(); 
			} 
		}
    }
}
