package game.entities;

import java.awt.Graphics;


import java.awt.Rectangle;
import java.util.ArrayList;

import game.Handler;

public abstract class Entity {

	protected float x, y;
	protected int width, height;
	protected Handler handler;
	
	protected boolean active = true;
	public static final int default_health = 5;
	protected Rectangle bounds;
	protected int health;
	protected boolean selected, startMoving, calculatePath;
	protected int destinationX, destinationY;
	protected ArrayList<Integer> movementOrder;
	protected int temporaryDestX, temporaryDestY, tempY, tempX, 
	tempDirection;
	protected boolean centerBeforeMove = false, centerY = false, tempoDestinationReached = false
			, lastTile = false, lastMove = false;
	
	public Entity(Handler handler, float y, float x, int width, int height) {
		this.handler =  handler;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		health = default_health;
		
		selected = false;
		bounds = new Rectangle(0, 0, width, height);
	}
	
	public void die() {
		
	}
	
	public abstract void tick();
	
	public abstract void render(Graphics g);

	
	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean select) {
		this.selected = select;
	}


	public float getX() {
		return x;
	}


	public void setX(float x) {
		this.x = x;
	}


	public float getY() {
		return y;
	}


	public void setY(float y) {
		this.y = y;
	}


	public int getHealth() {
		return health;
	}


	public void setHealth(int health) {
		this.health = health;
	}
	
	public int getDestinationX() {
		return destinationX;
	}

	public void setDestinationX(int destinationX) {
		this.destinationX = destinationX;
	}

	public int getDestinationY() {
		return destinationY;
	}

	public void setDestinationY(int destinationY) {
		this.destinationY = destinationY;
	}


	public int getWidth() {
		return width;
	}


	public int getHeight() {
		return height;
	}


	public boolean isCalculatePath() {
		return calculatePath;
	}


	public void setCalculatePath(boolean calculatePath) {
		this.calculatePath = calculatePath;
	}


	public boolean isStartMoving() {
		return startMoving;
	}


	public void setStartMoving(boolean startMoving) {
		this.startMoving = startMoving;
	}


	public ArrayList<Integer> getMovementOrder() {
		return movementOrder;
	}


	public void setMovementOrder(ArrayList<Integer> movementOrder) {
		this.movementOrder = movementOrder;
	}


	public int getTemporaryDestX() {
		return temporaryDestX;
	}


	public void setTemporaryDestX(int temporaryDestX) {
		this.temporaryDestX = temporaryDestX;
	}


	public int getTemporaryDestY() {
		return temporaryDestY;
	}


	public void setTemporaryDestY(int temporaryDestY) {
		this.temporaryDestY = temporaryDestY;
	}


	public int getTempY() {
		return tempY;
	}


	public void setTempY(int tempY) {
		this.tempY = tempY;
	}


	public int getTempX() {
		return tempX;
	}


	public void setTempX(int tempX) {
		this.tempX = tempX;
	}


	public int getTempDirection() {
		return tempDirection;
	}


	public void setTempDirection(int tempDirection) {
		this.tempDirection = tempDirection;
	}


	public boolean isCenterY() {
		return centerY;
	}


	public void setCenterY(boolean centerY) {
		this.centerY = centerY;
	}


	public boolean isTempoDestinationReached() {
		return tempoDestinationReached;
	}


	public void setTempoDestinationReached(boolean tempoDestinationReached) {
		this.tempoDestinationReached = tempoDestinationReached;
	}


	public boolean isLastTile() {
		return lastTile;
	}


	public void setLastTile(boolean lastTile) {
		this.lastTile = lastTile;
	}


	public boolean isLastMove() {
		return lastMove;
	}


	public void setLastMove(boolean lastMove) {
		this.lastMove = lastMove;
	}


	public boolean isCenterBeforeMove() {
		return centerBeforeMove;
	}


	public void setCenterBeforeMove(boolean centerBeforeMove) {
		this.centerBeforeMove = centerBeforeMove;
	}


	public Rectangle getBounds() {
		return bounds;
	}


	
	
	
	
}
