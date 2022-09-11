package io.github.yangyouwang.crud.system.service;

import io.github.yangyouwang.common.domain.TreeSelectNode;
import io.github.yangyouwang.core.converter.ListToTree;
import io.github.yangyouwang.core.converter.impl.ListToTreeImpl;
import io.github.yangyouwang.crud.system.entity.SysDept;
import io.github.yangyouwang.crud.system.mapper.SysDeptMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.stereotype.Service;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;

/**
* <p>
 * 部门表 服务实现类
 * </p>
*
* @author yangyouwang
* @since 2022-09-03
*/
@Service
public class SysDeptService extends ServiceImpl<SysDeptMapper, SysDept> {

  @Resource
  private SysDeptMapper sysDeptMapper;

  /**
  * 部门表分页列表
  * @param param 参数
  * @return 结果
  */
  public List<SysDept> page(SysDept param) {
    QueryWrapper<SysDept> queryWrapper = new QueryWrapper<>();
    queryWrapper.lambda()
      // 部门名称
          .like(!StringUtils.isEmpty(param.getDeptName()), SysDept::getDeptName, param.getDeptName())
          // 负责人
          .eq(!StringUtils.isEmpty(param.getLeader()), SysDept::getLeader, param.getLeader())
          // 手机号
          .eq(!StringUtils.isEmpty(param.getPhone()), SysDept::getPhone, param.getPhone())
          .orderByAsc(SysDept::getParentId,SysDept::getOrderNum)
    ;
    return list(queryWrapper);
  }

  /**
  * 部门表详情
  * @param id 主键
  * @return 结果
  */
  public SysDept info(Long id) {
    return getById(id);
  }

  /**
  * 部门表新增
  * @param param 根据需要进行传值
  */
  public void add(SysDept param) {
    save(param);
  }

  /**
  * 部门表修改
  * @param param 根据需要进行传值
  */
  public void modify(SysDept param) {
    updateById(param);
  }

  /**
  * 部门表删除(单个条目)
  * @param id 主键
  */
  public void remove(Long id) {
    removeById(id);
  }

  /**
  * 部门表删除(多个条目)
  * @param ids 主键数组
  */
  public void removes(List<Long> ids) {
     removeByIds(ids);
   }
  /**
   * 查询部门列表
   * @return 部门列表
   */
  public List<TreeSelectNode> treeSelect() {
    List<TreeSelectNode> depts = sysDeptMapper.getDeptTree();
    if (depts.isEmpty()) {
      return Collections.emptyList();
    }
    ListToTree treeBuilder = new ListToTreeImpl();
    return treeBuilder.toTree(depts);
  }
}
