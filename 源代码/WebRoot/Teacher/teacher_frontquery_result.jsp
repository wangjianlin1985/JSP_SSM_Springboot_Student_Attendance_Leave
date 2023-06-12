<%@ page language="java" import="java.util.*"  contentType="text/html;charset=UTF-8"%> 
<%@ page import="com.chengxusheji.po.Teacher" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    List<Teacher> teacherList = (List<Teacher>)request.getAttribute("teacherList");
    int currentPage =  (Integer)request.getAttribute("currentPage"); //当前页
    int totalPage =   (Integer)request.getAttribute("totalPage");  //一共多少页
    int recordNumber =   (Integer)request.getAttribute("recordNumber");  //一共多少记录
    String teacherNo = (String)request.getAttribute("teacherNo"); //教师编号查询关键字
    String name = (String)request.getAttribute("name"); //姓名查询关键字
    String telephone = (String)request.getAttribute("telephone"); //联系电话查询关键字
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1 , user-scalable=no">
<title>老师查询</title>
<link href="<%=basePath %>plugins/bootstrap.css" rel="stylesheet">
<link href="<%=basePath %>plugins/bootstrap-dashen.css" rel="stylesheet">
<link href="<%=basePath %>plugins/font-awesome.css" rel="stylesheet">
<link href="<%=basePath %>plugins/animate.css" rel="stylesheet">
<link href="<%=basePath %>plugins/bootstrap-datetimepicker.min.css" rel="stylesheet" media="screen">
</head>
<body style="margin-top:70px;">
<div class="container">
<jsp:include page="../header.jsp"></jsp:include>
<body style="margin-top:70px;"> 
<div class="container">
<jsp:include page="../header.jsp"></jsp:include>
	<div class="col-md-9 wow fadeInLeft">
		<ul class="breadcrumb">
  			<li><a href="<%=basePath %>index.jsp">首页</a></li>
  			<li><a href="<%=basePath %>Teacher/frontlist">老师信息列表</a></li>
  			<li class="active">查询结果显示</li>
  			<a class="pull-right" href="<%=basePath %>Teacher/teacher_frontAdd.jsp" style="display:none;">添加老师</a>
		</ul>
		<div class="row">
			<%
				/*计算起始序号*/
				int startIndex = (currentPage -1) * 5;
				/*遍历记录*/
				for(int i=0;i<teacherList.size();i++) {
            		int currentIndex = startIndex + i + 1; //当前记录的序号
            		Teacher teacher = teacherList.get(i); //获取到老师对象
            		String clearLeft = "";
            		if(i%4 == 0) clearLeft = "style=\"clear:left;\"";
			%>
			<div class="col-md-3 bottom15" <%=clearLeft %>>
			  <a  href="<%=basePath  %>Teacher/<%=teacher.getTeacherNo() %>/frontshow"><img class="img-responsive" src="<%=basePath%><%=teacher.getTeacherPhoto()%>" /></a>
			     <div class="showFields">
			     	<div class="field">
	            		教师编号:<%=teacher.getTeacherNo() %>
			     	</div>
			     	<div class="field">
	            		姓名:<%=teacher.getName() %>
			     	</div>
			     	<div class="field">
	            		性别:<%=teacher.getSex() %>
			     	</div>
			     	<div class="field">
	            		联系电话:<%=teacher.getTelephone() %>
			     	</div>
			     	<div class="field">
	            		邮箱地址:<%=teacher.getEmail() %>
			     	</div>
			        <a class="btn btn-primary top5" href="<%=basePath %>Teacher/<%=teacher.getTeacherNo() %>/frontshow">详情</a>
			        <a class="btn btn-primary top5" onclick="teacherEdit('<%=teacher.getTeacherNo() %>');" style="display:none;">修改</a>
			        <a class="btn btn-primary top5" onclick="teacherDelete('<%=teacher.getTeacherNo() %>');" style="display:none;">删除</a>
			     </div>
			</div>
			<%  } %>

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

	<div class="col-md-3 wow fadeInRight">
		<div class="page-header">
    		<h1>老师查询</h1>
		</div>
		<form name="teacherQueryForm" id="teacherQueryForm" action="<%=basePath %>Teacher/frontlist" class="mar_t15">
			<div class="form-group">
				<label for="teacherNo">教师编号:</label>
				<input type="text" id="teacherNo" name="teacherNo" value="<%=teacherNo %>" class="form-control" placeholder="请输入教师编号">
			</div>
			<div class="form-group">
				<label for="name">姓名:</label>
				<input type="text" id="name" name="name" value="<%=name %>" class="form-control" placeholder="请输入姓名">
			</div>
			<div class="form-group">
				<label for="telephone">联系电话:</label>
				<input type="text" id="telephone" name="telephone" value="<%=telephone %>" class="form-control" placeholder="请输入联系电话">
			</div>
            <input type=hidden name=currentPage value="<%=currentPage %>" />
            <button type="submit" class="btn btn-primary">查询</button>
        </form>
	</div>

		</div>
</div>
<div id="teacherEditDialog" class="modal fade" tabindex="-1" role="dialog">
  <div class="modal-dialog" style="width:900px;" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title"><i class="fa fa-edit"></i>&nbsp;老师信息编辑</h4>
      </div>
      <div class="modal-body" style="height:450px; overflow: scroll;">
      	<form class="form-horizontal" name="teacherEditForm" id="teacherEditForm" enctype="multipart/form-data" method="post"  class="mar_t15">
		  <div class="form-group">
			 <label for="teacher_teacherNo_edit" class="col-md-3 text-right">教师编号:</label>
			 <div class="col-md-9"> 
			 	<input type="text" id="teacher_teacherNo_edit" name="teacher.teacherNo" class="form-control" placeholder="请输入教师编号" readOnly>
			 </div>
		  </div> 
		  <div class="form-group">
		  	 <label for="teacher_password_edit" class="col-md-3 text-right">登录密码:</label>
		  	 <div class="col-md-9">
			    <input type="text" id="teacher_password_edit" name="teacher.password" class="form-control" placeholder="请输入登录密码">
			 </div>
		  </div>
		  <div class="form-group">
		  	 <label for="teacher_name_edit" class="col-md-3 text-right">姓名:</label>
		  	 <div class="col-md-9">
			    <input type="text" id="teacher_name_edit" name="teacher.name" class="form-control" placeholder="请输入姓名">
			 </div>
		  </div>
		  <div class="form-group">
		  	 <label for="teacher_sex_edit" class="col-md-3 text-right">性别:</label>
		  	 <div class="col-md-9">
			    <input type="text" id="teacher_sex_edit" name="teacher.sex" class="form-control" placeholder="请输入性别">
			 </div>
		  </div>
		  <div class="form-group">
		  	 <label for="teacher_teacherPhoto_edit" class="col-md-3 text-right">老师照片:</label>
		  	 <div class="col-md-9">
			    <img  class="img-responsive" id="teacher_teacherPhotoImg" border="0px"/><br/>
			    <input type="hidden" id="teacher_teacherPhoto" name="teacher.teacherPhoto"/>
			    <input id="teacherPhotoFile" name="teacherPhotoFile" type="file" size="50" />
		  	 </div>
		  </div>
		  <div class="form-group">
		  	 <label for="teacher_telephone_edit" class="col-md-3 text-right">联系电话:</label>
		  	 <div class="col-md-9">
			    <input type="text" id="teacher_telephone_edit" name="teacher.telephone" class="form-control" placeholder="请输入联系电话">
			 </div>
		  </div>
		  <div class="form-group">
		  	 <label for="teacher_email_edit" class="col-md-3 text-right">邮箱地址:</label>
		  	 <div class="col-md-9">
			    <input type="text" id="teacher_email_edit" name="teacher.email" class="form-control" placeholder="请输入邮箱地址">
			 </div>
		  </div>
		  <div class="form-group">
		  	 <label for="teacher_teacherDesc_edit" class="col-md-3 text-right">老师介绍:</label>
		  	 <div class="col-md-9">
			 	<textarea name="teacher.teacherDesc" id="teacher_teacherDesc_edit" style="width:100%;height:500px;"></textarea>
			 </div>
		  </div>
		</form> 
	    <style>#teacherEditForm .form-group {margin-bottom:5px;}  </style>
      </div>
      <div class="modal-footer"> 
      	<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
      	<button type="button" class="btn btn-primary" onclick="ajaxTeacherModify();">提交</button>
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
<script type="text/javascript" charset="utf-8" src="${pageContext.request.contextPath}/ueditor1_4_3/ueditor.config.js"></script>
<script type="text/javascript" charset="utf-8" src="${pageContext.request.contextPath}/ueditor1_4_3/ueditor.all.min.js"> </script>
<script type="text/javascript" charset="utf-8" src="${pageContext.request.contextPath}/ueditor1_4_3/lang/zh-cn/zh-cn.js"></script>
<script>
//实例化编辑器
var teacher_teacherDesc_edit = UE.getEditor('teacher_teacherDesc_edit'); //老师介绍编辑器
var basePath = "<%=basePath%>";
/*跳转到查询结果的某页*/
function GoToPage(currentPage,totalPage) {
    if(currentPage==0) return;
    if(currentPage>totalPage) return;
    document.teacherQueryForm.currentPage.value = currentPage;
    document.teacherQueryForm.submit();
}

/*可以直接跳转到某页*/
function changepage(totalPage)
{
    var pageValue=document.teacherQueryForm.pageValue.value;
    if(pageValue>totalPage) {
        alert('你输入的页码超出了总页数!');
        return ;
    }
    document.teacherQueryForm.currentPage.value = pageValue;
    documentteacherQueryForm.submit();
}

/*弹出修改老师界面并初始化数据*/
function teacherEdit(teacherNo) {
	$.ajax({
		url :  basePath + "Teacher/" + teacherNo + "/update",
		type : "get",
		dataType: "json",
		success : function (teacher, response, status) {
			if (teacher) {
				$("#teacher_teacherNo_edit").val(teacher.teacherNo);
				$("#teacher_password_edit").val(teacher.password);
				$("#teacher_name_edit").val(teacher.name);
				$("#teacher_sex_edit").val(teacher.sex);
				$("#teacher_teacherPhoto").val(teacher.teacherPhoto);
				$("#teacher_teacherPhotoImg").attr("src", basePath +　teacher.teacherPhoto);
				$("#teacher_telephone_edit").val(teacher.telephone);
				$("#teacher_email_edit").val(teacher.email);
				teacher_teacherDesc_edit.setContent(teacher.teacherDesc, false);
				$('#teacherEditDialog').modal('show');
			} else {
				alert("获取信息失败！");
			}
		}
	});
}

/*删除老师信息*/
function teacherDelete(teacherNo) {
	if(confirm("确认删除这个记录")) {
		$.ajax({
			type : "POST",
			url : basePath + "Teacher/deletes",
			data : {
				teacherNos : teacherNo,
			},
			success : function (obj) {
				if (obj.success) {
					alert("删除成功");
					$("#teacherQueryForm").submit();
					//location.href= basePath + "Teacher/frontlist";
				}
				else 
					alert(obj.message);
			},
		});
	}
}

/*ajax方式提交老师信息表单给服务器端修改*/
function ajaxTeacherModify() {
	$.ajax({
		url :  basePath + "Teacher/" + $("#teacher_teacherNo_edit").val() + "/update",
		type : "post",
		dataType: "json",
		data: new FormData($("#teacherEditForm")[0]),
		success : function (obj, response, status) {
            if(obj.success){
                alert("信息修改成功！");
                $("#teacherQueryForm").submit();
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

