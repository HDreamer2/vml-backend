package com.hku.vmlbackend.config;

import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.annotation.SpringAnnotationScanner;
import com.hku.vmlbackend.handler.NettySocketHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * socket服务配置
 */
@Configuration
public class NettySocketConfig {

    @Value("${socketio.host:localhost}")
    private String host;

    @Value("${socketio.port:9000}")
    private Integer port;

    /**
     * netty-socketio服务器
     * @return
     **/
    @Bean(name = "nettySocketIOServer")
    public SocketIOServer socketIOServer() {
        com.corundumstudio.socketio.Configuration config = new com.corundumstudio.socketio.Configuration();
        //设置host
        config.setHostname(host);
        //设置端口
        config.setPort(port);
        return new SocketIOServer(config);
    }

    /**
     *用于扫描netty-socketio的注解，比如 @OnConnect、@OnEvent
     *
     **/
    @Bean
    public SpringAnnotationScanner springAnnotationScanner() {
        return new SpringAnnotationScanner(socketIOServer());
    }

    /**
     * 注入socket处理拦截器
     * @return
     */
    @Bean
    public NettySocketHandler nettySocketHandler(){
        return new NettySocketHandler();
    }
}
