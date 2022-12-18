package io.github.yangyouwang.crud.app.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import io.github.yangyouwang.crud.app.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 用户表 Mapper 接口
 * </p>
 *
 * @author yangyouwang
 * @since 2022-08-03
 */
public interface UserMapper extends BaseMapper<User> {
    /**
     * 查询用户列表
     * @param wrapper 查询条件
     * @return 用户列表
     */
    List<User> getUserList(@Param(Constants.WRAPPER) Wrapper wrapper);
}
