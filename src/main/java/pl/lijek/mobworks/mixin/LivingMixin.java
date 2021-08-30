package pl.lijek.mobworks.mixin;

import net.minecraft.entity.EntityBase;
import net.minecraft.entity.Living;
import net.minecraft.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import pl.lijek.mobworks.ILevel;
import pl.lijek.mobworks.ILiving;

@Mixin(Living.class)
public abstract class LivingMixin extends EntityBase implements ILiving {
    public LivingMixin(Level level) {
        super(level);
    }

    @Override
    public boolean canSpawn1() {
        return ((ILevel)level).getBlockCollisions(this, boundingBox).size() == 0 && !this.level.method_218(this.boundingBox);
    }
}
