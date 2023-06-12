package com.chengxusheji.controller;

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
import com.chengxusheji.service.LeaveInfoService;
import com.chengxusheji.po.LeaveInfo;
import com.chengxusheji.po.Teacher;
import com.chengxusheji.service.StudentService;
import com.chengxusheji.po.Student;

//LeaveInfo管理控制层
@Controller
@RequestMapping("/LeaveInfo")
public class LeaveInfoController extends BaseController {

    /*业务层对象*/
    @Resource LeaveInfoService leaveInfoService;

    @Resource StudentService studentService;
	@InitBinder("studentObj")
	public void initBinderstudentObj(WebDataBinder binder) {
		binder.setFieldDefaultPrefix("studentObj.");
	}
	@InitBinder("leaveInfo")
	public void initBinderLeaveInfo(WebDataBinder binder) {
		binder.setFieldDefaultPrefix("leaveInfo.");
	}
	/*跳转到添加LeaveInfo视图*/
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(Model model,HttpServletRequest request) throws Exception {
		model.addAttribute(new LeaveInfo());
		/*查询所有的Student信息*/
		List<Student> studentList = studentService.queryAllStudent();
		request.setAttribute("studentList", studentList);
		return "LeaveInfo_add";
	}

	/*客户端ajax方式提交添加请假信息*/
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public void add(@Validated LeaveInfo leaveInfo, BindingResult br,
			Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
		String message = "";
		boolean success = false;
		if (br.hasErrors()) {
			message = "输入信息不符合要求！";
			writeJsonResponse(response, success, message);
			return ;
		}
        leaveInfoService.addLeaveInfo(leaveInfo);
        message = "请假添加成功!";
        success = true;
        writeJsonResponse(response, success, message);
	}
	
	
	/*客户端ajax方式提交添加请假信息*/
	@RequestMapping(value = "/stuAdd", method = RequestMethod.POST)
	public void stuAdd(LeaveInfo leaveInfo, BindingResult br,
			Model model, HttpServletRequest request,HttpServletResponse response,HttpSession session) throws Exception {
		String message = "";
		boolean success = false;
		if (br.hasErrors()) {
			message = "输入信息不符合要求！";
			writeJsonResponse(response, success, message);
			return ;
		}
		leaveInfo.setShhf("--");
		leaveInfo.setShsj("--");
		leaveInfo.setShzt("待审核");
		leaveInfo.setTeacherNo("--");
		Student student = new Student();
		student.setStudentNo(session.getAttribute("user_name").toString());
		leaveInfo.setStudentObj(student);
        leaveInfoService.addLeaveInfo(leaveInfo);
        message = "请假添加成功!";
        success = true;
        writeJsonResponse(response, success, message);
	}
	
	
	/*ajax方式按照查询条件分页查询请假信息*/
	@RequestMapping(value = { "/list" }, method = {RequestMethod.GET,RequestMethod.POST})
	public void list(String reason,@ModelAttribute("studentObj") Student studentObj,String leaveAddTime,String shzt,String teacherNo,Integer page,Integer rows, Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
		if (page==null || page == 0) page = 1;
		if (reason == null) reason = "";
		if (leaveAddTime == null) leaveAddTime = "";
		if (shzt == null) shzt = "";
		if (teacherNo == null) teacherNo = "";
		if(rows != 0)leaveInfoService.setRows(rows);
		List<LeaveInfo> leaveInfoList = leaveInfoService.queryLeaveInfo(reason, studentObj, leaveAddTime, shzt, teacherNo, page);
	    /*计算总的页数和总的记录数*/
	    leaveInfoService.queryTotalPageAndRecordNumber(reason, studentObj, leaveAddTime, shzt, teacherNo);
	    /*获取到总的页码数目*/
	    int totalPage = leaveInfoService.getTotalPage();
	    /*当前查询条件下总记录数*/
	    int recordNumber = leaveInfoService.getRecordNumber();
        response.setContentType("text/json;charset=UTF-8");
		PrintWriter out = response.getWriter();
		//将要被返回到客户端的对象
		JSONObject jsonObj=new JSONObject();
		jsonObj.accumulate("total", recordNumber);
		JSONArray jsonArray = new JSONArray();
		for(LeaveInfo leaveInfo:leaveInfoList) {
			JSONObject jsonLeaveInfo = leaveInfo.getJsonObject();
			jsonArray.put(jsonLeaveInfo);
		}
		jsonObj.accumulate("rows", jsonArray);
		out.println(jsonObj.toString());
		out.flush();
		out.close();
	}

	/*ajax方式按照查询条件分页查询请假信息*/
	@RequestMapping(value = { "/listAll" }, method = {RequestMethod.GET,RequestMethod.POST})
	public void listAll(HttpServletResponse response) throws Exception {
		List<LeaveInfo> leaveInfoList = leaveInfoService.queryAllLeaveInfo();
        response.setContentType("text/json;charset=UTF-8"); 
		PrintWriter out = response.getWriter();
		JSONArray jsonArray = new JSONArray();
		for(LeaveInfo leaveInfo:leaveInfoList) {
			JSONObject jsonLeaveInfo = new JSONObject();
			jsonLeaveInfo.accumulate("leaveId", leaveInfo.getLeaveId());
			jsonLeaveInfo.accumulate("reason", leaveInfo.getReason());
			jsonArray.put(jsonLeaveInfo);
		}
		out.println(jsonArray.toString());
		out.flush();
		out.close();
	}

	/*前台按照查询条件分页查询请假信息*/
	@RequestMapping(value = { "/frontlist" }, method = {RequestMethod.GET,RequestMethod.POST})
	public String frontlist(String reason,@ModelAttribute("studentObj") Student studentObj,String leaveAddTime,String shzt,String teacherNo,Integer currentPage, Model model, HttpServletRequest request) throws Exception  {
		if (currentPage==null || currentPage == 0) currentPage = 1;
		if (reason == null) reason = "";
		if (leaveAddTime == null) leaveAddTime = "";
		if (shzt == null) shzt = "";
		if (teacherNo == null) teacherNo = "";
		List<LeaveInfo> leaveInfoList = leaveInfoService.queryLeaveInfo(reason, studentObj, leaveAddTime, shzt, teacherNo, currentPage);
	    /*计算总的页数和总的记录数*/
	    leaveInfoService.queryTotalPageAndRecordNumber(reason, studentObj, leaveAddTime, shzt, teacherNo);
	    /*获取到总的页码数目*/
	    int totalPage = leaveInfoService.getTotalPage();
	    /*当前查询条件下总记录数*/
	    int recordNumber = leaveInfoService.getRecordNumber();
	    request.setAttribute("leaveInfoList",  leaveInfoList);
	    request.setAttribute("totalPage", totalPage);
	    request.setAttribute("recordNumber", recordNumber);
	    request.setAttribute("currentPage", currentPage);
	    request.setAttribute("reason", reason);
	    request.setAttribute("studentObj", studentObj);
	    request.setAttribute("leaveAddTime", leaveAddTime);
	    request.setAttribute("shzt", shzt);
	    request.setAttribute("teacherNo", teacherNo);
	    List<Student> studentList = studentService.queryAllStudent();
	    request.setAttribute("studentList", studentList);
		return "LeaveInfo/leaveInfo_frontquery_result"; 
	}
	
	/*学生前台按照查询条件分页查询请假信息*/
	@RequestMapping(value = { "/stuFrontlist" }, method = {RequestMethod.GET,RequestMethod.POST})
	public String stuFrontlist(String reason,@ModelAttribute("studentObj") Student studentObj,String leaveAddTime,String shzt,String teacherNo,Integer currentPage, Model model, HttpServletRequest request,HttpSession session) throws Exception  {
		if (currentPage==null || currentPage == 0) currentPage = 1;
		if (reason == null) reason = "";
		if (leaveAddTime == null) leaveAddTime = "";
		if (shzt == null) shzt = "";
		if (teacherNo == null) teacherNo = "";
		studentObj = new Student();
		studentObj.setStudentNo(session.getAttribute("user_name").toString());
		
		List<LeaveInfo> leaveInfoList = leaveInfoService.queryLeaveInfo(reason, studentObj, leaveAddTime, shzt, teacherNo, currentPage);
	    /*计算总的页数和总的记录数*/
	    leaveInfoService.queryTotalPageAndRecordNumber(reason, studentObj, leaveAddTime, shzt, teacherNo);
	    /*获取到总的页码数目*/
	    int totalPage = leaveInfoService.getTotalPage();
	    /*当前查询条件下总记录数*/
	    int recordNumber = leaveInfoService.getRecordNumber();
	    request.setAttribute("leaveInfoList",  leaveInfoList);
	    request.setAttribute("totalPage", totalPage);
	    request.setAttribute("recordNumber", recordNumber);
	    request.setAttribute("currentPage", currentPage);
	    request.setAttribute("reason", reason);
	    request.setAttribute("studentObj", studentObj);
	    request.setAttribute("leaveAddTime", leaveAddTime);
	    request.setAttribute("shzt", shzt);
	    request.setAttribute("teacherNo", teacherNo);
	    List<Student> studentList = studentService.queryAllStudent();
	    request.setAttribute("studentList", studentList);
		return "LeaveInfo/leaveInfo_stuFrontquery_result"; 
	}
	

     /*前台查询LeaveInfo信息*/
	@RequestMapping(value="/{leaveId}/frontshow",method=RequestMethod.GET)
	public String frontshow(@PathVariable Integer leaveId,Model model,HttpServletRequest request) throws Exception {
		/*根据主键leaveId获取LeaveInfo对象*/
        LeaveInfo leaveInfo = leaveInfoService.getLeaveInfo(leaveId);

        List<Student> studentList = studentService.queryAllStudent();
        request.setAttribute("studentList", studentList);
        request.setAttribute("leaveInfo",  leaveInfo);
        return "LeaveInfo/leaveInfo_frontshow";
	}

	/*ajax方式显示请假修改jsp视图页*/
	@RequestMapping(value="/{leaveId}/update",method=RequestMethod.GET)
	public void update(@PathVariable Integer leaveId,Model model,HttpServletRequest request,HttpServletResponse response) throws Exception {
        /*根据主键leaveId获取LeaveInfo对象*/
        LeaveInfo leaveInfo = leaveInfoService.getLeaveInfo(leaveId);

        response.setContentType("text/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
		//将要被返回到客户端的对象 
		JSONObject jsonLeaveInfo = leaveInfo.getJsonObject();
		out.println(jsonLeaveInfo.toString());
		out.flush();
		out.close();
	}

	/*ajax方式更新请假信息*/
	@RequestMapping(value = "/{leaveId}/update", method = RequestMethod.POST)
	public void update(@Validated LeaveInfo leaveInfo, BindingResult br,
			Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
		String message = "";
    	boolean success = false;
		if (br.hasErrors()) { 
			message = "输入的信息有错误！";
			writeJsonResponse(response, success, message);
			return;
		}
		try {
			leaveInfoService.updateLeaveInfo(leaveInfo);
			message = "请假更新成功!";
			success = true;
			writeJsonResponse(response, success, message);
		} catch (Exception e) {
			e.printStackTrace();
			message = "请假更新失败!";
			writeJsonResponse(response, success, message); 
		}
	}
	
	/*老师ajax方式更新请假信息*/
	@RequestMapping(value = "/{leaveId}/teacherUpdate", method = RequestMethod.POST)
	public void teacherUpdate(LeaveInfo leaveInfo, BindingResult br,
			Model model, HttpServletRequest request,HttpServletResponse response,HttpSession session) throws Exception {
		String message = "";
    	boolean success = false;
		 
		try { 
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			leaveInfo.setShsj(sdf.format(new java.util.Date()));
			leaveInfo.setTeacherNo(session.getAttribute("teacherNo").toString());
			
			leaveInfoService.updateLeaveInfo(leaveInfo);
			message = "请假审核成功!";
			success = true;
			writeJsonResponse(response, success, message);
		} catch (Exception e) {
			e.printStackTrace();
			message = "请假更新失败!";
			writeJsonResponse(response, success, message); 
		}
	}
	
	
    /*删除请假信息*/
	@RequestMapping(value="/{leaveId}/delete",method=RequestMethod.GET)
	public String delete(@PathVariable Integer leaveId,HttpServletRequest request) throws UnsupportedEncodingException {
		  try {
			  leaveInfoService.deleteLeaveInfo(leaveId);
	            request.setAttribute("message", "请假删除成功!");
	            return "message";
	        } catch (Exception e) { 
	            e.printStackTrace();
	            request.setAttribute("error", "请假删除失败!");
				return "error";

	        }

	}

	/*ajax方式删除多条请假记录*/
	@RequestMapping(value="/deletes",method=RequestMethod.POST)
	public void delete(String leaveIds,HttpServletRequest request,HttpServletResponse response) throws IOException, JSONException {
		String message = "";
    	boolean success = false;
        try { 
        	int count = leaveInfoService.deleteLeaveInfos(leaveIds);
        	success = true;
        	message = count + "条记录删除成功";
        	writeJsonResponse(response, success, message);
        } catch (Exception e) { 
            //e.printStackTrace();
            message = "有记录存在外键约束,删除失败";
            writeJsonResponse(response, success, message);
        }
	}

	/*按照查询条件导出请假信息到Excel*/
	@RequestMapping(value = { "/OutToExcel" }, method = {RequestMethod.GET,RequestMethod.POST})
	public void OutToExcel(String reason,@ModelAttribute("studentObj") Student studentObj,String leaveAddTime,String shzt,String teacherNo, Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
        if(reason == null) reason = "";
        if(leaveAddTime == null) leaveAddTime = "";
        if(shzt == null) shzt = "";
        if(teacherNo == null) teacherNo = "";
        List<LeaveInfo> leaveInfoList = leaveInfoService.queryLeaveInfo(reason,studentObj,leaveAddTime,shzt,teacherNo);
        ExportExcelUtil ex = new ExportExcelUtil();
        String _title = "LeaveInfo信息记录"; 
        String[] headers = { "请假id","请假原因","请假多久","请假学生","请假时间","审核状态","审核回复","审核的老师"};
        List<String[]> dataset = new ArrayList<String[]>(); 
        for(int i=0;i<leaveInfoList.size();i++) {
        	LeaveInfo leaveInfo = leaveInfoList.get(i); 
        	dataset.add(new String[]{leaveInfo.getLeaveId() + "",leaveInfo.getReason(),leaveInfo.getDuration(),leaveInfo.getStudentObj().getName(),leaveInfo.getLeaveAddTime(),leaveInfo.getShzt(),leaveInfo.getShhf(),leaveInfo.getTeacherNo()});
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
			response.setHeader("Content-disposition","attachment; filename="+"LeaveInfo.xls");//filename是下载的xls的名，建议最好用英文 
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
