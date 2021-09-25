package pl.lijek.mobworks.items;

import net.minecraft.block.BlockBase;
import net.minecraft.block.MobSpawner;
import net.minecraft.entity.EntityBase;
import net.minecraft.entity.Living;
import net.minecraft.entity.player.PlayerBase;
import net.minecraft.item.ItemInstance;
import net.minecraft.level.Level;
import net.minecraft.tileentity.TileEntityMobSpawner;
import net.modificationstation.stationapi.api.client.texture.atlas.Atlas.Sprite;
import net.modificationstation.stationapi.api.registry.Identifier;
import net.modificationstation.stationapi.api.template.item.TemplateItemBase;
import org.jetbrains.annotations.NotNull;
import paulevs.creative.CreativePlayer;
import pl.lijek.mobworks.EntityEggList;
import pl.lijek.mobworks.EntityEggProperties;
import pl.lijek.mobworks.Facing;

public class SpawnEgg extends TemplateItemBase{
    public Sprite overlayTexture;
    public Sprite backTexture;

    public SpawnEgg(@NotNull Identifier id) {
        super(id);
        setHasSubItems(true);
    }

    @Override
    public String getTranslationKey(ItemInstance item) {
        return EntityEggList.getEggTranslationKey(item.getDamage());
    }

    public void setOverlayTexture(Sprite texture){
        overlayTexture = texture;
    }

    public void setBackTexture(Sprite itemAtlasTexture) {
        backTexture = itemAtlasTexture;
        setTexturePosition(backTexture.index);
    }

    public int getBackColor(int meta) {
        return EntityEggList.getEggProperties(meta).primaryColor;
    }

    public int getDotsColor(int meta){
        return EntityEggList.getEggProperties(meta).secondaryColor;
    }

    @Override
    public boolean useOnTile(ItemInstance item, PlayerBase player, Level level, int x, int y, int z, int facing) {
        EntityEggProperties props = EntityEggList.getEggProperties(item.getDamage());
        if(props == null)
            return false;

        if(BlockBase.BY_ID[level.getTileId(x, y, z)] instanceof MobSpawner){
            TileEntityMobSpawner entity = (TileEntityMobSpawner) level.getTileEntity(x, y, z);
            if(entity != null){
                entity.setEntityId(props.entityID);
                if(!((CreativePlayer)player).isCreative())
                    item.count--;
                return true;
            }
            return false;
        }

        x += Facing.offsetsXForSide[facing];
        y += Facing.offsetsYForSide[facing];
        z += Facing.offsetsZForSide[facing];

        EntityBase entity = EntityEggList.createEntityFromProps(props, level);
        if(entity != null){
            entity.setPositionAndAngles(x + 0.5, y, z + 0.5, level.rand.nextFloat() * 360F, 0.0F);
            level.spawnEntity(entity);
            if(entity instanceof Living)
                ((Living) entity).method_918();

            if(!((CreativePlayer)player).isCreative())
                item.count--;
        }

        return true;
    }
}
