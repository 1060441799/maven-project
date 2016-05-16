package com.newframe.web.quartz;

import com.alibaba.fastjson.JSON;
import com.newframe.core.util.UUIDGenerator;
import com.newframe.web.model.ServerInfoVO;
import com.newframe.web.websocket.WSMessageTopic;
import com.newframe.web.websocket.WSMessageVO;
import com.newframe.web.websocket.WebSocketHander;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.TextMessage;

import java.util.Date;

/**
 * Created by xm on 2016/4/26.
 */
public class ServerInfoQtz{

    @Autowired
    private WebSocketHander wsh;

    public void execute(){
        System.out.println("Send message at " + new Date());
        WSMessageVO wv = new WSMessageVO();
        wv.setMessageId(UUIDGenerator.generate());
        wv.setMessageDate(new Date());
        wv.setMessageData(new ServerInfoVO());
        wv.setTopic(WSMessageTopic.SERVERTOUSERS);
        wsh.sendMessageToUsers(new TextMessage(JSON.toJSONString(wv)));
    }
}
