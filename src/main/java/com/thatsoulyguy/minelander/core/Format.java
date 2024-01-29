package com.thatsoulyguy.minelander.core;

import java.util.Formatter;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Format
{
    public static String ANSI_Format(String input)
    {
        Pattern pattern = Pattern.compile("&[0123456fr]");
        Matcher matcher = pattern.matcher(input);

        StringBuilder buffer = new StringBuilder();

        while (matcher.find())
        {
            String replacement = switch (matcher.group())
            {
                case "&0" -> "\u001B[30m";
                case "&1" -> "\u001B[34m";
                case "&2" -> "\u001B[32m";
                case "&3" -> "\u001B[36m";
                case "&4" -> "\u001B[31m";
                case "&5" -> "\u001B[35m";
                case "&6" -> "\u001B[33m";
                case "&f" -> "\u001B[37m";
                case "&r" -> "\u001B[0m";

                default -> "";
            };

            matcher.appendReplacement(buffer, replacement);
        }

        matcher.appendTail(buffer);

        return buffer + "\u001B[0m";
    }

    public static String ANSI_DeFormat(String input)
    {
        String ansiEscapeCodeRegex = "\\x1B\\[[;\\d]*m";
        return input.replaceAll(ansiEscapeCodeRegex, "");
    }

    public static String FormatText(String input, Object... args)
    {
        return new Formatter().format(input, args).toString();
    }
}