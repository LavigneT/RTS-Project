package game.pathfinding;

import game.Handler;
import game.entities.Entity;
import game.tiles.Tile;

public class PathFinding {
	
	private Handler handler;
	private MovementAlgo movementAlgo;
	private AStartV2 v2;

	public PathFinding(Handler handler) {
		this.handler = handler;
	}
	
	//----------------------------------------------------InitAStar-----------------------------------------
	//--------------------------------------------------------------------------------------------------
	public void initAStart(Entity e) {
		//movementAlgo = new MovementAlgo(handler);
		v2 = new AStartV2(handler);
			
		
		 //------------OLD A*----------------
		 //e.setMovementOrder(movementAlgo.getPath((int)(e.getY() / Tile.tile_dimension), (int)(e.getX() / Tile.tile_dimension),
		 	//(int)((e.getDestinationY()) /Tile.tile_dimension), (int)((e.getDestinationX())/Tile.tile_dimension)));
		

		
		 e.setMovementOrder(v2.getPath((int)(e.getY() / Tile.tile_dimension), (int)(e.getX() / Tile.tile_dimension),
				 	(int)((e.getDestinationY()) /Tile.tile_dimension), (int)((e.getDestinationX())/Tile.tile_dimension)));
		
		e.setTempY(e.getMovementOrder().size() - 3);
		e.setTempX(e.getMovementOrder().size() - 2);
		
		//create a starting position centered on a side or on the center
		e.setTemporaryDestY(e.getMovementOrder().get(e.getTempY())* Tile.tile_dimension + Tile.tile_dimension/2 - e.getWidth()/2);
		e.setTemporaryDestX(e.getMovementOrder().get(e.getTempX())* Tile.tile_dimension + Tile.tile_dimension/2 - e.getWidth()/2);
		e.setTempDirection(e.getMovementOrder().get((e.getTempX() + 1)));

		e.setCenterY(true);
		e.setCenterBeforeMove(true);
		e.setLastMove(false);
		e.setLastTile(false);
		
	}
}
