package game.entities.units;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import game.Handler;
import game.gfx.Assets;
import game.tiles.Tile;
public class MediumTank extends Unit{
	
	public static final int default_width = 40, default_height = 40; 

	public MediumTank(Handler handler, float x, float y) {
		super(handler, x, y, default_width, default_height, 30);
		speed = 4;
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
			return Assets.tankUp;
		else if(tempDirection==1)
			return Assets.tankUpLeft;
		else if(tempDirection==2)
			return Assets.tankLeft;
		else if(tempDirection==3)
			return Assets.tankDownLeft;
		else if(tempDirection==4)
			return Assets.tankDown;
		else if(tempDirection==5)
			return Assets.tankDownRight;
		else if(tempDirection==6)
			return Assets.tankRight;
		else
			return Assets.tankUpRight;
	}	
	

}
