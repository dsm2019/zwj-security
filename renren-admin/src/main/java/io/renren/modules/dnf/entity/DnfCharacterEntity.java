package io.renren.modules.dnf.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.renren.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@Data
@EqualsAndHashCode(callSuper=false)
@TableName("dnf_character")
public class DnfCharacterEntity extends BaseEntity {
    private String name;
    private String profession;
    private Integer fame;
    private Integer simulatedDamage;
    private Integer level;
    private Integer attackSpeed;
    private Integer movementSpeed;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Long updater;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateDate;

    private Integer sort;

    private Long userId;

    @TableField(exist = false)
    private String userName;
}