package io.github.yangyouwang.crud.app.service;

import io.github.yangyouwang.crud.api.model.UserAuthDTO;
import io.github.yangyouwang.crud.api.model.UserAuthVO;
import io.github.yangyouwang.crud.app.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* <p>
 * 用户表 服务类
 * </p>
*
* @author yangyouwang
* @since 2022-08-03
*/
  public interface IUserService extends IService<User> {

  /**
  * 用户表分页列表
  * @param param 参数
  * @return 结果
  */
  List<User> page(User param);

  /**
  * 用户表详情
  * @param id 主键
  * @return 结果
  */
  User info(Long id);

  /**
  * 用户表新增
  * @param param 根据需要进行传值
  */
  void add(User param);

  /**
  * 用户表修改
  * @param param 根据需要进行传值
  */
  void modify(User param);

  /**
  * 用户表删除(单个条目)
  * @param id 主键
  */
  void remove(Long id);

  /**
  * 删除(多个条目)
  * @param ids 主键数组
  */
  void removes(List<Long> ids);

  /**
   * 用户授权接口
   * @param userAuthDTO 授权DTO
   * @return 响应
   */
  UserAuthVO userAuth(UserAuthDTO userAuthDTO);
}
