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
