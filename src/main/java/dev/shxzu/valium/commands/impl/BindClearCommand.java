package dev.shxzu.valium.commands.impl;

import dev.shxzu.valium.Client;
import dev.shxzu.valium.commands.Command;
import dev.shxzu.valium.module.Module;

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
