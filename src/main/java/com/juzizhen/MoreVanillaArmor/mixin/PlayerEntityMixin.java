package com.juzizhen.MoreVanillaArmor.mixin;

import com.juzizhen.MoreVanillaArmor.items.ArmorTiers;
import com.juzizhen.MoreVanillaArmor.util.Bonus;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity {

    protected PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Unique
    private BlockPos lastArmorPos = null;
    @Unique
    private boolean wasWearingArmor = false;

    @Inject(method = "tick", at = @At("TAIL"))
    private void triggerRedstoneArmorUpdates(CallbackInfo ci) {
        World world = this.getWorld();
        if (world.isClient()) return;

        PlayerEntity player = (PlayerEntity) (Object) this;
        boolean isWearing = Bonus.isFullSet(player, ArmorTiers.REDSTONE);
        BlockPos currentPos = player.getBlockPos();

        if (isWearing) {
            if (!currentPos.equals(lastArmorPos) || !wasWearingArmor) {
                triggerRedstoneUpdate(world, currentPos);

                if (lastArmorPos != null && !currentPos.equals(lastArmorPos)) {
                    triggerRedstoneUpdate(world, lastArmorPos);
                }

                lastArmorPos = currentPos;
                wasWearingArmor = true;
            }
        } else if (wasWearingArmor) {
            if (lastArmorPos != null) {
                triggerRedstoneUpdate(world, lastArmorPos);
            }
            wasWearingArmor = false;
            lastArmorPos = null;
        }
    }

    @Unique
    private void triggerRedstoneUpdate(World world, BlockPos center) {
        for (int dx = -1; dx <= 1; dx++) {
            for (int dz = -1; dz <= 1; dz++) {
                BlockPos target = center.add(dx, 0, dz);
                if (world.isChunkLoaded(target)) {
                    world.updateNeighbor(target, Blocks.REDSTONE_BLOCK, center);
                }
            }
        }
    }
}
