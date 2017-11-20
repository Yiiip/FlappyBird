package com.lyp.flappybird.level;

import com.lyp.flappybird.graphics.ShaderFactory;
import com.lyp.flappybird.graphics.Texture;
import com.lyp.flappybird.graphics.VertexArray;
import com.lyp.flappybird.math.Matrix4f;
import com.lyp.flappybird.math.Vector3f;

public class Level {

	private VertexArray background;
	private Texture bgTexture;
	
	private int xScroll = 0;
	private int bgIndex = 0;

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
	
	public void update() {
		xScroll -= 1;
		if (Math.abs(xScroll) % 250 == 0) { //335 -> xScoll*0.03
			bgIndex++;
			System.out.println(""+xScroll);
			System.out.println(""+bgIndex);
		}
	}
	
	public void render() {
		bgTexture.bind();
		ShaderFactory.BG.enable();
		background.bind();
		for (int i = bgIndex; i < bgIndex + 3; i++) { //重复3个
			ShaderFactory.BG.setUniformMatrix4f("vw_matrix", Matrix4f.translate(new Vector3f(i * 10 + xScroll * 0.04f, 0, 0))); //10为可视区域的一半
			background.draw();
		}
		ShaderFactory.BG.disable();
		bgTexture.unbind();
	}
}
