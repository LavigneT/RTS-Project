package game.entities;

import java.awt.Graphics;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import game.Handler;
import game.entities.units.Unit;
import game.pathfinding.PlacementAlgo;
import game.pathfinding.PlacementAlgoV2;
import game.tiles.Tile;

public class EntityManager {
	
	List<Entity> entities = new ArrayList<Entity>();
	List<Entity> placementEntities = new ArrayList<Entity>();
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
	
	private Handler handler;
	
	public EntityManager(Handler handler) {
		this.handler = handler;
	}
	
	public void tick() {
		
		entities.sort(sortOnYCoord);
		
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
	}
	
	public void render(Graphics g) {
		for(Entity e : entities)
			e.render(g);
	}

	public List<Entity> getEntities() {
		return entities;
	}
	
	public void addEntity(Entity e) {
		entities.add(e);
	}
	
}
