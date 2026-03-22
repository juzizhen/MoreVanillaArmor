package com.juzizhen.MoreVanillaArmor;

import com.juzizhen.MoreVanillaArmor.event.ModEvent;
import com.juzizhen.MoreVanillaArmor.util.ArmorSetBonusHandler;
import com.juzizhen.MoreVanillaArmor.util.ModRegistries;
import net.fabricmc.api.ModInitializer;

public class MoreVanillaArmor implements ModInitializer {

    public static final String MODID = "morevanillaarmor";

    @Override
    public void onInitialize() {
        ModRegistries.register();
        ModEvent.registry();
        ArmorSetBonusHandler.register();
    }
}
