package pl.lijek.mobworks.events.init;

import net.mine_diver.unsafeevents.listener.EventListener;
import net.modificationstation.stationapi.api.client.event.texture.TextureRegisterEvent;
import net.modificationstation.stationapi.api.client.texture.atlas.Atlas;
import net.modificationstation.stationapi.api.client.texture.atlas.Atlases;

public class TextureListener {

    public static int mobGrinderFront, mobGrinderSide, mobGrinderSideNoSword,
                        cursedEarthTop, cursedEarthSide,
                        vacuumMachine;
    public static Atlas.Texture blankUpgrade;

    public int getItemTexture(String fileName){
        return Atlases.getStationGuiItems().addTexture("/assets/mobworks/textures/item/"+fileName).index;
    }

    public Atlas.Texture getItemAtlasTexture(String fileName){
        return Atlases.getStationGuiItems().addTexture("/assets/mobworks/textures/item/"+fileName);
    }

    public int getBlockTexture(String fileName){
        return Atlases.getStationTerrain().addTexture("/assets/mobworks/textures/block/"+fileName).index;
    }

    public Atlas.Texture getBlockAtlasTexture(String fileName){
        return Atlases.getStationTerrain().addTexture("/assets/mobworks/textures/block/"+fileName);
    }


    @EventListener
    public void registerTextures(TextureRegisterEvent event) {
        blankUpgrade = getItemAtlasTexture("blankUpgrade.png");

        ItemListener.coolItem.setTexturePosition(getItemTexture("coolItem.png"));
        ItemListener.spawnEgg.setBackTexture(getItemAtlasTexture("spawnEgg.png"));
        ItemListener.spawnEgg.setOverlayTexture(getItemAtlasTexture("dots.png"));

        ItemListener.mobGrinderUpgrade.baseTexture = blankUpgrade;
        ItemListener.mobGrinderUpgrade.textures.add(getItemAtlasTexture("damageUpgrade0.png"));
        ItemListener.mobGrinderUpgrade.textures.add(getItemAtlasTexture("damageUpgrade1.png"));
        ItemListener.mobGrinderUpgrade.textures.add(getItemAtlasTexture("damageUpgrade2.png"));
        ItemListener.mobGrinderUpgrade.textures.add(getItemAtlasTexture("grinderRangeUpgrade0.png"));
        ItemListener.mobGrinderUpgrade.textures.add(getItemAtlasTexture("grinderRangeUpgrade1.png"));
        ItemListener.mobGrinderUpgrade.textures.add(getItemAtlasTexture("grinderRangeUpgrade2.png"));

        ItemListener.spawnerUpgrade.textures[0] = getItemTexture("redstoneUpgrade.png");
        ItemListener.spawnerUpgrade.textures[1] = getItemTexture("countUpgrade.png");
        ItemListener.spawnerUpgrade.textures[2] = getItemTexture("countDowngrade.png");
        ItemListener.spawnerUpgrade.textures[3] = getItemTexture("entitiesToSpawnUpgrade.png");
        ItemListener.spawnerUpgrade.textures[4] = getItemTexture("entitiesToSpawnDowngrade.png");
        ItemListener.spawnerUpgrade.textures[5] = getItemTexture("rangeUpgrade.png");
        ItemListener.spawnerUpgrade.textures[6] = getItemTexture("rangeDowngrade.png");

        BlockListener.slowConveyor.setAtlasTexture(getBlockAtlasTexture("slowConveyor.png"));
        BlockListener.moderateConveyor.setAtlasTexture(getBlockAtlasTexture("moderateConveyor.png"));
        BlockListener.fastConveyor.setAtlasTexture(getBlockAtlasTexture("fastConveyor.png"));

        mobGrinderFront = getBlockTexture("mobGrinderFront.png");
        mobGrinderSide = getBlockTexture("mobGrinderSide.png");
        mobGrinderSideNoSword = getBlockTexture("mobGrinderSideNoSword.png");

        cursedEarthTop = getBlockTexture("cursedEarthTop.png");
        cursedEarthSide = getBlockTexture("cursedEarthSide.png");

        vacuumMachine = getBlockTexture("vacuumMachine.png");
    }
}
