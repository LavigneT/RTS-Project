package game.interfaces;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import game.Handler;
import game.gfx.Assets;

public class ButtonBuild extends UIObject{
	
	private BufferedImage image[];
	private ClickListener clicker;

	public ButtonBuild(Handler handler,float x, float y, int width, int height, BufferedImage image[], ClickListener clicker) {
		super(handler, x, y, width, height);
		this.image = image;
		this.clicker = clicker;
	}

	@Override
	public void tick() {
		
	}

	@Override
	public void rende(Graphics g) {
		g.drawImage(image[0],(int) x,(int) y, width, height, null);
		
		if(hovering)
			g.drawImage(image[1] , (int)x, (int)y, width, height, null); 
		//the button remain "pressed" if we set this boolean to true
		if(clicked)
			g.drawImage(image[1] , (int)x, (int)y, width, height, null); 
	}

	@Override
	public void onClick() {
		clicker.onClick();
		
	}

	
	
	
	
}
