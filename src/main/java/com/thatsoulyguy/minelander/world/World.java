package com.thatsoulyguy.minelander.world;

import org.joml.Vector3f;
import org.joml.Vector3i;

import java.util.HashSet;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class World
{
    private static final ConcurrentMap<Vector3i, Chunk> loadedChunks = new ConcurrentHashMap<>();

    private static final ExecutorService executor = Executors.newSingleThreadExecutor();
    private static final int LOAD_RADIUS = 6;

    public static void Update(Vector3f playerPosition)
    {
        executor.submit(() ->
        {
            Vector3i playerChunkPosition = CoordinateHelper.WorldCoordinatesToChunkCoordinates(playerPosition);

            for (int x = -LOAD_RADIUS; x <= LOAD_RADIUS; x++)
            {
                for (int z = -LOAD_RADIUS; z <= LOAD_RADIUS; z++)
                {
                    Vector3i chunkPosition = new Vector3i(playerChunkPosition.x + x, 0, playerChunkPosition.z + z);

                    if (!loadedChunks.containsKey(chunkPosition))
                        GenerateChunk(chunkPosition, false);
                }
            }

            HashSet<Vector3i> chunkSet = new HashSet<>(loadedChunks.keySet());

            for (Vector3i chunkCoordinate : chunkSet)
            {
                if (Math.abs(chunkCoordinate.x - playerChunkPosition.x) > LOAD_RADIUS || Math.abs(chunkCoordinate.y - playerChunkPosition.y) > (LOAD_RADIUS * 2) || Math.abs(chunkCoordinate.z - playerChunkPosition.z) > LOAD_RADIUS)
                {
                    Chunk chunk = loadedChunks.remove(chunkCoordinate);

                    if (chunk != null)
                        chunk.CleanUp();
                }
            }
        });
    }

    public static void GenerateChunk(Vector3i position, boolean generateNothing)
    {
        Chunk newChunk = new Chunk();
        newChunk.Initialize(new Vector3i(position).mul(Chunk.CHUNK_SIZE), generateNothing);
        loadedChunks.put(position, newChunk);
    }

    public static Chunk GetChunk(Vector3i position)
    {
        return loadedChunks.get(position);
    }

    public static void CleanUp()
    {
        executor.shutdown();
    }
}