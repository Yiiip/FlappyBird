package com.lyp.flappybird.graphics;

import com.lyp.flappybird.math.Matrix4f;

public class ShaderFactory {

	public static Shader BG;

	public static void loadAll() {
		if (BG == null) {
			BG = new Shader("shaders/bg.vert", "shaders/bg.frag");
			Matrix4f projectionMatrix = Matrix4f.orthographic(
				-10.0f, 10.0f, -10.0f * 9.0f / 16.0f, 10.0f * 9.0f / 16.0f, -1.0f, 1.0f //可视区域 x[-5,5] y[-10, 10]
			);
			BG.setUniformMatrix4f("pr_matrix", projectionMatrix);
		}
	}
}
