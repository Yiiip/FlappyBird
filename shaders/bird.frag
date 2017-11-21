#version 330 core

layout (location = 0) out vec4 color;

in DATA {
    vec2 tc;
} fs_in;

uniform sampler2D tex_bird;

void main() {
    color = texture(tex_bird, fs_in.tc);
    if (color.w < 1.0) { discard; } //the w is alpha chanel
}