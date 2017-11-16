package game.interfaces;

import java.awt.Graphics;

import game.Handler;
import game.gfx.Assets;
import game.inputs.KeyManager;

public class Interfaces {
	
	public static final int interface1_height = 500, interface1_width = 800;
	private Handler handler;

	public Interfaces(Handler handler) {
		this.handler = handler;
	}
	
	public void tick() {
		
	}
	
	public void render(Graphics g) {
		if(handler.getKeyManager().menu)
			g.drawImage(Assets.interface1, handler.getWidth()/2 - interface1_width/2, 
				handler.getHeight()/2 - interface1_height/2, interface1_width, interface1_height, null);
	}
			
}
	
	

