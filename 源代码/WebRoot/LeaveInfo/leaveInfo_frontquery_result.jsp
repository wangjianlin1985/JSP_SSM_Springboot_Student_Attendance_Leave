<%@ page language="java" import="java.util.*"  contentType="text/html;charset=UTF-8"%> 
<%@ page import="com.chengxusheji.po.LeaveInfo" %>
<%@ page import="com.chengxusheji.po.Student" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    List<LeaveInfo> leaveInfoList = (List<LeaveInfo>)request.getAttribute("leaveInfoList");
    //获取所有的studentObj信息
    List<Student> studentList = (List<Student>)request.getAttribute("studentList");
    int currentPage =  (Integer)request.getAttribute("currentPage"); //当前页
    int totalPage =   (Integer)request.getAttribute("totalPage");  //一共多少页
    int recordNumber =   (Integer)request.getAttribute("recordNumber");  //一共多少记录
    String reason = (String)request.getAttribute("reason"); //请假原因查询关键字
    Student studentObj = (Student)request.getAttribute("studentObj");
    String leaveAddTime = (String)request.getAttribute("leaveAddTime"); //请假时间查询关键字
    String shzt = (String)request.getAttribute("shzt"); //审核状态查询关键字
    String teacherNo = (String)request.getAttribute("teacherNo"); //审核的老师查询关键字
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1 , user-scalable=no">
<title>请假查询</title>
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
			    	<li role="presentation" class="active"><a href="#leaveInfoListPanel" aria-controls="leaveInfoListPanel" role="tab" data-toggle="tab">请假列表</a></li>
			    	<li role="presentation" ><a href="<%=basePath %>LeaveInfo/leaveInfo_frontAdd.jsp" style="display:none;">添加请假</a></li>
				</ul>
			  	<!-- Tab panes -->
			  	<div class="tab-content">
				    <div role="tabpanel" class="tab-pane active" id="leaveInfoListPanel">
				    		<div class="row">
				    			<div class="col-md-12 top5">
				    				<div class="table-responsive">
				    				<table class="table table-condensed table-hover">
				    					<tr class="success bold"><td>序号</td><td>请假id</td><td>请假原因</td><td>请假多久</td><td>请假学生</td><td>请假时间</td><td>审核状态</td><td>审核回复</td><td>审核的老师</td><td>操作</td></tr>
				    					<% 
				    						/*计算起始序号*/
				    	            		int startIndex = (currentPage -1) * 5;
				    	            		/*遍历记录*/
				    	            		for(int i=0;i<leaveInfoList.size();i++) {
					    	            		int currentIndex = startIndex + i + 1; //当前记录的序号
					    	            		LeaveInfo leaveInfo = leaveInfoList.get(i); //获取到请假对象
 										%>
 										<tr>
 											<td><%=currentIndex %></td>
 											<td><%=leaveInfo.getLeaveId() %></td>
 											<td><%=leaveInfo.getReason() %></td>
 											<td><%=leaveInfo.getDuration() %></td>
 											<td><%=leaveInfo.getStudentObj().getName() %></td>
 											<td><%=leaveInfo.getLeaveAddTime() %></td>
 											<td><%=leaveInfo.getShzt() %></td>
 											<td><%=leaveInfo.getShhf() %></td>
 											<td><%=leaveInfo.getTeacherNo() %></td>
 											<td>
 												<a href="<%=basePath  %>LeaveInfo/<%=leaveInfo.getLeaveId() %>/frontshow"><i class="fa fa-info"></i>&nbsp;查看</a>&nbsp;
 												<a href="#" onclick="leaveInfoEdit('<%=leaveInfo.getLeaveId() %>');" style="display:none;"><i class="fa fa-pencil fa-fw"></i>编辑</a>&nbsp;
 												<a href="#" onclick="leaveInfoDelete('<%=leaveInfo.getLeaveId() %>');" style="display:none;"><i class="fa fa-trash-o fa-fw"></i>删除</a>
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
    		<h1>请假查询</h1>
		</div>
		<form name="leaveInfoQueryForm" id="leaveInfoQueryForm" action="<%=basePath %>LeaveInfo/frontlist" class="mar_t15">
			<div class="form-group">
				<label for="reason">请假原因:</label>
				<input type="text" id="reason" name="reason" value="<%=reason %>" class="form-control" placeholder="请输入请假原因">
			</div>






            <div class="form-group">
            	<label for="studentObj_studentNo">请假学生：</label>
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
				<label for="leaveAddTime">请假时间:</label>
				<input type="text" id="leaveAddTime" name="leaveAddTime" class="form-control"  placeholder="请选择请假时间" value="<%=leaveAddTime %>" onclick="SelectDate(this,'yyyy-MM-dd')" />
			</div>
			<div class="form-group">
				<label for="shzt">审核状态:</label>
				<input type="text" id="shzt" name="shzt" value="<%=shzt %>" class="form-control" placeholder="请输入审核状态">
			</div>






			<div class="form-group">
				<label for="teacherNo">审核的老师:</label>
				<input type="text" id="teacherNo" name="teacherNo" value="<%=teacherNo %>" class="form-control" placeholder="请输入审核的老师">
			</div>






            <input type=hidden name=currentPage value="<%=currentPage %>" />
            <button type="submit" class="btn btn-primary">查询</button>
        </form>
	</div>

		</div>
	</div> 
<div id="leaveInfoEditDialog" class="modal fade" tabindex="-1" role="dialog">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title"><i class="fa fa-edit"></i>&nbsp;请假信息编辑</h4>
      </div>
      <div class="modal-body" style="height:450px; overflow: scroll;">
      	<form class="form-horizontal" name="leaveInfoEditForm" id="leaveInfoEditForm" enctype="multipart/form-data" method="post"  class="mar_t15">
		  <div class="form-group">
			 <label for="leaveInfo_leaveId_edit" class="col-md-3 text-right">请假id:</label>
			 <div class="col-md-9"> 
			 	<input type="text" id="leaveInfo_leaveId_edit" name="leaveInfo.leaveId" class="form-control" placeholder="请输入请假id" readOnly>
			 </div>
		  </div> 
		  <div class="form-group">
		  	 <label for="leaveInfo_reason_edit" class="col-md-3 text-right">请假原因:</label>
		  	 <div class="col-md-9">
			    <input type="text" id="leaveInfo_reason_edit" name="leaveInfo.reason" class="form-control" placeholder="请输入请假原因">
			 </div>
		  </div>
		  <div class="form-group">
		  	 <label for="leaveInfo_content_edit" class="col-md-3 text-right">请假内容:</label>
		  	 <div class="col-md-9">
			    <textarea id="leaveInfo_content_edit" name="leaveInfo.content" rows="8" class="form-control" placeholder="请输入请假内容"></textarea>
			 </div>
		  </div>
		  <div class="form-group">
		  	 <label for="leaveInfo_duration_edit" class="col-md-3 text-right">请假多久:</label>
		  	 <div class="col-md-9">
			    <input type="text" id="leaveInfo_duration_edit" name="leaveInfo.duration" class="form-control" placeholder="请输入请假多久">
			 </div>
		  </div>
		  <div class="form-group">
		  	 <label for="leaveInfo_studentObj_studentNo_edit" class="col-md-3 text-right">请假学生:</label>
		  	 <div class="col-md-9">
			    <select id="leaveInfo_studentObj_studentNo_edit" name="leaveInfo.studentObj.studentNo" class="form-control">
			    </select>
		  	 </div>
		  </div>
		  <div class="form-group">
		  	 <label for="leaveInfo_leaveAddTime_edit" class="col-md-3 text-right">请假时间:</label>
		  	 <div class="col-md-9">
                <div class="input-group date leaveInfo_leaveAddTime_edit col-md-12" data-link-field="leaveInfo_leaveAddTime_edit">
                    <input class="form-control" id="leaveInfo_leaveAddTime_edit" name="leaveInfo.leaveAddTime" size="16" type="text" value="" placeholder="请选择请假时间" readonly>
                    <span class="input-group-addon"><span class="glyphicon glyphicon-remove"></span></span>
                    <span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
                </div>
		  	 </div>
		  </div>
		  <div class="form-group">
		  	 <label for="leaveInfo_shzt_edit" class="col-md-3 text-right">审核状态:</label>
		  	 <div class="col-md-9">
			    <input type="text" id="leaveInfo_shzt_edit" name="leaveInfo.shzt" class="form-control" placeholder="请输入审核状态">
			 </div>
		  </div>
		  <div class="form-group">
		  	 <label for="leaveInfo_shhf_edit" class="col-md-3 text-right">审核回复:</label>
		  	 <div class="col-md-9">
			    <textarea id="leaveInfo_shhf_edit" name="leaveInfo.shhf" rows="8" class="form-control" placeholder="请输入审核回复"></textarea>
			 </div>
		  </div>
		  <div class="form-group">
		  	 <label for="leaveInfo_teacherNo_edit" class="col-md-3 text-right">审核的老师:</label>
		  	 <div class="col-md-9">
			    <input type="text" id="leaveInfo_teacherNo_edit" name="leaveInfo.teacherNo" class="form-control" placeholder="请输入审核的老师">
			 </div>
		  </div>
		  <div class="form-group">
		  	 <label for="leaveInfo_shsj_edit" class="col-md-3 text-right">审核时间:</label>
		  	 <div class="col-md-9">
                <div class="input-group date leaveInfo_shsj_edit col-md-12" data-link-field="leaveInfo_shsj_edit">
                    <input class="form-control" id="leaveInfo_shsj_edit" name="leaveInfo.shsj" size="16" type="text" value="" placeholder="请选择审核时间" readonly>
                    <span class="input-group-addon"><span class="glyphicon glyphicon-remove"></span></span>
                    <span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
                </div>
		  	 </div>
		  </div>
		</form> 
	    <style>#leaveInfoEditForm .form-group {margin-bottom:5px;}  </style>
      </div>
      <div class="modal-footer"> 
      	<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
      	<button type="button" class="btn btn-primary" onclick="ajaxLeaveInfoModify();">提交</button>
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
    document.leaveInfoQueryForm.currentPage.value = currentPage;
    document.leaveInfoQueryForm.submit();
}

/*可以直接跳转到某页*/
function changepage(totalPage)
{
    var pageValue=document.leaveInfoQueryForm.pageValue.value;
    if(pageValue>totalPage) {
        alert('你输入的页码超出了总页数!');
        return ;
    }
    document.leaveInfoQueryForm.currentPage.value = pageValue;
    documentleaveInfoQueryForm.submit();
}

/*弹出修改请假界面并初始化数据*/
function leaveInfoEdit(leaveId) {
	$.ajax({
		url :  basePath + "LeaveInfo/" + leaveId + "/update",
		type : "get",
		dataType: "json",
		success : function (leaveInfo, response, status) {
			if (leaveInfo) {
				$("#leaveInfo_leaveId_edit").val(leaveInfo.leaveId);
				$("#leaveInfo_reason_edit").val(leaveInfo.reason);
				$("#leaveInfo_content_edit").val(leaveInfo.content);
				$("#leaveInfo_duration_edit").val(leaveInfo.duration);
				$.ajax({
					url: basePath + "Student/listAll",
					type: "get",
					success: function(students,response,status) { 
						$("#leaveInfo_studentObj_studentNo_edit").empty();
						var html="";
		        		$(students).each(function(i,student){
		        			html += "<option value='" + student.studentNo + "'>" + student.name + "</option>";
		        		});
		        		$("#leaveInfo_studentObj_studentNo_edit").html(html);
		        		$("#leaveInfo_studentObj_studentNo_edit").val(leaveInfo.studentObjPri);
					}
				});
				$("#leaveInfo_leaveAddTime_edit").val(leaveInfo.leaveAddTime);
				$("#leaveInfo_shzt_edit").val(leaveInfo.shzt);
				$("#leaveInfo_shhf_edit").val(leaveInfo.shhf);
				$("#leaveInfo_teacherNo_edit").val(leaveInfo.teacherNo);
				$("#leaveInfo_shsj_edit").val(leaveInfo.shsj);
				$('#leaveInfoEditDialog').modal('show');
			} else {
				alert("获取信息失败！");
			}
		}
	});
}

/*删除请假信息*/
function leaveInfoDelete(leaveId) {
	if(confirm("确认删除这个记录")) {
		$.ajax({
			type : "POST",
			url : basePath + "LeaveInfo/deletes",
			data : {
				leaveIds : leaveId,
			},
			success : function (obj) {
				if (obj.success) {
					alert("删除成功");
					$("#leaveInfoQueryForm").submit();
					//location.href= basePath + "LeaveInfo/frontlist";
				}
				else 
					alert(obj.message);
			},
		});
	}
}

/*ajax方式提交请假信息表单给服务器端修改*/
function ajaxLeaveInfoModify() {
	$.ajax({
		url :  basePath + "LeaveInfo/" + $("#leaveInfo_leaveId_edit").val() + "/update",
		type : "post",
		dataType: "json",
		data: new FormData($("#leaveInfoEditForm")[0]),
		success : function (obj, response, status) {
            if(obj.success){
                alert("信息修改成功！");
                $("#leaveInfoQueryForm").submit();
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

    /*请假时间组件*/
    $('.leaveInfo_leaveAddTime_edit').datetimepicker({
    	language:  'zh-CN',  //语言
    	format: 'yyyy-mm-dd hh:ii:ss',
    	weekStart: 1,
    	todayBtn:  1,
    	autoclose: 1,
    	minuteStep: 1,
    	todayHighlight: 1,
    	startView: 2,
    	forceParse: 0
    });
    /*审核时间组件*/
    $('.leaveInfo_shsj_edit').datetimepicker({
    	language:  'zh-CN',  //语言
    	format: 'yyyy-mm-dd hh:ii:ss',
    	weekStart: 1,
    	todayBtn:  1,
    	autoclose: 1,
    	minuteStep: 1,
    	todayHighlight: 1,
    	startView: 2,
    	forceParse: 0
    });
})
</script>
</body>
</html>

