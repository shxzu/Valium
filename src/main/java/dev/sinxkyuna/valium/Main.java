package dev.sinxkyuna.valium;

import net.fabricmc.api.ModInitializer;

public final class Main implements ModInitializer {
    @Override
    public void onInitialize() {
        new Client();
        System.out.println("Loading Valium...");
    }
}
