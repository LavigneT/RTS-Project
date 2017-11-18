package game.entities.statics;

import java.awt.Graphics;

import game.Handler;
import game.entities.Entity;

public abstract class StaticEntity extends Entity {

	
	public StaticEntity(Handler handler, float x, float y, int width, int height, int buildingTime) {
		super(handler, x, y, width, height, buildingTime);
	}
	
	public abstract void tick();
	
	public abstract void render(Graphics g);
}
