package com.thatsoulyguy.minelander.render;

import com.thatsoulyguy.minelander.math.Transform;
import com.thatsoulyguy.minelander.thread.MainThreadExecutor;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.lwjgl.system.MemoryUtil;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

public class Mesh
{
    public String name;
    public List<Vertex> vertices = Collections.synchronizedList(new ArrayList<>());
    public List<Integer> indices = Collections.synchronizedList(new ArrayList<>());
    private Texture texture;
    public Shader shader;
    public Transform transform = Transform.Register(new Vector3f(0.0F, 0.0F, 0.0F));

    private int VAO, PBO, CBO, TBO, EBO;

    public boolean firstGeneration = true;

    public void GenerateCube()
    {
        vertices = new ArrayList<>()
        {
            {
                add(Vertex.Register(new Vector3f(-0.5f, 0.5f, -0.5f), new Vector2f(0.0f, 1.0f)));
                add(Vertex.Register(new Vector3f( 0.5f, 0.5f, -0.5f), new Vector2f(1.0f, 1.0f)));
                add(Vertex.Register(new Vector3f(-0.5f, 0.5f,  0.5f), new Vector2f(0.0f, 0.0f)));
                add(Vertex.Register(new Vector3f( 0.5f, 0.5f,  0.5f), new Vector2f(1.0f, 0.0f)));

                add(Vertex.Register(new Vector3f(-0.5f, -0.5f,  0.5f), new Vector2f(0.0f, 1.0f)));
                add(Vertex.Register(new Vector3f( 0.5f, -0.5f,  0.5f), new Vector2f(1.0f, 1.0f)));
                add(Vertex.Register(new Vector3f(-0.5f, -0.5f, -0.5f), new Vector2f(0.0f, 0.0f)));
                add(Vertex.Register(new Vector3f( 0.5f, -0.5f, -0.5f), new Vector2f(1.0f, 0.0f)));


                add(Vertex.Register(new Vector3f(-0.5f,  0.5f,  0.5f), new Vector2f(0.0f, 1.0f)));
                add(Vertex.Register(new Vector3f( 0.5f,  0.5f,  0.5f), new Vector2f(1.0f, 1.0f)));
                add(Vertex.Register(new Vector3f(-0.5f, -0.5f,  0.5f), new Vector2f(0.0f, 0.0f)));
                add(Vertex.Register(new Vector3f( 0.5f, -0.5f,  0.5f), new Vector2f(1.0f, 0.0f)));

                add(Vertex.Register(new Vector3f( 0.5f,  0.5f, -0.5f), new Vector2f(0.0f, 1.0f)));
                add(Vertex.Register(new Vector3f(-0.5f,  0.5f, -0.5f), new Vector2f(1.0f, 1.0f)));
                add(Vertex.Register(new Vector3f( 0.5f, -0.5f, -0.5f), new Vector2f(0.0f, 0.0f)));
                add(Vertex.Register(new Vector3f(-0.5f, -0.5f, -0.5f), new Vector2f(1.0f, 0.0f)));


                add(Vertex.Register(new Vector3f(0.5f,  0.5f,  0.5f), new Vector2f(0.0f, 1.0f)));
                add(Vertex.Register(new Vector3f(0.5f,  0.5f, -0.5f), new Vector2f(1.0f, 1.0f)));
                add(Vertex.Register(new Vector3f(0.5f, -0.5f,  0.5f), new Vector2f(0.0f, 0.0f)));
                add(Vertex.Register(new Vector3f(0.5f, -0.5f, -0.5f), new Vector2f(1.0f, 0.0f)));

                add(Vertex.Register(new Vector3f(-0.5f,  0.5f, -0.5f), new Vector2f(0.0f, 1.0f)));
                add(Vertex.Register(new Vector3f(-0.5f,  0.5f,  0.5f), new Vector2f(1.0f, 1.0f)));
                add(Vertex.Register(new Vector3f(-0.5f, -0.5f, -0.5f), new Vector2f(0.0f, 0.0f)));
                add(Vertex.Register(new Vector3f(-0.5f, -0.5f,  0.5f), new Vector2f(1.0f, 0.0f)));
            }
        };

        indices = new ArrayList<>()
        {
            {
                add(2); add(1); add(0);
                add(3); add(1); add(2);

                add(6); add(5); add(4);
                add(7); add(5); add(6);

                add(10); add(9); add(8);
                add(11); add(9); add(10);

                add(14); add(13); add(12);
                add(15); add(13); add(14);

                add(18); add(17); add(16);
                add(19); add(17); add(18);

                add(22); add(21); add(20);
                add(23); add(21); add(22);
            }
        };


        SetTexture("test");
        Generate();
    }

    public void GenerateSquare()
    {
        vertices = new ArrayList<>()
        {
            {
                add(Vertex.Register(new Vector3f(-0.5f,  0.5f, 0.0f), new Vector2f(0.0f, 1.0f)));
                add(Vertex.Register(new Vector3f( 0.5f,  0.5f, 0.0f), new Vector2f(1.0f, 1.0f)));
                add(Vertex.Register(new Vector3f(-0.5f, -0.5f, 0.0f), new Vector2f(0.0f, 0.0f)));
                add(Vertex.Register(new Vector3f( 0.5f, -0.5f, 0.0f), new Vector2f(1.0f, 0.0f)));
            }
        };

        indices = new ArrayList<>()
        {
            {
                add(0);
                add(1);
                add(2);

                add(1);
                add(3);
                add(2);
            }
        };

        SetTexture("test");
        Generate();
    }

    public void GenerateTriangle()
    {
        vertices = new ArrayList<>()
        {
            {
                add(Vertex.Register(new Vector3f( 0.0f,  0.5f, 0.0f), new Vector2f(0.5f, 1.0f)));
                add(Vertex.Register(new Vector3f(-0.5f, -0.5f, 0.0f), new Vector2f(0.0f, 0.0f)));
                add(Vertex.Register(new Vector3f( 0.5f, -0.5f, 0.0f), new Vector2f(1.0f, 0.0f)));
            }
        };

        indices = new ArrayList<>()
        {
            {
                add(0);
                add(1);
                add(2);
            }
        };

        SetTexture("test");
        Generate();
    }

    public void Generate()
    {
        if(firstGeneration)
        {
            shader.Generate();
            texture.Generate(TextureProperties.Register(TextureWrapping.REPEAT, TexturePrecision.POINT, true));
        }

        VAO = glGenVertexArrays();
        glBindVertexArray(VAO);


        FloatBuffer positionBuffer = MemoryUtil.memAllocFloat(vertices.size() * 3);

        vertices.forEach(vertex -> positionBuffer.put(new float[]{vertex.position.x, vertex.position.y, vertex.position.z}));
        positionBuffer.flip();

        PBO = GenerateBuffer(positionBuffer, 0, 3);

        FloatBuffer colorBuffer = MemoryUtil.memAllocFloat(vertices.size() * 3);

        vertices.forEach(vertex -> colorBuffer.put(new float[]{vertex.color.x, vertex.color.y, vertex.color.z}));
        colorBuffer.flip();

        CBO = GenerateBuffer(colorBuffer, 1, 3);


        FloatBuffer textureBuffer = MemoryUtil.memAllocFloat(vertices.size() * 2);

        vertices.forEach(vertex -> textureBuffer.put(new float[]{vertex.textureCoordinates.x, vertex.textureCoordinates.y}));
        textureBuffer.flip();

        TBO = GenerateBuffer(textureBuffer, 2, 2);


        IntBuffer indicesBuffer = MemoryUtil.memAllocInt(indices.size());

        indicesBuffer.put(indices.stream().mapToInt(i -> i).toArray()).flip();

        EBO = glGenBuffers();

        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, EBO);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, indicesBuffer, GL_STATIC_DRAW);

        MemoryUtil.memFree(indicesBuffer);

        glBindVertexArray(0);

        MemoryUtil.memFree(positionBuffer);
        MemoryUtil.memFree(colorBuffer);
        MemoryUtil.memFree(textureBuffer);

        if(firstGeneration)
        {
            shader.Bind();
            shader.SetUniform("diffuse", 0);
            shader.UnBind();
        }

        firstGeneration = false;
    }

    public void SetTexture(String texture)
    {
        this.texture = TextureManager.Get(texture);
    }

    private int GenerateBuffer(FloatBuffer buffer, int start, int end)
    {
        int id = glGenBuffers();

        glBindBuffer(GL_ARRAY_BUFFER, id);
        glBufferData(GL_ARRAY_BUFFER, buffer, GL_STATIC_DRAW);

        glVertexAttribPointer(start, end, GL_FLOAT, false, 0, 0);
        glEnableVertexAttribArray(start);
        glBindBuffer(GL_ARRAY_BUFFER, 0);

        return id;
    }

    public void ReRegister(List<Vertex> vertices, List<Integer> indices)
    {
        this.vertices = vertices;
        this.indices = indices;
    }

    public void Render(Camera camera)
    {
        if (VAO == 0)
            return;

        shader.Bind();
        shader.SetUniform("model", transform.GetMatrix());
        shader.SetUniform("view", camera.view);
        shader.SetUniform("projection", camera.projection);

        glBindTexture(GL_TEXTURE0, texture.id);
        glActiveTexture(GL_TEXTURE0);

        glBindVertexArray(VAO);

        if (indices != null && !indices.isEmpty())
            glDrawElements(GL_TRIANGLES, indices.size(), GL_UNSIGNED_INT, 0);
        else
            glDrawArrays(GL_TRIANGLES, 0, vertices.size());

        glBindVertexArray(0);
        shader.UnBind();
    }

    public void CleanUp()
    {
        shader.CleanUp();

        if (VAO != 0)
            glDeleteVertexArrays(VAO);


        if (PBO != 0)
            glDeleteBuffers(PBO);

        if (CBO != 0)
            glDeleteBuffers(CBO);

        if (TBO != 0)
            glDeleteBuffers(TBO);


        if (EBO != 0)
            glDeleteBuffers(EBO);
    }

    public static Mesh Register(String name, List<Vertex> vertices, List<Integer> indices, String shader)
    {
        Mesh out = new Mesh();

        out.name = name;
        out.vertices = vertices;
        out.indices = indices;
        out.shader = ShaderManager.Get(shader);

        return out;
    }
}