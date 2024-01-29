package com.thatsoulyguy.minelander.core;

import org.joml.Vector2f;
import org.lwjgl.glfw.*;

import static org.lwjgl.glfw.GLFW.*;

public class InputManager
{
    private static GLFWKeyCallback keyCallback;
    private static GLFWMouseButtonCallback mouseButtonCallback;
    private static GLFWScrollCallback scrollCallback;
    private static GLFWCursorPosCallback cursorPositionCallback;

    private static final KeyState[] keys = new KeyState[GLFW_KEY_LAST];
    private static final KeyState[] mouseButtons = new KeyState[GLFW_MOUSE_BUTTON_LAST];

    private static final Vector2f mousePosition = new Vector2f();
    private static final Vector2f scroll = new Vector2f();

    public static void Initialize()
    {
        keyCallback = glfwSetKeyCallback(Window.GetHandle(), (window, key, scancode, action, mods) ->
        {
            if (action == GLFW_PRESS)
            {
                if (keys[key] != KeyState.HELD)
                    keys[key] = KeyState.PRESSED;
            }
            else if (action == GLFW_RELEASE)
                keys[key] = KeyState.RELEASED;
        });

        mouseButtonCallback = glfwSetMouseButtonCallback(Window.GetHandle(), (window, button, action, mods) ->
        {
            if (action == GLFW_PRESS)
            {
                if (mouseButtons[button] != KeyState.HELD)
                    mouseButtons[button] = KeyState.PRESSED;
            }
            else if (action == GLFW_RELEASE)
                mouseButtons[button] = KeyState.RELEASED;
        });

        scrollCallback = glfwSetScrollCallback(Window.GetHandle(), (window, xOffset, yOffset) ->
        {
            scroll.x = (float)xOffset;
            scroll.y = (float)yOffset;
        });

        cursorPositionCallback = glfwSetCursorPosCallback(Window.GetHandle(), (window, xpos, ypos) ->
        {
            mousePosition.x = (float)xpos;
            mousePosition.y = (float)ypos;
        });
    }

    public static void Update()
    {
        for (int k = 0; k < keys.length; k++)
        {
            if (keys[k] == KeyState.PRESSED)
                keys[k] = KeyState.HELD;
        }

        for (int b = 0; b < mouseButtons.length; b++)
        {
            if (mouseButtons[b] == KeyState.PRESSED)
                mouseButtons[b] = KeyState.HELD;
        }
    }

    public static void SetCursorMode(CursorMode mode)
    {
        GLFW.glfwSetInputMode(Window.GetHandle(), GLFW.GLFW_CURSOR, mode.GetRaw());
    }

    public static boolean GetKey(KeyCode key, KeyState state)
    {
        return keys[key.GetRaw()] == state;
    }

    public static boolean GetMouseButton(MouseCode button, KeyState state)
    {
        return mouseButtons[button.GetRaw()] == state;
    }

    public static Vector2f GetMousePosition()
    {
        return new Vector2f(mousePosition);
    }

    public static Vector2f GetScroll()
    {
        return new Vector2f(scroll);
    }

    public static void CleanUp()
    {
        if (keyCallback != null)
            keyCallback.free();

        if (mouseButtonCallback != null)
            mouseButtonCallback.free();

        if (scrollCallback != null)
            scrollCallback.free();

        if (cursorPositionCallback != null)
            cursorPositionCallback.free();
    }
}