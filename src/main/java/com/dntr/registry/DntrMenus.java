package com.dntr.registry;

import com.dntr.DntrMod;
import com.dntr.ui.menu.ArcaneSmithingMenu;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredHolder;

public class DntrMenus {
    public static final DeferredRegister<MenuType<?>> MENUS = DeferredRegister.create(BuiltInRegistries.MENU,
            DntrMod.MODID);

    public static final DeferredHolder<MenuType<?>, MenuType<ArcaneSmithingMenu>> ARCANE_SMITHING = MENUS.register(
            "arcane_smithing",
            () -> IMenuTypeExtension.create(ArcaneSmithingMenu::new));
}
