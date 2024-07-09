package com.hku.vmlbackend.auth;

import com.corundumstudio.socketio.AuthorizationListener;
import com.corundumstudio.socketio.AuthorizationResult;
import com.corundumstudio.socketio.HandshakeData;

/**
 * 认证监听器
 */
public class SocketAuthListener implements AuthorizationListener {

    private SocketAuthService authService;

    public boolean isAuthorized(HandshakeData handshakeData) {
        return authService.auth(handshakeData);
    }

    public SocketAuthListener(SocketAuthService authService) {
        this.authService = authService;
    }

    public SocketAuthListener() {
        this.authService = new DefaultSocketAuthServiceImpl();
    }

    @Override
    public AuthorizationResult getAuthorizationResult(HandshakeData handshakeData) {
        if (isAuthorized(handshakeData)) {
            return AuthorizationResult.SUCCESSFUL_AUTHORIZATION;
        } else {
            return AuthorizationResult.FAILED_AUTHORIZATION;
        }
    }
}
