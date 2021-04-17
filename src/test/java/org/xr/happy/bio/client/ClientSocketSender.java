package org.xr.happy.bio.client;

import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;

/**
 * BIO 客户端 演示
 */
public class ClientSocketSender {


    public static void main(String[] args) throws Exception {

        Socket socket = new Socket("localhost", 8080);

        OutputStream outputStream = socket.getOutputStream();

        Scanner scanner = new Scanner(System.in);
        System.out.println("客户端已经启动：");
        String msg = scanner.nextLine();
        outputStream.write(msg.getBytes());
        outputStream.flush();
        System.out.println("客户端发送消息成功：" + msg);
        outputStream.close();
        socket.close();
        scanner.close();
    }
}
