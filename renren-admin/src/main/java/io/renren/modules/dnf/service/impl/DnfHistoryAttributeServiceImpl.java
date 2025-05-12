package io.renren.modules.dnf.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.renren.common.constant.Constant;
import io.renren.common.page.PageData;
import io.renren.common.service.impl.BaseServiceImpl;
import io.renren.common.utils.ConvertUtils;
import io.renren.modules.dnf.dao.DnfHistoryAttributeDao;
import io.renren.modules.dnf.dto.DnfCharacterDto;
import io.renren.modules.dnf.dto.DnfHistoryAttributeDto;
import io.renren.modules.dnf.entity.DnfHistoryAttributeEntity;
import io.renren.modules.dnf.service.DnfCharacterService;
import io.renren.modules.dnf.service.DnfHistoryAttributeService;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
@AllArgsConstructor
public class DnfHistoryAttributeServiceImpl extends BaseServiceImpl<DnfHistoryAttributeDao, DnfHistoryAttributeEntity> implements DnfHistoryAttributeService {

    private final DnfCharacterService dnfCharacterService;

    @Override
    public PageData<DnfHistoryAttributeDto> page(Map<String, Object> params) {
        IPage<DnfHistoryAttributeEntity> page = baseDao.selectPage(
                getPage(params, Constant.CREATE_DATE, false),
                getWrapper(params)
        );

//        List<Long> ids = page.getRecords().stream().map(DnfHistoryAttributeEntity::getUserId).toList();
        return getPageData(page, DnfHistoryAttributeDto.class);
    }

    private QueryWrapper<DnfHistoryAttributeEntity> getWrapper(Map<String, Object> params) {
        String name = (String) params.get("name");

        QueryWrapper<DnfHistoryAttributeEntity> wrapper = new QueryWrapper<>();
        wrapper.like(StrUtil.isNotBlank(name), "name", name);

        return wrapper;
    }

    @Override
    public List<DnfHistoryAttributeDto> list(Map<String, Object> params) {
        List<DnfHistoryAttributeEntity> entityList = baseDao.selectList(getWrapper(params));
        return ConvertUtils.sourceToTarget(entityList, DnfHistoryAttributeDto.class);
    }

    @Override
    public DnfHistoryAttributeDto get(Long id) {
        DnfHistoryAttributeEntity entity = baseDao.selectById(id);
        return ConvertUtils.sourceToTarget(entity, DnfHistoryAttributeDto.class);
    }

    @Override
    public void save(DnfHistoryAttributeDto dto) {
        fillDate(dto);
        DnfHistoryAttributeEntity oldEntity = findBy(dto.getCharacterId(), dto.getRecordDate());
        if (Objects.nonNull(oldEntity)) {
            dto.setId(oldEntity.getId());
            DnfHistoryAttributeEntity entity = ConvertUtils.sourceToTarget(dto, DnfHistoryAttributeEntity.class);
            updateById(entity);
            return;
        }

        DnfCharacterDto dnfCharacterDto = dnfCharacterService.get(dto.getCharacterId());

        dto.setCharacterName(dnfCharacterDto.getName());
        DnfHistoryAttributeEntity entity = ConvertUtils.sourceToTarget(dto, DnfHistoryAttributeEntity.class);
        insert(entity);

        dnfCharacterDto.setFame(dto.getFame());
        dnfCharacterDto.setSimulatedDamage(dto.getSimulatedDamage());
        dnfCharacterService.save(dnfCharacterDto);
    }

    private DnfHistoryAttributeEntity findBy(Long characterId, String recordDate) {
        QueryWrapper<DnfHistoryAttributeEntity> wrapper = new QueryWrapper<>();
        wrapper.eq("character_id", characterId);
        wrapper.eq("record_date", recordDate);
        return baseDao.selectOne(wrapper);
    }

    private void fillDate(DnfHistoryAttributeDto dto) {
        LocalDate currentDate = LocalDate.now();

        dto.setYear(currentDate.getYear());
        dto.setMonth(currentDate.getMonthValue());
        dto.setDay(currentDate.getDayOfMonth());

        DayOfWeek dayOfWeek = currentDate.getDayOfWeek();
        int value = dayOfWeek.getValue();
        dto.setWeek(value);

        String formattedDate = currentDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        dto.setRecordDate(formattedDate);
    }

    @Override
    public void update(DnfHistoryAttributeDto dto) {
        DnfHistoryAttributeEntity entity = ConvertUtils.sourceToTarget(dto, DnfHistoryAttributeEntity.class);
        baseDao.updateById(entity);
    }

    @Override
    public void delete(Long[] ids) {
        baseDao.deleteBatchIds(Arrays.asList(ids));
    }
}