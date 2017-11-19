package game.world;

import java.awt.Graphics;


import game.Handler;
import game.entities.EntityManager;
import game.entities.statics.Factory1;
import game.entities.units.MediumTank;
import game.gfx.Animation;
import game.gfx.Assets;
import game.interfaces.BuildingInterface;
import game.interfaces.MainMenuFrame;
import game.interfaces.UIManager;
import game.tiles.Tile;
import game.utils.Utils;

public class World {

	private Handler handler;
	private int width, height, spawnX, spawnY;
	private int[][] tilesId, solidMap; 
	private EntityManager entityManager;
	private MainMenuFrame interfaces;
	private UIManager uiManager;
	
	public World(Handler handler, String path) {
		this.handler = handler;
		handler.setWorld(this);
		
		loadWorld(path);
		createSolidMap();
		
		uiManager = new UIManager(handler);
		entityManager = new EntityManager(handler);
	}
	
	public void tick() {
		entityManager.tick();
		uiManager.tick();
		
	}
	
	public void render(Graphics g) {
		int xStart = (int) Math.max(0, handler.getXOffset() / Tile.tile_dimension);
		int xEnd = (int) Math.min(width, (handler.getXOffset() + handler.getWidth())/Tile.tile_dimension + 1);
		int yStart = (int) Math.max(0, handler.getYOffset()/Tile.tile_dimension);
		int yEnd = (int) Math.min(height, (handler.getYOffset() + handler.getHeight())/Tile.tile_dimension + 1);
		
		for(int y = yStart; y < yEnd; y++) {
			for(int x = xStart; x < xEnd; x++) {
				getTile(y, x).render(g, (int) (x * Tile.tile_dimension - handler.getXOffset()),
						(int) (y * Tile.tile_dimension - handler.getYOffset()));
			}
		}
		entityManager.render(g);
		uiManager.render(g);
	}
	
	
	public Tile getTile(int y, int x) {
		if(x < 0 || y < 0 || x > width || y > height)
			return Tile.water;
		Tile t = Tile.tiles[tilesId[y][x]];
		if(t==null) {
			return Tile.water;
		}
		return t;
	}
	
	private void loadWorld(String path) {
		String file = Utils.loadFileAsString(path);
		String[] tokens  = file.split("\\s+");
		width = Utils.parseInt(tokens[0]);
		height = Utils.parseInt(tokens[1]);
		spawnX = Utils.parseInt(tokens[2]);
		spawnY = Utils.parseInt(tokens[3]);
		
		tilesId = new int[height][width];
		solidMap = new int[height][width];
		for(int y = 0; y < height; y++) {
			for(int x = 0; x <width; x++) {
				tilesId[y][x] = Utils.parseInt(tokens[(x + y * width) + 4]);
			}
		}
	}
	
	private void createSolidMap() {
		for(int y = 0; y < height; y++) {
			for(int x = 0; x <width; x++) {
				solidMap[y][x] = getTile(y, x).isSolid();
			}
		}
	}


	public EntityManager getEntityManager() {
		return entityManager;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public int[][] getSolidMap() {
		return solidMap;
	}
	
	
	
}
