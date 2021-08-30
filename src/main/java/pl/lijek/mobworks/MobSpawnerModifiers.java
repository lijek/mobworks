package pl.lijek.mobworks;

public interface MobSpawnerModifiers {
    void setRequiresRedstone(boolean value);
    boolean getRequiresRedstone();

    int getMaxEntitiesNearby();
    void setMaxEntitiesNearby(int count);

    int getEntitiesToSpawn();
    void setEntitiesToSpawn(int count);

    int getSpawnRange();
    void setSpawnRange(int range);
}
