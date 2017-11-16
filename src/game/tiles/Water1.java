package game.tiles;

import java.awt.image.BufferedImage;

import game.gfx.Assets;

public class Water1 extends Tile{

	public Water1(int id) {
		super(Assets.water[0], id);
	}
	
	@Override
	public int isSolid() {
		return 1;
	}
	
}
