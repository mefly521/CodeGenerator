<#include "/macro.include"/>
<#assign className = table.className>   
<#assign classNameLower = className?uncap_first> 
package ${basepackage}.${namespace}.entity;

import java.util.Date;
import com.bit.base.vo.BasePageVo;
import lombok.Data;

/**
 * ${table.tableAlias}
 * @author generator
 */
@Data
public class ${className}VO extends BasePageVo{

	//columns START

	<#list table.columns as column>
    /**
     * ${column.columnAlias}
     */	
	private ${column.getJavaType()} ${column.columnNameLower};
	</#list>

	//columns END

}


