package pl.lijek.mobworks.events.init;

import net.mine_diver.unsafeevents.listener.EventListener;
import net.modificationstation.stationapi.api.event.registry.BlockRegistryEvent;
import net.modificationstation.stationapi.api.registry.Identifier;
import pl.lijek.mobworks.block.Conveyor;
import pl.lijek.mobworks.block.CursedGrass;
import pl.lijek.mobworks.block.MobGrinder;
import pl.lijek.mobworks.block.VacuumMachine;

import static pl.lijek.mobworks.Mobworks.MOD_ID;

public class BlockListener {

    public static Conveyor slowConveyor, moderateConveyor, fastConveyor;
    public static MobGrinder grinder;
    public static CursedGrass cursedGrass;
    public static VacuumMachine vacuumMachine;

    @EventListener
    public void registerBlocks(BlockRegistryEvent event) {
        // I know that conveyors use 3 block ids instead of one but it will not be fixed later :concern:

        slowConveyor = (Conveyor) new Conveyor(getIdentifierFor("slowConveyor"), 0.25D).setTranslationKey(MOD_ID, "slowConveyor");
        moderateConveyor = (Conveyor) new Conveyor(getIdentifierFor("moderateConveyor"), 0.5D).setTranslationKey(MOD_ID, "moderateConveyor");
        fastConveyor = (Conveyor) new Conveyor(getIdentifierFor("fastConveyor"), 1.0D).setTranslationKey(MOD_ID, "fastConveyor");
        grinder = (MobGrinder) new MobGrinder(getIdentifierFor("mobGrinder")).setTranslationKey(MOD_ID, "mobGrinder");
        cursedGrass = (CursedGrass) new CursedGrass(getIdentifierFor("cursedGrass")).setTranslationKey(MOD_ID, "cursedGrass").setTicksRandomly(true).setHardness(1.0F);
        vacuumMachine = (VacuumMachine) new VacuumMachine(getIdentifierFor("vacuumMachine")).setTranslationKey(MOD_ID, "vacuumMachine");
    }

    private Identifier getIdentifierFor(String id){
        return Identifier.of(MOD_ID, id);
    }
}
