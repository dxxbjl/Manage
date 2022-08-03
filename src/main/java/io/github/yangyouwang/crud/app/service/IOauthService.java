package io.github.yangyouwang.crud.app.service;

import io.github.yangyouwang.crud.app.entity.Oauth;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* <p>
 * 授权表 服务类
 * </p>
*
* @author yangyouwang
* @since 2022-08-03
*/
  public interface IOauthService extends IService<Oauth> {

  /**
  * 授权表分页列表
  * @param param 参数
  * @return 结果
  */
  List<Oauth> page(Oauth param);

  /**
  * 授权表详情
  * @param id 主键
  * @return 结果
  */
  Oauth info(Long id);

  /**
  * 授权表新增
  * @param param 根据需要进行传值
  */
  void add(Oauth param);

  /**
  * 授权表修改
  * @param param 根据需要进行传值
  */
  void modify(Oauth param);

  /**
  * 授权表删除(单个条目)
  * @param id 主键
  */
  void remove(Long id);

  /**
  * 删除(多个条目)
  * @param ids 主键数组
  */
  void removes(List<Long> ids);
 }
