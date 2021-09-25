package pl.lijek.mobworks;

import lombok.RequiredArgsConstructor;
import net.modificationstation.stationapi.api.client.texture.atlas.Atlas;
import pl.lijek.mobworks.events.init.TextureListener;

@RequiredArgsConstructor
public class TextureRGB {
    public static final TextureRGB BLANK = new TextureRGB(TextureListener.blankUpgrade, 255, 255, 255);

    public final Atlas.Sprite texture;
    public final int
                r,
                g,
                b;
}
