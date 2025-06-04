package dev.sinxkyuna.valium.event.bus;

@FunctionalInterface
public interface Listener<Event> { void call(Event event); }