package com.yf.springboot;

import net.sf.json.JSONObject;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.HashMap;
import java.util.Map;

public class TextMessageHandler extends TextWebSocketHandler {
    private Map<String, WebSocketSession> allClients = new HashMap<>();

    /**
     * 处理文本消息
     *
     * @param session 当前发送消息的用户的链接
     * @param message 发送的消息
     * @throws Exception
     */
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        JSONObject jsonObject = JSONObject.fromObject(new String(message.asBytes()));
        String to = jsonObject.getString("toUser");//找到接收者
        String toMessage = jsonObject.getString("toMessage");//获取发送的内容
        String formUser = (String) session.getAttributes().get("name");
        String content = "收到来自" + formUser + "的消息,内容是" + toMessage;
        TextMessage textMessage = new TextMessage(content);//创建消息对象
        sengMessage(to,textMessage);
    }

    /**
     * 关闭链接
     *
     * @param session
     * @param status
     * @throws Exception
     */
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        super.afterConnectionClosed(session, status);
    }

    /**
     * 建立链接
     *
     * @param session
     * @throws Exception
     */
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        String name = (String) session.getAttributes().get("name");//获取到拦截器中设置的name
        if (name != null) {
            allClients.put(name, session);//保存当前用户和session的关系
        }
    }

    public void sengMessage(String toUser, TextMessage textMessage) {
        //获取对方的链接
        WebSocketSession socketSession = allClients.get(toUser);
        if (socketSession != null && socketSession.isOpen()) {
            try {
                socketSession.sendMessage(textMessage);
            } catch (Exception e) {

            }
        }
    }
}
