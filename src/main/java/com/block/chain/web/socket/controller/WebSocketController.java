package com.block.chain.web.socket.controller;

import com.block.chain.constants.Constants;
import com.block.chain.models.BlockChain;
import com.block.chain.service.BlockChainService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;

@Controller
public class WebSocketController {

    private static final Logger logger = LoggerFactory.getLogger(WebSocketController.class);

    @Autowired
    private BlockChainService blockChainService;

    @MessageMapping(Constants.WHO_AM_I)
    public void whoAmi(String whoAmI) {
        String msg = whoAmI + " is connected !!!!!";
        logger.info(msg);
    }

    /**
     * Endpoint appeler par les clients au demarrage de la socket
     * pour mettre à jour leur blockchain
     * @return
     * @throws JsonProcessingException
     */
    @SubscribeMapping(Constants.SUFFIX_STOMP_URL_SUBSCRIBER)
    public String findBlockChain() throws JsonProcessingException {
        BlockChain bc = blockChainService.findBlockChain();
        logger.info(bc.toString());
        return new ObjectMapper().writeValueAsString(bc);
    }

}
