package game.entities.Creator;

import java.awt.Graphics;

import java.util.ArrayList;

import game.Handler;
import game.entities.Entity;
import game.entities.EntityManager;
import game.entities.units.MediumTank;

public class EntityCreator {
	
	protected Handler handler;
	private ArrayList<Entity> factories1;
	private int limit, coord = 300;
	
	/*
	 * The first int the list contain the id of the unit
	 * the second int contain the remain time before the unit is built
	 * 
	 */
	private ArrayList<Integer> listEntityToBuild = new ArrayList<Integer>();
	private boolean unitCreated = false;
	
	private long timer, lastTime;
	private EntityManager entityManager;
	
	protected int quantity, creationTime;
	
	public EntityCreator(Handler handler, EntityManager entityManager) {
		this.handler = handler;
		this.entityManager = entityManager;
		factories1 = new ArrayList<Entity>();
		
	}
	
	public void addToCreateList(int quantity, int idUnit, int timeToBuild) {
		for(int x = 0; x < quantity; x++){;
			listEntityToBuild.add(idUnit);
			listEntityToBuild.add(timeToBuild);
		}
	}
	
	//tick and render in the EntityManager
	public void tick() {
		
		//update the list every second
		if(!listEntityToBuild.isEmpty() && System.currentTimeMillis() - lastTime >= 50) {
			lastTime = System.currentTimeMillis();
			
			//Update timers, eventually create new unit
			upDateBuildingTime();
			
			//If a unit has been created, remove the order from the list
			if(unitCreated) {
				removeFromList();
			}
		}
	}

	
	public void render(Graphics g) {
		
	}
	
	private void upDateBuildingTime() {
		/*
		 *We have to determine a limit of the loop before entering it, if all the factory are
		 *not producing units, the loop must be limited to the number of order currently treated
		 *factories without order would cause an ArrayOutOfBounds exception for trying to
		 *execute an order that doesn't exist
		 */
		if(factories1.size() <= listEntityToBuild.size()/ 2) {
			limit = factories1.size();
		} else {
			limit = listEntityToBuild.size()/ 2;
		}
		
		//for every factory in the game, decrease the time to build at the index x by 1
		for(int x = 0; x < limit; x++) {
			
			//If there is still time before the unit can be created, effectively remove 1 to the remaining time
			if(listEntityToBuild.get(x * 2 + 1) > 0) {
				listEntityToBuild.set(x * 2 + 1, listEntityToBuild.get(x * 2 + 1)  - 1);
				/*
				 * If it is time to create a new unit
				 * Note that we don't delete order during the for loop or it would create an
				 * exception ArrayOutOfBounds if the next factory tried to access the list
				 * 
				 */
			} else {
				//if the unit was a mediumTank id = 0, timeToBuild = 30
				if(listEntityToBuild.get(x * 2) <= 0) {
					entityManager.addEntity(new MediumTank(handler, coord, 300));
					unitCreated = true;
					coord += 100;
				}
			}
		}
	}
	
	private void removeFromList() {
		//remove order from the list we start by the end so we don't get an ArrayOutOfBounds exception
		for(int x = listEntityToBuild.size()/2- 1; x >= 0; x--) {
			if(listEntityToBuild.get(x * 2 + 1) <= 0) {
				listEntityToBuild.remove(x);
				listEntityToBuild.remove(x);
			}
		}
		unitCreated = false;
	}
	
	//Getters and setters
	
	public ArrayList<Entity> getFactories() {
		return factories1;
	}
	
	public void addFactory1(Entity e) {
		factories1.add(e);
	}
}
