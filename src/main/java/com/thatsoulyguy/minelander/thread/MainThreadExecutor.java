package com.thatsoulyguy.minelander.thread;

import java.util.concurrent.ConcurrentLinkedQueue;

public class MainThreadExecutor
{
    private static final ConcurrentLinkedQueue<Runnable> taskQueue = new ConcurrentLinkedQueue<>();

    public static void Queue(Runnable task)
    {
        taskQueue.add(task);
    }

    public static void Update()
    {
        while (!taskQueue.isEmpty())
        {
            Runnable task = taskQueue.poll();

            if (task != null)
                task.run();
        }
    }
}