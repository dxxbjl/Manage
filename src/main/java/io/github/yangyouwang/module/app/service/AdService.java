package io.github.yangyouwang.module.app.service;

import io.github.yangyouwang.module.app.entity.Ad;
import io.github.yangyouwang.module.app.mapper.AdMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.stereotype.Service;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
* <p>
 * 广告表 服务实现类
 * </p>
*
* @author yangyouwang
* @since 2022-08-11
*/
@Service
public class AdService extends ServiceImpl<AdMapper, Ad> {

  /**
  * 广告表分页列表
  * @param param 参数
  * @return 结果
  */
  public List<Ad> page(Ad param) {
    QueryWrapper<Ad> queryWrapper = new QueryWrapper<>();
    queryWrapper.lambda()
      // 广告标题
          .like(!StringUtils.isEmpty(param.getAdTitle()), Ad::getAdTitle, param.getAdTitle())
          // 广告宣传图片
          .eq(!StringUtils.isEmpty(param.getAdUrl()), Ad::getAdUrl, param.getAdUrl())
          // 活动内容
          .eq(!StringUtils.isEmpty(param.getAdContent()), Ad::getAdContent, param.getAdContent())
          // 是否启用 Y 启用 N 禁用
          .eq(!StringUtils.isEmpty(param.getEnabled()), Ad::getEnabled, param.getEnabled())
          .orderByDesc(Ad::getCreateTime);
    ;
    return list(queryWrapper);
  }

  /**
  * 广告表详情
  * @param id 主键
  * @return 结果
  */
  public Ad info(Long id) {
    return getById(id);
  }

  /**
  * 广告表新增
  * @param param 根据需要进行传值
  */
  public void add(Ad param) {
    save(param);
  }

  /**
  * 广告表修改
  * @param param 根据需要进行传值
  */
  public void modify(Ad param) {
    updateById(param);
  }

  /**
  * 广告表删除(单个条目)
  * @param id 主键
  */
  public void remove(Long id) {
    removeById(id);
  }

  /**
  * 广告表删除(多个条目)
  * @param ids 主键数组
  */
  public void removes(List<Long> ids) {
     removeByIds(ids);
   }
}
