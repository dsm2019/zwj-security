package io.renren.modules.dnf.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
@Getter
public enum DungeonPeriodEnum {

    DAY(1, "日常"),
    WEEK(2, "周常"),
    MONTH(3, "月常"),
    ;

    private final int period;
    private final String description;

    public static Map<Integer, String> map = new HashMap<>();

    static {
        for (DungeonPeriodEnum d : DungeonPeriodEnum.values()) {
            map.put(d.getPeriod(), d.getDescription());
        }
    }
}
