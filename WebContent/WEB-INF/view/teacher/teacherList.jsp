<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta charset="UTF-8">
	<title>教师列表</title>
	<link rel="stylesheet" type="text/css" href="easyui/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="easyui/themes/icon.css">
	<link rel="stylesheet" type="text/css" href="easyui/css/demo.css">
	<script type="text/javascript" src="easyui/jquery.min.js"></script>
	<script type="text/javascript" src="easyui/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="easyui/js/validateExtends.js"></script>
	<script type="text/javascript">
	$(function() {	
		var table;
		
		//datagrid初始化 
	    $('#dataList').datagrid({ 
	        title:'教师列表', 
	        iconCls:'icon-more',//图标 
	        border: true, 
	        collapsible:false,//是否可折叠的 
	        fit: true,//自动大小 
	        method: "post",
	        url:"TeacherServlet?method=TeacherList&t="+new Date().getTime(),
	        idField:'id', 
	        singleSelect:false,//是否单选 
	        pagination:true,//分页控件 
	        rownumbers:true,//行号 
	        sortName:'id',
	        sortOrder:'DESC', 
	        remoteSort: false,
	        columns: [[  
				{field:'chk',checkbox: true,width:50},
 		        {field:'id',title:'ID',width:50, sortable: true},      
 		        {field:'name',title:'姓名',width:150},
 		        {field:'sex',title:'性别',width:100},
 		        {field:'entryYear',title:'入职年份',width:150},
 		        {field:'jobTitle',title:'职称',width:150},
 		        {field:'courseList',title:'课程',width:800, 
 		        	formatter: function(value,row,index){
 						if (row.courseList){
 							var courseList = row.courseList;
 							var course = "";
 							for(var i = 0;i < courseList.length;i++){
 								var professionName = courseList[i].profession.name;
 								//var clazzName = courseList[i].clazz.name;
 								var courseName = courseList[i].course.name;
 								course += "[" + professionName + " " + courseName + "] &nbsp;&nbsp;&nbsp;";
 							}
 							return course;
 						} else {
 							return value;
 						}
 					}	
 		        }
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
	    	table = $("#addTable");
	    	$("#addDialog").dialog("open");
	    });
	    //修改
	    $("#edit").click(function(){
	    	table = $("#editTable");
	    	var selectRows = $("#dataList").datagrid("getSelections");
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
            		ids[i] = row.id;
            	});
            	$.messager.confirm("消息提醒", "将删除与教师相关的所有数据，确认继续？", function(r){
            		if(r){
            			$.ajax({
							type: "post",
							url: "TeacherServlet?method=DeleteTeacher",
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
	    
	  	//设置添加窗口
	    $("#addDialog").dialog({
	    	title: "添加教师",
	    	width: 850,
	    	height: 550,
	    	iconCls: "icon-add",
	    	modal: true,
	    	collapsible: false,
	    	minimizable: false,
	    	maximizable: false,
	    	draggable: true,
	    	closed: true,
	    	buttons: [
				{
					text:'设置课程',
					plain: true,
					iconCls:'icon-book-add',
					handler:function(){
						$("#chooseCourseDialog").dialog("open");
					}
				},
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
							var chooseCourse = [];
							$(table).find(".chooseTr").each(function(){
								var professionid = $(this).find("input[textboxname='professionid']").attr("professionId");
								//var clazzid = $(this).find("input[textboxname='clazzid']").attr("clazzId");
								var courseid = $(this).find("input[textboxname='courseid']").attr("courseId");
								var course = professionid+"_"+courseid;
								chooseCourse.push(course);
							});
							var id = $("#add_id").textbox("getText");
							var name = $("#add_name").textbox("getText");
							var sex = $("#add_sex").textbox("getText");
							var entryYear = $("#add_entryYear").textbox("getText");
							var jobTitle = $("#add_jobTitle").textbox("getText");
							var data = {id:id, name:name,sex:sex,entryYear:entryYear,jobTitle:jobTitle,course:chooseCourse};
							
							$.ajax({
								type: "post",
								url: "TeacherServlet?method=AddTeacher",
								data: data,
								success: function(msg){
									if(msg == "success"){
										$.messager.alert("消息提醒","添加成功!","info");
										//关闭窗口
										$("#addDialog").dialog("close");
										//清空原表格数据
										$("#add_id").textbox('setValue', "");
										$("#add_name").textbox('setValue', "");
										$("#add_sex").textbox('setValue', "男");
										$("#add_entryYear").textbox('setValue', "");
										$("#add_jobTitle").textbox('setValue', "");
										$(table).find(".chooseTr").remove();
										
										//重新刷新页面数据
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
					iconCls:'icon-reload',
					handler:function(){
						$("#add_id").textbox('setValue', "");
						$("#add_name").textbox('setValue', "");
						$("#add_entryYear").textbox('setValue', "");
						$("#add_jobTitle").textbox('setValue', "");
						
						$(table).find(".chooseTr").remove();
						
					}
				},
			],
			onClose: function(){
				$("#add_id").textbox('setValue', "");
				$("#add_name").textbox('setValue', "");
				$("#add_entryYear").textbox('setValue', "");
				$("#add_jobTitle").textbox('setValue', "");
				
				$(table).find(".chooseTr").remove();
			}
	    });
	  	
	  	//设置课程窗口
	    $("#chooseCourseDialog").dialog({
	    	title: "设置课程",
	    	width: 400,
	    	height: 300,
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
			    		//添加之前先判断是否已选择该课程
						var chooseCourse = [];
						$(table).find(".chooseTr").each(function(){
							var professionid = $(this).find("input[textboxname='professionid']").attr("professionId");
							//var clazzid = $(this).find("input[textboxname='clazzid']").attr("clazzId");
							var courseid = $(this).find("input[textboxname='courseid']").attr("courseId");
							var course = professionid+"_"+courseid;
							chooseCourse.push(course);
						});
						//获取新选择的课程
			    		var professionId = $("#add_professionList").combobox("getValue");
			    		//var clazzId = $("#add_clazzList").combobox("getValue");
			    		var courseId = $("#add_courseList").combobox("getValue");
						var newChoose = professionId+"_"+courseId;
						for(var i = 0;i < chooseCourse.length;i++){
							if(newChoose == chooseCourse[i]){
								$.messager.alert("消息提醒","已选择该门课程!","info");
								return;
							}
						}
						
						//添加到表格显示
						var tr = $("<tr class='chooseTr'><td>课程:</td></tr>");
						
			    		var professionName = $("#add_professionList").combobox("getText");
			    		var professionTd = $("<td></td>");
			    		var professionInput = $("<input style='width: 200px; height: 30px;' data-options='readonly: true' class='easyui-textbox' name='professionid' />").val(professionName).attr("professionId", professionId);
			    		$(professionInput).appendTo(professionTd);
			    		$(professionTd).appendTo(tr);
			    		
			    		//var clazzName = $("#add_clazzList").combobox("getText");
			    		//var clazzTd = $("<td></td>");
			    		//var clazzInput = $("<input style='width: 200px; height: 30px;' data-options='readonly: true' class='easyui-textbox' name='clazzid' />").val(clazzName).attr("clazzId", clazzId);
			    		//$(clazzInput).appendTo(clazzTd);
			    		//$(clazzTd).appendTo(tr);
			    		
			    		var courseName = $("#add_courseList").combobox("getText");
			    		var courseTd = $("<td></td>");
			    		var courseInput = $("<input style='width: 200px; height: 30px;' data-options='readonly: true' class='easyui-textbox' name='courseid' />").val(courseName).attr("courseId", courseId);
			    		$(courseInput).appendTo(courseTd);
			    		$(courseTd).appendTo(tr);
			    		
			    		var removeTd = $("<td></td>");
			    		var removeA = $("<a href='javascript:;' class='easyui-linkbutton removeBtn'></a>").attr("data-options", "iconCls:'icon-remove'");
			    		$(removeA).appendTo(removeTd);
			    		$(removeTd).appendTo(tr);
			    		
			    		$(tr).appendTo(table);
			    		
			    		//解析
			    		$.parser.parse($(table).find(".chooseTr :last"));
			    		//关闭窗口
			    		$("#chooseCourseDialog").dialog("close");
					}
				}
			]
	    });
	  	
	  //下拉框通用属性
	  	$("#add_professionList, #add_courseList").combobox({
	  		width: "200",
	  		height: "30",
	  		valueField: "id",
	  		textField: "name",
	  		multiple: false, //不可多选
	  		editable: false, //不可编辑
	  		method: "post",
	  	});
	  	
	  	$("#add_professionList").combobox({
	  		url: "ProfessionServlet?method=ProfessionList&t="+new Date().getTime(),
	  		onChange: function(newValue, oldValue){
	  			//加载该年级下的班级
	  			//$("#add_clazzList").combobox("clear");
	  			//$("#add_clazzList").combobox("options").queryParams = {gradeid: newValue};
	  			//$("#add_clazzList").combobox("reload");
	  			
	  			//加载该年级下的课程
	  			$("#add_courseList").combobox("clear");
	  			$("#add_courseList").combobox("options").queryParams = {professionid: newValue};
	  			$("#add_courseList").combobox("reload");
	  		},
			onLoadSuccess: function(){
				//默认选择第一条数据
				var data = $(this).combobox("getData");
				$(this).combobox("setValue", data[0].id);
	  		}
	  	});
	  	
	  	//$("#add_clazzList").combobox({
	  		//url: "ClazzServlet?method=ClazzList&t="+new Date().getTime(),
	  		//onLoadSuccess: function(){
				//默认选择第一条数据
				//var data = $(this).combobox("getData");
				//$(this).combobox("setValue", data[0].id);
	  		//}
	  	//});
	  	
	  	$("#add_courseList").combobox({
	  		url: "CourseServlet?method=CourseList&t="+new Date().getTime(),
	  		onLoadSuccess: function(){
		  		//默认选择第一条数据
				var data
				= $(this).combobox("getData");;
				$(this).combobox("setValue", data[0].id);
	  		}
	  	});
	  	
	  	//编辑教师信息
	  	$("#editDialog").dialog({
	  		title: "修改教师信息",
	    	width: 850,
	    	height: 550,
	    	iconCls: "icon-edit",
	    	modal: true,
	    	collapsible: false,
	    	minimizable: false,
	    	maximizable: false,
	    	draggable: true,
	    	closed: true,
	    	buttons: [
				{
					text:'设置课程',
					plain: true,
					iconCls:'icon-book-add',
					handler:function(){
						$("#chooseCourseDialog").dialog("open");
					}
				},
	    		{
					text:'提交',
					plain: true,
					iconCls:'icon-user_add',
					handler:function(){
						var validate = $("#editForm").form("validate");
						if(!validate){
							$.messager.alert("消息提醒","请检查你输入的数据!","warning");
							return;
						} else{
							var chooseCourse = [];
							$(table).find(".chooseTr").each(function(){
								var professionid = $(this).find("input[textboxname='professionid']").attr("professionId");
								//var clazzid = $(this).find("input[textboxname='clazzid']").attr("clazzId");
								var courseid = $(this).find("input[textboxname='courseid']").attr("courseId");
								var course = professionid+"_"+courseid;
								chooseCourse.push(course);
							});
							//var id = $("#dataList").datagrid("getSelected").id;
							var id = $("#edit_id").textbox("getText");
							var name = $("#edit_name").textbox("getText");
							var sex = $("#edit_sex").textbox("getText");
							var entryYear = $("#edit_entryYear").textbox("getText");
							var jobTitle = $("#edit_jobTitle").textbox("getText");
							var data = {id:id, name:name,sex:sex,entryYear:entryYear,jobTitle:jobTitle,course:chooseCourse};
							
							$.ajax({
								type: "post",
								url: "TeacherServlet?method=EditTeacher",
								data: data,
								success: function(msg){
									if(msg == "success"){
										$.messager.alert("消息提醒","修改成功!","info");
										//关闭窗口
										$("#editDialog").dialog("close");
										//清空原表格数据
										$("#edit_id").textbox('setValue', "");
										$("#edit_name").textbox('setValue', "");
										$("#edit_sex").textbox('setValue', "男");
										$("#edit_entryYear").textbox('setValue', "");
										$("#edit_jobTitle").textbox('setValue', "");
										$(table).find(".chooseTr").remove();
										
										//重新刷新页面数据
							  			$('#dataList').datagrid("reload");
							  			$('#dataList').datagrid("uncheckAll");
										
									} else{
										$.messager.alert("消息提醒","修改失败!","warning");
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
						$("#edit_name").textbox('setValue', "");
						$("#edit_enrtyYear").textbox('setValue', "");
						$("#edit_jobTitle").textbox('setValue', "");
						
						$(table).find(".chooseTr").remove();
						
					}
				},
			],
			onBeforeOpen: function(){
				var selectRow = $("#dataList").datagrid("getSelected");
				//设置值
				$("#edit_id").textbox('setValue', selectRow.id);
				$("#edit_name").textbox('setValue', selectRow.name);
				$("#edit_sex").textbox('setValue', selectRow.sex);
				$("#edit_entryYear").textbox('setValue', selectRow.entryYear);
				$("#edit_jobTitle").textbox('setValue', selectRow.jobTitle);
				//$("#edit_photo").attr("src", "PhotoServlet?method=GetPhoto&type=3&number="+selectRow.number);
				
				var courseList = selectRow.courseList;
				
				for(var i = 0;i < courseList.length;i++){
					var professionId = courseList[i].profession.id;
					var professionName = courseList[i].profession.name;
					//var clazzId = courseList[i].clazz.id;
					//var clazzName = courseList[i].clazz.name;
					var courseId = courseList[i].course.id;
					var courseName = courseList[i].course.name;
					//添加到表格显示
					var tr = $("<tr class='chooseTr'><td>课程:</td></tr>");
					
		    		var professionTd = $("<td></td>");
		    		var professionInput = $("<input style='width: 200px; height: 30px;' data-options='readonly: true' class='easyui-textbox' name='professionid' />").val(professionName).attr("professionId", professionId);
		    		$(professionInput).appendTo(professionTd);
		    		$(professionTd).appendTo(tr);
		    		
		    		//var clazzTd = $("<td></td>");
		    		//var clazzInput = $("<input style='width: 200px; height: 30px;' data-options='readonly: true' class='easyui-textbox' name='clazzid' />").val(clazzName).attr("clazzId", clazzId);
		    		//$(clazzInput).appendTo(clazzTd);
		    		//$(clazzTd).appendTo(tr);
		    		
		    		var courseTd = $("<td></td>");
		    		var courseInput = $("<input style='width: 200px; height: 30px;' data-options='readonly: true' class='easyui-textbox' name='courseid' />").val(courseName).attr("courseId", courseId);
		    		$(courseInput).appendTo(courseTd);
		    		$(courseTd).appendTo(tr);
		    		
		    		var removeTd = $("<td></td>");
		    		var removeA = $("<a href='javascript:;' class='easyui-linkbutton removeBtn'></a>").attr("data-options", "iconCls:'icon-remove'");
		    		$(removeA).appendTo(removeTd);
		    		$(removeTd).appendTo(tr);
		    		
		    		$(tr).appendTo(table);
		    		
		    		//解析
		    		$.parser.parse($(table).find(".chooseTr :last"));
					
				}
				
			},
			onClose: function(){
				$("#edit_name").textbox('setValue', "");
				$("#edit_entryYear").textbox('setValue', "");
				$("#edit_jobTitle").textbox('setValue', "");
				
				$(table).find(".chooseTr").remove();
			}
	    });
	   	
	  	// 一行选择课程
	  	$(".removeBtn").live("click", function(){
	  		$(this).parents(".chooseTr").remove();
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
		<div style="float: left;"><a id="edit" href="javascript:;" class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:true">修改</a></div>
			<div style="float: left;" class="datagrid-btn-separator"></div>
		<div><a id="delete" href="javascript:;" class="easyui-linkbutton" data-options="iconCls:'icon-some-delete',plain:true">删除</a></div>
	</div>
	
	<!-- 添加窗口 -->
	<div id="addDialog" style="padding: 10px;">  
   		<form id="addForm" method="post">
	    	<table id="addTable" border=0 style="width:800px; table-layout:fixed;" cellpadding="6" >
	    		<tr>
	    			<td style="width:40px">ID:</td>
	    			<td colspan="3">
	    				<input id="add_id"  class="easyui-textbox" style="width: 200px; height: 30px;" type="text" name="id" data-options="required:true, validType:'repeat', missingMessage:'请输入工号'" />
	    			</td>
	    			<td style="width:80px"></td>
	    		</tr>
	    		<tr>
	    			<td>姓名:</td>
	    			<td colspan="4"><input id="add_name" style="width: 200px; height: 30px;" class="easyui-textbox" type="text" name="name" data-options="required:true, missingMessage:'请填写姓名'" /></td>
	    		</tr>
	    		<tr>
	    			<td>性别:</td>
	    			<td colspan="4"><select id="add_sex" class="easyui-combobox" data-options="editable: false, panelHeight: 50, width: 60, height: 30" name="sex"><option value="男">男</option><option value="女">女</option></select></td>
	    		</tr>
	    		<tr>
	    			<td>入职年份:</td>
	    			<td colspan="4"><input id="add_entryYear" style="width: 200px; height: 30px;" class="easyui-textbox" type="text" name="entryYear" /></td>
	    		</tr>
	    		<tr>
	    			<td>职称:</td>
	    			<td colspan="4"><input id="add_jobTitle" style="width: 200px; height: 30px;" class="easyui-textbox" type="text" name="jobTitle" /></td>
	    		</tr>
	    	</table>
	    </form>
	</div>
	
	<!-- 设置课程 -->
	<div id="chooseCourseDialog" style="padding: 10px">
	   	<table cellpadding="8" >
	   		<tr>
	   			<td>专业：</td>
	   			<td><input id="add_professionList" style="width: 200px; height: 30px;" class="easyui-combobox" name="professionid" /></td>
	   		</tr>
	   		<tr>
	   			<td>课程：</td>
	   			<td><input id="add_courseList" style="width: 200px; height: 30px;" class="easyui-combobox" name="courseid" /></td>
	   		</tr>
	   	</table>
	</div>
	
	<!-- 修改窗口 -->
	<div id="editDialog" style="padding: 10px">
    	<form id="editForm" method="post">
	    	<table id="editTable" border=0 style="width:800px; table-layout:fixed;" cellpadding="6" >
	    		<tr>
	    			<td style="width:40px">ID:</td>
	    			<td colspan="3"><input id="edit_id" data-options="readonly: true" class="easyui-textbox" style="width: 200px; height: 30px;" type="text" name="id" data-options="required:true, validType:'repeat', missingMessage:'请输入工号'" /></td>
	    			<td style="width:80px"></td>
	    		</tr>
	    		<tr>
	    			<td>姓名:</td>
	    			<td><input id="edit_name" style="width: 200px; height: 30px;" class="easyui-textbox" type="text" name="name" data-options="required:true, missingMessage:'请填写姓名'" /></td>
	    		</tr>
	    		<tr>
	    			<td>性别:</td>
	    			<td><select id="edit_sex" class="easyui-combobox" data-options="editable: false, panelHeight: 50, width: 60, height: 30" name="sex"><option value="男">男</option><option value="女">女</option></select></td>
	    		</tr>
	    		<tr>
	    			<td>入职年份:</td>
	    			<td><input id="edit_entryYear" style="width: 200px; height: 30px;" class="easyui-textbox" type="text" name="entryYear" /></td>
	    		</tr>
	    		<tr>
	    			<td>职称:</td>
	    			<td colspan="4"><input id="edit_jobTitle" style="width: 200px; height: 30px;" class="easyui-textbox" type="text" name="jobTitle" /></td>
	    		</tr>
	    	</table>
	    </form>
	</div>
	
	
</body>
</html>