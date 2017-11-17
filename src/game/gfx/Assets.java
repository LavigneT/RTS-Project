package game.gfx;

import java.awt.image.BufferedImage;

public class Assets {

	public static BufferedImage[] grass; 
	public static BufferedImage[] water;
	public static BufferedImage[] factory1;
	
	public static BufferedImage tankUp, tankUpRight, tankRight, tankDownRight, tankDown, tankDownLeft,
	tankLeft, tankUpLeft;
	public static BufferedImage interface1, buttonBuild, buttonTank;
	
	public static int tile_dimension = 20, mediumTank_dimension = 20, facto1_height = 42, facto1_width = 38;
	
	public static void init() {
		
		SpriteSheet groundSheet = new SpriteSheet(ImageLoader.loadImage("/textures/grass.bmp"));
		grass = new BufferedImage[13];
		
		grass[0] = groundSheet.crop(0, 1, tile_dimension, tile_dimension);
		grass[2] = groundSheet.crop(tile_dimension *2, 1, tile_dimension, tile_dimension);
		grass[3] = groundSheet.crop(tile_dimension * 4, 1, tile_dimension, tile_dimension);
		
		SpriteSheet waterSheet = new SpriteSheet(ImageLoader.loadImage("/textures/water.bmp"));
		water = new BufferedImage[10];
		
		water[0] = waterSheet.crop(0, 1, tile_dimension, tile_dimension);
		
		SpriteSheet tankSheet = new SpriteSheet(ImageLoader.loadImage("/textures/tank.png"));
		
		tankDownRight = tankSheet.crop(2, 0, mediumTank_dimension, mediumTank_dimension);
		tankDown = tankSheet.crop(mediumTank_dimension +1, 0, mediumTank_dimension , mediumTank_dimension);
		tankDownLeft = tankSheet.crop(mediumTank_dimension*2+1, 0, mediumTank_dimension, mediumTank_dimension);
		tankLeft = tankSheet.crop(1, mediumTank_dimension, mediumTank_dimension, mediumTank_dimension);
		tankRight = tankSheet.crop(mediumTank_dimension*2, mediumTank_dimension, mediumTank_dimension, mediumTank_dimension);
		tankUpRight = tankSheet.crop(1, mediumTank_dimension*2, mediumTank_dimension, mediumTank_dimension);
		tankUp= tankSheet.crop(mediumTank_dimension+1, 0, mediumTank_dimension, mediumTank_dimension);
		tankUpLeft = tankSheet.crop(mediumTank_dimension*2+1, 0, mediumTank_dimension, mediumTank_dimension);
		
		SpriteSheet facto1sheet = new SpriteSheet(ImageLoader.loadImage("/textures/factory1.png"));
		factory1 = new BufferedImage[11];
		
		factory1[0] = facto1sheet.crop(1, 19, facto1_width, facto1_height);
		factory1[1] = facto1sheet.crop(facto1_width + 3, 19, facto1_width, facto1_height);
		factory1[2] = facto1sheet.crop(facto1_width * 2 + 6, 19, facto1_width, facto1_height);
		factory1[3] = facto1sheet.crop(facto1_width* 3 + 7, 19, facto1_width, facto1_height);
		factory1[4] = facto1sheet.crop(facto1_width*4 + 9, 19, facto1_width, facto1_height);
		factory1[5] = facto1sheet.crop(facto1_width * 5 + 11, 19, facto1_width, facto1_height);
		factory1[6] = facto1sheet.crop(facto1_width * 6 + 12, 19, facto1_width, facto1_height);
		
		factory1[7] = facto1sheet.crop(1, 79, facto1_width, facto1_height);
		factory1[8] = facto1sheet.crop(facto1_width + 3, 79, facto1_width, facto1_height);
		factory1[9] = facto1sheet.crop(facto1_width * 2 + 5, 79, facto1_width, facto1_height);		
		factory1[10] = facto1sheet.crop(facto1_width * 3 + 7, 79, facto1_width, facto1_height);
		
		SpriteSheet interface1Sheet = new SpriteSheet(ImageLoader.loadImage("/textures/interface1.bmp"));
		
		interface1 = interface1Sheet.crop(0, 0, 320, 200);
		
		SpriteSheet buttons = new SpriteSheet(ImageLoader.loadImage("/textures/button.png"));
		
		buttonBuild = buttons.crop(99, 14, 32, 27);
		buttonTank = buttons.crop(99, 68, 32, 27);
		
		
	}
	
	
	
}
