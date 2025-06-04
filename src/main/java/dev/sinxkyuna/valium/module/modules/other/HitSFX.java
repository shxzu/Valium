package dev.sinxkyuna.valium.module.modules.other;

import dev.sinxkyuna.valium.event.bus.Listener;
import dev.sinxkyuna.valium.event.bus.annotations.EventLink;
import dev.sinxkyuna.valium.event.impl.network.EventPacket;
import dev.sinxkyuna.valium.event.impl.player.EventAttack;
import dev.sinxkyuna.valium.mixin.accesors.PlayerPositionLookS2CPacketAccessor;
import dev.sinxkyuna.valium.module.Module;
import dev.sinxkyuna.valium.module.ModuleCategory;
import dev.sinxkyuna.valium.module.modules.ghost.AutoClicker;
import dev.sinxkyuna.valium.module.setting.impl.ModeSetting;
import net.minecraft.client.sound.AudioStream;
import net.minecraft.network.packet.s2c.play.PlayerPositionLookS2CPacket;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/*
This breaks after a while due to how unoptimized the threads work but if I get someone
like really knowledgeable of this type of shit, I guarantee they can fix it in a one-liner.

P.S: IMA BUST TO THE ANIME MOAN RAHHHH!
*/

public class HitSFX extends Module {

    public HitSFX() {
        super("HitSFX", "Makes sounds when you hit a target.", 0, ModuleCategory.OTHER);
        addSettings(soundMode);
    }

    public static final ModeSetting soundMode = new ModeSetting("Sound", "Hitmarker", "Hitmarker", "Anime Moan", "Racism");

    @EventLink
    public final Listener<EventAttack> eventAttackListener = event -> {
        if (isNull()) {
            return;
        }

        switch (soundMode.getMode()) {
            case "Hitmarker": playSound("hitmarker");
                break;
            case "Anime Moan": playSound("moan");
                break;
            case "Racism": playSound("racism");
                break;
        }
    };
    
    public static void playSound(String name) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> {
            Clip clip = null;
            try {
                clip = AudioSystem.getClip();
                clip.open(AudioSystem.getAudioInputStream(HitSFX.class.getResource("/assets/valium/sounds/" + name + ".wav")));
                clip.start();
                if(!clip.isActive()) {
                Thread.sleep(clip.getMicrosecondLength());
                }
            } catch (Exception e) {
                System.out.println("Error with playing sound.");
            }
        });
    }

}
