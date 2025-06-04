package dev.sinxkyuna.valium.event;


import dev.sinxkyuna.valium.event.bus.bus.impl.EventBus;
import dev.sinxkyuna.valium.event.types.Event;
import lombok.Getter;

@Getter
public class EventManager {

    private final EventBus<Event> bus;

    public EventManager() {
        this.bus = new EventBus<>();
    }

    public void subscribe(Object listener) {
        bus.subscribe(listener);
    }

    public void unsubscribe(Object listener) {
        bus.unsubscribe(listener);
    }

    public void post(Event event) {
        bus.post(event);
    }
}