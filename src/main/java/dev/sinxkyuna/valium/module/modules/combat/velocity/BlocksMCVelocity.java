package dev.sinxkyuna.valium.module.modules.combat.velocity;

import dev.sinxkyuna.valium.event.bus.Listener;
import dev.sinxkyuna.valium.event.bus.annotations.EventLink;
import dev.sinxkyuna.valium.event.impl.network.EventPacket;
import dev.sinxkyuna.valium.mixin.accesors.EntityVelocityUpdateS2CPacketAccessor;
import dev.sinxkyuna.valium.module.modules.combat.Velocity;
import dev.sinxkyuna.valium.module.setting.impl.newmodesetting.SubMode;
import dev.sinxkyuna.valium.utils.mc.PacketUtils;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.c2s.play.ClientCommandC2SPacket;
import net.minecraft.network.packet.s2c.play.EntityVelocityUpdateS2CPacket;

public class BlocksMCVelocity extends SubMode<Velocity> {
    public BlocksMCVelocity(String name, Velocity parentModule) {
        super(name, parentModule);
    }

    @EventLink
    public final Listener<EventPacket> eventPacketListener = event -> {
        if (isNull()) {
            return;
        }
        Packet<?> packet = event.getPacket();

        if (packet instanceof EntityVelocityUpdateS2CPacket) {
            EntityVelocityUpdateS2CPacketAccessor s12 = (EntityVelocityUpdateS2CPacketAccessor) packet;
            if (s12.getId() == mc.player.getId()) {
                if (getParentModule().canWork(event)) {
                    PacketUtils.sendPacket(new ClientCommandC2SPacket(mc.player, ClientCommandC2SPacket.Mode.PRESS_SHIFT_KEY));
                    PacketUtils.sendPacket(new ClientCommandC2SPacket(mc.player, ClientCommandC2SPacket.Mode.RELEASE_SHIFT_KEY));

                    if (getParentModule().horizontalBlocksMC.getValueInt() == 0 && getParentModule().verticalBlocksMC.getValueInt() == 0) {
                        event.cancel();
                        return;
                    }

                    s12.setVelocityX((int) (s12.getVelocityX() * (getParentModule().horizontalBlocksMC.getValueInt() / 100D)));
                    s12.setVelocityZ((int) (s12.getVelocityZ() * (getParentModule().horizontalBlocksMC.getValueInt() / 100D)));
                    s12.setVelocityY((int) (s12.getVelocityY() * (getParentModule().verticalBlocksMC.getValueInt() / 100D)));
                }
            }
        }
    };
}
