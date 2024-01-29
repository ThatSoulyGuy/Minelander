package com.thatsoulyguy.minelander.core;

public enum MouseCode
{
    LEFT(0),
    MIDDLE(2),
    RIGHT(1);

    private final int raw;

    MouseCode(int raw)
    {
        this.raw = raw;
    }

    public int GetRaw()
    {
        return raw;
    }
}