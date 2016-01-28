package board;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Queue;
import java.util.Set;

import squares.Door;
import squares.Room;
import squares.Square;

/**
 * This object will find all valid moves on the board within the distance of the dice roll
 * from the position the actor is in.
 *
 * This uses a flooding algorithm in a breadth first search.
 * @author northleon
 *
 */
public class PathFinder {

	private Square[][] squares;

	private Board board;

	/**
	 * This object is only made once at the beginning of the game then is used for each diceroll
	 * @param squares
	 * @param board
	 */
	public PathFinder(Square[][] squares, Board board) {
		this.squares = squares;
		this.board = board;
	}

	/**
	 * The only method called from outside this class. It will perform a flooding algorithm.
	 *
	 * The 2 main cases are either the actor is on a corridor square and when the actor is in a room.
	 *
	 * When an actor is in a room, start the search by adding all the doors in the room to the queue.
	 *
	 * When an actor is in the corridor, begin by adding the square it is on to the queue.
	 *
	 * In both cases, it continues to do a breadth first search, adding all squares that are corridors
	 * or doors until the depth of the dice roll.
	 *
	 * return the list of all visited squares.
	 *
	 * @param roll
	 * @param start
	 * @param coordinates
	 * @return
	 */
	public List<int[]> checkValidMove(int roll, Square start,	int[] coordinates) {
		List <int[]> validMoves  = new ArrayList <int[]>();
		Set  <Square> set    	 = new HashSet   <Square>();
		Queue<Square> queue		 = new ArrayDeque<Square>();

		//if we are in a room, perform the slightly different algorithm, then return
		if (start instanceof Room){
			validMoves = movesFromRoom( validMoves, set, queue, (Room)start, roll);
			addExceptions(validMoves, start);
			return validMoves;
		}

		//begin the algorithm if player is not in a room:
		set.add(start);

		for (int depth = 0; depth < roll; depth++){//this outter loop will count to the depth of the dice roll

			//add everything in the set to the queue
			for(Square s: set){
				queue.add(s);
			}
			//delete everything in the set to prevent repeating calculations
			set.clear();

			//for everything in the queue, add it's children
			while(!queue.isEmpty()){

				//pop square from queue
				Square square = queue.remove();

				//find the squares coords
				int coords[] = board.getSquareCoordinates(square);
				int i = coords[0];
				int j = coords[1];

				if (!square.getName().contains("Door")) {//cannot walk out a door

					//Add Surrounding Squares
					if (checkSquareIsValid(i + 1, j)) {
						set.add(squares[i + 1][j]);
						validMoves.add(new int[]{i+1,j});
					}
					if (checkSquareIsValid(i - 1, j)) {
						set.add(squares[i - 1][j]);
						validMoves.add(new int[]{i-1,j});
					}
					if (checkSquareIsValid(i, j + 1)) {
						set.add(squares[i][j + 1]);
						validMoves.add(new int[]{i,j+1});
					}
					if (checkSquareIsValid(i, j - 1)) {
						set.add(squares[i][j - 1]);
						validMoves.add(new int[]{i,j-1});
					}
				}
			}
		}
		return validMoves;
	}

	/**
	 * Adds the stairwells/portals beween corner rooms
	 * @param validMoves
	 * @param start
	 */
	private void addExceptions(List<int[]> validMoves, Square start) {
		Room room = (Room)start;
		if(room.getName().equals("Conservatory")){ validMoves.add(new int []{20, 0});}
		else if(room.getName().equals("Study")){ validMoves.add(new int []{0 , 5});}
		else if(room.getName().equals("Lounge")){ validMoves.add(new int []{0 , 23});}
		else if(room.getName().equals("Kitchen")){ validMoves.add(new int []{21 , 23});}
	}

	/**
	 * This will perform the flooding algorithm from a room
	 * @param validMoves
	 * @param set
	 * @param queue
	 * @param start
	 * @param roll
	 * @return
	 */
	private List<int[]> movesFromRoom(List<int[]> validMoves, Set<Square> set,
			Queue<Square> queue, Room start, int roll) {

		//find coords of each door for the room the actor is in
		List<int[]> listOfDoorCoords = board.getDoorCoordinates(start.getDoors().get(0));

		//add all the doors to the set
		for(int[] coords: listOfDoorCoords){
			int i = coords[0];
			int j = coords[1];

			if (checkSquareIsValid(i + 1, j)) {
				set.add(squares[i + 1][j]);
				validMoves.add(new int[]{i+1,j});
			}
			if (checkSquareIsValid(i - 1, j)) {
				set.add(squares[i - 1][j]);
				validMoves.add(new int[]{i-1,j});
			}
			if (checkSquareIsValid(i, j + 1)) {
				set.add(squares[i][j + 1]);
				validMoves.add(new int[]{i,j+1});
			}
			if (checkSquareIsValid(i, j - 1)) {
				set.add(squares[i][j - 1]);
				validMoves.add(new int[]{i,j-1});
			}
		}

		for (int depth = 0; depth < roll-1; depth++){

			//add everything from the set to the queue
			queue.addAll(set);
			set.clear();
			while(!queue.isEmpty()){//for everything in the queue

				//pop the square from the queue
				Square square = queue.remove();

				//find its coords
				int coords[] = board.getSquareCoordinates(square);
				int i = coords[0];
				int j = coords[1];

				if (!square.getName().contains("Door")) {

					//Add Surrounding Squares
					if (checkSquareIsValid(i + 1, j)) {
						set.add(squares[i + 1][j]);
						validMoves.add(new int[]{i+1,j});
					}
					if (checkSquareIsValid(i - 1, j)) {
						set.add(squares[i - 1][j]);
						validMoves.add(new int[]{i-1,j});
					}
					if (checkSquareIsValid(i, j + 1)) {
						set.add(squares[i][j + 1]);
						validMoves.add(new int[]{i,j+1});
					}
					if (checkSquareIsValid(i, j - 1)) {
						set.add(squares[i][j - 1]);
						validMoves.add(new int[]{i,j-1});
					}
				}
			}
		}

		return validMoves;
	}

	/**
	 * Confirms if a square is a valid square to move to.
	 * @param i
	 * @param j
	 * @return
	 */
	private boolean checkSquareIsValid(int i, int j) {
		//make sure the coordinates are in bounds
		if(i < 0 || j < 0 ||
		   i >= squares.length ||
		   j >= squares[i].length
			){
			return false;
		}

		Square temp = squares[i][j];

		//make sure no actors on the square
		if (temp.getActors().size() > 0){
			return false;
		}
		//make sure the square is a corridor
		if (!(temp.getName().contains("Corridor")
				|| temp.getName().contains("oor")
				)){
			return false;
		}

		return true;
	}
}
