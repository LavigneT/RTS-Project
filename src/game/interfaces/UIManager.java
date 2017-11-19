package game.interfaces;

import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import game.Handler;

public class UIManager {
		
	private Handler handler;
	private ArrayList<UIObject> objects;
	
	/*
	 * ----------------Indexes of interfaces
	 * 
	 * 0 - BuildTankInterface
	 * 
	 */
	private List<BuildInterface> interfaces = new ArrayList<>();
	
	public UIManager(Handler handler) {
		this.handler = handler;
		objects = new ArrayList<UIObject>();
		handler.setUiManager(this);
		
		interfaces.add(new BuildTankInterface(handler));
	}
	
	public void tick() {
		
		for(UIObject o : objects) {
			o.tick();
		}
		
		//check if we must display various menus, make them display by setting clicked to true 
		if(handler.getInter().isBuildMenu()) {
			//Top icon
			objects.get(0).setClicked(true);
			//
		} else {
			objects.get(0).setClicked(false);
		}
		if(handler.getInter().isTankMenu()) {
			//Top icon
			objects.get(1).setClicked(true);
			//tick the button in the choice menu contain in the list
			for(UIObject o : interfaces.get(0).buttons) {
				o.tick();
			}

		} else {
			objects.get(1).setClicked(false);
			
		}
		
	}
	
	public void render(Graphics g) {
		for(UIObject o : objects) {
			o.rende(g);
		}
		//If tank menu is clicked render textField and image in the top right "screen"
		if(handler.getInter().isTankMenu()) {
			interfaces.get(0).render(g);
			for(UIObject o : interfaces.get(0).buttons) {
				o.rende(g);
			}
		}
		
		
	}
	
	public void onMouseMove(MouseEvent e) {
		for(UIObject o : objects) {
			o.onMouseMove(e);
		}
		
		//check if mouse is hover over the tank building interface
		if(handler.getInter().isTankMenu()) {
			for(UIObject o : interfaces.get(0).buttons) {
				o.onMouseMove(e);
			}
		}

	}
	
	public void MouseOnReleased(MouseEvent e) {
		for(UIObject o : objects) {
			o.onMouseReleased(e);
		}
		
		//check if mouse is clicked on the tank building interface
		if(handler.getInter().isTankMenu()) {
			for(UIObject o : interfaces.get(0).buttons) {
				o.onMouseReleased(e);
			}
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

	public List<BuildInterface> getInterfaces() {
		return interfaces;
	}
	
	public void  addInterfaces(BuildInterface interfa) {
		interfaces.add(interfa);
	}
	
	
}
