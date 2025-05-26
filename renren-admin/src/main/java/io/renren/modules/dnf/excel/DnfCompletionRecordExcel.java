package io.renren.modules.dnf.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

import java.util.Date;

/**
 * 地下城表
 *
 * @author Mark sunlightcs@gmail.com
 * @since 1.0.0 2025-05-23
 */
@Data
public class DnfCompletionRecordExcel {
    @ExcelProperty(value = "ID")
    private Long id;
    @ExcelProperty(value = "地下城ID")
    private Long dungeonId;
    @ExcelProperty(value = "角色ID")
    private Long characterId;
    @ExcelProperty(value = "ID")
    private String completionPeriod;
    @ExcelProperty(value = "完成的时间(毫秒)")
    private Integer completiontime;
    @ExcelProperty(value = "创建者")
    private Long creator;
    @ExcelProperty(value = "创建时间")
    private Date createDate;
    @ExcelProperty(value = "更新者")
    private Long updater;
    @ExcelProperty(value = "更新时间")
    private Date updateDate;

}