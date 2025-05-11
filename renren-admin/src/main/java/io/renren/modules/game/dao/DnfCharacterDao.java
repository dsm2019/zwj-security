package io.renren.modules.game.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.renren.modules.game.entity.DnfCharacterEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 操作 dnf_character 表的 Mapper 接口
 */
@Mapper
public interface DnfCharacterDao extends BaseMapper<DnfCharacterEntity> {

}