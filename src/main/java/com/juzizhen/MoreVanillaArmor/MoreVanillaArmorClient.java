package com.juzizhen.MoreVanillaArmor;

import com.juzizhen.MoreVanillaArmor.util.ModRegistries;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.client.render.RenderLayer;

public class MoreVanillaArmorClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        BlockRenderLayerMap.INSTANCE.putBlock(ModRegistries.REDSTONE_ESSENCE, RenderLayer.getTranslucent());
    }
}
