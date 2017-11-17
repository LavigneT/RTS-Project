package game.interfaces;

import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import game.Handler;

public class UIManager {
	
	private Handler handler;
	private ArrayList<UIObject> objects;
	
	public UIManager(Handler handler) {
		this.handler = handler;
		objects = new ArrayList<UIObject>();
		handler.setUiManager(this);
	}
	
	public void tick() {

		for(UIObject o : objects) {
			o.tick();
		}
		
		//check if we must display various menus, make them display by setting clicked to true 
		if(handler.getInter().isBuildMenu()) {
			objects.get(0).setClicked(true);
		} else {
			objects.get(0).setClicked(false);
		}
		if(handler.getInter().isTankMenu()) {
			objects.get(1).setClicked(true);
		} else {
			objects.get(1).setClicked(false);
		}
		
	}
	
	public void render(Graphics g) {
		for(UIObject o : objects) {
			o.rende(g);
		}
	}
	
	public void onMouseMove(MouseEvent e) {
		for(UIObject o : objects) {
			o.onMouseMove(e);
		}
	}
	
	public void MouseOnReleased(MouseEvent e) {
		for(UIObject o : objects) {
			o.onMouseReleased(e);
		}
	}
	
	public void addObject(UIObject o ) {
		objects.add(o);
	}
	
	public void removeObject(UIObject o) {
		objects.remove(o);
	}

	public Handler getHandler() {
		return handler;
	}

	public void setHandler(Handler handler) {
		this.handler = handler;
	}

	public ArrayList<UIObject> getObjects() {
		return objects;
	}

	public void setObjects(ArrayList<UIObject> objects) {
		this.objects = objects;
	}
	
}
