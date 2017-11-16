package game.pathfinding;

import java.util.ArrayList;


import game.Handler;



public class MovementAlgo {
	//make the method return a 2D array with the coord of the tile to go
	private Handler handler;
	private int[][] solidMap, heuristic, movingCost;
	private int[][] openList;
	private boolean[][] closedList;
	private int width, height;
	//private PathFinding pathFinding;
	
	public MovementAlgo(Handler handler) {
		this.handler = handler;
		this.solidMap = handler.getWorld().getSolidMap();
		width = handler.getWorld().getWidth();
		height = handler.getWorld().getHeight();
		
	}
	
	public ArrayList<Integer> getPath(int startY, int startX, int destY, int destX) {
		int minGValueY = 0, minGValueX = 0;
		int turn = 0;
		int saveStartX = startX;
		int saveStartY =startY;
		boolean found = false;
		
		openList = new int[width][height];
		closedList = new boolean[width][height];
		heuristic = new int[width][height];
		movingCost = new int[width][height];
		
		
		//----------------------------------------------------Heuristics calculations---------------------
		//set heuristic value and -1 on openList if solid
		for(int y = 0; y < height; y ++) {
			for(int x = 0; x < width; x ++) {
				if(solidMap[y][x] == 1) {
					heuristic[y][x] = -1;
					openList[y][x] = -1;
					//if map is ok
				} else if(solidMap[y][x] == 0){
					heuristic[y][x] = Math.abs(destY - y) + Math.abs(destX - x);
					openList[y][x] = 0;
					if(y == startY && x == startX) {
						openList[y][x] = heuristic[y][x];
						//Calcul a first coord of GValue next to the start to be compare later
						if(solidMap[y + 1][x] == 0 && y <= height) {
							minGValueY = y + 1;
							minGValueX = x;
							
						} else if(solidMap[y][x + 1] == 0 && x <= width) {
							minGValueY = y;
							minGValueX = x + 1;
							
						}else if(solidMap[y][x - 1] == 0 && x >= 0) {
							minGValueY = y;
							minGValueX = x - 1;
						} else if(solidMap[y - 1][x] == 0 && y >= 0) {
							minGValueY = y - 1;
							minGValueX = x;
						}
					}
				}
			}
		}
		
		closedList[startY][startX] = true;
		movingCost[startY][startX] = 0;
		
		
		while (found == false){
			turn++;
		//System.out.println(startY + " " + startX);
			
			
	//calculate value of the Gvalue from the start (heuristic + 10 or 12) check if x/y +1/-1 is 
	//within array's bounds
	//-1 means solid, 0 mean not calculate yet, 0 in the heuristic means finish!
	if(startY + 1 < height && heuristic[startY + 1][startX] != -1 && 
	closedList[startY + 1][startX] == false) {
		
		//done
		if(movingCost[startY][startX] + 10 +heuristic[startY + 1][startX] < openList[startY + 1][startX] || openList[startY + 1][startX]== 0) {
		movingCost[startY + 1][startX] = movingCost[startY][startX] + 10;
		openList[startY + 1][startX] = heuristic[startY + 1][startX] + movingCost[startY + 1][startX];
		}
	}
	if(startY - 1 >= 0 && startX + 1 < width && heuristic[startY - 1][startX + 1] != -1 &&
			closedList[startY + 1][startX + 1] == false) {
		
		//done
		if(movingCost[startY][startX] + 12 + heuristic[startY - 1][startX + 1] < openList[startY - 1][startX + 1] || openList[startY - 1][startX + 1]== 0) {
			movingCost[startY - 1][startX + 1] = movingCost[startY][startX] + 12;
			openList[startY - 1][startX + 1] = heuristic[startY - 1][startX + 1] + movingCost[startY - 1][startX + 1];
		}
	}
	if(startX + 1 < width && heuristic[startY][startX + 1] != -1 &&
			closedList[startY][startX + 1] == false) {
		
		//done
		if(movingCost[startY][startX] + 10 + heuristic[startY][startX + 1] < openList[startY][startX + 1] || openList[startY][startX + 1]== 0) {
			movingCost[startY][startX + 1] = movingCost[startY][startX] + 10;
			openList[startY][startX + 1] = heuristic[startY][startX + 1] + movingCost[startY][startX + 1];
		}
	}
	if(startY + 1 < height  && startX + 1 < width && heuristic[startY + 1][startX + 1] != -1 &&
			closedList[startY + 1][startX + 1] == false) {
		
		//done
		if(movingCost[startY][startX] + 12 + heuristic[startY + 1][startX + 1] < openList[startY + 1][startX + 1] || openList[startY + 1][startX + 1]== 0) {
			movingCost[startY + 1][startX + 1] = movingCost[startY][startX] + 12;
			openList[startY + 1][startX + 1] = heuristic[startY + 1][startX + 1] + movingCost[startY + 1][startX + 1];
		}
	}
		
	
	if(startY - 1 >= 0 && heuristic[startY - 1][startX] != -1 &&
			closedList[startY - 1][startX] == false) {
		
		//done
		if(movingCost[startY][startX] + 10 +heuristic[startY - 1][startX] < openList[startY - 1][startX] || openList[startY - 1][startX]== 0) {
			movingCost[startY - 1][startX] = movingCost[startY][startX] + 10;
			openList[startY - 1][startX] = heuristic[startY - 1][startX] + movingCost[startY - 1][startX];
		}
	}
	if(startY - 1 >= 0 && startX - 1 >= 0 && heuristic[startY - 1][startX - 1] != -1 &&
			closedList[startY - 1][startX - 1] == false) {
		
		//done
		if(movingCost[startY][startX] + 12 +heuristic[startY - 1][startX - 1] < openList[startY - 1][startX - 1] || openList[startY - 1][startX - 1]== 0) {
			movingCost[startY - 1][startX - 1] = movingCost[startY][startX] + 12;
			openList[startY - 1][startX - 1] = heuristic[startY - 1][startX - 1] + movingCost[startY - 1][startX - 1];
		}
	}
	if(startX - 1 >= 0 && heuristic[startY][startX - 1] != -1 &&
			closedList[startY][startX - 1] == false) {
		
		//done
		if(movingCost[startY][startX] + 10 +heuristic[startY][startX - 1] < openList[startY][startX - 1] || openList[startY][startX - 1]== 0) {
			movingCost[startY][startX - 1] = movingCost[startY][startX] + 10;
			openList[startY][startX - 1] = heuristic[startY][startX - 1] + movingCost[startY][startX - 1];
		}
	} 
	if(startY + 1 < height && startX - 1 >= 0 && heuristic[startY + 1][startX - 1] != -1 &&
			closedList[startY + 1][startX - 1] == false) {
		
		//done
		if(movingCost[startY][startX] + 12 +heuristic[startY + 1][startX - 1] < openList[startY + 1][startX - 1] || openList[startY + 1][startX - 1]== 0) {
			movingCost[startY + 1][startX - 1] = movingCost[startY][startX] + 12;
			openList[startY + 1][startX - 1] = heuristic[startY + 1][startX - 1] + movingCost[startY + 1][startX - 1];
		}
	}

	//look for the coords of the lowest Gvalue
	int toCompare = 0;
	boolean firstValueFound = true;
	for(int y = 0; y < height; y++) {
		for(int x = 0; x < height; x++) {
			//if the number is not in the closedList but has been calculate one time at least
			if(openList[y][x]!=0 && openList[y][x]!=-1 && closedList[y][x]==false) {
				toCompare = openList[y][x];
				if(firstValueFound) {
					minGValueY = y;
					minGValueX = x;
					firstValueFound= false;
				} else if(toCompare < openList[minGValueY][minGValueX]) {
					minGValueY = y;
					minGValueX = x;
				}
			}

		}
	}
	//System.out.println(openList[saveStartY][saveStartX]);
	//System.out.println(saveStartY + " "+saveStartX);
	
	//set the starting point for next turn
	//System.out.println(startY + " " + startX);
	closedList[minGValueY][minGValueX] = true;
	startY = minGValueY;
	startX = minGValueX;
	
	/*
		for(int y = 0; y < height; y ++) {
			for(int x = 0; x < width; x ++) {
				//System.out.print(solidMap[y][x]);
				//System.out.print(heuristic[y][x] + "  ");
				//System.out.print(closedList[y][x]  + "  ");
				System.out.print(openList[y][x]  + "  ");
				//System.out.print(movingCost[y][x] + "  ");
			}
			System.out.println();
		}
		System.out.println(startY + " " + startX);
		System.out.println("---------------------------------------------------");
		*/
		/*
		for(int y = 0; y < height; y ++) {
			for(int x = 0; x < width; x ++) {
				//System.out.print(solidMap[y][x]);
				//System.out.print(heuristic[y][x] + " ");
				//System.out.print(closedList[y][x]  + "  ");
				System.out.print(openList[y][x]  + "  ");
			}
			//System.out.println();
		}
		System.out.println("----------------------" + turn + "-----------------------------");
		*/
		if(startY == destY && startX == destX) {
			found = true;
		}
	}
		/*
		for(int y = 0; y < height; y ++) {
			for(int x = 0; x < width; x ++) {
				//System.out.print(solidMap[y][x]);
				//System.out.print(heuristic[y][x] + " ");
				//System.out.print(closedList[y][x]  + "  ");
				System.out.print(openList[y][x]  + "  ");
			}
			System.out.println();
		}
		System.out.println("----------------------" + turn + "-----------------------------");
	*/
		int pathX = destX, pathY = destY;
		int[] possibleAnswer = new int[8];
		int minGvalue = openList[destY][destX];
		ArrayList<Integer> coord = new ArrayList<Integer>();
		coord.add(pathY);
		coord.add(pathX);
		found = false;
		while(!found) {
			
			for(int y = 0; y < 8; y++){
				possibleAnswer[y] = -1;
			}
			if(pathY + 1 < height && openList[pathY + 1][pathX] > 0) { //down
				possibleAnswer[0] = openList[pathY + 1][pathX];
			}
			if(pathY + 1 < height && pathX + 1 < width && openList[pathY + 1][pathX + 1] !=0 &&
					 openList[pathY + 1][pathX + 1] !=-1)//down right
				possibleAnswer[1] = openList[pathY + 1][pathX + 1];			
			if(pathX + 1 < width && openList[pathY][pathX + 1] > 0)//right
				possibleAnswer[2] = openList[pathY][pathX + 1];
			if(pathY - 1 >= 0 && pathX + 1 < width && openList[pathY - 1][pathX + 1] > 0)//up right
				possibleAnswer[3] = openList[pathY - 1][pathX + 1];//
			if(pathY - 1 >= 0 && openList[pathY - 1][pathX] > 0)//up
				possibleAnswer[4] = openList[pathY - 1][pathX];				
			if(pathY - 1 >= 0 && pathX - 1 >= 0 && openList[pathY - 1][pathX - 1] > 0)//up left
				possibleAnswer[5] = openList[pathY - 1][pathX - 1];//
			if(pathX - 1 >= 0 && openList[pathY][pathX - 1] > 0)//left
				possibleAnswer[6] = openList[pathY][pathX - 1];//
			if(pathY + 1 < height && pathX - 1 >= 0 && openList[pathY + 1][pathX - 1] > 0)//down left
				possibleAnswer[7] = openList[pathY + 1][pathX - 1];//
			
			//here i forgot to give value to GValue
			int answerIs = 0;
			
			minGvalue = possibleAnswer[0];
			//which of the previous cases is the solution / lowest gValue
			for(int x = 0; x < possibleAnswer.length - 1; x++) {
				if(minGvalue< 0 || (possibleAnswer[x + 1] < minGvalue && possibleAnswer[x + 1] > 0)) {
					answerIs = x + 1;
					minGvalue = possibleAnswer[x + 1];
					
				}
			}
			//System.out.println("Gvalue = " +minGvalue + "   " + answerIs);
			//System.out.println("pathY = " +pathY + "pathX = " +pathX );
			//build the final String containing the answer and set the value for the next search
			//if the answer has not been found yet
			if(answerIs == 0) {	//down
				coord.add(pathY + 1);
				coord.add(pathX);
				coord.add(0);
				pathY ++;
			} else if(answerIs == 1) {//down right
				coord.add(pathY + 1);
				coord.add(pathX + 1);
				coord.add(1);
				pathY ++;
				pathX++;
			} else if(answerIs == 2) {//right
				coord.add(pathY); 
				coord.add(pathX + 1);
				coord.add(2);
				pathX++;
			} else if(answerIs == 3) {//up right
				coord.add(pathY - 1);
				coord.add(pathX + 1);
				coord.add(3);
				pathY--;
				pathX++;
			} else if(answerIs == 4) {//up
				coord.add(pathY - 1);
				coord.add(pathX);
				coord.add(4);
				pathY--;

			} else if(answerIs == 5) {//up left
				coord.add(pathY -1 );
				coord.add(pathX - 1);
				coord.add(5);
				pathX--;
				pathY--;
			} else if(answerIs == 6) {//left
				coord.add(pathY);
				coord.add(pathX - 1);
				coord.add(6);
				pathX--; 
			} else if(answerIs == 7) {
				coord.add(pathY + 1);//down left
				coord.add(pathX - 1);
				coord.add(7);
				pathX--;
				pathY++;
			} 
			
			//System.out.println("pathY path X" +pathY + " " +pathX + " saves " + saveStartY + " " + saveStartX);
			if(pathX == saveStartX && pathY == saveStartY) {
				found = true;
			}
		}
		System.out.println(coord);
		return coord;
}

}

	
	
	
