package com.thatsoulyguy.minelander.core;

import static org.lwjgl.glfw.GLFW.*;

public enum CursorMode
{
    NORMAL(GLFW_CURSOR_NORMAL),
    LOCKED(GLFW_CURSOR_DISABLED);

    private final int raw;

    CursorMode(int raw)
    {
        this.raw = raw;
    }

    public int GetRaw()
    {
        return raw;
    }
}