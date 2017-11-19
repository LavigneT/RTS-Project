package game.interfaces;

import java.awt.Graphics;

import game.Handler;
import game.entities.Creator.EntityCreator;
import game.entities.units.MediumTank;
import game.gfx.Assets;

public class Interfaces {
	
	public static final int interface1_height = 400, interface1_width = 640;
	private Handler handler;
	private UIManager uiManager;
	public static int centerX, centerY;
	private boolean buildMenu, tankMenu;

	public Interfaces(Handler handler) {
		this.handler = handler;
		uiManager = new UIManager(handler);
		handler.getMouseManager().setUiManager(uiManager);
		handler.setInter(this);
		
		centerX = handler.getWidth()/2;
		centerY = handler.getHeight()/2;
		
		//Build Button
		uiManager.addObject(new ButtonBuild(handler, centerX - 312,centerY - 118, 60, 50, Assets.buttonBuild, new ClickListener() {
			
			//Make the the buildMenu appear
			@Override
			public void onClick() {	
				tankMenu = false;
				buildMenu= true;
				
			}}));
		
		//Tank Button 
		uiManager.addObject(new ButtonBuild(handler, centerX - 248,centerY - 118, 60, 50, Assets.buttonTank, new ClickListener() {
			
			//Make the the tankMenu appear
			@Override
			public void onClick() {	
				buildMenu = false;
				tankMenu = true;
				
			}}));
		
		//Hammer button on the bottom right side, send order to build something
		uiManager.addObject(new ButtonBuild(handler, centerX + 242,centerY + 124, 46, 36, Assets.hammer, new ClickListener() {
			
			/*
			 *When the button is clicked, the order to build are sent to the entityCreator 
			 * for each order we create a new int[] composed of the id of the unit and its time to build
			 *that's why i made a for(), to create the number order require
			 * then we tick one time the entityCreator and reset the numberToBuild to zero
			 * 
			 */
			@Override
			public void onClick() {	
				if(tankMenu) {
					int tankToBuild = handler.getUiManager().getInterfaces().get(0).getNumberToBuild();
					for(int x = 0; x < tankToBuild; x++) {
						handler.getEntityCreator().addOrder(new int[] {MediumTank.idMediumTank, MediumTank.default_Time_Creation});
					}
					
					handler.getEntityCreator().tick();
					handler.getUiManager().getInterfaces().get(0).setNumberToBuild(0);
				}
				
			}}));
		
		//Stop button on the bottom right side, used to cancel a build order
		uiManager.addObject(new ButtonBuild(handler, centerX + 196,centerY + 124, 46, 36, Assets.stop, new ClickListener() {
			
			//Make the the tankMenu appear
			@Override
			public void onClick() {	
				
			}}));
	}
	
	//-------------------------------------Tick / render-----------------------------------------
	
	public void tick() {
		if(handler.getKeyManager().menu) {
			uiManager.tick();
		}
	}
	
	//don't forget to render UIManager after menu, or nothing buttons wil be display behind the interface
	public void render(Graphics g) {
		if(handler.getKeyManager().menu) {
			g.drawImage(Assets.interface1, handler.getWidth()/2 - interface1_width/2, 
					handler.getHeight()/2 - interface1_height/2, interface1_width, interface1_height, null);
			uiManager.render(g);
		}

	}

	public boolean isBuildMenu() {
		return buildMenu;
	}

	public void setBuildMenu(boolean buildMenu) {
		this.buildMenu = buildMenu;
	}

	public boolean isTankMenu() {
		return tankMenu;
	}

	public void setTankMenu(boolean tankMenu) {
		this.tankMenu = tankMenu;
	}
			
	
	
	
}
	
	

