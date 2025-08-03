package com.spw.plugin;

import org.pf4j.Plugin;
import org.pf4j.PluginWrapper;

/**
 * 插件入口类，必须正确实现构造函数
 */
public class LyricPlugin extends Plugin {
    private LyricPluginExtensionImpl extension;

    // 必须提供这个构造函数，PF4J 会通过反射调用
    public LyricPlugin(PluginWrapper wrapper) {
        super(wrapper); // 必须调用父类构造函数，否则插件无法实例化
    }

    @Override
    public void start() {
        // 初始化插件扩展实现
        extension = new LyricPluginExtensionImpl();
        extension.initialize();
        System.out.println("LyricPlugin started");
    }

    @Override
    public void stop() {
        if (extension != null) {
            extension.destroy();
            extension = null;
        }
        System.out.println("LyricPlugin stopped");
    }
}
