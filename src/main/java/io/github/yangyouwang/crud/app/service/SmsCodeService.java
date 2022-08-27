package io.github.yangyouwang.crud.app.service;

import io.github.yangyouwang.crud.app.entity.SmsCode;
import io.github.yangyouwang.crud.app.mapper.SmsCodeMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
* <p>
 * 短信验证码表 服务实现类
 * </p>
*
* @author yangyouwang
* @since 2022-08-27
*/
@Service
public class SmsCodeService extends ServiceImpl<SmsCodeMapper, SmsCode> {

}
