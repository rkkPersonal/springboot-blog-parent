package org.xr.happy.bio.server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 单线程 会阻塞，当第一个线程执行完之后，才能执行别的连接
 */

public class ServerSocketExecutor0 {



    public static void main(String[] args) throws IOException {


        ServerSocket serverSocket = new ServerSocket(8080);
        System.out.println("服务端启动成功：");

        while (!serverSocket.isClosed()) {

            Socket request = serverSocket.accept();
            System.out.println("收到连接：" + request.toString());


            InputStream inputStream = null;
            try {
                inputStream = request.getInputStream();

                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));


                String msg = "";

                while ((msg = bufferedReader.readLine()) != null) {

                    if (msg.length() == 0)
                        break;
                    OutputStream outputStream = request.getOutputStream();

                    outputStream.write(msg.getBytes());
                    outputStream.flush();
                    System.out.println("服务端收到消息：" + msg);
                    outputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }


        }


    }


}
