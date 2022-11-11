package io.github.yangyouwang.crud.app.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import io.github.yangyouwang.common.domain.TreeSelectNode;
import io.github.yangyouwang.core.converter.ListToTree;
import io.github.yangyouwang.core.converter.impl.ListToTreeImpl;
import io.github.yangyouwang.crud.app.entity.Category;
import io.github.yangyouwang.crud.app.mapper.CategoryMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.stereotype.Service;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
* <p>
 * 分类表 服务实现类
 * </p>
*
* @author yangyouwang
* @since 2022-10-30
*/
@Service
public class CategoryService extends ServiceImpl<CategoryMapper, Category> {

  @Resource
  private CategoryMapper categoryMapper;
  /**
  * 分类表分页列表
  * @param param 参数
  * @return 结果
  */
  public List<Category> page(Category param) {
    QueryWrapper<Category> queryWrapper = new QueryWrapper<>();
    queryWrapper.lambda()
          // 名称
          .like(!StringUtils.isEmpty(param.getName()), Category::getName, param.getName())
            .orderByAsc(Category::getParentId,Category::getOrderNum);
    return list(queryWrapper);
  }

  /**
  * 分类表详情
  * @param id 主键
  * @return 结果
  */
  public Category info(Long id) {
    return categoryMapper.info(id);
  }

  /**
  * 分类表新增
  * @param param 根据需要进行传值
  */
  public void add(Category param) {
    save(param);
  }

  /**
  * 分类表修改
  * @param param 根据需要进行传值
  */
  public void modify(Category param) {
    updateById(param);
  }

  /**
  * 分类表删除(单个条目)
  * @param id 主键
  */
  public void remove(Long id) {
    removeById(id);
  }

  /**
  * 分类表删除(多个条目)
  * @param ids 主键数组
  */
  public void removes(List<Long> ids) {
     removeByIds(ids);
   }

  /**
   * 查询分类树结构
   * @return 分类树结构
  */
  public List<TreeSelectNode> treeSelect() {
    List<Category> categories = this.list(new LambdaQueryWrapper<>());
    // 父分类ID是否存在顶级
    boolean flag = categories.stream().anyMatch(s -> 0 == s.getParentId());
    if (categories.isEmpty() || !flag) {
      return Collections.emptyList();
    }
    List<TreeSelectNode> result = categories.stream().map(sysMenu -> {
      TreeSelectNode treeNode = new TreeSelectNode();
      treeNode.setId(sysMenu.getId());
      treeNode.setParentId(sysMenu.getParentId());
      treeNode.setName(sysMenu.getName());
      return treeNode;
    }).collect(Collectors.toList());
    ListToTree treeBuilder = new ListToTreeImpl();
    return treeBuilder.toTree(result);
  }
}
