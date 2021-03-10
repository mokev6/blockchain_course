package com.block.chain.service;

import com.block.chain.models.Block;

import java.security.NoSuchAlgorithmException;
import java.util.List;

public interface BlockService {

    Block genesis();

    Block mineBlock(Block lastBlock, List<String> data) throws NoSuchAlgorithmException;

    String hash(Long timestamp, String lastHash, Long nonce, List<String>  data, Integer difficulty) throws NoSuchAlgorithmException;

    boolean isValidHash(Block block) throws NoSuchAlgorithmException ;

    Integer adjustDifficulty(Block lastBlock, Long currentTimestamp);
}
