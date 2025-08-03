package com.spw.plugin;

import com.spw.host.extension.LyricPluginExtension;
import com.spw.plugin.ui.LyricWindow;

public class LyricPluginExtensionImpl implements LyricPluginExtension {
    private LyricWindow lyricWindow;
    private SmtcManager smtcManager;

    @Override
    public void initialize() {
        // 初始化SMTC管理器
        smtcManager = new SmtcManager();
        
        // 初始化并显示歌词窗口
        lyricWindow = new LyricWindow();
        lyricWindow.show();
        
        System.out.println("LyricPluginExtension initialized");
    }

    @Override
    public void updateLyrics(String title, String artist, String[] lyricLines) {
        if (lyricLines == null || lyricLines.length == 0) {
            return;
        }
        
        // 更新SMTC信息
        smtcManager.updateMetadata(title, artist);
        
        // 提取当前歌词和下一行歌词
        String currentLyric = lyricLines[0];
        String nextLyric = lyricLines.length > 1 ? lyricLines[1] : "";
        
        // 更新歌词窗口显示
        if (lyricWindow != null) {
            lyricWindow.updateLyrics(currentLyric, nextLyric);
        }
        
        // 更新SMTC歌词
        String fullLyric = String.join("\n", lyricLines);
        smtcManager.updateLyrics(fullLyric);
    }

    @Override
    public void destroy() {
        System.out.println("Destroying LyricPluginExtension");
        
        // 销毁歌词窗口
        if (lyricWindow != null) {
            lyricWindow.dispose();
            lyricWindow = null;
        }
        
        // 关闭SMTC连接
        if (smtcManager != null) {
            smtcManager.shutdown();
            smtcManager = null;
        }
    }
}
    