package com.block.chain.web.socket;

import com.block.chain.constants.Constants;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    /**
     * Creation endpoint pour se connecter à Stomp
     * @param registry
     */
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint(Constants.SUFFIX_STOMP_URL_ENDPOINT)
                .setAllowedOrigins("*")
                .withSockJS();
    }

    /**
     * creation du prefix pour que les clients dialogue avec le server (client => server) +
     * creation de l'url à laquelle les clients se connecteront en tant que subscribers (server => client)
     * @param registry
     */
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.setApplicationDestinationPrefixes(Constants.PREFIX_URL_SOCKET)
                .enableSimpleBroker(Constants.SUFFIX_STOMP_URL_SUBSCRIBER); // subscriber
    }

}
