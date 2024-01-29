package com.thatsoulyguy.minelander.block.blocks;

import com.thatsoulyguy.minelander.block.Block;
import com.thatsoulyguy.minelander.block.BlockRegistration;
import com.thatsoulyguy.minelander.world.BlockType;
import org.joml.Vector2i;

public class StoneBlock extends Block
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
        return BlockRegistration.Register("stone_block", BlockType.STONE, new Vector2i[] { new Vector2i(1, 0), new Vector2i(1, 0), new Vector2i(1, 0), new Vector2i(1, 0), new Vector2i(1, 0), new Vector2i(1, 0) });
    }
}