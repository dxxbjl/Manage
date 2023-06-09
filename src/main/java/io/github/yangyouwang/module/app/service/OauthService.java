package io.github.yangyouwang.module.app.service;

import io.github.yangyouwang.module.app.entity.Oauth;
import io.github.yangyouwang.module.app.mapper.OauthMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
* <p>
 * 授权表 服务实现类
 * </p>
*
* @author yangyouwang
* @since 2022-08-16
*/
@Service
public class OauthService extends ServiceImpl<OauthMapper, Oauth> {

}
