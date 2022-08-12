package io.github.yangyouwang.crud.api.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import io.github.yangyouwang.common.constant.ConfigConsts;
import io.github.yangyouwang.crud.api.model.IndexVO;
import io.github.yangyouwang.crud.app.entity.Ad;
import io.github.yangyouwang.crud.app.service.AdService;
import org.springframework.beans.BeanUtils;
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
    private AdService adService;
    /**
     * 获取首页数据
     */
    public IndexVO getIndexData() {
        // 获取轮播图列表
        List<Ad> list = adService.list(new LambdaQueryWrapper<Ad>()
                .eq(Ad::getEnabled, ConfigConsts.ENABLED_YES));
        List<IndexVO.AdVO> adVOList = list.stream().map(s -> {
            IndexVO.AdVO adVO = new IndexVO.AdVO();
            BeanUtils.copyProperties(s, adVO);
            return adVO;
        }).collect(Collectors.toList());
        IndexVO indexVO = new IndexVO();
        indexVO.setAdVOList(adVOList);
        return indexVO;
    }
}
