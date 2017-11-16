package game.states;

import java.awt.Graphics;

import game.Handler;
import game.world.World;

public abstract class State {
	
	public static State currentState = null;

	public static State getCurrentState() {
		return currentState;
	}

	public static void setCurrentState(State state) {
		currentState = state;
	}
	
	
	protected Handler handler;
	
	public State (Handler handler) {
		this.handler = handler;
	}
	
	public abstract void tick();
	
	public abstract void render(Graphics g);

	
}
