package com.ancestralarcane.registry;

import com.ancestralarcane.AncestralArcaneMod;
import com.ancestralarcane.ui.menu.ArcaneSmithingMenu;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredHolder;

public class AncestralArcaneMenus {
    public static final DeferredRegister<MenuType<?>> MENUS = DeferredRegister.create(BuiltInRegistries.MENU,
            AncestralArcaneMod.MODID);

    public static final DeferredHolder<MenuType<?>, MenuType<ArcaneSmithingMenu>> ARCANE_SMITHING = MENUS.register(
            "arcane_smithing",
            () -> IMenuTypeExtension.create(ArcaneSmithingMenu::new));
}
