package com.thatsoulyguy.minelander.render;

public class TextureProperties
{
    TextureWrapping wrapping;
    TexturePrecision precision;
    boolean flip;

    public static TextureProperties Register(boolean flip)
    {
        TextureProperties out = new TextureProperties();

        out.wrapping = TextureWrapping.REPEAT;
        out.precision = TexturePrecision.LINEAR;
        out.flip = flip;

        return out;
    }

    public static TextureProperties Register(TextureWrapping wrapping, TexturePrecision precision, boolean flip)
    {
        TextureProperties out = new TextureProperties();

        out.wrapping = wrapping;
        out.precision = precision;
        out.flip = flip;

        return out;
    }
}