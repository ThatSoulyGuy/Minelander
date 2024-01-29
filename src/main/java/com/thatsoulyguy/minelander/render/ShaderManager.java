package com.thatsoulyguy.minelander.render;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class ShaderManager
{
    private static final ConcurrentMap<String, Shader> registeredShaders = new ConcurrentHashMap<>();

    public static void Register(Shader shader)
    {
        registeredShaders.put(shader.name, shader);
    }

    public static Shader Get(String name)
    {
        return registeredShaders.get(name).Copy();
    }
}