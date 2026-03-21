package com.juzizhen.MoreVanillaArmor.mixin;

import com.juzizhen.MoreVanillaArmor.items.ArmorTiers;
import com.juzizhen.MoreVanillaArmor.util.Bonus;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.world.RedstoneView;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(World.class)
public abstract class WorldMixin {

    @Inject(
            method = "getReceivedRedstonePower(Lnet/minecraft/util/math/BlockPos;)I",
            at = @At("RETURN"),
            cancellable = true
    )
    private void injectRedstoneArmorPower(BlockPos pos, CallbackInfoReturnable<Integer> cir) {
        if (cir.getReturnValue() < 15) {
            if (isPoweredByRedstoneArmor((World) (Object) this, pos)) {
                cir.setReturnValue(15);
            }
        }
    }

    @Inject(
            method = "isReceivingRedstonePower(Lnet/minecraft/util/math/BlockPos;)Z",
            at = @At("RETURN"),
            cancellable = true
    )
    private void injectRedstoneArmorState(BlockPos pos, CallbackInfoReturnable<Boolean> cir) {
        if (!cir.getReturnValue()) {
            if (isPoweredByRedstoneArmor((World) (Object) this, pos)) {
                cir.setReturnValue(true);
            }
        }
    }

    @Unique
    private boolean isPoweredByRedstoneArmor(World world, BlockPos pos) {
        Box box = new Box(pos).expand(1.5, 0.5, 1.5);
        List<PlayerEntity> players = world.getNonSpectatingEntities(PlayerEntity.class, box);

        for (PlayerEntity player : players) {
            if (Bonus.isFullSet(player, ArmorTiers.REDSTONE)) {
                BlockPos playerPos = player.getBlockPos();
                if (Math.abs(playerPos.getX() - pos.getX()) <= 1 &&
                        Math.abs(playerPos.getZ() - pos.getZ()) <= 1 &&
                        playerPos.getY() == pos.getY()) {
                    return true;
                }
            }
        }
        return false;
    }
}