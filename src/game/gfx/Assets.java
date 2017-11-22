package game.gfx;

import java.awt.image.BufferedImage;

public class Assets {

	public static BufferedImage[] grass; 
	public static BufferedImage[] water;
	public static BufferedImage[] factory1;
	public static BufferedImage[] buttonBuild, buttonTank, minusButton, plusButton, numbers, hammer, stop;
	
	public static BufferedImage tankUp, tankUpRight, tankRight, tankDownRight, tankDown, tankDownLeft,
	tankLeft, tankUpLeft;
	public static BufferedImage builder1Up, builder1UpRight, builder1Right, builder1DownRight, builder1Down, builder1DownLeft,
	builder1Left, builder1UpLeft;
	public static BufferedImage interface1, textField1, arrowRight, arrowLeft;
	public static BufferedImage buildFactory1;
	
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
		tankRight = tankSheet.crop(mediumTank_dimension*2 + 1, mediumTank_dimension, mediumTank_dimension - 1, mediumTank_dimension);
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
		
		buildFactory1 = facto1sheet.crop(181, 87, 38, 34);
		
		SpriteSheet interface1Sheet = new SpriteSheet(ImageLoader.loadImage("/textures/interface1.bmp"));
		
		interface1 = interface1Sheet.crop(0, 0, 320, 200);
		
		SpriteSheet buttons = new SpriteSheet(ImageLoader.loadImage("/textures/button.bmp"));
		
		buttonBuild =  new BufferedImage[2];
		
		buttonBuild[0] = buttons.crop(35, 14, 32, 27);
		buttonBuild[1] = buttons.crop(99, 14, 32, 27);
		
		buttonTank =  new BufferedImage[2];
		
		buttonTank[0] = buttons.crop(35, 68, 32, 27);
		buttonTank[1] = buttons.crop(99, 68, 32, 27);
		
		
		SpriteSheet BuyButtonSheet = new SpriteSheet(ImageLoader.loadImage("/textures/BuyButton.bmp"));
		
		textField1 = BuyButtonSheet.crop(202, 142, 75, 12);
		
		minusButton =  new BufferedImage[2];
		
		minusButton[0] = BuyButtonSheet.crop(20, 92, 40, 18);
		minusButton[1] = BuyButtonSheet.crop(66, 92, 40, 18);
		
		plusButton =  new BufferedImage[2];
		
		plusButton[0] = BuyButtonSheet.crop(20, 115, 40, 18);
		plusButton[1] = BuyButtonSheet.crop(66, 115, 40, 18);
		
		SpriteSheet numbersSheet = new SpriteSheet(ImageLoader.loadImage("/textures/numbers.png"));
		
		numbers = new BufferedImage[10];
		
		numbers[0] = numbersSheet.crop(0, 0, 62, 101);
		numbers[1] = numbersSheet.crop(62, 0, 43, 101);
		numbers[2] = numbersSheet.crop(105, 0, 69, 101);
		numbers[3] = numbersSheet.crop(174, 0, 63, 101);
		numbers[4] = numbersSheet.crop(237, 0, 63, 101);
		
		numbers[5] = numbersSheet.crop(300, 0, 67, 101);
		numbers[6] = numbersSheet.crop(367, 0, 63, 101);
		numbers[7] = numbersSheet.crop(430, 0, 63, 101);
		numbers[8] = numbersSheet.crop(0, 116, 65, 101);
		numbers[9] = numbersSheet.crop(65,116, 64, 101);
		
		SpriteSheet bottomRightButtonsSheet = new SpriteSheet(ImageLoader.loadImage("/textures/buttonBottomRight.bmp"));
		
		hammer = new BufferedImage[2];
		
		hammer[0] = bottomRightButtonsSheet.crop(18, 37, 23, 18);
		hammer[1] = bottomRightButtonsSheet.crop(64, 37, 23, 18);
		
		stop = new BufferedImage[2];
		
		stop[0] = bottomRightButtonsSheet.crop(110, 73, 23, 18);
		stop[1] = bottomRightButtonsSheet.crop(156, 73, 23, 18);
		
		SpriteSheet builder1Sheet = new SpriteSheet(ImageLoader.loadImage("/textures/Builder1.png"));
		
		builder1UpLeft = builder1Sheet.crop(1, 0, mediumTank_dimension, mediumTank_dimension);
		builder1Up = builder1Sheet.crop(mediumTank_dimension, 0, mediumTank_dimension, mediumTank_dimension);
		builder1UpRight =builder1Sheet.crop(mediumTank_dimension*2, 0, mediumTank_dimension, mediumTank_dimension);
		builder1Left = builder1Sheet.crop(1, mediumTank_dimension, mediumTank_dimension, mediumTank_dimension);
		builder1Right = builder1Sheet.crop(mediumTank_dimension*2, mediumTank_dimension, mediumTank_dimension, mediumTank_dimension);
		builder1DownLeft = builder1Sheet.crop(1, mediumTank_dimension*2, mediumTank_dimension, mediumTank_dimension);
		builder1Down = builder1Sheet.crop(mediumTank_dimension, mediumTank_dimension*2, mediumTank_dimension, mediumTank_dimension);
		builder1DownRight = builder1Sheet.crop(mediumTank_dimension*2, mediumTank_dimension*2, mediumTank_dimension, mediumTank_dimension);
		
		SpriteSheet arrowSheet = new SpriteSheet(ImageLoader.loadImage("/textures/arrow.png"));
		
		arrowRight = arrowSheet.crop(0, 0, 192, 182);
		arrowLeft = arrowSheet.crop(192, 0, 192, 182);
		
	}
	
	
	
}
