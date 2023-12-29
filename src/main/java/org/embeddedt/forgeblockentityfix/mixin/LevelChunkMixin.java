package org.embeddedt.forgeblockentityfix.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraftforge.common.extensions.IForgeBlockGetter;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LevelChunk.class)
public abstract class LevelChunkMixin {
    @Shadow @Final private Level level;

    @Inject(method = "getBlockEntity(Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/chunk/LevelChunk$EntityCreationType;)Lnet/minecraft/world/level/block/entity/BlockEntity;", at = @At(value = "HEAD"), cancellable = true)
    private void getWithoutModification(BlockPos p_62868_, LevelChunk.EntityCreationType p_62869_, CallbackInfoReturnable<BlockEntity> cir) {
        if(((LevelAccessor)this.level).fbef$getThread() != Thread.currentThread()) {
            cir.setReturnValue(((IForgeBlockGetter)this).getExistingBlockEntity(p_62868_));
        }
    }
}
