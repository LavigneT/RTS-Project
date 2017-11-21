package game.entities;

import java.awt.Graphics;


import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import game.Handler;
import game.entities.Creator.UnitCreator;
import game.entities.statics.Factory1;
import game.entities.units.Builder1;
import game.entities.units.Unit;
import game.pathfinding.PlacementAlgo;
import game.pathfinding.PlacementAlgoV2;
import game.tiles.Tile;

public class EntityManager {
	
	List<Entity> entities = new ArrayList<Entity>();
	List<Entity> placementEntities = new ArrayList<Entity>();
	List<Builder1> builderSelected = new ArrayList<>();
	
	//see comment at the end of tick
	List<Entity> entityToAdd = new ArrayList<Entity>();
	
	private UnitCreator entityCreator;
	
	public Comparator<Entity> sortOnYCoord = new Comparator<Entity>() {

		@Override
		public int compare(Entity a, Entity b) {
			if(a.getY() +a.getHeight()> b.getY() + b.getHeight()) 
				return 1;
			else if(a.getY() +a.getHeight() < b.getY() + b.getHeight())
				return -1;
			else
				return 0;
		}
		
	};
	
	/*
	 * This tick and render all entities in the game
	 * It also put aside builder in a list so that they can be used by the BuildingCreator to send
	 * builders on a construction site. This code is located before the test of "solidity" as for now
	 * the constructionFactory1 have a 1 index in the solid map and that would prevent  the 
	 * "builderSelected" list from being updated.
	 */
	
	private Handler handler;
	
	public EntityManager(Handler handler) {
		this.handler = handler;
		entityCreator = new UnitCreator(handler, this);
		handler.setUnitCreator(entityCreator);
		
		entities.add(new Builder1(handler, 100, 100));
		entities.add(new Builder1(handler, 100, 100));
		entities.add(new Builder1(handler, 100, 100));
	}
	
	public void tick(){
		
		entities.sort(sortOnYCoord);
		//System.out.println(builderSelected.size());
		//To select Units / trigger algos placement movement
		Iterator<Entity> it = entities.iterator();
		while(it.hasNext()) {
			Entity e = it.next();
			if(handler.getMouseManager().isTimeToSelect()) {
				if(e.getClass().getSuperclass().getSimpleName().equals("Unit")) {
					if(e.bounds.intersects(handler.getMouseManager().getSelectRect())) {
						e.setSelected(true);
						handler.getMouseManager().setUnitSelected(true);
					}
				}
			} else if(handler.getMouseManager().isDefocus()) {
				//if the buildingCreator is active, the player may want to send builder to building site
				if(handler.getUiManager().isBuildingCreatorActive() && e.getClass().getSimpleName().equals("Builder1") && e.isSelected()) {
					Builder1 builder = (Builder1) e;
					builderSelected.add(builder);
					
				}
				if(handler.getMouseManager().isSaveLocation() && e.isSelected() && 
						//only trigger movements algorithms if the destination is not solid
						handler.getWorld().getSolidMap()[(int)((handler.getMouseManager().getDestinationY() + handler.getYOffset())/Tile.tile_dimension)]
								[(int)((handler.getMouseManager().getDestinationX() + (int)handler.getXOffset())/Tile.tile_dimension)] == 0) {
					

					e.setDestinationX(handler.getMouseManager().getDestinationX() + (int)handler.getXOffset());
					e.setDestinationY(handler.getMouseManager().getDestinationY() + (int)handler.getYOffset());
					placementEntities.add(e);
					
				}
				e.setSelected(false);
			}
			e.tick();
			if(e.die) {
				e = null;
				it.remove();
			}
		}
		//At the end to allows several entities to get the same order
		handler.getMouseManager().setTimeToSelect(false);
		
		if(handler.getMouseManager().isDefocus()) {
			
			handler.getMouseManager().setDefocus(false);
			if(handler.getMouseManager().isSaveLocation()) {
				//Signify that we don't have units selected anymore
				handler.getMouseManager().setUnitSelected(false);
					
				//only trigger movements algorithms if the destination is not solid
				if(handler.getWorld().getSolidMap()[(int)((handler.getMouseManager().getDestinationY() + handler.getYOffset())/Tile.tile_dimension)]
								[(int)((handler.getMouseManager().getDestinationX() + (int)handler.getXOffset())/Tile.tile_dimension)] == 0) {
				//algo placement that will give the DestinationY and X before path is calculate
				//PlacementAlgo placement = new PlacementAlgo(placementEntities, handler);
					PlacementAlgoV2 placement = new PlacementAlgoV2(placementEntities, handler);
				}
				
				placementEntities.clear();
				
			}
		}
		handler.getMouseManager().setSaveLocation(false);
		
		/*
		 *I had to create entity here because factories would had new units to the list while this
		 *same list was already being iterate.
		 * It resulted in a "ConcurrentModificationException" that would crash the game
		 */
		if(!entityToAdd.isEmpty()) {
			createEntity();
		}
	}
	
	public void render(Graphics g) {
		//render all entities in the game in a sorted order
		for(Entity e : entities)
			e.render(g);
		
		//entityCreator.render(g);
	}
	
	public void createEntity() {
		for(Entity ent : entityToAdd) {
			entities.add(ent);
		}
		entityToAdd.clear();
	}
	
	
	//----------------------------------------Getters and setters--------------------------------------

	public List<Entity> getEntities() {
		return entities;
	}
	
	public void addEntity(Entity e) {
		entities.add(e);
	}

	public void addEntityToAdd(Entity e) {
		entityToAdd.add(e);
	}

	public List<Builder1> getBuilderSelected() {
		return builderSelected;
	}

	public List<Entity> getPlacementEntities() {
		return placementEntities;
	}
	
	
	
	
}
