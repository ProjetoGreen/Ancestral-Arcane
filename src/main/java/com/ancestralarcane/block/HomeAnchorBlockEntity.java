package com.ancestralarcane.block;

import com.ancestralarcane.registry.AncestralArcaneBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class HomeAnchorBlockEntity extends BlockEntity {
    public HomeAnchorBlockEntity(BlockPos pos, BlockState blockState) {
        super(AncestralArcaneBlockEntities.HOME_ANCHOR.get(), pos, blockState);
    }
}
