package dev.sinxkyuna.valium.commands.impl;

import dev.sinxkyuna.valium.Client;
import dev.sinxkyuna.valium.commands.Command;
import dev.sinxkyuna.valium.module.Module;

public class BindClearCommand extends Command {
    public BindClearCommand() {
        super("bindclear");
    }

    @Override
    public void execute(String[] commands) {
        for (Module module : Client.INSTANCE.getModuleManager().getModules()) {
            module.setKey(0);
        }
        sendMessage("Reset all binds");
    }
}
