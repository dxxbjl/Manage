package io.github.yangyouwang.crud.app.service;

import io.github.yangyouwang.crud.app.entity.Oauth;
import io.github.yangyouwang.crud.app.mapper.OauthMapper;
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
* @since 2022-08-16
*/
@Service
public class OauthService extends ServiceImpl<OauthMapper, Oauth> {

  /**
  * 授权表分页列表
  * @param param 参数
  * @return 结果
  */
  public List<Oauth> page(Oauth param) {
    QueryWrapper<Oauth> queryWrapper = new QueryWrapper<>();
    queryWrapper.lambda()
      // 秘钥
          .eq(!StringUtils.isEmpty(param.getAppSecret()), Oauth::getAppSecret, param.getAppSecret())
          // 类型：wx微信、phone手机、password密码
          .eq(!StringUtils.isEmpty(param.getAppType()), Oauth::getAppType, param.getAppType())
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
  public Oauth info(Long id) {
    return getById(id);
  }

  /**
  * 授权表新增
  * @param param 根据需要进行传值
  */
  public void add(Oauth param) {
    save(param);
  }

  /**
  * 授权表修改
  * @param param 根据需要进行传值
  */
  public void modify(Oauth param) {
    updateById(param);
  }

  /**
  * 授权表删除(单个条目)
  * @param id 主键
  */
  public void remove(Long id) {
    removeById(id);
  }

  /**
  * 授权表删除(多个条目)
  * @param ids 主键数组
  */
  public void removes(List<Long> ids) {
     removeByIds(ids);
   }
}
