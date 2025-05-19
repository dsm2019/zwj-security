package io.renren.modules.dnf.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
@Schema(title = "职业信息")
public class CareerDto {

    @Schema(title = "职业标识", description = "职业的唯一标识符")
    private String career;

    @Schema(title = "职业名称", description = "职业的中文名称")
    private String name;

    @Schema(title = "职业ID", description = "职业的唯一ID")
    private int id;

    @Schema(title = "父职业ID", description = "父职业的ID")
    private int parent;

    @Schema(title = "职业头像", description = "职业的头像路径")
    private String avatar;

    @Schema(title = "子职业", description = "子职业列表")
    private List<CareerDto> children;

}