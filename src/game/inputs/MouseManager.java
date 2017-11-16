package game.inputs;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import game.Handler;
import game.entities.Entity;

public class MouseManager implements MouseListener, MouseMotionListener{
	
	private Rectangle selectRect;
	
	private int mouseX, mouseY, startX, startY, x, y, width, height, destinationX, destinationY,
	centerScreenX, centerScreenY;
	private Handler handler;
	private boolean drawRect = false, timeToSelect = false, unitSelected = false, defocus = false
			, saveLocation = false;
	
	public MouseManager() {
		
	}
	

	@Override
	public void mouseDragged(MouseEvent e) {
		if(drawRect) {
			mouseX = e.getX();
			mouseY = e.getY();
			if(startX < mouseX) {
				x = startX;
				width = mouseX - startX;
			} else {
				x = mouseX;
				width = startX - mouseX;
			}
			if(startY < mouseY) {
				y = startY;
				height = mouseY - startY;
			} else {
				y = mouseY;
				height = startY - mouseY;
			}
		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		mouseX = e.getX();
		mouseY = e.getY();
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		
		for(Entity ent : handler.getWorld().getEntityManager().getEntities()) {
			if(ent.getBounds().contains(e.getPoint())) {
				if(handler.getKeyManager().menu)
					handler.getKeyManager().menu = false;
				else
					handler.getKeyManager().menu = true;
			}
		}
		
		
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if(e.getButton() == MouseEvent.BUTTON1) {
			drawRect = true;
			startX = mouseX;
			startY = mouseY;
		}
		
		//save coordinates of the wished location
		if(e.getButton() == MouseEvent.BUTTON3 && unitSelected) {
			destinationX = mouseX;
			destinationY = mouseY;
			saveLocation =true;
			defocus = true;
		} else if(e.getButton() == MouseEvent.BUTTON1 && unitSelected){
			defocus = true;
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if(e.getButton() == MouseEvent.BUTTON1) {
			timeToSelect = true;
			selectRect = new Rectangle(x, y, width, height);
			x = 0; y = 0; width = 0; height = 0;
			drawRect = false;
		}

		
	}
	
	public void tick() {
	}
	
	public void render(Graphics g) {
		if(drawRect)
			drawRect(g);
	}
	
	//drawn a rect on the screen to help th player select units
	private void drawRect(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.setStroke(new BasicStroke(5));
		
		g2.setColor(Color.black);
		g2.drawRect(x, y, width, height);
	}


	
	
	//getters and setters
	
	public Rectangle getSelectRect() {
		return selectRect;
	}


	public boolean isTimeToSelect() {
		return timeToSelect;
	}


	public void setTimeToSelect(boolean timeToSelect) {
		this.timeToSelect = timeToSelect;
	}


	public int getDestinationX() {
		return destinationX;
	}


	public int getDestinationY() {
		return destinationY;
	}


	public boolean isUnitSelected() {
		return unitSelected;
	}


	public void setUnitSelected(boolean unitSelected) {
		this.unitSelected = unitSelected;
	}


	public boolean isDefocus() {
		return defocus;
	}


	public void setDefocus(boolean defocus) {
		this.defocus = defocus;
	}


	public boolean isSaveLocation() {
		return saveLocation;
	}


	public void setSaveLocation(boolean saveLocation) {
		this.saveLocation = saveLocation;
	}


	public void setHandler(Handler handler, int halfWidth, int halfHeight) {
		this.handler = handler;
		centerScreenX = halfWidth;
		centerScreenY = halfHeight;
	}
	
	
	
	
	
}
