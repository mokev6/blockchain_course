package com.block.chain.models;

import static org.junit.jupiter.api.Assertions.*;

import com.block.chain.service.BlockService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Spy;
import org.springframework.test.context.junit4.SpringRunner;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
public class BlockTest {
     List<String> data = new ArrayList<>();
     Block lastBlock;
     Block block;
    private static final String DATA_NAME = "foo";
    @Spy
    private BlockService blockService;

    @Before
     public void setUp() throws NoSuchAlgorithmException {
        data.add(DATA_NAME);
        lastBlock = blockService.genesis();
        block = blockService.mineBlock(lastBlock, data);
    }


    @Test
     public void shouldHaveOneData() {
        assertEquals(1, block.getData().size());
    }


    @Test
     public void shouldHaveOneDataNamedData() {
        assertEquals(DATA_NAME, data.get(0));
    }
}
