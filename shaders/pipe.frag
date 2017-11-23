#version 330 core

layout (location = 0) out vec4 color;

in DATA {
    vec2 tc;
    vec3 position;
} fs_in;

uniform vec2 bird;
uniform int is_top;
uniform sampler2D tex_pipe;

void main() {
	vec2 tempTc = vec2(fs_in.tc.x, fs_in.tc.y);
    if (is_top == 1) {
        tempTc.y = 1 - tempTc.y;
    }
    color = texture(tex_pipe, tempTc);
    if (color.w < 1.0) { discard; }
    
    color *= 2.0 / (length(bird - fs_in.position.xy) + 1.3) + 0.52;
	color.w = 1.0;
}