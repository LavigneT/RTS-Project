package game.interfaces;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;

import game.Handler;

public abstract class UIObject {
	
	protected float x, y;
	protected int width, height;
	protected Rectangle bounds;
	protected boolean hovering = false, clicked = false; 
	protected Handler handler;
	
	public UIObject (Handler handler, float x, float y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		bounds  = new Rectangle((int)x, (int)y, width,  height);
		handler.setObj(this);
	}

	public abstract void tick();
	
	public abstract void rende(Graphics g);
	
	public abstract void onClick();
	
	public void onMouseMove(MouseEvent e) {
		if(bounds.contains(e.getX(), e.getY())) {
			hovering = true;
		}
			
		else 
			hovering = false; 
	}
	
	public void onMouseReleased(MouseEvent e) {
		
		if(bounds.contains(e.getX(), e.getY()))
			onClick();
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

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public boolean isHover() {
		return hovering;
	}

	public void setHover(boolean hovering) {
		this.hovering = hovering;
	}

	public boolean isClicked() {
		return clicked;
	}

	public void setClicked(boolean clicked) {
		this.clicked = clicked;
	}

	
	
	
	
}
