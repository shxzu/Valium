package dev.sinxkyuna.valium;

import dev.sinxkyuna.valium.anticheat.AntiCheatManager;
import dev.sinxkyuna.valium.commands.CommandManager;
import dev.sinxkyuna.valium.config.ConfigManager;
import dev.sinxkyuna.valium.event.EventManager;
import dev.sinxkyuna.valium.module.ModuleManager;
import dev.sinxkyuna.valium.utils.font.FontManager;
import dev.sinxkyuna.valium.utils.mc.DelayUtil;
import dev.sinxkyuna.valium.utils.render.notifications.NotificationManager;
import dev.sinxkyuna.valium.utils.rotation.manager.RotationManager;
import lombok.Getter;
import net.minecraft.client.MinecraftClient;

@Getter
public final class Client {
    public static MinecraftClient mc;
    public static Client INSTANCE;

    private final EventManager eventManager;
    private final RotationManager rotationManager;
    private final ModuleManager moduleManager;

    private final NotificationManager notificationManager;
    private final AntiCheatManager antiCheatManager;

    private final FontManager fontManager;
    private final ConfigManager configManager;
    private final CommandManager commandManager;

    private final DelayUtil delayUtil;
    public static String verison = "1.0.0";

    public Client() {

        INSTANCE = this;
        mc = MinecraftClient.getInstance();

        eventManager = new EventManager();

        notificationManager = new NotificationManager();
        antiCheatManager = new AntiCheatManager();
        rotationManager = new RotationManager();
        commandManager = new CommandManager();
        moduleManager = new ModuleManager();
        configManager = new ConfigManager();

        fontManager = new FontManager();
        delayUtil = new DelayUtil();

        eventManager.subscribe(notificationManager);
        eventManager.subscribe(rotationManager);
        eventManager.subscribe(delayUtil);
    }
}
