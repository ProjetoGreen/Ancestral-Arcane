package com.ancestralarcane.magic.spells;

public enum SpellType {
    FIRE("fire"),
    STORM("storm"),
    HEAL("heal"),
    BREATHE("breathe"),
    HEARTSTONE("heartstone"),
    BREAKER("breaker"),
    FERTILIZE("fertilize"),
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
