package game.entities.Creator;

import java.awt.Graphics;

import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import game.Handler;
import game.entities.Entity;
import game.entities.statics.Factory1;
import game.entities.statics.constructions.Construction;
import game.entities.statics.constructions.ConstructionFactory1;
import game.entities.units.Builder1;
import game.gfx.Assets;
import game.interfaces.UIManager;
import game.pathfinding.PathFinding;
import game.tiles.Tile;

public class BuildingCreator {
	
	private Handler handler;
	private UIManager uiManager;
	private int mouseX, mouseY;
	private List<int[]> orders = new ArrayList<>();
	private List<Construction> constructions = new ArrayList<>();
	
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
	
	/*----------------------------Tick-----------------------------------
	 * 
	 * 1st method : if both orders / constructions list are empty, then all the building in the orders have
	 * been placed and they've all been completed
	 * we can stop ticking / render this class until next order
	 * 
	 */

	public void tick() {
		//1st
		if(orders.isEmpty() && constructions.isEmpty()) {
			uiManager.setBuildingCreatorActive(false);
		}
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
		
		//If there is still orders in the list the player can lay new construction on the map
		if(!orders.isEmpty()) {
			executeOrders(e);
			
			//if the player want to send builder to a building site
		} else if(!constructions.isEmpty() && e.getButton()==MouseEvent.BUTTON3) {
			sendBuilderToConstructionSite(e);
		}
		
		

	}
	
	//add order to the list
	public void addOrder(int[] order) {
		orders.add(order);
	}
	
	//-----------------------------------------------------ExecuteOrders--------------------------------
	
	private void executeOrders(MouseEvent e) {
		if(e.getButton()==MouseEvent.BUTTON3) {
			orders.clear();
			uiManager.setBuildingCreatorActive(false);
			
			//the player place a new construction on the map
		} else if(e.getButton()==MouseEvent.BUTTON1) {
			
			//if the list contains Factoty1 building orders
			if(orders.get(0)[0] == 20) {
				addFactory1();
			}
		}
	}
	
	//------------------------------------------addFactory1------------------------------------------------
	
	private void addFactory1() {
		//check that the the tile is available for constructionFactory
		if(handler.getWorld().getSolidMap()[(int)(mouseY + handler.getYOffset())/Tile.tile_dimension]
				[(int)(mouseX + handler.getXOffset())/Tile.tile_dimension] == 0) {
			ConstructionFactory1 newFacto = new ConstructionFactory1(handler, 
					((int)(mouseY + handler.getYOffset())/Tile.tile_dimension)*Tile.tile_dimension, 
					((int)(mouseX + handler.getXOffset())/Tile.tile_dimension)*Tile.tile_dimension);
					constructions.add(newFacto);
					handler.getWorld().getEntityManager().addEntity(newFacto);
			orders.remove(0);
		}
	}
	
	//------------------------------------------sendBuilderToConstructionSite--------------------------------------------------
	
	private void sendBuilderToConstructionSite(MouseEvent e) {
		for(Construction c : constructions) {
			if(c.getBounds().contains(e.getPoint())) {
				for(int x = 0; x < c.getBuilderEmplacement().length; x++) {
					if(c.getBuilderEmplacement()[x] == true && !handler.getWorld().getEntityManager().getBuilderSelected().isEmpty()) {
						Builder1 builder = handler.getWorld().getEntityManager().getBuilderSelected().get(0);
						
						//Temporary code
						builder.setDestinationY(ConstructionFactory1.emplacementY_X[x*2]);
						builder.setDestinationX(ConstructionFactory1.emplacementY_X[x*2+1]);
						//the place have been attributed
						c.getBuilderEmplacement()[x] = false;
						handler.getWorld().getEntityManager().getBuilderSelected().remove(0);
						
						PathFinding path = new PathFinding(handler);
						path.initAStart(builder);
					}
				}
			}
		}
		handler.getWorld().getEntityManager().getBuilderSelected().clear();
	}
	

	//Getters and Setters
	
	
	
}
