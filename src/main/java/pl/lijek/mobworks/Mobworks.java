package pl.lijek.mobworks;


import net.modificationstation.stationapi.api.mod.entrypoint.Entrypoint;
import net.modificationstation.stationapi.api.registry.ModID;
import net.modificationstation.stationapi.api.util.Null;

public class Mobworks {

    @Entrypoint.Instance
    public static final Mobworks INSTANCE = Null.get();

    @Entrypoint.ModID
    public static final ModID MOD_ID = Null.get();
}
