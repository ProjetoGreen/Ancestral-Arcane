package com.ancestralarcane.magic.spells;

public enum SpellType {
    FLAME("flame"),
    CHANNELING("channeling"),
    MENDING("mending"),
    RESPIRATION("respiration"),
    SILK_TOUCH("silk_touch"),
    EFFICIENCY("efficiency"),
    FORTUNE("fortune"),
    LOYALTY("loyalty");

    private final String id;

    SpellType(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public static SpellType fromId(String id) {
        if (id == null)
            return null;
        for (SpellType type : values()) {
            if (type.id.equals(id)) {
                return type;
            }
        }
        return null;
    }
}
