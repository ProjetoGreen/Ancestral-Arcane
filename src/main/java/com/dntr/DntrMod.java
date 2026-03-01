package com.dntr;

import com.dntr.registry.*;
import com.dntr.network.DntrNetwork;
import com.dntr.ui.hud.ChargeBarOverlay;
import com.dntr.ui.screen.ArcaneSmithingScreen;
import com.dntr.commands.DntrGiveTestCommand;
import com.dntr.loot.DntrLootModifiers;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.RegisterGuiLayersEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.minecraft.resources.ResourceLocation;

@Mod(DntrMod.MODID)
public class DntrMod {
    public static final String MODID = "dntr";

    public DntrMod(IEventBus modEventBus) {
        DntrBlocks.BLOCKS.register(modEventBus);
        DntrItems.ITEMS.register(modEventBus);
        DntrBlockEntities.BLOCK_ENTITIES.register(modEventBus);
        DntrMenus.MENUS.register(modEventBus);
        DntrCreativeTab.CREATIVE_MODE_TABS.register(modEventBus);
        DntrLootModifiers.GLOBAL_LOOT_MODIFIER_SERIALIZERS.register(modEventBus);

        modEventBus.addListener(this::clientSetup);
        modEventBus.addListener(this::registerMenus);
        modEventBus.addListener(this::registerGuiLayers);
        modEventBus.addListener(this::registerNetwork);

        NeoForge.EVENT_BUS.addListener(this::registerCommands);
    }

    private void clientSetup(final FMLClientSetupEvent event) {
    }

    private void registerMenus(RegisterMenuScreensEvent event) {
        event.register(DntrMenus.ARCANE_SMITHING.get(), ArcaneSmithingScreen::new);
    }

    private void registerGuiLayers(RegisterGuiLayersEvent event) {
        event.registerAboveAll(ResourceLocation.fromNamespaceAndPath(MODID, "charge_bar"), new ChargeBarOverlay());
    }

    private void registerNetwork(RegisterPayloadHandlersEvent event) {
        DntrNetwork.register(event.registrar(MODID).versioned("1.0"));
    }

    private void registerCommands(RegisterCommandsEvent event) {
        DntrGiveTestCommand.register(event.getDispatcher());
    }
}
