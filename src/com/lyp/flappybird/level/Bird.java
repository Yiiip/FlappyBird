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
	
	public boolean control = true;

	public Bird() {
		float[] vertices = new float[] { 
			-SIZE / 2.0f, -SIZE / 2.0f, 2.0f, //bird_z > bg_z，图层 
			-SIZE / 2.0f,  SIZE / 2.0f, 2.0f, 
			 SIZE / 2.0f,  SIZE / 2.0f, 2.0f, 
			 SIZE / 2.0f, -SIZE / 2.0f, 2.0f 
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
		if (control && Input.isKeyDown(GLFW.GLFW_KEY_SPACE)) { //向上为y轴正方形
			gravitySpd = -0.15f;
		} else {
			gravitySpd += 0.01f;
		}
		position.y -= gravitySpd;
	}
	
	public void gameover() {
		gravitySpd = -0.12f;
		position.x+=0.08f;
	}
	
	private void rotate() {
		angle = -gravitySpd * 90.0f;
	}

	public void update() {
		gravity();
		rotate();
		//System.out.println("x="+position.x + ", y="+position.y + ", r="+angle + ", g=" + gravitySpd);
	}
	
	public void render() {
		texture.bind();
		ShaderFactory.BIRD.enable();
		Matrix4f cumulating = Matrix4f.rotate(angle).multiply(Matrix4f.translate(position));
		ShaderFactory.BIRD.setUniformMatrix4f("ml_matrix", cumulating);
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
