package io.renren.modules.dnf.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@Schema(title = "趋势数据")
public class TrendDataDto {

    @Schema(title = "用户id)")
    private Long characterId;
    @Schema(title = "用户名(职业)")
    private String name;
    @Schema(title = "用户头像")
    private String characterAvatar;

    @Schema(title = "用户的记录")
    private List<PerTrendDataDto> data;

    @Data
    @Builder
    public static class PerTrendDataDto {
        @Schema(title = "日期")
        private String date;
        @Schema(title = "值")
        private BigDecimal value;
    }

}
