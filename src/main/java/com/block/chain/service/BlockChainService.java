package com.block.chain.service;

import com.block.chain.models.Block;
import com.block.chain.models.BlockChain;

import java.security.NoSuchAlgorithmException;
import java.util.List;

public interface BlockChainService {
    BlockChain findBlockChain();
    BlockChain replaceBlockChain(BlockChain blockChain);

    Block addBlock(List<String> data) throws NoSuchAlgorithmException;

    boolean isValidChain(List<Block> blockChain) throws NoSuchAlgorithmException ;
}
