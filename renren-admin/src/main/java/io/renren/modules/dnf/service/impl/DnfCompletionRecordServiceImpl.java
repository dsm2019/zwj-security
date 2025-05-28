package io.renren.modules.dnf.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.renren.common.service.impl.CrudServiceImpl;
import io.renren.modules.dnf.dao.DnfCompletionRecordDao;
import io.renren.modules.dnf.dto.DnfCompletionRecordDTO;
import io.renren.modules.dnf.entity.DnfCompletionRecordEntity;
import io.renren.modules.dnf.service.DnfCompletionRecordService;
import cn.hutool.core.util.StrUtil;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 地下城表
 *
 * @author Mark sunlightcs@gmail.com
 * @since 1.0.0 2025-05-23
 */
@Service
public class DnfCompletionRecordServiceImpl extends CrudServiceImpl<DnfCompletionRecordDao, DnfCompletionRecordEntity, DnfCompletionRecordDTO> implements DnfCompletionRecordService {

    @Override
    public QueryWrapper<DnfCompletionRecordEntity> getWrapper(Map<String, Object> params){
        String id = (String)params.get("id");

        QueryWrapper<DnfCompletionRecordEntity> wrapper = new QueryWrapper<>();
        wrapper.eq(StrUtil.isNotBlank(id), "id", id);

        return wrapper;
    }


}