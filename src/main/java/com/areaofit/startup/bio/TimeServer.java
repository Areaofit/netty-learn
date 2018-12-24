package com.areaofit.startup.bio;

import java.net.ServerSocket;
import java.net.Socket;

public class TimeServer {

    public static void main(String[] args) throws Exception {
        ServerSocket serverSocket = new ServerSocket(8080);
        System.out.println("BIO Time server is startup, port is 8080");
        try {
            Socket socket;
            while (true) {
                socket = serverSocket.accept();
                new Thread(new TimeServerHandler(socket)).start();
            }
        } finally {
            if (serverSocket != null)
            {
                System.out.println("BIO Time server is close.");
                serverSocket.close();
            }
        }

    }
}
