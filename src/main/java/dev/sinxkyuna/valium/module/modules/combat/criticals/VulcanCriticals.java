package dev.sinxkyuna.valium.module.modules.combat.criticals;

import dev.sinxkyuna.valium.event.bus.Listener;
import dev.sinxkyuna.valium.event.bus.annotations.EventLink;
import dev.sinxkyuna.valium.event.impl.player.EventAttack;
import dev.sinxkyuna.valium.module.modules.combat.Criticals;
import dev.sinxkyuna.valium.module.setting.impl.newmodesetting.SubMode;
import dev.sinxkyuna.valium.utils.mc.PacketUtils;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;

public class VulcanCriticals extends SubMode<Criticals> {
    public VulcanCriticals(String name, Criticals parentModule) {
        super(name, parentModule);
    }

    private int a = 0;

    @EventLink
    public final Listener<EventAttack> eventAttackListener = event -> {
        if (isNull()) {
            return;
        }
        a++;
        if (a > 7) {
            PacketUtils.sendPacket(new PlayerMoveC2SPacket.PositionAndOnGround(mc.player.getPos().x, mc.player.getPos().y + 0.16477328182606651, mc.player.getPos().z, false));
            PacketUtils.sendPacket(new PlayerMoveC2SPacket.PositionAndOnGround(mc.player.getPos().x, mc.player.getPos().y + 0.08307781780646721, mc.player.getPos().z, false));
            PacketUtils.sendPacket(new PlayerMoveC2SPacket.PositionAndOnGround(mc.player.getPos().x, mc.player.getPos().y + 0.0030162615090425808, mc.player.getPos().z, false));
            a = 0;
        }
    };
}
