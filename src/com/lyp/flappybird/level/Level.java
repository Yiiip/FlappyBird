package com.lyp.flappybird.level;

import com.lyp.flappybird.graphics.ShaderFactory;
import com.lyp.flappybird.graphics.Texture;
import com.lyp.flappybird.graphics.VertexArray;

public class Level {

	private VertexArray background;
	private Texture bgTexture;

	public Level() {
		float[] vertices = new float[] { 
			-10.0f, -10.0f * 9.0f / 16.0f, 0.0f,
			-10.0f,  10.0f * 9.0f / 16.0f, 0.0f,
			  0.0f,  10.0f * 9.0f / 16.0f, 0.0f,
			  0.0f, -10.0f * 9.0f / 16.0f, 0.0f
		};
		
		byte[] indices = new byte[] {
			0, 1, 2,
			2, 3, 0
		};
		
		float[] textureCoordinates = new float[] {
				0, 1,
				0, 0,
				1, 0,
				1, 1
		};
		
		background = new VertexArray(vertices, indices, textureCoordinates);
		bgTexture = new Texture("res/bg.jpg");
	}
	
	public void render() {
		bgTexture.bind();
		ShaderFactory.BG.enable();
		background.render();
		ShaderFactory.BG.disable();
		bgTexture.unbind();
	}
}
