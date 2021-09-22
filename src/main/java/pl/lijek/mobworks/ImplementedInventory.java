package pl.lijek.mobworks;

import net.minecraft.entity.player.PlayerBase;
import net.minecraft.inventory.InventoryBase;
import net.minecraft.item.ItemInstance;
import net.minecraft.util.io.CompoundTag;
import net.minecraft.util.io.ListTag;

public interface ImplementedInventory extends InventoryBase {
    ItemInstance[] getInventory();
    void setInventory(ItemInstance[] inventory);

    @Override
    String getContainerName();

    @Override
    default int getInventorySize() {
        return getInventory().length;
    }

    @Override
    default ItemInstance getInventoryItem(int index) {
        return getInventory()[index];
    }

    @Override
    default ItemInstance takeInventoryItem(int index, int count) {
        if (getInventory()[index] != null) {
            ItemInstance var3;
            if (getInventory()[index].count <= count) {
                var3 = getInventory()[index];
                getInventory()[index] = null;
                this.markDirty();
                return var3;
            } else {
                var3 = getInventory()[index].split(count);
                if (getInventory()[index].count == 0) {
                    getInventory()[index] = null;
                }

                this.markDirty();
                return var3;
            }
        } else {
            return null;
        }
    }

    @Override
    default void setInventoryItem(int slot, ItemInstance arg) {
        getInventory()[slot] = arg;
        if (arg != null && arg.count > this.getMaxItemCount()) {
            arg.count = this.getMaxItemCount();
        }

        this.markDirty();
    }


    @Override
    default int getMaxItemCount() {
        return 64;
    }

    @Override
    default void markDirty() {
        // NO-OP
    }

    @Override
    default boolean canPlayerUse(PlayerBase player) {
        return true;
    }

    default void readInventoryData(CompoundTag tag){
        ListTag var2 = tag.getListTag("Items");
        setInventory(new ItemInstance[this.getInventorySize()]);

        for(int var3 = 0; var3 < var2.size(); ++var3) {
            CompoundTag var4 = (CompoundTag)var2.get(var3);
            byte var5 = var4.getByte("Slot");
            if (var5 >= 0 && var5 < getInventory().length) {
                getInventory()[var5] = new ItemInstance(var4);
            }
        }
    }

    default void writeInventoryData(CompoundTag tag){
        ListTag var2 = new ListTag();
        for(int var3 = 0; var3 < getInventory().length; ++var3) {
            if (getInventory()[var3] != null) {
                CompoundTag var4 = new CompoundTag();
                var4.put("Slot", (byte)var3);
                getInventory()[var3].toTag(var4);
                var2.add(var4);
            }
        }

        tag.put("Items", var2);
    }

}