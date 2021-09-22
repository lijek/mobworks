package pl.lijek.mobworks.tileentity;

import lombok.Getter;
import lombok.Setter;
import net.minecraft.entity.EntityBase;
import net.minecraft.entity.Item;
import net.minecraft.item.ItemInstance;
import net.minecraft.tileentity.TileEntityBase;
import net.minecraft.util.io.CompoundTag;
import net.minecraft.util.maths.Box;
import pl.lijek.mobworks.ImplementedInventory;

import java.util.List;

public class TileEntityVacuumMachine extends TileEntityBase implements ImplementedInventory {
    @Getter
    private final int maxWorkingSize = 9,
            maxOffset = 9;
    private ItemInstance[] inventory = new ItemInstance[5];
    @Getter
    @Setter
    private boolean renderingWorkingRange = false;
    @Getter
    @Setter
    private int offsetX = 0,
            offsetY = 0,
            offsetZ = 0,
            workingSize = maxWorkingSize,
            redstoneMode = 1;

    @Override
    public ItemInstance[] getInventory() {
        return inventory;
    }

    @Override
    public void setInventory(ItemInstance[] inventory) {
        this.inventory = inventory;
    }

    @Override
    public String getContainerName() {
        return "Vacuum hopper";
    }

    @Override
    public void writeIdentifyingData(CompoundTag tag) {
        super.writeIdentifyingData(tag);
        writeInventoryData(tag);
        tag.put("OffsetX", offsetX);
        tag.put("OffsetY", offsetY);
        tag.put("OffsetZ", offsetZ);
        tag.put("WorkingSize", workingSize);
        tag.put("RedstoneMode", redstoneMode);
    }

    @Override
    public void readIdentifyingData(CompoundTag tag) {
        super.readIdentifyingData(tag);
        readInventoryData(tag);
        offsetX = tag.getInt("OffsetX");
        offsetY = tag.getInt("OffsetY");
        offsetZ = tag.getInt("OffsetZ");
        workingSize = tag.getInt("WorkingSize");
        redstoneMode = tag.getInt("RedstoneMode");
    }

    @Override
    public void tick() {
        super.tick();

        Box hitArea = Box.create(this.x + offsetX, this.y + offsetY, this.z + offsetZ,
                this.x + offsetX + workingSize, this.y + offsetY + workingSize, this.z + offsetZ + workingSize);

        List entitiesInArea = level.getEntities((EntityBase) null, hitArea);
        for (Object object : entitiesInArea) {
            if (object instanceof Item) {
                Item itemEntity = (Item) object;
                ItemInstance item = itemEntity.item;
                ItemInstance itemCopy = itemEntity.item.copy();

                itemCopy.count = mergeStacks(item);
                if (itemCopy.count <= 0) {
                    itemEntity.remove();
                }
                itemEntity.item = itemCopy;

            }
        }
    }

    private int mergeStacks(ItemInstance arg) {
        int itemId = arg.itemId;
        int count = arg.count;
        int slot = this.getIdenticalStackSlot(arg);
        if (slot < 0) {
            slot = this.getFirstEmptySlotIndex();
        }

        if (slot < 0) {
            return count;
        } else {
            if (inventory[slot] == null) {
                inventory[slot] = new ItemInstance(itemId, 0, arg.getDamage());
            }

            int smallerItemCount = Math.min(count, inventory[slot].getMaxStackSize() - inventory[slot].count);

            if (smallerItemCount > this.getMaxItemCount() - inventory[slot].count) {
                smallerItemCount = this.getMaxItemCount() - inventory[slot].count;
            }

            if (smallerItemCount == 0) {
                return count;
            } else {
                count -= smallerItemCount;
                ItemInstance inventoryItem = inventory[slot];
                inventoryItem.count += smallerItemCount;
                inventory[slot].cooldown = 5;
                return count;
            }
        }
    }

    private int getIdenticalStackSlot(ItemInstance arg) {
        for(int i = 0; i < inventory.length; ++i) {
            if (inventory[i] != null && inventory[i].itemId == arg.itemId && inventory[i].isStackable() && inventory[i].count < inventory[i].getMaxStackSize() && inventory[i].count < this.getMaxItemCount() && (!inventory[i].usesMeta() || inventory[i].getDamage() == arg.getDamage())) {
                return i;
            }
        }

        return -1;
    }

    private int getFirstEmptySlotIndex() {
        for(int var1 = 0; var1 < inventory.length; ++var1) {
            if (inventory[var1] == null) {
                return var1;
            }
        }

        return -1;
    }
}
