package io.renren.modules.dnf.controller;

import io.renren.common.annotation.LogOperation;
import io.renren.common.constant.Constant;
import io.renren.common.page.PageData;
import io.renren.common.utils.ExcelUtils;
import io.renren.common.utils.Result;
import io.renren.common.validator.AssertUtils;
import io.renren.common.validator.ValidatorUtils;
import io.renren.common.validator.group.AddGroup;
import io.renren.common.validator.group.DefaultGroup;
import io.renren.common.validator.group.UpdateGroup;
import io.renren.modules.dnf.dto.DnfCharacterInfoDTO;
import io.renren.modules.dnf.excel.DnfCharacterInfoExcel;
import io.renren.modules.dnf.service.DnfCharacterInfoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;


/**
 * 角色信息表
 *
 * @author Mark sunlightcs@gmail.com
 * @since 1.0.0 2025-05-22
 */
@RestController
@RequestMapping("dnf/character/info ")
@Tag(name="角色信息表")
public class DnfCharacterInfoController {
    @Autowired
    private DnfCharacterInfoService dnfCharacterInfoService;

    @GetMapping("page")
    @Operation(summary = "分页")
    @Parameters({
        @Parameter(name = Constant.PAGE, description = "当前页码，从1开始", in = ParameterIn.QUERY, required = true, ref="int") ,
        @Parameter(name = Constant.LIMIT, description = "每页显示记录数", in = ParameterIn.QUERY,required = true, ref="int") ,
        @Parameter(name = Constant.ORDER_FIELD, description = "排序字段", in = ParameterIn.QUERY, ref="String") ,
        @Parameter(name = Constant.ORDER, description = "排序方式，可选值(asc、desc)", in = ParameterIn.QUERY, ref="String")
    })
    public Result<PageData<DnfCharacterInfoDTO>> page(@Parameter(hidden = true) @RequestParam Map<String, Object> params){
        PageData<DnfCharacterInfoDTO> page = dnfCharacterInfoService.page(params);

        return new Result<PageData<DnfCharacterInfoDTO>>().ok(page);
    }

    @GetMapping("{id}")
    @Operation(summary = "信息")
    public Result<DnfCharacterInfoDTO> get(@PathVariable("id") Long id){
        DnfCharacterInfoDTO data = dnfCharacterInfoService.get(id);

        return new Result<DnfCharacterInfoDTO>().ok(data);
    }

    @PostMapping
    @Operation(summary = "保存")
    @LogOperation("保存")
    public Result save(@RequestBody DnfCharacterInfoDTO dto){
        //效验数据
        ValidatorUtils.validateEntity(dto, AddGroup.class, DefaultGroup.class);

        dnfCharacterInfoService.save(dto);

        return new Result();
    }

    @PutMapping
    @Operation(summary = "修改")
    @LogOperation("修改")
    public Result update(@RequestBody DnfCharacterInfoDTO dto){
        //效验数据
        ValidatorUtils.validateEntity(dto, UpdateGroup.class, DefaultGroup.class);

        dnfCharacterInfoService.update(dto);

        return new Result();
    }

    @DeleteMapping
    @Operation(summary = "删除")
    @LogOperation("删除")
    @RequiresPermissions("demo:dnfcharacterinfo:delete")
    public Result delete(@RequestBody Long[] ids){
        //效验数据
        AssertUtils.isArrayEmpty(ids, "id");

        dnfCharacterInfoService.delete(ids);

        return new Result();
    }

    @GetMapping("export")
    @Operation(summary = "导出")
    @LogOperation("导出")
    public void export(@Parameter(hidden = true) @RequestParam Map<String, Object> params, HttpServletResponse response) throws Exception {
        List<DnfCharacterInfoDTO> list = dnfCharacterInfoService.list(params);

        ExcelUtils.exportExcelToTarget(response, null, "角色信息表", list, DnfCharacterInfoExcel.class);
    }

}
