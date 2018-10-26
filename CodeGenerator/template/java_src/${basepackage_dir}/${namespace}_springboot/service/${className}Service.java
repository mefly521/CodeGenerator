<#include "/java_copyright.include">
<#assign className = table.className>   
<#assign classNameLower = className?uncap_first> 
package ${basepackage}.${namespace}.service;

import java.util.List;
import org.springframework.stereotype.Service;
import ${basepackage}.${namespace}.${beanPackagename}.${className};
import ${basepackage}.${namespace}.vo.${className}VO;
import com.bit.base.vo.BaseVo;
<#include "/java_imports.include">
/**
 * ${table.tableAlias}的Service
 * @author codeGenerator
 */
@Service
public interface ${className}Service {
	/**
	 * 根据条件查询${table.tableAlias}
	 * @param ${classNameLower}VO
	 * @return
	 */
	public BaseVo findByConditionPage(${className}VO ${classNameLower}VO);
	/**
	 * 查询所有${table.tableAlias}
	 * @param sorter 排序字符串
	 * @return
	 */
	public List<${className}> findAll(String sorter);
	/**
	 * 通过主键查询单个${table.tableAlias}
	 * @param <#list table.compositeIdColumns as c>${c.columnNameFirstLower}<#if c_has_next>  </#if></#list>
	 * @return
	 */
	public ${className} findBy<#list table.compositeIdColumns as c>${c.columnName}<#if c_has_next>And</#if></#list>(<#list table.compositeIdColumns as c>${c.javaType} ${c.columnNameFirstLower}<#if c_has_next>,</#if></#list>);
<#list table.columns as column>
	<#if (column.isUnique()) && (!column.isPk())  >
	/**
	 * 查询单个${table.tableAlias}
	 * @param ${column.columnNameFirstLower}
	 * @return
	 */
	public ${className} findBy${column.columnName}(${column.javaType} ${column.columnNameFirstLower});
	</#if>
</#list>
	/**
	 * 批量保存${table.tableAlias}
	 * @param ${classNameLower}s
	 */
	public void batchAdd(List<${className}> ${classNameLower}s);
	/**
	 * 保存${table.tableAlias}
	 * @param ${classNameLower}
	 */
	public void add(${className} ${classNameLower});
	/**
	 * 批量更新${table.tableAlias}
	 * @param ${classNameLower}s
	 */
	public void batchUpdate(List<${className}> ${classNameLower}s);
	/**
	 * 更新${table.tableAlias}
	 * @param ${classNameLower}
	 */
	public void update(${className} ${classNameLower});
	/**
	 * 删除${table.tableAlias}
	 * @param <#list table.compositeIdColumns as c>${c.columnNameFirstLower}<#if c_has_next>,</#if></#list>
	 */
	public void delete(<#list table.compositeIdColumns as c>${c.javaType} ${c.columnNameFirstLower}<#if c_has_next>,</#if></#list>);
	/**
	 * 批量删除${table.tableAlias}
	 * @param <#list table.pkColumns as column>${column.columnNameLower}s<#if column_has_next>,</#if></#list>
	 */
	public void batchDelete(<#list table.pkColumns as column>List<Long> ${column.columnNameLower}s<#if column_has_next>,</#if></#list>);
}
