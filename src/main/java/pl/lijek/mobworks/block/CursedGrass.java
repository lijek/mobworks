package pl.lijek.mobworks.block;

import net.minecraft.block.BlockBase;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityEntry;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.Living;
import net.minecraft.level.Level;
import net.minecraft.level.biome.Biome;
import net.minecraft.level.chunk.Chunk;
import net.minecraft.util.maths.Vec2i;
import net.modificationstation.stationapi.api.registry.Identifier;
import net.modificationstation.stationapi.api.template.block.TemplateBlockBase;

import java.util.List;
import java.util.Random;

import static pl.lijek.mobworks.events.init.TextureListener.cursedEarthSide;
import static pl.lijek.mobworks.events.init.TextureListener.cursedEarthTop;

public class CursedGrass extends TemplateBlockBase {
    public CursedGrass(Identifier identifier) {
        super(identifier, Material.ORGANIC);
        setSounds(GRASS_SOUNDS);
    }

    public static void spawnEntity(Level level, int x, int y, int z) {
        if (level.getLightLevel(x, y, z) > 8)
            return;
        if (!(level.canSuffocate(x, y - 1, z) && !level.canSuffocate(x, y, z) && !level.getMaterial(x, y, z).isLiquid() && !level.canSuffocate(x, y + 1, z)))
            return;

        Chunk chunk = level.getChunk(x, z);
        Biome biome = level.getBiomeSource().getBiomeInChunk(new Vec2i(chunk.x, chunk.z));
        List spawnList = biome.getSpawnList(EntityType.MONSTER);
        if (spawnList != null && !spawnList.isEmpty()) {
            int maxRarity = 0;

            for (Object object : spawnList) {
                EntityEntry entityEntry = (EntityEntry) object;
                maxRarity += entityEntry.rarity;
            }

            int rarity = level.rand.nextInt(maxRarity);
            EntityEntry entityEntry = (EntityEntry) spawnList.get(0);

            for (Object object : spawnList) {
                EntityEntry entityEntryFromList = (EntityEntry) object;
                rarity -= entityEntryFromList.rarity;
                if (rarity < 0) {
                    entityEntry = entityEntryFromList;
                    break;
                }
            }

            Living monster = null;

            try {
                monster = (Living) entityEntry.entryClass.getConstructor(Level.class).newInstance(level);
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (monster == null)
                return;

            monster.setPositionAndAngles(x + 0.5, y + 0.5, z + 0.5, level.rand.nextFloat() * 360.0F, 0.0F);
            if (monster.canSpawn()) {
                level.spawnEntity(monster);
            }
        }
    }

    @Override
    public int getTextureForSide(int side) {
        if (side == 1) {
            return cursedEarthTop;
        } else if (side == 0) {
            return 2;
        } else {
            return cursedEarthSide;
        }
    }

    /*@Environment(EnvType.CLIENT)
    public int getTextureForSide(TileView tileView, int x, int y, int z, int meta) {
        if (meta == 1) {
            return cursedEarthTop;
        } else if (meta == 0) {
            return 2;
        } else {
            return cursedEarthSide;
        }
    }*/

    /*@Environment(EnvType.CLIENT)
    public int getColourMultiplier(TileView tileView, int x, int y, int z) {
        return 0x3c473b;
    }*/

    @Override
    public void onScheduledTick(Level level, int x, int y, int z, Random rand) {
        spawnEntity(level, x, y + 1, z);
        int nearX = x + rand.nextInt(2) - 1;
        int nearY = y + rand.nextInt(2) - 3;
        int nearZ = z + rand.nextInt(2) - 1;
        if (level.getTileId(nearX, nearY, nearZ) == BlockBase.DIRT.id
                || level.getTileId(nearX, nearY, nearZ) == BlockBase.GRASS.id
            /*&& level.placeTile(nearX, nearY + 1, nearZ) <= 8*/) {
            level.setTile(nearX, nearY, nearZ, this.id);
        }
    }

    public int getDropId(int meta, Random rand) {
        return BlockBase.DIRT.getDropId(0, rand);
    }
}
