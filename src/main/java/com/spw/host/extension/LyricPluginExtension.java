package com.spw.host.extension;

import org.pf4j.ExtensionPoint;

/**
 * 歌词插件扩展点接口，由宿主应用提供
 */
public interface LyricPluginExtension extends ExtensionPoint {
    /**
     * 初始化插件
     */
    void initialize();
    
    /**
     * 更新歌词内容
     * @param title 歌曲标题
     * @param artist 歌手
     * @param lyricLines 歌词行数组
     */
    void updateLyrics(String title, String artist, String[] lyricLines);
    
    /**
     * 销毁插件
     */
    void destroy();
}
    