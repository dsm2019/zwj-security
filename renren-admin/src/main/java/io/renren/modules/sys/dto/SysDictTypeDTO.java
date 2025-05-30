/**
 * Copyright (c) 2018 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package io.renren.modules.sys.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.renren.common.validator.group.AddGroup;
import io.renren.common.validator.group.DefaultGroup;
import io.renren.common.validator.group.UpdateGroup;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 字典类型
 *
 * @author Mark sunlightcs@gmail.com
 */
@Data
@Schema(title = "字典类型")
public class SysDictTypeDTO implements Serializable {
    private static final long serialVersionUID = 1L;

	@Schema(title = "id")
	@Null(message="{id.null}", groups = AddGroup.class)
	@NotNull(message="{id.require}", groups = UpdateGroup.class)
	private Long id;

	@Schema(title = "字典类型")
	@NotBlank(message="{sysdict.type.require}", groups = DefaultGroup.class)
	private String dictType;

	@Schema(title = "字典名称")
	@NotBlank(message="{sysdict.name.require}", groups = DefaultGroup.class)
	private String dictName;

	@Schema(title = "备注")
	private String remark;

	@Schema(title = "排序")
	@Min(value = 0, message = "{sort.number}", groups = DefaultGroup.class)
	private Integer sort;

	@Schema(title = "创建时间")
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	private Date createDate;

	@Schema(title = "更新时间")
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	private Date updateDate;
}
