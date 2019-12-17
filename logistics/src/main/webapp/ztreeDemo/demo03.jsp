<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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

<link rel="stylesheet" href="lib/zTree/v3/css/zTreeStyle/zTreeStyle.css" type="text/css">

<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

<ul id="permissionTree" class="ztree"></ul>



<script type="text/javascript" src="lib/jquery/1.11.3/jquery.min.js"></script>
<script type="text/javascript" src="lib/zTree/v3/js/jquery.ztree.all-3.5.min.js"></script>

<script type="text/javascript">
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
			onAsyncSuccess: zTreeOnAsyncSuccess
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
	var permissionIds = "23,24";
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
			treeObj.checkNode(node, true, true);
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
	}
	return childNodes;
}




$(document).ready(function(){
	
	
	
	
	//初始化zTree
	$.fn.zTree.init($("#permissionTree"), setting);
});

</script>

</body>
</html>