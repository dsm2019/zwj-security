package io.renren.modules.dnf.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(title = "地下城周期")
public class DungeonPeriodDto {
    @Schema(title = "周期")
    private Integer period;

    @Schema(title = "描述")
    private String description;
}
