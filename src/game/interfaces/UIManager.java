package game.interfaces;

import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import game.Handler;

public class UIManager {
		
	private Handler handler;
	private ArrayList<UIObject> mainFrameObjects;
	private MainMenuFrame mainMenuFrame;
	
	/*
	 * Instantiate in World.java
	 * 
	 * This class receive mouseEvent from the MouseManager, and transmit to the button by going 
	 * through the list
	 * 
	 * ----------------Indexes of interfaces
	 * 
	 * 0 - BuildTankInterface
	 * 
	 */
	private List<BuildInterface> interfaces = new ArrayList<>();
	
	public UIManager(Handler handler) {
		this.handler = handler;
		handler.setUiManager(this);
		mainFrameObjects = new ArrayList<UIObject>();
		handler.getMouseManager().setUiManager(this);
		
		interfaces.add(new BuildingInterface(handler));
		mainMenuFrame = new MainMenuFrame(handler, this);
		
	}
	
	public void tick() {
		
		if(handler.getKeyManager().menu) {
			mainMenuFrame.tick();
			
			for(UIObject o : mainFrameObjects) {
				o.tick();
			}
			
			//check if we must display various menus, make them display by setting clicked to true 
			if(mainMenuFrame.isBuildMenu()) {
				//Top icon
				mainFrameObjects.get(0).setClicked(true);
				//tick the button in the choice menu contain in the list
				for(UIObject o : interfaces.get(0).buttons) {
					o.tick();
				}
			} else {
				mainFrameObjects.get(0).setClicked(false);
			}
			if(mainMenuFrame.isTankMenu()) {
				//Top icon
				mainFrameObjects.get(1).setClicked(true);
				//tick the button in the choice menu contain in the list
				for(UIObject o : interfaces.get(0).buttons) {
					o.tick();
				}

			} else {
				mainFrameObjects.get(1).setClicked(false);
				
			}
			
		}
		
		
		
	}
	
	public void render(Graphics g) {
		
		if(handler.getKeyManager().menu) {
			
			mainMenuFrame.render(g);
			
			for(UIObject o : mainFrameObjects) {
				o.rende(g);
			}
			//If tank menu is clicked render textField and image in the top right "screen"
			if(mainMenuFrame.isTankMenu() ||mainMenuFrame.isBuildMenu()) {
				interfaces.get(0).render(g);
				for(UIObject o : interfaces.get(0).buttons) {
					o.rende(g);
				}
			}
		}
	}
	
	public void onMouseMove(MouseEvent e) {
		
		//Transmit MouseMove to buttons of the mainFrameObjects and TankMenu
		if(handler.getKeyManager().menu) {
			
			for(UIObject o : mainFrameObjects) {
				o.onMouseMove(e);
			}
			//check if mouse is hover over the tank building interface
			if(mainMenuFrame.isTankMenu()) {
				for(UIObject o : interfaces.get(0).buttons) {
					o.onMouseMove(e);
				}
			}
		}
	}
	
	public void MouseOnReleased(MouseEvent e) {
		
		//Transmit MouseRelease to buttons of the mainFrameObjects and TankMenu
		if(handler.getKeyManager().menu) {
			for(UIObject o : mainFrameObjects) {
				o.onMouseReleased(e);
			}
			
			//check if mouse is clicked on the tank building interface
			if(mainMenuFrame.isTankMenu()) {
				for(UIObject o : interfaces.get(0).buttons) {
					o.onMouseReleased(e);
				}
			}
		}

		

	}
	
	public void addMainFrameObjects(UIObject o ) {
		mainFrameObjects.add(o);
	}
	
	public void removeMainFrameObjects(UIObject o) {
		mainFrameObjects.remove(o);
	}

	public Handler getHandler() {
		return handler;
	}

	public void setHandler(Handler handler) {
		this.handler = handler;
	}

	public ArrayList<UIObject> getObjects() {
		return mainFrameObjects;
	}

	public void setMainFrameObjects(ArrayList<UIObject> objects) {
		this.mainFrameObjects = objects;
	}

	public List<BuildInterface> getInterfaces() {
		return interfaces;
	}
	
	public void  addInterfaces(BuildInterface interfa) {
		interfaces.add(interfa);
	}
	
	
}
