package org.xr.happy.nio;

import lombok.SneakyThrows;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class BackServer {


    @SneakyThrows
    public static void main(String[] args) {

        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(false);

        Selector selector = Selector.open();
        SelectionKey selectionKey = serverSocketChannel.register(selector, 0, serverSocketChannel);
        selectionKey.interestOps(SelectionKey.OP_ACCEPT);

        serverSocketChannel.socket().bind(new InetSocketAddress(8080));


        while (true) {

            // select 阻塞
            selector.select();

            Set<SelectionKey> selectionKeys = selector.selectedKeys();

            Iterator<SelectionKey> iterator = selectionKeys.iterator();

            while (iterator.hasNext()) {

                SelectionKey key = iterator.next();
                iterator.remove();

                if (key.isAcceptable()) {
                    ServerSocketChannel server = (ServerSocketChannel) key.attachment();
                    // 将拿到的客户端连接通道,注册到selector上面
                    SocketChannel clientSocketChannel = server.accept();

                    clientSocketChannel.configureBlocking(false);
                    clientSocketChannel.register(selector, SelectionKey.OP_READ, clientSocketChannel);
                    System.out.println("收到新的连接：" + clientSocketChannel.getRemoteAddress());
                }

                if (key.isReadable()) {

                    SocketChannel socketChannel = (SocketChannel) key.attachment();
                    ByteBuffer allocate = ByteBuffer.allocate(1024);

                    while (socketChannel.isOpen() && socketChannel.read(allocate) != -1) {
                        if (allocate.position() > 0) break;
                    }


                    if (allocate.position()==0)continue;


                    allocate.flip();

                    byte[] bytes = new byte[allocate.limit()];

                    allocate.get(bytes);

                    System.out.println(new String(bytes));

                    System.out.println("收到数据，来自："+socketChannel.getRemoteAddress());


                    String response= "HTTP/1.1 200 0K\r\n"
                            +"Content-Length: 11\r\n\r\n"+
                            "Hello Word";

                    ByteBuffer wrap = ByteBuffer.wrap(response.getBytes());

                    while (wrap.hasRemaining()){
                        socketChannel.write(wrap);
                    }

                }


            }
            selector.selectNow();
        }

    }
}
