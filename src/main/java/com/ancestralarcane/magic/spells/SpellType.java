package com.ancestralarcane.magic.spells;

public enum SpellType {
    FIRE("fire"),
    FIRE_FRIEND("fire_friend"),
    STORM("storm"),
    FROST("frost"),
    FROST_WALKER("frost_walker"),
    HEAL("heal"),
    MEND("mend"),
    STABILIZE("stabilize"),
    CLEANSE("cleanse"),
    BREATHE("breathe"),
    FERTILIZE("fertilize"),
    LIGHT("light"),
    BREAKER("breaker"),
    WARD("ward"),
    STONEBIND("stonebind"),
    REACH("reach"),
    SILENCE("silence"),
    HEARTSTONE("heartstone"),
    WOLVES("wolves");

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
