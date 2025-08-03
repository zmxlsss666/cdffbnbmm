package com.spw.plugin.ui;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

public class LyricWindow {
    private JFrame frame;
    private JFXPanel jfxPanel;
    private Text highlightLine;
    private Text normalLine;
    private int xOffset, yOffset;
    private boolean isDragging = false;

    public LyricWindow() {
        initializeSwingComponents();
        initializeJavaFXComponents();
    }

    private void initializeSwingComponents() {
        // 创建无边框窗口
        frame = new JFrame("SPW 桌面歌词");
        frame.setUndecorated(true);
        frame.setAlwaysOnTop(true);
        frame.setSize(800, 120);
        frame.setLocationRelativeTo(null); // 居中显示
        frame.setBackground(new Color(0, 0, 0, 0)); // 透明背景
        
        // 添加鼠标拖拽支持
        frame.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                xOffset = e.getX();
                yOffset = e.getY();
                isDragging = true;
            }
            
            @Override
            public void mouseReleased(MouseEvent e) {
                isDragging = false;
            }
        });
        
        frame.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (isDragging) {
                    int x = e.getXOnScreen() - xOffset;
                    int y = e.getYOnScreen() - yOffset;
                    frame.setLocation(x, y);
                }
            }
        });
        
        // 创建JavaFX面板
        jfxPanel = new JFXPanel();
        frame.getContentPane().add(jfxPanel);
    }

    private void initializeJavaFXComponents() {
        // 初始化JavaFX组件
        Platform.runLater(() -> {
            // 创建根容器
            VBox root = new VBox();
            
            // 设置渐变背景
            LinearGradient gradient = new LinearGradient(
                0, 0, 0, 1, true, 
                new Stop(0, Color.rgb(44, 62, 80)),    // 深色调
                new Stop(1, Color.rgb(52, 73, 94))     // 稍浅色调
            );
            root.setBackground(new javafx.scene.layout.Background(
                new javafx.scene.layout.BackgroundFill(gradient, null, null)
            ));
            
            // 创建歌词文本流
            TextFlow textFlow = new TextFlow();
            textFlow.setPadding(new javafx.geometry.Insets(15, 20, 15, 20));
            
            // 高亮歌词行
            highlightLine = new Text();
            highlightLine.setFont(Font.font("微软雅黑", 24));
            highlightLine.setFill(Color.rgb(255, 165, 0)); // 橙色高亮
            
            // 普通歌词行
            normalLine = new Text();
            normalLine.setFont(Font.font("微软雅黑", 18));
            normalLine.setFill(Color.WHITE);
            
            textFlow.getChildren().addAll(highlightLine, normalLine);
            root.getChildren().add(textFlow);
            
            // 创建场景并设置到面板
            Scene scene = new Scene(root, 800, 120);
            scene.setFill(Color.TRANSPARENT);
            jfxPanel.setScene(scene);
        });
    }

    /**
     * 更新歌词显示
     */
    public void updateLyrics(String currentLyric, String nextLyric) {
        Platform.runLater(() -> {
            highlightLine.setText(currentLyric + "\n");
            normalLine.setText(nextLyric != null ? nextLyric : "");
        });
    }

    /**
     * 显示窗口
     */
    public void show() {
        SwingUtilities.invokeLater(() -> frame.setVisible(true));
    }

    /**
     * 隐藏窗口
     */
    public void hide() {
        SwingUtilities.invokeLater(() -> frame.setVisible(false));
    }

    /**
     * 销毁窗口
     */
    public void dispose() {
        SwingUtilities.invokeLater(() -> {
            if (frame != null) {
                frame.dispose();
            }
        });
    }
}
