package io.github.yangyouwang.module.api.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.yangyouwang.common.constant.ConfigConsts;
import io.github.yangyouwang.module.app.entity.Notice;
import io.github.yangyouwang.module.app.mapper.NoticeMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
/**
 * Description: 公告业务层 <br/>
 * date: 2022/12/6 11:15<br/>
 *
 * @author yangyouwang<br />
 * @version v1.0
 * @since JDK 1.8
 */
@Service
public class ApiNoticeService extends ServiceImpl<NoticeMapper, Notice> {
    /**
     * 通知公告接口
     * @return 响应
     */
    public Object getNotice() {
        // 通知公告
        List<Notice> noticeList = this.list(new LambdaQueryWrapper<Notice>()
                .eq(Notice::getNoticeStatus, ConfigConsts.SYS_YES));
        List<String> noticeVOList = noticeList.stream().map(Notice::getNoticeTitle).collect(Collectors.toList());
        return String.join(" ", noticeVOList);
    }
}
