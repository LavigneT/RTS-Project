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
	
	public static int facto_Width = Tile.tile_dimension, facto_Height = Tile.tile_dimension, default_Time_Creation = 60,
			idFactoy1 = 20;
	private Animation factoAnim;
	private List<int[]> buildingList; 
	private long lastTime;

	public Factory1(Handler handler, float x, float y) {
		super(handler, x, y, facto_Width, facto_Height, default_Time_Creation);
		
		//change the state of the tile where the factory has been placed to solid
		handler.getWorld().getSolidMap()[(int)(y /Tile.tile_dimension)]
				[(int)(x /Tile.tile_dimension)] = 1;
		
		factoAnim = new Animation(Assets.factory1, 1000);
		buildingList = new ArrayList<int[]>();
		handler.getEntityCreator().addFactory1(this);
	}

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

	@Override
	public void render(Graphics g) {
		if(!buildingList.isEmpty())
		g.drawImage(factoAnim.getCurrentFrame(), (int)(x - handler.getXOffset()), 
				(int)(y - handler.getYOffset()), facto_Width,facto_Height, null);
		else
			g.drawImage(Assets.factory1[0], (int)(x - handler.getXOffset()), 
					(int)(y - handler.getYOffset()), facto_Width,facto_Height, null);
		
	}

	public List<int[]> getbuildingList() {
		return buildingList;
	}

	public void addOrderToBuildingList(int[] order) {
		buildingList.add(order);
	}
	
	private void buildUnits() {
		int timeBeforeBuild = buildingList.get(0)[1] -= 1;
		if(timeBeforeBuild <= 0) {
			//if the current unit to build is a tank
			if(buildingList.get(0)[0] == 0) {
				//The unit is not directly added to the game, see note at the end of the entityManager
				handler.getWorld().getEntityManager().addEntityToAdd(new MediumTank(handler, 100, 100));
				buildingList.remove(0);
			}
		}
	}
	
	
}
