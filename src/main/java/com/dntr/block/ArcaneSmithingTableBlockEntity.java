package com.dntr.block;

import com.dntr.registry.DntrBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.items.ItemStackHandler;
import com.dntr.ui.menu.ArcaneSmithingMenu;

public class ArcaneSmithingTableBlockEntity extends BlockEntity implements MenuProvider {

    public final ItemStackHandler inventory = new ItemStackHandler(8) {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
        }
    };

    private int tableLevel = 1;

    public ArcaneSmithingTableBlockEntity(BlockPos pos, BlockState blockState) {
        super(DntrBlockEntities.ARCANE_SMITHING_TABLE.get(), pos, blockState);
    }

    public int getTableLevel() {
        return tableLevel;
    }

    public void setTableLevel(int level) {
        this.tableLevel = level;
        setChanged();
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        tag.put("inventory", inventory.serializeNBT(registries));
        tag.putInt("tableLevel", tableLevel);
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        if (tag.contains("inventory")) {
            inventory.deserializeNBT(registries, tag.getCompound("inventory"));
        }
        if (tag.contains("tableLevel")) {
            tableLevel = tag.getInt("tableLevel");
        }
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("container.dntr.arcane_smithing");
    }

    @Override
    public AbstractContainerMenu createMenu(int id, Inventory playerInventory, Player player) {
        return new ArcaneSmithingMenu(id, playerInventory, this);
    }
}
