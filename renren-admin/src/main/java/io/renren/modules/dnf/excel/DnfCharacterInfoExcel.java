package io.renren.modules.dnf.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.ContentRowHeight;
import com.alibaba.excel.annotation.write.style.HeadRowHeight;
import lombok.Data;

import java.util.Date;

/**
 * 角色信息表
 *
 * @author Mark sunlightcs@gmail.com
 * @since 1.0.0 2025-05-22
 */
@Data
public class DnfCharacterInfoExcel {
    @ExcelProperty(value = "主键 ID")
    private Long id;
    @ExcelProperty(value = "冒险团名称")
    private String guildName;
    @ExcelProperty(value = "角色名称")
    private String name;
    @ExcelProperty(value = "职业")
    private String job;
    @ExcelProperty(value = "冒险家名望")
    private Integer fame;
    @ExcelProperty(value = "生命")
    private Integer hp;
    @ExcelProperty(value = "魔法")
    private Integer mp;
    @ExcelProperty(value = "物理防御率")
    private Double physicalDefenseRate;
    @ExcelProperty(value = "魔法防御率")
    private Double magicDefenseRate;
    @ExcelProperty(value = "力量")
    private Integer strength;
    @ExcelProperty(value = "智力")
    private Integer intelligence;
    @ExcelProperty(value = "体力")
    private Integer stamina;
    @ExcelProperty(value = "精神")
    private Integer spirit;
    @ExcelProperty(value = "物理攻击")
    private Integer physicalAttack;
    @ExcelProperty(value = "魔法攻击")
    private Integer magicAttack;
    @ExcelProperty(value = "独立攻击")
    private Integer independentAttack;
    @ExcelProperty(value = "物理暴击率")
    private Double physicalCritRate;
    @ExcelProperty(value = "魔法暴击率")
    private Double magicCritRate;
    @ExcelProperty(value = "攻击速度")
    private Double attackSpeed;
    @ExcelProperty(value = "施放速度")
    private Double castSpeed;
    @ExcelProperty(value = "移动速度")
    private Double moveSpeed;
    @ExcelProperty(value = "火属性强化")
    private Integer fire;
    @ExcelProperty(value = "冰属性强化")
    private Integer ice;
    @ExcelProperty(value = "光属性强化")
    private Integer light;
    @ExcelProperty(value = "暗属性强化")
    private Integer dark;
    @ExcelProperty(value = "创建者")
    private Long creator;
    @ExcelProperty(value = "创建时间")
    private Date createDate;
    @ExcelProperty(value = "更新者")
    private Long updater;
    @ExcelProperty(value = "更新时间")
    private Date updateDate;

}