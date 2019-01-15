<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta charset="UTF-8">
	<title>课程列表</title>
	<link rel="stylesheet" type="text/css" href="easyui/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="easyui/themes/icon.css">
	<link rel="stylesheet" type="text/css" href="easyui/css/demo.css">
	<script type="text/javascript" src="easyui/jquery.min.js"></script>
	<script type="text/javascript" src="easyui/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="easyui/js/validateExtends.js"></script>
	<script type="text/javascript">
	$(function() {	
		//datagrid初始化 
	    $('#dataList').datagrid({ 
	        title:'课程列表', 
	        iconCls:'icon-more',//图标 
	        border: true, 
	        collapsible: false,//是否可折叠的 
	        fit: true,//自动大小 
	        method: "post",
	        url:"CourseServlet?method=CourseList&t="+new Date().getTime(),
	        idField:'courseId',
	        singleSelect: true,//是否单选 
	        pagination: false,//分页控件 
	        rownumbers: true,//行号 
	        sortName:'courseId',
	        sortOrder:'asc',
	        remoteSort: false,
	        columns: [[  
				{field:'chk',checkbox: true,width:50},
 		        {field:'courseId',title:'ID',width:50, sortable: true},
 		        {field:'courseName',title:'课程名称',width:200},
				{field:'courseNature',title:'课程性质',width:200},
				{field:'courseHour',title:'课程学时',width:200}
	 		]], 
	        toolbar: "#toolbar"
	    }); 
	   	
	    //设置工具类按钮
	    $("#add").click(function(){
	    	$("#addDialog").dialog("open");
	    });
	    //删除
	    $("#delete").click(function(){
	    	var selectRow = $("#dataList").datagrid("getSelected");
        	if(selectRow == null){
            	$.messager.alert("消息提醒", "请选择数据进行删除!", "warning");
            } else{
            	var courseId = selectRow.courseId;
            	$.messager.confirm("消息提醒", "将删除与课程相关的所有数据，确认继续？", function(r){
            		if(r){
            			$.ajax({
							type: "post",
							url: "CourseServlet?method=DeleteCourse",
							data: {courseId: courseId},
							success: function(msg){
								if(msg == "success"){
									$.messager.alert("消息提醒","删除成功!","info");
									//刷新表格
									$("#dataList").datagrid("reload");
								} else{
									$.messager.alert("消息提醒","删除失败!","warning");
									return;
								}
							}
						});
            		}
            	});
            }
	    });
	  	
	  	//设置添加窗口
	    $("#addDialog").dialog({
	    	title: "添加课程",
	    	width: 440,
	    	height: 380,
	    	iconCls: "icon-add",
	    	modal: true,
	    	collapsible: false,
	    	minimizable: false,
	    	maximizable: false,
	    	draggable: true,
	    	closed: true,
	    	buttons: [
	    		{
					text:'添加',
					plain: true,
					iconCls:'icon-book-add',
					handler:function(){
						var validate = $("#addForm").form("validate");
						if(!validate){
							$.messager.alert("消息提醒","请检查你输入的数据!","warning");
							return;
						} else{
							$.ajax({
								type: "post",
								url: "CourseServlet?method=AddCourse",
								data: $("#addForm").serialize(),
								success: function(msg){
									if(msg == "success"){
										$.messager.alert("消息提醒","添加成功!","info");
										//关闭窗口
										$("#addDialog").dialog("close");
										//清空原表格数据
										$("#add_courseId").textbox('setValue',"");
										$("#add_courseName").textbox('setValue', "");
                                        $("#add_courseNature").textbox('setValue',"");
                                        $("#add_courseHour").textbox('setValue',"");
										//刷新
										$('#dataList').datagrid("reload");
									} else{
										$.messager.alert("消息提醒","添加失败!","warning");
										return;
									}
								}
							});
						}
					}
				},
				{
					text:'重置',
					plain: true,
					iconCls:'icon-book-reset',
					handler:function(){
                        $("#add_courseId").textbox('setValue',"");
                        $("#add_courseName").textbox('setValue', "");
                        $("#add_courseNature").textbox('setValue',"");
                        $("#add_courseHour").textbox('setValue',"");
					}
				},
			],
            onClose: function () {
                $("#add_courseId").textbox('setValue',"");
                $("#add_courseName").textbox('setValue', "");
                $("#add_courseNature").textbox('setValue',"");
                $("#add_courseHour").textbox('setValue',"");
            }
	    });
	  	
	});
	</script>
</head>
<body>
	<!-- 数据列表 -->
	<table id="dataList" cellspacing="0" cellpadding="0"> 
	    
	</table> 
	<!-- 工具栏 -->
	<div id="toolbar">
		<div style="float: left;"><a id="add" href="javascript:;" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true">添加</a></div>
			<div style="float: left;" class="datagrid-btn-separator"></div>
		<div><a id="delete" href="javascript:;" class="easyui-linkbutton" data-options="iconCls:'icon-some-delete',plain:true">删除</a></div>
	</div>
	
	<!-- 添加数据窗口 -->
	<div id="addDialog" style="padding: 10px">  
    	<form id="addForm" method="post">
	    	<table cellpadding="8" >
				<tr>
					<td>ID:</td>
					<td><input id="add_courseId" style="width: 200px; height: 30px;" class="easyui-textbox" type="text" name="courseId" data-options="required:true, missingMessage:'请输入课程号'" />
				</tr>
	    		<tr>
	    			<td>课程名称:</td>
	    			<td><input id="add_courseName" style="width: 200px; height: 30px;" class="easyui-textbox" type="text" name="courseName" data-options="required:true, missingMessage:'不能为空'" /></td>
	    		</tr>
				<tr>
					<td>课程类型:</td>
					<td colspan="4"><select id="add_courseNature" class="easyui-combobox"
											data-options="editable: false,  panelHeight: 100, width: 80, height: 30"
											name="courseNature">
						<option value="任意选修">任意选修</option>
						<option value="公共基础">公共基础</option>
						<option value="专业基础">专业基础</option>
						<option value="专业选修">专业选修</option>
					</select></td>
				</tr>
				<tr>
					<td>课程学时:</td>
					<td><input id="add_courseHour" style="width: 200px; height: 30px;" class="easyui-textbox" type="text" name="courseHour" /></td>
				</tr>
	    	</table>
	    </form>
	</div>
</body>
</html>