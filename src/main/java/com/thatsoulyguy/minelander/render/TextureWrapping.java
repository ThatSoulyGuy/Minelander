package com.thatsoulyguy.minelander.render;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.*;

public enum TextureWrapping
{
    REPEAT(GL_REPEAT),
    CLAMP(GL_CLAMP_TO_BORDER);

    private final int raw;

    TextureWrapping(int raw)
    {
        this.raw = raw;
    }

    public int GetRaw()
    {
        return raw;
    }
}