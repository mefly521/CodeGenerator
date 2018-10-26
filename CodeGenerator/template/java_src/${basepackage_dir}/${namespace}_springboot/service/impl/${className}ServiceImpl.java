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
import com.bit.base.vo.BaseVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import ${basepackage}.${namespace}.${beanPackagename}.${className};
import ${basepackage}.${namespace}.vo.${className}VO;
import ${basepackage}.${namespace}.dao.${className}Mapper;
import ${basepackage}.${namespace}.service.${className}Service;
/**
 * ${table.tableAlias}的Service实现类
 * @author codeGenerator
 *
 */
@Service("${classNameLower}Service")
public class ${className}ServiceImpl implements ${className}Service{
	
	private static final Logger logger = LoggerFactory.getLogger(${className}ServiceImpl.class);
	
	@Autowired
	private ${className}Mapper ${classNameLower}Mapper;
	
	/**
	 * 根据条件查询${table.tableAlias}
	 * @param page
	 * @return
	 */
	@Override
	public BaseVo findByConditionPage(${className}VO ${classNameLower}VO){
		PageHelper.startPage(${classNameLower}VO.getPageNo(), ${classNameLower}VO.getPageSize());
		List<User> list = ${classNameLower}Mapper.findByConditionPage(${classNameLower}VO);
		PageInfo<User> pageInfo = new PageInfo<User>(list);
		userVO.setData(pageInfo);
		return userVO;
	}
	/**
	 * 查询所有${table.tableAlias}
	 * @param sorter 排序字符串
	 * @return
	 */
	@Override
	public List<${className}> findAll(String sorter){
		return ${classNameLower}Mapper.findAll(sorter);
	}
	/**
	 * 通过主键查询单个${table.tableAlias}
	 * @param <#list table.compositeIdColumns as c>${c.columnNameFirstLower}<#if c_has_next>  </#if></#list>
	 * @return
	 */
	@Override
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
	@Override
	public ${className} findBy${column.columnName}(${column.javaType} ${column.columnNameFirstLower}){
		return ${classNameLower}Mapper.findBy${column.columnName}(${column.columnNameFirstLower});
	}
	</#if>
</#list>
	
	/**
	 * 批量保存${table.tableAlias}
	 * @param ${classNameLower}s
	 */
	@Override
	public void batchAdd(List<${className}> ${classNameLower}s){
		${classNameLower}Mapper.batchAdd(${classNameLower}s);
	}
	/**
	 * 保存${table.tableAlias}
	 * @param ${classNameLower}
	 */
	@Override
	public void add(${className} ${classNameLower}){
		${classNameLower}Mapper.add(${classNameLower});
	}
	/**
	 * 批量更新${table.tableAlias}
	 * @param ${classNameLower}s
	 */
	@Override
	public void batchUpdate(List<${className}> ${classNameLower}s){
		${classNameLower}Mapper.batchUpdate(${classNameLower}s);
	}
	/**
	 * 更新${table.tableAlias}
	 * @param ${classNameLower}
	 */
	@Override
	public void update(${className} ${classNameLower}){
		${classNameLower}Mapper.update(${classNameLower});
	}
	/**
	 * 删除${table.tableAlias}
	 * @param <#list table.pkColumns as column>${column.columnNameLower}s<#if column_has_next>,</#if></#list>
	 */
	@Override
	public void batchDelete(<#list table.pkColumns as column>List<Long> ${column.columnNameLower}s<#if column_has_next>,</#if></#list>){
		${classNameLower}Mapper.batchDelete(<#list table.pkColumns as column>${column.columnNameLower}s<#if column_has_next>,</#if></#list>);
	}
	/**
	 * 删除${table.tableAlias}
	 * @param <#list table.compositeIdColumns as c>${c.columnNameFirstLower}<#if c_has_next>,</#if></#list>
	 */
	@Override
	public void delete(<#list table.compositeIdColumns as c>${c.javaType} ${c.columnNameFirstLower}<#if c_has_next>,</#if></#list>){
		${classNameLower}Mapper.delete(<#list table.compositeIdColumns as c>${c.columnNameFirstLower}<#if c_has_next>,</#if></#list>);
	}
}
