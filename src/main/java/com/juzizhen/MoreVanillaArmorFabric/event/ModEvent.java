package com.juzizhen.MoreVanillaArmorFabric.event;

import com.juzizhen.MoreVanillaArmorFabric.items.Armor;
import com.juzizhen.MoreVanillaArmorFabric.items.ArmorTiers;
import com.juzizhen.MoreVanillaArmorFabric.util.ModRegistries;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.LightningEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.List;

public class ModEvent {
    public static void registry () {
        ServerEntityEvents.ENTITY_LOAD.register((entity, world) -> {
            if (entity instanceof LightningEntity lightning) {
                Vec3d pos = lightning.getPos();
                List<LivingEntity> targets = world.getEntitiesByClass(
                        LivingEntity.class,
                        Box.of(pos, 20, 20, 20),
                        living -> Armor.getArmorSetType(living) == ArmorTiers.COPPER
                );

                if (!targets.isEmpty()) {
                    LivingEntity target = targets.get(world.random.nextInt(targets.size()));
                    lightning.setPosition(target.getX(), target.getY(), target.getZ());
                }
            }
        });

        ServerTickEvents.END_SERVER_TICK.register(server -> {
            for (ServerPlayerEntity player : server.getPlayerManager().getPlayerList()) {
                World world = player.getWorld();
                if (player.isOnGround() && Armor.getArmorSetType(player) == ArmorTiers.REDSTONE) {
                    BlockPos pos = player.getBlockPos();
                    BlockState state = world.getBlockState(pos);

                    if (state.isOf(Blocks.AIR) || state.isOf(ModRegistries.REDSTONE_ESSENCE)) {
                        BlockState essence = ModRegistries.REDSTONE_ESSENCE.getDefaultState();
                        world.setBlockState(pos, essence, 3);
                    }
                }
            }
        });
    }
}
