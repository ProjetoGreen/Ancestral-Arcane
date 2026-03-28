package com.ancestralarcane.ui.menu;

import com.ancestralarcane.block.ArcaneSmithingTableBlockEntity;
import com.ancestralarcane.data.CustomDataUtil;
import com.ancestralarcane.registry.AncestralArcaneItems;
import com.ancestralarcane.registry.AncestralArcaneMenus;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.network.chat.Component;
import net.neoforged.neoforge.items.SlotItemHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Objects;

public class ArcaneSmithingMenu extends AbstractContainerMenu {
    private final ArcaneSmithingTableBlockEntity blockEntity;

    public ArcaneSmithingMenu(int containerId, Inventory playerInventory, RegistryFriendlyByteBuf extraData) {
        this(containerId, playerInventory, findBlockEntity(playerInventory, extraData));
    }

    private static ArcaneSmithingTableBlockEntity findBlockEntity(Inventory playerInventory,
            RegistryFriendlyByteBuf extraData) {
        BlockPos pos = extraData.readBlockPos();
        var entity = playerInventory.player.level().getBlockEntity(pos);
        if (entity instanceof ArcaneSmithingTableBlockEntity table) {
            return table;
        }
        throw new IllegalStateException("Block entity is not an ArcaneSmithingTableBlockEntity at " + pos);
    }

    public ArcaneSmithingMenu(int containerId, Inventory playerInventory, ArcaneSmithingTableBlockEntity entity) {
        super(AncestralArcaneMenus.ARCANE_SMITHING.get(), containerId);
        this.blockEntity = Objects.requireNonNull(entity);

        // Table Slots
        this.addSlot(new SlotItemHandler(entity.inventory, 0, 8, 48) {
            @Override
            public void setChanged() {
                super.setChanged();
                ArcaneSmithingMenu.this.createResult();
            }
        }); // Base/Input 1
        this.addSlot(new SlotItemHandler(entity.inventory, 1, 26, 48) {
            @Override
            public void setChanged() {
                super.setChanged();
                ArcaneSmithingMenu.this.createResult();
            }
        }); // Addition/Input 2
        this.addSlot(new SlotItemHandler(entity.inventory, 2, 44, 48) {
            @Override
            public void setChanged() {
                super.setChanged();
                ArcaneSmithingMenu.this.createResult();
            }
        }); // Material/Input 3

        this.addSlot(new SlotItemHandler(entity.inventory, 3, 98, 48) {
            @Override
            public boolean mayPlace(@Nonnull ItemStack stack) {
                return false;
            }

            @Override
            public void onTake(@Nonnull Player player, @Nonnull ItemStack stack) {
                ItemStack input1 = blockEntity.inventory.getStackInSlot(0);
                ItemStack input2 = blockEntity.inventory.getStackInSlot(1);
                ItemStack input3 = blockEntity.inventory.getStackInSlot(2);
                int lvl = blockEntity.getTableLevel();

                // 1. Upgrade Table
                if (lvl < 5 && input1.isEmpty() && stack.is(AncestralArcaneItems.ARCANE_SMITHING_TABLE.get())) {
                    if (lvl == 1 && input2.is(Items.AMETHYST_SHARD) && input3.is(Items.COPPER_INGOT)) {
                        input2.shrink(2);
                        input3.shrink(4);
                        blockEntity.setTableLevel(2);
                        stack.shrink(1);
                    } else if (lvl == 2 && input2.is(Items.ECHO_SHARD) && input3.is(Items.IRON_INGOT)) {
                        input2.shrink(2);
                        input3.shrink(4);
                        blockEntity.setTableLevel(3);
                        stack.shrink(1);
                    } else if (lvl == 3 && input2.is(Items.CHORUS_FLOWER) && input3.is(Items.GOLD_INGOT)) {
                        input2.shrink(1);
                        input3.shrink(4);
                        blockEntity.setTableLevel(4);
                        stack.shrink(1);
                    } else if (lvl == 4 && input2.is(Items.NETHERITE_SCRAP) && input3.is(Items.ECHO_SHARD)) {
                        input2.shrink(2);
                        input3.shrink(2);
                        blockEntity.setTableLevel(5);
                        stack.shrink(1);
                    }
                }
                
                // 2. Inscribe Scroll -> Grimoire
                processScrollToGrimoireOnTake(input1, input2, input3);

                // 3. Inscribe Grimoire -> Rune
                processGrimoireToRuneOnTake(player, input1, input2, input3);

                // 4. Upgrade Base Rune
                if (input1.getItem() instanceof com.ancestralarcane.item.RuneItem && input2.isEmpty() && !input3.isEmpty()) {
                    input1.shrink(1);
                    input3.shrink(1);
                }
                
                // 5. Binding (Rune -> Wand)
                if (input1.getItem() instanceof com.ancestralarcane.item.WandItem
                        && input2.getItem() instanceof com.ancestralarcane.item.RuneItem && input3.isEmpty()) {
                    input1.shrink(1);
                    input2.shrink(1);
                }
                
                // 6. Unbinding (Wand -> Rune)
                processUnbindingOnTake(player, input1, input2, input3);

                // 7. Reactivate Forgotten Magic Book
                processForgottenBookOnTake(input1, input2, input3);

                // 8. Leather Grip Upgrade
                if (input1.getItem() instanceof com.ancestralarcane.item.WandItem && input2.is(Items.LEATHER) && input3.is(Items.STRING)) {
                    input1.shrink(1);
                    input2.shrink(1);
                    input3.shrink(1);
                }

                // 9. Socketed Grimoire
                if (input1.getItem() instanceof com.ancestralarcane.item.WandItem && 
                    Objects.requireNonNull(input1.getDescriptionId()).contains("netherite") && 
                    input2.is(AncestralArcaneItems.GRIMOIRE_T5.get()) && input3.isEmpty()) {
                    input1.shrink(1);
                    input2.shrink(1);
                }

                super.onTake(player, stack);
            }
        }); // Output

        // Player Inventory
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 9; ++j) {
                this.addSlot(new Slot(playerInventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }
        for (int k = 0; k < 9; ++k) {
            this.addSlot(new Slot(playerInventory, k, 8 + k * 18, 142));
        }
    }

    public ArcaneSmithingTableBlockEntity getBlockEntity() {
        return blockEntity;
    }

    private void processScrollToGrimoireOnTake(ItemStack input1, ItemStack input2, ItemStack input3) {
        if (input3.isEmpty()) {
            if (isGrimoire(input1) && isScroll(input2)) {
                input1.shrink(1);
                input2.shrink(1);
            } else if (isGrimoire(input2) && isScroll(input1)) {
                input1.shrink(1);
                input2.shrink(1);
            }
        }
    }

    private void processGrimoireToRuneOnTake(Player player, ItemStack input1, ItemStack input2, ItemStack input3) {
        if (!input3.isEmpty()) return;
        
        ItemStack runeSlot = ItemStack.EMPTY;
        ItemStack grimoireSlot = ItemStack.EMPTY;

        if (input1.getItem() instanceof com.ancestralarcane.item.RuneItem && isGrimoire(input2)) {
            runeSlot = input1;
            grimoireSlot = input2;
        } else if (input2.getItem() instanceof com.ancestralarcane.item.RuneItem && isGrimoire(input1)) {
            runeSlot = input2;
            grimoireSlot = input1;
        }

        if (!runeSlot.isEmpty() && !grimoireSlot.isEmpty()) {
            CompoundTag gd = CustomDataUtil.getAncestralArcaneData(grimoireSlot);
            CompoundTag gcomp = gd.getCompound("grimoire");
            int tier = gcomp.contains("tier") ? gcomp.getInt("tier") : 1;
            int uses = gcomp.contains("uses") ? gcomp.getInt("uses") : (tier == 5 ? 15 : tier == 4 ? 12 : tier == 3 ? 9 : tier == 2 ? 6 : 3);
            uses--;

            if (uses <= 0) {
                String spellStr = gcomp.getString("spell");
                if (spellStr.isEmpty() && grimoireSlot.getItem() instanceof com.ancestralarcane.item.GrimoireSpellItem gsi) {
                    spellStr = gsi.getSpellName();
                }
                
                if (tier <= 1) {
                    ItemStack glowstone = new ItemStack(Items.GLOWSTONE_DUST, 8);
                    grimoireSlot.shrink(1);
                    if (!player.getInventory().add(glowstone)) {
                        player.drop(glowstone, false);
                    }
                } else {
                    ItemStack forgotten = new ItemStack(AncestralArcaneItems.FORGOTTEN_MAGICBOOK.get());
                    CompoundTag newGcomp = new CompoundTag();
                    newGcomp.putInt("tier", tier - 1);
                    newGcomp.putString("spell", spellStr);
                    CompoundTag forgetData = new CompoundTag();
                    forgetData.put("grimoire", newGcomp);
                    CustomDataUtil.setAncestralArcaneData(forgotten, forgetData);
                    grimoireSlot.shrink(1);
                    if (!player.getInventory().add(forgotten)) {
                        player.drop(forgotten, false);
                    }
                }
            } else {
                gcomp.putInt("uses", uses);
                gd.put("grimoire", gcomp);
                CustomDataUtil.setAncestralArcaneData(grimoireSlot, gd);
            }
            runeSlot.shrink(1);
        }
    }

    private void processUnbindingOnTake(Player player, ItemStack input1, ItemStack input2, ItemStack input3) {
        if (input1.getItem() instanceof com.ancestralarcane.item.WandItem && input2.isEmpty() && input3.isEmpty()) {
            CompoundTag wandData = CustomDataUtil.getAncestralArcaneData(input1);
            ListTag runes = wandData.getList("runes", Tag.TAG_COMPOUND);
            int activeSlot = wandData.getInt("active_slot");
            
            if (activeSlot < runes.size()) {
                CompoundTag runeDataBody = runes.getCompound(activeSlot).copy();
                runes.remove(activeSlot);
                wandData.put("runes", runes);
                
                if (activeSlot >= runes.size() && !runes.isEmpty()) {
                    wandData.putInt("active_slot", runes.size() - 1);
                } else if (runes.isEmpty()) {
                    wandData.putInt("active_slot", 0);
                }
                
                input1.shrink(1);
                ItemStack outRune = new ItemStack(AncestralArcaneItems.RUNE.get());
                CompoundTag rd = new CompoundTag();
                rd.put("rune", runeDataBody);
                CustomDataUtil.setAncestralArcaneData(outRune, rd);
                if (!player.getInventory().add(outRune)) {
                    player.drop(outRune, false);
                }
            }
        }
    }

    private void processForgottenBookOnTake(ItemStack input1, ItemStack input2, ItemStack input3) {
        if (input1.is(AncestralArcaneItems.FORGOTTEN_MAGICBOOK.get()) && input2.isEmpty() && input3.is(Items.GLOWSTONE_DUST)) {
            CompoundTag dt = CustomDataUtil.getAncestralArcaneData(input1);
            if (dt.contains("grimoire")) {
                int tier = Math.max(1, dt.getCompound("grimoire").getInt("tier"));
                int cost = tier == 1 ? 8 : tier * 16;
                input1.shrink(1);
                input3.shrink(cost);
            }
        }
    }

    @Nonnull
    @Override
    public ItemStack quickMoveStack(@Nonnull Player player, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);
        if (slot.hasItem()) {
            ItemStack stackInSlot = slot.getItem();
            itemstack = stackInSlot.copy();

            if (index == 3) {
                if (!this.moveItemStackTo(stackInSlot, 4, 40, true)) {
                    return ItemStack.EMPTY;
                }
                slot.onQuickCraft(stackInSlot, itemstack);
            } else if (index != 0 && index != 1 && index != 2) {
                if (!this.moveItemStackTo(stackInSlot, 0, 3, false)) {
                    if (index >= 4 && index < 31) {
                        if (!this.moveItemStackTo(stackInSlot, 31, 40, false)) {
                            return ItemStack.EMPTY;
                        }
                    } else if (index >= 31 && index < 40 && !this.moveItemStackTo(stackInSlot, 4, 31, false)) {
                        return ItemStack.EMPTY;
                    }
                }
            } else if (!this.moveItemStackTo(stackInSlot, 4, 40, false)) {
                return ItemStack.EMPTY;
            }

            if (stackInSlot.isEmpty()) {
                slot.setByPlayer(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }

            if (stackInSlot.getCount() == itemstack.getCount()) {
                return ItemStack.EMPTY;
            }

            slot.onTake(player, stackInSlot);
        }

        return itemstack;
    }

    @Override
    public boolean stillValid(@Nonnull Player player) {
        if (blockEntity.getLevel() == null) return false;
        return stillValid(
                net.minecraft.world.inventory.ContainerLevelAccess.create(blockEntity.getLevel(),
                        blockEntity.getBlockPos()),
                player, com.ancestralarcane.registry.AncestralArcaneBlocks.ARCANE_SMITHING_TABLE.get());
    }

    @Override
    public void slotsChanged(@Nonnull net.minecraft.world.Container inventory) {
        super.slotsChanged(inventory);
        this.createResult();
    }

    private void createResult() {
        if (blockEntity.getLevel() == null || blockEntity.getLevel().isClientSide)
            return;

        ItemStack input1 = blockEntity.inventory.getStackInSlot(0);
        ItemStack input2 = blockEntity.inventory.getStackInSlot(1);
        ItemStack input3 = blockEntity.inventory.getStackInSlot(2);

        ItemStack result = ItemStack.EMPTY;

        // 1. Upgrade Table Level
        result = processTableUpgradePreview(input1, input2, input3);

        // 2. Inscribing (Scroll -> Grimoire)
        if (result.isEmpty()) result = processScrollToGrimoirePreview(input1, input2, input3);

        // 3. Inscribing (Grimoire -> Rune)
        if (result.isEmpty()) result = processGrimoireToRunePreview(input1, input2, input3);

        // 4. Upgrading Base Rune
        if (result.isEmpty()) result = processRuneUpgradePreview(input1, input2, input3);

        // 5. Binding (Rune -> Wand)
        if (result.isEmpty()) result = processBindingPreview(input1, input2, input3);

        // 6. Unbinding (Wand -> Rune)
        if (result.isEmpty()) result = processUnbindingPreview(input1, input2, input3);

        // 7. Reactivate Forgotten Magic Book
        if (result.isEmpty()) result = processForgottenBookPreview(input1, input2, input3);

        // 8. Leather Grip Upgrade
        if (result.isEmpty()) result = processLeatherGripPreview(input1, input2, input3);

        // 9. Socketed Grimoire Upgrade
        if (result.isEmpty()) result = processSocketedGrimoirePreview(input1, input2, input3);

        blockEntity.inventory.setStackInSlot(3, result);
    }

    private ItemStack processTableUpgradePreview(ItemStack input1, ItemStack input2, ItemStack input3) {
        int lvl = blockEntity.getTableLevel();
        if (lvl < 5 && input1.isEmpty()) {
            boolean canUpgrade = false;
            if (lvl == 1 && input2.is(Items.AMETHYST_SHARD) && input2.getCount() >= 2 && input3.is(Items.COPPER_INGOT) && input3.getCount() >= 4) canUpgrade = true;
            else if (lvl == 2 && input2.is(Items.ECHO_SHARD) && input2.getCount() >= 2 && input3.is(Items.IRON_INGOT) && input3.getCount() >= 4) canUpgrade = true;
            else if (lvl == 3 && input2.is(Items.CHORUS_FLOWER) && input2.getCount() >= 1 && input3.is(Items.GOLD_INGOT) && input3.getCount() >= 4) canUpgrade = true;
            else if (lvl == 4 && input2.is(Items.NETHERITE_SCRAP) && input2.getCount() >= 2 && input3.is(Items.ECHO_SHARD) && input3.getCount() >= 2) canUpgrade = true;

            if (canUpgrade) {
                ItemStack token = new ItemStack(AncestralArcaneItems.ARCANE_SMITHING_TABLE.get());
                token.set(net.minecraft.core.component.DataComponents.CUSTOM_NAME, Component.literal("Click to Upgrade Table to Lvl " + (lvl + 1)));
                return token;
            }
        }
        return ItemStack.EMPTY;
    }

    private ItemStack processScrollToGrimoirePreview(ItemStack input1, ItemStack input2, ItemStack input3) {
        if (!input3.isEmpty()) return ItemStack.EMPTY;
        
        ItemStack grimoireSlot = ItemStack.EMPTY;
        ItemStack scrollSlot = ItemStack.EMPTY;

        if (isGrimoire(input1) && isScroll(input2)) {
            grimoireSlot = input1;
            scrollSlot = input2;
        } else if (isGrimoire(input2) && isScroll(input1)) {
            grimoireSlot = input2;
            scrollSlot = input1;
        }

        if (!grimoireSlot.isEmpty() && !scrollSlot.isEmpty()) {
            CompoundTag dt = CustomDataUtil.getAncestralArcaneData(grimoireSlot);
            CompoundTag grim = dt.contains("grimoire") ? dt.getCompound("grimoire").copy() : new CompoundTag();
            if (!grim.contains("spell") || grim.getString("spell").isEmpty()) {
                String spell = determineSpellFromScroll(scrollSlot);
                if (spell != null) {
                    int outputTier = getGrimoireTier(grimoireSlot);
                    grim.putString("spell", spell);
                    grim.putInt("tier", outputTier);
                    grim.putInt("uses", outputTier == 5 ? 15 : outputTier == 4 ? 12 : outputTier == 3 ? 9 : outputTier == 2 ? 6 : 3);
                    dt.put("grimoire", grim);
                    Item targetGrimoire = getGrimoireItemForSpell(spell);
                    ItemStack out = new ItemStack(targetGrimoire, 1);
                    CustomDataUtil.setAncestralArcaneData(out, dt);
                    return out;
                }
            }
        }
        return ItemStack.EMPTY;
    }

    private ItemStack processGrimoireToRunePreview(ItemStack input1, ItemStack input2, ItemStack input3) {
        if (!input3.isEmpty()) return ItemStack.EMPTY;
        
        ItemStack runeSlot = ItemStack.EMPTY;
        ItemStack grimoireSlot = ItemStack.EMPTY;

        if (input1.getItem() instanceof com.ancestralarcane.item.RuneItem && isGrimoire(input2)) {
            runeSlot = input1;
            grimoireSlot = input2;
        } else if (input2.getItem() instanceof com.ancestralarcane.item.RuneItem && isGrimoire(input1)) {
            runeSlot = input2;
            grimoireSlot = input1;
        }

        if (!runeSlot.isEmpty() && !grimoireSlot.isEmpty()) {
            CompoundTag runedt = CustomDataUtil.getAncestralArcaneData(runeSlot);
            CompoundTag grimdt = CustomDataUtil.getAncestralArcaneData(grimoireSlot);
            if (runedt.contains("rune")) {
                CompoundTag rune = runedt.getCompound("rune").copy();
                CompoundTag grim = grimdt.getCompound("grimoire");
                if (rune.getInt("crude") == 0 && rune.getInt("empty") == 1) {
                    String spell = grim.getString("spell");
                    if (spell.isEmpty() && grimoireSlot.getItem() instanceof com.ancestralarcane.item.GrimoireSpellItem gsi) {
                        spell = gsi.getSpellName();
                    }
                    int toolTier = Math.max(1, getGrimoireTier(grimoireSlot));
                    int runeTier = rune.getInt("tier");
                    if (spell != null && !spell.isEmpty() && runeTier >= toolTier) {
                        rune.putInt("empty", 0);
                        rune.putInt("lvl", toolTier);
                        rune.putString("spell", spell);
                        rune.putInt("charges", toolTier * 10);
                        rune.putInt("dirty", 0);
                        runedt.put("rune", rune);
                        ItemStack out = runeSlot.copy();
                        out.setCount(1);
                        CustomDataUtil.setAncestralArcaneData(out, runedt);
                        return out;
                    }
                }
            }
        }
        return ItemStack.EMPTY;
    }

    private ItemStack processRuneUpgradePreview(ItemStack input1, ItemStack input2, ItemStack input3) {
        if (input1.getItem() instanceof com.ancestralarcane.item.RuneItem && input2.isEmpty() && !input3.isEmpty()) {
            CompoundTag dt = CustomDataUtil.getAncestralArcaneData(input1);
            if (dt.contains("rune")) {
                CompoundTag rune = dt.getCompound("rune").copy();
                if (rune.getInt("crude") == 0 && rune.getInt("empty") == 1) {
                    int tier = rune.getInt("tier");
                    int maxUpg = Math.min(3, tier);
                    String upgType = null;
                    if (input3.is(Items.BLAZE_POWDER)) upgType = "blaze";
                    else if (input3.is(Items.QUARTZ)) upgType = "quartz";
                    else if (input3.is(Items.GHAST_TEAR)) upgType = "tear";

                    if (upgType != null) {
                        CompoundTag upg = rune.contains("upgrade") ? rune.getCompound("upgrade").copy() : new CompoundTag();
                        String curType = upg.getString("type");
                        int curLevel = upg.getInt("level");
                        if ((curType.isEmpty() || curType.equals(upgType)) && curLevel < maxUpg) {
                            upg.putString("type", upgType);
                            upg.putInt("level", curLevel + 1);
                            rune.put("upgrade", upg);
                            dt.put("rune", rune);
                            ItemStack out = input1.copy();
                            out.setCount(1);
                            CustomDataUtil.setAncestralArcaneData(out, dt);
                            return out;
                        }
                    }
                }
            }
        }
        return ItemStack.EMPTY;
    }

    private ItemStack processBindingPreview(ItemStack input1, ItemStack input2, ItemStack input3) {
        if (input1.getItem() instanceof com.ancestralarcane.item.WandItem && input2.getItem() instanceof com.ancestralarcane.item.RuneItem && input3.isEmpty()) {
            CompoundTag runeData = CustomDataUtil.getAncestralArcaneData(input2);
            if (runeData.contains("rune") && runeData.getCompound("rune").getInt("lvl") > 0) {
                ItemStack out = input1.copy();
                out.setCount(1);
                if (out.getItem() instanceof com.ancestralarcane.item.WandItem wand) {
                    wand.setActiveRune(out, runeData.getCompound("rune").copy());
                    return out;
                }
            }
        }
        return ItemStack.EMPTY;
    }

    private ItemStack processUnbindingPreview(ItemStack input1, ItemStack input2, ItemStack input3) {
        if (input1.getItem() instanceof com.ancestralarcane.item.WandItem && input2.isEmpty() && input3.isEmpty()) {
            CompoundTag wandData = CustomDataUtil.getAncestralArcaneData(input1);
            ListTag runes = wandData.getList("runes", Tag.TAG_COMPOUND);
            int activeSlot = wandData.getInt("active_slot");
            if (activeSlot < runes.size()) {
                CompoundTag runeDataBody = runes.getCompound(activeSlot);
                if (runeDataBody.contains("spell")) {
                    ItemStack out = input1.copy();
                    out.setCount(1);
                    CompoundTag previewData = wandData.copy();
                    ListTag previewRunes = previewData.getList("runes", Tag.TAG_COMPOUND).copy();
                    if (activeSlot < previewRunes.size()) previewRunes.remove(activeSlot);
                    previewData.put("runes", previewRunes);
                    CustomDataUtil.setAncestralArcaneData(out, previewData);
                    return out;
                }
            }
        }
        return ItemStack.EMPTY;
    }

    private ItemStack processForgottenBookPreview(ItemStack input1, ItemStack input2, ItemStack input3) {
        if (input1.is(AncestralArcaneItems.FORGOTTEN_MAGICBOOK.get()) && input2.isEmpty() && input3.is(Items.GLOWSTONE_DUST)) {
            CompoundTag dt = CustomDataUtil.getAncestralArcaneData(input1);
            int tier = dt.contains("grimoire") ? Math.max(1, dt.getCompound("grimoire").getInt("tier")) : 1;
            int cost = tier == 1 ? 8 : tier * 16;
            if (input3.getCount() >= cost) {
                Item grimItem = getEmptyGrimoireForTier(tier);
                ItemStack reactivated = new ItemStack(grimItem);
                CompoundTag newGcomp = new CompoundTag();
                newGcomp.putInt("tier", tier);
                newGcomp.putInt("uses", tier == 5 ? 15 : tier == 4 ? 12 : tier == 3 ? 9 : tier == 2 ? 6 : 3);
                CompoundTag newDt = new CompoundTag();
                newDt.put("grimoire", newGcomp);
                CustomDataUtil.setAncestralArcaneData(reactivated, newDt);
                return reactivated;
            }
        }
        return ItemStack.EMPTY;
    }

    private ItemStack processLeatherGripPreview(ItemStack input1, ItemStack input2, ItemStack input3) {
        if (input1.getItem() instanceof com.ancestralarcane.item.WandItem && input2.is(Items.LEATHER) && input3.is(Items.STRING)) {
            Item gripItem = getLeatherGripWand(input1.getItem());
            if (gripItem != null) {
                ItemStack out = new ItemStack(gripItem);
                out.set(net.minecraft.core.component.DataComponents.CUSTOM_DATA, input1.get(net.minecraft.core.component.DataComponents.CUSTOM_DATA));
                return out;
            }
        }
        return ItemStack.EMPTY;
    }

    private ItemStack processSocketedGrimoirePreview(ItemStack input1, ItemStack input2, ItemStack input3) {
        if (input1.getItem() instanceof com.ancestralarcane.item.WandItem && 
            Objects.requireNonNull(input1.getDescriptionId()).contains("netherite") && 
            input2.is(AncestralArcaneItems.GRIMOIRE_T5.get()) && input3.isEmpty()) {
            CompoundTag data = CustomDataUtil.getAncestralArcaneData(input1);
            if (!data.getBoolean("socketed_grimoire")) {
                ItemStack out = input1.copy();
                out.setCount(1);
                CompoundTag newData = data.copy();
                newData.putBoolean("socketed_grimoire", true);
                CustomDataUtil.setAncestralArcaneData(out, newData);
                return out;
            }
        }
        return ItemStack.EMPTY;
    }

    @Nullable
    private Item getLeatherGripWand(Item baseWand) {
        if (baseWand == AncestralArcaneItems.FLINT_WAND.get()) return AncestralArcaneItems.FLINT_WAND_LEATHER_GRIP.get();
        if (baseWand == AncestralArcaneItems.COPPER_WAND.get()) return AncestralArcaneItems.COPPER_WAND_LEATHER_GRIP.get();
        if (baseWand == AncestralArcaneItems.IRON_WAND.get()) return AncestralArcaneItems.IRON_WAND_LEATHER_GRIP.get();
        if (baseWand == AncestralArcaneItems.GOLDEN_WAND.get()) return AncestralArcaneItems.GOLDEN_WAND_LEATHER_GRIP.get();
        if (baseWand == AncestralArcaneItems.DIAMOND_WAND.get()) return AncestralArcaneItems.DIAMOND_WAND_LEATHER_GRIP.get();
        if (baseWand == AncestralArcaneItems.EMERALD_WAND.get()) return AncestralArcaneItems.EMERALD_WAND_LEATHER_GRIP.get();
        if (baseWand == AncestralArcaneItems.NETHERITE_WAND.get()) return AncestralArcaneItems.NETHERITE_WAND_LEATHER_GRIP.get();
        return null;
    }

    @Nonnull
    private Item getEmptyGrimoireForTier(int tier) {
        return switch (tier) {
            case 2 -> AncestralArcaneItems.GRIMOIRE_T2.get();
            case 3 -> AncestralArcaneItems.GRIMOIRE_T3.get();
            case 4 -> AncestralArcaneItems.GRIMOIRE_T4.get();
            case 5 -> AncestralArcaneItems.GRIMOIRE_T5.get();
            default -> AncestralArcaneItems.GRIMOIRE_T1.get();
        };
    }

    private boolean isGrimoire(ItemStack stack) {
        return stack.getItem() instanceof com.ancestralarcane.item.EmptyGrimoireItem ||
                stack.getItem() instanceof com.ancestralarcane.item.GrimoireSpellItem;
    }

    private boolean isScroll(ItemStack stack) {
        return determineSpellFromScroll(stack) != null;
    }

    private int getGrimoireTier(ItemStack stack) {
        CompoundTag data = CustomDataUtil.getAncestralArcaneData(stack);
        if (data.contains("grimoire")) {
            return data.getCompound("grimoire").getInt("tier");
        }
        if (stack.is(AncestralArcaneItems.GRIMOIRE_T1.get())) return 1;
        if (stack.is(AncestralArcaneItems.GRIMOIRE_T2.get())) return 2;
        if (stack.is(AncestralArcaneItems.GRIMOIRE_T3.get())) return 3;
        if (stack.is(AncestralArcaneItems.GRIMOIRE_T4.get())) return 4;
        if (stack.is(AncestralArcaneItems.GRIMOIRE_T5.get())) return 5;
        return 0;
    }

    @Nullable
    private String determineSpellFromScroll(ItemStack scroll) {
        if (scroll.is(AncestralArcaneItems.SCROLL_FIRE.get())) return "fire";
        if (scroll.is(AncestralArcaneItems.SCROLL_FIRE_FRIEND.get())) return "fire_friend";
        if (scroll.is(AncestralArcaneItems.SCROLL_STORM.get())) return "storm";
        if (scroll.is(AncestralArcaneItems.SCROLL_FROST.get())) return "frost";
        if (scroll.is(AncestralArcaneItems.SCROLL_FROST_WALKER.get())) return "frost_walker";
        if (scroll.is(AncestralArcaneItems.SCROLL_HEAL.get())) return "heal";
        if (scroll.is(AncestralArcaneItems.SCROLL_MEND.get())) return "mend";
        if (scroll.is(AncestralArcaneItems.SCROLL_STABILIZE.get())) return "stabilize";
        if (scroll.is(AncestralArcaneItems.SCROLL_CLEANSE.get())) return "cleanse";
        if (scroll.is(AncestralArcaneItems.SCROLL_BREATHE.get())) return "breathe";
        if (scroll.is(AncestralArcaneItems.SCROLL_FERTILIZE.get())) return "fertilize";
        if (scroll.is(AncestralArcaneItems.SCROLL_LIGHT.get())) return "light";
        if (scroll.is(AncestralArcaneItems.SCROLL_BREAKER.get())) return "breaker";
        if (scroll.is(AncestralArcaneItems.SCROLL_WARD.get())) return "ward";
        if (scroll.is(AncestralArcaneItems.SCROLL_STONEBIND.get())) return "stonebind";
        if (scroll.is(AncestralArcaneItems.SCROLL_REACH.get())) return "reach";
        if (scroll.is(AncestralArcaneItems.SCROLL_SILENCE.get())) return "silence";
        return null;
    }

    @Nonnull
    private Item getGrimoireItemForSpell(String spell) {
        return switch (spell) {
            case "fire" -> AncestralArcaneItems.GRIMOIRE_FIRE.get();
            case "fire_friend" -> AncestralArcaneItems.GRIMOIRE_FIRE_FRIEND.get();
            case "storm" -> AncestralArcaneItems.GRIMOIRE_STORM.get();
            case "frost" -> AncestralArcaneItems.GRIMOIRE_FROST.get();
            case "frost_walker" -> AncestralArcaneItems.GRIMOIRE_FROST_WALKER.get();
            case "heal" -> AncestralArcaneItems.GRIMOIRE_HEAL.get();
            case "mend" -> AncestralArcaneItems.GRIMOIRE_MEND.get();
            case "stabilize" -> AncestralArcaneItems.GRIMOIRE_STABILIZE.get();
            case "cleanse" -> AncestralArcaneItems.GRIMOIRE_CLEANSE.get();
            case "breathe" -> AncestralArcaneItems.GRIMOIRE_BREATHE.get();
            case "fertilize" -> AncestralArcaneItems.GRIMOIRE_FERTILIZE.get();
            case "light" -> AncestralArcaneItems.GRIMOIRE_LIGHT.get();
            case "breaker" -> AncestralArcaneItems.GRIMOIRE_BREAKER.get();
            case "ward" -> AncestralArcaneItems.GRIMOIRE_WARD.get();
            case "stonebind" -> AncestralArcaneItems.GRIMOIRE_STONEBIND.get();
            case "reach" -> AncestralArcaneItems.GRIMOIRE_REACH.get();
            case "silence" -> AncestralArcaneItems.GRIMOIRE_SILENCE.get();
            default -> AncestralArcaneItems.GRIMOIRE_T1.get();
        };
    }
}
