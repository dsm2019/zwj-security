package io.renren.modules.dnf.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.renren.common.entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Data
@TableName("dnf_character_info")
public class DnfCharacterInfoEntity extends BaseEntity {

    //冒险团名称
    private String guildName;
    //角色名
    private String name;
    //职业
    private String job;
    //冒险家名望
    private Integer fame;
    //生命
    private Integer hp;
    //魔法
    private Integer mp;
    //物理防御率
    private Double physicalDefenseRate;
    //魔法防御率
    private Double magicDefenseRate;

    //力量
    private Integer strength;
    //智力
    private Integer intelligence;
    //体力
    private Integer stamina;
    //精神
    private Integer spirit;
    //物理攻击
    private Integer physicalAttack;
    //魔法攻击
    private Integer magicAttack;
    //独立攻击
    private Integer independentAttack;
    //物理暴击率
    private Double physicalCritRate;
    //魔法暴击率
    private Double magicCritRate;

    //攻击速度
    private Double attackSpeed;
    //施放速度
    private Double castSpeed;
    //移动速度
    private Double moveSpeed;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Long updater;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateDate;

    // 攻击属性
    private AttackAttribute attackAttribute;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class AttackAttribute {
        //火属性
        private Integer fire;
        //冰属性
        private Integer ice;
        //光属性
        private Integer light;
        //暗属性
        private Integer dark;
    }


    // 个人信息（m） 角色 装扮/宠物 护石 你才是真正的英雄 115级PL刷完大乱斗 [光启·光明骑士] 冒险家名望75.662
    // 生命182551/182551 魔法107136/107136 物理防御率 42.8% 魔法防御率 39.6% 力量 9286 智力 9286 精炼 体力 6052 精神 6637
    // 物理攻击 2536 魔法攻击 2458 物理暴击 +109.9% 魔法暴击 +109.9% 独立攻击 3970 攻击速度 +92.0% 施放速度 +120.3% 移动速度 +82.0%
    // 攻击属性 火（107）/水（107）/光（107）/暗（107） 副职业 战斗分析决斗信息 详细信息 T 主动技能

}
