package com.fodk.gemcolony.datagen;

import com.fodk.gemcolony.GemColony;
import com.fodk.gemcolony.sound.ModSounds;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.Identifier;
import net.neoforged.neoforge.common.data.SoundDefinitionsProvider;


public class ModSoundsProvider extends SoundDefinitionsProvider {

    public ModSoundsProvider(PackOutput output) {
        super(output, GemColony.MOD_ID);
    }

    @Override
    public void registerSounds() {
        //sounds
        add(ModSounds.GEM_POOF.get(), definition().subtitle("sounds.gemcolony.gem_poof")
                .with(sound(Identifier.fromNamespaceAndPath(GemColony.MOD_ID, "gem_poof"))));

        //jukebox
        add(ModSounds.LOVE_LIKE_YOU.get(), definition().subtitle("sounds.gemcolony.love_like_you")
                .with(sound(Identifier.fromNamespaceAndPath(GemColony.MOD_ID, "love_like_you")).stream()));
        add(ModSounds.ITS_OVER_ISNT_IT.get(), definition().subtitle("sounds.gemcolony.its_over_isnt_it")
                .with(sound(Identifier.fromNamespaceAndPath(GemColony.MOD_ID, "its_over_isnt_it")).stream()));

    }
}
