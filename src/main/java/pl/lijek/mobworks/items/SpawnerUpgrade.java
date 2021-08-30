package pl.lijek.mobworks.items;

import net.minecraft.block.BlockBase;
import net.minecraft.block.MobSpawner;
import net.minecraft.client.resource.language.TranslationStorage;
import net.minecraft.entity.player.PlayerBase;
import net.minecraft.item.ItemInstance;
import net.minecraft.level.Level;
import net.modificationstation.stationapi.api.client.gui.CustomTooltipProvider;
import net.modificationstation.stationapi.api.registry.Identifier;
import net.modificationstation.stationapi.api.template.item.TemplateItemBase;
import pl.lijek.mobworks.MobSpawnerModifiers;
import pl.lijek.mobworks.Mobworks;

import java.util.Locale;

public class SpawnerUpgrade extends TemplateItemBase implements CustomTooltipProvider {
    public int[] textures = new int[7];

    /*
    * Meta guide
    * 0 - redstone upgrade
    * 1 - max entities nearby upgrade
    * 2 - max entities nearby downgrade
    * 3 - range upgrade
    * 4 - range downgrade
    * */

    public SpawnerUpgrade(Identifier identifier) {
        super(identifier);
        setHasSubItems(true);
    }

    @Override
    public boolean useOnTile(ItemInstance item, PlayerBase player, Level level, int x, int y, int z, int facing) {
        if(!(BlockBase.BY_ID[level.getTileId(x, y, z)] instanceof MobSpawner))
            return false;
        MobSpawnerModifiers modifiers = (MobSpawnerModifiers) level.getTileEntity(x, y, z);
        int max = modifiers.getMaxEntitiesNearby();
        int entitiesToSpawn = modifiers.getEntitiesToSpawn();
        int spawnRange = modifiers.getSpawnRange();

        switch (item.getDamage()){
            case 0:
                modifiers.setRequiresRedstone(true);
                break;
            case 1:
                max += 1;
                if(max > 20)
                    return false;
                else
                    modifiers.setMaxEntitiesNearby(max);
                break;
            case 2:
                max -= 1;
                if(max <= 0)
                    return false;
                else
                    modifiers.setMaxEntitiesNearby(max);
                break;
            case 3:
                entitiesToSpawn++;
                if(entitiesToSpawn > 20)
                    return false;
                else
                    modifiers.setEntitiesToSpawn(entitiesToSpawn);
                break;
            case 4:
                entitiesToSpawn--;
                if(entitiesToSpawn <= 0)
                    return false;
                else
                    modifiers.setEntitiesToSpawn(entitiesToSpawn);
                break;
            case 5:
                spawnRange++;
                if(spawnRange > 8 )
                    return false;
                else
                    modifiers.setSpawnRange(spawnRange);
                break;
            case 6:
                spawnRange--;
                if (spawnRange < 0)
                    return false;
                else
                    modifiers.setSpawnRange(spawnRange);
                break;
        }
        item.count--;

        return true;
    }

    @Override
    public int getTexturePosition(int damage) {
        if(damage >= textures.length)
            return -1;
        return textures[damage];
    }

    @Override
    public String getTranslationKey(ItemInstance item) {
        if(item.getDamage() >= textures.length)
            return "";
        return getTranslationKey();
    }

    @Override
    public String[] getTooltip(ItemInstance itemInstance, String originalTooltip) {
        String key = String.format(Locale.ROOT, "item.%s:spawnerUpgrade.name%d", Mobworks.MOD_ID, itemInstance.getDamage());
        String result = TranslationStorage.getInstance().method_995(key);
        System.out.println(result + " asdadasdad");
        //return new String[] {originalTooltip, "result == null || result.isEmpty() ? key : result"};
        return new String[0];
    }
}
