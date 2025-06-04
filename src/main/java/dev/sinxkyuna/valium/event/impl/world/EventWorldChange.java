package dev.sinxkyuna.valium.event.impl.world;

import dev.sinxkyuna.valium.event.types.Event;
import lombok.Getter;
import net.minecraft.client.world.ClientWorld;

@Getter
public class EventWorldChange implements Event {
    ClientWorld world;
    public EventWorldChange(ClientWorld world){
        this.world = world;
    }
}
