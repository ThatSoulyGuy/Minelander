package com.thatsoulyguy.minelander.render;

import static org.lwjgl.opengl.GL11.*;
public enum TexturePrecision
{
    LINEAR(GL_LINEAR),
    POINT(GL_NEAREST);

    private final int raw;

    TexturePrecision(int raw)
    {
        this.raw = raw;
    }

    public int GetRaw()
    {
        return raw;
    }
}