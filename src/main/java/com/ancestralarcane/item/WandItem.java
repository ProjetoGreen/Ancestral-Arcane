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
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import com.ancestralarcane.registry.AncestralArcaneBlocks;
import net.minecraft.core.BlockPos;

import java.util.List;

public class WandItem extends Item {
    private final boolean isLeatherGrip;

    public WandItem(Properties properties, boolean isLeatherGrip) {
        super(properties);
        this.isLeatherGrip = isLeatherGrip;
    }

    public boolean isLeatherGrip() {
        return isLeatherGrip;
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
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        BlockPos pos = context.getClickedPos();
        Player player = context.getPlayer();
        if (player == null)
            return InteractionResult.PASS;

        if (level.getBlockState(pos).is(AncestralArcaneBlocks.HOME_ANCHOR.get())) {
            ItemStack stack = context.getItemInHand();
            CompoundTag data = CustomDataUtil.getAncestralArcaneData(stack);
            data.putLong("linked_pos", pos.asLong());
            data.putString("linked_dim", level.dimension().location().toString());
            CustomDataUtil.setAncestralArcaneData(stack, data);

            if (level.isClientSide) {
                player.displayClientMessage(Component.literal("Wand linked to Home Anchor!"), true);
                for (int i = 0; i < 20; i++) {
                    level.addParticle(net.minecraft.core.particles.ParticleTypes.PORTAL, 
                        pos.getX() + 0.5 + (level.random.nextDouble() - 0.5) * 1.5,
                        pos.getY() + 1.0 + level.random.nextDouble() * 1.0,
                        pos.getZ() + 0.5 + (level.random.nextDouble() - 0.5) * 1.5,
                        0, 0.1, 0);
                }
            } else {
                level.playSound(null, pos, SoundEvents.BEACON_ACTIVATE, SoundSource.BLOCKS, 1.0f, 1.0f);
            }
            return InteractionResult.SUCCESS;
        }
        return super.useOn(context);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        CompoundTag data = CustomDataUtil.getAncestralArcaneData(stack);
        
        // Rapid Swapping (Fragment of All Knowledge)
        if (player.isShiftKeyDown()) {
            boolean hasFragment = player.getInventory().contains(new ItemStack(com.ancestralarcane.registry.AncestralArcaneItems.FRAGMENT_OF_ALL_KNOWLEDGE.get()));
            if (hasFragment) {
                ItemStack offhand = player.getOffhandItem();
                if (offhand.getItem() instanceof com.ancestralarcane.item.RuneItem) {
                    CompoundTag wandRune = data.getCompound("rune").copy();
                    CompoundTag offhandRune = CustomDataUtil.getAncestralArcaneData(offhand).getCompound("rune").copy();
                    
                    data.put("rune", offhandRune);
                    CustomDataUtil.setAncestralArcaneData(stack, data);
                    
                    CompoundTag newOffhandData = CustomDataUtil.getAncestralArcaneData(offhand);
                    newOffhandData.put("rune", wandRune);
                    CustomDataUtil.setAncestralArcaneData(offhand, newOffhandData);
                    
                    if (level.isClientSide) {
                        player.displayClientMessage(Component.literal("Runes Swapped!"), true);
                    } else {
                        level.playSound(null, player.blockPosition(), SoundEvents.END_PORTAL_FRAME_FILL, SoundSource.PLAYERS, 1.0f, 1.0f);
                    }
                    return InteractionResultHolder.success(stack);
                }
            }
        }

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
    public void onUseTick(Level level, LivingEntity entity, ItemStack stack, int timeLeft) {
        if (!(entity instanceof Player player)) return;
        
        int useDuration = this.getUseDuration(stack, entity) - timeLeft;
        CompoundTag data = CustomDataUtil.getAncestralArcaneData(stack);
        if (!data.contains("rune")) return;

        CompoundTag rune = data.getCompound("rune");
        String spellStr = rune.getString("spell");
        SpellType spell = SpellType.fromId(spellStr);
        if (spell == null) return;

        String catalyst = data.getString("catalyst");
        int baseCastTime = data.contains("cast_time_base") ? data.getInt("cast_time_base") : 20;

        String upgradeType = null;
        int upgradeLevel = 0;
        if (rune.contains("upgrade")) {
            CompoundTag upg = rune.getCompound("upgrade");
            upgradeType = upg.getString("type");
            upgradeLevel = upg.getInt("level");
        }

        float effectiveTicks = CastResolver.getEffectiveCastTimeTicks(catalyst, baseCastTime, upgradeType, upgradeLevel, spell);
        if (isLeatherGrip) {
            effectiveTicks *= 0.9f;
        }

        float progress = Math.min(1.0f, useDuration / effectiveTicks);
        
        // Affinity Particles
        if (progress >= 1.0f && CastResolver.hasAffinity(catalyst, spell)) {
            if (level.isClientSide && level.random.nextFloat() < 0.2f) {
                level.addParticle(CastResolver.getSpellParticle(spell), 
                    player.getX() + (level.random.nextDouble() - 0.5) * 0.5,
                    player.getY() + 1.2 + (level.random.nextDouble() - 0.5) * 0.5,
                    player.getZ() + (level.random.nextDouble() - 0.5) * 0.5,
                    0, 0, 0);
            }
        }

        // Auto-Fizzle Logic
        int gracePeriod = 20; // 1 second
        if (useDuration > (effectiveTicks + gracePeriod) && spell != SpellType.HEARTSTONE) {
            int charges = rune.getInt("charges");
            int dirty = rune.getInt("dirty");
            
            charges = Math.max(0, charges - 2);
            dirty += 2;
            
            rune.putInt("charges", charges);
            rune.putInt("dirty", dirty);
            data.put("rune", rune);
            CustomDataUtil.setAncestralArcaneData(stack, data);
            
            player.stopUsingItem();
            player.getCooldowns().addCooldown(this, 40);
            
            if (!level.isClientSide) {
                level.playSound(null, player.getX(), player.getY(), player.getZ(),
                        SoundEvents.FIRE_EXTINGUISH, SoundSource.PLAYERS, 1.0f, 0.5f);
            }
        }
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

        float effectiveTicks = CastResolver.getEffectiveCastTimeTicks(catalyst, baseCastTime, upgradeType, upgradeLevel, spell);
        if (isLeatherGrip) {
            effectiveTicks *= 0.9f; // 10% faster cast
        }
        
        float progress = Math.min(1.0f, useDuration / effectiveTicks);
        int idx = CastResolver.getCastIndex(progress);

        // Stage D (Peak) Hold/Collapse Logic
        boolean collapsed = false;
        int gracePeriod = 20; // 1 second window at Stage D
        if (useDuration > (effectiveTicks + gracePeriod)) {
            // Heartstone auto-triggers at the end of the window instead of collapsing
            if (spell == SpellType.HEARTSTONE) {
                progress = 1.0f;
                idx = 2; // Maximum stage
            } else {
                collapsed = true;
            }
        }

        int charges = rune.getInt("charges");
        int dirty = rune.getInt("dirty");

        if (collapsed) {
            // Fizzle penalty: consume 2 charges and add 2 dirty
            charges -= 2;
            dirty += 2;
        } else if (progress < 0.60f) {
            charges -= 2;
            dirty += 2;
        } else {
            // Affinity Bonus: -20% charge cost and dirty gain
            float costMultiplier = CastResolver.hasAffinity(catalyst, spell) ? 0.8f : 1.0f;
            
            // Special case: Summon Wolves with Raw Beef costs only 1 charge total
            if (spell == SpellType.WOLVES && player.getOffhandItem().is(Items.BEEF)) {
                charges -= 1;
                dirty += 1;
            } else if (spell == SpellType.WOLVES) {
                // Normal wolves cost: 1 per wolf (spellLevel)
                int cost = Math.max(1, Math.round(rune.getInt("lvl") * costMultiplier));
                charges -= cost;
                dirty += cost;
            } else {
                int cost = Math.max(1, Math.round(1 * costMultiplier));
                charges -= cost;
                dirty += cost;
            }
        }

        rune.putInt("charges", charges);
        rune.putInt("dirty", dirty);
        data.put("rune", rune);
        CustomDataUtil.setAncestralArcaneData(stack, data);

        if (!level.isClientSide) {
            if (collapsed) {
                player.level().playSound(null, player.getX(), player.getY(), player.getZ(),
                        SoundEvents.FIRE_EXTINGUISH, SoundSource.PLAYERS, 1.0f, 0.5f);
                // No spell effect on collapse
            } else {
                int effectiveChannel = CastResolver.getEffectiveChannel(catalyst, spell);
                float powerMultiplier = CastResolver
                        .getPowerMultiplier(idx == 0 ? 50 : idx == 1 ? 80 : idx == 2 ? 100 : 150, effectiveChannel);

                boolean success = SpellExecutor.execute((ServerPlayer) player, spell, rune.getInt("lvl"), powerMultiplier);

                if (success) {
                    int cd = getSpellCooldown(spell);
                    player.getCooldowns().addCooldown(this, cd);
                    player.level().playSound(null, player.getX(), player.getY(), player.getZ(),
                            SoundEvents.ILLUSIONER_PREPARE_BLINDNESS, SoundSource.PLAYERS, 1.0f, 1.0f);
                }
            }

            degradeRune((ServerPlayer) player, stack, data, rune, catalyst);
            
            // Consume wand durability
            stack.hurtAndBreak(1, player, player.getUsedItemHand() == InteractionHand.MAIN_HAND ? net.minecraft.world.entity.EquipmentSlot.MAINHAND : net.minecraft.world.entity.EquipmentSlot.OFFHAND);
        }
    }

    private void degradeRune(ServerPlayer player, ItemStack stack, CompoundTag data, CompoundTag rune,
            String catalyst) {
        int runeTier = rune.getInt("tier");
        int wandTier = CastResolver.getWandTier(catalyst);
        float wearAmount = 1.0f;

        // Special Rule: Heartstone costs only 0.5 wear
        String spellStr = rune.getString("spell");
        if (SpellType.HEARTSTONE.getId().equals(spellStr)) {
            wearAmount = 0.5f;
        } else {
            // Wiki Wear Rules:
            // - Wand tier >= Rune tier + 2: 0.5 wear
            // - Wand tier == Rune tier OR Rune tier + 1: 1 wear
            // - Wand tier < Rune tier: 2 wear
            if (wandTier >= runeTier + 2) {
                wearAmount = 0.5f;
            } else if (wandTier < runeTier) {
                wearAmount = 2.0f;
            }
        }

        float currentWear = rune.contains("wear") ? rune.getFloat("wear") : 0f;
        currentWear += wearAmount;

        // Wiki Durability Targets:
        // I: 3, II: 6, III: 9, IV: 12, V: 15
        int maxWear = switch (runeTier) {
            case 2 -> 6;
            case 3 -> 9;
            case 4 -> 12;
            case 5 -> 15;
            default -> 3;
        };
        
        if (isLeatherGrip) {
            maxWear += 1; // +1 reuse bonus
        }

        if (currentWear >= maxWear) {
            // Decay one tier
            int nextTier = runeTier - 1;
            if (nextTier <= 0) {
                data.remove("rune");
            } else {
                rune.putInt("tier", nextTier);
                rune.putInt("lvl", nextTier);
                rune.putInt("charges", nextTier * 10);
                rune.putFloat("wear", 0f);
                data.put("rune", rune);
            }
        } else {
            rune.putFloat("wear", currentWear);
            data.put("rune", rune);
        }

        CustomDataUtil.setAncestralArcaneData(stack, data);
    }

    private int getSpellCooldown(SpellType spell) {
        return switch (spell) {
            case FIRE -> 20;
            case FIRE_FRIEND -> 20;
            case STORM -> 100;
            case FROST -> 20;
            case FROST_WALKER -> 40;
            case HEAL -> 60;
            case MEND -> 60;
            case STABILIZE -> 40;
            case CLEANSE -> 40;
            case BREATHE -> 40;
            case FERTILIZE -> 100;
            case LIGHT -> 20;
            case BREAKER -> 80;
            case WARD -> 100;
            case STONEBIND -> 40;
            case REACH -> 80;
            case SILENCE -> 100;
            case HEARTSTONE -> 2400;
            case WOLVES -> 600;
        };
    }

    private String toRoman(int number) {
        return switch (number) {
            case 1 -> "I";
            case 2 -> "II";
            case 3 -> "III";
            case 4 -> "IV";
            case 5 -> "V";
            default -> number > 0 ? String.valueOf(number) : "";
        };
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents,
            TooltipFlag tooltipFlag) {
        CompoundTag data = CustomDataUtil.getAncestralArcaneData(stack);
        if (data.contains("catalyst")) {
            tooltipComponents.add(Component.translatable("tooltip.ancestral_arcane.catalyst", data.getString("catalyst")));
        }
        if (data.contains("rune")) {
            CompoundTag rune = data.getCompound("rune");
            tooltipComponents.add(Component.translatable("tooltip.ancestral_arcane.spell", rune.getString("spell")));
            tooltipComponents.add(Component.translatable("tooltip.ancestral_arcane.tier", toRoman(rune.getInt("tier"))));
            tooltipComponents.add(Component.translatable("tooltip.ancestral_arcane.level", rune.getInt("lvl")));
            tooltipComponents.add(Component.translatable("tooltip.ancestral_arcane.charges", rune.getInt("charges"), (rune.getInt("lvl") * 10)));
        } else {
            tooltipComponents.add(Component.translatable("tooltip.ancestral_arcane.no_rune"));
        }
    }
}
