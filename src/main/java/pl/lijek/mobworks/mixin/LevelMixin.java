package pl.lijek.mobworks.mixin;

import net.minecraft.block.BlockBase;
import net.minecraft.entity.EntityBase;
import net.minecraft.level.Level;
import net.minecraft.util.maths.Box;
import net.minecraft.util.maths.MathHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import pl.lijek.mobworks.ILevel;

import java.util.ArrayList;
import java.util.List;

@Mixin(Level.class)
public abstract class LevelMixin implements ILevel {
    @Shadow private ArrayList field_189;

    @Shadow public abstract boolean isTileLoaded(int x, int y, int z);

    @Shadow public abstract int getTileId(int x, int y, int z);

    @Override
    public List getBlockCollisions(EntityBase entity, Box box) {
        field_189.clear();
        int var3 = MathHelper.floor(box.minX);
        int var4 = MathHelper.floor(box.maxX + 1.0D);
        int var5 = MathHelper.floor(box.minY);
        int var6 = MathHelper.floor(box.maxY + 1.0D);
        int var7 = MathHelper.floor(box.minZ);
        int var8 = MathHelper.floor(box.maxZ + 1.0D);

        for(int var9 = var3; var9 < var4; ++var9) {
            for (int var10 = var7; var10 < var8; ++var10) {
                if (isTileLoaded(var9, 64, var10)) {
                    for (int var11 = var5 - 1; var11 < var6; ++var11) {
                        BlockBase var12 = BlockBase.BY_ID[getTileId(var9, var11, var10)];
                        if (var12 != null) {
                            var12.doesBoxCollide((Level) (Object) this, var9, var11, var10, box, this.field_189);
                        }
                    }
                }
            }
        }
        return field_189;
    }
}
