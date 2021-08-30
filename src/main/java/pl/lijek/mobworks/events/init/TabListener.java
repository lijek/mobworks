package pl.lijek.mobworks.events.init;

import net.mine_diver.unsafeevents.listener.EventListener;
import net.minecraft.item.ItemInstance;
import paulevs.creative.api.event.TabRegisterEvent;
import pl.lijek.mobworks.Mobworks;
import pl.lijek.mobworks.MobworksTab;

public class TabListener {

    @EventListener
    public void registerTabs(TabRegisterEvent event){
        event.register(new MobworksTab("mobworks", Mobworks.MOD_ID.toString(), new ItemInstance(ItemListener.spawnEgg)));
    }
}
