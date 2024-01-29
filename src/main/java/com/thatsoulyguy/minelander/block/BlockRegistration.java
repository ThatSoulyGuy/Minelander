package com.thatsoulyguy.minelander.block;

import com.thatsoulyguy.minelander.world.BlockType;
import org.joml.Vector2i;

public class BlockRegistration
{
    public String name;
    public BlockType type;
    public Vector2i[] textureCoordinates;

    public static BlockRegistration Register(String name, BlockType type, Vector2i[] textureCoordinates)
    {
        BlockRegistration out = new BlockRegistration();

        out.name = name;
        out.type = type;
        out.textureCoordinates = textureCoordinates;

        return out;
    }
}