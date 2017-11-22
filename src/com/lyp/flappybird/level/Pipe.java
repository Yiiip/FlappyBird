package com.lyp.flappybird.level;

import com.lyp.flappybird.graphics.Texture;
import com.lyp.flappybird.graphics.VertexArray;
import com.lyp.flappybird.math.Matrix4f;
import com.lyp.flappybird.math.Vector3f;

public class Pipe {

	private static VertexArray mesh;
	private static Texture texture;
	private Matrix4f ml_matrix;
	
	private Vector3f position = new Vector3f();
	private static float WIDTH = 1.5f;
	private static float HEIGHT = 8.0f;
	
	public Pipe(float x, float y) {
		this.position.x = x;
		this.position.y = y;
		ml_matrix = Matrix4f.translate(position);
	}
	
	public static void create() {
		float[] vertices = new float[] { 
			0.0f, 0.0f, 1.0f,
			0.0f, HEIGHT, 1.0f,
			WIDTH, HEIGHT, 1.0f,
			WIDTH, 0.0f, 1.0f
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
		texture = new Texture("res/pipe.png");
	}
	
	public Matrix4f getModelMatrix() {
		return ml_matrix;
	}
	
	public float getX() {
		return position.x;
	}
	
	public float getY() {
		return position.y;
	}
	
	public static VertexArray getMesh() {
		return mesh;
	}
	
	public static Texture getTexture() {
		return texture;
	}
	
	public static float getWidth() {
		return WIDTH;
	}
	
	public static float getHeight() {
		return HEIGHT;
	}
}
