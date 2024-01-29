package com.thatsoulyguy.minelander.render;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class MeshManager
{
    private static final ConcurrentMap<String, Mesh> registeredMeshes = new ConcurrentHashMap<>();

    public static void Register(Mesh mesh)
    {
        registeredMeshes.put(mesh.name, mesh);
    }

    public static void ReRegister(Mesh mesh)
    {
        UnRegister(mesh.name);
        Register(mesh);
    }

    public static void UnRegister(String mesh)
    {
        if(!registeredMeshes.containsKey(mesh))
            return;

        registeredMeshes.get(mesh).CleanUp();
        registeredMeshes.remove(mesh);
    }

    public static Mesh Get(String name)
    {
        return registeredMeshes.get(name);
    }

    public static void Render(Camera camera)
    {
        for(Mesh mesh : registeredMeshes.values())
            mesh.Render(camera);
    }

    public static void CleanUp()
    {
        for(Mesh mesh : registeredMeshes.values())
            mesh.CleanUp();

        registeredMeshes.clear();
    }
}