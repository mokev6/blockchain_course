package com.block.chain.models;

import java.util.List;
import java.util.Objects;

public class Block {

    private Long timestamp;
    private String lastHash;
    private String hash;
    private List<String> data;
    private Long nonce;
    private Integer difficulty;


    public Block(Long timestamp, String lastHash, String hash, List<String> data, Long nonce, Integer difficulty) {
        this.timestamp = timestamp;
        this.lastHash = lastHash;
        this.hash = hash;
        this.data = data;
        this.nonce = nonce;
        this.difficulty = difficulty;
    }

    public Block() {

    }

    @Override
    public String toString() {
        return "Block{" +
                "timestamp=" + timestamp +
                ", lastHash='" + lastHash + '\'' +
                ", hash='" + hash + '\'' +
                ", nonce=" + nonce +
                ", difficulty=" + difficulty +
                ", data=" + data +
                '}';
    }

    /*
    public static Block genesis() {
        return new Block(1L, "----","f1th-54f", new ArrayList<>(), 0L, Constants.DIFFICULTY);
    }

    public static Block mineBlock(Block lastBlock, List<String>  data) throws NoSuchAlgorithmException {
        Long timestamp;
        String lastHash = lastBlock.hash;
        Long nonce = 0L;
        String hash ;
        Integer difficulty = lastBlock.difficulty;
        do {
            nonce ++;
            timestamp = System.currentTimeMillis();

            hash = Block.hash(timestamp, lastHash, nonce, data, difficulty);
        } while (!hash.substring(0, difficulty).equals("0".repeat(difficulty)));


        return new Block(timestamp, lastHash, hash, data, nonce, difficulty);
    }

    public static String hash(Long timestamp, String lastHash, Long nonce, List<String>  data, Integer difficulty) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        return digest.digest((timestamp + lastHash + data + nonce + difficulty).getBytes(StandardCharsets.UTF_8)).toString();
    }

    public static boolean isValidHash(Block block) throws NoSuchAlgorithmException {
        return hash(block.timestamp, block.lastHash, block.nonce, block.data, block.difficulty).equals(block.hash);
    } */

    public Long getTimestamp() {
        return timestamp;
    }

    public String getLastHash() {
        return lastHash;
    }

    public String getHash() {
        return hash;
    }

    public List<String> getData() {
        return data;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Block)) return false;
        Block block = (Block) o;
        return getHash().equals(block.getHash());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getHash());
    }


    public Long getNonce() {
        return nonce;
    }

    public void setNonce(Long nonce) {
        this.nonce = nonce;
    }

    public Integer getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Integer difficulty) {
        this.difficulty = difficulty;
    }
}
