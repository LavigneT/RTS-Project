package game.gfx;

import java.awt.image.BufferedImage;

public class Animation {
	
	private int speed, index;
	private BufferedImage[] frames;
	private long lastTime, timer;
	
	
	public Animation(BufferedImage[] frames, int speed) {
		this.frames = frames;
		this.speed = speed;
		index = 0;
	}
	
	public void tick(){
		calculateIndex();
	}
	
	
	private void calculateIndex() {
		timer += System.nanoTime() - lastTime;
		if(timer > speed) {
			index++;
			lastTime = System.nanoTime();
			
			if(index >= frames.length)
				index=0;
		}
	}
	
	public BufferedImage getCurrentFrame() {
		return frames[index];
	}
	
	
}
