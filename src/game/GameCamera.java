package game;

import java.awt.Graphics;

public class GameCamera {
	
	private Handler handler;
	private float xOffset, yOffset;
	private int centerX, centerY;
	
	public GameCamera (Handler handler) {
		this.handler = handler;
		centerY = handler.getHeight() / 2;
		centerX = handler.getWidth() /2;
	}
	
	public void tick() {
		centerCamera();
	}
	
	private void centerCamera() {
		if(handler.getKeyManager().up)
			yOffset -= 5;
		if(handler.getKeyManager().down)
			yOffset += 5;
		if(handler.getKeyManager().right)
			xOffset += 5;
		if(handler.getKeyManager().left)
			xOffset -= 5;
		if(xOffset < 0)
			xOffset = 0;
		if(yOffset < 0)
			yOffset = 0;
	}
	
	
	public void render(Graphics g) {
		
	}

	public float getxOffset() {
		return xOffset;
	}

	public float getyOffset() {
		return yOffset;
	}
	
	
	
}
