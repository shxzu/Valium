package dev.sinxkyuna.valium.module.modules.render;

import dev.sinxkyuna.valium.event.bus.Listener;
import dev.sinxkyuna.valium.event.bus.annotations.EventLink;
import dev.sinxkyuna.valium.event.impl.player.EventTickPre;
import dev.sinxkyuna.valium.module.Module;
import dev.sinxkyuna.valium.module.ModuleCategory;
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
