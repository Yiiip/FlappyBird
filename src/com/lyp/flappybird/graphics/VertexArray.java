package com.lyp.flappybird.graphics;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

import com.lyp.flappybird.utils.BufferUtils;

public class VertexArray {

	private int count;
	private int vertexArrayObject;
	private int vertexBufferObject;
	private int indexBufferObject;
	private int textureBufferObject;
	
	/**
	 * 
	 * @param vertices 顶点数组
	 * @param indices 索引数组
	 * @param textureCoordinates
	 */
	public VertexArray(float[] vertices, byte[] indices, float[] textureCoordinates) {
		this.count = indices.length;

		this.vertexArrayObject = glGenVertexArrays();
		glBindVertexArray(vertexArrayObject);
		
		this.vertexBufferObject = glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER, this.vertexBufferObject);
		glBufferData(GL_ARRAY_BUFFER, BufferUtils.createFloatBuffer(vertices), GL_STATIC_DRAW);
		glVertexAttribPointer(Shader.VERTEX_ATTRIB, 3, GL_FLOAT, false, 0, 0);
		glEnableVertexAttribArray(Shader.VERTEX_ATTRIB);
		
		this.textureBufferObject = glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER, this.textureBufferObject);
		glBufferData(GL_ARRAY_BUFFER, BufferUtils.createFloatBuffer(textureCoordinates), GL_STATIC_DRAW);
		glVertexAttribPointer(Shader.TEXTURE_COORDINATE_ATTRIB, 2, GL_FLOAT, false, 0, 0);
		glEnableVertexAttribArray(Shader.TEXTURE_COORDINATE_ATTRIB);
		
		this.indexBufferObject = glGenBuffers();
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, indexBufferObject);
		glBufferData(GL_ELEMENT_ARRAY_BUFFER, BufferUtils.createByteBuffer(indices), GL_STATIC_DRAW);
		
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
		glBindBuffer(GL_ARRAY_BUFFER, 0);
		glBindVertexArray(0);
	}
	
	public void bind() {
		glBindVertexArray(vertexArrayObject);
		if (indexBufferObject > 0) {
			glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, indexBufferObject);
		}
	}
	
	public void unbind() {
		if (indexBufferObject > 0) {
			glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
		}
		glBindVertexArray(0);
	}
	
	public void draw() {
		if (indexBufferObject > 0) {
			glDrawElements(GL_TRIANGLES, count, GL_UNSIGNED_BYTE, 0);
		} else {
			glDrawArrays(GL_TRIANGLES, 0, count);
		}
	}
	
	public void render() {
		bind();
		draw();
	}
}
