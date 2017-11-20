#version 330 core

layout (location = 0) out vec4 color;

in DATA {
    vec2 tc;
} fs_in;

uniform sampler2D tex_bg;

void main() {
    //color = vec4(0.9, 0.3, 0.2, 1.0);
    color = texture(tex_bg, fs_in.tc);
}