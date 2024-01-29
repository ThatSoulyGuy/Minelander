package com.thatsoulyguy.minelander;

import com.thatsoulyguy.minelander.block.BlockManager;
import com.thatsoulyguy.minelander.block.blocks.DirtBlock;
import com.thatsoulyguy.minelander.block.blocks.GrassBlock;
import com.thatsoulyguy.minelander.block.blocks.StoneBlock;
import com.thatsoulyguy.minelander.block.blocks.TestBlock;
import com.thatsoulyguy.minelander.core.*;
import com.thatsoulyguy.minelander.entity.Player;
import com.thatsoulyguy.minelander.render.*;
import com.thatsoulyguy.minelander.thread.MainThreadExecutor;
import com.thatsoulyguy.minelander.world.Chunk;
import com.thatsoulyguy.minelander.world.World;
import org.joml.Vector2i;
import org.joml.Vector3f;
import org.joml.Vector3i;

public class Minelander implements Runnable
{
    public Player player = new Player();

    public Thread mainThread = null;

    public static Minelander Instance = null;

    public void PreInitialize()
    {
        mainThread = new Thread(this, "mainThread");
        mainThread.start();
    }

    public void Initialize()
    {
        Instance = this;

        Logger.Initialize();

        Window.Initialize();

        ShaderManager.Register(Shader.Register("shaders/default", "default"));
        TextureManager.Register(Texture.Register("textures/block.png", "test"));
        TextureManager.Register(Texture.Register("textures/terrain.png", "terrain"));

        BlockManager.Register(new TestBlock());
        BlockManager.Register(new GrassBlock());
        BlockManager.Register(new DirtBlock());
        BlockManager.Register(new StoneBlock());

        Window.Generate(Settings.defaultDomainCaps + "* 0.1.6", new Vector2i(750, 450).mul(2), new Vector3f(0.0f, 0.45f, 0.75f));

        InputManager.Initialize();

        player.Initialize(new Vector3f(0.0F, 0.0F, 2.0F));

        Logger.WriteConsole("Hello, Minelander!", LogLevel.INFO);
    }

    public void Update()
    {
        Window.PreRender();

        player.Update();

        World.Update(player.transform.position);

        MainThreadExecutor.Update();
        MeshManager.Render(player.camera);

        Window.PostRender();
    }

    public void CleanUp()
    {
        World.CleanUp();
        MeshManager.CleanUp();
        InputManager.CleanUp();
        Window.CleanUp();
        Logger.CleanUp();
    }

    @Override
    public void run()
    {
        Initialize();

        while (!Window.ShouldClose())
            Update();

        CleanUp();
    }

    public static void main(String[] args)
    {
        new Minelander().PreInitialize();
    }
}