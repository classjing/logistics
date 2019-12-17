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
<link rel="stylesheet" href="lib/zTree/v3/css/zTreeStyle/zTreeStyle.css" type="text/css">

</head>
<body>
<article class="page-container">
	<form class="form form-horizontal" id="roleForm" action="${role ==null ? 'role/insert.do' :'role/update.do'}">
	<!-- 隐藏域  -->
	<input type="hidden" name="roleId" value="${role.roleId}">
	<!-- 权限数据 -->
	<input type="hidden" id="permissionIds" name="permissionIds" >
	
	
	<div class="row cl">
		<label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>角色名称：</label>
		<div class="formControls col-xs-8 col-sm-9">
			<input type="text" class="input-text"   value="${role.rolename}" placeholder="" id="rolename" name="rolename">
		</div>
	</div>
	<div class="row cl">
			<label class="form-label col-xs-4 col-sm-3">角色描述：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<textarea name="remark" cols="" rows="" class="textarea"   placeholder="说点什么...最少输入10个字符" >${role.remark}</textarea>
			</div>
		</div>
	<div class="row cl">
			<label class="form-label col-xs-4 col-sm-3">角色的权限：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<ul id="permissionTree" class="ztree"></ul>
				
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
<script type="text/javascript" src="lib/zTree/v3/js/jquery.ztree.all-3.5.min.js"></script>

<!--请在下方写此页面业务相关的脚本-->
<script type="text/javascript" src="lib/jquery.validation/1.14.0/jquery.validate.js"></script> 
<script type="text/javascript" src="lib/jquery.validation/1.14.0/validate-methods.js"></script> 
<script type="text/javascript" src="lib/jquery.validation/1.14.0/messages_zh.js"></script> 
<script type="text/javascript">
$(function(){

	/*
		使用Jquery.validate 进行表单校验
	*/
	
	$("#roleForm").validate({
		rules:{
			rolename:{
				required:true,
			},
			remark:{
				required:true,
			}
		},
		messages:{
			rolename:{
				required:"角色名称不能为空",
			},
			remark:{
				required:"角色描述不能为空",
			}
			
		},
		submitHandler:function(form){
			
			/*
				问题：表单和zTree不是一起，如何在提交角色操作的时候，把zTree的数据和表单一起提交给后台
				
				解决思路
				
				1，先获取zTree选中的节点数据的id，拼接字符串
				
				2，给表单设置一个隐藏域
				
				3，使用dom操作，将第一步的数据设置 表单的隐藏域
			
			*/
			getZTreeCheckedData();
			
			
			//判断角色是否分配权限
			if($("#permissionIds").val() == ""){
				
				layer.msg("请给角色分配权限",{icon:0});
				
				return false;
			}
			
			
			var jqForm = $(form);
			
			//使用表单ajax提交表单数据
			
			jqForm.ajaxSubmit(function(data){
				
				//如果是1说明添加成功，刷新表格 ，调用 bootstrap插件的刷新一下表格
				if(data.code == 1){
					layer.msg(data.message,{icon:data.code,time:2000},function(){
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




//------------------------zTree 相关js代码 -------------------------------

//获取zTree的数据，拼接成字符串，并设置给 角色表单隐藏域
function getZTreeCheckedData(){
	
	//获取zTree树对象
	var treeObj = $.fn.zTree.getZTreeObj("permissionTree");
	//获取选中的节点
	var nodes = treeObj.getCheckedNodes(true);
	
	//声明数组用于添加 权限id
	var permissionIdsArr = [];
	
	//循环数组，获取每个节点对象，并获取节点的permissionId 值
	for(var i = 0 ;i < nodes.length ;i++){
		
		//获取每个节点数据
		var node = nodes[i];
		
		//获取每个节点的permissionId
		var permissionId  = node.permissionId;
		
		//将权限id添加到数组
		permissionIdsArr.push(permissionId);
	}
	
	console.log(permissionIdsArr);
	//var permissionIdsStr = permissionIdsArr.toString();//49,1,15,48,13,16,17,64
	
	var permissionIdsStr = permissionIdsArr.join(",");
	//console.log("permissionIdsStr",permissionIdsStr);
	
	
	//将拼接的字符串设置隐藏域
	$("#permissionIds").val(permissionIdsStr);
}


var setting = {	
		check: {enable: true},
		data: {
			simpleData: {enable: true}
		},
		async: {
			enable: true,
			url:"permission/selectAllPermission.do",
			dataFilter: filter
		},
		//在异步加载完毕以后执行回调函数
		callback: {
			onAsyncSuccess: zTreeOnAsyncSuccess,
		}
		
};


function zTreeOnAsyncSuccess(event, treeId, treeNode, msg) {
	/* 
		在此函数中完成 编辑数据的回显（将角色已有的权限对应的勾选上）
		
		1，拆分所有的权限id对应字符串
			var permissionIds = "${role.permissionIds}";
			var permissionIds = "23,24";
			
			var permissionIdsArr = permissionIds.split(",");
			
		2,循环数组，获取每一个id，并通过zTree的通过自定义属性获取对应的节点方法（通过id的属性属性获取对应的zTree节点）
		
		3，让第二步获取的对应的 节点选中即可
		
	*/
	//1，拆分所有的权限id对应字符串
	var permissionIds = "${role.permissionIds}";
	var permissionIdsArr = permissionIds.split(",");
	//2,循环数组，获取每一个id，并通过zTree的通过自定义属性获取对应的节点方法（通过id的属性属性获取对应的zTree节点）
	
	
	//获取zTree树对象
	var treeObj = $.fn.zTree.getZTreeObj("permissionTree");
	
	for(var i = 0 ;i<permissionIdsArr.length;i++){
		//获取每一个id值 ： 10,36,37,38,39,40,46,47,50,51,52,54,55
		var permissionId = permissionIdsArr[i];
		
		
		//通过zTree的id属性获取对应的zTree节点对象
		var node = treeObj.getNodeByParam("id", permissionId, null);
		
		if(node !=null){
			//让其node节点选中即可，即完成回显功能
			treeObj.checkNode(node, true, false);
		}
	}
};


function filter(treeId, parentNode, childNodes) {
	if (!childNodes) return null;
	
	for (var i=0, l=childNodes.length; i<l; i++) {
		var child = childNodes[i];
		child.id  = child.permissionId;
		child.pId  = child.parentId;
		child.open = true;
		//把所有url地址设置为空，不让有地址的数据点击跳转
		child.url = "";
		console.log(child);
	}
	return childNodes;
}

$(document).ready(function(){
	//初始化zTree
	$.fn.zTree.init($("#permissionTree"), setting);
});


</script> 

<!--/请在上方写此页面业务相关的脚本-->
</body>
</html>