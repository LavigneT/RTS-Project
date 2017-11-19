package game.entities.Creator;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import game.Handler;
import game.entities.EntityManager;
import game.entities.statics.Factory1;



public class EntityCreator {
	
	protected Handler handler;
	private EntityManager entityManager;
	private ArrayList<Factory1> factories1 = new ArrayList<>();
	private List<int[]> orders = new ArrayList<>();
	
	public EntityCreator(Handler handler, EntityManager entityManager) {
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
}
