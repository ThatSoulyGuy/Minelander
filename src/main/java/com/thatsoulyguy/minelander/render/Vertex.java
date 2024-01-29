package com.thatsoulyguy.minelander.render;

import org.joml.Vector2f;
import org.joml.Vector3f;

public class Vertex
{
    public Vector3f position;
    public Vector3f color;
    public Vector2f textureCoordinates;

    public static Vertex Register(Vector3f position, Vector2f textureCoordinates)
    {
        Vertex out = new Vertex();

        out.position = position;
        out.color = new Vector3f(1.0f, 1.0f, 1.0f);
        out.textureCoordinates = textureCoordinates;

        return out;
    }

    public static Vertex Register(Vector3f position, Vector3f color, Vector2f textureCoordinates)
    {
        Vertex out = new Vertex();

        out.position = position;
        out.color = color;
        out.textureCoordinates = textureCoordinates;

        return out;
    }
}