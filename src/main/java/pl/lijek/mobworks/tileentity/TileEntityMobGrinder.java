package pl.lijek.mobworks.tileentity;

import lombok.Getter;
import lombok.Setter;
import net.minecraft.entity.EntityBase;
import net.minecraft.entity.Living;
import net.minecraft.entity.player.PlayerBase;
import net.minecraft.item.ItemInstance;
import net.minecraft.tileentity.TileEntityBase;
import net.minecraft.util.io.CompoundTag;
import net.minecraft.util.maths.Box;
import pl.lijek.mobworks.ImplementedInventory;
import pl.lijek.mobworks.items.MobGrinderUpgrade;

import java.util.List;

public class TileEntityMobGrinder extends TileEntityBase implements ImplementedInventory {
    private ItemInstance[] inventory = new ItemInstance[9];

    private static final int baseDamage = 1;
    private static final int baseRange = 1;

    @Getter
    @Setter
    private int range = 3;
    @Getter
    private int maxRange = baseRange;
    @Getter
    @Setter
    private int redstoneMode = 1;
    private int delay = 20;
    @Getter
    private int damage = 1;
    @Setter
    @Getter
    private boolean renderingWorkingRange = false;

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
        return "Mob grinder";
    }

    @Override
    public void writeIdentifyingData(CompoundTag tag) {
        super.writeIdentifyingData(tag);
        writeInventoryData(tag);
        tag.put("Range", range);
        tag.put("RenderWorkingRange", renderingWorkingRange);
        tag.put("RedstoneMode", (byte) redstoneMode);
        tag.put("Damage", (byte) damage);
    }

    @Override
    public void readIdentifyingData(CompoundTag tag) {
        super.readIdentifyingData(tag);
        readInventoryData(tag);
        range = tag.getInt("Range");
        renderingWorkingRange = tag.getBoolean("RenderWorkingRange");
        redstoneMode = tag.getByte("RedstoneMode");
        damage = tag.getByte("Damage");
    }

    @Override
    public boolean canPlayerUse(PlayerBase player) {
        if (this.level.getTileEntity(this.x, this.y, this.z) != this) {
            return false;
        } else {
            return !(player.squaredDistanceTo((double)this.x + 0.5D, (double)this.y + 0.5D, (double)this.z + 0.5D) > 64.0D);
        }
    }

    @Override
    public void tick() {
        if(delay > 0) {
            delay--;
            return;
        }
        if(delay == 0)
            delay = 20;

        //redstone mode active
        if(redstoneMode == 1 ){
            if(!level.hasRedstonePower(x, y, z))
                return;
        }
        //redstone mode inverted
        else if(redstoneMode == 2){
            if(level.hasRedstonePower(x, y, z))
                return;
        }

        int[] sizesAndOffsets = getSizesAndOffsets();
        int x1 = x + sizesAndOffsets[2];
        int x2 = x1 + sizesAndOffsets[0];

        int y1 = y;
        int y2 = y + 1;

        int z1 = z + sizesAndOffsets[3];
        int z2 = z1 + sizesAndOffsets[1];

        Box hitArea = Box.create(Math.min(x1, x2), Math.min(y1, y2), Math.min(z1, z2),
                Math.max(x1, x2), Math.max(y1, y2), Math.max(z1, z2));

        List entitiesToHurt = level.getEntities((EntityBase) null, hitArea);
        for (Object entity : entitiesToHurt) {
            if (!(entity instanceof Living))
                continue;
            ((EntityBase)entity).damage(((EntityBase)entity), damage);
        }
    }

    public int getFacing(){
        return level.getTileMeta(x, y, z);
    }

    public int[] getSizesAndOffsets(){
        switch(getFacing()){
            case 0:
                return new int[]{getRange(), -getRange(), (int) -Math.floor(((float)getRange())/2), 0};
            case 1:
                return new int[]{getRange(), getRange(), 1, (int) -Math.floor(((float) getRange()) / 2)};
            case 2:
                return new int[]{getRange(), getRange(), (int) -Math.floor(((float)getRange())/2), 1};
            case 3:
                return new int[]{-getRange(), -getRange(), 0, (int) Math.ceil(((float)getRange())/2)};
            default:
                return new int[]{0, 0, 0, 0};
        }
    }

    @Override
    public void markDirty() {
        super.markDirty();
        damage = baseDamage;
        maxRange = baseRange;
        for(ItemInstance item : inventory){
            if(item == null)
                continue;
            if (!(item.getType() instanceof MobGrinderUpgrade))
                continue;
            switch (item.getDamage()){
                case 0:
                    damage += 2;
                    break;
                case 1:
                    damage += 4;
                    break;
                case 2:
                    damage += 6;
                    break;
                case 3:
                    maxRange = 3;
                    break;
                case 4:
                    maxRange = 9;
                    break;
                case 5:
                    maxRange = 27;
                    break;
            }

            if(range > maxRange)
                range = maxRange;
        }
    }
}
