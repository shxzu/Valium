package dev.sinxkyuna.valium.event.impl.player;

import dev.sinxkyuna.valium.event.types.Event;
import lombok.Getter;
import lombok.Setter;

public class EventYawMoveFix implements Event {
    @Getter
    @Setter
    float yaw;

    public EventYawMoveFix(float yaw) {
        this.yaw = yaw;
    }
}
