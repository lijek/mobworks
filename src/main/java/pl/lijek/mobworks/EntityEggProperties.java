package pl.lijek.mobworks;

import java.util.Locale;

public class EntityEggProperties {
    public static EntityEggProperties BLANK = new EntityEggProperties(0xffffff, 0, null, "blank");

    public Class entityClass;
    public int primaryColor;
    public int secondaryColor;
    public String entityID;

    public EntityEggProperties(int primaryColor, int secondaryColor, Class entityClass, String entityID) {
        this.primaryColor = primaryColor;
        this.secondaryColor = secondaryColor;
        this.entityClass = entityClass;
        this.entityID = entityID;
    }

    public String getTranslationKey(){
        return "item." + Mobworks.MOD_ID + ":spawnEgg." + entityID.toLowerCase(Locale.ROOT);
    }
}
