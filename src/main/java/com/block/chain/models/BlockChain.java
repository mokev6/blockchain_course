package com.block.chain.models;

import java.util.ArrayList;
import java.util.List;

public class BlockChain {

    private List<Block> blockChain = new ArrayList<>();

    public BlockChain() {
        this.blockChain = new ArrayList<>();
        //this.blockChain.add(Block.genesis());
    }

    public List<Block> getBlockChain() {
        return blockChain;
    }

    @Override
    public String toString() {
        return "BlockChain{" +
                "blockChain=" + blockChain +
                '}';
    }


    public void setBlockChain(List<Block> blockChain) {
        this.blockChain = blockChain;
    }
}
