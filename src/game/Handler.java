package game;

import game.entities.Creator.EntityCreator;
import game.inputs.KeyManager;
import game.inputs.MouseManager;
import game.interfaces.Interfaces;
import game.interfaces.UIManager;
import game.interfaces.UIObject;
import game.world.World;

public class Handler {
	
	private Game game;
	private World world;
	private UIObject obj;
	private Interfaces inter;
	private UIManager uiManager;
	private EntityCreator entityCreator;
	
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

	public UIObject getObj() {
		return obj;
	}

	public void setObj(UIObject obj) {
		this.obj = obj;
	}

	public Interfaces getInter() {
		return inter;
	}

	public void setInter(Interfaces inter) {
		this.inter = inter;
	}
	

	public UIManager getUiManager() {
		return uiManager;
	}

	public void setUiManager(UIManager uiManager) {
		this.uiManager = uiManager;
	}

	public EntityCreator getEntityCreator() {
		return entityCreator;
	}

	public void setEntityCreator(EntityCreator entityCreator) {
		this.entityCreator = entityCreator;
	}
	
	
	
	
}
