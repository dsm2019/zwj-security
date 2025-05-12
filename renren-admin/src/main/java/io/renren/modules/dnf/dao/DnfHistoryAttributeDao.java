package io.renren.modules.dnf.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.renren.modules.dnf.entity.DnfHistoryAttributeEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 操作 dnf_history_attribute 表的 Mapper 接口
 */
@Mapper
public interface DnfHistoryAttributeDao extends BaseMapper<DnfHistoryAttributeEntity> {

}