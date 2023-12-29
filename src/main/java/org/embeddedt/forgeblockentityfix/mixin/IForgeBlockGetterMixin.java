package org.embeddedt.forgeblockentityfix.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.core.SectionPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.chunk.ImposterProtoChunk;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraftforge.common.extensions.IForgeBlockGetter;
import org.embeddedt.forgeblockentityfix.VersionHelper;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(IForgeBlockGetter.class)
public interface IForgeBlockGetterMixin {
    /**
     * @author embeddedt
     * @reason rewrite branch for Level
     */
    @Overwrite(remap = false)
    default @Nullable BlockEntity getExistingBlockEntity(BlockPos pos) {
        if (this instanceof Level level) {
            BlockGetter getter = VersionHelper.getChunkForLighting(level.getChunkSource(), SectionPos.blockToSectionCoord(pos.getX()), SectionPos.blockToSectionCoord(pos.getZ()));
            return getter != null ? getter.getExistingBlockEntity(pos) : null;
        } else if (this instanceof LevelChunk chunk) {
            return chunk.getBlockEntities().get(pos);
        } else if (this instanceof ImposterProtoChunk chunk) {
            return chunk.getWrapped().getExistingBlockEntity(pos);
        } else {
            return ((BlockGetter)this).getBlockEntity(pos);
        }
    }
}
