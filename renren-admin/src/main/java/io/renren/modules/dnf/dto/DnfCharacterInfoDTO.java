package io.renren.modules.dnf.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.SchemaProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;


/**
 * 角色信息表
 *
 * @author Mark sunlightcs@gmail.com
 * @since 1.0.0 2025-05-22
 */
@Data
@Schema(name = "角色信息表")
public class DnfCharacterInfoDTO implements Serializable {
    private static final long serialVersionUID = 1L;

	@SchemaProperty(name = "主键 ID")
	private Long id;

	@SchemaProperty(name = "冒险团名称")
	private String guildName;

	@SchemaProperty(name = "角色名称")
	private String name;

	@SchemaProperty(name = "职业")
	private String job;

	@SchemaProperty(name = "冒险家名望")
	private Integer fame;

	@SchemaProperty(name = "生命")
	private Integer hp;

	@SchemaProperty(name = "魔法")
	private Integer mp;

	@SchemaProperty(name = "物理防御率")
	private Double physicalDefenseRate;

	@SchemaProperty(name = "魔法防御率")
	private Double magicDefenseRate;

	@SchemaProperty(name = "力量")
	private Integer strength;

	@SchemaProperty(name = "智力")
	private Integer intelligence;

	@SchemaProperty(name = "体力")
	private Integer stamina;

	@SchemaProperty(name = "精神")
	private Integer spirit;

	@SchemaProperty(name = "物理攻击")
	private Integer physicalAttack;

	@SchemaProperty(name = "魔法攻击")
	private Integer magicAttack;

	@SchemaProperty(name = "独立攻击")
	private Integer independentAttack;

	@SchemaProperty(name = "物理暴击率")
	private Double physicalCritRate;

	@SchemaProperty(name = "魔法暴击率")
	private Double magicCritRate;

	@SchemaProperty(name = "攻击速度")
	private Double attackSpeed;

	@SchemaProperty(name = "施放速度")
	private Double castSpeed;

	@SchemaProperty(name = "移动速度")
	private Double moveSpeed;

	@SchemaProperty(name = "火属性强化")
	private Integer fire;

	@SchemaProperty(name = "冰属性强化")
	private Integer ice;

	@SchemaProperty(name = "光属性强化")
	private Integer light;

	@SchemaProperty(name = "暗属性强化")
	private Integer dark;

	@SchemaProperty(name = "创建者")
	private Long creator;

	@SchemaProperty(name = "创建时间")
	private Date createDate;

	@SchemaProperty(name = "更新者")
	private Long updater;

	@SchemaProperty(name = "更新时间")
	private Date updateDate;


}
