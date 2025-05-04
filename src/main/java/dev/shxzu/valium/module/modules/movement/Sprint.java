package dev.shxzu.valium.module.modules.movement;

import dev.shxzu.valium.Client;
import dev.shxzu.valium.event.bus.Listener;
import dev.shxzu.valium.event.bus.annotations.EventLink;
import dev.shxzu.valium.event.impl.player.EventTickPre;
import dev.shxzu.valium.mixin.accesors.GameOptionsAccessor;
import dev.shxzu.valium.mixin.accesors.KeyBindingAccessor;
import dev.shxzu.valium.module.Module;
import dev.shxzu.valium.module.ModuleCategory;
import dev.shxzu.valium.module.modules.player.Scaffold;
import dev.shxzu.valium.module.setting.impl.BooleanSetting;
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
