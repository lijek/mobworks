package pl.lijek.mobworks.block;

import net.minecraft.block.material.Material;
import net.minecraft.entity.Item;
import net.minecraft.entity.Living;
import net.minecraft.entity.player.PlayerBase;
import net.minecraft.inventory.InventoryBase;
import net.minecraft.item.ItemInstance;
import net.minecraft.level.BlockView;
import net.minecraft.level.Level;
import net.minecraft.tileentity.TileEntityBase;
import net.minecraft.util.maths.MathHelper;
import net.modificationstation.stationapi.api.gui.screen.container.GuiHelper;
import net.modificationstation.stationapi.api.registry.Identifier;
import net.modificationstation.stationapi.api.template.block.TemplateBlockWithEntity;
import pl.lijek.mobworks.gui.MobGrinderContainer;
import pl.lijek.mobworks.tileentity.TileEntityMobGrinder;

import java.util.Random;

import static pl.lijek.mobworks.Mobworks.MOD_ID;
import static pl.lijek.mobworks.events.init.TextureListener.*;

public class MobGrinder extends TemplateBlockWithEntity {
    private final Random random = new Random();
    private final int[] front = {2, 5, 3, 4};
    private final int[] opposite = {2, 3, 0, 1};

    public MobGrinder(Identifier identifier) {
        super(identifier, Material.METAL);

        setBlastResistance(2000.0F);
    }

    @Override
    public void afterPlaced(Level level, int x, int y, int z, Living living) {
        int meta = MathHelper.floor((double)(living.yaw * 4.0F / 360.0F) + 0.5D) & 3;
        level.setTileMeta(x, y, z, meta);
    }

    public int getTextureForSide(int side) {
        if (side == 1 || side == 0) {
            return mobGrinderSideNoSword;
        } else {
            return side == 3 ? mobGrinderFront : mobGrinderSide;
        }
    }

    @Override
    public int getTextureForSide(BlockView tileView, int x, int y, int z, int side) {
        if (side == 1 || side == 0) {
            return mobGrinderSideNoSword;
        } else {
            int meta = tileView.getTileMeta(x, y, z);
            if (side == front[meta]) {
                return mobGrinderFront;
            }else if(side == front[opposite[meta]]){
                return mobGrinderSideNoSword;
            }

            return mobGrinderSide;
        }
    }

    @Override
    protected TileEntityBase createTileEntity() {
        return new TileEntityMobGrinder();
    }

    public boolean canUse(Level level, int x, int y, int z, PlayerBase player) {
        TileEntityBase tileEntityMobGrinder = level.getTileEntity(x, y, z);
        if (tileEntityMobGrinder instanceof TileEntityMobGrinder)
            GuiHelper.openGUI(player, Identifier.of(MOD_ID, "mobGrinder"), (InventoryBase) tileEntityMobGrinder, new MobGrinderContainer(player.inventory, (TileEntityMobGrinder) tileEntityMobGrinder));
        return true;
    }

    @Override
    public void onBlockRemoved(Level level, int x, int y, int z) {
        TileEntityMobGrinder mobGrinder = (TileEntityMobGrinder)level.getTileEntity(x, y, z);

        for(int i = 0; i < mobGrinder.getInventorySize(); ++i) {
            ItemInstance item = mobGrinder.getInventoryItem(i);
            if (item != null) {
                float var8 = this.random.nextFloat() * 0.8F + 0.1F;
                float var9 = this.random.nextFloat() * 0.8F + 0.1F;
                float var10 = this.random.nextFloat() * 0.8F + 0.1F;

                Item itemEntity = new Item(level, (float)x + var8, (float)y + var9, (float)z + var10, new ItemInstance(item.itemId, item.count, item.getDamage()));
                float var13 = 0.05F;
                itemEntity.velocityX = (float)this.random.nextGaussian() * var13;
                itemEntity.velocityY = (float)this.random.nextGaussian() * var13 + 0.2F;
                itemEntity.velocityZ = (float)this.random.nextGaussian() * var13;
                level.spawnEntity(itemEntity);
            }
        }

        super.onBlockRemoved(level, x, y, z);
    }
}
