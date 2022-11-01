package io.github.yangyouwang.crud.app.service;

import io.github.yangyouwang.crud.app.entity.News;
import io.github.yangyouwang.crud.app.mapper.NewsMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.stereotype.Service;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
* <p>
 * 新闻表 服务实现类
 * </p>
*
* @author yangyouwang
* @since 2022-11-01
*/
@Service
public class NewsService extends ServiceImpl<NewsMapper, News> {

  /**
  * 新闻表分页列表
  * @param param 参数
  * @return 结果
  */
  public List<News> page(News param) {
    QueryWrapper<News> queryWrapper = new QueryWrapper<>();
    queryWrapper.lambda()
          // 标题
          .like(!StringUtils.isEmpty(param.getTitle()), News::getTitle, param.getTitle())
    .orderByDesc(News::getCreateTime);
    return list(queryWrapper);
  }

  /**
  * 新闻表详情
  * @param id 主键
  * @return 结果
  */
  public News info(Long id) {
    return getById(id);
  }

  /**
  * 新闻表新增
  * @param param 根据需要进行传值
  */
  public void add(News param) {
    save(param);
  }

  /**
  * 新闻表修改
  * @param param 根据需要进行传值
  */
  public void modify(News param) {
    updateById(param);
  }

  /**
  * 新闻表删除(单个条目)
  * @param id 主键
  */
  public void remove(Long id) {
    removeById(id);
  }

  /**
  * 新闻表删除(多个条目)
  * @param ids 主键数组
  */
  public void removes(List<Long> ids) {
     removeByIds(ids);
   }
}
