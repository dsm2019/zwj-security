/**
 * Copyright (c) 2018 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package io.renren.modules.dnf.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.renren.common.validator.group.AddGroup;
import io.renren.common.validator.group.DefaultGroup;
import io.renren.common.validator.group.UpdateGroup;
import io.renren.modules.dnf.enums.DnfCareerEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.Optional;

/**
 * 角色管理
 *
 * @author Mark sunlightcs@gmail.com
 * @since 1.0.0
 */
@Data
@Schema(title = "dnf 角色管理")
public class DnfCharacterDto implements Serializable {
    private static final long serialVersionUID = 1L;

	@Schema(title = "id")
	@Null(message="{id.null}", groups = AddGroup.class)
	@NotNull(message="{id.require}", groups = UpdateGroup.class)
	private Long id;

	@Schema(title = "dnf 角色名称")
	@NotBlank(message="{sysrole.name.require}", groups = DefaultGroup.class)
	private String name;

	@Schema(title = "职业")
	private String profession;

	@Schema(title = "职业id")
	private Integer career;

	@Schema(title = "职业名称")
	private String careerName;

	public String getCareerName() {
		return Optional.of(DnfCareerEnum.careerEnumMap.get(career)).orElse(DnfCareerEnum.UNKNOWN).getName();
	}

	@Schema(title = "名望")
	private Integer fame;

	@Schema(title = "模拟伤害")
	private Integer simulatedDamage;

	@Schema(title = "角色等级")
	private Integer level;

	@Schema(title = "攻击速度")
	private Integer attackSpeed;

	@Schema(title = "移动速度")
	private Integer movementSpeed;

	@Schema(title = "sort")
	private Integer sort;

	@Schema(title = "用户id")
	private Long userId;

	@Schema(title = "创建时间")
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	private Date createDate;

	@Schema(title = "用户名称")
	private String userName;

	@Schema(title = "头像")
	private String avatar;
}
