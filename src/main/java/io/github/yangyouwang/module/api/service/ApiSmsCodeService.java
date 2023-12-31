package io.github.yangyouwang.module.api.service;

import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.yangyouwang.common.constant.ConfigConsts;
import io.github.yangyouwang.core.util.aliyun.SampleSms;
import io.github.yangyouwang.core.util.DateTimeUtil;
import io.github.yangyouwang.module.app.entity.SmsCode;
import io.github.yangyouwang.module.app.mapper.SmsCodeMapper;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.Calendar;
import java.util.Date;

/**
 * Description: 短信验证码表 服务实现类 <br/>
 * date: 2022/12/5 22:48<br/>
 *
 * @author yangyouwang<br />
 * @version v1.0
 * @since JDK 1.8
 */
@Service
public class ApiSmsCodeService extends ServiceImpl<SmsCodeMapper, SmsCode> {

    @Resource
    private SampleSms sampleSms;
    /**
     * 校验手机验证码
     * @param mobile 手机号
     * @param code 验证码
     */
    public void checkMobileCode(String mobile,String code) {
        SmsCode smsCode = this.getOne(new LambdaQueryWrapper<SmsCode>()
                .eq(SmsCode::getMobile, mobile)
                .eq(SmsCode::getCode, code)
                .eq(SmsCode::getUsable, ConfigConsts.USABLE_EFFECTIVE)
                .eq(SmsCode::getSended, ConfigConsts.SEND_HAS_BEEN_SENT));
        Assert.notNull(smsCode,"验证码不存在");
        // 当前时间小于过期时间
        boolean flag = DateTimeUtil.compare(new Date(),smsCode.getDeadLine());
        Assert.isTrue(flag,"验证码失效");
        // 验证码作废
        smsCode.setUsable(ConfigConsts.USABLE_INVALID);
        this.updateById(smsCode);
    }

    /**
     * 发送验证码
     * @param mobile 手机号
     * @return 发送成功
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
        SendSmsResponse sendSmsResponse = sampleSms.sendSms(mobile, "SMS_176520044", "{\"code\":"+code+"}");
        Assert.isTrue(sendSmsResponse.getCode().equals("OK"),sendSmsResponse.getMessage());
        //请求成功
        smsCode.setUsable(ConfigConsts.USABLE_EFFECTIVE);
        smsCode.setSended(ConfigConsts.SEND_HAS_BEEN_SENT);
        return this.save(smsCode);
    }
}
