package com.thatsoulyguy.minelander.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

public class FileHelper
{
    public static String ReadFile(String path)
    {
        StringBuilder result = new StringBuilder();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(Objects.requireNonNull(FileHelper.class.getResourceAsStream(path)))))
        {
            String line = "";

            while ((line = reader.readLine()) != null)
                result.append(line).append("\n");

        }
        catch (IOException e)
        {
            System.err.println("Couldn't find the file at " + path);
        }

        return result.toString();
    }
}