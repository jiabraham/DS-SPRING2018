package project3;

import java.util.ArrayList;

/*This Program way finder takes a command line argument of numbers, put them into an array list, and discovers if there are any solutions
 * to the puzzle as listed in the project specifications
 * @author Joseph Abraham
 * @date 3/28/17
 */
public class WayFinder {
	
	//Default constructor so that variables and data fields can be accessed in the main class
	public WayFinder() {
	
	}
	/*This method begins the search algorithm for potential solutions.
	 * @param int current_positions this is used to determine where exactly you are in the path, but in our case the starting index is always zero
	 */
	public void search(int current_position) {
		Path path = new Path();
		//Uses generic path constructor because there are no moves to be stored yet
		paths.add(path);
		//adds this path to the array list of paths
		searchNext(current_position, path);
		//calls the searchNext function because the parameters and execution is different after the very first step
	}
	/*This method is to cover all the moves made after the very first one because now all the previous moves need to be stored as well
	 * @param int current_position is the current index or the result of the previous move
	 * @param Path path this method inherits the previous path and will declare a new path with the next move in a potential solution
	 * @return A boolean is returned whether or not this method is finished recursing because when it returns true only when the path is a viable solution
	 */
	public boolean searchNext(int current_position, Path path) {
		if (current_position == goal_position) {
			//If this method does take in a complete solution(index = size - 1), no more recursing is necessary
			path.setComplete();
			//The boolean data field of the path can be set true because it is a viable path
			return true;
			//The return true ends the recursing
		}
		//System.out.println("==DUMP PATHS===");
		//needed to debug, showed all the paths, even the ones that ended up failing
		for (int i=0; i< paths.size(); i++) {
			//running through each element of the paths array list
			paths.get(i).displayPath();
			//displayPath() was used in debugging to output every step of a path
		}
		//System.out.println("==END DUMP PATHS===");
		//System.out.println("++++\nSearchRight: " + current_position + " orig = " + original_moves.get(current_position) + " path number: " + path.path_number);
		//Both print statements used in debugging to describe the status at every step of a path
		path.displayPath();
		//Again, not part of the actual search algorithm
		if (path.isAllowed(current_position) && tryRight(current_position)) {
			//If this is not an index that's been visited before and 
			//tryRight returns true means the move to the right can be executed without an out of bounds error 
			Path new_path = new Path(path);
			//Declares new path using the more complex constructor because previous moves have to be saved
			paths.add(new_path);
			//Adds to array list of paths
			int next_position = current_position + original_moves.get(current_position);
			//defines what the next index will be based on the current one of the path that was passed in
			new_path.setAdvance(current_position, "R");
			//calls setAdvance() method to update the covered_positions array list and the directions array list
			searchNext(next_position, new_path);
			//Recurses and calls itself with the new path with all data fields updated and the next position which will determine whether the solution can have another step, is done, or simply cannot move anymore
		}
		//System.out.println("====\nSearchLeft: " + current_position + " orig = " + original_moves.get(current_position) + " path number: " + path.path_number);
		//Print statement used in debugging to check status at every step
		path.displayPath();
		//More debugging
		if (path.isAllowed(current_position) && tryLeft(current_position)) {
			//If this is not an index that's been visited before and 
			//tryLeft returns true means the move to the left can be executed without an out of bounds error 
			Path new_path = new Path(path);
			//Declares new path using the more complex constructor because previous moves have to be saved
			paths.add(new_path);
			//Adds to array list of paths
			int next_position = current_position - original_moves.get(current_position);
			//defines what the next index will be based on the current one of the path that was passed in
			new_path.setAdvance(current_position, "L");
			//calls setAdvance() method to update the covered_positions array list and the directions array list
			searchNext(next_position, new_path);
			//Recurses and calls itself with the new path with all data fields updated and the next position which will determine whether the solution can have another step, is done, or simply cannot move anymore
		}
		return false;
		//returns false means that no move can be made and the path is not viable
	}
	/*Method called to execute the printing of the totality of the paths array list showing all the steps of a given solution
	 * 
	 */
	public void print_paths() {
		int num_paths = 0;
		//Initially the number of solutions should be zero
		for (int i = 0; i < paths.size(); i++) {
			//Running all the elements of the array list that holds paths
			if (paths.get(i).printPath(original_moves)) {
				//If the method printPath returns true it means it found a viable solution
				num_paths++;
				//This increments the total number of solutions for a puzzle for the output statement at the end
			}
		}
		System.out.println("There are " + num_paths + " ways through the puzzle.");
		//Output statement according to the project specifications
	}
	/*if there isn't a single number in the array that is length minus position of that number and it's the first check necessary
	 * @param ArrayList<Integer> original is taken in because it is the actual puzzle and needs to be examined
	 * @return A boolean is returned whether or not a puzzle is potentially solvable
	 */
	public boolean oneValidWay(ArrayList<Integer> original) {
		for (int i = 0; i < original.size()-1; i++) {
			//Runs through all the indices of the original puzzle
			if (original_moves.get(i) + i == original_moves.size()-1) {
				return true;
			}
		}
		return false;
	}
	/*This method checks for any negative numbers in the puzzle
	 *@param ArrayList<Integer> original needs to be passed into this method to be examined for any negative numbers
	 *@return a boolean value is returned indicated whether the array list is only made up of positive numbers
	 */
	public boolean onlyPositives(ArrayList<Integer> original) {
		for (int i = 0; i < original.size()-1; i++) {
			//Runs through  all the indices of the original array except the last one
			if (original_moves.get(i) <= 0) {
				//If any are equal to or less than zero it isn't a solvable puzzle
				return false;
			}
		}
		return true;
	}
	/*This method checks for numbers greater than 99 which don't need to be included according to the specifications
	 * @param ArrayList<Integer> original needs to be passed into this method to be examined for any numbers greater than 99
	 * @return A boolean value is returned whether or not any numbers are greater than 99
	 */
	public boolean smallerThan99(ArrayList<Integer> original) {
		for (int i = 0; i < original.size()-1; i++) {
			//Runs through  all the indices of the original array except the last one
			if (original_moves.get(i) >= 99) {
				//If any are greater than 99, this puzzle doesn't need to be solved
				return false;
			}
		}
		return true;
	}
	/*This method determines whether or not moving to the right is a viable move
	 * @param int index is passed in to know what exactly the next position should be if possible
	 * @return a boolean value that determines whether or not a move to the right is possible given the index
	 */
	public boolean tryRight(int index) {
		int n = index + original_moves.get(index);
		//Using another integer first and examining it prevents any IndexOutOfBoundsError
		if (n >= original_moves.size()) {
			//Moving right can only mean the out of bounds was greater than the array size
			return false;
		}
		return true;
	}
	public boolean tryLeft(int index) {
		//Using another integer first and examining it prevents any IndexOutOfBoundsError
		int n = index - original_moves.get(index);
		if (n < 0) {
			//Moving left can only mean the out of bounds was less than the 0
			return false;
		}
		return true;
	}
	public int goal_position = 0;
	//This represents the determining factor in whether or not a path is viable
	public ArrayList<Path> paths = new ArrayList<Path>();
	//This holds all the different solutions possible
	public ArrayList<Integer> original_moves = new ArrayList<Integer>();
	//This holds the original puzzle
			
	/*This is the main method of the program which pulls from wayfinder and path to find and print viable solution.
	 * It also creates a command line argument for a list of numbers to come in
	 */
	public static void main(String[] args) throws IllegalArgumentException
	{
		if (args.length < 2) 
		{
			System.err.println("Usage Error: the program expects a list of integers");
			System.exit(1);
			//we need an actual list to work through
		}
		int last_value = -1;
		//Last value needs to be initialized to check that the last number in the puzzle is indeed zero
		WayFinder wayfinder = new WayFinder();
		//Works through a wayfinder object
		for (int i = 0; i < args.length; i++) {
			//runs through every number in the command line input
			wayfinder.original_moves.add(Integer.parseInt(args[i]));
			//Adds all the command line integers into the array list original 
			last_value = wayfinder.original_moves.get(i);
			//After for loop ends this will hold the last value of the input
			wayfinder.goal_position = i;
			//After the for loop this data field will hold the index
		}
		if (last_value != 0) {
			//check if the last value of the list of numbers is 0
			throw new IllegalArgumentException("Last value must be 0");
		}
		if (!wayfinder.onlyPositives(wayfinder.original_moves)) {
			//Checks if only positive numbers are in the array
			throw new IllegalArgumentException("Puzzle most only contain positive numbers");
		}
		if (!wayfinder.smallerThan99(wayfinder.original_moves)) {
			//checks if there are any numbers larger than 99
			throw new IllegalArgumentException("Puzzle most only contain positive numbers less than 99");
		}
		if (!wayfinder.oneValidWay(wayfinder.original_moves)) {
			//Checks if there is a potential way through the puzzle and the algorithm needs to be used to find it
			System.out.println("No way through this puzzle.");
		}
		
		wayfinder.search(0);
		//calls search which calls search next which recursively calls itself
		//Potentially, the user could be asked for a starting index, but according to the project specifications we only start with index[0]
		wayfinder.print_paths();
		//prints all the solutions found
	}
	
}
