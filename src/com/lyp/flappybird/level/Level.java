package com.lyp.flappybird.level;

import java.util.Random;

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
	
	private Bird bird;
	private Pipe[] pipes;
	
	private float PIPE_OFFSET = 5.0f; //半个屏幕宽
	private int index = 0;
	
	private Random random = new Random();

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
		
		bird = new Bird();
		initPipes();
	}
	
	private void initPipes() {
		Pipe.create();
		this.pipes = new Pipe[5 * 2]; //5 top, 5 bottom, and reuse them
		for (int i = 0; i < this.pipes.length; i+=2) {
			this.pipes[i] = new Pipe(PIPE_OFFSET + 3.0f * index, random.nextFloat() * 5.0f);
			this.pipes[i+1] = new Pipe(pipes[i].getX(), pipes[i].getY() - (11.0f+random.nextFloat()*2.0f));
			index+=2;
		}
	}
	
	public void updatePipes() {
		pipes[index % 10] = new Pipe(PIPE_OFFSET + 3.0f * index, random.nextFloat() * 5.0f);
		pipes[(index+1) % 10] = new Pipe(pipes[index % 10].getX(), pipes[index % 10].getY() - (11.0f+random.nextFloat()*2.0f));
		index+=2;
	}
	
	public void update() {
		xScroll -= 1;
		if (Math.abs(xScroll) % 250 == 0) { //335 -> xScoll*0.03
			bgIndex++;
			System.out.println(""+xScroll);
			System.out.println(""+bgIndex);
		}
		if (Math.abs(xScroll) > 250 && Math.abs(xScroll) % 125 == 0) {
			updatePipes();
		}
		bird.update();
	}
	
	public void render() {
		bgTexture.bind();
		ShaderFactory.BG.enable();
		background.bind();
		for (int i = bgIndex; i < bgIndex + 4; i++) { //重复4个, view_matrix
			ShaderFactory.BG.setUniformMatrix4f("vw_matrix", Matrix4f.translate(new Vector3f(i * 10 + xScroll * 0.04f, 0, 0))); //10为可视区域的一半
			background.draw();
		}
		ShaderFactory.BG.disable();
		bgTexture.unbind();
		
		renderPipes();
		bird.render();
	}
	
	private void renderPipes() {
		ShaderFactory.PIPE.enable();
		ShaderFactory.PIPE.setUniformMatrix4f("vw_matrix", Matrix4f.translate(new Vector3f(0.05f * xScroll, 0, 0))); //pipe移动快慢
		Pipe.getTexture().bind();
		Pipe.getMesh().bind();
		
		for (int i = 0; i < pipes.length; i++) {
			ShaderFactory.PIPE.setUniformMatrix4f("ml_matrix", pipes[i].getModelMatrix());
			ShaderFactory.PIPE.setUniform1i("is_top", i % 2 == 0 ? 1 : 0);
			Pipe.getMesh().draw();
		}
		Pipe.getMesh().unbind();
		Pipe.getTexture().unbind();
		ShaderFactory.PIPE.disable();
	}
}
