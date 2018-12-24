package com.areaofit.startup.bio;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

public class TimeClient {

    public static void main(String[] args) throws Exception {
        Socket socket = null;
        InputStream inputStream = null;
        OutputStream out = null;
        try {
            socket = new Socket("127.0.0.1", 8080);
            out = socket.getOutputStream();
            String s = "QUERY NOW TIME";
            out.write(s.getBytes());
            out.flush();
            // 关闭输出流
            socket.shutdownOutput();
            inputStream = socket.getInputStream();
            byte[] bytes = new byte[1024];
            int len;
            while ((len = inputStream.read(bytes)) != -1)
            {
                System.out.print(new String(bytes, 0 ,len));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (out != null)
            {
                out.close();
            }
            if (inputStream != null)
            {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
