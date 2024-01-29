package com.thatsoulyguy.minelander.block;

import com.thatsoulyguy.minelander.world.BlockType;
import org.joml.Vector2i;

public abstract class Block
{
    public String name;
    public BlockType type;
    public Vector2i[] textureCoordinates;

    public Block()
    {
        BlockRegistration registration = Register();

        name = registration.name;
        type = registration.type;
        textureCoordinates = registration.textureCoordinates;

        Initialize();
    }

    public abstract void Initialize();

    public abstract void OnPlace();
    public abstract void OnBreak();

    public abstract BlockRegistration Register();
}