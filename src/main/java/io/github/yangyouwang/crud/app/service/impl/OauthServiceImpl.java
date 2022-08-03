package io.github.yangyouwang.crud.app.service.impl;

import io.github.yangyouwang.crud.app.entity.Oauth;
import io.github.yangyouwang.crud.app.mapper.OauthMapper;
import io.github.yangyouwang.crud.app.service.IOauthService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.stereotype.Service;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
* <p>
 * 授权表 服务实现类
 * </p>
*
* @author yangyouwang
* @since 2022-08-03
*/
@Service
public class OauthServiceImpl extends ServiceImpl<OauthMapper, Oauth> implements IOauthService {

  /**
  * 授权表分页列表
  * @param param 参数
  * @return 结果
  */
  @Override
  public List<Oauth> page(Oauth param) {
    QueryWrapper<Oauth> queryWrapper = new QueryWrapper<>();
    queryWrapper.lambda()
      // open_id
          .eq(!StringUtils.isEmpty(param.getOpenId()), Oauth::getOpenId, param.getOpenId())
          // 类型 1 微信
          .eq(param.getAppType() != null, Oauth::getAppType, param.getAppType())
          // 用户id
          .eq(param.getUserId() != null, Oauth::getUserId, param.getUserId())
    ;
    return list(queryWrapper);
  }

  /**
  * 授权表详情
  * @param id 主键
  * @return 结果
  */
  @Override
  public Oauth info(Long id) {
    return getById(id);
  }

  /**
  * 授权表新增
  * @param param 根据需要进行传值
  */
  @Override
  public void add(Oauth param) {
    save(param);
  }

  /**
  * 授权表修改
  * @param param 根据需要进行传值
  */
  @Override
  public void modify(Oauth param) {
    updateById(param);
  }

  /**
  * 授权表删除(单个条目)
  * @param id 主键
  */
  @Override
  public void remove(Long id) {
    removeById(id);
  }

  /**
  * 授权表删除(多个条目)
  * @param ids 主键数组
  */
  @Override
  public void removes(List<Long> ids) {
     removeByIds(ids);
   }
}
