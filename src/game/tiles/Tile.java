package game.tiles;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Tile {
	
	public static Tile[] tiles = new Tile[256];
	public static Tile grass1 = new Grass1(0);
	
	
	public static Tile water = new Water1(1);
	
	
	public static int tile_dimension = 64;
	
	private int id;
	private BufferedImage texture;
	
	public Tile(BufferedImage texture, int id) {
		this.id = id;
		this.texture = texture;
		
		tiles[id] = this;
	}
	
	public void render(Graphics g, int x, int y) {
		g.drawImage(texture, x, y, tile_dimension, tile_dimension, null);
	}
	
	public int isSolid() {
		return 0;
	}

}
