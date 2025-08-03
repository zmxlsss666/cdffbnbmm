package com.spw.plugin;

import org.pf4j.Plugin;
import org.pf4j.PluginWrapper;
import org.pf4j.Extension;

public class LyricPlugin extends Plugin {

    public LyricPlugin(PluginWrapper wrapper) {
        super(wrapper);
    }
    
    @Override
    public void start() {
        System.out.println("LyricPlugin started");
    }
    
    @Override
    public void stop() {
        System.out.println("LyricPlugin stopped");
        super.stop();
    }
    
    @Extension
    public static class LyricPluginExtensionProvider extends LyricPluginExtensionImpl {
        // 由PF4J框架自动实例化
    }
}
    