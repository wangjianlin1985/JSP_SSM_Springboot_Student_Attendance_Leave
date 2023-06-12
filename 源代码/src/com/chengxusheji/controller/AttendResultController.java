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
import com.chengxusheji.service.AttendResultService;
import com.chengxusheji.po.AttendResult;

//AttendResult管理控制层
@Controller
@RequestMapping("/AttendResult")
public class AttendResultController extends BaseController {

    /*业务层对象*/
    @Resource AttendResultService attendResultService;

	@InitBinder("attendResult")
	public void initBinderAttendResult(WebDataBinder binder) {
		binder.setFieldDefaultPrefix("attendResult.");
	}
	/*跳转到添加AttendResult视图*/
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(Model model,HttpServletRequest request) throws Exception {
		model.addAttribute(new AttendResult());
		return "AttendResult_add";
	}

	/*客户端ajax方式提交添加考勤结果信息*/
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public void add(@Validated AttendResult attendResult, BindingResult br,
			Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
		String message = "";
		boolean success = false;
		if (br.hasErrors()) {
			message = "输入信息不符合要求！";
			writeJsonResponse(response, success, message);
			return ;
		}
        attendResultService.addAttendResult(attendResult);
        message = "考勤结果添加成功!";
        success = true;
        writeJsonResponse(response, success, message);
	}
	/*ajax方式按照查询条件分页查询考勤结果信息*/
	@RequestMapping(value = { "/list" }, method = {RequestMethod.GET,RequestMethod.POST})
	public void list(Integer page,Integer rows, Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
		if (page==null || page == 0) page = 1;
		if(rows != 0)attendResultService.setRows(rows);
		List<AttendResult> attendResultList = attendResultService.queryAttendResult(page);
	    /*计算总的页数和总的记录数*/
	    attendResultService.queryTotalPageAndRecordNumber();
	    /*获取到总的页码数目*/
	    int totalPage = attendResultService.getTotalPage();
	    /*当前查询条件下总记录数*/
	    int recordNumber = attendResultService.getRecordNumber();
        response.setContentType("text/json;charset=UTF-8");
		PrintWriter out = response.getWriter();
		//将要被返回到客户端的对象
		JSONObject jsonObj=new JSONObject();
		jsonObj.accumulate("total", recordNumber);
		JSONArray jsonArray = new JSONArray();
		for(AttendResult attendResult:attendResultList) {
			JSONObject jsonAttendResult = attendResult.getJsonObject();
			jsonArray.put(jsonAttendResult);
		}
		jsonObj.accumulate("rows", jsonArray);
		out.println(jsonObj.toString());
		out.flush();
		out.close();
	}

	/*ajax方式按照查询条件分页查询考勤结果信息*/
	@RequestMapping(value = { "/listAll" }, method = {RequestMethod.GET,RequestMethod.POST})
	public void listAll(HttpServletResponse response) throws Exception {
		List<AttendResult> attendResultList = attendResultService.queryAllAttendResult();
        response.setContentType("text/json;charset=UTF-8"); 
		PrintWriter out = response.getWriter();
		JSONArray jsonArray = new JSONArray();
		for(AttendResult attendResult:attendResultList) {
			JSONObject jsonAttendResult = new JSONObject();
			jsonAttendResult.accumulate("arId", attendResult.getArId());
			jsonAttendResult.accumulate("arName", attendResult.getArName());
			jsonArray.put(jsonAttendResult);
		}
		out.println(jsonArray.toString());
		out.flush();
		out.close();
	}

	/*前台按照查询条件分页查询考勤结果信息*/
	@RequestMapping(value = { "/frontlist" }, method = {RequestMethod.GET,RequestMethod.POST})
	public String frontlist(Integer currentPage, Model model, HttpServletRequest request) throws Exception  {
		if (currentPage==null || currentPage == 0) currentPage = 1;
		List<AttendResult> attendResultList = attendResultService.queryAttendResult(currentPage);
	    /*计算总的页数和总的记录数*/
	    attendResultService.queryTotalPageAndRecordNumber();
	    /*获取到总的页码数目*/
	    int totalPage = attendResultService.getTotalPage();
	    /*当前查询条件下总记录数*/
	    int recordNumber = attendResultService.getRecordNumber();
	    request.setAttribute("attendResultList",  attendResultList);
	    request.setAttribute("totalPage", totalPage);
	    request.setAttribute("recordNumber", recordNumber);
	    request.setAttribute("currentPage", currentPage);
		return "AttendResult/attendResult_frontquery_result"; 
	}

     /*前台查询AttendResult信息*/
	@RequestMapping(value="/{arId}/frontshow",method=RequestMethod.GET)
	public String frontshow(@PathVariable Integer arId,Model model,HttpServletRequest request) throws Exception {
		/*根据主键arId获取AttendResult对象*/
        AttendResult attendResult = attendResultService.getAttendResult(arId);

        request.setAttribute("attendResult",  attendResult);
        return "AttendResult/attendResult_frontshow";
	}

	/*ajax方式显示考勤结果修改jsp视图页*/
	@RequestMapping(value="/{arId}/update",method=RequestMethod.GET)
	public void update(@PathVariable Integer arId,Model model,HttpServletRequest request,HttpServletResponse response) throws Exception {
        /*根据主键arId获取AttendResult对象*/
        AttendResult attendResult = attendResultService.getAttendResult(arId);

        response.setContentType("text/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
		//将要被返回到客户端的对象 
		JSONObject jsonAttendResult = attendResult.getJsonObject();
		out.println(jsonAttendResult.toString());
		out.flush();
		out.close();
	}

	/*ajax方式更新考勤结果信息*/
	@RequestMapping(value = "/{arId}/update", method = RequestMethod.POST)
	public void update(@Validated AttendResult attendResult, BindingResult br,
			Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
		String message = "";
    	boolean success = false;
		if (br.hasErrors()) { 
			message = "输入的信息有错误！";
			writeJsonResponse(response, success, message);
			return;
		}
		try {
			attendResultService.updateAttendResult(attendResult);
			message = "考勤结果更新成功!";
			success = true;
			writeJsonResponse(response, success, message);
		} catch (Exception e) {
			e.printStackTrace();
			message = "考勤结果更新失败!";
			writeJsonResponse(response, success, message); 
		}
	}
    /*删除考勤结果信息*/
	@RequestMapping(value="/{arId}/delete",method=RequestMethod.GET)
	public String delete(@PathVariable Integer arId,HttpServletRequest request) throws UnsupportedEncodingException {
		  try {
			  attendResultService.deleteAttendResult(arId);
	            request.setAttribute("message", "考勤结果删除成功!");
	            return "message";
	        } catch (Exception e) { 
	            e.printStackTrace();
	            request.setAttribute("error", "考勤结果删除失败!");
				return "error";

	        }

	}

	/*ajax方式删除多条考勤结果记录*/
	@RequestMapping(value="/deletes",method=RequestMethod.POST)
	public void delete(String arIds,HttpServletRequest request,HttpServletResponse response) throws IOException, JSONException {
		String message = "";
    	boolean success = false;
        try { 
        	int count = attendResultService.deleteAttendResults(arIds);
        	success = true;
        	message = count + "条记录删除成功";
        	writeJsonResponse(response, success, message);
        } catch (Exception e) { 
            //e.printStackTrace();
            message = "有记录存在外键约束,删除失败";
            writeJsonResponse(response, success, message);
        }
	}

	/*按照查询条件导出考勤结果信息到Excel*/
	@RequestMapping(value = { "/OutToExcel" }, method = {RequestMethod.GET,RequestMethod.POST})
	public void OutToExcel( Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
        List<AttendResult> attendResultList = attendResultService.queryAttendResult();
        ExportExcelUtil ex = new ExportExcelUtil();
        String _title = "AttendResult信息记录"; 
        String[] headers = { "考勤结果id","考勤结果名称"};
        List<String[]> dataset = new ArrayList<String[]>(); 
        for(int i=0;i<attendResultList.size();i++) {
        	AttendResult attendResult = attendResultList.get(i); 
        	dataset.add(new String[]{attendResult.getArId() + "",attendResult.getArName()});
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
			response.setHeader("Content-disposition","attachment; filename="+"AttendResult.xls");//filename是下载的xls的名，建议最好用英文 
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
