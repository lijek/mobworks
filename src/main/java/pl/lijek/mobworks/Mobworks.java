package pl.lijek.mobworks;


import net.minecraft.client.resource.language.TranslationStorage;
import net.modificationstation.stationapi.api.mod.entrypoint.Entrypoint;
import net.modificationstation.stationapi.api.registry.ModID;
import net.modificationstation.stationapi.api.util.Null;

import java.util.Locale;

public class Mobworks {

    @Entrypoint.Instance
    public static final Mobworks INSTANCE = Null.get();

    @Entrypoint.ModID
    public static final ModID MOD_ID = Null.get();

    public static String translateKey(String key){
        String result = ( "" + TranslationStorage.getInstance().method_995(key)).trim();
        return result.isEmpty() ? key : result;
    }

    public static String translateTooltip(String key){
        return translateKey(String.format(Locale.ROOT, "tooltip.%s:%s", MOD_ID, key));
    }

    public static String translateText(String key){
        return translateKey(String.format(Locale.ROOT, "text.%s:%s", MOD_ID, key));
    }
}
