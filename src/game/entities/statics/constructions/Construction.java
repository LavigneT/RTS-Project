package game.entities.statics.constructions;

import game.Handler;
import game.entities.statics.StaticEntity;

public abstract class Construction extends StaticEntity{
	
	protected int buildingTime, numberOfBuilderOnSite;
	protected boolean[] builderEmplacement;
	protected long lastTime;

	public Construction(Handler handler, float y, float x, int width, int height, int buildingTime) {
		super(handler, y, x, width, height);
		this.buildingTime = buildingTime;
		builderEmplacement = new boolean[4];
	}
	
	
	public void checkCompletion() {
		if(System.currentTimeMillis() - lastTime >= 1000) {
			lastTime = System.currentTimeMillis();
			buildingTime -= numberOfBuilderOnSite;
			if(buildingTime <= 0)
				build();
		}

	}
	
	public abstract void build();
	//Getters and setters
	
	public boolean[] getBuilderEmplacement() {
		return builderEmplacement;
	}

	public void setBuilderEmplacement(boolean[] builderEmplacement) {
		this.builderEmplacement = builderEmplacement;
	}
	
	public int getNumberOfBuilderOnSite() {
		return numberOfBuilderOnSite;
	}

	public void setNumberOfBuilderOnSite(int numberOfBuilderOnSite) {
		this.numberOfBuilderOnSite = numberOfBuilderOnSite;
	}
	
	
}
