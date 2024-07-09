package com.hku.vmlbackend.auth;

/**
 * AUTHOR:XYS
 * DESCRIPCTION:NO BUG
 */
import com.corundumstudio.socketio.HandshakeData;

/**
 * @author machenike
 */
public interface SocketAuthService {
    /**
     * 对连接的socket客户端进行认证
     * @param handshakeData
     * @return
     */
    boolean auth(HandshakeData handshakeData);
}