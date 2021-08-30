package pl.lijek.mobworks.block;

import net.minecraft.block.material.Material;
import net.minecraft.client.render.TileRenderer;
import net.minecraft.entity.Living;
import net.minecraft.level.Level;
import net.minecraft.level.TileView;
import net.minecraft.util.maths.MathHelper;
import net.modificationstation.stationapi.api.client.model.BlockWithWorldRenderer;
import net.modificationstation.stationapi.api.registry.Identifier;
import net.modificationstation.stationapi.api.template.block.TemplateBlockBase;

public class MobGrinder extends TemplateBlockBase implements BlockWithWorldRenderer {
    public int[] textures = new int[3];
    private final int[] magic = {2, 5, 3, 4};

    public MobGrinder(Identifier identifier) {
        super(identifier, Material.METAL);
        /*textures[0] = 4;
        textures[1] = 5;
        textures[2] = 6;*/
    }

    @Override
    public void afterPlaced(Level level, int x, int y, int z, Living living) {
        int meta = magic[MathHelper.floor((double)(living.yaw * 4.0F / 360.0F) + 0.5D) & 3];
        level.setTileMeta(x, y, z, meta);
    }

    public int getTextureForSide(int side) {
        if (side == 1) {
            return textures[1];
        } else if (side == 0) {
            return textures[1];
        } else {
            return side == 3 ? textures[2] : textures[0];
        }
    }

    @Override
    public int getTextureForSide(TileView tileView, int x, int y, int z, int side) {
        int meta = tileView.getTileMeta(x, y, z);
        if (side == 1) {
            return textures[1];
        } else if (side == 0) {
            return textures[1];
        } else {
            return side != meta ? textures[2] : textures[0];
        }
        //return textures[0];
    }

    @Override
    public void renderWorld(TileRenderer tileRenderer, TileView tileView, int x, int y, int z) {
        tileRenderer.method_76(this, x, y, z);
    }
}
