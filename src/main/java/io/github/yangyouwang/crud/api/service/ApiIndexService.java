package io.github.yangyouwang.crud.api.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import io.github.yangyouwang.common.constant.ConfigConsts;
import io.github.yangyouwang.crud.app.entity.Notice;
import io.github.yangyouwang.crud.app.service.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Description: 首页业务层 <br/>
 * date: 2022/8/12 13:02<br/>
 *
 * @author yangyouwang<br />
 * @version v1.0
 * @since JDK 1.8
 */
@Service
public class ApiIndexService {

    @Autowired
    private NoticeService noticeService;

    /**
     * 通知公告接口
     * @return 响应
     */
    public Object getNotice() {
        // 通知公告
        List<Notice> noticeList = noticeService.list(new LambdaQueryWrapper<Notice>()
                .eq(Notice::getNoticeStatus, ConfigConsts.ENABLED_YES));
        List<String> noticeVOList = noticeList.stream().map(Notice::getNoticeTitle).collect(Collectors.toList());
        return String.join(" ", noticeVOList);
    }
}
