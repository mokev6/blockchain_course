package com.block.chain.service;

import com.block.chain.constants.Constants;
import com.block.chain.models.Block;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

@Service
public class BlockServiceImpl implements BlockService {

    @Override
    public Block genesis() {
        return new Block(1L, "----","f1th-54f", new ArrayList<>(), 0L, Constants.DIFFICULTY);
    }

    @Override
    public Block mineBlock(Block lastBlock, List<String> data) throws NoSuchAlgorithmException {
        Long timestamp;
        String lastHash = lastBlock.getHash();
        Long nonce = 0L;
        String hash ;
        Integer difficulty = lastBlock.getDifficulty();

        // *** PROOF OF WORK *** //
        do {
            nonce ++;
            timestamp = System.currentTimeMillis();
            difficulty = adjustDifficulty(lastBlock, timestamp);
            hash = hash(timestamp, lastHash, nonce, data, difficulty);
        } while (!hash.substring(0, difficulty).equals("0".repeat(difficulty)));
        // *** END PROOF OF WORK *** //

        return new Block(timestamp, lastHash, hash, data, nonce, difficulty);
    }

    @Override
    public String hash(Long timestamp, String lastHash, Long nonce, List<String>  data, Integer difficulty) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        return bytesToHex(digest.digest((timestamp + lastHash  + data + nonce + difficulty).getBytes(StandardCharsets.UTF_8)));
    }

    @Override
    public boolean isValidHash(Block block) throws NoSuchAlgorithmException {
        return hash(block.getTimestamp(), block.getLastHash(), block.getNonce(), block.getData(), block.getDifficulty()).equals(block.getHash());
    }

    @Override
    public Integer adjustDifficulty(Block lastBlock, Long currentTimestamp) {
        return lastBlock.getTimestamp() + Constants.MINE_RATE > currentTimestamp ? lastBlock.getDifficulty() + 1 : lastBlock.getDifficulty() - 1;
    }

    private String bytesToHex(byte[] hash) {
        StringBuilder hexString = new StringBuilder(2 * hash.length);
        for (int i = 0; i < hash.length; i++) {
            String hex = Integer.toHexString(0xff & hash[i]);
            if(hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }
}
