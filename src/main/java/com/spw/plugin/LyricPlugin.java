package com.spw.plugin;

import org.pf4j.Plugin;
import org.pf4j.PluginWrapper;

public class LyricPlugin extends Plugin {
    private LyricPluginExtensionImpl extension;

    public LyricPlugin(PluginWrapper wrapper) {
        super(wrapper);
    }

    @Override
    public void start() {
        System.out.println("LyricPlugin started");
        extension = new LyricPluginExtensionImpl();
        extension.initialize();
    }

    @Override
    public void stop() {
        System.out.println("LyricPlugin stopped");
        if (extension != null) {
            extension.destroy();
            extension = null;
        }
    }
}
