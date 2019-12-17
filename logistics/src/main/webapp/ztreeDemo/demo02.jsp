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
		}
		
};

function filter(treeId, parentNode, childNodes) {
	if (!childNodes) return null;
	
	for (var i=0, l=childNodes.length; i<l; i++) {
		var child = childNodes[i];
		
		child.id  = child.permissionId;
		child.pId  = child.parentId;
		child.open = true;
		console.log(child);
		//childNodes[i].name = childNodes[i].name.replace(/\.n/g, '.');
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