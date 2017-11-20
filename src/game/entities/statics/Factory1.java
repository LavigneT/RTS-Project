package game.entities.statics;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import game.Handler;
import game.entities.units.MediumTank;
import game.gfx.Animation;
import game.gfx.Assets;
import game.tiles.Tile;

public class Factory1 extends StaticEntity{
	
	public static int facto_Width = Tile.tile_dimension, facto_Height = Tile.tile_dimension; 
			
	public static final int idFactoy1 = 20;
	private Animation factoAnim;
	private List<int[]> buildingList; 
	private long lastTime;
	private int countPostion = 0;
	private int[] possiblePostion;

	public Factory1(Handler handler, float y, float x) {
		super(handler, y, x, facto_Width, facto_Height);
		
		//change the state of the tile where the factory has been placed to solid
		handler.getWorld().getSolidMap()[(int)(y /Tile.tile_dimension)]
				[(int)(x /Tile.tile_dimension)] = 1;
		
		//Check surrounding tiles for positioning
		possiblePostion = new int[8];
		createPossiblePostion();
		
		factoAnim = new Animation(Assets.factory1, 1000);
		buildingList = new ArrayList<int[]>();
		handler.getUnitCreator().addFactory1(this);
	}
	
	
	//----------------------------------------------------Tick-----------------------------------------
	
	@Override
	public void tick() {
		//animation of the factory
		if(!buildingList.isEmpty())
			factoAnim.tick();
		
		//if the list is not empty and 1sec has passed by since the last execution
		if(!buildingList.isEmpty() && System.currentTimeMillis() - lastTime >= 50) {
			lastTime = System.currentTimeMillis();
			buildUnits();
		}
		
		bounds.x = (int)(x - handler.getXOffset());
		bounds.y = (int)(y - handler.getYOffset());
	}

	
	//------------------------------------------------Render--------------------------------------------
	
	@Override
	public void render(Graphics g) {
		if(!buildingList.isEmpty())
		g.drawImage(factoAnim.getCurrentFrame(), (int)(x - handler.getXOffset()), 
				(int)(y - handler.getYOffset()), facto_Width,facto_Height, null);
		else
			g.drawImage(Assets.factory1[0], (int)(x - handler.getXOffset()), 
					(int)(y - handler.getYOffset()), facto_Width,facto_Height, null);
		
	}


	
	private void buildUnits() {
		int timeBeforeBuild = buildingList.get(0)[1] -= 1;
		if(timeBeforeBuild <= 0) {
			countPostion++;
			if(countPostion > 5)
				countPostion = 0;
			
			//if the current unit to build is a tank
			if(buildingList.get(0)[0] == 0) {
				//The unit is not directly added to the game, see note at the end of the entityManager
				handler.getWorld().getEntityManager().addEntityToAdd(new MediumTank(handler, 
						determineCoordY(MediumTank.default_height), determineCoordX(MediumTank.default_width)));
				buildingList.remove(0);
			}
		}
	}
	
	//-----------------------------------------createPossiblePostion----------------------------------------
	
	//check if the surrounding tiles are available, later use to place unit around when created
	private void createPossiblePostion() {
		
		//up left
		if((int)(y /Tile.tile_dimension - 1) - 1 >= 0 && (int)(x /Tile.tile_dimension - 1) >= 0){
			possiblePostion[0] = handler.getWorld().getSolidMap()[(int)(y /Tile.tile_dimension - 1)]
					[(int)(x /Tile.tile_dimension - 1)];
		} else {
			possiblePostion[0] = 1;
		}
		
		//up
		if((int)(y /Tile.tile_dimension - 1) - 1 >= 0){
			possiblePostion[1] = handler.getWorld().getSolidMap()[(int)(y /Tile.tile_dimension - 1)]
					[(int)(x /Tile.tile_dimension)];
		} else {
			possiblePostion[1] = 1;
		}
		
		//up right
		if((int)(y /Tile.tile_dimension - 1) - 1 >= 0 && (int)(x /Tile.tile_dimension + 1) < handler.getWorld().getWidth()){
			possiblePostion[2] = handler.getWorld().getSolidMap()[(int)(y /Tile.tile_dimension - 1)]
					[(int)(x /Tile.tile_dimension + 1)];
		} else {
			possiblePostion[2] = 1;
		}
		
		//Right
		if((int)(x /Tile.tile_dimension + 1) < handler.getWorld().getWidth()){
			possiblePostion[3] = handler.getWorld().getSolidMap()[(int)(y /Tile.tile_dimension)]
					[(int)(x /Tile.tile_dimension + 1)];
		} else {
			possiblePostion[3] = 1;
		}
		
		//down right
		if((int)(y /Tile.tile_dimension + 1) < handler.getWorld().getHeight() && (int)(x /Tile.tile_dimension + 1) < handler.getWorld().getWidth()){
			possiblePostion[4] = handler.getWorld().getSolidMap()[(int)(y /Tile.tile_dimension + 1)]
					[(int)(x /Tile.tile_dimension + 1)];
		} else {
			possiblePostion[4] = 1;
		}
		
		//Down
		if((int)(y /Tile.tile_dimension + 1) < handler.getWorld().getHeight()){
			possiblePostion[5] = handler.getWorld().getSolidMap()[(int)(y /Tile.tile_dimension + 1)]
					[(int)(x /Tile.tile_dimension)];
		} else {
			possiblePostion[5] = 1;
		}
		
		//Down left
		if((int)(y /Tile.tile_dimension + 1) < handler.getWorld().getHeight() && (int)(x /Tile.tile_dimension - 1) >= 0){
			possiblePostion[6] = handler.getWorld().getSolidMap()[(int)(y /Tile.tile_dimension + 1)]
					[(int)(x /Tile.tile_dimension - 1)];
		} else {
			possiblePostion[6] = 1;
		}
		
		//left
		if((int)(x /Tile.tile_dimension - 1) >= 0){
			possiblePostion[7] = handler.getWorld().getSolidMap()[(int)(y /Tile.tile_dimension)]
					[(int)(x /Tile.tile_dimension - 1)];
		} else {
			possiblePostion[7] = 1;
		}

	}
	
	//-------------------------------------------------DetermineCoordX / Y--------------------------------
	
	private int determineCoordY(int unitHeight) {
		boolean countPostionFound = false;
		int result = 0;
		while(!countPostionFound) {
			
			if(countPostion == 0 && possiblePostion[0] == 0 && possiblePostion[1] == 0 ) {
				countPostionFound = true;
			} else if(countPostion == 1 && possiblePostion[1] == 0 && possiblePostion[2] == 0) {
				countPostionFound = true;
			} else if(countPostion == 2 && possiblePostion[3] == 0 && possiblePostion[4] == 0) {
				countPostionFound = true;
			} else if(countPostion == 3 && possiblePostion[4] == 0 && possiblePostion[5] == 0) {
				countPostionFound = true;
			} else if(countPostion == 4 && possiblePostion[5] == 0 && possiblePostion[6] == 0) {
				countPostionFound = true;
			} else if(countPostion == 5 && possiblePostion[6] == 0 && possiblePostion[7] == 0) {
				countPostionFound = true;
			}
			
			//if countPostion has not been found, increment it
			if(!countPostionFound) {
				countPostion++;
				if(countPostion > 7) {
					countPostion =0;
				}
			}
		}
		
		//determine Y coord depending on the state of countPostion and the available tile
		if(countPostion == 0)
			result = (int)(y - 4 - unitHeight);
		else if(countPostion == 1)
			result = (int)(y - 4 - unitHeight);
		else if(countPostion == 2)
			result = (int)(y  + 10);
		else if(countPostion == 3)
			result = (int)(y + facto_Height + 4);
		else if(countPostion == 4)
			result = (int)(y + facto_Height + 4);
		else if(countPostion == 5)
			result = (int)(y + 10);
			
		return result;
		
	}
	
	private int determineCoordX(int unitWidth) {
		int result = 0;
		
		//determine Y coord depending on the state of countPostion and the available tile
		if(countPostion == 0)
			result = (int)(x - 15);
		else if(countPostion == 1)
			result = (int)(x + unitWidth);
		else if(countPostion == 2)
			result = (int)(x + facto_Width + 4);
		else if(countPostion == 3)
			result = (int)(x + unitWidth);
		else if(countPostion == 4)
			result = (int)(x - 15);
		else if(countPostion == 5)
			result = (int)(x - 4 - unitWidth);
			
		return result;
		
	}
	
	//----------------------------------------------------Getters and Setters-----------------------------------
	
	public List<int[]> getbuildingList() {
		return buildingList;
	}

	public void addOrderToBuildingList(int[] order) {
		buildingList.add(order);
	}
	
}
