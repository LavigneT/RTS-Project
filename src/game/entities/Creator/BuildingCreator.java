package game.entities.Creator;

import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import game.Handler;
import game.entities.EntityManager;
import game.entities.statics.Factory1;
import game.gfx.Assets;
import game.interfaces.UIManager;

public class BuildingCreator {
	
	private Handler handler;
	//private EntityManager entityManager;
	private UIManager uiManager;
	private int mouseX, mouseY;
	private List<int[]> orders = new ArrayList<>();
	
	/*
	 * This class is instantiate in UIManager
	 * When  hammer is clicked with the purpose of building new buildings, set buildingCreatorActive
	 * to true (UIManager) and get mouseEvent, tick and render from this same class
	 * While this class is active an image of the building is display at the coord of the mouse
	 * 
	 */
	
	public BuildingCreator(Handler handler, UIManager uiManager2) {
		this.handler = handler;
		this.uiManager = uiManager2;
	}
	


	public void tick() {
		
	}
	
	public void render(Graphics g) {
		if(!orders.isEmpty()) {
			//if the order is to build a Factory1
			if(orders.get(0)[0]== 20) {
				g.drawImage(Assets.buildFactory1, mouseX -Factory1.facto_Width/2, mouseY - Factory1.facto_Height/2, 
						Factory1.facto_Width, Factory1.facto_Height, null);
			}
		}
	}
	
	public void onMouseMove(MouseEvent e) {
		mouseX = e.getX();
		mouseY = e.getY();
	}
	
	public void MouseOnReleased(MouseEvent e) {
		if(e.getButton()==MouseEvent.BUTTON3) {
			orders.clear();
			uiManager.setBuildingCreatorActive(false);
		}
	}
	
	public void addOrder(int[] order) {
		orders.add(order);
	}
	
}
