package com.thatsoulyguy.minelander.world;

import com.thatsoulyguy.minelander.block.Block;
import com.thatsoulyguy.minelander.block.BlockManager;
import com.thatsoulyguy.minelander.math.TransformI;
import com.thatsoulyguy.minelander.render.Mesh;
import com.thatsoulyguy.minelander.render.MeshManager;
import com.thatsoulyguy.minelander.render.TextureManager;
import com.thatsoulyguy.minelander.render.Vertex;
import com.thatsoulyguy.minelander.thread.MainThreadExecutor;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector3i;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Chunk
{
    public static final int CHUNK_SIZE = 16;

    public TransformI transform = TransformI.Register(new Vector3i(0, 0, 0));

    private final long[][][] blocks = new long[CHUNK_SIZE][CHUNK_SIZE][CHUNK_SIZE];

    private Mesh mesh;
    private final List<Vertex> vertices = Collections.synchronizedList(new ArrayList<>());
    private final List<Integer> indices = Collections.synchronizedList(new ArrayList<>());

    public void Initialize(Vector3i position, boolean generateNothing)
    {
        transform.position = position;

        mesh = Mesh.Register("chunk_" + position.x + "_" + position.y + "_" + position.z, null, null, "default");

        MainThreadExecutor.Queue(() ->
            mesh.SetTexture("terrain"));

        mesh.transform = transform.ToTransform();

        if(!generateNothing)
        {
            for(int x = 0; x < CHUNK_SIZE; x++)
            {
                for(int y = 0; y < CHUNK_SIZE; y++)
                {
                    for(int z = 0; z < CHUNK_SIZE; z++)
                    {
                        if(y >= 12)
                            blocks[x][y][z] = BlockType.DIRT.GetRaw();
                        else
                            blocks[x][y][z] = BlockType.STONE.GetRaw();

                        blocks[x][15][z] = BlockType.GRASS_BLOCK.GetRaw();
                    }
                }
            }
        }

        Rebuild();
    }

    public void Rebuild()
    {
        vertices.clear();
        indices.clear();

        for (int x = 0; x < CHUNK_SIZE; x++)
        {
            for (int y = 0; y < CHUNK_SIZE; y++)
            {
                for (int z = 0; z < CHUNK_SIZE; z++)
                {
                    if (blocks[x][y][z] == BlockType.AIR.GetRaw())
                        continue;

                    Block block = BlockManager.Get(BlockType.FromRaw((int) blocks[x][y][z]));

                    if (y == CHUNK_SIZE - 1 || blocks[x][y + 1][z] == BlockType.AIR.GetRaw())
                        GenerateFace(new Vector3i(x, y, z), ChunkFaceSide.TOP, TextureAtlasManager.GetTextureCoordinates(block.textureCoordinates[0]));

                    if (y == 0 || blocks[x][y - 1][z] == BlockType.AIR.GetRaw())
                        GenerateFace(new Vector3i(x, y, z), ChunkFaceSide.BOTTOM, TextureAtlasManager.GetTextureCoordinates(block.textureCoordinates[1]));

                    if (z == CHUNK_SIZE - 1 || blocks[x][y][z + 1] == BlockType.AIR.GetRaw())
                        GenerateFace(new Vector3i(x, y, z), ChunkFaceSide.FRONT, TextureAtlasManager.GetTextureCoordinates(block.textureCoordinates[2], 180));

                    if (z == 0 || blocks[x][y][z - 1] == BlockType.AIR.GetRaw())
                        GenerateFace(new Vector3i(x, y, z), ChunkFaceSide.BACK, TextureAtlasManager.GetTextureCoordinates(block.textureCoordinates[3], 180));

                    if (x == CHUNK_SIZE - 1 || blocks[x + 1][y][z] == BlockType.AIR.GetRaw())
                        GenerateFace(new Vector3i(x, y, z), ChunkFaceSide.RIGHT, TextureAtlasManager.GetTextureCoordinates(block.textureCoordinates[4], 180));

                    if (x == 0 || blocks[x - 1][y][z] == BlockType.AIR.GetRaw())
                        GenerateFace(new Vector3i(x, y, z), ChunkFaceSide.LEFT, TextureAtlasManager.GetTextureCoordinates(block.textureCoordinates[5], 180));
                }
            }
        }

        mesh.ReRegister(vertices, indices);

        MainThreadExecutor.Queue(() ->
        {
            mesh.Generate();
            MeshManager.ReRegister(mesh);
        });
    }

    public boolean IsPositionInsideChunk(Vector3i position)
    {
        Vector3i localPosition = new Vector3i(position.x - transform.position.x, position.y - transform.position.y, position.z - transform.position.z);

        return localPosition.x >= 0 && localPosition.x < CHUNK_SIZE && localPosition.y >= 0 && localPosition.y < CHUNK_SIZE && localPosition.z >= 0 && localPosition.z < CHUNK_SIZE;
    }

    public void SetBlock(Vector3i block, BlockType type)
    {
        if(!IsPositionInsideChunk(block))
            return;

        blocks[block.x][block.y][block.z] = type.GetRaw();
        Rebuild();
    }

    public void GenerateFace(Vector3i position, ChunkFaceSide faceDirection, Vector2f[] uvs)
    {
        int baseIndex = vertices.size();

        switch (faceDirection)
        {
            case TOP:
                vertices.add(Vertex.Register(new Vector3f(0.0f + position.x, 1.0f + position.y, 1.0f + position.z), uvs[0]));
                vertices.add(Vertex.Register(new Vector3f(1.0f + position.x, 1.0f + position.y, 1.0f + position.z), uvs[1]));
                vertices.add(Vertex.Register(new Vector3f(0.0f + position.x, 1.0f + position.y, 0.0f + position.z), uvs[3]));
                vertices.add(Vertex.Register(new Vector3f(1.0f + position.x, 1.0f + position.y, 0.0f + position.z), uvs[2]));
                break;

            case BOTTOM:
                vertices.add(Vertex.Register(new Vector3f(0.0f + position.x, 0.0f + position.y, 0.0f + position.z), uvs[0]));
                vertices.add(Vertex.Register(new Vector3f(1.0f + position.x, 0.0f + position.y, 0.0f + position.z), uvs[1]));
                vertices.add(Vertex.Register(new Vector3f(0.0f + position.x, 0.0f + position.y, 1.0f + position.z), uvs[3]));
                vertices.add(Vertex.Register(new Vector3f(1.0f + position.x, 0.0f + position.y, 1.0f + position.z), uvs[2]));
                break;

            case FRONT:
                vertices.add(Vertex.Register(new Vector3f(0.0f + position.x, 0.0f + position.y, 1.0f + position.z), uvs[0]));
                vertices.add(Vertex.Register(new Vector3f(1.0f + position.x, 0.0f + position.y, 1.0f + position.z), uvs[1]));
                vertices.add(Vertex.Register(new Vector3f(0.0f + position.x, 1.0f + position.y, 1.0f + position.z), uvs[3]));
                vertices.add(Vertex.Register(new Vector3f(1.0f + position.x, 1.0f + position.y, 1.0f + position.z), uvs[2]));
                break;

            case BACK:
                vertices.add(Vertex.Register(new Vector3f(1.0f + position.x, 0.0f + position.y, 0.0f + position.z), uvs[0]));
                vertices.add(Vertex.Register(new Vector3f(0.0f + position.x, 0.0f + position.y, 0.0f + position.z), uvs[1]));
                vertices.add(Vertex.Register(new Vector3f(1.0f + position.x, 1.0f + position.y, 0.0f + position.z), uvs[3]));
                vertices.add(Vertex.Register(new Vector3f(0.0f + position.x, 1.0f + position.y, 0.0f + position.z), uvs[2]));
                break;

            case RIGHT:
                vertices.add(Vertex.Register(new Vector3f(1.0f + position.x, 0.0f + position.y, 1.0f + position.z), uvs[0]));
                vertices.add(Vertex.Register(new Vector3f(1.0f + position.x, 0.0f + position.y, 0.0f + position.z), uvs[1]));
                vertices.add(Vertex.Register(new Vector3f(1.0f + position.x, 1.0f + position.y, 1.0f + position.z), uvs[3]));
                vertices.add(Vertex.Register(new Vector3f(1.0f + position.x, 1.0f + position.y, 0.0f + position.z), uvs[2]));
                break;

            case LEFT:
                vertices.add(Vertex.Register(new Vector3f(0.0f + position.x, 0.0f + position.y, 0.0f + position.z), uvs[0]));
                vertices.add(Vertex.Register(new Vector3f(0.0f + position.x, 0.0f + position.y, 1.0f + position.z), uvs[1]));
                vertices.add(Vertex.Register(new Vector3f(0.0f + position.x, 1.0f + position.y, 0.0f + position.z), uvs[3]));
                vertices.add(Vertex.Register(new Vector3f(0.0f + position.x, 1.0f + position.y, 1.0f + position.z), uvs[2]));
                break;
        }

        indices.add(baseIndex);
        indices.add(baseIndex + 1);
        indices.add(baseIndex + 2);
        indices.add(baseIndex + 2);
        indices.add(baseIndex + 1);
        indices.add(baseIndex + 3);
    }

    public void CleanUp()
    {
        MainThreadExecutor.Queue(() ->
            MeshManager.UnRegister(mesh.name));
    }
}