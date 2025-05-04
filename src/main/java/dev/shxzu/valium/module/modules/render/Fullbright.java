package dev.shxzu.valium.module.modules.render;

import dev.shxzu.valium.event.bus.Listener;
import dev.shxzu.valium.event.bus.annotations.EventLink;
import dev.shxzu.valium.event.impl.player.EventTickPre;
import dev.shxzu.valium.module.Module;
import dev.shxzu.valium.module.ModuleCategory;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;

public class Fullbright extends Module {
    public Fullbright() {
        super("Fullbright", "Makes the game BRIGHT", 0, ModuleCategory.RENDER);
    }

    @EventLink
    public final Listener<EventTickPre> eventTickPreListener = event -> {
        if (mc.player == null || mc.world == null) {
            return;
        }
        mc.player.addStatusEffect(new StatusEffectInstance(StatusEffects.NIGHT_VISION, 42069));
    };

    @Override
    public void onDisable() {
        mc.player.removeStatusEffect(StatusEffects.NIGHT_VISION);
        super.onDisable();
    }
}
