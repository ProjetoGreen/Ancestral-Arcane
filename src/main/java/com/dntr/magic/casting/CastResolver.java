package com.dntr.magic.casting;

import com.dntr.magic.spells.SpellType;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemStack;
import net.minecraft.nbt.CompoundTag;

public class CastResolver {

    public static int getBaseChannel(String catalyst) {
        if (catalyst == null)
            return 70;
        return switch (catalyst) {
            case "flint" -> 70;
            case "copper" -> 80;
            case "iron" -> 85;
            case "gold" -> 95;
            case "diamond" -> 90;
            case "emerald" -> 95;
            case "netherite" -> 100;
            default -> 70;
        };
    }

    public static int getReuse(String catalyst) {
        if (catalyst == null)
            return 5;
        return switch (catalyst) {
            case "flint" -> 5;
            case "copper" -> 4;
            case "iron" -> 3;
            case "gold" -> 3;
            case "diamond" -> 1;
            case "emerald" -> 1;
            case "netherite" -> 1;
            default -> 5;
        };
    }

    public static boolean hasAffinity(String catalyst, SpellType spell) {
        if (catalyst == null || spell == null)
            return false;
        if (catalyst.equals("flint") && spell == SpellType.FIRE)
            return true;
        if (catalyst.equals("copper") && spell == SpellType.STORM)
            return true;
        if (catalyst.equals("diamond") && spell == SpellType.BREAKER)
            return true;
        if (catalyst.equals("emerald") && spell == SpellType.HEAL)
            return true;
        if (catalyst.equals("netherite") && spell == SpellType.STORM)
            return true;
        return false;
    }

    public static int getEffectiveChannel(String catalyst, SpellType spell) {
        int base = getBaseChannel(catalyst);
        int bonus = hasAffinity(catalyst, spell) ? 15 : 0;
        return Math.min(100, base + bonus);
    }

    public static float getPowerMultiplier(int castPercent, int effectiveChannel) {
        float castValue = castPercent / 100.0f;
        float channelValue = effectiveChannel / 100.0f;
        float power = castValue * channelValue;
        return Mth.clamp(power, 0.30f, 1.50f);
    }

    public static int getCastIndex(float progress) {
        if (progress < 0.60f)
            return 0;
        if (progress < 0.85f)
            return 1;
        if (progress < 0.99f)
            return 2;
        return 3;
    }

    public static float getEffectiveCastTimeTicks(int baseTicks, String upgradeType, int upgradeLevel,
            SpellType spell) {
        float reduction = 0f;
        if (upgradeType != null && spell != null) {
            boolean matches = false;
            if (upgradeType.equals("blaze") && spell == SpellType.FIRE)
                matches = true;
            if (upgradeType.equals("quartz") && spell == SpellType.STORM)
                matches = true;
            if (upgradeType.equals("tear") && spell == SpellType.HEAL)
                matches = true;

            if (matches) {
                if (upgradeLevel == 1)
                    reduction = 0.10f;
                else if (upgradeLevel == 2)
                    reduction = 0.20f;
                else if (upgradeLevel >= 3)
                    reduction = 0.30f;
            }
        }
        return baseTicks * (1.0f - reduction);
    }
}
