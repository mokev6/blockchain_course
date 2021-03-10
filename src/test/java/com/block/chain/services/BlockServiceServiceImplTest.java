package com.block.chain.services;

import com.block.chain.models.Block;
import com.block.chain.service.BlockServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Spy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.junit4.SpringRunner;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
public class BlockServiceServiceImplTest {

    private Block block;
    private Block lastBlock;

    @Spy
    private BlockServiceImpl blockServiceImpl;

    private static final Logger logger = LoggerFactory.getLogger(BlockServiceServiceImplTest.class);

    @Before
    public void init() throws NoSuchAlgorithmException {
        List<String> data = new ArrayList<>();
        data.add("test");
        lastBlock = blockServiceImpl.genesis();
        block = blockServiceImpl.mineBlock(lastBlock, data);
    }

    @Test
    public void shouldGenerateHash_matchDifficulty() {
        logger.info(block.toString());
        Assert.assertTrue(block.getHash().substring(0, block.getDifficulty()).equals("0".repeat(block.getDifficulty())));
    }

    @Test
    public void shouldLowerDifficulty_whenMiningTakesTime() {
        Assert.assertEquals(Long.valueOf(block.getDifficulty() - 1), Long.valueOf(blockServiceImpl.adjustDifficulty(block, block.getTimestamp() + 36000)));
    }

    @Test
    public void shouldRaiseDifficulty_whenMiningGoesQuickly() {
        Assert.assertEquals(Long.valueOf(block.getDifficulty() + 1), Long.valueOf(blockServiceImpl.adjustDifficulty(block, block.getTimestamp() + 1)));
    }
}
