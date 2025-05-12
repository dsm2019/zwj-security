package io.renren.modules.dnf.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.renren.common.validator.group.AddGroup;
import io.renren.common.validator.group.UpdateGroup;
import io.renren.modules.dnf.enums.DungeonsTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Schema(title = "dnf 地下城")
public class DnfDungeonDto implements Serializable {
    @Schema(title = "id")
    @Null(message="{id.null}", groups = AddGroup.class)
    @NotNull(message="{id.require}", groups = UpdateGroup.class)
    private Long id;

    @Schema(title = "名称")
    private String name;

    @Schema(title = "boss")
    private String boss;

    @Schema(title = "类型")
    private Integer type;

    @Schema(title = "类型名")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String typeName;

    @Schema(title = "军团父id")
    private Integer parentId;

    @Schema(title = "创建时间")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Date createDate;

    @Schema(title = "军团的子图")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    public List<DnfDungeonDto> children = new ArrayList<>();

    public boolean isParent() {
        return 0 == parentId;
    }

    public boolean isLegion() {
        return DungeonsTypeEnum.LEGION.getCode() == type;
    }
}