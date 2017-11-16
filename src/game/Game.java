package game;

import java.awt.Graphics;
import java.awt.image.BufferStrategy;

import game.display.Display;
import game.gfx.Assets;
import game.inputs.KeyManager;
import game.inputs.MouseManager;
import game.pathfinding.AStartV2;
import game.states.GameState;
import game.states.MenuState;
import game.states.State;

public class Game implements Runnable{
	
	private int width, height;
	private String title;
	
	private Thread thread;
	private boolean running;
	
	private BufferStrategy bs;
	private Graphics g;
	
	private Display display;
	private Handler handler;
	
	private State gameState;
	private MenuState menuState;
	
	private MouseManager mouseManager;
	private KeyManager keyManager;
	
	private GameCamera gameCamera;
	
	public Game(String title, int width, int height) {
		this.width = width;
		this.title =title;
		this.height = height;
		
		
		keyManager = new KeyManager();
		mouseManager =new MouseManager();
	}
	
	private void init() {
		display = new Display(title, width, height);
		Assets.init();
		display.getCanvas().addMouseListener(mouseManager);
		display.getCanvas().addMouseMotionListener(mouseManager);
		display.getFrame().addMouseMotionListener(mouseManager);
		display.getFrame().addMouseListener(mouseManager);
		display.getFrame().addKeyListener(keyManager);
		
		handler = new Handler(this);
		
		
		gameCamera = new GameCamera(handler);
		mouseManager.setHandler(handler, width/2, height/2);
		
		gameState = new GameState(handler);
		menuState = new MenuState(handler);
		State.setCurrentState(gameState);
	}
	
	private void tick() {
		keyManager.tick();
		mouseManager.tick();
		gameCamera.tick();
		if(State.getCurrentState()!=null)
			State.getCurrentState().tick();
	}
	
	private void render() {
		bs = display.getCanvas().getBufferStrategy();
		if(bs==null) {
			display.getCanvas().createBufferStrategy(3);
			return;
		}
		
		g = bs.getDrawGraphics();
		g.clearRect(0, 0, width, height);
		//draw
		if(State.getCurrentState()!=null)
			State.getCurrentState().render(g);
		mouseManager.render(g);
		//stop drawing
		bs.show();
		g.dispose();
		
	}
	
	@Override
	public void run() {
		init();
		
		int fps = 40;
		double timePerTick = 1000000000 / fps;
		double delta = 0;
		long now;
		long lastTime = System.nanoTime();
		int ticks= 0;
		long timer = 0;
		
		while(running) {
			
			now = System.nanoTime();
			delta += (now - lastTime) / timePerTick;
			timer += (now - lastTime);
			lastTime = now;
			
			if(delta >= 1) {
				tick();
				render();
				delta--;
				ticks++;
			}

			if(timer >= 1000000000) {
				//System.out.println(ticks);
				ticks = 0;
				timer = 0;
			}
		}
	}
	
	public synchronized void start() {
		if(running)
			return;
		
		running = true;
		thread = new Thread(this);
		thread.start();
	}
	
	public synchronized void stop() {
		if(!running)
			return;
		
		running = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public MouseManager getMouseManager() {
		return mouseManager;
	}

	public void setMouseManager(MouseManager mouseManager) {
		this.mouseManager = mouseManager;
	}

	public KeyManager getKeyManager() {
		return keyManager;
	}

	public GameCamera getGameCamera() {
		return gameCamera;
	}

	
	
	

}
