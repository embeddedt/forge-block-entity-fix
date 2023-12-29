package org.embeddedt.forgeblockentityfix.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.common.extensions.IForgeBlockGetter;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Level.class)
public class LevelMixin {
    @Shadow @Final private Thread thread;

    @Inject(method = "getBlockEntity", at = @At("HEAD"), cancellable = true)
    private void allowOffThreadBERetrieval(BlockPos pos, CallbackInfoReturnable<BlockEntity> cir) {
        if(Thread.currentThread() != this.thread) {
            cir.setReturnValue(((IForgeBlockGetter)this).getExistingBlockEntity(pos));
        }
    }
}
