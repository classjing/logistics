<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
/* http://localhost:8080/logistics/ */
%>
<!DOCTYPE HTML>
<html>
<head>
<!-- 设置页面的 基本路径，页面所有资源引入和页面的跳转全部基于 base路径 -->
<base href="<%=basePath%>">
<meta charset="utf-8">
<link rel="stylesheet" type="text/css" href="static/h-ui/css/H-ui.min.css" />
<link rel="stylesheet" type="text/css" href="static/h-ui.admin/css/H-ui.admin.css" />
<link rel="stylesheet" type="text/css" href="lib/Hui-iconfont/1.0.8/iconfont.css" />
<link rel="stylesheet" type="text/css" href="static/h-ui.admin/skin/default/skin.css" id="skin" />
<link rel="stylesheet" type="text/css" href="static/h-ui.admin/css/style.css" />
</head>
<body>
<article class="page-container">
	<form class="form form-horizontal" id="userForm" action="${user ==null ? 'admin/insert.do' :'admin/update.do'}">
	<!-- 隐藏域  -->
	<input type="hidden" name="userId" value="${user.userId}">
	
	<div class="row cl">
		<label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>管理员账号：</label>
		<div class="formControls col-xs-8 col-sm-9">
			<input type="text" class="input-text"  ${user != null? "disabled":""}  value="${user.username}" placeholder="" id="username" name="username">
		</div>
	</div>
	<div class="row cl">
		<label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>真实姓名：</label>
		<div class="formControls col-xs-8 col-sm-9">
			<input type="text" class="input-text" value="${user.realname}" placeholder="" id="realname" name="realname">
		</div>
	</div>
	<div class="row cl">
		<label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>初始密码：</label>
		<div class="formControls col-xs-8 col-sm-9">
			<input type="password" class="input-text" autocomplete="off" value="" placeholder="密码" id="pwd" name="password">
		</div>
	</div>
	<div class="row cl">
		<label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>确认密码：</label>
		<div class="formControls col-xs-8 col-sm-9">
			<input type="password" class="input-text" autocomplete="off"  placeholder="确认新密码" id="password2" name="password2">
		</div>
	</div>
	<!-- 
		<div class="row cl">
		<label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>性别：</label>
		<div class="formControls col-xs-8 col-sm-9 skin-minimal">
			<div class="radio-box">
				<input name="sex" type="radio" id="sex-1" checked>
				<label for="sex-1">男</label>
			</div>
			<div class="radio-box">
				<input type="radio" id="sex-2" name="sex">
				<label for="sex-2">女</label>
			</div>
		</div>
	</div> -->
	<div class="row cl">
		<label class="form-label col-xs-4 col-sm-3">角色：</label>
		<div class="formControls col-xs-8 col-sm-9"> <span class="select-box" style="width:150px;">
			<select class="select" name="roleId" size="1">
				<option value="0">请选择</option>
				<c:forEach items="${roles}" var="role">
					<option ${user.roleId eq role.roleId ? 'selected':''}  value="${role.roleId}">${role.rolename}</option>
				</c:forEach>
			</select>
			</span> </div>
	</div>
	<div class="row cl">
		<div class="col-xs-8 col-sm-9 col-xs-offset-4 col-sm-offset-3">
			<input class="btn btn-primary radius" type="submit" value="&nbsp;&nbsp;提交&nbsp;&nbsp;">
		</div>
	</div>
	</form>
</article>

<!--_footer 作为公共模版分离出去--> 
<script type="text/javascript" src="lib/jquery/1.9.1/jquery.min.js"></script> 
<script type="text/javascript" src="lib/layer/2.4/layer.js"></script>
<script type="text/javascript" src="static/h-ui/js/H-ui.min.js"></script> 
<script type="text/javascript" src="static/h-ui.admin/js/H-ui.admin.js"></script> <!--/_footer 作为公共模版分离出去-->

<!--请在下方写此页面业务相关的脚本-->
<script type="text/javascript" src="lib/jquery.validation/1.14.0/jquery.validate.js"></script> 
<script type="text/javascript" src="lib/jquery.validation/1.14.0/validate-methods.js"></script> 
<script type="text/javascript" src="lib/jquery.validation/1.14.0/messages_zh.js"></script> 
<script type="text/javascript">
$(function(){
	
	
	/*
		使用Jquery.validate 进行表单校验
	*/
	
	$("#userForm").validate({
		rules:{
			username:{
				required:true,
				rangelength:[3,10],
				remote: {
				    url: "admin/checkUsername.do",     //后台处理程序
				    type: "post",               //数据发送方式
				    dataType: "json",           //接受数据格式   
				    data: {                     //要传递的数据
				        username: function() {
				            return $("#username").val();
				        }
				    }
				}
			},
			realname:{
				required:true,
				minlength:2,
				isChinese:true
			},
			password:"required",
			password2:{
				equalTo:"#pwd"
			},
			roleId:{
				min:1
			}
		},
		messages:{
			username:{
				required:"账号不能为空",
				rangelength:"账号长度3-10之间",
				remote:"账号已经存在！！"
			},
			realname:{
				required:"真实姓名不能为空",
				minlength:"最少2位数",
				isChinese:"真实姓名必须是中文"
			},
			password:"密码不能为空",
			password2:{
				equalTo:"确认密码必须和密码相同"
			},
			roleId:{
				min:"请选择一个角色"
			}
			
		},
		submitHandler:function(form){
			/*
				form ：原生js的DOM对象
				
				所有的jquery对象都使用  $() 包装起来
				
				1,原生js的dom转jquery对象
				$(form)
				2,jquery对象转换成原生dom对象
				jquery对象[0] 
			*/
			
			var jqForm = $(form);
			
			//使用表单ajax提交表单数据
			
			jqForm.ajaxSubmit(function(data){
				
				//如果是1说明添加成功，刷新表格 ，调用 bootstrap插件的刷新一下表格
				if(data.code == 1){
					layer.msg(data.message,{icon:1,time:2000},function(){
						//刷新父页面的表格
						window.parent.refreshTable();
						//关闭模态框-从父页面开始关
						window.parent.layer.closeAll();
					});
					
				}
				
			});
			
			
			console.log(form);
		}
	});
	
	
});
</script> 
<!--/请在上方写此页面业务相关的脚本-->
</body>
</html>