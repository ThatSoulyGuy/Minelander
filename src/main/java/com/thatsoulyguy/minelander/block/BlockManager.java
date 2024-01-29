package com.thatsoulyguy.minelander.block;

import com.thatsoulyguy.minelander.world.BlockType;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class BlockManager
{
    private static final ConcurrentMap<BlockType, Block> blocks = new ConcurrentHashMap<>();

    public static void Register(Block block)
    {
        blocks.put(block.type, block);
    }

    public static Block Get(BlockType type)
    {
        return blocks.get(type);
    }
}