package io.renren.modules.dnf.service;

import io.renren.common.page.PageData;
import io.renren.common.service.BaseService;
import io.renren.modules.dnf.dto.DnfDungeonDto;
import io.renren.modules.dnf.dto.DungeonTypeDto;
import io.renren.modules.dnf.entity.DnfDungeonEntity;

import java.util.List;
import java.util.Map;

public interface DnfDungeonService extends BaseService<DnfDungeonEntity> {
    PageData<DnfDungeonDto> page(Map<String, Object> params);

    List<DnfDungeonDto> list(Map<String, Object> params);

    DnfDungeonDto get(Long id);

    void save(DnfDungeonDto dto);

    void update(DnfDungeonDto dto);

    void delete(Long[] ids);

    List<DungeonTypeDto> typeList();
}