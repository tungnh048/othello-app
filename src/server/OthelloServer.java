/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;


/**
 *
 * @author Hoang Tung Nguyen
 */
public class OthelloServer {
    
    private static ArrayList<OthelloServerThread> clients = new ArrayList<>();
    
    public static void main(String[] args) throws IOException {
        int serverPort = 62000;
        if (args.length == 1) {
            try {
                int temp = Integer.parseInt(args[0]);
                if (temp >= 0 && temp <= 65535) {
                    serverPort = temp;
                    System.out.println("Using port: " + serverPort);
                } else {
                    System.out.println("ERROR: Invalid port number: " + temp);
                }
            } catch (NumberFormatException e) {
                System.out.println("ERROR: Invalid port number: " + args[0]);
                System.out.println("Using default port: " + serverPort);
            }
        } else {
            System.out.println("Using default port: " + serverPort);
        }
        
        ServerSocket listener = new ServerSocket(serverPort);
        
        while (true) {
            Socket client = listener.accept();
            OthelloServerThread thread = new OthelloServerThread(client);
            clients.add(thread);
            thread.start();
            System.out.println("Inbound connection #" + clients.size());
        }
        
    }
}
