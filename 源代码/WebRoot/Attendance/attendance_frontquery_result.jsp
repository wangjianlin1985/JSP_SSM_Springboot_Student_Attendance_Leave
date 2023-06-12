<%@ page language="java" import="java.util.*"  contentType="text/html;charset=UTF-8"%> 
<%@ page import="com.chengxusheji.po.Attendance" %>
<%@ page import="com.chengxusheji.po.AttendResult" %>
<%@ page import="com.chengxusheji.po.Course" %>
<%@ page import="com.chengxusheji.po.Student" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    List<Attendance> attendanceList = (List<Attendance>)request.getAttribute("attendanceList");
    //获取所有的arObj信息
    List<AttendResult> attendResultList = (List<AttendResult>)request.getAttribute("attendResultList");
    //获取所有的courseObj信息
    List<Course> courseList = (List<Course>)request.getAttribute("courseList");
    //获取所有的studentObj信息
    List<Student> studentList = (List<Student>)request.getAttribute("studentList");
    int currentPage =  (Integer)request.getAttribute("currentPage"); //当前页
    int totalPage =   (Integer)request.getAttribute("totalPage");  //一共多少页
    int recordNumber =   (Integer)request.getAttribute("recordNumber");  //一共多少记录
    Student studentObj = (Student)request.getAttribute("studentObj");
    Course courseObj = (Course)request.getAttribute("courseObj");
    String weekDay = (String)request.getAttribute("weekDay"); //周日期查询关键字
    String sectionNo = (String)request.getAttribute("sectionNo"); //第几节查询关键字
    AttendResult arObj = (AttendResult)request.getAttribute("arObj");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1 , user-scalable=no">
<title>考勤查询</title>
<link href="<%=basePath %>plugins/bootstrap.css" rel="stylesheet">
<link href="<%=basePath %>plugins/bootstrap-dashen.css" rel="stylesheet">
<link href="<%=basePath %>plugins/font-awesome.css" rel="stylesheet">
<link href="<%=basePath %>plugins/animate.css" rel="stylesheet">
<link href="<%=basePath %>plugins/bootstrap-datetimepicker.min.css" rel="stylesheet" media="screen">
</head>
<body style="margin-top:70px;">
<div class="container">
<jsp:include page="../header.jsp"></jsp:include>
	<div class="row"> 
		<div class="col-md-9 wow fadeInDown" data-wow-duration="0.5s">
			<div>
				<!-- Nav tabs -->
				<ul class="nav nav-tabs" role="tablist">
			    	<li><a href="<%=basePath %>index.jsp">首页</a></li>
			    	<li role="presentation" class="active"><a href="#attendanceListPanel" aria-controls="attendanceListPanel" role="tab" data-toggle="tab">考勤列表</a></li>
			    	<li role="presentation" ><a href="<%=basePath %>Attendance/attendance_frontAdd.jsp" style="display:none;">添加考勤</a></li>
				</ul>
			  	<!-- Tab panes -->
			  	<div class="tab-content">
				    <div role="tabpanel" class="tab-pane active" id="attendanceListPanel">
				    		<div class="row">
				    			<div class="col-md-12 top5">
				    				<div class="table-responsive">
				    				<table class="table table-condensed table-hover">
				    					<tr class="success bold"><td>序号</td><td>考勤id</td><td>考勤学生</td><td>考勤课程</td><td>周日期</td><td>第几节</td><td>考勤结果</td><td>操作</td></tr>
				    					<% 
				    						/*计算起始序号*/
				    	            		int startIndex = (currentPage -1) * 5;
				    	            		/*遍历记录*/
				    	            		for(int i=0;i<attendanceList.size();i++) {
					    	            		int currentIndex = startIndex + i + 1; //当前记录的序号
					    	            		Attendance attendance = attendanceList.get(i); //获取到考勤对象
 										%>
 										<tr>
 											<td><%=currentIndex %></td>
 											<td><%=attendance.getAttendanceId() %></td>
 											<td><%=attendance.getStudentObj().getName() %></td>
 											<td><%=attendance.getCourseObj().getCourseName() %></td>
 											<td><%=attendance.getWeekDay() %></td>
 											<td><%=attendance.getSectionNo() %></td>
 											<td><%=attendance.getArObj().getArName() %></td>
 											<td>
 												<a href="<%=basePath  %>Attendance/<%=attendance.getAttendanceId() %>/frontshow"><i class="fa fa-info"></i>&nbsp;查看</a>&nbsp;
 												<a href="#" onclick="attendanceEdit('<%=attendance.getAttendanceId() %>');" style="display:none;"><i class="fa fa-pencil fa-fw"></i>编辑</a>&nbsp;
 												<a href="#" onclick="attendanceDelete('<%=attendance.getAttendanceId() %>');" style="display:none;"><i class="fa fa-trash-o fa-fw"></i>删除</a>
 											</td> 
 										</tr>
 										<%}%>
				    				</table>
				    				</div>
				    			</div>
				    		</div>

				    		<div class="row">
					            <div class="col-md-12">
						            <nav class="pull-left">
						                <ul class="pagination">
						                    <li><a href="#" onclick="GoToPage(<%=currentPage-1 %>,<%=totalPage %>);" aria-label="Previous"><span aria-hidden="true">&laquo;</span></a></li>
						                     <%
						                    	int startPage = currentPage - 5;
						                    	int endPage = currentPage + 5;
						                    	if(startPage < 1) startPage=1;
						                    	if(endPage > totalPage) endPage = totalPage;
						                    	for(int i=startPage;i<=endPage;i++) {
						                    %>
						                    <li class="<%= currentPage==i?"active":"" %>"><a href="#"  onclick="GoToPage(<%=i %>,<%=totalPage %>);"><%=i %></a></li>
						                    <%  } %> 
						                    <li><a href="#" onclick="GoToPage(<%=currentPage+1 %>,<%=totalPage %>);"><span aria-hidden="true">&raquo;</span></a></li>
						                </ul>
						            </nav>
						            <div class="pull-right" style="line-height:75px;" >共有<%=recordNumber %>条记录，当前第 <%=currentPage %>/<%=totalPage %> 页</div>
					            </div>
				            </div> 
				    </div>
				</div>
			</div>
		</div>
	<div class="col-md-3 wow fadeInRight">
		<div class="page-header">
    		<h1>考勤查询</h1>
		</div>
		<form name="attendanceQueryForm" id="attendanceQueryForm" action="<%=basePath %>Attendance/frontlist" class="mar_t15">
            <div class="form-group">
            	<label for="studentObj_studentNo">考勤学生：</label>
                <select id="studentObj_studentNo" name="studentObj.studentNo" class="form-control">
                	<option value="">不限制</option>
	 				<%
	 				for(Student studentTemp:studentList) {
	 					String selected = "";
 					if(studentObj!=null && studentObj.getStudentNo()!=null && studentObj.getStudentNo().equals(studentTemp.getStudentNo()))
 						selected = "selected";
	 				%>
 				 <option value="<%=studentTemp.getStudentNo() %>" <%=selected %>><%=studentTemp.getName() %></option>
	 				<%
	 				}
	 				%>
 			</select>
            </div>
            <div class="form-group">
            	<label for="courseObj_courseNo">考勤课程：</label>
                <select id="courseObj_courseNo" name="courseObj.courseNo" class="form-control">
                	<option value="">不限制</option>
	 				<%
	 				for(Course courseTemp:courseList) {
	 					String selected = "";
 					if(courseObj!=null && courseObj.getCourseNo()!=null && courseObj.getCourseNo().equals(courseTemp.getCourseNo()))
 						selected = "selected";
	 				%>
 				 <option value="<%=courseTemp.getCourseNo() %>" <%=selected %>><%=courseTemp.getCourseName() %></option>
	 				<%
	 				}
	 				%>
 			</select>
            </div>
			<div class="form-group">
				<label for="weekDay">周日期:</label>
				<input type="text" id="weekDay" name="weekDay" value="<%=weekDay %>" class="form-control" placeholder="请输入周日期">
			</div>






			<div class="form-group">
				<label for="sectionNo">第几节:</label>
				<input type="text" id="sectionNo" name="sectionNo" value="<%=sectionNo %>" class="form-control" placeholder="请输入第几节">
			</div>






            <div class="form-group">
            	<label for="arObj_arId">考勤结果：</label>
                <select id="arObj_arId" name="arObj.arId" class="form-control">
                	<option value="0">不限制</option>
	 				<%
	 				for(AttendResult attendResultTemp:attendResultList) {
	 					String selected = "";
 					if(arObj!=null && arObj.getArId()!=null && arObj.getArId().intValue()==attendResultTemp.getArId().intValue())
 						selected = "selected";
	 				%>
 				 <option value="<%=attendResultTemp.getArId() %>" <%=selected %>><%=attendResultTemp.getArName() %></option>
	 				<%
	 				}
	 				%>
 			</select>
            </div>
            <input type=hidden name=currentPage value="<%=currentPage %>" />
            <button type="submit" class="btn btn-primary">查询</button>
        </form>
	</div>

		</div>
	</div> 
<div id="attendanceEditDialog" class="modal fade" tabindex="-1" role="dialog">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title"><i class="fa fa-edit"></i>&nbsp;考勤信息编辑</h4>
      </div>
      <div class="modal-body" style="height:450px; overflow: scroll;">
      	<form class="form-horizontal" name="attendanceEditForm" id="attendanceEditForm" enctype="multipart/form-data" method="post"  class="mar_t15">
		  <div class="form-group">
			 <label for="attendance_attendanceId_edit" class="col-md-3 text-right">考勤id:</label>
			 <div class="col-md-9"> 
			 	<input type="text" id="attendance_attendanceId_edit" name="attendance.attendanceId" class="form-control" placeholder="请输入考勤id" readOnly>
			 </div>
		  </div> 
		  <div class="form-group">
		  	 <label for="attendance_studentObj_studentNo_edit" class="col-md-3 text-right">考勤学生:</label>
		  	 <div class="col-md-9">
			    <select id="attendance_studentObj_studentNo_edit" name="attendance.studentObj.studentNo" class="form-control">
			    </select>
		  	 </div>
		  </div>
		  <div class="form-group">
		  	 <label for="attendance_courseObj_courseNo_edit" class="col-md-3 text-right">考勤课程:</label>
		  	 <div class="col-md-9">
			    <select id="attendance_courseObj_courseNo_edit" name="attendance.courseObj.courseNo" class="form-control">
			    </select>
		  	 </div>
		  </div>
		  <div class="form-group">
		  	 <label for="attendance_weekDay_edit" class="col-md-3 text-right">周日期:</label>
		  	 <div class="col-md-9">
			    <input type="text" id="attendance_weekDay_edit" name="attendance.weekDay" class="form-control" placeholder="请输入周日期">
			 </div>
		  </div>
		  <div class="form-group">
		  	 <label for="attendance_sectionNo_edit" class="col-md-3 text-right">第几节:</label>
		  	 <div class="col-md-9">
			    <input type="text" id="attendance_sectionNo_edit" name="attendance.sectionNo" class="form-control" placeholder="请输入第几节">
			 </div>
		  </div>
		  <div class="form-group">
		  	 <label for="attendance_arObj_arId_edit" class="col-md-3 text-right">考勤结果:</label>
		  	 <div class="col-md-9">
			    <select id="attendance_arObj_arId_edit" name="attendance.arObj.arId" class="form-control">
			    </select>
		  	 </div>
		  </div>
		  <div class="form-group">
		  	 <label for="attendance_attendMemo_edit" class="col-md-3 text-right">备注信息:</label>
		  	 <div class="col-md-9">
			    <textarea id="attendance_attendMemo_edit" name="attendance.attendMemo" rows="8" class="form-control" placeholder="请输入备注信息"></textarea>
			 </div>
		  </div>
		</form> 
	    <style>#attendanceEditForm .form-group {margin-bottom:5px;}  </style>
      </div>
      <div class="modal-footer"> 
      	<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
      	<button type="button" class="btn btn-primary" onclick="ajaxAttendanceModify();">提交</button>
      </div>
    </div><!-- /.modal-content -->
  </div><!-- /.modal-dialog -->
</div><!-- /.modal -->
<jsp:include page="../footer.jsp"></jsp:include> 
<script src="<%=basePath %>plugins/jquery.min.js"></script>
<script src="<%=basePath %>plugins/bootstrap.js"></script>
<script src="<%=basePath %>plugins/wow.min.js"></script>
<script src="<%=basePath %>plugins/bootstrap-datetimepicker.min.js"></script>
<script src="<%=basePath %>plugins/locales/bootstrap-datetimepicker.zh-CN.js"></script>
<script type="text/javascript" src="<%=basePath %>js/jsdate.js"></script>
<script>
var basePath = "<%=basePath%>";
/*跳转到查询结果的某页*/
function GoToPage(currentPage,totalPage) {
    if(currentPage==0) return;
    if(currentPage>totalPage) return;
    document.attendanceQueryForm.currentPage.value = currentPage;
    document.attendanceQueryForm.submit();
}

/*可以直接跳转到某页*/
function changepage(totalPage)
{
    var pageValue=document.attendanceQueryForm.pageValue.value;
    if(pageValue>totalPage) {
        alert('你输入的页码超出了总页数!');
        return ;
    }
    document.attendanceQueryForm.currentPage.value = pageValue;
    documentattendanceQueryForm.submit();
}

/*弹出修改考勤界面并初始化数据*/
function attendanceEdit(attendanceId) {
	$.ajax({
		url :  basePath + "Attendance/" + attendanceId + "/update",
		type : "get",
		dataType: "json",
		success : function (attendance, response, status) {
			if (attendance) {
				$("#attendance_attendanceId_edit").val(attendance.attendanceId);
				$.ajax({
					url: basePath + "Student/listAll",
					type: "get",
					success: function(students,response,status) { 
						$("#attendance_studentObj_studentNo_edit").empty();
						var html="";
		        		$(students).each(function(i,student){
		        			html += "<option value='" + student.studentNo + "'>" + student.name + "</option>";
		        		});
		        		$("#attendance_studentObj_studentNo_edit").html(html);
		        		$("#attendance_studentObj_studentNo_edit").val(attendance.studentObjPri);
					}
				});
				$.ajax({
					url: basePath + "Course/listAll",
					type: "get",
					success: function(courses,response,status) { 
						$("#attendance_courseObj_courseNo_edit").empty();
						var html="";
		        		$(courses).each(function(i,course){
		        			html += "<option value='" + course.courseNo + "'>" + course.courseName + "</option>";
		        		});
		        		$("#attendance_courseObj_courseNo_edit").html(html);
		        		$("#attendance_courseObj_courseNo_edit").val(attendance.courseObjPri);
					}
				});
				$("#attendance_weekDay_edit").val(attendance.weekDay);
				$("#attendance_sectionNo_edit").val(attendance.sectionNo);
				$.ajax({
					url: basePath + "AttendResult/listAll",
					type: "get",
					success: function(attendResults,response,status) { 
						$("#attendance_arObj_arId_edit").empty();
						var html="";
		        		$(attendResults).each(function(i,attendResult){
		        			html += "<option value='" + attendResult.arId + "'>" + attendResult.arName + "</option>";
		        		});
		        		$("#attendance_arObj_arId_edit").html(html);
		        		$("#attendance_arObj_arId_edit").val(attendance.arObjPri);
					}
				});
				$("#attendance_attendMemo_edit").val(attendance.attendMemo);
				$('#attendanceEditDialog').modal('show');
			} else {
				alert("获取信息失败！");
			}
		}
	});
}

/*删除考勤信息*/
function attendanceDelete(attendanceId) {
	if(confirm("确认删除这个记录")) {
		$.ajax({
			type : "POST",
			url : basePath + "Attendance/deletes",
			data : {
				attendanceIds : attendanceId,
			},
			success : function (obj) {
				if (obj.success) {
					alert("删除成功");
					$("#attendanceQueryForm").submit();
					//location.href= basePath + "Attendance/frontlist";
				}
				else 
					alert(obj.message);
			},
		});
	}
}

/*ajax方式提交考勤信息表单给服务器端修改*/
function ajaxAttendanceModify() {
	$.ajax({
		url :  basePath + "Attendance/" + $("#attendance_attendanceId_edit").val() + "/update",
		type : "post",
		dataType: "json",
		data: new FormData($("#attendanceEditForm")[0]),
		success : function (obj, response, status) {
            if(obj.success){
                alert("信息修改成功！");
                $("#attendanceQueryForm").submit();
            }else{
                alert(obj.message);
            } 
		},
		processData: false,
		contentType: false,
	});
}

$(function(){
	/*小屏幕导航点击关闭菜单*/
    $('.navbar-collapse a').click(function(){
        $('.navbar-collapse').collapse('hide');
    });
    new WOW().init();

})
</script>
</body>
</html>

