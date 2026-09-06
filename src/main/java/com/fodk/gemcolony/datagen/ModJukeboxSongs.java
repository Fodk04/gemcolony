package com.fodk.gemcolony.datagen;

import com.fodk.gemcolony.sound.ModSounds;
import net.minecraft.core.Holder;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.Util;
import net.minecraft.world.item.JukeboxSong;

public class ModJukeboxSongs {

    public static void bootstrap(BootstrapContext<JukeboxSong> context) {
        register(context, ModSounds.LOVE_LIKE_YOU_KEY, ((Holder.Reference<SoundEvent>) ModSounds.LOVE_LIKE_YOU.getDelegate()), 126, 15);
        register(context, ModSounds.ITS_OVER_ISNT_IT_KEY, ((Holder.Reference<SoundEvent>) ModSounds.ITS_OVER_ISNT_IT.getDelegate()), 141, 15);

    }

    private static void register(BootstrapContext<JukeboxSong> context, ResourceKey<JukeboxSong> key,
                                 Holder.Reference<SoundEvent> soundEvent, int lengthInSeconds, int comparatorOutput) {
        context.register(key, new JukeboxSong(soundEvent,
                Component.translatable(Util.makeDescriptionId("jukebox_song", key.identifier())), lengthInSeconds, comparatorOutput));
    }
}
