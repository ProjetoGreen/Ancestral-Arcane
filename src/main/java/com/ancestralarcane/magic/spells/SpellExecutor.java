package com.ancestralarcane.magic.spells;

import com.ancestralarcane.registry.AncestralArcaneBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.item.BoneMealItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;

import java.util.List;
import java.util.Optional;

public class SpellExecutor {

    public static boolean execute(ServerPlayer player, SpellType spell, int spellLevel, float powerMultiplier) {
        Level level = player.level();
        if (!(level instanceof ServerLevel serverLevel))
            return false;

        return switch (spell) {
            case FLAME -> executeFlame(player, serverLevel, spellLevel, powerMultiplier);
            case CHANNELING -> executeChanneling(player, serverLevel, spellLevel, powerMultiplier);
            case MENDING -> executeMending(player, serverLevel, spellLevel, powerMultiplier);
            case RESPIRATION -> executeRespiration(player, serverLevel, spellLevel, powerMultiplier);
            case SILK_TOUCH -> executeSilkTouch(player, serverLevel, spellLevel, powerMultiplier);
            case EFFICIENCY -> executeEfficiency(player, serverLevel, spellLevel, powerMultiplier);
            case FORTUNE -> executeFortune(player, serverLevel, spellLevel, powerMultiplier);
            case LOYALTY -> executeLoyalty(player, serverLevel, spellLevel, powerMultiplier);
        };
    }

    private static boolean executeFlame(ServerPlayer player, ServerLevel level, int spellLevel, float powerMultiplier) {
        HitResult hit = player.pick(12.0, 0.0f, false);
        int[] baseDamage = { 2, 3, 4, 5, 6 };
        int[] baseFireTicks = { 40, 60, 80, 100, 120 };
        int idx = Math.min(spellLevel - 1, 4);
        if (idx < 0)
            idx = 0;

        int finalDamage = Math.round(baseDamage[idx] * powerMultiplier);
        int finalFireTicks = Math.round(baseFireTicks[idx] * powerMultiplier);

        if (hit.getType() == HitResult.Type.ENTITY) {
            EntityHitResult ehr = (EntityHitResult) hit;
            Entity target = ehr.getEntity();
            target.hurt(target.damageSources().inFire(), finalDamage);
            target.setRemainingFireTicks(target.getRemainingFireTicks() + finalFireTicks);
            level.sendParticles(ParticleTypes.FLAME, target.getX(), target.getY() + 1, target.getZ(), 10, 0.2, 0.2, 0.2,
                    0.05);
            return true;
        } else if (hit.getType() == HitResult.Type.BLOCK) {
            BlockHitResult bhr = (BlockHitResult) hit;
            BlockPos pos = bhr.getBlockPos().relative(bhr.getDirection());
            if (level.isEmptyBlock(pos)) {
                level.setBlockAndUpdate(pos, Blocks.FIRE.defaultBlockState());
            }
            level.sendParticles(ParticleTypes.FLAME, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, 5, 0.2, 0.2,
                    0.2, 0.05);
            return true;
        }
        return false;
    }

    private static boolean executeChanneling(ServerPlayer player, ServerLevel level, int spellLevel,
            float powerMultiplier) {
        HitResult hit = player.pick(12.0, 0.0f, false);
        if (hit.getType() == HitResult.Type.ENTITY) {
            Entity target = ((EntityHitResult) hit).getEntity();
            boolean openSky = level.canSeeSky(target.blockPosition());
            if (spellLevel < 3 || !openSky) {
                int[] shockBase = { 1, 2, 3, 4, 5 };
                int idx = Math.min(spellLevel - 1, 4);
                if (idx < 0)
                    idx = 0;
                int dmg = Math.round(shockBase[idx] * powerMultiplier);
                target.hurt(target.damageSources().magic(), dmg);
                if (target instanceof LivingEntity le) {
                    le.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 40, 0));
                }
            } else {
                int[] lightningBase = { 0, 0, 4, 5, 6 };
                int idx = Math.min(spellLevel - 1, 4);
                if (idx < 0)
                    idx = 0;
                int dmg = Math.round(lightningBase[idx] * powerMultiplier);
                target.hurt(target.damageSources().lightningBolt(), dmg);
                net.minecraft.world.entity.LightningBolt bolt = EntityType.LIGHTNING_BOLT.create(level);
                if (bolt != null) {
                    bolt.moveTo(target.position());
                    bolt.setVisualOnly(true);
                    level.addFreshEntity(bolt);
                }
            }
            return true;
        }
        return false;
    }

    private static boolean executeMending(ServerPlayer player, ServerLevel level, int spellLevel,
            float powerMultiplier) {
        int[] repairs = { 50, 100, 200, 300, 500 };
        int idx = Math.min(spellLevel - 1, 4);
        if (idx < 0)
            idx = 0;

        int repairAmount = Math.round(repairs[idx] * powerMultiplier);
        boolean repairedAny = false;

        for (ItemStack armor : player.getArmorSlots()) {
            if (armor.isDamaged()) {
                armor.setDamageValue(Math.max(0, armor.getDamageValue() - repairAmount));
                repairedAny = true;
                break; // only rep one per cast
            }
        }

        if (repairedAny) {
            level.sendParticles(ParticleTypes.HAPPY_VILLAGER, player.getX(), player.getY() + 1.0, player.getZ(), 10,
                    0.4, 0.4, 0.4, 0.1);
            return true;
        }
        return false;
    }

    private static boolean executeRespiration(ServerPlayer player, ServerLevel level, int spellLevel,
            float powerMultiplier) {
        int[] baseDur = { 300, 600, 1200, 1800, 2400 };
        int idx = Math.min(spellLevel - 1, 4);
        if (idx < 0)
            idx = 0;
        long calc = Math.round(baseDur[idx] * powerMultiplier);
        int dur = (int) Math.min(calc, Math.round(baseDur[idx] * 1.2));
        player.addEffect(new MobEffectInstance(MobEffects.WATER_BREATHING, dur, 0));
        return true;
    }

    private static boolean executeSilkTouch(ServerPlayer player, ServerLevel level, int spellLevel,
            float powerMultiplier) {
        HitResult hit = player.pick(6.0, 0.0f, false);
        if (hit.getType() == HitResult.Type.BLOCK) {
            BlockPos targetPos = ((BlockHitResult) hit).getBlockPos();
            BlockState state = level.getBlockState(targetPos);
            if (state.getDestroySpeed(level, targetPos) >= 0) { // Not bedrock
                if (consumeItem(player, new ItemStack(Items.EMERALD))) { // Needs an emerald
                    Block.popResource(level, targetPos, new ItemStack(state.getBlock()));
                    level.destroyBlock(targetPos, false);
                    return true;
                }
            }
        }
        return false;
    }

    private static boolean executeEfficiency(ServerPlayer player, ServerLevel level, int spellLevel,
            float powerMultiplier) {
        HitResult hit = player.pick(6.0, 0.0f, false);
        if (hit.getType() == HitResult.Type.BLOCK) {
            BlockPos targetPos = ((BlockHitResult) hit).getBlockPos();
            BlockState state = level.getBlockState(targetPos);
            if (state.getDestroySpeed(level, targetPos) >= 0) {
                if (consumeItem(player, new ItemStack(Items.IRON_INGOT))) {
                    level.destroyBlock(targetPos, true);
                    return true;
                }
            }
        }
        return false;
    }

    private static boolean executeFortune(ServerPlayer player, ServerLevel level, int spellLevel,
            float powerMultiplier) {
        player.addEffect(new MobEffectInstance(MobEffects.LUCK, spellLevel * 600, spellLevel - 1));
        return true;
    }

    private static boolean executeLoyalty(ServerPlayer player, ServerLevel level, int spellLevel,
            float powerMultiplier) {
        int count = 0;
        for (Entity e : level.getAllEntities()) {
            if (e instanceof net.minecraft.world.entity.TamableAnimal pet) {
                if (pet.isOwnedBy(player)) {
                    pet.teleportTo(player.getX(), player.getY(), player.getZ());
                    count++;
                }
            }
        }
        return count > 0;
    }

    private static boolean consumeItem(ServerPlayer player, ItemStack stack) {
        return consumeItem(player, stack, 1);
    }

    private static int countItem(ServerPlayer player, net.minecraft.world.item.Item item) {
        int count = 0;
        for (ItemStack is : player.getInventory().items) {
            if (is.is(item))
                count += is.getCount();
        }
        return count;
    }

    private static boolean consumeItem(ServerPlayer player, ItemStack stack, int amount) {
        int found = countItem(player, stack.getItem());
        if (found >= amount) {
            int remaining = amount;
            for (int i = 0; i < player.getInventory().getContainerSize(); i++) {
                ItemStack is = player.getInventory().getItem(i);
                if (is.is(stack.getItem())) {
                    int take = Math.min(remaining, is.getCount());
                    is.shrink(take);
                    remaining -= take;
                    if (remaining <= 0)
                        break;
                }
            }
            return true;
        }
        return false;
    }
}
