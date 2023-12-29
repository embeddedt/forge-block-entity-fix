package org.embeddedt.forgeblockentityfix.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.chunk.ChunkAccess;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Mixin(ChunkAccess.class)
public class ChunkAccessMixin {
    /* make BE map concurrent */
    @Shadow @Final protected final Map<BlockPos, BlockEntity> blockEntities = new ConcurrentHashMap<>();
}
