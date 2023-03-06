package com.example.websockettestproject.websocket;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.ArrayList;
import java.util.List;

@Component
@Log4j2
public class TestWebSocketHandler extends TextWebSocketHandler{
    private static List<WebSocketSession> list = new ArrayList<>();

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message)throws Exception{
        String payload = message.getPayload();
        System.out.println(message);
        log.info(payload);

        for (WebSocketSession sessions : list) {
            sessions.sendMessage(message);
        }
    }

    //클라이언트 접속시 사용되는 메소드
    @Override
    public void afterConnectionEstablished(WebSocketSession session)throws Exception{
        list.add(session);
        log.info(session+"의 접속");
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status)throws Exception{
        list.remove(session);
        log.info(session+" 아웃");
    }
}