package pl.lijek.mobworks.gui;

import net.minecraft.container.ContainerBase;
import net.minecraft.container.slot.Slot;
import net.minecraft.entity.player.PlayerBase;
import net.minecraft.entity.player.PlayerInventory;
import pl.lijek.mobworks.tileentity.TileEntityVacuumMachine;

public class VacuumMachineContainer extends ContainerBase{
    public VacuumMachineContainer(PlayerInventory inventory, TileEntityVacuumMachine vacuumMachine) {
        for(int var5 = 0; var5 < 5; ++var5) {
            this.addSlot(new Slot(vacuumMachine, var5, 8 + var5 * 18, 20));
        }

        for(int var3 = 0; var3 < 3; ++var3) {
            for(int var4 = 0; var4 < 9; ++var4) {
                this.addSlot(new Slot(inventory, var4 + var3 * 9 + 9, 8 + var4 * 18, 99 + var3 * 18));
            }
        }

        for(int var5 = 0; var5 < 9; ++var5) {
            this.addSlot(new Slot(inventory, var5, 8 + var5 * 18, 157));
        }
    }

    @Override
    public boolean canUse(PlayerBase arg) {
        return true;
    }
}
