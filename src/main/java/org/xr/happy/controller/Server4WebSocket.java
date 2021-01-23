package org.xr.happy.controller;


import org.springframework.stereotype.Component;
import org.xr.happy.common.utils.MySpringConfigurator;

import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * @author Steven
 */
@ServerEndpoint(value = "/serverForWebSocket/{id}",configurator = MySpringConfigurator.class)
public class Server4WebSocket {

    private Session session;


    private static CopyOnWriteArraySet<Session> webSocketSet = new CopyOnWriteArraySet<Session>();

    @OnOpen //事件 --登录的人.
    public void onopen(Session session){
        this.session = session;
        System.out.println("seesionId为"+session.getId());
        webSocketSet.add(session);
        System.out.println("当前的连接数量为:"+webSocketSet.size());
    }


    @OnMessage
    public void onMessage(String msg, Session session){
        System.out.println(msg);
        try {
            sendMessage(msg, session);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //推送
    public void sendMessage(String msg,Session session) throws IOException {
        for (Session session1 : webSocketSet) {
            if(session!=session1) {
                session1.getBasicRemote().sendText(msg);
            }
        }

    }
}

