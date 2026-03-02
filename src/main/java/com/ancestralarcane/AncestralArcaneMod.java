package com.ancestralarcane;

import com.ancestralarcane.registry.*;
import com.ancestralarcane.network.AncestralArcaneNetwork;
import com.ancestralarcane.ui.hud.ChargeBarOverlay;
import com.ancestralarcane.ui.screen.ArcaneSmithingScreen;
import com.ancestralarcane.commands.AncestralArcaneGiveTestCommand;
import com.ancestralarcane.loot.AncestralArcaneLootModifiers;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.RegisterGuiLayersEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.minecraft.resources.ResourceLocation;

@Mod(AncestralArcaneMod.MODID)
public class AncestralArcaneMod {
    public static final String MODID = "ancestral_arcane";

    public AncestralArcaneMod(IEventBus modEventBus) {
        AncestralArcaneBlocks.BLOCKS.register(modEventBus);
        AncestralArcaneItems.ITEMS.register(modEventBus);
        AncestralArcaneBlockEntities.BLOCK_ENTITIES.register(modEventBus);
        AncestralArcaneMenus.MENUS.register(modEventBus);
        AncestralArcaneCreativeTab.CREATIVE_MODE_TABS.register(modEventBus);
        AncestralArcaneLootModifiers.GLOBAL_LOOT_MODIFIER_SERIALIZERS.register(modEventBus);

        modEventBus.addListener(this::clientSetup);
        modEventBus.addListener(this::registerMenus);
        modEventBus.addListener(this::registerGuiLayers);
        modEventBus.addListener(this::registerNetwork);

        NeoForge.EVENT_BUS.addListener(this::registerCommands);
    }

    private void clientSetup(final FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            net.minecraft.world.item.Item[] wands = new net.minecraft.world.item.Item[] {
                    AncestralArcaneItems.FLINT_WAND.get(),
                    AncestralArcaneItems.COPPER_WAND.get(),
                    AncestralArcaneItems.IRON_WAND.get(),
                    AncestralArcaneItems.GOLDEN_WAND.get(),
                    AncestralArcaneItems.DIAMOND_WAND.get(),
                    AncestralArcaneItems.EMERALD_WAND.get(),
                    AncestralArcaneItems.NETHERITE_WAND.get()
            };
            for (net.minecraft.world.item.Item wand : wands) {
                net.minecraft.client.renderer.item.ItemProperties.register(wand,
                        ResourceLocation.fromNamespaceAndPath(MODID, "has_leather"),
                        (stack, level, entity, seed) -> {
                            net.minecraft.nbt.CompoundTag data = com.ancestralarcane.data.CustomDataUtil
                                    .getAncestralArcaneData(stack);
                            return data.getString("grip").equals("leather") ? 1.0F : 0.0F;
                        });
            }

            net.minecraft.client.renderer.item.ItemProperties.register(AncestralArcaneItems.RUNE.get(),
                    ResourceLocation.fromNamespaceAndPath(MODID, "rune_state"),
                    (stack, level, entity, seed) -> {
                        net.minecraft.nbt.CompoundTag data = com.ancestralarcane.data.CustomDataUtil
                                .getAncestralArcaneData(stack);
                        if (!data.contains("rune"))
                            return 0.0F;
                        net.minecraft.nbt.CompoundTag rune = data.getCompound("rune");
                        if (rune.getInt("crude") == 1)
                            return 0.1F;

                        boolean upgraded = rune.contains("upgrade");
                        boolean inscribed = rune.getInt("lvl") > 0;

                        if (upgraded && inscribed)
                            return 0.4F;
                        if (upgraded)
                            return 0.3F;
                        if (inscribed)
                            return 0.2F;
                        return 0.0F;
                    });
        });
    }

    private void registerMenus(RegisterMenuScreensEvent event) {
        event.register(AncestralArcaneMenus.ARCANE_SMITHING.get(), ArcaneSmithingScreen::new);
    }

    private void registerGuiLayers(RegisterGuiLayersEvent event) {
        event.registerAboveAll(ResourceLocation.fromNamespaceAndPath(MODID, "charge_bar"), new ChargeBarOverlay());
    }

    private void registerNetwork(RegisterPayloadHandlersEvent event) {
        AncestralArcaneNetwork.register(event.registrar(MODID).versioned("1.0"));
    }

    private void registerCommands(RegisterCommandsEvent event) {
        AncestralArcaneGiveTestCommand.register(event.getDispatcher());
    }
}
