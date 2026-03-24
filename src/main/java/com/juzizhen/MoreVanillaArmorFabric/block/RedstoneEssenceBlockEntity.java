package com.juzizhen.MoreVanillaArmorFabric.block;

import com.juzizhen.MoreVanillaArmorFabric.util.ModRegistries;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class RedstoneEssenceBlockEntity extends BlockEntity {

    private int tickCount = 0;

    public RedstoneEssenceBlockEntity(BlockPos pos, BlockState state) {
        super(ModRegistries.REDSTONE_ESSENCE_BLOCK_ENTITY, pos, state);
    }

    public static void tick(World world, BlockPos pos, BlockState state, RedstoneEssenceBlockEntity blockEntity) {
        if (!world.isClient) {
            blockEntity.tickCount++;
            if (blockEntity.tickCount > 40) {
                world.setBlockState(pos, Blocks.AIR.getDefaultState(), 3);
                blockEntity.markRemoved();
            }
        }
    }
}
