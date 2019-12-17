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

<meta charset="utf-8">
<meta name="renderer" content="webkit|ie-comp|ie-stand">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
<meta http-equiv="Cache-Control" content="no-siteapp" />
<link rel="Bookmark" href="/favicon.ico" >
<link rel="Shortcut Icon" href="/favicon.ico" />
<link rel="stylesheet" type="text/css" href="static/h-ui/css/H-ui.min.css" />
<link rel="stylesheet" type="text/css" href="static/h-ui.admin/css/H-ui.admin.css" />
<link rel="stylesheet" type="text/css" href="lib/Hui-iconfont/1.0.8/iconfont.css" />
<link rel="stylesheet" type="text/css" href="static/h-ui.admin/skin/default/skin.css" id="skin" />
<link rel="stylesheet" type="text/css" href="static/h-ui.admin/css/style.css" />
<link rel="stylesheet" type="text/css" href="lib/bootstrap/css/bootstrap.min.css" />
<link rel="stylesheet" type="text/css" href="lib/bootstrap-table/bootstrap-table.min.css" />
</head>
<body>
<nav class="breadcrumb"><i class="Hui-iconfont">&#xe67f;</i> 首页 <span class="c-gray en">&gt;</span> 管理员管理 <span class="c-gray en">&gt;</span> 管理员列表 </nav>
<div class="page-container">

	<span class="l" id="toolbar">
		<a href="javascript:;" onclick="datadel()" class="btn btn-danger radius"><i class="Hui-iconfont">&#xe6e2;</i> 批量删除</a> 
		<a href="javascript:;" onclick="admin_add()" class="btn btn-primary radius"><i class="Hui-iconfont">&#xe600;</i> 添加管理员</a>
	</span>  
	
	
	<table id="dataTable"></table>
	
	
</div>
<!--_footer 作为公共模版分离出去-->
<script type="text/javascript" src="lib/jquery/1.11.3/jquery.min.js"></script> 
<script type="text/javascript" src="lib/layer/2.4/layer.js"></script>
<script type="text/javascript" src="static/h-ui/js/H-ui.min.js"></script> 
<script type="text/javascript" src="static/h-ui.admin/js/H-ui.admin.js"></script> <!--/_footer 作为公共模版分离出去-->

<!--请在下方写此页面业务相关的脚本-->
<script type="text/javascript" src="lib/My97DatePicker/4.8/WdatePicker.js"></script> 
<script type="text/javascript" src="lib/datatables/1.10.0/jquery.dataTables.min.js"></script> 
<script type="text/javascript" src="lib/laypage/1.2/laypage.js"></script>
<script type="text/javascript" src="lib/bootstrap-table/bootstrap-table.min.js"></script>
<script type="text/javascript" src="lib/bootstrap-table/bootstrap-table-zh-CN.min.js"></script>

<script type="text/javascript">
	
	
	
	//在文档加载完毕以后执行匿名函数中的代码
	$(function(){
		//初始化表格
		$('#dataTable').bootstrapTable({
			url: 'admin/list.do',//ajax请求的url地址
			method:'get',
			/*
				ajax请求以后回调函数的处理
				后台使用返回的PageInfo对象中的 结果 级的key是list，总条数是total
				而前台bootstrapTable插件需要的数据的key叫做rows ，总条数也是叫做total
				那么出现一个问题 : 总条数的key能对上，结果集对不上，就需要在ajax请求完成回调
				responseHandler 这个函数方法处理一下
				并且在自定义一个 json,rows做为key，返回json的 list作为值
					total：还是total
				这样才能满足 bootstrapTable插件数据的需要
			*/
			responseHandler: function(res) { 
				/*
					res: 后台分页对象PageInfo返回对应的json对象
					res.list : 结果集
					res.total : 总记录数
				*/
				var data =  {rows: res.list,total: res.total};
				return data;
			},
			pagination: true,
			toolbar: "#toolbar",//顶部显示的工具条（添加和批量删除的）
			contentType: 'application/x-www-form-urlencoded',//条件搜索的时候ajax请求给后台数据的数据类型（条件搜索post提交必须设置）
			search: true,//是否显示搜索框
			pageNumber: 1,//默认的页面 第一页
			pageSize: 10,//默认的每页条数
			pageList:[10,18,25,50,100],//每页能显示的条数
			sidePagination: "server",//是否是服务器分页，每次请求都是对应的10条数据，下一页发送ajax请求
			paginationHAlign: 'right', //底部分页条
			showRefresh: true, //是否显示刷新按钮
			columns: [ //表格显示数据对应的表头设置，
				{ checkbox: true},//是否显示前台的复选框（多选）
				/*
					每列数据的表头的设置
					filed:返回json数据对应数据的key
					title:表头要显示的名
				*/
				{field: 'userId',title: '编号'}, 
				{field: 'username',title: '账号'}, 
				{field: 'realname',title: '真实名称'},
				{field: 'createDate',title: '入职日期'},
				{field: 'rolename',title: '角色'},
				//操作列的设置（删除，修改）
				/*
				formatter: 格式化这一行，回调一个函数
				*/
				{
					field:'userId',
					title:'操作',
					align:'center',
					formatter:operationFormatter
				} 
			],
			/*发送请求的参数，
				params: bootstrapTable的插件内部参数对象包含如下参数
				limit, offset, search, sort
				limit：每页条数
				offset：每页的结束位置
				search:搜索框对应的值
				sort：排序
			*/
			queryParams:function(params){
				console.log(params);
				
				
				var paramsData = {
						//页码
						pageNum: params.offset / params.limit + 1,
						pageSize: params.limit, //页面大小
						keyword: params.search
					};
				//此方法在用户分页或者搜索的时候回自动发送ajax请求调用，并把对应的参数传递给后台
				return paramsData;
				
			}
		})
		
		
	});
	
	/* 
		格式化行的函数，可以在此函数使用html标签组装一个删除和修改按钮，运行过程中会自动渲染的到浏览器
		
		value ：当前行的 值  field:'userId'
		row ：当前行对应的json对象
		index ：索引 ,从0开始
	
	*/
	
	
	function operationFormatter(value,row,index){
		var html = "<span onclick='admin_del("+value+")' style='color:red;cursor: pointer;' class='glyphicon glyphicon-trash'></span>&nbsp;&nbsp;";		
		 html += "<span onclick='admin_edit("+value+")' style='color:blue;cursor: pointer;' class='glyphicon glyphicon-pencil'></span>";		
		 
		 // html += '<button type="button" class="btn btn-info btn-xs">禁用</button>';		
		
		return html;
	}
	

</script>

<script type="text/javascript">
/*
	参数解释：
	title	标题
	url		请求的url
	id		需要操作的数据id
	w		弹出层宽度（缺省调默认值）
	h		弹出层高度（缺省调默认值）
*/
/*管理员-增加*/
function admin_add(){
	layer_show("添加管理员","admin/edit.do");
}
/*管理员-编辑*/
function admin_edit(userId){
	layer_show("修改管理员","admin/edit.do?userId="+userId);
}

/*管理员-删除*/
function admin_del(userId){
	layer.confirm('确认要删除吗？',function(index){
		
		$.get("admin/delete.do",{userId:userId},function(data){
			
			//弹出一个提示消息
			layer.msg(data.message, {time: 2500, icon:data.code});
			
			//如果是1说明删除成功，刷新表格 ，调用 bootstrap插件的刷新一下表格
			if(data.code == 1){
				refreshTable();
			}
			
		});
		
	});
}

/**
 * 刷新表格方法，在删除，修改，添加成功以后调用
 */
function refreshTable(){
	$("#dataTable").bootstrapTable("refresh");
}


/*管理员-停用*/
function admin_stop(obj,id){
	layer.confirm('确认要停用吗？',function(index){
		//此处请求后台程序，下方是成功后的前台处理……
		
		$(obj).parents("tr").find(".td-manage").prepend('<a onClick="admin_start(this,id)" href="javascript:;" title="启用" style="text-decoration:none"><i class="Hui-iconfont">&#xe615;</i></a>');
		$(obj).parents("tr").find(".td-status").html('<span class="label label-default radius">已禁用</span>');
		$(obj).remove();
		layer.msg('已停用!',{icon: 5,time:1000});
	});
}

/*管理员-启用*/
function admin_start(obj,id){
	layer.confirm('确认要启用吗？',function(index){
		//此处请求后台程序，下方是成功后的前台处理……
		
		
		$(obj).parents("tr").find(".td-manage").prepend('<a onClick="admin_stop(this,id)" href="javascript:;" title="停用" style="text-decoration:none"><i class="Hui-iconfont">&#xe631;</i></a>');
		$(obj).parents("tr").find(".td-status").html('<span class="label label-success radius">已启用</span>');
		$(obj).remove();
		layer.msg('已启用!', {icon: 6,time:1000});
	});
}
</script>
</body>
</html>