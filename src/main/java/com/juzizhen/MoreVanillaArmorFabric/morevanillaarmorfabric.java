package com.juzizhen.MoreVanillaArmorFabric;

import com.juzizhen.MoreVanillaArmorFabric.event.ModEvent;
import com.juzizhen.MoreVanillaArmorFabric.util.ArmorSetBonusHandler;
import com.juzizhen.MoreVanillaArmorFabric.util.ModRegistries;
import net.fabricmc.api.ModInitializer;

public class morevanillaarmorfabric implements ModInitializer {

    public static final String MODID = "morevanillaarmorfabric";

    @Override
    public void onInitialize() {
        ModRegistries.register();
        ModEvent.registry();
        ArmorSetBonusHandler.register();
    }
}
