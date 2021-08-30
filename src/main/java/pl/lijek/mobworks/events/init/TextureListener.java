package pl.lijek.mobworks.events.init;

import net.mine_diver.unsafeevents.listener.EventListener;
import net.modificationstation.stationapi.api.client.event.texture.TextureRegisterEvent;
import net.modificationstation.stationapi.api.client.texture.atlas.Atlas;
import net.modificationstation.stationapi.api.client.texture.atlas.ExpandableAtlas;
import pl.lijek.mobworks.Mobworks;

import java.util.Arrays;

import static net.modificationstation.stationapi.api.registry.Identifier.of;

public class TextureListener {

    public static ExpandableAtlas TEXTURES;

    public int getItemTexture(String fileName){
        Atlas.Texture texture = ExpandableAtlas.STATION_GUI_ITEMS.addTexture("/assets/mobworks/textures/"+fileName);
        return texture.index;
        //return TextureFactory.INSTANCE.addTexture(TextureRegistry.getRegistry("GUI_ITEMS"), "/assets/mobworks/textures/"+fileName);
    }

    public int getBlockTexture(String fileName){
        //Atlas.Texture texture = TEXTURES.addTexture("/assets/mobworks/textures/"+fileName);
        return ExpandableAtlas.STATION_TERRAIN.addTexture("/assets/mobworks/textures/"+fileName).index;
    }

    public int getTextureId(String fileName){
        Atlas.Texture texture = TEXTURES.addTexture("/assets/mobworks/textures/"+fileName);
        return texture.index;
    }

    public Atlas.Texture getBlockAtlasTexture(String fileName){
        return ExpandableAtlas.STATION_TERRAIN.addTexture("/assets/mobworks/textures/"+fileName);
    }


    @EventListener
    public void registerTextures(TextureRegisterEvent event) {
        TEXTURES = new ExpandableAtlas(of(Mobworks.MOD_ID, "textures"));

        ItemListener.coolItem.setTexturePosition(getItemTexture("coolitem.png"));
        ItemListener.spawnEgg.setTexturePosition(getItemTexture("spawnEgg.png"));
        ItemListener.spawnEgg.setOverlayTexturePosition(getItemTexture("dots.png"));

        ItemListener.spawnerUpgrade.textures[0] = getItemTexture("redstoneUpgrade.png");
        ItemListener.spawnerUpgrade.textures[1] = getItemTexture("countUpgrade.png");
        ItemListener.spawnerUpgrade.textures[2] = getItemTexture("countDowngrade.png");
        ItemListener.spawnerUpgrade.textures[3] = getItemTexture("entitiesToSpawnUpgrade.png");
        ItemListener.spawnerUpgrade.textures[4] = getItemTexture("entitiesToSpawnDowngrade.png");
        ItemListener.spawnerUpgrade.textures[5] = getItemTexture("rangeUpgrade.png");
        ItemListener.spawnerUpgrade.textures[6] = getItemTexture("rangeDowngrade.png");

        /*BlockListener.slowConveyor.texture      = getBlockTexture("slowConveyor.png");
        BlockListener.moderateConveyor.texture  = getBlockTexture("moderateConveyor.png");
        BlockListener.fastConveyor.texture      = getBlockTexture("fastConveyor.png");*/

        //BlockListener.slowConveyor.texture      = ExpandableAtlas.STATION_TERRAIN.addTexture("/assets/mobworks/textures/slowConveyor.png").index;
        BlockListener.slowConveyor.setAtlasTexture(getBlockAtlasTexture("slowConveyor.png"));
        BlockListener.moderateConveyor.setAtlasTexture(getBlockAtlasTexture("moderateConveyor.png"));
        BlockListener.fastConveyor.setAtlasTexture(getBlockAtlasTexture("fastConveyor.png"));

        /*BlockListener.grinder.textures[0] = getBlockTexture("mobGrinderFront.png");
        BlockListener.grinder.textures[1] = getBlockTexture("mobGrinderTopAndBottom.png");
        BlockListener.grinder.textures[2] = getBlockTexture("mobGrinderSide.png");*/
        //BlockListener.grinder.texture = getBlockTexture("mobGrinderTopAndBottom.png");

        /*BlockListener.grinder.textures[0] = getBlockTexture("slowConveyor.png");
        BlockListener.grinder.textures[1] = getBlockTexture("moderateConveyor.png");
        BlockListener.grinder.textures[2] = getBlockTexture("fastConveyor.png");*/

        System.out.println(BlockListener.grinder.textures[0] = getBlockTexture("mobGrinderSide.png"));
        System.out.println(BlockListener.grinder.textures[1] = getBlockTexture("mobGrinderTopAndBottom.png"));
        System.out.println(BlockListener.grinder.textures[2] = getBlockTexture("mobGrinderFront.png"));

        System.out.println(Arrays.toString(BlockListener.grinder.textures));
    }
}
