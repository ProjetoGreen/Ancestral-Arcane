package com.dntr.registry;

import com.dntr.DntrMod;
import com.dntr.block.ArcaneSmithingTableBlockEntity;
import com.dntr.block.HomeAnchorBlockEntity;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredHolder;

public class DntrBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister
            .create(BuiltInRegistries.BLOCK_ENTITY_TYPE, DntrMod.MODID);

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<ArcaneSmithingTableBlockEntity>> ARCANE_SMITHING_TABLE = BLOCK_ENTITIES
            .register("arcane_smithing_table",
                    () -> BlockEntityType.Builder
                            .of(ArcaneSmithingTableBlockEntity::new, DntrBlocks.ARCANE_SMITHING_TABLE.get())
                            .build(null));

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<HomeAnchorBlockEntity>> HOME_ANCHOR = BLOCK_ENTITIES
            .register("home_anchor",
                    () -> BlockEntityType.Builder.of(HomeAnchorBlockEntity::new, DntrBlocks.HOME_ANCHOR.get())
                            .build(null));
}
