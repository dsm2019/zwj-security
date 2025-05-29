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
import io.renren.modules.dnf.dto.DnfHistoryAttributeDto;
import io.renren.modules.dnf.dto.TrendDataDto;
import io.renren.modules.dnf.service.DnfHistoryAttributeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * DNF 角色历史属性控制器
 *
 * @author YourName
 */
@RestController
@RequestMapping("/dnf/history-attribute")
@Tag(name = "DNF 角色历史属性管理")
@AllArgsConstructor
public class DnfHistoryAttributeController {
    private final DnfHistoryAttributeService dnfHistoryAttributeService;

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
    public Result<PageData<DnfHistoryAttributeDto>> page(@Parameter(hidden = true) @RequestParam Map<String, Object> params) {
        PageData<DnfHistoryAttributeDto> page = dnfHistoryAttributeService.page(params);

        return new Result<PageData<DnfHistoryAttributeDto>>().ok(page);
    }

    @GetMapping("list")
    @Operation(summary = "列表")
    @RequiresPermissions("dnf:character:list")
    public Result<List<DnfHistoryAttributeDto>> list() {
        List<DnfHistoryAttributeDto> data = dnfHistoryAttributeService.list(new HashMap<>(1));

        return new Result<List<DnfHistoryAttributeDto>>().ok(data);
    }

    @GetMapping("trend")
    @Operation(summary = "属性趋势")
    @RequiresPermissions("dnf:character:list")
    public Result<List<TrendDataDto>> trend(@RequestParam(defaultValue = "fame") String attributeName,
                                            @RequestParam(required = false) String from,
                                            @RequestParam(required = false) String to,
                                            @RequestParam(defaultValue = "true") boolean asc) {
        List<TrendDataDto> data = dnfHistoryAttributeService.getTrendData(attributeName, from, to);
        data.sort(Comparator.comparing(a -> a.getData().get(0).getValue().multiply(BigDecimal.valueOf(asc ? 1 : -1))));
        return new Result<List<TrendDataDto>>().ok(data);
    }

    @GetMapping("recordDates")
    @Operation(summary = "有记录的日期")
    @LogOperation("有记录的日期")
    @RequiresPermissions("dnf:character:delete")
    public Result<List<String>> recordDates() {
        List<String> recordDates = dnfHistoryAttributeService.recordDates();
        return new Result<List<String>>().ok(recordDates);
    }

    @GetMapping("{id}")
    @Operation(summary = "信息")
    @RequiresPermissions("dnf:character:info")
    public Result<DnfHistoryAttributeDto> get(@PathVariable("id") Long id) {
        DnfHistoryAttributeDto data = dnfHistoryAttributeService.get(id);

        return new Result<DnfHistoryAttributeDto>().ok(data);
    }

    @PostMapping
    @Operation(summary = "保存")
    @LogOperation("保存")
    @RequiresPermissions("dnf:character:save")
    public Result save(@RequestBody DnfHistoryAttributeDto dto) {
        //效验数据
        ValidatorUtils.validateEntity(dto, AddGroup.class, DefaultGroup.class);

        dnfHistoryAttributeService.save(dto);

        return new Result();
    }

    @PutMapping
    @Operation(summary = "修改")
    @LogOperation("修改")
    @RequiresPermissions("dnf:character:update")
    public Result update(@RequestBody DnfHistoryAttributeDto dto) {
        //效验数据
        ValidatorUtils.validateEntity(dto, UpdateGroup.class, DefaultGroup.class);

        dnfHistoryAttributeService.update(dto);

        return new Result();
    }

    @DeleteMapping
    @Operation(summary = "删除")
    @LogOperation("删除")
    @RequiresPermissions("dnf:character:delete")
    public Result delete(@RequestBody Long[] ids) {
        //效验数据
        AssertUtils.isArrayEmpty(ids, "id");

        dnfHistoryAttributeService.delete(ids);

        return new Result();
    }

    @PostMapping("refreshRanking")
    @Operation(summary = "刷新排名")
    @LogOperation("刷新排名")
    @RequiresPermissions("dnf:character:delete")
    public Result<?> refreshRanking() {
        dnfHistoryAttributeService.refreshRanking();
        return new Result<>();
    }

    @PostMapping("fillEmptyData")
    @Operation(summary = "填充空白数据")
    @LogOperation("填充空白数据")
    @RequiresPermissions("dnf:character:delete")
    public Result<?> fillEmptyData() {
        dnfHistoryAttributeService.fillEmptyData();
        return new Result<>();
    }
}