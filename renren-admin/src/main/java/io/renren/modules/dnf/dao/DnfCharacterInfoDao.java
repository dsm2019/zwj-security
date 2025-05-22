package io.renren.modules.dnf.dao;

import io.renren.common.dao.BaseDao;
import io.renren.modules.dnf.entity.DnfCharacterInfoEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 角色信息表
 *
 * @author Mark sunlightcs@gmail.com
 * @since 1.0.0 2025-05-22
 */
@Mapper
public interface DnfCharacterInfoDao extends BaseDao<DnfCharacterInfoEntity> {
	
}