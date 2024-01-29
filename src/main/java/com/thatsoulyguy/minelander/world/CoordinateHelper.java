package com.thatsoulyguy.minelander.world;

import org.joml.Vector3f;
import org.joml.Vector3i;

public class CoordinateHelper 
{
    public static Vector3i WorldCoordinatesToChunkCoordinates(Vector3f worldPosition)
    {
        return new Vector3i(Math.floorDiv((int) worldPosition.x, Chunk.CHUNK_SIZE), Math.floorDiv((int) worldPosition.y, Chunk.CHUNK_SIZE), Math.floorDiv((int) worldPosition.z, Chunk.CHUNK_SIZE));
    }
}