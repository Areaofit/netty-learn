package com.areaofit.startup.bio;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeServerHandler implements Runnable {

    private Socket socket;

    public TimeServerHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        InputStream in = null;
        OutputStream out = null;
        try {
            // 获取输入流
            in = this.socket.getInputStream();
            // 获取输出流
            out = this.socket.getOutputStream();
            byte[] bytes = new byte[1024];
            System.out.println("The request is :");
            int len;
            while ((len = in.read(bytes)) != -1)
            {
                System.out.print(new String(bytes,0,len));
            }
            // 关闭输入流，获取输出流
            socket.shutdownInput();
            StringBuffer s = new StringBuffer();
            s.append("Now time is : ");
            Date date = new Date(System.currentTimeMillis());
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String dateString = simpleDateFormat.format(date);
            s.append(dateString);
            System.out.println("\n Now time is : "+dateString);
            out.write(s.toString().getBytes());
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (in != null)
                {
                    in.close();
                }
                if (out != null)
                {
                    out.close();
                }
                if (socket != null)
                {
                    socket.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }
}
