package pl.lijek.mobworks;

import net.minecraft.entity.EntityBase;
import net.minecraft.util.maths.Box;

import java.util.List;

public interface ILevel {
    List getBlockCollisions(EntityBase entity, Box box);
}
