package ${package.ServiceImpl};

import ${package.Entity}.${entity};
import ${package.Mapper}.${table.mapperName};
import ${package.Service}.${table.serviceName};
import ${superServiceImplClassPackage};
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.stereotype.Service;
import io.github.yangyouwang.common.domain.PageDTO;

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
  * @param pageDTO 分页DTO
  * @return 结果
  */
  @Override
  public IPage<${entity}> page(PageDTO pageDTO) {
    QueryWrapper<${entity}> queryWrapper = new QueryWrapper<>();
    IPage<${entity}> page = page(new Page<>(pageDTO.getPageNum(), pageDTO.getPageSize()), queryWrapper);
    return page;
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