package game.entities.statics.constructions;

import game.Handler;
import game.entities.statics.StaticEntity;

public abstract class Construction extends StaticEntity{
	
	protected int buildingTime, numberOfBuilderOnSite;
	protected long lastTime;
	protected int[] emplacementY_X, builderEmplacement;	
	
	

	public Construction(Handler handler, float y, float x, int width, int height, int buildingTime) {
		super(handler, y, x, width, height);
		this.buildingTime = buildingTime;
		builderEmplacement = new int[4];
		emplacementY_X = new int[8];
	}
	
	
	public void checkCompletion() {
		
		buildingTime--;
			
		if(buildingTime <= 0)				
			build();
		}
	
	public abstract void build();
	//Getters and setters
	
	public int[] getBuilderEmplacement() {
		return builderEmplacement;
	}

	public void setBuilderEmplacement(int[] builderEmplacement) {
		this.builderEmplacement = builderEmplacement;
	}
	
	public int getNumberOfBuilderOnSite() {
		return numberOfBuilderOnSite;
	}

	public void setNumberOfBuilderOnSite(int numberOfBuilderOnSite) {
		this.numberOfBuilderOnSite = numberOfBuilderOnSite;
	}
	
	public int[] getEmplacementY_X() {
		return emplacementY_X;
	}


	public int getBuildingTime() {
		return buildingTime;
	}


	public void setBuildingTime(int buildingTime) {
		this.buildingTime = buildingTime;
	}
	
	
}
