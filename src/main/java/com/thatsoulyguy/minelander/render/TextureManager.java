package com.thatsoulyguy.minelander.render;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class TextureManager
{
    private static final ConcurrentMap<String, Texture> registeredTextures = new ConcurrentHashMap<>();

    public static void Register(Texture texture)
    {
        registeredTextures.put(texture.name, texture);
    }

    public static Texture Get(String name)
    {
        return registeredTextures.get(name).Copy();
    }
}