package pl.lijek.mobworks.events.init;

import net.mine_diver.unsafeevents.listener.EventListener;
import net.minecraft.item.ItemBase;
import net.modificationstation.stationapi.api.event.registry.ItemRegistryEvent;
import net.modificationstation.stationapi.api.registry.Identifier;
import net.modificationstation.stationapi.api.template.item.TemplateItemBase;
import pl.lijek.mobworks.items.MobGrinderUpgrade;
import pl.lijek.mobworks.items.SpawnEgg;
import pl.lijek.mobworks.items.SpawnerUpgrade;

import static pl.lijek.mobworks.Mobworks.MOD_ID;

public class ItemListener {

    public static ItemBase coolItem;
    public static SpawnEgg spawnEgg;
    public static SpawnerUpgrade spawnerUpgrade;
    public static MobGrinderUpgrade mobGrinderUpgrade;

    @EventListener
    public void registerItems(ItemRegistryEvent event) {
        coolItem = new TemplateItemBase(Identifier.of(MOD_ID, "coolitem")).setTranslationKey(MOD_ID, "coolitem");
        spawnEgg = (SpawnEgg) new SpawnEgg(Identifier.of(MOD_ID, "spawnEgg")).setTranslationKey(MOD_ID, "spawnEgg");
        spawnerUpgrade = (SpawnerUpgrade) new SpawnerUpgrade(Identifier.of(MOD_ID, "spawnerUpgrade")).setTranslationKey(MOD_ID, "spawnerUpgrade");
        mobGrinderUpgrade = (MobGrinderUpgrade) new MobGrinderUpgrade(Identifier.of(MOD_ID, "mobGrinderUpgrade")).setTranslationKey(MOD_ID, "mobGrinderUpgrade");
    }
}
