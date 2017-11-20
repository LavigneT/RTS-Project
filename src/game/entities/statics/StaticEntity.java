package game.entities.statics;

import java.awt.Graphics;

import game.Handler;
import game.entities.Entity;

public abstract class StaticEntity extends Entity {

	
	public StaticEntity(Handler handler, float y, float x, int width, int height) {
		super(handler, y, x, width, height);
	}
	
	public abstract void tick();
	
	public abstract void render(Graphics g);
}
