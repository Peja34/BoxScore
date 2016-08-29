/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package boxscore.gui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.JPanel;

/**
 * Implements personal customization of JPanle
 * @author Peja
 */
public class RoundedPanel extends JPanel {
    protected int strokeSize = 1;
    protected Color shadowColor = Color.black;
    protected boolean shady = false;
    protected boolean highQuality = true;
    protected Dimension arcs = new Dimension(20, 20);
    protected int shadowGap = 5;
    protected int shadowOffset = 4;
    protected int shadowAlpha = 150;
    private final Color color;
    
    /**
     * Creates new and rounded JPanel
     * 
     * @param color Color of new Panel
     */
    public RoundedPanel(Color color) {
        super();
        setOpaque(false);
        this.color = color;
    }
    
    /**
     * Algorithm for panel creation
     * 
     * @param g Graphics used for new panel
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int width = getWidth();
        int height = getHeight();
        int shadowGap = this.shadowGap;
        Color shadowColorA = new Color(shadowColor.getRed(), shadowColor.getGreen(), shadowColor.getBlue(), shadowAlpha);
        Graphics2D graphics = (Graphics2D) g;
        
        if (highQuality) {
            graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        }
        
        if (shady) {
            graphics.setColor(shadowColorA);
            graphics.fillRoundRect(shadowOffset, shadowOffset, width - strokeSize - shadowOffset, height - strokeSize - shadowOffset, arcs.width, arcs.height);
        }
        else {
            shadowGap = 1;
        }
        
        graphics.setColor(getBackground());
        graphics.fillRoundRect(0, 0, width - shadowGap, height - shadowGap, arcs.width, arcs.height);
        graphics.setColor(this.color);
        graphics.setStroke(new BasicStroke(strokeSize));
        graphics.fillRoundRect(0, 0, width - shadowGap, height - shadowGap, arcs.width, arcs.height);
        
        graphics.setStroke(new BasicStroke());
    }
}
