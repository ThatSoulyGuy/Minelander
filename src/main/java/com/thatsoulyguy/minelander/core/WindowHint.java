package com.thatsoulyguy.minelander.core;

import java.nio.ByteBuffer;

import static org.lwjgl.glfw.GLFW.*;

public class WindowHint
{
    int hint;
    Object value;

    public void Apply()
    {
        if(value instanceof Integer)
            glfwWindowHint(hint, (Integer) value);
        else if (value instanceof CharSequence)
            glfwWindowHintString(hint, (CharSequence) value);
        else if (value instanceof ByteBuffer)
            glfwWindowHintString(hint, (ByteBuffer) value);
    }

    public static WindowHint Register(int hint, Object value)
    {
        WindowHint out = new WindowHint();

        out.hint = hint;
        out.value = value;

        return out;
    }
}