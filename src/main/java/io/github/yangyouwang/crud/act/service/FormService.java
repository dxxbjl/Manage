package io.github.yangyouwang.crud.act.service;

import io.github.yangyouwang.crud.act.entity.Form;
import io.github.yangyouwang.crud.act.mapper.FormMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.stereotype.Service;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
* <p>
 * 表单 服务实现类
 * </p>
*
* @author yangyouwang
* @since 2022-08-31
*/
@Service
public class FormService extends ServiceImpl<FormMapper, Form> {

  /**
  * 表单分页列表
  * @param param 参数
  * @return 结果
  */
  public List<Form> page(Form param) {
    QueryWrapper<Form> queryWrapper = new QueryWrapper<>();
    queryWrapper.lambda()
      // 表单名称
          .eq(!StringUtils.isEmpty(param.getFormName()), Form::getFormName, param.getFormName())
    ;
    return list(queryWrapper);
  }

  /**
  * 表单详情
  * @param id 主键
  * @return 结果
  */
  public Form info(Long id) {
    return getById(id);
  }

  /**
  * 表单新增
  * @param param 根据需要进行传值
  */
  public void add(Form param) {
    save(param);
  }

  /**
  * 表单修改
  * @param param 根据需要进行传值
  */
  public void modify(Form param) {
    updateById(param);
  }

  /**
  * 表单删除(单个条目)
  * @param id 主键
  */
  public void remove(Long id) {
    removeById(id);
  }

  /**
  * 表单删除(多个条目)
  * @param ids 主键数组
  */
  public void removes(List<Long> ids) {
     removeByIds(ids);
   }
}
