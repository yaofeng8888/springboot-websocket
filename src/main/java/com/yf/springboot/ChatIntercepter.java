package com.yf.springboot;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.lang.Nullable;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

import java.util.Map;

/**
 * websocket握手的拦截器，检查握手请求和响应，对websockethander传递属性，
 */
public class ChatIntercepter extends HttpSessionHandshakeInterceptor {
    //握手之前
    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
        //区分链接。通过用户名区分链接是谁
        //获取用户的名字。地址是rest风格。定义的地址最后一位是名字，所以只要找到请求地址的最后一位就可以了
        String url = request.getURI().toString();
        String name = url.substring(url.lastIndexOf("/")+1);
        //给当前链接设置名字
        attributes.put("name",name);//建议将name抽取未静态常量
        return super.beforeHandshake(request, response, wsHandler, attributes);
    }

    //握手之后
    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, @Nullable Exception ex) {
        System.out.println("握手之后");
        super.afterHandshake(request, response, wsHandler, ex);
    }
}
