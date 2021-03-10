package com.block.chain.web.socket.client;

import com.block.chain.constants.Constants;
import com.block.chain.service.BlockChainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.converter.StringMessageConverter;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import org.springframework.web.socket.sockjs.client.RestTemplateXhrTransport;
import org.springframework.web.socket.sockjs.client.SockJsClient;
import org.springframework.web.socket.sockjs.client.Transport;
import org.springframework.web.socket.sockjs.client.WebSocketTransport;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class ClientWebSocketSockJsStompConfig {

    @Value("${connected:true}")
    private Boolean connected;
    @Value("${clientsUrls:null}")
    private List<String> clientsUrl = new ArrayList<>();
    @Value("${whoAmI:null}")
    private String whoAmI;
    @Autowired
    private BlockChainService blockChainService;

    @Bean
    public WebSocketStompClient webSocketStompClient(WebSocketClient webSocketClient) {
        WebSocketStompClient webSocketStompClient = new WebSocketStompClient(webSocketClient);
        if (connected) {
            webSocketStompClient.setMessageConverter(new StringMessageConverter());
            clientsUrl.stream()
                    .forEach(url -> webSocketStompClient.connect(url + Constants.SUFFIX_STOMP_URL_ENDPOINT, new ClientStompSessionHandler(whoAmI, blockChainService)));
        }

        return webSocketStompClient;
    }

    @Bean
    public WebSocketClient webSocketClient() {
        List<Transport> transports = new ArrayList<>();
        transports.add(new WebSocketTransport(new StandardWebSocketClient()));
        transports.add(new RestTemplateXhrTransport());
        return new SockJsClient(transports);
    }

}
