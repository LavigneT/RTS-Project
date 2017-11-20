package game.entities.statics.constructions;

import game.Handler;
import game.entities.statics.StaticEntity;

public abstract class Construction extends StaticEntity{
	
	private int buildingTime;
	protected boolean[] builderEmplacement;

	public Construction(Handler handler, float y, float x, int width, int height, int buildingTime) {
		super(handler, y, x, width, height);
		this.buildingTime = buildingTime;
		builderEmplacement = new boolean[4];
	}

	
	//Getters and setters
	
	public boolean[] getBuilderEmplacement() {
		return builderEmplacement;
	}

	public void setBuilderEmplacement(boolean[] builderEmplacement) {
		this.builderEmplacement = builderEmplacement;
	}
	
	
	
	
}
