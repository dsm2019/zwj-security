package io.renren.modules.dnf.service;

import io.renren.common.page.PageData;
import io.renren.common.service.BaseService;
import io.renren.modules.dnf.dto.CareerDto;
import io.renren.modules.dnf.dto.DnfCharacterDto;
import io.renren.modules.dnf.entity.DnfCharacterEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface DnfCharacterService extends BaseService<DnfCharacterEntity> {
    PageData<DnfCharacterDto> page(Map<String, Object> params);

    List<DnfCharacterDto> list(Map<String, Object> params);

    DnfCharacterDto get(Long id);

    void save(DnfCharacterDto dto);

    void update(DnfCharacterDto dto);

    void delete(Long[] ids);

    void refresh();

    String updateAvatar(Long id, MultipartFile file);

    List<CareerDto> getCareerList();
}