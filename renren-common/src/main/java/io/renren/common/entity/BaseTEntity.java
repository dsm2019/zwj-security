package io.renren.common.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.util.Date;

@Data
public abstract class BaseTEntity extends BaseEntity {

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Long updater;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateDate;

}


//private Long id;
//private Long  creator;
//private Date createDate;
//private Long updater;
//private Date updateDate;
