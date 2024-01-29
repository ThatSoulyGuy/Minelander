package com.thatsoulyguy.minelander.render;

import com.thatsoulyguy.minelander.core.Window;
import com.thatsoulyguy.minelander.math.Transform;
import org.joml.Matrix4f;
import org.joml.Vector3d;
import org.joml.Vector3f;

public class Camera
{
    public Transform transform = Transform.Register(new Vector3f(0.0F, 0.0F, 0.0F));

    public Matrix4f projection = new Matrix4f().identity();
    public Matrix4f view = new Matrix4f().identity();

    public float fov = 45.0F, nearPlane = 0.01F, farPlane = 200.0F;

    public void Initialize(Vector3f position, float fov, float nearPlane, float farPlane)
    {
        transform.position = position;
        this.fov = fov;
        this.nearPlane = nearPlane;
        this.farPlane = farPlane;

        projection.identity().perspective((float) Math.toRadians(this.fov), ((float) Window.GetSize().x / (float)Window.GetSize().y), this.nearPlane, this.farPlane);
    }

    public void Update()
    {
        transform.Update();

        projection.identity().perspective((float) Math.toRadians(this.fov), ((float) Window.GetSize().x / (float)Window.GetSize().y), this.nearPlane, this.farPlane);
        view.identity().lookAt(transform.position, new Vector3f(transform.position).add(transform.forward), Transform.worldUp);
    }
}