<#include "/java_copyright.include">
<#assign className = table.className>   
<#assign classNameLower = className?uncap_first> 
package ${basepackage}.${namespace}.service.impl;

import java.util.List;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ${basepackage}.${namespace}.entity.${className};
import ${basepackage}.${namespace}.mapper.${className}Mapper;
import ${basepackage}.${namespace}.service.I${className}Service;
import ${basepackage}.util.Page;
/**
 * ${table.tableAlias}的Service实现类
 * @author 陶富
 *
 */
@Service("${classNameLower}Service")
public class ${className}ServiceImpl implements I${className}Service{
	
	private static final Logger logger = LoggerFactory.getLogger(${className}ServiceImpl.class);
	
	@Autowired
	private ${className}Mapper ${classNameLower}Mapper;
	
	/**
	 * 根据条件查询${table.tableAlias}
	 * @param page
	 * @return
	 */
	public List<${className}> findByConditionPage(Page page){
		return ${classNameLower}Mapper.findByConditionPage(page);
	}
	/**
	 * 查询所有${table.tableAlias}
	 * @param sorter 排序字符串
	 * @return
	 */
	public List<${className}> findAll(String sorter){
		return ${classNameLower}Mapper.findAll(sorter);
	}
	/**
	 * 通过主键查询单个${table.tableAlias}
	 * @param <#list table.compositeIdColumns as c>${c.columnNameFirstLower}<#if c_has_next>  </#if></#list>
	 * @return
	 */
	public ${className} findBy<#list table.compositeIdColumns as c>${c.columnName}<#if c_has_next>And</#if></#list>(<#list table.compositeIdColumns as c>${c.javaType} ${c.columnNameFirstLower}<#if c_has_next>,</#if></#list>){
		return ${classNameLower}Mapper.findBy<#list table.compositeIdColumns as c>${c.columnName}<#if c_has_next>And</#if></#list>(<#list table.compositeIdColumns as c>${c.columnNameFirstLower}<#if c_has_next>,</#if></#list>);
	}
<#list table.columns as column>
	<#if (column.isUnique()) && (!column.isPk())  >
	/**
	 * 查询单个${table.tableAlias}
	 * @param ${column.columnNameFirstLower}
	 * @return
	 */
	public ${className} findBy${column.columnName}(${column.javaType} ${column.columnNameFirstLower}){
		return ${classNameLower}Mapper.findBy${column.columnName}(${column.columnNameFirstLower});
	}
	</#if>
</#list>
	
	/**
	 * 批量保存${table.tableAlias}
	 * @param ${classNameLower}s
	 */
	public void batchAdd(List<${className}> ${classNameLower}s){
		${classNameLower}Mapper.batchAdd(${classNameLower}s);
	}
	/**
	 * 保存${table.tableAlias}
	 * @param ${classNameLower}
	 */
	public void add(${className} ${classNameLower}){
		${classNameLower}Mapper.add(${classNameLower});
	}
	/**
	 * 批量更新${table.tableAlias}
	 * @param ${classNameLower}s
	 */
	public void batchUpdate(List<${className}> ${classNameLower}s){
		${classNameLower}Mapper.batchUpdate(${classNameLower}s);
	}
	/**
	 * 更新${table.tableAlias}
	 * @param ${classNameLower}
	 */
	public void update(${className} ${classNameLower}){
		${classNameLower}Mapper.update(${classNameLower});
	}
	/**
	 * 删除${table.tableAlias}
	 * @param <#list table.pkColumns as column>${column.columnNameLower}s<#if column_has_next>,</#if></#list>
	 */
	public void batchDelete(<#list table.pkColumns as column>List<String> ${column.columnNameLower}s<#if column_has_next>,</#if></#list>){
		List<${className}> ${classNameLower}s = new ArrayList<${className}>();
		if(<#list table.pkColumns as column>${column.columnNameLower}s!=null&&${column.columnNameLower}s.size()>0<#if column_has_next>&&</#if></#list>){
			for(int i=0;i<<#list table.pkColumns as column>${column.columnNameLower}s.size()<#if column_index = 0><#break></#if></#list>;i++){
				${className} ${classNameLower} = new ${className}();
				<#list table.pkColumns as column>
				${classNameLower}.set${column.columnName}(<#if !(column.getJdbcType() = "VARCHAR")>Long.parseLong(${column.columnNameLower}s[i])</#if><#if column.getJdbcType() = "VARCHAR">${column.columnNameLower}s.get(i)</#if>);
				</#list>
				${classNameLower}s.add(${classNameLower});
			}
		}else{
			throw new RuntimeException("删除记录参数不存在");
		}
		
		${classNameLower}Mapper.batchDelete(${classNameLower}s);
	}
	/**
	 * 删除${table.tableAlias}
	 * @param <#list table.compositeIdColumns as c>${c.columnNameFirstLower}<#if c_has_next>,</#if></#list>
	 */
	public void delete(<#list table.compositeIdColumns as c>${c.javaType} ${c.columnNameFirstLower}<#if c_has_next>,</#if></#list>){
		${classNameLower}Mapper.delete(<#list table.compositeIdColumns as c>${c.columnNameFirstLower}<#if c_has_next>,</#if></#list>);
	}
}
