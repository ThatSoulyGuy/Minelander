package com.thatsoulyguy.minelander.world;

public enum BlockType
{
    AIR(0),
    TEST(1),
    GRASS_BLOCK(2),
    DIRT(3),
    STONE(4);

    private final int raw;

    BlockType(int raw)
    {
        this.raw = raw;
    }

    public int GetRaw()
    {
        return raw;
    }

    public static BlockType FromRaw(int raw)
    {
        for(BlockType type : BlockType.values())
        {
            if(type.GetRaw() == raw)
                return type;
        }

        return null;
    }
}