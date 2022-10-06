package io.github.yangyouwang.crud.app.service;

import io.github.yangyouwang.crud.app.entity.Notice;
import io.github.yangyouwang.crud.app.mapper.NoticeMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.stereotype.Service;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
* <p>
 * 通知公告表 服务实现类
 * </p>
*
* @author yangyouwang
* @since 2022-10-04
*/
@Service
public class NoticeService extends ServiceImpl<NoticeMapper, Notice> {

  /**
  * 通知公告表分页列表
  * @param param 参数
  * @return 结果
  */
  public List<Notice> page(Notice param) {
    QueryWrapper<Notice> queryWrapper = new QueryWrapper<>();
    queryWrapper.lambda()
      // 标题
          .like(!StringUtils.isEmpty(param.getNoticeTitle()), Notice::getNoticeTitle, param.getNoticeTitle())
    .orderByDesc(Notice::getCreateTime);
    return list(queryWrapper);
  }

  /**
  * 通知公告表详情
  * @param id 主键
  * @return 结果
  */
  public Notice info(Long id) {
    return getById(id);
  }

  /**
  * 通知公告表新增
  * @param param 根据需要进行传值
  */
  public void add(Notice param) {
    save(param);
  }

  /**
  * 通知公告表修改
  * @param param 根据需要进行传值
  */
  public void modify(Notice param) {
    updateById(param);
  }

  /**
  * 通知公告表删除(单个条目)
  * @param id 主键
  */
  public void remove(Long id) {
    removeById(id);
  }

  /**
  * 通知公告表删除(多个条目)
  * @param ids 主键数组
  */
  public void removes(List<Long> ids) {
     removeByIds(ids);
   }
}
