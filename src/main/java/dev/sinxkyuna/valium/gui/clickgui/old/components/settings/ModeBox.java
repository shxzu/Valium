package dev.sinxkyuna.valium.gui.clickgui.old.components.settings;

import dev.sinxkyuna.valium.gui.clickgui.old.components.ModuleButton;
import dev.sinxkyuna.valium.module.setting.Setting;
import dev.sinxkyuna.valium.module.setting.impl.ModeSetting;
import dev.sinxkyuna.valium.utils.render.TextRenderer;
import net.minecraft.client.gui.DrawContext;

import java.awt.*;

public final class ModeBox extends RenderableSetting {
    public final ModeSetting setting;

    public ModeBox(ModuleButton parent, Setting setting, int offset) {
        super(parent, setting, offset);
        this.setting = (ModeSetting) setting;
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);

        TextRenderer.drawMinecraftText(setting.getName() + ": " + setting.getMode(), context, parentX() + 6, (parentY() + parentOffset() + offset) + 6, Color.WHITE.getRGB());
    }

    @Override
    public void mouseClicked(double mouseX, double mouseY, int button) {
        if (isHovered(mouseX, mouseY)) {
            setting.cycle();
        }
        super.mouseClicked(mouseX, mouseY, button);
    }
}