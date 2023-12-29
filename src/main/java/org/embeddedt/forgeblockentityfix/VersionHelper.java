package org.embeddedt.forgeblockentityfix;

import com.google.common.base.MoreObjects;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.chunk.ChunkSource;
import net.minecraftforge.fml.util.ObfuscationReflectionHelper;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

public class VersionHelper {
    private static final MethodHandle GET_CHUNK_FOR_LIGHTING;

    private static MethodHandle adaptMojangGetter() throws Throwable {
        MethodHandle mojangGetter = MethodHandles.publicLookup().unreflect(ObfuscationReflectionHelper.findMethod(ChunkSource.class, "m_6196_", int.class, int.class));
        return mojangGetter.asType(MethodType.methodType(BlockGetter.class, ChunkSource.class, int.class, int.class));
    }

    static {
        try {
            GET_CHUNK_FOR_LIGHTING = adaptMojangGetter();
        } catch(Throwable e) {
            throw new AssertionError(e);
        }
    }

    public static BlockGetter getChunkForLighting(ChunkSource source, int x, int z) {
        try {
            return (BlockGetter)GET_CHUNK_FOR_LIGHTING.invokeExact(source, x, z);
        } catch(Throwable e) {
            throw new RuntimeException(e);
        }
    }
}
