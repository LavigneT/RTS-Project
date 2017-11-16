package game;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;

import game.pathfinding.AStartV2;

public class Launcher {
	
	//get Screen width
	public static int getScreenWidth() {
		double width2 = 0;
		
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		GraphicsDevice gd = ge.getDefaultScreenDevice();
		width2 = gd.getDefaultConfiguration().getBounds().getWidth();
		int width1 = (int)width2;
		return width1;
	}
	
	//get screen height
	public static int getScreenHeight() {
		double height2 = 0;
		
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		GraphicsDevice gd = ge.getDefaultScreenDevice();
		height2 = gd.getDefaultConfiguration().getBounds().getHeight();
		int height1 = (int)height2;
		return height1;
	}
	
	//----------------------------------------------Window X and Y Center---------------------------------
	


	public static void main(String[] args) {
		Game game = new Game("Tank game", getScreenWidth(), getScreenHeight());
		game.start();
	}
}

