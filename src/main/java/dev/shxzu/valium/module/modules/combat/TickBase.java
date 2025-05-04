package dev.shxzu.valium.module.modules.combat;

import dev.shxzu.valium.Client;
import dev.shxzu.valium.event.bus.Listener;
import dev.shxzu.valium.event.bus.annotations.EventLink;
import dev.shxzu.valium.event.impl.network.EventPacket;
import dev.shxzu.valium.event.impl.player.EventTickPost;
import dev.shxzu.valium.event.impl.player.EventTickPre;
import dev.shxzu.valium.event.impl.player.EventTickbase;
import dev.shxzu.valium.event.types.TransferOrder;
import dev.shxzu.valium.mixin.accesors.EntityVelocityUpdateS2CPacketAccessor;
import dev.shxzu.valium.module.Module;
import dev.shxzu.valium.module.ModuleCategory;
import dev.shxzu.valium.module.setting.impl.NumberSetting;
import dev.shxzu.valium.module.setting.impl.RangeSetting;
import dev.shxzu.valium.utils.mc.CombatUtils;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.EntityVelocityUpdateS2CPacket;
import net.minecraft.network.packet.s2c.play.PlayerPositionLookS2CPacket;

public class TickBase extends Module {
    public static final RangeSetting range = new RangeSetting("Range", 0, 6, 3.5, 4.5, 0.1);
    public static final NumberSetting ticks = new NumberSetting("Ticks", 1, 100, 5, 1);

    private int frozenTicks = 0;
    private boolean isFreezing = false;
    private boolean shouldBoost = false;

    private int coolDownTicks = 0;

    public TickBase() {
        super("TickBase", "Freezes the game to give you a small boost", 0, ModuleCategory.COMBAT);
        this.addSettings(range, ticks);
    }

    @EventLink
    public final Listener<EventTickPre> eventTickPreListener = event -> {
        if (isNull()) {
            return;
        }
        this.setSuffix(String.valueOf(ticks.getValueInt()) + " Ticks");

        if (coolDownTicks > 0) {
            coolDownTicks--;
        }
    };

    @EventLink
    public final Listener<EventTickPost> eventTickPostListener = event -> {
        if (isNull()) {
            return;
        }

        if (coolDownTicks > 0) {
            return;
        }

        if (shouldBoost) {
            for (int i = 0; i < frozenTicks; i++) {
                mc.player.tick();
            }
            frozenTicks = 0;
            shouldBoost = false;
            isFreezing = false;
        }
    };

    @EventLink
    public final Listener<EventPacket> eventPacketListener = event -> {
        if (isNull()) {
            return;
        }

        Packet<?> packet = event.getPacket();

        if (event.getOrder() == TransferOrder.RECEIVE) {
            if (packet instanceof EntityVelocityUpdateS2CPacket) {
                EntityVelocityUpdateS2CPacketAccessor s12 = (EntityVelocityUpdateS2CPacketAccessor) packet;
                if (s12.getId() == mc.player.getId()) {
                    shouldBoost = true;
                    isFreezing = false;
                }
            }

            if (packet instanceof PlayerPositionLookS2CPacket) {
                frozenTicks = 0;
                shouldBoost = false;
                isFreezing = false;

                coolDownTicks = 60;
            }
        }
    };

    @EventLink
    public final Listener<EventTickbase> eventTickbaseListener = event -> {
        if (isNull()) {
            return;
        }

        if (coolDownTicks > 0) {
            return;
        }

        if (isFreezing && !canFreeze()) {
            shouldBoost = true;
            isFreezing = false;
            return;
        }

        if (canStartFreeze() && !isFreezing) {
            isFreezing = true;
        }

        if (isFreezing) {
            if (frozenTicks < ticks.getValue()) {
                event.cancel();
                frozenTicks++;
            }

            if (frozenTicks >= ticks.getValue()) {
                shouldBoost = true;
            }
        }
    };

    private boolean canStartFreeze() {
        KillAura killAura = Client.INSTANCE.getModuleManager().getModule(KillAura.class);
        if (!(killAura.isEnabled() && killAura.target != null)) {
            return false;
        }
        PlayerEntity killAuraTarget = killAura.target;

        return CombatUtils.getDistanceToEntity(killAuraTarget) >= range.getValueMin() &&
                CombatUtils.getDistanceToEntity(killAuraTarget) <= range.getValueMax() &&
                killAura.target.hurtTime == 0;
    }

    private boolean canFreeze() {
        KillAura killAura = Client.INSTANCE.getModuleManager().getModule(KillAura.class);
        if (!(killAura.isEnabled() && killAura.target != null)) {
            return false;
        }
        PlayerEntity killAuraTarget = killAura.target;

        return CombatUtils.getDistanceToEntity(killAuraTarget) >= range.getValueMin() &&
                CombatUtils.getDistanceToEntity(killAuraTarget) <= range.getValueMax();
    }
}
