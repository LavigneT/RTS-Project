package game.interfaces;

import java.awt.Graphics;

import javax.swing.plaf.synth.SynthSpinnerUI;

import game.Handler;
import game.gfx.Assets;
import game.inputs.KeyManager;

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
		uiManager.addObject(new ButtonBuild(handler, centerX - 312,centerY - 120, 60, 50, Assets.buttonBuild, new ClickListener() {
			
			//Make the the buildMenu appear
			@Override
			public void onClick() {	
				tankMenu = false;
				buildMenu= true;
				
			}}));
		
		//Tank Button 
		uiManager.addObject(new ButtonBuild(handler, centerX - 250,centerY - 120, 60, 50, Assets.buttonTank, new ClickListener() {
			
			//Make the the tankMenu appear
			@Override
			public void onClick() {	
				buildMenu = false;
				tankMenu = true;
				
			}}));
	}
	
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
	
	

