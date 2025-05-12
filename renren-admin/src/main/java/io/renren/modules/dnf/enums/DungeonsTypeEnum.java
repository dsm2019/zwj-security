package io.renren.modules.dnf.enums;

import lombok.Getter;

@Getter
public enum DungeonsTypeEnum {

    NORMAL(1, "普通地下城"),
    CRUSADE(2, "征讨地下城"),
    LEGION(3, "军团地下城"),
    BATTLE(4, "攻坚战"),



    UNKNOWN(0, "<UNK>");

    ;

    private final int code;
    private final String name;

    DungeonsTypeEnum(int code, String name) {
        this.code = code;
        this.name = name;
    }

    public static DungeonsTypeEnum getByCode(int code) {
        for (DungeonsTypeEnum dungeonsTypeEnum : DungeonsTypeEnum.values()) {
            if (dungeonsTypeEnum.getCode() == code) {
                return dungeonsTypeEnum;
            }
        }
        return UNKNOWN;
    }
}
