<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<#include "/macro.include"/>
<#include "/custom.include"/>
<#assign table = table>
<#assign dbTableAlias = table.tableAlias>
<#assign className = table.className>   
<#assign classNameLower = className?uncap_first>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <base href="<%=basePath%>">
    
    <title>${dbTableAlias}表单</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is ${dbTableAlias}表单">
	<script src="<%=basePath%>/scripts/boot.js" type="text/javascript"></script>
	<style type="text/css">
		body {
			width: 98%;
			height: 98%;
		}
	</style>
</head>
<body>    
    <form id="${classNameLower}_form" method="post">
    <#list table.columns as column>
	<#if column.pk>
		<input name="${column.columnNameLower}" class="mini-hidden" />
	</#if>
	</#list>
        <div style="padding-left:11px;padding-bottom:5px;">
            <table>
            <#list table.notPkColumns?chunk(2) as row>
                <tr>
                	<#list row as column>
                    <td style="width:100px;font-size:12px;text-align:right;">${column.columnAlias}：</td>
                    <td style="width:150px;text-align:left;">    
                    	<#if column.isDateTimeColumn>
                    	<input name="${column.columnNameLower}" class="mini-datepicker" <#if !column.isNullable()>required="true"</#if>/><#if !column.isNullable()><span style="color:red">☆</span></#if>
                    	</#if>
                    	<#if !column.isDateTimeColumn>
                    		<#if (column.size>200)>
                       	<input name="${column.columnNameLower}" class="mini-textarea" <#if !column.isNullable()>required="true"</#if>/><#if !column.isNullable()><span style="color:red">☆</span></#if>
                       		</#if>
                       		<#if (column.size<=200)>
                       			 <#if column.columnNameLower = "state">
                       	<input name="state" class="mini-combobox" data="stateGenders" showNullItem="true"/>
                       			</#if>	
                       			<#if column.columnNameLower != "state">
                       	<input name="${column.columnNameLower}" class="mini-textbox" <#if (column.getJdbcType() = "DECIMAL")>vtype="float"</#if> <#if !column.isNullable()>required="true"</#if>/><#if !column.isNullable()><span style="color:red">☆</span></#if>
                       			</#if>	
                       		</#if>	
                        </#if>
                    </td>
                    </#list>
                </tr>
             </#list>	
            </table>
        </div>
        <div style="text-align:center;padding:10px;">               
            <a class="mini-button" onclick="onOk" style="width:60px;margin-right:20px;">确定</a>       
            <a class="mini-button" onclick="onCancel" style="width:60px;">取消</a>       
        </div>        
    </form>
    <script type="text/javascript">
	    <#list table.notPkColumns as column>
	    <#if column.columnNameLower = "state">
	 	//状态
		var stateGenders = [{ id: '0', text: '禁用' }, { id: '1', text: '启用'}];
	    </#if>
		</#list>
	    mini.parse();

        var form = new mini.Form("${classNameLower}_form");
		//保存方法
        function saveData() {
        	//收集数据
            var ${classNameLower}Data = form.getData();  
            //数据验证          
            form.validate();
            if (!form.isValid())
            	return;
            <#list table.notPkColumns as column>
            <#if column.isDateTimeColumn>
            ${classNameLower}Data["${column.columnNameLower}"]=${classNameLower}Data["${column.columnNameLower}"]?${classNameLower}Data["${column.columnNameLower}"].Format("yyyy-MM-dd hh:mm:ss.S"):"";
            </#if>
            </#list>
            
            //var json = mini.encode(${classNameLower});
            $.ajax({
                url: "<%=basePath%>${pageRequestBase}/${namespace}/${classNameLower}/saveOrUpdate",
                data: ${classNameLower}Data,
                type:"post",
                cache: false,
                dataType: "json",
                success: function (result) {
                	var resultObj = result;//mini.decode(result);
                	if(!resultObj){
                		mini.alert("加载异常，请尝试刷新页面");
                		return;
                	}
                    if(resultObj.successful){
	                	mini.alert("保存成功");
	                    closeWindow("save");
                    }else{
                    	mini.alert(resultObj.resultMsg);
                    }
                }
            });
        }

        //标准方法接口定义
        function setData(data) {
            if (data.action == "edit") {
                //跨页面传递的数据对象，克隆后才可以安全使用
                data = mini.clone(data);
                $.ajax({
                	url: "<%=basePath%>${pageRequestBase}/${namespace}/${classNameLower}/findById?<#list table.pkColumns as column>${column.columnNameLower}=" + data.${column.columnNameLower}<#if column_has_next>+"&</#if></#list>,
                    cache: false,
                    success: function (result) {
                        var resultObj = mini.decode(result);
                        if(!resultObj){
	                		mini.alert("加载异常，请尝试刷新页面");
	                		return;
	                	}
                        if(resultObj.successful)
                        	form.setData(resultObj.data);
                    }
                });
            }
        }
        //保存按钮
        function onOk(e) {
            saveData();
        }
        //取消按钮
        function onCancel(e) {
            closeWindow("cancel");
        }
		//关闭窗口
		function closeWindow(action) {
			if (window.CloseOwnerWindow) {
				window.CloseOwnerWindow(action);
			} else {
				window.close();
			}
		}
    </script>
</body>
</html>
