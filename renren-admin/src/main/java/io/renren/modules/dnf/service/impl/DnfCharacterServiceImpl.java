package io.renren.modules.dnf.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.renren.common.constant.Constant;
import io.renren.common.page.PageData;
import io.renren.common.service.impl.BaseServiceImpl;
import io.renren.common.utils.ConvertUtils;
import io.renren.modules.dnf.dao.DnfCharacterDao;
import io.renren.modules.dnf.dto.DnfCharacterDto;
import io.renren.modules.dnf.entity.DnfCharacterEntity;
import io.renren.modules.dnf.service.DnfCharacterService;
import io.renren.modules.sys.dto.SysUserDTO;
import io.renren.modules.sys.service.SysUserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class DnfCharacterServiceImpl extends BaseServiceImpl<DnfCharacterDao, DnfCharacterEntity> implements DnfCharacterService {

    private final SysUserService sysUserService;

    @Override
    public PageData<DnfCharacterDto> page(Map<String, Object> params) {
        if ("".equals(params.get(Constant.ORDER_FIELD)))  {
            params.put(Constant.ORDER_FIELD, "fame");
            params.put(Constant.ORDER, Constant.DESC);
        }

        IPage<DnfCharacterEntity> page = baseDao.selectPage(
                getPage(params, Constant.CREATE_DATE, false),
                getWrapper(params)
        );

        List<Long> ids = page.getRecords().stream().map(DnfCharacterEntity::getUserId).toList();
        List<SysUserDTO> userDTOS = sysUserService.list(Map.of("userIds", ids));
        Map<Long, String> userMap = userDTOS.stream().collect(Collectors.toMap(SysUserDTO::getId, SysUserDTO::getUsername));
        page.getRecords().forEach(item -> {
            item.setUserName(userMap.get(item.getUserId()));
        });

        return getPageData(page, DnfCharacterDto.class);
    }

    private QueryWrapper<DnfCharacterEntity> getWrapper(Map<String, Object> params) {
        String name = (String) params.get("name");

        QueryWrapper<DnfCharacterEntity> wrapper = new QueryWrapper<>();
        wrapper.like(StrUtil.isNotBlank(name), "name", name);
        wrapper.orderByDesc("fame");

        //普通管理员，只能查询所属部门及子部门的数据
//        UserDetail user = SecurityUser.getUser();
//        if (user.getSuperAdmin() == SuperAdminEnum.NO.value()) {
//            List<Long> deptIdList = sysDeptService.getSubDeptIdList(user.getDeptId());
//            wrapper.in(deptIdList != null, "dept_id", deptIdList);
//        }

        return wrapper;
    }

    @Override
    public List<DnfCharacterDto> list(Map<String, Object> params) {
        List<DnfCharacterEntity> entityList = baseDao.selectList(getWrapper(params));

        List<Long> ids = entityList.stream().map(DnfCharacterEntity::getUserId).toList();
        List<SysUserDTO> userDTOS = sysUserService.list(Map.of("userIds", ids));
        Map<Long, String> userMap = userDTOS.stream().collect(Collectors.toMap(SysUserDTO::getId, SysUserDTO::getUsername));
        entityList.forEach(item -> {
            item.setUserName(userMap.get(item.getUserId()));
        });

        return ConvertUtils.sourceToTarget(entityList, DnfCharacterDto.class);
    }

    @Override
    public DnfCharacterDto get(Long id) {
        DnfCharacterEntity entity = baseDao.selectById(id);
        SysUserDTO sysUserDTO = sysUserService.get(entity.getUserId());
        entity.setUserName(sysUserDTO.getUsername());

        return ConvertUtils.sourceToTarget(entity, DnfCharacterDto.class);
    }

    @Override
    public void save(DnfCharacterDto dto) {
        DnfCharacterEntity entity = ConvertUtils.sourceToTarget(dto, DnfCharacterEntity.class);
        insert(entity);
    }

    @Override
    public void update(DnfCharacterDto dto) {
        DnfCharacterEntity entity = ConvertUtils.sourceToTarget(dto, DnfCharacterEntity.class);
        baseDao.updateById(entity);
    }

    @Override
    public void delete(Long[] ids) {
        baseDao.deleteBatchIds(Arrays.asList(ids));
    }
}