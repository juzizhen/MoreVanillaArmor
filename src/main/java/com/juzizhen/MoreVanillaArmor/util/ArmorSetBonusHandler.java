package com.juzizhen.MoreVanillaArmor.util;

import com.juzizhen.MoreVanillaArmor.items.ArmorTiers;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents;
import net.minecraft.block.*;
import net.minecraft.entity.LightningEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.world.Heightmap;

import java.util.List;

public class ArmorSetBonusHandler {
    public static void register() {
        ServerEntityEvents.ENTITY_LOAD.register((entity, world) -> {
            if (entity instanceof LightningEntity lightning) {
                Box box = new Box(
                        lightning.getX() - 10, lightning.getY() - 10, lightning.getZ() - 10,
                        lightning.getX() + 10, lightning.getY() + 10, lightning.getZ() + 10
                );

                List<PlayerEntity> candidates = world.getEntitiesByClass(PlayerEntity.class, box,
                        p -> Bonus.isFullSet(p, ArmorTiers.COPPER));

                if (!candidates.isEmpty()) {
                    PlayerEntity target = candidates.get(world.random.nextInt(candidates.size()));

                    BlockPos groundPos = world.getTopPosition(Heightmap.Type.MOTION_BLOCKING, target.getBlockPos());

                    lightning.refreshPositionAfterTeleport(
                            groundPos.getX() + 0.5,
                            groundPos.getY(),
                            groundPos.getZ() + 0.5
                    );

                    lightning.setChanneler((ServerPlayerEntity) target);
                }
            }
        });
    }
}
