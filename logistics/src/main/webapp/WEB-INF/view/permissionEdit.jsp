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
	<form class="form form-horizontal" id="permissionForm" action="${permission ==null ? 'permission/insert.do' :'permission/update.do'}">
	<!-- 隐藏域  -->
	<input type="hidden" name="permissionId" value="${permission.permissionId}">
	
	<div class="row cl">
		<label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>权限名称：</label>
		<div class="formControls col-xs-8 col-sm-9">
			<input type="text" class="input-text"  ${permission != null? "disabled":""}  value="${permission.name}" placeholder="" id="name" name="name">
		</div>
	</div>
	<div class="row cl">
		<label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>权限表达式：</label>
		<div class="formControls col-xs-8 col-sm-9">
			<input type="text" class="input-text" value="${permission.expression}" placeholder="格式 模块:功能(如 admin:insert,admin:list)" id="expression" name="expression">
		</div>
	</div>
	<div class="row cl">
		<label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>权限url地址：</label>
		<div class="formControls col-xs-8 col-sm-9">
			<input type="text" class="input-text" value="${permission.url}" placeholder="" id="url" name="url">
		</div>
	</div>
	
	<div class="row cl">
	<label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>权限类型：</label>
	<div class="formControls col-xs-8 col-sm-9 skin-minimal">
		<div class="radio-box">
			<input name="type" type="radio" id="sex-1" ${permission.type eq 'permission' ?'checked':''} value="permission">
			<label for="sex-1">普通权限</label>
		</div>
		<div class="radio-box">
			<input type="radio" id="sex-2" name="type" ${permission.type eq 'menu' ?'checked':''} value="menu">
			<label for="sex-2">菜单权限</label>
		</div>
	</div>
	</div>
	<div class="row cl">
		<label class="form-label col-xs-4 col-sm-3">父权限：</label>
		<div class="formControls col-xs-8 col-sm-9"> <span class="select-box" style="width:150px;">
			<select class="select" name="parentId" size="1">
				<option value="0">请选择</option>
				<c:forEach items="${permissions}" var="p">
					<option ${permission.parentId eq p.permissionId ?'selected':''} value="${p.permissionId}">${p.name}</option>
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
	
	$("#permissionForm").validate({
		rules:{
			name:{
				required:true,
			},
			expression:{
				required:true,
			}
		},
		messages:{
			name:{
				required:"权限名称不能为空",
			},
			expression:{
				required:"权限表达式不能为空",
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