package game.interfaces;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import game.gfx.Assets;

public class ButtonBuild extends UIObject{
	
	private BufferedImage image;
	private ClickListener clicker;

	public ButtonBuild(float x, float y, int width, int height, BufferedImage image, ClickListener clicker) {
		super(x, y, width, height);
		this.image = image;
		this.clicker = clicker;
	}

	@Override
	public void tick() {
		
	}

	@Override
	public void rende(Graphics g) {
		if(hovering)
			g.drawImage(image , (int)x, (int)y, width, height, null); 
	}

	@Override
	public void onClick() {
		clicker.onClick();
		
	}

	
	
	
}
