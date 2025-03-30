package ctn.project_moon.common.entitys.mob.abnormalities;

import ctn.project_moon.api.PmApi;

/**
 * @author wang_
 * @version 2024.3.4.1
 * @description
 * @date 2025/3/29
 */
public enum AbnormalitiesTypes {
    ZAYIN("zayin",1),
    TETH("teth",2),
    HE("he",3),
    WAW("waw",4),
    ALEPH("aleph",5);

    private final String name;
    private final int Level;
    AbnormalitiesTypes(String name, int Level) {
        this.name = name;
        this.Level = Level;
    }

    public String getName() {
        return name;
    }

    public int getLevel() {
        return Level;
    }

    public static double damageMultiple(AbnormalitiesTypes type, AbnormalitiesTypes type2) {
        return PmApi.damageMultiple(type, type2);
    }
}
