package com.ancestralarcane.magic.spells;

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
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;

public class SpellExecutor {

    public static boolean execute(ServerPlayer player, SpellType spell, int spellLevel, float powerMultiplier) {
        Level level = player.level();
        if (!(level instanceof ServerLevel serverLevel))
            return false;

        return switch (spell) {
            case FIRE -> executeFire(player, serverLevel, spellLevel, powerMultiplier);
            case FIRE_FRIEND -> executeFireFriend(player, serverLevel, spellLevel, powerMultiplier);
            case STORM -> executeStorm(player, serverLevel, spellLevel, powerMultiplier);
            case FROST -> executeFrost(player, serverLevel, spellLevel, powerMultiplier);
            case FROST_WALKER -> executeFrostWalker(player, serverLevel, spellLevel, powerMultiplier);
            case HEAL -> executeHeal(player, serverLevel, spellLevel, powerMultiplier);
            case MEND -> executeMend(player, serverLevel, spellLevel, powerMultiplier);
            case STABILIZE -> executeStabilize(player, serverLevel, spellLevel, powerMultiplier);
            case CLEANSE -> executeCleanse(player, serverLevel, spellLevel, powerMultiplier);
            case BREATHE -> executeBreathe(player, serverLevel, spellLevel, powerMultiplier);
            case FERTILIZE -> executeFertilize(player, serverLevel, spellLevel, powerMultiplier);
            case LIGHT -> executeLight(player, serverLevel, spellLevel, powerMultiplier);
            case BREAKER -> executeBreaker(player, serverLevel, spellLevel, powerMultiplier);
            case WARD -> executeWard(player, serverLevel, spellLevel, powerMultiplier);
            case STONEBIND -> executeStonebind(player, serverLevel, spellLevel, powerMultiplier);
            case REACH -> executeReach(player, serverLevel, spellLevel, powerMultiplier);
            case SILENCE -> executeSilence(player, serverLevel, spellLevel, powerMultiplier);
            case HEARTSTONE -> executeHeartstone(player, serverLevel, spellLevel, powerMultiplier);
            case WOLVES -> executeWolves(player, serverLevel, spellLevel, powerMultiplier);
        };
    }

    private static boolean executeFire(ServerPlayer player, ServerLevel level, int spellLevel, float powerMultiplier) {
        HitResult hit = player.pick(12.0, 0.0f, false);
        int damage = Math.round((spellLevel * 2) * powerMultiplier);
        if (hit.getType() == HitResult.Type.ENTITY) {
            Entity target = ((EntityHitResult) hit).getEntity();
            target.hurt(target.damageSources().inFire(), damage);
            target.setRemainingFireTicks(target.getRemainingFireTicks() + (spellLevel * 40));
            level.sendParticles(ParticleTypes.FLAME, target.getX(), target.getY() + 1, target.getZ(), 10, 0.2, 0.2, 0.2,
                    0.05);
            return true;
        } else if (hit.getType() == HitResult.Type.BLOCK) {
            BlockPos pos = ((BlockHitResult) hit).getBlockPos().relative(((BlockHitResult) hit).getDirection());
            if (level.isEmptyBlock(pos))
                level.setBlockAndUpdate(pos, Blocks.FIRE.defaultBlockState());
            return true;
        }
        return false;
    }

    private static boolean executeFireFriend(ServerPlayer player, ServerLevel level, int spellLevel,
            float powerMultiplier) {
        player.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, spellLevel * 600, 0));
        return true;
    }

    private static boolean executeStorm(ServerPlayer player, ServerLevel level, int spellLevel, float powerMultiplier) {
        HitResult hit = player.pick(12.0, 0.0f, false);
        if (hit.getType() == HitResult.Type.ENTITY) {
            Entity target = ((EntityHitResult) hit).getEntity();
            target.hurt(target.damageSources().magic(), Math.round((spellLevel * 2) * powerMultiplier));
            if (spellLevel >= 3 && level.canSeeSky(target.blockPosition())) {
                net.minecraft.world.entity.LightningBolt bolt = EntityType.LIGHTNING_BOLT.create(level);
                if (bolt != null) {
                    bolt.moveTo(target.position());
                    bolt.setVisualOnly(spellLevel < 4);
                    level.addFreshEntity(bolt);
                }
            }
            return true;
        }
        return false;
    }

    private static boolean executeFrost(ServerPlayer player, ServerLevel level, int spellLevel, float powerMultiplier) {
        HitResult hit = player.pick(12.0, 0.0f, false);
        if (hit.getType() == HitResult.Type.ENTITY) {
            Entity target = ((EntityHitResult) hit).getEntity();
            target.hurt(target.damageSources().freeze(), Math.round(spellLevel * powerMultiplier));
            target.setTicksFrozen(target.getTicksFrozen() + (spellLevel * 60));
            if (target instanceof LivingEntity le)
                le.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 60 * spellLevel, spellLevel - 1));
            return true;
        }
        return false;
    }

    private static boolean executeFrostWalker(ServerPlayer player, ServerLevel level, int spellLevel,
            float powerMultiplier) {
        // Placeholder for Frost Walker logic
        return true;
    }

    private static boolean executeHeal(ServerPlayer player, ServerLevel level, int spellLevel, float powerMultiplier) {
        player.heal(spellLevel * 2 * powerMultiplier);
        level.sendParticles(ParticleTypes.HAPPY_VILLAGER, player.getX(), player.getY() + 1.0, player.getZ(), 10, 0.4,
                0.4, 0.4, 0.1);
        return true;
    }

    private static boolean executeMend(ServerPlayer player, ServerLevel level, int spellLevel, float powerMultiplier) {
        int repairAmount = Math.round((spellLevel * 100) * powerMultiplier);
        boolean repairedAny = false;
        for (ItemStack armor : player.getArmorSlots()) {
            if (armor.isDamaged()) {
                armor.setDamageValue(Math.max(0, armor.getDamageValue() - repairAmount));
                repairedAny = true;
                break;
            }
        }
        return repairedAny;
    }

    private static boolean executeStabilize(ServerPlayer player, ServerLevel level, int spellLevel,
            float powerMultiplier) {
        player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, spellLevel * 200, 0));
        return true;
    }

    private static boolean executeCleanse(ServerPlayer player, ServerLevel level, int spellLevel,
            float powerMultiplier) {
        player.removeAllEffects();
        return true;
    }

    private static boolean executeBreathe(ServerPlayer player, ServerLevel level, int spellLevel,
            float powerMultiplier) {
        player.addEffect(new MobEffectInstance(MobEffects.WATER_BREATHING, spellLevel * 600, 0));
        return true;
    }

    private static boolean executeFertilize(ServerPlayer player, ServerLevel level, int spellLevel,
            float powerMultiplier) {
        HitResult hit = player.pick(6.0, 0.0f, false);
        if (hit.getType() == HitResult.Type.BLOCK) {
            BlockPos targetPos = ((BlockHitResult) hit).getBlockPos();
            if (consumeItem(player, new ItemStack(Items.BONE_MEAL))) {
                BoneMealItem.growCrop(new ItemStack(Items.BONE_MEAL), level, targetPos);
                level.levelEvent(2005, targetPos, 0);
                return true;
            }
        }
        return false;
    }

    private static boolean executeLight(ServerPlayer player, ServerLevel level, int spellLevel, float powerMultiplier) {
        player.addEffect(new MobEffectInstance(MobEffects.NIGHT_VISION, spellLevel * 600, 0));
        return true;
    }

    private static boolean executeBreaker(ServerPlayer player, ServerLevel level, int spellLevel,
            float powerMultiplier) {
        HitResult hit = player.pick(6.0, 0.0f, false);
        if (hit.getType() == HitResult.Type.BLOCK) {
            BlockPos targetPos = ((BlockHitResult) hit).getBlockPos();
            if (level.getBlockState(targetPos).getDestroySpeed(level, targetPos) >= 0) {
                level.destroyBlock(targetPos, true);
                return true;
            }
        }
        return false;
    }

    private static boolean executeWard(ServerPlayer player, ServerLevel level, int spellLevel, float powerMultiplier) {
        player.addEffect(new MobEffectInstance(MobEffects.ABSORPTION, spellLevel * 600, spellLevel - 1));
        return true;
    }

    private static boolean executeStonebind(ServerPlayer player, ServerLevel level, int spellLevel,
            float powerMultiplier) {
        HitResult hit = player.pick(12.0, 0.0f, false);
        if (hit.getType() == HitResult.Type.BLOCK) {
            BlockPos targetPos = ((BlockHitResult) hit).getBlockPos();
            if (level.getBlockState(targetPos).is(Blocks.LAVA)) {
                level.setBlockAndUpdate(targetPos, Blocks.OBSIDIAN.defaultBlockState());
                return true;
            }
        }
        return false;
    }

    private static boolean executeReach(ServerPlayer player, ServerLevel level, int spellLevel, float powerMultiplier) {
        HitResult hit = player.pick(20.0, 0.0f, false);
        if (hit.getType() != HitResult.Type.MISS) {
            player.teleportTo(hit.getLocation().x, hit.getLocation().y + 1, hit.getLocation().z);
            return true;
        }
        return false;
    }

    private static boolean executeSilence(ServerPlayer player, ServerLevel level, int spellLevel,
            float powerMultiplier) {
        HitResult hit = player.pick(12.0, 0.0f, false);
        if (hit.getType() == HitResult.Type.ENTITY) {
            Entity target = ((EntityHitResult) hit).getEntity();
            if (target instanceof LivingEntity le) {
                le.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, spellLevel * 200, spellLevel - 1));
                return true;
            }
        }
        return false;
    }

    private static boolean executeHeartstone(ServerPlayer player, ServerLevel level, int spellLevel,
            float powerMultiplier) {
        BlockPos spawn = player.getRespawnPosition();
        if (spawn != null) {
            ServerLevel targetLevel = player.server.getLevel(player.getRespawnDimension());
            if (targetLevel != null) {
                player.teleportTo(targetLevel, spawn.getX(), spawn.getY(), spawn.getZ(), player.getYRot(),
                        player.getXRot());
                return true;
            }
        }
        // Fallback to world spawn
        BlockPos worldSpawn = level.getSharedSpawnPos();
        player.teleportTo(level, worldSpawn.getX(), worldSpawn.getY(), worldSpawn.getZ(), player.getYRot(),
                player.getXRot());
        return true;
    }

    private static boolean executeWolves(ServerPlayer player, ServerLevel level, int spellLevel,
            float powerMultiplier) {
        int wolvesToSpawn = 3;
        boolean spawnedAny = false;
        for (int i = 0; i < wolvesToSpawn; i++) {
            Wolf wolf = EntityType.WOLF.create(level);
            if (wolf != null) {
                wolf.moveTo(player.getX(), player.getY(), player.getZ(), 0.0F, 0.0F);
                wolf.tame(player);
                if (player.getLastHurtMob() != null) {
                    wolf.setTarget(player.getLastHurtMob());
                }
                level.addFreshEntity(wolf);
                spawnedAny = true;
            }
        }
        return spawnedAny;
    }

    private static boolean consumeItem(ServerPlayer player, ItemStack stack) {
        int amount = 1;
        int found = 0;
        for (ItemStack is : player.getInventory().items) {
            if (is.is(stack.getItem()))
                found += is.getCount();
        }
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
