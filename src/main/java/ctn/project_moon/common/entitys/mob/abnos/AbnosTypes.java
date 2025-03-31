package ctn.project_moon.common.entitys.mob.abnos;

import ctn.project_moon.api.PmApi;

public enum AbnosTypes {
    ZAYIN("zayin",1),
    TETH("teth",2),
    HE("he",3),
    WAW("waw",4),
    ALEPH("aleph",5);

    private final String name;
    private final int Level;
    AbnosTypes(String name, int Level) {
        this.name = name;
        this.Level = Level;
    }

    public String getName() {
        return name;
    }

    public int getLevel() {
        return Level;
    }

    public static double damageMultiple(AbnosTypes type, AbnosTypes type2) {
        return PmApi.damageMultiple(type, type2);
    }
}
