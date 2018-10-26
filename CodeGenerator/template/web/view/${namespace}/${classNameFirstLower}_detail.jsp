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
    
    <title>${dbTableAlias}详情</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is ${dbTableAlias}详情">
	<script src="<%=basePath%>/scripts/boot.js" type="text/javascript"></script>
    <style type="text/css">
		body {
			width: 98%;
			height: 98%;
			background-color: white;
		}
	</style>
	<link href="<%=basePath%>/css/detail.css" rel="stylesheet" type="text/css" />
</head>
<body>    
    <form id="${classNameLower}_form" method="post">
		<#list table.columns as column>
		<#if column.pk>
			<input name="${column.columnNameLower}" class="mini-hidden" />
		</#if>
		</#list>
        <div style="width:100%;padding-left:2%;padding-top:1%;padding-bottom: 1%;">
            <table class="table_detail">
                 <#list table.notPkColumns?chunk(2) as row>
                <tr>
                	<#list row as column>
                    <td class="table_detail_td_odd">${column.columnAlias}：</td>
                    <td class="table_detail_td_even">    
                    	<#if column.isDateTimeColumn>
                    	<input name="${column.columnNameLower}" class="mini-datepicker" style="width:99%" format="yyyy-MM-dd H:mm:ss"/>
                    	</#if>
                    	<#if !column.isDateTimeColumn>
                    		<#if (column.size>200)>
                      	<input name="${column.columnNameLower}" class="mini-textarea" style="width:99%" />
                       		</#if>
                       		<#if (column.size<=200)>
                       			<#if column.columnNameLower = "state">
                       	<input name="state" class="mini-combobox" data="stateGenders" style="width:99%"  showNullItem="true"/>
                       			</#if>	
                       			<#if column.columnNameLower != "state">
                       	<input name="${column.columnNameLower}" class="mini-textbox" style="width:99%"  />
                       			</#if>
                       		</#if>	
                        </#if>
                    </td>
                    </#list>
                </tr>
             </#list>
           </table>
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

        //标准方法接口定义
        function setData(data) {
            if (data.action == "detail") {
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
		//关闭窗口
		function closeWindow(action) {
			if (window.CloseOwnerWindow) {
				window.CloseOwnerWindow(action);
			} else {
				window.close();
			}
		}
		function labelModel() {
            var fields = form.getFields(); 
            for (var i = 0, l = fields.length; i < l; i++) {
                var c = fields[i];
                if (c.setReadOnly) c.setReadOnly(true);     //只读
                if (c.setIsValid) c.setIsValid(true);      //去除错误提示
                if (c.addCls) c.addCls("asLabel");          //增加asLabel外观
            }
        }
		labelModel();
    </script>
</body>
</html>
