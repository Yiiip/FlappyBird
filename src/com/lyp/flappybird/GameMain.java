package com.lyp.flappybird;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.system.MemoryUtil.*;
import static org.lwjgl.opengl.GL11.*;

import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;

import com.lyp.flappybird.input.Input;

public class GameMain implements Runnable {

	private int width = 1280;
	private int height = 720;
	private String title = "Flappy !";

	private boolean running = true;
	private Thread thread;

	private long window;

	public void start() {
		running = true;
		thread = new Thread(this, "GameLoopThread");
		thread.start();
	}

	private void init() {
		if (glfwInit()) {
			System.out.println(glfwGetVersionString());
		}
		glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);
		window = glfwCreateWindow(width, height, title, NULL, NULL);
		if (window == NULL) {
			System.err.println("Could not create GLFW window !");
		}

		GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
		glfwSetWindowPos(window, (vidmode.width() - width) / 2, (vidmode.height() - height) / 2);
		
		glfwSetKeyCallback(window, new Input());
		
		glfwMakeContextCurrent(window);
		glfwSwapInterval(1); // ‘ –Ìv-sync
		glfwShowWindow(window);
		
		GL.createCapabilities(); //¥¥Ω®OpenGL Context
		System.out.println("OpenGL : " + glGetString(GL_VERSION));
		glClearColor(0.0f, 1.0f, 1.0f, 1.0f);
		glEnable(GL_DEPTH_TEST);
		
	}

	private void update() {
		glfwPollEvents();
		
		if (Input.keys[GLFW_KEY_SPACE]) {
			System.out.println("Fly");
		}
	}

	private void render() {
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		glfwSwapBuffers(window);
	}

	@Override
	public void run() {
		init();
		while (running) {
			update();
			render();

			if (glfwWindowShouldClose(window)) {
				running = false;
			}
		}
	}

	public static void main(String[] args) {
		new GameMain().start();
	}

}
