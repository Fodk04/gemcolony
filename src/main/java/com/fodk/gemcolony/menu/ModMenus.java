package com.fodk.gemcolony.menu;

import com.fodk.gemcolony.GemColony;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;
import net.neoforged.neoforge.network.IContainerFactory;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModMenus {
    public static final DeferredRegister<MenuType<?>> MENUS = DeferredRegister.create(Registries.MENU, GemColony.MOD_ID);

    public static final DeferredHolder<MenuType<?>, MenuType<GemMenu>> GEM_MENU = MENUS.register("gem_menu",
            () -> IMenuTypeExtension.create(GemMenu::new));

    public static final DeferredHolder<MenuType<?>, MenuType<InjectorMenu>> INJECTOR_MENU = MENUS.register("injector_menu",
                    () -> IMenuTypeExtension.create(InjectorMenu::new));

    public static void register(IEventBus eventBus){
        MENUS.register(eventBus);
    }
}
