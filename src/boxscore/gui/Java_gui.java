/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package boxscore.gui;

import java.io.File;
import javax.swing.JFrame;

/**
 *
 * @author Peja
 */
public class Java_gui {
    
    public static void main (String[] args) {
        gui window = new gui();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setVisible(true);
        window.setSize(800,600);
        window.pack();
        window.setLocationRelativeTo(null);
    }
    
}
