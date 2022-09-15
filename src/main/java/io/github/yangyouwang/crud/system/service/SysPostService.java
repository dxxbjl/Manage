package io.github.yangyouwang.crud.system.service;

import io.github.yangyouwang.crud.system.entity.SysPost;
import io.github.yangyouwang.crud.system.mapper.SysPostMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.stereotype.Service;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
* <p>
 * 岗位表 服务实现类
 * </p>
*
* @author yangyouwang
* @since 2022-09-15
*/
@Service
public class SysPostService extends ServiceImpl<SysPostMapper, SysPost> {

  /**
  * 岗位表分页列表
  * @param param 参数
  * @return 结果
  */
  public List<SysPost> page(SysPost param) {
    QueryWrapper<SysPost> queryWrapper = new QueryWrapper<>();
    queryWrapper.lambda()
      // 岗位编码
          .like(!StringUtils.isEmpty(param.getPostCode()), SysPost::getPostCode, param.getPostCode())
          // 岗位名称
          .like(!StringUtils.isEmpty(param.getPostName()), SysPost::getPostName, param.getPostName())
    .orderByDesc(SysPost::getCreateTime);
    return list(queryWrapper);
  }

  /**
  * 岗位表详情
  * @param id 主键
  * @return 结果
  */
  public SysPost info(Long id) {
    return getById(id);
  }

  /**
  * 岗位表新增
  * @param param 根据需要进行传值
  */
  public void add(SysPost param) {
    save(param);
  }

  /**
  * 岗位表修改
  * @param param 根据需要进行传值
  */
  public void modify(SysPost param) {
    updateById(param);
  }

  /**
  * 岗位表删除(单个条目)
  * @param id 主键
  */
  public void remove(Long id) {
    removeById(id);
  }

  /**
  * 岗位表删除(多个条目)
  * @param ids 主键数组
  */
  public void removes(List<Long> ids) {
     removeByIds(ids);
   }
}
