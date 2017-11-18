package game.interfaces;

import java.awt.Graphics;
import java.util.ArrayList;

import game.Handler;

public abstract class BuildInterface{
	
	protected UIObject uiObject;
	protected Handler handler;
	protected ArrayList<UIObject> buttons;
	//protected ArrayList<BuildInterface> buildInterface;

	public BuildInterface(Handler handler) {
		buttons = new ArrayList<UIObject>();
	}
	
	public int getNumberToBuild() {
		return 0;
	}
	
	public void setNumberToBuild(int numberToBuild) {
		
	}
	
	public abstract void tick();
	
	public abstract void render(Graphics g);
	
	//Getters and setters

	
	
}
