package org.xr.happy.websocket;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.xr.happy.common.constant.MessageType;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * @author Steven
 */
@Component
@ServerEndpoint(value = "/serverForWebSocket/{id}")
public class Server4WebSocket {


    private static final Logger logger = LoggerFactory.getLogger(Server4WebSocket.class);

    private Session session;


    private static CopyOnWriteArraySet<Session> webSocketSet = new CopyOnWriteArraySet<Session>();

    /**
     * 当websocket 连接上的时候 会监听到连接上了
     *
     * @param id      请求链接上要传的值，{id}
     * @param session
     */
    @OnOpen //事件 --登录的人.
    public void onopen(@PathParam("id") String id, Session session) {
        this.session = session;
        System.out.println("seesionId为" + session.getId());
        webSocketSet.add(session);
        System.out.println("当前的连接数量为:" + webSocketSet.size());

        System.out.println("我的id是：" + id);
    }


    @OnClose
    public void onclose(Session session) {

        webSocketSet.remove(session);

        logger.info(session.getId() + "退出成功，" + "当前连接人数：" + webSocketSet.size());

    }

    /**
     * 发送消息，如果休消息有变化，就推送数据到客户端
     *
     * @param msg     文本框中要传的值
     * @param session
     */
    @OnMessage
    public void onMessage(String msg, Session session) {
        logger.info("收到消息：" + msg);
        try {
            switch (msg) {
                case MessageType.TO_USER:
                    sendMessage(msg, session);
                    break;
                case MessageType.TO_SHARE:
                    groupMessage(msg, session);
                    break;
                default:
                    break;
            }
        } catch (Exception e) {
            logger.error("error :", e.getMessage());
        }
    }

    //推送
    public void groupMessage(String msg, Session session) throws Exception {
        for (Session session1 : webSocketSet) {
            Thread.sleep(5000);
            Random random = new Random();
            int money = random.nextInt(100000);
            String sendMsg = "转账成功了" + money + "元";
            session1.getBasicRemote().sendText(sendMsg);
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

