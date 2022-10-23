/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package othello;

import java.awt.EventQueue;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import server.OthelloServer;


/**
 *
 * @author Hoang Tung Nguyen
 */
public class OthelloNetworkController implements Runnable {
    private Socket client;
    private String clientName;
    
    public OthelloNetworkController(String address, int portNumber, String name) throws IOException {
        this.client = new Socket(address, portNumber);
        this.clientName = name;
        EventQueue.invokeLater(()->{
            sendName();
        });

    }
    
    public void sendName() {
        try {
            PrintWriter pr = new PrintWriter(client.getOutputStream(), true);
            pr.println(clientName);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        
    }
}
