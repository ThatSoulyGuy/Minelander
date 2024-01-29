package com.thatsoulyguy.minelander.math;

import org.joml.Matrix4f;
import org.joml.Vector3f;

public class Transform
{
    public Vector3f position = new Vector3f();
    public Vector3f rotation = new Vector3f();
    public Vector3f scale = new Vector3f();

    public Vector3f forward = new Vector3f();
    public Vector3f right = new Vector3f();
    public Vector3f up = new Vector3f();

    public static final Vector3f worldUp = new Vector3f(0.0F, 1.0F, 0.0F);

    public Matrix4f GetMatrix()
    {
        Matrix4f transformationMatrix = new Matrix4f();
        transformationMatrix.identity();

        transformationMatrix.translate(position.x, (float) position.y, (float) position.z);

        if(rotation.x <= 0.0F)
            rotation.x = 0.0001F;

        if(rotation.y <= 0.0F)
            rotation.y = 0.0001F;

        if(rotation.z <= 0.0F)
            rotation.z = 0.0001F;

        transformationMatrix.rotateX((float) Math.toRadians(rotation.x));
        transformationMatrix.rotateY((float) Math.toRadians(rotation.y));
        transformationMatrix.rotateZ((float) Math.toRadians(rotation.z));

        transformationMatrix.scale((float) scale.x, (float) scale.y, (float) scale.z);

        return transformationMatrix;
    }

    public void Update()
    {
        forward.x = (float) (-Math.sin(Math.toRadians(rotation.y)) * Math.cos(Math.toRadians(rotation.x)));
        forward.y = (float) Math.sin(Math.toRadians(rotation.x));
        forward.z = (float) (-Math.cos(Math.toRadians(rotation.y)) * Math.cos(Math.toRadians(rotation.x)));
        forward.normalize();

        right = new Vector3f(worldUp).cross(forward);
        right.normalize();

        up = new Vector3f(forward).cross(right);
        up.normalize();
    }

    public static Transform Register(Vector3f position)
    {
        return Register(position, new Vector3f(0.0F, 0.0F, 0.0F));
    }

    public static Transform Register(Vector3f position, Vector3f rotation)
    {
        return Register(position, rotation, new Vector3f(1.0F, 1.0F, 1.0F));
    }

    public static Transform Register(Vector3f position, Vector3f rotation, Vector3f scale)
    {
        Transform out = new Transform();

        out.position = position;
        out.rotation = rotation;
        out.scale = scale;

        return out;
    }
}