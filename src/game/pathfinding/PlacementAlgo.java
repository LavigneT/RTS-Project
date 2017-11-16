package game.pathfinding;

import java.util.Comparator;
import java.util.List;

import game.Handler;
import game.entities.Entity;
import game.entities.units.Unit;
import game.tiles.Tile;

public class PlacementAlgo {
	
	private Handler handler;
	private List<Entity> entities;
	private int[][] solidMap;
	private int width, height;
	private PathFinding pathFinding;
	private int count; 
	
	//store the number of tile possible depending on the direction west/east/north/south
	private int[] numberOfTileAvailaible;
	private int[] widthsUnit, heightUnit;
	private int beforPlacementX, beforPlacementY;
	private boolean westPossible = true, eastPossible = true, northPossible = true, southPossible = true;
	private Comparator<Entity> widthSorter = new Comparator<Entity>() {

		@Override
		public int compare(Entity a, Entity b) {
			if(a.getWidth() < b.getWidth()) {
				return -1;
			} else {
				return 1;
			}
		}
	};
	
	public PlacementAlgo(List<Entity> entities, Handler handler) {
		this.entities = entities;
		//sort the entities in the list relative to their width
		entities.sort(widthSorter);
		
		solidMap = handler.getWorld().getSolidMap();
		width = handler.getWorld().getWidth();
		height = handler.getWorld().getHeight();
		
		pathFinding = new PathFinding(handler);
		
		setFinalDestination();
	}
	
	private void setFinalDestination() {
		//System.out.println("------------new turn----------- algo placement");
		beforPlacementX = entities.get(0).getDestinationX();
		beforPlacementY = entities.get(0).getDestinationY();
		int startingTileX = (int)beforPlacementX/Tile.tile_dimension;
		int startingTileY = (int)beforPlacementY/Tile.tile_dimension;
		int unitNumber = entities.size();
		widthsUnit = new int[unitNumber + 1];
		heightUnit = new int[unitNumber + 1];
		int totalWidth = 0 , totalHeight= 0;
		
		
		int y = 1;
		for(Entity e : entities) {
			totalWidth += e.getWidth();
			totalHeight += e.getHeight();
			//so that we can later offset units by progressivly adding the totalWidth/height
			//start at 1 because the first unit will  not have any offset
			widthsUnit[y] = totalWidth;
			heightUnit[y] = totalHeight;
			y++;
		}
		int numberOfTileNeededX = (int)Math.floor(totalWidth/Tile.tile_dimension);
		int numberOfTileNeededY = (int)Math.floor(totalHeight/Tile.tile_dimension);
		
		boolean positionsFound = false;
		int x = 0;
		numberOfTileAvailaible = new int[4];
		//look for successive available tiles in the 4 cardinals directions
		while(!positionsFound && x <= numberOfTileNeededX && x <=numberOfTileNeededY) {
			x++;
			if(startingTileX + x < width &&solidMap[startingTileY][startingTileX + x] == 0 && westPossible ) {
				numberOfTileAvailaible[0] += 1;
			} else if(westPossible){
				westPossible = false;
			}
			if(startingTileX - x >= 0 &&solidMap[startingTileY][startingTileX - x] == 0 && eastPossible ) {
				numberOfTileAvailaible[1] += 1;
			} else if(eastPossible){
				eastPossible = false;
			}
			if(startingTileY - x >= 0 && solidMap[startingTileY - x][startingTileX] == 0 && northPossible ) {
				numberOfTileAvailaible[2] += 1;
			}  else if(northPossible){
				northPossible = false;
			}
			if(startingTileY + x < height && solidMap[startingTileY + x][startingTileX] == 0 && southPossible) {
				numberOfTileAvailaible[3] += 1;
			}  else if(southPossible){
				southPossible = false;;
			}
			
			if(numberOfTileAvailaible[0] >= numberOfTileNeededX) {
				
				//start at 0 so that the first unit has no offset
				y = 0;
				for(Entity e : entities) {
					e.setDestinationX(beforPlacementX + widthsUnit[y]);
					e.setDestinationY(beforPlacementY);
					pathFinding.initAStart(e);
					y++;
					positionsFound = true;
				}
			} else if(numberOfTileAvailaible[1] >= numberOfTileNeededX) {
				
				//start at 0 so that the first unit has no offset
				y = 0;
				for(Entity e : entities) {
					e.setDestinationX(beforPlacementX - widthsUnit[y]);
					e.setDestinationY(beforPlacementY);
					pathFinding.initAStart(e);
					y++;
					positionsFound= true;
				}
			} else if(numberOfTileAvailaible[2] >= numberOfTileNeededY) {
				
				//start at 0 so that the first unit has no offset
				y = 0;
				for(Entity e : entities) {
					e.setDestinationY(beforPlacementY - heightUnit[y]);
					e.setDestinationX(beforPlacementX);
					pathFinding.initAStart(e);
					y++;
					positionsFound= true;
				}
			} else if(numberOfTileAvailaible[3] >= numberOfTileNeededY) {
				//start at 0 so that the first unit has no offset
				y = 0;
				for(Entity e : entities) {
					e.setDestinationY(beforPlacementY + heightUnit[y]);
					e.setDestinationX(beforPlacementX);
					pathFinding.initAStart(e);
					y++;
					positionsFound= true;
				}
			}
		}
	}
	

	
	
	
	
	public void check() {
		
	}
	
	
	
}
