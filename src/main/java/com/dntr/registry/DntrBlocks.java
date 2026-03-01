package com.dntr.registry;

import com.dntr.DntrMod;
import com.dntr.block.ArcaneSmithingTableBlock;
import com.dntr.block.HomeAnchorBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredBlock;

public class DntrBlocks {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(DntrMod.MODID);

    public static final DeferredBlock<Block> ARCANE_SMITHING_TABLE = BLOCKS.register("arcane_smithing_table",
            () -> new ArcaneSmithingTableBlock(BlockBehaviour.Properties.of().mapColor(MapColor.WOOD).strength(2.5F)
                    .requiresCorrectToolForDrops()));

    public static final DeferredBlock<Block> HOME_ANCHOR = BLOCKS.register("home_anchor",
            () -> new HomeAnchorBlock(BlockBehaviour.Properties.of().mapColor(MapColor.STONE).strength(3.5F)
                    .requiresCorrectToolForDrops()));
}
