package io.renren.modules.dnf.service;

import io.renren.common.page.PageData;
import io.renren.common.service.BaseService;
import io.renren.modules.dnf.dto.DnfHistoryAttributeDto;
import io.renren.modules.dnf.dto.TrendDataDto;
import io.renren.modules.dnf.entity.DnfHistoryAttributeEntity;

import java.util.List;
import java.util.Map;

public interface DnfHistoryAttributeService extends BaseService<DnfHistoryAttributeEntity> {
    PageData<DnfHistoryAttributeDto> page(Map<String, Object> params);

    List<DnfHistoryAttributeDto> list(Map<String, Object> params);

    List<TrendDataDto> getTrendData(String attribute, String from, String to);

    DnfHistoryAttributeDto get(Long id);

    void save(DnfHistoryAttributeDto dto);

    void update(DnfHistoryAttributeDto dto);

    void delete(Long[] ids);
}