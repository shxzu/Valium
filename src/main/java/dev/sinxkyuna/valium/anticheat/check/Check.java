package dev.sinxkyuna.valium.anticheat.check;

import dev.sinxkyuna.valium.Client;
import dev.sinxkyuna.valium.event.types.TransferOrder;
import dev.sinxkyuna.valium.module.modules.other.AntiCheat;
import dev.sinxkyuna.valium.utils.mc.ChatFormatting;
import dev.sinxkyuna.valium.utils.mc.ChatUtils;
import dev.sinxkyuna.valium.utils.timer.MillisTimer;
import lombok.Getter;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.packet.Packet;

public class Check {
    protected static MinecraftClient mc = MinecraftClient.getInstance();

    @Getter
    private final PlayerEntity player;
    public String checkName;

    public Check(PlayerEntity player, String checkName) {
        this.player = player;
        this.checkName = checkName;
    }

    public void onPacket(Packet<?> packet, TransferOrder origin) {

    }

    public void onTick() {

    }

    private final MillisTimer delayTimer = new MillisTimer();

    public void flag(String message) {
        if (delayTimer.hasElapsed(500L)) {
            ChatUtils.addACMessageToChat(ChatFormatting.RED + player.getGameProfile().getName() + ChatFormatting.RESET + " " + "flagged: [" + ChatFormatting.AQUA + checkName + ChatFormatting.RESET + "]" + ChatFormatting.GRAY + " (" + message + ")");
            delayTimer.reset();
        }
    }

    public boolean shouldCheckSelf() {
        Client.INSTANCE.getModuleManager().getModule(AntiCheat.class);
        return AntiCheat.checkSelf.getValue();
    }
}
