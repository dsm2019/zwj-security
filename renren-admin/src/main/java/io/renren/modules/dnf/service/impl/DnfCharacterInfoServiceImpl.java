package io.renren.modules.dnf.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.renren.common.service.impl.CrudServiceImpl;
import io.renren.modules.dnf.dao.DnfCharacterInfoDao;
import io.renren.modules.dnf.dto.DnfCharacterInfoDTO;
import io.renren.modules.dnf.entity.DnfCharacterInfoEntity;
import io.renren.modules.dnf.service.DnfCharacterInfoService;
import cn.hutool.core.util.StrUtil;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 角色信息表
 *
 * @author Mark sunlightcs@gmail.com
 * @since 1.0.0 2025-05-22
 */
@Service
public class DnfCharacterInfoServiceImpl extends CrudServiceImpl<DnfCharacterInfoDao, DnfCharacterInfoEntity, DnfCharacterInfoDTO> implements DnfCharacterInfoService {

    @Override
    public QueryWrapper<DnfCharacterInfoEntity> getWrapper(Map<String, Object> params){
        String id = (String)params.get("id");

        QueryWrapper<DnfCharacterInfoEntity> wrapper = new QueryWrapper<>();
        wrapper.eq(StrUtil.isNotBlank(id), "id", id);

        return wrapper;
    }


}