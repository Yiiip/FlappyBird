package com.lyp.flappybird.level;

import org.lwjgl.glfw.GLFW;

import com.lyp.flappybird.graphics.ShaderFactory;
import com.lyp.flappybird.graphics.Texture;
import com.lyp.flappybird.graphics.VertexArray;
import com.lyp.flappybird.input.Input;
import com.lyp.flappybird.math.Matrix4f;
import com.lyp.flappybird.math.Vector3f;

public class Bird {

	private float SIZE = 1.0f;
	private VertexArray mesh;
	private Texture texture;

	private Vector3f position = new Vector3f();
	private float angle = 0.0f;
	private float gravitySpd = 0.0f;

	public Bird() {
		float[] vertices = new float[] { 
			-SIZE / 2.0f, -SIZE / 2.0f, 1.0f, //(bird_z=1.0) > (bg_z=0.0)，图层 
			-SIZE / 2.0f,  SIZE / 2.0f, 1.0f, 
			 SIZE / 2.0f,  SIZE / 2.0f, 1.0f, 
			 SIZE / 2.0f, -SIZE / 2.0f, 1.0f 
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

		mesh = new VertexArray(vertices, indices, textureCoordinates);
		texture = new Texture("res/bird.png");
	}
	
	private void gravity() {
		if (Input.isKeyDown(GLFW.GLFW_KEY_SPACE)) {
			gravitySpd = -0.15f;
		} else {
			gravitySpd += 0.01f;
		}
		position.y -= gravitySpd;
	}
	
	private void rotate() {
		angle = -gravitySpd * 10.0f;
	}

	public void update() {
		gravity();
		rotate();
		System.out.println("x="+position.x + ", y="+position.y + ", r="+angle + ", g=" + gravitySpd);
	}
	
	public void render() {
		texture.bind();
		ShaderFactory.BIRD.enable();
		ShaderFactory.BIRD.setUniformMatrix4f("ml_matrix", Matrix4f.translate(position).multiply(Matrix4f.rotate(angle)));
		mesh.render();
		ShaderFactory.BIRD.disable();
		texture.unbind();
	}
	
	public float getSize() {
		return SIZE;
	}
	
	public float getY() {
		return position.y;
	}
}
