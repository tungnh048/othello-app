/**
 * File name: Othello.java
 * Author: Hoang Tung Nguyen, 040977322
 *         Manh Nghia Duong, 040971252
 * Course: CST8221 - JAP, Lab Section: 302
 * Assignment: 1-2
 * Date: November 15, 2020
 * Professor: Daniel Cormier
 * Purpose: This class implement the MVC model to create a GUI of othello game
 * Class list: Othello
 */
package othello;

import java.awt.EventQueue;

/**
 * This is the main entry point of the program
 * @author Hoang Tung Nguyen
 *         Manh Nghia Duong
 */
public class Othello {

    /**
     * main entry point
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        int duration = 500;
        if(args.length == 1){
            try{
             duration = Integer.parseInt(args[0]);
            }catch (NumberFormatException mfe){
              System.out.println("Wrong command line argument: must be an integer number");
              System.out.println("The default duration 10000 milliseconds will be used");
              //mfe.printStackTrace();	
            } 
        }
        // Create the screen
        OthelloSplashScreen splashWindow = new OthelloSplashScreen(duration);
        //Show the Splash screen 
        splashWindow.showSplashWindow();
        //Create and display the main application GUI
        EventQueue.invokeLater(new Runnable(){
            @Override
            public void run(){ 	
                OthelloViewController OVC = new OthelloViewController();
                OVC.createGUI();
                OVC.pack();
           }
        }); 
    }
    
}
