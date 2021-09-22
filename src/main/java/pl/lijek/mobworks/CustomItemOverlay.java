package pl.lijek.mobworks;

import net.minecraft.item.ItemInstance;
import net.modificationstation.stationapi.api.client.texture.atlas.Atlas;

public interface CustomItemOverlay {
    TextureRGB getTextureAndColorForRender(ItemInstance item);

    Atlas.Texture getBaseTexture();
}
