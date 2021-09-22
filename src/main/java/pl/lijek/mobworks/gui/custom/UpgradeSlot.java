package pl.lijek.mobworks.gui.custom;

import net.minecraft.container.slot.Slot;
import net.minecraft.inventory.InventoryBase;
import net.minecraft.item.ItemInstance;
import pl.lijek.mobworks.items.MobGrinderUpgrade;

public class UpgradeSlot extends Slot {
    public UpgradeSlot(InventoryBase inventory, int index, int x, int y) {
        super(inventory, index, x, y);
    }

    @Override
    public boolean canInsert(ItemInstance arg) {
        return arg.getType() instanceof MobGrinderUpgrade;
    }
}
