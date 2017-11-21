package game.entities.units;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.plaf.synth.SynthSpinnerUI;

import game.Handler;
import game.entities.statics.constructions.Construction;
import game.gfx.Assets;

public class Builder1 extends Unit{
	
	private Construction workOn;
	private int idEmplacement;
	private long lastTime;
	
	public static final int default_width = 40, default_height = 40, default_Time_Creation = 20,
			idBuilder1 = 1; 

	public Builder1(Handler handler, float y, float x) {
		super(handler, y, x, default_width, default_height, default_Time_Creation);
		speed = 5;
	}
	
	/*
	 * 1st : The purpose of this method is to allow the player to relocate a builder and at the same 
	 * time free it's place on construction site and slow down the construction rythm.
	 * 
	 * 
	 *
	 */

	@Override
	public void tick() {
		
		//1st
		if(workOn != null && handler.getWorld().getEntityManager().getPlacementEntities().contains(this)){
			workOn.getBuilderEmplacement()[idEmplacement] = 0;
			System.out.println(workOn.getBuilderEmplacement()[idEmplacement]);
			workOn = null;
		}
		
		if(workOn != null && destinationX == x && destinationY == y) {
			if(System.currentTimeMillis() - lastTime >= 1000) {
				lastTime = System.currentTimeMillis();
				workOn.checkCompletion();
				if(workOn.getBuildingTime() <= 0)
					workOn = null;
			}
		}
		
		if(destinationX != (int)x || destinationY != (int)y) {
			move();

		}
			
		bounds.x= (int)(x-handler.getXOffset());
		bounds.y = (int)(y-handler.getYOffset());
	}
	
	

	@Override
	public void render(Graphics g) {
		g.drawImage(getCurrentFrame(),(int) (x - handler.getXOffset()),
				(int) (y - handler.getYOffset()), width, height, null);
	}
	
	private BufferedImage getCurrentFrame() {
		if(tempDirection==0)
			return Assets.builder1Up;
		else if(tempDirection==1)
			return Assets.builder1UpLeft;
		else if(tempDirection==2)
			return Assets.builder1Left;
		else if(tempDirection==3)
			return Assets.builder1DownLeft;
		else if(tempDirection==4)
			return Assets.builder1Down;
		else if(tempDirection==5)
			return Assets.builder1DownRight;
		else if(tempDirection==6)
			return Assets.builder1Right;
		else
			return Assets.builder1UpRight;
	}

	public Construction getWorkOn() {
		return workOn;
	}

	public void setWorkOn(Construction workOn) {
		this.workOn = workOn;
	}

	public int getIdEmplacement() {
		return idEmplacement;
	}

	public void setIdEmplacement(int idEmplacement) {
		this.idEmplacement = idEmplacement;
	}	
	
	public void clearWorkOn() {
		workOn.getBuilderEmplacement()[idEmplacement] = 0;
		System.out.println(workOn.getBuilderEmplacement()[idEmplacement] + " methog");
		workOn = null;
	}
	
	
}
