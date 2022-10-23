/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Hoang Tung Nguyen
 */
public class OthelloServerThread extends Thread {
    private Socket client;
    
    public OthelloServerThread(Socket clientSocket) throws IOException {
        this.client = clientSocket;
    }
    
    @Override
    public void run() {
        try {
            InputStream inStream = client.getInputStream();
       
            Scanner in = new Scanner(inStream);
            while (in.hasNextLine()) {
                String line = in.nextLine();
                System.out.println(line + " has connected");
            }
            } catch (IOException ex) {
        }
    }
    
}
