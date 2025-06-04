package dev.sinxkyuna.valium.anticheat;

import dev.sinxkyuna.valium.Client;
import dev.sinxkyuna.valium.anticheat.check.Check;
import dev.sinxkyuna.valium.anticheat.check.impl.aim.AimAssistACheck;
import dev.sinxkyuna.valium.anticheat.check.impl.aim.AimAssistBCheck;
import dev.sinxkyuna.valium.anticheat.check.impl.aim.InvalidPitchCheck;
import dev.sinxkyuna.valium.anticheat.check.impl.combat.*;
import dev.sinxkyuna.valium.event.bus.Listener;
import dev.sinxkyuna.valium.event.bus.annotations.EventLink;
import dev.sinxkyuna.valium.event.impl.network.EventPacket;
import dev.sinxkyuna.valium.event.impl.player.EventTickPre;
import dev.sinxkyuna.valium.event.types.TransferOrder;
import dev.sinxkyuna.valium.module.modules.combat.AntiBot;
import dev.sinxkyuna.valium.module.modules.other.AntiCheat;
import lombok.Getter;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.network.packet.Packet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

@Getter
public class AntiCheatManager {

    private final Map<AbstractClientPlayerEntity, List<Check>> playerChecks = new HashMap<>();

    public AntiCheatManager() {
        Client.INSTANCE.getEventManager().subscribe(this);
    }

    @EventLink
    public final Listener<EventTickPre> eventTickPreListener = event -> {
        if (isNull()) {
            return;
        }

        if (!Client.INSTANCE.getModuleManager().getModule(AntiCheat.class).isEnabled()) {
            return;
        }

        List<AbstractClientPlayerEntity> players = getPlayers();
        for (AbstractClientPlayerEntity player : players) {
            List<Check> checks = playerChecks.get(player);
            if (checks != null) {
                for (Check check : checks) {
                    check.onTick();
                }
            }
        }
    };

    @EventLink
    public final Listener<EventPacket> eventPacketListener = event -> {
        if (isNull()) {
            return;
        }

        if (!Client.INSTANCE.getModuleManager().getModule(AntiCheat.class).isEnabled()) {
            return;
        }

        Packet<?> packet = event.getPacket();
        TransferOrder origin = event.getOrder();
        List<AbstractClientPlayerEntity> players = getPlayers();
        for (AbstractClientPlayerEntity player : players) {
            List<Check> checks = playerChecks.get(player);
            if (checks != null) {
                for (Check check : checks) {
                    check.onPacket(packet, origin);
                }
            }
        }
    };

    private List<AbstractClientPlayerEntity> getPlayers() {
        List<AbstractClientPlayerEntity> players = new CopyOnWriteArrayList<>();
        List<AbstractClientPlayerEntity> worldPlayers = new ArrayList<>(MinecraftClient.getInstance().world.getPlayers());

        for (AbstractClientPlayerEntity e : worldPlayers) {
            if (e == null) {
                continue;
            }
            if (Client.INSTANCE.getModuleManager().getModule(AntiBot.class).isBot(e)) {
                continue;
            }
            Client.INSTANCE.getModuleManager().getModule(AntiCheat.class);
            if (!AntiCheat.checkSelf.getValue() && e == MinecraftClient.getInstance().player) {
                continue;
            }
            players.add(e);
            initializeChecks(e);
        }
        return players;
    }

    private void initializeChecks(AbstractClientPlayerEntity player) {
        if (!playerChecks.containsKey(player)) {
            List<Check> checks = new ArrayList<>();

            // Combat
            checks.add(new AutoBlockCheck(player));
            checks.add(new AutoClickerACheck(player));
            checks.add(new AutoClickerBCheck(player));
            checks.add(new AutoClickerCCheck(player));
            checks.add(new AutoClickerDCheck(player));

            // Aim
            checks.add(new InvalidPitchCheck(player));
            checks.add(new AimAssistACheck(player));
            checks.add(new AimAssistBCheck(player));
            playerChecks.put(player, checks);
        }
    }

    public boolean isNull() {
        return MinecraftClient.getInstance().player == null || MinecraftClient.getInstance().world == null;
    }
}
