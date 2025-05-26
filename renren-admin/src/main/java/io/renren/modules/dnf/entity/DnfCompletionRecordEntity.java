package io.renren.modules.dnf.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.renren.common.entity.BaseTEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@Data
@EqualsAndHashCode(callSuper=false)
@TableName("dnf_completion_record")
public class DnfCompletionRecordEntity extends BaseTEntity {

    /**
     * 地下城ID
     */
    private Long dungeonId;
    /**
     * 角色ID
     */
    private Long characterId;
    /**
     * ID
     */
    private String completionPeriod;
    /**
     * 完成的时间(毫秒*10)
     */
    private Integer completionTime;

}
