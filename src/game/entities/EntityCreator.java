package game.entities;

import java.util.ArrayList;

import game.Handler;

public class EntityCreator {
	
	private Handler handler;
	private ArrayList<Entity> factories = new ArrayList<>();
	
	public EntityCreator(Handler handler) {
		this.handler = handler;
	}
	
	
	//Getters and setters
	
	public ArrayList<Entity> getFactories() {
		return factories;
	}
	
	public void addFactory(Entity e) {
		factories.add(e);
	}
	
	
	
	
}
