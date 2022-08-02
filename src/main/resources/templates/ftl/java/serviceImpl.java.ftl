package ${package.ServiceImpl};

import ${package.Entity}.${entity};
import ${package.Mapper}.${table.mapperName};
import ${package.Service}.${table.serviceName};
import ${superServiceImplClassPackage};
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* <p>
 * ${table.comment!} 服务实现类
 * </p>
*
* @author ${author}
* @since ${date}
*/
@Service
<#if kotlin>
open class ${table.serviceImplName} : ${superServiceImplClass}<${table.mapperName}, ${entity}>(), ${table.serviceName} {

}
<#else>
public class ${table.serviceImplName} extends ${superServiceImplClass}<${table.mapperName}, ${entity}> implements ${table.serviceName} {

  /**
  * ${table.comment!}分页列表
  * @param param 参数
  * @return 结果
  */
  @Override
  public List<${entity}> page(${entity} param) {
    QueryWrapper<${entity}> queryWrapper = new QueryWrapper<>();
    return list(queryWrapper);
  }

  /**
  * ${table.comment!}详情
  * @param id 主键
  * @return 结果
  */
  @Override
  public ${entity} info(Long id) {
    return getById(id);
  }

  /**
  * ${table.comment!}新增
  * @param param 根据需要进行传值
  */
  @Override
  public void add(${entity} param) {
    save(param);
  }

  /**
  * ${table.comment!}修改
  * @param param 根据需要进行传值
  */
  @Override
  public void modify(${entity} param) {
    updateById(param);
  }

  /**
  * ${table.comment!}删除(单个条目)
  * @param id 主键
  */
  @Override
  public void remove(Long id) {
    removeById(id);
  }

  /**
  * ${table.comment!}删除(多个条目)
  * @param ids 主键数组
  */
  @Override
  public void removes(List<Long> ids) {
     removeByIds(ids);
   }
}
 </#if>