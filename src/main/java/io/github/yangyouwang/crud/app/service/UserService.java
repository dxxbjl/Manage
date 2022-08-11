package io.github.yangyouwang.crud.app.service;

import io.github.yangyouwang.crud.app.entity.User;
import io.github.yangyouwang.crud.app.mapper.UserMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.stereotype.Service;
import org.apache.commons.lang3.StringUtils;
import java.util.List;

/**
* <p>
 * 用户表 服务实现类
 * </p>
*
* @author yangyouwang
* @since 2022-08-03
*/
@Service
public class UserService extends ServiceImpl<UserMapper, User> {

  /**
  * 用户表分页列表
  * @param param 参数
  * @return 结果
  */
  public List<User> page(User param) {
    QueryWrapper<User> queryWrapper = new QueryWrapper<>();
    queryWrapper.lambda()
      // 用户昵称或网络名称
          .eq(!StringUtils.isEmpty(param.getNickName()), User::getNickName, param.getNickName())
          // 用户头像图片
          .eq(!StringUtils.isEmpty(param.getAvatar()), User::getAvatar, param.getAvatar())
          // 性别：1时是男性，值为2时是女性，值为0时是未知
          .eq(param.getGender() != null, User::getGender, param.getGender())
          // 生日
          .eq(param.getBirthday() != null, User::getBirthday, param.getBirthday())
          // 手机号
          .eq(!StringUtils.isEmpty(param.getMobile()), User::getMobile, param.getMobile())
          // 邮箱
          .eq(!StringUtils.isEmpty(param.getEmail()), User::getEmail, param.getEmail())
          // 0 可用, 1 禁用, 2 注销
          .eq(param.getStatus() != null, User::getStatus, param.getStatus())
    ;
    return list(queryWrapper);
  }

  /**
  * 用户表详情
  * @param id 主键
  * @return 结果
  */
  public User info(Long id) {
    return getById(id);
  }

  /**
  * 用户表新增
  * @param param 根据需要进行传值
  */
  public void add(User param) {
    save(param);
  }

  /**
  * 用户表修改
  * @param param 根据需要进行传值
  */
  public void modify(User param) {
    updateById(param);
  }

  /**
  * 用户表删除(单个条目)
  * @param id 主键
  */
  public void remove(Long id) {
    removeById(id);
  }

  /**
  * 用户表删除(多个条目)
  * @param ids 主键数组
  */
  public void removes(List<Long> ids) {
     removeByIds(ids);
   }
}
