package ${package.Entity};

#foreach($pkg in ${table.importPackages})
import ${pkg};
#end

/**
 * <p>
 * $!{table.comment}
 * </p>
 *
 * @author ${author}
 * @since ${date}
 */
#if(${table.convert})
@TableName("${table.name}")
#end
#if(${superEntityClass})
public class ${entity} extends ${superEntityClass}<${entity}>#if(${activeRecord})<${entity}>#end {
#elseif(${activeRecord})
public class ${entity} extends Model<${entity}> {
#else
public class ${entity} implements Serializable {
#end

    private static final long serialVersionUID = 1L;

#foreach($field in ${table.fields})
#if(${field.keyFlag})
#set($keyPropertyName=${field.propertyName})
#end
#if("$!field.comment" != "")
    /**
     * ${field.comment}
     */
#end
	private ${field.propertyType} ${field.propertyName};
#end

#foreach($field in ${table.fields})
#if(${field.propertyType.equals("Boolean")})
#set($getprefix="is")
#else
#set($getprefix="get")
#end

	public ${field.propertyType} ${getprefix}${field.capitalName}() {
		return ${field.propertyName};
	}

#if(${entityBuilderModel})
	public ${entity} set${field.capitalName}(${field.propertyType} ${field.propertyName}) {
#else
	public void set${field.capitalName}(${field.propertyType} ${field.propertyName}) {
#end
		this.${field.propertyName} = ${field.propertyName};
#if(${entityBuilderModel})
		return this;
#end
	}
#end

#if(${entityColumnConstant})
#foreach($field in ${table.fields})
	public static final String ${field.name.toUpperCase()} = "${field.name}";

#end
#end
#if(${activeRecord})
	@Override
	protected Serializable pkVal() {
#if(${keyPropertyName})
		return this.${keyPropertyName};
#else
		return this.id;
#end
	}

#end
}
