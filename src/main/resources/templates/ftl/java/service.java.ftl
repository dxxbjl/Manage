package ${package.Service};

import ${package.Entity}.${entity};
import ${superServiceClassPackage};

import java.util.List;

/**
* <p>
 * ${table.comment!} 服务类
 * </p>
*
* @author ${author}
* @since ${date}
*/
<#if kotlin>
interface ${table.serviceName} : ${superServiceClass}<${entity}>
<#else>
  public interface ${table.serviceName} extends ${superServiceClass}<${entity}> {

  /**
  * ${table.comment!}分页列表
  * @param param 参数
  * @return 结果
  */
  List<${entity}> page(${entity} param);

  /**
  * ${table.comment!}详情
  * @param id 主键
  * @return 结果
  */
  ${entity} info(Long id);

  /**
  * ${table.comment!}新增
  * @param param 根据需要进行传值
  */
  void add(${entity} param);

  /**
  * ${table.comment!}修改
  * @param param 根据需要进行传值
  */
  void modify(${entity} param);

  /**
  * ${table.comment!}删除(单个条目)
  * @param id 主键
  */
  void remove(Long id);

  /**
  * 删除(多个条目)
  * @param ids 主键数组
  */
  void removes(List<Long> ids);
 }
 </#if>