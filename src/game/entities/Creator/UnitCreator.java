package game.entities.Creator;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import game.Handler;
import game.entities.EntityManager;
import game.entities.statics.Factory1;



public class UnitCreator {
	
	protected Handler handler;
	private EntityManager entityManager;
	private ArrayList<Factory1> factories1 = new ArrayList<>();
	private List<int[]> orders = new ArrayList<>();
	
	/*
	 * This class send order to build unit to factory by sending them int[] composed of id + timeToBuild
	 * of the Unit. Build is manage by factories, nothing is build here.
	 * This class is only tick one time when the hammer is clicked for build unit.
	 * It instantiate in EntityManager, and transmit to handler in the same class
	 * 
	 */
	
	public UnitCreator(Handler handler, EntityManager entityManager) {
		this.handler = handler;
		this.entityManager = entityManager;
		
	}
	
	//this method is ticked when the hammer button is clicked in the interfaces class
	public void tick() {
		sendOrderToFactories();
	}
	
	public void render(Graphics g) {
		
	}
	
	//send int[] containing the building orders to factories
	private void sendOrderToFactories() {
		
		while(!orders.isEmpty()){
			for(Factory1 f : factories1) {
				if(!orders.isEmpty()) {
					f.addOrderToBuildingList(orders.get(0));
					orders.remove(0);
				}
			}
		}
	}
	
	
	//getters and setters
	
	public void addFactory1(Factory1 f) {
		factories1.add(f);
	}
	
	public void addOrder(int[] newOrder) {
		orders.add(newOrder);
	}

	public ArrayList<Factory1> getFactories1() {
		return factories1;
	}
	
	
}
