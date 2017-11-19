package game;

import game.entities.Creator.UnitCreator;
import game.inputs.KeyManager;
import game.inputs.MouseManager;
import game.interfaces.MainMenuFrame;
import game.interfaces.UIManager;
import game.interfaces.UIObject;
import game.world.World;

public class Handler {
	
	private Game game;
	private World world;
	private UIObject obj;
	private MainMenuFrame mainMenuFrame;
	private UIManager uiManager;
	private UnitCreator unitCreator;
	
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

	public MainMenuFrame getMainMenuFrame() {
		return mainMenuFrame;
	}

	public void setMainMenuFrame(MainMenuFrame mainMenuFrame) {
		this.mainMenuFrame = mainMenuFrame;
	}
	

	public UIManager getUiManager() {
		return uiManager;
	}

	public void setUiManager(UIManager uiManager) {
		this.uiManager = uiManager;
	}

	public UnitCreator getUnitCreator() {
		return unitCreator;
	}

	public void setUnitCreator(UnitCreator unitCreator) {
		this.unitCreator = unitCreator;
	}
	
	
	
	
}
