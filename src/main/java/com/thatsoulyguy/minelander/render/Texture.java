package com.thatsoulyguy.minelander.render;

import com.thatsoulyguy.minelander.core.Logger;
import com.thatsoulyguy.minelander.core.Settings;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector2i;
import org.lwjgl.BufferUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL30.*;
import static org.lwjgl.stb.STBImage.*;

public class Texture
{
    public IntBuffer width = BufferUtils.createIntBuffer(1);
    public IntBuffer height = BufferUtils.createIntBuffer(1);
    public IntBuffer comp = BufferUtils.createIntBuffer(1);

    public String name;
    public String localPath;
    ByteBuffer image;

    public int id;

    public void Generate(TextureProperties properties)
    {
        Vector2i size = new Vector2i(width.get(), height.get());

        id = glGenTextures();
        glBindTexture(GL_TEXTURE_2D, id);

        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, properties.wrapping.GetRaw());
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, properties.wrapping.GetRaw());
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, properties.precision.GetRaw());
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, properties.precision.GetRaw());

        int components = comp.get();
        int renderType;

        renderType = GL_RGBA;

        glTexImage2D(GL_TEXTURE_2D, 0, renderType, size.x, size.y, 0, renderType, GL_UNSIGNED_BYTE, image);
        glGenerateMipmap(GL_TEXTURE_2D);
    }

    public Texture Copy()
    {
        Texture out = new Texture();

        out.name = this.name;
        out.localPath = this.localPath;

        if (this.image != null)
        {
            out.image = ByteBuffer.allocateDirect(this.image.capacity());

            this.image.rewind();

            out.image.put(this.image);

            this.image.rewind();

            out.image.flip();
        }

        out.width.put(this.width.get(0)).flip();
        out.height.put(this.height.get(0)).flip();
        out.comp.put(this.comp.get(0)).flip();

        return out;
    }

    public void CleanUp()
    {
        glBindTexture(GL_TEXTURE_2D, 0);
        stbi_image_free(image);

        glDeleteTextures(id);
    }

    public static Texture Register(String localPath, String name)
    {
        return Texture.Register(localPath, name, Settings.defaultDomain);
    }

    public static Texture Register(String localPath, String name, String domain)
    {
        Texture out = new Texture();

        out.name = name;
        out.localPath = "/assets/" + domain + "/" + localPath;

        try (InputStream in = Texture.class.getResourceAsStream(out.localPath))
        {
            if (in == null)
                throw new RuntimeException("Resource not found: " + out.localPath);

            ByteBuffer imageBuffer = ResourceToByteBuffer(in, 8 * 1024);
            ByteBuffer image = stbi_load_from_memory(imageBuffer, out.width, out.height, out.comp, 4);

            if (image == null)
                throw new RuntimeException("Failed to load a texture file!" + System.lineSeparator() + stbi_failure_reason());

            out.image = image;
        }
        catch (IOException e)
        {
            throw new RuntimeException("Failed to load resource: " + out.localPath, e);
        }

        return out;
    }

    private static ByteBuffer ResourceToByteBuffer(InputStream in, int bufferSize) throws IOException
    {
        ByteBuffer buffer;

        try (ReadableByteChannel rbc = Channels.newChannel(in))
        {
            buffer = BufferUtils.createByteBuffer(bufferSize);

            while (true)
            {
                int bytes = rbc.read(buffer);

                if (bytes == -1)
                    break;

                if (buffer.remaining() == 0)
                    buffer = ResizeBuffer(buffer, buffer.capacity() * 2);

            }
        }

        buffer.flip();

        return buffer;
    }

    private static ByteBuffer ResizeBuffer(ByteBuffer buffer, int newCapacity)
    {
        ByteBuffer newBuffer = BufferUtils.createByteBuffer(newCapacity);

        buffer.flip();
        newBuffer.put(buffer);

        return newBuffer;
    }
}