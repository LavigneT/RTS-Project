package game;

import game.inputs.KeyManager;
import game.inputs.MouseManager;
import game.world.World;

public class Handler {
	
	private Game game;
	private World world;
	
	public Handler(Game game){
		this.game = game;
	}
	
	public int getWidth() {
		return game.getWidth();
	}
	
	public int getHeight() {
		return game.getHeight();
	}
	
	public MouseManager getMouseManager() {
		return game.getMouseManager();
	}

	public World getWorld() {
		return world;
	}

	public void setWorld(World world) {
		this.world = world;
	}
	
	public KeyManager getKeyManager() {
		return game.getKeyManager();
	}
	
	public float getXOffset() {
		return game.getGameCamera().getxOffset();
	}
	
	public float getYOffset() {
		return game.getGameCamera().getyOffset();
	}
	
	
}
