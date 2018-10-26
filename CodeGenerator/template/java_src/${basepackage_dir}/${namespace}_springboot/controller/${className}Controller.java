<#include "/custom.include">
<#include "/java_copyright.include">
<#assign className = table.className>   
<#assign classNameLower = className?uncap_first>
package ${basepackage}.${namespace}.controller;

import java.io.IOException;
import java.util.List;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.bit.base.exception.BusinessException;
import ${basepackage}.${namespace}.${beanPackagename}.${className};
import ${basepackage}.${namespace}.vo.${className}VO;
import ${basepackage}.${namespace}.service.${className}Service;
import com.bit.base.vo.BaseVo;
<#include "/java_imports.include">

/**
 * ${table.tableAlias}的相关请求
 * @author generator
 */
@RestController
@RequestMapping(value = "/${classNameLower}")
public class ${className}Controller {
	private static final Logger logger = LoggerFactory.getLogger(${className}Controller.class);
	@Autowired
	private ${className}Service ${classNameLower}Service;
	

    /**
     * 分页查询${table.tableAlias}列表
     *
     */
    @GetMapping("/listPage")
    public BaseVo listPage(@RequestBody ${className}VO ${classNameLower}VO) {
    	//分页对象，前台传递的包含查询的参数

        return ${classNameLower}Service.findByConditionPage(${classNameLower}VO);
    }

    /**
     * 根据主键ID查询${table.tableAlias}
     *
     * @param <#list table.compositeIdColumns as c>${c.columnNameFirstLower}<#if c_has_next>,</#if></#list>
     * @return
     */
    @GetMapping("/query/{<#list table.compositeIdColumns as c>${c.columnNameFirstLower}<#if c_has_next>,</#if></#list>}")
    public BaseVo query(@PathVariable(value = "<#list table.compositeIdColumns as c>${c.columnNameFirstLower}<#if c_has_next>,</#if></#list>") Long <#list table.compositeIdColumns as c>${c.columnNameFirstLower}<#if c_has_next>,</#if></#list>) {

        ${className} ${classNameLower} = ${classNameLower}Service.findById(id);
		BaseVo baseVo = new BaseVo();
		baseVo.setData(${classNameLower});
		return baseVo;
    }
    
    /**
     * 新增${table.tableAlias}
     *
     * @param ${classNameLower} ${table.tableAlias}实体
     * @return
     */
    @PostMapping("/add")
    public BaseVo add(@Valid @RequestBody ${className}VO ${classNameLower}) {
        ${classNameLower}Service.add(${classNameLower});
		BaseVo baseVo = new BaseVo();
        return baseVo;
    }
    /**
     * 修改${table.tableAlias}
     *
     * @param ${classNameLower} ${table.tableAlias}实体
     * @return
     */
    @PostMapping("/modify")
    public BaseVo modify(@Valid @RequestBody ${className} ${classNameLower}) {
		${classNameLower}Service.update(${classNameLower});
		BaseVo baseVo = new BaseVo();
        return baseVo;
    }
    
    /**
     * 根据主键ID删除${table.tableAlias}
     *
     * @param <#list table.compositeIdColumns as c>${c.columnNameFirstLower}<#if c_has_next>,</#if></#list>
     * @return
     */
    @GetMapping("/delete/{<#list table.compositeIdColumns as c>${c.columnNameFirstLower}<#if c_has_next>,</#if></#list>}")
    public BaseVo delete(@PathVariable(value = "<#list table.compositeIdColumns as c>${c.columnNameFirstLower}<#if c_has_next>,</#if></#list>") Long <#list table.compositeIdColumns as c>${c.columnNameFirstLower}<#if c_has_next>,</#if></#list>) {
        ${classNameLower}Service.delete(<#list table.compositeIdColumns as c>${c.columnNameFirstLower}<#if c_has_next>,</#if></#list>);
		BaseVo baseVo = new BaseVo();
        return baseVo;
    }

    /**
     * 根据${table.tableAlias}ID集合批量删除${table.tableAlias}
     *
     * @param <#list table.pkColumns as column>${column.columnNameLower}s<#if column_has_next>,</#if></#list> ${table.tableAlias}ID集合
     * @return BaseVo
     *
     */
    @PostMapping("/delBatchByIds")
    public BaseVo delBatchByIds(@RequestBody List<Long> <#list table.pkColumns as column>${column.columnNameLower}s<#if column_has_next>,</#if></#list>) {
        ${classNameLower}Service.batchDelete(<#list table.compositeIdColumns as c>${c.columnNameFirstLower}<#if c_has_next>,</#if></#list>s);
		BaseVo baseVo = new BaseVo();
        return baseVo;
   }
}
