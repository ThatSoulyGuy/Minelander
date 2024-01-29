package com.thatsoulyguy.minelander.block.blocks;

import com.thatsoulyguy.minelander.block.Block;
import com.thatsoulyguy.minelander.block.BlockRegistration;
import com.thatsoulyguy.minelander.world.BlockType;
import org.joml.Vector2i;

public class TestBlock extends Block
{

    @Override
    public void Initialize()
    {

    }

    @Override
    public void OnPlace()
    {

    }

    @Override
    public void OnBreak()
    {

    }

    @Override
    public BlockRegistration Register()
    {
        return BlockRegistration.Register("test_block", BlockType.TEST, new Vector2i[] { new Vector2i(5, 11), new Vector2i(5, 11), new Vector2i(5, 11), new Vector2i(5, 11), new Vector2i(5, 11), new Vector2i(5, 11) });
    }
}