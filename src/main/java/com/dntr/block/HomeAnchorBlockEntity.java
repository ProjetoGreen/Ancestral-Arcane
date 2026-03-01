package com.dntr.block;

import com.dntr.registry.DntrBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class HomeAnchorBlockEntity extends BlockEntity {
    public HomeAnchorBlockEntity(BlockPos pos, BlockState blockState) {
        super(DntrBlockEntities.HOME_ANCHOR.get(), pos, blockState);
    }
}
