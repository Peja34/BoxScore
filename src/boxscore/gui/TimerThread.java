/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package boxscore.gui;

import java.util.logging.Level;
import java.util.logging.Logger;
import boxscore.game.Game;
import java.awt.Color;

/**
 * Implements thread for game timer
 * @author Peja
 */
public class TimerThread implements Runnable {
    Thread t;
    Game game;
    gui g;
    private int time;
    
    /**
     * Creates game timer
     * 
     * @param game game of basketball which is played
     * @param G currently used graphical interface
     */
    TimerThread (Game game, gui G) {
        this.game = game;
        this.g = G;
    }
    
    /**
     * Runs thread
     */
    @Override
    public void run() {
        while (this.game.actualTime != 0) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(TimerThread.class.getName()).log(Level.SEVERE, null, ex);
            }
            if (this.game.stop) {
                this.game.actualTime--;
                this.time = this.game.actualTime;
                if (time%60 < 10)
                    this.g.timeLabel.setText(time/60 + ":0" + time%60);
                else
                    this.g.timeLabel.setText(time/60 + ":" + time%60);
            }
            System.out.println(this.game.actualTime);
        }
        this.g.startBtn.setBackground(Color.yellow);
        this.g.startBtn.setText("End quarter");
    }
    
    /**
     * Starts new thread
     */
    public void start() {
        t = new Thread(this, "timer");
        t.start();
    }
}
