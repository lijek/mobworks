package pl.lijek.mobworks.block;

import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerBase;
import net.minecraft.inventory.InventoryBase;
import net.minecraft.level.Level;
import net.minecraft.tileentity.TileEntityBase;
import net.modificationstation.stationapi.api.gui.screen.container.GuiHelper;
import net.modificationstation.stationapi.api.registry.Identifier;
import net.modificationstation.stationapi.api.template.block.TemplateBlockWithEntity;
import pl.lijek.mobworks.events.init.TextureListener;
import pl.lijek.mobworks.gui.VacuumMachineContainer;
import pl.lijek.mobworks.tileentity.TileEntityVacuumMachine;

import static pl.lijek.mobworks.Mobworks.MOD_ID;

public class VacuumMachine extends TemplateBlockWithEntity {
    public VacuumMachine(Identifier identifier) {
        super(identifier, Material.METAL);
        setSounds(METAL_SOUNDS);
    }

    @Override
    public int getTextureForSide(int side) {
        return TextureListener.vacuumMachine;
    }

    @Override
    protected TileEntityBase createTileEntity() {
        return new TileEntityVacuumMachine();
    }

    @Override
    public boolean canUse(Level level, int x, int y, int z, PlayerBase player) {
        TileEntityBase tileEntityVacuumMachine = level.getTileEntity(x, y, z);
        if (tileEntityVacuumMachine instanceof TileEntityVacuumMachine)
            GuiHelper.openGUI(player, Identifier.of(MOD_ID, "vacuumMachine"), (InventoryBase) tileEntityVacuumMachine, new VacuumMachineContainer(player.inventory, (TileEntityVacuumMachine) tileEntityVacuumMachine));
        return true;
    }
}
