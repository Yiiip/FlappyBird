package com.lyp.flappybird.math;

import static java.lang.Math.*;

import java.nio.FloatBuffer;

import com.lyp.flappybird.utils.BufferUtils;

/**
 * �Ľ׾���
 */
public class Matrix4f {

	public final static int SIZE = 4 * 4;
	public float[] elements = new float[SIZE];

	public Matrix4f() {
	}

	/**
	 * ��λ����
	 * 
	 * @return
	 */
	public static Matrix4f identity() {
		Matrix4f result = new Matrix4f();
		for (int i = 0; i < SIZE; i++) {
			result.elements[i] = 0.0f;
		}
		result.elements[0 + 0 * 4] = 1.0f;
		result.elements[1 + 1 * 4] = 1.0f;
		result.elements[2 + 2 * 4] = 1.0f;
		result.elements[3 + 3 * 4] = 1.0f;
		return result;
	}

	/**
	 * ����˻�
	 * 
	 * @param matrix
	 * @return
	 */
	public Matrix4f multiply(Matrix4f matrix) {
		Matrix4f result = new Matrix4f();
		for (int y = 0; y < 4; y++) {
			for (int x = 0; x < 4; x++) {
				float sum = 0.0f;
				for (int e = 0; e < 4; e++) {
					sum += this.elements[e + y * 4] * matrix.elements[x + e * 4];
				}
				result.elements[x + y * 4] = sum;
			}
		}
		return result;
	}

	/**
	 * ƽ�ƾ��� ���������ľ���[12],[13],[14]�±괦�ֱ���Ϊ��x,��y,��z
	 * 
	 * @param vector3f
	 * @return
	 */
	public static Matrix4f translate(Vector3f vector3f) {
		Matrix4f result = identity();
		result.elements[0 + 3 * 4] = vector3f.x;
		result.elements[1 + 3 * 4] = vector3f.y;
		result.elements[2 + 3 * 4] = vector3f.z;
		return result;
	}

	/**
	 * ��ת���� (����������������) (2Dͼ��ı�����Ƕȣ�ֻ��z����ת)
	 */
	public static Matrix4f rotate(float angle) {
		Matrix4f result = identity();
		float r = (float) toRadians(angle);
		float cos = (float) cos(r);
		float sin = (float) sin(r);
		result.elements[0 + 0 * 4] = cos;
		result.elements[1 + 0 * 4] = sin;
		result.elements[0 + 1 * 4] = -sin;
		result.elements[1 + 1 * 4] = cos;
		return result;
	}

	/**
	 * �õ����ض�����ת����ת���� ��{x=1, y=0, z=0}����x����ת��
	 * 
	 * @param angle
	 * @param x
	 * @param y
	 * @param z
	 * @return
	 */
	public static Matrix4f rotate(float angle, float x, float y, float z) {
		Matrix4f result = identity();
		float r = (float) toRadians(angle);
		float cos = (float) cos(r);
		float sin = (float) sin(r);
		float omc = 1.0f - cos;
		result.elements[0 + 0 * 4] = x * omc + cos;
		result.elements[1 + 0 * 4] = y * x * omc + z * sin;
		result.elements[2 + 0 * 4] = x * z * omc - y * sin;

		result.elements[0 + 1 * 4] = x * y * omc - z * sin;
		result.elements[1 + 1 * 4] = y * omc + cos;
		result.elements[2 + 1 * 4] = y * z * omc + x * sin;

		result.elements[0 + 2 * 4] = x * z * omc + y * sin;
		result.elements[1 + 2 * 4] = y * z * omc - x * sin;
		result.elements[2 + 2 * 4] = z * omc + cos;
		return result;
	}

	/**
	 * ����ͶӰ���� (����������������)
	 * 
	 * @param left
	 * @param right
	 * @param bottom
	 * @param top
	 * @param near
	 * @param far
	 * @return
	 */
	public static Matrix4f orthographic(float left, float right, float bottom, float top, float near, float far) {
		Matrix4f result = identity();
		result.elements[0 + 0 * 4] = 2.0f / (right - left);

		result.elements[1 + 1 * 4] = 2.0f / (top - bottom);

		result.elements[2 + 2 * 4] = 2.0f / (near - far);

		result.elements[0 + 3 * 4] = (left + right) / (left - right);
		result.elements[1 + 3 * 4] = (bottom + top) / (bottom - top);
		result.elements[2 + 3 * 4] = (far + near) / (far - near);
		return result;
	}

	/**
	 * lwjgl��Ҫfloat buffer��ʽ������Ҫ������ת��һ��
	 * @return
	 */
	public FloatBuffer toFloatBuffer() {
		return BufferUtils.createFloatBuffer(elements);
	}
}
