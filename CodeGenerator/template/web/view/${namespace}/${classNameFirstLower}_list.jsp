<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<#include "/macro.include"/>
<#include "/custom.include"/> 
<#assign table = table>
<#assign dbTableAlias = table.tableAlias>
<#assign className = table.className>   
<#assign namespace = namespace>   
<#assign classNameLower = className?uncap_first>
<#assign actionExtension = "action"> 
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>${dbTableAlias}管理</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is ${dbTableAlias}管理">
	<script src="<%=basePath%>/scripts/boot.js" type="text/javascript"></script>

  </head>
  
  <body>
    <div style="width:100%;">
        <div class="mini-toolbar" style="border-bottom:0;padding:0px;">
            <table style="width:99%;">
                 <tr>
                    <td style="white-space:nowrap;">
                    	<form>
                    	<fieldset style="color:#333;border:#06c dashed 1px;">
	                   		<table style="width:100%;">
	                   			<#list table.notPkColumns?chunk(4) as row>
	                   			<tr>
	               					<#list row as column>
	             					<td style="width:10%;font-size:12px;text-align:right;">${column.columnAlias}：</td>
	             					<td style="width:15%;text-align:left;font-size:12px;">
	             						<#if column.isDateTimeColumn>
	             						从&nbsp;<input id="start${column.columnName}Key" class="mini-datepicker" emptyText="请输入时间" onenter="onKeyEnter"/>
	             						至&nbsp;<input id="end${column.columnName}Key" class="mini-datepicker" emptyText="请输入时间" onenter="onKeyEnter"/>
				                    	</#if>
				                    	<#if !column.isDateTimeColumn>
				                    		<#if column.columnNameLower != "state">
				                        		<input id="${column.columnNameLower}Key" class="mini-textbox" emptyText="请输入${column.columnAlias}" onenter="onKeyEnter"/>
				                        	</#if>
				                        	<#if column.columnNameLower = "state">
				                        		<input id="stateKey" class="mini-combobox" data="stateGenders" showNullItem="true"/>
				                        	</#if>
				                        </#if>
	             					</td>
	               					</#list>
	               				</tr>
	               				</#list>
	                   		</table>
                   		</fieldset>
                   		</form>
                    </td>
               	</tr>
               	<tr>
                    <td style="width:99%;">
                    	<a class="mini-button" iconCls="icon-search" onclick="search()">查询</a>
                        <a class="mini-button" iconCls="icon-add" onclick="add()">增加</a>
                        <a class="mini-button" iconCls="icon-edit" onclick="edit()">编辑</a>
                        <a class="mini-button" iconCls="icon-remove" onclick="remove()">删除</a>       
                    </td>
                 </tr>
            </table>           
        </div>
    </div>
    <div style="height:84%">
	    <div id="${classNameLower}_grid" class="mini-datagrid" style="width:100%;height:100%;" allowResize="true"
	        url="<%=basePath%>${pageRequestBase}/${namespace}/${classNameLower}/findByCondition"  idField="id" multiSelect="true" pageSize="15">
	        <div property="columns">
	            <div type="checkcolumn" ></div>   
	            <#list table.notPkColumns as column>
	            <#if column.isDateTimeColumn>
	            <div field="${column.columnNameLower}" width="${100/((table.columns?size)-1)}%" headerAlign="center" dateFormat="yyyy-MM-dd H:mm:ss" allowSort="true">${column.columnAlias}</div>
	            </#if>
				<#if !column.isDateTimeColumn>
					<#if column.columnNameLower != "state">
                <div field="${column.columnNameLower}" width="${100/((table.columns?size)-1)}%" headerAlign="center" allowSort="true">${column.columnAlias}</div>
                    </#if> 
                    <#if column.columnNameLower = "state">
                <div field="${column.columnNameLower}" renderer="onStateGenderRenderer" width="${100/((table.columns?size)-1)}%" headerAlign="center" allowSort="true">${column.columnAlias}</div>
                    </#if>
				</#if>
				</#list>
				<div field="operate_buttons" width="${100/((table.columns?size)-1)}%" headerAlign="center" align="center" allowSort="false" renderer="operateButtonRenderer">操作</div>  
	        </div>
	    </div>
    </div>

    <script type="text/javascript">
	    <#list table.notPkColumns as column>
	    <#if column.columnNameLower = "state">
	 	//状态
		var stateGenders = [{ id: '0', text: '禁用' }, { id: '1', text: '启用'}];
	    </#if>
		</#list>
        mini.parse();

        var grid = mini.get("${classNameLower}_grid");
        grid.load();
        
        <#list table.notPkColumns as column>
        <#if column.columnNameLower = "state">
		//渲染状态字段
       	function onStateGenderRenderer(e) {
            for (var i = 0, l = stateGenders.length; i < l; i++) {
                var g = stateGenders[i];
                if (g.id == e.value+""){
                	return g.text;
                } 
            }
            return "";
        }
        </#if>
		</#list>
        
      	//操作按钮集中渲染
        function operateButtonRenderer(e) {
			var grid = e.sender;
		   	var record = e.record;
		   	<#list table.pkColumns as column>
		   	var u${column.columnNameLower}=record.${column.columnNameLower}<#if column_has_next>;</#if>
		   	</#list> 
		   	var rowIndex = e.rowIndex;
		   	//查看详情
		   	var s = '<span class="icon-zoomin" style="cursor:pointer;" title="查看详情" onclick="showRowDetail(\''+<#list table.pkColumns as column>u${column.columnNameLower}<#if column_has_next>+'\',\''+</#if></#list> +'\')">&nbsp;&nbsp;&nbsp;</span>';
		    //删除行
		   	//s+='&nbsp;&nbsp;<span class="icon-remove" title="删除记录" onclick="remove('+uid+')">&nbsp;&nbsp;&nbsp;</span>';;
		   	//启用禁用
		   	/*
            var s =  '  <a class="mini-button" iconCls="icon-lock" onclick="enabled('+uid+')" style="width:30px;margin-right:10px;">启用</a>'
                    + ' <a class="mini-button" iconCls="icon-unlock" onclick="disable('+uid+')" style="width:30px;">禁用</a> ';
             */
            //新建、编辑、删除
            /*
            var s = '<a class="New_Button" href="javascript:newRow()">New</a>'
                   + ' <a class="Edit_Button" href="javascript:editRow(\'' + uid + '\')" >Edit</a>'
                   + ' <a class="Delete_Button" href="javascript:delRow(\'' + uid + '\')">Delete</a>';
             */
            return s;
        }
      	//查看详情方法
        function showRowDetail(<#list table.pkColumns as column>u${column.columnNameLower}<#if column_has_next>,</#if></#list> ){
            if (<#list table.pkColumns as column>u${column.columnNameLower}<#if column_has_next>&&</#if></#list> ) {
                mini.open({
                    url: "<%=basePath%>view/${namespace}/${classNameLower}_detail.jsp",
                    title: "查看${dbTableAlias}详情", 
                    width: 800, 
                    height: 520,
                    onload: function () {
                        var iframe = this.getIFrameEl();
                        var data = { action: "detail", <#list table.pkColumns as column>${column.columnNameLower}:u${column.columnNameLower}<#if column_has_next>,</#if></#list>  };
                        //给表单赋值
                        iframe.contentWindow.setData(data);
                    },
                    ondestroy: function (action) {
                        grid.reload();
                        
                    }
                });
            } else {
                mini.alert("行标识为空，无法查看详情！");
            }
        }
        
        //新增的方法
        function add() {
            mini.open({
                url: "<%=basePath%>view/${namespace}/${classNameLower}_form.jsp",
                title: "新增${dbTableAlias}", 
                width: 600, 
                height: 320,
                onload: function () {
                    var iframe = this.getIFrameEl();
                    var data = { action: "new"};
                    iframe.contentWindow.setData(data);
                },
                ondestroy: function (action) {
                    grid.reload();
                }
            });
        }
        //编辑的方法
        function edit() {
            var row = grid.getSelected();
            if (row) {
                mini.open({
                    url: "<%=basePath%>view/${namespace}/${classNameLower}_form.jsp",
                    title: "编辑${dbTableAlias}", 
                    width: 600, 
                    height: 320,
                    onload: function () {
                        var iframe = this.getIFrameEl();
                        var data = { action: "edit", <#list table.pkColumns as column>${column.columnNameLower}: row.${column.columnNameLower}<#if column_has_next>,</#if></#list> };
                        //给表单赋值
                        iframe.contentWindow.setData(data);
                    },
                    ondestroy: function (action) {
                        grid.reload();
                        
                    }
                });
            } else {
                mini.alert("请选中一条记录");
            }
            
        }
        //删除的方法
        function remove() {
            var rows = grid.getSelecteds();
            if (rows.length > 0) {
            	mini.confirm("确定删除选中记录？","确定？",function(action){
	                if (action == "ok") {
	                	<#list table.pkColumns as column>
	                	var ${column.columnNameLower}s = [];
	                	</#list>
	                    for (var i = 0, l = rows.length; i < l; i++) {
	                        var r = rows[i];
	                        <#list table.pkColumns as column>
	                        	${column.columnNameLower}s.push(r.${column.columnNameLower});
	                       	</#list>
	                    }
	                    grid.loading("操作中，请稍后......");
	                    $.ajax({
	                        url: "<%=basePath%>${pageRequestBase}/${namespace}/${classNameLower}/batchDelete?<#list table.pkColumns as column>${column.columnNameLower}s=" +${column.columnNameLower}s<#if column_has_next>+"&</#if></#list>,
	                        success: function (result) {
	                       		var resultObj = mini.decode(result);
			                	if(!resultObj){
			                		mini.alert("加载异常，请尝试刷新页面");
			                		return;
			                	}
			                    if(resultObj.successful){
				                	mini.alert("删除成功");
			                    }else{
			                    	mini.alert(resultObj.resultMsg);
			                    }
	                        	grid.reload();
	                          		
	                        }
	                    });
	                }
            	});	
            } else {
                mini.alert("请选中一条记录");
            }
        }
        //查询的方法
        function search() {
            <#list table.notPkColumns as column>
            <#if !column.isDateTimeColumn>
            var ${column.columnNameLower}Key = mini.isNull(mini.get("${column.columnNameLower}Key"))?"":mini.get("${column.columnNameLower}Key").getValue();
            </#if>
        	<#if column.isDateTimeColumn>
        	var start${column.columnName} = mini.isNull(mini.get("start${column.columnName}Key"))?"":mini.get("start${column.columnName}Key").getValue();
            var end${column.columnName} = mini.isNull(mini.get("end${column.columnName}Key"))?"":mini.get("end${column.columnName}Key").getValue();
        	</#if>
        	</#list>
            grid.load({ 
            	<#list table.notPkColumns as column>
            	<#if !column.isDateTimeColumn>
            	${column.columnNameLower}: ${column.columnNameLower}Key <#if column_has_next>,</#if>
            	</#if>
            	<#if column.isDateTimeColumn>
            	start${column.columnName}:(start${column.columnName}?start${column.columnName}.Format("yyyy-MM-dd"):"") ,
            	end${column.columnName}:(end${column.columnName}?end${column.columnName}.Format("yyyy-MM-dd"):"") ,
            	//${column.columnNameLower}String: (${column.columnNameLower}Key?${column.columnNameLower}Key.Format("yyyy-MM-dd"):"") <#if column_has_next>,</#if>
            	</#if>
            	</#list>
            });
        }
        //键盘事件
        function onKeyEnter(e) {
            search();
        }
    </script>
  </body>
</html>
