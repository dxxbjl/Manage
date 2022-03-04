package io.github.yangyouwang.core.runner;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import io.github.yangyouwang.common.constant.ConfigConsts;
import io.github.yangyouwang.crud.system.entity.SysDictType;
import io.github.yangyouwang.crud.system.service.SysDictTypeService;
import io.github.yangyouwang.crud.system.service.SysDictValueService;
import lombok.AllArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author yangyouwang
 * @title: InItDictCacheRunner
 * @projectName crud
 * @description: 初始化字典缓存到redis
 * @date 2022/3/415:25 PM
 */
@Component
@AllArgsConstructor
public class InItDictCacheRunner implements CommandLineRunner {

    private SysDictTypeService sysDictTypeService;

    private SysDictValueService sysDictValueService;

    @Override
    public void run(String... args) {
        List<SysDictType> sysDictTypes = sysDictTypeService.list(new LambdaQueryWrapper<SysDictType>()
                .eq(SysDictType::getEnabled, ConfigConsts.ENABLED_YES).select(SysDictType::getDictKey));
        sysDictTypes.forEach(sysDictType -> {
            String dictKey = sysDictType.getDictKey();
            if (Strings.isNotBlank(dictKey)) {
                sysDictValueService.getDictValues(dictKey);
            }
        });
    }
}
