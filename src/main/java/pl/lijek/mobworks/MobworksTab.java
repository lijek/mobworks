package pl.lijek.mobworks;

import net.minecraft.block.BlockBase;
import net.minecraft.item.ItemInstance;
import paulevs.creative.api.SearchTab;
import pl.lijek.mobworks.events.init.BlockListener;
import pl.lijek.mobworks.events.init.ItemListener;

public class MobworksTab extends SearchTab {
    public static ItemInstance block(BlockBase block){
        return new ItemInstance(block);
    }

    public MobworksTab(String name, String modID, ItemInstance icon) {
        super(name, modID, icon);
        addItemWithVariants(ItemListener.spawnEgg);
        addItemWithVariants(ItemListener.spawnerUpgrade);
        addItemWithVariants(ItemListener.mobGrinderUpgrade);
        addItem(block(BlockListener.slowConveyor));
        addItem(block(BlockListener.moderateConveyor));
        addItem(block(BlockListener.fastConveyor));
        addItem(block(BlockListener.grinder));
        addItem(block(BlockListener.vacuumMachine));
        addItem(block(BlockListener.cursedGrass));
    }
}
