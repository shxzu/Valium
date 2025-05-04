package dev.shxzu.valium.event.bus;

@FunctionalInterface
public interface Listener<Event> { void call(Event event); }