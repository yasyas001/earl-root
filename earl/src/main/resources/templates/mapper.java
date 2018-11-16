package ${package.Mapper};

import ${package.Entity}.${entity};
import ${superMapperClassPackage};
import com.yihaodian.scm.framework.shard.dao.annotation.DAO;

/**
 * <p>
  * $!{table.comment} Dao 接口
 * </p>
 *
 * @author ${author}
 * @since ${date}
 */
@DAO
public interface ${table.mapperName} extends ${superMapperClass}<${entity}> {

}