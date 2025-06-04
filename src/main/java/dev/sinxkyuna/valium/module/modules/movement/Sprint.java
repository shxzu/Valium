package dev.sinxkyuna.valium.module.modules.movement;

import dev.sinxkyuna.valium.Client;
import dev.sinxkyuna.valium.event.bus.Listener;
import dev.sinxkyuna.valium.event.bus.annotations.EventLink;
import dev.sinxkyuna.valium.event.impl.player.EventTickPre;
import dev.sinxkyuna.valium.mixin.accesors.GameOptionsAccessor;
import dev.sinxkyuna.valium.mixin.accesors.KeyBindingAccessor;
import dev.sinxkyuna.valium.module.Module;
import dev.sinxkyuna.valium.module.ModuleCategory;
import dev.sinxkyuna.valium.module.modules.player.Scaffold;
import dev.sinxkyuna.valium.module.setting.impl.BooleanSetting;
import net.minecraft.client.option.KeyBinding;

public class Sprint extends Module {
    public static BooleanSetting rage = new BooleanSetting("Rage", false);

    public Sprint() {
        super("Sprint", "Sprints for you", 0, ModuleCategory.MOVEMENT);
        this.addSetting(rage);
    }

    public static boolean shouldSprintDiagonally() {
        if (Client.INSTANCE.getModuleManager().getModule(Scaffold.class).isEnabled())
            return false;
        return rage.getValue();
    }

    @EventLink
    public final Listener<EventTickPre> eventTickPreListener = event -> {
        if (isNull()) {
            return;
        }
        this.setSuffix(rage.getValue() ? "Rage" : "Legit");
        if (Client.INSTANCE.getModuleManager().getModule(Scaffold.class).isEnabled())
            return;

        ((GameOptionsAccessor) mc.options).getSprintToggled().setValue(false);
        KeyBinding.setKeyPressed(((KeyBindingAccessor) mc.options.sprintKey).getBoundKey(), true);
    };
}
