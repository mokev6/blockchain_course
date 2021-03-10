package com.block.chain.rest.controllers;

import com.block.chain.constants.Constants;
import com.block.chain.models.BlockChain;
import com.block.chain.pojo.PostPojo;
import com.block.chain.service.BlockChainService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.converter.StringMessageConverter;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

@RestController
public class BlockChainController {

    @Autowired
    private BlockChainService blockChainService;

    @Autowired
    private SimpMessagingTemplate template;

    @Value("${whoAmI}")
    private String whoAmI;

    private static final Logger logger = LoggerFactory.getLogger(BlockChainController.class);


    @GetMapping(Constants.CURRENT_BLOCK_CHAIN_ENDPOINT)
    public BlockChain getBlockChain() {
        BlockChain bc = blockChainService.findBlockChain();
        logger.info(bc.toString());
        return bc;
    }

    @PostMapping(Constants.MINE_ENDPOINT)
    public Mono<BlockChain> mine(@RequestBody PostPojo pojo) throws NoSuchAlgorithmException, JsonProcessingException {
        List<String> datas = new ArrayList<>();
        datas.add(pojo.getData());
        blockChainService.addBlock(datas);

        this.template.setMessageConverter(new StringMessageConverter());
        template.convertAndSend(Constants.SUFFIX_STOMP_URL_SUBSCRIBER, new ObjectMapper().writeValueAsString(blockChainService.findBlockChain())); // publie dans la socket

        return WebClient.create(whoAmI)
                        .get()
                        .uri(Constants.CURRENT_BLOCK_CHAIN_ENDPOINT)
                        .retrieve()
                        .bodyToMono(BlockChain.class);
    }

}
