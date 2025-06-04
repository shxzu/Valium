package dev.sinxkyuna.valium.module.modules.render;

import dev.sinxkyuna.valium.event.bus.Listener;
import dev.sinxkyuna.valium.event.bus.annotations.EventLink;
import dev.sinxkyuna.valium.event.impl.network.EventPacket;
import dev.sinxkyuna.valium.event.impl.player.EventTickPre;
import dev.sinxkyuna.valium.event.types.TransferOrder;
import dev.sinxkyuna.valium.mixin.accesors.WorldTimeUpdateS2CPacketAccesor;
import dev.sinxkyuna.valium.module.Module;
import dev.sinxkyuna.valium.module.ModuleCategory;
import dev.sinxkyuna.valium.module.setting.impl.NumberSetting;
import net.minecraft.network.packet.s2c.play.WorldTimeUpdateS2CPacket;
import net.minecraft.world.World;

public class Ambience extends Module {
    public static final NumberSetting timeOfDay = new NumberSetting("Time", World.field_30969 / 2, World.field_30969, 0, 1);

    public Ambience() {
        super("Ambience", "Changes the game", 0, ModuleCategory.RENDER);
        this.addSettings(timeOfDay);
    }

    @EventLink
    public final Listener<EventTickPre> eventTickPreListener = event -> {
        if (isNull()) {
            return;
        }
        mc.world.setTimeOfDay(timeOfDay.getValueInt());
    };

    @EventLink
    public final Listener<EventPacket> eventPacketListener = event -> {
        if (isNull()) {
            return;
        }
        if (event.getOrder() == TransferOrder.SEND) {
            return;
        }
        if (event.getPacket() instanceof WorldTimeUpdateS2CPacket a) {
            ((WorldTimeUpdateS2CPacketAccesor) a).setTimeOfDay(timeOfDay.getValueInt());
        }
    };
}
