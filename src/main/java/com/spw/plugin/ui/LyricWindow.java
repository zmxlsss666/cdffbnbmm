package com.spw.plugin.ui;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class LyricWindow {
    private JFrame frame;
    private JFXPanel jfxPanel;
    private Text currentLyricText;
    private Text nextLyricText;
    
    // 窗口拖动相关变量
    private int xOffset = 0;
    private int yOffset = 0;

    public LyricWindow() {
        initializeSwingComponents();
        initializeJavaFXComponents();
    }
    
    private void initializeSwingComponents() {
        // 创建无边框窗口
        frame = new JFrame("桌面歌词");
        frame.setUndecorated(true);
        frame.setAlwaysOnTop(true);
        frame.setSize(800, 120);
        frame.setLocationRelativeTo(null); // 居中显示
        
        // 设置透明背景
        frame.setBackground(new java.awt.Color(0, 0, 0, 0));
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        // 添加鼠标监听器用于拖动窗口
        frame.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                xOffset = e.getX();
                yOffset = e.getY();
            }
        });
        
        frame.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                int x = e.getXOnScreen() - xOffset;
                int y = e.getYOnScreen() - yOffset;
                frame.setLocation(x, y);
            }
        });
        
        // 创建JavaFX面板
        jfxPanel = new JFXPanel();
        frame.getContentPane().add(jfxPanel);
    }
    
    private void initializeJavaFXComponents() {
        Platform.runLater(() -> {
            // 创建布局容器
            VBox root = new VBox(5); // 垂直布局，间距5
            
            // 创建渐变背景
            LinearGradient gradient = new LinearGradient(
                0, 0, 0, 1, true, 
                CycleMethod.NO_CYCLE,
                new Stop(0.0, Color.rgb(44, 62, 80)),    // 深色调
                new Stop(1.0, Color.rgb(52, 73, 94))     // 稍浅色调
            );
            root.setBackground(new javafx.scene.layout.Background(
                new javafx.scene.layout.BackgroundFill(gradient, null, null)
            ));
            
            // 创建歌词文本流
            TextFlow textFlow = new TextFlow();
            textFlow.setPadding(new javafx.geometry.Insets(15, 20, 15, 20));
            
            // 当前歌词（高亮）
            currentLyricText = new Text();
            currentLyricText.setFont(Font.font("Microsoft YaHei", 24));
            currentLyricText.setFill(Color.rgb(255, 165, 0)); // 橙色高亮
            
            // 下一行歌词
            nextLyricText = new Text();
            nextLyricText.setFont(Font.font("Microsoft YaHei", 18));
            nextLyricText.setFill(Color.WHITE);
            
            textFlow.getChildren().addAll(currentLyricText, new Text("\n"), nextLyricText);
            root.getChildren().add(textFlow);
            
            // 创建场景
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
            currentLyricText.setText(currentLyric);
            nextLyricText.setText(nextLyric);
        });
    }
    
    /**
     * 显示窗口
     */
    public void show() {
        SwingUtilities.invokeLater(() -> {
            frame.setVisible(true);
        });
    }
    
    /**
     * 销毁窗口
     */
    public void dispose() {
        SwingUtilities.invokeLater(() -> {
            frame.dispose();
        });
    }
}
    