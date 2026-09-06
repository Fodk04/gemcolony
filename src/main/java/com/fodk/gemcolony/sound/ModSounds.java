package com.fodk.gemcolony.sound;

import com.fodk.gemcolony.GemColony;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.JukeboxSong;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModSounds {
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS =
            DeferredRegister.create(BuiltInRegistries.SOUND_EVENT, GemColony.MOD_ID);

    public static final Supplier<SoundEvent> GEM_POOF = SOUND_EVENTS.register("gem_poof",
            () -> SoundEvent.createVariableRangeEvent(Identifier.fromNamespaceAndPath(GemColony.MOD_ID, "gem_poof")));

    public static final DeferredHolder<SoundEvent, SoundEvent> LOVE_LIKE_YOU = registerJukeboxSong("love_like_you");
    public static final ResourceKey<JukeboxSong> LOVE_LIKE_YOU_KEY = createSong("love_like_you");

    public static final DeferredHolder<SoundEvent, SoundEvent> ITS_OVER_ISNT_IT = registerJukeboxSong("its_over_isnt_it");
    public static final ResourceKey<JukeboxSong> ITS_OVER_ISNT_IT_KEY = createSong("its_over_isnt_it");


    private static ResourceKey<JukeboxSong> createSong(String name) {
        return ResourceKey.create(Registries.JUKEBOX_SONG, Identifier.fromNamespaceAndPath(GemColony.MOD_ID, name));
    }

    private static DeferredHolder<SoundEvent, SoundEvent> registerJukeboxSong(String name) {
        return SOUND_EVENTS.register(name, () -> SoundEvent.createVariableRangeEvent(Identifier.fromNamespaceAndPath(GemColony.MOD_ID, name)));
    }

    public static void register(IEventBus eventBus) {
        SOUND_EVENTS.register(eventBus);
    }
}
