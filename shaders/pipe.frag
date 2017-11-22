#version 330 core

layout (location = 0) out vec4 color;

in DATA {
    vec2 tc;
} fs_in;

uniform int is_top;
uniform sampler2D tex_pipe;

void main() {
	vec2 tempTc = vec2(fs_in.tc.x, fs_in.tc.y);
    if (is_top == 1) {
        tempTc.y = 1 - tempTc.y;
    }
    color = texture(tex_pipe, tempTc);
    if (color.w < 1.0) { discard; }
}