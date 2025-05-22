package io.renren.modules.dnf.utils;

import io.renren.modules.dnf.entity.DnfCharacterInfoEntity;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ParseEntityUtil {

    public static DnfCharacterInfoEntity parseDCIEntity(String text){
        DnfCharacterInfoEntity entity = new DnfCharacterInfoEntity();

        // 处理 冒险团名称
        Pattern guildNamePattern = Pattern.compile("护石\\s+(.*?)\\s+\\d+级");
        Matcher guildNameMatcher = guildNamePattern.matcher(text);
        if (guildNameMatcher.find()) {
            entity.setGuildName(guildNameMatcher.group(1).trim());
        }

        // 处理 name
        Pattern namePattern = Pattern.compile("\\d+级(.*?)\\s*\\[");
        Matcher nameMatcher = namePattern.matcher(text);
        if (nameMatcher.find()) {
            entity.setName(nameMatcher.group(1).trim());
        }

        // 处理 job
        Pattern jobPattern = Pattern.compile("\\[(.*?)\\]");
        Matcher jobMatcher = jobPattern.matcher(text);
        if (jobMatcher.find()) {
            entity.setJob(jobMatcher.group(1));
        }

        // Regex Parsing for Fame
        Pattern famePattern = Pattern.compile("冒险家名望(\\d+\\.\\d+)");
        Matcher fameMatcher = famePattern.matcher(text);
        if (fameMatcher.find()) {
            String fameString = fameMatcher.group(1).replace(".", "");
            entity.setFame(Integer.parseInt(fameString));
        }

        // Regex Parsing for HP and MP
        Pattern hpMpPattern = Pattern.compile("生命(\\d+)/(\\d+) 魔法(\\d+)/(\\d+)");
        Matcher hpMpMatcher = hpMpPattern.matcher(text);
        if (hpMpMatcher.find()) {
            entity.setHp(Integer.parseInt(hpMpMatcher.group(1)));
            entity.setMp(Integer.parseInt(hpMpMatcher.group(3)));
        }

        // 解析防御力
        Pattern defensePattern = Pattern.compile("物理防御率 (\\d+\\.\\d+)% 魔法防御率 (\\d+\\.\\d+)%");
        Matcher defenseMatcher = defensePattern.matcher(text);
        if (defenseMatcher.find()) {
            entity.setPhysicalDefenseRate(Double.parseDouble(defenseMatcher.group(1)));
            entity.setMagicDefenseRate(Double.parseDouble(defenseMatcher.group(2)));
        }

        // 解析四维属性
        Pattern statsPattern = Pattern.compile("力量 (\\d+) 智力 (\\d+) .*? 体力 (\\d+) 精神 (\\d+)");
        Matcher statsMatcher = statsPattern.matcher(text);
        if (statsMatcher.find()) {
            entity.setStrength(Integer.parseInt(statsMatcher.group(1)));
            entity.setIntelligence(Integer.parseInt(statsMatcher.group(2)));
            entity.setStamina(Integer.parseInt(statsMatcher.group(3)));
            entity.setSpirit(Integer.parseInt(statsMatcher.group(4)));
        }

        // 解析物理攻击
        Pattern physicalAttackPattern = Pattern.compile("物理攻击 (\\d+)");
        Matcher physicalAttackMatcher = physicalAttackPattern.matcher(text);
        if (physicalAttackMatcher.find()) {
            entity.setPhysicalAttack(Integer.parseInt(physicalAttackMatcher.group(1)));
        }

        // 解析魔法攻击
        Pattern magicAttackPattern = Pattern.compile("魔法攻击 (\\d+)");
        Matcher magicAttackMatcher = magicAttackPattern.matcher(text);
        if (magicAttackMatcher.find()) {
            entity.setMagicAttack(Integer.parseInt(magicAttackMatcher.group(1)));
        }

        // 解析独立攻击
        Pattern independentAttackPattern = Pattern.compile("独立攻击 (\\d+)");
        Matcher independentAttackMatcher = independentAttackPattern.matcher(text);
        if (independentAttackMatcher.find()) {
            entity.setIndependentAttack(Integer.parseInt(independentAttackMatcher.group(1)));
        }

        // 解析物理暴击率
        Pattern physicalCritRatePattern = Pattern.compile("物理暴击 \\+?([\\d.]+)%");
        Matcher physicalCritRateMatcher = physicalCritRatePattern.matcher(text);
        if (physicalCritRateMatcher.find()) {
            entity.setPhysicalCritRate(Double.parseDouble(physicalCritRateMatcher.group(1)) / 100);
        }

        // 解析魔法暴击率
        Pattern magicCritRatePattern = Pattern.compile("魔法暴击 \\+?([\\d.]+)%");
        Matcher magicCritRateMatcher = magicCritRatePattern.matcher(text);
        if (magicCritRateMatcher.find()) {
            entity.setMagicCritRate(Double.parseDouble(magicCritRateMatcher.group(1)) / 100);
        }

        // 解析攻击速度
        Pattern attackSpeedPattern = Pattern.compile("攻击速度 \\+?([\\d.]+)%");
        Matcher attackSpeedMatcher = attackSpeedPattern.matcher(text);
        if (attackSpeedMatcher.find()) {
            entity.setAttackSpeed(Double.parseDouble(attackSpeedMatcher.group(1)) / 100);
        }

        // 解析施放速度
        Pattern castSpeedPattern = Pattern.compile("施放速度 \\+?([\\d.]+)%");
        Matcher castSpeedMatcher = castSpeedPattern.matcher(text);
        if (castSpeedMatcher.find()) {
            entity.setCastSpeed(Double.parseDouble(castSpeedMatcher.group(1)) / 100);
        }

        // 解析移动速度
        Pattern moveSpeedPattern = Pattern.compile("移动速度 \\+?([\\d.]+)%");
        Matcher moveSpeedMatcher = moveSpeedPattern.matcher(text);
        if (moveSpeedMatcher.find()) {
            entity.setMoveSpeed(Double.parseDouble(moveSpeedMatcher.group(1)) / 100);
        }

        // 解析攻击属性
        Pattern attackPattern = Pattern.compile("攻击属性 火（(\\d+)）/水（(\\d+)）/光（(\\d+)）/暗（(\\d+)）");
        Matcher attackMatcher = attackPattern.matcher(text);
        if (attackMatcher.find()) {
            DnfCharacterInfoEntity.AttackAttribute attackAttribute = new DnfCharacterInfoEntity.AttackAttribute();
            attackAttribute.setFire(Integer.parseInt(attackMatcher.group(1)));
            attackAttribute.setIce(Integer.parseInt(attackMatcher.group(2)));
            attackAttribute.setLight(Integer.parseInt(attackMatcher.group(3)));
            attackAttribute.setDark(Integer.parseInt(attackMatcher.group(4)));
            entity.setAttackAttribute(attackAttribute);
        }

        return entity;
    }

}
