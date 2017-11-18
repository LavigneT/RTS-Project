package game.entities.units;

import game.Handler;
import game.entities.Entity;
import game.tiles.Tile;

public abstract class Unit extends Entity{
	
	protected float xMove, yMove;
	public static final float default_speed = 4.0f;
	protected float speed;

	public Unit(Handler handler, float x, float y, int width, int height, int buildingTime) {
		super(handler, x, y, width, height, buildingTime);
		speed = default_speed;
		destinationX = (int)x;
		destinationY = (int)y;
	}
	
	/*-------------------------------------------MUST BE IMPROVED----------------------------------------
	 * The system of list is fine but the process during the tempoDest must be improved
	 * Currently we receive a list of order like this from A*:
	 * [2, 5, 1, 5, 4*, 1, 4, 6*, 1, 3, 6, 1, 2, 6, 1, 1, 6]
	 * [2, 5] is the last tile and 4* is the order that lead to the tile
	 * [1, 5] is the tile right before the last tile 6* being the direction leading to it
	 * 
	 * That is why in the tempoDest method where we switch order and direction check if tempY is == -1 because
	 * there is no direction on last order
	 * the gap between the order and the direction should be remove so that the whole process is clearer
	 * 
	 * plus the following methods could be better commented and built
	 */
	
	public void move() {
		if(centerBeforeMove) {
			if(!centerY) {
				
				centerOnYaxis();
				
			} else {
				centerOnXaxis();
				
			}
			
			//once the unit is center on the starting tile apply the movement
		} else {
			
			if(!lastMove) {
				if(!lastTile) {
					tempoDestinationReached();
					
				}	
				
				moveXandY();
				//last tile reached, send the unit to the exact coord
			} else {
				lastMove();
				
			}
		}
		
	}
	
	
	//----------------------------------------------------------Center on Axis X and Y-------------------
	//----------------------------------------------------------------------------------------------------
	//this method check if the destination has been reached
	private void centerOnYaxis() {
		//if it must be placed lower
		if(temporaryDestY > (int)y) {
			//add speed to y axis if not center ont the y axis
			y += speed;
			//When y is centered
			if((int)(y + speed) >= temporaryDestY){
				y = temporaryDestY;
				centerY = true;
			}
		} else if(temporaryDestY < (int)y) {
			//substract speed to y axis
			y -= speed;
			if((int)(y + speed) <= temporaryDestY){
			y = temporaryDestY;
			centerY = true;
			}
		}
	}
	
	//once unit center make x and y move according to the direction index
	private void centerOnXaxis() {
		//if it must be placed lower
		x += speed;
		//When x is centered with a +speed
		if((int)(x + speed) >= temporaryDestX){
			x = temporaryDestX;
			centerBeforeMove = false;
			//set the new temporary coord
			tempoDestinationReached = true;
			
			//if we need to remove speed to x
		} else if(temporaryDestX < (int)x) {
			//if we need to add speed to x
			x -= speed;
			//When x is centered with a +speed
			if((int)(x - speed) <= temporaryDestX){
				x = temporaryDestX;
				centerBeforeMove = false;
				//set the new temporary coord
				tempoDestinationReached = true;
				} 
			}
		}
	
	//-----------------------------------------tempoDestinationReached-----------------------------
	//---------------------------------------------------------------------------------------------------
	
	//---------------------------------------mistake---------------------------------------------------
	//here i forgot to forbid the access of code "if there is space to center the unit on the tile"
	//indeed it would be executed once before any movement and units would gain 20 coords automaticly
	//because y < 0, that's why i check for y == -1 and then -5
	//so that the following code CAN'T be executed during timelapse between
	//the moment we click to give order and the moment the unit receive the new orderList(1frame)
	
	//I did the same mistake with the move methods, movement was executed 1 time before the new order
	//it would move the unit a few pixel according to it's outdated direction and could crash the game if it
	//pushed the top left hedge of the unit (point that determine the carac of the tile in the A* algo) 
	//in a solid block
	
	private void tempoDestinationReached() {
		if(tempoDestinationReached) {
			
			//set the new temporary coord
			tempY-= 3;
			tempX -= 3;
			//if we arrived on last tile
			if(tempY == -1) {
				tempY = 0;
				tempX = 1;
				tempDirection = movementOrder.get(4);
				lastTile =true;
				//if there is space to center the unit on the tile
				if((destinationY - height/2)/Tile.tile_dimension > 0 &&
						((destinationX - width/2)/Tile.tile_dimension > 0) && 
						handler.getWorld().getSolidMap()[(int)((destinationY - height/2)/Tile.tile_dimension)][ 
						(int)((destinationX - width/2)/Tile.tile_dimension)] == 0){
					destinationX -= width/2;
					destinationY -= height/2;
				}

			} else if(tempY != -5 && tempY != -1){
				tempDirection = movementOrder.get(tempX + 4);
				tempoDestinationReached = false;
			}
			if(tempY != -5) {
				temporaryDestY = movementOrder.get(tempY) * Tile.tile_dimension + Tile.tile_dimension/2 - width/2;
				temporaryDestX = movementOrder.get(tempX) * Tile.tile_dimension+ Tile.tile_dimension/2 - height/ 2;
			}
		}
	}
	
	
	//----------------------------------------------moveXandY------------------------------------
	//--------------------------------------------------------------------------------------------
	private void moveXandY() {
		if(tempDirection == 0 && tempY > -2) {//go up
			//check if destination is reached
			if((int)(y - speed) <= temporaryDestY) {
				y = temporaryDestY;
				tempoDestinationReached = true;
				if(lastTile) {
					lastMove = true;
					lastTile = false;
				}
					
					
			} else {
				y += -speed;
			}
			
		} else if(tempDirection == 1 && tempY > -2) {//go up left
			if((int)(y - speed/2) <= temporaryDestY && (int)(x - speed/2) <= temporaryDestX) {
				y = temporaryDestY;
				x = temporaryDestX;
				tempoDestinationReached = true;
				if(lastTile) {
					lastMove = true;
					lastTile = false;
				}
			} else {
				y += -speed/2;
				x += -speed/2;
			}
		} else if(tempDirection == 2 && tempY > -2) {//go left
			if((int)(x - speed) <= temporaryDestX) {
				x = temporaryDestX;
				tempoDestinationReached = true;
				if(lastTile) {
					lastMove = true;
					lastTile = false;
				}
			} else {
				x += -speed;
			}
		} else if(tempDirection == 3 && tempY > -2) {//go down left
			if((int)(y + speed/2) >= temporaryDestY && (int)(x - speed/2) <= temporaryDestX) {
				y = temporaryDestY;
				x = temporaryDestX;
				tempoDestinationReached = true;
				if(lastTile) {
					lastMove = true;
					lastTile = false;
				}
			} else {
				y += speed/2;
				x += -speed/2;
			}

		} 
		else if(tempDirection == 4 && tempY > -2) {//go down
			if((int)(y + speed) >= temporaryDestY) {
				y = temporaryDestY;
				tempoDestinationReached = true;
				if(lastTile) {
					lastMove = true;
					lastTile = false;
				}
			} else {
				y += speed;
			}
		} else if(tempDirection == 5 && tempY > -2) {//go down right
			if((int)(y + speed/2) >= temporaryDestY && (int)(x + speed/2) >= temporaryDestX) {
				y = temporaryDestY;
				x = temporaryDestX;
				tempoDestinationReached = true;
				if(lastTile) {
					lastMove = true;
					lastTile = false;
				}
			} else {
				y += speed/2;
				x += speed/2;
			}
		} else if(tempDirection == 6 && tempY > -2) {//right
			if((int)(x + speed) >= temporaryDestX) {
				x = temporaryDestX;
				tempoDestinationReached = true;
				if(lastTile) {
					lastMove = true;
					lastTile = false;
				}
			} else {
				x += speed;
			}
		} else if(tempDirection == 7 && tempY > -2) {//go up right
			if((int)(y - speed/2) <= temporaryDestY && x + speed/2 >= temporaryDestX) {
				y = temporaryDestY;
				x = temporaryDestX;
				tempoDestinationReached = true;
				if(lastTile) {
					lastMove = true;
					lastTile = false;
				}
			} else {
				y += -speed/2;
				x += speed/2;
			}
		}
	}
	
	//------------------------------------------LastMove------------------------------------------------
	//-----------------------------------------------------------------------------------------------------
	private void lastMove() {
		//System.out.println(destinationY + " " + y + " " + destinationX + " " + x);
		if(x < destinationX) {
			x += speed;
			if(x + speed >= destinationX) {
				x= destinationX;
			}
		} else if(x > destinationX) {
			x -= speed;
			if(x - speed <= destinationX) {
				x = destinationX;
			}
		}
		if(x == destinationX) {
			if(y < destinationY) {
				y += speed;
				if(y + speed >= destinationY) {
					y= destinationY;
					lastMove = false;
					tempY = -2;
				}
			} else if(y > destinationY) {
				y -= speed;
				if(y - speed <= destinationY) {
					y = destinationY;
					lastMove = false;
					tempY = -2;
				}
			}
		}
	}
	


	//---------------------------------------------getters and setters----------------------------------
	//---------------------------------------------------------------------------------------------------------
	


	
}
	

