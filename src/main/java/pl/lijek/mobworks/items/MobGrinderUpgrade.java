package pl.lijek.mobworks.items;

import net.minecraft.item.ItemInstance;
import net.modificationstation.stationapi.api.client.gui.CustomTooltipProvider;
import net.modificationstation.stationapi.api.client.texture.atlas.Atlas;
import net.modificationstation.stationapi.api.registry.Identifier;
import net.modificationstation.stationapi.api.template.item.TemplateItemBase;
import pl.lijek.mobworks.CustomItemOverlay;
import pl.lijek.mobworks.Mobworks;
import pl.lijek.mobworks.TextureRGB;

import java.util.ArrayList;
import java.util.List;

public class MobGrinderUpgrade extends TemplateItemBase implements CustomItemOverlay, CustomTooltipProvider {

    public List<Atlas.Sprite> textures = new ArrayList<>();
    public Atlas.Sprite baseTexture;

    /*
    * Meta guide
    * 0 - damage upgrade 1 (+2)
    * 1 - damage upgrade 2 (+4)
    * 2 - damage upgrade 3 (+6)
    * 3 - range upgrade (max 3x3)
    * 4 - range upgrade (max 9x9)
    * 5 - range upgrade (max 27x27)
    * */

    public MobGrinderUpgrade(Identifier identifier) {
        super(identifier);
        setHasSubItems(true);
    }

    @Override
    public String getTranslationKey(ItemInstance item) {
        if(item.getDamage() >= textures.size())
            return "";
        return getTranslationKey();
    }

    @Override
    public TextureRGB getTextureAndColorForRender(ItemInstance item) {
        int damage = item.getDamage();
        if(damage > textures.size())
            return TextureRGB.BLANK;
        return new TextureRGB(textures.get(damage), 255, 255, 255);

    }

    @Override
    public Atlas.Sprite getBaseTexture() {
        return baseTexture;
    }

    @Override
    public String[] getTooltip(ItemInstance itemInstance, String originalTooltip) {
        return new String[]{
                originalTooltip,
                Mobworks.translateTooltip("mobGrinderUpgrade" + itemInstance.getDamage())
        };
    }
}
