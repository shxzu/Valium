package dev.shxzu.valium.event.impl.player;

import dev.shxzu.valium.event.types.Event;
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
