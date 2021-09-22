package pl.lijek.mobworks.events.init;

import net.mine_diver.unsafeevents.listener.EventListener;
import net.modificationstation.stationapi.api.client.event.tileentity.TileEntityRendererRegisterEvent;
import pl.lijek.mobworks.tileentity.MobGrinderRenderer;
import pl.lijek.mobworks.tileentity.TileEntityMobGrinder;
import pl.lijek.mobworks.tileentity.TileEntityVacuumMachine;
import pl.lijek.mobworks.tileentity.VacuumMachineRenderer;

public class TileEntityRendererListener {

    @EventListener
    public void registerTileEntityRenderers(TileEntityRendererRegisterEvent event){
        event.renderers.put(TileEntityMobGrinder.class, new MobGrinderRenderer());
        event.renderers.put(TileEntityVacuumMachine.class, new VacuumMachineRenderer());
    }
}
