package com.thatsoulyguy.minelander.core;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Logger
{
    private static FileWriter writer;
    private static Scanner scanner;

    public static void Initialize()
    {
        if (!Files.exists(Path.of("logs")))
            new File("logs/").mkdirs();

        File file = new File("logs/log_" + GetTime("HH_ss_mm__MM_dd_yyyy") + ".log");
        scanner = new Scanner(System.in);

        try
        {
            file.createNewFile();
            writer = new FileWriter("logs/log_" + GetTime("HH_ss_mm__MM_dd_yyyy") + ".log");
        }
        catch (IOException exception)
        {
            exception.printStackTrace();
        }
    }

    public static void WriteConsole(String message, LogLevel level)
    {
        String name = StackWalker.getInstance(StackWalker.Option.RETAIN_CLASS_REFERENCE).walk(s -> s.skip(1).findFirst()).map(frame -> frame.getDeclaringClass().getSimpleName()).orElse("UnknownClass");
        String time = GetTime("HH:ss:mm");

        try
        {
            String currentMessage = "";
            switch (level)
            {
                case INFO:
                {
                    currentMessage = Format.ANSI_Format(Format.FormatText("&2[%s] [Thread/INFO] [%s]: %s&r", time, name, message));
                    System.out.println(currentMessage);
                    writer.write(Format.ANSI_DeFormat(currentMessage));
                    break;
                }

                case DEBUG:
                {
                    currentMessage = Format.ANSI_Format(Format.FormatText("&1[%s] [Thread/DEBUG] [%s]: %s&r", time, name, message));
                    System.out.println(currentMessage);
                    writer.write(Format.ANSI_DeFormat(currentMessage));
                    break;
                }

                case WARNING:
                {
                    currentMessage = Format.ANSI_Format(Format.FormatText("&6[%s] [Thread/WARNING] [%s]: %s&r", time, name, message));
                    System.out.println(currentMessage);
                    writer.write(Format.ANSI_DeFormat(currentMessage));
                    break;
                }

                case ERROR:
                {
                    currentMessage = Format.ANSI_Format(Format.FormatText("&4[%s] [Thread/ERROR] [%s]: %s&r", time, name, message));
                    System.out.println(currentMessage);
                    writer.write(Format.ANSI_DeFormat(currentMessage));
                    break;
                }

                case FATAL_ERROR:
                {
                    currentMessage = Format.ANSI_Format(Format.FormatText("&4[%s] [Thread/FATAL ERROR] [%s]: %s&r", time, name, message));
                    System.out.println(currentMessage);
                    writer.write(Format.ANSI_DeFormat(currentMessage));
                    break;
                }
            }
        }
        catch (IOException exception)
        {
            exception.printStackTrace();
        }
    }

    public static String ReadConsole()
    {
        return scanner.next();
    }

    public static void ThrowError(String unexpected, String message, RuntimeException exception)
    {
        String name = StackWalker.getInstance(StackWalker.Option.RETAIN_CLASS_REFERENCE).walk(s -> s.skip(1).findFirst()).map(frame -> frame.getDeclaringClass().getSimpleName() + "::" + frame.getMethodName() + "::" + frame.getLineNumber()).orElse("UnknownClass::UnknownMethod::-1");

        WriteConsole(Format.FormatText("Unexpected: '%s' at: '%s', %s", unexpected, name, message), LogLevel.ERROR);

        throw exception;
    }

    public static void ThrowError(String unexpected, String message, RuntimeException exception, boolean fatal, int code)
    {
        String name = StackWalker.getInstance(StackWalker.Option.RETAIN_CLASS_REFERENCE).walk(s -> s.skip(1).findFirst()).map(frame -> frame.getDeclaringClass().getSimpleName() + "::" + frame.getMethodName() + "::" + frame.getLineNumber()).orElse("UnknownClass::UnknownMethod::-1");

        if(fatal)
            WriteConsole(Format.FormatText("Unexpected: '%s' at: '%s', %s\n\n----CODE----\n%d\n", unexpected, name, message, code), LogLevel.FATAL_ERROR);
        else
            WriteConsole(Format.FormatText("Unexpected: '%s' at: '%s', %s", unexpected, name, message), LogLevel.ERROR);

        throw exception;
    }

    public static void CleanUp()
    {
        try
        {
            writer.close();
            scanner.close();
        }
        catch (IOException exception)
        {
            exception.printStackTrace();
        }
    }

    private static String GetTime(String format)
    {
        return DateTimeFormatter.ofPattern(format).format(LocalDateTime.now());
    }
}