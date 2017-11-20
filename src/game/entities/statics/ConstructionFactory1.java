package game.entities.statics;

import java.awt.Graphics;

import game.Handler;
import game.gfx.Assets;
import game.tiles.Tile;

public class ConstructionFactory1 extends StaticEntity{
	
	public static int facto_Width = Tile.tile_dimension, facto_Height = Tile.tile_dimension; 
	public static final int default_Time_Creation = 60, idFactoy1 = 21;
	private static boolean[] builderEmplacement;

	public ConstructionFactory1(Handler handler, float y, float x) {
		super(handler, y, x, facto_Width, facto_Height, default_Time_Creation);
		builderEmplacement = new boolean[4];
		
		checkSurroundingEmplacement();
	}

	@Override
	public void tick() {
		
		checkCompletion();
	}

	@Override
	public void render(Graphics g) {
		g.drawImage(Assets.buildFactory1, (int)x, (int)y, facto_Width, facto_Height, null);
	}
	
	private void checkSurroundingEmplacement(){
		
		//if up tile can host a builder
		if((int)(y /Tile.tile_dimension) - 1 >= 0 && handler.getWorld().getSolidMap()[(int)(y /Tile.tile_dimension) - 1]
				[(int)(x / Tile.tile_dimension)] == 0) {
			builderEmplacement[0] = true;
		} else {
			builderEmplacement[0] = false;
		}
		
		//right Tile can host a builder
		if((int)(x /Tile.tile_dimension) + 1 <= handler.getWorld().getHeight() && 
				handler.getWorld().getSolidMap()[(int)(y /Tile.tile_dimension)][(int)(x / Tile.tile_dimension) + 1] == 0) {
			builderEmplacement[1] = true;
		} else {
			builderEmplacement[1] = false;
		}
		
		//bottom Tile can host a builder
		if((int)(y /Tile.tile_dimension) + 1 <= handler.getWorld().getWidth() && 
				handler.getWorld().getSolidMap()[(int)(y /Tile.tile_dimension) + 1][(int)(x / Tile.tile_dimension)] == 0) {
			builderEmplacement[2] = true;
		} else {
			builderEmplacement[2] = false;
		}
		
		//left Tile can host a builder
		if((int)(x /Tile.tile_dimension) - 1 >= 0 && 
				handler.getWorld().getSolidMap()[(int)(y /Tile.tile_dimension)][(int)(x / Tile.tile_dimension) - 1] == 0) {
			builderEmplacement[3] = true;
		} else {
			builderEmplacement[3] = false;
		}
	}
	
	private void checkCompletion() {
		
	}
	

}
