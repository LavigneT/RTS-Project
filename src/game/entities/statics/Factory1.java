package game.entities.statics;

import java.awt.Graphics;

import game.Handler;
import game.gfx.Animation;
import game.gfx.Assets;
import game.tiles.Tile;

public class Factory1 extends StaticEntity{
	
	public static int facto_Width = Tile.tile_dimension, facto_Height = Tile.tile_dimension;
	private Animation facto;

	public Factory1(Handler handler, float x, float y) {
		super(handler, x, y, facto_Width, facto_Height, 60);
		
		//change the state of the tile where the factory has been placed to solid
		handler.getWorld().getSolidMap()[(int)(y /Tile.tile_dimension)]
				[(int)(x /Tile.tile_dimension)] = 1;
		
		facto = new Animation(Assets.factory1, 1000);
	}

	@Override
	public void tick() {
		facto.tick();
		
		bounds.x = (int)(x - handler.getXOffset());
		bounds.y = (int)(y - handler.getYOffset());
	}

	@Override
	public void render(Graphics g) {
		g.drawImage(facto.getCurrentFrame(), (int)(x - handler.getXOffset()), 
				(int)(y - handler.getYOffset()), facto_Width,facto_Height, null);
		
	}

}
