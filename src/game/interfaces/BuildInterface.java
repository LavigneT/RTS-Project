package game.interfaces;

import java.awt.Graphics;
import java.util.ArrayList;

import game.Handler;

public abstract class BuildInterface{
	
	protected UIObject uiObject;
	protected Handler handler;
	protected ArrayList<UIObject> buttons;

	public BuildInterface(Handler handler) {
		buttons = new ArrayList<UIObject>();
	}
	
	
	public abstract void tick();
	
	public abstract void render(Graphics g);
	
	//Getters and setters

	
	
}
