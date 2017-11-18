package game.pathfinding;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.swing.plaf.synth.SynthSpinnerUI;

import game.Handler;

class Edge{
	
	int v, w;
	public Edge(int v, int w) {
		this.v = v; this.w = w;
	}
	
	@Override
	public String toString(){
		return "("+v+","+w+")";
	}
	
	public int getValue() {
		return this.w;
	}
	
	public int getId() {
		return this.v;
	}
	
	void changeWeight(Edge e, int moveCost) {
		e.w += moveCost;
		
	}
	
	void changeWeight(int moveCost) {
		this.w += moveCost;
		
	}
}

class Graph{

	
	List<Edge> G[];
	public Graph(int n) {
		
		G = new LinkedList[n];
		for(int x = 0; x <G.length;x++) {
			G[x] = new LinkedList<Edge>();
		}
	}
	
	List<Edge> getEdgeByCoord(int y, int x, int width) {
		return G[y * width + x];
	}
	
	List<Edge> getEdgeById(int id) {
		return G[id];
	}
	
	/*
	 * The following method add edges to the node 
	 * y * width + x give the ID depending on the x and y coordinate on the map
	 * 
	 * v allows us to create different edge, starting with the current ID of the node
	 */
	
	void addEdge(int y, int x, int width, int v, int w) {
		G[y * width + x].add(new Edge(y * width + x + v, w));
	}
	
	boolean isConnected(int u, int v) {
		for(Edge i : G[u])
			if(i.v==v)return true;
		return false;
	}
	
	public int getId(Edge e) {
		return e.v;
	}
	
	@Override
	public String toString(){
		String result="";
		for(int i=0;i<G.length;i++)
			result+=i+"=>"+G[i]+"\n";
		return result;
	}
}

public class AStartV2{
	
	private Handler handler;
	private int width, height;
	private int[][] solidMap;
	private Graph g;
	
	public Comparator<Edge> valueSorter =  new Comparator<Edge>() {
		@Override
		public int compare(Edge a, Edge b) {
			
			if(a.getValue() < b.getValue())
				return -1;
			else if(a.getValue() > b.getValue())
				return 1;
			else 
				return 0;
		}
	};
	
	public  AStartV2 (Handler handler){
		
		this.handler = handler;
		this.height = handler.getWorld().getHeight();
		this.width  = handler.getWorld().getWidth();
		this.solidMap = handler.getWorld().getSolidMap();


	}
	
	public ArrayList<Integer> getPath(int startY, int startX, int destY, int destX) {
		int idDest = destY * width + destX, idStart =  startY * width + startX;
		Graph g = new Graph(width * height);
		
		List<Edge> openList = new LinkedList();
		List<Edge>closedList = new LinkedList();
		//-------------------------------Creations of all Edges (empty)--------------------------------------
		
		//Here we create all the Edges, they are set to 0 by default and are null if it is a solid tile
		for(int y =0; y < height; y++) {
			for(int x =0; x < width; x++) {
				if(solidMap[y][x] != 1) {
					//cardinals directions 
					if(y + 1 < height && solidMap[y + 1][x] != 1)//down
						g.addEdge(y, x, width, width, 0);
					if( y - 1 >= 0 && solidMap[y - 1][x] != 1)//up
						g.addEdge(y, x, width, -width, 0);
					if(x + 1 < width && solidMap[y][x + 1] != 1)//right
						g.addEdge(y, x, width, 1, 0);
					if(x - 1 >= 0 && solidMap[y][x - 1] != 1)//left
						g.addEdge(y, x, width, -1, 0);
					/*
					//diagonals directions 
					if(y + 1 < height && x + 1 < width && solidMap[y + 1][x + 1] != 1)//down right
						g.addEdge(y, x, width, width + 1, 0);
					if( y - 1 >= 0 && x + 1 < width && solidMap[y - 1][x + 1] != 1)//up right
						g.addEdge(y, x, width, -width +1, 0); 
					if(y + 1 < height && x - 1 >= 0 && solidMap[y + 1][x - 1] != 1)//down left
						g.addEdge(y, x, width, width - 1, 0);
					if(y - 1 >= 0 && x - 1 >= 0 && solidMap[y - 1][x - 1] != 1)//up left
						g.addEdge(y, x, width, -width - 1, 0);*/
				}
			}
		}

		
		
		//--------------------------------Initialization---------------------------------------------------
		
		//add the first elements to the openList
		boolean found = false;

		//add the starting point to the closed list with a value of 0
		closedList.add(new Edge(idStart, 0));
		for(Edge e : g.getEdgeById(idStart)) {
			if(e.getId() == idDest) {
				found = true;
			} else {
				
				e.changeWeight(e,  10);
				openList.add(e);
				openList.get(openList.size()-1).changeWeight(distanceFromDest(openList.get(openList.size()-1).getId(), destY, destX, width));
			}
		}
		
		openList.sort(valueSorter);
		//------------------------------------------Main Loop--------------------------------------------------------------
		//--------------------------------------------------------------------------------------------------------------------
		
		int turn = 0;
		
		while(!found) {
			//System.out.println("-----------------Turn : " + turn + "--------------------");
			//Here we take the lowest value of the openlist which is the first this list being ordered
			//then we add it to the closedList
			Edge edge = openList.get(0);
			
			boolean addEdgeToClosedList = true;
			/*
			because i sometimes remove item here, I had to USE AN ITERATOR + it.remove();
			here we check that the node doesn't exit in the closedList with a lower Value
			if it does but has a higher value we replace it by this one
			*
			*--------------------MISTAKE-------------------------------------------------
			*
			*--1
			*Here is set the following condition with a simple <
			*if(ed.getValue() <= edge.getValue())
			*
			*it resulted that when the values were equals, the process would carry on as usual
			*and the equals values merged together, it resulted in huge number if the path was
			*a bit far away and the algo would even crash / not find the shortest path
			*About that, as the number grew I ended up with negative numbers that would loop
			*the algo indefinitely
			*It illustrate what we've seen at the training, that number simply "loop" if their
			*value overlap their type's limit
			*
			*---2
			*
			*When units were in diagonal movement, the point where their position is determine
			* (top right) could start on a solid tile 
			* i had to deactivate the diagonal move for now
			*/
			Iterator<Edge> it = closedList.iterator();
			while(it.hasNext()) {
				Edge ed = it.next();
				
				//if The node exist in the closedList
				if(ed.getId() == edge.getId()) {
					//if the node has a higher value than the closedList's one, don't add it later
					if(ed.getValue() <= edge.getValue()) {
						addEdgeToClosedList = false;
						//if the tested node has a lower value, remove the previous one, and later
						//add the current node to the closedList
					} else {
						it.remove();
					}
				}
			}
			
			//if we can indeed add the node to the closedList
			if(addEdgeToClosedList) {
				closedList.add(edge);
			}
				
			//we can now remove the tested node from the openList
			openList.remove(edge);
			
			//----------------------------------------------Processing surrounding nodes----------------------------------
			
			/*
			 * Here we calculate both the heuristic and the movement cost
			 * We only need to execute the following code if the node has been added to closedList
			 * Otherwise it means the node was already not the shortest path
			 * 
			 * -----------------------------------MISTAKE--------------------------------------------
			 * Remember that when adding in a linked list, the added element goes at the end the list
			 * 
			 */

			if(addEdgeToClosedList) {
				
				for(Edge e : g.getEdgeById(edge.getId())) {
					
					//If the ID match the destination ID!
					if(e.getId() == idDest) {
						found = true;
						/*
						 *first check that we are not comparing the starting tile
						 *Then calculate the  value by adding the cost of default movement + the movement value of the previous node
						 *that is found by subtracting the heuristic of the previous node from its total value
						 *finally calculate the definite value of the node by adding its own heuristic
						 */
					} else if(e.getId()!=idStart ){
						
						
						//here we check if the edge is a cardinal direction, else it means it is a diagonal
						if(Math.abs(e.getId() - edge.getId()) == 1 || Math.abs(e.getId() - edge.getId()) == width) {
							//add the movement cost to the edge
							e.changeWeight(e,  10 + (edge.getValue() - distanceFromDest(edge.getId(), destY, destX, width)));
							
							openList.add(e);
							//add the heuristic to the edge
							openList.get(openList.size()-1).changeWeight(distanceFromDest(openList.get(openList.size()-1).getId(), destY, destX, width));
							
							//In that case it is a diagonal, we have to add 12 as a movement cost
						} else {
							//add the movement cost to the edge
							e.changeWeight(e,  12 + (edge.getValue() - distanceFromDest(edge.getId(), destY, destX, width)));
							
							openList.add(e);
							//add the heuristic to the edge
							openList.get(openList.size()-1).changeWeight(distanceFromDest(openList.get(openList.size()-1).getId(), destY, destX, width));
						}

					}
				}
			}
			//Simply sort the list
			openList.sort(valueSorter);
			try {
				
				openList.get(0);
			} catch(Exception e) {
				
				e.printStackTrace();
			}
			turn ++;
		}
		
		
		//we pass in the graph to have access to the connection between nodes and the method "isConnected"
		return findShortestPath(closedList, idDest, width, g);
	}
	
	
	//----------------------------------------------FindShortestPath Method-------------------------------------------------
	
	private ArrayList<Integer> findShortestPath(List<Edge>closedList, int idDest, int width, Graph g){
		boolean found = false; ArrayList<Integer> coord = new ArrayList<>();
		
		//I needed to initiate this int, so i set it to the value of last item of the list + 1
		//Meaning that we will necessarily found a shorter value to start later on
		int currentValue = closedList.get(closedList.size() - 1).getValue() + 1;
		
		//These int will be used to determine the direction by comparing the id of the two tiles
		int saveId = 0; int previousId = idDest;
		
		//Add the coord of last tile to the result
		coord.add(idDest/width);
		coord.add(idDest%width);
		
		//--------------------Main Loop------------------
		
		while(!found) {
			//look for all edges in the closedList
			for(Edge e : closedList) {
				//if the current node is connected to the previous tile and has a shorter value it becomes part
				//of the final path
				if(g.isConnected(previousId, e.getId()) && e.getValue() < currentValue) {
					saveId = e.getId(); currentValue = e.getValue(); 
				}	
			}

			/*
			 *Found the direction, then append it to ArrayList<Integer> containing the final path
			 *Note that the first two represents the y and x coordinates on the grid in that order
			 *the the last number added represents the direction
			 * 
			 * ---------------------Mistake------------------
			 * I hardcoded width by 10 because i was working on a 10*10 grid when i began
			 * Be more careful when working with dimension that are not fixed!
			 */
			if(saveId == previousId + width) {//down
				coord.add(saveId/width);
				coord.add(saveId%width);
				coord.add(0);
				
			} else if(saveId == previousId + 1) {//right
				coord.add(saveId/width);
				coord.add(saveId%width);
				coord.add(2);
				
			} else if(saveId == previousId - width) {//up
				coord.add(saveId/width);
				coord.add(saveId%width);
				coord.add(4);
				
			} else if(saveId == previousId - 1) {//left
				coord.add(saveId/width);
				coord.add(saveId%width);
				coord.add(6);
				
			} else if(saveId == previousId + width + 1) {//down right
				coord.add(saveId/width);
				coord.add(saveId%width);
				coord.add(1);
				
			} else if(saveId == previousId - width + 1) {//up right
				coord.add(saveId/width);
				coord.add(saveId%width);
				coord.add(3);
				
			} else if(saveId == previousId - width - 1) {//up left
				coord.add(saveId/width);
				coord.add(saveId%width);
				coord.add(5);
				
			} else if(saveId == previousId + width - 1) {//down left
				coord.add(saveId/width);
				coord.add(saveId%width);
				coord.add(7);
				
			}
			
			//if we are back on the starting point we have found the path
			if(currentValue == 0)
				found = true;
			
			previousId = saveId;
		}
		return coord;
		
	}
	
	private void changeWeight(Edge e,int distanceFromDest) {
		e.w +=distanceFromDest;
	}

	private int distanceFromDest(int id, int destY, int destX, int width) {
		int y = id /width;
		int x = id%width;
		return Math.abs(y - destY)+ Math.abs(x - destX);
	}
}