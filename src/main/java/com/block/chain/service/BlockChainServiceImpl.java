package com.block.chain.service;

import com.block.chain.models.Block;
import com.block.chain.models.BlockChain;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.util.List;

@Service
public class BlockChainServiceImpl implements BlockChainService {

    private BlockChain blockChain = new BlockChain();
    @Autowired
    private BlockService blockService;

    @Override
    public BlockChain findBlockChain() {
        if (blockChain == null || blockChain.getBlockChain().isEmpty()) {
            blockChain.getBlockChain().add(blockService.genesis());
        }

        return blockChain;
    }

    @Override
    public BlockChain replaceBlockChain(BlockChain blockChain) {
        this.blockChain = blockChain;
        return this.blockChain;
    }

    public Block addBlock(List<String> data) throws NoSuchAlgorithmException {
        Block lastBlock = findBlockChain().getBlockChain().get(this.blockChain.getBlockChain().size() - 1);
        Block newBlock = blockService.mineBlock(lastBlock, data);
        this.blockChain.getBlockChain().add(newBlock);
        return newBlock;
    }

    public boolean isValidChain(List<Block> blockChain) throws NoSuchAlgorithmException {
        if (!blockChain.get(0).equals(blockService.genesis())) return false;

        for (int i = 1; i< blockChain.size(); i++) {
            if (blockChain.get(i).getLastHash().equals(blockChain.get(i-1).getHash())
                    || !blockService.isValidHash(blockChain.get(i))) {
                return false;
            }
        }
        return true;
    }
}
