package io.renren.modules.dnf.dto;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.renren.common.validator.group.AddGroup;
import io.renren.common.validator.group.UpdateGroup;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

@Data
@EqualsAndHashCode(callSuper=false)
@TableName("dnf_history_attribute")
public class DnfHistoryAttributeDto implements Serializable {

    @Schema(title = "id")
    @Null(message="{id.null}", groups = AddGroup.class)
    @NotNull(message="{id.require}", groups = UpdateGroup.class)
    private Long id;

    @Schema(title = "dnf角色id")
    private Long characterId;

    @Schema(title = "dnf角色名")
    private String characterName;

    @Schema(title = "名望")
    private Integer fame;

    @Schema(title = "模拟伤害")
    private Integer simulatedDamage;

    @Schema(title = "日期")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String recordDate;

    @Schema(title = "年份")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Integer year;

    @Schema(title = "月份")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Integer month;

    @Schema(title = "天")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Integer day;

    @Schema(title = "星期几")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Integer week;

    @Schema(title = "创建时间")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Date createDate;


}
