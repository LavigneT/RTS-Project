package game.entities.units;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import game.Handler;
import game.gfx.Assets;

public class Builder1 extends Unit{

	
	public static final int default_width = 40, default_height = 40, default_Time_Creation = 20,
			idBuilder1 = 1; 

	public Builder1(Handler handler, float y, float x) {
		super(handler, y, x, default_width, default_height, default_Time_Creation);
		speed = 5;
	}

	@Override
	public void tick() {
		
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
}
