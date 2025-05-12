package io.renren.modules.dnf.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.renren.common.constant.Constant;
import io.renren.common.page.PageData;
import io.renren.common.service.impl.BaseServiceImpl;
import io.renren.common.utils.ConvertUtils;
import io.renren.modules.dnf.dao.DnfDungeonDao;
import io.renren.modules.dnf.dto.DnfDungeonDto;
import io.renren.modules.dnf.dto.DungeonTypeDto;
import io.renren.modules.dnf.entity.DnfDungeonEntity;
import io.renren.modules.dnf.enums.DungeonsTypeEnum;
import io.renren.modules.dnf.service.DnfDungeonService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static io.renren.modules.dnf.enums.DungeonsTypeEnum.getByCode;

@Service
@AllArgsConstructor
public class DnfDungeonServiceImpl extends BaseServiceImpl<DnfDungeonDao, DnfDungeonEntity> implements DnfDungeonService {

    @Override
    public PageData<DnfDungeonDto> page(Map<String, Object> params) {
        IPage<DnfDungeonEntity> page = baseDao.selectPage(
                getPage(params, Constant.CREATE_DATE, false),
                getWrapper(params)
        );

        PageData<DnfDungeonDto> result = getPageData(page, DnfDungeonDto.class);

        result.getList().forEach(this::buildDungeon);

        return result;
    }

    private QueryWrapper<DnfDungeonEntity> getWrapper(Map<String, Object> params) {
        String name = (String) params.get("name");
        QueryWrapper<DnfDungeonEntity> wrapper = new QueryWrapper<>();
        wrapper.like(StrUtil.isNotBlank(name), "name", name);
        wrapper.eq("parent_id", 0);

        return wrapper;
    }

    private void buildDungeon(DnfDungeonDto dto) {
        dto.setTypeName(getByCode(dto.getType()).getName());

        if (dto.isLegion() && dto.isParent()) {
            List<DnfDungeonDto> children = list(Map.of("parent_id", dto.getParentId()));
            dto.setChildren(children);
        }
    }

    @Override
    public List<DnfDungeonDto> list(Map<String, Object> params) {
        List<DnfDungeonEntity> entityList = baseDao.selectList(getWrapper(params));
        return ConvertUtils.sourceToTarget(entityList, DnfDungeonDto.class);
    }

    @Override
    public DnfDungeonDto get(Long id) {
        DnfDungeonEntity entity = baseDao.selectById(id);
        return ConvertUtils.sourceToTarget(entity, DnfDungeonDto.class);
    }

    @Override
    public void save(DnfDungeonDto dto) {
        DnfDungeonEntity entity = ConvertUtils.sourceToTarget(dto, DnfDungeonEntity.class);
        insert(entity);
    }

    @Override
    public void update(DnfDungeonDto dto) {
        DnfDungeonEntity entity = ConvertUtils.sourceToTarget(dto, DnfDungeonEntity.class);
        baseDao.updateById(entity);
    }

    @Override
    public void delete(Long[] ids) {
        baseDao.deleteBatchIds(Arrays.asList(ids));
    }

    @Override
    public List<DungeonTypeDto> typeList() {
        return Arrays.stream(DungeonsTypeEnum.values()).map(DnfDungeonServiceImpl::convertType).toList();
    }

    private static DungeonTypeDto convertType(DungeonsTypeEnum type) {
        DungeonTypeDto dto = new DungeonTypeDto();
        dto.setId(type.getCode());
        dto.setName(type.getName());
        return dto;
    }
}