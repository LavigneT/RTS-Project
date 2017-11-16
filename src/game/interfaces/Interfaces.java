package game.interfaces;

import java.awt.Graphics;

import game.Handler;
import game.gfx.Assets;
import game.inputs.KeyManager;

public class Interfaces {
	
	public static final int interface1_height = 400, interface1_width = 640;
	private Handler handler;
	private UIManager uiManager;
	public static int centerX, centerY;

	public Interfaces(Handler handler) {
		this.handler = handler;
		uiManager = new UIManager(handler);
		
		centerX = handler.getWidth()/2;
		centerY = handler.getHeight()/2;
		
		uiManager.addObject(new ButtonBuild(centerX,centerY, 60, 50, Assets.buttonBuild, new ClickListener() {

			@Override
			public void onClick() {
				
				
			}}));
		
	}
	
	public void tick() {
		if(handler.getKeyManager().menu) {
			uiManager.tick();
		}
			
	}
	
	public void render(Graphics g) {
		if(handler.getKeyManager().menu) {
			uiManager.render(g);
			g.drawImage(Assets.interface1, handler.getWidth()/2 - interface1_width/2, 
					handler.getHeight()/2 - interface1_height/2, interface1_width, interface1_height, null);
		}

	}
			
}
	
	

