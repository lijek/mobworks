package pl.lijek.mobworks.items;

import net.minecraft.block.BlockBase;
import net.minecraft.block.MobSpawner;
import net.minecraft.client.render.TextRenderer;
import net.minecraft.client.render.entity.ItemRenderer;
import net.minecraft.client.texture.TextureManager;
import net.minecraft.entity.EntityBase;
import net.minecraft.entity.Living;
import net.minecraft.entity.player.PlayerBase;
import net.minecraft.item.ItemInstance;
import net.minecraft.level.Level;
import net.minecraft.tileentity.TileEntityMobSpawner;
import net.modificationstation.stationapi.api.client.gui.CustomItemOverlay;
import net.modificationstation.stationapi.api.registry.Identifier;
import net.modificationstation.stationapi.api.template.item.TemplateItemBase;
import org.jetbrains.annotations.NotNull;
import paulevs.creative.CreativePlayer;
import pl.lijek.mobworks.EntityEggList;
import pl.lijek.mobworks.EntityEggProperties;
import pl.lijek.mobworks.Facing;

public class SpawnEgg extends TemplateItemBase implements CustomItemOverlay {
    public int overlayTexturePosition;

    public SpawnEgg(@NotNull Identifier id) {
        super(id);
        setHasSubItems(true);
    }

    @Override
    public String getTranslationKey(ItemInstance item) {
        return EntityEggList.getEggTranslationKey(item.getDamage());
    }

    @Override
    public void renderItemOverlay(ItemRenderer itemRenderer, int x, int y, ItemInstance itemInstance, TextRenderer textRenderer, TextureManager textureManager) {
        //textureManager.bindTexture(textureManager.getTextureId("/assets/mobworks/textures/dots.png"));
        /*textureManager.bindTexture(overlayTexturePosition);
        Tessellator tessellator = Tessellator.INSTANCE;
        x-= 2; y-=2;
        tessellator.start();
        tessellator.colour(getSecondaryNameColor(itemInstance.getDamage()));
        tessellator.addVertex(x + 0, y + 0, 0.0D);
        tessellator.addVertex(x + 0, y + 16, 0.0D);
        tessellator.addVertex(x + 16, y + 16, 0.0D);
        tessellator.addVertex(x + 16, y + 0, 0.0D);
        tessellator.draw();*/
        textRenderer.drawTextWithShadow(String.valueOf(itemInstance.getDamage()), x, y, 0xffffff);
    }

    public void setOverlayTexturePosition(int texturePosition){
        overlayTexturePosition = texturePosition;
    }

    @Override
    public int getNameColour(int meta) {
        return EntityEggList.getEggProperties(meta).primaryColor;
    }

    public int getSecondaryNameColor(int meta){
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
