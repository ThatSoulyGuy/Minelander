package com.thatsoulyguy.minelander.entity;

import com.thatsoulyguy.minelander.core.CursorMode;
import com.thatsoulyguy.minelander.core.InputManager;
import com.thatsoulyguy.minelander.core.KeyCode;
import com.thatsoulyguy.minelander.core.KeyState;
import com.thatsoulyguy.minelander.math.Transform;
import com.thatsoulyguy.minelander.render.Camera;
import org.joml.Vector2f;
import org.joml.Vector3f;

public class Player
{
    public Transform transform = Transform.Register(new Vector3f(0.0F, 0.0F, 0.0F));

    public Camera camera = new Camera();
    
    private final Vector2f newMouse = new Vector2f();
    private final Vector2f oldMouse = new Vector2f();

    public void Initialize(Vector3f position)
    {
        InputManager.SetCursorMode(CursorMode.LOCKED);

        transform.position = position;
        transform.Update();

        camera.Initialize(position, 45.0F, 0.01F, 200.0F);
    }

    public void Update()
    {
        UpdateMouseLook();
        UpdateMovement();

        transform.Update();
        camera.transform = transform;
        camera.Update();
    }

    private void UpdateMouseLook()
    {
        newMouse.x = InputManager.GetMousePosition().x;
        newMouse.y = InputManager.GetMousePosition().y;

        float dx = (newMouse.x - oldMouse.x);
        float dy = (newMouse.y - oldMouse.y);

        float mouseSensitivity = 0.08f;

        transform.rotation.y -= dx * mouseSensitivity;
        transform.rotation.x -= dy * mouseSensitivity;

        if(transform.rotation.x > 90)
            transform.rotation.x = 89.99f;

        if(transform.rotation.x < -90)
            transform.rotation.x = -89.99f;

        oldMouse.x = newMouse.x;
        oldMouse.y = newMouse.y;
    }

    private void UpdateMovement()
    {
        float movementSpeed = 0.08f;

        if (InputManager.GetKey(KeyCode.W, KeyState.PRESSED))
        {
            transform.position.x += transform.forward.x * movementSpeed;
            transform.position.y += transform.forward.y * movementSpeed;
            transform.position.z += transform.forward.z * movementSpeed;
        }

        if (InputManager.GetKey(KeyCode.A, KeyState.PRESSED))
        {
            transform.position.x += transform.right.x * movementSpeed;
            transform.position.y += transform.right.y * movementSpeed;
            transform.position.z += transform.right.z * movementSpeed;
        }

        if (InputManager.GetKey(KeyCode.S, KeyState.PRESSED))
        {
            transform.position.x -= transform.forward.x * movementSpeed;
            transform.position.y -= transform.forward.y * movementSpeed;
            transform.position.z -= transform.forward.z * movementSpeed;
        }

        if (InputManager.GetKey(KeyCode.D, KeyState.PRESSED))
        {
            transform.position.x -= transform.right.x * movementSpeed;
            transform.position.y -= transform.right.y * movementSpeed;
            transform.position.z -= transform.right.z * movementSpeed;
        }
    }
}