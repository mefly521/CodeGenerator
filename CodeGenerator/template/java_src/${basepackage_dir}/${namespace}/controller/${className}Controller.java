<#include "/custom.include">
<#include "/java_copyright.include">
<#assign className = table.className>   
<#assign classNameLower = className?uncap_first>
package ${basepackage}.${namespace}.controller;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ego.util.BackResult;
import com.ego.util.JSON;

import ${basepackage}.${namespace}.entity.${className};
import ${basepackage}.${namespace}.service.I${className}Service;
import ${basepackage}.util.BackResult;
import ${basepackage}.util.StringUtil;
import ${basepackage}.util.GUID;
import ${basepackage}.util.JSON;
import ${basepackage}.util.Page;
import ${basepackage}.util.ValidatorUtil;
<#include "/java_imports.include">

/**
 * ${table.tableAlias}的相关请求
 * @author 陶富
 */
@Controller("${classNameLower}Controller")
@RequestMapping(value="/${namespace}/${classNameLower}")
public class ${className}Controller {
	private static final Logger logger = LoggerFactory.getLogger(${className}Controller.class);
	@Autowired
	private I${className}Service ${classNameLower}Service;
	/**
	 * 查询${table.tableAlias}集合
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "/findByCondition")
	public @ResponseBody String findByCondition(HttpServletRequest request){
		BackResult backResult = null;
		try {
			//分页对象，前台传递的包含查询的参数
			Page page = new Page(request);
			logger.info("前台传递的参数【"+page.toString()+"】");
			List<${className}> ${classNameLower}List = ${classNameLower}Service.findByConditionPage(page);
			//返回的结果对象，包装了返回值、返回记录数、返回状态
			if(!page.getIsPage()){
				page.setTotal(${classNameLower}List.size());
			}
			backResult = BackResult.successResult(${classNameLower}List, page.getTotal());
		} catch (Exception e) {
			logger.error("查询错误", e);
			backResult = BackResult.failedResult("查询错误，原因："+e.getMessage());
		}
		return JSON.Encode(backResult);
	}
	/**
	 * 查询${table.tableAlias}集合
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "/findDictByCondition")
	public @ResponseBody String findDictByCondition(HttpServletRequest request){
		try {
			//分页对象，前台传递的包含查询的参数
			Page page = new Page(request);
			logger.info("前台传递的参数【"+page.toString()+"】");
			List<${className}> ${classNameLower}List = ${classNameLower}Service.findByConditionPage(page);
			//返回的结果对象，包装了返回值、返回记录数、返回状态
			return JSON.Encode(${classNameLower}List);
		} catch (Exception e) {
			logger.error("查询错误", e);
			return "查询错误，原因："+e.getMessage();
		}
	}
	/**
	 * 保存或更新${table.tableAlias}数据
	 */
	@RequestMapping(value = "/saveOrUpdate",method = RequestMethod.POST)
	public @ResponseBody String saveOrUpdate(${className} ${classNameLower}){
		BackResult backResult = null;
		try{
			logger.debug("保存${table.tableAlias}，参数："+${classNameLower});
			
			<#list table.columns as column><#if (column.isUnique()) && (!column.isPk())  >
			${className} ${classNameLower}By${column.columnName} = null;
			if(${classNameLower}!=null&&${classNameLower}.get${column.columnName}()!=null){
				${classNameLower}By${column.columnName} = ${classNameLower}Service.findBy${column.columnName}(${classNameLower}.get${column.columnName}());
			}
			</#if></#list>
			boolean pkIsNotNUll = true;//主键是否为空
			//主键全为空，正常执行添加
			if(<#list table.pkColumns as column>StringUtil.isEmpty(${classNameLower}.get${column.columnName}())<#if column_has_next>&&</#if></#list>){
				pkIsNotNUll = false;
			}
			//主键全不为空，正常执行修改			
			else if(<#list table.pkColumns as column>StringUtil.isNotEmpty(${classNameLower}.get${column.columnName}())<#if column_has_next>&&</#if></#list>){
				pkIsNotNUll = true;
			}//主键有为空有不为空，则抛出异常
			else{
				throw new RuntimeException("主键参数异常！");
			}
			if(pkIsNotNUll){
				<#list table.columns as column><#if (column.isUnique()) && (!column.isPk())  >
				if(${classNameLower}By${column.columnName}!=null&&!${classNameLower}By${column.columnName}.equals(${classNameLower}))
					throw new RuntimeException("${table.tableAlias}的${column.columnAlias}已经存在！");
				</#if></#list>
				
				ValidatorUtil.validatorJavaBean(${classNameLower});
				${classNameLower}Service.update(${classNameLower});
			}else {
				//生成主键
				<#list table.pkColumns as column>
				<#if !(column.getJdbcType() = "VARCHAR")>
				${classNameLower}.set${column.columnName}(GUID.next());//非字符串类型默认为长整形主键
				</#if><#if column.getJdbcType() = "VARCHAR">
				${classNameLower}.set${column.columnName}(GUID.getGuid());//字符串类型主键
				</#if><#if column_has_next></#if></#list>
				
				<#list table.columns as column><#if (column.isUnique()) && (!column.isPk())  >
				//判断唯一字段是否已被使用
				if(${classNameLower}By${column.columnName}!=null){
					throw new RuntimeException("${table.tableAlias}的${column.columnAlias}已经存在！");
				}
				</#if></#list>
				//执行配置验证
				ValidatorUtil.validatorJavaBean(${classNameLower});
				//执行添加
				${classNameLower}Service.add(${classNameLower});
			}
			backResult = BackResult.successResult(${classNameLower});
		}catch (Exception e) {
			logger.error("保存或更新错误", e);
			backResult = BackResult.failedResult("保存或更新失败，失败原因："+e.getMessage());
		}
		return JSON.Encode(backResult);
	}
	/**
	 * 单个查询${table.tableAlias}
	 * @throws IOException 
	 */
	@RequestMapping(value = "/findById")
	public @ResponseBody String findById(<#list table.pkColumns as column>@RequestParam("${column.columnNameLower}")${column.javaType} ${column.columnNameLower}<#if column_has_next>,</#if></#list>){
		BackResult backResult = null;
		try{
			${className} ${classNameLower} = ${classNameLower}Service.findBy<#list table.pkColumns as column>${column.columnName}<#if column_has_next>And</#if></#list>(<#list table.pkColumns as column>${column.columnNameLower}<#if column_has_next>,</#if></#list>);
			logger.debug("查询${table.tableAlias}，返回结果："+${classNameLower});
			//返回的结果对象，包装了返回值、返回记录数、返回状态
			backResult = BackResult.successResult(${classNameLower});
		}catch (Exception e) {
			logger.error("查询错误", e);
			backResult = BackResult.failedResult("查询错误，原因："+e.getMessage());
		}
		return JSON.Encode(backResult);
	}
<#list table.columns as column>
	<#if (column.isUnique()) && (!column.isPk())  >
	/**
	 * 查询单个${table.tableAlias}
	 * @param ${column.columnNameFirstLower}
	 * @return
	 */
	@RequestMapping(value = "/findBy${column.columnName}")
	public @ResponseBody String findBy${column.columnName}(@RequestParam("${column.columnNameLower}")${column.javaType} ${column.columnNameFirstLower}){
		BackResult backResult = null;
		try{
			${className} ${classNameLower} = ${classNameLower}Service.findBy${column.columnName}(${column.columnNameFirstLower});
			logger.debug("查询${table.tableAlias}，返回结果："+${classNameLower});
			//返回的结果对象，包装了返回值、返回记录数、返回状态
			backResult = BackResult.successResult(${classNameLower});
		}catch (Exception e) {
			logger.error("查询错误", e);
			backResult = BackResult.failedResult("查询错误，原因："+e.getMessage());
		}
		return JSON.Encode(backResult);
	}
	</#if>
</#list>
	/**
	 * 批量删除${table.tableAlias}
	 */
	@RequestMapping(value = "/batchDelete")
	public @ResponseBody String batchDelete(<#list table.pkColumns as column>@RequestParam("${column.columnNameLower}s")String ${column.columnNameLower}s<#if column_has_next>,</#if></#list>){
		BackResult backResult = null;
		try {
			logger.debug("批量删除${table.tableAlias}，参数："+<#list table.pkColumns as column>${column.columnNameLower}s<#if column_has_next>+","+</#if></#list>);
			if(<#list table.pkColumns as column>${column.columnNameLower}s!=null&&!"".equals(${column.columnNameLower}s)<#if column_has_next>&&</#if></#list>){
				${classNameLower}Service.batchDelete(Arrays.asList(<#list table.pkColumns as column>${column.columnNameLower}s.split(",")<#if column_has_next>,</#if></#list>));
				backResult = BackResult.successResult(<#list table.pkColumns as column>${column.columnNameLower}s<#if column_has_next>+"::"+</#if></#list>);
			}else {
				backResult = BackResult.failedResult("未选择删除记录");
			}
		} catch (Exception e) {
			logger.error("删除错误", e);
			backResult = BackResult.failedResult("删除错误，原因："+e.getMessage());
		}
		return JSON.Encode(backResult);
	}
	/**
	 * 通过ID删除${table.tableAlias}
	 */
	@RequestMapping(value = "/delete")
	public @ResponseBody String delete(<#list table.pkColumns as column>@RequestParam("${column.columnNameLower}")${column.javaType} ${column.columnNameLower}<#if column_has_next>,</#if></#list>){
		BackResult backResult = null;
		try {
			logger.debug("通过ID删除${table.tableAlias}，参数："+<#list table.pkColumns as c>${c.columnNameLower}<#if c_has_next>+","+</#if></#list>);
			if(<#list table.pkColumns as c>${c.columnNameLower}!=null&&!"".equals(${c.columnNameLower})<#if c_has_next>+","+</#if></#list>){
				${classNameLower}Service.delete(<#list table.compositeIdColumns as c>${c.columnNameFirstLower}<#if c_has_next>,</#if></#list>);
				backResult = BackResult.successResult(<#list table.compositeIdColumns as c>${c.columnNameFirstLower}<#if c_has_next>+"::"+</#if></#list>);
			}else {
				backResult = BackResult.failedResult("未选择删除记录");
			}
		} catch (Exception e) {
			logger.error("删除错误", e);
			backResult = BackResult.failedResult("删除错误，原因："+e.getMessage());
		}
		return JSON.Encode(backResult);
	}
}
