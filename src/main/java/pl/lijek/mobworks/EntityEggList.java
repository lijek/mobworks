package pl.lijek.mobworks;

import net.minecraft.entity.EntityBase;
import net.minecraft.entity.animal.*;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.swimming.Squid;
import net.minecraft.level.Level;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EntityEggList {
    private static Map<String, EntityEggProperties> entityEggPropertiesMap = new HashMap<>();
    private static List<String> metaToString = new ArrayList<>();

    public static void addEntity(String entityID, int primaryColor, int secondaryColor, Class entityClass){
        metaToString.add(entityID);
        entityEggPropertiesMap.put(entityID, new EntityEggProperties(primaryColor, secondaryColor, entityClass, entityID));
    }

    public static EntityEggProperties getEggProperties(int meta){
        if(meta >= metaToString.size())
            return EntityEggProperties.BLANK;
        return entityEggPropertiesMap.get(metaToString.get(meta));
    }

    public static String getEggTranslationKey(int meta){
        if(meta >= metaToString.size())
            return null;
        return entityEggPropertiesMap.get(metaToString.get(meta)).getTranslationKey();
    }

    public static EntityEggProperties getEggProperties(String entityID){
        return entityEggPropertiesMap.get(entityID);
    }

    public static EntityBase createEntityFromProps(EntityEggProperties props, Level level){
        return createEntityFromString(props.entityID, level);
    }

    public static EntityBase createEntityFromString(String entityID, Level level){
        EntityBase entity = null;

        try
        {
            Class entityClass = entityEggPropertiesMap.get(entityID).entityClass;

            if (entityClass != null)
            {
                entity = (EntityBase)entityClass.getConstructor(new Class[]
                        {
                                Level.class
                        }).newInstance(new Object[]
                        {
                                level
                        });
            }
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
        }

        if (entity == null)
        {
            System.out.println("Skipping Entity with id " + entityID);
        }

        return entity;
    }

    static {
        addEntity("Creeper", 0xda70b, 0, Creeper.class);
        addEntity("Skeleton",0xc1c1c1, 0x494949, Skeleton.class);
        addEntity("Spider", 0x342d27, 0xa80e0e, Spider.class);
        addEntity("Giant", 0xffffff, 0, Giant.class);
        addEntity("Zombie", 44975, 0x799c65, Zombie.class);
        addEntity("Slime", 0x51a03e, 0x7ebf6e, Slime.class);
        addEntity("Ghast", 0xf9f9f9, 0xbcbcbc, Ghast.class);
        addEntity("PigZombie", 0xea9393, 0x4c7129, ZombiePigman.class);

        addEntity("Pig",  0xf0a5a2, 0xdb635f, Pig.class);
        addEntity("Sheep", 0xe7e7e7, 0xffb5b5, Sheep.class);
        addEntity("Cow", 0x443626, 0xa1a1a1, Cow.class);
        addEntity("Chicken", 0xa1a1a1, 0xff0000, Chicken.class);
        addEntity("Squid", 0x223b4d, 0x708899, Squid.class);
        addEntity("Wolf", 0xd7d3d3, 0xceaf96, Wolf.class);
    }


}
