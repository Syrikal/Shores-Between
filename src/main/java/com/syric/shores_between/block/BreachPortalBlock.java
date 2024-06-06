package com.syric.shores_between.block;

import com.syric.shores_between.registry.SBDimensions;
import com.syric.shores_between.worldgen.dimension.portal.BreachTeleporter;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;

public class BreachPortalBlock extends Block {
    public BreachPortalBlock(Properties properties) {
        super(properties);
    }

    @Override
    public @NotNull InteractionResult useWithoutItem(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, Player player, @NotNull BlockHitResult hit) {
        if (player.canChangeDimensions()) {
            handlePortal(player, pos);
            return InteractionResult.SUCCESS;
        } else {
            return InteractionResult.CONSUME;
        }
    }

    private void handlePortal(Entity player, BlockPos blockPos) {
        if (player.level() instanceof ServerLevel serverlevel) {
            MinecraftServer minecraftserver = serverlevel.getServer();
            ResourceKey<Level> resourcekey = player.level().dimension() == SBDimensions.BREACH_LEVEL_KEY ?
                    Level.OVERWORLD : SBDimensions.BREACH_LEVEL_KEY;

            ServerLevel portalDimension = minecraftserver.getLevel(resourcekey);
            if (portalDimension != null && !player.isPassenger()) {
                if (resourcekey == SBDimensions.BREACH_LEVEL_KEY) {
                    player.changeDimension(portalDimension, new BreachTeleporter(blockPos, true));
                } else {
                    player.changeDimension(portalDimension, new BreachTeleporter(blockPos, false));
                }
            }
        }
    }

}
