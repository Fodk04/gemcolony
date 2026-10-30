package com.fodk.gemcolony.modonomicon;

import com.klikli_dev.modonomicon.api.datagen.LanguageProviderCache;
import com.fodk.gemcolony.GemColony;
import net.neoforged.neoforge.common.data.LanguageProvider;
import net.minecraft.data.PackOutput;

public class GemColonyBookLanguageProvider extends LanguageProvider {

    private final LanguageProviderCache languageCache;

    public GemColonyBookLanguageProvider(PackOutput output, LanguageProviderCache languageCache) {
        super(output, GemColony.MOD_ID, "en_us");
        this.languageCache = languageCache;
    }

    @Override
    protected void addTranslations() {
        this.languageCache.data(this::add);
    }
}

