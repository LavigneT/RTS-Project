package game.interfaces;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import game.Handler;
import game.gfx.Assets;

public class BuildTankdingInterface extends BuildInterface{
	
	private Handler handler;
	private int numberTankChosen = 0;
	
	public BuildTankdingInterface(Handler handler) {
		super(handler);
		this.handler = handler;
		
		//button minus normal
		buttons.add(0, new ButtonBuild(handler, handler.getWidth()/2 - 300, handler.getHeight()/2 + 120, 
				80, 36, Assets.minusButton, new ClickListener() {

					@Override
					public void onClick() {
						numberTankChosen --;
						if(numberTankChosen < 0)
							numberTankChosen = 0;
					}
		}));
		
		buttons.add(0, new ButtonBuild(handler, handler.getWidth()/2 + 40, handler.getHeight()/2 + 120, 
				80, 36, Assets.plusButton, new ClickListener() {

					@Override
					public void onClick() {
						numberTankChosen ++;
						if(numberTankChosen > 20)
							numberTankChosen = 20;
					}
		}));
		
	}
	
	
	
	
	public void tick() {
		
	}

	public void render(Graphics g) {
		//tank image in the top right corner
		g.drawImage(Assets.tankRight, handler.getWidth()/2 + 190, handler.getHeight()/2 - 192, 100, 100, null);
		
		//textField
		g.drawImage(Assets.textField1, handler.getWidth()/2 - 220, handler.getHeight()/2 + 120, 
				258, 66,null);
		
		
		//the following two images display the number of unit the player wants to build
		if(numberTankChosen >9)
			g.drawImage(firstNumber(numberTankChosen), handler.getWidth()/2 - 110, handler.getHeight()/2 + 130, 
					20, 40, null);
		
		g.drawImage(secondNumber(numberTankChosen),handler.getWidth()/2 - 90, handler.getHeight()/2 + 130, 
				20, 40, null);
	}
	
	//return the first number of the chosen number of unit to build if it is < 10
	private BufferedImage firstNumber(int number) {
		return Assets.numbers[Character.getNumericValue(Integer.toString(number).charAt(0))];
	}
	
	//return the second number of the chosen number of unit to build
	private BufferedImage secondNumber(int number) {
		return Assets.numbers[numberTankChosen%10];
	}
	
	
	
}
