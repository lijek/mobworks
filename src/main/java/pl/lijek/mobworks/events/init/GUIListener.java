package pl.lijek.mobworks.events.init;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.mine_diver.unsafeevents.listener.EventListener;
import net.minecraft.client.gui.screen.ScreenBase;
import net.minecraft.entity.player.PlayerBase;
import net.minecraft.inventory.InventoryBase;
import net.modificationstation.stationapi.api.event.registry.GuiHandlerRegistryEvent;
import net.modificationstation.stationapi.api.registry.GuiHandlerRegistry;
import net.modificationstation.stationapi.api.registry.Identifier;
import pl.lijek.mobworks.gui.MobGrinderGUI;
import pl.lijek.mobworks.gui.VacuumMachineGUI;
import pl.lijek.mobworks.tileentity.TileEntityMobGrinder;
import pl.lijek.mobworks.tileentity.TileEntityVacuumMachine;
import uk.co.benjiweber.expressions.tuple.BiTuple;

import static pl.lijek.mobworks.Mobworks.MOD_ID;

public class GUIListener {

    @Environment(EnvType.CLIENT)
    @EventListener
    public void registerGuiHandlers(GuiHandlerRegistryEvent event) {
        GuiHandlerRegistry registry = event.registry;
        registry.registerValueNoMessage(Identifier.of(MOD_ID, "mobGrinder"), BiTuple.of(this::openMobGrinder, TileEntityMobGrinder::new));
        registry.registerValueNoMessage(Identifier.of(MOD_ID, "vacuumMachine"), BiTuple.of(this::openVacuumMachine, TileEntityVacuumMachine::new));
    }

    @Environment(EnvType.CLIENT)
    public ScreenBase openMobGrinder(PlayerBase player, InventoryBase inventoryBase) {
        return new MobGrinderGUI(player.inventory, (TileEntityMobGrinder) inventoryBase);
    }

    @Environment(EnvType.CLIENT)
    public ScreenBase openVacuumMachine(PlayerBase player, InventoryBase inventoryBase) {
        return new VacuumMachineGUI(player.inventory, (TileEntityVacuumMachine) inventoryBase);
    }
}
