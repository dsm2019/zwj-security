package io.renren.modules.dnf.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.renren.common.constant.Constant;
import io.renren.common.page.PageData;
import io.renren.common.service.impl.BaseServiceImpl;
import io.renren.common.utils.ConvertUtils;
import io.renren.modules.dnf.dao.DnfHistoryAttributeDao;
import io.renren.modules.dnf.dto.DnfCharacterDto;
import io.renren.modules.dnf.dto.DnfHistoryAttributeDto;
import io.renren.modules.dnf.dto.TrendDataDto;
import io.renren.modules.dnf.entity.DnfCharacterEntity;
import io.renren.modules.dnf.entity.DnfHistoryAttributeEntity;
import io.renren.modules.dnf.enums.DnfCareerEnum;
import io.renren.modules.dnf.service.DnfCharacterService;
import io.renren.modules.dnf.service.DnfHistoryAttributeService;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

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

        String recordDate = (String) params.get("recordDate");
        wrapper.eq(StrUtil.isNotBlank(recordDate), "record_date", recordDate);

        long characterId = Long.parseLong(Optional.ofNullable((String) params.get("characterId")).orElse("-1"));
        wrapper.eq(characterId != -1, "character_id", characterId);

        return wrapper;
    }

    @Override
    public List<DnfHistoryAttributeDto> list(Map<String, Object> params) {
        List<DnfHistoryAttributeEntity> entityList = baseDao.selectList(getWrapper(params));
        return ConvertUtils.sourceToTarget(entityList, DnfHistoryAttributeDto.class);
    }

    @Override
    public List<TrendDataDto> getTrendData(String attributeName, String from, String to) {
        LambdaQueryWrapper<DnfHistoryAttributeEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.between(StringUtils.isNoneBlank(from), DnfHistoryAttributeEntity::getRecordDate, from, to);
        List<DnfHistoryAttributeEntity> attributeEntities = baseDao.selectList(wrapper);
        Set<Long> characterIds = attributeEntities.stream().map(DnfHistoryAttributeEntity::getCharacterId).collect(Collectors.toSet());
        List<DnfCharacterEntity> characterEntities = dnfCharacterService.listByIds(characterIds);
        Map<Long, DnfCharacterEntity> characterMap = characterEntities.stream().collect(Collectors.toMap(DnfCharacterEntity::getId, Function.identity()));

        if ("fame".equals(attributeName)) {
            return getTrendData(attributeEntities, characterMap, DnfHistoryAttributeEntity::getFame);
        } else if ("fameRanking".equals(attributeName)) {
            return getTrendData(attributeEntities, characterMap, DnfHistoryAttributeEntity::getFameRanking);
        } else if ("simulatedDamage".equals(attributeName)) {
            return getTrendData(attributeEntities, characterMap, DnfHistoryAttributeEntity::getSimulatedDamage);
        }else if ("simulatedDamageRanking".equals(attributeName)) {
            return getTrendData(attributeEntities, characterMap, DnfHistoryAttributeEntity::getSimulatedDamageRanking);
        }
        return List.of();
    }

    private static List<TrendDataDto> getTrendData(List<DnfHistoryAttributeEntity> attributeEntities, Map<Long, DnfCharacterEntity> characterMap, Function<DnfHistoryAttributeEntity, ?> function) {
        List<String> dateList = attributeEntities.stream().map(DnfHistoryAttributeEntity::getRecordDate).distinct().sorted().toList();
        Map<Long, List<DnfHistoryAttributeEntity>> map = attributeEntities.stream().collect(Collectors.groupingBy(DnfHistoryAttributeEntity::getCharacterId));
        return map.entrySet().stream().map(entry -> {
            DnfCharacterEntity character = characterMap.get(entry.getKey());
            return TrendDataDto.builder()
                    .characterId(entry.getKey())
                    .characterAvatar(character.getAvatar())
                    .name(String.format("%s(%s)", character.getName(), DnfCareerEnum.careerEnumMap.get(character.getCareer()).getName()))
                    .data(getPerTrendData(entry.getValue(), function, dateList))
                    .build();
        }).collect(Collectors.toList());
    }

    private static List<TrendDataDto.PerTrendDataDto> getPerTrendData(List<DnfHistoryAttributeEntity> attributeEntities, Function<DnfHistoryAttributeEntity, ?> function, List<String> dateList) {
        Map<String, DnfHistoryAttributeEntity> attributeMap = attributeEntities.stream().collect(Collectors.toMap(DnfHistoryAttributeEntity::getRecordDate, Function.identity()));
        List<TrendDataDto.PerTrendDataDto> result = new ArrayList<>();
        DnfHistoryAttributeEntity attribute = null;
        for (String date : dateList) {
            attribute = attributeMap.get(date) == null ? attribute : attributeMap.get(date);
            TrendDataDto.PerTrendDataDto dto = TrendDataDto.PerTrendDataDto.builder()
                    .date(date)
                    .value(Objects.isNull(attribute) ? new BigDecimal(0) : BigDecimal.valueOf((Integer) function.apply(attribute)))
                    .build();
            result.add(dto);
        }
        return result;
//        return dateList.stream()
//                .map(date -> {
//                    DnfHistoryAttributeEntity attribute = attributeMap.get(date);
//                    return TrendDataDto.PerTrendDataDto.builder()
//                            .date(date)
//                            .value(Objects.isNull(attribute) ? new BigDecimal(0) : BigDecimal.valueOf((Integer) function.apply(attribute)))
//                            .build();
//                }).collect(Collectors.toList());
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

        DnfCharacterDto dnfCharacterDto = dnfCharacterService.get(dto.getCharacterId());

        if (Objects.nonNull(oldEntity)) {
            dto.setId(oldEntity.getId());
            DnfHistoryAttributeEntity entity = ConvertUtils.sourceToTarget(dto, DnfHistoryAttributeEntity.class);
            updateById(entity);
        } else {
            dto.setCharacterName(dnfCharacterDto.getName());
            DnfHistoryAttributeEntity entity = ConvertUtils.sourceToTarget(dto, DnfHistoryAttributeEntity.class);
            insert(entity);
        }

        dnfCharacterDto.setFame(dto.getFame());
        dnfCharacterDto.setSimulatedDamage(dto.getSimulatedDamage());
        dnfCharacterService.update(dnfCharacterDto);
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

    @Override
    public void refreshRanking() {
        List<DnfHistoryAttributeEntity> attributeEntities = baseDao.selectList(new QueryWrapper<>());
        Map<String, List<DnfHistoryAttributeEntity>> collect = attributeEntities.stream().collect(Collectors.groupingBy(DnfHistoryAttributeEntity::getRecordDate));
        for (Map.Entry<String, List<DnfHistoryAttributeEntity>> entry : collect.entrySet()) {
            List<DnfHistoryAttributeEntity> list = entry.getValue();
            list.sort(Comparator.comparing(DnfHistoryAttributeEntity::getFame));
            Collections.reverse(list);
            for (int i = 0; i < list.size(); i++) {
                list.get(i).setFameRanking(i + 1);
            }
            list.sort(Comparator.comparing(DnfHistoryAttributeEntity::getSimulatedDamage));
            Collections.reverse(list);
            for (int i = 0; i < list.size(); i++) {
                list.get(i).setSimulatedDamageRanking(i + 1);
            }

            for (DnfHistoryAttributeEntity dnfHistoryAttributeEntity : list) {
                baseDao.updateById(dnfHistoryAttributeEntity);
            }
        }
    }

    @Override
    public List<String> recordDates() {
        return baseDao.recordDates();
    }
}