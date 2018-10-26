<#include "/java_copyright.include">
<#assign className = table.className>   
<#assign classNameLower = className?uncap_first> 
package ${basepackage}.${namespace}.dao;

import java.util.List;
import java.util.ArrayList;

import com.bit.sc.ScServerApplication;
import org.junit.runner.RunWith;
import org.junit.Test;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
/**
 * ${table.tableAlias}的Service实现类
 * @author codeGenerator
 *
 */
@RunWith(SpringJUnit4ClassRunner.class) // SpringJUnit支持，由此引入Spring-Test框架支持！
@SpringBootTest(classes = ScServerApplication.class)
@WebAppConfiguration
public class ${className}DaoTest{
	
	private static final Logger logger = LoggerFactory.getLogger(${className}DaoTest.class);
	
	@Autowired
	private ${className}Mapper ${classNameLower}Mapper;
	
	/**
	 * 根据条件查询${table.tableAlias}
	 * @param page
	 * @return
	 */
	@Test
	public void findByConditionPage(){
		${className}VO ${classNameLower}VO = new ${className}VO() ;
		PageHelper.startPage(${classNameLower}VO.getPageNo(), ${classNameLower}VO.getPageSize());
		List<User> list = ${classNameLower}Mapper.findByConditionPage(${classNameLower}VO);
		PageInfo<User> pageInfo = new PageInfo<User>(list);
		userVO.setData(pageInfo);
	}
	/**
	 * 查询所有${table.tableAlias}
	 * @param sorter 排序字符串
	 * @return
	 */
	@Test
	public void findAll(){
		${classNameLower}Mapper.findAll(null);
	}
	/**
	 * 通过主键查询单个${table.tableAlias}
	 * @param <#list table.compositeIdColumns as c>${c.columnNameFirstLower}<#if c_has_next>  </#if></#list>
	 * @return
	 */
	@Test
	public void findBy<#list table.compositeIdColumns as c>${c.columnName}<#if c_has_next>And</#if></#list>(){
		 ${classNameLower}Mapper.findBy<#list table.compositeIdColumns as c>${c.columnName}<#if c_has_next>And</#if></#list>(1L);
	}

	/**
	 * 批量保存${table.tableAlias}
	 * @param ${classNameLower}s
	 */
	@Test
	public void batchAdd(){
		${classNameLower}Mapper.batchAdd(null);
	}
	/**
	 * 保存${table.tableAlias}
	 * @param ${classNameLower}
	 */
	@Test
	public void add(){
		${classNameLower}Mapper.add(null);
	}
	/**
	 * 批量更新${table.tableAlias}
	 * @param ${classNameLower}s
	 */
	@Test
	public void batchUpdate(){
		${classNameLower}Mapper.batchUpdate(null);
	}
	/**
	 * 更新${table.tableAlias}
	 * @param ${classNameLower}
	 */
@Test
	public void update(){
		${classNameLower}Mapper.update(null);
	}
	/**
	 * 删除${table.tableAlias}
	 * @param <#list table.pkColumns as column>${column.columnNameLower}s<#if column_has_next>,</#if></#list>
	 */
	@Test
	public void batchDelete(){
		${classNameLower}Mapper.batchDelete(<#list table.pkColumns as column>${column.columnNameLower}s<#if column_has_next>,</#if></#list>);
	}
	/**
	 * 删除${table.tableAlias}
	 * @param <#list table.compositeIdColumns as c>${c.columnNameFirstLower}<#if c_has_next>,</#if></#list>
	 */
	@Test
	public void delete(){
		${classNameLower}Mapper.delete(1L);
	}
}
