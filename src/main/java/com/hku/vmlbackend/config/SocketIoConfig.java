//package com.hku.vmlbackend.config;
//
//import com.corundumstudio.socketio.Configuration;
//import com.corundumstudio.socketio.SocketIOServer;
//import org.springframework.context.annotation.Bean;
//import org.springframework.stereotype.Component;
//
//import javax.annotation.PreDestroy;
//
//@Component
//public class SocketIoConfig {
//
//    private SocketIOServer server;
//
//    @Bean
//    public SocketIOServer socketIOServer() {
//        Configuration config = new Configuration();
////        config.setHostname("localhost");
//        config.setPort(8081);
//// 允许来自所有域的跨域请求
//        config.setOrigin("*");
//        server = new SocketIOServer(config);
//        server.start();  // 启动Socket.IO服务器
//        System.out.println("Socket.IO server started on port 8081");
//        return server;
//    }
//
//    @PreDestroy
//    public void stopSocketServer() {
//        if (server != null) {
//            server.stop();  // 停止Socket.IO服务器
//            System.out.println("Socket.IO server stopped");
//        }
//    }
//}
