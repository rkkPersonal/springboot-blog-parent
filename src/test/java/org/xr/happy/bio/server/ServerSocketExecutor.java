package org.xr.happy.bio.server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 结局BIO阻塞问题
 */
public class ServerSocketExecutor {


    private static final ExecutorService executorPoll = Executors.newFixedThreadPool(10);

    public static void main(String[] args) throws Exception {


        ServerSocket serverSocket = new ServerSocket(8080);
        System.out.println("服务端启动成功：");

        while (!serverSocket.isClosed()) {


            Socket request = serverSocket.accept();
            System.out.println("收到连接：" + request.toString());

            executorPoll.execute(() -> {

                InputStream inputStream = null;
                try {
                    inputStream = request.getInputStream();

                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));


                    String msg = "";

                    while ((msg = bufferedReader.readLine()) != null) {

                        if (msg.length() == 0)
                            break;

                        System.out.println("服务端收到消息：" + msg);
                    }

                    OutputStream outputStream = request.getOutputStream();

                    outputStream.write("HTTP/1.1 200 OK\r\n".getBytes());
                    outputStream.write("Content-Length: 11\r\n\r\n".getBytes());
                    outputStream.write("2021 Welcome".getBytes());
                    outputStream.flush();


                } catch (IOException e) {
                    e.printStackTrace();
                }finally {
                    try {
                        request.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            });


        }

        serverSocket.close();

    }
}



