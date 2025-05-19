package io.renren.modules.dnf.enums;

import lombok.Getter;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Getter
public enum DnfCareerEnum {

    // Male Slayer
    GHOST_KNIGHT("ghost_knight", "鬼剑士(男)", 1, 0),
    BLADE_MASTER("blade_master", "剑魂", 101, 1),
    BERSERKER("berserker", "狂战士", 102, 1),
    ASURA("asura", "阿修罗", 103, 1),
    GHOST_LORD("ghost_lord", "鬼泣", 104, 1),
    SWORD_SHADOW("sword_shadow", "剑影", 105, 1),

    // Female Slayer
    FEMALE_SLAYER("female_slayer", "鬼剑士(女)", 2, 0),
    SWORD_MASTER("sword_master", "驭剑士", 201, 2),
    DEMON_SLAYER("demon_slayer", "契魔者", 202, 2),
    DARK_TEMPLAR("dark_templar", "黑暗武士", 203, 2),
    VAGABOND("vagabond", "流浪武士", 204, 2),
    BLADE_DANCER("blade_dancer", "剑舞者", 205, 2),

    // Male Fighter
    MALE_FIGHTER("male_fighter", "格斗家(男)", 3, 0),
    STRIKER("striker", "散打", 301, 3),
    GRAPPLER("grappler", "柔道家", 302, 3),
    BRAWLER("brawler", "街霸", 303, 3),
    NEN_MASTER("nen_master", "气功师", 304, 3),

    // Female Fighter
    FEMALE_FIGHTER("female_fighter", "格斗家(女)", 4, 0),
    STRIKER_F("striker_f", "散打", 401, 4),
    GRAPPLER_F("grappler_f", "柔道家", 402, 4),
    BRAWLER_F("brawler_f", "街霸", 403, 4),
    NEN_MASTER_F("nen_master_f", "气功师", 404, 4),

    // Male Gunner
    MALE_GUNNER("male_gunner", "神枪手(男)", 5, 0),
    RANGER("ranger", "漫游枪手", 501, 5),
    MECHANIC("mechanic", "机械师", 502, 5),
    SPITFIRE("spitfire", "弹药专家", 503, 5),
    LAUNCHER("launcher", "枪炮师", 504, 5),
    ALLOY_WARRIOR("alloy_warrior", "合金战士", 505, 5),

    // Female Gunner
    FEMALE_GUNNER("female_gunner", "神枪手(女)", 6, 0),
    RANGER_F("ranger_f", "漫游枪手", 601, 6),
    MECHANIC_F("mechanic_f", "机械师", 602, 6),
    SPITFIRE_F("spitfire_f", "弹药专家", 603, 6),
    LAUNCHER_F("launcher_f", "枪炮师", 604, 6),

    // Male Mage
    MALE_MAGE("male_mage", "魔法师(男)", 7, 0),
    ELEMENTAL_BOMBER("elemental_bomber", "元素爆破师", 701, 7),
    GLACIER_MASTER("glacier_master", "冰结师", 702, 7),
    BLOOD_MAGE("blood_mage", "血法师", 703, 7),
    SWIFT_MASTER("swift_master", "逐风者", 704, 7),
    DIMENSION_WALKER("dimension_walker", "次元行者", 705, 7),

    // Female Mage
    FEMALE_MAGE("female_mage", "魔法师(女)", 8, 0),
    ELEMENTAL_MASTER("elemental_master", "元素师", 801, 8),
    SUMMONER("summoner", "召唤师", 802, 8),
    BATTLE_MAGE("battle_mage", "战斗法师", 803, 8),
    WITCH("witch", "魔道学者", 804, 8),
    LITTLE_WITCH("little_witch", "小魔女", 805, 8),

    // Priest
    MALE_PRIEST("male_priest", "光职者(男)", 9, 0),
    CRUSADER("crusader", "光明骑士(男)", 901, 9),
    MONK("monk", "蓝拳圣使", 902, 9),
    EXORCIST("exorcist", "驱魔师(男)", 903, 9),
    JUDGEMENT("judgement", "惩戒者", 904, 9),


    // Female Priest
    FEMALE_PRIEST("female_priest", "光职者(女)", 10, 0),
    FEMALE_CRUSADER("crusader_f", "光明骑士(女)", 1001, 10),
    JUDGE("judge", "正义审判者", 1002, 10),
    FEMALE_EXORCIST("exorcist_f", "驱魔师(女)", 1003, 10),
    EXTERMINATOR("exterminator", "除恶者", 1004, 10),



    // Thief
    THIEF("thief", "暗夜使者", 11, 0),
    ROGUE("rogue", "刺客", 1101, 11),
    NECROMANCER("necromancer", "死灵术士", 1102, 11),
    SHADOW_DANCER("shadow_dancer", "影舞者", 1103, 11),
    NINJA("ninja", "忍者", 1104, 11),

    // Knight
    KNIGHT("knight", "守护者", 12, 0),
    DRAGON_KNIGHT("dragon_knight", "精灵骑士", 1202, 12),
    CHAOS("chaos", "混沌魔灵", 1203, 12),
    PALADIN("paladin", "帕拉丁", 1204, 12),

    // Spearman
    SPEARMAN("spearman", "魔枪士", 13, 0),
    CONQUEROR("conqueror", "征战者", 1301, 13),
    WARRIOR("warrior", "决战者", 1302, 13),
    HUNTSMAN("huntsman", "狩猎者", 1303, 13),
    DARK_SPEARMAN("dark_spearman", "暗枪士", 1304, 13),

    // Gun Slayer
    GUN_SLAYER("gun_slayer", "枪剑士", 14, 0),
    AGENT("agent", "特工", 1401, 14),
    BATTLE_MERCENARY("battle_mercenary", "战线佣兵", 1402, 14),
    DARK_BLADE("dark_blade", "暗刃", 1403, 14),
    ARCANA_SPECIALIS("arcana_specialis", "源能专家", 1404, 14),

    // Archer
    ARCHER("archer", "弓箭手", 15, 0),
    MUSE("muse", "缪斯", 1501, 15),
    VOYAGER("voyager", "旅人", 1502, 15),
    HUNTER("hunter", "猎人", 1503, 15),
    WARDANCER("wardancer", "妖护使", 1504, 15),
    CHIMERA("chimera", "奇美拉", 1505, 15),

    // External Career
    EXTERNAL_CAREER("External Career", "外传职业", 16, 0),
    DARK_KNIGHT("dark_knight", "黑暗武士", 1601, 16),
    FOUNDER("founder", "缔造者", 1602, 16),


    // Unknown
    UNKNOWN("unknown", "未知职业", 0, 0);

    private final String career;
    private final String name;
    private final int id;
    private final int parent;
    private final String avatar;

    DnfCareerEnum(String career, String name, int id, int parent, String avatar) {
        this.career = career;
        this.name = name;
        this.id = id;
        this.parent = parent;
        this.avatar = avatar;
    }

    DnfCareerEnum(String career, String name, int id, int parent) {
        this.career = career;
        this.name = name;
        this.id = id;
        this.parent = parent;
        this.avatar = "";
    }

    public static DnfCareerEnum getByCareer(String career) {
        for (DnfCareerEnum careerEnum : DnfCareerEnum.values()) {
            if (careerEnum.getCareer().equals(career)) {
                return careerEnum;
            }
        }
        return UNKNOWN;
    }

    public static final Map<Integer, DnfCareerEnum> careerEnumMap = new HashMap<Integer, DnfCareerEnum>();
    static {
        for (DnfCareerEnum careerEnum : DnfCareerEnum.values()) {
            careerEnumMap.put(careerEnum.getId(), careerEnum);
        }
    }
}