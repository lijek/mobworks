package pl.lijek.mobworks.mixin;

import net.minecraft.entity.Living;
import net.minecraft.tileentity.TileEntityBase;
import net.minecraft.tileentity.TileEntityMobSpawner;
import net.minecraft.util.io.CompoundTag;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import pl.lijek.mobworks.ILiving;
import pl.lijek.mobworks.MobSpawnerModifiers;

import java.util.Random;

@Mixin(TileEntityMobSpawner.class)
public class TileEntityMobSpawnerMixin extends TileEntityBase implements MobSpawnerModifiers {
    @Shadow public double lastEntityRotation;
    @Shadow public double entityRotation;
    private boolean requiresRedstone = false;
    private byte maxEntitiesNearby = 6;
    private byte entitiesToSpawn = 4;
    private byte spawnRange = 4;

    @Inject(method = "tick", at = @At("HEAD"), cancellable = true)
    private void tick_Head(CallbackInfo ci){
        if(requiresRedstone){
            if(!level.hasRedstonePower(x, y, z)) {
                lastEntityRotation = 0.0d;
                entityRotation = 0.0d;
                ci.cancel();
            }
        }
    }

    @Redirect(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Living;canSpawn()Z"))
    private boolean tick_canSpawn(Living living){
        return ((ILiving) living).canSpawn1();
    }

    @Redirect(method = "tick", at = @At(value = "INVOKE", target = "Ljava/util/Random;nextInt(I)I"))
    private int tick_randomY(Random random, int bound){
        return 2;
    }

    @ModifyConstant(method = "tick", constant = @Constant(doubleValue = 4.0D, ordinal = 1))
    private double tick_spawnRangeX(double value){
        return spawnRange;
    }

    @ModifyConstant(method = "tick", constant = @Constant(doubleValue = 4.0D, ordinal = 2))
    private double tick_spawnRangeZ(double value){
        return spawnRange;
    }

    @ModifyVariable(method = "tick", at = @At("STORE"), ordinal = 0)
    private int tick_entitiesToSpawn(int value){
        return entitiesToSpawn;
    }

    @ModifyConstant(method = "tick", constant = @Constant(intValue = 6))
    private int tick_maxEntitiesNearby(int value){
        return maxEntitiesNearby;
    }

    @Inject(method = "readIdentifyingData", at = @At("TAIL"))
    private void readIdentifyingData(CompoundTag tag, CallbackInfo ci){
        requiresRedstone = tag.getBoolean("RequiresRedstone");
        maxEntitiesNearby = tag.getByte("MaxEntitiesNearby");
        entitiesToSpawn = tag.getByte("EntitiesToSpawn");
        spawnRange = tag.getByte("SpawnRange");
    }

    @Inject(method = "writeIdentifyingData", at = @At("TAIL"))
    private void writeIdentifyingData(CompoundTag tag, CallbackInfo ci){
        tag.put("RequiresRedstone", requiresRedstone);
        tag.put("MaxEntitiesNearby", maxEntitiesNearby);
        tag.put("EntitiesToSpawn", entitiesToSpawn);
        tag.put("SpawnRange", spawnRange);
    }

    @Override
    public void setRequiresRedstone(boolean value) {
        requiresRedstone = value;
    }

    @Override
    public boolean getRequiresRedstone() {
        return requiresRedstone;
    }

    @Override
    public int getMaxEntitiesNearby() {
        return maxEntitiesNearby;
    }

    @Override
    public void setMaxEntitiesNearby(int count) {
        maxEntitiesNearby = (byte) count;
    }

    @Override
    public int getEntitiesToSpawn() {
        return entitiesToSpawn;
    }

    @Override
    public void setEntitiesToSpawn(int count) {
        entitiesToSpawn = (byte) count;
    }

    @Override
    public int getSpawnRange() {
        return spawnRange;
    }

    @Override
    public void setSpawnRange(int range) {
        spawnRange = (byte) range;
    }
}
