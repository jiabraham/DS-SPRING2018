package project3;
import java.util.ArrayList;

/*This class defines the object path which will be used in WayFinder to discover different paths in different arrays.
 * @author Joseph Abraham
 * @date 3/29/18
 */
public class Path {
	
	
	/* First constructor for path objects which is called every time a new move is made in the search algorithm. 
	 * @param Path path is passed into this constructor so that it can essentially update the path in that in found a new move that is viable.
	 */
	public Path(Path path) {
		this.covered_positions = new ArrayList<Integer>(path.covered_positions);
		//This set the new covered positions array to be the same as the parameter, that is then updated in WayFinder
		this.directions = new ArrayList<String>(path.directions);
		//This set the new directions array to be the same as the parameter, that is then updated in WayFinder
		this.complete_path = false;
		//This boolean still must be false because the path has not gotten to zero
		path_number = overall_paths;
		//needed in displayPath() for debugging;
		overall_paths++;
		//Also needed for debugging to keep track of all the different paths, not just the current step
	}
	/*This constructor was created for all original path objects that are not based on any previous moves because the first move is stored here.
	 */
	public Path() {
		complete_path = false;
		//This boolean still must be false because the path has not gotten to zero
		path_number = overall_paths;
		//This incremented the path_number to match which path was currently in use
		overall_paths++;
		//Incremented the overall path to match the total.
	}
	/*This method was used in debugging to find out what exactly was making the program not back-trace and look through every possible distinct path
	 * Essentially, this method printed out what indexes are being checked after every single move in the search algorithm
	 */
	public void displayPath() {
		// System.out.print("Display Path " + path_number);	
		for (int i=0; i < covered_positions.size(); i++)  {
			// System.out.print(" " + covered_positions.get(i) + directions.get(i));	
			//Printed out exactly what was happening at every step
			//Ran through the covered positions arraylist which was to keep track of indices(indexes) and prevented infinite loops from occurring
		}
		// System.out.println("");
	 }  
	/*This method was to add the variable position to keep track of previous indices and add the direction being traveled in concert with that particular index
	 * @param int position to be stored in covered_positions array
	 * @param String direction to be stored in directions array
	 */
	public void setAdvance(int position, String direction) {
		//System.out.println("setAdvance: " + position + " direction " + direction);
		covered_positions.add(position);
		//updating covered_positions
		directions.add(direction);
		//updating directions
	}
	/*This method is to figure out if a potential move is trying to return to an already visited index which is not productive in finding viable solutions
	 * @param int position is needed to check the array covered positions
	 * @return a boolean is returned indicating whether a move will be compliant in making the search algorithm enter into an infinite loop
	 */
	 public boolean isAllowed(int position) {
		if (covered_positions.contains(position)) {
			//System.out.println("IsAllowed: false " + position);
			//Printed out in debugging to check status
			return false;
		}
		//System.out.println("IsAllowed: true " + position);
		//Printed out in debugging to check status
		return true;
	}
	 /*This method is a standard "setter" to change the value of the boolean complete_path for when the position reaches the end of the array
	  */
	public void setComplete() {
		complete_path = true;
	}
	/*Standard "getter" to reveal the status of a path having reached the end or not
	 * @return complete_path is returned so that the search algorithm can know if it's found a viable way through the puzzle
	 */
	public boolean isComplete() {
		return complete_path;
	}
	/*This method takes the place of overriding the toString method traditionally used with newly programmed objects that are not normally contained within java itself
	 * A boolean was returned during dubugging but not really needed(A boolean true here is returned if and only if the particular path we are printing has reached the end of the ArrayList, otherwise false is returned and no printing is done)
	 * @param ArrayList<Integer> original_moves is used to create each printed ArrayList that demonstrates one move made by the WayFinder algorithm
	 * @return A boolean true here is returned if and only if the particular path we are printing has reached the end of the ArrayList, otherwise false is returned and no printing is done)
	 */
	public boolean printPath(ArrayList<Integer> original_moves) {
		//First need to check whether or not this is a viable solution
		if (complete_path) {
			for (int i = 0; i < covered_positions.size(); i++) {
				//for loop runs through all the indices visited by the search
				int current_position = covered_positions.get(i);
				//Integer not really needed just useful in describing which step is currently taking place in the print process
				String out = "[ ";
				//Project specs
				for (int j = 0; j< original_moves.size() - 1; j++) {
				//Nested loop because for every covered position we need the whole original array to formulate the steps according to the specs
					if (current_position == j) {
						//if the current position is the same as our index j, then we need to add the direction from the direction ArrayList
						out += Integer.toString(original_moves.get(j)) + directions.get(i);
						//adding to the string each individual move
					}
					else {
						out += Integer.toString(original_moves.get(j)) + " ";
						//If it's not the index which is moved upon, then we just need the regular index of the original array
					}
					out += ",  ";
					//Project specifications require comma and two spaces
				}
				out += "0 ]";
				//Project specifications require that zero be the last index of the puzzle
				System.out.println(out);
				//Print statement of created string with directions
			}
			System.out.println("");
			//Space in between the different solutions of the puzzle if there is more than one
			return true;
			//Returns true with a correct path so that the total number of solutions can be incremented
		}
		return false;
		//If no correct paths exist
	}
	static int overall_paths = 0;
	//Static integer used in debugging to represent how many total steps were being taken
	int path_number = 0;
	//Integer used in debugging to indicate which step was currently under consideration
	boolean complete_path;
	//boolean whether or not a path was a viable solution to the puzzle
	ArrayList<Integer> covered_positions = new ArrayList<Integer>();
	//ArrayList for all the indices visited of the ArrayList inputed in the command line argument
	ArrayList<String> directions = new ArrayList<String>();
	//ArrayList for all the directions which come at Strings and the same index of the previous array list declared will be the direction traveled in this array list
}
