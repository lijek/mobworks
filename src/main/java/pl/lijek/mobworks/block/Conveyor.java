package pl.lijek.mobworks.block;

import net.minecraft.block.material.Material;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.block.BlockRenderer;
import net.minecraft.entity.EntityBase;
import net.minecraft.entity.Living;
import net.minecraft.level.BlockView;
import net.minecraft.level.Level;
import net.minecraft.util.maths.Box;
import net.minecraft.util.maths.MathHelper;
import net.minecraft.util.maths.Vec3f;
import net.modificationstation.stationapi.api.client.model.block.BlockWithWorldRenderer;
import net.modificationstation.stationapi.api.client.texture.atlas.Atlas;
import net.modificationstation.stationapi.api.registry.Identifier;
import net.modificationstation.stationapi.api.template.block.TemplateBlockBase;
import paulevs.creative.CreativePlayer;

public class Conveyor extends TemplateBlockBase implements BlockWithWorldRenderer {
    public final double speed;
    public Atlas.Sprite atlasTexture;
    public Conveyor(Identifier identifier, double speed) {
        super(identifier, Material.WOOL);
        setBoundingBox(0.0F, 0.0F, 0.0F, 1.0F, 0.120F, 1.0F);
        this.speed = speed;
    }

    public void setAtlasTexture(Atlas.Sprite atlasTexture){
        this.atlasTexture = atlasTexture;
        this.texture = atlasTexture.index;
    }

    @Override
    public boolean canPlaceAt(Level level, int x, int y, int z) {
        return level.canSuffocate(x, y - 1, z);
    }

    public void afterPlaced(Level level, int x, int y, int z, Living living) {
        int meta = MathHelper.floor((double)(living.yaw * 4.0F / 360.0F) + 0.5D) & 3;
        level.setTileMeta(x, y, z, meta);
    }

    @Override
    public boolean canGrow(Level level, int x, int y, int z) {
        return level.canSuffocate(x, y - 1, z) && super.canGrow(level, x, y, z);
    }

    @Override
    public void onAdjacentBlockUpdate(Level level, int x, int y, int z, int id) {
        if (!this.canGrow(level, x, y, z)) {
            this.drop(level, x, y, z, level.getTileMeta(x, y, z));
            level.setTile(x, y, z, 0);
        }
    }

    @Override
    public void onEntityCollision(Level level, int x, int y, int z, EntityBase entityBase) {
        if(entityBase instanceof CreativePlayer)
            if(((CreativePlayer)entityBase).isFlying())
                return;
        Vec3f velocity = getEntityVelocityModifier(level, x, y, z);
        entityBase.accelerate(velocity.x * speed, 0, velocity.z * speed);
    }

    @Override
    public Box getCollisionShape(Level level, int x, int y, int z) {
        return null;
    }

    private Vec3f getEntityVelocityModifier(Level level, int x, int y, int z) {
        int facing = level.getTileMeta(x, y, z);
        Vec3f out = Vec3f.from(0, 0, 0);
        switch(facing){
            case 0:
                out.z++;
                break;
            case 1:
                out.x--;
                break;
            case 2:
                out.z--;
                break;
            case 3:
                out.x++;
                break;
        }
        return out;
    }

    public boolean isFullOpaque() {
        return false;
    }

    public boolean isFullCube() {
        return false;
    }

    @Override
    public boolean renderWorld(BlockRenderer tileRenderer, BlockView level, int x, int y, int z) {
        int meta = level.getTileMeta(x, y, z) & 3;
        tileRenderer.renderStandardBlock(this, x, y, z);
        Tessellator tessellator = atlasTexture.getAtlas().getTessellator();
        float brightness = this.getBrightness(level, x, y, z);
        tessellator.colour(brightness, brightness, brightness);
        //int var20 = this.getTextureForSide(0);
        /*int textureX = (var20 & 15) << 4;
        int textureY = var20 & 240;
        double minTextureX = ((float)textureX / 256.0F);
        double maxTextureX = (((float)textureX + 15.99F) / 256.0F);
        double minTextureY = ((float)textureY / 256.0F);
        double maxTextureY = (((float)textureY + 15.99F) / 256.0F);*/
        double minTextureX = atlasTexture.getStartU();
        double maxTextureX = atlasTexture.getEndU();
        double minTextureY = atlasTexture.getStartV();
        double maxTextureY = atlasTexture.getEndV();
        float height = 0.125F;
        float var32 = (float)(x + 1);
        float var33 = (float)(x + 1);
        float var34 = (float)(x + 0);
        float var35 = (float)(x + 0);
        float var36 = (float)(z + 0);
        float var37 = (float)(z + 1);
        float var38 = (float)(z + 1);
        float var39 = (float)(z + 0);
        float var40 = (float)y + height;
        if (meta == 2) {
            var32 = var33 = (float)(x + 0);
            var34 = var35 = (float)(x + 1);
            var36 = var39 = (float)(z + 1);
            var37 = var38 = (float)(z + 0);
        } else if (meta == 3) {
            var32 = var35 = (float)(x + 0);
            var33 = var34 = (float)(x + 1);
            var36 = var37 = (float)(z + 0);
            var38 = var39 = (float)(z + 1);
        } else if (meta == 1) {
            var32 = var35 = (float)(x + 1);
            var33 = var34 = (float)(x + 0);
            var36 = var37 = (float)(z + 1);
            var38 = var39 = (float)(z + 0);
        }

        tessellator.vertex(var35, var40, var39, minTextureX, minTextureY);
        tessellator.vertex(var34, var40, var38, minTextureX, maxTextureY);
        tessellator.vertex(var33, var40, var37, maxTextureX, maxTextureY);
        tessellator.vertex(var32, var40, var36, maxTextureX, minTextureY);
        return false;
    }
}
