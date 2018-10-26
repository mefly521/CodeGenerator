<#include "/macro.include"/>
<#include "/java_copyright.include">
<#assign className = table.className>   
<#assign classNameLower = className?uncap_first> 
package ${basepackage}.${namespace}.entity;

import java.text.ParseException;
import java.util.Date;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import ${basepackage}.util.DateUtil;
import ${basepackage}.util.StringUtil;
import ${basepackage}.util.GUID;
/**
 * ${table.tableAlias}
 * @author taofu
 *
 */
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class ${className} implements java.io.Serializable{
	private static final long serialVersionUID = 1L;
	protected static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";	
	
	//date formats
	<#list table.columns as column>
	<#if column.isDateTimeColumn>
	public static final String FORMAT_${column.constantName} = DATE_TIME_FORMAT;
	</#if>
	</#list>
	
	//columns START
	<#list table.columns as column>
    /**
     * ${column.columnAlias}       db_column: ${column.sqlName} 
     */	
	<#if !column.isNullable() && !column.isPk()>
	@NotNull(message = "${column.columnAlias}不能为空")	
	</#if>
	<#if column.getJdbcType() = "VARCHAR">
	@Size(min=0, max=${column.size}, message="${column.columnAlias}长度必须在0-${column.size}之间")
	</#if>
	<#if (column.getJdbcType() = "DECIMAL") && (column.decimalDigits = 0)&&(column.size < 10) >
	@Digits(integer=${column.size},fraction=${column.decimalDigits}, message="${column.columnAlias}必须小于等于${column.size}位的整数")
	</#if>
	<#if (column.getJdbcType() = "DECIMAL") && (column.decimalDigits = 0)&&(column.size >= 10) >
	@Digits(integer=${column.size},fraction=${column.decimalDigits}, message="${column.columnAlias}必须小于等于${column.size}位的整数")
	</#if>
	<#if (column.getJdbcType() = "DECIMAL") && (column.decimalDigits > 0) >
	@Digits(integer=${column.size},fraction=${column.decimalDigits}, message="${column.columnAlias}必须长度小于${column.size}，精度在0-${column.decimalDigits}的数值")
	</#if>
	<#if (column.getJdbcType() = "CHAR")  >
	@Size(min=0, max=${column.size}, message="${column.columnAlias}长度必须在0-${column.size}之间")
	</#if>
	<#if column.columnNameLower == "id">
	private String id = GUID.getGuid();
	<#elseif column.columnNameLower == "state">
	private ${column.getJavaType()} ${column.columnNameLower}=<#if (column.getJdbcType() = "DECIMAL")>1L<#else>"1"</#if>;
	<#else>
	private ${column.getJavaType()} ${column.columnNameLower};
	</#if>
	</#list>
	//columns END

<@generateConstructor className/>
<@generateJavaColumns/>
<@generateJavaOneToMany/>
<@generateJavaManyToOne/>

	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
		<#list table.columns as column>
			.append("${column.columnName}",get${column.columnName}())
		</#list>
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
		<#list table.pkColumns as column>
			.append(get${column.columnName}())
		</#list>
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof ${className} == false) return false;
		if(this == obj) return true;
		${className} other = (${className})obj;
		return new EqualsBuilder()
			<#list table.pkColumns as column>
			.append(get${column.columnName}(),other.get${column.columnName}())
			</#list>
			.isEquals();
	}
}

<#macro generateJavaColumns>
	<#list table.columns as column>
		<#if column.isDateTimeColumn>
	/**
	 * @return ${column.columnAlias}字符串格式获取 
	 */
	public String get${column.columnName}String() {
		return DateUtil.date2String(get${column.columnName}(), FORMAT_${column.constantName});
	}
	/**
	 * @param ${column.columnNameLower} ${column.columnAlias}字符串格式设置日期
	 */
	public void set${column.columnName}String(String ${column.columnNameLower}) {
		try{
			if(StringUtil.isNotEmpty(${column.columnNameLower}))
				set${column.columnName}(DateUtil.string2Date(${column.columnNameLower}, FORMAT_${column.constantName}));
		}catch(ParseException e){
			e.printStackTrace();
			set${column.columnName}(null);
		}
	}
	
	</#if>	
	/**
	 * @param ${column.columnNameLower} ${column.columnAlias} 
	 */
	public void set${column.columnName}(${column.getJavaType()} ${column.columnNameLower}) {
		this.${column.columnNameLower} = ${column.columnNameLower};
	}
	/**
	 * @return ${column.columnAlias}
	 */
	public ${column.getJavaType()} get${column.columnName}() {
		return this.${column.columnNameLower};
	}
	</#list>
</#macro>

<#macro generateJavaOneToMany>
	<#list table.exportedKeys.associatedTables?values as foreignKey>
	<#assign fkSqlTable = foreignKey.sqlTable>
	<#assign fkTable    = fkSqlTable.className>
	<#assign fkPojoClass = fkSqlTable.className>
	<#assign fkPojoClassVar = fkPojoClass?uncap_first>
	
	private Set ${fkPojoClassVar}s = new HashSet(0);
	public void set${fkPojoClass}s(Set<${fkPojoClass}> ${fkPojoClassVar}){
		this.${fkPojoClassVar}s = ${fkPojoClassVar};
	}
	
	public Set<${fkPojoClass}> get${fkPojoClass}s() {
		return ${fkPojoClassVar}s;
	}
	</#list>
</#macro>

<#macro generateJavaManyToOne>
	<#list table.importedKeys.associatedTables?values as foreignKey>
	<#assign fkSqlTable = foreignKey.sqlTable>
	<#assign fkTable    = fkSqlTable.className>
	<#assign fkPojoClass = fkSqlTable.className>
	<#assign fkPojoClassVar = fkPojoClass?uncap_first>
	
	private ${fkPojoClass} ${fkPojoClassVar};
	
	public void set${fkPojoClass}(${fkPojoClass} ${fkPojoClassVar}){
		this.${fkPojoClassVar} = ${fkPojoClassVar};
	}
	
	public ${fkPojoClass} get${fkPojoClass}() {
		return ${fkPojoClassVar};
	}
	</#list>
</#macro>
