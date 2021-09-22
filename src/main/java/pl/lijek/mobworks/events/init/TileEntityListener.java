package pl.lijek.mobworks.events.init;

import net.mine_diver.unsafeevents.listener.EventListener;
import net.modificationstation.stationapi.api.event.tileentity.TileEntityRegisterEvent;
import pl.lijek.mobworks.tileentity.TileEntityMobGrinder;
import pl.lijek.mobworks.tileentity.TileEntityVacuumMachine;

import static pl.lijek.mobworks.Mobworks.MOD_ID;

public class TileEntityListener {

    @EventListener
    public void registerTileEntities(TileEntityRegisterEvent event){
        event.register(TileEntityMobGrinder.class, MOD_ID + ".mobGrinder");
        event.register(TileEntityVacuumMachine.class, MOD_ID + ".vacuumHopper");
    }
}
