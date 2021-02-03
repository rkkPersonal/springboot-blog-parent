package org.xr.happy.websocket;


import org.springframework.stereotype.Component;

import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * @author Steven
 */
@Component
@ServerEndpoint(value = "/serverForWebSocket/{id}")
public class Server4WebSocket {

    private Session session;


    private static CopyOnWriteArraySet<Session> webSocketSet = new CopyOnWriteArraySet<Session>();

    /**
     * 当websocket 连接上的时候 会监听到连接上了
     * @param id  请求链接上要传的值，{id}
     * @param session
     */
    @OnOpen //事件 --登录的人.
    public void onopen(@PathParam("id")String id, Session session) {
        this.session = session;
        System.out.println("seesionId为" + session.getId());
        webSocketSet.add(session);
        System.out.println("当前的连接数量为:" + webSocketSet.size());

        System.out.println("我的id是："+id);
    }


    /**
     * 发送消息，如果休消息有变化，就推送数据到客户端
     *
     * @param msg  文本框中要传的值
     * @param session
     */
    @OnMessage
    public void onMessage(String msg, Session session) {
        System.out.println(msg);
        try {
            sendMessage(msg, session);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //推送
    public void sendMessage(String msg, Session session) throws Exception {
        for (Session session1 : webSocketSet) {
            if (session == session1) {
                Thread.sleep(5000);
                String sendMsg = "转账成功了";
                session1.getBasicRemote().sendText(sendMsg);
            }
        }

    }
}

