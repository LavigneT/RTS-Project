package game.pathfinding;

import java.util.Comparator;
import java.util.List;

import javax.swing.plaf.synth.SynthSpinnerUI;

import game.Handler;
import game.entities.Entity;
import game.tiles.Tile;

public class PlacementAlgoV2 {
	
	private List<Entity> entities;
	private Handler handler;
	private int[][] solidMap;
	private int width, height;
	private PathFinding pathFinding;
	private Comparator<Entity> widthSorter = new Comparator<Entity>() {

		@Override
		public int compare(Entity a, Entity b) {
			if(a.getWidth() < b.getWidth()) {
				return -1;
			}
			return 1;
		}
		
	};
	
	public PlacementAlgoV2(List<Entity> entities, Handler handler) {
		this.entities = entities;
		entities.sort(widthSorter);
		this.handler = handler;
		
		solidMap = handler.getWorld().getSolidMap();
		width = handler.getWorld().getWidth();
		height = handler.getWorld().getHeight();
		
		pathFinding = new PathFinding(handler);
		
		setFinalDestination();
	}
	
	private void setFinalDestination() {
		
		//Directions :
		//0-up    1-up_left    2-left   3-down_left    4-down    5-down_right    6-right   7-up_right
		boolean up, up_left, left, down_left, down, down_right, right, up_right;
		int beforPlacementX, beforPlacementY;
		boolean allUnitPlaced = false;
		int[][] idPlacements =new int[entities.size()*2][entities.size()*2];
		
		//set the whole array to -1, so when a unit is placed it will be <0
		for(int x = 0; x <idPlacements.length; x++) {
			for(int y = 0; y <idPlacements.length; y++) {
				idPlacements[x][y] = -1;
			}
		}
		
		beforPlacementX = entities.get(0).getDestinationX();
		beforPlacementY = entities.get(0).getDestinationY();
		int startingTileX = (int)beforPlacementX/Tile.tile_dimension;
		int startingTileY = (int)beforPlacementY/Tile.tile_dimension;
		
		idPlacements[(int)Math.floor(entities.size())][(int)Math.floor(entities.size())] = 0;
		
		int placementNum = 1;
		int numberOfUnitPlacedDuringLoop = 0;
		
		int currentPosX = beforPlacementX;
		int currentPosY = beforPlacementY;	
		
		int saveCurrentX = beforPlacementX;
		int saveCurrentY = beforPlacementY;
		
		int currentPositionIdPlacementY = (int)Math.floor(entities.size());
		int currentPositionIdPlacementX = (int)Math.floor(entities.size());
		
		int saveCurentIdPlacementX = currentPositionIdPlacementX ;
		int saveCurentIdPlacementY = currentPositionIdPlacementY ;
		
		/*-----------------------------------------------NOTES----------------------------------------------------
		we need to add the width / height of the occupant in the case of right / down ONLY to check if the edge will 
		overlap on a solid rock
		
		i had a problem here because i initiate placementNum =0 so when it loops another time it would try +1 and will
		be out of limits if it was next unit.
		
		-----------------------------------------------Could be improve-----------------------------------------------
		
		Currently it should work for the right and left direction. Meaning if it can't place unit on one edge it will
		Automatically try to place the rest of the units on the other side
		That's what should be work later, implements the same idea for UP and Down 
		Then try to make the algorithm still works if the four cardinals directions are obstructed
		
		
		----------------------------------------------------------------------------------------------------------------
		-----------------------------------------------Main Loop--------------------------------------------------------*/
		int turn = 0;
		boolean rightLine = true, leftLine =true, topColumn = true, bottomColumn = true;
		boolean first  = true;

		while(!allUnitPlaced && entities.size() != 1 && turn < 50 ) {

			//System.out.println("------------------------------------turn num" + turn);
			
			
			//-----------------------------------------------Bottom Edge Check-----------------------------------------------
			if(turn % 2 == 1 && bottomColumn) {
				

				//If both edges are available and future staring position is not solid
				if(topColumn && idPlacements[currentPositionIdPlacementY + turn][currentPositionIdPlacementX] != -1) {
					for(int y = 0; y < turn; y ++) {
						currentPosY += entities.get(idPlacements[currentPositionIdPlacementY + y][currentPositionIdPlacementX ]).getHeight();
					}
					currentPositionIdPlacementY += turn;
					//If left edge is not available anymore and future staring position is not solid
				} else if(!topColumn && idPlacements[currentPositionIdPlacementY + 1][currentPositionIdPlacementX] != -1){
					currentPosY += entities.get(idPlacements[currentPositionIdPlacementY + 1][currentPositionIdPlacementX]).getHeight();
					currentPositionIdPlacementY++;
					//if the future starting tile is solid
				} else {
					bottomColumn = false;
				}

				
				
				//------------------------------------------------Top Edge Check-----------------------------------------------
			} else if(turn % 2 == 0 && turn != 0 && topColumn) {
				
					
				//If both edges are available and future starting position is not solid
				if(bottomColumn && idPlacements[currentPositionIdPlacementY - turn][currentPositionIdPlacementX] != -1) {
					for(int y = 1; y <= turn; y ++) {
						currentPosY -= entities.get(idPlacements[currentPositionIdPlacementY - y][currentPositionIdPlacementX]).getWidth();
					}
					currentPositionIdPlacementY -= turn;
					
					//If right edge is not available anymore and future starting position is not solid
				} else if(!bottomColumn && idPlacements[currentPositionIdPlacementY - 1][currentPositionIdPlacementX] != -1){
						currentPosY -= entities.get(idPlacements[currentPositionIdPlacementY - 1][currentPositionIdPlacementX]).getWidth();
						currentPositionIdPlacementY--;
						
						//if the future starting tile is solid
					} else {
						topColumn = false;
					}			
			}
			
			if(!topColumn && !bottomColumn) {
				
				if(first)
					turn = 0; first = false;currentPositionIdPlacementY = saveCurentIdPlacementY; currentPosY = saveCurrentY;
				
				//------------------------------------------------Right Edge Check-----------------------------------------------
				if(turn % 2 == 1 && rightLine) {
					
					
					//If both edges are available and future staring position is not solid
					if(leftLine && idPlacements[currentPositionIdPlacementY][currentPositionIdPlacementX + turn] != -1) {
						for(int y = 0; y < turn; y ++) {
							currentPosX += entities.get(idPlacements[currentPositionIdPlacementY][currentPositionIdPlacementX + y]).getWidth();
						}
						currentPositionIdPlacementX += turn;
						
						//If left edge is not available anymore and future starting position is not solid
					} else if(!leftLine && idPlacements[currentPositionIdPlacementY][currentPositionIdPlacementX + 1] != -1){
						currentPosX += entities.get(idPlacements[currentPositionIdPlacementY][currentPositionIdPlacementX + 1]).getWidth();
						currentPositionIdPlacementX++;
						
						//if the future starting tile is solid
					} else {
						rightLine = false;
					}


					
					//------------------------------------------------Left Edge Check-----------------------------------------------
				} else if(turn % 2 == 0 && leftLine) {
					
					//If both edges are available and future starting position is not solid
					if(rightLine && idPlacements[currentPositionIdPlacementY][currentPositionIdPlacementX - turn] != -1) {
						//if(turn != 0) {
							for(int y = 1; y <= turn; y ++) {
								currentPosX -= entities.get(idPlacements[currentPositionIdPlacementY][currentPositionIdPlacementX - y]).getWidth();
							}
							currentPositionIdPlacementX -= turn;
						//}

						//If right edge is not available anymore and future starting position is not solid
					} else if(!rightLine && idPlacements[currentPositionIdPlacementY][currentPositionIdPlacementX - 1] != -1){
						//if(turn != 0) {
							currentPosX -= entities.get(idPlacements[currentPositionIdPlacementY][currentPositionIdPlacementX - 1]).getWidth();
							currentPositionIdPlacementX--;
						//}
							//if the future starting tile is solid
						} else {
							leftLine = false;
						}			
				}
				
				
			}
			
			
			//---------------------------------------Checks of surrounding tiles------------------------------------------------
			
			//I didn't use all those boolean for now
			up = false; up_left = false; left =false; down_left =false; down = false; down_right = false; right =false; up_right= false;
			
			/*The four following methods look for possible placement around them in the four cardinals directions
			  according to the height and width needed by the current unit
			  
			 
			 */
			//Check if current Y + height is within bounds of the world and if it is a solid Tile
			//up
			 
			
			if(idPlacements[currentPositionIdPlacementY- 1][currentPositionIdPlacementX] == -1 && 
					(int)(((currentPosY - (entities.get(placementNum).getHeight()))/Tile.tile_dimension)) >=0 &&
					solidMap[(int)(((currentPosY - (entities.get(placementNum).getHeight()))/Tile.tile_dimension))][currentPosX/Tile.tile_dimension] == 0) {
				up = true;
			}
			//Check if current Y + height +HEIGHT OF THE OCCUPANT is within bounds of the world and if it is a solid Tile
			//down
			if(idPlacements[currentPositionIdPlacementY+ 1][currentPositionIdPlacementX] == -1 && 
					(int)(((currentPosY + (entities.get(placementNum).getHeight() + entities.get(idPlacements[currentPositionIdPlacementY][currentPositionIdPlacementX]).getHeight()))/Tile.tile_dimension)) < height &&
					solidMap[(int)((currentPosY + (entities.get(placementNum).getHeight()))/Tile.tile_dimension)][currentPosX/Tile.tile_dimension] == 0) {
				down = true;
			}
			
			//right
			if(idPlacements[currentPositionIdPlacementY][currentPositionIdPlacementX +1] == -1 && 
					(int)(((currentPosX + (entities.get(placementNum).getWidth() + entities.get(idPlacements[currentPositionIdPlacementY][currentPositionIdPlacementX]).getWidth()))/Tile.tile_dimension)) < width &&
					solidMap[currentPosY/Tile.tile_dimension][(int)((currentPosX + (entities.get(placementNum).getHeight()))/Tile.tile_dimension)] == 0) {
				right = true;
				
			}
			
			//left
			if(idPlacements[currentPositionIdPlacementY][currentPositionIdPlacementX -1] == -1 && 
					(int)(((currentPosX - (entities.get(placementNum).getWidth()))/Tile.tile_dimension)) >=0 &&
					solidMap[currentPosY/Tile.tile_dimension][(int)((currentPosX - (entities.get(placementNum).getHeight()))/Tile.tile_dimension)] == 0) {
				left = true;
			}
			
			
			//-------------------------------------------positions' assignment------------------------------------------
			//-----------------------------------------------------------------------------------------------------------
			
			//The following code will assign position to the unit tested before, and may also assign positions
			//to units that have that share the SAME DIMENSIONS with the previous unit if there
			//still is available tile 
			
			
			numberOfUnitPlacedDuringLoop = 0;
			
			//to shorten the code bellow when comparing dimensions, simply save the width and height of the 1st
			//unit to be compare with other unit later on
			int widthUnitTested = entities.get(placementNum + numberOfUnitPlacedDuringLoop).getWidth();
			int heightUnitTested = entities.get(placementNum + numberOfUnitPlacedDuringLoop).getHeight();
			int widthSecondUnitTested = entities.get(placementNum + numberOfUnitPlacedDuringLoop).getWidth();
			int heighSecondtUnitTested = entities.get(placementNum + numberOfUnitPlacedDuringLoop).getHeight();
			//------------------------------------------------------Down------------------------------------------------------
			if(down && placementNum + numberOfUnitPlacedDuringLoop < entities.size()) {
				down = false;
				//check that we try to place the unit tested before
				if(numberOfUnitPlacedDuringLoop == 0) {
					entities.get(placementNum + numberOfUnitPlacedDuringLoop).setDestinationY(currentPosY + entities.get(placementNum + numberOfUnitPlacedDuringLoop).getHeight());
					entities.get(placementNum + numberOfUnitPlacedDuringLoop).setDestinationX(currentPosX);
					idPlacements[currentPositionIdPlacementY + 1][currentPositionIdPlacementX] = placementNum + numberOfUnitPlacedDuringLoop;
					numberOfUnitPlacedDuringLoop++;
					//if the first unit has been positioned try to position another one if the dimensions are the same
				} else if(widthSecondUnitTested == widthUnitTested && heighSecondtUnitTested == heightUnitTested) {
					
					entities.get(placementNum + numberOfUnitPlacedDuringLoop).setDestinationY(currentPosY + entities.get(placementNum + numberOfUnitPlacedDuringLoop).getHeight());
					entities.get(placementNum + numberOfUnitPlacedDuringLoop).setDestinationX(currentPosX);
					idPlacements[currentPositionIdPlacementY + 1][currentPositionIdPlacementX] = placementNum + numberOfUnitPlacedDuringLoop;
					numberOfUnitPlacedDuringLoop++;

				}
			}
			
			//------------------------------------------------------UP--------------------------------------------------
			if(up && placementNum + numberOfUnitPlacedDuringLoop < entities.size()) {
				up = false;
				//check that we try to place the unit tested before
				if(numberOfUnitPlacedDuringLoop == 0) {
					entities.get(placementNum + numberOfUnitPlacedDuringLoop).setDestinationY(currentPosY - entities.get(placementNum + numberOfUnitPlacedDuringLoop).getHeight());
					entities.get(placementNum + numberOfUnitPlacedDuringLoop).setDestinationX(currentPosX);
					idPlacements[currentPositionIdPlacementY - 1][currentPositionIdPlacementX] = placementNum + numberOfUnitPlacedDuringLoop;
					numberOfUnitPlacedDuringLoop++;
					//if the first unit has been positioned try to position another one if the dimensions are the same
				} else if(widthSecondUnitTested == widthUnitTested && heighSecondtUnitTested == heightUnitTested) {
					entities.get(placementNum + numberOfUnitPlacedDuringLoop).setDestinationY(currentPosY - entities.get(placementNum + numberOfUnitPlacedDuringLoop).getHeight());
					entities.get(placementNum + numberOfUnitPlacedDuringLoop).setDestinationX(currentPosX);
					idPlacements[currentPositionIdPlacementY - 1][currentPositionIdPlacementX] = placementNum + numberOfUnitPlacedDuringLoop;
					numberOfUnitPlacedDuringLoop++;
				}
			}
			
			//------------------------------------------------------RIGHT--------------------------------------------------
			if(right && placementNum + numberOfUnitPlacedDuringLoop < entities.size()) {
				right = false;
				
				//check that we try to place the unit tested before, in that case we need to add the width of the occupant of the tile
				if(numberOfUnitPlacedDuringLoop == 0) {
					entities.get(placementNum + numberOfUnitPlacedDuringLoop).setDestinationY(currentPosY);
					entities.get(placementNum + numberOfUnitPlacedDuringLoop).setDestinationX(currentPosX + entities.get(placementNum + numberOfUnitPlacedDuringLoop).getWidth());
					idPlacements[currentPositionIdPlacementY][currentPositionIdPlacementX + 1] = placementNum + numberOfUnitPlacedDuringLoop;
					numberOfUnitPlacedDuringLoop++;
					
					//if the first unit has been positioned try to position another one if the dimensions are the same
				} else if(widthSecondUnitTested == widthUnitTested && heighSecondtUnitTested == heightUnitTested) {
					
					entities.get(placementNum + numberOfUnitPlacedDuringLoop).setDestinationY(currentPosY);
					entities.get(placementNum + numberOfUnitPlacedDuringLoop).setDestinationX(currentPosX + entities.get(placementNum + numberOfUnitPlacedDuringLoop).getWidth());
					idPlacements[currentPositionIdPlacementY][currentPositionIdPlacementX + 1] = placementNum + numberOfUnitPlacedDuringLoop;
					numberOfUnitPlacedDuringLoop++;
				}
			}
			
			//------------------------------------------------------left--------------------------------------------------
			if(left && placementNum + numberOfUnitPlacedDuringLoop < entities.size()) {
				left = false;
				
				//check that we try to place the unit tested before, in that case we need to add the width of the occupant of the tile
				if(numberOfUnitPlacedDuringLoop == 0) {
					entities.get(placementNum + numberOfUnitPlacedDuringLoop).setDestinationY(currentPosY);
					entities.get(placementNum + numberOfUnitPlacedDuringLoop).setDestinationX(currentPosX - entities.get(placementNum + numberOfUnitPlacedDuringLoop).getWidth());
					idPlacements[currentPositionIdPlacementY][currentPositionIdPlacementX - 1] = placementNum + numberOfUnitPlacedDuringLoop;
					numberOfUnitPlacedDuringLoop++;
					
					//if the first unit has been positioned try to position another one if the dimensions are the same
				} else if(widthSecondUnitTested == widthUnitTested && heighSecondtUnitTested == heightUnitTested) {
					
					entities.get(placementNum + numberOfUnitPlacedDuringLoop).setDestinationY(currentPosY);
					entities.get(placementNum + numberOfUnitPlacedDuringLoop).setDestinationX(currentPosX - entities.get(placementNum + numberOfUnitPlacedDuringLoop).getWidth());
					idPlacements[currentPositionIdPlacementY][currentPositionIdPlacementX - 1] = placementNum + numberOfUnitPlacedDuringLoop;
					numberOfUnitPlacedDuringLoop++;
				}
			}
			
			//if we have found positions for all the units quit the loop
			if((placementNum += numberOfUnitPlacedDuringLoop) == entities.size()){
				allUnitPlaced = true;
				
			}
			
			turn++;
			
		}
		//trigger A* for all units with final destinations possessed here
		for(Entity e : entities) {
			pathFinding.initAStart(e);
		}
		
		//pathFinding.initAStart(entities.get(0));
	}
	
}
