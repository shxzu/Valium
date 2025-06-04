package dev.sinxkyuna.valium.event.impl.render;

import dev.sinxkyuna.valium.event.types.Event;
import lombok.Getter;
import net.minecraft.client.util.math.MatrixStack;

@Getter
public class EventRender3D implements Event {
    MatrixStack matrixStack;

    public EventRender3D(MatrixStack matrixStack) {
        this.matrixStack = matrixStack;
    }
}
