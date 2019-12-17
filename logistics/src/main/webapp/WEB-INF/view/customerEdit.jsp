<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c"%>
<%@ taglib uri="http://shiro.apache.org/tags"  prefix="shiro"%>

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
	<form class="form form-horizontal" id="customerForm" action="${customer ==null ? 'customer/insert.do' :'customer/update.do'}">
	<!-- 隐藏域  -->
	<input type="hidden" name="customerId" value="${customer.customerId}">
	
	<div class="row cl">
		<label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>客户名称：</label>
		<div class="formControls col-xs-8 col-sm-9">
			<input type="text" class="input-text"    value="${customer.customerName}" placeholder="" id="customerName" name="customerName">
		</div>
	</div>
	<div class="row cl">
		<label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>客户电话：</label>
		<div class="formControls col-xs-8 col-sm-9">
			<input type="text" class="input-text"    value="${customer.phone}" placeholder="" id="phone" name="phone">
		</div>
	</div>
	<div class="row cl">
		<label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>客户邮箱：</label>
		<div class="formControls col-xs-8 col-sm-9">
			<input type="text" class="input-text"    value="${customer.email}" placeholder="" id="email" name="email">
		</div>
	</div>
	<div class="row cl">
		<label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>客户地址：</label>
		<div class="formControls col-xs-8 col-sm-9">
			<input type="text" class="input-text"    value="${customer.address}" placeholder="" id="address" name="address">
		</div>
	</div>
	<div class="row cl">
		<label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>客户身份证：</label>
		<div class="formControls col-xs-8 col-sm-9">
			<input type="text" class="input-text"    value="${customer.idCard}" placeholder="" id="idCard" name="idCard">
		</div>
	</div>
	<div class="row cl">
	<label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>性别：</label>
	<div class="formControls col-xs-8 col-sm-9 skin-minimal">
		<div class="radio-box">
			<input name="gender" type="radio" id="sex-1" ${customer.gender eq 1 ?'checked':''} value="1">
			<label for="sex-1">男</label>
		</div>
		<div class="radio-box">
			<input type="radio" id="sex-2" name="gender" ${customer.gender eq 2 ?'checked':''} value="2">
			<label for="sex-2">女</label>
		</div>
	</div>
	</div>
	
	

	
	<div class="row cl">
		<label class="form-label col-xs-4 col-sm-3">业务员：</label>
		<div class="formControls col-xs-8 col-sm-9"> 
			<span class="select-box" style="width:150px;">
			
			
			<c:if test="${user.rolename eq '业务员'}">
				<input type="hidden" name="userId" value="${user.userId}">
				${user.realname}
			</c:if>
			<!-- 不是业务员 -->
			<c:if test="${user.rolename != '业务员'}">
				<select class="select" name="userId" size="1">
					<option value="">请选择</option>
					<c:forEach items="${users}" var="user">
						<option  ${customer.userId eq user.userId ?'selected':''}  value="${user.userId}">${user.realname}</option>
					</c:forEach>
					
				</select>
			</c:if>
				
			
			</span> 
		</div>
	</div>
	
	<div class="row cl">
		<label class="form-label col-xs-4 col-sm-3">客户区间：</label>
		<div class="formControls col-xs-8 col-sm-9"> 
			<span class="select-box" style="width:150px;">
				
				<select class="select" name="baseId" size="1">
					<option value="">请选择</option>
					<c:forEach items="${baseDatas}" var="p">
						<option ${customer.baseId eq p.baseId ?'selected':''} value="${p.baseId}">${p.baseName}</option>
					</c:forEach>
				</select>
			</span> 
		</div>
	</div>
	
	<div class="row cl">
		<label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>客户描述：</label>
		<div class="formControls col-xs-8 col-sm-9">
			<textarea name="remark" cols="" rows="" class="textarea"   placeholder="说点什么...最少输入10个字符" >${customer.remark}</textarea>
		</div>
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
	
	$("#customerForm").validate({
		rules:{
			customerName:{
				required:true,
			}
		},
		messages:{
			customerName:{
				required:"客户名称不能为空",
			}
		},
		submitHandler:function(form){
			
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