package io.renren.modules.dnf.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.renren.modules.dnf.entity.DnfCharacterEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 操作 dnf_character 表的 Mapper 接口
 */
@Mapper
public interface DnfCharacterDao extends BaseMapper<DnfCharacterEntity> {

}