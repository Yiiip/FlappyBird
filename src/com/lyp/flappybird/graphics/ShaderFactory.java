package com.lyp.flappybird.graphics;

import static org.lwjgl.opengl.GL13.*;

import com.lyp.flappybird.math.Matrix4f;

public class ShaderFactory {

	public static Shader BG;
	public static Shader BIRD;
	public static Shader PIPE;
	public static Shader FADE;
	
	private static Matrix4f projectionMatrix = Matrix4f.orthographic(
		-10.0f, 10.0f, -10.0f * 9.0f / 16.0f, 10.0f * 9.0f / 16.0f, -10.0f, 10.0f //可视区域 x[-5,5] y[-10, 10]
	);

	public static void loadAll() {
		glActiveTexture(GL_TEXTURE1);
		
		if (BG == null) {
			BG = new Shader("shaders/bg.vert", "shaders/bg.frag");
			BG.setUniformMatrix4f("pr_matrix", projectionMatrix);
			BG.setUniform1i("tex_bg", 1);
		}
		
		if (BIRD == null) {
			BIRD = new Shader("shaders/bird.vert", "shaders/bird.frag");
			BIRD.setUniformMatrix4f("pr_matrix", projectionMatrix);
			BIRD.setUniform1i("tex_bird", 1);
		}
		
		if (PIPE == null) {
			PIPE = new Shader("shaders/pipe.vert", "shaders/pipe.frag");
			PIPE.setUniformMatrix4f("pr_matrix", projectionMatrix);
			PIPE.setUniform1i("tex_pipe", 1);
		}
		
		if (FADE == null) {
			FADE = new Shader("shaders/fade.vert", "shaders/fade.frag");
		}
	}
}
