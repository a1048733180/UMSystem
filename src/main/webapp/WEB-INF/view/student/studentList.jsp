<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta charset="UTF-8">
	<title>学生列表</title>
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
	        title:'学生列表', 
	        iconCls:'icon-more',//图标 
	        border: true, 
	        collapsible:false,//是否可折叠的 
	        fit: true,//自动大小 
	        method: "post",
	        url:"StudentServlet?method=StudentList&t="+new Date().getTime(),
	        idField:'studentId',
	        singleSelect:false,//是否单选 
	        pagination:true,//分页控件 
	        rownumbers:true,//行号 
	        sortName:'studentId',
	        sortOrder:'DESC', 
	        remoteSort: false,
	        columns: [[  
				{field:'chk',checkbox: true,width:50},
 		        {field:'studentId',title:'学号',width:200, sortable: true},
 		        {field:'studentName',title:'姓名',width:200},
 		        {field:'studentSex',title:'性别',width:100},
 		        {field:'studentBirthday',title:'出生年月',width:100},
 		        {field:'studentProfession',title:'专业',width:200,
 		        	formatter: function(value,row,index){
 						if (row.studentProfession){
 							return row.studentProfession.professionName;
 						} else {
 							return value;
 						}
 					}
				},
 		        {field:'studentTel',title:'联系方式',width:200},
	 		]], 
	        toolbar: "#toolbar"
	    }); 
	    //设置分页控件 
	    var p = $('#dataList').datagrid('getPager'); 
	    $(p).pagination({ 
	        pageSize: 10,//每页显示的记录条数，默认为10 
	        pageList: [10,20,30,50,100],//可以设置每页记录条数的列表 
	        beforePageText: '第',//页数文本框前显示的汉字 
	        afterPageText: '页    共 {pages} 页', 
	        displayMsg: '当前显示 {from} - {to} 条记录   共 {total} 条记录', 
	    }); 
	    //设置工具类按钮
	    $("#add").click(function(){
	    	$("#addDialog").dialog("open");
	    });
	    //修改
	    $("#edit").click(function(){
	    	var selectRows = $ ("#dataList").datagrid("getSelections");
        	if(selectRows.length != 1){
            	$.messager.alert("消息提醒", "请选择一条数据进行操作!", "warning");
            } else{
		    	$("#editDialog").dialog("open");
            }
	    });
	    //删除
	    $("#delete").click(function(){
	    	var selectRows = $("#dataList").datagrid("getSelections");
        	var selectLength = selectRows.length;
        	if(selectLength == 0){
            	$.messager.alert("消息提醒", "请选择数据进行删除!", "warning");
            } else{
            	var ids = [];
            	$(selectRows).each(function(i, row){
            		ids[i] = row.studentId;
            	});
            	$.messager.confirm("消息提醒", "将删除与学生相关的所有数据，确认继续？", function(r){
            		if(r){
            			$.ajax({
							type: "post",
							url: "StudentServlet?method=DeleteStudent",
							data: {ids: ids},
							success: function(msg){
								if(msg == "success"){
									$.messager.alert("消息提醒","删除成功!","info");
									//刷新表格
									$("#dataList").datagrid("reload");
									$("#dataList").datagrid("uncheckAll");
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
	    
	  	//专业下拉框
	  	$("#professionList").combobox({
	  		width: "150",
	  		height: "25",
	  		valueField: "professionId",
	  		textField: "professionName",
	  		multiple: false, //可多选
	  		editable: false, //不可编辑
	  		method: "post",
	  		url: "ProfessionServlet?method=ProfessionList&t="+new Date().getTime(),
	  		onChange: function(newValue, oldValue){
	  			//加载该专业下的学生
	  			$('#dataList').datagrid("options").queryParams = {professionid: newValue};
	  			$('#dataList').datagrid("reload");
	  			
	  			
	  		}
	  	}); 
	  		  	
	  	//下拉框通用属性
	  	$("#add_professionList, #edit_professionList").combobox({
	  		width: "200",
	  		height: "20",
	  		valueField: "professionId",
	  		textField: "professionName",
	  		multiple: false, //可多选
	  		editable: false, //不可编辑
	  		method: "post",
	  	});
	  	
	  	// 添加板块中的专业模块
	  	  $("#add_professionList").combobox({
	  		url: "ProfessionServlet?method=ProfessionList&t="+new Date().getTime(),
			onLoadSuccess: function(){
				//默认选择第一条数据
				var data = $(this).combobox("getData");
				$(this).combobox("setValue", data[0].professionId);
	  		}
	  	});  
	  	  
	  	
	  	// 修改板块中的专业模块
	  	$("#edit_professionList").combobox({
	  		url: "ProfessionServlet?method=ProfessionList&t="+new Date().getTime(),
			onLoadSuccess: function(){
				//默认选择第一条数据
				var data = $(this).combobox("getData");
				$(this).combobox("setValue", data[0].id);
	  		}
	  	});
	  	  	
	  	//设置添加学生窗口
	    $("#addDialog").dialog({
	    	title: "添加学生",
	    	width: 650,
	    	height: 460,
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
					iconCls:'icon-user_add',
					handler:function(){
						var validate = $("#addForm").form("validate");
						if(!validate){
							$.messager.alert("消息提醒","请检查你输入的数据!","warning");
							return;
						} else{
							var professionid = $("#add_professionList").combobox("getValue");
							$.ajax({
								type: "post",
								url: "StudentServlet?method=AddStudent",
								data: $("#addForm").serialize(),
								success: function(msg){
									if(msg == "success"){
										$.messager.alert("消息提醒","添加成功!","info");
										//关闭窗口
										$("#addDialog").dialog("close");
										//清空原表格数据
										$("#add_studentId").textbox('setValue', "");
										$("#add_studentName").textbox('setValue', "");
										$("#add_studentSex").textbox('setValue', "男");
										$("#add_stuBirthday").textbox('setValue', "");
										$("#add_stuTel").textbox('setValue', "");
										
										//重新刷新页面数据
										//$('#dataList').datagrid("options").queryParams = {clazzid: clazzid};
							  			$('#dataList').datagrid("reload");
							  			$("#professionList").combobox('setValue', professionid);
										
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
					iconCls:'icon-reload',
					handler:function(){
						$("#add_studentId").textbox('setValue', "");
						$("#add_studentName").textbox('setValue', "");
						$("#add_studentAge").textbox('setValue', "");
						//重新加载专业
						$("#add_professionList").combobox("clear");
						$("#add_professionList").combobox("reload");
						 $("#add_studentTel").textbox('setValue', "");
					}
				},
			]
	    });
	  	
	  	//设置编辑学生窗口
	    $("#editDialog").dialog({
	    	title: "修改学生信息",
	    	width: 650,
	    	height: 460,
	    	iconCls: "icon-edit",
	    	modal: true,
	    	collapsible: false,
	    	minimizable: false,
	    	maximizable: false,
	    	draggable: true,
	    	closed: true,
	    	buttons: [
	    		{
					text:'提交',
					plain: true,
					iconCls:'icon-user_add',
					handler:function(){
						var validate = $("#editForm").form("validate");
						var professionid = $("#edit_professionList").combobox("getValue");
						if(!validate){
							$.messager.alert("消息提醒","请检查你输入的数据!","warning");
							return;
						} else{
							$.ajax({
								type: "post",
								url: "StudentServlet?method=EditStudent&t="+new Date().getTime(),
								data: $("#editForm").serialize(),
								success: function(msg){
									if(msg == "success"){
										$.messager.alert("消息提醒","更新成功!","info");
										//关闭窗口
										$("#editDialog").dialog("close");
										//刷新表格
										//$('#dataList').datagrid("options").queryParams = {clazzid: clazzid};
										$("#dataList").datagrid("reload");
										$("#dataList").datagrid("uncheckAll");
										
										$("#professionList").combobox('setValue', professionid);
							  			
									} else{
										$.messager.alert("消息提醒","更新失败!","warning");
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
					iconCls:'icon-reload',
					handler:function(){
						//清空表单
						$("#edit_studentId").textbox('setValue', "");
						$("#edit_studentSex").textbox('setValue', "男");
						$("#edit_studentBirthday").textbox('setValue', "");
						$("#edit_professionList").combobox("clear");
						$("#edit_professionList").combobox("reload");
						 $("#add_studentTel").textbox('setValue', "");
					}
				}
			],
			onBeforeOpen: function(){
				var selectRow = $("#dataList").datagrid("getSelected");
				//设置值
				$("#edit_studentId").textbox('setValue', selectRow.studentId);
				$("#edit_studentName").textbox('setValue', selectRow.studentName);
				$("#edit_studentSex").textbox('setValue', selectRow.studentSex);
				$("#edit_studentBirthday").textbox('setValue', selectRow.studentBirthday);
				var professionid = selectRow.studentProfession.professionId;
				$("#edit_professionList").combobox('setValue', professionid);
				$("#edit_studentTel").textbox('setValue', selectRow.studentTel);
			}
	    });
	   
	});
	</script>
</head>
<body>
	<!-- 学生列表 -->
	<table id="dataList" cellspacing="0" cellpadding="0"> 
	    
	</table> 
	<!-- 工具栏 -->
	<div id="toolbar">
		<div style="float: left;"><a id="add" href="javascript:;" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true">添加</a></div>
			<div style="float: left;" class="datagrid-btn-separator"></div>
		<div style="float: left;"><a id="edit" href="javascript:;" class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:true">修改</a></div>
			<div style="float: left;" class="datagrid-btn-separator"></div>
		<div style="float: left;"><a id="delete" href="javascript:;" class="easyui-linkbutton" data-options="iconCls:'icon-some-delete',plain:true">删除</a></div>
		
		<div style="float: left; margin: 0 10px 0 10px">专业：<input id="professionList" class="easyui-textbox" name="profession" /></div>
		
	
	</div>
	
	<!-- 添加学生窗口 -->
	<div id="addDialog" style="padding: 10px">  
    	<form id="addForm" method="post">
	    	<table cellpadding="8" >
	    		<tr>
	    			<td>学号:</td>
	    			<td>
	    				<input id="add_studentId"  class="easyui-textbox" style="width: 200px; height: 30px;" type="text" name="id" data-options="required:true, validType:'repeat', missingMessage:'请输入学号'" />
	    			</td>
	    		</tr>
	    		<tr>
	    			<td>姓名:</td>
	    			<td><input id="add_studentName" style="width: 200px; height: 30px;" class="easyui-textbox" type="text" name="name" data-options="required:true, missingMessage:'请填写姓名'" /></td>
	    		</tr>
	    		<tr>
	    			<td>性别:</td>
	    			<td><select id="add_studentSex" class="easyui-combobox" data-options="editable: false, panelHeight: 50, width: 60, height: 30" name="sex"><option value="男">男</option><option value="女">女</option></select></td>
	    		</tr>
	    		<tr>
	    			<td>出生年月:</td>
	    			<td><input id="add_studentBirthday" style="width: 200px; height: 30px;" class="easyui-textbox" type="text" name="birthday"  /></td>
	    		</tr>
	    		<tr>
	    			<td>专业:</td>
	    			<td><input id="add_professionList" style="width: 200px; height: 30px;" class="easyui-textbox" name="professionid" /></td>
	    		</tr>
	    		<tr>
	    			<td>联系方式:</td>
	    			<td><input id="add_studentTel" style="width: 200px; height: 50px;" class="easyui-textbox" name="tel" /></td>
	    		</tr>
	    	</table>
	    </form>
	</div>
	
	<!-- 修改学生窗口 -->
	<div id="editDialog" style="padding: 10px"> 
    	<form id="editForm" method="post">
	    	<table cellpadding="8" >
	    		<tr>
	    			<td>学号:</td>
	    			<td>
	    				<input id="edit_studentId" data-options="readonly: true" class="easyui-textbox" style="width: 200px; height: 30px;" type="text" name="id" data-options="required:true, validType:'repeat', missingMessage:'请输入学号'" />
	    			</td>
	    		</tr>
	    		<tr>
	    			<td>姓名:</td>
	    			<td><input id="edit_studentName" style="width: 200px; height: 30px;" class="easyui-textbox" type="text" name="name" data-options="required:true, missingMessage:'请填写姓名'" /></td>
	    		</tr>
	    		<tr>
	    			<td>性别:</td>
	    			<td><select id="edit_studentSex" class="easyui-combobox" data-options="editable: false, panelHeight: 50, width: 60, height: 30" name="sex"><option value="男">男</option><option value="女">女</option></select></td>
	    		</tr>
	    		<tr>
	    			<td>出生年月:</td>
	    			<td><input id="edit_studentBirthday" style="width: 200px; height: 30px;" class="easyui-textbox" type="text" name="birthday"  /></td>
	    		</tr>
	    		<tr>
	    			<td>专业:</td>
	    			<td><input id="edit_professionList" style="width: 200px; height: 30px;" class="easyui-textbox" name="professionid" /></td>
	    		</tr>
	    		<tr>
	    			<td>联系方式:</td>
	    			<td><input id="edit_studentTel" style="width: 200px; height: 50px;" class="easyui-textbox" name="tel" /></td>
	    		</tr>
	    	</table>
	    </form>
	</div>
	
</body>
</html>