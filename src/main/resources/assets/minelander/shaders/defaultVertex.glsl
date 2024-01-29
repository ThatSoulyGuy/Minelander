#version 330 core

layout (location = 0) in vec3 positionIn;
layout (location = 1) in vec3 colorIn;
layout (location = 2) in vec2 textureCoordinatesIn;

out vec3 color;
out vec2 textureCoordinates;

uniform mat4 model;
uniform mat4 projection;
uniform mat4 view;

void main()
{
    color = colorIn;
    textureCoordinates = textureCoordinatesIn;

    gl_Position = projection * view * model * vec4(positionIn, 1.0f);
}