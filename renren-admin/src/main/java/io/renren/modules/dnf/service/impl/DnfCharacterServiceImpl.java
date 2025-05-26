package io.renren.modules.dnf.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.renren.common.constant.Constant;
import io.renren.common.page.PageData;
import io.renren.common.service.impl.BaseServiceImpl;
import io.renren.common.utils.ConvertUtils;
import io.renren.modules.dnf.dao.DnfCharacterDao;
import io.renren.modules.dnf.dto.CareerDto;
import io.renren.modules.dnf.dto.DnfCharacterDto;
import io.renren.modules.dnf.entity.DnfCharacterEntity;
import io.renren.modules.dnf.enums.DnfCareerEnum;
import io.renren.modules.dnf.service.DnfCharacterService;
import io.renren.modules.sys.dto.SysUserDTO;
import io.renren.modules.sys.service.SysUserService;
import io.renren.modules.upload.UploadService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class DnfCharacterServiceImpl extends BaseServiceImpl<DnfCharacterDao, DnfCharacterEntity> implements DnfCharacterService {

    private final SysUserService sysUserService;

    private final UploadService uploadService;

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

    @Override
    public void refresh() {
        List<DnfCharacterDto> list = list(new HashMap<>());
        for (int i = 0; i < list.size(); i++) {
            DnfCharacterDto dto = list.get(i);
            dto.setSort(i + 1);
            update(dto);
        }
    }

    @Override
    public String updateAvatar(Long id, MultipartFile file) {
        DnfCharacterEntity entity = baseDao.selectById(id);
        if (entity == null) {
            return "角色不存在";
        }
        if (file == null || file.isEmpty()) {
            return "文件不能为空";
        }
        if (file.getSize() > 1024 * 1024 * 2) {
            return "文件大小不能超过2MB";
        }
        if (!Objects.requireNonNull(file.getContentType()).startsWith("image/")) {
            return "文件类型不正确";
        }
        String fileName = uploadService.upload(file, DnfCareerEnum.careerEnumMap.get(entity.getCareer()).getName());
        if (fileName == null) {
            return "上传失败";
        }
        entity.setAvatar(fileName);
        baseDao.updateById(entity);
        return fileName;
    }

    @Override
    public List<CareerDto> getCareerList() {
        // Convert all enum values to CareerDto
        List<CareerDto> allCareers = Arrays.stream(DnfCareerEnum.values())
                .filter(entity -> !DnfCareerEnum.UNKNOWN.equals(entity))
                .map(careerEnum -> {
                    CareerDto dto = new CareerDto();
                    dto.setCareer(careerEnum.getCareer());
                    dto.setName(careerEnum.getName());
                    dto.setId(careerEnum.getId());
                    dto.setParent(careerEnum.getParent());
                    dto.setAvatar(careerEnum.getAvatar());
                    return dto;
                })
                .toList();

        // Map to group careers by their parent ID
        Map<Integer, List<CareerDto>> groupedByParent = allCareers.stream()
                .collect(Collectors.groupingBy(CareerDto::getParent));

        // Build the hierarchical structure
        List<CareerDto> rootCareers = groupedByParent.getOrDefault(0, List.of());
        rootCareers.forEach(root -> buildHierarchy(root, groupedByParent));

        return rootCareers;
    }

    private void buildHierarchy(CareerDto parent, Map<Integer, List<CareerDto>> groupedByParent) {
        List<CareerDto> children = groupedByParent.getOrDefault(parent.getId(), List.of());
        parent.setChildren(children);
        children.forEach(child -> buildHierarchy(child, groupedByParent));
    }
}