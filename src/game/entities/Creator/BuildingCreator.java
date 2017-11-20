package game.entities.Creator;

import java.awt.Graphics;

import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import game.Handler;
import game.entities.statics.ConstructionFactory1;
import game.entities.statics.Factory1;
import game.gfx.Assets;
import game.interfaces.UIManager;
import game.tiles.Tile;

public class BuildingCreator {
	
	private Handler handler;
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
		handler.setBuildingCreator(this);
	}
	


	public void tick() {
		
	}
	
	public void render(Graphics g) {
		if(!orders.isEmpty()) {
			//if the order is to build a Factory1
			if(orders.get(0)[0]== 20) {
				g.drawImage(Assets.buildFactory1, ((int)(mouseX + handler.getXOffset())/Tile.tile_dimension)*Tile.tile_dimension, 
						((int)(mouseY + handler.getYOffset())/Tile.tile_dimension)*Tile.tile_dimension, 
						Factory1.facto_Width, Factory1.facto_Height, null);
			}
		}
	}
	
	public void onMouseMove(MouseEvent e) {
		mouseX = e.getX();
		mouseY = e.getY();
	}
	
	/*
	 * Here get mouseEvent from the UIMAnager, if right click is released, we clear the list and exit
	 * if left click is released and the list is not empty we place a new factory at the specified 
	 * coordinates
	 * 
	 */
	public void MouseOnReleased(MouseEvent e) {
		if(e.getButton()==MouseEvent.BUTTON3) {
			orders.clear();
			uiManager.setBuildingCreatorActive(false);
			
		} else if(e.getButton()==MouseEvent.BUTTON1) {
			
			//if the list contains Factoty1 building orders
			if(!orders.isEmpty() && orders.get(0)[0] == 20) {
				addFactory1();
			}
		}
	}
	
	//add order to the list
	public void addOrder(int[] order) {
		orders.add(order);
	}
	
	private void addFactory1() {
		//check that the the tile is available
		if(handler.getWorld().getSolidMap()[(int)(mouseY + handler.getYOffset())/Tile.tile_dimension]
				[(int)(mouseX + handler.getXOffset())/Tile.tile_dimension] == 0) {
			handler.getWorld().getEntityManager().addEntity(new ConstructionFactory1(handler, 
					((int)(mouseY + handler.getYOffset())/Tile.tile_dimension)*Tile.tile_dimension, 
					((int)(mouseX + handler.getXOffset())/Tile.tile_dimension)*Tile.tile_dimension));
			orders.remove(0);
		}

		
		//if the list is now empty, we have placed all the buildings so we can exit and stop
		//ticking / render this class
		if(orders.isEmpty()) {
			uiManager.setBuildingCreatorActive(false);
		}
	}

	//Getters and Setters
	
	
	
}
