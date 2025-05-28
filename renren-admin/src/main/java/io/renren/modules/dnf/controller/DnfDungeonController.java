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
import io.renren.modules.dnf.dto.DnfDungeonDto;
import io.renren.modules.dnf.dto.DungeonPeriodDto;
import io.renren.modules.dnf.dto.DungeonTypeDto;
import io.renren.modules.dnf.service.DnfDungeonService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * DNF 地下城控制器
 * @author YourName
 */
@RestController
@RequestMapping("/dnf/dungeon")
@Tag(name = "DNF 地下城管理")
@AllArgsConstructor
public class DnfDungeonController {
    private final DnfDungeonService dnfDungeonService;

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
    public Result<PageData<DnfDungeonDto>> page(@Parameter(hidden = true) @RequestParam Map<String, Object> params) {
        PageData<DnfDungeonDto> page = dnfDungeonService.page(params);

        return new Result<PageData<DnfDungeonDto>>().ok(page);
    }

    @GetMapping("list")
    @Operation(summary = "列表")
    @RequiresPermissions("dnf:character:list")
    public Result<List<DnfDungeonDto>> list() {
        List<DnfDungeonDto> data = dnfDungeonService.list(new HashMap<>(1));
        return new Result<List<DnfDungeonDto>>().ok(data);
    }

    @GetMapping("{id}")
    @Operation(summary = "信息")
    @RequiresPermissions("dnf:character:info")
    public Result<DnfDungeonDto> get(@PathVariable("id") Long id) {
        DnfDungeonDto data = dnfDungeonService.get(id);

        return new Result<DnfDungeonDto>().ok(data);
    }

    @PostMapping
    @Operation(summary = "保存")
    @LogOperation("保存")
    @RequiresPermissions("dnf:character:save")
    public Result save(@RequestBody DnfDungeonDto dto) {
        //效验数据
        ValidatorUtils.validateEntity(dto, AddGroup.class, DefaultGroup.class);

        dnfDungeonService.save(dto);

        return new Result();
    }

    @PutMapping
    @Operation(summary = "修改")
    @LogOperation("修改")
    @RequiresPermissions("dnf:character:update")
    public Result update(@RequestBody DnfDungeonDto dto) {
        //效验数据
        ValidatorUtils.validateEntity(dto, UpdateGroup.class, DefaultGroup.class);

        dnfDungeonService.update(dto);

        return new Result();
    }

    @DeleteMapping
    @Operation(summary = "删除")
    @LogOperation("删除")
    @RequiresPermissions("dnf:character:delete")
    public Result delete(@RequestBody Long[] ids) {
        //效验数据
        AssertUtils.isArrayEmpty(ids, "id");

        dnfDungeonService.delete(ids);

        return new Result();
    }

    @GetMapping("typeList")
    @Operation(summary = "类型列表")
    @RequiresPermissions("dnf:character:list")
    public Result<List<DungeonTypeDto>> typeList() {
        List<DungeonTypeDto> data = dnfDungeonService.typeList();
        return new Result<List<DungeonTypeDto>>().ok(data);
    }

    @GetMapping("periodList")
    @Operation(summary = "周期列表")
    @RequiresPermissions("dnf:character:list")
    public Result<List<DungeonPeriodDto>> periodList() {
        List<DungeonPeriodDto> data = dnfDungeonService.periodList();
        return new Result<List<DungeonPeriodDto>>().ok(data);
    }
}