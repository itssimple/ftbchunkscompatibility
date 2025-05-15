package se.itssimple.ftbchunkscompatibility.forge.mixin;

import dev.ftb.mods.ftbchunks.api.ChunkTeamData;
import dev.ftb.mods.ftbchunks.api.ClaimedChunk;
import dev.ftb.mods.ftbchunks.api.ClaimedChunkManager;
import dev.ftb.mods.ftbchunks.api.FTBChunksAPI;
import dev.ftb.mods.ftblibrary.math.ChunkDimPos;
import net.mehvahdjukaar.supplementaries.common.entities.SlingshotProjectileEntity;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SlingshotProjectileEntity.class)
abstract class SlingshotProjectileBlockHitMixin {

    @Inject(method = "m_8060_(Lnet/minecraft/world/phys/BlockHitResult;)V", at = @At("HEAD"), cancellable = true, remap = false)
    public void ftbchunkscompatibility$blockHit(BlockHitResult hit, CallbackInfo ci) {
        SlingshotProjectileEntity projectile = (SlingshotProjectileEntity) (Object) this;

        Entity owner = projectile.getOwner();
        Level level = projectile.level();

        if(!level.isClientSide && owner instanceof ServerPlayer) {
            ClaimedChunkManager chunkManager = FTBChunksAPI.api().getManager();
            ClaimedChunk chunk = chunkManager.getChunk(new ChunkDimPos(level, hit.getBlockPos()));

            if (chunk != null) {
                ChunkTeamData teamData = chunk.getTeamData();

                if(teamData != null) {
                    if (!teamData.isAlly(owner.getUUID()) || !teamData.isTeamMember(owner.getUUID())) {
                        projectile.remove(Entity.RemovalReason.DISCARDED);
                        ci.cancel();
                    }
                }
            }
        }
    }
}
