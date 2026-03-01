package com.ancestralarcane.item;

import com.ancestralarcane.data.CustomDataUtil;
import com.ancestralarcane.magic.casting.CastResolver;
import com.ancestralarcane.magic.spells.SpellExecutor;
import com.ancestralarcane.magic.spells.SpellType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;

import java.util.List;

public class ArcaneSpearFocusItem extends Item {
    public ArcaneSpearFocusItem(Properties properties) {
        super(properties);
    }

    @Override
    public int getUseDuration(ItemStack stack, LivingEntity entity) {
        return 72000;
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.BOW;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        CompoundTag data = CustomDataUtil.getAncestralArcaneData(stack);
        if (!data.contains("rune")) {
            return InteractionResultHolder.fail(stack);
        }
        CompoundTag rune = data.getCompound("rune");
        if (rune.getInt("charges") <= 0) {
            return InteractionResultHolder.fail(stack);
        }

        if (player.getCooldowns().isOnCooldown(this)) {
            return InteractionResultHolder.fail(stack);
        }

        player.startUsingItem(hand);
        return InteractionResultHolder.consume(stack);
    }

    @Override
    public void releaseUsing(ItemStack stack, Level level, LivingEntity entityLiving, int timeLeft) {
        if (!(entityLiving instanceof Player player))
            return;

        int useDuration = this.getUseDuration(stack, entityLiving) - timeLeft;

        CompoundTag data = CustomDataUtil.getAncestralArcaneData(stack);
        if (!data.contains("rune"))
            return;

        CompoundTag rune = data.getCompound("rune");
        String spellStr = rune.getString("spell");
        SpellType spell = SpellType.fromId(spellStr);
        if (spell == null)
            return;

        String catalyst = data.getString("catalyst");
        int baseCastTime = data.contains("cast_time_base") ? data.getInt("cast_time_base") : 20;

        String upgradeType = null;
        int upgradeLevel = 0;
        if (rune.contains("upgrade")) {
            CompoundTag upg = rune.getCompound("upgrade");
            upgradeType = upg.getString("type");
            upgradeLevel = upg.getInt("level");
        }

        float effectiveTicks = CastResolver.getEffectiveCastTimeTicks(baseCastTime, upgradeType, upgradeLevel, spell);
        float progress = Math.min(1.0f, useDuration / effectiveTicks);

        int idx = CastResolver.getCastIndex(progress);

        int charges = rune.getInt("charges");
        int dirty = rune.getInt("dirty");

        if (progress < 0.60f) {
            charges -= 2;
            dirty += 2;
        } else {
            charges -= 1;
            dirty += 1;
        }

        rune.putInt("charges", charges);
        rune.putInt("dirty", dirty);
        data.put("rune", rune);
        CustomDataUtil.setAncestralArcaneData(stack, data);

        if (!level.isClientSide) {
            int effectiveChannel = CastResolver.getEffectiveChannel(catalyst, spell);
            float powerMultiplier = CastResolver
                    .getPowerMultiplier(idx == 0 ? 50 : idx == 1 ? 80 : idx == 2 ? 100 : 150, effectiveChannel);

            boolean success = SpellExecutor.execute((ServerPlayer) player, spell, rune.getInt("lvl"), powerMultiplier);

            if (success) {
                int cd = getSpellCooldown(spell);
                player.getCooldowns().addCooldown(this, cd);
                level.playSound(null, player.getX(), player.getY(), player.getZ(),
                        SoundEvents.ILLUSIONER_PREPARE_BLINDNESS, SoundSource.PLAYERS, 1.0f, 1.0f);
            }

            degradeRune((ServerPlayer) player, stack, data, rune, catalyst);
        }
    }

    private void degradeRune(ServerPlayer player, ItemStack stack, CompoundTag data, CompoundTag rune,
            String catalyst) {
        int reuse = CastResolver.getReuse(catalyst);
        int dirty = rune.getInt("dirty");
        int charges = rune.getInt("charges");
        int lvl = rune.getInt("lvl");

        boolean maxedDirty = dirty >= reuse;
        boolean noCharges = charges <= 0;

        if (maxedDirty || noCharges) {
            lvl -= 1;
            rune.putInt("lvl", lvl);
            rune.putInt("dirty", 0);
            rune.putInt("charges", Math.max(0, Math.min(charges, lvl * 10)));

            if (lvl <= 0) {
                int crafted = rune.getInt("crafted");
                int residues = (crafted == 1) ? 1 : 2;
                player.drop(new ItemStack(com.ancestralarcane.registry.AncestralArcaneItems.ARCANE_RESIDUE.get(), residues), false);

                if (crafted == 1) {
                    ItemStack emptyRune = new ItemStack(com.ancestralarcane.registry.AncestralArcaneItems.RUNE.get());
                    CompoundTag emptyData = new CompoundTag();
                    emptyData.putString("kind", "rune");
                    emptyData.putInt("tier", rune.getInt("tier"));
                    emptyData.putInt("crude", 0);
                    emptyData.putInt("empty", 1);
                    emptyData.putInt("crafted", 1);
                    emptyData.putString("last_spell", rune.getString("spell"));
                    CustomDataUtil.setAncestralArcaneData(emptyRune, emptyData);
                    player.drop(emptyRune, false);
                }
                data.remove("rune");
            }
            CustomDataUtil.setAncestralArcaneData(stack, data);
        }
    }

    private int getSpellCooldown(SpellType spell) {
        return switch (spell) {
            case FIRE -> 20;
            case STORM -> 100;
            case HEAL -> 60;
            case BREATHE -> 40;
            case HEARTSTONE -> 2400;
            case BREAKER -> 80;
            case FERTILIZE -> 100;
            case WOLVES -> 600;
        };
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents,
            TooltipFlag tooltipFlag) {
        CompoundTag data = CustomDataUtil.getAncestralArcaneData(stack);
        if (data.contains("catalyst")) {
            tooltipComponents.add(Component.literal("Catalyst: " + data.getString("catalyst")));
        }
        if (data.contains("rune")) {
            CompoundTag rune = data.getCompound("rune");
            tooltipComponents.add(Component.literal("Spell: " + rune.getString("spell")));
            tooltipComponents.add(Component.literal("Lv: " + rune.getInt("lvl") + " | Tier: " + rune.getInt("tier")));
            tooltipComponents
                    .add(Component.literal("Charges: " + rune.getInt("charges") + "/" + (rune.getInt("lvl") * 10)));
        } else {
            tooltipComponents.add(Component.literal("No Rune Bound"));
        }
    }
}
