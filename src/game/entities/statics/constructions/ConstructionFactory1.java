package game.entities.statics.constructions;

import java.awt.Graphics;

import game.Handler;
import game.entities.statics.StaticEntity;
import game.entities.units.Builder1;
import game.gfx.Assets;
import game.tiles.Tile;

public class ConstructionFactory1 extends Construction{
	
	public static int facto_Width = Tile.tile_dimension, facto_Height = Tile.tile_dimension; 
	public static final int default_Time_Creation = 60, idFactoy1 = 21;
	public static int[] emplacementY_X = new int[8];

	public ConstructionFactory1(Handler handler, float y, float x) {
		super(handler, y, x, facto_Width, facto_Height, default_Time_Creation);
		
		
		checkSurroundingEmplacement();
	}

	@Override
	public void tick() {
		
		checkCompletion();
		
		bounds.x = (int)(x - handler.getXOffset());
		bounds.y = (int)(y - handler.getYOffset());
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
			emplacementY_X[0] = (int)(y - 40);
			emplacementY_X[1] = (int)(x + 20 + (Tile.tile_dimension- Builder1.default_width)/2);
			
		} else {
			builderEmplacement[0] = false;
		}
		
		//right Tile can host a builder
		if((int)(x /Tile.tile_dimension) + 1 <= handler.getWorld().getHeight() && 
				handler.getWorld().getSolidMap()[(int)(y /Tile.tile_dimension)][(int)(x / Tile.tile_dimension) + 1] == 0) {
			builderEmplacement[1] = true;
			emplacementY_X[2] = (int)(y + (Tile.tile_dimension- Builder1.default_height)/2);
			emplacementY_X[3] = (int)(x + width + 10);
		} else {
			builderEmplacement[1] = false;
		}
		
		//bottom Tile can host a builder
		if((int)(y /Tile.tile_dimension) + 1 <= handler.getWorld().getWidth() && 
				handler.getWorld().getSolidMap()[(int)(y /Tile.tile_dimension) + 1][(int)(x / Tile.tile_dimension)] == 0) {
			builderEmplacement[2] = true;
			emplacementY_X[4] = (int)(y + 10 + facto_Height);
			emplacementY_X[5] = (int)(x + (Tile.tile_dimension- Builder1.default_width)/2);
		} else {
			builderEmplacement[2] = false;
		}
		
		//left Tile can host a builder
		if((int)(x /Tile.tile_dimension) - 1 >= 0 && 
				handler.getWorld().getSolidMap()[(int)(y /Tile.tile_dimension)][(int)(x / Tile.tile_dimension) - 1] == 0) {
			builderEmplacement[3] = true;
			emplacementY_X[6] = (int)(y + (Tile.tile_dimension- Builder1.default_height)/2);
			emplacementY_X[7] = (int)(x - 10);
		} else {
			builderEmplacement[3] = false;
		}
	}
	
	private void checkCompletion() {
		
	}
	

}
