package se.artcomputer.f1.bingo;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import se.artcomputer.f1.bingo.controller.auth.StompAuthChannelInterceptor;
import se.artcomputer.f1.bingo.controller.auth.WsHandshakeAuthInterceptor;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    private final WsHandshakeAuthInterceptor handshakeAuth;
    private final StompAuthChannelInterceptor stompAuth;

    public WebSocketConfig(WsHandshakeAuthInterceptor handshakeAuth,
                           StompAuthChannelInterceptor stompAuth) {
        this.handshakeAuth = handshakeAuth;
        this.stompAuth = stompAuth;
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/chat-ws")
                .addInterceptors(handshakeAuth)
                .setAllowedOriginPatterns("https://*","http://*"); // eller lås till din domän
    }

    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(stompAuth);
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("/topic");
        registry.setApplicationDestinationPrefixes("/app");
    }
}
