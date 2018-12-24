package com.areaofit.startup.bio;

import java.net.ServerSocket;
import java.net.Socket;

public class TimeServer {

    // 端口号
    public static int port = 8080;

    public static void main(String[] args) throws Exception {
        if (args != null && !"".equals(args[0]))
        {
            int p = Integer.parseInt(args[0]);
            if (p > 0)
            {
                port = p;
            }
        }
        ServerSocket serverSocket = new ServerSocket(port);
        System.out.println("BIO Time server is startup, port is " + port);
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
