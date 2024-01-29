package com.thatsoulyguy.minelander.math;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.joml.Vector3i;

public class TransformI 
{
    public Vector3i position = new Vector3i();
    public Vector3i rotation = new Vector3i();
    public Vector3i scale = new Vector3i();

    public Vector3f forward = new Vector3f();
    public Vector3f right = new Vector3f();
    public Vector3f up = new Vector3f();

    public static final Vector3i worldUp = new Vector3i(0, 1, 0);

    public Matrix4f GetMatrix()
    {
        Matrix4f transformationMatrix = new Matrix4f();
        transformationMatrix.identity();

        transformationMatrix.translate(position.x, (float) position.y, (float) position.z);

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

    public Transform ToTransform()
    {
        return Transform.Register(new Vector3f(position), new Vector3f(rotation), new Vector3f(scale));
    }

    public static TransformI Register(Vector3i position)
    {
        return Register(position, new Vector3i(0, 0, 0));
    }

    public static TransformI Register(Vector3i position, Vector3i rotation)
    {
        return Register(position, rotation, new Vector3i(1, 1, 1));
    }

    public static TransformI Register(Vector3i position, Vector3i rotation, Vector3i scale)
    {
        TransformI out = new TransformI();

        out.position = position;
        out.rotation = rotation;
        out.scale = scale;

        return out;
    }
}