package com.block.chain.web.socket.client;

import com.block.chain.constants.Constants;
import com.block.chain.models.BlockChain;
import com.block.chain.service.BlockChainService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.Nullable;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;

public class ClientStompSessionHandler extends StompSessionHandlerAdapter {

    private static final Logger logger = LoggerFactory.getLogger(ClientStompSessionHandler.class);

    private String whoAmI;
    private BlockChainService blockChainService;

    public ClientStompSessionHandler(String whoAmI, BlockChainService blockChainService) {
        this.whoAmI= whoAmI;
        this.blockChainService = blockChainService;
    }

    @Override
    public void afterConnected(StompSession session, StompHeaders connectedHeaders) {
        session.subscribe(Constants.SUFFIX_STOMP_URL_SUBSCRIBER , this);
        session.subscribe("/app/" + Constants.SUFFIX_STOMP_URL_SUBSCRIBER , this); // appel le endpoint pour mettre à jour la blockchain à la connection de la socket
        session.send(Constants.PREFIX_URL_SOCKET + Constants.WHO_AM_I, whoAmI);
    }

    @Override
    public void handleFrame(StompHeaders headers, @Nullable Object payload) {
        logger.info("Handle Frame");
        String payloadStr = (String) payload;
        try {
            BlockChain bc = new ObjectMapper().readerFor(BlockChain.class).readValue(payloadStr);
            bc = blockChainService.replaceBlockChain(bc);
            logger.info(bc.toString());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void handleException(StompSession session, @Nullable StompCommand command, StompHeaders headers, byte[] payload, Throwable exception) {
        logger.error("Handle Exception");
    }

}
