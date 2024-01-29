package com.thatsoulyguy.minelander.core;

import org.joml.Vector2i;
import org.joml.Vector3f;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWFramebufferSizeCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.system.MemoryStack;

import java.nio.IntBuffer;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryStack.*;
import static org.lwjgl.system.MemoryUtil.*;

public class Window
{
    private static long raw;
    public static Vector3f color;
    private static WindowHint[] hints = null;

    public static void Initialize()
    {
        GLFWErrorCallback.createPrint(System.err).set();

        if (!glfwInit())
            Logger.ThrowError("null", "Failed to initialize GLFW!", new IllegalStateException(), true, -1);
    }

    public static void RegisterHints(WindowHint... hints)
    {
        Window.hints = hints;
    }

    public static void Generate(String title, Vector2i size, Vector3f color)
    {
        Window.color = color;

        glfwDefaultWindowHints();

        if(hints != null)
        {
            for (WindowHint hint : hints)
                hint.Apply();
        }

        raw = glfwCreateWindow(size.x, size.y, title, NULL, NULL);

        if(raw == NULL)
            Logger.ThrowError("NULL", "Failed to create GLFW window!", new RuntimeException(), true, -1);

        glfwSetFramebufferSizeCallback(raw, new GLFWFramebufferSizeCallback()
        {
            @Override
            public void invoke(long window, int width, int height)
            {
                glViewport(0, 0, width, height);
            }
        });

        glfwMakeContextCurrent(raw);

        glfwSwapInterval(1);

        Center();

        GL.createCapabilities();

        glEnable(GL_DEPTH_TEST);

        glEnable(GL_CULL_FACE);
        glCullFace(GL_BACK);
    }

    public static void Center()
    {
        try (MemoryStack stack = stackPush())
        {
            IntBuffer pWidth = stack.mallocInt(1);
            IntBuffer pHeight = stack.mallocInt(1);

            glfwGetWindowSize(raw, pWidth, pHeight);

            GLFWVidMode video = glfwGetVideoMode(glfwGetPrimaryMonitor());

            assert video != null;
            glfwSetWindowPos(raw, (video.width() - pWidth.get(0)) / 2, (video.height() - pHeight.get(0)) / 2);
        }
    }

    public static void PreRender()
    {
        glClearColor(color.x, color.y, color.z, 1.0f);
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
    }

    public static void PostRender()
    {
        glfwPollEvents();
        glfwSwapBuffers(raw);
    }

    public static boolean ShouldClose()
    {
        return glfwWindowShouldClose(raw);
    }

    public static void CleanUp()
    {
        glfwDestroyWindow(raw);
        glfwTerminate();
    }

    public static long GetHandle()
    {
        return raw;
    }

    public static Vector2i GetPosition()
    {
        try (MemoryStack stack = stackPush())
        {
            IntBuffer vertical = stack.mallocInt(1);
            IntBuffer horizontal = stack.mallocInt(1);

            glfwGetWindowPos(raw, horizontal, vertical);

            return new Vector2i(horizontal.get(), vertical.get());
        }
    }

    public static void SetPosition(Vector2i position)
    {
        glfwSetWindowPos(raw, position.x, position.y);
    }

    public static Vector2i GetSize()
    {
        try (MemoryStack stack = stackPush())
        {
            IntBuffer width = stack.mallocInt(1);
            IntBuffer height = stack.mallocInt(1);

            glfwGetWindowSize(raw, width, height);

            return new Vector2i(width.get(), height.get());
        }
    }

    public static void SetSize(Vector2i size)
    {
        glfwSetWindowSize(raw, size.x, size.y);
    }
}