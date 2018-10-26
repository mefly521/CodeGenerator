<#include "/java_copyright.include">
<#assign className = table.className>   
<#assign classNameLower = className?uncap_first> 
package ${basepackage}.${namespace}.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import com.ego.util.AnnotationName;

import ${basepackage}.${namespace}.entity.${className};
import ${basepackage}.util.Page;
import ${basepackage}.util.AnnotationName;
<#include "/java_imports.include">
/**
 * ${table.tableAlias}的Service
 * @author 陶富
 */
@Service
public interface I${className}Service {
	/**
	 * 根据条件查询${table.tableAlias}
	 * @param page
	 * @return
	 */
	@AnnotationName(moduleName="${table.tableAlias}",operateContent="根据条件查询",operateType="82",logType="1")
	public List<${className}> findByConditionPage(Page page);
	/**
	 * 查询所有${table.tableAlias}
	 * @param sorter 排序字符串
	 * @return
	 */
	@AnnotationName(moduleName="${table.tableAlias}",operateContent="查询所有",operateType="81",logType="1")
	public List<${className}> findAll(String sorter);
	/**
	 * 通过主键查询单个${table.tableAlias}
	 * @param <#list table.compositeIdColumns as c>${c.columnNameFirstLower}<#if c_has_next>  </#if></#list>
	 * @return
	 */
	@AnnotationName(moduleName="${table.tableAlias}",operateContent="根据主键查询",operateType="80",logType="1")
	public ${className} findBy<#list table.compositeIdColumns as c>${c.columnName}<#if c_has_next>And</#if></#list>(<#list table.compositeIdColumns as c>${c.javaType} ${c.columnNameFirstLower}<#if c_has_next>,</#if></#list>);
<#list table.columns as column>
	<#if (column.isUnique()) && (!column.isPk())  >
	/**
	 * 查询单个${table.tableAlias}
	 * @param ${column.columnNameFirstLower}
	 * @return
	 */
	@AnnotationName(moduleName="${table.tableAlias}",operateContent="根据唯一字段${column.columnNameFirstLower}查询",operateType="80",logType="1")
	public ${className} findBy${column.columnName}(${column.javaType} ${column.columnNameFirstLower});
	</#if>
</#list>
	
	/**
	 * 批量保存${table.tableAlias}
	 * @param ${classNameLower}s
	 */
	@AnnotationName(moduleName="${table.tableAlias}",operateContent="批量添加",operateType="11",logType="1")
	public void batchAdd(List<${className}> ${classNameLower}s);
	/**
	 * 保存${table.tableAlias}
	 * @param ${classNameLower}
	 */
	@AnnotationName(moduleName="${table.tableAlias}",operateContent="添加",operateType="10",logType="1")
	public void add(${className} ${classNameLower});
	/**
	 * 批量更新${table.tableAlias}
	 * @param ${classNameLower}s
	 */
	@AnnotationName(moduleName="${table.tableAlias}",operateContent="批量更新",operateType="21",logType="1")
	public void batchUpdate(List<${className}> ${classNameLower}s);
	/**
	 * 更新${table.tableAlias}
	 * @param ${classNameLower}
	 */
	@AnnotationName(moduleName="${table.tableAlias}",operateContent="更新",operateType="20",logType="1")
	public void update(${className} ${classNameLower});
	/**
	 * 批量删除${table.tableAlias}
	 * @param <#list table.pkColumns as column> ${column.columnNameLower}s<#if column_has_next>,</#if></#list>
	 */
	@AnnotationName(moduleName="${table.tableAlias}",operateContent="批量删除",operateType="31",logType="1")
	public void batchDelete(<#list table.pkColumns as column>List<String> ${column.columnNameLower}s<#if column_has_next>,</#if></#list>);
	/**
	 * 删除${table.tableAlias}
	 * @param <#list table.compositeIdColumns as c>${c.columnNameFirstLower}<#if c_has_next>,</#if></#list>
	 */
	@AnnotationName(moduleName="${table.tableAlias}",operateContent="删除",operateType="30",logType="1")
	public void delete(<#list table.compositeIdColumns as c>${c.javaType} ${c.columnNameFirstLower}<#if c_has_next>,</#if></#list>);
}
