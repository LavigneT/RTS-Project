package game.interfaces;

import java.awt.Graphics;


import game.Handler;
import game.entities.statics.Factory1;
import game.entities.units.MediumTank;
import game.gfx.Assets;

public class MainMenuFrame {
	
	public static final int interface1_height = 400, interface1_width = 640;
	private Handler handler;
	private UIManager uiManager;
	public static int centerX, centerY;
	private boolean buildMenu = false, tankMenu = false;
	
	/*
	 * This class contain buttons that doesn't change when menu switch
	 * It also display the whole frame on the screen by checking in the keyManager if the menu should
	 * be display
	 * It also active / deactivate the different sub-menus (tank building, build building)
	 *For now :
	 * - build button (just set clicked, render in BuildingInterface)
	 * - tank button (just set clicked, render in BuildingInterface)
	 * - hammer button (to send orders, see below)
	 * - hand/stop button (nothing for  now)
	 */

	public MainMenuFrame(Handler handler, UIManager uiManager) {
		this.handler = handler;
		this.uiManager = uiManager;
		handler.setMainMenuFrame(this);
		
		
		centerX = handler.getWidth()/2;
		centerY = handler.getHeight()/2;
		
		//Build Button
		uiManager.addMainFrameObjects(new ButtonBuild(handler, centerX - 312,centerY - 118, 60, 50, Assets.buttonBuild, new ClickListener() {

			//Make the the buildMenu appear
			@Override
			public void onClick() {	
				tankMenu = false;
				buildMenu= true;
			}}));
		
		//Tank Button 
		uiManager.addMainFrameObjects(new ButtonBuild(handler, centerX - 248,centerY - 118, 60, 50, Assets.buttonTank, new ClickListener() {
			
			//Make the the tankMenu appear
			@Override
			public void onClick() {	
				buildMenu = false;
				tankMenu = true;
				
			}}));
		
		//Hammer button on the bottom right side, send order to build something
		uiManager.addMainFrameObjects(new ButtonBuild(handler, centerX + 242,centerY + 124, 46, 36, Assets.hammer, new ClickListener() {
			
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
					sendOrderToBuildTank();
				} else if(buildMenu) {
					sendOrderToBuildBuilding();
				}
				
			}}));
		
		//Stop button on the bottom right side, used to cancel a build order
		uiManager.addMainFrameObjects(new ButtonBuild(handler, centerX + 196,centerY + 124, 46, 36, Assets.stop, new ClickListener() {
			
			//Make the the tankMenu appear
			@Override
			public void onClick() {	
				
			}}));
	}
	
	//-------------------------------------Tick / render-----------------------------------------
	
	public void tick() {
		
	}
	
	//don't forget to render UIManager after menu, or nothing buttons wil be display behind the interface
	public void render(Graphics g) {
		g.drawImage(Assets.interface1, handler.getWidth()/2 - interface1_width/2, 
				handler.getHeight()/2 - interface1_height/2, interface1_width, interface1_height, null);

	}
	
	private void sendOrderToBuildTank() {
		//.getInterfaces().get(0) refer to BuildingInterface
		int tankToBuild = handler.getUiManager().getInterfaces().get(0).getNumberToBuild();
		//If we have medium tank to build, send the number of array require composed of id and timeToBuild
		for(int x = 0; x < tankToBuild; x++) {
			handler.getUnitCreator().addOrder(new int[] {MediumTank.idMediumTank, MediumTank.default_Time_Creation});
		}
		
		handler.getUnitCreator().tick();
		uiManager.getInterfaces().get(0).setNumberToBuild(0);
	}
	
	private void sendOrderToBuildBuilding() {
		uiManager.setBuildingCreatorActive(true);
		uiManager.setTimeWhenClicked(System.currentTimeMillis());
		
		//.getInterfaces().get(0) refer to BuildingInterface where number are set
		int factory1ToBuild = handler.getUiManager().getInterfaces().get(0).getNumberToBuild();
		//If we have factories1 to build, send the number of array require composed of id and timeToBuild
		for(int x = 0; x < factory1ToBuild; x++) {
			handler.getUiManager().getBuildingCreator().addOrder(new int[] {Factory1.idFactoy1, Factory1.default_Time_Creation});
		}
		uiManager.getInterfaces().get(0).setNumberToBuild(0);
		handler.getKeyManager().menu = false;
	}
	
	//Getters and setters

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
	
	

