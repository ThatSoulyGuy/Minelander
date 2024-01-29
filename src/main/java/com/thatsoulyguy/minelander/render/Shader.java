package com.thatsoulyguy.minelander.render;

import com.thatsoulyguy.minelander.core.Settings;
import com.thatsoulyguy.minelander.util.FileHelper;
import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.lwjgl.system.MemoryUtil;

import java.nio.FloatBuffer;
import java.util.Objects;

import static org.lwjgl.opengl.GL20.*;

public class Shader
{
    public String name;
    private String vertexPath;
    private String fragmentPath;

    private int program = 0;

    public void Generate()
    {
        int vertexShader = GenerateShader(GL_VERTEX_SHADER, "VERTEX", vertexPath);
        int fragmentShader = GenerateShader(GL_FRAGMENT_SHADER, "FRAGMENT", fragmentPath);

        program = glCreateProgram();

        glAttachShader(program, vertexShader);
        glAttachShader(program, fragmentShader);
        glLinkProgram(program);

        CheckLinkerErrors();

        glDeleteShader(vertexShader);
        glDeleteShader(fragmentShader);
    }

    public void Bind()
    {
        glUseProgram(program);
    }

    public void UnBind()
    {
        glUseProgram(0);
    }

    public Shader Copy()
    {
        Shader out = new Shader();

        out.name = name;
        out.vertexPath = vertexPath;
        out.fragmentPath = fragmentPath;

        return out;
    }

    private int GetUniformLocation(String name)
    {
        return glGetUniformLocation(program, name);
    }

    public void SetUniform(String name, float value)
    {
        glUniform1f(GetUniformLocation(name), value);
    }

    public void SetUniform(String name, int value)
    {
        glUniform1i(GetUniformLocation(name), value);
    }

    public void SetUniform(String name, Vector2f value)
    {
        glUniform2f(GetUniformLocation(name), value.x, value.y);
    }

    public void SetUniform(String name, Vector3f value)
    {
        glUniform3f(GetUniformLocation(name), value.x, value.y, value.z);
    }

    public void SetUniform(String name, Matrix4f value)
    {
        FloatBuffer matrix = MemoryUtil.memAllocFloat(16);
        value.get(matrix);

        glUniformMatrix4fv(GetUniformLocation(name), false, matrix);
    }

    public void CleanUp()
    {
        glDeleteProgram(program);
    }

    private int GenerateShader(int type, String name, String path)
    {
        int id = glCreateShader(type);

        glShaderSource(id, Objects.requireNonNull(FileHelper.ReadFile(path)));
        glCompileShader(id);
        CheckCompilerErrors(id, name);

        return id;
    }

    private void CheckCompilerErrors(int id, String type)
    {
        if (glGetShaderi(id, GL_COMPILE_STATUS) == GL_FALSE)
        {
            System.err.println("ERROR::SHADER_COMPILATION of type: " + type);
            System.err.println(glGetShaderInfoLog(id));
        }
    }

    private void CheckLinkerErrors()
    {
        if (glGetProgrami(program, GL_LINK_STATUS) == GL_FALSE)
        {
            System.err.println("ERROR::PROGRAM_LINKING");
            System.err.println(glGetProgramInfoLog(program));
        }
    }

    public static Shader Register(String localPath, String name)
    {
        return Register(localPath, name, Settings.defaultDomain);
    }

    public static Shader Register(String localPath, String name, String domain)
    {
        Shader out = new Shader();

        out.name = name;
        out.vertexPath = "/assets/" + domain + "/" + localPath + "Vertex.glsl";
        out.fragmentPath = "/assets/" + domain + "/" + localPath + "Fragment.glsl";

        return out;
    }
}