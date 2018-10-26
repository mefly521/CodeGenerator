<#include "/java_copyright.include">
<#assign className = table.className>   
<#assign classNameLower = className?uncap_first>   
<#macro mapperEl value>${"#{"}${value}}</#macro>
package ${basepackage}.${namespace}.mapper;

<#include "/java_imports.include">
import java.util.List;
import org.apache.ibatis.annotations.Param;
import ${basepackage}.${namespace}.entity.${className};
import ${basepackage}.${namespace}.mapper.${className}Mapper;
import ${basepackage}.util.util.Page;
/**
 * ${table.tableAlias}管理的Dao
 * @author 
 *
 */
public interface ${className}Mapper {
	/**
	 * 根据条件查询${table.tableAlias}
	 * @param page
	 * @return
	 */
	public List<${className}> findByConditionPage(Page page);
	/**
	 * 查询所有${table.tableAlias}
	 * @return
	 */
	public List<${className}> findAll(@Param(value="sorter")String sorter);
	/**
	 * 通过主键查询单个${table.tableAlias}
	 <#list table.compositeIdColumns as c>
	 * @param ${c.columnNameFirstLower}<#if c_has_next>  </#if>	 </#list>	 
	 * @return
	 */
	public ${className} findBy<#list table.compositeIdColumns as c>${c.columnName}<#if c_has_next>And</#if></#list>(<#list table.compositeIdColumns as c>@Param(value="${c.columnNameFirstLower}")${c.javaType} ${c.columnNameFirstLower}<#if c_has_next>,</#if></#list>);
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
	 * @param ${classNameLower}s
	 */
	public void batchDelete(List<${className}> ${classNameLower}s);
	/**
	 * 删除${table.tableAlias}
	 * @param id
	 */
	public void delete(<#list table.compositeIdColumns as c>@Param(value="${c.columnNameFirstLower}")${c.javaType} ${c.columnNameFirstLower}<#if c_has_next>,</#if></#list>);
}
