package io.github.yangyouwang.crud.api.service;

import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import io.github.yangyouwang.common.constant.ConfigConsts;
import io.github.yangyouwang.core.aliyun.SampleSms;
import io.github.yangyouwang.crud.app.entity.Notice;
import io.github.yangyouwang.crud.app.entity.SmsCode;
import io.github.yangyouwang.crud.app.service.NoticeService;
import io.github.yangyouwang.crud.app.service.SmsCodeService;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
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
    private SmsCodeService smsCodeService;

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

    /**
     * 发送手机验证码
     */
    public boolean sendMobileCode(String mobile) {
        String code = RandomStringUtils.random(4, false, true);
        // 保存数据
        SmsCode smsCode = new SmsCode();
        smsCode.setMobile(mobile);
        smsCode.setCode(code);
        Calendar nowTime = Calendar.getInstance();
        nowTime.add(Calendar.MINUTE, 5);
        smsCode.setDeadLine(nowTime.getTime());
        // 发送验证码
        SendSmsResponse sendSmsResponse = SampleSms.sendSms(mobile, "", code);
        if(sendSmsResponse.getCode() != null && sendSmsResponse.getCode().equals("OK")) {
            //请求成功
            smsCode.setUsable(ConfigConsts.USABLE_EFFECTIVE);
            smsCode.setSended(ConfigConsts.SEND_HAS_BEEN_SENT);
        }
        return smsCodeService.save(smsCode);
    }

}
