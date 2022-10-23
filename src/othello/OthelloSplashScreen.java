/**
 * File name: OthelloSplashScreen.java
 * Author: Hoang Tung Nguyen, 040977322
 *         Manh Nghia Duong, 040971252
 * Course: CST8221 - JAP, Lab Section: 302
 * Assignment: 1-2
 * Date: November 15, 2020
 * Professor: Daniel Cormier
 * Purpose: This class extends JWindow and is the splash screen of assignment
 * Class list: OthelloSplashScreen
 */

package othello;

import java.awt.*;
import javax.swing.*;

/**
 * This class create the splash screen before going into the game
 * @author Hoang Tung Nguyen
 *         Manh Nghia Duong
 */
public class OthelloSplashScreen extends JWindow {
    private static final long serialVersionUID = 6248477390124803341L;
    private final int duration;
    private JProgressBar b;
    
    public OthelloSplashScreen(int duration) {
        this.duration = duration;
    }
    
    public void fillBar() {
        int i = 0;
        try {
            while (i <= 100) {
                //this fill the bar
                b.setValue(i);
                
                //this delay the thread
                Thread.sleep(500);
                i+=20;
            }
        } catch (Exception e) {
        }
    }
    
    /**
     * Method to display the splash window
     * return void
     */
    public void showSplashWindow() {
        //content pane
        JPanel content = new JPanel(new BorderLayout());
        content.setBackground(Color.WHITE);
        
        // Set the window's bounds, position the window in the center of the screen
        int width = 781;
        int height = 390;
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (screen.width-width)/2;
        int y = (screen.height-height)/2;
        //set the location and the size of the window
        setBounds(x,y,width,height);
        
        JLabel label1 = new JLabel(new ImageIcon(getClass().getResource("splash.gif")));
        
        JLabel demo = new JLabel("Simple Othello Game", JLabel.CENTER);
        b = new JProgressBar();
        b.setValue(0);
        b.setStringPainted(true);
        demo.setFont(new Font(Font.SANS_SERIF, Font.ITALIC, 14));
        JPanel southLabel = new JPanel(new BorderLayout());
        southLabel.add(demo, BorderLayout.CENTER);
        southLabel.add(b, BorderLayout.SOUTH);
        content.add(label1, BorderLayout.CENTER);
        content.add(southLabel, BorderLayout.SOUTH);
        // create custom RGB color
        Color customColor = new Color(44, 197, 211);
        content.setBorder(BorderFactory.createLineBorder(customColor, 10));

        //replace the window content pane with the content JPanel
        setContentPane(content);

        //make the splash window visible
        setVisible(true);
        fillBar();

        // Snooze for awhile, pretending the code is loading something awesome while
        // our splashscreen is entertaining the user.
        try {
            
             Thread.sleep(duration); }
        catch (InterruptedException e) {/*log an error here?*//*e.printStackTrace();*/}
        //destroy the window and release all resources
        dispose(); 
        }
}
