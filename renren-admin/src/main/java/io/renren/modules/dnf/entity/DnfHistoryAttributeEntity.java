package io.renren.modules.dnf.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.renren.common.entity.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@Data
@EqualsAndHashCode(callSuper=false)
@TableName("dnf_history_attribute")
public class DnfHistoryAttributeEntity extends BaseEntity {

    private Long characterId;
    private String characterName;
    private Integer fame;
    private Integer fameRanking;
    private Integer simulatedDamage;
    private Integer simulatedDamageRanking;
    private String recordDate;
    private Integer year;
    private Integer month;
    private Integer day;
    private Integer week;
    private Integer fill;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Long updater;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateDate;

}
