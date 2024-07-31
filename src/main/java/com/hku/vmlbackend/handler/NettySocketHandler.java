package com.hku.vmlbackend.handler;

/**
 * AUTHOR:XYS
 * DESCRIPCTION:NO BUG
 */

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.annotation.OnConnect;
import com.corundumstudio.socketio.annotation.OnDisconnect;
import com.corundumstudio.socketio.annotation.OnEvent;
import com.hku.vmlbackend.service.impl.LinearRegressionServiceImpl;
import com.hku.vmlbackend.util.NettySocketUtil;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;


/**
 * socket处理拦截器
 * @author machenike
 */
@Slf4j
public class NettySocketHandler implements CommandLineRunner {

    /**
     * 日志
     */
    private final static Logger logger = LoggerFactory.getLogger(NettySocketHandler.class);

    /**
     * 客户端保存用Map
     */
    public static Map<String, SocketIOClient> clientMap = new ConcurrentHashMap<>();

    /**
     * 连接数
     */
    public static AtomicInteger onlineCount = new AtomicInteger(0);

    private final static String QUERY_CLIENT_ID = "clientId";
    @Autowired
    private SocketIOServer socketIOServer;
    @Autowired
    private LinearRegressionServiceImpl linearRegressionService;
    /**
     * 客户端连上socket服务器时执行此事件
     * @param client
     */
    @OnConnect
    public void onConnect(SocketIOClient client) {
        String clientId = client.getHandshakeData().getSingleUrlParam(QUERY_CLIENT_ID);
        log.info("onConnect: [clientId="+clientId+"]");
        if(clientId!=null) {
            clientMap.put(clientId, client);
            onlineCount.addAndGet(1);
            log.info("connect success: [clientId="+clientId+",onlineCount="+onlineCount.get()+"]");
        }
        // 客户端连接成功后，调用服务方法发送之前存储的 pending data
//        linearRegressionService.sendPendingDataWhenClientIdAvailable(clientId);
    }


    /**
     * 客户端断开socket服务器时执行此事件
     * @param client
     */
    @OnDisconnect
    public void onDisconnect(SocketIOClient client) {
        String clientId = client.getHandshakeData().getSingleUrlParam(QUERY_CLIENT_ID);
        if (clientId != null) {
            clientMap.remove(clientId);
            client.disconnect();
            onlineCount.addAndGet(-1);
            log.info("disconnect success: [clientId="+clientId+",onlineCount="+onlineCount.get()+"]");
        }
    }

    /**
     *
     * @param client
     */
    @OnEvent( value = "hello")
    public void onMessage(SocketIOClient client, AckRequest request, Object data) {
        String clientId = client.getHandshakeData().getSingleUrlParam(QUERY_CLIENT_ID);
//        TODO:获取seesionID
        String sessionId = client.getSessionId().toString();
        log.info("onepochData: [sessionId="+sessionId+",clientId="+clientId+",data="+data+"]");
        //request.sendAckData("message is revived");
        // 将接收到的data转换为字符串，然后拼接到消息中
        String message = "I am socketio server, your message is " + data.toString();

        // 假设NettySocketUtil.sendNotice方法接受一个String类型的参数
        NettySocketUtil.sendNotice(message);
        client.sendEvent("ack",1);
    }



    @Override
    public void run(String... args) throws Exception {
        log.info("socketHandler start-------------------------------");
        socketIOServer.start();

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            socketIOServer.stop();
        }));
    }

}