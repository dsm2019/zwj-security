package io.renren.modules.dnf.controller;

import io.renren.common.annotation.LogOperation;
import io.renren.common.constant.Constant;
import io.renren.common.page.PageData;
import io.renren.common.utils.Result;
import io.renren.common.validator.AssertUtils;
import io.renren.common.validator.ValidatorUtils;
import io.renren.common.validator.group.AddGroup;
import io.renren.common.validator.group.DefaultGroup;
import io.renren.common.validator.group.UpdateGroup;
import io.renren.modules.dnf.dto.CareerDto;
import io.renren.modules.dnf.dto.DnfCharacterDto;
import io.renren.modules.dnf.service.DnfCharacterService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * DNF 角色控制器
 * @author YourName
 */
@RestController
@RequestMapping("/dnf/character")
@Tag(name = "DNF 角色管理")
@AllArgsConstructor
public class DnfCharacterController {
    private final DnfCharacterService dnfCharacterService;

    @GetMapping("page")
    @Operation(summary = "分页")
    @Parameters({
            @Parameter(name = Constant.PAGE, description = "当前页码，从1开始", in = ParameterIn.QUERY, required = true, ref = "int"),
            @Parameter(name = Constant.LIMIT, description = "每页显示记录数", in = ParameterIn.QUERY, required = true, ref = "int"),
            @Parameter(name = Constant.ORDER_FIELD, description = "排序字段", in = ParameterIn.QUERY, ref = "String"),
            @Parameter(name = Constant.ORDER, description = "排序方式，可选值(asc、desc)", in = ParameterIn.QUERY, ref = "String"),
            @Parameter(name = "dnf name", description = "dnf 角色名", in = ParameterIn.QUERY, ref = "String")
    })
    @RequiresPermissions("dnf:character:page")
    public Result<PageData<DnfCharacterDto>> page(@Parameter(hidden = true) @RequestParam Map<String, Object> params) {
        PageData<DnfCharacterDto> page = dnfCharacterService.page(params);
        return new Result<PageData<DnfCharacterDto>>().ok(page);
    }

    @GetMapping("list")
    @Operation(summary = "列表")
    @RequiresPermissions("dnf:character:list")
    public Result<List<DnfCharacterDto>> list() {
        List<DnfCharacterDto> data = dnfCharacterService.list(new HashMap<>(1));

        return new Result<List<DnfCharacterDto>>().ok(data);
    }

    @GetMapping("{id}")
    @Operation(summary = "信息")
    @RequiresPermissions("dnf:character:info")
    public Result<DnfCharacterDto> get(@PathVariable("id") Long id) {
        DnfCharacterDto data = dnfCharacterService.get(id);

        return new Result<DnfCharacterDto>().ok(data);
    }

    @PostMapping
    @Operation(summary = "保存")
    @LogOperation("保存")
    @RequiresPermissions("dnf:character:save")
    public Result save(@RequestBody DnfCharacterDto dto) {
        //效验数据
        ValidatorUtils.validateEntity(dto, AddGroup.class, DefaultGroup.class);

        dnfCharacterService.save(dto);

        return new Result();
    }

    @PutMapping
    @Operation(summary = "修改")
    @LogOperation("修改")
    @RequiresPermissions("dnf:character:update")
    public Result update(@RequestBody DnfCharacterDto dto) {
        //效验数据
        ValidatorUtils.validateEntity(dto, UpdateGroup.class, DefaultGroup.class);

        dnfCharacterService.update(dto);

        return new Result();
    }

    @DeleteMapping
    @Operation(summary = "删除")
    @LogOperation("删除")
    @RequiresPermissions("dnf:character:delete")
    public Result delete(@RequestBody Long[] ids) {
        //效验数据
        AssertUtils.isArrayEmpty(ids, "id");

        dnfCharacterService.delete(ids);

        return new Result();
    }

    @PostMapping("refreshSort")
    @Operation(summary = "刷新fame排行")
    @RequiresPermissions("dnf:character:update")
    public Result<PageData<DnfCharacterDto>> refreshSort(@Parameter(hidden = true) @RequestParam Map<String, Object> params) {
        dnfCharacterService.refresh();
        return new Result();
    }

    @PostMapping("{id}/avatar")
    @Operation(summary = "上传头像")
    @LogOperation("上传头像")
    @RequiresPermissions("dnf:character:update")
    public Result uploadAvatar(@RequestParam("file") MultipartFile file,
                               @PathVariable Long id) {
        String upload = dnfCharacterService.updateAvatar(id, file);
        return new Result().ok(Map.of("fileUrl", upload));
    }

    @GetMapping("career/list")
    @Operation(summary = "获取职业列表")
    @RequiresPermissions("dnf:character:list")
    public Result<List<CareerDto>> getCareerList() {
        List<CareerDto> list = dnfCharacterService.getCareerList();
        return new Result<List<CareerDto>>().ok(list);
    }
}